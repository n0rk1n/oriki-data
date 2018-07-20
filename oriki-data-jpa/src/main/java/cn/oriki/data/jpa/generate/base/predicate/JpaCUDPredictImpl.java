package cn.oriki.data.jpa.generate.base.predicate;

import cn.oriki.data.generate.base.predicate.crd.AbstractCRDPredicate;
import cn.oriki.data.jpa.generate.base.where.JpaWhereImpl;

public class JpaCUDPredictImpl extends AbstractCRDPredicate {

    public JpaCUDPredictImpl() {
        super(new JpaWhereImpl());
    }

}
