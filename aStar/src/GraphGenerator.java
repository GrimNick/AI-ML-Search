import java.util.*;

public class GraphGenerator {
    static Random rand = new Random();
    private static boolean isBidirectional = false;
    private static boolean isRandomEdge = false;
    static String generateCityName(){
        String[] syllables = {"ba", "ra", "da", "na", "mo", "lo", "ti", "si", "ga", "re"};
        return syllables[rand.nextInt(syllables.length)] + syllables[rand.nextInt(syllables.length)];
    }
    static double randomCord(){
        return -1000 + (2000) *rand.nextDouble();
    }

    public static void setIsBidirectional(){
        isBidirectional=true;
    }

    public static void setIsRandomEdge(){
        isRandomEdge=true;
    }


    static boolean edgeExists(myNode pointA,myNode pointB){
        for(edge e: pointA.child){
            if(e.target == pointB){
                return true;
            }
        }
        for (edge e : pointB.child) {
            if (e.target == pointA) {
                return true;
            }
        }

        return false;
    }

    public static double calculateDistance(myNode A, myNode B){
        double x1= A.x;
        double y1= A.y;
        double x2= B.x;
        double y2= B.y;
        return Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2,2) );

    }

    public static List<myNode> generateGraph(int n,int e){
        List<myNode> graphNodes = new ArrayList<>();
        double lastX = randomCord();
        double lastY = randomCord();

        for(int i=0;i<n;i++){
            String name = generateCityName() + i;
            double x,y;
            if (i == 0) {
                x = lastX;
                y = lastY;
            } else {
                double angle = Math.random() * 2 * Math.PI;
                double radius = 200 + Math.random() * 800; // random between 3 and 5
                x = lastX + radius * Math.cos(angle);
                y = lastY + radius * Math.sin(angle);
            }
            lastX=x;
            lastY=y;
            graphNodes.add(new myNode(name,x,y));
        }
        //graph connectivity
        Set<Integer> connected = new HashSet<>();
        connected.add(0);
        while(connected.size()<n){
            n = graphNodes.size();
            int a =rand.nextInt(n);
            int b =rand.nextInt(n);
            if(connected.contains(a) && !connected.contains(b)){
                if(!isBidirectional && edgeExists(graphNodes.get(a),graphNodes.get(b))) continue;
                int cost = (int)calculateDistance(graphNodes.get(a),graphNodes.get(b));
                graphNodes.get(a).child.add(new edge(graphNodes.get(b),cost));
                if(isBidirectional) graphNodes.get(b).child.add(new edge(graphNodes.get(a),cost));
                connected.add(b);
            }
        }

        //add random extra edges
        int extraEdges=1;
        int i=0;
        int EdgesLimit=e;
        int maxAttempts = n * 1000; // prevent infinite loop
        int attempts = 0;
        if(isRandomEdge) EdgesLimit = rand.nextInt(e-1) + 1;

        while(i<n && attempts < maxAttempts){
            int a = i;
            int b = rand.nextInt(n);
            if (a != b && !edgeExists(graphNodes.get(a), graphNodes.get(b)) && extraEdges<EdgesLimit) {
                int cost = (int)calculateDistance(graphNodes.get(a),graphNodes.get(b));
                graphNodes.get(a).child.add(new edge(graphNodes.get(b),cost));
                //for bidirectional
                if(isBidirectional) graphNodes.get(b).child.add(new edge(graphNodes.get(a),cost));
                extraEdges++;
            }

            if(extraEdges>=EdgesLimit){
                if(isRandomEdge) EdgesLimit = rand.nextInt(e-1) + 1;
                i++;
                extraEdges=1;
                attempts=0;
            }
            attempts++;


        }
        return graphNodes;
    }
}
