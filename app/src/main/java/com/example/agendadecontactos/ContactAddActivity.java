package com.example.agendadecontactos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.agendadecontactos.utilidades.Utilidades;

import java.util.UUID;

public class ContactAddActivity extends AppCompatActivity {

    private static final String TAG = "log_depuration";

    private Button btnSave, btnCancel;
    private EditText etContactName,
                     etContactDirection,
                     etContactPhone,
                     etContactTelephone,
                     etContactEmail;

    private String contactName,
                   contactDirection,
                   contactPhone,
                   contactTelephone,
                   contactEmail,
                   contactRegistrationDate,
                   contactUpdateDate;

    ConexionSQLiteHelper conexionSQLiteHelper;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add);

        Initialization();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveData();
            }
        }); // End OnClick Save

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        }); // End OnClick Cancel

    } // End OnCreate

    private void Initialization() {

        etContactName           = findViewById(R.id.tiet_contact_add_name);
        etContactDirection      = findViewById(R.id.tiet_contact_add_direction);
        etContactPhone          = findViewById(R.id.tiet_contact_add_phone);
        etContactTelephone      = findViewById(R.id.tiet_contact_add_telephone);
        etContactEmail          = findViewById(R.id.tiet_contact_add_email);

        btnSave                 = findViewById(R.id.btn_contact_add_save);
        btnCancel               = findViewById(R.id.btn_contact_add_cancel);

        conexionSQLiteHelper = new ConexionSQLiteHelper(this, Utilidades.DB_NAME, null, Utilidades.DB_VERSION);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

    } // End Initialization

    private void SaveData() {

        String randomID     = UUID.randomUUID().toString();
        contactName         = etContactName.getText().toString().trim();
        contactDirection    = etContactDirection.getText().toString().trim();
        contactPhone        = etContactPhone.getText().toString().trim();
        contactTelephone    = etContactTelephone.getText().toString().trim();
        contactEmail        = etContactEmail.getText().toString().trim();
        contactRegistrationDate     = String.valueOf(System.currentTimeMillis());
        contactUpdateDate           = String.valueOf(System.currentTimeMillis());

        // validation
        awesomeValidation.addValidation(this, R.id.tiet_contact_add_name, RegexTemplate.NOT_EMPTY, R.string.invalidate_name);
        //awesomeValidation.addValidation(this, R.id.tiet_contact_add_direction, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.invalidate_direction);
        awesomeValidation.addValidation(this, R.id.tiet_contact_add_direction, "[a-zA-Z\\s]+", R.string.invalidate_direction);
        awesomeValidation.addValidation(this, R.id.tiet_contact_add_phone, RegexTemplate.TELEPHONE, R.string.invalidate_phone);
        //awesomeValidation.addValidation(this, R.id.tiet_contact_add_telephone, "[2-9]{2}[0-9]{8}$", R.string.invalidate_telephone);
        awesomeValidation.addValidation(this, R.id.tiet_contact_add_telephone, RegexTemplate.TELEPHONE, R.string.invalidate_telephone);
        awesomeValidation.addValidation(this, R.id.tiet_contact_add_email, Patterns.EMAIL_ADDRESS, R.string.invalidate_email);

        if(awesomeValidation.validate()){
            long id = conexionSQLiteHelper.AddContact(
                    ""+randomID,
                    "" + contactName,
                    "" + contactDirection,
                    "" + contactPhone,
                    "" + contactTelephone,
                    "" + contactEmail,
                    "" + contactRegistrationDate,
                    "" + contactUpdateDate
            );
            Toast.makeText(getApplicationContext(), R.string.add_contact_validation_succ, Toast.LENGTH_SHORT).show();
            finish();
        } else{
            Toast.makeText(getApplicationContext(), R.string.add_contact_validation_fai, Toast.LENGTH_SHORT).show();
        }

    }

} // End Activity