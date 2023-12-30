package com.example.carproject.Handlers;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NaiveBayesHandler {

    private final Context context;

    public NaiveBayesHandler(Context context) {

        this.context = context;
    }

    public String NaiveBayesWorkInBackground(Cars CarInstance) throws IOException {

        //Attributes To be processed
        List<Integer> mpg = new ArrayList<>();
        List<Integer> displacement = new ArrayList<>();
        List<Integer> horsepower = new ArrayList<>();
        List<Integer> weight = new ArrayList<>();
        List<Integer> acceleration = new ArrayList<>();

        String line = "";

        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("dataset.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        while ((line=br.readLine())!= null){

            String F[] = line.split(",");

            //Filling The Attributes Lists To Calculate the average for each attributes
            mpg.add(Integer.parseInt(F[0]));
            displacement.add(Integer.parseInt(F[1]));
            horsepower.add(Integer.parseInt(F[2]));
            weight.add(Integer.parseInt(F[3]));
            acceleration.add(Integer.parseInt(F[4]));
        }

        //Calculating The Sum For Each Of The Attributes
        int sumMpg = 0;
        int sumDisplacement = 0;
        int sumHorsePower = 0;
        int sumWeight = 0;
        int sumAcceleration = 0;

        for(int i =0; i<mpg.size(); i++){
            sumMpg += mpg.get(i);
            sumDisplacement += displacement.get(i);
            sumHorsePower += horsepower.get(i);
            sumWeight += weight.get(i);
            sumAcceleration += acceleration.get(i);
        }

        //Calculating The Average For Each Of The Attributes

        int averageMpg = sumMpg/mpg.size();
        int averageDisplacement = sumDisplacement/displacement.size();
        int averageHorsePower = sumHorsePower/horsepower.size();
        int averageWeight = sumWeight/weight.size();
        int averageAcceleration = sumAcceleration/acceleration.size();

        // Opening a new BufferedReader
        AssetManager assetManager2 = context.getAssets();
        InputStream inputStream2 = assetManager.open("dataset.txt");
        BufferedReader br2 = new BufferedReader(new InputStreamReader(inputStream2));

        List<Cars> cars = new ArrayList<>();
        while ((line=br2.readLine())!= null){

            String F[] = line.split(",");

            if(Integer.parseInt(F[0]) > averageMpg){
                F[0] = "Higher";
            }else{
                F[0] = "Lower";
            }
            if(Integer.parseInt(F[1]) > averageDisplacement){
                F[1] = "Higher";
            }else{
                F[1] = "Lower";
            }
            if(Integer.parseInt(F[2]) > averageHorsePower){
                F[2] = "Higher";
            }else{
                F[2] = "Lower";
            }
            if(Integer.parseInt(F[3]) > averageWeight){
                F[3] = "Higher";
            }else{
                F[3] = "Lower";
            }
            if(Integer.parseInt(F[4]) > averageAcceleration){
                F[4] = "Higher";
            }else{
                F[4] = "Lower";
            }

            //Creating An Object With The Strings Constructor To Hold The Made Changes
            Cars C = new Cars(F[0], F[1], F[2], F[3], F[4], F[5]);
            cars.add(C);
        }

        //Attributes For The New Car Instance That Will Be Created
        String MpgForInstance;
        String DisplacementForInstance;
        String HorsePowerForInstance;
        String WeightForInstance;
        String AccelerationForInstance;

        if(CarInstance.Mpg > averageMpg){
            //Setting A new Mpg Attribute For The Instance That Takes A Higher Or Lower String
            MpgForInstance = "Higher";
        }else{
            MpgForInstance = "Lower";
        }
        if(CarInstance.Displacement > averageDisplacement){
            //Setting A new Displacement Attribute For The Instance That Takes A Higher Or Lower String
            DisplacementForInstance = "Higher";
        }else{
            DisplacementForInstance = "Lower";
        }
        if(CarInstance.HorsePower > averageHorsePower){
            //Setting A new HorsePower Attribute For The Instance That Takes A Higher Or Lower String
            HorsePowerForInstance = "Higher";
        }else{
            HorsePowerForInstance = "Lower";
        }
        if(CarInstance.Weight > averageWeight){
            //Setting A new Weight Attribute For The Instance That Takes A Higher Or Lower String
            WeightForInstance = "Higher";
        }else{
            WeightForInstance = "Lower";
        }
        if(CarInstance.Acceleration > averageAcceleration){
            //Setting A new Acceleration Attribute For The Instance That Takes A Higher Or Lower String
            AccelerationForInstance = "Higher";
        }else{
            AccelerationForInstance = "Lower";
        }

        //Creating the car Instance to predict the origin Of
        Cars newCarInstance = new Cars(MpgForInstance, DisplacementForInstance, HorsePowerForInstance, WeightForInstance, AccelerationForInstance);

        //Starting The Bayes Algorithm and calculating Probabilities

        //Calculating The Probability Of Each Origin
        double ProbabilityOfJapaneseBeingTheOrigin ;
        double ProbabilityOfEuropeanBeingTheOrigin ;
        double ProbabilityOfAmericanBeingTheOrigin ;

        //Variables That Keeps Track Of The Number Of Each Origin In The DataSet
        int SumOfJapaneseCars = 0;
        int SumOfEuropeanCars = 0;
        int SumOfAmericanCars = 0;

        for (Cars CAR : cars){
            if (CAR.Origin2.equals("japanese")){
                SumOfJapaneseCars +=1;
            } else if (CAR.Origin2.equals("european")) {
                SumOfEuropeanCars +=1;
            }else {
                SumOfAmericanCars +=1;
            }
        }

        //Probabilities For Each Origin Class (J,E,A)
        ProbabilityOfJapaneseBeingTheOrigin = (double) SumOfJapaneseCars /cars.size();
        ProbabilityOfEuropeanBeingTheOrigin = (double) SumOfEuropeanCars /cars.size();
        ProbabilityOfAmericanBeingTheOrigin = (double) SumOfAmericanCars /cars.size();

        //Calculating the Probability For the newCarInstance's Origin Being Either Japanese, European Or American

        double ProbabilityOf_TheNewCarInstance_BeingJapanese;
        double ProbabilityOf_TheNewCarInstance_BeingEuropean;
        double ProbabilityOf_TheNewCarInstance_BeingAmerican;

        //Keeping Track Of How Many Properties (Mpg, Displacement,ctt) Which Origin Is Japanese That
        //The DataSet has, and that matches the New Car Instance's Properties (Yaa laha min jomlatin mot3iba hh)

        //How Many Are Japanese
        int SumOfMpgThatMatchesNewCarMpgAndIsJapan = 0;
        int SumOfDisplacementThatMatchesNewCarDisplacementAndIsJapan = 0;
        int SumOfHorsePowerThatMatchesNewCarHorsePowerAndIsJapan = 0;
        int SumOfWeightThatMatchesNewCarWeightAndIsJapan = 0;
        int SumOfAccelerationThatMatchesNewCarAccelerationAndIsJapan = 0;

        //How Many Are European
        int SumOfMpgThatMatchesNewCarMpgAndIsEurope = 0;
        int SumOfDisplacementThatMatchesNewCarDisplacementAndIsEurope = 0;
        int SumOfHorsePowerThatMatchesNewCarHorsePowerAndIsEurope = 0;
        int SumOfWeightThatMatchesNewCarWeightAndIsEurope = 0;
        int SumOfAccelerationThatMatchesNewCarAccelerationAndIsEurope = 0;

        //How Many Are American
        int SumOfMpgThatMatchesNewCarMpgAndIsAmerican = 0;
        int SumOfDisplacementThatMatchesNewCarDisplacementAndIsAmerican = 0;
        int SumOfHorsePowerThatMatchesNewCarHorsePowerAndIsAmerican = 0;
        int SumOfWeightThatMatchesNewCarWeightAndIsAmerican = 0;
        int SumOfAccelerationThatMatchesNewCarAccelerationAndIsAmerican = 0;

        for (int i =0; i<cars.size(); i++){

            if(cars.get(i).Mpg2.equals(newCarInstance.Mpg2) && cars.get(i).Origin2.equals("japanese")){
                SumOfMpgThatMatchesNewCarMpgAndIsJapan += 1;
            } else if (cars.get(i).Mpg2.equals(newCarInstance.Mpg2) && cars.get(i).Origin2.equals("european")) {
                SumOfMpgThatMatchesNewCarMpgAndIsEurope +=1;
            } else if (cars.get(i).Mpg2.equals(newCarInstance.Mpg2) && cars.get(i).Origin2.equals("american")) {
                SumOfMpgThatMatchesNewCarMpgAndIsAmerican +=1;
            }

            if (cars.get(i).Displacement2.equals(newCarInstance.Displacement2) && cars.get(i).Origin2.equals("japanese")){
                SumOfDisplacementThatMatchesNewCarDisplacementAndIsJapan +=1;
            }else if (cars.get(i).Displacement2.equals(newCarInstance.Displacement2) && cars.get(i).Origin2.equals("european")) {
                SumOfDisplacementThatMatchesNewCarDisplacementAndIsEurope +=1;
            } else if (cars.get(i).Displacement2.equals(newCarInstance.Displacement2) && cars.get(i).Origin2.equals("american")) {
                SumOfDisplacementThatMatchesNewCarDisplacementAndIsAmerican +=1;
            }

            if (cars.get(i).HorsePower2.equals(newCarInstance.HorsePower2) && cars.get(i).Origin2.equals("japanese")){
                SumOfHorsePowerThatMatchesNewCarHorsePowerAndIsJapan +=1;
            }else if (cars.get(i).HorsePower2.equals(newCarInstance.HorsePower2) && cars.get(i).Origin2.equals("european")) {
                SumOfHorsePowerThatMatchesNewCarHorsePowerAndIsEurope +=1;
            } else if (cars.get(i).HorsePower2.equals(newCarInstance.HorsePower2) && cars.get(i).Origin2.equals("american")) {
                SumOfHorsePowerThatMatchesNewCarHorsePowerAndIsAmerican +=1;
            }

            if (cars.get(i).Weight2.equals(newCarInstance.Weight2) && cars.get(i).Origin2.equals("japanese")){
                SumOfWeightThatMatchesNewCarWeightAndIsJapan +=1;
            }else if (cars.get(i).Weight2.equals(newCarInstance.Weight2) && cars.get(i).Origin2.equals("european")) {
                SumOfWeightThatMatchesNewCarWeightAndIsEurope +=1;
            } else if (cars.get(i).Weight2.equals(newCarInstance.Weight2) && cars.get(i).Origin2.equals("american")) {
                SumOfWeightThatMatchesNewCarWeightAndIsAmerican +=1;
            }

            if (cars.get(i).Acceleration2.equals(newCarInstance.Acceleration2) && cars.get(i).Origin2.equals("japanese")){
                SumOfAccelerationThatMatchesNewCarAccelerationAndIsJapan +=1;
            }else if (cars.get(i).Acceleration2.equals(newCarInstance.Acceleration2) && cars.get(i).Origin2.equals("european")) {
                SumOfAccelerationThatMatchesNewCarAccelerationAndIsEurope +=1;
            } else if (cars.get(i).Acceleration2.equals(newCarInstance.Acceleration2) && cars.get(i).Origin2.equals("american")) {
                SumOfAccelerationThatMatchesNewCarAccelerationAndIsAmerican +=1;
            }
        }

        //Final Result For Each Origin Probability

        ProbabilityOf_TheNewCarInstance_BeingJapanese =
                (double) SumOfMpgThatMatchesNewCarMpgAndIsJapan /SumOfJapaneseCars*
                        SumOfDisplacementThatMatchesNewCarDisplacementAndIsJapan/SumOfJapaneseCars*
                        SumOfHorsePowerThatMatchesNewCarHorsePowerAndIsJapan/SumOfJapaneseCars*
                        SumOfWeightThatMatchesNewCarWeightAndIsJapan/SumOfJapaneseCars*
                        SumOfAccelerationThatMatchesNewCarAccelerationAndIsJapan/SumOfJapaneseCars*
                        ProbabilityOfJapaneseBeingTheOrigin;

        ProbabilityOf_TheNewCarInstance_BeingEuropean =
                (double) SumOfMpgThatMatchesNewCarMpgAndIsEurope /SumOfEuropeanCars*
                        SumOfDisplacementThatMatchesNewCarDisplacementAndIsEurope/SumOfEuropeanCars*
                        SumOfHorsePowerThatMatchesNewCarHorsePowerAndIsEurope/SumOfEuropeanCars*
                        SumOfWeightThatMatchesNewCarWeightAndIsEurope/SumOfEuropeanCars*
                        SumOfAccelerationThatMatchesNewCarAccelerationAndIsEurope/SumOfEuropeanCars*
                        ProbabilityOfEuropeanBeingTheOrigin;

        ProbabilityOf_TheNewCarInstance_BeingAmerican =
                (double) SumOfMpgThatMatchesNewCarMpgAndIsAmerican /SumOfAmericanCars*
                        SumOfDisplacementThatMatchesNewCarDisplacementAndIsAmerican/SumOfAmericanCars*
                        SumOfHorsePowerThatMatchesNewCarHorsePowerAndIsAmerican/SumOfAmericanCars*
                        SumOfWeightThatMatchesNewCarWeightAndIsAmerican/SumOfAmericanCars*
                        SumOfAccelerationThatMatchesNewCarAccelerationAndIsAmerican/SumOfAmericanCars*
                        ProbabilityOfAmericanBeingTheOrigin;

        //I am Going To Use This Array To Fill It With The Results , Sort It And Return The Biggest Value
        double[] arrayOfFinalResults = new double[3];
        arrayOfFinalResults[0] = ProbabilityOf_TheNewCarInstance_BeingJapanese;
        arrayOfFinalResults[1] = ProbabilityOf_TheNewCarInstance_BeingEuropean;
        arrayOfFinalResults[2] = ProbabilityOf_TheNewCarInstance_BeingAmerican;
        Arrays.sort(arrayOfFinalResults);

        return arrayOfFinalResults[2] == ProbabilityOf_TheNewCarInstance_BeingJapanese ?
                "Japanese" :
                (arrayOfFinalResults[2] == ProbabilityOf_TheNewCarInstance_BeingEuropean ?
                        "European" : "American");

    }
}
