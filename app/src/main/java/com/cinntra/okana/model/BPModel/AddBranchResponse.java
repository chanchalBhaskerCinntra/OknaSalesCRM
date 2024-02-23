package com.cinntra.okana.model.BPModel;

import java.util.ArrayList;

public class AddBranchResponse {
    public String message;
    public int status;
    public ArrayList<Data> data;

    public class Data{
        public int id;
        public int RowNum;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRowNum() {
            return RowNum;
        }

        public void setRowNum(int rowNum) {
            RowNum = rowNum;
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }
}
