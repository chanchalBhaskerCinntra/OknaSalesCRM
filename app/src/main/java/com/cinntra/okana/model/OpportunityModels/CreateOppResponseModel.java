package com.cinntra.okana.model.OpportunityModels;

import java.util.ArrayList;

public class CreateOppResponseModel {
    public String message;
    public int status;
    public ArrayList<Datum> data;
    public class Datum{
        public int Opp_Id;
        public int SequentialNo;

        public int getOpp_Id() {
            return Opp_Id;
        }

        public void setOpp_Id(int opp_Id) {
            Opp_Id = opp_Id;
        }

        public int getSequentialNo() {
            return SequentialNo;
        }

        public void setSequentialNo(int sequentialNo) {
            SequentialNo = sequentialNo;
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
