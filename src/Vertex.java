import java.util.HashMap;
import java.util.HashSet;

public class Vertex {
	private int id;
	private HashSet<Integer> neighbours;
	private HashMap<Integer, Integer> shortestPaths;

	public Vertex(int id) {
		this.id = id;
		neighbours = new HashSet<>();
		shortestPaths = new HashMap<>();
	}

	public void addNeighbour(int id) {
		neighbours.add(id);

	}

	public void deleteNeighbour(int id) {
		neighbours.remove(id);
	}

	public boolean isNeighbour(int id) {
		return neighbours.contains(id);
	}

	public void addShortestPath(int dest, int cost) {
		shortestPaths.put(dest, cost);
	}

	public int getShortestPath(int dest) {
		return shortestPaths.get(dest);
	}

	public boolean isReachable(int dest) {
		return shortestPaths.containsKey(dest);
	}

	public void updateShortestPath(int dest, int cost) {
		shortestPaths.put(dest, cost);
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

	public HashMap<Integer, Integer> getShortestPaths() {
		return shortestPaths;
	}

	public void setShortestPaths(HashMap<Integer, Integer> shortestPaths) {
		this.shortestPaths = shortestPaths;
	}

}
