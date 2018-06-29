package cn.oriki.commons.constants;

import java.math.BigInteger;

/**
 * Class & ClassPath 常量
 *
 * @author oriki.wang
 */
public interface ClassConstants {

    Class<Object> JAVA_LANG_OBJECT = Object.class; // java.lang.Object
    String JAVA_LANG_OBJECT_CLASS_PATH = JAVA_LANG_OBJECT.getName();

    Class<Byte> JAVA_LANG_BYTE = Byte.class;
    String JAVA_LANG_BTYE_CLASS_PATH = JAVA_LANG_BYTE.getName();

    Class<Short> JAVA_LANG_SHORT = Short.class;
    String JAVA_LANG_SHORT_CLASS_PATH = JAVA_LANG_SHORT.getName();

    Class<Integer> JAVA_LANG_INTEGER = Integer.class;
    String JAVA_LANG_INTEGER_CLASS_PATH = JAVA_LANG_INTEGER.getName();

    Class<Long> JAVA_LANG_LONG = Long.class;
    String JAVA_LANG_LONG_CLASS_PATH = JAVA_LANG_LONG.getName();

    Class<BigInteger> JAVA_MATH_BIGINTEGER = BigInteger.class;
    String JAVA_MATH_BIGINTEGER_CLASS_PATH = JAVA_MATH_BIGINTEGER.getName();

    Class<String> JAVA_LANG_STRING = String.class;
    String JAVA_LANG_STRING_CLASS_PATH = JAVA_LANG_STRING.getName();

    Class<Float> JAVA_LANG_FLOAT = Float.class;
    String JAVA_LANG_FLOAT_CLASS_PATH = JAVA_LANG_FLOAT.getName();

    Class<Double> JAVA_LANG_DOUBLE = Double.class;
    String JAVA_LANG_DOUBLE_CLASS_PATH = JAVA_LANG_DOUBLE.getName();

}
