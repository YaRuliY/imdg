package yaruliy.tests;
import yaruliy.bloom.BloomFilterMD5;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PerformanceTest {
    static int elementCount = 50000;
    static final Random r = new Random();

    public static void main(String a[]){
        List<String> existingElements = new ArrayList<>(elementCount);
        List<String> nonExistingElements = new ArrayList<>(elementCount);
        for (int i = 0; i < elementCount; i++) {
            byte[] b = new byte[200];
            r.nextBytes(b);
            existingElements.add(new String(b));
        }
        for (int i = 0; i < elementCount; i++) {
            byte[] b = new byte[200];
            r.nextBytes(b);
            nonExistingElements.add(new String(b));
        }

        BloomFilterMD5<String> bf = new BloomFilterMD5<>(0.001, elementCount);

        System.out.print("add(): ");
        for (int i = 0; i < elementCount; i++) {
            bf.add(existingElements.get(i));
        }

        int yes = 0;
        int no = 0;
        System.out.println("contains(), nonexisting: ");
        for (int i = 0; i < elementCount; i++) {
            if (bf.contains(nonExistingElements.get(i))) yes++;
            else no++;
        }
        System.out.println("in BF: "+yes);
        System.out.println("false posotive: "+no);
    }
}