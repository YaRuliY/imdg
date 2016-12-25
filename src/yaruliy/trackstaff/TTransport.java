package yaruliy.trackstaff;
import yaruliy.trackstaff.proccess.ProccessManager;
import yaruliy.trackstaff.proccess.TProccess;

public class TTransport {
    public static void sendMessage(int nodeReceiver, TMessage message, String regionName) {
        ProccessManager.getProccessByTableName(regionName).receiveMessage(nodeReceiver, message);
        TProccess.getInstance().writeIntoTable(message);
    }
}