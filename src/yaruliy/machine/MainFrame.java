package yaruliy.machine;
import yaruliy.bloom.BloomFilter;
import yaruliy.util.Logger;

public class MainFrame {
    public static void main(String a[]){
        /*IMDGObject object = new User(1, "name", "sername", 5);
        System.out.println("All Dependency:");
        System.out.println(object.getObjectDependency().getAllDependencyInString());*/
        BloomFilter bf = new BloomFilter(5000, 10);

        for (int i = 0; i < 100; i++){
            bf.add(i*100);
        }

        /*Logger.log("BEGIN: ", false);
        for (int i = 0; i < 150; i++){
            Logger.log("Query for " + i * 100 + ": " + bf.mightContain(i * 100));
        }*/

        int count = 0;
        for (int i = 100; i < 150; i++){
            if (bf.mightContain(i * 100)){
                count++;
            }
        }
        System.out.println("FALSE_POSITIVE count:" + count);
        //Logger.log("FALSE_POSITIVE count:" + count);
    }
}