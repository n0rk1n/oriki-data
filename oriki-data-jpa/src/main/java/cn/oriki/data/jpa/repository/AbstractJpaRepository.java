package cn.oriki.data.jpa.repository;

import cn.oriki.commons.utils.reflect.entity.FieldTypeNameValue;
import cn.oriki.data.generate.curd.cud.delete.result.DeleteResult;
import cn.oriki.data.generate.curd.cud.save.result.SaveResult;
import cn.oriki.data.generate.curd.cud.update.result.UpdateResult;
import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.jpa.generate.curd.delete.JpaDeleteImpl;
import cn.oriki.data.jpa.generate.curd.save.JpaSaveImpl;
import cn.oriki.data.jpa.generate.curd.update.JpaUpdateImpl;
import cn.oriki.data.utils.reflect.ReflectDatas;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

public abstract class AbstractJpaRepository<T, ID extends Serializable> extends AbstractJpaRepositorys<T, ID> {

    public AbstractJpaRepository(Class<T> entityClass, Class<ID> idClass) {
        super(entityClass, idClass);
    }

    // -----------------------------------------------------------------------------------------------------------------

    private JpaSaveImpl getSaveImpl() {
        return new JpaSaveImpl(getTableName());
    }

    @Override
    public <S extends T> SaveResult<S, ID> save(S entity) throws GenerateException, IllegalAccessException {
        JpaSaveImpl save = getSaveImpl();


        List<FieldTypeNameValue> fieldTypeNameValues = ReflectDatas.getFieldTypeNameValues(entity);// 获取包含null值的属性 TODO 后续使用
        fieldTypeNameValues.forEach((fieldTypeNameValue -> {
            String fieldName = fieldTypeNameValue.getName();// 获取属性名称
            Field field = ReflectDatas.getField(entityClass, fieldName);
            String columnName = getColumnName(field); // 转换为标准列名称

            Serializable value = (Serializable) fieldTypeNameValue.getValue(); // 该值一定为 Serializable 的子类（上述获取已做过滤（ fieldValue insteadof Serializable ））

            save.save(columnName, value);
        }));

        return executeSave(save, entity);
    }

    // -----------------------------------------------------------------------------------------------------------------

    private JpaDeleteImpl getDeleteImpl() {
        return new JpaDeleteImpl(getTableName());
    }

    @Override
    public DeleteResult deleteById(ID id) throws GenerateException {
        JpaDeleteImpl delete = getDeleteImpl();

        String primaryKeyColumnName = getPrimaryKeyColumnName(); // 获取 主键列名

        delete.getWhere().equals(primaryKeyColumnName, id);

        return executeDelete(delete);
    }

    @Override
    public DeleteResult delete(T entity) throws GenerateException {
        JpaDeleteImpl delete = getDeleteImpl();

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
        JpaDeleteImpl delete = getDeleteImpl();
        return super.executeDelete(delete);
    }

    // -----------------------------------------------------------------------------------------------------------------

    private JpaUpdateImpl getUpdateImpl() {
        return new JpaUpdateImpl(getTableName());
    }

    @Override
    public <S extends T> UpdateResult update(S entity) throws GenerateException, IllegalAccessException {
        // 根据 id 查询后，不存在执行 save 操作
        Serializable id = getIdValue(entity);

        UpdateResult updateResult = new UpdateResult();

        if (Objects.isNull(id)) {
            SaveResult<S, ID> saveResult = save(entity);
            {
                updateResult.isUpdate(false); // 为 save 操作
                updateResult.setNumber(saveResult.getNumber());
            }
        } else {
            // 更新操作
            JpaUpdateImpl update = getUpdateImpl();

            List<FieldTypeNameValue> fieldTypeNameValues = ReflectDatas.getFieldTypeNameValues(entity);// 获取包含null值的属性
            fieldTypeNameValues.forEach((fieldTypeNameValue -> {
                String fieldName = fieldTypeNameValue.getName(); // 获取属性名称
                Field field = ReflectDatas.getField(entityClass, fieldName);
                String columnName = getColumnName(field); // 转换为标准列名称
                Serializable value = (Serializable) fieldTypeNameValue.getValue(); // 该值一定为 Serializable 的子类（上述获取已做过滤（ fieldValue insteadof Serializable ））

                update.update(columnName, value);
            }));

            update.getWhere().equals(getPrimaryKeyColumnName(), id); // 拼接 where

            int i = super.executeUpdate(update);
            {
                updateResult.setNumber(i);
            }
        }

        return updateResult;
    }

}
