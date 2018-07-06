package cn.oriki.commons.utils.string;

import cn.oriki.commons.utils.collection.Collections;
import com.alibaba.fastjson.JSON;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Objects;

/**
 * Jsons
 *
 * @author oriki.wang
 */
public class Jsons {

    private static final String EMPTY_OBJECT_JSON = "{}";

    private static final String EMPTY_ARRAY_JSON = "[]";

    private static final String EMPTY_COLLECTION_JSON = "[]";

    /**
     * json 转 object 的方法
     *
     * @param text  Json
     * @param clazz 待转换对象字节码
     * @param <T>   泛型
     * @return entity 实体
     */
    public static <T> T toObject(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    /**
     * object 转 jsonString 的方法
     *
     * @param object entity 对象
     * @return 转换后的 Json
     */
    public static <S> String toJson(S object) {
        if (Objects.isNull(object)) {
            return EMPTY_OBJECT_JSON;
        }

        // 为空集合
        if (Collections.isCollection(object) && Collections.isNullOrEmpty((Collection<?>) object)) {
            return EMPTY_COLLECTION_JSON;
        }

        // 为空数组
        if (object.getClass().isArray()) {
            if (0 == Array.getLength(object)) {
                return EMPTY_ARRAY_JSON;
            }
        }

        return JSON.toJSONString(object);
    }

}
