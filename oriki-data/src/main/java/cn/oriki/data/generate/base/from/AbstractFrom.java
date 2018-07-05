package cn.oriki.data.generate.base.from;

import cn.oriki.data.generate.Generate;

public abstract class AbstractFrom implements From, Generate {

    private String fromName; // 来源名称

    public AbstractFrom(String fromName) {
        this.fromName = fromName;
    }

    @Override
    public void from(String fromName) {
        this.fromName = fromName;
    }

    @Override
    public String getFromName() {
        return fromName;
    }

}
