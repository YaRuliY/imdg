package yaruliy.bloom;
import java.util.BitSet;
import java.util.Random;

public class BloomFilter {
    private final BitSet bs;
    private final int [] hashSeeds;
    private final int capacity;

    public BloomFilter(int slots, int hashFunctions) {
        bs = new BitSet(slots);
        Random r = new Random(System.currentTimeMillis());
        hashSeeds = new int[hashFunctions];
        for (int i=0; i<hashFunctions; ++i) {
            hashSeeds[i] = r.nextInt();
        }
        capacity = slots;
    }

    public void add(int value) {
        byte [] b = new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
        for (int i=0; i<hashSeeds.length; ++i) {
            int h = MurMurHash.hash32(b, 4, hashSeeds[i]);
            bs.set(Math.abs(h)%capacity, true);
        }
    }

    public void clear() { bs.clear(); }

    public boolean mightContain(int value) {
        byte[] b = new byte[]{
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value};
        for (int i = 0; i < hashSeeds.length; ++i) {
            int h = MurMurHash.hash32(b, 4, hashSeeds[i]);

            if (!bs.get(Math.abs(h) % capacity)) {
                return false;


            }
            return true;
        }
        return false;
    }


    public static void main(String [] args) {
        BloomFilter bf = new BloomFilter(1000, 10);
        System.out.println("Query for 2000: " + bf.mightContain(2000));
        System.out.println("Adding 2000");
        bf.add(2000);
        System.out.println("Query for 2000: " + bf.mightContain(2000));
    }
}