package ru.tusur.edu.repository.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tusur.edu.entity.Task;
import ru.tusur.edu.entity.UserTask;
import ru.tusur.edu.security.entity.User;

import java.util.Optional;

public interface UserTaskRepository extends JpaRepository<UserTask, Long> {

    @Query("select u from UserTask u where u.user.id = :userId and u.task.id = :taskId")
    Optional<UserTask> findByUserIdAndTaskId(@Param("userId") Long userId, @Param("taskId") Long taskId);

    @Query("select u.user from UserTask u where u.user.id = :userId")
    User findUserByUserId(@Param("userId") Long userId);

    @Query("select u.task from UserTask u where u.task.id = :taskId")
    Task findTaskByTaskId(@Param("taskId") Long taskId);

    @Query("select (count(u) > 0) from UserTask u where u.user.id = :userId and u.task.id = :taskId")
    boolean existsByUserIdAndTaskId(@Param("userId") Long userId, @Param("taskId") Long taskId);
}
