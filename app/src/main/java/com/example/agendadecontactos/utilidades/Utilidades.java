package com.example.agendadecontactos.utilidades;

/**
 * Created by DAME on 29/11/2021
 */

public class Utilidades {

    public static final String DB_NAME = "bd_contacts";
    public static final int DB_VERSION = 1;

    // Constantes campos tabla usuario
    public static final String TABLA_CONTACTOS      = "contacts";

    public static final String CAMPO_ID                     = "id";
    public static final String CAMPO_NAME                   = "name";
    public static final String CAMPO_DIRECTION              = "direction";
    public static final String CAMPO_PHONE                  = "phone";
    public static final String CAMPO_TELEPHONE              = "telephone";
    public static final String CAMPO_EMAIL                  = "email";
    public static final String CAMPO_REGISTRATION_DATE      = "registration";
    public static final String CAMPO_UPDATE_DATE            = "updateDate";

    public static final String CREAR_TABLA_CONTACTOS = "CREATE TABLE "
            +TABLA_CONTACTOS+" ("+CAMPO_ID+" TEXT, "
                                 +CAMPO_NAME+" TEXT, "
                                 +CAMPO_DIRECTION+" TEXT, "
                                 +CAMPO_PHONE+" TEXT, "
                                 +CAMPO_TELEPHONE+" TEXT, "
                                 +CAMPO_EMAIL+" TEXT, "
                                 +CAMPO_REGISTRATION_DATE+" TEXT, "
                                 +CAMPO_UPDATE_DATE+" TEXT"
                                 +")";

}
