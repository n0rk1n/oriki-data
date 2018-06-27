package cn.oriki.data.jpa.generate.curd.from;

import cn.oriki.commons.utils.string.Strings;
import cn.oriki.data.generate.curd.from.AbstractFrom;
import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.generate.result.GenerateResult;

public abstract class AbstractJpaFrom extends AbstractFrom {

    private static final String FROM_KEY_WORD = " FROM ";

    public AbstractJpaFrom(String fromName) {
        super(fromName);
    }

    @Override
    public GenerateResult generate() throws GenerateException {
        String tableName = getFromName();
        if (Strings.isNotBlank(tableName)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(FROM_KEY_WORD); // FROM
            stringBuilder.append(" " + tableName + " "); // table_name - 直接为来源名称

            String fromTableName = stringBuilder.toString(); // FROM table_name

            // 拼接结果
            return new GenerateResult(fromTableName);
        }
        throw new GenerateException("tableName can't be null");
    }

}
