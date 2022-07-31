package com.example.agendadecontactos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.agendadecontactos.model.ContactModel;
import com.example.agendadecontactos.utilidades.Utilidades;

import java.util.ArrayList;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = "log_depuracion";

    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Utilidades.CREAR_TABLA_CONTACTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_CONTACTOS);
        onCreate(sqLiteDatabase);
    }

    public long AddContact(String contactId,
                           String contactName,
                           String contactDirection,
                           String contactPhone,
                           String contactTelephone,
                           String contactEmail,
                           String contactRegistrationDate,
                           String contactUpdateDate
                          ){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Utilidades.CAMPO_ID, contactId);
        contentValues.put(Utilidades.CAMPO_NAME, contactName);
        contentValues.put(Utilidades.CAMPO_DIRECTION, contactDirection);
        contentValues.put(Utilidades.CAMPO_PHONE, contactPhone);
        contentValues.put(Utilidades.CAMPO_TELEPHONE, contactTelephone);
        contentValues.put(Utilidades.CAMPO_EMAIL, contactEmail);
        contentValues.put(Utilidades.CAMPO_REGISTRATION_DATE, contactRegistrationDate);
        contentValues.put(Utilidades.CAMPO_UPDATE_DATE, contactUpdateDate);

        long id = sqLiteDatabase.insert(Utilidades.TABLA_CONTACTOS, null, contentValues);

        if (id != -1){
            Log.d(TAG, "AddContact: Successful");
        }else{
            Log.d(TAG, "AddContact: Failure");
        }

        sqLiteDatabase.close();

        return id;

    } // End AddUser


    public ArrayList<ContactModel> ShowUsers(String orderBy){

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + Utilidades.TABLA_CONTACTOS + " ORDER BY " + orderBy, null);

        ArrayList<ContactModel> contactArrayList = new ArrayList<>();

        if (cursor.moveToFirst()){
            do{
                contactArrayList.add(new ContactModel(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)
                ));

            }while (cursor.moveToNext());
        }
        sqLiteDatabase.close();

        return contactArrayList;
    } // End ShowUsers


    public ArrayList<ContactModel> SearchUsers(String stringQuery) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ Utilidades.TABLA_CONTACTOS +" WHERE "+ Utilidades.CAMPO_NAME + " LIKE '%" + stringQuery +"%'", null);

        ArrayList<ContactModel> contactArrayList = new ArrayList<>();

        if (cursor.moveToFirst()){
            do{
                contactArrayList.add(new ContactModel(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)
                ));

            }while (cursor.moveToNext());
        }
        sqLiteDatabase.close();

        return contactArrayList;
    }


    public void UpdateContact(String contactId,
                               String contactName,
                               String contactDirection,
                               String contactPhone,
                               String contactTelephone,
                               String contactEmail,
                               String contactRegistrationDate,
                               String contactUpdateDate
                            ){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Utilidades.CAMPO_NAME, contactName);
        contentValues.put(Utilidades.CAMPO_DIRECTION, contactDirection);
        contentValues.put(Utilidades.CAMPO_PHONE, contactPhone);
        contentValues.put(Utilidades.CAMPO_TELEPHONE, contactTelephone);
        contentValues.put(Utilidades.CAMPO_EMAIL, contactEmail);
        contentValues.put(Utilidades.CAMPO_REGISTRATION_DATE, contactRegistrationDate);
        contentValues.put(Utilidades.CAMPO_UPDATE_DATE, contactUpdateDate);

        sqLiteDatabase.update(Utilidades.TABLA_CONTACTOS, contentValues, Utilidades.CAMPO_ID + " = ?", new String[]{contactId});

        sqLiteDatabase.close();
    } // End UpdateContact


    public void DeleteContact(String id){

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        sqLiteDatabase.delete(Utilidades.TABLA_CONTACTOS,
                Utilidades.CAMPO_ID +" = ?",
                new String[]{id});

        sqLiteDatabase.close();

    } // End Delete Contact


    public void DeleteAllContacts(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + Utilidades.TABLA_CONTACTOS);
        sqLiteDatabase.close();
    } // End Delete All Contacts

} // End ConexionSQLiteHelper
