package cn.oriki.data.generate.curd.predicate;

import cn.oriki.data.generate.curd.pageable.Pageable;
import cn.oriki.data.generate.curd.sort.Sort;
import cn.oriki.data.generate.curd.where.Where;

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
