package yaruliy.util;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import static yaruliy.util.Util.getProperty;

public final class Logger {
    private Logger(){}
    private static String source;
    static{
        String folder = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        String name = new SimpleDateFormat("HH-mm-ss").format(new Date());
        source = "logs/" + folder + "/" + name + ".log";
        if(!(new File("logs/" + folder + "/").exists())){ new File("logs/" + folder + "/").mkdir(); }
    }

    public static void log(String message, boolean append){
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(source, append), "utf-8"))) {
            writer.write(message + "\n");
        }
        catch (IOException e){ e.printStackTrace(); }
    }

    public static void log(String message){ log(message, true); }
    public static void clearLog() {
        try(Writer writer = new BufferedWriter(new FileWriter(source, false))){
            writer.write("");
            writer.close();
        }
        catch (IOException e) { e.printStackTrace(); }
    }

    public static void writeEnd(){
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(source, true), "utf-8"))) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss - dd.M.yyyy");
            sdf.setTimeZone(TimeZone.getTimeZone("Europe/Kiev"));
            writer.write("End of Testing. Time: " + sdf.format(new Date()));
        }
        catch (IOException e){ e.printStackTrace(); }
    }

    public static void logSystemInfo() {
        log("");
        log("\t\t---------System-Properties---------------");
        log("\t\t--\t\tReplication Count = " + getProperty("replicationCount"));
        log("\t\t--\t\tNode Count = " + getProperty("nodeCount"));
        log("\t\t--\t\tLandwidth = " + getProperty("landwidth"));
        log("\t\t--\t\tLatency = " + getProperty("latency"));
        log("\t\t--\t\t" + "R0: " + Util.getConfigScreens().get("Region0").regionElementsCount
                  + "; " + "R1: " + Util.getConfigScreens().get("Region1").regionElementsCount);
        log("\t\t--");
        log("\t\t--\t\tJoin Key Frequency:");
        log("\t\t--\t\t\tRegion0:");

        for (Map.Entry<String, Integer> entry : Util.getConfigScreens().get("Region0").joinKeyDistributionLaw.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            log("\t\t--\t\t\t\t" + key + ": " + value + "%");
        }

        log("\t\t--");
        log("\t\t--\t\t\tRegion1:");


        for (Map.Entry<String, Integer> entry : Util.getConfigScreens().get("Region1").joinKeyDistributionLaw.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            log("\t\t--\t\t\t\t" + key + ": " + value + "%");
        }

        log("\t\t--");
        log("\t\t--\t\tObject Size Distribution:");
        log("\t\t--\t\t\tRegion0:");

        for (Map.Entry<Integer, ArrayList<Integer>> entry : Util.getConfigScreens().get("Region0").objectSizeDistributionLaw.entrySet()) {
            Integer key = entry.getKey();
            ArrayList<Integer> value = entry.getValue();
            log("\t\t--\t\t\t\t" + key + "%: " + value);
        }

        log("\t\t--");
        log("\t\t--\t\t\tRegion1:");


        for (Map.Entry<Integer, ArrayList<Integer>> entry : Util.getConfigScreens().get("Region1").objectSizeDistributionLaw.entrySet()) {
            Integer key = entry.getKey();
            ArrayList<Integer> value = entry.getValue();
            log("\t\t--\t\t\t\t" + key + "%: " + value);
        }

        log("\t\t-----------------------------------------\n");
    }
}