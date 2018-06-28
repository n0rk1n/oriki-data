package cn.oriki.data.jpa.repository.impl;

import cn.oriki.commons.utils.reflect.entity.FieldTypeNameValue;
import cn.oriki.data.generate.curd.delete.result.DeleteResult;
import cn.oriki.data.generate.curd.save.result.SaveResult;
import cn.oriki.data.generate.curd.where.enumeration.ConditionalEnum;
import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.jpa.generate.curd.delete.AbstractJpaDelete;
import cn.oriki.data.jpa.generate.curd.delete.impl.MySQLDeleteImpl;
import cn.oriki.data.jpa.generate.curd.query.AbstractJpaQuery;
import cn.oriki.data.jpa.generate.curd.query.impl.MySQLQueryImpl;
import cn.oriki.data.jpa.generate.curd.save.AbstractJpaSave;
import cn.oriki.data.jpa.generate.curd.save.impl.MySQLSaveImpl;
import cn.oriki.data.jpa.repository.AbstractJpaRepository;
import cn.oriki.data.utils.reflect.ReflectDatas;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

public class MySQLRepositoryImpl<T, ID extends Serializable> extends AbstractJpaRepository<T, ID> {

    public MySQLRepositoryImpl(Class T, Class ID) {
        super(T, ID);
    }

    @Override
    public <S extends T> SaveResult<S, ID> save(S entity) throws GenerateException, IllegalAccessException {
        AbstractJpaSave save = new MySQLSaveImpl(getTableName());

        List<FieldTypeNameValue> fieldTypeNameValues = ReflectDatas.getFieldTypeNameValues(entity);// 获取包含null值的属性 TODO 后续使用

        fieldTypeNameValues.forEach((fieldTypeNameValue -> {
            String fieldName = fieldTypeNameValue.getName();// 获取属性名称
            Field field = ReflectDatas.getField(entityClass, fieldName);
            String columnName = super.getColumnName(field); // 转换为标准列名称

            Serializable value = (Serializable) fieldTypeNameValue.getValue(); // 该值一定为 Serializable 的子类（上述获取已做过滤（ fieldValue insteadof Serializable ））

            save.save(columnName, value);
        }));

        return super.executeSave(save, entity);
    }

    @Override
    public DeleteResult deleteById(ID id) throws GenerateException {
        AbstractJpaDelete delete = new MySQLDeleteImpl(getTableName());

        String primaryKeyColumnName = getPrimaryKeyColumnName(); // 获取 主键列名

        delete.getWhere().andCriteria(primaryKeyColumnName, ConditionalEnum.EQUALS, id);

        return executeDelete(delete);
    }

    @Override
    public DeleteResult delete(T entity) throws GenerateException {
        AbstractJpaDelete delete = new MySQLDeleteImpl(getTableName());

        // 获取存在值的 FieldTypeNameValue , 添加作为存入列
        List<FieldTypeNameValue> fieldTypeNameValues = ReflectDatas.getFieldTypeNameValuesWithValuesIsNotNull(entity);

        fieldTypeNameValues.forEach((fieldTypeNameValue -> {
            String columnName;
            {
                String fieldName = fieldTypeNameValue.getName();// 获取属性名称
                Field field = ReflectDatas.getField(entityClass, fieldName);
                columnName = super.getColumnName(field); // 转换为标准列名称
            }
            Serializable value = (Serializable) fieldTypeNameValue.getValue(); // 该值一定为 Serializable 的子类（上述获取已做过滤（ fieldValue insteadof Serializable ））

            delete.getWhere().andCriteria(columnName, ConditionalEnum.EQUALS, value);
        }));

        return executeDelete(delete);
    }

    @Override
    public DeleteResult deleteAll() throws GenerateException {
        AbstractJpaDelete delete = new MySQLDeleteImpl(getTableName());
        return super.executeDelete(delete);
    }

    @Override
    public T queryById(ID id) throws GenerateException {
        AbstractJpaQuery query = new MySQLQueryImpl(getTableName());

        String primaryKeyColumnName = super.getPrimaryKeyColumnName();
        query.getWhere().andCriteria(primaryKeyColumnName, ConditionalEnum.EQUALS, id); // 添加查询条件

        query.queryAll(entityClass);

        return queryOne(query);
    }

    @Override
    public Collection<T> queryByIds(Collection<ID> ids) throws GenerateException {
        AbstractJpaQuery query = new MySQLQueryImpl(getTableName());

        String primaryKeyColumnName = super.getPrimaryKeyColumnName();
        query.getWhere().in(primaryKeyColumnName, ids);

        query.queryAll(entityClass);

        return queryList(query);
    }

    @Override
    public Collection<T> queryAll() throws GenerateException {
        AbstractJpaQuery query = new MySQLQueryImpl(getTableName());

        query.queryAll(entityClass); // 查询所有字段

        return queryList(query);
    }

}
