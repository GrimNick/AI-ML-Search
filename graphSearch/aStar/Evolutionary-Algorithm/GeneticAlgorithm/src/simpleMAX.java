import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

public class simpleMAX {

    //Maximizing function

    //Simply count 1's
    public static int fitness(String pop){
        int count=0;
        for(int i =0;i<pop.length()-1;i++){
            if(pop.charAt(i)=='1') count++;
        }
        return count;
    }
    //random gen
    //Current size is 20
    static int MAXPopLength = 20;
    static int MAXPopSize = 6;
    public static String generatePop(){
        String Temp = "";
        List a = new ArrayList();
        Random random = new Random();
        for(int i =0;i<MAXPopLength;i++){
            a.add(random.nextInt(2));
        }

        return a.toString();
    }


    public static String filterNumOnly(String pop){
        return pop.replaceAll("[,\\[\\]\\s+]","");

    }
    //Reproduction
    public static String reproduce(String pop1,String pop2){
        //System.out.println(pop1.substring())
        String pop1Filtered = filterNumOnly(pop1);
        String pop2Filtered = filterNumOnly(pop2);
        return (pop1Filtered.substring(0,(MAXPopLength/2) )
                + pop2Filtered.substring(MAXPopLength/2 ) );
    }


    //random mutations
    public static String mutate(String pop1){
        Random rand = new Random();
        int numToMutate = rand.nextInt(20);
        String popFiltered = filterNumOnly(pop1);
        char MutateChar = popFiltered.charAt(numToMutate);
        List MutateBuilder= new ArrayList();

        for(int i =0;i<popFiltered.length();i++){
            if(i==numToMutate) {
                if(MutateChar == '1') MutateBuilder.add('0');
                else MutateBuilder.add('1');
            }else {
                MutateBuilder.add(popFiltered.charAt(i));
            }
        }
        String Mutated = MutateBuilder.toString();
        System.out.println("mutated position " + numToMutate+ " | "+ pop1 + " -> " +Mutated + " " + Mutated.length());
        return Mutated;
    }

    public static int lottery(int rand1){
        int a;
        if(rand1 < MAXPopSize * 0.3){
            a = 0;
        }else if(rand1>= MAXPopSize * 0.3 && rand1 < MAXPopSize * 0.60) a = 1;

        else if(rand1>= MAXPopSize * 0.45 && rand1 < MAXPopSize * 0.65) a = 2;
        else if(rand1>= MAXPopSize * 0.65 && rand1 < MAXPopSize * 0.80) a = 3;
        else if(rand1>= MAXPopSize * 0.80 && rand1 < MAXPopSize * 0.90) a = 4;
        else if(rand1>= MAXPopSize * 0.90 && rand1 < MAXPopSize * 0.95) a = 5;
        else a = 6;

        return a;
    }


    public static void main(String[] args){
        //Make a vector or map of fitness and pop number
        String[] popn = new String[MAXPopSize];
        System.out.println("Populations are: ");
        for(int i=0;i<MAXPopSize;i++) {
            popn[i] = generatePop();
            System.out.println("Population no. "+(i+1)+"=" + popn[i]);
        }

        boolean ConvergedFitness = false;
        int lastFitness = 0;
        Map<Integer, Integer> fitPopMap = new HashMap<>();

        int MAXGEN = 100;
        int iterGen = 0;

        int MAXMATCH = 10;
        int iterMatch = 0;

        while(true) {
            int sumOfFitness = 0;
            fitPopMap.clear();

            for(int i=0;i<MAXPopSize;i++){
                int fitnessScore = fitness(popn[i]);
                sumOfFitness += fitnessScore;
                fitPopMap.put(i,fitnessScore);
            }

            //Convergence check

            if(sumOfFitness == lastFitness) {
                ConvergedFitness = true;
                iterMatch++;
            }else if(iterGen > MAXGEN){
                break;
            }
            if(iterMatch > MAXMATCH) break;

            iterGen++;

            lastFitness = sumOfFitness;


            Map<Integer,Integer> OrderedMap = fitPopMap.entrySet()
                    .stream()
                    .sorted(Map.Entry.<Integer,Integer>comparingByValue().reversed()) // or comparingByKey()
                    .collect(Collectors.toMap(
                            Map.Entry :: getKey,
                            Map.Entry :: getValue,
                            (e1,e2) -> e1,
                            LinkedHashMap::new
                    ));


            System.out.println("*------------ Ordering population --------*");
            OrderedMap.forEach((id,fitness) ->
                            System.out.println("ID: " + id + ", Fitness: " + fitness));

            //Selection

            Set<Integer> mySet = OrderedMap.keySet();
            List<Integer> myOrderedList ;
            myOrderedList = new ArrayList<>(mySet);
            System.out.println("Ids in ordered position : "+myOrderedList );

            String[] popTemp = new String[MAXPopSize];

            //crossing for reproduction
            //keeping the best to next gen
            popTemp[0] = reproduce(popn[myOrderedList.get(0)],popn[myOrderedList.get(0)]);
            Random rand = new Random();
            for(int i =1; i<MAXPopSize;i++) {

                int rand1 = rand.nextInt(MAXPopSize);
                int rand2 = rand.nextInt(MAXPopSize);
                int a,b;
                a = lottery(rand1);
                b = lottery(rand2);
                popTemp[i] = reproduce(popn[myOrderedList.get(a)], popn[myOrderedList.get(b)]);
                System.out.println("reproducing :\n"+ (a+1) +" | "+(b+1)+" new pop for "+(i+1)+" = "+ popTemp[i]);
            }

            for(int i =1; i<MAXPopSize;i++) popn[i] = popTemp[i];




            double chanceMutate = rand.nextDouble();
            if(chanceMutate<0.1) {
                int randIndex = rand.nextInt(MAXPopSize);
                System.out.println("Mutating pop no."+(randIndex+1) +": ");
                mutate(popn[randIndex]);
            }
        }

        System.out.println("*--------Final population by genetic algorithm------------*");
        for(int i=0;i<MAXPopSize;i++) {
            popn[i] = generatePop();
            System.out.println("Population no. "+(i+1)+"=" + popn[i]);
        }
        System.out.println("----Converged = "+ConvergedFitness+"--------");
        System.out.println("----Generation = "+iterGen+"--------");


    }
}
