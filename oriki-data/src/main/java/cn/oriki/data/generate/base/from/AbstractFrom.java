package cn.oriki.data.generate.base.from;

import cn.oriki.commons.utils.string.Strings;
import cn.oriki.data.generate.Generate;

public abstract class AbstractFrom implements From, Generate {

    private String fromName; // 来源名称

    public AbstractFrom(String fromName) {
        setFromName(fromName);
    }

    @Override
    public void from(String fromName) {
        setFromName(fromName);
    }

    // 设置 fromName
    private void setFromName(String fromName) {
        check(fromName);
        this.fromName = fromName;
    }

    // 检查 fromName 是否合法
    private void check(String fromName) {
        if (Strings.isBlank(fromName)) {
            throw new IllegalArgumentException("can't set fromName null");
        }
    }

    @Override
    public String getFromName() {
        return fromName;
    }

}
