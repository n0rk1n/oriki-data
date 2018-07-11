package cn.oriki.data.generate.result;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class GenerateResult {

    private String generateResult; // 生成 sql 语句片段
    private List<Serializable> params; // 语句对应注入参数，如果没有参数，为 null 或空集合

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
        if (Objects.isNull(this.params)) { // 如果 params 为空
            this.params = params;
        } else { // 如果 params 不为空
            this.params.addAll(params); // 向后追加
        }
    }

    public void setParam(Serializable param) {
        checkParams();
        this.params.add(param); // 向后追加
    }

    // 判断 params 为空创建集合
    private void checkParams() {
        if (Objects.isNull(params)) {
            this.params = Lists.newArrayList(); // 添加集合并添加元素
        }
    }

    public GenerateResult() {
    }

    public GenerateResult(String generateResult) {
        this.generateResult = generateResult;
    }

}
