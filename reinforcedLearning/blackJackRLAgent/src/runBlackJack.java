public class runBlackJack {
    public static void main(String[] args){
        environment env = new environment();

        QLearn agent = new QLearn(0.1,0.9,0.1);
        if(!agent.load()) {
            System.out.println("Not loaded so training and saving :");

            for (int ep = 0; ep < 50000; ep++) {
                env.reset();
                System.out.println("New ep " + ep);
                int[] state = env.getState();
                int totReward = 0;
                while (true) {
                    int action = agent.SelectAction(state);
                    environment.nextStepResult res = env.nextStep(action);
                    agent.update(state, action, res.reward, res.nextState);
                    totReward += res.reward;
                    if (res.done) break;
                    state = res.nextState;
                }

                System.out.println("Total reward in this episode is : " + totReward + " | Player Sum: " + state[0] + " | Dealer Sum: " + state[1]);
            }


            agent.save();
            System.out.println("Training finished and saved");
        }else{
            System.out.println("Loaded :");

        }

        System.out.println("printing Q table:");
        agent.visualizeQ();
    }
}
