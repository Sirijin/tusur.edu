package ru.tusur.edu.repository.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tusur.edu.entity.TaskCategory;
import ru.tusur.edu.type.task.TaskCategoryType;

public interface TaskCategoryRepository extends JpaRepository<TaskCategory, Long> {


    @Query("select t from TaskCategory t where t.name = ?1")
    TaskCategory findByName(TaskCategoryType name);
}
