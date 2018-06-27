package cn.oriki.data.jpa.generate.curd.delete.impl;

import cn.oriki.data.generate.curd.from.From;
import cn.oriki.data.generate.curd.where.Where;
import cn.oriki.data.jpa.generate.curd.delete.AbstractJpaDelete;

public class MySQLDeleteImpl extends AbstractJpaDelete {

    public MySQLDeleteImpl(Where where, From from) {
        super(where, from);
    }
    
}
