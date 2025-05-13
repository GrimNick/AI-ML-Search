import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Graph {
    HashMap<myNode, LinkedList<myNode>> adjacencyMap;
    boolean directed;

    public Graph(boolean directed){
        this.directed = directed;
        adjacencyMap = new HashMap<>();
    }
    public void insertEdge(myNode source, myNode target){
        LinkedList<myNode> tempNodes = adjacencyMap.getOrDefault(source,new LinkedList<>());
        tempNodes.add(target);
        adjacencyMap.put(source, tempNodes);
        if(!directed){
            tempNodes = adjacencyMap.getOrDefault(target,new LinkedList<>());
            tempNodes.add(source);
            adjacencyMap.put(target, tempNodes);
        }
    }

    public LinkedList<myNode> getAdjacents(myNode source){
        return adjacencyMap.get(source);
    }
    public ArrayList<myNode> getAllNodes() {
        return new ArrayList<>(adjacencyMap.keySet());
    }


    public void printEdges(myNode source){
        LinkedList<myNode> EdgesList = adjacencyMap.get(source);
        for(myNode e: EdgesList){
            System.out.print(source.name+"->"+ e.getName());
            System.out.println();
        }
    }


}
