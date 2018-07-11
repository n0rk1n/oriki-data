package cn.oriki.data.generate.base.where;

import cn.oriki.data.generate.base.where.entity.Criteria;
import cn.oriki.data.generate.base.where.enumeration.ConditionalEnum;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public interface Where {

    void clear(); // 清空所有条件

    void andCriteria(Criteria... criterias); // 添加与关系条件

    void orCriteria(Criteria... criterias); // 添加或关系条件

    /**
     * in 方法，转换 in 为 or 参数
     * <p>
     * 效率考虑
     *
     * @param key    键名（可以是列名）
     * @param values 值的集合
     */
    default <E extends Serializable> void in(String key, Collection<E> values) {
        values = values.stream().filter(Objects::nonNull).collect(Collectors.toList());

        Criteria[] criterias = new Criteria[values.size()];
        int index = 0;

        for (Serializable value : values) {
            Criteria criteria = createCriteria(key, ConditionalEnum.EQUALS, value);
            criterias[index] = criteria;
            index++;
        }
        orCriteria(criterias);
    }

    default void equals(String key, Serializable value) {
        Criteria criteria = createCriteria(key, ConditionalEnum.EQUALS, value);
        andCriteria(criteria);
    }

    default void greaterThan(String key, Serializable value) {
        Criteria criteria = createCriteria(key, ConditionalEnum.GREATER_THAN, value);
        andCriteria(criteria);
    }

    default void greaterThanAndEquals(String key, Serializable value) {
        Criteria criteria = createCriteria(key, ConditionalEnum.GREATER_THAN_AND_EQUALS, value);
        andCriteria(criteria);
    }

    default void lessThan(String key, Serializable value) {
        Criteria criteria = createCriteria(key, ConditionalEnum.LESS_THAN, value);
        andCriteria(criteria);
    }

    default void lessThanAndEquals(String key, Serializable value) {
        Criteria criteria = createCriteria(key, ConditionalEnum.LESS_THAN_AND_EQUALS, value);
        andCriteria(criteria);
    }

    default void isNull(String key) {
        Criteria criteria = createCriteria(key, ConditionalEnum.IS, null); // 不设置 value 值
        andCriteria(criteria);
    }

    default void isNotNull(String key) {
        Criteria criteria = createCriteria(key, ConditionalEnum.IS_NOT, null); // 不设置 value 值
        andCriteria(criteria);
    }

    default Criteria createCriteria(String key, ConditionalEnum conditionalEnum, Serializable value) {
        Criteria criteria = new Criteria();
        criteria.setKey(key);
        criteria.setConditional(conditionalEnum);
        criteria.setValue(value);

        return criteria;
    }

}
