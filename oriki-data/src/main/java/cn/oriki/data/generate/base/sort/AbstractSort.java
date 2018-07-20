package cn.oriki.data.generate.base.sort;

import cn.oriki.data.generate.Generate;
import cn.oriki.data.generate.base.sort.entiy.OrderEntity;
import cn.oriki.data.generate.base.sort.enumeration.Direction;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 抽象的排序方法
 *
 * @author oriki.wang
 */
public abstract class AbstractSort implements Sort, Generate {

    /**
     * 考虑排序优先级， List
     */
    private List<OrderEntity> orders;

    public AbstractSort() {
        checkOrders();
    }

    /**
     * 根据 keys 进行默认升序的排序
     *
     * @param keys 排序字段
     */
    public AbstractSort(String... keys) { // keys 传值建议 > 2
        checkOrders();
        orderAsc(keys);
    }

    @Override
    public void sort(String key, Direction direction) {
        checkOrders();
        addOrder(key, direction);
    }

    @Override
    public Integer sortSize() {
        checkOrders();
        return orders.size();
    }

    /**
     * 检查 orders 是否存在
     */
    private void checkOrders() {
        if (Objects.isNull(orders)) {
            orders = Lists.newArrayList();
        }
    }

    private void addOrder(String key, Direction direction) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrder(key);
        orderEntity.setDirection(direction);
        orders.add(orderEntity);
    }

    public List<OrderEntity> getOrders() {
        return Collections.unmodifiableList(orders);
    }

}
