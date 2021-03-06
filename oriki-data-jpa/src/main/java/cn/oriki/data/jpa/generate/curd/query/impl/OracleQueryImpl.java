package cn.oriki.data.jpa.generate.curd.query.impl;

import cn.oriki.data.generate.base.predicate.AbstractPredicate;
import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.generate.result.GenerateResult;
import cn.oriki.data.jpa.generate.base.from.JpaFromImpl;
import cn.oriki.data.jpa.generate.base.pageable.impl.OraclePageableImpl;
import cn.oriki.data.jpa.generate.base.predicate.JpaPredictImpl;
import cn.oriki.data.jpa.generate.curd.query.AbstractJpaQuery;

import java.util.Objects;

public class OracleQueryImpl extends AbstractJpaQuery {

    public OracleQueryImpl(AbstractPredicate predicate, String tableName) {
        super(predicate, new JpaFromImpl(tableName));
    }

    public OracleQueryImpl(String tableName) {
        super(new JpaPredictImpl(new OraclePageableImpl(null, null)), new JpaFromImpl(tableName));
    }

    @Override
    public GenerateResult generate() throws GenerateException {
        // TODO 如果需要进行分页操作，需要在 select 中添加 rownum
        GenerateResult generateResult = new GenerateResult();
        StringBuilder stringBuilder = new StringBuilder();
        if (Objects.nonNull(getPredicate().getPageable())) {
            query("ROWNUM", "rn");
        }

        setSelectImpl(stringBuilder);
        setFromImpl(stringBuilder);

        GenerateResult whereResult = getPredicate().getWhere().generate();
        String whereSQL = whereResult.getGenerateResult();
        stringBuilder.append(whereSQL); // where ...
        generateResult.setParams(whereResult.getParams());

        // 添加 sort 排序 sql
        setSortImpl(stringBuilder);

        if (Objects.nonNull(getPredicate().getPageable()) && Objects.nonNull(getPredicate().getPageable().getPageNumber()) && Objects.nonNull(getPredicate().getPageable().getPageSize())) {
            GenerateResult pageResult = getPredicate().getPageable().generate();
            String[] split = pageResult.getGenerateResult().split(OraclePageableImpl.SEPARATOR_KEY_WORD);

            stringBuilder.insert(0, split[0]);
            stringBuilder.append(split[1]);

            generateResult.setParams(pageResult.getParams());
        }

        generateResult.setGenerateResult(stringBuilder.toString());

        return generateResult;
    }

}
