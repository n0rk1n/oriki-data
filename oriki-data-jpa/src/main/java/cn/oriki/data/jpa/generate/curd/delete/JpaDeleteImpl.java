package cn.oriki.data.jpa.generate.curd.delete;

import cn.oriki.data.generate.curd.delete.AbstractDelete;
import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.generate.result.GenerateResult;
import cn.oriki.data.jpa.generate.base.from.JpaFromImpl;
import cn.oriki.data.jpa.generate.base.where.JpaWhereImpl;

public class JpaDeleteImpl extends AbstractDelete {

    private static final String DELETE_KEY_WORD = " DELETE ";

    public JpaDeleteImpl(String tableName) {
        super(new JpaWhereImpl(), new JpaFromImpl(tableName));
    }

    @Override
    public GenerateResult generate() throws GenerateException {
        // GOAL:
        //      DELETE FROM table_name where (key1 = ? or key2 = ?) and ( key3 = ? and key4 = ?) and ( key5 = ?)
        //                                         value1      value2          value3       value4          value5
        GenerateResult result = new GenerateResult();

        StringBuilder stringBuilder = new StringBuilder();
        // DELETE
        stringBuilder.append(DELETE_KEY_WORD);
        // FROM table_name
        stringBuilder.append(super.getFrom().generate().getGenerateResult());

        GenerateResult whereResult = super.getWhere().generate();
        stringBuilder.append(whereResult.getGenerateResult());

        // 拼接语句
        result.setGenerateResult(stringBuilder.toString());
        // 拼接参数
        result.setParams(whereResult.getParams());

        return result;
    }

}
