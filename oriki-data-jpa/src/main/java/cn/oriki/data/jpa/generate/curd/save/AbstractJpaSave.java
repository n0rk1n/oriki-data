package cn.oriki.data.jpa.generate.curd.save;

import cn.oriki.commons.utils.collection.Collections;
import cn.oriki.data.generate.Generate;
import cn.oriki.data.generate.base.from.AbstractFrom;
import cn.oriki.data.generate.curd.save.AbstractSave;
import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.generate.result.GenerateResult;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public abstract class AbstractJpaSave extends AbstractSave {

    private static final String INSERT_INTO_KEY_WORD = "INSERT INTO ";
    private static final String VLAUES_KEY_WORD = " VALUES ";

    public AbstractJpaSave(AbstractFrom from) {
        super(from);
    }

    @Override
    public GenerateResult generate() throws GenerateException {
        // GOAL:
        // INSERT INTO table_name ( column1 , column2 , column3 ) VALUES ( ? , ? , ? )
        //                                                              value1 value2 value3
        StringBuilder stringBuilder = new StringBuilder();
        GenerateResult params = this.generateParams();

        stringBuilder.append(INSERT_INTO_KEY_WORD); // INSERT INTO
        stringBuilder.append(getFrom().getFromName()); // 获取表名

        stringBuilder.append(Generate.LEFT_PARENTHESIS + params.getGenerateResult() + RIGHT_PARENTHESIS); // ( column1 , column2 , column3 )

        stringBuilder.append(VLAUES_KEY_WORD); // VALUES

        String join2 = Collections.join(Collections.nCopies(params.getParams().size(), Generate.INJECTION), Generate.COMMA);
        stringBuilder.append(Generate.LEFT_PARENTHESIS + join2 + Generate.RIGHT_PARENTHESIS); // ( ? , ? , ? )

        GenerateResult result = new GenerateResult();
        {
            result.setParams(params.getParams());
            result.setGenerateResult(stringBuilder.toString());
        }
        return result;
    }

    // 获取 " key1, key2 , key3 " 和  [ value1 , value2 , value3 ]
    private GenerateResult generateParams() {
        Map<String, Serializable> params = super.getParams();
        GenerateResult result = new GenerateResult();

        Set<String> keys = params.keySet();
        String join = Collections.join(keys, " , ");
        result.setGenerateResult(join);

        String[] split = join.split(" , ");

        for (String key : split) {
            result.setParam(params.get(key));
        }
        return result;
    }

}
