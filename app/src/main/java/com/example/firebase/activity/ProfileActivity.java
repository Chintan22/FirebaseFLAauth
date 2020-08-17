package com.example.firebase.activity;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.firebase.R;
import com.example.firebase.adapter.FavoriteListAdapter;
import com.example.firebase.models.Dictionary;
import com.example.firebase.models.Upload;
import com.example.firebase.models.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.io.InputStream;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProfileActivity extends AppCompatActivity {
    public TextView tvUserName,tvEmail,tvAddress,tvMobile;
    public Button btnShare;

    private List<User> userArrayList = new ArrayList<>();
    private List<Upload> uploadArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FavoriteListAdapter mAdapter;
    private ImageView ivProfile;

    DatabaseReference databaseArtists;
    DatabaseReference databaseImages;
    private FirebaseAuth auth;
    String imageURL;


    private Uri filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        auth = FirebaseAuth.getInstance();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Profile");
        tvUserName=findViewById(R.id.tvName);
        tvAddress=findViewById(R.id.tvAddress);
        tvMobile=findViewById(R.id.tvMobile);

        tvEmail= (TextView) findViewById(R.id.tvEmail);
        btnShare= (Button) findViewById(R.id.btnShare);
        auth = FirebaseAuth.getInstance();

        databaseArtists = FirebaseDatabase.getInstance().getReference("users");
        databaseImages = FirebaseDatabase.getInstance().getReference("images");

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null) {
            Log.d("firebase_user", "" + user.getEmail());
            tvEmail.setText(user.getEmail());
        }
        else {
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
        if (user!=null)
        {
            btnShare.setVisibility(View.VISIBLE);
        }
        else {
            btnShare.setVisibility(View.GONE);

        }

        ivProfile=findViewById(R.id.ivProfile);
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                userArrayList.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    final User dictionary = postSnapshot.getValue(User.class);

                    //adding artist to the list
                    userArrayList.add(dictionary);
                    assert dictionary != null;
                    tvAddress.setText(dictionary.getAddress());
                    tvEmail.setText(dictionary.getEmail());
                    tvUserName.setText(dictionary.getName());
                    tvMobile.setText(dictionary.getMobile());
                    btnShare.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                            /*This will be the actual content you wish you share.*/
                            /*The type of the content is text, obviously.*/
                            intent.setType("text/plain");
                            /*Applying information Subject and Body.*/

                            intent.putExtra(android.content.Intent.EXTRA_SUBJECT,"USER PROFILE");
                            intent.putExtra(android.content.Intent.EXTRA_TEXT, "EMAIL:"+ dictionary.getEmail() + "\n" + "USERNAME : "+dictionary.getName() + "\n" + "ADDRESS:"+dictionary.getAddress() +"\n" + "MOBILE NO:"+dictionary.getMobile());

                            /*Fire!*/
                            startActivity(Intent.createChooser(intent, dictionary.getEmail()));
                        }
                    });

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseImages.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                uploadArrayList.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    final Upload dictionary = postSnapshot.getValue(Upload.class);

                    //adding artist to the list
                    uploadArrayList.add(dictionary);
                    assert dictionary != null;
                    if (dictionary.getUrl()!=null) {
                        Glide.with(ProfileActivity.this).load(dictionary.getUrl()).into(ivProfile);
                    }


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent5 = new Intent(ProfileActivity.this, DashboardActivity.class);
        startActivity(intent5);
        finish();
    }

    private void SelectImage()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();

            // Setting image on image view using Bitmap
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ivProfile.setImageBitmap(bitmap);
            UploadImages();
        }
    }

    private void uploadImage()
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            final ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference

            StorageReference ref = storageReference.child(auth.getCurrentUser().getUid()).child(
                    "images/"
                            + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    Upload upload = new Upload(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());

                                    //adding an upload to firebase database
                                    databaseImages.child(auth.getCurrentUser().getUid()).setValue(upload);
                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(ProfileActivity.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(ProfileActivity.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }

    public void UploadImages() {
        try {

            final StorageReference ref = storageReference.child(auth.getCurrentUser().getUid()).child(
                    "images/"
                            + UUID.randomUUID().toString());

            UploadTask uploadTask = ref.putFile(filePath);
            Log.e("Fire Path", ref.toString());
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    Upload upload = new Upload(ref.getDownloadUrl().toString());

                    //adding an upload to firebase database
                    databaseImages.child(auth.getCurrentUser().getUid()).setValue(upload);

                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        Log.e("Image URL", downloadUri.toString());
                        Upload upload = new Upload(downloadUri.toString());

                        //adding an upload to firebase database
                        databaseImages.child(auth.getCurrentUser().getUid()).setValue(upload);



                    } else {
                        Toast.makeText(ProfileActivity.this, "Image upload unsuccessful. Please try again."
                                , Toast.LENGTH_LONG).show();
                    }

                    DownloadImageFromURL downloadImageFromURL = new DownloadImageFromURL();
                    downloadImageFromURL.execute("");
                }
            });
        } catch (Exception ex) {
            Toast.makeText(ProfileActivity.this, ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    private class DownloadImageFromURL extends AsyncTask<String, Void, String> {
        Bitmap bitmap = null;

        @Override
        protected void onPreExecute() {

        }

        protected String doInBackground(String... urls) {
            try {
                Log.e("imageURL is ", imageURL);
                InputStream in = new java.net.URL(imageURL).openStream();
                if (in != null) {
                    bitmap = BitmapFactory.decodeStream(in);
                } else
                    Log.e("Empty InputStream", "InputStream is empty.");
            } catch (MalformedInputException e) {
                Log.e("Error URL", e.getMessage().toString());
            } catch (Exception ex) {
                Log.e("Input stream error", "Input stream error");
            }
            return "";
        }

        protected void onPostExecute(String result) {
            if (bitmap != null) {
                ivProfile.setImageBitmap(bitmap);
            } else
                Log.e("Empty Bitmap", "Bitmap is empty.");
        }
    }

}


