package ro.pub.cs.systems.eim.lab04.contactsmanager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        Button detailsButton = (Button) findViewById(R.id.show_fields_button);
        Button saveButton = (Button) findViewById(R.id.save_button);
        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        EditText phoneEditText = (EditText) findViewById(R.id.phoneNumber);

        detailsButton.setOnClickListener(new Listener());
        saveButton.setOnClickListener(new Listener());
        cancelButton.setOnClickListener(new Listener());

        Intent intent = getIntent();

        if (intent != null) {
            String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
            if (phone != null) {
                phoneEditText.setText(phone);
            } else {
                Toast.makeText(this, getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch(requestCode) {
            case Constants.CONTACTS_MANAGER_REQUEST_CODE:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }

    class Listener implements View

            .OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.show_fields_button:
                    LinearLayout details = (LinearLayout) findViewById(R.id.details);
                    Button detailsButton = (Button) findViewById(R.id.show_fields_button);
                    if (details.getVisibility() == View.VISIBLE) {
                        details.setVisibility(View.GONE);
                        detailsButton.setText("Show Additional Details");
                    } else {
                        details.setVisibility(View.VISIBLE);
                        detailsButton.setText("Hide Additional Details");
                    }
                    break;

                case R.id.save_button:
                    String name = ((EditText) findViewById(R.id.name)).getText().toString();
                    String phone = ((EditText) findViewById(R.id.phoneNumber)).getText().toString();
                    String email = ((EditText) findViewById(R.id.email)).getText().toString();
                    String address = ((EditText) findViewById(R.id.address)).getText().toString();
                    String jobTitle = ((EditText) findViewById(R.id.job)).getText().toString();
                    String company = ((EditText) findViewById(R.id.company)).getText().toString();
                    String website = ((EditText) findViewById(R.id.website)).getText().toString();
                    String im = ((EditText) findViewById(R.id.im)).getText().toString();

                    Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                    if (name != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                    }
                    if (phone != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                    }
                    if (email != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                    }
                    if (address != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                    }
                    if (jobTitle != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
                    }
                    if (company != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                    }
                    ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
                    if (website != null) {
                        ContentValues websiteRow = new ContentValues();
                        websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                        websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                        contactData.add(websiteRow);
                    }
                    if (im != null) {
                        ContentValues imRow = new ContentValues();
                        imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                        imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                        contactData.add(imRow);
                    }
                    intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                    startActivityForResult(intent, Constants.CONTACTS_MANAGER_REQUEST_CODE);
                    break;

                case R.id.cancel_button:
                    setResult(Activity.RESULT_CANCELED, new Intent());
                    finish();
                    break;
            }
        }
    }
}