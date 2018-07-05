package cn.oriki.data.generate.base.sort;

import cn.oriki.data.generate.Generate;
import cn.oriki.data.generate.base.sort.entiy.OrderEntity;
import cn.oriki.data.generate.base.sort.enumeration.Direction;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class AbstractSort implements Sort, Generate {

    private static final Direction NORMAL_ORDER = Direction.ASC;

    private List<OrderEntity> orders;

    public AbstractSort() {
        orders = Lists.newArrayList();
    }

    /**
     * 根据 keys 进行默认升序的排序
     *
     * @param keys 排序字段
     */
    public AbstractSort(String... keys) {
        orders = Lists.newArrayList();
        for (String key : keys) {
            addSort(key, NORMAL_ORDER);
        }
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
        return Collections.unmodifiableList(orders);
    }

    @Override
    public Integer size() {
        if (Objects.nonNull(orders)) {
            return orders.size();
        }
        return 0;
    }

}
