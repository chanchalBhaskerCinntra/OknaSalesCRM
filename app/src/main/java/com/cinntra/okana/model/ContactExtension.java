package com.cinntra.okana.model;

 import android.os.Parcel;
 import android.os.Parcelable;
 import com.google.gson.annotations.SerializedName;
 import java.util.ArrayList;


public class ContactExtension implements Parcelable {


    @SerializedName("ContactEmployees")
    ArrayList<AddContactEmployee>  ContactEmployees;



    public ContactExtension()
     {
     }


    protected ContactExtension(Parcel in)
      {
    ContactEmployees = in.createTypedArrayList(AddContactEmployee.CREATOR);
      }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeTypedList(ContactEmployees);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContactExtension> CREATOR = new Creator<ContactExtension>() {
        @Override
        public ContactExtension createFromParcel(Parcel in) {
            return new ContactExtension(in);
        }

        @Override
        public ContactExtension[] newArray(int size) {
            return new ContactExtension[size];
        }
    };



    public ArrayList<AddContactEmployee> getContactEmployees() {
        return ContactEmployees;
    }

    public void setContactEmployees(ArrayList<AddContactEmployee> contactEmployees) {
        ContactEmployees = contactEmployees;
    }
}
