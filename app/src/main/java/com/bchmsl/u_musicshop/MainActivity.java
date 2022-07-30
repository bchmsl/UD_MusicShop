package com.bchmsl.u_musicshop;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bchmsl.u_musicshop.databinding.ActivityMainBinding;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    ArrayAdapter<String> spinnerAdapter;

    HashMap<String, Integer> itemsMap = new HashMap<>();
    String[] items;

    int selectedQuantity;
    int selectedPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        selectedQuantity = Integer.parseInt(binding.tvQuantity.getText().toString());
        setPrice();
        listeners();
        makeSpinner();
    }

    private void makeSpinner() {
        setupData();
        items = itemsMap.keySet().toArray(new String[0]);

        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spSpinner.setAdapter(spinnerAdapter);


    }

    private void listeners() {
        binding.btnQuantityIncrease.setOnClickListener(v -> {
            int value = Integer.parseInt(binding.tvQuantity.getText().toString());
            if (value < 99) {
                value += 1;
                binding.tvQuantity.setText(String.valueOf(value));
                selectedQuantity = value;
            }
            setPrice();
        });

        binding.btnQuantityReduce.setOnClickListener(v -> {
            int value = Integer.parseInt(binding.tvQuantity.getText().toString());
            if (value > 1) {
                value -= 1;
                binding.tvQuantity.setText(String.valueOf(value));
                selectedQuantity = value;
            }
            setPrice();
        });

        binding.spSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                @Nullable String goodsName = items[position];
                if (itemsMap.get(goodsName) != null) {
                    selectedPrice = itemsMap.get(goodsName);
                    setPrice();
                    setImage(goodsName);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.btnAddToCart.setEnabled(!s.toString().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.btnAddToCart.setOnClickListener(v -> {
            addToCart();
        });
    }

    private void setImage(@NonNull String goodsName) {
        switch (goodsName) {
            case "Guitar":
                binding.ivProductImage.setImageResource(R.drawable.img_guitar);
                break;
            case "Drums":
                binding.ivProductImage.setImageResource(R.drawable.img_drums);
                break;
            case "Piano":
                binding.ivProductImage.setImageResource(R.drawable.img_piano);
                break;
            default:
                binding.ivProductImage.setImageResource(R.mipmap.ic_launcher);
                break;
        }
    }

    private void setPrice() {
        int price = selectedPrice * selectedQuantity;
        binding.tvPrice.setText(String.valueOf(price));
    }

    private void setupData() {
        itemsMap.put("Guitar", 360);
        itemsMap.put("Drums", 6000);
        itemsMap.put("Piano", 18000);

    }

    private void addToCart() {
        Order order = new Order();
        order.userName = binding.etName.getText().toString();
        order.orderPrice = Integer.parseInt(binding.tvPrice.getText().toString());
        order.itemsName = binding.spSpinner.getSelectedItem().toString();
        order.quantity = Integer.parseInt(binding.tvQuantity.getText().toString());
        openActivity(order);
    }

    private void openActivity(@NonNull Order order) {
        Intent intent = new Intent(MainActivity.this, OrderActivity.class);
        intent.putExtra("userName", order.userName);
        intent.putExtra("orderPrice", order.orderPrice);
        intent.putExtra("itemsName", order.itemsName);
        intent.putExtra("quantity", order.quantity);
        startActivity(intent);
    }

}