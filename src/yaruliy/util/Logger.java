package yaruliy.util;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Logger {
    private Logger(){}
    private static String source = "resources/main.log";

    public static void log(String string, boolean append){
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(source, append), "utf-8"))) {
            writer.write(string + "\n");
        }
        catch (IOException e){ e.printStackTrace(); }
    }

    public static void log(String string){ log(string, true); }
    public static void clearLog() {
        try (Writer writer = new BufferedWriter(new FileWriter(source, false))){
            writer.write("");
            writer.close();
        }
        catch (IOException e) { e.printStackTrace(); }
    }

    public static void writeEnd(){
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(source, true), "utf-8"))) {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss [a] - dd.M.yyyy");
            sdf.setTimeZone(TimeZone.getTimeZone("Europe/Kiev"));
            writer.write("########### " + sdf.format(new Date()) + " ###########");
        }
        catch (IOException e){ e.printStackTrace(); }
    }
}