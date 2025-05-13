import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class SuffixPrefixSeparator {
    public static void main(String[] args){
        Morfessor myMorfessor = new Morfessor();
        myMorfessor.loadModel();
        if(myMorfessor.getTotalMorphemeCount() ==0){

            System.out.println("Not trained so we will now train it");


            ArrayList<String> tokens = Preprocessor.preprocess();

//            for(String s:tokens){
//                System.out.println(s);
//            }
            System.out.print("The total number of tokens is: "+ tokens.size());


            myMorfessor.trainModel(tokens);
            myMorfessor.saveModel();

        }
        else{
            System.out.println("Already trained so skipping training");

        }

        Scanner in = new Scanner(System.in);
        myMorfessor.getTopMorphemes();

        while(true){
            System.out.println("Give a sentence. (type exit to stop the program)");
            String input = in.nextLine();
            if(input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("exits")) break;
            String[] inputArray = input.split("\\s+");
            ArrayList<String> listInput = new ArrayList<>();
            Collections.addAll(listInput,inputArray);
            myMorfessor.segment(listInput);
        }


    }
}
//i am testing on harder mathematics unhappiness clapping disquisition disqualified dongs peoples
//after disquisition are unknown new words,

//--------without resetting

//top morphemes
//e: 2407316
//s: 1557659
//t: 1482798
//d: 1235714
//o: 1104261
//th: 1011596
//n: 867248
//r: 757615
//i: 674210
//y: 666505
//f: 598991
//a: 554265
//l: 493340
//h: 468770
//an: 409438
//m: 361307
//g: 356357
//b: 175379
//w: 173440
//he: 153491



//------with resetting epoch = 1
//top morphemes
//e: 245716
//s: 150147
//t: 127725
//d: 117489
//o: 113361
//th: 95167
//r: 72631
//n: 65256
//y: 61713
//f: 53366
//a: 50206
//i: 50119
//h: 49635
//l: 44691
//an: 37171
//g: 33313
//m: 31199
//in: 20117
//b: 13909
//w: 13780
//Give a sentence. (type exit to stop the program)
//i am testing on harder mathematics unhappiness clapping disquisition disqualified dongs peoples
//i -> i
//am -> a m
//testing -> testin g
//on -> o n
//harder -> harde r
//mathematics -> mathematic s
//unhappiness -> unhappines s
//clapping -> clappin g
//disquisition -> disquisitio n
//disqualified -> disqualifie d
//dongs -> don g s
//peoples -> peopl e s