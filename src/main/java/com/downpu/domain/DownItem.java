package com.downpu.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by yy187 on 2017/8/21.
 */
@Entity
@Table(name = "item")
public class DownItem {
    @Id
    @GeneratedValue
    private Integer idloaditem;
    @NotNull(message = "必须有名字")
    private String name;
    @NotNull(message = "必须有介绍")
    private String intro;
    private Date uploaddate;
    private String uploader;
    private String url;
    private String kind;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public DownItem(){

    }
    public Integer getIdloaditem() {
        return idloaditem;
    }

    public void setIdloaditem(Integer idloaditem) {
        this.idloaditem = idloaditem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Date getUploaddate() {
        return uploaddate;
    }

    public void setUploaddate(Date uploaddate) {
        this.uploaddate = uploaddate;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }
}
