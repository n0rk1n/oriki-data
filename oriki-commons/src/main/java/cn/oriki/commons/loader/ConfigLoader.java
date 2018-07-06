package cn.oriki.commons.loader;

import cn.oriki.commons.utils.string.Strings;
import com.google.common.collect.Maps;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * ConfigLoader 配置文件加载器
 * <p>
 * 可以加载多个配置映射文件
 * <p>
 * TODO 后加载映射出现重复 key 会覆盖前面添加的映射，请小心配置
 *
 * @author oriki.wang
 */
public class ConfigLoader extends Properties {

    private static final long serialVersionUID = 2036439234035849247L;

    private String[] resourceFiles; // 加载器加载配置文件数组集合
    private Map<String, String> properties = Maps.newHashMap(); // 加载器加载所有配置映射集合

    public ConfigLoader(String... configFilePaths) {
        resourceFiles = configFilePaths; // 获取的多个配置文件
        for (String configFilePath : configFilePaths) {
            if (Strings.isNotBlank(configFilePath)) {
                this.load(configFilePath);
            }
        }

    }

    @Override
    public String getProperty(String key) {
        return properties.get(key);
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        return properties.getOrDefault(key, defaultValue);
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

        String valueTemp = properties.get(key);
        if (Objects.nonNull(valueTemp)) {
            valueTemp = valueTemp.trim();
            if (Boolean.TRUE.toString().equals(valueTemp)) {
                b = Boolean.TRUE;
            } else if (Boolean.FALSE.toString().equals(valueTemp)) {
                b = Boolean.FALSE;
            }

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
    private void load(String resourceFile) {
        try (InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream(resourceFile)) {
            if (Objects.isNull(inputStream)) {
                throw new RuntimeException("instance configLoader failed , we can't get resource");
            }

            super.load(inputStream);
            super.keySet().stream().map((e) -> (String) e).forEach((object) ->
                    // 去除前后空格
                    properties.put(object, super.getProperty(object).trim())
            );
            super.clear();
        } catch (IOException e) {
            throw new RuntimeException("failed loading config from : " + resourceFile, e);
        }
    }

    public Map<String, String> getProperties() {
        return Collections.unmodifiableMap(properties);
    }

    public String[] getResourceFiles() {
        return resourceFiles;
    }

}
