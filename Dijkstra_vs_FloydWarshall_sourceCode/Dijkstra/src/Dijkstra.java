import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

/*The class represents each vertex of a graph*/
class Vertex implements Comparable<Vertex> {

	public String sourceVertex;
	public List<Edge> adjcacencies;
	
	public double minDistance;
	public Vertex prev;
	
	public Vertex(String argVertex)
	{
		sourceVertex = argVertex;
		adjcacencies = new LinkedList<Edge>();
		prev = null;
		minDistance = Double.POSITIVE_INFINITY;
	}
	
	 public int compareTo(Vertex other)
	 {
	    return Double.compare(minDistance, other.minDistance);
	 }
}

/* The class represents each edge of a graph from a vertex */
class Edge {

	public Vertex target;
	public double weight;
	
	public Edge(Vertex argTarget,double argWeight)
	{
		target = argTarget;
		weight = argWeight;
	}
}


public class Dijkstra {
	
	private Map<String, Vertex> vertexMap = new HashMap<String, Vertex>( ); // Maps String to Vertex
	private PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>();
	
	/* The method adds an edge to the graph */
	public void addEdge(String sourceVertex,String destVertex,double cost)
	{
		Vertex v = getVertex(sourceVertex);
		Vertex w = getVertex(destVertex);
		v.adjcacencies.add(new Edge(w,cost));
	}
	
	/* The method checks if vertex is not present, it adds it to the vertexMap
	 * and returns the vertex.
	 */
	private Vertex getVertex(String sourceVertex)
	{
		Vertex v = (Vertex)vertexMap.get(sourceVertex);
		if (v == null)
		{
			v = new Vertex(sourceVertex);
			vertexMap.put(sourceVertex, v);			
		}
		return v;
	}
	
	/* The method performs dijkstra from one node to every other node in graph */
	public void dijkstra(Vertex sourceVertex)
	{
		sourceVertex.minDistance = 0;
		
		pq.add(sourceVertex);
		
		int nodeVisited = 0;
		while (!pq.isEmpty() && nodeVisited < vertexMap.size())
		{
			Vertex source = pq.poll();
			nodeVisited++;
			for (Iterator<Edge> itr = source.adjcacencies.iterator(); itr.hasNext();)
			{
				Edge e = (Edge) itr.next();
				Vertex nxt = e.target;
				double edgeWeight = e.weight;
				
				if (edgeWeight < 0) 
				{
					System.out.println("Graph has negative edges");
					break;
				}
				
				if (nxt.minDistance > source.minDistance + edgeWeight)
				{
					nxt.minDistance = source.minDistance + edgeWeight;
					nxt.prev = source;
					pq.add(nxt);
				}
			}
			
		}
		pq.clear();
	}
	

	public static void main(String[] args) throws IOException {

		Dijkstra g = new Dijkstra();
		
		System.out.println("Please enter input path file where graph is stored:");
		Scanner scanInput = new Scanner(System.in);
		String input = scanInput.next();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(input));
			String line = null;
			
			//To skip information of vertices and edges in file
			line = reader.readLine();
			
			//To get number of vertices and edges
			line = reader.readLine();
			String graphInfo[] = line.split(" ");
			int vertices = Integer.parseInt(graphInfo[1]);
			int edgesCount = Integer.parseInt(graphInfo[2]);
			System.out.println("Graph has " + vertices + " vertices " + edgesCount + " edges");
			
			//Reads graph data
			while ((line = reader.readLine()) != null) {
				String parts[] = line.split(" ");
				g.addEdge(parts[1], parts[2],Double.parseDouble(parts[3]));
			}
			
			Vertex[] allVertices = new Vertex[vertices];
			for (int i =0; i < vertices; i++)
			{
				allVertices[i] = (Vertex)g.vertexMap.get(Integer.toString(i+1));
			}
			
			long startTimeSeconds = System.currentTimeMillis();
			
			for (int i = 0; i < vertices; i++ )
			{
				for (Vertex v : allVertices) {
					v.minDistance = Double.POSITIVE_INFINITY;
				}
				g.dijkstra(allVertices[i]);
				/*for (Vertex v : allVertices) {
					System.out.println("Distance from " + allVertices[i].sourceVertex + " to " + v.sourceVertex + ": " + v.minDistance);
				}*/
			}
			
			long endTimeSeconds = System.currentTimeMillis();
			
			System.out.println("Start time : "+startTimeSeconds+"\nFinish time : "+endTimeSeconds+"\n Total time taken : "+(endTimeSeconds-startTimeSeconds));	
			
			reader.close();
			scanInput.close();
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		
	}

}
