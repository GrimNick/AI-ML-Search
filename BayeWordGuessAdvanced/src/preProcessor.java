import java.io.*;
import java.text.BreakIterator;
import java.util.*;

public class preProcessor {
    public static void main(String[] args){
        String input = "gutenberg.txt";
        String output = "data.txt";
        try {
            StringBuilder raw = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(input));
            boolean inBook = false;
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("*** START OF")) {
                    inBook = true;
                    continue;
                } else if (line.contains("*** END OF")) {
                    inBook=false;
                }
                if (inBook) raw.append(line).append(" ");
            }
            reader.close();

            String FullRaw = raw.toString().replaceAll("\\s+"," ").replaceAll("[^a-zA-Z0-9. ]", " ").replaceAll("\\s+"," ");
            FullRaw = FullRaw.trim();
            BufferedWriter writer = new BufferedWriter(new FileWriter(output));

            BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
            iterator.setText(FullRaw);
            int start = iterator.first();
            for(int end=iterator.next();end!=BreakIterator.DONE; start=end, end = iterator.next()){
                String sentence = FullRaw.substring(start,end).trim();
                if(!sentence.isEmpty()){
                    writer.write(sentence);
                    writer.newLine();
                }
            }
            writer.close();
            System.out.println("Sentences saved to " + output);

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
