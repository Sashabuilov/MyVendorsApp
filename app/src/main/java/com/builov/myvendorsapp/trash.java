package com.builov.myvendorsapp;

public class trash {

    // работа с детализацией

    /*public void advanceItem(HashMap<String, String> data) {

        String query = "SELECT Name FROM " + tblMan + " as man," + tblMat + " as mat," + tblSvod + " as svod " +
                "WHERE man.id=svod.manufacturers_id " +
                "AND mat.Id=svod.materials_id " +
                "AND mat.Id=" + data.get("Id");

        Cursor cursor = database.rawQuery(query, null);
        String result = "";


        //получаем длину таблицы
        long rowCount = DatabaseUtils.queryNumEntries(database, "manufacturers");
        String str = Long.toString(rowCount);
        int i = Integer.parseInt(str);
        strings = new String[i];
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            strings[j] = cursor.getString(0);
            j = j + 1;

            //result += cursor.getString(0) + " | ";

            cursor.moveToNext();
        }
        cursor.close();
    }*/
}
