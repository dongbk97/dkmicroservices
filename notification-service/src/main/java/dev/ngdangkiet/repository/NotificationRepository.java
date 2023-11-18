package dev.ngdangkiet.repository;

import dev.ngdangkiet.domain.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ngdangkiet
 * @since 11/14/2023
 */

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    @Query("select n from NotificationEntity n " +
            "where n.receiverId = :receiverId " +
            "and ((:all = true) or (n.seen = :seen))")
    List<NotificationEntity> getNotifications(@Param("receiverId") Long receiverId,
                                              @Param("all") boolean all,
                                              @Param("seen") boolean seen);
}
