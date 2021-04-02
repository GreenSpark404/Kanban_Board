package org.greenspark404.kanbanboard.data;

import org.greenspark404.kanbanboard.data.model.Task;
import org.greenspark404.kanbanboard.data.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {

    @Transactional(readOnly = true)
    List<Task> findByAssignee(User assignee, Sort sort);

    @Transactional(readOnly = true)
    @Query("SELECT task FROM Task task WHERE lower(task.title) LIKE lower(concat('%', :pattern,'%')) " +
            "OR lower(task.description) LIKE lower(concat('%', :pattern,'%'))" +
            "ORDER BY task.lastModificationDate DESC")
    List<Task> findByPattern(@Param("pattern") String pattern);
}
