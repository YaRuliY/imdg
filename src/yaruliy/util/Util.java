package yaruliy.util;
import yaruliy.bloom.BloomFilterMD5;
import yaruliy.data.IMDGObject;
import yaruliy.db.Node;
import yaruliy.trackstaff.proccess.ProccessManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Util {
    private static final java.util.Properties properties = new java.util.Properties();
    private static final BloomFilterMD5<String> bloomFilter;
    private static final int bloomFilterElementCount = 50000;
    private static final ArrayList<Node> array;

    public static byte getProperty(String property) { return Byte.parseByte(properties.getProperty(property)); }
    public static BloomFilterMD5<String> getBloomFilter() { return bloomFilter; }
    public static ArrayList<Node> getNodes() { return array; }

    static {
        try(InputStream input = new FileInputStream("resources/imdg.properties")) { properties.load(input); }
        catch (IOException ex) { ex.printStackTrace(); }
        bloomFilter = new BloomFilterMD5<>(0.001, bloomFilterElementCount);
        array = new ArrayList<>();
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

    static public String valueGetter(String field, IMDGObject object){
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

    /*static public void transferDataToNode(int nodeSender, int nodeReceiver){
        for (String regionKey: array.get(nodeSender).getPartitions().keySet()) {
            for (IMDGObject object: array.get(nodeSender).getPartitions().get(regionKey).getAllRecords()) {
                if(!array.get(nodeReceiver).getPartitions().get(regionKey).getAllRecords().contains(object)){
                    array.get(nodeReceiver).addObject(regionKey, object);
                    System.out.println("SENDED by a data transfer: " + object.getHashID());
                }
            }
        }
    }

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
}