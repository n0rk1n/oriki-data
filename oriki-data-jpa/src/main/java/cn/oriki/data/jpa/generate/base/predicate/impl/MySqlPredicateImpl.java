package cn.oriki.data.jpa.generate.base.predicate.impl;

import cn.oriki.data.jpa.generate.base.pageable.impl.MySqlPageableImpl;
import cn.oriki.data.jpa.generate.base.predicate.AbstractJpaPredict;
import cn.oriki.data.jpa.generate.base.sort.impl.MySqlSortImpl;
import cn.oriki.data.jpa.generate.base.where.impl.MySqlWhereImpl;

public class MySqlPredicateImpl extends AbstractJpaPredict {

    public MySqlPredicateImpl() {
        super(new MySqlWhereImpl(), new MySqlSortImpl(), new MySqlPageableImpl(null, null)); // 默认不分页
    }

}
