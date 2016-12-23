package yaruliy.trackstaff;
import yaruliy.util.Util;
import yaruliy.trackstaff.proccess.TProccess;

public class TTransport {
    public static void sendMessage(int nodeReceiver, TMessage message) {
        TProccess tProccess = TProccess.getInstance();
        Util.getNodes().get(nodeReceiver).receiveMessage(message);
        tProccess.writeIntoTable(message);
    }
}