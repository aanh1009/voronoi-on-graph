/**
 * Tuan Anh Ngo
 * Edge.java: the Edge class for edges in the graph with getters for its distance and finding the vertices it connects. 
 */
public class Edge {
    private Vertex start;
    private Vertex end;
    private double distance;

    /**
     * constructor for and Edge object
     * @param u the first vertex
     * @param v the other vertex 
     * @param distance the edge's distance
     */
    public Edge(Vertex u, Vertex v, double distance){
        start = u;
        end = v;
        this.distance = distance;
    }

    /**
     * get the edge's distance
     * @return the edge's distance
     */
    public double distance(){
        return distance;
    }

    /**
     * get the vertex on the other end of the edge 
     * @param vertex the vertex on one end
     * @return the vertex on the other end
     */
    public Vertex other(Vertex vertex){
        if (vertex.equals(start)){
            return end;
        }
        else if (vertex.equals(end)){
            return start;
        }
        else{
            return null;
        }
    }

    /**
     * get the vertices connected by the edge
     * @return a list of 2 vertices connected by the edge
     */
    public Vertex[] vertices(){
        return new Vertex[]{start, end};
    }

}
