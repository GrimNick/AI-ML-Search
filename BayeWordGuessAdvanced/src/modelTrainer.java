import java.io.*;
import java.util.*;

public class modelTrainer {
    public static void main(String[] args) throws Exception{
        BufferedReader reader = new BufferedReader(new FileReader("data.txt"));

        //kati ota word herne for context significance
        int contextSize =10;

        String line;
        Map<String, Map<String,Integer>> contextNext = new HashMap<>();
        //P(H)
        Map<String,Integer> globalNextCount = new HashMap<>();
        int totalNextWords = 0;
        while((line = reader.readLine() )!= null){
            line = line.toLowerCase().replaceAll("[\"',:;()“”‘’]", "").trim();  // clean punctuation
            if(line.isEmpty()) continue;

            String[] words = line.split("\\s+");
            if(words.length < 2) continue;
            for(int i =0;i < words.length-1; i++) {
                int maxN = Math.min(contextSize, i + 1);
                for (int n = 1; n <= maxN; n++) {


                    StringBuilder contextBuild = new StringBuilder();
                    for (int j = i - n + 1; j <= i; j++) {
                        contextBuild.append(words[j]);
                        if (j < i) contextBuild.append(" ");
                    }
                    String context = contextBuild.toString();
                    String nextWord = words[i + 1];
                    //update mapping for context
                    contextNext.computeIfAbsent(context, k -> new HashMap<>()).merge(nextWord, 1, Integer::sum);
                    globalNextCount.merge(nextWord, 1, Integer::sum);
                    totalNextWords++;

                }
            }
        }
        reader.close();


        //save models
        ObjectOutputStream out = new ObjectOutputStream((new FileOutputStream("model.ser")));
        out.writeObject((contextNext)); //P(E|H)
        out.writeObject(globalNextCount); //P(H)
        out.writeObject(totalNextWords); //normalization
        out.close();
        System.out.println("Model trained and saved.");

    }
}
