package yaruliy.data.build;
import java.util.ArrayList;
import java.util.HashMap;

public class RConfig{
    public int regionElementsCount = 0;
    public HashMap<String, Integer> joinKeyDistributionLaw = new HashMap<>();
    public HashMap<Integer, ArrayList<Integer>> objectSizeDistributionLaw = new HashMap<>();
    String[] names = {"Jonh", "Sam", "Dean", "Tom", "Piter", "Natan", "Jenna", "Sophia", "Jack", "Kelly", "Robert"};
    String alpfa = "_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
}