package com.laboratory.demo.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lab {
    private int id;
    private String lab_name;
    private String lab_area;
    private String lab_stun;
    private int lab_enum;
    private int lab_isone;
    private String lab_rule;
    private Date lab_time;
    private int isdelete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLab_name() {
        return lab_name;
    }

    public void setLab_name(String lab_name) {
        this.lab_name = lab_name;
    }

    public String getLab_area() {
        return lab_area;
    }

    public void setLab_area(String lab_area) {
        this.lab_area = lab_area;
    }

    public String getLab_stun() {
        return lab_stun;
    }

    public void setLab_stun(String lab_stun) {
        this.lab_stun = lab_stun;
    }

    public int getLab_enum() {
        return lab_enum;
    }

    public void setLab_enum(int lab_enum) {
        this.lab_enum = lab_enum;
    }

    public int getLab_isone() {
        return lab_isone;
    }

    public void setLab_isone(int lab_isone) {
        this.lab_isone = lab_isone;
    }

    public String getLab_rule() {
        return lab_rule;
    }

    public void setLab_rule(String lab_rule) {
        this.lab_rule = lab_rule;
    }

    public Date getLab_time() {
        return lab_time;
    }

    public void setLab_time(Date lab_time) {
        this.lab_time = lab_time;
    }

    public int getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(int isdelete) {
        this.isdelete = isdelete;
    }
}
