package yaruliy.util.trackstaff;
import yaruliy.util.Util;

public class TTransport {
    public static void sendMessage(int nodeReceiver, TMessage message) {
        TProccess tProccess = TProccess.getInstance();
        Util.getNodes().get(nodeReceiver).receiveMessage(message);
        tProccess.writeIntoTable(message);
    }
}