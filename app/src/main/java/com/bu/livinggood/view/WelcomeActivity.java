package com.bu.livinggood.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.bu.livinggood.controller.FirebaseController;
import com.bu.livinggood.databinding.ActivityWelcomeBinding;

import java.util.Locale;

public class WelcomeActivity extends AppCompatActivity {
    private ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // store search value.
        SearchView searchVal = binding.welSvPlace;

        Spinner s1 = binding.welSpMinPrice;
        Spinner s2 = binding.welSpMaxPrice;

        // check login
        checkLogin();

        binding.btSearch.setOnClickListener(view1 -> {
            Intent i = new Intent(WelcomeActivity.this, MapActivity.class);
            Bundle bundle = new Bundle();
            // collect user filter, and send to Map activity
            bundle.putString("SearchVal", searchVal.getQuery().toString());
            bundle.putInt("MinPrice", s1.getSelectedItemPosition());
            bundle.putInt("MaxPrice", s2.getSelectedItemPosition());
            bundle.putInt("Bedroom", binding.welSpBedroom.getSelectedItemPosition());
            bundle.putInt("Bathroom", binding.welSpBathroom.getSelectedItemPosition());
            bundle.putInt("Radius", binding.welSkRadius.getProgress());
            bundle.putBoolean("Food", binding.cbFood.isChecked());
            bundle.putBoolean("Security", binding.cbSecurity.isChecked());
            bundle.putBoolean("Transit", binding.cbTransit.isChecked());
            bundle.putBoolean("Store", binding.cbStore.isChecked());

            i.putExtras(bundle);
            startActivity(i);
        });

        // Radius seek bar. Modify radius text when seek bar changes.
        binding.welSkRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            double pval = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pval = progress * 0.1 + 0.5;
                binding.welTvRadius.setText(String.format(Locale.US, "%.1f MI", pval));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void checkLogin() {
        //get auth
        FirebaseController model = new FirebaseController();
        //not login
        if (!(model.checkLogin())) {
            //start login page
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }
    }
}