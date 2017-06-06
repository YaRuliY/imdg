package yaruliy.test;
import yaruliy.util.CSV;
import yaruliy.util.Logger;
import java.util.ArrayList;

public class Statistics{
    private int size = 0;
    private String joinType;
    private ArrayList<Integer> transferCount;
    private ArrayList<Integer> transferVolume;
    private ArrayList<Long> executionTime;
    private ArrayList<Long> realTime;

    public Statistics(int size) {
        this.size = size;
        this.transferCount = new ArrayList<>(this.size);
        this.transferVolume = new ArrayList<>(this.size);
        this.executionTime = new ArrayList<>(this.size);
        this.realTime = new ArrayList<>(this.size);
    }

    public void addTransferCount(int tc){ this.transferCount.add(tc); }
    public void addTransferVolume(int tv){ this.transferVolume.add(tv); }
    public void addExecutionTime(long et){ this.executionTime.add(et); }
    public void addRealTime(long rt){ this.realTime.add(rt/1000); }

    public void printStatistics(){
        Logger.log("JOIN: ");
        Logger.log("transferCount: ");
        Logger.log(transferCount);
        Logger.log("transferVolume: ");
        Logger.log(transferVolume);
        Logger.log("executionTime: ");
        Logger.log(executionTime);
        Logger.log("realTime: ");
        Logger.log(realTime);
        Logger.log("");
    }

    public void writeCSV(){
        if (this.joinType == null) this.joinType = "SomehowJoin";
        CSV.writeCSVIntoFile("transferCount: ", this.joinType);
        CSV.writeCSVIntoFile(transferCount, this.joinType);
        CSV.writeCSVIntoFile("transferVolume: ", this.joinType);
        CSV.writeCSVIntoFile(transferVolume, this.joinType);
        CSV.writeCSVIntoFile("executionTime: ", this.joinType);
        CSV.writeCSVIntoFile(executionTime, this.joinType);
        CSV.writeCSVIntoFile("realTime: ", this.joinType);
        CSV.writeCSVIntoFile(realTime, this.joinType);
    }

    public void changeCurrentJoin(String jn){
        this.joinType = jn;
    }
}