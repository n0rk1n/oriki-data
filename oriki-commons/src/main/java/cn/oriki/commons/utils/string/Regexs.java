package cn.oriki.commons.utils.string;

import java.util.regex.Pattern;

/**
 * Regexs
 *
 * @author oriki.wang
 */
public class Regexs {

    /**
     * regex 匹配
     *
     * @param regex   正则表达式
     * @param content 待检测字符串
     * @return 待检测字符串匹配正则，返回 true
     */
    public static boolean regex(String regex, String content) {
        return regex(regex, 0, content);
    }

    /**
     * regex 匹配
     * <p>
     * flags参数详见Pattern
     *
     * @param regex   正则表达式
     * @param flags   Pattern 的常量
     * @param content 待检测字符串
     * @return 待检测字符串匹配正则，返回 true
     */
    public static boolean regex(String regex, int flags, String content) {
        return Pattern.compile(regex, flags).matcher(content).matches();
    }

    /**
     * 对匹配元素进行替换
     * <p>
     * flags参数详见Pattern
     *
     * @param regex         正则表达式
     * @param flags         Pattern 的常量
     * @param content       待检测字符串
     * @param replaceString 替换字符串
     * @return 替换后字符串
     */
    public static String replace(String regex, int flags, String content, String replaceString) {
        return Pattern.compile(regex, flags).matcher(content).replaceAll(replaceString);
    }

    /**
     * 对匹配元素进行替换
     *
     * @param regex         正则表达式
     * @param content       待检测字符串
     * @param replaceString 替换字符串
     * @return 替换后字符串
     */
    public static String replace(String regex, String content, String replaceString) {
        return replace(regex, 0, content, replaceString);
    }

}
