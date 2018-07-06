package cn.oriki.data.jpa.generate.curd.save.impl;

import cn.oriki.data.jpa.generate.base.from.impl.MySqlFromImpl;
import cn.oriki.data.jpa.generate.curd.save.AbstractJpaSave;

public class MySqlSaveImpl extends AbstractJpaSave {

    public MySqlSaveImpl(String tableName) {
        super(new MySqlFromImpl(tableName));
    }

}
