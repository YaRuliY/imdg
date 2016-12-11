package yaruliy.util;
import yaruliy.bloom.BloomFilterMD5;
import yaruliy.data.IMDGObject;
import yaruliy.db.Node;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class WHUtils {
    private static final java.util.Properties properties;
    private static final BloomFilterMD5<String> bloomFilter;
    private static final int elementCount = 50000;
    private static final ArrayList<Node> array;

    static {
        properties = new java.util.Properties();
        try(InputStream input = new FileInputStream("resources/imdg.properties")) { properties.load(input); }
        catch (IOException ex) { ex.printStackTrace(); }
    }
    static {
        bloomFilter = new BloomFilterMD5<>(0.001, elementCount);
        array = new ArrayList<>();
        for (int i = 0; i < Byte.parseByte(properties.getProperty("nodeCount")); i++){
            array.add(new Node(i));
        }
    }

    public static int calculateStringSize(String s){
        int size = 12 + (s.length()*2);
        int diff = 8 - (size % 8);
        size = size + diff + 24;
        return size;
    }

    public static String valueGetter(String field, IMDGObject object){
        field = Character.toUpperCase(field.charAt(0)) + field.substring(1);
        String result = null;
        try { result = IMDGObject.class.getMethod("get"+field).invoke(object).toString(); }
        catch (NoSuchMethodException e) {
            System.out.println("No Such Field Exception!!!");
            System.exit(2);
        }
        catch (InvocationTargetException | IllegalAccessException e) { e.printStackTrace(); }
        return result;
    }

    public static byte getProperty(String property) { return Byte.parseByte(properties.getProperty(property)); }
    public static BloomFilterMD5<String> getBloomFilter() { return bloomFilter; }
    public static ArrayList<Node> getNodes() { return array; }
}