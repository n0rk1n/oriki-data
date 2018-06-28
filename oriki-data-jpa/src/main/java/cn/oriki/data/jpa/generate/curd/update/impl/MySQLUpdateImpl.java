package cn.oriki.data.jpa.generate.curd.update.impl;

import cn.oriki.data.jpa.generate.curd.from.impl.MySQLFromImpl;
import cn.oriki.data.jpa.generate.curd.update.AbstractJpaUpdate;
import cn.oriki.data.jpa.generate.curd.where.impl.MySQLWhereImpl;

public class MySQLUpdateImpl extends AbstractJpaUpdate {

    public MySQLUpdateImpl(String tableName) {
        super(new MySQLWhereImpl(), new MySQLFromImpl(tableName));
    }

}
