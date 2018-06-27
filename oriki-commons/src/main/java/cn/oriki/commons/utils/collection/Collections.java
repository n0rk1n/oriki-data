package cn.oriki.commons.utils.collection;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

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
     * @param <T>        泛型
     * @return Collection 对象存在 & 有元素，返回 true
     */
    public static <T> boolean isNotNullAndHasElements(Collection<T> collection) {
        return Objects.nonNull(collection) && !collection.isEmpty();
    }

    /**
     * Collection 对象不为空但是没有元素
     *
     * @param collection Collection 对象
     * @param <T>        泛型
     * @return Collection 对象存在 & 没有元素，返回 true
     */
    public static <T> boolean isNotNullButEmpty(Collection<T> collection) {
        return Objects.nonNull(collection) && collection.isEmpty();
    }

    /**
     * 判断 Collection 对象未实例化或没有元素
     *
     * @param collection Collection 对象
     * @param <T>        泛型
     * @return Collection 对象为空 | 存在但没有元素，返回 true
     */
    public static <T> boolean isNullOrEmpty(Collection<T> collection) {
        return !isNotNullAndHasElements(collection);
    }

    /**
     * Collection 根据 separator 进行拼接，并添加前缀后缀生成字符串
     *
     * @param collection Collection 对象
     * @param separator  分隔符
     * @param prefix     前缀
     * @param suffix     后缀
     * @param <T>        泛型
     * @return 拼接后的字符串
     */
    public static <T> String join(final Collection<T> collection, final String separator, String prefix, String suffix) {
        return prefix + join(collection, separator) + suffix;
    }

    /**
     * Collection 根据 separator 进行拼接，生成字符串
     * <p>
     * TODO 对 null 元素还是会做拼接，待解决
     *
     * @param collection Collection 对象
     * @param separator  分隔符
     * @param <T>        泛型
     * @return 拼接后字符串
     */
    public static <T> String join(final Collection<T> collection, final String separator) {
        return StringUtils.join(collection, separator);
    }

    /**
     * 判断对象是否为集合
     *
     * @param object 带判断对象
     * @return 如果是集合子类，返回 true
     */
    public static boolean isCollection(Object object) {
        return object instanceof Iterable || object instanceof Enumeration;
    }

    /**
     * 创建一个长度为 n 的集合，填充 o 对象
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
