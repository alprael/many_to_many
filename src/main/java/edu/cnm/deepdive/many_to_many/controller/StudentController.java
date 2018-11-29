package edu.cnm.deepdive.many_to_many.controller;

import edu.cnm.deepdive.many_to_many.model.dao.ProjectRepository;
import edu.cnm.deepdive.many_to_many.model.dao.StudentRepository;
import edu.cnm.deepdive.many_to_many.model.entity.Project;
import edu.cnm.deepdive.many_to_many.model.entity.Student;
import java.util.List;
import java.util.NoSuchElementException;
import javax.transaction.Transactional;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ExposesResourceFor(Student.class)
@RequestMapping("/students")
public class StudentController {

  private StudentRepository studentRepository;
  private ProjectRepository projectRepository;

  public StudentController(
      ProjectRepository projectRepository, StudentRepository studentRepository) {
    this.projectRepository = projectRepository;
    this.studentRepository = studentRepository;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Student> list() {
    return studentRepository.findAllByOrderByNameAsc();
  }


  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Student> post(@RequestBody Student student) {
    studentRepository.save(student);
    return ResponseEntity.created(student.getHref()).body(student);
  }

  @GetMapping(value = "{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Student get(@PathVariable("studentId") long studentId) {
    return studentRepository.findById(studentId).get();
  }

  @GetMapping(value = "{studentId}", produces = MediaType.TEXT_HTML_VALUE)
  public String getHtml(@PathVariable("studentId") long studentId) {
    return "<html><body"+studentRepository.findById(studentId).get().getName()+"</body></html>";
  }

  @GetMapping(value = "{studentId}/projects", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Project> projectList(@PathVariable("studentId") long studentId) {
    return get(studentId).getProjects();
  }

  @PostMapping(value = "{studentId}/projects", produces = MediaType.APPLICATION_JSON_VALUE,
  consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Project> postProject(@PathVariable("studentId") long studentId,
      @RequestBody Project partialProject) {
    Project project = projectRepository.findById(partialProject.getId()).get();
    Student student = get(studentId);
    student.getProjects().add(project);
    studentRepository.save(student);
    return ResponseEntity.created(project.getHref()).body(project);
  }

  @DeleteMapping(value = "{studentId}/projects/{project}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteStudent(@PathVariable("studentId") long studentId,
      @PathVariable("projectId") long projectId) {
    Student student = get(studentId);
    Project project = projectRepository.findById(projectId).get();
    if (student.getProjects().remove(project)) {
      studentRepository.save(student);
    } else {
      throw new NoSuchElementException();
    }
  }

  @DeleteMapping(value = "{studentId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("studentId") long studentId) {
    studentRepository.deleteById(studentId);
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
  @ExceptionHandler(NoSuchElementException.class)
  public void notFound() {
  }

}
