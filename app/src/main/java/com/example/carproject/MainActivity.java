package com.example.carproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText carMpgInput, carDisplacementInput, carWeightInput, carSpeedInput, carHorsePowerInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide the action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        //         Recovering EditText fields
        carMpgInput = findViewById(R.id.CarMpgInput);
        carDisplacementInput = findViewById(R.id.CarDisplacementInput);
        carWeightInput = findViewById(R.id.CarWeightInput);
        carSpeedInput = findViewById(R.id.CarSpeedInput);
        carHorsePowerInput = findViewById(R.id.CarHorsePowerInput);


        //navigating to the algorithmPage
        Button MlAlgorithmButton = findViewById(R.id.btn_submit);
        MlAlgorithmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Retrieve values from EditText fields
                String mpg = carMpgInput.getText().toString();
                String displacement = carDisplacementInput.getText().toString();
                String weight = carWeightInput.getText().toString();
                String speed = carSpeedInput.getText().toString();
                String horsePower = carHorsePowerInput.getText().toString();

                if (mpg.isEmpty() || displacement.isEmpty() || weight.isEmpty() ||
                        speed.isEmpty() || horsePower.isEmpty()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Violation !!")
                            .setMessage("All Fields Are required to perform an efficient prediction")
                            .setPositiveButton("Correct", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //dismissing the dialog alert
                                    dialogInterface.dismiss();
                                }
                            }).show();
                } else {
                    String message = "Mpg: " + mpg +
                            "\nDisplacement: " + displacement +
                            "\nWeight: " + weight +
                            "\nSpeed: " + speed +
                            "\nHorsePower: " + horsePower;

                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

                    // Creating an Intent to start AlgorithmActivity
                    Intent intent = new Intent(MainActivity.this, AlgorithmActivity.class);
                    intent.putExtra("mpg", Double.parseDouble(mpg));
                    intent.putExtra("displacement", Double.parseDouble(displacement));
                    intent.putExtra("weight", Double.parseDouble(weight));
                    intent.putExtra("speed", Double.parseDouble(speed));
                    intent.putExtra("horsePower", Double.parseDouble(horsePower));
                    startActivity(intent);
                }
            }
        });

    }
}
