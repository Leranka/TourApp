package com.example.admin.tourapps;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Admin on 7/28/2017.
 */

public class FirebaseHelper {

    DatabaseReference db;
    Boolean saved;
    ArrayList<Pojo> spacecrafts = new ArrayList<>();

    /*
 PASS DATABASE REFERENCE
  */
    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    //WRITE IF NOT NULL
    public Boolean save(Pojo spacecraft) {
        if (spacecraft == null) {
            saved = false;
        } else {
            try {
                db.child("Spacecraft").push().setValue(spacecraft);
                saved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }

    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    private void fetchData(DataSnapshot dataSnapshot) {
        spacecrafts.clear();
        //dataSnapshot.getChildren()

        try {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                //System.out.println(ds.getValue());
                Pojo spacecraft = ds.getValue(Pojo.class);
                spacecrafts.add(spacecraft);
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
            saved = false;
        }

    }

    //RETRIEVE
    public ArrayList<Pojo> retrieve() {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return spacecrafts;
    }
}
