package com.nolookcoding.productservice.domain;

public enum SortStrategy {
    PRICE,
    NAME,
    CHRONOLOGICAL;

    public static SortStrategy findStrategyByString(String strategy) {
        for (SortStrategy ss : SortStrategy.values()) {
            if (ss.name().equalsIgnoreCase(strategy))
                return ss;
        }
        return null;
    }
}
