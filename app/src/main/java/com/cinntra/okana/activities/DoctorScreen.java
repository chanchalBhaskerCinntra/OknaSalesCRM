package com.cinntra.okana.activities;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cinntra.okana.R;
import com.cinntra.okana.databinding.StaticUiPageOneBinding;

public class DoctorScreen extends AppCompatActivity {
    StaticUiPageOneBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = StaticUiPageOneBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());
        // setSupportActionBar(binding.toolbar);

        String[] data = getResources().getStringArray(R.array.Select_Doctor);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, data);
        binding.participantValue.setAdapter(adapter);

        binding.participantValue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedText = (String) parent.getItemAtPosition(position);
                binding.participantValue.setText(selectedText);
                // Handle the selected item
            }
        });





        String[] dataChemist = getResources().getStringArray(R.array.Select_Chemist);

        ArrayAdapter<String> adapterChemist = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, dataChemist);
        binding.autocompleteChemist.setAdapter(adapterChemist);

        binding.autocompleteChemist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedText = (String) parent.getItemAtPosition(position);
                binding.autocompleteChemist.setText(selectedText);
                // Handle the selected item
            }
        });


        String[] dataproduct = getResources().getStringArray(R.array.product_list);

        ArrayAdapter<String> adapterPrroduct = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, dataproduct);
        binding.autocompleteProduct.setAdapter(adapterPrroduct);

        binding.autocompleteProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedText = (String) parent.getItemAtPosition(position);
                binding.autocompleteProduct.setText(selectedText);
                // Handle the selected item
            }
        });


        binding.autocompleteCompettitorProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedText = (String) parent.getItemAtPosition(position);
                binding.autocompleteCompettitorProduct.setText(selectedText);
                // Handle the selected item
            }
        });

        String[] datacompettitor = getResources().getStringArray(R.array.company_brand_name);

        ArrayAdapter<String> adapterdatacompettitor = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, datacompettitor);
        binding.autocompleteCompettitorBrand.setAdapter(adapterdatacompettitor);
        binding.autocompleteCompettitorProduct.setAdapter(adapterPrroduct);

        binding.autocompleteCompettitorBrand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedText = (String) parent.getItemAtPosition(position);
                binding.autocompleteCompettitorBrand.setText(selectedText);
                // Handle the selected item
            }
        });


        binding.btnSave.setOnClickListener(view -> {
            finish();
        });

        binding.btnCompetitor.setOnClickListener(view -> {
            if (binding.autocompleteChemist.getText().toString().isEmpty() || binding.autocompleteProduct.getText().toString().isEmpty() ){
                Toast.makeText(this, "please select chemist or product", Toast.LENGTH_SHORT).show();
            }else {
               binding.cardCompetitor.setVisibility(View.VISIBLE);
               binding.tvChemistNameSelected.setText(binding.autocompleteChemist.getText().toString());
               binding.tvProductNameSelected.setText(binding.autocompleteProduct.getText().toString());
            }
        });
    }
}
