package com.example.lostandfound;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class NewPostActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private ImageView imageView;
    ProgressBar progressBar;
    private EditText editTextName, editTextDate, editTextTime, editTextDescription;
    private RadioGroup radioGroupStatus;
    private Button buttonPost;
    private Button buttonSelectImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        imageView = findViewById(R.id.imageView);

        editTextName = findViewById(R.id.editTextName);
        editTextDate = findViewById(R.id.editTextDate);
        editTextTime = findViewById(R.id.editTextTime);
        progressBar = findViewById(R.id.progress_bar);
        editTextDescription = findViewById(R.id.editTextDescription);
        radioGroupStatus = findViewById(R.id.radioGroupStatus);
        buttonSelectImage = findViewById(R.id.buttonSelectImage);
        buttonPost = findViewById(R.id.buttonPost);

        buttonSelectImage.setOnClickListener(v -> openFileChooser());

        buttonPost.setOnClickListener(v -> uploadImageAndSaveData());
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void uploadImageAndSaveData() {
        if (imageUri != null) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference("uploads").child(System.currentTimeMillis() + ".jpg");

            storageReference.putFile(imageUri).addOnSuccessListener(taskSnapshot ->
                            storageReference.getDownloadUrl().addOnSuccessListener(uri -> savePostData(uri.toString())))
                    .addOnFailureListener(e -> Toast.makeText(NewPostActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void savePostData(String imageUrl) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String name = editTextName.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        String time = editTextTime.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String status = ((RadioButton) findViewById(radioGroupStatus.getCheckedRadioButtonId())).getText().toString();

        Map<String, Object> post = new HashMap<>();
        post.put("name", name);
        post.put("date", date);
        post.put("time", time);
        post.put("description", description);
        post.put("status", status);
        post.put("imageUrl", imageUrl);

        db.collection("posts").add(post)
                .addOnSuccessListener(documentReference -> Toast.makeText(NewPostActivity.this, "Post uploaded", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(NewPostActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}