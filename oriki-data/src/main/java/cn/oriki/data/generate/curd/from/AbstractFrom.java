package cn.oriki.data.generate.curd.from;

public abstract class AbstractFrom implements From {

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
