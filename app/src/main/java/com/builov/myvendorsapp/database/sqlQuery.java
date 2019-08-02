package com.builov.myvendorsapp.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.builov.myvendorsapp.database.workWithDb;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class sqlQuery {
    String mat;
    String man;
    String svod;
    ArrayList<HashMap<String, String>> svodDataSet = new
            ArrayList<HashMap<String, String>>();
    public void del(SQLiteDatabase database, String condition, String table, Map data, RadioButton rbMaterials){

        if (rbMaterials.isChecked()){
            database.delete(table, condition + " = " + data.get("Id"),
                    null);
        } else
            database.delete(table, condition + " = " +
                    data.get("id"), null);
    }
    public void insert(ContentValues contentValues, RadioButton
            rbMaterials, SQLiteDatabase database, int rb, String name,
                       String inn, String tblMat, String
                               tblMan,ArrayList<HashMap<String,
            String>> dataset,HashMap<String, String> dataItem){
        if (rb == 1) {
            contentValues.put("mName", name);
            database.insert("materials", null, contentValues);
            new workWithDb().showAll(tblMat, dataset, database,
                    dataItem, rbMaterials);}
        if (rb == 2) {
            contentValues.put("name", name);
            contentValues.put("INN", inn);
            database.insert("manufacturers", null, contentValues);
            new workWithDb().showAll(tblMan, dataset, database,
                    dataItem, rbMaterials);}
    }
    public void update(){
    }
    public void svodDel(SQLiteDatabase database, String condition,
                        String table, Map data, RadioButton rbMaterials, Context context){

        String query = "SELECT * FROM manufacturers_materials";

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
//Пробегаем по всем клиентам
        while (!cursor.isAfterLast()) {
            HashMap<String, String> svodData = new HashMap<String,
                    String>();
            //svodData.put("_id",cursor.getString(0));
            svodData.put("manufacturers_id",cursor.getString(1));
            svodData.put("materials_id",cursor.getString(2));
            cursor.moveToNext();
        }
        cursor.close();
        if (rbMaterials.isChecked()){
            database.delete("manufacturers_materials","materials_id="+data.get(condition),null);}
        else database.delete("manufacturers_materials", "manufacturers_id="+data.get(condition),null);
    }
}