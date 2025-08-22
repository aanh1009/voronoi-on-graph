public class GraphTests {
    public static void main(String[] args) {
        {
            Graph newGraph = new Graph("graph1.txt");
            System.out.println(newGraph.getVertices().toString());
        }

        {
            Graph newGraph = new Graph(20, 0.7);
            Iterable<Edge> edges = newGraph.getEdges();
            int size = 0;
            for (Edge edge: edges){
                size++;
            }
            System.out.println(size);
        }

        {
            Vertex first = new Vertex();
            Vertex second = new Vertex();
            Vertex third= new Vertex();
            Edge newEdge = new Edge(first, third, 0.2);
            assert first.equals(newEdge.other(third)): " error in other";
            System.out.println("Other is okay");
        }

        {
            Graph newGraph = new Graph(0);
            Vertex first = new Vertex();
            Vertex second = new Vertex();
            Vertex third= new Vertex();
            Vertex fourth = new Vertex();
            
            newGraph.addEdge(first, third, 2);
            newGraph.addEdge(first,fourth, 3);
            newGraph.addEdge(second, third, 1.5);
            Edge firstEdge = newGraph.getEdge(first, third);
            Edge secondEdge = newGraph.getEdge(first, fourth);
            Edge thirdEdge = newGraph.getEdge(second, third);

            newGraph.remove(thirdEdge);
            System.out.println(newGraph.getEdge(second, third));
            System.out.println(newGraph.getEdge(first, third));
        }
    }
}
