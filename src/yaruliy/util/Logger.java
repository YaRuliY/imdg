package yaruliy.util;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import static yaruliy.util.Util.getRegionInfo;
import static yaruliy.util.Util.getProperty;

public final class Logger {
    private Logger(){}
    private static String source = "resources/main.log";

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
        log("---------System-Properties---------------");
        log("--\t\tReplication Count = " + getProperty("replicationCount") + "          --");
        log("--\t\tNode Count = " + getProperty("nodeCount") + "                 --");
        log("--\t\tLandwidth = 3000000            --");
        log("--\t\tLatency = 1000                 --");
        log("--\t\t" + "R0: " + getRegionInfo().get("Region0")
                + "; " + "R1: " + getRegionInfo().get("Region1")
                + ";                --");
        log("-----------------------------------------\n");
    }
}