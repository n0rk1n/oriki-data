package cn.oriki.data.jpa.generate.base.pageable.impl;

import cn.oriki.commons.constants.StringConstants;
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
        GenerateResult generateResult = new GenerateResult();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(LIMIT_KEY_WORD);

        Integer pageNumber = getPageNumber();
        Integer pageSize = getPageSize();

        if (pageNumber == null || pageSize == null) {
            generateResult.setGenerateResult(StringConstants.EMPTY_STRING_VALUE);
            return generateResult;
        }

        stringBuilder.append(" ? " + Generate.COMMA + " ? ");

        generateResult.setGenerateResult(stringBuilder.toString());
        generateResult.setParams(Arrays.asList((pageNumber - 1) * pageSize, pageSize));

        return generateResult;
    }

}
