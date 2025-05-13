import java.util.List;

public class SuffixPrefixSeparator {
    public static void main(String[] args){
        List<String> tokens = Preprocessor.processFile();
        System.out.println("tokens are:");

        for(String t:tokens){
            System.out.print(t +" ");
        }
        List<String> removedPrefix = MorphologicalRules.implementPrefixRules(tokens);
        System.out.println("\n Removed prefix:");

        for(String t:removedPrefix){
            System.out.print(t +" ");
        }

        List<String> removedSuffix = MorphologicalRules.implementSuffixRules(tokens);
        System.out.println("\n Removed prefix:");

        for(String t:removedSuffix){
            System.out.print(t +" ");
        }

        List<String> removedBoth = MorphologicalRules.implementSuffixRules(removedPrefix);
        System.out.println("\n Removed prefix and suffix:");

        for(String t:removedBoth){
            System.out.print(t +" ");
        }


    }
}
