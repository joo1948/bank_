package com.jooyoung.bank.domain.transaction;


import com.jooyoung.bank.domain.account.Account;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor //풀 생성자 만들때 꼭 필요함 > 스프링이 User 객체 생성할 때 빈 생성자로 New 를 하기 때문에 >> 빈 생성자 만들어주는 어노테이션
@EntityListeners(AuditingEntityListener.class)
@Table(name = "transaction_tb")
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Account withdrawAccount; // 출금
    private Account depositAccount; //입금

    private Long amount; //얼마나 입금이 됐는지 양

    //실시간 계좌의 history > 이 필드가 없으면 최종 금액만 남기에 꼭 있어야함.
    private Long withdrawAccountBalance; // 1111 계좌 -> 1000원 있음. > 2222 계좌로 500원 이체 >> 500 원 되겠지
    private Long depositAccountBalance;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionEnum gubun; // WITHDRAW, DEPOSIT, ALL, TRANSFER

    //계좌가 사라져도 로그는 남아야한다. >
    private String sender;
    private String receiver;
    private String tel;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime updateAt;

    @Builder
    public Transaction(Long id, Account withdrawAccount, Account depositAccount, Long amount, Long withdrawAccountBalance, Long depositAccountBalance, TransactionEnum gubun, String sender, String receiver, String tel, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.id = id;
        this.withdrawAccount = withdrawAccount;
        this.depositAccount = depositAccount;
        this.amount = amount;
        this.withdrawAccountBalance = withdrawAccountBalance;
        this.depositAccountBalance = depositAccountBalance;
        this.gubun = gubun;
        this.sender = sender;
        this.receiver = receiver;
        this.tel = tel;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }
}
