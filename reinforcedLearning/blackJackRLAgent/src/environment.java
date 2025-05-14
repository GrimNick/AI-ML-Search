import java.util.Random;
public class environment {
    static int dealerSum;
    static int playerSum;
    static int usableAce;
    static Random rand = new Random();

    public void reset(){
        int randNum = rand.nextInt(13) +1;
        int randNum2 = rand.nextInt(13) +1;

        dealerSum=randNum+randNum2;
        randNum = rand.nextInt(13) +1;
        randNum2 = rand.nextInt(13) +1;

        playerSum=randNum+randNum2;
        usableAce=0;

    }

    public int[] getState(){
        return new int[] {
                playerSum,dealerSum,usableAce
        };
    }

    //supposing action =0-> stand,
    //1 -> hit

    public static nextStepResult nextStep(int action){
        nextStepResult res = new nextStepResult();
        res.nextState = new int[]{
                playerSum,dealerSum,usableAce
        };

        if(action ==1){
            boolean ace=false;
            int randNum = rand.nextInt(13);
            if(randNum==0){
                ace = true;
            }
            playerSum += randNum+1;
            int sumWithAce=0;
            if(ace) {
                sumWithAce = playerSum + 10;
                if (sumWithAce > 21) usableAce = 1;
                else usableAce=0;
            }

            if(playerSum>21 ){
                res.reward = -1;
                res.done = true;
            }

            return res;
        }

        res.done = true;

        if(playerSum>21 || playerSum<dealerSum  ){
            res.reward = -1;
        }else if(playerSum>dealerSum){
            res.reward = 1;
        }else{
            res.reward = -1;
        }

        return res;

    }


    public static class nextStepResult{
        int[] nextState;
        int reward;
        boolean done;
    }
}
