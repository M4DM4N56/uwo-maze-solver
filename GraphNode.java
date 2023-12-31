public class GraphNode {
    private int name;
    private boolean marked;
	
    
    public GraphNode(int name) {
    	this.name = name;
    	this.marked = false;
    } // constructor GraphNode
   
    
    public void mark(boolean mark) {
    	this.marked = mark;
    } // method mark
    
    
    public boolean isMarked() {
    	return marked;
    } // method isMarked
    
    public int getName() {
    	return name;
    } // method getName

    
} // class graphnode
