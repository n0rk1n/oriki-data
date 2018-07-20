package cn.oriki.data.generate.base.predicate;

import cn.oriki.data.generate.base.pageable.Pageable;
import cn.oriki.data.generate.base.predicate.crd.CRDPredicate;
import cn.oriki.data.generate.base.sort.Sort;
import cn.oriki.data.generate.base.sort.enumeration.Direction;

/**
 * 预留分页，排序，和查询条件的接口
 *
 * @author oriki.wang
 */
public interface Predicate extends CRDPredicate, Sort, Pageable {

    Sort getSort();

    Pageable getPageable();

    @Override
    default void sort(String key, Direction direction) {
        getSort().sort(key, direction);
    }

    @Override
    default Integer sortSize() {
        return getSort().sortSize();
    }

    @Override
    default Integer getPageNumber() {
        return getPageable().getPageNumber();
    }

    @Override
    default Integer getPageSize() {
        return getPageable().getPageSize();
    }

    @Override
    default void set(Integer pageNumber, Integer pageSize) {
        getPageable().set(pageNumber, pageSize);
    }

}
