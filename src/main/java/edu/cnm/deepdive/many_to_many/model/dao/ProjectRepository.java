package edu.cnm.deepdive.many_to_many.model.dao;

import edu.cnm.deepdive.many_to_many.model.entity.Project;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {

  List<Project> findAllByOrderByNameAsc();

}
