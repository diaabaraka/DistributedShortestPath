import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Graph {

	private ArrayList<Vertex> vertices;
	private HashMap<Integer, Integer> idToIndex;
	private int nodeCount = 0;

	public Graph() {

		vertices = new ArrayList<>();
		idToIndex = new HashMap<Integer, Integer>();

	}

	public void addVertex(int id) {

		// map id to array index
		Vertex newVertex = new Vertex(nodeCount);
		vertices.add(newVertex);
		idToIndex.put(id, nodeCount++);

	}

	public void deleteEdge(int src, int dest) {
		// should update these index
		// check if it's neigbour
		vertices.get(src).deleteNeighbour(dest);

		for (Vertex v : vertices) {
			if (v.containsEdge(src, dest)) {
				runBFS(v.getId());
			}
		}

	}

	public void addEdge(int sourceId, int destId) {
		boolean newSrc = false;
		if (!idToIndex.containsKey(sourceId)) {
			addVertex(sourceId);
			newSrc = true;
		}
		if (!idToIndex.containsKey(destId)) {
			addVertex(destId);
			// loop over all the vertices and add this new node
			
			addNewDest(idToIndex.get(sourceId) , idToIndex.get(destId));
						
		}
		
		if(newSrc){
			addNewSrc(idToIndex.get(sourceId) , idToIndex.get(destId));
		}
		
		int source = idToIndex.get(sourceId);
		int dest = idToIndex.get(destId);

		vertices.get(source).addNeighbour(dest);
		relaxPaths(source, dest);

	}

	private void addNewDest(int src , int dest) {
		// TODO Auto-generated method stub
		for(Vertex v : vertices){
			
			if(v.isReachable(src)){
				HashSet<String> edges = v.getShortestPath(src);
				edges.add(src+" "+dest);
				int cost = v.getShortestPathCost(src)+1;
				Path path = new Path(cost);
				path.setEdges(edges);
				v.addShortestPath(dest, path);
				
				
			}
			
		}
		
		
		
		
	}

	private void addNewSrc(int src , int dest) {
		// TODO Auto-generated method stub
		Vertex newSrc = vertices.get(src);
		
		Vertex destVertex = vertices.get(dest);
		
		 HashMap<Integer, Path> shortestPaths = destVertex.getShortestPaths();
		 
		 Iterator it = shortestPaths.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        Path path = (Path)pair.getValue();
//		        System.out.println(pair.getKey() + " = " + ((Path)pair.getValue()).getCost());
		        Path newPath = new Path(path.getCost()+1);
		        newPath.setEdges(path.getEdges());
		        newPath.AddEdge(src+" "+dest);
		        newSrc.addShortestPath((int)pair.getKey(), newPath);
		        
		        
		               
		        
		    }
		 
		 
		
		
		
		
		
		
	}
	
	
	public void relaxPaths(int src, int dest) {
		for (int i = 0; i < vertices.size(); i++) {
			for (int j = i + 1; j < vertices.size(); j++) {

				if (vertices.get(i).isReachable(src) && vertices.get(dest).isReachable(j)) {
					int costViaEdge = vertices.get(i).getShortestPathCost(src) + 1
							+ vertices.get(dest).getShortestPathCost(j);
					if (vertices.get(i).isReachable(j)) {
						if (costViaEdge < vertices.get(i).getShortestPathCost(j)) {
							Path path = new Path(costViaEdge);
							HashSet<String> edges = new HashSet<>();
							edges.addAll(vertices.get(i).getShortestPath(src));
							edges.add(src + " " + dest);
							edges.addAll(vertices.get(dest).getShortestPath(j));
							path.setEdges(edges);
							vertices.get(i).updateShortestPath(dest, path);
						}
					} else {
						Path path = new Path(costViaEdge);
						HashSet<String> edges = new HashSet<>();
						edges.addAll(vertices.get(i).getShortestPath(src));
						edges.add(src + " " + dest);
						edges.addAll(vertices.get(dest).getShortestPath(j));
						path.setEdges(edges);
						vertices.get(i).addShortestPath(j, path);
					}
				}
			}
		}
	}

	public void allPairsShortestPath() {

		// loop over all the vertices and calculate the shortest paths

		for (int i = 0; i < vertices.size(); i++) {
			// get the correct id?
			runBFS(i);

		}

	}

	public void printAllShortestPaths() {

		for (int i = 0; i < vertices.size(); i++) {

			System.out.println("shortest paths of Node number :" + i);

			vertices.get(i).printShortestPaths();

		}

	}

	public void runBFS(int source) {

		// this method will be resposible for running the bfs and find the
		// shortest paths for specific paths

		Queue<Vertex> queue = new LinkedList<Vertex>();

		// remember to get the correct id
		Vertex startingNode = vertices.get(source);
		startingNode.clearAllPaths();
		queue.add(startingNode);

		while (!queue.isEmpty()) {

			Vertex curNode = queue.poll();

			HashSet<Integer> neighbours = curNode.getNeighbours();

			for (int id : neighbours) {
				if (startingNode.isNeighbour(id)) {
					// get the correct id
					queue.add(vertices.get(id));
				} else if (!startingNode.isReachable(id)) {

					int cost = startingNode.getShortestPathCost(curNode.getId()) + 1;
					Path path = new Path(cost);

					path.setEdges(startingNode.getShortestPath(curNode.getId()));
					path.AddEdge(curNode.getId() + " " + id);

					startingNode.addShortestPath(id, path);
					// get the correct id
					queue.add(vertices.get(id));

				}

			}

		}

	}

}
