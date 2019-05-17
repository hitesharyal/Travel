package com.example.naveenaryal.travelapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {
   EditText ymail;
       EditText password;
    private Button login;
    private Button signup;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       firebaseAuth=FirebaseAuth.getInstance();
       if(firebaseAuth.getCurrentUser()!= null)
       {
           finish();
           startActivity(new Intent(this,useractivity.class));

       }

        login=(Button) findViewById(R.id.login);
        signup=(Button) findViewById(R.id.signup);

        login.setOnClickListener(this);
        signup.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view==signup){
            Intent i= new Intent(this,signup_activity.class);
            startActivity(i);


        }
        if(view==login){

            userlogin();
        }
    }



    public void userlogin(){

        ymail=findViewById(R.id.email);
        password=findViewById(R.id.password);
       String mail=ymail.getText().toString().trim();
       String pass=password.getText().toString().trim();

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
        progressDialog.setMessage("Logging in.......");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    progressDialog.hide();
                    Toast.makeText(MainActivity.this,"Login successful",Toast.LENGTH_SHORT).show();
                    finish();
                    Intent i= new Intent(MainActivity.this,useractivity.class);
                    startActivity(i);

                }
                else {
                    progressDialog.hide();
                    Toast.makeText(MainActivity.this,"Invalid Email or password",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


}
