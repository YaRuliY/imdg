package yaruliy.data.build;
import yaruliy.data.IMDGObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class OGenerator {
    private RConfig rConfig = new RConfig();
    private String[] preparedNames;
    private int[] preparedPercents;

    public OGenerator setElementsCountInRegion(int elementsCount){
        rConfig.regionElementsCount = elementsCount;
        return this;
    }

    public OGenerator setDistributionLawForJoinKey(String key, int count){
        rConfig.joinKeyDistributionLaw.put(key, count);
        return this;
    }

    public OGenerator setDistributionLawForObjectSize(int[] persentArray, int[] depCountArray){
        for (int i = 0; i < persentArray.length; i++) {
            rConfig.objectSizeDistributionLaw.put(persentArray[i], depCountArray[i]);
        }
        return this;
    }

    public ArrayList<IMDGObject> generateObjectArray(){
        ArrayList<IMDGObject> objects = new ArrayList<>(rConfig.regionElementsCount);
        Random random = new Random();

        Set<String> nameKeys = rConfig.joinKeyDistributionLaw.keySet();
        preparedNames = new String[rConfig.regionElementsCount];
        int firstIndex = 0;
        for (String nk : nameKeys){
            int elementsPart = rConfig.joinKeyDistributionLaw.get(nk) * rConfig.regionElementsCount / 100;
            for(int i = firstIndex; i < (elementsPart + firstIndex);i++) preparedNames[i] = nk;
            firstIndex = firstIndex + elementsPart;
        }
        //---------------------------------------------------------------
        Set<Integer> percentKey = rConfig.objectSizeDistributionLaw.keySet();
        preparedPercents = new int[rConfig.regionElementsCount];
        firstIndex = 0;
        for (int pKey: percentKey){
            int count = pKey * rConfig.regionElementsCount / 100;
            for (int i = firstIndex; i < (firstIndex+count); i++) preparedPercents[i] = rConfig.objectSizeDistributionLaw.get(pKey);
            firstIndex = firstIndex + count;
        }

        for (int i = 0; i < rConfig.regionElementsCount; i++){
            String serName = "";
            for (int j = 0; j < random.nextInt((6)) + 5; j++)
                serName = serName + rConfig.alpfa.charAt(random.nextInt((rConfig.alpfa.length() - 1)));

            String name = preparedNames[i];
            int dc = preparedPercents[i];
            if (name == null) name = rConfig.names[random.nextInt((rConfig.names.length))];
            if (dc == 0 ) dc = 1;
            //objects.add(new IMDGObject(name, serName, random.nextInt((rConfig.maxDependencySize)) + rConfig.minDependencySize));
            objects.add(new IMDGObject(name, serName, dc));
        }
        return objects;
    }

    public OGenerator clear(){
        rConfig.regionElementsCount = 10;
        rConfig.joinKeyDistributionLaw = new HashMap<>();
        rConfig.objectSizeDistributionLaw = new HashMap<>();
        return this;
    }

    class RConfig{
        public int regionElementsCount = 0;
        /*public int maxDependencySize = 7;
        public int minDependencySize = 3;*/
        public HashMap<String, Integer> joinKeyDistributionLaw = new HashMap<>();
        public HashMap<Integer, Integer> objectSizeDistributionLaw = new HashMap<>();
        String[] names = {"Jonh", "Sam", "Dean", "Tom", "Piter", "Natan", "Jenna", "Sophia", "Jack", "Kelly", "Robert"};
        String alpfa = "_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    }
}