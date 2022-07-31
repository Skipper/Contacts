package com.example.agendadecontactos.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.agendadecontactos.ConexionSQLiteHelper;
import com.example.agendadecontactos.ContactAddActivity;
import com.example.agendadecontactos.R;

import com.example.agendadecontactos.adapter.ContactAdapter;
import com.example.agendadecontactos.interfaces.CallBackInterface;
import com.example.agendadecontactos.utilidades.Utilidades;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContactsFragment extends Fragment {

    public static String TAG = "log_depuration_contacts_fragment";

    String orderByNewest = Utilidades.CAMPO_REGISTRATION_DATE + " DESC";
    String orderByOldest = Utilidades.CAMPO_UPDATE_DATE + " ASC";
    String orderByNameAsc = Utilidades.CAMPO_NAME + " ASC";
    String orderByNameDesc = Utilidades.CAMPO_NAME + " DESC";

    String currentOrderState = orderByNewest;

    RecyclerView rvContacts;

    ConexionSQLiteHelper conexionSQLiteHelper;

    ConstraintLayout clContactsEmpty;
    FloatingActionButton fabContactAdd;

    private CallBackInterface callBackInterface;


    /* 1 */
    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /* 2 */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    } // End onCreateView


    /* 3 */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvContacts = view.findViewById(R.id.rv_main_contactos);
        Initialization(view);
        LoadContacts(orderByNewest);

        fabContactAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (getActivity(), ContactAddActivity.class));
            }
        }); // End FAB

    } // End onViewCreated


    private void Initialization(View view) {

        conexionSQLiteHelper = new ConexionSQLiteHelper(getContext(), Utilidades.DB_NAME, null, Utilidades.DB_VERSION);
        clContactsEmpty     = view.findViewById(R.id.cl_main_contacts_empty);
        fabContactAdd       = view.findViewById(R.id.fab_fragment_contacts_add);

    }


    public void LoadContacts(String orderBy) {
        currentOrderState = orderBy;
        ContactAdapter contactAdapter = new ContactAdapter(getContext(), conexionSQLiteHelper.ShowUsers(orderBy));
        rvContacts.setAdapter(contactAdapter);

        /* if (conexionSQLiteHelper.ShowUsers(orderByNewest).size() <= 0){
            rvContacts.setVisibility(View.GONE);
            clContactsEmpty.setVisibility(View.VISIBLE);
        }else{
            rvContacts.setVisibility(View.VISIBLE);
            clContactsEmpty.setVisibility(View.GONE);
        } */

        rvContacts.setLayoutManager(new GridLayoutManager(getContext(), 1));

        contactAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int positionContacts = rvContacts.getChildAdapterPosition(view);

                Log.d(TAG,"*** CONTACTS ***");
                Log.d(TAG,"Posicion: "+positionContacts);
                Log.d(TAG,Utilidades.CAMPO_ID+" "+conexionSQLiteHelper.ShowUsers(orderBy).get(positionContacts).getContactId());
                Log.d(TAG,Utilidades.CAMPO_NAME+" "+conexionSQLiteHelper.ShowUsers(orderBy).get(positionContacts).getContactName());
                Log.d(TAG,Utilidades.CAMPO_PHONE+" "+conexionSQLiteHelper.ShowUsers(orderBy).get(positionContacts).getContactPhone());

                if (callBackInterface != null) {
                    callBackInterface.callBackMethod(
                            conexionSQLiteHelper.ShowUsers(orderBy).get(positionContacts).getContactId(),
                            conexionSQLiteHelper.ShowUsers(orderBy).get(positionContacts).getContactName(),
                            conexionSQLiteHelper.ShowUsers(orderBy).get(positionContacts).getContactDirection(),
                            conexionSQLiteHelper.ShowUsers(orderBy).get(positionContacts).getContactPhone(),
                            conexionSQLiteHelper.ShowUsers(orderBy).get(positionContacts).getContactTelephone(),
                            conexionSQLiteHelper.ShowUsers(orderBy).get(positionContacts).getContactEmail()
                            );
                    Log.d(TAG,Utilidades.CAMPO_NAME+" "+conexionSQLiteHelper.ShowUsers(orderBy).get(positionContacts).getContactName());
                }

            }
        });

        rvContacts.setItemAnimator(new DefaultItemAnimator());

    } // End LoadContacts

    public void SearchContact(String stringQuery) {
        ContactAdapter contactAdapter = new ContactAdapter(getContext(), conexionSQLiteHelper.SearchUsers(stringQuery));
        rvContacts.setAdapter(contactAdapter);
        rvContacts.setLayoutManager(new GridLayoutManager(getContext(), 1));

        /* contactAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int positionSearch = rvContacts.getChildAdapterPosition(view);

                Log.d(TAG,"*** SEARCH ***");
                Log.d(TAG,"Posicion: "+positionSearch);
                Log.d(TAG,Utilidades.CAMPO_ID+" "+conexionSQLiteHelper.ShowUsers().get(positionSearch).getContactId());
                Log.d(TAG,Utilidades.CAMPO_NAME+" "+conexionSQLiteHelper.ShowUsers().get(positionSearch).getContactName());
                Log.d(TAG,Utilidades.CAMPO_PHONE+" "+conexionSQLiteHelper.ShowUsers().get(positionSearch).getContactPhone());

                Toast.makeText(getContext(), "YYY", Toast.LENGTH_SHORT).show();
                if (callBackInterface != null) {
                    callBackInterface.callBackMethod(
                            conexionSQLiteHelper.ShowUsers().get(positionSearch).getContactId(),
                            conexionSQLiteHelper.ShowUsers().get(positionSearch).getContactName(),
                            conexionSQLiteHelper.ShowUsers().get(positionSearch).getContactDirection(),
                            conexionSQLiteHelper.ShowUsers().get(positionSearch).getContactPhone(),
                            conexionSQLiteHelper.ShowUsers().get(positionSearch).getContactTelephone(),
                            conexionSQLiteHelper.ShowUsers().get(positionSearch).getContactEmail()
                    );
                    Log.d(TAG,Utilidades.CAMPO_NAME+" "+conexionSQLiteHelper.ShowUsers().get(positionSearch).getContactName());
                }
                //ARG_NAME_SELECTED = contactModelList.get(position).getContactName().toString();
                //conexionSQLiteHelper.DeleteContact(position);
            }
        });
        //rvContacts.addItemDecoration(new ItemDecorationSeparator(this, ItemDecorationSeparator.VERTICAL_LIST));
        rvContacts.setItemAnimator(new DefaultItemAnimator());

        Log.d(TAG,"Searchs: "+conexionSQLiteHelper.ShowUsers()); */

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            callBackInterface = (CallBackInterface) context;
        } catch (ClassCastException exception){
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    } // End onAttach


    @Override
    public void onDetach() {
        super.onDetach();
        callBackInterface = null;
    } // End onDetach

}