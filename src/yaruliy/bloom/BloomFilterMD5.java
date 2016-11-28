package yaruliy.bloom;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;
import java.util.Collection;

public class BloomFilterMD5<E> implements Serializable{
    private BitSet bitset;
    private int bitSetSize;
    private int expectedNumberOfFilterElements;
    private int k;
    static final Charset charset = Charset.forName("UTF-8");
    static final String hashName = "MD5";
    static final MessageDigest digestFunction;

    static {
        MessageDigest tmp;
        try { tmp = java.security.MessageDigest.getInstance(hashName); }
        catch (NoSuchAlgorithmException e) { tmp = null; }
        digestFunction = tmp;
    }

    public BloomFilterMD5(double c, int n, int k) {
        this.expectedNumberOfFilterElements = n;
        this.k = k;
        this.bitSetSize = (int)Math.ceil(c * n);
        this.bitset = new BitSet(bitSetSize);
    }

    public BloomFilterMD5(double falsePositiveProbability, int expectedNumberOfElements) {
        this(Math.ceil(-(Math.log(falsePositiveProbability) / Math.log(2))) / Math.log(2),
                expectedNumberOfElements,
                (int)Math.ceil(-(Math.log(falsePositiveProbability) / Math.log(2))));
    }

    public static int[] createHashes(byte[] data, int hashes) {
        int[] result = new int[hashes];
        int k = 0;
        byte salt = 0;
        while (k < hashes) {
            byte[] digest;
            synchronized (digestFunction) {
                digestFunction.update(salt);
                salt++;
                digest = digestFunction.digest(data);
            }

            for (int i = 0; i < digest.length/4 && k < hashes; i++) {
                int h = 0;
                for (int j = (i*4); j < (i*4)+4; j++) {
                    h <<= 8;
                    h |= ((int) digest[j]) & 0xFF;
                }
                result[k] = h;
                k++;
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        if (obj instanceof BloomFilterMD5) {
            final BloomFilterMD5<E> other = (BloomFilterMD5<E>) obj;
            return this.expectedNumberOfFilterElements == other.expectedNumberOfFilterElements &&
                    this.k == other.k &&
                    this.bitSetSize == other.bitSetSize &&
                    !(this.bitset != other.bitset
                            && (this.bitset == null || !this.bitset.equals(other.bitset)));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + (this.bitset != null ? this.bitset.hashCode() : 0);
        hash = 61 * hash + this.expectedNumberOfFilterElements;
        hash = 61 * hash + this.bitSetSize;
        hash = 61 * hash + this.k;
        return hash;
    }

    public void add(E element) {
        this.add(element.toString().getBytes(charset));
    }

    public void add(byte[] bytes) {
        int[] hashes = createHashes(bytes, k);
        for (int hash : hashes)
            bitset.set(Math.abs(hash % bitSetSize), true);
    }

    public boolean contains(E element) {
        return contains(element.toString().getBytes(charset));
    }

    public boolean contains(byte[] bytes) {
        int[] hashes = createHashes(bytes, k);
        for (int hash : hashes) {
            if (!bitset.get(Math.abs(hash % bitSetSize))) {
                return false;
            }
        }
        return true;
    }

    public boolean containsAll(Collection<? extends E> c) {
        for (E element : c)
            if (!contains(element))
                return false;
        return true;
    }
}