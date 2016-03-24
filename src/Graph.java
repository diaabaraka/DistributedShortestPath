import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {

	private ArrayList<Vertex> vertices;
	private HashMap<Integer, Integer> idToIndex;
	private int nodeCount = 0;
	
	
	public Graph(){
		
		vertices = new ArrayList<>();
		idToIndex = new HashMap<Integer, Integer>();
		
		
	}
	
	
	public void addVertex(int id){
		
		// map id to array index
		Vertex newVertex = new Vertex(id);
		vertices.add(newVertex);
		idToIndex.put(id, nodeCount++);
		
		
	}
	
	public void addEdge(int sourceId , int destId){
		
		if(!idToIndex.containsKey(sourceId)){
			addVertex(sourceId);
		}
		if(!idToIndex.containsKey(destId)){
			addVertex(destId);
		}
		int source = idToIndex.get(sourceId);
		int dest = idToIndex.get(destId);
		
		vertices.get(source).addNeighbour(dest);
		relaxPaths(source, dest);
		
		
		
		
	}
	
	
	
	
	public void relaxPaths(int src, int dest) {
		for (int i = 0; i < vertices.size(); i++) {
			for (int j = i + 1; j < vertices.size(); j++) {

				if (vertices.get(i).isReachable(src) && vertices.get(dest).isReachable(j)) {
					int costViaEdge = vertices.get(i).getShortestPath(src) + 1 + vertices.get(dest).getShortestPath(j);
					if (vertices.get(i).isReachable(j)) {
						if (costViaEdge < vertices.get(i).getShortestPath(j)) {
							vertices.get(i).updateShortestPath(dest, costViaEdge);
						}
					} else {
						vertices.get(i).addShortestPath(j, costViaEdge);
					}
				}
			}
		}
	}
	
	
	public void allPairsShortestPath(){
		
		// loop over all the vertices and calculate the shortest paths
		
		for(int i = 0 ; i < vertices.size() ; i++){
			// get the correct id?
			runBFS(i);
			
			
		}
		
		
		
		
	}
	
	
	public void printAllShortestPaths(){
		
		for (int i = 0; i < vertices.size(); i++) {
			
			System.out.println("shortest paths of Node number :" + i);
			
			vertices.get(i).printShortestPaths();
			
			
			
		}
		
		
		
		
		
		
	}
	
	
	
	public void runBFS(int source){
		
		// this method will be resposible for running the bfs and find the shortest paths for specific paths
		
		Queue<Vertex> queue = new LinkedList<Vertex>();
		
		// remember to get the correct id  
		Vertex startingNode = vertices.get(source);
		startingNode.clearAllPaths();
		queue.add(startingNode);
		
		
		while(!queue.isEmpty()){
			
			
			Vertex curNode = queue.poll();
			
		
				
				HashSet<Integer> neighbours = curNode.getNeighbours();
				
				for(int id : neighbours){
					if(startingNode.isNeighbour(id)){
						// get the correct id
						queue.add(vertices.get(id));
					}
					else if(!startingNode.isReachable(id)){
						
						int cost = startingNode.getShortestPath(curNode.getId()) + 1;
						startingNode.addShortestPath(id, cost);
						// get the correct id
						queue.add(vertices.get(id));

						
					}
					
					
					
					
					
				
				
				
				
				
			}
			
			
			
			
			
			
			
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
	}
	
	

}
