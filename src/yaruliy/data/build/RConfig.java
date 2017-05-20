package yaruliy.data.build;
import java.util.ArrayList;
import java.util.HashMap;

public class RConfig{
    public int regionElementsCount = 0;
    public HashMap<String, Integer> joinKeyDistributionLaw = new HashMap<>();
    public HashMap<Integer, ArrayList<Integer>> objectSizeDistributionLaw = new HashMap<>();
    String[] names = {"Jonh", "Sam", "Dean", "Tom", "Piter", "Natan", "Jenna", "Sophia", "Jack", "Kelly", "Robert"};
    String alpfa = "_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public RConfig(){}
    public RConfig(HashMap<String, Integer> jkd, HashMap<Integer, ArrayList<Integer>> osdl, int rec){
        this.joinKeyDistributionLaw = jkd;
        this.objectSizeDistributionLaw = osdl;
        this.regionElementsCount = rec;
    }
}