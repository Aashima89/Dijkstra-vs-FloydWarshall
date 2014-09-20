
import java.io.*;
import java.util.Scanner;

public class FloydWarshall {
		
	public static void main(String[] args) throws IOException
	{
	
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
			
			//Create graph adjacency matrix
			double[][] costMatrix = new double[vertices][vertices];
			for (int i = 0; i < vertices; i++)
				for (int j = 0; j < vertices; j++)
				{
					if (i==j) costMatrix[i][j] = 0;
					else costMatrix[i][j] = Double.POSITIVE_INFINITY;
				}					
						
			//Reads graph data
			while ((line = reader.readLine()) != null)
			{
				String edges[]= line.split(" ");
				costMatrix[Integer.parseInt(edges[1])-1][Integer.parseInt(edges[2])-1] = Integer.parseInt(edges[3]);					
			}
						
			reader.close();
			
			long startTimeSeconds = System.currentTimeMillis();
			
			//Finding Shortest Path between any two vertices
			for (int k = 0; k < vertices; k++)
				for (int i = 0; i < vertices; i++)
					for (int j = 0; j < vertices; j++)
					{
						double sum = costMatrix[i][k] + costMatrix[k][j];
						if ( sum < costMatrix[i][j])
							costMatrix[i][j] = sum;
					}
			
			long endTimeSeconds = System.currentTimeMillis();
			
			System.out.println("Start time : "+startTimeSeconds+"\nFinish time : "+endTimeSeconds+"\n Total time taken : "+(endTimeSeconds-startTimeSeconds));
			
		} catch (FileNotFoundException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
