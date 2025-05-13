import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.*;
public class Morfessor {

    Map<String, Integer> morphemeCount = new HashMap<>();
    Map<String,String> segmentation = new HashMap<>();
    int totalMorphemeCount =0;
    String fileName = "model.ser";
    int maxEpoch = 30;
    double minImprovement=0.0000001;

    public void trainModel(ArrayList<String> tokens){
        Map<String,String> memo = new HashMap<>();
        //pre- initialize morpheme frequencies.
        for(String w:tokens){
            segmentation.put(w,w);

            for(int i =1;i<w.length();i++){
                String left = w.substring(0, i);
                String right = w.substring(i);
                morphemeCount.put(left, morphemeCount.getOrDefault(left, 0) + 1);
                morphemeCount.put(right, morphemeCount.getOrDefault(right, 0) + 1);
                totalMorphemeCount += 2;
            }
        }
        for(int epoch = 0;epoch<maxEpoch;epoch++) {
            if(epoch % 2 !=0  ){
//                memo.clear();
                morphemeCount.clear();
                totalMorphemeCount=0;
            }
            int changes =0;
            for (String w : tokens) {
                String bestSeg = bestMDLSegmentation(w, memo);
                if(!bestSeg.equals(segmentation.get(w))){ //maybe get or default needs use
                    changes++;
                }
                segmentation.put(w, bestSeg);
                String[] parts = bestSeg.split(" ");
                for (String morpheme : parts) {
                    morphemeCount.put(morpheme, morphemeCount.getOrDefault(morpheme, 0) + 1);
                    totalMorphemeCount++;
                }
            }

        }
        //printAllSegmentation();
        System.out.println("Training is finished with total Morphemecount : " + totalMorphemeCount);

    }


    public String bestMDLSegmentation(String word,Map<String,String> memo){
        if(word.length()<=1) return word;
        if(memo.containsKey(word)) return memo.get(word);

        double bestCost = getMorphemeCost(word);
        String bestSeg = word;
        for(int i =1;i< word.length();i++){
            String leftPart = word.substring(0,i);
            String rightPart = word.substring(i);
            String rightPartSeg = bestMDLSegmentation(rightPart,memo);
            String SegPart = leftPart + " " + rightPartSeg;
            String[] parts = SegPart.split(" ");
            double totalCost = 0;
            for(String p: parts){
                totalCost += getMorphemeCost(p);
            }
            totalCost = totalCost/word.length();
            if(totalCost<bestCost){
                bestCost = totalCost;
                bestSeg = SegPart;
            }
        }
        memo.put(word,bestSeg);
        return bestSeg;
    }

    private double getMorphemeCost(String morpheme){

        if(totalMorphemeCount==0) return 20; //this is penalty to prevent division by 0
        int morphemeCountInt =  morphemeCount.getOrDefault(morpheme,0);    //log 0 should be prevented so used.
        //lets penalize if 0, for unseen ones
        double probability = (double) (morphemeCountInt+1) / (totalMorphemeCount+morphemeCount.size());
        double lexiconPenalty = Math.log(morpheme.length()+1)/Math.log(2);
        return -(Math.log(probability)/(Math.log(2))) + lexiconPenalty;
    }
    public int getTotalMorphemeCount(){
        return totalMorphemeCount;
    }

    public void segment(ArrayList<String> userText){//maybe i can make this return list of string? for now this is fine
        for(String s: userText){
            printSegmentations(s,bestMDLSegmentation(s,new HashMap<>()));
        }
    }

    private void printAllSegmentation(){
        for(Map.Entry<String,String> s:segmentation.entrySet()){
            System.out.println(s.getKey() + " -> "+s.getValue());
        }
    }

    private void printSegmentations(String originalWord,String updatedWord){
        System.out.println(originalWord +" -> " +updatedWord);
    }

    public void getTopMorphemes(){
        morphemeCount.entrySet().stream().sorted((a,b)->b.getValue() - a.getValue()).limit(20).forEach(entry->System.out.println(entry.getKey()+": "+entry.getValue()));
    }



    public void saveModel(){
        try(PrintWriter writer = new PrintWriter(fileName)){
            for(Map.Entry<String,String> s:segmentation.entrySet()){
                writer.println("SEG\t"+s.getKey()+"\t"+s.getValue());
            }
            for(Map.Entry<String,Integer> s:morphemeCount.entrySet()){
                writer.println("MOR\t"+s.getKey()+"\t"+s.getValue());
            }

        }catch(IOException e){
            System.out.println("file name error. save model error");
        }
        System.out.println("Model has been saved as :"+fileName);

    }

    public void loadModel(){
        try(BufferedReader read = new BufferedReader(new FileReader(fileName))){
            String line;
            while((line = read.readLine() )!= null) {
                String[] parts = line.split("\t");
                if (parts.length >= 3) {
                    if(parts[0].equals("SEG"))  {

                        segmentation.put(parts[1], parts[2]);
                    }
                    else if(parts[0].equals("MOR")){
                        int count = Integer.parseInt(parts[2]);
                        morphemeCount.put(parts[1],count);
                        totalMorphemeCount+= count;
                    }

                }
            }
        }catch(IOException e){
            System.out.println("file name error. load model error");
        }
        System.out.println("Model has been loaded as :"+fileName +" with total morpheme counts :"+totalMorphemeCount);

    }
}
