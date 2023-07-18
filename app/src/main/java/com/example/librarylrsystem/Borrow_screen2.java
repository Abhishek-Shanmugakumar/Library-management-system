package com.example.librarylrsystem;

import static com.example.librarylrsystem.Borrow_screen1.user_id;

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

public class Borrow_screen2 extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    private Button btnDone; // button for calling home screen after done

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_screen2);
        CodeScannerView scannerView = findViewById(R.id.scanner_viewIN2);

// defining and calling the home screen
        btnDone = (Button) findViewById(R.id.btnDone1);
        btnDone.setOnClickListener(new View.OnClickListener() {
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
                        String value2 = result.getText().toString();
                        String[] stringarray2 = value2.split("-");

                        String acc_no = String.valueOf(stringarray2[0]);
                        String bname = String.valueOf(stringarray2[1]);
                        String author = String.valueOf(stringarray2[2]);
//                        String dept = String.valueOf(stringarray[3]);


                        Toast.makeText(Borrow_screen2.this, result.getText(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(Borrow_screen2.this, user_id, Toast.LENGTH_SHORT).show();


                        Connection connection = connectionclass();
                        try {
                            if (connection != null) {

                                String Sqlinsert = "Insert into book_info (user_id, acc_no, book_name, author) values ('" + user_id + "','" + acc_no + "','" + bname + "','" + author +"')";

                               // String Sqlinsert = "Insert into book_info (acc_no, book_name, author) values ('"+ accNo + "','" + bname + "','" + author +"')";

                                ////String SqlB2 = "Update Lib_LR set Acc_no = "+ AccNo +"',' Book_name = "+ Bname +"','Author = "+Author+" Where User_Id = "+ user_id +";";

                                Statement st = connection.createStatement();

                                ResultSet rs = st.executeQuery(Sqlinsert);
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
// using to move to home screen
    public void openActivity1() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        Intent new_intent = new Intent(this, MainActivity.class);

        this.startActivity(new_intent);

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
        String ip = "<Enter your network IP>", port = "<Port address>", username = "sa", password = "password", databasename = "LmsDB";
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