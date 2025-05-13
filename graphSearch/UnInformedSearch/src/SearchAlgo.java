import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class SearchAlgo {
    static Graph graph;
    static int iterDFS;
    static int iterBFS;

    public static void main(String[] args){
        myNode n1 = new myNode(1,"Arad");
        myNode n2 = new myNode(2,"Zerind");
        myNode n3 = new myNode(3,"Oradea");
        myNode n4 = new myNode(4,"Sibiu");
        myNode n5 = new myNode(5,"Timisoara");
        myNode n6 = new myNode(6,"Lugoj");
        myNode n7 = new myNode(7,"Mehadia");
        myNode n8 = new myNode(8,"Dolbreta");
        myNode n9 = new myNode(9,"Craiova");
        myNode n10 = new myNode(10,"Rimnicu Vilcea");
        myNode n11 = new myNode(11,"Fagaras");
        myNode n12 = new myNode(12,"Pitesti");
        myNode n13 = new myNode(13,"Bucharest");
        myNode n14 = new myNode(14,"Giurgiu");
        myNode n15 = new myNode(15,"Urziceni");
        myNode n16 = new myNode(16,"Hirsova");
        myNode n17 = new myNode(17,"Eforie");
        myNode n18 = new myNode(18,"Vaslui");
        myNode n19 = new myNode(19,"lasi");
        myNode n20 = new myNode(20,"Neamt");

        graph = new Graph(false);
        graph.insertEdge(n1,n2);
        graph.insertEdge(n1,n4);
        graph.insertEdge(n1,n5);
        graph.insertEdge(n2,n3);
        graph.insertEdge(n3,n4);
        graph.insertEdge(n4,n11);
        graph.insertEdge(n4,n10);
        graph.insertEdge(n5,n6);
        graph.insertEdge(n6,n7);
        graph.insertEdge(n7,n8);
        graph.insertEdge(n8,n9);
        graph.insertEdge(n9,n10);
        graph.insertEdge(n9,n12);
        graph.insertEdge(n10,n12);
        graph.insertEdge(n11,n13);
        graph.insertEdge(n12,n13);
        graph.insertEdge(n13,n14);
        graph.insertEdge(n13,n15);
        graph.insertEdge(n15,n16);
        graph.insertEdge(n15,n18);
        graph.insertEdge(n16,n17);
        graph.insertEdge(n18,n19);
        graph.insertEdge(n19,n20);


//        graph.printEdges(n1);
        iterDFS=0;
        String path = DFS(n6,n12);
        if(path==null) System.out.println("Not found using DFS");
        else System.out.println("iters :["+ iterDFS+"] Found using DFS :"+path);
        resetVisited();
        iterBFS=0;
        String path2 = BFS(n6,n12);
        if(path2==null) System.out.println("Not found using BFS");
         else System.out.println("iters: ["+iterBFS+"] Found using BFS :"+path2);

    }

    public static String DFS(myNode v,myNode goal){
        iterDFS++;
        if(!v.getVisited()) v.setVisited();
        else return null;
        if(v.getName().equalsIgnoreCase(goal.getName())) return v.getName();
        for(myNode w:graph.getAdjacents(v)) {
            if(!w.getVisited()) {
                String result = DFS(w, goal);
                if(result!= null){
                    return v.getName()+"->"+result;
                }
            }
        }

        return null;

    }

    public static String BFS(myNode v, myNode goal){

        LinkedList<myNode> queue = new LinkedList<myNode>();
        ArrayList<String> path = new ArrayList<>();
        String finalPath="";
        boolean first =true;
        HashMap<myNode,myNode> parentMap = new HashMap<>();
        v.setVisited();
        queue.add(v);
        iterBFS++;

        parentMap.put(v,null);
        while(!queue.isEmpty()){
            myNode checkNode = queue.pollFirst();
            if(checkNode.getName().equalsIgnoreCase(goal.getName())){
                while(checkNode!=null){
                    path.add(checkNode.getName());
                    checkNode = parentMap.get(checkNode);
                }
                Collections.reverse(path);
                for(String n:path){

                    if(first){
                        finalPath = n;
                        first = false;
                    }
                    else{
                        finalPath +="->" +n;
                    }
                }
                return finalPath;
            }
            for(myNode e:graph.getAdjacents(checkNode)){
                if(!e.getVisited() && !queue.contains(e)){
                    e.setVisited();
                    parentMap.put(e,checkNode);

                    queue.add(e);
                    iterBFS++;
                }
            }

        }
        return null;
    }

    public static void resetVisited(){
        for(myNode node:graph.getAllNodes()){
            node.setOff();
        }
    }

}
