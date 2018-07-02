package cn.oriki.data.jpa.generate.curd.predicate.impl;

import cn.oriki.data.generate.curd.sort.Sort;
import cn.oriki.data.generate.curd.where.Where;
import cn.oriki.data.jpa.generate.curd.predicate.AbstractJpaPredict;

public class MySQLPredicateImpl extends AbstractJpaPredict {

    public MySQLPredicateImpl(Where where, Sort sort) {
        super(where, sort);
    }

}
