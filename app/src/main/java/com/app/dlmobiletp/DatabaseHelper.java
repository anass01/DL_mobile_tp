package com.app.dlmobiletp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "datab.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table fil (code INTEGER primary key ,libelle TEXT)");
        db.execSQL("create table groupe (code INTEGER primary key ,libelle TEXT)");
        db.execSQL("create table Stagiaire (code INTEGER primary key ,nom TEXT,prenom TEXT,daten DATE,fil INTEGER , groupe INTEGER,FOREIGN KEY(fil) REFERENCES fil(code),FOREIGN KEY(groupe) REFERENCES groupe(code))");
        db.execSQL("create table abs (code INTEGER primary key AUTOINCREMENT,ids INTEGER,date DATE,FOREIGN KEY(ids) REFERENCES Stagiaire(code))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
