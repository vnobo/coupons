package com.alex.core.wallet.beans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface WalletRepository extends JpaRepository<Wallet, Integer>, QuerydslPredicateExecutor<Wallet> {

    Optional<Wallet> findByOpenid(String openId);
}
