package cn.oriki.data.generate.base.where;

import cn.oriki.data.generate.base.where.entity.Criteria;
import cn.oriki.data.generate.base.where.enumeration.ConditionalEnum;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 定义条件相关
 *
 * @author oriki.wang
 */
public interface Where {

    /**
     * 清空所有条件
     */
    void clear();

    /**
     * 添加与关系条件
     *
     * @param criterias 条件
     */
    void andCriteria(Criteria... criterias);

    /**
     * 添加或关系条件
     *
     * @param criterias 条件
     */
    void orCriteria(Criteria... criterias);

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

    /**
     * 添加相等条件
     *
     * @param key   键
     * @param value 值
     */
    default void equals(String key, Serializable value) {
        Criteria criteria = createCriteria(key, ConditionalEnum.EQUALS, value);
        andCriteria(criteria);
    }

    /**
     * 添加大于条件
     *
     * @param key   键
     * @param value 值
     */
    default void greaterThan(String key, Serializable value) {
        Criteria criteria = createCriteria(key, ConditionalEnum.GREATER_THAN, value);
        andCriteria(criteria);
    }

    /**
     * 添加大于等于条件
     *
     * @param key   键
     * @param value 值
     */
    default void greaterThanAndEquals(String key, Serializable value) {
        Criteria criteria = createCriteria(key, ConditionalEnum.GREATER_THAN_AND_EQUALS, value);
        andCriteria(criteria);
    }

    /**
     * 添加小于条件
     *
     * @param key   键
     * @param value 值
     */
    default void lessThan(String key, Serializable value) {
        Criteria criteria = createCriteria(key, ConditionalEnum.LESS_THAN, value);
        andCriteria(criteria);
    }

    /**
     * 添加小于等于条件
     *
     * @param key   键
     * @param value 值
     */
    default void lessThanAndEquals(String key, Serializable value) {
        Criteria criteria = createCriteria(key, ConditionalEnum.LESS_THAN_AND_EQUALS, value);
        andCriteria(criteria);
    }

    /**
     * 添加为空条件
     *
     * @param key 键
     */
    default void isNull(String key) {
        // 不设置 value 值
        Criteria criteria = createCriteria(key, ConditionalEnum.IS, null);
        andCriteria(criteria);
    }

    /**
     * 添加不为空条件
     *
     * @param key 键
     */
    default void isNotNull(String key) {
        // 不设置 value 值
        Criteria criteria = createCriteria(key, ConditionalEnum.IS_NOT, null);
        andCriteria(criteria);
    }

    /**
     * 创建条件对象
     *
     * @param key             键
     * @param conditionalEnum 关系运算符
     * @param value           值
     * @return 条件对象
     */
    default Criteria createCriteria(String key, ConditionalEnum conditionalEnum, Serializable value) {
        Criteria criteria = new Criteria();
        criteria.setKey(key);
        criteria.setConditional(conditionalEnum);
        criteria.setValue(value);

        return criteria;
    }

}
