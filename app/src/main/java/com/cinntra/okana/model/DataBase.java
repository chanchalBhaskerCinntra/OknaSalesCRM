package com.cinntra.okana.model;
import java.io.Serializable;

public class DataBase implements Serializable {


    String Name;
    String CompanyName;
    String Version;

    public DataBase(String name,String CompanyName,String Version)
          {
        this.Name        = name;
        this.CompanyName = CompanyName;
        this.Version     = Version;

          }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }
}
