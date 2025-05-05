import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.*;
import java.util.PriorityQueue;
public class SearchingAlgo extends JPanel {
    static int totalGreedDist=0;
    static int totalstarDist=0;

    //print all cities made with their edges name and cost in console
    //better display in jpanel by making names more visible, repeated edge should label below the arrow
    //better coloring for start and end
    //add ukalo,etc. as h
    //add list button and start search button in jpanel by making events
    //
    public static void main(String args[]){
        //Sir ko bata tannu parla

        System.out.println("Do you want simple or custom?(S for simple and anything else for custom ");
        Scanner myTexts = new Scanner(System.in);
        String Choice = myTexts.nextLine();
        if(Choice.toLowerCase().contains("s")) {


            myNode sibiu = new myNode("sibiu", -14.573, 302.174);
            myNode fagaras = new myNode("fagaras", 150.291, 220.261);
            myNode rimnicu = new myNode("rimnicu", -10.518, 200.826);
            myNode pitesti = new myNode("pitesti", 80.337, 2.174);
            myNode bucharest = new myNode("bucharest", 129.146, -118.565);
            sibiu.child.add(new edge(fagaras, 99));
            sibiu.child.add(new edge(rimnicu, 80));
            rimnicu.child.add(new edge(pitesti, 97));
            pitesti.child.add(new edge(bucharest, 101));
            fagaras.child.add(new edge(bucharest, 211));

            List<myNode> listOfNode = new ArrayList<>();
            listOfNode.add(sibiu);
            listOfNode.add(fagaras);
            listOfNode.add(rimnicu);
            listOfNode.add(pitesti);
            listOfNode.add(bucharest);
            visualize(listOfNode);

            List<String> path = GreedySearch(sibiu, bucharest);
            System.out.println("greedy path is " + path);

            List<String> path2 = AStarSearch(sibiu, bucharest);
            System.out.println("a star path is " + path2);
        }
        else{
            System.out.println("How many cities or node do you want?(max 7 digit i think)");
            int citiesChoices = myTexts.nextInt();
            myTexts.nextLine();
            System.out.println("Do you want bidirectional edges(two way) y for yes else no?");
            String biChoice = myTexts.nextLine();

            if(biChoice.toLowerCase().contains("y")) GraphGenerator.setIsBidirectional();

            System.out.println("Do you want random edges(0 to edges) y for yes else no?");
            String edgeChoice = myTexts.nextLine();

            if(edgeChoice.toLowerCase().contains("y")) GraphGenerator.setIsRandomEdge();

            System.out.println("How many edges (neighbors do you want?");
            int edgesChoices = myTexts.nextInt();
            myTexts.nextLine();

            List<myNode> listOfNode = GraphGenerator.generateGraph(citiesChoices,edgesChoices);

            System.out.println("visualizing ");
            for(int i=0;i<citiesChoices;i++){
                myNode current = listOfNode.get(i);
                for(edge ed:current.child){
                    System.out.println("["+current.name+"->"+ed.target.name+" : cost(dist) :"+ ed.cost+"]");
                }
            }
            System.out.println("visualizing ");

            visualize(listOfNode);

            while(true) {
                totalGreedDist=0;
                totalstarDist=0;
                System.out.println("choose start location by their ending number of name");
                int startChoice = myTexts.nextInt();
                myTexts.nextLine();
                System.out.println("choose end location by their ending number of name");
                int endChoice = myTexts.nextInt();
                myTexts.nextLine();

                List<String> path = GreedySearch(listOfNode.get(startChoice), listOfNode.get(endChoice));
                System.out.println("greedy path is " + path + "with cost: "+ totalGreedDist);
            //total cost chaiyo
                List<String> path2 = AStarSearch(listOfNode.get(startChoice), listOfNode.get(endChoice));
                System.out.println("a star path is " + path2 + "with cost: "+ totalstarDist);

                System.out.println("Do you want another choice, y for yes else no?");
                String choiceAgain = myTexts.nextLine();
                if(choiceAgain.toLowerCase().contains("n")) break;
            }
        }
    }

    public static double calculateH(myNode A, myNode B){
        double x1= A.x;
        double y1= A.y;
        double x2= B.x;
        double y2= B.y;
        double h = Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2,2) );
        //System.out.println("h is "+h + "for "+ A.name);
        return h;

    }
    // Static method you can call from searchingalgo
    public static void visualize(List<myNode> nodeList) {
        JFrame frame = new JFrame("Graph Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setContentPane(new graphPlotter(nodeList));
        frame.setVisible(true);
    }





    public static List<String> GreedySearch(myNode start, myNode goal){
        PriorityQueue<myNode> openedList = new PriorityQueue<>(Comparator.comparingDouble(n->n.h));
        Map<myNode, myNode> parentMap = new HashMap<>();
        Set<myNode> visitedList = new HashSet<>();
        start.h = calculateH(start,goal);

        openedList.add(start);
        while(!openedList.isEmpty()){
            myNode current = openedList.poll();
            if(current == goal){
                int totalCost =0;
                List<String> path = new ArrayList<>();
                while(current!=null){
                    path.add(current.name);
                    myNode parent = parentMap.get(current);
                    if(parent!=null){
                        for(edge ed:parent.child){
                            if(ed.target == current){
                                totalCost += ed.cost;
                                break;
                            }
                        }
                    }
                    current = parent;
                    //System.out.println("new path found "+current);
                }
                totalGreedDist = totalCost;
                Collections.reverse(path);
                return path;
            }
            for(edge ed :current.child){
                if(!visitedList.contains(ed.target)){
                    parentMap.put(ed.target,current);
                    ed.target.h = calculateH(ed.target,goal);

                    openedList.add(ed.target);
                }
            }
            visitedList.add(current);

        }
        return null;
    }

    public static List<String> AStarSearch(myNode start, myNode goal){
        Map<myNode, Integer> NewG = new HashMap<>();
        PriorityQueue<myNode> openedList = new PriorityQueue<>(Comparator.comparingDouble(n->n.f));
        Map<myNode, myNode> parentMap = new HashMap<>();
        Set<myNode> visitedList = new HashSet<>();
        start.h = calculateH(start,goal);
        NewG.put(start,0);
        start.f = start.h + NewG.get(start);
        openedList.add(start);

        while(!openedList.isEmpty()){
            myNode current = openedList.poll();
            if(current == goal){
                List<String> path = new ArrayList<>();
                while(current!=null){
                    path.add(current.name);
                    current = parentMap.get(current);
                    //System.out.println("new path found "+current.toString());
                }
                Collections.reverse(path);
                totalstarDist = NewG.get(goal);
                return path;
            }
            for(edge ed :current.child){
                myNode neighbor = ed.target;
                int tentativeG = NewG.get(current) + ed.cost;
                if(visitedList.contains(neighbor)) continue;
                if(!NewG.containsKey(neighbor) || tentativeG < NewG.get(neighbor) ){
                    NewG.put(neighbor,tentativeG);
                    parentMap.put(ed.target,current);
                    neighbor.h = calculateH(neighbor,goal);
                    neighbor.f = neighbor.h + NewG.get(neighbor);
                    //System.out.println("f is "+neighbor.f + "for "+ neighbor.name);

                    openedList.add(ed.target);
                }
            }
            visitedList.add(current);

        }
        return null;
    }








}
