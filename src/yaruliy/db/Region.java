package yaruliy.db;
import yaruliy.bloom.MurMurHash;
import yaruliy.data.IMDGObject;
import yaruliy.util.WHProperties;
import java.util.ArrayList;
import java.util.Random;

public class Region {
    private short nodeCount;
    private short replicationCount;
    private ArrayList<Node> nodes;

    public Region(){
        nodeCount = Short.parseShort(WHProperties.getProperty("nodeCount"));
        replicationCount = Short.parseShort(WHProperties.getProperty("replicationCount"));
        this.nodes = new ArrayList<>();
        for (int i = 0; i < nodeCount; i++){
            nodes.add(new Node());
        }
    }

    //когда мы кладем данные в кластер
    //регион высчит. хэш, определ. ноду и говорит ей - положы это в себя
    public void addObject(String key, IMDGObject object) {
        int hashCode = MurMurHash.hash32(key.getBytes(), key.length());
        int index = Math.abs(hashCode) % nodes.size();

        nodes.get(index).addObject(this.getClass().getName(), object);
/*        ArrayList<Integer> mas = new ArrayList<>();
        indexHash.put(key, mas);

        for (int i = 0; i < replicationCount; i++){
            int nodeIndex = getRandom(this.nodeCount - 1);
            System.out.println("Random Node Index in BookRegion: " + nodeIndex);
            if (!nodes.get(nodeIndex).contains(key)){
                nodes.get(nodeIndex).addObject(key, object);
            }
            else System.out.println(key + " is already in node");
            mas.add(nodeIndex);
        }
        indexHash.put(key, mas);*/
    }

    public IMDGObject getObject(String key) {
        int hashCode = MurMurHash.hash32(key.getBytes(), key.length());
        int index = Math.abs(hashCode) % nodes.size();
        return nodes.get(index).getObject(key);
    }

    private int getRandom(int size){
        int range = size + 1;
        return new Random().nextInt(range);
    }
}