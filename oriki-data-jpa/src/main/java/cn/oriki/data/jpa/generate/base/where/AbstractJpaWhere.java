package cn.oriki.data.jpa.generate.base.where;

import cn.oriki.commons.constants.StringConstants;
import cn.oriki.commons.utils.collection.Collections;
import cn.oriki.data.generate.Generate;
import cn.oriki.data.generate.base.where.AbstractWhere;
import cn.oriki.data.generate.base.where.entity.Criteria;
import cn.oriki.data.generate.base.where.entity.OperatorCreterias;
import cn.oriki.data.generate.base.where.enumeration.ConditionalEnum;
import cn.oriki.data.generate.base.where.enumeration.OperatorEnum;
import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.generate.result.GenerateResult;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public abstract class AbstractJpaWhere extends AbstractWhere {

    private static final String WHERE_KEY_WORD = " WHERE ";

    @Override
    public GenerateResult generate() throws GenerateException {
        GenerateResult generateResult = new GenerateResult();

        List<OperatorCreterias> operatorCreterias = getOperatorCreterias();
        if (operatorCreterias.size() == 0) { // 没有条件，返回空字符串用于拼接
            generateResult.setGenerateResult(StringConstants.EMPTY_STRING_VALUE);
        } else {
            // GOAL:
            //      WHERE ( key1 = ? or key2 = ? ) and ( key3 = ? or key4 = ? ) and ( key5 = ? )
            //                   value1      value2           value3      value4           value5
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(WHERE_KEY_WORD); // WHERE

            List<String> cr = Lists.newArrayList();
            // GOAL : ( key1 = ? or key2 = ? ) and ( key3 = ? or key4 = ? ) and ( key5 = ? )
            for (OperatorCreterias operatorCreteria : operatorCreterias) {
                OperatorEnum operator = operatorCreteria.getOperator(); // and | or
                List<Criteria> criterias = operatorCreteria.getCriterias(); // keyN _conditional_ valueN *n

                // 结果参数
                List<String> criteriaString = Lists.newArrayList();
                List<Serializable> criteriaValues = Lists.newArrayList();

                for (Criteria criteria : criterias) {
                    String s;
                    if (!ConditionalEnum.IS_NOT.equals(criteria.getConditional())) {
                        Serializable value = criteria.getValue();

                        if (Objects.nonNull(value)) {
                            s = criteria.getKey() + criteria.getConditional().getConditional() + Generate.INJECTION; // key = ?
                            criteriaValues.add(criteria.getValue());
                        } else {
                            s = criteria.getKey() + ConditionalEnum.IS.getConditional() + " NULL "; // key is null
                        }
                    } else {
                        s = criteria.getKey() + ConditionalEnum.IS_NOT.getConditional() + " NULL "; // key is null
                    }

                    criteriaString.add(s);
                }
                String join = Collections.join(criteriaString, operator.getOperator(), Generate.LEFT_PARENTHESIS, Generate.RIGHT_PARENTHESIS); // ( key = ? and key2 = ? )

                cr.add(join);
                generateResult.setParams(criteriaValues); // 为result 添加参数
            }
            stringBuilder.append(Collections.join(cr, OperatorEnum.AND.getOperator())); // ( key1 = ? or key2 = ? ) and ( key3 = ? or key4 = ? ) and ( key5 = ? )

            generateResult.setGenerateResult(stringBuilder.toString());
        }
        return generateResult;
    }

    // 添加条件，关系符使用 operator
    /*private OperatorCreterias createOperatorCreteria(OperatorEnum operator, Criteria... keyConditionalValues) {
        OperatorCreterias operatorCreterias = new OperatorCreterias();
        {
            operatorCreterias.setOperator(operator);
            operatorCreterias.setCriterias(Lists.newArrayList(keyConditionalValues));
        }
        return operatorCreterias;
    }*/

}
