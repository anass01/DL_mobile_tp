package com.app.dlmobiletp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        System.out.println(db.getPath());

        Cursor rs= db.query("fil",null,null,null,null,null ,null);
        while(rs.moveToNext()){
            System.out.println(rs.getInt(0));
            System.out.println(rs.getString(1));
        }

        db.close();
        Button b_af = (Button)findViewById(R.id.btn_af);
        b_af.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), ajouter_filiere.class);
                startActivity(myIntent);
            }
        });
        Button b_ag = (Button)findViewById(R.id.btn_ag);
        b_ag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), ajouter_groupe.class);
                startActivity(myIntent);
            }
        });
        Button b_as = (Button)findViewById(R.id.btn_as);
        b_as.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), ajouter_stagiaire.class);
                startActivity(myIntent);
            }
        });
        Button b_aa = (Button)findViewById(R.id.btn_aa);
        b_aa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), ajouter_absence.class);
                startActivity(myIntent);
            }
        });
        Button b_la = (Button)findViewById(R.id.btn_la);
        b_la.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), liste_absence.class);
                startActivity(myIntent);
            }
        });
    }
}
