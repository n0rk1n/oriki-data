package cn.oriki.data.jpa.repository.impl;

import cn.oriki.commons.utils.reflect.entity.FieldTypeNameValue;
import cn.oriki.data.generate.base.predicate.AbstractPredicate;
import cn.oriki.data.generate.base.where.AbstractWhere;
import cn.oriki.data.generate.base.where.entity.Criteria;
import cn.oriki.data.generate.base.where.enumeration.ConditionalEnum;
import cn.oriki.data.generate.curd.delete.result.DeleteResult;
import cn.oriki.data.generate.curd.save.result.SaveResult;
import cn.oriki.data.generate.curd.update.result.UpdateResult;
import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.jpa.generate.curd.delete.AbstractJpaDelete;
import cn.oriki.data.jpa.generate.curd.delete.impl.MySqlDeleteImpl;
import cn.oriki.data.jpa.generate.curd.query.AbstractJpaQuery;
import cn.oriki.data.jpa.generate.curd.query.impl.MySqlQueryImpl;
import cn.oriki.data.jpa.generate.curd.save.AbstractJpaSave;
import cn.oriki.data.jpa.generate.curd.save.impl.MySqlSaveImpl;
import cn.oriki.data.jpa.generate.curd.update.AbstractJpaUpdate;
import cn.oriki.data.jpa.generate.curd.update.impl.MySqlUpdateImpl;
import cn.oriki.data.jpa.repository.AbstractJpaRepository;
import cn.oriki.data.utils.reflect.ReflectDatas;
import com.google.common.collect.Lists;

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
    public <S extends T> SaveResult<S, ID> save(S entity) throws GenerateException, IllegalAccessException {
        AbstractJpaSave save = new MySqlSaveImpl(getTableName());

        List<FieldTypeNameValue> fieldTypeNameValues = ReflectDatas.getFieldTypeNameValues(entity);// 获取包含null值的属性 TODO 后续使用

        fieldTypeNameValues.forEach((fieldTypeNameValue -> {
            String fieldName = fieldTypeNameValue.getName();// 获取属性名称
            Field field = ReflectDatas.getField(entityClass, fieldName);
            String columnName = getColumnName(field); // 转换为标准列名称

            Serializable value = (Serializable) fieldTypeNameValue.getValue(); // 该值一定为 Serializable 的子类（上述获取已做过滤（ fieldValue insteadof Serializable ））

            save.save(columnName, value);
        }));

        return super.executeSave(save, entity);
    }

    @Override
    public DeleteResult deleteById(ID id) throws GenerateException {
        AbstractJpaDelete delete = new MySqlDeleteImpl(getTableName());

        String primaryKeyColumnName = getPrimaryKeyColumnName(); // 获取 主键列名

        delete.getWhere().equals(primaryKeyColumnName, id);

        return executeDelete(delete);
    }

    @Override
    public DeleteResult delete(T entity) throws GenerateException {
        AbstractJpaDelete delete = new MySqlDeleteImpl(getTableName());

        // 获取存在值的 FieldTypeNameValue , 添加作为存入列
        List<FieldTypeNameValue> fieldTypeNameValues = ReflectDatas.getFieldTypeNameValuesWithValuesIsNotNull(entity);

        fieldTypeNameValues.forEach((fieldTypeNameValue -> {
            String columnName;
            {
                String fieldName = fieldTypeNameValue.getName();// 获取属性名称
                Field field = ReflectDatas.getField(entityClass, fieldName);
                columnName = getColumnName(field); // 转换为标准列名称
            }
            Serializable value = (Serializable) fieldTypeNameValue.getValue(); // 该值一定为 Serializable 的子类（上述获取已做过滤（ fieldValue insteadof Serializable ））

            delete.getWhere().equals(columnName, value);
        }));

        return executeDelete(delete);
    }

    @Override
    public DeleteResult deleteAll() throws GenerateException {
        AbstractJpaDelete delete = new MySqlDeleteImpl(getTableName());
        return super.executeDelete(delete);
    }

    @Override
    public <S extends T> UpdateResult update(S entity) throws GenerateException, IllegalAccessException {
        // 根据 id 查询后，不存在执行 save 操作
        Serializable id = getIdValue(entity);

        UpdateResult updateResult = new UpdateResult();

        if (Objects.isNull(id)) {
            SaveResult<S, ID> saveResult = save(entity);
            updateResult.isUpdate(false); // 为 save 操作
            updateResult.setNumber(saveResult.getNumber());
        } else {
            // 更新操作
            AbstractJpaUpdate update = new MySqlUpdateImpl(getTableName());

            List<FieldTypeNameValue> fieldTypeNameValues = ReflectDatas.getFieldTypeNameValues(entity);// 获取包含null值的属性
            fieldTypeNameValues.forEach((fieldTypeNameValue -> {
                String fieldName = fieldTypeNameValue.getName();// 获取属性名称
                Field field = ReflectDatas.getField(entityClass, fieldName);
                String columnName = getColumnName(field); // 转换为标准列名称
                Serializable value = (Serializable) fieldTypeNameValue.getValue(); // 该值一定为 Serializable 的子类（上述获取已做过滤（ fieldValue insteadof Serializable ））

                update.update(columnName, value);
            }));

            update.getWhere().equals(getPrimaryKeyColumnName(), id); // 拼接 where

            int i = super.executeUpdate(update);
            updateResult.setNumber(i);
        }

        return updateResult;
    }

    @Override
    public T queryById(ID id) throws GenerateException {
        AbstractJpaQuery query = new MySqlQueryImpl(getTableName());

        String primaryKeyColumnName = super.getPrimaryKeyColumnName();
        query.getPredicate().getWhere().equals(primaryKeyColumnName, id); // 添加查询条件

        query.queryAll(entityClass);

        return queryOne(query);
    }

    @Override
    public Collection<T> queryByIds(Collection<ID> ids) throws GenerateException {
        AbstractJpaQuery query = new MySqlQueryImpl(getTableName());

        String primaryKeyColumnName = super.getPrimaryKeyColumnName();
        query.getPredicate().getWhere().in(primaryKeyColumnName, ids);

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
        query.getPredicate().getWhere().equals(primaryKeyColumnName, id); // where id = ?

        ID value = queryValue(query, idClass);

        return Objects.nonNull(value);
    }

    @Override
    public <S extends T> Long count(S entity) throws GenerateException {
        MySqlQueryImpl query = new MySqlQueryImpl(getTableName());

        query.count();

        List<FieldTypeNameValue> fieldTypeNameValues = ReflectDatas.getFieldTypeNameValuesWithValuesIsNotNull(entity);

        List<Criteria> criterias = Lists.newArrayList();

        for (FieldTypeNameValue fieldTypeNameValue : fieldTypeNameValues) {
            Criteria criteria = new Criteria();
            criteria.setKey(fieldTypeNameValue.getName());
            criteria.setConditional(ConditionalEnum.EQUALS);
            criteria.setValue((Serializable) fieldTypeNameValue.getValue());
            criterias.add(criteria);
        }

        query.getPredicate().getWhere().andCriteria(criterias.toArray(new Criteria[0]));

        return queryValue(query, Long.class);
    }

    @Override
    public <S extends T> Long count(AbstractWhere where) throws GenerateException {
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
    public <S extends T> Long count(AbstractPredicate predicate) throws GenerateException {
        AbstractJpaQuery query = new MySqlQueryImpl(predicate, getTableName());
        query.setPredicate(predicate);
        query.count();
        return queryValue(query, Long.class);
    }

}