package com.example.project_1.dataModels.UI;

import java.sql.Date;

public class Leave {
    private String type;
    private Date date;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
