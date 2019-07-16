package com.mohammedsalah;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.internal.ExponentialBackoffSender;
import com.mohammedsalah.salah.notekeeper.R;
import com.mohammedsalah.salah.notekeeper.SettingsActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile extends AppCompatActivity {

    private CircleImageView imageView;
    private static final int PICK_IMAGE = 1;
    Uri imageuri;

    private StorageReference mstorage ;
    private ProgressDialog progressDialog ;
    private StorageReference filepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setTitle("Profile");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mstorage = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(this);

        imageView = (CircleImageView) findViewById(R.id.image_prof);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent , "select photo"),PICK_IMAGE);
            }
        });

         StorageReference storageReference =  mstorage.child("photos");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK){


            progressDialog.setMessage("Uploading...");
            progressDialog.show();

            imageuri=data.getData();

            filepath = mstorage.child("photos").child(imageuri.getLastPathSegment());
              filepath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                  @Override
                  public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                      progressDialog.dismiss();

                      mstorage.child("photos").child(imageuri.getLastPathSegment()).getDownloadUrl()
                     .addOnCompleteListener(new OnCompleteListener<Uri>() {
                         @Override
                         public void onComplete(@NonNull Task<Uri> task) {
                           // logic error
                             Picasso.with(getApplicationContext()).load(imageuri).into(imageView);
                         }
                     });

                      Toast.makeText(getApplicationContext() , "Uploaded" , Toast.LENGTH_SHORT).show();
                  }
              }).addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {
                      progressDialog.dismiss();
                      Toast.makeText(getApplicationContext() , "Error!" , Toast.LENGTH_SHORT).show();
                  }
              });

/*
        try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver() , imageuri);
                imageView.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
            */
        }




    }


    public void ChatMe(View view) {
        SharedPreferences pre = PreferenceManager.getDefaultSharedPreferences(this);
        String name = pre.getString("user_display_name","");
        if (name.equals("User name")){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Set Your name please!");
            alert.setTitle("Confirm name");
            alert.create();
            alert.show();
        }else {
            Intent intent = new Intent(getApplicationContext(), com.mohammedsalah.salah.chat.class);
            startActivity(intent);
        }
    }

    int count =0;
    public void LikeMe(View view) {
        count++;
        Snackbar.make(view , "+ " + count + " Liked this Profile ^-^" ,
                Snackbar.LENGTH_SHORT).setAction("Action" , null).show();

    }

    public void edit_name(View view) {
        Intent intent = new Intent(getApplicationContext() , SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateInf();
    }

    private void updateInf() {
        TextView u_n = (TextView) findViewById(R.id.prof_username);
        TextView u_e= (TextView) findViewById(R.id.prof_useremail);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String userName = pref.getString("user_display_name", "");
        String emailAddress = pref.getString("user_email_address", "");

        u_n.setText(userName);
        u_e.setText(emailAddress);
    }
}
