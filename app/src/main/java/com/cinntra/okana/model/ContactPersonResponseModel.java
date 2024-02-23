package com.cinntra.okana.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactPersonResponseModel implements Serializable {
    public String message;
    public int status;
    public ArrayList<Datum> data;

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

    public class Datum implements Serializable{
        public int id;
        public String CardCode;
        public String CardName;
        public String Industry;
        public String IndustryName;
        public String PAN;
        public String Notes;
        public String Cellular;
        public String CardType;
        public String Website;
        public String EmailAddress;
        public String secondEmail;
        public String createdBy;
        public String managerName;
        public String Phone1;
        public String CountryCode;
        public String CreditLimit;
        public String ContactPerson;
        public String U_LEADID;
        public String U_LEADNM;
        public String UseBillToAddrToDetermineTax;
        public String U_PARENTACC;
        public String U_CONTOWNR;
        public  ArrayList<Type> U_TYPE;
        public String U_ANLRVN;
        public String U_CURBAL;
        public String U_INVNO;
        public String U_LAT;
        public String U_LONG;
        public String U_SOURCE;
        public String U_EMIRATESID;
        public String U_VATNUMBER;
        private String CreateDate;
        private String CreateTime;
        private String UpdateDate;
        private String UpdateTime;
        private String Currency;
        private String GroupCode;

        private ArrayList<PayMentTerm> PayTermsGrpCode;
        private ArrayList<SalesPersonCodes> SalesPersonCode;
        private List<ContactEmployeesModel> ContactEmployees = new ArrayList<>();
        private List<BPAddress> BPAddresses = new ArrayList<>();

        public class Type implements Serializable{
            public int id;
            public String Type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getType() {
                return Type;
            }

            public void setType(String type) {
                Type = type;
            }
        }

        public class SalesPersonCodes implements Serializable{
            public int id;
            public String SalesEmployeeCode;
            public String SalesEmployeeName;
            public String EmployeeID;
            public String userName;
            public String password;
            public String firstName;
            public String lastName;
            public String Email;
            public String Mobile;
            public String CountryCode;
            public Role role;


            public class Role {
                public String Name;
                public String DiscountPercentage;
                public String Subdepartment;
                public String id;

                public String getName() {
                    return Name;
                }

                public void setName(String name) {
                    Name = name;
                }

                public String getDiscountPercentage() {
                    return DiscountPercentage;
                }

                public void setDiscountPercentage(String discountPercentage) {
                    DiscountPercentage = discountPercentage;
                }

                public String getSubdepartment() {
                    return Subdepartment;
                }

                public void setSubdepartment(String subdepartment) {
                    Subdepartment = subdepartment;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }
            }


            public Role getRole() {
                return role;
            }

            public void setRole(Role role) {
                this.role = role;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public String getEmployeeID() {
                return EmployeeID;
            }

            public void setEmployeeID(String employeeID) {
                EmployeeID = employeeID;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }

            public String getLastName() {
                return lastName;
            }

            public void setLastName(String lastName) {
                this.lastName = lastName;
            }

            public String getEmail() {
                return Email;
            }

            public void setEmail(String email) {
                Email = email;
            }

            public String getMobile() {
                return Mobile;
            }

            public void setMobile(String mobile) {
                Mobile = mobile;
            }

            public String getCountryCode() {
                return CountryCode;
            }

            public void setCountryCode(String countryCode) {
                CountryCode = countryCode;
            }
        }


        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getManagerName() {
            return managerName;
        }

        public void setManagerName(String managerName) {
            this.managerName = managerName;
        }

        public String getSecondEmail() {
            return secondEmail;
        }

        public void setSecondEmail(String secondEmail) {
            this.secondEmail = secondEmail;
        }

        public String getGroupCode() {
            return GroupCode;
        }

        public void setGroupCode(String groupCode) {
            GroupCode = groupCode;
        }

        public String getCurrency() {
            return Currency;
        }

        public void setCurrency(String currency) {
            Currency = currency;
        }

        public String getUseBillToAddrToDetermineTax() {
            return UseBillToAddrToDetermineTax;
        }

        public void setUseBillToAddrToDetermineTax(String useBillToAddrToDetermineTax) {
            UseBillToAddrToDetermineTax = useBillToAddrToDetermineTax;
        }

        public String getCellular() {
            return Cellular;
        }

        public void setCellular(String cellular) {
            Cellular = cellular;
        }

        public String getNotes() {
            return Notes;
        }

        public void setNotes(String notes) {
            Notes = notes;
        }

        public String getIndustryName() {
            return IndustryName;
        }

        public void setIndustryName(String industryName) {
            IndustryName = industryName;
        }

        public String getPAN() {
            return PAN;
        }

        public void setPAN(String PAN) {
            this.PAN = PAN;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCardCode() {
            return CardCode;
        }

        public void setCardCode(String cardCode) {
            CardCode = cardCode;
        }

        public String getCardName() {
            return CardName;
        }

        public void setCardName(String cardName) {
            CardName = cardName;
        }

        public String getIndustry() {
            return Industry;
        }

        public void setIndustry(String industry) {
            Industry = industry;
        }

        public String getCardType() {
            return CardType;
        }

        public void setCardType(String cardType) {
            CardType = cardType;
        }

        public String getWebsite() {
            return Website;
        }

        public void setWebsite(String website) {
            Website = website;
        }

        public String getEmailAddress() {
            return EmailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            EmailAddress = emailAddress;
        }

        public String getPhone1() {
            return Phone1;
        }

        public void setPhone1(String phone1) {
            Phone1 = phone1;
        }

        public String getCountryCode() {
            return CountryCode;
        }

        public void setCountryCode(String countryCode) {
            CountryCode = countryCode;
        }

        public String getCreditLimit() {
            return CreditLimit;
        }

        public void setCreditLimit(String creditLimit) {
            CreditLimit = creditLimit;
        }

        public String getContactPerson() {
            return ContactPerson;
        }

        public void setContactPerson(String contactPerson) {
            ContactPerson = contactPerson;
        }

        public String getU_LEADID() {
            return U_LEADID;
        }

        public void setU_LEADID(String u_LEADID) {
            U_LEADID = u_LEADID;
        }

        public String getU_LEADNM() {
            return U_LEADNM;
        }

        public void setU_LEADNM(String u_LEADNM) {
            U_LEADNM = u_LEADNM;
        }

        public String getU_PARENTACC() {
            return U_PARENTACC;
        }

        public void setU_PARENTACC(String u_PARENTACC) {
            U_PARENTACC = u_PARENTACC;
        }

        public String getU_CONTOWNR() {
            return U_CONTOWNR;
        }

        public void setU_CONTOWNR(String u_CONTOWNR) {
            U_CONTOWNR = u_CONTOWNR;
        }

        public ArrayList<Type> getU_TYPE() {
            return U_TYPE;
        }

        public void setU_TYPE(ArrayList<Type> u_TYPE) {
            U_TYPE = u_TYPE;
        }

        public String getU_ANLRVN() {
            return U_ANLRVN;
        }

        public void setU_ANLRVN(String u_ANLRVN) {
            U_ANLRVN = u_ANLRVN;
        }

        public String getU_CURBAL() {
            return U_CURBAL;
        }

        public void setU_CURBAL(String u_CURBAL) {
            U_CURBAL = u_CURBAL;
        }

        public String getU_INVNO() {
            return U_INVNO;
        }

        public void setU_INVNO(String u_INVNO) {
            U_INVNO = u_INVNO;
        }

        public String getU_LAT() {
            return U_LAT;
        }

        public void setU_LAT(String u_LAT) {
            U_LAT = u_LAT;
        }

        public String getU_LONG() {
            return U_LONG;
        }

        public void setU_LONG(String u_LONG) {
            U_LONG = u_LONG;
        }

        public String getU_SOURCE() {
            return U_SOURCE;
        }

        public void setU_SOURCE(String u_SOURCE) {
            U_SOURCE = u_SOURCE;
        }

        public String getU_EMIRATESID() {
            return U_EMIRATESID;
        }

        public void setU_EMIRATESID(String u_EMIRATESID) {
            U_EMIRATESID = u_EMIRATESID;
        }

        public String getU_VATNUMBER() {
            return U_VATNUMBER;
        }

        public void setU_VATNUMBER(String u_VATNUMBER) {
            U_VATNUMBER = u_VATNUMBER;
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

        public ArrayList<PayMentTerm> getPayTermsGrpCode() {
            return PayTermsGrpCode;
        }

        public void setPayTermsGrpCode(ArrayList<PayMentTerm> payTermsGrpCode) {
            PayTermsGrpCode = payTermsGrpCode;
        }

        public ArrayList<SalesPersonCodes> getSalesPersonCode() {
            return SalesPersonCode;
        }

        public void setSalesPersonCode(ArrayList<SalesPersonCodes> salesPersonCode) {
            SalesPersonCode = salesPersonCode;
        }

        public List<ContactEmployeesModel> getContactEmployees() {
            return ContactEmployees;
        }

        public void setContactEmployees(List<ContactEmployeesModel> contactEmployees) {
            ContactEmployees = contactEmployees;
        }

        public List<BPAddress> getBPAddresses() {
            return BPAddresses;
        }

        public void setBPAddresses(List<BPAddress> BPAddresses) {
            this.BPAddresses = BPAddresses;
        }
    }
}
