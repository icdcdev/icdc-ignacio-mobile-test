package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.adapter.OrderAdapter;
import com.example.myapplication.client.APIService;
import com.example.myapplication.client.Service;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.model.Order;
import com.example.myapplication.model.Orders;
import com.example.myapplication.util.DatePickerFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private Service service;
    private OrderAdapter adapterNoAsignados;
    private OrderAdapter adapterAsignados;
    private List<Order> orders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupTabs();
        setupListeners();
        setupAdapters();

        service = APIService.newServiceInstance();
        callServiceOrders();
    }

    private void setupAdapters() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        binding.rv.setLayoutManager(layoutManager);
        adapterNoAsignados = new OrderAdapter(this, new OrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Order order, Integer positionRemoved) {
                askToMoveOrder(order, positionRemoved);
            }
        });
        adapterAsignados = new OrderAdapter(this, null);
        binding.rv.setAdapter(adapterNoAsignados);
    }

    private void setupListeners() {
        binding.fab.setOnClickListener(view -> {
            askCloseSession();
        });

        binding.calendarImage.setOnClickListener(v->{
            showDatePickerDialog();
        });

        binding.fechaLabel.setOnClickListener(v->{
            showDatePickerDialog();
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterNoAsignados.filter( newText );
                adapterNoAsignados.notifyDataSetChanged();

                adapterAsignados.filter( newText );
                adapterAsignados.notifyDataSetChanged();
                return false;
            }
        });

        TabLayout tabLayout = binding.tabLayout;
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        binding.rv.setAdapter( adapterAsignados );
                        break;
                    case 1:
                        binding.rv.setAdapter(adapterNoAsignados);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void setupTabs() {
        TabLayout tabLayout = binding.tabLayout;
        TabLayout.Tab tabAsignados = tabLayout.newTab();
        TabLayout.Tab tabNoAsignados = tabLayout.newTab();
        tabAsignados.setText("Asignados");
        tabNoAsignados.setText("No Asignados");
        //tabAsignados.setIcon(R.drawable.ic_launcher);
        tabLayout.addTab(tabAsignados);
        tabLayout.addTab(tabNoAsignados, true);
    }

    private void callServiceOrders() {
        Call<Orders> call = service.listOrders();
        call.enqueue(new Callback<Orders>() {
            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {
                Orders res = response.body();
                List<Order> list = res.getData();
                //Toast.makeText(getApplicationContext(), "" + list.size(), Toast.LENGTH_SHORT).show();

                orders = list;
                adapterNoAsignados.setOrders( orders );
                adapterNoAsignados.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void askCloseSession() {
        new AlertDialog.Builder(MainActivity.this)
                //.setIcon(R.drawable.ic_launcher_background)
                .setTitle("¡Advertencia!")
                .setMessage("¿Desear cerrar la sesion?")
                .setPositiveButton("Aceptar", (dialog, which) -> {
                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("VW", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean(getString(R.string.logged_key), false);
                    editor.apply();

                    Intent intent = new Intent(MainActivity.this, SplashScreenActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {})
                .show();
    }

    private void askToMoveOrder(Order order, Integer positionRemoved) {
        new AlertDialog.Builder(MainActivity.this)
                //.setIcon(R.drawable.ic_launcher_background)
                //.setTitle("¡Advertencia!")
                .setMessage("¿Deseas asignar la orden?")
                .setPositiveButton("Aceptar", (dialog, which) -> {
                    adapterNoAsignados.removeOrder( order );
                    adapterNoAsignados.notifyItemRemoved( positionRemoved );

                    adapterAsignados.addOrder( order );
                    adapterAsignados.filter( String.valueOf( binding.searchView.getQuery() ) );
                    adapterAsignados.notifyItemInserted( adapterAsignados.getItemCount() + 1 );
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {})
                .show();
    }
}