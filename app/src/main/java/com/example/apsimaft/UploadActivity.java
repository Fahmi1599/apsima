package com.example.apsimaft;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.auth.FirebaseAuth;

public class UploadActivity extends AppCompatActivity {

    Button selectFile, upload;
    TextView notification;
    Uri pdfUri;

    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth mAuth;

    FirebaseStorage storage;
    FirebaseDatabase database;
    ProgressDialog progressDialog = new ProgressDialog(this);

    public UploadActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        selectFile = findViewById(R.id.btn_selectfile);
        upload = findViewById(R.id.btn_upload);
        notification = findViewById(R.id.tvdoang);

        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(UploadActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectPdf();
                } else
                    ActivityCompat.requestPermissions(UploadActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pdfUri != null)
                    uploadFile(pdfUri);
                else
                    Toast.makeText(UploadActivity.this, "Select a File", Toast.LENGTH_SHORT).show();

            }

            private void uploadFile(Uri pdfUri) {

                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setTitle("Uploading File...");
                progressDialog.setProgress(0);
                progressDialog.show();

                final String fileName = System.currentTimeMillis() + "";
                StorageReference storageReference = storage.getReference();

                storageReference.child("Upload").child(fileName).putFile(pdfUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                String url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                                DatabaseReference reference = database.getReference();
                                reference.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task)
                                    {
                                      if(task.isSuccessful())
                                          Toast.makeText(UploadActivity.this,"File Succsesfully Uploaded",Toast.LENGTH_SHORT).show();
                                      else
                                          Toast.makeText(UploadActivity.this,"File Not Succsesfully Uploaded",Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(UploadActivity.this,"File Not Succsesfully Uploaded",Toast.LENGTH_SHORT).show();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        int currentProgress= (int) (100*taskSnapshot.getBytesTransferred() /taskSnapshot.getTotalByteCount());
                        progressDialog.setProgress(currentProgress);

                    }
                });


            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectPdf();
        } else
            Toast.makeText(UploadActivity.this, "Please provide permission..", Toast.LENGTH_SHORT).show();


    }

    private void selectPdf() {

        //to offer user to select a file using file manager

        //we will using intent

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null)
        {
            pdfUri = data.getData();
            notification.setText("A file is selected : "+ data.getData().getLastPathSegment());
        }
        else
            {
            Toast.makeText(UploadActivity.this, "Please select a file", Toast.LENGTH_SHORT).show();
        }
    }


}
