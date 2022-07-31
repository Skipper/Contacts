package com.example.agendadecontactos.main;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.agendadecontactos.ConexionSQLiteHelper;
import com.example.agendadecontactos.R;
import com.example.agendadecontactos.details.DetailsActivity;
import com.example.agendadecontactos.details.DetailsFragment;
import com.example.agendadecontactos.interfaces.CallBackInterface;
import com.example.agendadecontactos.model.ContactModel;
import com.example.agendadecontactos.utilidades.Utilidades;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CallBackInterface {

    public static String TAG = "log_depuration_main_activity";

    private static final int REQUEST_PERMISSION_EXPORT_CONTACTS = 100;
    private static final int REQUEST_PERMISSION_IMPORT_CONTACTS = 200;

    private ConexionSQLiteHelper conexionSQLiteHelper;

    private ContactsFragment contactsFragment;

    private final String orderByNewest = Utilidades.CAMPO_REGISTRATION_DATE + " DESC";
    private final String orderByOldest = Utilidades.CAMPO_UPDATE_DATE + " ASC";
    private final String orderByAscending = Utilidades.CAMPO_NAME + " ASC";
    private final String orderByDescending = Utilidades.CAMPO_NAME + " DESC";

    private final String currentOrderState = orderByNewest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tb_activity_main);
        setSupportActionBar(toolbar);

        CargandoFragmentos();
        ShowFragments();

    } // End onCreate

    /*---------------------------------------------------------------------------------------------------------------------------
     *                                                               M   E   T  O    D   O   S
     * ---------------------------------------------------------------------------------------------------------------------------*/

    private void CargandoFragmentos() {

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){

            FragmentManager fragmentManager = getSupportFragmentManager();
            contactsFragment = (ContactsFragment) fragmentManager.findFragmentById(R.id.fragment_container_contacts);

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // contactsFragment.setCallBackInterface(this);
            fragmentTransaction.replace(R.id.fragment_container_contacts, contactsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

            conexionSQLiteHelper = new ConexionSQLiteHelper(this, Utilidades.DB_NAME, null, Utilidades.DB_VERSION);

            ArrayList<ContactModel> contactModelArrayList = conexionSQLiteHelper.ShowUsers(currentOrderState);

            if (!contactModelArrayList.isEmpty() && contactModelArrayList.get(0).getContactId() != null){

                FragmentManager fragmentManager = getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString(DetailsFragment.ARG_ID_ENTRADA_SELECIONADA, contactModelArrayList.get(0).getContactId());
                bundle.putString(DetailsFragment.ARG_NAME_ENTRADA_SELECIONADA, contactModelArrayList.get(0).getContactName());
                bundle.putString(DetailsFragment.ARG_DIRECTION_ENTRADA_SELECIONADA, contactModelArrayList.get(0).getContactDirection());
                bundle.putString(DetailsFragment.ARG_PHONE_ENTRADA_SELECIONADA, contactModelArrayList.get(0).getContactPhone());
                bundle.putString(DetailsFragment.ARG_TELEPHONE_ENTRADA_SELECIONADA, contactModelArrayList.get(0).getContactTelephone());
                bundle.putString(DetailsFragment.ARG_EMAIL_ENTRADA_SELECIONADA, contactModelArrayList.get(0).getContactEmail());

                Log.d(TAG,"*** MainCallBack ***: "+contactModelArrayList.get(0).getContactName());

                // Create new fragment and transaction
                Fragment newFragment = new DetailsFragment();
                newFragment.setArguments(bundle);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.fragment_container_details, newFragment);
                //transaction.addToBackStack(null);
                // Commit the transaction
                transaction.commit();


            } // End If

        }

    }

    private void ShowFragments() {
        // Fragment Contacts
        FragmentManager fragmentManager = getSupportFragmentManager();
        contactsFragment = (ContactsFragment) fragmentManager.findFragmentById(R.id.fragment_container_contacts);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // contactsFragment.setCallBackInterface(this);
        fragmentTransaction.replace(R.id.fragment_container_contacts, contactsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


    @Override
    public void callBackMethod(String interfaceContactId,
                               String  contactName,
                               String  contactDirection,
                               String  contactPhone,
                               String  contactTelephone,
                               String  contactEmail) {

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

            Bundle bundleDetails = new Bundle();

            bundleDetails.putString(DetailsFragment.ARG_ID_ENTRADA_SELECIONADA, interfaceContactId);
            bundleDetails.putString(DetailsFragment.ARG_NAME_ENTRADA_SELECIONADA, contactName);
            bundleDetails.putString(DetailsFragment.ARG_DIRECTION_ENTRADA_SELECIONADA, contactDirection);
            bundleDetails.putString(DetailsFragment.ARG_PHONE_ENTRADA_SELECIONADA, contactPhone);
            bundleDetails.putString(DetailsFragment.ARG_TELEPHONE_ENTRADA_SELECIONADA, contactTelephone);
            bundleDetails.putString(DetailsFragment.ARG_EMAIL_ENTRADA_SELECIONADA, contactEmail);

            Log.d(TAG, "Reenviando");
            Log.d(TAG, "interfaceContactId: "+interfaceContactId);
            Log.d(TAG, "contactName: "+contactName);

            // Create new fragment and transaction
            Fragment newFragment = new DetailsFragment();
            newFragment.setArguments(bundleDetails);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack
            transaction.replace(R.id.fragment_container_details, newFragment);
            //transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();

        } else {

            Intent detailIntent = new Intent(this, DetailsActivity.class);

            detailIntent.putExtra(DetailsFragment.ARG_ID_ENTRADA_SELECIONADA, interfaceContactId);
            detailIntent.putExtra(DetailsFragment.ARG_NAME_ENTRADA_SELECIONADA, contactName);
            detailIntent.putExtra(DetailsFragment.ARG_DIRECTION_ENTRADA_SELECIONADA, contactDirection);
            detailIntent.putExtra(DetailsFragment.ARG_PHONE_ENTRADA_SELECIONADA, contactPhone);
            detailIntent.putExtra(DetailsFragment.ARG_TELEPHONE_ENTRADA_SELECIONADA, contactTelephone);
            detailIntent.putExtra(DetailsFragment.ARG_EMAIL_ENTRADA_SELECIONADA, contactEmail);

            startActivity(detailIntent);
        }

    } // End callBackMethod


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint(getString(R.string.search_main_activity_hint)); // "Buscar datos aquí ..."

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                contactsFragment.SearchContact(query); // Search is expanded
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                contactsFragment.SearchContact(newText); // Search is collapse
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    } // End onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_order_by:
                OrderBy();
                return true;

            case R.id.action_delete_all:
                conexionSQLiteHelper.DeleteAllContacts();
                onResume();
                return true;

            case R.id.action_export:
                PedirPermisos();
                ExportContactsCSV(); // Supuestamente lectura estaba otorgado para versiones 16 o posteriores
                return true;

            case R.id.action_import:
                PedirPermisos();
                ImportContactsCSV();
                onResume();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        } // End Switch
    } // End onOptionsItemSelected


    /* Metodo 1 */
    private void PedirPermisos() {
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 0);
        }
    }

    private void ExportContactsCSV() {

        File file = new File(Environment.getExternalStorageDirectory() + "/ExportarSQLiteCSV");
        String archivo = file.toString() + "/" + "Contacts.csv";

        boolean isCreate = false;
        if(!file.exists()){
            isCreate = file.mkdir();
        }
        Log.d(TAG,"Boolean isCreate: "+isCreate);

        ArrayList<ContactModel> contactArrayList = new ArrayList<>();
        contactArrayList.clear();
        contactArrayList = conexionSQLiteHelper.ShowUsers(orderByOldest);

        try {
            FileWriter fileWriter = new FileWriter(archivo);

            for(int i = 0; i <contactArrayList.size(); ++i){

                fileWriter.append(String.valueOf(contactArrayList.get(i).getContactId()));
                fileWriter.append(",");

                fileWriter.append(String.valueOf(contactArrayList.get(i).getContactName()));
                fileWriter.append(",");

                fileWriter.append(String.valueOf(contactArrayList.get(i).getContactDirection()));
                fileWriter.append(",");

                fileWriter.append(String.valueOf(contactArrayList.get(i).getContactPhone()));
                fileWriter.append(",");

                fileWriter.append(String.valueOf(contactArrayList.get(i).getContactTelephone()));
                fileWriter.append(",");

                fileWriter.append(String.valueOf(contactArrayList.get(i).getContactEmail()));
                fileWriter.append(",");

                fileWriter.append(String.valueOf(contactArrayList.get(i).getContactRegistrationDate()));
                fileWriter.append(",");

                fileWriter.append(String.valueOf(contactArrayList.get(i).getContactUpdateDate()));
                fileWriter.append("\n");

            }
            fileWriter.flush();
            fileWriter.close();

            Toast.makeText(this, getString(R.string.main_activity_toast_export_successful)+file, Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.main_activity_toast_export_error)+e, Toast.LENGTH_LONG).show();
        }

    } // End CSV

    private void ImportContactsCSV() {

        String archivo = Environment.getExternalStorageDirectory() + "/ExportarSQLiteCSV" + "/" + "Contacts.csv";
        File file = new File(archivo);

        if (file.exists()){
            try{
                CSVReader csvReader = new CSVReader(new FileReader(file.getAbsolutePath()));
                String [] nextLine;
                while((nextLine = csvReader.readNext()) != null){
                    String contactId = nextLine[0];
                    String contactName = nextLine[1];
                    String contactDirection = nextLine[2];
                    String contactPhone = nextLine[3];
                    String contactTelephone = nextLine[4];
                    String contactEmail = nextLine[5];
                    String contactRegistrationDate = nextLine[6];
                    String contactUpdateDate = nextLine[7];

                    long id = conexionSQLiteHelper.AddContact(
                            "" + contactId,
                            "" + contactName,
                            "" + contactDirection,
                            "" + contactPhone,
                            "" + contactTelephone,
                            "" + contactEmail,
                            "" + contactRegistrationDate,
                            "" + contactUpdateDate
                    );
                    Log.d(TAG,"Contact: "+id);
                }
                Toast.makeText(this, getString(R.string.main_activity_toast_import_successful)+file, Toast.LENGTH_SHORT).show();

            }catch (Exception exception){
                Toast.makeText(this, getString(R.string.main_activity_toast_import_error)+exception, Toast.LENGTH_LONG).show();

            }
        }

    } // End CSV

    private void OrderBy() {

        String [] options = {getString(R.string.main_activity_order_by_option_one),
                getString(R.string.main_activity_order_by_option_two),
                getString(R.string.main_activity_order_by_option_three),
                getString(R.string.main_activity_order_by_option_four)
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.main_activity_order_by_title));

        builder.setItems(options, (dialogInterface, i) -> {

            switch (i){
                case 0:
                    contactsFragment.LoadContacts(orderByAscending);
                    break;
                case 1:
                    contactsFragment.LoadContacts(orderByDescending);
                    break;
                case 2:
                    contactsFragment.LoadContacts(orderByNewest);
                    break;
                case 3:
                    contactsFragment.LoadContacts(orderByOldest);
                    break;
            }
        }).show();
    } // End OrderBy


    /* Metodo 2 */
    private void CheckPermissionsToExport() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.d("Permission", "Permission granted");
            ExportContactsCSV();
        } else {
            Log.d("Permission", "Approach and Precision permit required");

            // verificar si el usuario anteriormente rechazó el permiso
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Log.d("Permission", "The user previously rejected the request.");
            } else {
                Log.d("Permission", "Request permission");
            }

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, REQUEST_PERMISSION_EXPORT_CONTACTS);
        }

    } // End CheckPermissionsToExport

    private void CheckPermissionsToImport() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.d("Permission", "Permission granted");
            ImportContactsCSV();
            onResume();
        } else {
            Log.d("Permission", "Approach and Precision permit required");

            // verificar si el usuario anteriormente rechazó el permiso
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Log.d("Permission", "The user previously rejected the request.");
            } else {
                Log.d("Permission", "Request permission");
            }

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, REQUEST_PERMISSION_IMPORT_CONTACTS);
        }

    } // End CheckPermissionsToImport

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_PERMISSION_EXPORT_CONTACTS){
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Permission", "Permission granted (request)");
                ExportContactsCSV();
            } else {
                Log.d("Permission", "Permission denied (request)");
                // Averiguar si anteriormente se rechazaron los permisos
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //"You need to enable permission to use this App"
                    new AlertDialog.Builder(MainActivity.this).setMessage(getString(R.string.btn_main_activity_try_again_message))
                            .setPositiveButton(getString(R.string.btn_main_activity_try_again), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                                    }, REQUEST_PERMISSION_EXPORT_CONTACTS);
                                }
                            })
                            .setNegativeButton(getString(R.string.btn_main_activity_try_again_cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // Leave
                                    Log.d("Permission", "User leave?");
                                }
                            }).show();
                } else {
                    Log.d("Permission", "You need to ablle permission manually");
                }
            }
        }
        if (requestCode == REQUEST_PERMISSION_IMPORT_CONTACTS){
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Permission", "Permission granted (request)");
                ExportContactsCSV();
            } else {
                Log.d("Permission", "Permission denied (request)");
                // Averiguar si anteriormente se rechazaron los permisos
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    //"You need to enable permission to use this App"
                    new AlertDialog.Builder(MainActivity.this).setMessage(getString(R.string.btn_main_activity_try_again_message))
                            .setPositiveButton(getString(R.string.btn_main_activity_try_again), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {
                                            Manifest.permission.READ_EXTERNAL_STORAGE
                                    }, REQUEST_PERMISSION_IMPORT_CONTACTS);
                                }
                            })
                            .setNegativeButton(getString(R.string.btn_main_activity_try_again_cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // Leave
                                    Log.d("Permission", "User leave?");
                                }
                            }).show();
                } else {
                    Log.d("Permission", "You need to ablle permission manually");
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onResume() {
        super.onResume();
        contactsFragment.LoadContacts(currentOrderState);
    }

}