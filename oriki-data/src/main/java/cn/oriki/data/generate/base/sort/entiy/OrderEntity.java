package cn.oriki.data.generate.base.sort.entiy;

import cn.oriki.data.generate.base.sort.enumeration.Direction;

public class OrderEntity {

    private String order;
    private Direction direction;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

}
