package com.cinntra.okana.newapimodel;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AttachDocument implements Serializable, Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("File")
    @Expose
    private String file;
    @SerializedName("LinkType")
    @Expose
    private String linkType;
    @SerializedName("Caption")
    @Expose
    private String caption;
    @SerializedName("LinkID")
    @Expose
    private Integer linkID;
    @SerializedName("CreateDate")
    @Expose
    private String createDate;
    @SerializedName("CreateTime")
    @Expose
    private String createTime;
    @SerializedName("UpdateDate")
    @Expose
    private String updateDate;
    @SerializedName("UpdateTime")
    @Expose
    private String updateTime;
    @SerializedName("FileName")
    @Expose
    private String fileName;
    private final static long serialVersionUID = 4506091094702080866L;

    /**
     * No args constructor for use in serialization
     */
    public AttachDocument() {
    }

    /**
     * @param updateDate
     * @param fileName
     * @param file
     * @param linkID
     * @param createTime
     * @param caption
     * @param linkType
     * @param updateTime
     * @param id
     * @param createDate
     */
    public AttachDocument(Integer id, String file, String linkType, String caption, Integer linkID, String createDate, String createTime, String updateDate, String updateTime, String fileName) {
        super();
        this.id = id;
        this.file = file;
        this.linkType = linkType;
        this.caption = caption;
        this.linkID = linkID;
        this.createDate = createDate;
        this.createTime = createTime;
        this.updateDate = updateDate;
        this.updateTime = updateTime;
        this.fileName = fileName;
    }

    protected AttachDocument(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        file = in.readString();
        linkType = in.readString();
        caption = in.readString();
        if (in.readByte() == 0) {
            linkID = null;
        } else {
            linkID = in.readInt();
        }
        createDate = in.readString();
        createTime = in.readString();
        updateDate = in.readString();
        updateTime = in.readString();
        fileName = in.readString();
    }

    public static final Creator<AttachDocument> CREATOR = new Creator<AttachDocument>() {
        @Override
        public AttachDocument createFromParcel(Parcel in) {
            return new AttachDocument(in);
        }

        @Override
        public AttachDocument[] newArray(int size) {
            return new AttachDocument[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Integer getLinkID() {
        return linkID;
    }

    public void setLinkID(Integer linkID) {
        this.linkID = linkID;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(file);
        parcel.writeString(linkType);
        parcel.writeString(caption);
        if (linkID == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(linkID);
        }
        parcel.writeString(createDate);
        parcel.writeString(createTime);
        parcel.writeString(updateDate);
        parcel.writeString(updateTime);
        parcel.writeString(fileName);
    }
}
