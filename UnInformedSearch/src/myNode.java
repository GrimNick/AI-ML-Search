public class myNode {
    int NodeID;
    String name;
    boolean visited;


    public myNode(int id,String name){
        NodeID = id;
        this.name = name;
    }

    public void setVisited(){
        visited = true;
    }
    public void setOff(){
        visited = false;
    }

    public boolean getVisited(){
        return visited;
    }

    public String getName(){
        return name;
    }

}
