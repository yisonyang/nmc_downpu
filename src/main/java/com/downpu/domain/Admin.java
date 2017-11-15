package com.downpu.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.NotNull;

/**
 * Created by yy187 on 2017/9/11.
 */
@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue
    private Integer Idadmin;
    @NotNull
    private String name;
    @NotNull
    private Integer degree;
    @NotNull
    private String password;

    public Integer getIdadmin() {
        return Idadmin;
    }

    public void setIdadmin(Integer idadmin) {
        Idadmin = idadmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
