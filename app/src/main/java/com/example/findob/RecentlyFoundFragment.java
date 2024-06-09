package com.example.findob;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class RecentlyFoundFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recently_found, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(new ItemsAdapter(getSampleItems()));
        return view;
    }

    private List<Item> getSampleItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Watch", "Posted by John Doe", R.drawable.cat_3));
        items.add(new Item("Laptop", "Posted by Jane Doe", R.drawable.cat_5));
        items.add(new Item("Phone", "Posted by Robert Doe", R.drawable.cat_2));
        items.add(new Item("Backpack", "Posted by Emily Doe", R.drawable.cat_1));
        items.add(new Item("Watch", "Posted by John Doe", R.drawable.cat_3));
        items.add(new Item("Laptop", "Posted by Jane Doe", R.drawable.cat_5));
        items.add(new Item("Phone", "Posted by Robert Doe", R.drawable.cat_2));
        items.add(new Item("Backpack", "Posted by Emily Doe", R.drawable.cat_1));
        items.add(new Item("Jacket", "Posted by Michael Doe", R.drawable.cat_4));
        items.add(new Item("Earbuds", "Posted by Olivia Doe", R.drawable.cat_3));
        return items;
    }
}
