package cn.oriki.data.generate.base.predicate;

import cn.oriki.data.generate.base.pageable.AbstractPageable;
import cn.oriki.data.generate.base.sort.AbstractSort;
import cn.oriki.data.generate.base.where.AbstractWhere;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Predicate 抽象类
 * <p>
 * 只做查询条件预存，不需进行 generate
 *
 * @author oriki.wang
 */
@Getter
@Setter
@AllArgsConstructor
public abstract class AbstractPredicate implements Predicate {

    private AbstractWhere where;
    private AbstractSort sort;
    private AbstractPageable pageable;

}
