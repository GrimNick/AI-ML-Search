import java.util.Random;

public class QLearn {
    String fileName="Qval.ser";
    static int state = 4;      //currently only states are 0,1,2. 2 is my final state
    //0->1, 1->3, 3->2, 1->2;
    static int action = 2;     //possible actions are 0 and 1. where 0 is not moving forward and 1 is moving forward.

    static double[][] Qval;


    public static void train(){
            Qval = new double[state][action];
            double alpha=0.1;  //this is learning rate
            double gamma=0.9;   //this is discount factor to make it look ahead for future rewards
            double epsilon=0.1;  //this is to make it explore new actions. when 1 then explores random action.
            int currentState;
            int takeAction;
            Random rand = new Random();
            int episode;
            for( episode =1;episode<1000;episode++) {
                currentState = 0;
                while (currentState != 2) {
                    if (rand.nextDouble() < epsilon){
                        takeAction = rand.nextInt(action);
                    }else{
                        takeAction = Qval[currentState][0] > Qval[currentState][1] ? 0:1;
                    }

                    int nextState = currentState;
                    int reward=0;
                    if(takeAction==1){
                        nextState = Math.min(currentState+1,2);
                    }

                    if(nextState ==2){
                        reward =10;
                    }

                    double maxQvalueNext = Math.max(Qval[nextState][0],Qval[nextState][1]);
                    Qval[currentState][takeAction] += alpha*(reward + gamma*maxQvalueNext - Qval[currentState][takeAction]);

                    currentState = nextState;


                }
            }
            int k=0;
            System.out.println("Training completed:");

            for(int i=0;i<state;i++){
                System.out.print("State:"+i+"\t");

                for(int j=0;j<action;j++){
                    System.out.print("Step: "+j+"->  Q:"+Qval[i][j]+"\t,\t" );

                }
                System.out.println();
            }
    }

    public static void main(String[] args){
            train();
            System.out.println("New testing in episodes :");
            for(int ep=0;ep<5;ep++){
                int curstate =0;
                int nextAction;
                int totreward =0;
                System.out.println("episode :" +ep);
                while(curstate!=2){
                    nextAction = Qval[curstate][0]>Qval[curstate][1] ? 0:1;
                    System.out.print("State : " + curstate +" -> ");

                    if(nextAction == 1){
                        curstate = Math.min(curstate+1,2);
                    }
                    int reward = (curstate == 2) ? 10:0;

                    System.out.println(curstate );
                    totreward +=reward;


                }
                System.out.println("Total reward = "+totreward);
            }
    }


}
