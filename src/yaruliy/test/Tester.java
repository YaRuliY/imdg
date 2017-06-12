package yaruliy.test;
import yaruliy.algorithm.BloomJoin;
import yaruliy.algorithm.HashJoin;
import yaruliy.algorithm.TrackJoin;
import yaruliy.data.IMDGObject;
import yaruliy.data.build.ObjectGenerator;
import yaruliy.structure.Warehouse;
import yaruliy.util.Logger;
import yaruliy.util.Util;

import java.util.ArrayList;

public class Tester {
    static int iterationCount = Util.getProperty("iterationCount");
    static String region0 = "Region0";
    static String region1 = "Region1";

    public static void main(String args[]){
        Logger.clearLog();
        Warehouse warehouse = new Warehouse();
        ObjectGenerator generator = new ObjectGenerator();

        generator.setElementsCountInRegion(1000)
                /*.setGaussianDistribution()*/
                .setErlangDistribution()
                /*.setObjectDependencies(new int[]{100}, new int[]{1})*/
                .setJoinKeyFrequency("Jenna", 50)
                .setJoinKeyFrequency("Tom", 50);

        ArrayList<IMDGObject> first = generator.generateObjectArray(region0);

        generator.reInit();
        generator.setElementsCountInRegion(1000)
                /*.setGaussianDistribution()*/
                .setErlangDistribution()
                /*.setObjectDependencies(new int[]{100}, new int[]{30})*/
                .setJoinKeyFrequency("Jenna", 50)
                .setJoinKeyFrequency("Tom", 50);

        ArrayList<IMDGObject> second = generator.generateObjectArray(region1);

        warehouse.addCollection(first, region0);
        warehouse.addCollection(second, region1);

        warehouse.getRegionByName(region0).printRecords(true);
        warehouse.getRegionByName(region1).printRecords(false);

        Logger.logSystemInfo();
        /*warehouse.executeJOIN(region0, region1, new HashJoin(), "name");
        warehouse.executeJOIN(region0, region1, new BloomJoin(), "name");
        warehouse.executeJOIN(region0, region1, new TrackJoin(), "name");*/


        for (int i = 0; i < iterationCount; i++){
            warehouse.executeJOIN(region0, region1, new HashJoin(), "name");
        }

        Util.getStatistics().changeCurrentJoin("HashJoin");
        Util.getStatistics().writeCSV();
        Util.reInitStatistics();
        Util.clearNodes();
        warehouse.addCollection(first, region0);
        warehouse.addCollection(second, region1);

        for (int i = 0; i < iterationCount; i++){
            warehouse.executeJOIN(region0, region1, new BloomJoin(), "name");
        }

        Util.getStatistics().changeCurrentJoin("BloomJoin");
        Util.getStatistics().writeCSV();
        Util.reInitStatistics();
        Util.clearNodes();
        warehouse.addCollection(first, region0);
        warehouse.addCollection(second, region1);

        for (int i = 0; i < iterationCount; i++){
            warehouse.executeJOIN(region0, region1, new TrackJoin(), "name");
            Util.clearNodes();
            warehouse.addCollection(first, region0);
            warehouse.addCollection(second, region1);
        }
        Util.getStatistics().changeCurrentJoin("TrackJoin");
        Util.getStatistics().writeCSV();

        Logger.writeEnd();
    }
}