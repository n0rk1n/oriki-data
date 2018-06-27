package cn.oriki.data.repository;

import java.io.Serializable;

public abstract class AbstractRepository<T, ID extends Serializable> implements PagingAndSortRepository<T, ID> {

    protected Class<T> entityClass;
    protected Class<ID> idClass;

    public AbstractRepository(Class<T> T, Class<ID> ID) {
        this.entityClass = T;
        this.idClass = ID;
    }

}
