package com.example.va.todolist;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.example.va.todolist.db.DatabaseHelper;
import com.example.va.todolist.db.model.Item;
import com.example.va.todolist.db.model.ListI;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    public static String key = "ACT";
    private DatabaseHelper databaseHelper;
    private List<ListI> list;

    public enum Completed {
        COMPLETED("Completed"),
        NOT_COMPLETED("Not completed");

        private String text;

        Completed(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null)

        {
            setSupportActionBar(toolbar);
        }


        final ListView listView = (ListView) findViewById(R.id.lista_name);

       try {
            list = getDatabaseHelper().getListIDao().queryForAll();

            ListAdapter adapter = new ArrayAdapter<>(MainActivity.this, R.layout.list_item, list);
            listView.setAdapter(adapter);


           listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ListI p = (ListI) listView.getItemAtPosition(position);

                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra(key, p.getId());
                    startActivity(intent);
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void addItem(){

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_list_layout);
        Button add_list = (Button) dialog.findViewById(R.id.add_list);

        completedMainList();

        add_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                EditText name = (EditText) dialog.findViewById(R.id.Sname);
                ListI l = new ListI();

                l.setName(name.getText().toString());
                l.setComplete(Completed.NOT_COMPLETED.toString());

                try {
                    getDatabaseHelper().getListIDao().create(l);

                    Log.i("Sta je u bazi", getDatabaseHelper().getListIDao().queryForAll().toString());




                    refresh();
                    dialog.dismiss();


                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }


        });

        dialog.show();}

 /*   try {                                                                   da li ovako moze ?????

      QueryBuilder<Item, Integer> orderQb = getDatabaseHelper.getItemDao.queryBuilder();
        orderQb.where().eg("purchased", true);
        QueryBuilder<ListI, Integer> accountQb = ListIDao.queryBuilder();
// join with the order query
        List<ListI> listView = accountQb.leftJoin(orderQb).query();
    } catch (SQLException e) {
        e.printStackTrace();
    }

*/




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create:

                addItem();
                break;
            case R.id.action_update:

                break;
         }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();

        refresh();
    }



    private void refresh() {
        ListView listview = (ListView) findViewById(R.id.lista_name);

        if (listview != null){
            ArrayAdapter<ListI> adapter = (ArrayAdapter<ListI>) listview.getAdapter();

            if(adapter!= null)
            {
                try {
                    adapter.clear();
                    List<ListI> list = getDatabaseHelper().getListIDao().queryForAll();

                    adapter.addAll(list);

                    adapter.notifyDataSetChanged();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
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

    private void completedMainList() {
        for (ListI main : list) {
            for (Item items : main.getItems()) {
                if (!items.isPurchased()) {
                    main.setComplete(Completed.NOT_COMPLETED.toString());
                    break;
                } else {
                    main.setComplete(Completed.COMPLETED.toString());
                    break;
                }
            }
            try {
                getDatabaseHelper().getListIDao().update(main);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



}
