package org.belosoft.testsqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnCreate;
    private Button btnDelete;

    private CarsSQLiteHelper carsHelper;
    private SQLiteDatabase db;

    private ListView listView;
    private MyAdapter adapter;

    private List<Car> cars;

    private int counter = 0;

    private final static int DB_VERSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        cars = new ArrayList<Car>();

        btnCreate = (Button) findViewById(R.id.buttonCreate);
        btnDelete = (Button) findViewById(R.id.buttonDelete);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viev) {
                create();
                update();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAll();
                update();
            }
        });

        // abrimos la bd DBTest1 modo escritura
        carsHelper = new CarsSQLiteHelper(this, "DBTest1", null, 1);
        db = carsHelper.getWritableDatabase();

        adapter = new MyAdapter(this, cars, R.layout.itemdb);
        listView.setAdapter(adapter);

        update();

    }

    private List<Car> getAllCars() {
        // Seleccionamos todos los registros de la tabla Cars
        Cursor cursor = db.rawQuery("select * from Cars", null);
        List<Car> list = new ArrayList<Car>();

        if (cursor.moveToFirst()) {
            // iteramos sobre el cursor de resultados,
            // y vamos rellenando el array que posteriormente devolveremos
            while (cursor.isAfterLast() == false) {

                int VIN = cursor.getInt(cursor.getColumnIndex("VIN"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String color = cursor.getString(cursor.getColumnIndex("color"));

                list.add(new Car(VIN, name, color));
                cursor.moveToNext();
            }
        }
        return list;
    }

    private void create() {
        // si esta abierta conectamos con la bd
        if (db != null){
            ContentValues nuevoRegistro = new ContentValues();
            // el id se autoincrementa, no se usa aqui
            nuevoRegistro.put("name","Seat " + (++counter));
            nuevoRegistro.put("color", "Black");
            // insertamos
            db.insert("Cars", null, nuevoRegistro);
        }
    }

    private void removeAll() {
        db.delete("Cars", "", null);
    }

    private void update() {
        // borramos todos los elementos
        cars.clear();
        // cargamos todos los elementos
        cars.addAll(getAllCars());
        // refrescamos el adaptador
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        // cerramos la conexion con la bd
        db.close();
        super.onDestroy();
    }

}
