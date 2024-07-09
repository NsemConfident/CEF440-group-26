package com.example.lostandfound;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecentlyMissingFragment extends Fragment implements ItemsAdapter.OnItemClickListener {

    private ItemsAdapter adapter;
    private List<Item> itemList;
    private DatabaseReference dbRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recently_missing, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // Revert to GridLayoutManager
        itemList = new ArrayList<>();
        //adapter = new ItemsAdapter(getContext(), itemList, this);
        recyclerView.setAdapter(new ItemsAdapter(getContext(), itemList, this));

        // Reference to the "Missing" items in the database
        dbRef = FirebaseDatabase.getInstance().getReference("posts").child("Missing");
        loadItems();

        return view;
    }

    private void loadItems() {
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("RecentlyMissingFragment", "Data snapshot: " + dataSnapshot.toString());
                itemList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Item item = snapshot.getValue(Item.class);
                    if (item != null) {
                        itemList.add(item);
                        Log.d("RecentlyMissingFragment", "Item added: " + item.getName());
                    } else {
                        Log.e("RecentlyMissingFragment", "Item is null");
                    }
                }
                Log.d("RecentlyMissingFragment", "Item list size: " + itemList.size());
                getActivity().runOnUiThread(() -> {
                    adapter.notifyDataSetChanged();
                    Log.d("RecentlyMissingFragment", "Adapter notified");
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DatabaseError", "Error getting data: ", databaseError.toException());
            }
        });
    }

    @Override
    public void onItemClick(Item item) {
        // Handle item click if needed
    }
}
