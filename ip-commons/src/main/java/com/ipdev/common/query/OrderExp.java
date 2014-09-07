package com.ipdev.common.query;

public class OrderExp {
    public static enum Direction {
        ASC,
        DSC;
    }

    private String orderBy;
    private Direction direction;

    public String getOrderBy() {
        return orderBy;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

}
