import java.util.HashMap;

public class PlayerAlgorithm extends VoronoiGraphPlayerAlgorithm {
    public PlayerAlgorithm(VoronoiGraph g) {
        super(g);
    }

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
