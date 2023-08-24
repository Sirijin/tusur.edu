package ru.tusur.edu.entity.task;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import ru.tusur.edu.security.entity.User;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "user_task")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(name = "complete_date")
    private Instant completeDate;

    @Column(name = "is_started", nullable = false)
    private Boolean isStarted;

    @Column(name = "is_finished", nullable = false)
    private Boolean isFinished;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        UserTask userTask = (UserTask) o;
        return getId() != null && Objects.equals(getId(), userTask.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
