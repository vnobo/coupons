package com.alex.wxmp.core.Customer.beans;

import javax.persistence.*;

/**
 * rebate-alliance TBPids
 * Created by 2019-02-28
 *
 * @author Alex bob(https://github.com/vnobo)
 */
@Entity
@Table(name = "tb_pids")
public class TBPid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String type;
    private String name;
    private String pid;
    @Column(name = "p3")
    private String adZoneId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getAdZoneId() {
        return adZoneId;
    }

    public void setAdZoneId(String adZoneId) {
        this.adZoneId = adZoneId;
    }
}
