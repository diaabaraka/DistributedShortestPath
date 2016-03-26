import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Controller {
	
	// non paralllllllllllll
	public static void main(String[] args) throws IOException {
		Graph graph = new Graph();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		int src, dest;
		while(!(line = br.readLine()).equals("S")) {
			StringTokenizer st = new StringTokenizer(line);
			src = Integer.parseInt(st.nextToken());
			dest = Integer.parseInt(st.nextToken());
			graph.addEdge(src, dest);
		}
		long x = System.currentTimeMillis();
		
		graph.allPairsShortestPath();
		
		long y = System.currentTimeMillis();
		
		System.out.println("Time Needed To get All PAirs Shortest Paths is non : "+(y-x));
		
//		System.out.println(y-x);
		System.out.println("R");
		String query;
		while(true) {
			StringBuilder sb=new StringBuilder();
			
			
			while(!(line = br.readLine()).equals("F")) {
				StringTokenizer st = new StringTokenizer(line);
				query = st.nextToken();
				src = Integer.parseInt(st.nextToken());
				dest = Integer.parseInt(st.nextToken());
				if(query.equals("Q")){
					// get the shortest path between src and dest
					int cost = graph.getShortestPath(src, dest);
					sb.append(cost + "\n");
				}
				else if(query.equals("A")){
					//add new edge between src and dest
					graph.addNewEdge(src, dest);
					
				}
				else{
					// query = D
					// remove edge between src dest
					graph.deleteEdge(src, dest);
				}
				
				
			}
			System.out.print(sb);
		}
		

	}

}
