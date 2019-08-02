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
    public Bundle getPosition(SQLiteDatabase database,Context context,HashMap<String, String> data){
        Bundle bundle = new Bundle();
        String name="";
        String id="";
        //Toast.makeText(context, data.get("Id"),Toast.LENGTH_SHORT).show();
        //добавить выбор таблицы, сейчас только materials
        String query = "SELECT * FROM materials WHERE Id = "+ data.get("Id");
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){

             id = cursor.getString(0);
             name = cursor.getString(1);

            cursor.moveToNext();
        }
        cursor.close();
        bundle.putString("Id",id);
        bundle.putString("mName",name);
        //bundle.getString("mName",name);
        //Toast.makeText(context, "text from getDataActivity = " + bundle.getString("mName"),Toast.LENGTH_SHORT).show();

        return bundle;
    }


}