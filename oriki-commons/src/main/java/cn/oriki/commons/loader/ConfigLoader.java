package cn.oriki.commons.loader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * ConfigLoader 配置文件加载器
 *
 * @author oriki.wang
 */
public class ConfigLoader extends Properties {

    private static final long serialVersionUID = 2036439234035849247L;

    private String resourceFile;

    public ConfigLoader(String configFilePath) {
        this.resourceFile = configFilePath;
        this.load();
    }

    /**
     * 通过 key 获取值转化为 Boolean 类型，方法内去掉前后空格
     * <p>
     * 如果该映射不存在，则返回 null
     * <p>
     * 如果映射不匹配 Boolean 类型，返回 null
     *
     * @param key 获取映射的 key
     * @return 根据键获取的值的 Boolean 类型，如果不存在返回 null
     */
    public Boolean getBooleanProperty(String key) {
        Boolean b = null;

        String _value = this.getProperty(key);
        if (Objects.nonNull(_value)) {
            _value = _value.trim();
            if (Boolean.TRUE.toString().equals(_value))
                b = Boolean.TRUE;
            else if (Boolean.FALSE.toString().equals(_value))
                b = Boolean.FALSE;
        }
        return b;
    }

    /**
     * 通过 key 获取值转化为 Boolean 类型
     * <p>
     * 如果不存在或转化失败使用默认值
     *
     * @param key            获取映射的 key
     * @param defaultBoolean 默认值
     * @return 映射键对应的值的 Boolean 类型，不存在对应值时返回的默认值
     */
    public boolean getBooleanProperty(String key, boolean defaultBoolean) {
        Boolean b = this.getBooleanProperty(key);
        return Objects.nonNull(b) ? b : defaultBoolean;
    }

    // Properties 加载
    // 未获取流或 IO 异常会抛出
    private void load() {
        try (InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream(this.resourceFile)) {
            if (Objects.isNull(inputStream))
                throw new RuntimeException("instance classLoader failed , we can't get resource");

            super.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("failed loading config from : " + resourceFile, e);
        }
    }

    public String getResourceFile() {
        return resourceFile;
    }

}
