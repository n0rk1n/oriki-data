package cn.oriki.data.repository;

import cn.oriki.data.annotation.PrimaryKey;
import cn.oriki.data.utils.reflect.ReflectDatas;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

public abstract class AbstractRepository<T, ID extends Serializable> implements PagingAndSortRepository<T, ID> {

    protected Class<T> entityClass;
    protected Class<ID> idClass;

    public AbstractRepository(Class<T> T, Class<ID> ID) {
        this.entityClass = T;
        this.idClass = ID;
    }

    /**
     * save 方法使用，将获取的主键存入实体类中
     *
     * @param entity save 对象
     * @param id     插入后获取的id
     * @param <S>    泛型
     * @throws IllegalAccessException Field 对象存入数据失败抛出异常
     */
    protected <S extends T> void putIdToEntity(S entity, ID id) throws IllegalAccessException {
        Field field = getPrimaryKeyField();
        if (Objects.isNull(field)) {
            throw new IllegalArgumentException("we can't find @ParmaryKey at " + entityClass.getName());
        }
        field.setAccessible(true);
        field.set(entity, id);
    }

    /**
     * 获取被 @PrimaryKey 标识的属性，默认情况下一个实体类中只有一个
     *
     * @return 被 @PrimaryKey 标识的属性
     */
    protected Field getPrimaryKeyField() {
        List<Field> fields = ReflectDatas.getFields(entityClass); // 获取实体及其父类的 Fields

        Field _field = null;
        int count = 0;

        for (Field field : fields) {
            List<Annotation> annotations = ReflectDatas.getAnnotations(field);
            for (Annotation annotation : annotations) {
                if (PrimaryKey.class.equals(annotation.annotationType())) {
                    _field = field;
                    count++;

                    // check 条件：有且只有一个
                    if (count > 1) {
                        break;
                    }
                }
            }
        }
        if (Objects.nonNull(_field) && count == 1) {
            return _field;
        }
        throw new IllegalArgumentException("we need only one @PrimaryKey but find more than 1");
    }

}
