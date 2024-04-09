package ru.tusur.edu.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "task")
@Getter
@Setter
@RequiredArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
@ToString(exclude = {"taskTheme", "taskDifficulty", "userTasks", "taskSolutions"})
@EntityListeners(AuditingEntityListener.class)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "add_date", nullable = false)
    @CreatedDate
    private Timestamp addDate;

    @ManyToOne
    @JoinColumn(name = "task_theme_id")
    private TaskTheme taskTheme;

    @ManyToOne
    @JoinColumn(name = "task_difficulty_id")
    private TaskDifficulty taskDifficulty;

    @ManyToOne
    @JoinColumn(name = "task_level_id")
    private TaskLevel taskLevel;

    @OneToMany(mappedBy = "task", orphanRemoval = true)
    private List<TaskSolution> taskSolutions;

    @OneToMany(mappedBy = "task")
    private List<UserTask> userTasks;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Task task = (Task) o;
        return getId() != null && Objects.equals(getId(), task.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}


