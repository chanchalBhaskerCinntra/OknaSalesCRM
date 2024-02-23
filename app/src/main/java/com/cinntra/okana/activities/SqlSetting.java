package com.cinntra.okana.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.DataBasesListAdapter;
import com.cinntra.okana.databinding.ActivitySqlSettingBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.interfaces.DatabaseClick;
import com.cinntra.okana.model.DataBase;
import com.pixplicity.easyprefs.library.Prefs;

import java.sql.*;
import java.util.ArrayList;




public class SqlSetting extends AppCompatActivity implements View.OnClickListener, DatabaseClick
      {
//    @BindView(R.id.progressBar2)
//    ProgressBar progressBar;
//
//    @BindView(R.id.sql_ip)
//    EditText sql_ip;
//    @BindView(R.id.sql_user)
//    EditText sql_user;
//    @BindView(R.id.sql_pass)
//    EditText sql_pass;
//    @BindView(R.id.submit)
//    Button submit;

          ActivitySqlSettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding=ActivitySqlSettingBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    //ButterKnife.bind(this);
    binding.progressBar2.setVisibility(View.GONE);
        binding. submit.setOnClickListener(this);

        setServerDetails();
       // setSqlServer();


       // hanaTest();

        //setHanaServer();
        //setHanaServer();
        //TESTHANA();






       }

          private void setServerDetails() {
              Globals.IP        = "192.168.29.240"; // SAP
              // public static String PORT     = "1433";           // SAP
              Globals.PORT     = "1433";             //HANA
              Globals.Classes  = "net.sourceforge.jtds.jdbc.Driver";
              Globals.database = "SLDModel.SLDData";
              Globals.username = "sa";
              Globals.password = "sa@2019";
          }


          @Override
          public void onClick(View v) {
        if(v.getId()==R.id.submit)
          {
         if(validation(binding.sqlIp.getText().toString().trim(),binding.sqlUser.getText().toString().trim(),binding.sqlPass.getText().toString().trim())){
            // selectDAtabases();
         if (connection!=null){
          Statement statement = null;
         try {
            statement = connection.createStatement();
           // ResultSet resultSet = statement.executeQuery("Select * from CompanyDBs");
            ResultSet resultSet = statement.executeQuery("Select Name,CompanyName,Version from CompanyDBs");
             DBList = new ArrayList<>();
            while (resultSet.next()){
           // Log.e("Result=>", resultSet.getCursorName());
            DataBase dataBase = new DataBase(resultSet.getString(1),resultSet.getString(1),resultSet.getString(1));
                DBList.add(dataBase);

                      }
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
             selectDAtabases();
             }
             else {
            Log.e("Result=>","Connection is null");

                 }
               }

             }
           }

       private boolean validation(String ip,String user,String pass){
        if(ip.isEmpty())
          {
       Toast.makeText(SqlSetting.this,getResources().getString(R.string.enter_ip_),Toast.LENGTH_SHORT).show();
       return false;
          }
        else if(user.isEmpty())
          {
        Toast.makeText(SqlSetting.this,getResources().getString(R.string.enter_user),Toast.LENGTH_SHORT).show();
        return false;
          }
        else if(pass.isEmpty())
          {
        Toast.makeText(SqlSetting.this,getResources().getString(R.string.enter_sql_pass),Toast.LENGTH_SHORT).show();
        return false;
          }
        return  true;
          }


          /************* Sql server Data Connection **************/

        private void TESTHANA(){
            Connection connection = null;
            try {
                 connection = java.sql.DriverManager.getConnection
                        ("jdbc:sap://103.118.16.194:30013/?databaseName=TEST&user=B1ADMIN&password=Cinntra#@123");

               /* connection = DriverManager.getConnection(
                        "jdbc:sap://103.118.16.194:30015/?autocommit=false", "B1ADMIN", "Cinntra#@123");*/
            } catch (SQLException e) {
                System.err.println("Connection Failed:");
                System.err.println(e);
                return;
            }
            if (connection != null) {
                try {
                    Log.e("ConStatus","HANA successful!");
                    System.out.println("Connection to HANA successful!");
                    Statement stmt = connection.createStatement();
                    ResultSet resultSet = stmt.executeQuery("Select 'Hello, world' from dummy");
                    resultSet.next();
                    String hello = resultSet.getString(1);
                    System.out.println(hello);
                } catch (SQLException e) {
                    System.err.println("Query failed!");
                }
            }
        }
        private void hanaTest()
        {
            try {
               // Class.forName("com.sap.db.jdbc.Driver");
                Log.e("Info==>",""+com.sap.db.jdbc.Driver.getVersionInfo());
                Class.forName("com.sap.db.jdbc.Driver");
                String url  = "jdbc:sap://103.118.16.194:30015/TEST"; //IP Address of HANAsystem followed by Port number
                String user = "B1ADMIN";
                String password = "Cinntra#@123";
                Connection cn = java.sql.DriverManager.getConnection(url, user, password);
                if (cn != null)
                 Log.e("ConStatus","HANA successful!");

               // ResultSet rs = cn.createStatement().executeQuery("CALL Test.STORED_PROC");
                // ...Enter the action here
            } catch(Exception e) {
                e.printStackTrace();
            }

        }

          private void setHanaServer() {

              Connection connection = null;
              try
              {
                  connection = DriverManager.getConnection(
                          "jdbc:sap://103.118.16.194:30015/?autocommit=false","B1ADMIN", "Cinntra#@123");
              } catch (SQLException e) {
                  System.err.println("Connection Failed. User/Passwd Error?");
                  return;
              }
              if (connection != null) {
                  try {
                      System.out.println("Connection to HANA successful!");
                      Statement stmt = connection.createStatement();
                      ResultSet resultSet = stmt.executeQuery("Select 'hello world' from dummy");
                      resultSet.next();
                      String hello = resultSet.getString(1);
                      System.out.println(hello);
                  } catch (SQLException e) {
                      System.err.println("Query failed!");
                  }
              }
          }
          private Connection connection = null;

          private void setSqlServer()
                  {
               String url = "jdbc:jtds:sqlserver://"+Globals.IP+":"+Globals.PORT+"/"+Globals.database;
              ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);


              StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
              StrictMode.setThreadPolicy(policy);
              try {
                  Class.forName(Globals.Classes);
                  connection = DriverManager.getConnection(url, Globals.username,Globals.password);
                  Log.e("ConStatus","Success");

                  Prefs.putString(Globals.SelectedSqlIP,Globals.IP);
                  Prefs.putString(Globals.SelectedSqlUser,Globals.username);
                  Prefs.putString(Globals.SelectedSqlPass,Globals.password);

              } catch (ClassNotFoundException e) {
                  e.printStackTrace();
                  Log.e("ConStatus","Error");
              } catch (SQLException e) {
                  e.printStackTrace();
                  Log.e("ConStatus","Failure");
              }


          }




         /************ DataBase Alert *****************/
          public ArrayList<DataBase> DBList;
         private void selectDAtabases()
           {
          final Dialog dialog = new Dialog(SqlSetting.this,R.style.FullScreen);
         //final Dialog dialog = new Dialog(SqlSetting.this);
         dialog.setContentView(R.layout.alert_databse_listing);
         dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
         RecyclerView database_listing = (RecyclerView)dialog.findViewById(R.id.database_listing);
         database_listing.setLayoutManager(new LinearLayoutManager(SqlSetting.this,LinearLayoutManager.VERTICAL,false));
         database_listing.setAdapter(new DataBasesListAdapter(SqlSetting.this,DBList));
         dialog.show();
               //if button is clicked, close the custom dialog
               ImageView cross = (ImageView) dialog.findViewById(R.id.cross);
               cross.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       dialog.dismiss();
                   }
               });
            }

          @Override
          public void onClick(int po) {
              Prefs.putString(Globals.SelectedDatabase,DBList.get(po).getName());
              onBackPressed();

          }

          @Override
          public void onBackPressed() {
          super.onBackPressed();
           Intent intent=new Intent();
           setResult(2,intent);
                 }
      }