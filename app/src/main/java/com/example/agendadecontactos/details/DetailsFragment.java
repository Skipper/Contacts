package com.example.agendadecontactos.details;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agendadecontactos.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class DetailsFragment extends Fragment implements View.OnClickListener {

    public static String TAG = "log_depuration_details_fragment";

    // Esta solo la declaran en Details Fragment
    public static final String ARG_ID_ENTRADA_SELECIONADA = "contactId";
    public static final String ARG_NAME_ENTRADA_SELECIONADA = "contactName";
    public static final String ARG_DIRECTION_ENTRADA_SELECIONADA = "contactDirection";
    public static final String ARG_PHONE_ENTRADA_SELECIONADA = "contactPhone";
    public static final String ARG_TELEPHONE_ENTRADA_SELECIONADA = "contactTelephone";
    public static final String ARG_EMAIL_ENTRADA_SELECIONADA = "contactEmail";

    private static final int REQUEST_PERMISSION_CALL_PHONE = 100;

    String contactId,
           contactName,
           contactDirection,
           contactPhone,
           contactTelephone,
           contactEmail;

    private TextView tvInitial;
    private CollapsingToolbarLayout ctlName;

    private CardView cvName,
                     cvDirection,
                     cvPhone,
                     cvTelephone,
                     cvEmail;

    private TextView tvName,
                     tvDirection,
                     tvPhone,
                     tvTelephone,
                     tvEmail;

    /* 1 */
    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(String contactId,
                                              String contactName,
                                              String contactDirection,
                                              String contactPhone,
                                              String contactTelephone,
                                              String contactEmail) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID_ENTRADA_SELECIONADA, contactId);
        args.putString(ARG_NAME_ENTRADA_SELECIONADA, contactName);
        args.putString(ARG_DIRECTION_ENTRADA_SELECIONADA, contactDirection);
        args.putString(ARG_PHONE_ENTRADA_SELECIONADA, contactPhone);
        args.putString(ARG_TELEPHONE_ENTRADA_SELECIONADA, contactTelephone);
        args.putString(ARG_EMAIL_ENTRADA_SELECIONADA, contactEmail);
        // Pendiente llamar para comprobar

        fragment.setArguments(args);
        return fragment;
    }

    /* 2 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Metodo 3 de recibir datos del activity */
        if (getArguments() != null) {
            contactId                = getArguments().getString(ARG_ID_ENTRADA_SELECIONADA);
            contactName              = getArguments().getString(ARG_NAME_ENTRADA_SELECIONADA);
            contactDirection         = getArguments().getString(ARG_DIRECTION_ENTRADA_SELECIONADA);
            contactPhone             = getArguments().getString(ARG_PHONE_ENTRADA_SELECIONADA);
            contactTelephone         = getArguments().getString(ARG_TELEPHONE_ENTRADA_SELECIONADA);
            contactEmail             = getArguments().getString(ARG_EMAIL_ENTRADA_SELECIONADA);

            Log.d(TAG, "Details Id: "         +contactId);
            Log.d(TAG, "Details Name: "       +contactName);
            Log.d(TAG, "Details Direction: "  +contactDirection);
            Log.d(TAG, "Details Phone: "      +contactPhone);
            Log.d(TAG, "Details Telephone: "  +contactTelephone);
            Log.d(TAG, "Details Email: "      +contactEmail);
        }

    }

    /* 3 */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    } // End onCreateView

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        InitResources(view);

        if(contactId != null &&
           contactName != null &&
           contactDirection != null &&
           contactPhone != null &&
           contactTelephone != null &&
           contactEmail != null){

            MostrandoDatosRecibidos();
        }

    } // End onViewCreated

    private void MostrandoDatosRecibidos() {
        // Recibiendo de activity 2 por parametros
        Log.d(TAG, "Details Id: "         +contactId);
        Log.d(TAG, "Details Name: "       +contactName);
        Log.d(TAG, "Details Direction: "  +contactDirection);
        Log.d(TAG, "Details Phone: "      +contactPhone);
        Log.d(TAG, "Details Telephone: "  +contactTelephone);
        Log.d(TAG, "Details Email: "      +contactEmail);

        String contactInitial;
        if (contactName.length() > 0) {
            contactInitial = String.valueOf(contactName.charAt(0));
        }else {
            contactInitial = "NA";
        }
        tvInitial.setText(contactInitial);
        ctlName.setTitle(contactName);
        tvName.setText(contactName); // Gone
        tvDirection.setText(contactDirection);
        tvPhone.setText(contactPhone);
        tvTelephone.setText(contactTelephone);
        tvEmail.setText(contactEmail);
    }

    private void InitResources(View view) {
        tvInitial     = view.findViewById(R.id.tv_fragment_details_initial);
        ctlName       = view.findViewById(R.id.ctl_fragment_details_container);

        cvName        = view.findViewById(R.id.cv_fragment_details_contact_name);
        tvName        = view.findViewById(R.id.tv_fragment_details_name);
        cvName.setOnClickListener(this);

        cvDirection   = view.findViewById(R.id.cv_fragment_details_contact_direction);
        tvDirection   = view.findViewById(R.id.tv_fragment_details_direction);
        cvDirection.setOnClickListener(this);

        cvPhone       = view.findViewById(R.id.cv_fragment_details_contact_phone);
        tvPhone       = view.findViewById(R.id.tv_fragment_details_phone);
        cvPhone.setOnClickListener(this);

        cvTelephone   = view.findViewById(R.id.cv_fragment_details_contact_telephone);
        tvTelephone   = view.findViewById(R.id.tv_fragment_details_telephone);
        cvTelephone.setOnClickListener(this);

        cvEmail       = view.findViewById(R.id.cv_fragment_details_contact_email);
        tvEmail       = view.findViewById(R.id.tv_fragment_details_email);
        cvEmail.setOnClickListener(this);
    }

    /* 4 */

    /*---------------------------------------------------------------------------------------------------------------------------
     *                                                               M   E   T  O    D   O   S
     * ---------------------------------------------------------------------------------------------------------------------------*/

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.cv_fragment_details_contact_direction:
                ShowDirectionUser(contactDirection);
                break;

            case R.id.cv_fragment_details_contact_phone:

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    Log.d("Permission", "API < 23");
                    CallUser(contactPhone);
                } else { // API >= 23
                    Log.d("Permission", "API >= 23");
                    VerificarPermisosCalls(); // Serie de condiciones antes de realizar llamada
                }
                break;

            case R.id.cv_fragment_details_contact_telephone:

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    Log.d("Permission", "API < 23");
                    CallUser(contactPhone);
                } else { // API >= 23
                    Log.d("Permission", "API >= 23");
                    VerificarPermisosCalls(); // Serie de condiciones antes de realizar llamada
                }
                break;

            case R.id.cv_fragment_details_contact_email:
                SendEmailUser(contactEmail);
                break;

        } // End Switch
    } // End onClick

    private void ShowDirectionUser(String contactDirection) {
        Uri location = Uri.parse("http://maps.google.co.in/maps?q="+contactDirection);
        Intent map = new Intent(Intent.ACTION_VIEW, location);
        startActivity(map);
    }

    private void VerificarPermisosCalls() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Log.d("Permission", "Permission granted");
            CallUser(contactPhone);
        } else {
            Log.d("Permission", "Approach and Precision permit required");

            // verificar si el usuario anteriormente rechazó el permiso
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CALL_PHONE)) {
                Log.d("Permission", "The user previously rejected the request.");
            } else {
                Log.d("Permission", "Request permission");
            }

            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.CALL_PHONE
            }, REQUEST_PERMISSION_CALL_PHONE);
        }

    } // End verificar permiso

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CALL_PHONE){
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Permission", "Permission granted (request)");
                CallUser(contactPhone);
            } else {
                Log.d("Permission", "Permission denied (request)");
                // Averiguar si anteriormente se rechazaron los permisos
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CALL_PHONE)) {
                    //"You need to enable permission to use this App"
                    new AlertDialog.Builder(getContext()).setMessage(getString(R.string.btn_details_try_again_message_call))
                            .setPositiveButton(getString(R.string.btn_details_try_again_call), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCompat.requestPermissions(getActivity(), new String[] {
                                            Manifest.permission.CALL_PHONE
                                    }, REQUEST_PERMISSION_CALL_PHONE);
                                }
                            })
                            .setNegativeButton(getString(R.string.btn_details_try_again_cancel), new DialogInterface.OnClickListener() {
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
    } // onRequestPermissionResult

    private void CallUser(String numberPhone){
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+ numberPhone)));
    }

    private void SendEmailUser(String emailUser){

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailUser});
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.message_details_send_email_header));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.message_details_send_email_body));

        startActivity(Intent.createChooser(intent, getString(R.string.btn_details_send_email)));

    }

}