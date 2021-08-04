package com.example.marketplace.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketplace.Detail_product;
import com.example.marketplace.activity_add_product;
import com.example.marketplace.R;
import com.example.marketplace.adapter.ProductAdapter;
import com.example.marketplace.databinding.FragmentSlideshowBinding;
import com.example.marketplace.model.Favorite;
import com.example.marketplace.model.ManagementFavorites;
import com.example.marketplace.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.security.PrivateKey;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private RecyclerView rv_list;
    private ArrayList<Product> list;
    private FirebaseFirestore db;
    private ProductAdapter productAdapter;
    private FloatingActionButton btnAdd;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home,container,false);
        rv_list = root.findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("products");

        list = new ArrayList<>();
        Favorite.getInstance();

        reference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Favorite favorite = ManagementFavorites.getFavorite(getContext());
                for (QueryDocumentSnapshot document: queryDocumentSnapshots){
                    Product temp = document.toObject(Product.class);
                    temp.setId(document.getId());
                    temp.setFavorite(favorite.getFavorites().contains(temp));
                    list.add(temp);
                }
            }
        }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                productAdapter = new ProductAdapter(list,getActivity());
                productAdapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent act_goDetail = new Intent(getActivity(), Detail_product.class);
                        act_goDetail.putExtra("name",list.get(rv_list.getChildAdapterPosition(v)).getName());
                        act_goDetail.putExtra("description",list.get(rv_list.getChildAdapterPosition(v)).getDescription());
                        act_goDetail.putExtra("price",list.get(rv_list.getChildAdapterPosition(v)).getPrice());
                        act_goDetail.putExtra("quantity",list.get(rv_list.getChildAdapterPosition(v)).getQuantity());
                        act_goDetail.putExtra("url",list.get(rv_list.getChildAdapterPosition(v)).getUrl());
                        act_goDetail.putExtra("latitude",list.get(rv_list.getChildAdapterPosition(v)).getLatitude());
                        act_goDetail.putExtra("longitude", list.get(rv_list.getChildAdapterPosition(v)).getLongitude());
                        startActivity(act_goDetail);
                    }
                });
                rv_list.setAdapter(productAdapter);
                productAdapter.setListenerFavorite((product, position) -> {
                    Favorite favorite = ManagementFavorites.getFavorite(getContext());
                    if(product.isFavorite())
                        favorite.addFavorites(product);
                    else
                        favorite.removeFavorites(product);
                    ManagementFavorites.saveFavorites(favorite, getContext());
                });
            }
        });

        btnAdd = root.findViewById(R.id.btnAddProduct);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent act_goMain = new Intent(getActivity(), activity_add_product.class);
                startActivity(act_goMain);
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}