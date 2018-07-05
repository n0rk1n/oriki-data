package cn.oriki.data.jpa.generate.base.pageable.impl;

import cn.oriki.data.generate.Generate;
import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.generate.result.GenerateResult;
import cn.oriki.data.jpa.generate.base.pageable.AbstractJpaPageable;

import java.util.Arrays;

public class MySQLPageableImpl extends AbstractJpaPageable {

    private final static String LIMIT_KEY_WORD = " LIMIT ";

    public MySQLPageableImpl(Integer pageNumber, Integer pageSize) {
        super(pageNumber, pageSize);
    }

    @Override
    public GenerateResult generate() throws GenerateException {
        // GOAL:
        // limit offset , limit
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(LIMIT_KEY_WORD);

        int pageNumber = getPageNumber();
        int pageSize = getPageSize();

        stringBuilder.append(" ? " + Generate.COMMA + " ? ");

        GenerateResult generateResult = new GenerateResult();
        generateResult.setGenerateResult(stringBuilder.toString());
        generateResult.setParams(Arrays.asList((pageNumber - 1) * pageSize, pageSize));

        return generateResult;
    }

}
