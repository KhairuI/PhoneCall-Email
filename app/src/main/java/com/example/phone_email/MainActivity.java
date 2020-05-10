package com.example.phone_email;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int request_call=5;

    private AppCompatEditText phoneEditText,toEditText,subjectEditText,messageEditText;
    private AppCompatImageView phoneImageView,sentImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phoneEditText= findViewById(R.id.phoneNoEditText);
        phoneImageView= findViewById(R.id.phoneImageButtonId);
        toEditText= findViewById(R.id.toEditTextId);
        subjectEditText= findViewById(R.id.subjectEditTextId);
        messageEditText= findViewById(R.id.messageEditTextId);
        sentImageView= findViewById(R.id.sentImageButtonId);

        phoneImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhone();
            }
        });
        sentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
    }

    private void sendMail() {

        String to= toEditText.getEditableText().toString();
        String[] makeTo= to.split(","); //email1@gmail.com,email2@gmail.com(if more than one email)

        String subject= subjectEditText.getEditableText().toString();
        String message= messageEditText.getEditableText().toString();

        Intent intent= new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,makeTo);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Choose am email client"));

    }

    private void makePhone() {
        String number= phoneEditText.getEditableText().toString();
        if(number.trim().length() >0){

            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.CALL_PHONE},request_call);
            }
            else {
                String dila= "tel:"+number;
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dila)));
            }
        }
        else {
            Toast.makeText(this, "Enter Phone No", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==request_call){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                makePhone();
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
