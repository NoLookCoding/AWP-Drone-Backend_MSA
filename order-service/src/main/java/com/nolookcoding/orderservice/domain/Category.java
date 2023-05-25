package com.nolookcoding.orderservice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    PERFORMANCE("PERFORMANCE"), // 공연용
    FILM("FILM"), // 촬영용
    DISTRIBUTION("DISTRIBUTION"), // 물류용
    RECONNAISSANCE("RECONNAISSANCE"), // 정찰용
    ATTACK("ATTACK"), // 공격용
    MANAGE("MANAGE"); // (시설)관리용

    private final String name;

    public static Category findCategoryByString(String category) {
        for (Category c : Category.values()) {
            if (c.name.equalsIgnoreCase(category))
                return c;
        }
        return null;
    }

}
