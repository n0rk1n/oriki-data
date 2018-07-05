package cn.oriki.data.jpa.generate.curd.query.impl;

import cn.oriki.data.generate.base.predicate.AbstractPredicate;
import cn.oriki.data.jpa.generate.base.from.impl.MySQLFromImpl;
import cn.oriki.data.jpa.generate.base.predicate.impl.MySQLPredicateImpl;
import cn.oriki.data.jpa.generate.curd.query.AbstractJpaQuery;

public class MySQLQueryImpl extends AbstractJpaQuery {

    /*public MySQLQueryImpl(String tableName) {
        super(new MySQLWhereImpl(), new MySQLFromImpl(tableName));
    }*/

    public MySQLQueryImpl(String tableName) {
        super(new MySQLPredicateImpl(), new MySQLFromImpl(tableName));
    }

    public MySQLQueryImpl(AbstractPredicate predicate, String tableName) {
        super(predicate, new MySQLFromImpl(tableName));
    }

}
