package cn.oriki.data.generate.curd.save;

import cn.oriki.data.generate.curd.from.From;

public class AbstractSave {

    private From from;

    public AbstractSave(From from) {
        this.from = from;
    }

    public String getFromName() {
        return this.from.getFromName();
    }

    public From getFrom() {
        return from;
    }

}
