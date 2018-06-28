package cn.oriki.data.jpa.generate.curd.save;

import cn.oriki.commons.utils.collection.Collections;
import cn.oriki.data.generate.Generate;
import cn.oriki.data.generate.curd.from.From;
import cn.oriki.data.generate.curd.save.AbstractSave;
import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.generate.result.GenerateResult;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public abstract class AbstractJpaSave extends AbstractSave {

    private static final String INSERT_INTO_KEY_WORD = "INSERT INTO ";
    private static final String VLAUES_KEY_WORD = " VALUES ";

    public AbstractJpaSave(From from) {
        super(from);
    }

    @Override
    public GenerateResult generate() throws GenerateException {
        // GOAL:
        // INSERT INTO table_name ( column1 , column2 , column3 ) VALUES ( ? , ? , ? )
        //                                                              value1 value2 value3
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(INSERT_INTO_KEY_WORD); // INSERT INTO

        stringBuffer.append(super.getFromName()); // 获取表名

        stringBuffer.append(Generate.LEFT_PARENTHESIS); // (

        GenerateResult params = this.generateParams();
        stringBuffer.append(params.getGenerateResult()); // column1 , column2 , column3

        stringBuffer.append(Generate.RIGHT_PARENTHESIS); // )

        stringBuffer.append(VLAUES_KEY_WORD); // VALUES

        stringBuffer.append(Generate.LEFT_PARENTHESIS); // (
        String join2 = Collections.join(Collections.nCopies(params.getParams().size(), Generate.INJECTION), " , ");
        stringBuffer.append(join2);
        stringBuffer.append(Generate.RIGHT_PARENTHESIS);

        GenerateResult result = new GenerateResult();
        result.setParams(params.getParams());
        result.setGenerateResult(stringBuffer.toString());

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
