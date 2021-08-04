package com.example.marketplace.ui.slideshow;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketplace.MenuActivity;
import com.example.marketplace.R;

import com.example.marketplace.model.Product;
import com.example.marketplace.ui.home.HomeFragment;
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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class SlideshowFragment extends Fragment {

    private ArrayList<Product> list;
    private ArrayList<String> ids;
    private FirebaseFirestore db;
    private RecyclerView rv_list2;
    private Button btn_buy;
    private ShoppingAdapter shoppingAdapter;
    private Integer total=0;
    private TextView tv_price;
    private LinearLayout linear_empty;
    DecimalFormat formater = new DecimalFormat("###,###.##");


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
//        slideshowViewModel =
//                new ViewModelProvider(this).get(ShoppingAdapter.class);

//        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
        View root = inflater.inflate(R.layout.fragment_slideshow,container,false);
        rv_list2 = root.findViewById(R.id.rv_list2);
        btn_buy = root.findViewById(R.id.btn_buy);
        tv_price = root.findViewById(R.id.item_price);
        linear_empty = root.findViewById(R.id.linear_empty);
        TextView tv_total = root.findViewById(R.id.itemsTotal);
        rv_list2.setLayoutManager(new LinearLayoutManager(getActivity()));


//        SharedPreferences preferences= getActivity().getSharedPreferences("products", Context.MODE_PRIVATE);
//        String name = preferences.getString("name","");
//        Integer price = preferences.getInt("price",0);

        list = new ArrayList<>();
        ids = new ArrayList<>();
        CollectionReference reference = db.collection("shopping");
        reference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot document: queryDocumentSnapshots){
                    Product temp = document.toObject(Product.class);
                    ids.add(temp.getId());
                    list.add(temp);
                }
            }
        }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                shoppingAdapter = new ShoppingAdapter(list,getActivity());
                for (int i = 0; i<list.size();i++) {
                    total+= Integer.parseInt(list.get(i).getPrice());
                }
                tv_total.setText("Total: $" + formater.format(total));
                rv_list2.setAdapter(shoppingAdapter);

                if (list.size()==0){
                    btn_buy.setVisibility(View.GONE);
                    tv_total.setVisibility(View.GONE);
                    linear_empty.setVisibility(View.VISIBLE);
                }

                    btn_buy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int i = 0; i < ids.size(); i++) {
                                int newVal = 0;
                                int cantIni = Integer.parseInt(list.get(i).getCantIni());
                                int count = Integer.parseInt(list.get(i).getCount());
                                newVal = cantIni - count;
                                Map<String, Object> product = new HashMap<>();
                                product.put("quantity", String.valueOf(newVal));
                                int finalI = i;
                                db.collection("products").document(ids.get(i))
                                        .update(product)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                db.collection("shopping")
                                                        .document(String.valueOf(finalI))
                                                        .delete()
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                HomeFragment newFragment;
                                                                newFragment = new HomeFragment();
                                                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                                                transaction.replace(R.id.nav_host_fragment_content_menu, newFragment);
                                                                transaction.addToBackStack(null);
                                                                transaction.commit();
                                                            }
                                                        });
                                            }
                                        });
                            }
                        }
                    });

            }
        });



        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}