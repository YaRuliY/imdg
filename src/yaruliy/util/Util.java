package yaruliy.util;
import yaruliy.bloom.BloomFilterMD5;
import yaruliy.data.IMDGObject;
import yaruliy.structure.Node;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public final class Util {
    private static final java.util.Properties properties = new java.util.Properties();
    private static final BloomFilterMD5<String> bloomFilter;
    private static final int bloomFilterElementCount = 50000;
    private static final ArrayList<Node> array;
    private static final HashMap<String, Integer> regionNameSize;

    public static int getProperty(String property) { return Integer.parseInt(properties.getProperty(property), 10); }
    public static BloomFilterMD5<String> getBloomFilter() { return bloomFilter; }
    public static ArrayList<Node> getNodes() { return array; }
    public static HashMap<String, Integer> getRegionInfo() { return regionNameSize; }
    public static void addRegionNameSize(String name, int size){ regionNameSize.put(name, size); }
    public static int joinSize = 0;

    static {
        try(InputStream input = new FileInputStream("resources/imdg.properties")) { properties.load(input); }
        catch (IOException ex) { ex.printStackTrace(); }
        bloomFilter = new BloomFilterMD5<>(0.001, bloomFilterElementCount);
        array = new ArrayList<>();
        regionNameSize  = new HashMap<>();
        for (int i = 0; i < Byte.parseByte(properties.getProperty("nodeCount")); i++){
            array.add(new Node(i));
        }
    }

    static public int calculateStringSize(String s){
        int size = 12 + (s.length()*2);
        int diff = 8 - (size % 8);
        size = size + diff + 24;
        return size;
    }

    static public String getValue(String field, IMDGObject object){
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

    static public void printNodesContent() {
        for (Node node: Util.getNodes()) {
            if (node.getPartitions().keySet().size() < 0) {
                System.out.println("Node[" + node.getNodeID() + "] is empty");
            }
            else {
                System.out.println("==============Node[" + node.getNodeID() + "]=BEGIN============");
                int count = 0;
                for (String regKey: node.getPartitions().keySet()){
                    node.getPartitions().get(regKey).printContent(regKey);
                    if (count < 1) System.out.println("\t\t ------------------");
                    count++;
                }
                System.out.println("==============Node[" + node.getNodeID() + "]=END==============\n");
            }
        }
    }

    /*static public int findNodeWithMaxElemCount(String firstRegion, String secondRegion){
        int firstSize = 0;
        int secondSize = 0;
        int nodeIndex = 0;
        for (Node node: Util.getNodes()) {
            if(node.getPartitions().containsKey(firstRegion)){
                if(node.getPartitions().get(firstRegion).getPartitionSize() > firstSize){
                    firstSize = node.getPartitions().get(firstRegion).getPartitionSize();
                    nodeIndex = node.getNodeID();
                }
                if(node.getPartitions().get(firstRegion).getPartitionSize() == firstSize){
                    if(node.getPartitions().get(secondRegion).getPartitionSize() > secondSize){
                        secondSize = node.getPartitions().get(secondRegion).getPartitionSize();
                        firstSize = node.getPartitions().get(firstRegion).getPartitionSize();
                        nodeIndex = node.getNodeID();
                    }
                }
            }
        }
        return nodeIndex;
    }*/

    /*
    static public void sendSignalToAll(int nodeIndex, String regionName){
        while (array.get(nodeIndex).getPartitions().get(regionName).getAllRecords().size() < 10){
            for (Node node: array) {
                for (IMDGObject object: node.getPartitions().get(regionName).getAllRecords()){
                    if(!array.get(nodeIndex).getPartitions().get(regionName).getAllRecords().contains(object)){
                        array.get(nodeIndex).addObject(regionName, object);
                        System.out.println("SENDED by a signal: " + object.getHashID());
                    }
                }
            }
        }
    }*/

    static public void transferDataToNode(int nodeSender, int nodeReceiver){
        for (String regionKey: array.get(nodeSender).getPartitions().keySet()) {
            for (IMDGObject object: array.get(nodeSender).getPartitions().get(regionKey).getAllRecords()) {
                if(!array.get(nodeReceiver).getPartitions().get(regionKey).getAllRecords().contains(object)){
                    array.get(nodeReceiver).addObject(regionKey, object);
                }
            }
        }
    }

    static public List<IMDGObject> getRegionDataFromNodes(String rName){
        List<IMDGObject> result = new ArrayList<>();
        Logger.log("Transfering Data from " + rName + " Region...");
        int size = 0;
        for (Node node: Util.getNodes())
            for (IMDGObject object: node.getPartitions().get(rName).getAllRecords())
                if(!(result.contains(object))){
                    result.add(object);
                    size = size + object.calculateSize();
                }
        Util.joinSize = Util.joinSize + size;
        Logger.log("\t-> " + rName + " Send: " + result.size() + " objects. Transfered Data Size: " + formatNum(size));
        return result;
    }

    static public void writeValuesIntoFilter(BloomFilterMD5<String> bloomFilter, String rName, String field){
        for (IMDGObject object: getRegionDataFromNodes(rName))
            bloomFilter.add(getValue(field, object));
    }

    static public ArrayList<IMDGObject> getRegionDataWithFilter(BloomFilterMD5<String> bloomFilter, String rName, String field){
        ArrayList<IMDGObject> result = new ArrayList<>();
        Logger.log("Transfering Data from " + rName + " Region...");
        int size = 0;
        for (Node node: Util.getNodes())
            for (IMDGObject object: node.getPartitions().get(rName).getAllRecords())
                if(bloomFilter.contains(getValue(field, object)) && (!(result.contains(object)))){
                    result.add(object);
                    size = size + object.calculateSize();
                }
        Util.joinSize = Util.joinSize + size;
        Logger.log("\t-> " + rName + " Send: " + result.size() + " objects. Transfered Data Size: " + formatNum(size));
        return result;
    }

    static public String formatNum(int num){
        if(num == 0) return "[0]";
        else return "[" + String.format("%,2d", num) + "]";
    }
    static public String formatNum(long num){ return formatNum((int)num); }
}