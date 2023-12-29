package com.example.carproject.Handlers;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.carproject.ResultDisplay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KnnClassifierHandler {

    private final Context context;

    public KnnClassifierHandler(Context context) {
        this.context = context;
    }

    public String knnWorkInBackground(int k, Cars car) throws Throwable {
        List<Cars> cars = loadDataSet();
        List<Cars> so = calcDistance(cars, car);
        Collections.sort(so);
        List<Cars> finalClassesToChooseFrom = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            Log.d("NearestNeighbor", "Nearest Neighbor: " + so.get(i));
            finalClassesToChooseFrom.add(so.get(i));
        }

        // Predict the origin based on the k-NN algorithm
        return predictOrigin(finalClassesToChooseFrom);

    }

    private List<Cars> loadDataSet() throws IOException {
        List<Cars> cars = new ArrayList<>();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("dataset.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = br.readLine()) != null) {
            String[] F = line.split(",");
            Cars c = new Cars(
                    Integer.parseInt(F[0]),
                    Integer.parseInt(F[1]),
                    Integer.parseInt(F[2]),
                    Integer.parseInt(F[3]),
                    Integer.parseInt(F[4]),
                    F[5]
            );
            cars.add(c);
        }

        br.close();
        return cars;
    }

    private List<Cars> calcDistance(List<Cars> cL, Cars c) {
        for (Cars car : cL) {
            double m = Math.pow(car.Mpg - c.Mpg, 2);
            double d = Math.pow(car.Displacement - c.Displacement, 2);
            double h = Math.pow(car.HorsePower - c.HorsePower, 2);
            double w = Math.pow(car.Weight - c.Weight, 2);
            double a = Math.pow(car.Acceleration - c.Acceleration, 2);
            double distance = Math.sqrt(m + d + h + w + a);
            car.Distance = distance;
        }
        return cL;
    }

    private String predictOrigin(List<Cars> nearestNeighbors) {
        HashMap<String, Integer> originCount = new HashMap<>();

        for (Cars car : nearestNeighbors) {
            String origin = car.Origin;
            originCount.put(origin, originCount.getOrDefault(origin, 0) + 1);
        }

        int maxCount = 0;
        String predictedOrigin = null;

        for (Map.Entry<String, Integer> entry : originCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                predictedOrigin = entry.getKey();
            }
        }

        return predictedOrigin;
    }
}
