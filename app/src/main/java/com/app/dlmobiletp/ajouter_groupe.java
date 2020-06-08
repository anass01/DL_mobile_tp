package com.app.dlmobiletp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ajouter_groupe extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajouter_groupe);
        ListView lv =(ListView)findViewById(R.id.lv_fil);
        final DatabaseHelper dbHelper = new DatabaseHelper(this);
        loadlv(dbHelper);
        final TextView txtcode=(TextView) findViewById(R.id.txt_code_g);
        final TextView libelle=(TextView) findViewById(R.id.txt_libelle_g);
        Button btn_af= (Button)findViewById(R.id.Btn_ajouter_g);
        btn_af.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase dbi = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("code", Integer.valueOf(txtcode.getText().toString()));
                values.put("libelle", libelle.getText().toString());
                long newRowId = dbi.insert("groupe", null, values);
                dbi.close();
                txtcode.setText("");
                libelle.setText("");
                loadlv(dbHelper);
            }
        });
    }
    public void loadlv(DatabaseHelper dbHelper){
        ListView lv =(ListView)findViewById(R.id.lv_gr);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<String> fillist = new ArrayList<String>();
        Cursor rs= db.query("groupe",null,null,null,null,null ,null);
        while(rs.moveToNext()){
            fillist.add("ID : "+Integer.toString(rs.getInt(0))+" Libelle : "+rs.getString(1));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fillist);
        lv.setAdapter(adapter);
    }
}