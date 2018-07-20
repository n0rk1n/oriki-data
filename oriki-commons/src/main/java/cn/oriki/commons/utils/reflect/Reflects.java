package cn.oriki.commons.utils.reflect;

import cn.oriki.commons.utils.reflect.entity.FieldTypeNameValue;
import cn.oriki.commons.utils.string.Strings;
import com.google.common.collect.Lists;
import lombok.NonNull;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Reflects
 *
 * @author oriki.wang
 */
public class Reflects {

    /**
     * 全路径分隔符
     */
    private static final String CLASS_PATH_SEPARATOR = "\\.";

    /**
     * 截取字节码文件的类名（如 java.lang.Object ， 截取获得 Object）
     *
     * @param clazz 字节码文件
     * @param <T>   泛型
     * @return Class 类名
     */
    public static <T> String getClassName(Class<T> clazz) {
        String[] split = clazz.getName().split(CLASS_PATH_SEPARATOR);
        return split[split.length - 1];
    }

    /**
     * 获取字节码文件的所有 public method（包含父类）
     *
     * @param clazz 字节码文件
     * @param <T>   泛型
     * @return Class 对象中所有权限为 public 方法的对象
     */
    public static <T> List<Method> getPublicMethods(Class<T> clazz) {
        return Lists.newArrayList(clazz.getMethods());
    }

    /**
     * 获取字节码文件的所有 field (包含父类)
     *
     * @param clazz 字节码文件
     * @param <T>   泛型
     * @return 对象中所有成员变量
     */
    public static <T> List<Field> getFields(Class<T> clazz) {
        // 递归获取父类 Field
        return getFields(Lists.newArrayList(), clazz);
    }

    /**
     * 获取特定名称的 Field（包含父类）
     *
     * @param clazz     Class 对象
     * @param fieldName 属性名称
     * @param <T>       Class 泛型
     * @return Field 对象，如果不存在，返回 null
     */
    public static <T> Field getField(Class<T> clazz, String fieldName) {
        if (Strings.isBlank(fieldName)) {
            return null;
        }
        Field field = null;

        // 获取所有属性
        for (Field fieldTemp : getFields(clazz)) {
            if (fieldName.equals(fieldTemp.getName())) {
                field = fieldTemp;
                break;
            }
        }
        return field;
    }

    /**
     * 获取对象的属性 类型、名称和值（一定存在并且保证值为 Serializable 的子类） 集合
     *
     * @param entity 实体
     * @param <S>    泛型
     * @return 存在 value 的 FieldTypeNameValue
     */
    public static <S> List<FieldTypeNameValue> getFieldTypeNameValuesWithValuesIsNotNull(S entity) {
        List<FieldTypeNameValue> fieldTypeNameValues = getFieldTypeNameValues(entity);

        return fieldTypeNameValues.stream().filter(fieldTypeNameValue ->
                Objects.nonNull(fieldTypeNameValue.getValue()) && (fieldTypeNameValue.getValue() instanceof Serializable)).
                collect(Collectors.toList());
    }

    /**
     * 获取对象的属性的 类型、名称和值 集合（包含值为空的情况）
     *
     * @param entity Object 对象
     * @param <S>    泛型
     * @return 对象中的成员变量类型、名称和值
     */
    public static <S> List<FieldTypeNameValue> getFieldTypeNameValues(S entity) {
        List<FieldTypeNameValue> list = Lists.newArrayList();

        getFields(entity.getClass()).forEach((field -> {
            try {
                list.add(getFieldTypeNameValue(field, entity));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }));
        return list;
    }

    /**
     * 获取 Field 上的注解列表，如果不存在，返回空集合
     *
     * @param field Field 对象
     * @return Field 对象上的注解集合
     */
    public static List<Annotation> getAnnotations(Field field) {
        Annotation[] annotations = field.getDeclaredAnnotations();
        return Lists.newArrayList(annotations);
    }

    /**
     * 获取 Field 上的特定类型注解集合，如果不存在，返回 null
     *
     * @param field           Field 对象
     * @param annotationClass 特定注解类型
     * @return Field 对象上的注解
     */
    public static <C extends Annotation> Annotation getAnnotation(Field field, Class<C> annotationClass) {
        return field.getDeclaredAnnotation(annotationClass);
    }

    /**
     * 获取类上的所有注解的集合，如果不存在，返回空集合
     *
     * @param clazz Class 文件
     * @return 注解集合
     */
    public static List<Annotation> getAnnotations(Class clazz) {
        Annotation[] annotations = clazz.getDeclaredAnnotations();
        return Lists.newArrayList(annotations);
    }

    /**
     * 获取类上的特定注解，如果不存在，返回null
     *
     * @param clazz      类的字节码文件
     * @param annotation 特定注解的字节码文件
     * @return 特定注解对象
     */
    public static <C extends Annotation> Annotation getAnnotation(Class<?> clazz, Class<C> annotation) {
        return clazz.getDeclaredAnnotation(annotation);
    }

    /**
     * 递归，获取 Class 本身及其父类中成员变量
     *
     * @param list  存储集合
     * @param clazz Class 对象
     * @param <T>   泛型
     * @return 存储集合
     */
    private static <T> List<Field> getFields(@NonNull List<Field> list, Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        // 存入本字节码文件所有 Field
        list.addAll(Arrays.asList(fields));
        Class<? super T> superclass = clazz.getSuperclass();
        if (Objects.nonNull(superclass) && !Object.class.equals(superclass.getName())) {
            getFields(list, superclass);
        }
        return list;
    }

    /**
     * 获取对象中对应成员变量的FieldTypeNameValue对象
     *
     * @param field  对应想要获取实体类中属性的 Field 对象
     * @param entity 实体类
     * @param <S>    泛型
     * @return FieldTypeNameValue 对象
     * @throws IllegalAccessException field 对象获取值不成功时抛出的异常
     */
    private static <S> FieldTypeNameValue getFieldTypeNameValue(@NonNull Field field, S entity) throws IllegalAccessException {
        // 设置 Field 对象权限
        field.setAccessible(true);

        Object o = field.get(entity);
        return new FieldTypeNameValue(field.getType(), field.getName(), o);
    }

}
