package org.belosoft.testsqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by oscar on 20/09/2017.
 */

public class CarsSQLiteHelper extends SQLiteOpenHelper {

    // sentencia para crear la tabla Cars
    String sqlCreate = "CREATE TABLE Cars (VIN INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT, color TEXT)";

    public CarsSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // se borra la version anterior de la tabla
        db.execSQL("DROP TABLE IF EXIST Cars");

        // se crea la nueva version de la tabla
        db.execSQL(sqlCreate);
    }
}
