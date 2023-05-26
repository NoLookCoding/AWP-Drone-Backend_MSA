package com.nolookcoding.orderservice.repository;

import com.nolookcoding.orderservice.domain.Order;
import com.nolookcoding.orderservice.domain.OrderState;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.nolookcoding.orderservice.domain.QDetailOrder.detailOrder;
import static com.nolookcoding.orderservice.domain.QOrder.order;

@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Order> searchAllOrdersOfUser(Boolean isReceivable, Pageable pageable, Long userId) {
        return queryFactory.select(order)
                .from(order)
                .join(order.detailOrders, detailOrder).fetchJoin() // order, detailOrder.order로 하는 것이 아니더군...
                .where(
                        searchUser(userId),
                        filterReceivedOption(isReceivable)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public Predicate searchUser(Long userId) {
        if (userId == null)
            return null;

        return order.userId.eq(userId);
    }

    public Predicate filterReceivedOption(Boolean isReceivable) {
        if (isReceivable == null)
            return null;

        return isReceivable ? ExpressionUtils.or(order.orderState.eq(OrderState.PAID),
                ExpressionUtils.or(order.orderState.eq(OrderState.DELIVERING), order.orderState.eq(OrderState.DELIVERING)))
                : ExpressionUtils.or(order.orderState.eq(OrderState.RECEIVED), order.orderState.eq(OrderState.CANCEL));
    }
}
