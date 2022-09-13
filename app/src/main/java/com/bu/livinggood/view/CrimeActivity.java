package com.bu.livinggood.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bu.livinggood.api.CrimeAPI;
import com.bu.livinggood.bean.CrimeData;
import com.bu.livinggood.databinding.ActivityCrimeBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrimeActivity extends AppCompatActivity {
    private Button get;
    private TextView csi;
    private TextView incidents;
    private ActivityCrimeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCrimeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        get = binding.btnGet;
        csi = binding.csi;
        incidents = binding.incidentResult;

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrimeAPI api = new CrimeAPI();

                api.request("-71.1077", "42.3498", "1mi",
                        "2021-06-01T00:00:00.000Z", "2022-01-01T00:00:00.000Z", 1, new Callback<CrimeData>() {
                            @Override
                            public void onResponse(Call<CrimeData> call, Response<CrimeData> response) {
                                assert response.body() != null;
                                String inci_text = response.body().getTotalIncidents().toString();
                                String csi_text = response.body().getTotalCsi();
                                incidents.setText(inci_text);
                                csi.setText(csi_text);
                                //saveData(response.body());
                            }

                            @Override
                            public void onFailure(Call<CrimeData> call, Throwable t) {
                                t.printStackTrace();
                            }

                        }
                );
            }
        });
    }

}