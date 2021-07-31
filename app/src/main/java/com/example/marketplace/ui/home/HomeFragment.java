package com.example.marketplace.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.marketplace.Detail_product;
import com.example.marketplace.MenuActivity;
import com.example.marketplace.Product;
import com.example.marketplace.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private RecyclerView rv_list;
    private ArrayList<Product> list;
    private FirebaseFirestore db;
    private ProductAdapter productAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home,container,false);
        rv_list = root.findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("products");

        list = new ArrayList<>();

        reference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot document: queryDocumentSnapshots){
                    list.add(document.toObject(Product.class));
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
                        startActivity(act_goDetail);
                    }
                });
                rv_list.setAdapter(productAdapter);
            }
        });
        return root;
    }
}


class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements  View.OnClickListener{

    ArrayList<Product> products;
    Context context;
    DecimalFormat formater = new DecimalFormat("###,###.##");
    private View.OnClickListener listener;

    public ProductAdapter(ArrayList<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductAdapter.ViewHolder holder, int position) {
        holder.name.setText(products.get(position).getName());
        holder.description.setText(products.get(position).getDescription());
        holder.price.setText("$"+formater.format(Integer.parseInt(products.get(position).getPrice())));
        Glide.with(context).load(products.get(position).getUrl()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setOnClickListener (View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, description, price;
        private ImageView image;
        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.nameCart);
            description = v.findViewById(R.id.descriptionCart);
            price = v.findViewById(R.id.priceCart);
            image = v.findViewById(R.id.imageCart);
        }
    }
}