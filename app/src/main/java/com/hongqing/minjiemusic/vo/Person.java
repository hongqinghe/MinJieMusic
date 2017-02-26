package com.hongqing.minjiemusic.vo;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by 贺红清 on 2017/2/27.
 */
@Table(name = "Person")

public class Person {

    @Column(name = "age")
    private int age ;
    @Column(name = "id",isId = true)
    private int id;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
