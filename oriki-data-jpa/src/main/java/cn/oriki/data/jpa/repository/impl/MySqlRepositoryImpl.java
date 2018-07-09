package cn.oriki.data.jpa.repository.impl;

import cn.oriki.data.generate.base.predicate.AbstractPredicate;
import cn.oriki.data.generate.base.where.AbstractWhere;
import cn.oriki.data.generate.base.where.entity.Criteria;
import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.jpa.generate.curd.query.AbstractJpaQuery;
import cn.oriki.data.jpa.generate.curd.query.impl.MySqlQueryImpl;
import cn.oriki.data.jpa.repository.AbstractJpaRepository;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class MySqlRepositoryImpl<T, ID extends Serializable> extends AbstractJpaRepository<T, ID> {

    public MySqlRepositoryImpl(Class<T> entityClass, Class<ID> idClass) {
        super(entityClass, idClass);
    }

    @Override
    public T queryById(ID id) throws GenerateException {
        AbstractJpaQuery query = new MySqlQueryImpl(getTableName());

        String primaryKeyColumnName = super.getPrimaryKeyColumnName();
        query.equals(primaryKeyColumnName, id); // 添加查询条件

        query.queryAll(entityClass);

        return queryOne(query);
    }

    @Override
    public Collection<T> queryByIds(Collection<ID> ids) throws GenerateException {
        AbstractJpaQuery query = new MySqlQueryImpl(getTableName());

        String primaryKeyColumnName = super.getPrimaryKeyColumnName();
        query.in(primaryKeyColumnName, ids);

        query.queryAll(entityClass);

        return queryList(query);
    }

    @Override
    public Collection<T> queryAll() throws GenerateException {
        AbstractJpaQuery query = new MySqlQueryImpl(getTableName());

        query.queryAll(entityClass); // 查询所有字段

        return queryList(query);
    }

    @Override
    public Collection<T> queryAll(AbstractWhere where) throws GenerateException {
        MySqlQueryImpl query = new MySqlQueryImpl(getTableName());

        query.getPredicate().setWhere(where);
        query.queryAll(entityClass);

        return queryList(query);
    }

    @Override
    public boolean exists(ID id) throws GenerateException {
        AbstractJpaQuery query = new MySqlQueryImpl(getTableName());

        String primaryKeyColumnName = super.getPrimaryKeyColumnName();
        Field primaryKeyField = getPrimaryKeyField();
        String idFieldName = primaryKeyField.getName();

        query.query(primaryKeyColumnName, idFieldName); // select id
        query.equals(primaryKeyColumnName, id); // where id = ?

        ID value = queryValue(query, idClass);

        return Objects.nonNull(value);
    }

    @Override
    public <S extends T> Long count(S entity) throws GenerateException {
        MySqlQueryImpl query = new MySqlQueryImpl(getTableName());

        query.count();

        List<Criteria> criterias = createCriteriasByEntity(entity);
        query.andCriteria(criterias.toArray(new Criteria[0]));

        return queryValue(query, Long.class);
    }

    @Override
    public Long count(AbstractWhere where) throws GenerateException {
        MySqlQueryImpl query = new MySqlQueryImpl(getTableName());
        query.count();
        query.getPredicate().setWhere(where);
        return queryValue(query, Long.class);
    }

    @Override
    public Iterable<T> query(AbstractPredicate predicate) throws GenerateException {
        AbstractJpaQuery query = new MySqlQueryImpl(predicate, getTableName());
        return queryList(query);
    }

    @Override
    public Long count(AbstractPredicate predicate) throws GenerateException {
        AbstractJpaQuery query = new MySqlQueryImpl(predicate, getTableName());
        query.count();
        return queryValue(query, Long.class);
    }

}
