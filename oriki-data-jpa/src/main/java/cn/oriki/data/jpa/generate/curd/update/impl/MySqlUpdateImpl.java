package cn.oriki.data.jpa.generate.curd.update.impl;

import cn.oriki.data.jpa.generate.base.from.impl.MySqlFromImpl;
import cn.oriki.data.jpa.generate.base.where.impl.MySqlWhereImpl;
import cn.oriki.data.jpa.generate.curd.update.AbstractJpaUpdate;

public class MySqlUpdateImpl extends AbstractJpaUpdate {

    public MySqlUpdateImpl(String tableName) {
        super(new MySqlWhereImpl(), new MySqlFromImpl(tableName));
    }

}
