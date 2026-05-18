package com.SpringNotificationHub.NotificationServ.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SpringNotificationHub.NotificationServ.model.NotificationEntity;

public interface NotificationRepository extends JpaRepository<NotificationEntity, UUID> {

@Query("SELECT n FROM NotificationEntity n WHERE n.userIp = :ip " + "AND n.date >= :startDay AND n.date <= :endDay")
    List<NotificationEntity> findTodayNotificationsByIp(
        @Param("ip") String ip, 
        @Param("startDay") LocalDateTime start,
        @Param("endDay") LocalDateTime end
    );
}
