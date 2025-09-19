
package com.eduai.auth.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String provider;

    @Builder
    public User(String email, String name, Role role, String provider) {
        this.email = email;
        this.name = name;
        this.role = role;
        this.provider = provider;
    }

    public User update(String name) {
        this.name = name;
        return this;
    }
}
