package cn.oriki.commons.utils.string;

/**
 * StringConverts
 *
 * @author oriki.wang
 */
public class StringConverts {

    // 全角和半角的转换---------------------------------------------------------------------------------------------------

    /**
     * 字符串中全角转半角
     *
     * @param string 可能含全角的 String
     * @return 转换后 String
     */
    /*public static String toSBC(String string) {
        char[] chars = string.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();

        for (char aChar : chars) {
            if (aChar == ' ') {
                stringBuilder.append('\u3000');
            } else if (aChar < '\177') {
                stringBuilder.append((char) (aChar + 65248));
            } else {
                stringBuilder.append(aChar);
            }
        }

        return stringBuilder.toString();
    }*/

    /**
     * 字符串中半角转全角
     *
     * @param string 可能含半角的String
     * @return 转换后 String
     */
    /*public static String toDBC(String string) {
        char[] chars = string.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();

        for (char aChar : chars) {
            if (aChar == '\u3000') {
                stringBuilder.append(' ');
            } else if (aChar > '\uFF00' && aChar < '\uFF5F') {
                stringBuilder.append((char) (aChar - 65248));
            } else {
                stringBuilder.append(aChar);
            }
        }

        return stringBuilder.toString();
    }*/

    // Java命名和标准表结构命名的转换--------------------------------------------------------------------------------------

    /**
     * JavaClassName to SQLTableName （ eg : CustomerInformation -> t_customer_information ，标准结构 ）
     *
     * @param javaClassName JavaClassName
     * @return SQLTableName
     */
    /*public static String toSQLTableName(String javaClassName) {
        return toSQLName(javaClassName, "t_");
    }*/

    /**
     * JavaClassName to SQLTableName （ eg : CustomerInformation -> 特定前缀 + customer_information ）
     *
     * @param javaClassName JavaClassName
     * @return SQLTableName
     */
    public static String toSQLTableName(String javaClassName, String prefix) {
        return toSQLName(javaClassName, prefix);
    }

    /**
     * JavaFieldName to SQLColumnName （ eg : userName -> user_name ）
     *
     * @param javaFieldName JavaFieldName
     * @return SQLColumnName
     */
    public static String toSQLColumnName(String javaFieldName) {
        return toSQLName(javaFieldName, "");
    }

    /**
     * SQLTableName to JavaClassName （ eg : 特定前缀 + customer_information -> CustomerInfomation ）
     *
     * @param tableName 标准表名（含前缀）
     * @return 标准Java类名
     */
    public static String toJavaClassName(String tableName, String prefix) {
        if (Strings.isNotBlank(tableName)) {
            tableName = tableName.toLowerCase();
            // 去除常用前缀
            tableName = tableName.replaceFirst(prefix, "");

            String[] strings = tableName.split("_");
            if (strings.length == 1) {
                return firstLetterUpper(tableName);
            } else {
                StringBuilder classNameTemp = new StringBuilder();
                for (String string : strings) {
                    classNameTemp.append(firstLetterUpper(string));
                }
                return classNameTemp.toString();
            }
        }
        return tableName;
    }

    /**
     * SQLTableName to JavaClassName （ eg : t_customer_information -> CustomerInfomation ）
     *
     * @param SQLTableName 标准表名（含前缀）
     * @return 标准Java类名
     */
    /*public static String toJavaClassName(String SQLTableName) {
        return toJavaClassName(SQLTableName, "t_");
    }*/

    /**
     * SQLColumnName to JavaFieldName （ eg : user_name -> userName ）
     *
     * @param columnName 标准列名
     * @return JavaFieldName
     */
    public static String toJavaFieldName(String columnName) {
        return toJavaClassName(columnName, "");
    }

    // Java 转换为 SQL ，不存在前缀
    /*private static String toSQLName(String javaName) {
        return toSQLName(javaName, "");
    }*/

    // Java 转换为 SQL ，存在前缀
    private static String toSQLName(String javaName, String prefix) {
        char[] chars = javaName.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        // 如有前缀进行拼接
        if (Strings.isNotBlank(prefix)) {
            stringBuilder.append(prefix);
        }

        for (char aChar : chars) {
            if (Character.isUpperCase(aChar)) {
                if (!prefix.equals(stringBuilder.toString())) {
                    stringBuilder.append("_");
                }
                stringBuilder.append((char) (aChar + 32));
            } else {
                stringBuilder.append(aChar);
            }
        }

        return stringBuilder.toString();
    }

    // 单词首字母大写（其余小写）
    private static String firstLetterUpper(String string) {
        if (Strings.isNotBlank(string)) {
            if (1 == string.length()) {
                return string.toUpperCase();
            }
            string = string.toLowerCase();
            return string.substring(0, 1).toUpperCase()
                    + string.substring(1, string.length());
        }
        return string;
    }

}
