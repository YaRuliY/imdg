package yaruliy.util.trackstaff;

import java.util.Arrays;

public class TElement {
    private int[] nodes = null;
    public TElement(int node){ this.nodes = new int[]{node}; }

    public void addInfo(int node){
        if (!containsNode(node)){
            int[] newmas = new int[this.nodes.length + 1];
            System.arraycopy(this.nodes, 0, newmas, 0, this.nodes.length);
            newmas[newmas.length - 1] = node;
            this.nodes = newmas;
        }
    }

    public int[] getNodes(){
        return this.nodes;
    }

    private boolean containsNode(int node){
        for (int i : this.nodes) {
            if (i == node) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.nodes);
    }
}