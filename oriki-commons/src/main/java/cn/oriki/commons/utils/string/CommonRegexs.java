package cn.oriki.commons.utils.string;

import cn.oriki.commons.loader.ConfigLoader;

/**
 * CommonRegexs
 *
 * @author oriki.wang
 */
public class CommonRegexs extends Regexs {

    static {
        ConfigLoader loader = new ConfigLoader("oriki-commons-regex.properties");

        // 更新到2018年5月，支持166号段
        phone_regex = loader.getProperty("oriki.regex.phone-number", "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$");
    }

    private static String phone_regex;

    /**
     * 匹配手机号码
     *
     * @param phoneNumber 待测 phoneNumber
     * @return 是否匹配手机号
     */
    public static boolean isPhone(String phoneNumber) {
        return regex(phone_regex, phoneNumber);
    }

}
