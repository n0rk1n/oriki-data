package cn.oriki.data.jpa.generate.curd.query.impl;

import cn.oriki.data.generate.curd.predicate.Predicate;
import cn.oriki.data.jpa.generate.curd.from.impl.MySQLFromImpl;
import cn.oriki.data.jpa.generate.curd.pageable.impl.MySQLPageableImpl;
import cn.oriki.data.jpa.generate.curd.predicate.impl.MySQLPredicateImpl;
import cn.oriki.data.jpa.generate.curd.query.AbstractJpaQuery;
import cn.oriki.data.jpa.generate.curd.sort.impl.MySQLSortImpl;
import cn.oriki.data.jpa.generate.curd.where.impl.MySQLWhereImpl;

public class MySQLQueryImpl extends AbstractJpaQuery {

    /*public MySQLQueryImpl(String tableName) {
        super(new MySQLWhereImpl(), new MySQLFromImpl(tableName));
    }*/

    public MySQLQueryImpl(String tableName) {
        super(new MySQLPredicateImpl(new MySQLWhereImpl(), new MySQLSortImpl(), new MySQLPageableImpl(null, null)), new MySQLFromImpl(tableName));
    }

    public MySQLQueryImpl(Predicate predicate, String tableName) {
        super(predicate, new MySQLFromImpl(tableName));
    }

}
