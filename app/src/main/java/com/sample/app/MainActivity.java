package com.sample.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.sample.realmutf8issue.RealmLib;
import com.sample.realmutf8issue.StringHaver;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RealmLib realmLib;

    private RecyclerView recyclerView;
    private StringHaverAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new StringHaverAdapter();
        recyclerView = this.findViewById(R.id.rvContactsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);


        realmLib = RealmLib.initialize(ExampleJavaApplication.getInstance());
        realmLib.createAndCauseProblem(ExampleJavaApplication.getInstance(), () -> {
            Log.d(ExampleJavaApplication.EXAMPLE_TAG, "afterCreate");
            loadDataToAdapter();
        });
    }

    private void loadDataToAdapter() {
        ArrayList<StringHaverViewObject> stringHaverViewObjects = getViewObjects();
        adapter.setData(stringHaverViewObjects);
    }

    @NotNull
    private ArrayList<StringHaverViewObject> getViewObjects() {
        ArrayList<StringHaverViewObject> stringHaverViewObjects = new ArrayList<>();
        for (StringHaver stringHaver : RealmLib.getInstance().readItems()) {
            stringHaverViewObjects.add(StringHaverAdapter.Mapper.toViewObject(stringHaver));
        }
        return stringHaverViewObjects;
    }
}