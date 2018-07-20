package cn.oriki.data.jpa.generate.curd.query.impl;

import cn.oriki.commons.utils.collection.Collections;
import cn.oriki.data.generate.base.predicate.AbstractPredicate;
import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.generate.result.GenerateResult;
import cn.oriki.data.jpa.generate.base.from.JpaFromImpl;
import cn.oriki.data.jpa.generate.base.pageable.impl.MySqlPageableImpl;
import cn.oriki.data.jpa.generate.base.predicate.JpaPredictImpl;
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

        setSelectImpl(stringBuilder);
        setFromImpl(stringBuilder);

        GenerateResult whereResult = getWhere().generate();
        String whereSQL = whereResult.getGenerateResult();
        // where ...
        stringBuilder.append(whereSQL);
        generateResult.setParams(whereResult.getParams());

        // 添加 sort 排序 sql
        setSortImpl(stringBuilder);

        // 添加分页数据
        if (Objects.nonNull(getPageable()) && Objects.nonNull(getPageNumber()) && Objects.nonNull(getPageSize())) {
            GenerateResult pageResult = getPredicate().getPageable().generate();
            if (Collections.isNotNullAndHasElements(pageResult.getParams())) {
                stringBuilder.append(pageResult.getGenerateResult());
                // 分页参数
                generateResult.setParams(pageResult.getParams());
            }
        }

        generateResult.setGenerateResult(stringBuilder.toString());

        return generateResult;
    }

    public MySqlQueryImpl(String tableName) {
        super(new JpaPredictImpl(new MySqlPageableImpl(null, null)), new JpaFromImpl(tableName));
    }

    public MySqlQueryImpl(AbstractPredicate predicate, String tableName) {
        super(predicate, new JpaFromImpl(tableName));
    }

}
