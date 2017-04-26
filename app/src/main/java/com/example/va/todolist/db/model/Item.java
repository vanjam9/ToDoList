package com.example.va.todolist.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by va on 4/19/2017.
 */





@DatabaseTable(tableName = Item.TABLE_NAME_ITEM)
public class Item {


    public static final String TABLE_NAME_ITEM = "item";
    public static final String FIELD_NAME_ID ="id" ;
    public static final String FIELD_ITEM_NAME ="name" ;
    public static final String FIELD_ITEM_AMMOUNT = "ammount";
    public static final String TABLE_FIELD_PURCHASED = "purchased";
    public static final String TABLE_FIELD_PURCHASED_STATUS = "purchased_status";

    public static final String FIELD_NAME_USER ="list" ;


    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int mId;

    @DatabaseField(columnName = FIELD_ITEM_NAME)
    private String Name;

    @DatabaseField(columnName = FIELD_ITEM_AMMOUNT)
    private String Ammount;



    @DatabaseField(columnName = TABLE_FIELD_PURCHASED)
    private boolean purchased;

    @DatabaseField (columnName = TABLE_FIELD_PURCHASED_STATUS)
    private String purchasedStatus;



    @DatabaseField(columnName = FIELD_NAME_USER, foreign = true, foreignAutoRefresh = true)
    private ListI mListI;


    public Item() {

    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAmmount() {
        return Ammount;
    }

    public void setAmmount(String ammount) {
        Ammount = ammount;
    }

    public ListI getmListI() {
        return mListI;
    }

    public void setmListI(ListI mListI) {
        this.mListI = mListI;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public String getPurchasedStatus() {
        return purchasedStatus;
    }

    public void setPurchasedStatus(String purchasedStatus) {
        this.purchasedStatus = purchasedStatus;
    }

    @Override
    public String toString() {
        return "Item  :" +

                " Name= " + Name  +
                ", Ammount= " + Ammount +
                " purchased= " + purchased ;
    }
}
