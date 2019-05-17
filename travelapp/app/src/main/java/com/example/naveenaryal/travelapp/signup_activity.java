package com.example.naveenaryal.travelapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup_activity extends AppCompatActivity {

    private EditText email,password;

    ProgressDialog progressDialog;
   FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_activity);
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!= null)
        {
          finish();
            startActivity(new Intent(this,useractivity.class));
        }

        email=(EditText)findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password2);



    }
    public void signmeup(View view){
        usersignup();
    }

    private void usersignup() {

        String mail = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        //Checking whether email or password is empty
        if (TextUtils.isEmpty(mail)) {

            Toast.makeText(this,"Please Enter Email",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)){


            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }
        //checking validation;

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Signing up.......");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progressDialog.hide();
                    Toast.makeText(signup_activity.this,"Registration sucessful", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(signup_activity.this,profileActivity.class);

                    startActivity(i);
                    //task to preform
                }
                else {
                    progressDialog.hide();
                    Toast.makeText(signup_activity.this,"Account exist with this EMAIL", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
