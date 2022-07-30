package com.bchmsl.u_musicshop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bchmsl.u_musicshop.databinding.ActivityOrderBinding;

import java.util.Locale;

public class OrderActivity extends AppCompatActivity {

    private ActivityOrderBinding binding;

    String userName;
    Integer orderPrice;
    String itemsName;
    Integer quantity;

    String[] addresses = {"bachana.mosulishvili.1@btu.edu.ge"};
    String subject = "Udemy - Order for Music Shop";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        setData();
        listeners();
    }

    private void listeners() {
        binding.btnCheckout.setOnClickListener(v -> sendOrder());
    }

    private void setData() {
        userName = getIntent().getStringExtra("userName");
        orderPrice = getIntent().getIntExtra("orderPrice", 0);
        itemsName = getIntent().getStringExtra("itemsName");
        quantity = getIntent().getIntExtra("quantity", 0);

        binding.tvItemName.setText(itemsName);
        binding.tvQuantity.setText(String.valueOf(quantity));
        binding.tvTotal.setText(String.valueOf(orderPrice));
        binding.tvUsername.setText(userName);

        switch (itemsName) {
            case "Guitar":
                binding.ivImage.setImageResource(R.drawable.img_guitar);
                break;
            case "Drums":
                binding.ivImage.setImageResource(R.drawable.img_drums);
                break;
            case "Piano":
                binding.ivImage.setImageResource(R.drawable.img_piano);
                break;
            default:
                binding.ivImage.setImageResource(R.mipmap.ic_launcher);
                break;
        }
    }

    private String getMessage() {
        return "This email is sent from Music Shop Application\n" +
                "-----------------------------\n" +
                "Username: " + userName + "\n" +
                "Order: " + itemsName + "\n" +
                "Quantity: " + quantity + "\n" +
                "Total: " + orderPrice + "\n" +
                "-----------------------------\n" +
                "Thanks for using Music Shop :)";
    }

    private void sendOrder() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, getMessage());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
}