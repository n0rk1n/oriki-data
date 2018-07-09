package cn.oriki.data.jpa.repository.impl;

import cn.oriki.data.generate.base.predicate.AbstractPredicate;
import cn.oriki.data.generate.base.where.AbstractWhere;
import cn.oriki.data.generate.base.where.entity.Criteria;
import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.jpa.generate.base.from.JpaFromImpl;
import cn.oriki.data.jpa.generate.base.pageable.impl.OraclePageableImpl;
import cn.oriki.data.jpa.generate.base.predicate.JpaPredictImpl;
import cn.oriki.data.jpa.generate.curd.query.AbstractJpaQuery;
import cn.oriki.data.jpa.generate.curd.query.impl.OracleQueryImpl;
import cn.oriki.data.jpa.repository.AbstractJpaRepository;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class OracleRepositoryImpl<T, ID extends Serializable> extends AbstractJpaRepository<T, ID> {

    public OracleRepositoryImpl(Class<T> entityClass, Class<ID> idClass) {
        super(entityClass, idClass);
    }

    private AbstractJpaQuery getQueryImpl() {
        return new OracleQueryImpl(new JpaPredictImpl(new OraclePageableImpl(null, null)), new JpaFromImpl(getTableName()));
    }

    @Override
    public T queryById(ID id) throws GenerateException {
        AbstractJpaQuery query = getQueryImpl();

        String primaryKeyColumnName = super.getPrimaryKeyColumnName();
        query.getPredicate().getWhere().equals(primaryKeyColumnName, id); // 添加查询条件

        query.queryAll(entityClass);

        return queryOne(query);
    }

    @Override
    public Collection<T> queryByIds(Collection<ID> ids) throws GenerateException {
        AbstractJpaQuery query = getQueryImpl();

        String primaryKeyColumnName = super.getPrimaryKeyColumnName();
        query.getPredicate().getWhere().in(primaryKeyColumnName, ids);

        query.queryAll(entityClass);

        return queryList(query);
    }

    @Override
    public Collection<T> queryAll() throws GenerateException {
        AbstractJpaQuery query = getQueryImpl();

        query.queryAll(entityClass); // 查询所有字段

        return queryList(query);
    }

    @Override
    public Collection<T> queryAll(AbstractWhere where) throws GenerateException {
        AbstractJpaQuery query = getQueryImpl();

        query.getPredicate().setWhere(where);
        query.queryAll(entityClass);

        return queryList(query);
    }

    @Override
    public boolean exists(ID id) throws GenerateException {
        AbstractJpaQuery query = getQueryImpl();

        String primaryKeyColumnName = super.getPrimaryKeyColumnName();
        Field primaryKeyField = getPrimaryKeyField();
        String idFieldName = primaryKeyField.getName();

        query.query(primaryKeyColumnName, idFieldName); // select id
        query.getPredicate().getWhere().equals(primaryKeyColumnName, id); // where id = ?

        ID value = queryValue(query, idClass);

        return Objects.nonNull(value);
    }

    @Override
    public <S extends T> Long count(S entity) throws GenerateException {
        AbstractJpaQuery query = getQueryImpl();

        query.count();

        List<Criteria> criterias = createCriteriasByEntity(entity);

        query.getPredicate().getWhere().andCriteria(criterias.toArray(new Criteria[0]));

        return queryValue(query, Long.class);
    }

    @Override
    public Long count(AbstractWhere where) throws GenerateException {
        AbstractJpaQuery query = getQueryImpl();
        query.getPredicate().setWhere(where);
        query.count();
        return queryValue(query, Long.class);
    }

    @Override
    public Iterable<T> query(AbstractPredicate predicate) throws GenerateException {
        AbstractJpaQuery query = getQueryImpl();
        query.setPredicate(predicate);
        return queryList(query);
    }

    @Override
    public Long count(AbstractPredicate predicate) throws GenerateException {
        AbstractJpaQuery query = getQueryImpl();
        query.setPredicate(predicate);

        query.count();
        return queryValue(query, Long.class);
    }

}
