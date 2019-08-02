package com.builov.myvendorsapp.adapter;
import android.content.Context;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import com.builov.myvendorsapp.R;
import java.util.ArrayList;
import java.util.HashMap;
public class listViewAdapter {
    public void setAdapter(String[] from, int[] to, RadioButton
            rbMaterials, ListView listView,
                           ArrayList<HashMap<String, String>>
                                   dataset, Context context) {
//Создаем адаптер
        if (rbMaterials.isChecked()) {
            SimpleAdapter adapter = new SimpleAdapter(context,
                    dataset, R.layout.materials_item, from, to);
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
        } else {
            SimpleAdapter adapter = new SimpleAdapter(context,
                    dataset, R.layout.manufacturers_item, from, to);
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
        }
    }
}