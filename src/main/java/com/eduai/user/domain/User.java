
package com.eduai.user.domain;

import com.eduai.common.domain.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String provider;

    private String refreshToken;

    private String job;

    private String ageGroup;

    private String purpose;

    public static User create(String email, String name, Role role, String provider, String refreshToken) {
        return User.builder()
                .email(email)
                .name(name)
                .role(role)
                .provider(provider)
                .refreshToken(refreshToken)
                .build();
    }

    public User update(String name) {
        this.name = name;
        return this;
    }

    public void updateUserInfo(String job, String ageGroup, String purpose) {
        this.job = job;
        this.ageGroup = ageGroup;
        this.purpose = purpose;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
