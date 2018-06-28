package cn.oriki.data.jpa.repository.impl;

import cn.oriki.commons.loader.ConfigLoader;
import cn.oriki.data.generate.curd.delete.result.DeleteResult;
import cn.oriki.data.generate.curd.save.result.SaveResult;
import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.jpa.entity.Children;
import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class MySQLRepositoryImplTest {

    private MySQLRepositoryImpl<Children, Long> repository;

    @Before
    public void before() {
        ConfigLoader configLoader = new ConfigLoader("oriki-commons-datasource.properties");
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(configLoader.getProperty("oriki.csf-mysql.mysql.driverClass"));
        dataSource.setUrl(configLoader.getProperty("oriki.csf-mysql.mysql.url"));
        dataSource.setUsername(configLoader.getProperty("oriki.csf-mysql.mysql.userName"));
        dataSource.setPassword(configLoader.getProperty("oriki.csf-mysql.mysql.password"));

        repository = new MySQLRepositoryImpl<>(Children.class, Long.class);
        repository.setJdbcTemplate(dataSource);

        configLoader.clear();
    }

    @Test
    public void save() throws IllegalAccessException, GenerateException {
        Children children = new Children();
        children.setName("zhangsan");
        children.setAge(18);
        children.setCreateTime(new Date());

        SaveResult<Children, Long> saveResult = this.repository.save(children);

        System.out.println("影响行数：" + saveResult.getNumber());
        Iterable<Children> entities = saveResult.getEntitys();
        entities.forEach((entity) -> {
            System.out.println("插入数据的返回id：" + entity.getId());
        });
    }

    @Test
    public void deleteById() throws GenerateException {
        DeleteResult delete = this.repository.deleteById(37L);
        System.out.println("删除的元素个数：" + delete.getNumber());
    }

    @Test
    public void delete() throws GenerateException {
        Children children = new Children();
        children.setName("zhangsan");
        children.setAge(18);
//        children.setCreateTime(new Date());

        DeleteResult delete = this.repository.delete(children);
        System.out.println("删除的元素个数：" + delete.getNumber());
    }

    @Test
    public void deleteAll() throws GenerateException {
        DeleteResult delete = this.repository.deleteAll();
        System.out.println("删除的元素个数：" + delete.getNumber());
    }

}