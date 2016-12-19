package yaruliy.util.trackstaff;

public class TElement {
    private int[] nodes = null;
    private int[] sizes = null;
    public int[] getNodes(){ return this.nodes; }

    public TElement(int node, int size){
        this.nodes = new int[]{node};
        this.sizes = new int[]{size};
    }

    public void addInfo(int node, int size){
        if (!containsNode(node)){
            int[] newmas = new int[this.nodes.length + 1];
            System.arraycopy(this.nodes, 0, newmas, 0, this.nodes.length);
            newmas[newmas.length - 1] = node;
            this.nodes = newmas;

            int[] newmassizes = new int[this.sizes.length + 1];
            System.arraycopy(this.sizes, 0, newmassizes, 0, this.sizes.length);
            newmassizes[newmassizes.length - 1] = size;
            this.sizes = newmassizes;
        }
        else {
            this.sizes[getNumberIndex(node)] = this.sizes[getNumberIndex(node)] + size;
        }
    }

    public int getTotalCount(){
        int[] mas = this.sizes;
        int totalCount = 0;
        for (int i: mas)
            totalCount = totalCount + i;
        return totalCount;
    }

    private boolean containsNode(int node){
        for (int i : this.nodes)
            if (i == node) return true;
        return false;
    }

    private int getNumberIndex(int number){
        for (int i = 0; i < this.nodes.length; i++)
            if (this.nodes[i] == number) return i;
        return -1;
    }

    @Override
    public String toString() {
        int[] a = this.nodes;
        int[] b = this.sizes;
        if (a == null)
            return "null";
        int iMax = a.length - 1;
        if (iMax == -1)
            return "[](0)";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; ; i++) {
            sb.append(a[i]).append(" (").append(b[i]).append(")");
            if (i == iMax)
                return sb.append(']').toString();
            sb.append(", ");
        }
    }
}