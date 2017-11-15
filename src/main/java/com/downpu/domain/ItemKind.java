package com.downpu.domain;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by yy187 on 2017/9/22.
 */
@Entity
@Transactional
@Table(name = "item_kind")
/**
 * 这个是资源种类的对应，现在是在页面上写的，后期可在数据可添加
 */
public class ItemKind {
    @Id
    @GeneratedValue
    private Integer id;
    @NotNull
    private String kind;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
