package cn.oriki.data.generate.result;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class GenerateResult {

    private String generateResult; // 生成 sql 语句片段
    private List<Serializable> params; // 语句对应注入参数，如果没有参数，为空集合

    public String getGenerateResult() {
        return generateResult;
    }

    public void setGenerateResult(String generateResult) {
        this.generateResult = generateResult;
    }

    public List<Serializable> getParams() {
        return params;
    }

    public void setParams(List<Serializable> params) {
        if (Objects.isNull(this.params)) {
            // 如果 params 为空
            this.params = params;
        } else {
            // 如果 params 不为空
            // 向后追加
            this.params.addAll(params);
        }
    }

    public void setParam(Serializable param) {
        checkParams();
        // 向后追加
        this.params.add(param);
    }

    /**
     * 校验
     */
    private void checkParams() {
        if (Objects.isNull(params)) {
            // 添加集合并添加元素
            this.params = Lists.newArrayList();
        }
    }

    public GenerateResult() {
        checkParams();
    }

    public GenerateResult(String generateResult) {
        checkParams();
        this.generateResult = generateResult;
    }

}
