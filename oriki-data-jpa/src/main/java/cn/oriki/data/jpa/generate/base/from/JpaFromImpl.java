package cn.oriki.data.jpa.generate.base.from;

import cn.oriki.commons.utils.string.Strings;
import cn.oriki.data.generate.base.from.AbstractFrom;
import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.generate.result.GenerateResult;

/**
 * 通用 From
 *
 * @author oriki.wang
 */
public class JpaFromImpl extends AbstractFrom {

    private static final String FROM_KEY_WORD = " FROM ";

    public JpaFromImpl(String tableName) {
        super(tableName);
    }

    @Override
    public GenerateResult generate() throws GenerateException {
        String tableName = getFromName();
        if (Strings.isNotBlank(tableName)) {
            // FROM table_name ， table_name - 直接为来源名称
            String fromTableName = FROM_KEY_WORD + tableName + " ";
            return new GenerateResult(fromTableName);
        }
        throw new GenerateException("tableName can't be null");
    }

}
