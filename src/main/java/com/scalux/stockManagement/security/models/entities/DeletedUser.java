package com.scalux.stockManagement.security.models.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "deleted_user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DeletedUser {
    @Id
    private Long id;

    @NotBlank @Column(name = "username", unique = true)
    private String username;

    private LocalDateTime deletedAt;

}
