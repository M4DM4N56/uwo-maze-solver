import java.io.*;
import java.util.*;

public class Maze {
    
	private Graph mazeGraph;
    private int entranceNode, exitNode, coins, width,length ;

    
    public Maze(String inputFile) throws MazeException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
        	int odd = 0, even = 0;
        	// take info from first 4 lines
        	int scale  = Integer.parseInt(reader.readLine().trim());
        		width  = Integer.parseInt(reader.readLine().trim());
        		length = Integer.parseInt(reader.readLine().trim());
        		coins  = Integer.parseInt(reader.readLine().trim());
        		
        		mazeGraph = new Graph(length * width);
        	// start reading lines with readLine
            String line = reader.readLine();

            while (line !=  null) {
                // ODDLINE LOOP - accounts for walls, corridors, rooms, and doors
                for (int i = 0; i < line.length(); i++) {
                	char character = line.charAt(i);
                	
                    // only even values can be rooms
                    if (i % 2 ==  0) { 
                        if 		(character ==  's') 	entranceNode = odd; 
                        else if (character ==  'x') 	exitNode = odd;
                        odd++;
                    } // if
                    
                    else {
                    	// wall has no connections
                    	if (character ==  'w') continue;
                    	// ODD-1 AND ODD are the two nodes that must be connected by the doors/corridors
                        else if (character ==  'c') 	mazeGraph.insertEdge(mazeGraph.getNode(odd - 1), mazeGraph.getNode(odd), 0, "corridor"); // type 0 because 0 coins needed
                        else 						mazeGraph.insertEdge(mazeGraph.getNode(odd - 1), mazeGraph.getNode(odd), Integer.parseInt(String.valueOf(character)), "door");
                    } // else
                } // for

                // scan to next line
                line = reader.readLine();
                // if at the end of the file, stop
                if(line ==  null) break;

                // Read next line with only doors, corridors and walls (even text line)
                for (int j = 0; j < line.length(); j++) {
                	char character = line.charAt(j);
                    // Even values can be different types, others will be exclusively walls
                    if (j % 2 == 0) {
                    	if 		(character ==  'w') continue;
                    	else if (character ==  'c') mazeGraph.insertEdge(mazeGraph.getNode(even), mazeGraph.getNode(even + width), 0, "corridor");
                        else 					   mazeGraph.insertEdge(mazeGraph.getNode(even), mazeGraph.getNode(even + width), Integer.parseInt(String.valueOf(character)), "door");
                        even++;
                    } // if
                } // for
                
                // move to next line for next iteration
                line = reader.readLine();
            } // while
        } // try
        catch (Exception e) {
            System.err.println(e.getMessage());
            throw new MazeException("maze error.");
        } // catch
        
    } // constructor maze
	
	
	public Graph getGraph() throws MazeException {
	    if (mazeGraph ==  null) throw new MazeException("Error: invalid maze");
	    
	    return mazeGraph;
	} // method get graph
	
	

	public Iterator<GraphNode> solve() throws GraphException {
		// path stack stores the nodes of a correct traversal, start is the entrance node
	    Stack<GraphNode> path = new Stack<>();
	    GraphNode start = mazeGraph.getNode(entranceNode);

	    if (mazeGraph ==  null) throw new GraphException("Error: invalid graph");

	    // returns true or false depending on whether a working path is found
	    boolean success = dfs(start, path, coins); 

	    if (success) return path.iterator();
	    return null;
	} // solve
    

	private boolean dfs(GraphNode currentNode, Stack<GraphNode> path, int currentCoins) throws GraphException {
		// initialize the current number of coins, the mark the current node, and push it to the path
	    int initialCoins = currentCoins;
	    currentNode.mark(true);
	    path.push(currentNode);
	    GraphEdge edge;
	    GraphNode next;

	    // solution found
	    if (currentNode ==  mazeGraph.getNode(exitNode)) return true;
	    
	    // connection is an iterator of the possible paths that can be taken
	    Iterator<GraphEdge> connection = mazeGraph.incidentEdges(currentNode);
	    
	    // loop through the possible connections
	    while (connection.hasNext()) {
	        edge = connection.next();
	        next = edge.secondEndpoint();

	        // if neighboring node is unmarked
	        if (!next.isMarked()) {
	            // if the node is a door and we have enough coins, continue
	            if (Objects.equals(edge.getLabel(), "door") && currentCoins - edge.getType() >= 0) {
	                // update coin counter
	                currentCoins = currentCoins - edge.getType();
	                next.mark(true);

	                // continue traversing
	                if (dfs(next, path, currentCoins)) return true;

	                // not in the path, reimburse coins
	                if (path.peek() != next && path.peek() != mazeGraph.getNode(exitNode)) currentCoins = initialCoins;
	            } // if
	            
	            else if (Objects.equals(edge.getLabel(), "corridor")) {
	                next.mark(true);
	                // continue traversing through the corridor
	                if (dfs(next, path, currentCoins)) return true;
	            } // else if
	        } // if
	    }// while
	    
	    // backtracks if the path is not viable
	    backtrack(path, currentNode, currentCoins);

	    return false;
	} // method dfs
	
	
	private void backtrack(Stack<GraphNode> path, GraphNode currentNode, int currentCoins) throws GraphException {
		// if dead end, unmark and pop node
	    if (path.peek() !=  mazeGraph.getNode(exitNode)) {
	        currentNode.mark(false);
	        path.pop();
	    } // if
	} // method backtrack


	
	
	
	
} // class maze
