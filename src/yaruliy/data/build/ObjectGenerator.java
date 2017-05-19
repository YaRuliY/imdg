package yaruliy.data.build;
import yaruliy.data.IMDGObject;
import yaruliy.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class ObjectGenerator {
    private RConfig rConfig = new RConfig();
    private Random random = new Random();
    private String[] preparedNames;
    private int[] preparedDepCount;

    public ObjectGenerator setElementsCountInRegion(int elementsCount){
        rConfig.regionElementsCount = elementsCount;
        return this;
    }

    public ObjectGenerator setJoinKeyFrequency(String key, int count){
        int summa = 0;
        for(int i: rConfig.joinKeyDistributionLaw.values()) summa = summa + i;

        if(summa < 100) rConfig.joinKeyDistributionLaw.put(key, count);
        else throw new IllegalArgumentException("Can not be more than 100 percent!");

        return this;
    }

    public ObjectGenerator setObjectDependencies(int[] persentArray, int[] depCountArray){
        if(persentArray.length != depCountArray.length)
            throw new IllegalArgumentException("Arrays sizes must match!");

        int summa = 0;
        for (int aPersentArray : persentArray) summa = summa + aPersentArray;
        if(summa > 100)
            throw new IllegalArgumentException("Can not be more than 100 percent!");

        for (int i = 0; i < persentArray.length; i++)
            if(rConfig.objectSizeDistributionLaw.containsKey(persentArray[i])){
                rConfig.objectSizeDistributionLaw.get(persentArray[i]).add(depCountArray[i]);
            }
            else {
                rConfig.objectSizeDistributionLaw.put(persentArray[i], new ArrayList<>());
                rConfig.objectSizeDistributionLaw.get(persentArray[i]).add(depCountArray[i]);
            }
        return this;
    }

    public ArrayList<IMDGObject> generateObjectArray(String rName){
        ArrayList<IMDGObject> objects = new ArrayList<>(rConfig.regionElementsCount);

        Set<String> nameKeys = rConfig.joinKeyDistributionLaw.keySet();
        preparedNames = new String[rConfig.regionElementsCount];
        int firstIndex = 0;
        for (String nk : nameKeys){
            int elementsPart = rConfig.joinKeyDistributionLaw.get(nk) * rConfig.regionElementsCount / 100;
            for(int i = firstIndex; i < (elementsPart + firstIndex); i++) preparedNames[i] = nk;
            firstIndex = firstIndex + elementsPart;
        }

        Set<Integer> percentKeys = rConfig.objectSizeDistributionLaw.keySet();
        preparedDepCount = new int[rConfig.regionElementsCount];
        firstIndex = 0;
        for (int pKey: percentKeys){
            int count = pKey * rConfig.regionElementsCount / 100;
            for (int i = firstIndex; i < (firstIndex+count); i++)
                preparedDepCount[i] = rConfig.objectSizeDistributionLaw.get(pKey).get(0);
            firstIndex = firstIndex + count;
        }
        for (int i = 0; i< preparedDepCount.length; i++) {
            if (preparedDepCount[i] == 0)
                for (int pKey : percentKeys) {
                    if (rConfig.objectSizeDistributionLaw.get(pKey).size() > 1) {
                        preparedDepCount[i] = rConfig.objectSizeDistributionLaw.get(pKey).get(1);
                        //System.out.println("i: " + i);
                    }
                }
        }

        for (int i = 0; i < rConfig.regionElementsCount; i++){
            StringBuilder secondName = new StringBuilder();
            for (int j = 0; j < random.nextInt((6)) + 5; j++)
                secondName.append(rConfig.alpfa.charAt(random.nextInt((rConfig.alpfa.length() - 1))));

            String name = preparedNames[i];
            int dependencyCount = preparedDepCount[i];
            if (name == null) name = rConfig.names[random.nextInt((rConfig.names.length))];
            if (dependencyCount == 0 ) dependencyCount = -(preparedDepCount[preparedDepCount.length-1]);
            objects.add(new IMDGObject(name, secondName.toString(), dependencyCount));
        }

        Util.addConfig(rName, this.rConfig);
        return objects;
    }

    public void reInit(){
        rConfig.regionElementsCount = 10;
        rConfig.joinKeyDistributionLaw = new HashMap<>();
        rConfig.objectSizeDistributionLaw = new HashMap<>();
    }
}