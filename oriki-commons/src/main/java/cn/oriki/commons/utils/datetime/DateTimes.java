package cn.oriki.commons.utils.datetime;

import cn.oriki.commons.constants.DateTimeConstants;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DateTimes
 *
 * @author oriki.wang
 */
public class DateTimes {

    /**
     * 获取当前时间 LocalDateTime 对象
     *
     * @return 当前时间 LocalDateTime 对象
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * 判断 t1 是否在 t2 之前
     *
     * @param t1 LocalDateTime 比较对象
     * @param t2 LocalDateTime 待比较对象
     * @return t1 在 t2 之前，返回 true
     */
    public static boolean isBefore(LocalDateTime t1, LocalDateTime t2) {
        return t1.isBefore(t2);
    }

    /**
     * 判断 t1 是否在 t2 之后
     *
     * @param t1 LocalDateTime 比较对象
     * @param t2 LocalDateTime 待比较对象
     * @return t1 在 t2 之后，返回 true
     */
    public static boolean isAfter(LocalDateTime t1, LocalDateTime t2) {
        return t1.isAfter(t2);
    }

    /**
     * 获取两时间的时间差，单位为毫秒
     *
     * @param before 之前时间对象
     * @param after  之后时间对象
     * @return 两个时间的毫秒差值
     */
    public static long diffMillis(LocalDateTime before, LocalDateTime after) {
        Duration duration = Duration.between(before, after);
        return duration.toMillis();
    }

    /**
     * 获取两个时间查，填入时间为之前的时间，与调用该方法的时刻作比较，单位为毫秒值
     *
     * @param before
     * @return
     */
    public static long diffMillis(LocalDateTime before) {
        return diffMillis(before, now());
    }

    /**
     * 推进（后退） hours 小时
     * <p>
     * 链式结构
     *
     * @param localDateTime 目标对象
     * @param hours         延后的时间（小时）
     * @return 目标对象延后后后的对象
     */
    public static LocalDateTime addHours(LocalDateTime localDateTime, long hours) {
        return localDateTime.plusHours(hours);
    }

    /**
     * 推进（后退） months 月
     *
     * @param localDateTime 目标对象
     * @param months        延后的时间（小时）
     * @return 目标对象延后后后的对象
     */
    public static LocalDateTime addMonths(LocalDateTime localDateTime, long months) {
        return localDateTime.plusMonths(months);
    }

    /**
     * 以 yyyy-MM-dd 格式化 LocalDateTime 对象
     *
     * @param localDateTime LocalDateTime 对象
     * @return 时间字符串
     */
    public static String formatDate(LocalDateTime localDateTime) {
        return format(localDateTime, DateTimeConstants.NORMAL_DATE_PATTERN);
    }

    /**
     * 以 HH:mm:ss 格式化 LocalDateTime 对象
     *
     * @param localDateTime LocalDateTime 对象
     * @return 时间字符串
     */
    public static String formatTime(LocalDateTime localDateTime) {
        return format(localDateTime, DateTimeConstants.NORMAL_TIME_PATTERN);
    }

    /**
     * 以 yyyy-MM-dd HH:mm:ss 格式化 LocalDateTime 对象
     *
     * @param localDateTime LocalDateTime 对象
     * @return 时间字符串
     */
    public static String formatDateTime(LocalDateTime localDateTime) {
        return format(localDateTime, DateTimeConstants.NORMAL_DATETIME_PATTERN);
    }

    /**
     * 特定 pattern 格式化 LocalDateTime 对象（ TODO 暂定权限为 private）
     *
     * @param localDateTime LocalDateTime 对象
     * @param pattern       时间格式化字符串
     * @return 时间字符串
     */
    private static String format(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

}
