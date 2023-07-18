package com.example.librarylrsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnA; // button used for about screen
    private Button btnB; // button used for borrow screen
    private Button btnR; // button used for return screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// defining and calling about screen
        btnA = (Button) findViewById(R.id.btnAbout);
        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
// defining and called the borrow screen 1
        btnB = (Button) findViewById(R.id.btnBorrow);
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });
// defining and called the borrow screen 1
        btnR = (Button) findViewById(R.id.btnReturn);
        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity4();
            }
        });
    }
// this opens about screen
    public void openActivity2() {
        Intent intent = new Intent(this, about_screen.class);
        startActivity(intent);
    }
// this opens borrow screen 1
    public void openActivity3() {
        Intent intent = new Intent(this, Borrow_screen1.class);
        startActivity(intent);
    }
// this opens return screen
    public void openActivity4() {
        Intent intent = new Intent(this, Return_screen.class);
        startActivity(intent);
    }
}