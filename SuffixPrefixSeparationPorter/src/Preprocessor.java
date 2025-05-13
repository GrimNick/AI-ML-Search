import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Preprocessor{
    public static List<String> processFile(){
        String inputRawText = "rawText";
        ArrayList<String> tokens = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputRawText));
            String line;
            while((line=reader.readLine() )!= null){
                String tokensOfLine = line.toLowerCase().replaceAll("[^a-z\\s]","");
                String[] tokensArray = tokensOfLine.split("\\s+");
                tokens.addAll(Arrays.asList(tokensArray));
            }
            reader.close();

        } catch (IOException ex) {
        ex.printStackTrace();
        }

        return tokens;

    }

    public static void main(String[] arrs){
        List<String> tokens = processFile();
        for(String t:tokens){
            System.out.println(t);
        }
    }
}
