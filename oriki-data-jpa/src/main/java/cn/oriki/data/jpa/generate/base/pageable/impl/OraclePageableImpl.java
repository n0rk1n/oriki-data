package cn.oriki.data.jpa.generate.base.pageable.impl;

import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.generate.result.GenerateResult;
import cn.oriki.data.jpa.generate.base.pageable.AbstractJpaPageable;

import java.util.Arrays;

/**
 * Oracle 的分页 sql 生成，需要进行前后拼接
 *
 * @author oriki.wang
 */
public class OraclePageableImpl extends AbstractJpaPageable {

    public final static String SEPARATOR_KEY_WORD = "@separator@";

    public OraclePageableImpl(Integer pageNumber, Integer pageSize) {
        super(pageNumber, pageSize);
    }

    @Override
    public GenerateResult generate() throws GenerateException {
        // GOAL:
        //      select a1.* from (select id,rownum rn from nlp_news_feed) a1 where rn between 3 and 5;
        GenerateResult generateResult = new GenerateResult();
        StringBuilder stringBuilder = new StringBuilder();

        if (getPageNumber() == null || getPageSize() == null) {
            stringBuilder.append(" " + SEPARATOR_KEY_WORD + " ");
        } else {
            stringBuilder.append(" SELECT a1.* FROM ( ");
            stringBuilder.append(SEPARATOR_KEY_WORD);
            stringBuilder.append(") a1 WHERE rn BETWEEN ? AND ?");

            Integer pageNumber = getPageNumber();
            Integer pageSize = getPageSize();
            generateResult.setParams(Arrays.asList((pageNumber - 1) * pageSize, (pageNumber - 1) * pageSize + pageSize));
        }
        generateResult.setGenerateResult(stringBuilder.toString());

        return generateResult;
    }

}
