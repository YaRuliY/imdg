package yaruliy.trackstaff;
import yaruliy.trackstaff.proccess.MProccessManager;
import yaruliy.trackstaff.proccess.TProccess;

public class TTransport {
    public static void sendMessage(int nodeReceiver, TMessage message, String regionName) {
        MProccessManager.getProccessByTableName(regionName).receiveMessage(nodeReceiver, message);
        TProccess.getInstance().writeIntoTable(message);
    }

    public static void sendNodesToTProccess(int[] mas, String joinUniKey) {
        TProccess.getInstance().writeNodes(mas, joinUniKey);
    }
}