package com.nolookcoding.orderservice.domain;

public enum OrderState {
    PAID,
    READY,
    DELIVERING,
    RECEIVED,
    CANCEL;

    public static OrderState findCategoryByString(String orderState) {
        for (OrderState o : OrderState.values()) {
            if (o.name().equalsIgnoreCase(orderState))
                return o;
        }
        return null;
    }
}
