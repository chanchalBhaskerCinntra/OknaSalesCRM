package com.cinntra.okana.model;

import java.util.ArrayList;

public class AttachmentResponseModel {
    public String message;
    public int status;
    public ArrayList<Datum> data;

    public class Datum{
        public ArrayList<Integer> AttachmentsIds;

        public ArrayList<Integer> getAttachmentsIds() {
            return AttachmentsIds;
        }

        public void setAttachmentsIds(ArrayList<Integer> attachmentsIds) {
            AttachmentsIds = attachmentsIds;
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
