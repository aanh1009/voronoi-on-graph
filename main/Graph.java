/**
 * Tuan Anh Ngo
 * Graph.java: a class for the Graph data structure implemented using adjacency (linked) lists with methods to get, add, and remove edges and vertices and calculate distances 
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;
public class Graph {
    private LinkedList<Vertex> vertices;
    private LinkedList<Edge> edges;

    /**
     * constructor for an empty graph
     */
    public Graph(){
        this(0);
    }

    /**
     * constructor for a graph with specified size and no edges
     * @param n the graph's size
     */
    public Graph(int n){
        this(n,0.0);
    }

    /**
     * constructor for a graph with specified size and probability of adjacency between vertices
     * @param n the graph's size
     * @param probability the probability of adjacency between each pair of vertices
     */
    public Graph(int n, double probability){
        vertices = new LinkedList<>();
        edges = new LinkedList<>();
        Random ran = new Random();
        for (int i = 0; i<n;++i){
            addVertex();
        }
        for (int i = 0; i<n;++i){
            for (int j = i+1; j<n; ++j){
                double p = ran.nextDouble();
                if (p<probability){
                    this.addEdge(vertices.get(i), vertices.get(j), 1+ ran.nextDouble());
                }
            }
        }
    }

    /**
     * get the graph's size
     * @return the graph's size
     */
    public int size(){
        return vertices.size();
    }

    /**
     * get an iterable object containing all vertices
     * @return an iterable object containing all vertices
     */
    public Iterable<Vertex> getVertices(){
        return vertices;
    }

    /**
     * get an iterable object containing all edges
     * @return an iterable object containing all edges
     */
    public Iterable<Edge> getEdges(){
        return edges;
    }

    /**
     * add an vertex to the graph
     * @return the added vertex
     */
    public Vertex addVertex(){
        Vertex newVertex = new Vertex();
        vertices.addLast(newVertex);
        return newVertex;
    }

    /**
     * add an edge to the graph
     * @param u the vertex on one end of the edge
     * @param v the vertex on the other end
     * @param distance the edge's distance
     * @return the added edge
     */
    public Edge addEdge(Vertex u, Vertex v, double distance){
        Edge newEdge = new Edge(u, v, distance);
        u.addEdge(newEdge);
        v.addEdge(newEdge);
        edges.add(newEdge);
        return newEdge;
    }

    /**
     * get an edge connecting 2 specified vertices
     * @param u the vertex on one end
     * @param v the vertex on the other end
     * @return the edge connecting the 2 vertices
     */
    public Edge getEdge(Vertex u, Vertex v){
        for (Edge edge: edges){
            Vertex[] points = edge.vertices();
            if (points[0].equals(u) || points[1].equals(u)){
                if (edge.other(u).equals(v)){
                    return edge;
                }
            }
        }
        return null;
    }

    /**
     * remove a vertex from the graph
     * @param vertex the vertex that needs to be removed
     * @return the removed vertex
     */
    public boolean remove(Vertex vertex){
        Iterable<Edge> e = vertex.incidentEdges();
        for (Edge edge: e){
            remove(edge);
        }
       for (int i = 0; i<vertices.size(); ++i){
            if (vertices.get(i).equals(vertex)){
                vertices.remove(i);
                return true;
            }
        } 
        return false;
    }

    /**
     * get a hashmap containing all shortest distances from a specified vertex to all other vertices
     * @param source the specified vertex
     * @return a hashmap containing the shortest possible distances from the source to all other vertices
     */
    public HashMap<Vertex, Double> distanceFrom(Vertex source){
        HashMap<Vertex, Double> distances = new HashMap<>();
        PriorityQueue<Vertex> queue = new PriorityQueue<>(new Comparator<Vertex>(){
            public int compare(Vertex a, Vertex b){
                return distances.get(a).compareTo(distances.get(b));
            }
        });
        distances.put(source, (double) 0);
        queue.offer(source);
        for (Vertex vertex: vertices){
            if (!vertex.equals(source)){
                distances.put(vertex,Double.MAX_VALUE);
                queue.offer(vertex);
            }
        }

        while (!queue.isEmpty()){
            Vertex u = queue.poll();
            for (Vertex v: u.adjacentVertices()){
                double alt = distances.get(u) + u.getEdgeTo(v).distance();
                if (alt<distances.get(v)){
                    distances.put(v, alt);
                    queue.remove(v);
                    queue.offer(v);
                }
            }
        }
        return distances;
    }

    /**
     * remove an edge from the graph
     * @param edge the edge that needs to be removed
     * @return the removed edge
     */
    public boolean remove(Edge edge){
        Vertex[] v = edge.vertices();
        v[0].removeEdge(edge);
        v[1].removeEdge(edge);
        for (int i = 0; i<edges.size(); ++i){
            if (edges.get(i).equals(edge)){
                edges.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * get the vertex at a specified index
     * @param index the index of the vertex that needs to be returned
     * @return the vertex at the specified index
     */
    public Vertex getVertex(int index){
        return vertices.get(index);
    }
    
/* *
                 * A graph constructor that takes in a filename and builds
                 * the graph with the number of vertices and specific edges 
                 * specified.  
                 * */
                public Graph( String filename ) {
            
                    try {
                        //Setup for reading the file
                        FileReader fr = new FileReader(filename);
                        BufferedReader br = new BufferedReader(fr);
            
                        //Get the number of vertices from the file and initialize that number of vertices
                        vertices = new LinkedList<>() ;
                        Integer numVertices = Integer.valueOf( br.readLine().split( ": " )[ 1 ] ) ;
                        for ( int i = 0 ; i < numVertices ; i ++ ) {
                            vertices.add( new Vertex() );
                        }
            
                        //Read in the edges specified by the file and create them
                        edges = new LinkedList<>() ; //If you used a different data structure to store Edges, you'll need to update this line
                        String header = br.readLine(); //We don't use the header, but have to read it to skip to the next line
                        //Read in all the lines corresponding to edges
                        String line = br.readLine();
                           while(line != null){
                               //Parse out the index of the start and end vertices of the edge
                                String[] arr = line.split(",");
                                Integer start = Integer.valueOf( arr[ 0 ] ) ;
                                Integer end = Integer.valueOf( arr[ 1 ] ) ;
                                
                                //Make the edge that starts at start and ends at end with weight 1
                                Edge edge = new Edge( vertices.get( start ) , vertices.get( end ) , 1. ) ;
                             //Add the edge to the set of edges for each of the vertices
                             vertices.get( start ).addEdge( edge ) ;
                            vertices.get( end ).addEdge( edge ) ;
                            //Add the edge to the collection of edges in the graph
                            this.edges.add( edge );
                            
                            //Read the next line
                            line = br.readLine();
                        }
                        // call the close method of the BufferedReader:
                        br.close();
                        System.out.println( this.edges );
                      }
                      catch(FileNotFoundException ex) {
                        System.out.println("Graph constructor:: unable to open file " + filename + ": file not found");
                      }
                      catch(IOException ex) {
                        System.out.println("Graph constructor:: error reading file " + filename);
                      }
                }
}


