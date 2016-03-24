
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class Vertex {
	private int id;
	private HashSet<Integer> neighbours;
	private HashMap<Integer, Path> shortestPaths;

	public Vertex(int id) {
		this.id = id;
		neighbours = new HashSet<>();
		shortestPaths = new HashMap<>();
	}

	public void printShortestPaths(){
		
		Iterator it = shortestPaths.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	    }
		
		
		
	}
	public void addNeighbour(int id) {
		Path edge = new Path(1);
		edge.AddEdge(this.id + " " + id);
		neighbours.add(id);
		shortestPaths.put(id, edge);

	}

	public void deleteNeighbour(int id) {
		neighbours.remove(id);
		shortestPaths.remove(id);
	}

	public boolean isNeighbour(int id) {
		return neighbours.contains(id);
	}

	public void addShortestPath(int dest, Path path) {
		shortestPaths.put(dest, path);
	}

	public int getShortestPathCost(int dest) {
		return shortestPaths.get(dest).getCost();
	}
	public HashSet<String> getShortestPath(int dest) {
		return shortestPaths.get(dest).getEdges();
	}

	public boolean isReachable(int dest) {
		return shortestPaths.containsKey(dest);
	}

	public void updateShortestPath(int dest, Path path) {
		shortestPaths.put(dest, path);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public HashSet<Integer> getNeighbours() {
		return neighbours;
	}

	public void setNeighbours(HashSet<Integer> neighbours) {
		this.neighbours = neighbours;
	}

	public HashMap<Integer, Path> getShortestPaths() {
		return shortestPaths;
	}

	public void setShortestPaths(HashMap<Integer, Path> shortestPaths) {
		this.shortestPaths = shortestPaths;
	}

	public void clearAllPaths() {
		shortestPaths.clear();
		
		for(int id : neighbours){
			Path edge = new Path(1);
			edge.AddEdge(this.id + " " + id);
			shortestPaths.put(id, edge);
		}
		
	}

}
