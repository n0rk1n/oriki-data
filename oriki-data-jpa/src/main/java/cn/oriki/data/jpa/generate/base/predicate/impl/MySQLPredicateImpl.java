package cn.oriki.data.jpa.generate.base.predicate.impl;

import cn.oriki.data.generate.base.pageable.AbstractPageable;
import cn.oriki.data.generate.base.sort.AbstractSort;
import cn.oriki.data.generate.base.where.AbstractWhere;
import cn.oriki.data.jpa.generate.base.predicate.AbstractJpaPredict;

public class MySQLPredicateImpl extends AbstractJpaPredict {

    public MySQLPredicateImpl(AbstractWhere where, AbstractSort sort, AbstractPageable pageable) {
        super(where, sort, pageable);
    }

}
