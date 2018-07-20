package cn.oriki.data.generate.base.from;

import cn.oriki.commons.utils.string.Strings;
import cn.oriki.data.generate.Generate;
import lombok.Getter;
import lombok.NonNull;

/**
 * From 接口抽象类
 *
 * @author oriki.wang
 */
public abstract class AbstractFrom implements From, Generate {

    /**
     * 来源名称
     */
    @Getter
    private String fromName;

    public AbstractFrom(@NonNull String fromName) {
        setFromName(fromName);
    }

    @Override
    public void from(@NonNull String fromName) {
        setFromName(fromName);
    }

    private void setFromName(@NonNull String fromName) {
        if (Strings.isBlank(fromName)) {
            throw new IllegalArgumentException("fromName can't be null");
        }
        this.fromName = fromName;
    }

}
