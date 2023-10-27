package com.jooyoung.bank.domain.user;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor //풀 생성자 만들때 꼭 필요함 > 스프링이 User 객체 생성할 때 빈 생성자로 New 를 하기 때문에 >> 빈 생성자 만들어주는 어노테이션
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_tb")
@Entity
public class User {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String username;

    @Column(unique = false, length = 60)//패스워드 인코딩(BCrypt)
    private String password;

    @Column(nullable = false, length = 20)
    private String email;

    @Column(nullable = false, length = 20)
    private String fullname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserEnum role;//ADMIN, CUSTOMER

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime updateAt;

    @Builder
    public User(Long id, String username, String password, String email, String fullname, UserEnum role, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.role = role;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }

}
