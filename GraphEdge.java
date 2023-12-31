public class GraphEdge {
	private GraphNode u;
    private GraphNode v;
    private int type;
    private String label;

    
	GraphEdge(GraphNode u, GraphNode v, int type, String label){
		this.u = u;
		this.v = v;
		this.type = type;
		this.label = label;
	} // constructor GraphEdge
	
	
	public GraphNode firstEndpoint() {
        return u;
    } // getter first endpoint
    

    public GraphNode secondEndpoint() {
        return v;
    } // getter last endpoint
    

    public int getType() {
        return type;
    } // getter node type
    

    public String getLabel() {
        return label;
    } // getter label

    
    public void setType(int type) {
        this.type = type;
    } // setter node type
    

    public void setLabel(String label) {
        this.label = label;
    } // setter label 
	
	
} // class GraphEdge
