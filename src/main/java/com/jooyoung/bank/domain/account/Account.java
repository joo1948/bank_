package com.jooyoung.bank.domain.account;

import com.jooyoung.bank.domain.user.User;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor //풀 생성자 만들때 꼭 필요함 > 스프링이 User 객체 생성할 때 빈 생성자로 New 를 하기 때문에 >> 빈 생성자 만들어주는 어노테이션
@EntityListeners(AuditingEntityListener.class)
@Table(name = "account_tb")
@Entity
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable = false, length = 20)
    private Long number; //계좌번호
    @Column(nullable = false, length = 4)
    private Long password; //계좌 비번
    @Column(nullable = false)
    private Long balance; // 잔액 ( 기본값 1000원 )

    //  항상 ORM 에서 fk 주인은 Main Entity 쪽
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; //

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime updateAt;

    @Builder
    public Account(Long id, Long number, Long password, Long balance, User user, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.id = id;
        this.number = number;
        this.password = password;
        this.balance = balance;
        this.user = user;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }
}
