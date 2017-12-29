package com.mhm.xq.entity.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class NewsColumn {
    @Id
    private Long id;
    private String name;
    @NotNull
    private int type;
    @NotNull
    private long ct;

    @Generated(hash = 1170115090)
    public NewsColumn(Long id, String name, int type, long ct) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.ct = ct;
    }

    @Generated(hash = 379899747)
    public NewsColumn() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getCt() {
        return this.ct;
    }

    public void setCt(long ct) {
        this.ct = ct;
    }

}
