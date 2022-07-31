package com.example.agendadecontactos.editable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.agendadecontactos.ConexionSQLiteHelper;
import com.example.agendadecontactos.R;
import com.example.agendadecontactos.utilidades.Utilidades;

public class ContactEditActivity extends AppCompatActivity {

    private static final String TAG = "log_depuration_contact_edit";

    private Button btnSave, btnCancel;
    private EditText etContactName,
            etContactDirection,
            etContactPhone,
            etContactTelephone,
            etContactEmail;

    private String contactId,
            contactName,
            contactDirection,
            contactPhone,
            contactTelephone,
            contactEmail,
            contactRegistrationDate,
            contactUpdateDate;

    private String updateId,
            updateName,
            updateDirection,
            updatePhone,
            updateTelephone,
            updateEmail,
            updateRegistrationDate,
            updateUpdateDate;

    ConexionSQLiteHelper conexionSQLiteHelper;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit);

        Initialization();
        recibiendoDatos();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateContact();
            }
        }); // End OnClick Save

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        }); // End OnClick Cancel

    } // End OnCreate

    private void UpdateContact() {
        updateId = contactId;
        updateName = etContactName.getText().toString().trim();
        updateDirection = etContactDirection.getText().toString().trim();
        updatePhone = etContactPhone.getText().toString().trim();
        updateTelephone = etContactTelephone.getText().toString().trim();
        updateEmail = etContactEmail.getText().toString().trim();

        updateRegistrationDate = contactRegistrationDate;
        updateUpdateDate = String.valueOf(System.currentTimeMillis());

        // validation
        awesomeValidation.addValidation(this, R.id.tiet_edit_name, RegexTemplate.NOT_EMPTY, R.string.invalidate_name);
        //awesomeValidation.addValidation(this, R.id.tiet_edit_direction, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.invalidate_direction);
        awesomeValidation.addValidation(this, R.id.tiet_edit_direction, "[a-zA-Z\\s]+", R.string.invalidate_direction);
        awesomeValidation.addValidation(this, R.id.tiet_edit_phone, RegexTemplate.TELEPHONE, R.string.invalidate_phone);
        //awesomeValidation.addValidation(this, R.id.tiet_edit_telephone, "[2-9]{2}[0-9]{8}$", R.string.invalidate_telephone);
        awesomeValidation.addValidation(this, R.id.tiet_edit_telephone, RegexTemplate.TELEPHONE, R.string.invalidate_telephone);
        awesomeValidation.addValidation(this, R.id.tiet_edit_email, Patterns.EMAIL_ADDRESS, R.string.invalidate_email);

        if(awesomeValidation.validate()){

            conexionSQLiteHelper.UpdateContact(
                    ""+updateId,
                    "" + updateName,
                    "" + updateDirection,
                    "" + updatePhone,
                    "" + updateTelephone,
                    "" + updateEmail,
                    "" + updateRegistrationDate,
                    "" + updateUpdateDate

            );

            Toast.makeText(getApplicationContext(), R.string.add_contact_validation_succ, Toast.LENGTH_SHORT).show();
            finish();

        } else{
            Toast.makeText(getApplicationContext(), R.string.add_contact_validation_fai, Toast.LENGTH_SHORT).show();
        }

    } // End updateContact

    private void recibiendoDatos() {

        Intent intent = getIntent();
        if (getIntent().getExtras() != null){

        }

        contactId           = intent.getStringExtra(Utilidades.CAMPO_ID);
        contactName         = intent.getStringExtra(Utilidades.CAMPO_NAME);
        contactDirection    = intent.getStringExtra(Utilidades.CAMPO_DIRECTION);
        contactPhone        = intent.getStringExtra(Utilidades.CAMPO_PHONE);
        contactTelephone    = intent.getStringExtra(Utilidades.CAMPO_TELEPHONE);
        contactEmail        = intent.getStringExtra(Utilidades.CAMPO_EMAIL);
        contactRegistrationDate        = intent.getStringExtra(Utilidades.CAMPO_REGISTRATION_DATE);
        contactUpdateDate              = intent.getStringExtra(Utilidades.CAMPO_UPDATE_DATE);

        etContactName.setText(contactName);
        etContactDirection.setText(contactDirection);
        etContactPhone.setText(contactPhone);
        etContactTelephone.setText(contactTelephone);
        etContactEmail.setText(contactEmail);

    }

    private void Initialization() {

        etContactName           = findViewById(R.id.tiet_edit_name);
        etContactDirection      = findViewById(R.id.tiet_edit_direction);
        etContactPhone          = findViewById(R.id.tiet_edit_phone);
        etContactTelephone      = findViewById(R.id.tiet_edit_telephone);
        etContactEmail          = findViewById(R.id.tiet_edit_email);

        btnSave                 = findViewById(R.id.btn_edit_save);
        btnCancel               = findViewById(R.id.btn_edit_cancel);

        conexionSQLiteHelper = new ConexionSQLiteHelper(this, Utilidades.DB_NAME, null, Utilidades.DB_VERSION);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

    }

}