package cn.oriki.data.jpa.entity;

import cn.oriki.data.annotation.PrimaryKey;
import cn.oriki.data.annotation.Table;

@Table(name = "t_children", sourceKey = "csf")
public class Children extends Parent {

    @PrimaryKey
    private Long id;

    // @Column(name = "abcd")
    private String name;
    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
