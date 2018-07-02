package cn.oriki.data.generate.curd.sort;

import cn.oriki.data.generate.curd.sort.entiy.OrderEntity;
import cn.oriki.data.generate.curd.sort.enumeration.Direction;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;

public abstract class AbstractSort implements Sort {

    private static final String NORMAL_ORDER_KEY_WORD = Direction.ASC.getOrder();

    private List<OrderEntity> orders;

    public AbstractSort() {
        orders = Lists.newArrayList();
    }

    @Override
    public void addSort(String key, Direction direction) {
        if (Objects.isNull(orders)) {
            orders = Lists.newArrayList();
        }
        OrderEntity orderEntity = new OrderEntity();
        {
            orderEntity.setOrder(key);
            orderEntity.setDirection(direction);
        }
        orders.add(orderEntity);
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    @Override
    public Integer size() {
        if (Objects.nonNull(orders)) {
            return orders.size();
        }
        return 0;
    }

}
