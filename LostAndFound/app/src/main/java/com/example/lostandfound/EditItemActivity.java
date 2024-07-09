package com.example.lostandfound;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class EditItemActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText editTextName, editTextDescription, editTextAddress, editTextPosterName;
    private Spinner spinnerStatus;
    private Button buttonUpdate, buttonDelete;
    private DatabaseReference databaseReference;
    private String itemId;

    private String status;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        imageView = findViewById(R.id.image_view);
        editTextName = findViewById(R.id.edit_text_name);
        editTextDescription = findViewById(R.id.edit_text_description);
        editTextAddress = findViewById(R.id.edit_text_address);
        editTextPosterName = findViewById(R.id.edit_text_poster_name);
        spinnerStatus = findViewById(R.id.spinner_status);
        buttonUpdate = findViewById(R.id.button_update);
        buttonDelete = findViewById(R.id.button_delete);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);

        Item item = getIntent().getParcelableExtra("item");
        if (item != null) {
            itemId = item.getId();
            Picasso.get().load(item.getImageUrl()).into(imageView);
            editTextName.setText(item.getName());
            editTextDescription.setText(item.getDescription());
            editTextAddress.setText(item.getAddress());
            editTextPosterName.setText(item.getPosterName());
            spinnerStatus.setSelection(((ArrayAdapter)spinnerStatus.getAdapter()).getPosition(item.getStatus()));
        }

        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                status = "Missing";
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("posts");

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateItem();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem();
            }
        });
    }

    private void updateItem() {
        if (!TextUtils.isEmpty(editTextName.getText().toString()) && !TextUtils.isEmpty(editTextDescription.getText().toString())
                && !TextUtils.isEmpty(editTextAddress.getText().toString()) && !TextUtils.isEmpty(editTextPosterName.getText().toString())) {
            Item item = new Item(itemId, getIntent().getStringExtra("imageUrl"), editTextName.getText().toString(),
                    status, getIntent().getStringExtra("time"), editTextDescription.getText().toString(),
                    editTextAddress.getText().toString(), editTextPosterName.getText().toString());
            databaseReference.child(itemId).setValue(item);
            Toast.makeText(EditItemActivity.this, "Item updated", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteItem() {
        databaseReference.child(itemId).removeValue();
        Toast.makeText(EditItemActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
        finish();
    }
}
