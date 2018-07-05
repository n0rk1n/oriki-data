package cn.oriki.data.generate.base.predicate;

import cn.oriki.data.generate.base.pageable.Pageable;
import cn.oriki.data.generate.base.sort.Sort;
import cn.oriki.data.generate.base.where.Where;

/**
 * 预留分页，排序，和查询条件的接口
 *
 * @author oriki.wang
 */
public interface Predicate {

    Where getWhere();

    Sort getSort();

    Pageable getPageable();

}
