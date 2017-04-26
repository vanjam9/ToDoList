package com.example.va.todolist.db.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by va on 4/19/2017.
 */
@DatabaseTable(tableName = ListI.TABLE_NAME_USERS)
public class ListI {


    public static final String TABLE_NAME_USERS = "list";
    public static final String FIELD_NAME_ID ="id" ;
    public static final String FIELD_LIST_NAME ="nameList" ;
    public static final String FIELD_LIST_COMPLETE = "Complete";


    public static final String TABLE_ITEM_ITEMS = "item";


    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int Id;

    @DatabaseField(columnName = FIELD_LIST_NAME)
    private String Name;

    @DatabaseField(columnName = FIELD_LIST_COMPLETE)
    private String Complete;

    @ForeignCollectionField(columnName = ListI.TABLE_ITEM_ITEMS, eager = true)
    private ForeignCollection<Item> items;


    public ListI() {

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ForeignCollection<Item> getItems() {
        return items;
    }

    public void setItems(ForeignCollection<Item> items) {
        this.items = items;
    }

    public String getComplete() {
        return Complete;
    }

    public void setComplete(String complete) {
        Complete = complete;
    }

    @Override
    public String toString() {
        return "ListI " +

                " Name=" + Name    +
                ",  Complete= " + Complete
        ;
    }
}
