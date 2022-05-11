package com.example.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database.data.Phone;

import java.util.List;

public class PhoneListAdapter extends RecyclerView.Adapter<PhoneListAdapter.PhoneViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private List<Phone> mPhoneList;


    public PhoneListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mPhoneList = null;
    }

    @NonNull
    @Override
    public PhoneListAdapter.PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View wiersz = mLayoutInflater.inflate(R.layout.model, parent, false);
        return new PhoneViewHolder(wiersz);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneListAdapter.PhoneViewHolder PhoneHolder, int numerWiersza) {

        if (PhoneHolder.getAdapterPosition() == 0) {
            PhoneHolder.collumn1.setBackgroundResource(R.drawable.table_header_cell);
            PhoneHolder.collumn2.setBackgroundResource(R.drawable.table_header_cell);
            PhoneHolder.collumn1.setText("Producent");
            PhoneHolder.collumn2.setText("Model");
        } else {
            Phone phone = mPhoneList.get(numerWiersza - 1);
            PhoneHolder.collumn1.setText(phone.mProducent);
            PhoneHolder.collumn1.setBackgroundResource(R.drawable.table_content_cell);
            PhoneHolder.collumn2.setText(phone.mModel);
            PhoneHolder.collumn2.setBackgroundResource(R.drawable.table_content_cell);
            PhoneHolder.phone = phone;
            PhoneHolder.phoneRow.setOnClickListener(view -> {
                Context context = view.getContext();
                if (context instanceof MainActivity) {
                    ((MainActivity) context).startUpdateActivity(phone);
                } else {
                    System.out.println("PHONELISTADAPTER onBINDVIEWHOLER context error");
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mPhoneList != null)
            return mPhoneList.size() + 1;
        return 0;
    }

    public void setPhoneList(List<Phone> phoneList) {
        this.mPhoneList = phoneList;
        notifyDataSetChanged();
    }

    public class PhoneViewHolder extends RecyclerView.ViewHolder {
        LinearLayout phoneRow;
        TextView collumn1;
        TextView collumn2;
        Phone phone;

        public PhoneViewHolder(@NonNull View itemView) {
            super(itemView);
            collumn1 = itemView.findViewById(R.id.collumn1);
            collumn2 = itemView.findViewById(R.id.collumn2);
            phoneRow = itemView.findViewById(R.id.phoneRow);
            phone = null;
        }
    }
}
