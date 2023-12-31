import java.util.*;

public class Graph implements GraphADT{
	private int numNodes;
    private List<GraphNode> nodes;
    private GraphEdge[][] adjacencyMatrix;
	
    
    public Graph(int n) {
        this.numNodes = n;
        this.nodes = new ArrayList<>();
        // creates empty adjacency matrix
        this.adjacencyMatrix = new GraphEdge[n][n];

        // initialize every connection to null
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                adjacencyMatrix[row][col] = null;
            } // for
        } // for

        // each row represents a node and its connections, which are added to the nodes list
        for (int row = 0; row < n; row++) { 
        	nodes.add(new GraphNode(row)); 
        } // for
        
    } // graph constructor


    @Override
	public void insertEdge(GraphNode nodeu, GraphNode nodev, int type, String label) throws GraphException {
		// gets names of nodes to connect
        int uName = nodeu.getName();
        int vName = nodev.getName();

        // check if nodes are in the specified range
        if (uName < 0 || uName >=  numNodes || vName < 0 || vName >=  numNodes) throw new GraphException("Error: node does not exist");
        
        // check if edge between u and v exist already
        if (adjacencyMatrix[uName][vName] !=  null || adjacencyMatrix[vName][uName] !=  null) throw new GraphException("Error: edge already exists");
        
        // create the connection between u and v with type
        adjacencyMatrix[uName][vName] = new GraphEdge(nodeu, nodev, type, label);
        adjacencyMatrix[vName][uName] = new GraphEdge(nodev, nodeu, type, label);
        
	} // method insert edge
    

	@Override
	public GraphNode getNode(int u) throws GraphException {
		// checks if u exists in the range
		if (u < 0 || u >= numNodes) throw new GraphException("Error: invalid node name");
	
        return nodes.get(u);
	} // getter get node


	@Override
	public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {
		int uName = u.getName();
		
		// check if u is in range
		if (uName < 0 || uName >=  numNodes) throw new GraphException("Error: node out of range");
		
		// creates a list of the edges of u
		List<GraphEdge> edges = new ArrayList<>();
		
		
        for (int vName = 0; vName < numNodes; vName++) {
        	// if the edge is not null, add the edge to the edge list
            if (adjacencyMatrix[uName][vName] !=  null) {
            	edges.add(adjacencyMatrix[uName][vName]);
            } // if
        } // for
		
        // return the iterator of the list
		return edges.iterator();
	
	} // method incident edges


	@Override
	public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
		int uName = u.getName();
		int vName = v.getName();
		
	    // checks if nodes are identical
	    if (uName == vName) {
	        throw new GraphException("Error: nodes are the same");
	    } // if
		
		// check if u and v are in range
        if (uName < 0 || uName >= numNodes || vName < 0 || vName >= numNodes) {
            throw new GraphException("Error: nodes out of range");
        } // if

        // Check if there is an edge between u and v
        if (adjacencyMatrix[uName][vName] ==  null && adjacencyMatrix[vName][uName] ==  null) {
            throw new GraphException("Error: no edge between nodes");
        } // if
        
        return adjacencyMatrix[uName][vName];
	
	} // method get edge


	@Override
	public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
		int uName = u.getName();
		int vName = v.getName();
		
		// check if u and v are in range
        if (uName < 0 || uName >= numNodes || vName < 0 || vName >= numNodes) {
            throw new GraphException("Error: nodes out of range");
        } // if
        
        // return whether or not the two nodes have any connection to each other
        return adjacencyMatrix[uName][vName] !=  null || adjacencyMatrix[vName][uName] !=  null;
        
	} // method are adjacent
    
	
    
} // class graph
