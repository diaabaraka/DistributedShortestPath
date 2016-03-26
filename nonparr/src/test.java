
public class test {
	/// non parrallll

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Graph graph = new Graph();
		
		
		for(int i = 0 ; i < 700 ; i++){
			graph.addEdge(i, i+1);
		}
		
		double x = System.currentTimeMillis();
		graph.allPairsShortestPath();

		
		
		
		graph.addNewEdge(0, 50);
		graph.addNewEdge(70, 100);
		graph.addNewEdge(200, 300);
		graph.addNewEdge(329, 250);
		graph.addNewEdge(0, 210);
		graph.addNewEdge(50, 120);
		graph.addNewEdge(206, 300);
		graph.addNewEdge(429, 150);
		graph.addNewEdge(0, 10);
		graph.addNewEdge(240, 110);
		graph.addNewEdge(201, 310);
		graph.addNewEdge(321, 251);
		graph.addNewEdge(419, 550);
		graph.addNewEdge(0, 600);
		graph.addNewEdge(240, 610);
		graph.addNewEdge(208, 310);
		graph.addNewEdge(321, 551);
		
		double y = System.currentTimeMillis();

		System.out.println("Time need to get shortest paths is "+(y-x));

		
		

	}

}
