package cn.oriki.data.generate.base.predicate;

import cn.oriki.data.generate.base.pageable.Pageable;
import cn.oriki.data.generate.base.sort.Sort;
import cn.oriki.data.generate.base.sort.enumeration.Direction;
import cn.oriki.data.generate.base.where.Where;
import cn.oriki.data.generate.base.where.entity.Criteria;

/**
 * 预留分页，排序，和查询条件的接口
 *
 * @author oriki.wang
 */
public interface Predicate extends Where, Sort, Pageable {

    /**
     * 获取 条件
     *
     * @return Where
     */
    Where getWhere();

    /**
     * 获取排序列表
     *
     * @return Sort
     */
    Sort getSort();

    /**
     * 获取分页数据
     *
     * @return Pageable
     */
    Pageable getPageable();

    /**
     * 清空所有条件
     */
    @Override
    default void clear() {
        getWhere().clear();
    }

    /**
     * 添加与关系条件
     *
     * @param criterias 条件
     */
    @Override
    default void andCriteria(Criteria... criterias) {
        getWhere().andCriteria(criterias);
    }

    /**
     * 添加或关系条件
     *
     * @param criterias 条件
     */
    @Override
    default void orCriteria(Criteria... criterias) {
        getWhere().orCriteria(criterias);
    }

    /**
     * 获取分页页码（当前第几页）
     *
     * @return 当前第几页
     */
    @Override
    default Integer getPageNumber() {
        return getPageNumber();
    }

    /**
     * 获取每页条数
     *
     * @return 每页条数
     */
    @Override
    default Integer getPageSize() {
        return getPageSize();
    }

    /**
     * 设置当前页码和每页条数
     *
     * @param pageNumber 当前页码
     * @param pageSize   每页条数
     */
    @Override
    default void set(Integer pageNumber, Integer pageSize) {
        set(pageNumber, pageSize);
    }

    /**
     * 排序列和排序方向
     *
     * @param key       键
     * @param direction 排序方向
     */
    @Override
    default void sort(String key, Direction direction) {
        sort(key, direction);
    }

    /**
     * 排序的数量
     * <p>
     * 用于判断是否有排序条件，传 size （比起 boolean ） 更通用一些
     *
     * @return 参与排序列的数量
     */
    @Override
    default Integer sortSize() {
        return sortSize();
    }
    
}
