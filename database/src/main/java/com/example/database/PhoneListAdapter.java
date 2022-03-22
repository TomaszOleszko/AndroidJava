package com.example.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database.data.Phone;
import com.example.database.data.PhoneDao;
import com.example.database.data.PhoneViewModel;

import java.util.List;

public class PhoneListAdapter extends RecyclerView.Adapter<PhoneListAdapter.PhoneViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private List<Phone> mPhoneList;

    public PhoneListAdapter(Context context){
        mLayoutInflater = LayoutInflater.from(context);
        this.mPhoneList = null;
    }

    @NonNull
    @Override
    public PhoneListAdapter.PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneListAdapter.PhoneViewHolder holder, int position) {

    }

    @Override
    public int getItemCount(){
        if(mPhoneList != null)
            return mPhoneList.size();
        return 0;
    }

    public void setPhoneList(List<Phone> phoneList){
        this.mPhoneList = phoneList;
        notifyDataSetChanged();
    }

    public class PhoneViewHolder extends RecyclerView.ViewHolder {


        public PhoneViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
