package com.example.lijing.coolwwath.Mode;

/**
 * Created by lijing on 15/6/8.
 */
//  县的实体类
public class County {
    private int conty_id;
    private String county_name;
    private String county_code;
    private int cityu_id;

    public int getConty_id() {
        return conty_id;
    }

    public String getCounty_name() {
        return county_name;
    }

    public String getCounty_code() {
        return county_code;
    }

    public int getCityu_id() {
        return cityu_id;
    }

    public void setConty_id(int conty_id) {
        this.conty_id = conty_id;
    }

    public void setCounty_name(String county_name) {
        this.county_name = county_name;
    }

    public void setCounty_code(String county_code) {
        this.county_code = county_code;
    }

    public void setCityu_id(int cityu_id) {
        this.cityu_id = cityu_id;
    }

    @Override
    public String toString() {
        return "County{" +
                ", cityu_id=" + cityu_id +
                "id=" + conty_id +
                ", county_name='" + county_name + '\'' +
                ", county_code='" + county_code + '\'' +
                '}';
    }
}
