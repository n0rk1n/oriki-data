package cn.oriki.data.generate.base.from;

/**
 * 来源定义
 *
 * @author oriki.wang
 */
public interface From {

    /**
     * 修改来源（例如表名）
     *
     * @param fromName 来源名称（可以为表名，可以为集合名）
     */
    void setFromName(String fromName);

    /**
     * 获取来源名称
     *
     * @return 来源名城
     */
    String getFromName(); // 获取来源名称

}
