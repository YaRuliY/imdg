package yaruliy.util;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.io.IOException;

public class Logger {
    private Logger(){}

    public static void log(String string, boolean append){
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("main.log", append), "utf-8"))) {
            writer.write(string + "\n");
        }
        catch (IOException e){ e.printStackTrace(); }
    }

    public static void log(String string){
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("main.log", true), "utf-8"))) {
            writer.write(string + "\n");
        }
        catch (IOException e){ e.printStackTrace(); }
    }
}