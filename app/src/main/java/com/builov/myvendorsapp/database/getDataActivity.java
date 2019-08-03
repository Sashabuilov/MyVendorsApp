package com.builov.myvendorsapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.HashMap;

public class getDataActivity {

    public Bundle showAdvance(String tblMan, String tblMat, String tblSvod,
                              HashMap<String, String> data, SQLiteDatabase database,
                              String[] strings, int j, Context context,
                              String row, String rowName, String tables, String selectName) {

        Bundle bundle = new Bundle();
        String query = "SELECT " + selectName + " FROM " + tblMan + " as man," + tblMat + " as mat," + tblSvod + " as svod " +
                "WHERE man.id=svod.manufacturers_id " +
                "AND mat.Id=svod.materials_id " +
                "AND " + rowName + "=" + data.get(row);

        Cursor cursor = database.rawQuery(query, null);


        //получаем длину таблицы
        long rowCount;
        rowCount = DatabaseUtils.queryNumEntries(database, tables);

        String str = Long.toString(rowCount);
        int i = Integer.parseInt(str);

        strings = new String[i];
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            strings[j] = cursor.getString(0);
            j = j + 1;
            cursor.moveToNext();
        }
        cursor.close();
        bundle.putInt("j", j);
        bundle.putSerializable("strings", strings);
        return bundle;
    }


    //получаем позицию любого выбранного элемента в ListView
    public Bundle getPosition(SQLiteDatabase database, Context context, HashMap<String, String> data, String tables, String row, String radioButton) {
        //объект bundle который будем возвращать
        Bundle bundle = new Bundle();
        //инициализируем переменные
        String id = ""; //ИД
        String name = "";//ИМЯ
        String inn = "";//ИНН


        if (radioButton.equals("1")) radioButton = "Id";
        else radioButton = "id";

        //Выбираем все из таблицы tables(значение которой нужно получить из MainActiity) ID=row

        String query = "SELECT * FROM " + tables + " WHERE " + radioButton + " = " + data.get(row);
        //Toast.makeText(context,query,Toast.LENGTH_LONG).show();

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            id = cursor.getString(0);
            name = cursor.getString(1);
            if (radioButton.equals("id")) inn = cursor.getString(2);
            cursor.moveToNext();
        }
        cursor.close();
        bundle.putString("Id", id);
        bundle.putString("mName", name);
        if (!inn.equals("")) {
            bundle.putString("mINN", inn); }
        return bundle;
    }


}
