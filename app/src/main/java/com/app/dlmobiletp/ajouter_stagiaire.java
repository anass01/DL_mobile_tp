package com.app.dlmobiletp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ajouter_stagiaire extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajouer_stagiaire);
        mxid();
        fillcombo();
        final DatabaseHelper dbHelper = new DatabaseHelper(this);
        final EditText cne = (EditText)findViewById(R.id.txt_cne);
        final EditText nom = (EditText)findViewById(R.id.txt_nom);
        final EditText prenom = (EditText)findViewById(R.id.txt_prenom);
        final EditText date = (EditText)findViewById(R.id.txt_date);
        final Spinner filiere = (Spinner) findViewById(R.id.spinner_as);
        final Spinner groupe = (Spinner) findViewById(R.id.spinner2_as);
        Button btn =(Button) findViewById(R.id.Btn_ajouter_s);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date d = new Date();
                try {
                    d=formatter.parse(date.getText().toString());
                } catch (ParseException e) {
                    Toast.makeText(ajouter_stagiaire.this, "verifier date", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                SQLiteDatabase dbi = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("code", Integer.valueOf(cne.getText().toString()));
                values.put("nom", nom.getText().toString());
                values.put("prenom", prenom.getText().toString());
                values.put("daten", String.valueOf(d));
                values.put("fil", nametoid(filiere.getSelectedItem().toString(),"fil"));
                values.put("groupe", nametoid(groupe.getSelectedItem().toString(),"groupe"));
                long newRowId = dbi.insert("Stagiaire", null, values);
                dbi.close();
                Toast.makeText(ajouter_stagiaire.this, "done !", Toast.LENGTH_SHORT).show();
                mxid();
                nom.setText("");
                prenom.setText("");
                date.setText("");
            }
        });
    }
    public void mxid(){
        EditText cne = (EditText)findViewById(R.id.txt_cne);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        System.out.println(db.getPath());
        String selectQuery = "SELECT max(code)+1 as id FROM Stagiaire";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            cne.setText(cursor.getInt(0)+"");
        }
        db.close();
        dbHelper.close();

    }
    public int nametoid(String name,String table){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select code from "+table+" where libelle='"+name+"'", null);
        if (cursor.moveToFirst()){
            return cursor.getInt(0);
        }
        return -1;
    }
    public void fillcombo(){
        List<String> filiereArray =  new ArrayList<String>();
        List<String> groupeArray =  new ArrayList<String>();
        Spinner filiere = (Spinner) findViewById(R.id.spinner_as);
        Spinner groupe = (Spinner) findViewById(R.id.spinner2_as);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from fil", null);
        while(cursor.moveToNext()){
            filiereArray.add(cursor.getString(1));
        }
        cursor = db.rawQuery("select * from groupe", null);
        while(cursor.moveToNext()){
            groupeArray.add(cursor.getString(1));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, filiereArray);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, groupeArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filiere.setAdapter(adapter);
        groupe.setAdapter(adapter1);


    }
}
