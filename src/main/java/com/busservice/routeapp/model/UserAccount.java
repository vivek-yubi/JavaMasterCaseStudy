package com.busservice.routeapp.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;


@Entity
@Getter
@Setter
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer userId;

    @Column(unique = true, name = "user_login_id")
    @NotBlank(message = "Login ID cannot be blank")
    private String loginId;

    @Column(name = "user_pass")
    @NotBlank(message = "Password cannot be blank")
    private String password;

    @Column(name = "token")
    private String token;

    @Column(columnDefinition = "BOOLEAN DEFAULT 'false'", name = "is_user_admin")
    private Boolean isAdmin = false;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    @JsonSerialize(using = DateSerializer.class)
    Date createdDate;

    @UpdateTimestamp
    @Column(name = "last_modified_date", nullable = false)
    @JsonSerialize(using = DateSerializer.class)
    Date lastModifiedDate;


}
