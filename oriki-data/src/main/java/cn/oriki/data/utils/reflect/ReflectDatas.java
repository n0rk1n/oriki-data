package cn.oriki.data.utils.reflect;

import cn.oriki.commons.utils.reflect.Reflects;

import java.math.BigInteger;
import java.text.ParseException;

/**
 * ReflectDatas
 *
 * @author oriki.wang
 */
public class ReflectDatas extends Reflects {

    /**
     * 转换 ID
     *
     * @param idClass  Class 文件
     * @param idString id对应值
     * @param <ID>     泛型
     * @return 对应泛型类型
     * @throws ParseException 转换失败抛出的异常
     */
    @SuppressWarnings("unchecked")
    public static <ID> ID parseId(Class<ID> idClass, String idString) throws ParseException {
        if (Byte.class.equals(idClass)) {
            return (ID) Byte.valueOf(idString);
        } else if (Short.class.equals(idClass)) {
            return (ID) Short.valueOf(idString);
        } else if (Integer.class.equals(idClass)) {
            return (ID) Integer.valueOf(idString);
        } else if (Long.class.equals(idClass)) {
            return (ID) Long.valueOf(idString);
        } else if (BigInteger.class.equals(idClass)) {
            return (ID) BigInteger.valueOf(Long.valueOf(idString));
        } else if (Float.class.equals(idClass)) {
            return (ID) Float.valueOf(idString);
        } else if (Double.class.equals(idClass)) {
            return (ID) Double.valueOf(idString);
        } else if (String.class.equals(idClass)) {
            return (ID) String.valueOf(idString);
        }
        throw new ParseException("we can't parse id", 0);
    }

}
