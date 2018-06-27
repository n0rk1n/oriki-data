package cn.oriki.data.jpa.repository.impl;

import cn.oriki.data.jpa.repository.AbstractJpaRepository;
import com.sun.xml.internal.bind.v2.model.core.ID;

import java.io.Serializable;

public class MySQLRepositoryImpl<T, Id extends Serializable> extends AbstractJpaRepository<T, ID> {

    public MySQLRepositoryImpl(Class T, Class ID) {
        super(T, ID);
    }
    
}
