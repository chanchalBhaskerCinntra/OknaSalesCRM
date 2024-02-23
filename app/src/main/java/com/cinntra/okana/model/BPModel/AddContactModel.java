package com.cinntra.okana.model.BPModel;

import java.util.ArrayList;

public class AddContactModel {

    public String message;
    public int status;
    public ArrayList<Datum> data;

    public class Datum{
        public int id;
        public int InternalCode;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getInternalCode() {
            return InternalCode;
        }

        public void setInternalCode(int internalCode) {
            InternalCode = internalCode;
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

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }
}
