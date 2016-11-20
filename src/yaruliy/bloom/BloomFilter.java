package yaruliy.bloom;
import java.util.BitSet;
import java.util.Random;

public class BloomFilter {
    private final BitSet bs;
    private final int[] hashSeeds;
    private final int capacity;

    public BloomFilter(int capacity, int hashFunctions) {
        bs = new BitSet(capacity);
        Random r = new Random(System.currentTimeMillis());
        hashSeeds = new int[hashFunctions];
        for (int i = 0; i < hashFunctions; ++i)
            hashSeeds[i] = r.nextInt();
        this.capacity = capacity;
    }

    public void add(int value) {
        byte [] b = new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte) value
        };

        for (int hashSeed : hashSeeds) {
            int h = MurMurHash.hash32(b, 4, hashSeed);
            bs.set(Math.abs(h) % capacity, true);
        }
    }

    public boolean mightContain(int value) {
        byte[] b = new byte[]{
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte) value
        };

        for (int hashSeed : hashSeeds) {
            int h = MurMurHash.hash32(b, 4, hashSeed);
            return bs.get(Math.abs(h) % capacity);
        }
        return false;
    }
}