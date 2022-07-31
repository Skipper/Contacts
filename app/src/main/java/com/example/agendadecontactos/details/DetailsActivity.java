package com.example.agendadecontactos.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.example.agendadecontactos.R;

public class DetailsActivity extends AppCompatActivity {

    String interfaceContactId,
            contactName,
            contactDirection,
            contactPhone,
            contactTelephone,
            contactEmail;

    public static String TAG = "log_depuration_details_activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // recibir datos del activity 1
        Bundle bundle = this.getIntent().getExtras();

        if(bundle != null){
            interfaceContactId = bundle.getString(DetailsFragment.ARG_ID_ENTRADA_SELECIONADA);
            contactName = bundle.getString(DetailsFragment.ARG_NAME_ENTRADA_SELECIONADA);
            contactDirection = bundle.getString(DetailsFragment.ARG_DIRECTION_ENTRADA_SELECIONADA);
            contactPhone = bundle.getString(DetailsFragment.ARG_PHONE_ENTRADA_SELECIONADA);
            contactTelephone = bundle.getString(DetailsFragment.ARG_TELEPHONE_ENTRADA_SELECIONADA);
            contactEmail = bundle.getString(DetailsFragment.ARG_EMAIL_ENTRADA_SELECIONADA);

            LoadDetailFragment();

        }
        Log.d(TAG, "Fuera");
        Log.d(TAG, "interfaceContactId: "+interfaceContactId);
        Log.d(TAG, "contactName: "+contactName);
        Log.d(TAG, "contactDirection: "+contactDirection);
        Log.d(TAG, "contactPhone: "+contactPhone);
        Log.d(TAG, "contactTelephone: "+contactTelephone);
        Log.d(TAG, "contactEmail: "+contactEmail);

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, we can show the
            // dialog in-line with the list so we don't need this activity.
            // Aca deberia reenviar el contacto seleccionado al main activity
            //finish();
            //return;
        }

    } // End onCreate



    private void LoadDetailFragment() {
        Bundle bundleDetails = new Bundle();

        bundleDetails.putString(DetailsFragment.ARG_ID_ENTRADA_SELECIONADA,interfaceContactId);
        bundleDetails.putString(DetailsFragment.ARG_NAME_ENTRADA_SELECIONADA,contactName);
        bundleDetails.putString(DetailsFragment.ARG_DIRECTION_ENTRADA_SELECIONADA,contactDirection);
        bundleDetails.putString(DetailsFragment.ARG_PHONE_ENTRADA_SELECIONADA,contactPhone);
        bundleDetails.putString(DetailsFragment.ARG_TELEPHONE_ENTRADA_SELECIONADA,contactTelephone);
        bundleDetails.putString(DetailsFragment.ARG_EMAIL_ENTRADA_SELECIONADA,contactEmail);

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

    }

    /*---------------------------------------------------------------------------------------------------------------------------
    *                                                               M   E   T  O    D   O   S
    * ---------------------------------------------------------------------------------------------------------------------------*/

}