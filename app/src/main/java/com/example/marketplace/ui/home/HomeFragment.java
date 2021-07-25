package com.example.marketplace.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketplace.R;

import org.json.JSONArray;
import org.json.JSONException;

public class HomeFragment extends Fragment {


    private String textJson = "[{\"name\":\"Pedro\",\"lastname\":\"Fernandez\",\"id\":\"1\",\"phone\":\"31231313\",\"address\":\"Manizales\",\"color\":\"red\",\"property\":[{\"name\":\"car\",\"amount\":100000000},{\"name\":\"house\",\"amount\":200000000}]},{\"name\":\"Mario\",\"lastname\":\"Arías\",\"id\":\"1\",\"phone\":\"31231313\",\"color\":\"purple\",\"address\":\"Manizales\",\"property\":[{\"name\":\"car\",\"amount\":100000000},{\"name\":\"house\",\"amount\":200000000}]}]";
    private RecyclerView rv_list;
    private RecyclerView.Adapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home,container,false);
        rv_list = root.findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));

        try {
            JSONArray json = new JSONArray(textJson);
            mAdapter = new UserAdapter(json, getActivity());
            rv_list.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }



        return root;
    }


}


class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private JSONArray userModelList;
    private Activity mySelf;

    public UserAdapter(JSONArray userModelList, Activity mySelf) {
        this.userModelList = userModelList;
        this.mySelf = mySelf;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        try {
            String name = this.userModelList.getJSONObject(position).getString("name");
            String text = this.userModelList.getJSONObject(position).getString("lastname");
            holder.name.setText(name);

            String color = this.userModelList.getJSONObject(position).getString("color");

            if (color.equals("red")) {
                holder.name.setBackgroundColor(mySelf.getResources().getColor(R.color.red));
            } else if (color.equals("purple")) {
                holder.name.setBackgroundColor(mySelf.getResources().getColor(R.color.purple_200));
            }

            holder.text.setText(text);
        } catch (JSONException e) {
            holder.name.setText("error");
        }

    }

    @Override
    public int getItemCount() {
//        return userModelList.size();
        return this.userModelList.length();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView text;
        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.tv_row);
            text = v.findViewById(R.id.tv_text);
        }
    }
}
