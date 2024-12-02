package com.study.bot.entity;

import com.study.bot.entity.type.UserRegistrationStatus;
import com.study.bot.entity.type.UserRoles;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "chat_id")
    private Long chatId;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private UserRoles userRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_reg_status")
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private UserRegistrationStatus userRegistrationStatus;

    @Column(name = "user_first_name")
    private String userFirstName;

    @Column(name = "user_last_name")
    private String userLastName;

    @ManyToOne
    @JoinColumn(name = "journal_id")
    private Journal journal;

}
