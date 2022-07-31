package com.example.agendadecontactos.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agendadecontactos.ConexionSQLiteHelper;
import com.example.agendadecontactos.R;
import com.example.agendadecontactos.editable.ContactEditActivity;
import com.example.agendadecontactos.main.MainActivity;
import com.example.agendadecontactos.model.ContactModel;
import com.example.agendadecontactos.utilidades.Utilidades;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>
        implements View.OnClickListener {

    Context context;
    private ArrayList<ContactModel> contactModelList;
    private View.OnClickListener listener;

    public ContactAdapter(Context context, ArrayList<ContactModel> contactModelList) {
        this.context = context;
        this.contactModelList = contactModelList;
    }

    public ContactAdapter() {
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contactos, parent, false);

        ContactViewHolder contactViewHolder = new ContactViewHolder(view);
        view.setOnClickListener(this);

        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        ContactModel contactModel = contactModelList.get(position);

        holder.bindContacts(contactModel);

    }

    @Override
    public int getItemCount() {
        return contactModelList.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onClick(view);
   }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {

        private static final String TAG = "log_deputation_adapter";

        private TextView contactInitial,
                contactName,
                contactPhone;
        private TextView contactOptions;
        private ConexionSQLiteHelper conexionSQLiteHelper;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            contactInitial      = itemView.findViewById(R.id.tv_item_contact_name_initial);
            contactName         = itemView.findViewById(R.id.tv_item_contact_name);
            contactPhone        = itemView.findViewById(R.id.tv_item_contact_phone);

            contactOptions      = itemView.findViewById(R.id.tv_item_contact_options);
        }

        public void bindContacts(ContactModel contactModel) {

            stringContactID         = contactModel.getContactId();
            stringContactName       = contactModel.getContactName();
            stringContactDirection  = contactModel.getContactDirection();
            stringContactPhone      = contactModel.getContactPhone();
            stringContactTelephone  = contactModel.getContactTelephone();
            stringContactEmail      = contactModel.getContactEmail();

            String iniciales;
            if (contactModel.getContactName().length() > 0) {
                iniciales = String.valueOf(contactModel.getContactName().charAt(0));
                Log.d(TAG,"Iniciales: "+iniciales);
            }else {
                iniciales = "NA";
            }
            contactInitial.setText(iniciales);
            contactName.setText(contactModel.getContactName());
            contactPhone.setText(contactModel.getContactPhone());

            contactOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowPopupMenu(view);

                }
            });

        } // End bindContacts

        private void ShowPopupMenu (View view){
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();

            /*popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    return false;
                }
            }); // On Menu Item Click Listener */
        }

        private String stringContactID, stringContactName, stringContactDirection, stringContactPhone, stringContactTelephone, stringContactEmail;

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            conexionSQLiteHelper = new ConexionSQLiteHelper(itemView.getContext(), Utilidades.DB_NAME,null, Utilidades.DB_VERSION);
            switch (item.getItemId()) {
                case R.id.action_popup_edit:
                    Log.d(TAG,"Edit");
                    //Log.d(TAG,"Position: "+getAdapterPosition());
                    Log.d(TAG,"Contact ID: "+stringContactID);
                    Log.d(TAG,"Contact Name: "+stringContactName);
                    Log.d(TAG,"Contact Direction: "+stringContactDirection);
                    Log.d(TAG,"Contact Phone: "+stringContactPhone);
                    Log.d(TAG,"Contact Telephone: "+stringContactTelephone);
                    Log.d(TAG,"Contact Email: "+stringContactEmail);

                    Intent intentEdit = new Intent(itemView.getContext(), ContactEditActivity.class);

                    intentEdit.putExtra(Utilidades.CAMPO_ID, stringContactID);
                    intentEdit.putExtra(Utilidades.CAMPO_NAME, stringContactName);
                    intentEdit.putExtra(Utilidades.CAMPO_DIRECTION, stringContactDirection);
                    intentEdit.putExtra(Utilidades.CAMPO_PHONE, stringContactPhone);
                    intentEdit.putExtra(Utilidades.CAMPO_TELEPHONE, stringContactTelephone);
                    intentEdit.putExtra(Utilidades.CAMPO_EMAIL, stringContactEmail);

                    itemView.getContext().startActivity(intentEdit);

                    return true;

                case R.id.action_popup_delete:
                    Log.d(TAG,"Delete");
                    Log.d(TAG,"Position: "+getAdapterPosition());
                    Log.d(TAG,"Contact ID: "+stringContactID);
                    Log.d(TAG,"Contact Name: "+stringContactName);
                    Log.d(TAG,"Contact Direction: "+stringContactDirection);
                    Log.d(TAG,"Contact Phone: "+stringContactPhone);
                    Log.d(TAG,"Contact Telephone: "+stringContactTelephone);
                    Log.d(TAG,"Contact Email: "+stringContactEmail);

                    conexionSQLiteHelper.DeleteContact(stringContactID);
                    ((MainActivity)itemView.getContext()).onResume();
                    return true;

                default:
                    return false;
            }
        } // End OnMenuItemClick
    }
}
