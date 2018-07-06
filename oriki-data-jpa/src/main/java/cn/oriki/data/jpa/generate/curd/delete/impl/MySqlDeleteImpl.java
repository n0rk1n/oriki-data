package cn.oriki.data.jpa.generate.curd.delete.impl;

import cn.oriki.data.jpa.generate.base.from.impl.MySqlFromImpl;
import cn.oriki.data.jpa.generate.base.where.impl.MySqlWhereImpl;
import cn.oriki.data.jpa.generate.curd.delete.AbstractJpaDelete;

public class MySqlDeleteImpl extends AbstractJpaDelete {

    public MySqlDeleteImpl(String tableName) {
        super(new MySqlWhereImpl(), new MySqlFromImpl(tableName));
    }

}
