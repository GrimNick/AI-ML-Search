import java.util.ArrayList;
import java.util.List;
public class MorphologicalRules {
    public static List<String> implementPrefixRules(List<String> s){
        List<String> newListString = new ArrayList<>();
        for(String toCheck:s){
            if(toCheck.startsWith("un")){
                newListString.add(toCheck.substring(2));
            }
            else if(toCheck.startsWith("re")){
                newListString.add(toCheck.substring(2));
            }
            else{
                newListString.add(toCheck);
            }

        }
        return newListString;
    }
    public static List<String> implementSuffixRules(List<String> s){
        List<String> newListString = new ArrayList<>();
        for(String toCheck:s){
            if(toCheck.endsWith("ing")){
                newListString.add(toCheck.substring(0,toCheck.length()-3));
            }
            else if(toCheck.endsWith("ed")){
                newListString.add(toCheck.substring(0,toCheck.length()-2));
            }
            else if(toCheck.endsWith("s")){
                newListString.add(toCheck.substring(0,toCheck.length()-1));
            }
            else{
                newListString.add(toCheck);
            }

        }
        return newListString;
    }


}
