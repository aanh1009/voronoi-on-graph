/**
 * Tuan Anh Ngo
 * PlayerAlgorithm.java: a Class extending the VoronoiGraphPlayerAlgorithm to define the Weighted Distancing Algorithm (WDA)
 */
import java.util.HashMap;

public class PlayerAlgorithm extends VoronoiGraphPlayerAlgorithm {
    
    /**
     * constructor to inherits instance fields from the constructor of the VoronoiGraphPlayerAlgorithm
     * @param g the VoronoiGraph
     */
    public PlayerAlgorithm(VoronoiGraph g) {
        super(g);
    }

    /**
     * choose the vertex to occupy by iterating through the graph and locate the vertex with the highest sum of internal and external values with distance weighting
     * @param playerIndex the player's id
     * @param numRemainingTurns number of turns left
     */
    public Vertex chooseVertex(int playerIndex, int numRemainingTurns){
        Vertex out = new Vertex();
        double highest = Double.MIN_VALUE;
        for (Vertex v: graph.getVertices()){
            if (!graph.hasToken(v)){
                double ratio = 0;
                HashMap<Vertex, Double> allDistances = graph.distanceFrom(v);
                double totalDistance = 0;
                for (Vertex vertex: graph.getVertices()){
                    totalDistance += allDistances.get(vertex);
                }
                for (Vertex vertex: graph.getVertices()){
                    if (!graph.hasToken(vertex)){
                        if (vertex.equals(v)){
                            ratio+= 1.2*graph.getValue(vertex);
                        }
                        else if (allDistances.get(vertex)<=1/4*totalDistance/allDistances.size()){
                            ratio+= graph.getValue(vertex)/allDistances.get(vertex);
                        }
                        else {
                            ratio+= graph.getValue(vertex)/allDistances.get(vertex)/allDistances.get(vertex);
                        }
                    }
                }
                if (ratio>highest){
                    highest = ratio;
                    out = v;
                }
            }
        }
        return out;
    }
}
