package yaruliy.data.build;
import yaruliy.data.IMDGObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class OGenerator {
    private RConfig rConfig = new RConfig();

    public OGenerator setRegionElementsCount(int elementsCount){
        rConfig.regionElementsCount = elementsCount;
        return this;
    }

    public OGenerator setMinDependencySize(int minSize){
        rConfig.minDependencySize = minSize;
        return this;
    }

    public OGenerator setMaxDependencySize(int maxSize){
        rConfig.maxDependencySize = maxSize;
        return this;
    }

    public OGenerator addDistributionLawForJoinKey(String key, int count){
        rConfig.joinKeyDistributionLaw.put(key, count);
        return this;
    }

    public OGenerator addDistributionLawForObjectSize(int dependencyCount, int percent){
        rConfig.objectSizeDistributionLaw.put(dependencyCount, percent);
        return this;
    }

    public ArrayList<IMDGObject> generateObjectArray(){
        ArrayList<IMDGObject> objects = new ArrayList<>();
        Random r = new Random();

        objects.add(new IMDGObject("Jenna", "Medison", 8));
        objects.add(new IMDGObject("Sam", "Winchester", 7));

        for (int i = 0; i < rConfig.regionElementsCount-2; i++){
            String name = rConfig.names[r.nextInt((rConfig.names.length))];
            String serName = "";
            for (int j = 0; j < r.nextInt((6)) + 5; j++)
                serName = serName + rConfig.alpfa.charAt(r.nextInt((rConfig.alpfa.length() - 1)));
            objects.add(new IMDGObject(name, serName, r.nextInt((rConfig.maxDependencySize)) + rConfig.minDependencySize));
        }
        return objects;
    }

    class RConfig{
        public int regionElementsCount = 0;
        public int minDependencySize = 0;
        public int maxDependencySize = 5;
        public HashMap<String, Integer> joinKeyDistributionLaw = new HashMap<>();
        public HashMap<Integer, Integer> objectSizeDistributionLaw = new HashMap<>();
        String[] names = {"Jonh", "Sam", "Dean", "Tom", "Piter", "Natan", "Jenna", "Sophia", "Jack", "Kelly", "Robert"};
        String alpfa = "_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    }
}