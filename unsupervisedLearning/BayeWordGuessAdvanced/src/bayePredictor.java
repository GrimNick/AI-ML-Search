import java.util.*;
import java.io.*;
public class bayePredictor {
    public static void main(String[] args) throws Exception {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("model.ser"));
        Map<String, Map<String, Integer>> contextNext = (Map<String, Map<String, Integer>>) in.readObject();
        Map<String, Integer> nextWordGlobalCounts = (Map<String, Integer>) in.readObject();
        int totalNextWords = (int) in.readObject();
        in.close();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type a phrase to get bayes predictions:");
        while (true) {
            System.out.print(">");
            String input = scanner.nextLine().toLowerCase().trim();
            if (input.equalsIgnoreCase("exit")) break;
            //List<String> matches = getMatchingContexts(input,contextNext);
//            if(matches.isEmpty()){
//                System.out.println("No matches for "+input);
//                continue;
//            }
            List<String> matchContext = BackOff(input);
//            String matchContext = findBest(input,contextNext);
//            if (matchContext.isEmpty()) {
//                System.out.println("No matches for " + input);
//                continue;
//            }
//
//            if(matchContext ==null){
//                System.out.println("No matches for "+input);
//                continue;
//            }
            //bnayesian scoring P(H|E) ~ P(E|H) * P(H)

            Map<String, Double> bayeScore = new HashMap<>();
//            Map<String,Integer> nextWords = contextNext.get(input);
            boolean found = false;
            int decay = 0;
            for (String context : matchContext) {
                if (!contextNext.containsKey(context)) continue;
                found = true;
                Map<String, Integer> nextWords = contextNext.get(context);
                System.out.println("Next words for '" + context + "': " + nextWords.keySet());

                int totalInContext = nextWords.values().stream().mapToInt(Integer::intValue).sum();

                for (Map.Entry<String, Integer> e : nextWords.entrySet()) {
                    String word = e.getKey();
                    int contextCount = e.getValue();
                    double P_E_Given_H = (double) contextCount / totalInContext;
                    double pH = (double) nextWordGlobalCounts.getOrDefault(word, 0) / totalNextWords;
                    double score = P_E_Given_H * pH;

                    if (decay <2) score *= 200; // Highest boost for most specific context
                    else score *= Math.pow(0.5, decay);
                    decay++;

                    bayeScore.put(word, bayeScore.getOrDefault(word, 0.00) + score);
                }
                //decay++;
            }

            if(!found){
                System.out.println("No context found for: " + input);
                continue;

            }


                List<Map.Entry<String, Double>> toSort = new ArrayList<>(bayeScore.entrySet());
                toSort.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

                for (int i = 0; i < Math.min(5, toSort.size()); i++) {
                    Map.Entry<String, Double> en = toSort.get(i);
                    System.out.println(en.getKey() + " | chance : " + (en.getValue() * 100) + "%");
                }


            }
            scanner.close();

        }


//    static String findBest(String input, Map<String, Map<String, Integer>> model) {
//        String[] tokens = input.split("\\s+");
//        for (int i = 0; i < tokens.length; i++) {
//            String sub = String.join(" ", Arrays.copyOfRange(tokens, i, tokens.length));
//            if (model.containsKey(sub)) {
//                return sub;
//            }
//        }
//        return null;
//    }

    // Generate back-off contexts: longest to shortest
    // Generate back-off contexts from longest (4-gram) to shortest (unigram)
// Generate back-off contexts from longest n-gram to unigram
    static List<String> BackOff(String input) {
        String[] tokens = input.trim().split("\\s+");
        List<String> contexts = new ArrayList<>();

        // Only generate suffixes of the phrase: last 4, 3, 2, and 1 tokens
        int maxGram = Math.min(tokens.length, 10);  // max n-gram = 4
        for (int n = maxGram; n >= 1; n--) {
            String context = String.join(" ", Arrays.copyOfRange(tokens, tokens.length - n, tokens.length));
            contexts.add(context);
        }

        return contexts; // Already in longest to shortest order
    }


//    static List<String> getMatchingContexts(String input,Map<String,Map<String,Integer>> model1){
//        List<String> matches = new ArrayList<>();
//        for(String key: model1.keySet()){
//            if(key.startsWith(input)){
//                matches.add(key);
//            }
//        }
//        return matches;
//    }


}
