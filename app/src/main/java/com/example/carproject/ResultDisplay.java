package com.example.carproject;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import com.example.carproject.Handlers.Cars;
import com.example.carproject.Handlers.KnnClassifierHandler;

public class ResultDisplay extends AppCompatActivity {

    protected double mpg, displacement, weight, speed, horsePower;
    protected String selectedAlgorithm;

    protected int kValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_display);

        // Retrieve data from the intent
        Intent intent = getIntent();
        if (intent != null) {
            // Get the Bundle containing the data
            Bundle dataBundle = intent.getExtras();
            if (dataBundle != null) {
                // Iterate over the keys and retrieve the data dynamically
                for (String key : dataBundle.keySet()) {
                    // Retrieve the data using key and log it
                    double data = dataBundle.getDouble(key, 0.0); // Setting 0.0 is the default value
                    switch (key) {
                        case "mpg":
                            mpg = data;
                            Log.d("ResultDisplay", "Received " + key + ": " + mpg);
                            break;
                        case "displacement":
                            displacement = data;
                            Log.d("ResultDisplay", "Received " + key + ": " + displacement);
                            break;
                        case "weight":
                            weight = data;
                            Log.d("ResultDisplay", "Received " + key + ": " + weight);
                            break;
                        case "speed":
                            speed = data;
                            Log.d("ResultDisplay", "Received " + key + ": " + speed);
                            break;
                        case "horsePower":
                            horsePower = data;
                            Log.d("ResultDisplay", "Received " + key + ": " + horsePower);
                            break;
                        case "selectedAlgorithm":
                            selectedAlgorithm = dataBundle.getString("selectedAlgorithm", "");
                            if (selectedAlgorithm.equals("KNN")) {
                                if (dataBundle.containsKey("K")) {
                                    kValue = dataBundle.getInt("K", 0); // Setting 0 is the default value
                                    Log.d("ResultDisplay", "Received Selected Algorithm: " + selectedAlgorithm);
                                    Log.d("ResultDisplay", "Received K Value: " + kValue);
                                } else {
                                    Log.d("ResultDisplay", "K value is not present for KNN algorithm");
                                }
                            } else {
                                Log.d("ResultDisplay", "Received Selected Algorithm: " + selectedAlgorithm);
                            }
                            break;

                    }
                }
            }
        }

        //Handling The Data For The Appropriate Algorithm (Working And Logging The Result)
        if (selectedAlgorithm.equals("KNN")) {

                KnnClassifierHandler knnClassifier = new KnnClassifierHandler(this);
                Cars car = new Cars(mpg, displacement, horsePower, weight, speed);
            try {
                String predictedAlgo = knnClassifier.knnWorkInBackground(kValue, car);
                Log.d("predictedAlgo", "predictedAlgo: " + predictedAlgo);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }

        }



    }

}
