package cn.oriki.commons.loader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class ConfigLoaderTest {

    private ConfigLoader configLoader;

    @Before
    public void before() {
        configLoader = new ConfigLoader("test.properties", "test2.properties");
    }

    @Test
    public void getBooleanProperty() {
        Boolean b = configLoader.getBooleanProperty("key3");
        Assert.assertTrue(b);
    }

    @Test
    public void getBooleanProperty1() {
        Boolean b = configLoader.getBooleanProperty("key3");
        Assert.assertTrue(b);

        Boolean b2 = configLoader.getBooleanProperty("keyx", false);
        Assert.assertFalse(b2);
    }

    @Test
    public void getProperties() {
        String s = configLoader.getProperty("key1");

        Assert.assertEquals("12", s);
    }

    @Test
    public void getResourceFiles() {
        String s = configLoader.getProperty("keyn", "abcd");
        Assert.assertEquals("abcd", s);
    }

    @Test
    public void getAllPropertie() {
        Map<String, String> properties = configLoader.getProperties();
        properties.forEach((key, value) -> {
            System.out.println("获取的键：" + key);
            System.out.println("获取的值：" + value);
        });
    }

    /*@Test
    public void getPath() {
        String[] resourceFiles = configLoader.getResourceFiles();
        for (String resourceFile : resourceFiles) {
            System.out.println(resourceFile);
        }
    }*/

}