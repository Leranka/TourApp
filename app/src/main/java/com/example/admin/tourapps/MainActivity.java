package com.example.admin.tourapps;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    DatabaseReference db;
    FirebaseHelper helper;
    CustomAdapter adapter;
    ListView lv;
    EditText nameEditTxt, descTxt;
    String pic = "null";


    //Adding image
    StorageReference mStorage;
    ProgressDialog mProgressDialog;
    ImageView mImageview, mImage;
    Button addBtn;
    private static final int GALLERY_INTENT = 1;
    Uri uri, uriBtn;
    public static Pojo SELECTED_ITEM;
    private Pojo s;
    String main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv);

        //INITIALIZE FIREBASE DB
        db = FirebaseDatabase.getInstance().getReference();
        helper = new FirebaseHelper(db);

        //ADAPTER
        adapter = new CustomAdapter(this, helper.retrieve());
        lv.setAdapter(adapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayInputDialog();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, Museums.class);

                Pojo museum = (Pojo) lv.getItemAtPosition(i);
                SELECTED_ITEM = museum;
                intent.putExtra("select", SELECTED_ITEM);
                startActivity(intent);
            }
        });

    }

    //DISPLAY INPUT DIALOG
    private void displayInputDialog() {
        Dialog d = new Dialog(this);
        d.setTitle("Save To Firebase");
        d.setContentView(R.layout.inputdialog);

        nameEditTxt = d.findViewById(R.id.nameEditText);
        descTxt = d.findViewById(R.id.descEditText);

        //Setting and image
        mImageview = d.findViewById(R.id.imgAdd);

        addBtn = d.findViewById(R.id.btnAdd);

        mStorage = FirebaseStorage.getInstance().getReference();
        mProgressDialog = new ProgressDialog(this);

        mProgressDialog.setMessage("Loading .....");

        Button saveBtn = d.findViewById(R.id.saveBtn);
        //SAVE
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadInfo();

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
                pic = "btn";
            }
        });


        d.show();
        Window window = d.getWindow();
        window.setLayout(AppBarLayout.LayoutParams.FILL_PARENT, AppBarLayout.LayoutParams.WRAP_CONTENT);
    }
//images

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            uri = data.getData();

            mImage.setImageURI(uri);
            uriBtn = uri;

        }
    }


    public void uploadInfo() {

        StorageReference filepath = mStorage.child("Photos").child(uri.getPath());
        filepath.putFile(uriBtn).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUri = taskSnapshot.getDownloadUrl();
                mProgressDialog.show();
                //GET DATA
                String name = nameEditTxt.getText().toString();
                String desc = descTxt.getText().toString();

                //SET DATA
                s = new Pojo();
                s.setName(name);

                s.setDescription(desc);
                s.setImage(downloadUri.toString());

                //SIMPLE VALIDATION
                if (name != null && name.length() > 0) {
                    //THEN SAVE
                    if (helper.save(s)) {
                        //IF SAVED CLEAR EDITXT
                        nameEditTxt.setText("");
                        descTxt.setText("");

                        adapter = new CustomAdapter(MainActivity.this, helper.retrieve());
                        lv.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Name Must Not Be Empty", Toast.LENGTH_SHORT).show();
                }

                main = s.getImage();

                Picasso.with(MainActivity.this).load(s.getImage()).fit().centerCrop().into(mImageview);
                mProgressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Upload done", Toast.LENGTH_SHORT).show();

            }
        });
    }

}






