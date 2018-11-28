package edu.cnm.deepdive.many_to_many.model.dao;

import edu.cnm.deepdive.many_to_many.model.entity.Student;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {

  List<Student> findAllByOrderByNameAsc();

}
