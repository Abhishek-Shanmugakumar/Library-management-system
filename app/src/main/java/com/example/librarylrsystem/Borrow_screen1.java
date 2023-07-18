package com.example.librarylrsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Borrow_screen1 extends AppCompatActivity {

    private Button btnB2; // button for calling second screen
    private CodeScanner mCodeScanner;

    public static String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_screen1);
        CodeScannerView scannerView = findViewById(R.id.scanner_view_IN1);

        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String value = result.getText();   //.toString();
                        String[] stringarray = value.split("-");

                        user_id = String.valueOf(stringarray[0]);
                        String name = String.valueOf(stringarray[1]);
                        String year = String.valueOf(stringarray[2]);
                        String dept = String.valueOf(stringarray[3]);

//                        Toast.makeText(Borrow_screen1.this, result.getText(), Toast.LENGTH_SHORT).show();

                        Toast.makeText(Borrow_screen1.this, user_id, Toast.LENGTH_SHORT).show();
                        Toast.makeText(Borrow_screen1.this, name, Toast.LENGTH_SHORT).show();
                        Toast.makeText(Borrow_screen1.this, year, Toast.LENGTH_SHORT).show();
                        Toast.makeText(Borrow_screen1.this, dept, Toast.LENGTH_SHORT).show();

                        Connection connection = connectionclass();
                        try {
                            if (connection != null) {

//                                String sqlinsert = "Insert into Lib_LR (User_Id, Student_name, Year) values ('" + User_Id + "','" + Student_Name + "','" + Year + "','" + Dept +"')";

//                                String sqlinsert = "Insert into Lib_LR (User_Id, Student_name, Year) values ('" + User_Id + "','" + Student_Name + "','" + Year + "')'";

                                String sqlinsert = "Insert into user_info values ('" + user_id + "','" + name + "','" + year + "','" + dept +"')";

                                Statement st = connection.createStatement();
                                ResultSet rs = st.executeQuery(sqlinsert);
                            }
                        } catch (Exception exception) {
                            Log.e("Error", exception.getMessage());
                        }

                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

// defining and calling 2nd borrow screen
        btnB2 = (Button) findViewById(R.id.btnBnext);
        btnB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity5();
            }
        });
    }
// using to call the second screen
    public void openActivity5() {
        Intent intent = new Intent(this, Borrow_screen2.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    @SuppressLint("NewApi")
    public Connection connectionclass() {
        Connection con = null;
        String ip = "<Enter your network IP>", port = "1433", username = "sa", password = "password", databasename = "LmsDB";
        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String connectionUrl = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";databasename=" + databasename + ";User=" + username + ";password=" + password + ";";
            con = DriverManager.getConnection(connectionUrl);
        } catch (Exception exception) {
            Log.e("Error", exception.getMessage());
        }
        return con;
    }

}