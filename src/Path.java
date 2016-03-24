import java.util.HashSet;

public class Path {
	private int cost;
	private HashSet<String> edges;
	public Path() {
		edges = new HashSet<>();
	}
	public Path(int cost) {
		this.cost = cost;
		edges = new HashSet<>();
	}
	public void AddEdge(String edge) {
		edges.add(edge);
		
	}
	
	
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public HashSet<String> getEdges() {
		return edges;
	}
	public void setEdges(HashSet<String> edges) {
		this.edges = edges;
	}
}
