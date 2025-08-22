/**
 * Tuan Anh Ngo
 * AdjacencyAlgorithm.java: a Class extending the VoronoiGraphPlayerAlgorithm to define the algorithm for the extension
 */
public class AdjacencyAlgorithm extends VoronoiGraphPlayerAlgorithm {
    /**
     * constructor to inherits instance fields from the constructor of the VoronoiGraphPlayerAlgorithm
     * @param g the VoronoiGraph
     */
    public AdjacencyAlgorithm(VoronoiGraph g) {
        super(g);
    }

    /**
     * choose the vertex to occupy by iterating through the graph and locate the vertex with highest product of value and number of adjacent vertices
     * @param playerIndex the player's id
     * @param numRemainingTurns number of turns left
     */
    public Vertex chooseVertex(int playerIndex, int numRemainingTurns){
        Vertex out = new Vertex();
        double maxNumOfAdjacentVertices = 0;
        double highestValue = Double.MIN_VALUE;
        for (Vertex v: graph.getVertices()){
            if (!graph.hasToken(v)){
                Iterable<Vertex> adjacentVertices = v.adjacentVertices();
                int numOfAdjacentVertices = 0;
                for (Vertex vertex: adjacentVertices){
                    numOfAdjacentVertices+=1;
                }
                if (numOfAdjacentVertices>= maxNumOfAdjacentVertices &&  graph.getValue(v)*numOfAdjacentVertices > highestValue){
                    out = v;
                    maxNumOfAdjacentVertices = numOfAdjacentVertices;
                    highestValue = graph.getValue(v)*numOfAdjacentVertices;
                }
            }
        }
        return out;
    }
}