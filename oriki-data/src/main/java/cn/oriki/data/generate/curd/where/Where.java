package cn.oriki.data.generate.curd.where;

import cn.oriki.data.generate.Generate;
import cn.oriki.data.generate.curd.where.entity.Criteria;
import cn.oriki.data.generate.curd.where.enumeration.ConditionalEnum;

import java.io.Serializable;
import java.util.Collection;

public interface Where extends Generate {

    void clear(); // 清空所有条件

    void andCriteria(Criteria... criterias); // 与关系添加条件

    void orCriteria(Criteria... criterias); // 或关系添加条件

    /**
     * in 方法，转换 in 为 or 参数
     *
     * @param key    键名（可以是列名）
     * @param values 值的集合
     */
    default <E extends Serializable> void in(String key, Collection<E> values) {
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

}
