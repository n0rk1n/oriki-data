package cn.oriki.data.jpa.generate.base.predicate.impl;

import cn.oriki.data.jpa.generate.base.pageable.impl.MySqlPageableImpl;
import cn.oriki.data.jpa.generate.base.predicate.AbstractJpaPredict;
import cn.oriki.data.jpa.generate.base.sort.JpaSortImpl;
import cn.oriki.data.jpa.generate.base.where.JpaWhereImpl;

public class MySqlPredicateImpl extends AbstractJpaPredict {

    public MySqlPredicateImpl() {
        super(new JpaWhereImpl(), new JpaSortImpl(), new MySqlPageableImpl(null, null)); // 默认不分页
    }

}
