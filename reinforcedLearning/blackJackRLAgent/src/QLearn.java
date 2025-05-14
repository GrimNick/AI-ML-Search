import java.io.*;
import java.util.Random;
public class QLearn {
    private double[][][][] Q;
    private String fileName = "model.ser";
    private double alpha,gamma,epsilon;
    public QLearn(double alpha,double gamma,double epsilon){
        this.alpha = alpha;
        this.gamma = gamma;
        this.epsilon = epsilon;
        Q = new double[100][100][2][2];
    }
    public int SelectAction(int[] state){
        Random rand = new Random();
        int action;
        int playerSum = state[0];
        System.out.println(playerSum);
        int dealerSum = rand.nextInt(Math.min(state[1]/2,state[1]-2),state[1]-1) ;  //making it show only one card
        int usableAce = state[2];
        if(rand.nextDouble() < epsilon){
            action = rand.nextInt(2);
        }
        else{
            action = Q[playerSum][dealerSum][usableAce][0] > Q[playerSum][dealerSum][usableAce][1] ? 0:1;
        }
        return action;

    }
            public void update(int[] state,int action, int reward, int[] nextState){
                double maxQ = Math.max(Q[nextState[0]][nextState[1]][nextState[2]][0],Q[nextState[0]][nextState[1]][nextState[2]][1]);
                Q[state[0]][state[1]][state[2]][action] +=alpha *(reward + gamma * maxQ - Q[state[0]][state[1]][state[2]][action]);
            }

            public void save(){
                try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))){
                    out.writeObject(Q);
                    System.out.println("saved in "+ fileName);

                    out.close();
                }catch(IOException e){

                    System.out.println("Error in saving");
                }
            }
    public boolean load(){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))){
            Q = (double[][][][]) in.readObject();
            System.out.println("loaded from "+ fileName);
            return true;
        }catch(IOException e){
            System.out.println("Error in loading");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
            public void visualizeQ(){
        for(int playerSum=2;playerSum<27;playerSum++){
            for(int dealerSum=2;dealerSum<25;dealerSum++){
                for(int Ace=0;Ace<=1;Ace++) {
                    double hitQ = Q[playerSum][dealerSum][Ace][1];
                    double standQ =Q[playerSum][dealerSum][Ace][0];
                    System.out.println("PlayerSum: "+playerSum+" | Dealer Sum : "+dealerSum+" | Ace presence : "+Ace+" | Hit Q : "+hitQ+" | Stand Q : "+standQ);
                }
            }
        }
            }
}
