package com.cinntra.okana.model.BPModel;

import java.util.ArrayList;

public class BranchContactPersonResponse {
    public String message;
    public int status;
    public ArrayList<Data> data = new ArrayList<>();

    public class Data  {
        public String id;
        public String AddressType;
        public String U_Contperson;
        public String U_CONTNO;
        public String Email;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAddressType() {
            return AddressType;
        }

        public void setAddressType(String addressType) {
            AddressType = addressType;
        }

        public String getU_Contperson() {
            return U_Contperson;
        }

        public void setU_Contperson(String u_Contperson) {
            U_Contperson = u_Contperson;
        }

        public String getU_CONTNO() {
            return U_CONTNO;
        }

        public void setU_CONTNO(String u_CONTNO) {
            U_CONTNO = u_CONTNO;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
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
