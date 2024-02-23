package com.cinntra.okana.model.BPModel;

import java.util.ArrayList;

public class AtatchmentListModel {
    public String message;
    public int status;
    public ArrayList<Data> data = new ArrayList<>();

    public class Data{

        public int id;
        
        public String File;
        
        public String CreateDate;
        
        public String CreateTime;
        
        public String UpdateDate;
        
        public String UpdateTime;
        
        public int CustId;
        
        public String Size;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFile() {
            return File;
        }

        public void setFile(String file) {
            File = file;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String createDate) {
            CreateDate = createDate;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String createTime) {
            CreateTime = createTime;
        }

        public String getUpdateDate() {
            return UpdateDate;
        }

        public void setUpdateDate(String updateDate) {
            UpdateDate = updateDate;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String updateTime) {
            UpdateTime = updateTime;
        }

        public int getCustId() {
            return CustId;
        }

        public void setCustId(int custId) {
            CustId = custId;
        }

        public String getSize() {
            return Size;
        }

        public void setSize(String size) {
            Size = size;
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
