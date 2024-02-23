package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MemberList implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("CampSetId")
    @Expose
    private Integer campSetId;
    private final static long serialVersionUID = -7752366642179689130L;

    /**
     * No args constructor for use in serialization
     *
     */
    public MemberList() {
    }

    /**
     *
     * @param phone
     * @param name
     * @param id
     * @param campSetId
     * @param email
     */
    public MemberList(Integer id, String name, String phone, String email, Integer campSetId) {
        super();
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.campSetId = campSetId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCampSetId() {
        return campSetId;
    }

    public void setCampSetId(Integer campSetId) {
        this.campSetId = campSetId;
    }

}
