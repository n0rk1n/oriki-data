package cn.oriki.data.jpa.generate.curd.update;

import cn.oriki.commons.utils.collection.Collections;
import cn.oriki.data.generate.Generate;
import cn.oriki.data.generate.base.where.enumeration.ConditionalEnum;
import cn.oriki.data.generate.curd.cud.update.AbstractUpdate;
import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.generate.result.GenerateResult;
import cn.oriki.data.jpa.generate.base.from.JpaFromImpl;
import cn.oriki.data.jpa.generate.base.predicate.JpaCUDPredictImpl;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JpaUpdateImpl extends AbstractUpdate {

    private static final String UPDATE_KEY_WORD = " UPDATE ";
    private static final String SET_KEY_WORD = " SET ";

    public JpaUpdateImpl(String tableName) {
        super(new JpaCUDPredictImpl(), new JpaFromImpl(tableName));
    }

    @Override
    public GenerateResult generate() throws GenerateException {
        // GOAL:
        //      UPDATE Person SET Address = ?, City = ?
        //                                value1    value2
        //      WHERE LastName = ?
        //                     value3
        GenerateResult generateResult = new GenerateResult();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(UPDATE_KEY_WORD); // UPDATE

        stringBuilder.append(super.getFrom().getFromName()); // table_name

        stringBuilder.append(SET_KEY_WORD); // SET

        List<String> sp = Lists.newArrayList(); // GOAL : key1 = ?
        List<Serializable> setPar = Lists.newArrayList(); // set 的参数

        Map<String, Serializable> setParams = getSetParams();
        Set<String> keySet = setParams.keySet();
        for (String key : keySet) {
            String s = key + ConditionalEnum.EQUALS.getConditional() + Generate.INJECTION;
            sp.add(s);

            setPar.add(setParams.get(key));
        }
        stringBuilder.append(Collections.join(sp, Generate.COMMA)); // key1 = ? , key2 = ? ...
        generateResult.setParams(setPar); // 追加参数

        GenerateResult whereResult = super.getWhere().generate();
        String whereSql = whereResult.getGenerateResult();
        List<Serializable> whereParams = whereResult.getParams();

        stringBuilder.append(whereSql);

        generateResult.setParams(whereParams); // 追加 where 参数

        generateResult.setGenerateResult(stringBuilder.toString());

        return generateResult;
    }

}
