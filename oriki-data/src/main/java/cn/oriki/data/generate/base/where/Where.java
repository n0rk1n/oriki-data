package cn.oriki.data.generate.base.where;

import cn.oriki.data.generate.base.where.entity.Criteria;
import cn.oriki.data.generate.base.where.enumeration.ConditionalEnum;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public interface Where {

    void clear(); // 清空所有条件

    void andCriteria(Criteria... criterias); // 与关系添加条件

    void orCriteria(Criteria... criterias); // 或关系添加条件

    /**
     * in 方法，转换 in 为 or 参数
     * <p>
     * 效率考虑
     *
     * @param key    键名（可以是列名）
     * @param values 值的集合
     */
    default <E extends Serializable> void in(String key, Collection<E> values) {
        values = values.stream().filter((e) -> Objects.nonNull(e)).collect(Collectors.toList());

        Criteria[] criterias = new Criteria[values.size()];
        int index = 0;

        for (Serializable value : values) {
            Criteria criteria = new Criteria();
            {
                criteria.setKey(key);
                criteria.setConditional(ConditionalEnum.EQUALS);
                criteria.setValue(value);
            }
            criterias[index] = criteria;
            index++;
        }
        orCriteria(criterias);
    }

    default void equals(String key, Serializable value) {
        Criteria criteria = new Criteria();
        {
            criteria.setKey(key);
            criteria.setConditional(ConditionalEnum.EQUALS);
            criteria.setValue(value);
        }
        andCriteria(criteria);
    }

    default Criteria greaterThan(String key, Serializable value) {
        Criteria criteria = new Criteria();
        {
            criteria.setKey(key);
            criteria.setConditional(ConditionalEnum.GREATER_THAN);
            criteria.setValue(value);
        }
        return criteria;
    }

    default Criteria lessThan(String key, Serializable value) {
        Criteria criteria = new Criteria();
        {
            criteria.setKey(key);
            criteria.setConditional(ConditionalEnum.LESS_THAN);
            criteria.setValue(value);
        }
        return criteria;
    }

    default Criteria isNull(String key) {
        Criteria criteria = new Criteria();
        {
            criteria.setKey(key);
            criteria.setConditional(ConditionalEnum.IS);
        }
        return criteria;
    }

    default Criteria isNotNull(String key) {
        Criteria criteria = new Criteria();
        {
            criteria.setKey(key);
            criteria.setConditional(ConditionalEnum.IS_NOT);
        }
        return criteria;
    }

}
