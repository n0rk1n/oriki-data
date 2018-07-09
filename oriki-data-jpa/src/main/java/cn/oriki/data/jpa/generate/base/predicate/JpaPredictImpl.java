package cn.oriki.data.jpa.generate.base.predicate;

import cn.oriki.data.generate.base.pageable.AbstractPageable;
import cn.oriki.data.generate.base.predicate.AbstractPredicate;
import cn.oriki.data.jpa.generate.base.sort.JpaSortImpl;
import cn.oriki.data.jpa.generate.base.where.JpaWhereImpl;

public class JpaPredictImpl extends AbstractPredicate {

    public JpaPredictImpl(AbstractPageable pageable) {
        super(new JpaWhereImpl(), new JpaSortImpl(), pageable);
    }

}
