package com.example.va.todolist;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.va.todolist.db.DatabaseHelper;
import com.example.va.todolist.db.model.Item;
import com.example.va.todolist.db.model.ListI;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by va on 4/22/2017.
 */

public class ThirdActivity extends AppCompatActivity{

    private DatabaseHelper databaseHelper;
    private Item m;
    private TextView name;
    private TextView ammount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_item);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        int key = getIntent().getExtras().getInt(SecondActivity.key_detail);

        try {
            m = getDatabaseHelper().getItemDao().queryForId(key);

            name = (EditText) findViewById(R.id.item_name);
            ammount = (EditText) findViewById(R.id.Ammount);

            name.setText(m.getName());
             ammount.setText(m.getAmmount());


        } catch (SQLException e) {
            e.printStackTrace();
        }

        final CheckBox c = (CheckBox) findViewById(R.id.cb_purchase);
        c.setChecked(m.isPurchased());
        c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (c.isChecked()) {
                    c.setText("Purchased");
                    c.setChecked(true);
                    try {
                        m.setPurchased(true);
                        m.setPurchasedStatus("Purchased");
                        getDatabaseHelper().getItemDao().update(m);
                        Log.i("Sta je u bazi", getDatabaseHelper().getItemDao().queryForAll().toString());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    c.setText("Not purchased");
                    c.setChecked(false);
                    try {
                        m.setPurchased(false);
                        m.setPurchasedStatus("Not purchased");
                        getDatabaseHelper().getItemDao().update(m);
                        Log.i("Sta je u bazi", getDatabaseHelper().getItemDao().queryForAll().toString());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        if (c.isChecked()) {
            c.setText("Purchased");
        } else {
            c.setText("Not purchased");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu0, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_update:



                try {

                    m.setName(name.getText().toString());
                    m.setAmmount(ammount.getText().toString());
                    getDatabaseHelper().getItemDao().update(m);

                    finish();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.action_delete:
                try {
                    getDatabaseHelper().getItemDao().delete(m);


                    finish();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }




    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }




}
