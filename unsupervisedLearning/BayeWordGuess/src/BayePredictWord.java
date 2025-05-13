import java.util.*;

public class BayePredictWord {
    static String[] trainingData = {
            "I am happy",
            "I am sad",
            "I am excited",
            "I am tired",
            "I am a student",
            "I am a teacher",
            "I am working",
            "I am working",
            "I am working",
            "I am a teacher",
            "I am a teacher",

            "I am learning"

    };
    //for prior P(H)
    static Map<String,Integer> wordsCount = new HashMap<>(); //posterior
    //for context specific frequency P(E|H) how often "I am" occurs before the word
    static Map<String,Integer> likelihoodGivenContext = new HashMap<>();
    //for prioir P(H)

    static int totalCount =0;

    //for total "i am " appears
    static int contextTotal =0;
    public static void main(String[] args){
        trainModel();
        List<Map.Entry<String,Double>> predictions = wordPredict(5);
        System.out.println("predictions for sentence 'I am' is ");
        for(Map.Entry<String,Double> entry : predictions){
            double probability = entry.getValue();
            System.out.println("The probability of word "+entry.getKey()+" is "+ entry.getValue() *100);
        }
    }

    static void trainModel(){
        for(String sentence : trainingData){
            sentence=sentence.trim().toLowerCase();
            if(sentence.startsWith("i am")) {
                String[] words;
                words = sentence.split("\\s+");
                if(words.length >=3){
                    String nextWord = words[2];
                    if(words.length >= 4){
                        nextWord = words[2] + " "+words[3];
                    }
                    wordsCount.put(nextWord,wordsCount.getOrDefault(nextWord,0)+1);
                    totalCount++;
                    likelihoodGivenContext.put(nextWord,likelihoodGivenContext.getOrDefault(nextWord,0)+1);

                }
                contextTotal++;
            }
        }
    }

    static List<Map.Entry<String,Double>> wordPredict(int limit){
        Map<String,Double> posterior = new HashMap<>();
        for(String word: likelihoodGivenContext.keySet()){
            int likelihood = likelihoodGivenContext.getOrDefault(word,0);
            int prior = wordsCount.getOrDefault(word,0);
            //bayes : P(H|E) ~ P(E|H) * P(H)
            double pE_given_H = (double) likelihood/contextTotal;
            double pH = (double) prior/totalCount;
            double posteriorProb = pE_given_H *pH;
            posterior.put(word,posteriorProb);
        }
        List<Map.Entry<String,Double>> words = new ArrayList<>(posterior.entrySet());
        words.sort((a,b) -> Double.compare(b.getValue(),a.getValue()));
        return words.subList(0,Math.min(limit,words.size()));

    }


}
