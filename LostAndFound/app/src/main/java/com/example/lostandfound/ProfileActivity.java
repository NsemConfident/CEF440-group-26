package com.example.lostandfound;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView profileImageView;
    private Button changeImageButton, editProfileButton;
    private EditText editTextName, editTextAddress, editTextCountry, editTextEmail;
    private Uri imageUri;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImageView = findViewById(R.id.profile_image);
        changeImageButton = findViewById(R.id.button_change_image);
        editProfileButton = findViewById(R.id.button_edit_profile);
        editTextName = findViewById(R.id.edit_text_name);
        editTextAddress = findViewById(R.id.edit_text_address);
        editTextCountry = findViewById(R.id.edit_text_country);
        editTextEmail = findViewById(R.id.edit_text_email);

        ImageButton back = findViewById(R.id.backabout);
        TextView textback = findViewById(R.id.textbackabout);

        storageReference = FirebaseStorage.getInstance().getReference("profile_images");
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            editTextEmail.setText(user.getEmail());
            loadUserProfile(user.getUid());
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        textback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        changeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserProfile();
            }
        });
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
            profileImageView.setImageURI(imageUri);
        }
    }

    private void saveUserProfile() {
        final String name = editTextName.getText().toString().trim();
        final String address = editTextAddress.getText().toString().trim();
        final String country = editTextCountry.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(address) || TextUtils.isEmpty(country)) {
            Toast.makeText(ProfileActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        final FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Saving Profile...");
        progressDialog.show();

        if (imageUri != null) {
            final StorageReference fileReference = storageReference.child(user.getUid() + ".jpg");
            fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    UserProfile userProfile = new UserProfile(user.getUid(), uri.toString(), name, address, country, user.getEmail());
                                    databaseReference.child(user.getUid()).setValue(userProfile);
                                    progressDialog.dismiss();
                                    Toast.makeText(ProfileActivity.this, "Profile saved", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            UserProfile userProfile = new UserProfile(user.getUid(), null, name, address, country, user.getEmail());
            databaseReference.child(user.getUid()).setValue(userProfile);
            progressDialog.dismiss();
            Toast.makeText(ProfileActivity.this, "Profile saved", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadUserProfile(String userId) {
        // Load user profile data from Firebase and set it to the views
        databaseReference.child(userId).get().addOnSuccessListener(dataSnapshot -> {
            UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
            if (userProfile != null) {
                if (userProfile.getImageUrl() != null) {
                    Picasso.get().load(userProfile.getImageUrl()).into(profileImageView);
                }
                editTextName.setText(userProfile.getName());
                editTextAddress.setText(userProfile.getAddress());
                editTextCountry.setText(userProfile.getCountry());
            }
        });
    }
}
