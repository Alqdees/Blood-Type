package com.Blood.types.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.Blood.types.Adapter.RecyclerViewAdapter;
import com.Blood.types.Model.Model;
import com.Blood.types.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ArrayList<Model> models;
    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    private FloatingActionButton floatingActionButton;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // not change color in dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        /////////
        setContentView(R.layout.activity_main);



         db = FirebaseFirestore.getInstance();
        models = new ArrayList<>();
        floatingActionButton = findViewById(R.id.registerBtn);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();
            showData();

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                }
            });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.custemoptions, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchBar(newText);

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void searchBar(String search) {
       ArrayList<Model> list = new ArrayList<>();
        for (Model m: models) {
            if (m.getType().toLowerCase().contains(search.toLowerCase())
                    || m.getName().toLowerCase().contains(search.toLowerCase())){
                list.add(m);
            }
        }
        RecyclerViewAdapter viewAdapter =
                new RecyclerViewAdapter(MainActivity.this,list);
        recyclerView.setAdapter(viewAdapter);
    }
    private void showData()
    {

        db.collection("User").orderBy("name", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Toast.makeText(
                                    MainActivity.this, error.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            for (DocumentChange document: value.getDocumentChanges()) {
                                if (document.getType() == DocumentChange.Type.ADDED){
                                    models.add(document.getDocument().toObject(Model.class));
                                }
                            }
                            adapter = new RecyclerViewAdapter(MainActivity.this,models);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });
    }

}
