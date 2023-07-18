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
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Return_screen extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    private Button btnD2; // button to call the home screen



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_screen);

//        TextView Fname = (TextView) findViewById(R.id.editTextD);

        CodeScannerView scannerView = findViewById(R.id.scanner_viewR);

// defining and calling 2nd home screen
        btnD2 = (Button) findViewById(R.id.btnDone2);
        btnD2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity1();
            }
        });

        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String value = result.getText().toString();
                        String[] stringarray3 = value.split("-");

//                        String reg = String.valueOf(stringarray[0]);
//                        String Name = String.valueOf(stringarray[1]);
//                        String yr = String.valueOf(stringarray[2]);
//                        String dept = String.valueOf(stringarray[3]);

                        String AccNo = String.valueOf(stringarray3[0]);

                        Toast.makeText(Return_screen.this, result.getText(), Toast.LENGTH_SHORT).show();

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                        String currentDate = sdf.format(new Date());





                        Connection connection = connectionclass();
                        try {
                            if (connection != null) {

//                                String sqlinsert = "Insert into LmsTable values ('" + reg + "','" + Name + "','" + yr + "','" + dept +"')";

                                String sqlinsert = "Update book_info set status = 'YES' , return_date = '"+currentDate+"' where acc_no = '"+ AccNo +"'";

//                                String Sqlreturn = "Update book_info set status = YES where ";

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

    }
    // using to call the home screen
    public void openActivity1() {
        Intent intent = new Intent(this, MainActivity.class);
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
        String ip = "<Enter your IP>", port = "<port address>", username = "sa", password = "password", databasename = "LmsDB";

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