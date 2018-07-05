package cn.oriki.data.jpa.generate.curd.save.impl;

import cn.oriki.data.jpa.generate.base.from.impl.MySQLFromImpl;
import cn.oriki.data.jpa.generate.curd.save.AbstractJpaSave;

public class MySQLSaveImpl extends AbstractJpaSave {

    public MySQLSaveImpl(String tableName) {
        super(new MySQLFromImpl(tableName));
    }

}
