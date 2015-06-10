package com.example.lijing.coolwwath.Mode;
/**
 * Created by lijing on 15/6/8.
 */
//  省份的实体类
public class Province {
    private int province_id;
    private String province_name;
    private String province_code;

    public String getProvince_name() {
        return province_name;
    }

    public int getProvince_id() {
        return province_id;
    }

    public String getProvince_code() {
        return province_code;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public void setProvince_code(String province_code) {
        this.province_code = province_code;
    }

    @Override
    public String toString() {
        return "Province{" +
                "id=" + province_id +
                ", province_name='" + province_name + '\'' +
                ", province_code='" + province_code + '\'' +
                '}';
    }
}
