import java.util.ArrayList;

public class Graph {

	private ArrayList<Vertex> vertices;

	public Graph() {

		vertices = new ArrayList<>();

	}

	public void addVertex(int id) {

		// map id to array index
		Vertex newVertex = new Vertex(id);
		vertices.add(newVertex);

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

}
