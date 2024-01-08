package com.example.carproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AlgorithmActivity extends AppCompatActivity {

    private RadioButton knnRadioButton, dtRadioButton, bayesNetRadioButton;
    protected double mpg, displacement, weight, speed, horsePower;
    private EditText kValueEditText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm);


        // Hide the action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // Retrieve data from the intent
        Intent intent = getIntent();
        if (intent != null) {
            // Get the Bundle containing the data
            Bundle dataBundle = intent.getExtras();
            if (dataBundle != null) {
                // Iterate over the keys and retrieve the data dynamically
                for (String key : dataBundle.keySet()) {
                    // Retrieve the data using key and display it
                    double data = dataBundle.getDouble(key, 0.0); // Setting 0.0 is the default value
                    switch (key){
                        case "mpg": mpg = data;
                            Toast.makeText(this, "Received " + key + ": " + mpg, Toast.LENGTH_SHORT).show();
                            break;
                        case "displacement" : displacement = data;
                            Toast.makeText(this, "Received " + key + ": " + displacement, Toast.LENGTH_SHORT).show();
                            break;
                        case "weight": weight = data;
                            Toast.makeText(this, "Received " + key + ": " + weight, Toast.LENGTH_SHORT).show();
                            break;
                        case "speed" : speed = data;
                            Toast.makeText(this, "Received " + key + ": " + speed, Toast.LENGTH_SHORT).show();
                            break;
                        case "horsePower": horsePower = data;
                            Toast.makeText(this, "Received " + key + ": " + horsePower, Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
        }

        // Recovering views
        knnRadioButton = findViewById(R.id.KNNradioButton);
        dtRadioButton = findViewById(R.id.DTradioButton);
        bayesNetRadioButton = findViewById(R.id.BayesNetradioButton);
        kValueEditText = findViewById(R.id.KNNInput);
        submitButton = findViewById(R.id.Go);

        kValueEditText.setVisibility(View.GONE); // Set initial visibility to GONE
        knnRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                kValueEditText.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check which algorithm is selected
                String selectedAlgorithm = getSelectedAlgorithm();

                // Retrieve K value for KNN if applicable
                int kValue = -1; //default value if not applicable
                if (selectedAlgorithm.equals("KNN")) {
                    String kValueStr = kValueEditText.getText().toString();
                    if (!kValueStr.isEmpty()) {
                        kValue = Integer.parseInt(kValueStr);
                    }
                }

                // For now, let's just show a toast message with the selected algorithm
                String message = "Selected Algorithm: " + selectedAlgorithm +
                        (selectedAlgorithm.equals("KNN") ? ("\nK Value: " + kValue) : "");
                Toast.makeText(AlgorithmActivity.this, message, Toast.LENGTH_SHORT).show();

                // Creating an Intent to start AlgorithmActivity
                Intent intent = new Intent(AlgorithmActivity.this, ResultDisplay.class);
                if (selectedAlgorithm.equals("KNN")){
                    intent.putExtra("selectedAlgorithm", "KNN");
                    intent.putExtra("K",kValue);
                } else if (selectedAlgorithm.equals("Decision Tree")) {
                    intent.putExtra("selectedAlgorithm", "Decision Tree");
                }else{
                    intent.putExtra("selectedAlgorithm", "Bayes Network");
                }
                intent.putExtra("mpg", mpg);
                intent.putExtra("displacement", displacement);
                intent.putExtra("weight", weight);
                intent.putExtra("speed", speed);
                intent.putExtra("horsePower", horsePower);
                startActivity(intent);

            }
        });
    }

    private String getSelectedAlgorithm() {
        if (knnRadioButton.isChecked()) {
            return "KNN";
        } else if (dtRadioButton.isChecked()) {
            return "Decision Tree";
        } else if (bayesNetRadioButton.isChecked()) {
            return "Bayes Network";
        } else {
            return "No Algorithm Selected";
        }
    }
}