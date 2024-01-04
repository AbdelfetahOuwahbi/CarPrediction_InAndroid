package com.example.carproject.Handlers;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DecisionTreeHandler {

    private final Context context;

    public DecisionTreeHandler(Context context) {
        this.context = context;
    }


    public String DTWorkInBackground(Cars carInstance) throws IOException {

        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("dataset.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        String line = "";
        List<Cars> cars = new ArrayList<>();

        //Attributes To be processed
        List<Integer> mpg = new ArrayList<>();
        List<Integer> displacement = new ArrayList<>();
        List<Integer> horsepower = new ArrayList<>();
        List<Integer> weight = new ArrayList<>();
        List<Integer> acceleration = new ArrayList<>();

        while ((line = br.readLine()) != null) {

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
        for (int i = 0; i < mpg.size(); i++) {
            sumMpg += mpg.get(i);
            sumDisplacement += displacement.get(i);
            sumHorsePower += horsepower.get(i);
            sumWeight += weight.get(i);
            sumAcceleration += acceleration.get(i);
        }

        //Calculating The Average For Each Of The Attributes

        int averageMpg = sumMpg / mpg.size();
        int averageDisplacement = sumDisplacement / displacement.size();
        int averageHorsePower = sumHorsePower / horsepower.size();
        int averageWeight = sumWeight / weight.size();
        int averageAcceleration = sumAcceleration / acceleration.size();

        AssetManager assetManager1 = context.getAssets();
        InputStream inputStream1 = assetManager1.open("dataset.txt");
        BufferedReader br2 = new BufferedReader(new InputStreamReader(inputStream1));

        while ((line = br2.readLine()) != null) {

            String F[] = line.split(",");

            if (Integer.parseInt(F[0]) > averageMpg) {
                F[0] = "Higher";
            } else {
                F[0] = "Lower";
            }
            if (Integer.parseInt(F[1]) > averageDisplacement) {
                F[1] = "Higher";
            } else {
                F[1] = "Lower";
            }
            if (Integer.parseInt(F[2]) > averageHorsePower) {
                F[2] = "Higher";
            } else {
                F[2] = "Lower";
            }
            if (Integer.parseInt(F[3]) > averageWeight) {
                F[3] = "Higher";
            } else {
                F[3] = "Lower";
            }
            if (Integer.parseInt(F[4]) > averageAcceleration) {
                F[4] = "Higher";
            } else {
                F[4] = "Lower";
            }

            //Creating An Object With The Strings Constructor To Hold The Made Changes
            Cars C = new Cars(F[0], F[1], F[2], F[3], F[4], F[5]);
            cars.add(C);
        }

        //The Instance Of Car that Comes As A Parameter
        Cars CarInstance = new Cars(10, 20, 90, 2000, 20);

        //Attributes For The New Car Instance That Will Be Created
        String MpgForInstance;
        String DisplacementForInstance;
        String HorsePowerForInstance;
        String WeightForInstance;
        String AccelerationForInstance;

        if (CarInstance.Mpg > averageMpg) {
            //Setting A new Mpg Attribute For The Instance That Takes A Higher Or Lower String
            MpgForInstance = "Higher";
        } else {
            MpgForInstance = "Lower";
        }
        if (CarInstance.Displacement > averageDisplacement) {
            //Setting A new Displacement Attribute For The Instance That Takes A Higher Or Lower String
            DisplacementForInstance = "Higher";
        } else {
            DisplacementForInstance = "Lower";
        }
        if (CarInstance.HorsePower > averageHorsePower) {
            //Setting A new HorsePower Attribute For The Instance That Takes A Higher Or Lower String
            HorsePowerForInstance = "Higher";
        } else {
            HorsePowerForInstance = "Lower";
        }
        if (CarInstance.Weight > averageWeight) {
            //Setting A new Weight Attribute For The Instance That Takes A Higher Or Lower String
            WeightForInstance = "Higher";
        } else {
            WeightForInstance = "Lower";
        }
        if (CarInstance.Acceleration > averageAcceleration) {
            //Setting A new Acceleration Attribute For The Instance That Takes A Higher Or Lower String
            AccelerationForInstance = "Higher";
        } else {
            AccelerationForInstance = "Lower";
        }

        //Creating the car Instance to predict the origin Of
        Cars newCarInstance = new Cars(MpgForInstance, DisplacementForInstance, HorsePowerForInstance, WeightForInstance, AccelerationForInstance);

        //Calculating The Number Of Higher Lower Variables in Each Attribute

        double MpgHigher = 0;
        double MpgLower = 0;

        double DisplacementHigher = 0;
        double DisplacementLower = 0;

        double HorsePowerHigher = 0;
        double HorsePowerLower = 0;

        double WeightHigher = 0;
        double WeightLower = 0;

        double AccelerationHigher = 0;
        double AccelerationLower = 0;

        for (Cars car : cars) {
            if (car.Mpg2.equals("Higher")) {
                MpgHigher += 1;
            } else {
                MpgLower += 1;
            }
            if (car.Displacement2.equals("Higher")) {
                DisplacementHigher += 1;
            } else {
                DisplacementLower += 1;
            }
            if (car.HorsePower2.equals("Higher")) {
                HorsePowerHigher += 1;
            } else {
                HorsePowerLower += 1;
            }
            if (car.Weight2.equals("Higher")) {
                WeightHigher += 1;
            } else {
                WeightLower += 1;
            }
            if (car.Acceleration2.equals("Higher")) {
                AccelerationHigher += 1;
            } else {
                AccelerationLower += 1;
            }
        }

        //The Total Number Of Instances That Exist On The Data Set (Will Be Used To Calculate The Info H(Attribute))
        double TotalDS = cars.size();

        //Information For Each Of The Attributes (I guess I still Need The number of JHigher, EHigher, AHigher...)
        double infoMpg;
        double infoDisplacement;
        double infoHorsePower;
        double infoWeight;
        double infoAcceleration;

        //How Many Higher is Japanese, European And American, And So On For The Lower Variable As Well

        double HigherJapanese = 0;
        double HigherEuropean = 0;
        double HigherAmerican = 0;

        double LowerJapanese = 0;
        double LowerEuropean = 0;
        double LowerAmerican = 0;

        //Calculate Info For The Mpg
        infoMpg = DecisionTreeHandler.calculateInfo(cars, "Mpg", HigherJapanese, HigherEuropean,
                HigherAmerican, LowerJapanese, LowerEuropean, LowerAmerican,
                TotalDS, MpgHigher, MpgLower);

        //Calculate Info For The Displacement
        infoDisplacement = DecisionTreeHandler.calculateInfo(cars, "Displacement", HigherJapanese, HigherEuropean,
                HigherAmerican, LowerJapanese, LowerEuropean, LowerAmerican,
                TotalDS, DisplacementHigher, DisplacementLower);

        //Calculate Info For The Horse Power
        infoHorsePower = DecisionTreeHandler.calculateInfo(cars, "Horse Power", HigherJapanese, HigherEuropean,
                HigherAmerican, LowerJapanese, LowerEuropean, LowerAmerican,
                TotalDS, HorsePowerHigher, HorsePowerLower);

        //Calculate Info For The Weight
        infoWeight = DecisionTreeHandler.calculateInfo(cars, "Weight", HigherJapanese, HigherEuropean,
                HigherAmerican, LowerJapanese, LowerEuropean, LowerAmerican,
                TotalDS, WeightHigher, WeightLower);

        //Calculate Info For The Acceleration
        infoAcceleration = DecisionTreeHandler.calculateInfo(cars, "Acceleration", HigherJapanese, HigherEuropean,
                HigherAmerican, LowerJapanese, LowerEuropean, LowerAmerican,
                TotalDS, AccelerationHigher, AccelerationLower);

        //Creating An Array That Holds All The Informations And Sorting it To Get The Root
        double[] arrayOfInformations = new double[5];
        arrayOfInformations[0] = infoMpg;
        arrayOfInformations[1] = infoDisplacement;
        arrayOfInformations[2] = infoHorsePower;
        arrayOfInformations[3] = infoWeight;
        arrayOfInformations[4] = infoAcceleration;

        //Sorting The Array To Get The Smallest Element (Root)
        Arrays.sort(arrayOfInformations);

        String rootName = (arrayOfInformations[0] == infoMpg ? "Mpg" :
                (arrayOfInformations[0] == infoDisplacement ? "Displacement" :
                        (arrayOfInformations[0] == infoHorsePower ? "Horse Power" :
                                (arrayOfInformations[0] == infoWeight ? "Weight" : "Acceleration"))));

        TreeNode root = new TreeNode(rootName);

        //Initializing The Variables Once Again
        MpgHigher = 0;
        MpgLower = 0;
        DisplacementHigher = 0;
        DisplacementLower = 0;
        HorsePowerHigher = 0;
        HorsePowerLower = 0;

        WeightHigher = 0;
        WeightLower = 0;
        AccelerationHigher = 0;
        AccelerationLower = 0;

        //How Many Higher is Japanese, European And American, And So On For The Lower Variable As Well
        HigherJapanese = 0;
        HigherEuropean = 0;
        HigherAmerican = 0;
        LowerJapanese = 0;
        LowerEuropean = 0;
        LowerAmerican = 0;

        List<String> RestOfAttributes = new ArrayList<>(Arrays.asList("Mpg", "Displacement", "Horse Power", "Weight", "Acceleration"));

        List<String> ResultForTheLeftOfRootNode = DecisionTreeHandler.HandleSide(cars, TotalDS, "HandleLeft", MpgHigher,
                MpgLower, DisplacementHigher, DisplacementLower, HorsePowerHigher,
                HorsePowerLower, WeightHigher, WeightLower, AccelerationHigher,
                AccelerationLower, HigherJapanese, HigherEuropean, HigherAmerican,
                LowerJapanese, LowerEuropean, LowerAmerican, RestOfAttributes, rootName
        );


        String PassFromRootToLeft = ResultForTheLeftOfRootNode.get(0);
        TreeNode LeftSecondNode = new TreeNode(ResultForTheLeftOfRootNode.get(1));

        //Deleting All Rows From The dataSet the contains FromRootToLeftLeaf In The Root Attribute
        Iterator<Cars> iterator = cars.iterator();
        while (iterator.hasNext()) {
            Cars car = iterator.next();
            String attibute = rootName.equals("Mpg") ? car.Mpg2 :
                    rootName.equals("Displacement") ? car.Displacement2 :
                            rootName.equals("Horse Power") ? car.HorsePower2 :
                                    rootName.equals("Weight") ? car.Weight2 : car.Acceleration2;
            if (attibute.equals(PassFromRootToLeft)) {
                iterator.remove();
            }
        }

        //Initializing The Variables Once Again
        MpgHigher = 0;
        MpgLower = 0;
        DisplacementHigher = 0;
        DisplacementLower = 0;
        HorsePowerHigher = 0;
        HorsePowerLower = 0;

        WeightHigher = 0;
        WeightLower = 0;
        AccelerationHigher = 0;
        AccelerationLower = 0;

        //How Many Higher is Japanese, European And American, And So On For The Lower Variable As Well
        HigherJapanese = 0;
        HigherEuropean = 0;
        HigherAmerican = 0;
        LowerJapanese = 0;
        LowerEuropean = 0;
        LowerAmerican = 0;

        TotalDS = cars.size();

        //Getting The rest Of Attributes To Be Processed
        List<String> NewRestOfAttributes = new ArrayList<>();

        for (int i = 0; i < RestOfAttributes.size(); i++) {
            if (!RestOfAttributes.get(i).equals(rootName)) {
                NewRestOfAttributes.add(RestOfAttributes.get(i));
            }
        }

        List<String> ResultForTheRightOfRootNode = DecisionTreeHandler.HandleSide(cars, TotalDS, "HandleRight", MpgHigher,
                MpgLower, DisplacementHigher, DisplacementLower, HorsePowerHigher,
                HorsePowerLower, WeightHigher, WeightLower, AccelerationHigher,
                AccelerationLower, HigherJapanese, HigherEuropean, HigherAmerican,
                LowerJapanese, LowerEuropean, LowerAmerican, NewRestOfAttributes, rootName
        );


        String PassFromRootToRight = ResultForTheRightOfRootNode.get(0);
        TreeNode RightSecondNode = new TreeNode(ResultForTheRightOfRootNode.get(1));

        //New Cars After We Got The Root And The Left & Right Nodes And their Passes
        List<Cars> SecondNodeRightCars = DecisionTreeHandler.getNewCarsList(new ArrayList<>(cars), "Higher", RightSecondNode.data);
        List<Cars> SecondNodeLeftCars = DecisionTreeHandler.getNewCarsList(cars, "Lower", RightSecondNode.data);

        //Getting The rest Of Attributes To Be Processed
        List<String> NewRestOfAttributes1 = new ArrayList<>();

        for (int i = 0; i < NewRestOfAttributes.size(); i++) {
            if (!NewRestOfAttributes.get(i).equals(RightSecondNode.data)) {
                NewRestOfAttributes1.add(NewRestOfAttributes.get(i));
            }
        }

        TotalDS = SecondNodeRightCars.size();
        //For The RightThirdNode
        List<String> ResultForTheRightOfSecondNode = DecisionTreeHandler.HandleSide(SecondNodeRightCars, TotalDS, "HandleRight", MpgHigher,
                MpgLower, DisplacementHigher, DisplacementLower, HorsePowerHigher,
                HorsePowerLower, WeightHigher, WeightLower, AccelerationHigher,
                AccelerationLower, HigherJapanese, HigherEuropean, HigherAmerican,
                LowerJapanese, LowerEuropean, LowerAmerican, NewRestOfAttributes1, RightSecondNode.data
        );

        String PassFromSecondNodeToRight = ResultForTheRightOfSecondNode.get(0);
        TreeNode RightThirdNode = new TreeNode(ResultForTheRightOfSecondNode.get(1));

        TotalDS = SecondNodeLeftCars.size();

        //For The RightThirdNode
        List<String> ResultForTheLeftOfSecondNode = DecisionTreeHandler.HandleSide(SecondNodeLeftCars, TotalDS, "HandleLeft", MpgHigher,
                MpgLower, DisplacementHigher, DisplacementLower, HorsePowerHigher,
                HorsePowerLower, WeightHigher, WeightLower, AccelerationHigher,
                AccelerationLower, HigherJapanese, HigherEuropean, HigherAmerican,
                LowerJapanese, LowerEuropean, LowerAmerican, NewRestOfAttributes1, RightSecondNode.data
        );

        String PassFromSecondNodeToLeft = ResultForTheLeftOfSecondNode.get(0);
        TreeNode LeftThirdNode = new TreeNode(ResultForTheLeftOfSecondNode.get(1));

        //Now We'll Work On The Right Part Of The Tree After The Second Node And Its Children Are Determined

        //New Cars
        List<Cars> RightThirdNodeRightCars = DecisionTreeHandler.getNewCarsList(new ArrayList<>(SecondNodeRightCars), "Higher", RightThirdNode.data);
        List<Cars> RightThirdNodeLeftCars = DecisionTreeHandler.getNewCarsList(SecondNodeRightCars, "Lower", RightThirdNode.data);

        //Getting The rest Of Attributes To Be Processed
        List<String> NewRestOfAttributes2 = new ArrayList<>();

        for (int i = 0; i < NewRestOfAttributes1.size(); i++) {
            if (!NewRestOfAttributes1.get(i).equals(LeftThirdNode.data)) {
                NewRestOfAttributes2.add(NewRestOfAttributes1.get(i));
            }
        }

        TotalDS = RightThirdNodeRightCars.size();
        //For The RightFourthNode
        List<String> ResultForTheRightOfRightThirdNode = DecisionTreeHandler.HandleSide(RightThirdNodeRightCars, TotalDS, "HandleRight", MpgHigher,
                MpgLower, DisplacementHigher, DisplacementLower, HorsePowerHigher,
                HorsePowerLower, WeightHigher, WeightLower, AccelerationHigher,
                AccelerationLower, HigherJapanese, HigherEuropean, HigherAmerican,
                LowerJapanese, LowerEuropean, LowerAmerican, NewRestOfAttributes2, RightThirdNode.data
        );

        String PassFromRightThirdNodeToRight = ResultForTheRightOfRightThirdNode.get(0);
        TreeNode RightFourthNode = new TreeNode(ResultForTheRightOfRightThirdNode.get(1));

        TotalDS = RightThirdNodeLeftCars.size();
        //For The LeftFourthNode
        List<String> ResultForTheLeftOfRightThirdNode = DecisionTreeHandler.HandleSide(RightThirdNodeLeftCars, TotalDS, "HandleLeft", MpgHigher,
                MpgLower, DisplacementHigher, DisplacementLower, HorsePowerHigher,
                HorsePowerLower, WeightHigher, WeightLower, AccelerationHigher,
                AccelerationLower, HigherJapanese, HigherEuropean, HigherAmerican,
                LowerJapanese, LowerEuropean, LowerAmerican, NewRestOfAttributes2, RightThirdNode.data
        );

        String PassFromRightThirdNodeToLeft = ResultForTheLeftOfRightThirdNode.get(0);
        TreeNode LeftFourthNode = new TreeNode(ResultForTheLeftOfRightThirdNode.get(1));

        //New Cars
        List<Cars> RightFourthNodeRightCars = DecisionTreeHandler.getNewCarsList(new ArrayList<>(RightThirdNodeRightCars), "Higher", RightFourthNode.data);
        List<Cars> RightFourthNodeLeftCars = DecisionTreeHandler.getNewCarsList(RightThirdNodeRightCars, "Lower", RightFourthNode.data);

        //Getting The Left Fifth Node

        //Getting The rest Of Attributes To Be Processed
        List<String> NewRestOfAttributes3 = new ArrayList<>();

        for (int i = 0; i < NewRestOfAttributes2.size(); i++) {
            if (!NewRestOfAttributes2.get(i).equals(RightFourthNode.data)) {
                NewRestOfAttributes3.add(NewRestOfAttributes2.get(i));
            }
        }

        TotalDS = RightFourthNodeLeftCars.size();

        //For The LeftFifthNode
        List<String> ResultForTheLeftOfRightFourthNode = DecisionTreeHandler.HandleSide(RightFourthNodeLeftCars, TotalDS, "HandleLeft", MpgHigher,
                MpgLower, DisplacementHigher, DisplacementLower, HorsePowerHigher,
                HorsePowerLower, WeightHigher, WeightLower, AccelerationHigher,
                AccelerationLower, HigherJapanese, HigherEuropean, HigherAmerican,
                LowerJapanese, LowerEuropean, LowerAmerican, NewRestOfAttributes3, RightFourthNode.data
        );

        String PassFromRightFourthNodeToLeft = ResultForTheLeftOfRightFourthNode.get(0);
        TreeNode LeftFivthNode = new TreeNode(ResultForTheLeftOfRightFourthNode.get(1));

        //Setting The Right Path Manually
        String PassFromRightFourthNodeToRight = "Higher";
        TreeNode RightFivthNode = new TreeNode("Japanese");

        //New Cars
        List<Cars> LeftThirdNodeRightCars = DecisionTreeHandler.getNewCarsList(new ArrayList<>(SecondNodeLeftCars), "Higher", LeftThirdNode.data);
        List<Cars> LeftThirdNodeLeftCars = DecisionTreeHandler.getNewCarsList(SecondNodeLeftCars, "Lower", LeftThirdNode.data);

        //Getting The rest Of Attributes To Be Processed
        List<String> NewRestOfAttributes4 = new ArrayList<>();

        for (int i = 0; i < NewRestOfAttributes1.size(); i++) {
            if (!NewRestOfAttributes1.get(i).equals(LeftThirdNode.data)) {
                NewRestOfAttributes4.add(NewRestOfAttributes1.get(i));
            }
        }


        TotalDS = LeftThirdNodeRightCars.size();
        //For The RightSixthNode
        List<String> ResultForTheRightOfLeftThirdNode = DecisionTreeHandler.HandleSide(LeftThirdNodeRightCars, TotalDS, "HandleRight", MpgHigher,
                MpgLower, DisplacementHigher, DisplacementLower, HorsePowerHigher,
                HorsePowerLower, WeightHigher, WeightLower, AccelerationHigher,
                AccelerationLower, HigherJapanese, HigherEuropean, HigherAmerican,
                LowerJapanese, LowerEuropean, LowerAmerican, NewRestOfAttributes3, LeftThirdNode.data
        );

        String PassFromLeftThirdNodeToRight = ResultForTheRightOfLeftThirdNode.get(0);
        TreeNode RightSixthNode = new TreeNode(ResultForTheRightOfLeftThirdNode.get(1));

        TotalDS = LeftThirdNodeLeftCars.size();

        //For The LeftSixthNode
        List<String> ResultForTheLeftOfLeftThirdNode = DecisionTreeHandler.HandleSide(LeftThirdNodeLeftCars, TotalDS, "HandleLeft", MpgHigher,
                MpgLower, DisplacementHigher, DisplacementLower, HorsePowerHigher,
                HorsePowerLower, WeightHigher, WeightLower, AccelerationHigher,
                AccelerationLower, HigherJapanese, HigherEuropean, HigherAmerican,
                LowerJapanese, LowerEuropean, LowerAmerican, NewRestOfAttributes3, LeftThirdNode.data
        );

        String PassFromLeftThirdNodeToLeft = ResultForTheLeftOfLeftThirdNode.get(0);
        TreeNode LeftSixthNode = new TreeNode(ResultForTheLeftOfLeftThirdNode.get(1));

        //Getting The rest Of Attributes To Be Processed
        List<String> NewRestOfAttributes5 = new ArrayList<>();

        for (int i = 0; i < NewRestOfAttributes3.size(); i++) {
            if (!NewRestOfAttributes3.get(i).equals(LeftSixthNode.data)) {
                NewRestOfAttributes5.add(NewRestOfAttributes3.get(i));
            }
        }

        //New Cars
        List<Cars> LeftSixthNodeRightCars = DecisionTreeHandler.getNewCarsList(new ArrayList<>(LeftThirdNodeLeftCars), "Higher", LeftSixthNode.data);
        List<Cars> LeftSixthNodeLeftCars = DecisionTreeHandler.getNewCarsList(LeftThirdNodeLeftCars, "Lower", LeftSixthNode.data);

        TotalDS = LeftSixthNodeRightCars.size();
        //For The RightSeventhNode
        List<String> ResultForTheRightOfLeftSeventhNode = DecisionTreeHandler.HandleSide(LeftSixthNodeRightCars, TotalDS, "HandleRight", MpgHigher,
                MpgLower, DisplacementHigher, DisplacementLower, HorsePowerHigher,
                HorsePowerLower, WeightHigher, WeightLower, AccelerationHigher,
                AccelerationLower, HigherJapanese, HigherEuropean, HigherAmerican,
                LowerJapanese, LowerEuropean, LowerAmerican, NewRestOfAttributes5, LeftSixthNode.data
        );

        String PassFromLeftSeventhNodeToLeft = ResultForTheRightOfLeftSeventhNode.get(0);
        TreeNode LeftSeventhNode = new TreeNode(ResultForTheRightOfLeftSeventhNode.get(1));

        TotalDS = LeftSixthNodeLeftCars.size();
        //For The LeftSeventhNode
        List<String> ResultForTheLeftOfLeftSeventhNode = DecisionTreeHandler.HandleSide(LeftSixthNodeLeftCars, TotalDS, "HandleLeft", MpgHigher,
                MpgLower, DisplacementHigher, DisplacementLower, HorsePowerHigher,
                HorsePowerLower, WeightHigher, WeightLower, AccelerationHigher,
                AccelerationLower, HigherJapanese, HigherEuropean, HigherAmerican,
                LowerJapanese, LowerEuropean, LowerAmerican, NewRestOfAttributes5, LeftSixthNode.data
        );

        String PassFromLeftSeventhNodeToRight = ResultForTheLeftOfLeftSeventhNode.get(0);
        TreeNode RightSeventhNode = new TreeNode(ResultForTheLeftOfLeftSeventhNode.get(1));

        //RETURNING THE FINAL PREDICTION

        String ToPredictRootInIntance = rootName.equals("Mpg") ? newCarInstance.Mpg2 :
                rootName.equals("Displacement") ? newCarInstance.Displacement2 :
                        rootName.equals("Horse Power") ? newCarInstance.HorsePower2 :
                                rootName.equals("Weight") ? newCarInstance.Weight2 : newCarInstance.Acceleration2;

        String ToPredictSecondNodeInIntance = RightSecondNode.data.equals("Mpg") ? newCarInstance.Mpg2 :
                RightSecondNode.data.equals("Displacement") ? newCarInstance.Displacement2 :
                        RightSecondNode.data.equals("Horse Power") ? newCarInstance.HorsePower2 :
                                RightSecondNode.data.equals("Weight") ? newCarInstance.Weight2 : newCarInstance.Acceleration2;

        String ToPredictLeftThirdNodeInIntance = LeftThirdNode.data.equals("Mpg") ? newCarInstance.Mpg2 :
                LeftThirdNode.data.equals("Displacement") ? newCarInstance.Displacement2 :
                        LeftThirdNode.data.equals("Horse Power") ? newCarInstance.HorsePower2 :
                                LeftThirdNode.data.equals("Weight") ? newCarInstance.Weight2 : newCarInstance.Acceleration2;

        String ToPredictRightThirdNodeInIntance = RightThirdNode.data.equals("Mpg") ? newCarInstance.Mpg2 :
                RightThirdNode.data.equals("Displacement") ? newCarInstance.Displacement2 :
                        RightThirdNode.data.equals("Horse Power") ? newCarInstance.HorsePower2 :
                                RightThirdNode.data.equals("Weight") ? newCarInstance.Weight2 : newCarInstance.Acceleration2;

        String ToPredictRightFourthNodeInIntance = RightFourthNode.data.equals("Mpg") ? newCarInstance.Mpg2 :
                RightFourthNode.data.equals("Displacement") ? newCarInstance.Displacement2 :
                        RightFourthNode.data.equals("Horse Power") ? newCarInstance.HorsePower2 :
                                RightFourthNode.data.equals("Weight") ? newCarInstance.Weight2 : newCarInstance.Acceleration2;

        String ToPredictLeftSixthNodeInIntance = LeftSeventhNode.data.equals("Mpg") ? newCarInstance.Mpg2 :
                LeftSeventhNode.data.equals("Displacement") ? newCarInstance.Displacement2 :
                        LeftSeventhNode.data.equals("Horse Power") ? newCarInstance.HorsePower2 :
                                LeftSeventhNode.data.equals("Weight") ? newCarInstance.Weight2 : newCarInstance.Acceleration2;

        if (ToPredictRootInIntance.equals(PassFromRootToLeft)) {

            return (LeftSecondNode.data);

        } else if (ToPredictRootInIntance.equals(PassFromRootToRight)) {
            if (ToPredictSecondNodeInIntance.equals(PassFromSecondNodeToLeft)) {
                if (ToPredictLeftThirdNodeInIntance.equals(PassFromLeftThirdNodeToRight)) {
                    return (RightSixthNode.data);
                } else {
                    if (ToPredictLeftSixthNodeInIntance.equals(PassFromLeftSeventhNodeToRight)) {
                        return (RightSeventhNode.data);
                    } else {
                        return (LeftSeventhNode.data);
                    }
                }
            } else {
                if (ToPredictRightThirdNodeInIntance.equals(PassFromRightThirdNodeToLeft)) {
                    return (LeftFourthNode.data);
                } else {
                    if (ToPredictRightFourthNodeInIntance.equals(PassFromRightFourthNodeToLeft)) {
                        return (LeftFivthNode.data);
                    } else {
                        return (RightFivthNode.data);
                    }
                }
            }
        }else {
            return null;
        }
    }

    public static double calculateLog(double x, double y){
        if (x == 0.0 || y == 0){
            return 0;
        }else{
            return Math.log(x/y) / Math.log(2);
        }
    }

    //a,b and c play HigherJapanese, HigherEuropean and HigherAmerican,
    //x,y and z play LowerJapanese, LowerEuropean and LowerAmerican,
    //AttHigher is the number of Higher In The Attribute
    //AttLower is the number of Lower in The Attribute
    public static double calculateInfo(List<Cars> cars, String attribute, double a, double b, double c, double x, double y, double z, double TotalDS, double AttHigher, double AttLower){

        //Loop The Get How Many Origin For Each Variable(Higher, Lower) For The HORSE POWER Attribute
        for (Cars car : cars){

            String attributeValue = attribute.equals("Mpg") ? car.Mpg2 :
                    attribute.equals("Displacement") ? car.Displacement2 :
                            attribute.equals("Horse Power") ? car.HorsePower2 :
                                    attribute.equals("Weight") ? car.Weight2 : car.Acceleration2;

            if (attributeValue.equals("Higher") && car.Origin2.equals("japanese")){
                a +=1; //3
            } else if (attributeValue.equals("Higher") && car.Origin2.equals("european")) {
                b +=1; //1
            } else if (attributeValue.equals("Higher") && car.Origin2.equals("american")) {
                c +=1; //1
            } else if (attributeValue.equals("Lower") && car.Origin2.equals("japanese")) {
                x +=1; //1
            } else if (attributeValue.equals("Lower") && car.Origin2.equals("european")) {
                y +=1; //4
            } else if (attributeValue.equals("Lower") && car.Origin2.equals("american")) {
                z +=1; //0
            }
        }

        return    ((AttHigher/TotalDS*
                (
                        -(a/AttHigher)* DecisionTreeHandler.calculateLog(a,AttHigher)-
                                (b/AttHigher)* DecisionTreeHandler.calculateLog(b,AttHigher) -
                                (c/AttHigher)* DecisionTreeHandler.calculateLog(c,AttHigher)
                )
        ) + (AttLower/TotalDS*
                (
                        -(x/AttLower)* DecisionTreeHandler.calculateLog(x,AttLower)-
                                (y/AttLower)* DecisionTreeHandler.calculateLog(y,AttLower) -
                                (z/AttLower)* DecisionTreeHandler.calculateLog(z,AttLower)
                )
        )) == -0.0 ? 0.2 : (AttHigher/TotalDS* //Returning a default value to avoid data Similarity Confusions
                (
                        -(a/AttHigher)* DecisionTreeHandler.calculateLog(a,AttHigher)-
                                (b/AttHigher)* DecisionTreeHandler.calculateLog(b,AttHigher) -
                                (c/AttHigher)* DecisionTreeHandler.calculateLog(c,AttHigher)
                )
        ) + (AttLower/TotalDS*
                (
                        -(x/AttLower)* DecisionTreeHandler.calculateLog(x,AttLower)-
                                (y/AttLower)* DecisionTreeHandler.calculateLog(y,AttLower) -
                                (z/AttLower)* DecisionTreeHandler.calculateLog(z,AttLower)
                )
        );
    }

    public static List<String> getResultAndGateWord (double HMHIJ, double HMHIE, double HMHIA, double HMLIJ, double HMLIE, double HMLIA, double AttributeHigher, double AttributeLower){
        List<String> NodeLeftPart = new ArrayList<>();
        if (HMHIJ == AttributeHigher && AttributeHigher!=0){
            NodeLeftPart.add("Higher");
            NodeLeftPart.add("Japanese");
        } else if (HMHIE == AttributeHigher && AttributeHigher!=0) {
            NodeLeftPart.add("Higher");
            NodeLeftPart.add("European");
        } else if (HMHIA == AttributeHigher && AttributeHigher!=0) {
            NodeLeftPart.add("Higher");
            NodeLeftPart.add("American");
        } else if (HMLIJ == AttributeLower && AttributeLower!=0) {
            NodeLeftPart.add("Lower");
            NodeLeftPart.add("Japanese");
        } else if (HMLIE == AttributeLower && AttributeLower!=0) {
            NodeLeftPart.add("Lower");
            NodeLeftPart.add("European");
        } else if (HMLIA == AttributeLower && AttributeLower!=0) {
            NodeLeftPart.add("Lower");
            NodeLeftPart.add("American");
        }else {
            NodeLeftPart.add("None");
            NodeLeftPart.add("None");
        }

        return NodeLeftPart;
    }

    //This Method Is Used To Send The New List Of Cars After Getting A node
    //The Values Could Be Higher Or Lower
    public static List<Cars> getNewCarsList(List<Cars> cars, String Value, String Node){
        Iterator<Cars> iterator = cars.iterator();
        if (Value.equals("Higher")){
            while (iterator.hasNext()) {
                Cars car = iterator.next();
                String attibute = Node.equals("Mpg") ? car.Mpg2 :
                        Node.equals("Displacement") ? car.Displacement2 :
                                Node.equals("Horse Power") ? car.HorsePower2 :
                                        Node.equals("Weight") ? car.Weight2 : car.Acceleration2;
                if (attibute.equals("Lower")){
                    iterator.remove();
                }
            }
        }else {
            while (iterator.hasNext()) {
                Cars car = iterator.next();
                String attibute = Node.equals("Mpg") ? car.Mpg2 :
                        Node.equals("Displacement") ? car.Displacement2 :
                                Node.equals("Horse Power") ? car.HorsePower2 :
                                        Node.equals("Weight") ? car.Weight2 : car.Acceleration2;
                if (attibute.equals("Higher")){
                    iterator.remove();
                }
            }
        }
        return cars;
    }

    //HANDLING EACH SIDE
    public static List<String> HandleSide(
            List<Cars> cars, double TotalDS, String SideToHandle, double MpgHigher,
            double MpgLower, double DisplacementHigher, double DisplacementLower, double HorsePowerHigher,
            double HorsePowerLower, double WeightHigher, double WeightLower, double AccelerationHigher,
            double AccelerationLower, double HigherJapanese, double HigherEuropean, double HigherAmerican,
            double LowerJapanese, double LowerEuropean, double LowerAmerican, List<String> RestOfAttributes,
            String Node
    ){

        double infoMpg = 0;
        double infoDisplacement = 0;
        double infoHorsePower = 0;
        double infoWeight = 0;
        double infoAcceleration = 0;

        double HowManyHigherIsJap = 0;
        double HowManyHigherIsEur = 0;
        double HowManyHigherIsAm = 0;
        double HowManyLowerIsJap = 0;
        double HowManyLowerIsEur = 0;
        double HowManyLowerIsAm = 0;


        //Checking Where either All Higher or All Lower Are Equal To The Same Origin
        for (Cars car : cars){
            //Checking Which Value (Higher or lower) have the same origin at every row for the root
            String attibute = Node.equals("Mpg") ? car.Mpg2 :
                    Node.equals("Displacement") ? car.Displacement2 :
                            Node.equals("Horse Power") ? car.HorsePower2 :
                                    Node.equals("Weight") ? car.Weight2 : car.Acceleration2;
            if (attibute.equals("Higher") && car.Origin2.equals("japanese")){
                HowManyHigherIsJap +=1;
            } else if (attibute.equals("Higher") && car.Origin2.equals("european")) {
                HowManyHigherIsEur +=1;
            } else if (attibute.equals("Higher") && car.Origin2.equals("american")) {
                HowManyHigherIsAm +=1;
            }
            if (attibute.equals("Lower") && car.Origin2.equals("japanese")){
                HowManyLowerIsJap +=1;
            } else if (attibute.equals("Lower") && car.Origin2.equals("european")) {
                HowManyLowerIsEur +=1;
            } else if (attibute.equals("Lower") && car.Origin2.equals("american")) {
                HowManyLowerIsAm +=1;
            }

        }

        for (Cars car : cars){
            if (car.Mpg2.equals("Higher")){
                MpgHigher +=1;
            }else {
                MpgLower +=1;
            }
            if (car.Displacement2.equals("Higher")){
                DisplacementHigher +=1;
            }else {
                DisplacementLower +=1;
            }
            if (car.HorsePower2.equals("Higher")){
                HorsePowerHigher +=1;
            }else {
                HorsePowerLower +=1;
            }
            if (car.Weight2.equals("Higher")){
                WeightHigher +=1;
            }else {
                WeightLower +=1;
            }
            if (car.Acceleration2.equals("Higher")){
                AccelerationHigher +=1;
            }else {
                AccelerationLower +=1;
            }
        }



        List<String> CheckDirectResult = new ArrayList<>();

        if (Node.equals("Mpg")){
            CheckDirectResult = getResultAndGateWord(
                    HowManyHigherIsJap, HowManyHigherIsEur, HowManyHigherIsAm,
                    HowManyLowerIsJap, HowManyLowerIsEur, HowManyLowerIsAm, MpgHigher,MpgLower
            );
        } else if (Node.equals("Displacement")) {
            CheckDirectResult = getResultAndGateWord(
                    HowManyHigherIsJap, HowManyHigherIsEur, HowManyHigherIsAm,
                    HowManyLowerIsJap, HowManyLowerIsEur, HowManyLowerIsAm, DisplacementHigher,DisplacementLower
            );
        } else if (Node.equals("Horse Power")) {
            CheckDirectResult = getResultAndGateWord(
                    HowManyHigherIsJap, HowManyHigherIsEur, HowManyHigherIsAm,
                    HowManyLowerIsJap, HowManyLowerIsEur, HowManyLowerIsAm, HorsePowerHigher,HorsePowerLower
            );
        } else if (Node.equals("Weight")) {
            CheckDirectResult = getResultAndGateWord(
                    HowManyHigherIsJap, HowManyHigherIsEur, HowManyHigherIsAm,
                    HowManyLowerIsJap, HowManyLowerIsEur, HowManyLowerIsAm, WeightHigher,WeightLower
            );
        }else if (Node.equals("Acceleration")){
            CheckDirectResult = getResultAndGateWord(
                    HowManyHigherIsJap, HowManyHigherIsEur, HowManyHigherIsAm,
                    HowManyLowerIsJap, HowManyLowerIsEur, HowManyLowerIsAm, AccelerationHigher,AccelerationLower
            );
        }



        if (CheckDirectResult.get(0).equals("None")){

            MpgHigher=0;
            MpgLower=0;
            DisplacementHigher=0;
            DisplacementLower=0;
            HorsePowerHigher=0;
            HorsePowerLower=0;
            WeightHigher=0;
            WeightLower=0;
            AccelerationHigher=0;
            AccelerationLower=0;


            for (Cars car : cars){
                if (car.Mpg2.equals("Higher")){
                    MpgHigher +=1;
                }else {
                    MpgLower +=1;
                }
                if (car.Displacement2.equals("Higher")){
                    DisplacementHigher +=1;
                }else {
                    DisplacementLower +=1;
                }
                if (car.HorsePower2.equals("Higher")){
                    HorsePowerHigher +=1;
                }else {
                    HorsePowerLower +=1;
                }
                if (car.Weight2.equals("Higher")){
                    WeightHigher +=1;
                }else {
                    WeightLower +=1;
                }
                if (car.Acceleration2.equals("Higher")){
                    AccelerationHigher +=1;
                }else {
                    AccelerationLower +=1;
                }
            }


            List<Double> WhichInfosAreCalculated = new ArrayList<>();
            for (String Att : RestOfAttributes){
                if (Att.equals("Mpg")){
                    infoMpg =  DecisionTreeHandler.calculateInfo(cars, "Mpg", HigherJapanese, HigherEuropean,
                            HigherAmerican, LowerJapanese, LowerEuropean, LowerAmerican,
                            TotalDS, MpgHigher, MpgLower);
                    WhichInfosAreCalculated.add(infoMpg);
                } else if (Att.equals("Displacement")) {
                    infoDisplacement =  DecisionTreeHandler.calculateInfo(cars, "Displacement", HigherJapanese, HigherEuropean,
                            HigherAmerican, LowerJapanese, LowerEuropean, LowerAmerican,
                            TotalDS, DisplacementHigher, DisplacementLower);
                    WhichInfosAreCalculated.add(infoDisplacement);
                } else if (Att.equals("Horse Power")) {
                    infoHorsePower =  DecisionTreeHandler.calculateInfo(cars, "Horse Power", HigherJapanese, HigherEuropean,
                            HigherAmerican, LowerJapanese, LowerEuropean, LowerAmerican,
                            TotalDS, HorsePowerHigher, HorsePowerLower);
                    WhichInfosAreCalculated.add(infoHorsePower);
                } else if (Att.equals("Weight")) {
                    infoWeight =  DecisionTreeHandler.calculateInfo(cars, "Weight", HigherJapanese, HigherEuropean,
                            HigherAmerican, LowerJapanese, LowerEuropean, LowerAmerican,
                            TotalDS, WeightHigher, WeightLower);
                    WhichInfosAreCalculated.add(infoWeight);
                } else if (Att.equals("Acceleration")) {
                    infoAcceleration =  DecisionTreeHandler.calculateInfo(cars, "Acceleration", HigherJapanese, HigherEuropean,
                            HigherAmerican, LowerJapanese, LowerEuropean, LowerAmerican,
                            TotalDS, AccelerationHigher, AccelerationLower);
                    WhichInfosAreCalculated.add(infoAcceleration);
                }

            }

            //Determining The Second Node (In This Very Case It Was MPG)
            TreeNode NextNode = new TreeNode((WhichInfosAreCalculated.get(0) == infoMpg ? "Mpg" :
                    (WhichInfosAreCalculated.get(0) == infoDisplacement ? "Displacement" :
                            (WhichInfosAreCalculated.get(0) == infoHorsePower ? "Horse Power" :
                                    (WhichInfosAreCalculated.get(0) == infoWeight ? "Weight" : "Acceleration")))));
            String Pass;
            if (SideToHandle.equals("HandleLeft")){
                Pass = "Lower";
            }else {
                Pass = "Higher";
            }
            List<String> ListToSend = new ArrayList<>();
            ListToSend.add(Pass);
            ListToSend.add(NextNode.data);
            return ListToSend;
        }else {
            return CheckDirectResult;
        }



    }
}
