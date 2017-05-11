package yaruliy.trackstaff.proccess;
import yaruliy.structure.Node;
import yaruliy.util.Logger;
import yaruliy.util.Util;
import yaruliy.trackstaff.TElement;
import yaruliy.trackstaff.TMessage;
import yaruliy.trackstaff.TTable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TProccess {
    private TTable table;
    private Set<Integer> nodesForJoin;
    private static volatile TProccess instance;
    public void writeIntoTable(TMessage message){ table.addInfoFromMessage(message); }
    private TProccess(){
        this.table = new TTable();
        this.nodesForJoin = new HashSet<>();
    }
    public static TProccess getInstance() {
        TProccess localInstance = instance;
        if (localInstance == null) {
            synchronized (TProccess.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new TProccess();
                }
            }
        }
        return localInstance;
    }
    public void printTable() {
        System.out.println("[-------------------------------------------------" + "Hash-Table-[" +
                this.table.getHash().size() + "]:" + "-------------------------------------------------");
        int max = 0;
        for(Map.Entry<String, HashMap<String, TElement>> entry : this.table.getHash().entrySet()) {
            String key = entry.getKey();
            if(this.table.getNodes(key, "Region0") != null)
                if(max < this.table.getNodes(key, "Region0").length)
                    max = this.table.getNodes(key, "Region0").length;
            if(this.table.getNodes(key, "Region1") != null)
                if (max < this.table.getNodes(key, "Region1").length)
                    max = this.table.getNodes(key, "Region1").length;
        }
        int t = (max*12) + max;
        System.out.print("| UnicKey ");
        System.out.printf("| %-"+t+"s| %-"+t+"s \n", "    Region0     ", "     Region1");
        System.out.println("|---------------------------------------------------------" +
                "-----------------------------------------------------------------");
        for(Map.Entry<String, HashMap<String, TElement>> entry : this.table.getHash().entrySet()) {
            String key = entry.getKey();
            HashMap<String, TElement> value = entry.getValue();
            System.out.printf("| %-8s| ", key);
            if(value.get("Region0") != null){
                System.out.printf("%-"+t+"s| ", value.get("Region0"));
            }
            else System.out.printf("%-"+t+"s| ", "[null] ");
            if(value.get("Region1") != null){
                System.out.printf("%-"+t+"s \n", value.get("Region1"));
            }
            else System.out.printf("%-"+t+"s \n", "[null]");
        }
        System.out.println("[--------------------------------------------------------------" +
                "--------------------------------------------------------------------");
    }

    public void phaseTwoFour(String leftRegion, String rightRegion) {
        Logger.log("Phase Two Starts:");
        for(Map.Entry<String, HashMap<String, TElement>> entry : this.table.getHash().entrySet()) {
            String key = entry.getKey();
            HashMap<String, TElement> value = entry.getValue();

            if(value.get(leftRegion) != null && value.get(rightRegion) != null){
                System.out.printf("\t\t%-8s: ", key);
                int leftTotal = value.get(leftRegion).getTotalCount();
                int rightTotal = value.get(rightRegion).getTotalCount();
                Logger.log("-------------------FOR-KEY-(" + key + ")--------------------");
                Logger.log("Route Definition Starts:");
                Logger.log("Total Count from Left Table (for key " + key + "): " + leftTotal);
                Logger.log("Total Count from Right Table (for key " + key + "): " + rightTotal + "\n");
                if(leftTotal < rightTotal){
                    System.out.println(leftRegion + " -> " + rightRegion);
                    Logger.log(leftRegion + " -> " + rightRegion);

                    int beforeMigrationCount = 0;
                    for (int i : value.get(leftRegion).getSizes())
                        beforeMigrationCount = beforeMigrationCount + i*value.get(rightRegion).getNodes().length;
                    System.out.println("transfer count from left to right is: " + beforeMigrationCount/1024);

                    int afterMigrationCount = value.get(rightRegion).calculateMigrationCost();
                    for (int i: value.get(leftRegion).getSizes())
                        afterMigrationCount = afterMigrationCount + i;
                    System.out.println("transfer count after migration is: " + afterMigrationCount/1024);

                    if(afterMigrationCount < beforeMigrationCount){
                        value.get(rightRegion).doMigration(rightRegion, key);
                    }

                    doTransfer(leftRegion, rightRegion, key, value);
                }
                else {
                    Logger.log(leftRegion + " <- " + rightRegion);
                    System.out.println(leftRegion + " <- " + rightRegion);

                    int beforeMigrationCount = 0;
                    for (int i : value.get(rightRegion).getSizes())
                        beforeMigrationCount = beforeMigrationCount + i*value.get(leftRegion).getNodes().length;
                    System.out.println("transfer count from right to left is: " + beforeMigrationCount/1024);

                    int afterMigrationCount = value.get(leftRegion).calculateMigrationCost();
                    for (int i: value.get(rightRegion).getSizes())
                        afterMigrationCount = afterMigrationCount + i;
                    System.out.println("transfer count after migration is: " + afterMigrationCount/1024);

                    if(afterMigrationCount < beforeMigrationCount){
                        value.get(leftRegion).doMigration(leftRegion, key);
                    }

                    doTransfer(rightRegion, leftRegion, key, value);
                }
            }
        }
        System.out.println();
    }

    private void doTransfer(String leftRegion, String rightRegion, String key, HashMap<String, TElement> value){
        final int[] transferCount = {0};
        Logger.log("Transfering: ");
        for (int leftRegionIndex: value.get(leftRegion).getNodes()) {
            Node nodeForLeft = Util.getNodes().get(leftRegionIndex);
            for (String regKey : nodeForLeft.getPartitions().keySet()) {
                nodeForLeft.getPartitions().get(regKey).getAllRecords()
                        .stream()
                        .filter(object -> object.getName().equals(key))
                        .forEachOrdered(object -> {
                    for (int rightRegionIndex: value.get(rightRegion).getNodes()) {
                        if(!(Util.getNodes().get(rightRegionIndex).containsObject(object.getRegion(), object))){
                            Util.getNodes().get(rightRegionIndex).addObject(object.getRegion(), object);
                            Logger.log("\t[" + object.getHashID() + "](" + object.getName() + ") "
                                    + "from N[" + leftRegionIndex + "]"
                                    + "to N[" + rightRegionIndex + "] ");
                            transferCount[0]++;
                        }
                    }
                });
            }
        }
        Logger.log("Transfer Count: " + transferCount[0]);
        Logger.log("");
    }

    public Set<Integer> getNodesForJoin(){ return this.nodesForJoin; }

    public void writeNodes(int[] mas) {
        for (int m: mas){
            this.nodesForJoin.add(m);
        }
    }
}