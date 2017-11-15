package com.downpu.domain;

import javafx.beans.DefaultProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by yy187 on 2017/8/23.
 */
@Entity
@Table(name = "user_comment")

public class Comment {
    @Id
    @GeneratedValue
    private Integer id;
    @NotNull
    private String username;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date loadDate;
    @NotNull
    private Integer rid;
    public String getCommenttext() {
        return commenttext;
    }

    public void setCommenttext(String commenttext) {
        this.commenttext = commenttext;
    }

    @NotNull
    private String commenttext;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(Date loadDate) {
        this.loadDate = loadDate;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }
}
