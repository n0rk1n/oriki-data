package cn.oriki.datasource.jpa.manager;

import cn.oriki.commons.loader.ConfigLoader;
import cn.oriki.commons.utils.string.Strings;
import cn.oriki.datasource.jpa.container.Container;
import cn.oriki.datasource.jpa.init.DataSourceInitializer;
import cn.oriki.datasource.jpa.init.info.DataSourceInfo;
import lombok.NonNull;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 数据源管理器，单例。
 * <p>
 * 获取方式使用 getInstance 获取，对外提供 chooseDataSource 方法获取数据源
 *
 * @author oriki.wang
 */
public class DataSourceManager<T extends DataSource> {

    /**
     * 数据库容器
     */
    private static Container container;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * singleton
     */
    private static DataSourceManager dataSourceManager;

    private DataSourceManager() {
    }

    public static DataSourceManager getInstance() {
        if (Objects.isNull(dataSourceManager)) {
            synchronized (DataSourceManager.class) {
                if (Objects.isNull(dataSourceManager)) {
                    dataSourceManager = new DataSourceManager();
                    dataSourceManager.init();
                }
            }
        }
        return dataSourceManager;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * 顶配配置预设
     */
    private static final String DEFAULT_KEY_WORD = "default";

    private static final String SUFFIX_DRIVER_CLASS_KEY_WORD = ".driver-class";
    private static final String SUFFIX_URL_KEY_WORD = ".url";
    private static final String SUFFIX_USER_NAME_KEY_WORD = ".userName";
    private static final String SUFFIX_PASSWORD_KEY_WORD = ".password";

    /**
     * 默认配置映射键
     */
    private static final String DEFAULT_DRIVER_CLASS_KEY = DEFAULT_KEY_WORD + SUFFIX_DRIVER_CLASS_KEY_WORD;
    private static final String DEFAULT_URL_KEY = DEFAULT_KEY_WORD + SUFFIX_URL_KEY_WORD;
    private static final String DEFAULT_USERNAME_KEY = DEFAULT_KEY_WORD + SUFFIX_USER_NAME_KEY_WORD;
    private static final String DEFAULT_PASSWORD_KEY = DEFAULT_KEY_WORD + SUFFIX_PASSWORD_KEY_WORD;


    /**
     * 获取 source 对应 DataSource ，如果不存在则提供默认数据源
     *
     * @param sourceKey 数据源对应键
     * @return 数据源
     */
    public DataSource chooseDataSource(String sourceKey) {
        return container.getContains().getOrDefault(sourceKey, container.getContains().get(DEFAULT_KEY_WORD));
    }

    private void init() {
        ConfigLoader configLoader = new ConfigLoader("oriki-datasource.properties");

        // 获取激活环境
        String profile = configLoader.getProperty("oriki.datasource.profile.active");

        if (Strings.isNotBlank(profile)) {
            ConfigLoader configLoader2 = new ConfigLoader("oriki-datasource-" + profile + ".properties");

            Map<String, String> properties = configLoader2.getProperties();
            List<String> sourceKeys = properties.keySet().stream().filter((e) -> e.contains(SUFFIX_DRIVER_CLASS_KEY_WORD)).map((e) ->
                    e.replace(SUFFIX_DRIVER_CLASS_KEY_WORD, "")
            ).collect(Collectors.toList());

            // 校验是否有重复项
            checkDistinct(sourceKeys);

            sourceKeys.forEach((sourceKey) -> {
                // 拼接
                String driverClass = configLoader2.getProperty(sourceKey + SUFFIX_DRIVER_CLASS_KEY_WORD);
                String url = configLoader2.getProperty(sourceKey + SUFFIX_URL_KEY_WORD);
                String username = configLoader2.getProperty(sourceKey + SUFFIX_USER_NAME_KEY_WORD);
                String password = configLoader2.getProperty(sourceKey + SUFFIX_PASSWORD_KEY_WORD);
                DataSourceInfo dataSourceInfo = DataSourceInitializer.createDataSourceInfo(driverClass, url, username, password);
                createAndSetDataSource(sourceKey, dataSourceInfo);
            });

            configLoader2.clear();
        }

        // 判断是否已有默认配置，如果没有，获取 oriki-datasource.properties 的默认数据源
        if (Objects.isNull(container.getContains().get(DEFAULT_KEY_WORD))) {
            // 初始化默认配置信息
            String defaultDriverClass = configLoader.getProperty(DEFAULT_DRIVER_CLASS_KEY);
            String defaultUrl = configLoader.getProperty(DEFAULT_URL_KEY);
            String defaultUsername = configLoader.getProperty(DEFAULT_USERNAME_KEY);
            String defaultPassword = configLoader.getProperty(DEFAULT_PASSWORD_KEY);

            DataSourceInfo defaultDataSource = DataSourceInitializer.createDataSourceInfo(defaultDriverClass, defaultUrl, defaultUsername, defaultPassword);
            createAndSetDataSource(DEFAULT_KEY_WORD, defaultDataSource);
        }
    }

    /**
     * 创建和设置 DataSource TODO 使用 Druid 数据库（方便统计和效率考虑
     *
     * @param key            映射键
     * @param dataSourceInfo 数据源配置信息
     */
    private static void createAndSetDataSource(String key, DataSourceInfo dataSourceInfo) {
        checkContainer();

        DataSource dataSource = DataSourceInitializer.createDataSource(dataSourceInfo);
        container.setContain(key, dataSource);
    }

    /**
     * 校验
     */
    private static void checkContainer() {
        if (Objects.isNull(container)) {
            container = new Container();
        }
    }

    /**
     * 保证 sourceKeys 没有重复
     *
     * @param list 保证 List 中没有重复值
     */
    private static void checkDistinct(@NonNull List<String> list) {
        HashSet<String> strings = new HashSet<>(list);
        if (strings.size() != list.size()) {
            list.remove(strings);
            throw new RuntimeException("we find the same sourceKey in properties : " + list.toString() + ", please check");
        }
    }

}
