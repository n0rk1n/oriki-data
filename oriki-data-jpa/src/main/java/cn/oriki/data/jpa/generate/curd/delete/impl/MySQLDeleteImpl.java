package cn.oriki.data.jpa.generate.curd.delete.impl;

import cn.oriki.data.jpa.generate.curd.delete.AbstractJpaDelete;
import cn.oriki.data.jpa.generate.base.from.impl.MySQLFromImpl;
import cn.oriki.data.jpa.generate.base.where.impl.MySQLWhereImpl;

public class MySQLDeleteImpl extends AbstractJpaDelete {

    public MySQLDeleteImpl(String tableName) {
        super(new MySQLWhereImpl(), new MySQLFromImpl(tableName));
    }
    
}
