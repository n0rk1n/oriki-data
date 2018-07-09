package cn.oriki.data.jpa.generate.curd.query.impl;

import cn.oriki.commons.utils.collection.Collections;
import cn.oriki.commons.utils.string.Strings;
import cn.oriki.data.generate.Generate;
import cn.oriki.data.generate.base.predicate.AbstractPredicate;
import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.generate.result.GenerateResult;
import cn.oriki.data.jpa.generate.base.from.JpaFromImpl;
import cn.oriki.data.jpa.generate.base.predicate.impl.MySqlPredicateImpl;
import cn.oriki.data.jpa.generate.curd.query.AbstractJpaQuery;

import java.util.Objects;

public class MySqlQueryImpl extends AbstractJpaQuery {

    /*public MySQLQueryImpl(String tableName) {
        super(new MySQLWhereImpl(), new MySQLFromImpl(tableName));
    }*/

    @Override
    public GenerateResult generate() throws GenerateException {
        GenerateResult generateResult = new GenerateResult();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SELECT_KEY_WORD); // SELECT

        // 获取所有属性
        if (getSelectQuery().size() == 0) {
            stringBuilder.append(SELECT_ALL_KEY_WORD); // select * TODO 不使用别名可能会存在录入数据问题。最好保证 selectQuery 数据 > 0 ;
        } else {
            String join = Collections.join(getSelectQuery(), Generate.COMMA);
            stringBuilder.append(join);
        }

        String fromSQL = getFrom().generate().getGenerateResult();
        stringBuilder.append(fromSQL); // from table_name

        GenerateResult whereResult = getPredicate().getWhere().generate();
        String whereSQL = whereResult.getGenerateResult();
        stringBuilder.append(whereSQL); // where ...
        generateResult.setParams(whereResult.getParams());

        // 添加 sort 排序 sql
        if (Objects.nonNull(getPredicate().getSort()) && 0 != getPredicate().getSort().size()) {
            GenerateResult sortResult = getPredicate().getSort().generate();
            String sortSQL = sortResult.getGenerateResult();
            if (Strings.isNotBlank(sortSQL)) {
                stringBuilder.append(sortSQL);
            }
        }

        // 添加分页数据
        if (Objects.nonNull(getPredicate().getPageable()) && Objects.nonNull(getPredicate().getPageable().getPageNumber()) && Objects.nonNull(getPredicate().getPageable().getPageSize())) {
            stringBuilder.append(getPredicate().getPageable().generate().getGenerateResult());
            // 分页参数
            generateResult.setParams(getPredicate().getPageable().generate().getParams());
        }

        generateResult.setGenerateResult(stringBuilder.toString());

        return generateResult;
    }

    public MySqlQueryImpl(String tableName) {
        super(new MySqlPredicateImpl(), new JpaFromImpl(tableName));
    }

    public MySqlQueryImpl(AbstractPredicate predicate, String tableName) {
        super(predicate, new JpaFromImpl(tableName));
    }

}
