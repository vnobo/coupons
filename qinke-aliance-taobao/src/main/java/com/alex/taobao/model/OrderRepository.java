package com.alex.taobao.model;

import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.Optional;

/**
 * rebate-alliance OrderRepository
 * Created by 2019-02-25
 *
 * @author Alex bob(https://github.com/vnobo)
 */
public interface OrderRepository extends JpaRepository<Order, Long>,
        QuerydslPredicateExecutor<Order>, QuerydslBinderCustomizer<QOrder> {

    Optional<Order> findByTradeIdAndType(String tid, int type);

    @Override
    @SuppressWarnings("NullableProblems")
    default void customize(QuerydslBindings bindings, QOrder root) {
        bindings.bind(root.itemTitle).first(StringExpression::containsIgnoreCase);
    }
}
