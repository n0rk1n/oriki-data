package cn.oriki.commons.constants;

import java.io.File;

/**
 * String 常量
 *
 * @author oriki.wang
 */
public interface StringConstants {

    String EMPTY_STRING_VALUE = "";

    String SYSTEM_FILE_SEPERATOR = File.separator; // 在 UNIX 系统上，此字段的值为 '/'；在 Microsoft Windows 系统上，它为 '\\'

    String SYSTEM_FILE_PATH_SEPERATOR = File.pathSeparator; // 在 UNIX 系统上，此字段为 ':'；在 Microsoft Windows 系统上，它为 ';'

}
