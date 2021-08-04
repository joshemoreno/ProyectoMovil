package com.example.marketplace;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class activity_add_product extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    LoadinDialog loadinDialog = new LoadinDialog(activity_add_product.this);
    static final int MAP_REQUEST = 1;

    private FirebaseAuth mAuth;
    private StorageReference mStorage;
    private EditText et_name, et_description, et_quantity, et_price, et_lat, et_lon;
    private Button btnSave, btnCancel, btnUpload;
    private ImageView uploadImage;
    private Activity mySelf;
    private Uri globalPath;
    private TextView tv_setloc;


    private String nameProduct = "";
    private String description = "";
    private String quantity = "";
    private String price = "";
    private Task<Uri> imagePath;
    private String latitude = "";
    private String longitude = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        mStorage = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        et_name = findViewById(R.id.et_maneProduct);
        et_description = findViewById(R.id.et_descProduct);
        et_quantity = findViewById(R.id.et_countProduct);
        et_price = findViewById(R.id.priceProduct);

        btnSave = findViewById(R.id.btn_save);
        btnUpload = findViewById(R.id.btn_upLoad);
        btnCancel = findViewById(R.id.btn_cancel);
        uploadImage = findViewById(R.id.imageProduct);
        et_lat = findViewById(R.id.et_lat);
        et_lon = findViewById(R.id.et_lon);
        tv_setloc = findViewById(R.id.tv_setloc);
        mySelf = this;

        tv_setloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent map = new Intent(mySelf,MapActivity1.class);
                startActivityForResult(map, MAP_REQUEST);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent act_goHome = new Intent(mySelf, MenuActivity.class);
                startActivity(act_goHome);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImages();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nameProduct = et_name.getText().toString().trim();
                description = et_description.getText().toString().trim();
                quantity =  et_quantity.getText().toString().trim();
                price = et_price.getText().toString().trim();
                latitude = et_lat.getText().toString().trim();
                longitude = et_lon.getText().toString().trim();

                AlertDialog.Builder builder = new AlertDialog.Builder(mySelf);
                builder.setTitle(R.string.warning);
                builder.setIcon(R.drawable.warning);
                builder.setPositiveButton(R.string.btn_ok,null);

                if (nameProduct.isEmpty()){
                    builder.setMessage(R.string.msg_nameProduct);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else if (description.isEmpty()){
                    builder.setMessage(R.string.msg_descProduct);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else if (quantity.isEmpty()){
                    builder.setMessage(R.string.msg_countProduct);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else if (price.isEmpty()) {
                    builder.setMessage(R.string.msg_priceProduct);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else if (globalPath == null) {
                    builder.setMessage(R.string.msg_imageProduct);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else if (latitude.isEmpty()){
                    builder.setMessage(R.string.msg_locationProduct);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else if (longitude.isEmpty()){
                builder.setMessage(R.string.msg_locationProduct);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
                else{
                    loadinDialog.startLoading();
                    createProduct();
                }
            }
        });



    }

    private void createProduct(){
        StorageReference filepath = mStorage.child("photos").child(globalPath.getLastPathSegment());
        filepath.putFile(globalPath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagePath = taskSnapshot.getStorage().getDownloadUrl();
                while (!imagePath.isSuccessful());
                Uri downloadUri = imagePath.getResult();

                String id = mAuth.getCurrentUser().getEmail();

                Map<String, Object> product = new HashMap<>();
                product.put("name", nameProduct);
                product.put("description", description);
                product.put("quantity", quantity);
                product.put("price", price);
                product.put("url", downloadUri.toString());
                product.put("user",id);
                product.put("latitude",latitude);
                product.put("longitude", longitude);

                db.collection("products")
                        .add(product)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                loadinDialog.dismissDialog();
                                AlertDialog.Builder builder = new AlertDialog.Builder(mySelf);
                                builder.setTitle(R.string.msg_info);
                                builder.setMessage(R.string.msg_successProduct);
                                builder.setIcon(R.drawable.info);
                                builder.setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        Intent act_goMenu = new Intent(mySelf, MenuActivity.class);
                                        startActivity(act_goMenu);
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        });
            }
        });
    }

    private void uploadImages(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent,"Choose a application"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MAP_REQUEST) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                String lati = data.getStringExtra("lat");
                String longi = data.getStringExtra("lon");
                et_lat.setText(lati);
                et_lon.setText(longi);
            }
        }
        else {if (resultCode == RESULT_OK) {
            Uri path = data.getData();
            uploadImage.setImageURI(path);
            globalPath = path;
        }
    }
}
}