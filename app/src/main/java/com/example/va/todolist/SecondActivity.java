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
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.va.todolist.db.DatabaseHelper;
import com.example.va.todolist.db.model.Item;
import com.example.va.todolist.db.model.ListI;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by va on 4/20/2017.
 */

public class SecondActivity extends AppCompatActivity{

    private EditText name;
    private ListI l;
    private DatabaseHelper databaseHelper;
    public static String key_detail = "BCT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_item);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        int key = getIntent().getExtras().getInt(MainActivity.key);

        try {
            l = getDatabaseHelper().getListIDao().queryForId(key);

            name = (EditText) findViewById(R.id.Sname);


            name.setText(l.getName());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        final ListView listView = (ListView) findViewById(R.id.item_ammount);

        try {
            List<Item> list = getDatabaseHelper().getItemDao().queryBuilder()
                    .where()
                    .eq(Item.FIELD_NAME_USER, l.getId())
                    .query();

            ListAdapter adapter = new ArrayAdapter<>(this, R.layout.list_item, list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Item m = (Item) listView.getItemAtPosition(position);
                    Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                    intent.putExtra(key_detail, m.getmId());
                    startActivity(intent);

                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

   private void refresh() {
        ListView listview = (ListView) findViewById(R.id.item_ammount);

        if (listview != null){
            ArrayAdapter<Item> adapter = (ArrayAdapter<Item>) listview.getAdapter();

            if(adapter!= null)
            {
                try {
                    adapter.clear();
                    List<Item> list = getDatabaseHelper().getItemDao().queryBuilder()
                            .where()
                            .eq(Item.FIELD_NAME_USER, l.getId())
                            .query();

                    adapter.addAll(list);

                    adapter.notifyDataSetChanged();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_create:

                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.dialog_add_item);

                Button add = (Button) dialog.findViewById(R.id.add_item);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText name = (EditText) dialog.findViewById(R.id.item_name);
                        EditText ammount = (EditText) dialog.findViewById(R.id.Ammount);


                        Item m = new Item();
                        m.setName(name.getText().toString());
                        m.setAmmount(ammount.getText().toString());
                        m.setmListI(l);

                        try {
                            getDatabaseHelper().getItemDao().create(m);
                            Log.i("Sta je u bazi", getDatabaseHelper().getItemDao().queryForAll().toString());


                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        refresh();



                        dialog.dismiss();
                    }
                });

                dialog.show();

                break;
            case R.id.action_update:

                l.setName(name.getText().toString());


                try {
                    getDatabaseHelper().getListIDao().update(l);

                    finish();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.action_delete:
                try {
                    getDatabaseHelper().getListIDao().delete(l);


                    finish();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();

        refresh();
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
