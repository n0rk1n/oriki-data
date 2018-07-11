package cn.oriki.data.jpa.repository.impl;

import cn.oriki.data.generate.curd.delete.result.DeleteResult;
import cn.oriki.data.generate.curd.save.result.SaveResult;
import cn.oriki.data.generate.curd.update.result.UpdateResult;
import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.jpa.entity.Children;
import cn.oriki.data.jpa.generate.base.pageable.impl.MySqlPageableImpl;
import cn.oriki.data.jpa.generate.base.predicate.JpaPredictImpl;
import cn.oriki.data.jpa.generate.base.where.JpaWhereImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class MySqlRepositoryImplTest {

    private MySqlRepositoryImpl<Children, Long> repository;

    @Before
    public void before() {
//        ConfigLoader configLoader = new ConfigLoader("oriki-commons-datasource.properties");
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setDriverClassName(configLoader.getProperty("oriki.csf-mysql.mysql.driverClass"));
//        dataSource.setUrl(configLoader.getProperty("oriki.csf-mysql.mysql.url"));
//        dataSource.setUsername(configLoader.getProperty("oriki.csf-mysql.mysql.userName"));
//        dataSource.setPassword(configLoader.getProperty("oriki.csf-mysql.mysql.password"));

        repository = new MySqlRepositoryImpl<>(Children.class, Long.class);
//        repository.setJdbcTemplate(dataSource);

//        configLoader.clear();
    }

    @Test
    public void save() throws IllegalAccessException, GenerateException {
        Children children = new Children();
        children.setName("zhangsan");
        children.setAge(18);
        children.setCreateTime(new Date());

        SaveResult<Children, Long> saveResult = this.repository.save(children);

        System.out.println("影响行数：" + saveResult.getNumber());
        Iterable<Children> entities = saveResult.getEntities();
        entities.forEach((entity) ->
                System.out.println("插入数据的返回id：" + entity.getId())
        );
    }

    @Test
    public void deleteById() throws GenerateException {
        DeleteResult delete = this.repository.deleteById(68L);
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

    @Test
    public void queryById() throws GenerateException {
        Children children = this.repository.queryById(64L);
        showChildren(children);
    }

    @Test
    public void queryByIds() throws GenerateException {
        Collection<Children> children = this.repository.queryByIds(Collections.singletonList(64L));

        for (Children child : children) {
            showChildren(child);
        }
    }

    @Test
    public void queryAll() throws GenerateException {
        Collection<Children> children = this.repository.queryAll();

        for (Children child : children) {
            showChildren(child);
        }
    }

    @Test
    public void exists() throws GenerateException {
        boolean b = this.repository.exists(64L);
        Assert.assertTrue(b);
    }

    @Test
    public void update() throws IllegalAccessException, GenerateException {
        Children children = new Children();
        children.setId(1L);
        children.setName("zhangsan");
        children.setAge(18);
        children.setCreateTime(new Date());
        UpdateResult updateResult = this.repository.update(children);

        System.out.println("是否为更新操作：" + updateResult.isUpdate());
        System.out.println("影响行数：" + updateResult.getNumber());
    }

    @Test
    public void query() throws GenerateException {
        JpaPredictImpl predicate = new JpaPredictImpl(new MySqlPageableImpl(null, null));
        predicate.orderDesc("id");

        Iterable<Children> query = this.repository.query(predicate);

        for (Children children : query) {
            showChildren(children);
        }
    }

    @Test
    public void count() throws GenerateException {
        Children children = new Children();
        children.setName("zhangsan");
        children.setAge(18);

        Long count = this.repository.count(children);
        System.out.println("数据库总数：" + count);
    }

    @Test
    public void count1() throws GenerateException {
        JpaWhereImpl jpaWhere = new JpaWhereImpl();
        jpaWhere.in("id", Arrays.asList(1L, 2L, 3L));
        Long count = this.repository.count(jpaWhere);
        System.out.println("数据库总数：" + count);
    }

    @Test
    public void count2() throws GenerateException {
        Long count = this.repository.count(new JpaPredictImpl(new MySqlPageableImpl(null, null)));
        System.out.println("数据库总数：" + count);
    }

    @Test
    public void queryAll1() throws GenerateException {
        JpaWhereImpl where = new JpaWhereImpl();
        where.equals("name", "zhangsan");

        Collection<Children> children = this.repository.queryAll(where);
        for (Children child : children) {
            showChildren(child);
        }
    }

    private void showChildren(Children children) {
        if (Objects.nonNull(children)) {
            System.out.println("id:" + children.getId());
            System.out.println("name:" + children.getName());
            System.out.println("age:" + children.getAge());
            System.out.println("create_time:" + children.getCreateTime());
            System.out.println("update_time:" + children.getUpdateTime());

            System.out.println(" ------");
        }
    }

}