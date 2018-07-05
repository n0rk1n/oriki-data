package cn.oriki.data.jpa.generate.base.predicate;

import cn.oriki.data.generate.base.pageable.AbstractPageable;
import cn.oriki.data.generate.base.predicate.AbstractPredicate;
import cn.oriki.data.generate.base.sort.AbstractSort;
import cn.oriki.data.generate.base.where.AbstractWhere;

public abstract class AbstractJpaPredict extends AbstractPredicate {

    public AbstractJpaPredict(AbstractWhere where, AbstractSort sort, AbstractPageable pageable) {
        super(where, sort, pageable);
    }

}
