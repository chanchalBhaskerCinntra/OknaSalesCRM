package com.cinntra.okana.databse;


public class DB_SalesEmployee {
    public static final String TABLE_NAME = "SalesEmployees";

    public static final String COLUMN_SalesEmployeeCode = "_SalesEmployeeCode";
    public static final String COLUMN_SalesEmployeeName = "_SalesEmployeeName";



    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME       + "("
                    + COLUMN_SalesEmployeeCode + " TEXT,"
                    + COLUMN_SalesEmployeeName + " TEXT,"
                    + ")";

    public DB_SalesEmployee() {
    }


    String SalesEmployeeCode;
    String SalesEmployeeName;

    public DB_SalesEmployee(String salesEmployeeCode, String salesEmployeeName) {
        SalesEmployeeCode = salesEmployeeCode;
        SalesEmployeeName = salesEmployeeName;
    }

    public String getSalesEmployeeCode() {
        return SalesEmployeeCode;
    }

    public void setSalesEmployeeCode(String salesEmployeeCode) {
        SalesEmployeeCode = salesEmployeeCode;
    }

    public String getSalesEmployeeName() {
        return SalesEmployeeName;
    }

    public void setSalesEmployeeName(String salesEmployeeName) {
        SalesEmployeeName = salesEmployeeName;
    }
}
