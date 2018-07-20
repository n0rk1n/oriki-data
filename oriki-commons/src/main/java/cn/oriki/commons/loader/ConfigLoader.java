package cn.oriki.commons.loader;

import com.google.common.collect.Maps;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * ConfigLoader 配置文件加载
 * <p>
 * 可以加载多个配置映射文件
 * <p>
 * TODO 但是后加载映射出现重复 key 会覆盖前面添加的映射，请小心配置
 *
 * @author oriki.wang
 */
public class ConfigLoader extends Properties {

    private static final long serialVersionUID = 2036439234035849247L;

    /**
     * 加载器加载所有配置映射集合，同名映射后加载会覆盖先加载配置
     */
    private Map<String, String> properties;

    public ConfigLoader(String... configFilePaths) {
        checkProperties();
        Arrays.stream(configFilePaths).forEach(this::load);
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
    public Boolean getBooleanProperty(@NonNull String key) {
        Boolean b = null;

        @NonNull String valueTemp = properties.get(key);
        if (Boolean.TRUE.toString().equals(valueTemp)) {
            b = Boolean.TRUE;
        } else if (Boolean.FALSE.toString().equals(valueTemp)) {
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

    /**
     * Properties 加载,未获取流或 IO 异常会抛出
     * <p>
     * 加载内容会被存入本类 properties 中
     *
     * @param resourceFile properties 文件位置
     */
    private void load(@NonNull String resourceFile) {
        try (InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream(resourceFile)) {
            super.load(inputStream);
            super.keySet().forEach((object) -> {
                String key = (String) object;
                properties.put(key, super.getProperty(key));
            });
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("failed loading config from : " + resourceFile, e);
        } finally {
            // 父类清空方法
            super.clear();
        }
    }

    /**
     * 获取不可变映射
     *
     * @return 读取的配置文件所有映射，不可修改
     */
    public Map<String, String> getProperties() {
        return Collections.unmodifiableMap(properties);
    }

    /**
     * 清空所有读取的配置文件映射
     */
    @Override
    public void clear() {
        this.properties.clear();
    }

    /**
     * 校验成员变量不为空
     */
    private void checkProperties() {
        if (Objects.isNull(properties)) {
            properties = Maps.newHashMap();
        }
    }

}
