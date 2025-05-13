import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Preprocessor {
    static String fileName = "gutenberg.txt";

    public static ArrayList<String> preprocess(){
        ArrayList<String> tokens = new ArrayList<>();

        try(BufferedReader read = new BufferedReader(new FileReader(fileName))){
            String line;
            String[] words;
            boolean inBook = false;
            while((line = read.readLine()) != null){
                if(line.contains("*** START")){
                    inBook=true;
                    continue;
                }
                if(line.contains("*** END")){
                    inBook = false;
                }
                if(inBook){
                    words = line.toLowerCase().split("\\W+");
                    for(String w:words){
                        if(!w.matches("[a-zA-Z]+")) continue; //1128366 -> 1047389 chars by adding this.
                        tokens.add(w);
                    }
                }
            }
        }catch(IOException e){
            System.out.println("input file has error");
        }


        return tokens;
    }
}
