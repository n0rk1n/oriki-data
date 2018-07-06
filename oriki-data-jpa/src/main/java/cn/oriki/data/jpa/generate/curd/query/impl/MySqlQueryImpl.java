package cn.oriki.data.jpa.generate.curd.query.impl;

import cn.oriki.data.generate.base.predicate.AbstractPredicate;
import cn.oriki.data.jpa.generate.base.from.impl.MySqlFromImpl;
import cn.oriki.data.jpa.generate.base.predicate.impl.MySqlPredicateImpl;
import cn.oriki.data.jpa.generate.curd.query.AbstractJpaQuery;

public class MySqlQueryImpl extends AbstractJpaQuery {

    /*public MySQLQueryImpl(String tableName) {
        super(new MySQLWhereImpl(), new MySQLFromImpl(tableName));
    }*/

    public MySqlQueryImpl(String tableName) {
        super(new MySqlPredicateImpl(), new MySqlFromImpl(tableName));
    }

    public MySqlQueryImpl(AbstractPredicate predicate, String tableName) {
        super(predicate, new MySqlFromImpl(tableName));
    }

}
