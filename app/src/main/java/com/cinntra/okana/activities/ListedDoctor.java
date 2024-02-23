package com.cinntra.okana.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.cinntra.okana.R;
import com.cinntra.okana.databinding.StaticUiPageTwoBinding;
import com.cinntra.okana.globals.Globals;
import com.pixplicity.easyprefs.library.Prefs;

public class ListedDoctor extends AppCompatActivity {
    StaticUiPageTwoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = StaticUiPageTwoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (Prefs.getString("key", "").equalsIgnoreCase("chemist")) {
            binding.toolbar.setTitle("Listed Chemist");
        } else {
            binding.toolbar.setTitle("Listed Dr.");
        }


        String[] data = getResources().getStringArray(R.array.Select_Doctor);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, data);
        binding.participantValue.setAdapter(adapter);

        binding.participantValue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedText = (String) parent.getItemAtPosition(position);
                binding.participantValue.setText(selectedText);
                binding.specailiyt.setText("CRD");
                binding.Category.setText("Heart");
                binding.classes.setText("TWA");
                binding.dob.setText("10-03-1968");
                binding.dow.setText("10-03-2023");

                // Handle the selected item
            }
        });


        String[] dataMember = getResources().getStringArray(R.array.member_list);

        ArrayAdapter<String> adapterMember = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, dataMember);
        binding.autocompletemember.setAdapter(adapterMember);

        binding.autocompletemember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedText = (String) parent.getItemAtPosition(position);
                binding.autocompletemember.setText(selectedText);
                // Handle the selected item
            }
        });

        binding.rcpa.setOnClickListener(view -> {
            Intent intent = new Intent(this, DoctorScreen.class);
            startActivity(intent);
        });

        binding.btnSave.setOnClickListener(view -> {
            Toast.makeText(this, "Updated SuccessFully", Toast.LENGTH_SHORT).show();

            finish();
        });


        binding.productCard.setOnClickListener(view -> {
            Intent intent = new Intent(this, ItemsList.class);
            intent.putExtra("CategoryID", "");
//            fromwhere= String.valueOf(getIntent().getExtras().getInt("CategoryID"));
            startActivity(intent);
        });
        binding.newVisitCard.setOnClickListener(view -> {
            Globals.selectDat(this);
        });

    }
}