import java.util.ArrayList;

public class Graph {

	private ArrayList<Vertex> vertices;
	
	
	
	public Graph(){
		
		vertices = new ArrayList<>();
		
		
	}
	
	
	public void addVertex(int id){
		
		// map id to array index
		Vertex newVertex = new Vertex(id);
		vertices.add(newVertex);
		
		
	}
	
	

}
