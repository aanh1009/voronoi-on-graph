/**
 * Tuan Anh Ngo
 * Vertex.java: the Vertex class that defines vertices in the Graph with methods to get adjacent vertices and add, remove, and get incident edges and edges connected to specific vertices
 */
public class Vertex{
    private LinkedList<Edge> edges;

    /**
     * constructor for the Vertex object
     */
    public Vertex(){
        edges = new LinkedList<>();
    }

    /**
     * get the edge connecting this vertex to a specified vertex
     * @param vertex the connected vertex
     * @return the eedge connecting with that vertex
     */
    public Edge getEdgeTo(Vertex vertex){
        for (Edge edge: edges){
            if (edge.other(this).equals(vertex)){
                return edge;
            }
        }
        return null;  
    }

    /**
     * add an edge to the collection of incident edges
     * @param edge the added edge
     */
    public void addEdge(Edge edge){
        edges.add(edge);
    }

    /**
     * remove an edge from the collection of incident edges
     * @param edge the removed edge
     * @return whether the edge has been removed (exists)
     */
    public boolean removeEdge(Edge edge){
        for (int i = 0; i<edges.size(); ++i){
            if (edges.get(i).equals(edge)){
                edges.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * get an iterable object of adjacent vertices to this vertex
     * @return the iterable object of adjacent vertices
     */
    public Iterable<Vertex> adjacentVertices(){
        LinkedList<Vertex> output = new LinkedList<>();
        for (Edge edge: edges){
            output.add(edge.other(this));
        }
        return output;
    }

    /**
     * return an iterable object of incident edges
     * @return the iterable object of incident edges
     */
    public Iterable<Edge> incidentEdges(){
        return edges;
    }
}