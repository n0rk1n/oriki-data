package cn.oriki.commons.utils.string;

import cn.oriki.commons.constants.StringConstants;

import java.util.Random;
import java.util.UUID;

/**
 * Randoms
 *
 * @author oriki.wang
 */
public class Randoms {

    private static final String NUMBER_STRING = "0123456789";

    /**
     * 获取固定长度 random number
     *
     * @param length 随机数的长度
     * @return random number
     */
    public static String getRandomNumber(int length) {
        if (length <= 0) {
            return StringConstants.EMPTY_STRING_VALUE;
        }
        return getRandom(NUMBER_STRING, length);
    }

    /**
     * 获取32位长度的 uuid
     *
     * @return uuid
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获取一个从 0 到 max 的数字
     *
     * @param max 最大值
     * @return 0 ~ max 的随机数
     */
    public static int randomInt(int max) {
        return new Random().nextInt(max);
    }

    /**
     * 给定字符串随机生成 random code
     *
     * @param targetString 目标随机字符串
     * @param length       获取长度
     * @return 随机字符串
     */
    private static String getRandom(String targetString, int length) {
        // length must >0
        if (length <= 0 || Strings.isBlank(targetString)) {
            return StringConstants.EMPTY_STRING_VALUE;
        }

        StringBuilder stringBuilder = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(targetString.length());
            char c = targetString.charAt(index);
            stringBuilder.append(c);
        }

        return stringBuilder.toString();
    }

}
