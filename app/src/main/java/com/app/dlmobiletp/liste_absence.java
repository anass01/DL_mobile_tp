package com.app.dlmobiletp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class liste_absence extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_absence);
        fillspinner();
        final Spinner spinner = (Spinner)findViewById(R.id.spinner_abs);
        final Spinner spinner2 = (Spinner)findViewById(R.id.spinner_m);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.m_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        Button btn_voir=(Button)findViewById(R.id.btn_abs);
        btn_voir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(v.getContext());
                ListView lv =(ListView)findViewById(R.id.lv_abs);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                ArrayList<String> abslist = new ArrayList<String>();
                String sqls="select * from abs where ids="+Integer.toString(nametoid(spinner.getSelectedItem().toString()))+" and strftime('%m', date) = '"+spinner2.getSelectedItem().toString()+"'";
                Cursor rs = db.rawQuery(sqls,null);
                Toast.makeText(liste_absence.this, " "+rs.getCount()+" ", Toast.LENGTH_SHORT).show();
                while(rs.moveToNext()){
                    System.out.println("rs");
                    System.out.println(rs.getString(2));
                    abslist.add("date : "+rs.getString(2));
                }
                System.out.println("val");
                ArrayAdapter<String> adapte = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, abslist);
                lv.setAdapter(adapte);
            }
        });

    }
    public void fillspinner(){
        List<String> stagiereArray =  new ArrayList<String>();
        Spinner spinner = (Spinner)findViewById(R.id.spinner_abs);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Stagiaire", null);
        while(cursor.moveToNext()){
            stagiereArray.add(cursor.getString(1)+" "+cursor.getString(2));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stagiereArray);
        spinner.setAdapter(adapter);
        cursor.close();
        db.close();
        dbHelper.close();
    }
    public int nametoid(String nom){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String  sqlst="select code from Stagiaire where nom||' '||prenom = '"+nom+"'";
        Cursor cursor = db.rawQuery(sqlst, null);
        if (cursor.moveToFirst()){
            return cursor.getInt(0);
        }
        return -1;
    }

}
