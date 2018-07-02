package cn.oriki.data.jpa.generate.curd.predicate;

import cn.oriki.data.generate.curd.predicate.AbstractPredicate;
import cn.oriki.data.generate.curd.sort.Sort;
import cn.oriki.data.generate.curd.where.Where;

public class AbstractJpaPredict extends AbstractPredicate {

    public AbstractJpaPredict(Where where, Sort sort) {
        super(where, sort);
    }

}
