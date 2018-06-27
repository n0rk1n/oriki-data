package cn.oriki.data.utils.reflect;

import cn.oriki.commons.constants.ClassConstants;
import cn.oriki.commons.utils.reflect.Reflects;
import cn.oriki.commons.utils.string.Strings;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ReflectDatas
 *
 * @author oriki.wang
 */
public class ReflectDatas extends Reflects {

    /**
     * 获取所有 getter 方法
     *
     * @param clazz entity 对象的字节码
     * @param <T>   泛型
     * @return Method 集合
     */
    public static <T> List<Method> getGetterMethod(Class<T> clazz) {
        return getMethodStartWithPrefix(clazz, ReflectConstants.GETTER_METHOD_PREFIX);
    }

    /**
     * 获取所有 setter 方法
     *
     * @param clazz entity 对象的字节码
     * @param <T>   泛型
     * @return Method 集合
     */
    public static <T> List<Method> getSetterMethod(Class<T> clazz) {
        return getMethodStartWithPrefix(clazz, ReflectConstants.SETTER_METHOD_PREFIX);
    }

    /**
     * 获取 getter 对应 Field
     *
     * @param clazz class 对象
     * @param <T>   泛型
     * @return 对应属性集合
     */
    public static <T> List<Field> getGetterFields(Class<T> clazz) {
        return getGetterOrSetterFields(clazz, ReflectConstants.GETTER_METHOD_PREFIX);
    }

    /**
     * 获取 setter 对应 Field
     *
     * @param clazz class 对象
     * @param <T>   泛型
     * @return 对应属性集合
     */
    public static <T> List<Field> getSetterFields(Class<T> clazz) {
        return getGetterOrSetterFields(clazz, ReflectConstants.SETTER_METHOD_PREFIX);
    }

    // 获取getter或setter对应成员变量
    private static <T> List<Field> getGetterOrSetterFields(Class<T> clazz, String prefix) {
        List<Method> gettersOrSetters = getMethodStartWithPrefix(clazz, prefix); // 获取所有

        List<String> fieldName = speculateFieldName(gettersOrSetters, prefix); // 转换成员变量名称（假定）

        List<Field> fields = getFields(clazz); // 获取所有成员变量

        // 对比，保留名称匹配 Field
        return fields.stream().filter((field -> {
            return fieldName.contains(field.getName());
        })).collect(Collectors.toList());
    }

    // 获取以 prefix 开头的 Method 集合（public权限）
    private static <T> List<Method> getMethodStartWithPrefix(Class<T> clazz, String prefix) {
        List<Method> methods = getPublicMethods(clazz);

        //获取所有以 prefix 开头的 public 方法，并且不为 Object 的 getClass 方法
        return methods.stream().filter((method -> {
            String methodName = method.getName();

            boolean flag = true;
            if (Strings.isBlank(methodName) || !methodName.startsWith(prefix))// 名称不为空 或 不以prefix开头
                flag = false;

            if ("getClass".equals(methodName) && ClassConstants.JAVA_LANG_OBJECT.equals(method.getDeclaringClass().getName())) {// 不为Object中的getClass
                flag = false;
            }
            return flag;
        })).collect(Collectors.toList());
    }

    // 推测方法的属性名称（针对setter & getter）
    private static List<String> speculateFieldName(List<Method> gettersOrSetter, String prefix) {
        return gettersOrSetter.stream().map((getterOrsetter) -> {
            String methodName = getterOrsetter.getName().replaceFirst(prefix, "");
            if (Strings.isNotBlank(methodName) && Character.isUpperCase(methodName.charAt(0))) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(Character.toLowerCase(methodName.charAt(0)));
                stringBuilder.append(methodName.substring(1));

                return stringBuilder.toString();
            }
            return null;
        }).collect(Collectors.toList());
    }

}