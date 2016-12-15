package yaruliy.util.trackstaff;

public class TUtil {
    public static void sendMessage(int nodeReceiver, TMessage message) {
        TProccess tProccess = TProccess.getInstance();
        tProccess.writeIntoTable(message);
    }
}