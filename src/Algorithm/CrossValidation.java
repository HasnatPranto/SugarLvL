package Algorithm;

import Data.Patient;

import java.util.ArrayList;
import java.util.Map;

public class CrossValidation {

    private String[][] testTable; int t=0;
     boolean leaf=false;
    public static String result="";
     String[] singleSample;
     ArrayList<String> classes;
    int[][] cMat;
     private int dataColumn;
     public static double accuracy,precision,recall,f1;
     static public Node node;

    public CrossValidation(String[][] testTable, int dataColumn, ArrayList classes, Node node) {

        this.testTable = testTable;
        this.dataColumn = dataColumn;
        this.classes = classes;
        this.node = node;
        cMat= new int[classes.size()][classes.size()];
        singleSample=new String[dataColumn];


    }
    public CrossValidation(){}

    public void processInfo(Node node){

        if(node.leaf){

            result = node.decision;
            leaf=true;
            return;
        }

        for(int i=0;i<dataColumn-1;i++){
            if(node.attribute.equals(testTable[0][i])){

                for (Map.Entry<String, Node> entry : node.nodes.entrySet()) {
                    String key = entry.getKey();
                    Node nord = entry.getValue();

                    if(key.equals(singleSample[i])){
                        singleSample[i]="";
                       processInfo(nord);
                        if(leaf){
                            //leaf=false;
                            return;
                        }
                    }
                }

            }
        }

    }
    public void matchMaking(Node node){

        if(node.leaf){

            result = node.decision;

            if(node.decision.equals(singleSample[dataColumn-1]))
                cMat[classes.indexOf(node.decision)][classes.indexOf(node.decision)]+=1;
            /*t++; leaf=true; return;*/
            else
                cMat[classes.indexOf(node.decision)][classes.indexOf(singleSample[dataColumn-1])]++;

            leaf=true;
            return;
        }

        for(int i=0;i<dataColumn-1;i++){
            if(node.attribute.equals(testTable[0][i])){

                for (Map.Entry<String, Node> entry : node.nodes.entrySet()) {
                    String key = entry.getKey();
                    Node nord = entry.getValue();

                    if(key.equals(singleSample[i])){
                        singleSample[i]="";
                        matchMaking(nord);
                        if(leaf){
                            //leaf=false;
                            return;
                        }
                    }
                }

            }
        }
    }

    public double calculateAccuracy(){

         accuracy=0;
           for(int i=0;i<cMat.length;i++)
               accuracy+=cMat[i][i];

           accuracy/=(testTable.length-1);
           accuracy*=100;

        return accuracy;
    }
    public double calculatePrecision(){

        precision=0; double denom;

        for(int i=0;i<cMat.length;i++){
            denom=0;
            for(int j=0;j<cMat.length;j++){
                denom+=cMat[i][j];
            }
            precision+= cMat[i][i]/denom;
        }
        precision/=classes.size();

        precision*=100;
        return precision;
    }
    public double calculateF1(){

        double denomP,denomR,precision,recall;
        f1=0;
        for(int i=0;i<cMat.length;i++){
            denomP=denomR=0;

            for(int j=0;j<cMat.length;j++){
                denomP+=cMat[i][j];
                denomR+=cMat[j][i];
            }
            precision = cMat[i][i]/denomP;
            recall = cMat[i][i]/denomR;

            f1+= 2*precision*recall/(precision+recall);
        }

        f1/=classes.size();
        f1*=100;
        return f1;
    }
    public double calculateRecall(){

        double denom;
        recall=0;

        for(int i=0;i<cMat.length;i++){
            denom=0;
            for(int j=0;j<cMat.length;j++){
                denom+=cMat[j][i];
            }
            recall+= cMat[i][i]/denom;
        }
        recall/=classes.size();

        recall*=100;
        return recall;
    }

    public void buildConfusionMat(){

        for(int i=1;i<testTable.length;i++) {
            leaf = false;
            for (int k = 0; k < dataColumn; k++) {
                singleSample[k] = testTable[i][k];
            }
            matchMaking(node);
        }
        leaf = false;
        System.out.println("\nAccuracy: "+ calculateAccuracy() +"% "+"\nPrecision: "+ calculatePrecision() +"% "+
                "\nRecall: "+ calculateRecall() +"%"+"\nF1-score: "+ calculateF1() +"%");
    }

    public String getAResult(){

        for (int i = 0; i < dataColumn; i++) {

            singleSample[i]= (Patient.symptoms[i]==null)?"" : Patient.symptoms[i];
        }
        processInfo(node);

        if(leaf){
            leaf = false;
            return result;
        }
        else return null;
    }
}
