package com.example.database;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.database.data.Phone;
import com.example.database.data.PhoneViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private PhoneViewModel mPhoneViewModel;
    private PhoneListAdapter mAdapter;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    public static ActivityResultLauncher<Intent> mActivityLauncher;

    public static final String PHONE_KEY = "com.example.database.PHONE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createRes();
        populate_db();

        mPhoneViewModel.getAllPhones().observe(this, phones -> mAdapter.setPhoneList(phones));
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Usun pozycje");
            builder.setMessage("Czy jestes pewien?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                int pos = viewHolder.getAdapterPosition();
                mPhoneViewModel.delete(((PhoneListAdapter.PhoneViewHolder) viewHolder).phone);
                mAdapter.notifyItemRemoved(pos);
            });
            builder.setNegativeButton("No", (dialogInterface, i) -> mAdapter.notifyItemChanged(viewHolder.getAdapterPosition()));
            builder.show();
        }
    };

    private void populate_db() {
        ArrayList<Phone> phoneList = new ArrayList<>(Arrays.asList(
                new Phone(0, "Samsung", "Galaxy-S22", "11.0", "https://www.samsung.com/pl/smartphones/galaxy-s22/"),
                new Phone(0, "Samsung", "Galaxy Z Fold3 5G", "9.0", "https://www.samsung.com/pl/smartphones/galaxy-z-fold3-5g/"),
                new Phone(0, "Samsung", "Galaxy Z Flip3 5G", "9.0", "https://www.samsung.com/pl/smartphones/galaxy-z-flip3-5g/"),
                new Phone(0, "Samsung", "Galaxy M52 5G", "9.0", "https://www.samsung.com/pl/smartphones/galaxy-m/galaxy-m52-5g-light-blue-128gb-sm-m526blbdeue/"),
                new Phone(0, "Samsung", "Galaxy A53 5G", "9.0", "https://www.samsung.com/pl/smartphones/galaxy-a/galaxy-a53-5g-awesome-blue-128gb-sm-a536blbneue/"),
                new Phone(0, "ASUS", "Zenfone 8", "10.0", "https://www.asus.com/pl/Mobile/Phones/ZenFone/Zenfone-8/"),
                new Phone(0, "ASUS", "ROG Phone 5s Pro", "10.0", "https://rog.asus.com/pl/phones/rog-phone-5s-pro-model/"),
                new Phone(0, "ASUS", "ROG Phone 5s", "10.0", "https://rog.asus.com/pl/phones/rog-phone-5s-model/"),
                new Phone(0, "ASUS", "ROG Phone 5s Ultimate", "10.0", "https://rog.asus.com/pl/phones/rog-phone-5-ultimate-model/"),
                new Phone(0, "Nokia", "Nokia-G21", "11.0", "https://www.nokia.com/phones/pl_pl/nokia-g-21")

        ));

         phoneList.forEach((v) -> mPhoneViewModel.insert(v));
    }

    private void createRes() {
        recyclerView = findViewById(R.id.recyclerview);
        fab = findViewById(R.id.floatingActionButton);

        mAdapter = new PhoneListAdapter(this);
        recyclerView.setAdapter(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPhoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);

        mActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bundle bundle = result.getData().getExtras();
                        Phone phone = new Phone(0,
                                bundle.getString(AddPhoneActivity.MANUFACTURER_KEY),
                                bundle.getString(AddPhoneActivity.MODEL_KEY),
                                bundle.getString(AddPhoneActivity.ANDROID_VERSION_KEY),
                                bundle.getString(AddPhoneActivity.WEB_SITE_KEY)
                        );
                        mPhoneViewModel.insert(phone);

                        System.out.println("INSERT");
                    }
                    if (result.getResultCode() == RESULT_FIRST_USER + 1 && result.getData() != null) {
                        Bundle bundle = result.getData().getExtras();
                        mPhoneViewModel.update(bundle.getParcelable(UpdatePhoneActivity.PHONE_OUT_KEY));
                        System.out.println("UPDATE");
                    }
                    if (result.getResultCode() == RESULT_CANCELED) {
                        System.out.println("Canceled");
                    }
                }
        );

        fab.setOnClickListener(view -> startAddActivity());
    }

    public void startAddActivity() {
        mActivityLauncher.launch(new Intent(this, AddPhoneActivity.class));
    }

    public void startUpdateActivity(Phone phone) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(PHONE_KEY, phone);
        Intent intent = new Intent(this, UpdatePhoneActivity.class);
        intent.putExtras(bundle);
        mActivityLauncher.launch(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_first_item) {
            mPhoneViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}