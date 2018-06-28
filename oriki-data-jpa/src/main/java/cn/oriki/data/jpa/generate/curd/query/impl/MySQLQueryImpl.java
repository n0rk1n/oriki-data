package cn.oriki.data.jpa.generate.curd.query.impl;

import cn.oriki.data.jpa.generate.curd.from.impl.MySQLFromImpl;
import cn.oriki.data.jpa.generate.curd.query.AbstractJpaQuery;
import cn.oriki.data.jpa.generate.curd.where.impl.MySQLWhereImpl;

public class MySQLQueryImpl extends AbstractJpaQuery {

    public MySQLQueryImpl(String tableName) {
        super(new MySQLWhereImpl(), new MySQLFromImpl(tableName));
    }

}
