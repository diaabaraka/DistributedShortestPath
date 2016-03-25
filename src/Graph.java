import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
		
		ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime()
				.availableProcessors());

		
		
		vertices.get(src).deleteNeighbour(dest);

		for (Vertex v : vertices) {
			if (v.containsEdge(src, dest)) {
				
				final int nodeIndex = v.getId();
				 es.execute(new Runnable() {
				 public void run() {
						runBFS(nodeIndex);
				 }
				 });
				
				
			}
		}
		
		 es.shutdown();
		 try {
		 es.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
		 } catch (InterruptedException e) {
		
		 }
		
		

	}

	public void addEdge(int sourceId, int destId) {
		if (!idToIndex.containsKey(sourceId)) {
			addVertex(sourceId);
		}
		if (!idToIndex.containsKey(destId)) {
			addVertex(destId);

		}
		int source = idToIndex.get(sourceId);
		int dest = idToIndex.get(destId);

		vertices.get(source).addNeighbour(dest);

	}

	public void addNewEdge(int sourceId, int destId) {
		boolean newSrc = false;
		if (!idToIndex.containsKey(sourceId)) {
			addVertex(sourceId);
			newSrc = true;
		}
		if (!idToIndex.containsKey(destId)) {
			addVertex(destId);
			// loop over all the vertices and add this new node

			addNewDest(idToIndex.get(sourceId), idToIndex.get(destId));

		}

		if (newSrc) {
			addNewSrc(idToIndex.get(sourceId), idToIndex.get(destId));
		}

		int source = idToIndex.get(sourceId);
		int dest = idToIndex.get(destId);

		vertices.get(source).addNeighbour(dest);
		relaxPaths(source, dest);

	}

	private void addNewDest(int src, int dest) {
		// TODO Auto-generated method stub
		for (Vertex v : vertices) {

			if (v.isReachable(src)) {
				HashSet<String> edges = v.getShortestPath(src);
				edges.add(src + " " + dest);
				int cost = v.getShortestPathCost(src) + 1;
				Path path = new Path(cost);
				path.setEdges(edges);
				v.addShortestPath(dest, path);

			}

		}

	}

	private void addNewSrc(int src, int dest) {
		// TODO Auto-generated method stub
		Vertex newSrc = vertices.get(src);

		Vertex destVertex = vertices.get(dest);

		HashMap<Integer, Path> shortestPaths = destVertex.getShortestPaths();

		Iterator it = shortestPaths.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			Path path = (Path) pair.getValue();
			// System.out.println(pair.getKey() + " = " +
			// ((Path)pair.getValue()).getCost());
			Path newPath = new Path(path.getCost() + 1);
			newPath.setEdges(path.getEdges());
			newPath.AddEdge(src + " " + dest);
			newSrc.addShortestPath((int) pair.getKey(), newPath);

		}

	}

	public void relaxPaths(int src, int dest) {
		ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime()
				.availableProcessors());

		for (int i = 0; i < vertices.size(); i++) {

//			 relaxSpecificPath(i, src, dest);


			 final int nodeIndex = i;
			 es.execute(new Runnable() {
			 public void run() {
			 relaxSpecificPath(nodeIndex, src, dest);
			 }
			 });

		}

		es.shutdown();
		try {
			es.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
		} catch (InterruptedException e) {

		}

	}

	public void relaxSpecificPath(int i, int src, int dest) {
		for (int j = 0; j < vertices.size(); j++) {

			if (vertices.get(i).isReachable(src)
					&& vertices.get(dest).isReachable(j) && i != j) {
				int costViaEdge = 1;
				if (i != src) {
					costViaEdge += vertices.get(i).getShortestPathCost(src);
				}
				if (dest != j) {
					costViaEdge += vertices.get(dest)
							.getShortestPathCost(j);
				}

				if (!vertices.get(i).isReachable(j)
						|| costViaEdge < vertices.get(i)
								.getShortestPathCost(j)) {
					Path path = new Path(costViaEdge);
					HashSet<String> edges = new HashSet<>();
					if (i != src) {
						edges.addAll(vertices.get(i).getShortestPath(src));
					}
					edges.add(src + " " + dest);
					if (dest != j) {
						edges.addAll(vertices.get(dest).getShortestPath(j));
					}
					path.setEdges(edges);
					vertices.get(i).updateShortestPath(j, path);
				}

			}
		}

	}

	public void allPairsShortestPath() {

		// loop over all the vertices and calculate the shortest paths

		ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime()
				.availableProcessors());

		for (int i = 0; i < vertices.size(); i++) {
			// get the correct id?
//			runBFS(i);
			//
			 final int nodeIndex = i;
			 es.execute(new Runnable() {
			 public void run() {
			 runBFS(nodeIndex);
			 }
			 });
			//
			//

		}
		 es.shutdown();
		 try {
		 es.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
		 } catch (InterruptedException e) {
		
		 }

		// while(!es.isTerminated()){
		//
		// }

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

					int cost = startingNode
							.getShortestPathCost(curNode.getId()) + 1;
					Path path = new Path(cost);
					HashSet<String> edges = new HashSet<String>();
					edges.addAll(startingNode.getShortestPath(curNode.getId()));
					path.setEdges(edges);
					path.AddEdge(curNode.getId() + " " + id);

					startingNode.addShortestPath(id, path);
					// get the correct id
					queue.add(vertices.get(id));

				}

			}

		}

	}

}
