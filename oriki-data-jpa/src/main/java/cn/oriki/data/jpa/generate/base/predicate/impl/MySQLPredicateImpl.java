package cn.oriki.data.jpa.generate.base.predicate.impl;

import cn.oriki.data.jpa.generate.base.pageable.impl.MySQLPageableImpl;
import cn.oriki.data.jpa.generate.base.predicate.AbstractJpaPredict;
import cn.oriki.data.jpa.generate.base.sort.impl.MySQLSortImpl;
import cn.oriki.data.jpa.generate.base.where.impl.MySQLWhereImpl;

public class MySQLPredicateImpl extends AbstractJpaPredict {

    public MySQLPredicateImpl() {
        super(new MySQLWhereImpl(), new MySQLSortImpl(), new MySQLPageableImpl(null, null)); // 默认不分页
    }

}
