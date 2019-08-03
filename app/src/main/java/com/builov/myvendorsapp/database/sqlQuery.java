package com.builov.myvendorsapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class sqlQuery {
    ArrayList<HashMap<String, String>> svodDataSet = new ArrayList<HashMap<String, String>>();

    public void del(SQLiteDatabase database, String condition, String table, HashMap<String, String> data, RadioButton rbMaterials, Context context) {

        if (rbMaterials.isChecked()) {
            database.delete(table, condition + " = " + data.get("Id"), null);
        } else
            database.delete(table, condition + " = " + data.get("id"), null);
    }

    public void svodDel(SQLiteDatabase database, String condition, HashMap<String, String> data, RadioButton rbMaterials, Context context) {

        String query = "SELECT * FROM manufacturers_materials";

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
//Пробегаем по всем клиентам
        while (!cursor.isAfterLast()) {
            HashMap<String, String> svodData = new HashMap<String,
                    String>();
            svodData.put("_id", cursor.getString(0));
            svodData.put("manufacturers_id", cursor.getString(1));
            svodData.put("materials_id", cursor.getString(2));
            cursor.moveToNext();
        }
        cursor.close();
        if (rbMaterials.isChecked()) {
            database.delete("manufacturers_materials", "materials_id=" + data.get(condition), null);
        } else
            database.delete("manufacturers_materials", "manufacturers_id=" + data.get(condition), null);
    }

    public void insert(ContentValues contentValues,SQLiteDatabase database, String rb, String name, String inn, ArrayList<HashMap<String, String>> dataset, Context context) {


        if (rb.equals("1")) {
            contentValues.put("mName", name);
            database.insert("materials", null, contentValues);
            new workWithDb().showAll(context,dataset, database, rb);

            //Toast.makeText(context, "1", Toast.LENGTH_LONG).show();
        }
        if (rb.equals("2")) {
            contentValues.put("name", name);
            contentValues.put("INN", inn);
            database.insert("manufacturers", null, contentValues);
            new workWithDb().showAll(context,dataset, database, rb);

            //Toast.makeText(context, 1, Toast.LENGTH_LONG).show();
        }
    }

    public void update(SQLiteDatabase database, String id, String name, String inn, Context context) {
        String query = "";
        String table = "";
        String select= "";

        //создаем контейнер с данными
        ContentValues cv = new ContentValues();

        //если ИНН пустое, значит добавляем в таблицу materials, если ИНН не пустое то в таблицу manufacturers
        //создаем строку запроса обновления в базу данных

        if (!inn.equals("")) {

            //наполняем cv полученными ИД, ИМЯ
            cv.put("id", id);
            cv.put("INN", inn);
            cv.put("Name", name);
            select = "Id=";
            table = "manufacturers";
            query = "SELECT * FROM manufacturers";
           // Toast.makeText(context, query, Toast.LENGTH_LONG).show();
        }

        if (inn.equals("")) {

            //наполняем cv полученными ИД, ИМЯ
            cv.put("Id", id);
            cv.put("mName", name);
            select="id=";
            table = "materials";
            query = "SELECT * FROM materials";
            //Toast.makeText(context, query, Toast.LENGTH_LONG).show();
        }

        //выполняем запрос описаный выше
        Cursor cursor = database.rawQuery(query, null);

        //переходим к первой записи БД
        cursor.moveToFirst();

        //пока не последняя запись делаем запросы
        while (!cursor.isAfterLast()) {

            //обновляем элемент в таблице table в котором id(ID)=id
            database.update(table, cv, select + id, null);
            cursor.moveToNext();
        }
        cursor.close();

        // данные в таблице обновлены, осталось их отобразить. Возвращаемся в MainActivity.
    }
}