package com.example.naveenaryal.travelapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;

public class profileActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST =234 ;
    Button  upload,choose,save,signout;
    ImageView profilepic;
    private Uri imagepath;
    private StorageReference storageReference;



FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();

EditText name,add,phone;


private DatabaseReference databaseReference;
Userinformation usercurr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView welcome= findViewById(R.id.welcome);
        profilepic = (ImageView)findViewById(R.id.profilepicture);
          upload=(Button) findViewById(R.id.upload);
             choose= (Button) findViewById(R.id.choose);
             signout=(Button) findViewById(R.id.logout);
            save= (Button) findViewById(R.id.save);
        FirebaseUser user= firebaseAuth.getCurrentUser();
        welcome.setText("welcome" + user.getEmail());

           storageReference= FirebaseStorage.getInstance().getReference();
        upload.setOnClickListener(this);
        choose.setOnClickListener(this);
        save.setOnClickListener(this);
        signout.setOnClickListener(this);


    }


 @Override
   protected void onStart() {
        super.onStart();

     name = findViewById(R.id.name);
     add = findViewById(R.id.address);
     phone = findViewById(R.id.phone);
     DataSnapshot data;



        FirebaseDatabase db= FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        DatabaseReference dr= db.getReference();





            dr.addValueEventListener(new ValueEventListener() {
                Userinformation usi;
                @Override

                public void onDataChange(DataSnapshot dataSnapshot) {

                    if ((Userinformation) dataSnapshot.child(firebaseAuth.getCurrentUser().getUid().toString()).getValue(Userinformation.class)!=null) {
                        usi = (Userinformation) dataSnapshot.child(firebaseAuth.getCurrentUser().getUid().toString()).getValue(Userinformation.class);
//               String naam = (String) dataSnapshot.child(firebaseAuth.getCurrentUser().getUid().toString()).child("name").getValue();
//                String pata = (String) dataSnapshot.child(firebaseAuth.getCurrentUser().getUid().toString()).child("address").getValue();
//                String fone = (String) dataSnapshot.child(firebaseAuth.getCurrentUser().getUid().toString()).child("phone").getValue();


                        name.setText(usi.name);
                        phone.setText(usi.phone);
                        add.setText(usi.address);

                    }


                }
                    @Override
                    public void onCancelled (DatabaseError databaseError){
                        Toast.makeText(profileActivity.this, "Oops " + databaseError.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });



    firebaseUser=firebaseAuth.getCurrentUser();
     //  String id= (String)  firebaseUser.getUid();




        }



    @Override
    public void onClick(View view) {
       if(view==signout){
           firebaseAuth.signOut();
           finish();
           startActivity(new Intent(this,MainActivity.class));
       }

       else if(view==save){

           saveinfo();
           startActivity(new Intent(this, useractivity.class));

       }
        else if(view==choose){

                       filechooser();
       }

       else if(view==upload){

                         uploadpic();

       }



    }
    private void uploadpic(){


        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
      //  String id= (String)  firebaseUser.getUid();

        if(imagepath!=null) {
            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Uploading........");
            progressDialog.show();

            StorageReference riversRef = storageReference.child(firebaseUser.getUid()+".jpg");

            riversRef.putFile(imagepath)

                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override

                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                             progressDialog.dismiss();
                             Toast.makeText(getApplicationContext(),"Upload succesful",Toast.LENGTH_SHORT).show();
                            // Get a URL to the uploaded content
                           // Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress=(100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("uploded.."+ (int)progress+ "%");
                    progressDialog.show();
                }
            });
        }

        else{

        }

    }
    private void filechooser(){

        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"SELECT AN IMAGE"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK &&data!=null&& data.getData()!=null){

            imagepath=data.getData();

            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imagepath);
                profilepic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



    public void saveinfo(){

        //method to save user info in firebase database
        name=findViewById(R.id.name);
        add= findViewById(R.id.address);
        phone=findViewById(R.id.phone);

        String uname=name.getText().toString().trim();
        String uadd=add.getText().toString().trim();
        String uphone=phone.getText().toString().trim();


        usercurr= new Userinformation(uadd,uname,uphone);


        databaseReference= FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        databaseReference.child(firebaseUser.getUid()).setValue(usercurr);
        Toast.makeText(this,"Details Saved Sucessfully",Toast.LENGTH_SHORT).show();


    }




}
