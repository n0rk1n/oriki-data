package cn.oriki.data.jpa.generate.curd.query.impl;

import cn.oriki.data.generate.base.predicate.AbstractPredicate;
import cn.oriki.data.jpa.generate.base.from.impl.MySQLFromImpl;
import cn.oriki.data.jpa.generate.base.pageable.impl.MySQLPageableImpl;
import cn.oriki.data.jpa.generate.base.predicate.impl.MySQLPredicateImpl;
import cn.oriki.data.jpa.generate.base.sort.impl.MySQLSortImpl;
import cn.oriki.data.jpa.generate.base.where.impl.MySQLWhereImpl;
import cn.oriki.data.jpa.generate.curd.query.AbstractJpaQuery;

public class MySQLQueryImpl extends AbstractJpaQuery {

    /*public MySQLQueryImpl(String tableName) {
        super(new MySQLWhereImpl(), new MySQLFromImpl(tableName));
    }*/

    public MySQLQueryImpl(String tableName) {
        super(new MySQLPredicateImpl(new MySQLWhereImpl(), new MySQLSortImpl(), new MySQLPageableImpl(null, null)), new MySQLFromImpl(tableName));
    }

    public MySQLQueryImpl(AbstractPredicate predicate, String tableName) {
        super(predicate, new MySQLFromImpl(tableName));
    }

}
