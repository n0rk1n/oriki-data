package cn.oriki.commons.utils.reflect.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * 成员变量的类型、名称、实体类的值
 *
 * @author oriki.wang
 */
@Getter
public class FieldTypeNameValue {

    /**
     * 成员变量类型
     */
    private Class<?> type;

    /**
     * 成员变量名称
     */
    private String name;

    /**
     * 成员变量的值
     */
    @Setter
    private Object value;

    public FieldTypeNameValue(Class<?> type, String name, Object value) {
        // 保证 type 和 name 不能为 null
        if (Objects.isNull(type) || Objects.isNull(name)) {
            throw new IllegalArgumentException(getClass().getName() + " class instance fail , because fieldType and fieldName must be not null");
        }

        this.type = type;
        this.name = name;
        this.value = value;
    }

    public void setType(Class<?> type) {
        if (Objects.isNull(type)) {
            throw new IllegalArgumentException("fieldType must be not null");
        }
        this.type = type;
    }

    public void setName(String name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("fieldName must be not null");
        }
        this.name = name;
    }

}
