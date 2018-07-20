package cn.oriki.commons.utils.collection;


import cn.oriki.commons.constants.StringConstants;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Collections
 *
 * @author oriki.wang
 */
public class Collections {

    /**
     * Collection 对象已实例化并且有元素
     *
     * @param collection Collection 对象
     * @return Collection 对象存在 & 有元素，返回 true
     */
    public static boolean isNotNullAndHasElements(Collection<?> collection) {
        return Objects.nonNull(collection) && !collection.isEmpty();
    }

    /**
     * 判断 Collection 对象未实例化或没有元素
     *
     * @param collection Collection 对象
     * @return Collection 对象为空 | 存在但没有元素，返回 true
     */
    public static boolean isNullOrEmpty(Collection<?> collection) {
        return !isNotNullAndHasElements(collection);
    }

    /**
     * Collection 根据 separator 进行拼接，并添加前缀后缀生成字符串
     *
     * @param collection Collection 对象
     * @param separator  分隔符
     * @param prefix     前缀
     * @param suffix     后缀
     * @return 拼接后的字符串
     */
    public static String join(Collection<?> collection, String separator, String prefix, String suffix) {
        return prefix + join(collection, separator) + suffix;
    }

    /**
     * Collection 根据 separator 进行拼接，生成字符串
     * <p>
     * 过滤除去不存在元素
     *
     * @param collection Collection 对象
     * @param separator  分隔符
     * @return 拼接后字符串
     */
    public static String join(Collection<?> collection, String separator) {
        // 过滤集合中为 null 元素
        collection = collection.stream().filter(Objects::nonNull).collect(Collectors.toList());

        if (Objects.isNull(collection)) {
            // 不存在映射返回空字符串
            return StringConstants.EMPTY_STRING_VALUE;
        } else {
            Iterator<?> iterator = collection.iterator();
            if (!iterator.hasNext()) {
                return StringConstants.EMPTY_STRING_VALUE;
            } else {
                Object first = iterator.next();
                if (!iterator.hasNext()) {
                    return Objects.isNull(first) ? StringConstants.EMPTY_STRING_VALUE : first.toString();
                } else {
                    StringBuilder stringBuilder = new StringBuilder(256);
                    if (Objects.nonNull(first)) {
                        stringBuilder.append(first);
                    }

                    while (iterator.hasNext()) {
                        Object obj = iterator.next();
                        if (Objects.isNull(obj)) {
                            continue;
                        }

                        if (Objects.nonNull(separator)) {
                            stringBuilder.append(separator);
                        }
                        stringBuilder.append(obj.toString());
                    }
                    return stringBuilder.toString();
                }
            }
        }
    }

    /**
     * 判断对象是否为集合或者为枚举集（备用）
     *
     * @param object 带判断对象
     * @return 如果是集合子类，返回 true
     */
    public static boolean isCollection(Object object) {
        return object instanceof Iterable || object instanceof Enumeration;
    }

    /**
     * 创建一个长度为 n 的集合，填充 o 对象
     * <p>
     * 实际调用的是 java.util.Collection 的 nCopy 方法
     *
     * @param n      集合长度
     * @param object 填充对象
     * @param <T>    泛型
     * @return 填充 o 对象的集合
     */
    public static <T> List<T> nCopies(int n, T object) {
        return java.util.Collections.nCopies(n, object);
    }

}
