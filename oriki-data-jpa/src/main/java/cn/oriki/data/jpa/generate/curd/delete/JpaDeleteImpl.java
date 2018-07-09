package cn.oriki.data.jpa.generate.curd.delete;

import cn.oriki.data.generate.base.from.AbstractFrom;
import cn.oriki.data.generate.base.where.AbstractWhere;
import cn.oriki.data.generate.curd.delete.AbstractDelete;
import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.generate.result.GenerateResult;

public class JpaDeleteImpl extends AbstractDelete {

    private static final String DELETE_KEY_WORD = " DELETE ";

    public JpaDeleteImpl(AbstractWhere where, AbstractFrom from) {
        super(where, from);
    }

    @Override
    public GenerateResult generate() throws GenerateException {
        // GOAL:
        //      DELETE FROM table_name where (key1 = ? or key2 = ?) and ( key3 = ? and key4 = ?) and ( key5 = ?)
        //                                         value1      value2          value3       value4          value5
        GenerateResult result = new GenerateResult();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DELETE_KEY_WORD); // DELETE

        GenerateResult fromResult = super.getFrom().generate();
        stringBuilder.append(fromResult.getGenerateResult()); // FROM table_name

        GenerateResult whereResult = super.getWhere().generate();
        stringBuilder.append(whereResult.getGenerateResult());

        // 拼接参数
        result.setParams(whereResult.getParams());
        // 拼接语句
        result.setGenerateResult(stringBuilder.toString());

        return result;
    }

}
