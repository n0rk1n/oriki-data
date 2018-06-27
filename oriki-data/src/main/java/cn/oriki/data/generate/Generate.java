package cn.oriki.data.generate;

import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.generate.result.GenerateResult;

public interface Generate {

    // 间隔号
    String PREFIX_SEPARATION_DOT = " `";
    String SUFFIX_SEPARATION_DOT = "` ";

    // 括号
    String LEFT_PARENTHESIS = " ( ";
    String RIGHT_PARENTHESIS = " ) ";

    // 分号
    String SEMICOLON = " ; ";

    // 占位符 ?
    String INJECTION = " ? ";

    // 逗号（英文）
    String COMMA = " , ";

    GenerateResult generate() throws GenerateException;

}
