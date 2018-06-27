package cn.oriki.data.generate.curd.from;

import cn.oriki.data.generate.Generate;

public interface From extends Generate {

    /**
     * 修改来源（例如表名）
     *
     * @param fromName 来源名称（可以为表名，可以为集合名）
     */
    void from(String fromName);

    String getFromName(); // 获取来源名称

}
