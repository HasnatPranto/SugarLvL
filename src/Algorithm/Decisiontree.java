package Algorithm;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Decisiontree {
  static String[][] table, testTable, allData;
  static Map<String, Integer> categoryMap = new HashMap<>();
  static ArrayList<String> classes = new ArrayList<>();

  int dataRows=0, nTrainSet; static int dataColumns;
  public static Node rootNode;

  public void readData(String file) throws IOException {

      BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
      String[] arr= new String[30];
      String frow; int r=0;

      while ( bufferedReader.readLine()!= null) dataRows++;

      nTrainSet= (int) Math.round((dataRows-1)*.9);

      bufferedReader = new BufferedReader(new FileReader(file));

      while ((frow = bufferedReader.readLine())!= null){

          arr = frow.split(",");

          if(r!=0){
              categoryMap.put(arr[arr.length-1],0);
              int age = Integer.parseInt(arr[0]);
              if(age < 43) arr[0]="(< 43)";
              else arr[0] ="(> 43)";

              System.arraycopy(arr, 0, allData[r], 0, arr.length);
          }

          if(r==0) {

              allData = new String[dataRows][arr.length];
              table = new String[nTrainSet+1][arr.length];
              testTable = new String[(dataRows) - nTrainSet][arr.length];

              for (int i=0;i<arr.length;i++) {
                  testTable[r][i] = arr[i]; table[r][i]=arr[i]; allData[r][i] = arr[i];
              }
          }

          r+=1;
      }

      dataColumns=arr.length;

      bufferedReader.close();

      splitData();
  }

  public void splitData(){

      boolean[] flag= new boolean[dataRows];
      int rIndex, tindex=1; Random random= new Random();

      for(int i=0;i<nTrainSet;i++){
        rIndex= random.nextInt(dataRows);

        if(rIndex==0 || flag[rIndex]){ i--;continue;}

        else{
            for (int j=0;j<dataColumns;j++)
              table[tindex][j] = allData[rIndex][j]; tindex++;
              flag[rIndex]=true;
        }
      }
      tindex=1;

      for(int i=1;i<flag.length;i++){
          if(!flag[i]){

              for (int j=0;j<dataColumns;j++){

                  testTable[tindex][j] = allData[i][j];
              }
              tindex++;
          }
      }
     // System.out.println(dataRows+" . "+allData.length+" . "+table.length+" . "+testTable.length+" . "+dataColumns);
  }

    public double calculateTotalEntropy(Map<String, Integer> categoryMap, String[][] table){

      Map<String, Integer> copyCat= new HashMap<>(categoryMap);

      for(int i=1; i<table.length;i++)
          copyCat.put(table[i][dataColumns-1], copyCat.get(table[i][dataColumns-1])+1);

        double entropy = 0; int hCheck=0;

        for (Map.Entry<String, Integer> entry : copyCat.entrySet()) {

            double catFrequency = entry.getValue();

            if(catFrequency>0) hCheck++;

            entropy+= -((catFrequency/(table.length-1))*(Math.log(catFrequency/(table.length-1))/ Math.log(2)));

        }
        if(hCheck==1) return 0;

        return entropy;
    }

  public double calculateBranchEntropy(Map<String, Integer> catMap, double branchFreq, int rows){

      double entropy = 0;
      for (Map.Entry<String, Integer> entry : catMap.entrySet()) {

         double catFrequency = entry.getValue();

         entropy+= -((catFrequency/branchFreq)*(Math.log(catFrequency/branchFreq)/ Math.log(2)));

      }
      return entropy*(branchFreq/(rows-1));

  }

  public String[][] makeChildTable(String[][] parentTable, String branchName, int column){

      int rTRow=1,temp=1,trow = parentTable.length;

      for(int i=1; i<trow;i++){

          if(parentTable[i][column].equals(branchName)) rTRow++;
      }
      String[][] newTable= new String[rTRow][dataColumns];

      for(int i=0; i<dataColumns; i++) newTable[0][i] = parentTable[0][i];

      for(int i=1;i<trow;i++){
          if(parentTable[i][column].equals(branchName)){
              for (int j=0;j<dataColumns;j++)
                  newTable[temp][j]= parentTable[i][j];
              temp++;
          }
      }
      return newTable;
  }

  public Node expandBranches(String[][] table){

      Node n = new Node();
      int rows=table.length;
      Set<String> temp = new HashSet<>();
      Set<String> branches = new HashSet<>();
      int currentColumnNumber=0, newTColumn=1;
      double maxGain = 0.0;
      
      double totalEntropy= calculateTotalEntropy(categoryMap,table);

      if(totalEntropy==0){

          n.leaf=true;
          n.decision = table[1][dataColumns-1];
          return n;
      }

      while (currentColumnNumber< dataColumns-1){

          double featureEntropy=0;

          for(int i=1;i<rows;i++)
              branches.add(table[i][currentColumnNumber]);

          for(String branch: branches){

              double branchFrequency=0;

              Map<String, Integer> copyCat = new HashMap<>(categoryMap);

              for (int j=1;j<rows;j++){

                  if(branch.equals(table[j][currentColumnNumber])){
                      branchFrequency++;

                      copyCat.put(table[j][dataColumns-1], copyCat.get(table[j][dataColumns-1])+1);
                  }
              }

              if(Collections.frequency(copyCat.values(), 0) == copyCat.size()-1)
                  featureEntropy+=0;

              else
                  featureEntropy+= calculateBranchEntropy(copyCat,branchFrequency,rows);
              
          }
          //System.out.println("Info Gain for "+ table[0][currentColumnNumber]+": "+  d);

          if( (totalEntropy-featureEntropy) > maxGain){
              temp.clear();
              maxGain = totalEntropy-featureEntropy;
              newTColumn = currentColumnNumber;
              n.attribute = table[0][currentColumnNumber];
              n.leaf=false;

              for(String s: branches)
                  temp.add(s);
          }
          currentColumnNumber++;
          branches.clear();
      }
     // System.out.println("\nSelected Feature: "+n.attribute+"\n");
      for(String s: temp){
          String[][] reducedTable= makeChildTable(table, s, newTColumn);
          n.nodes.put(s,expandBranches(reducedTable));
      }

      return n;
  }

  public void TurnTheLights_On(Node node, int depth){

      if(node.leaf){
          System.out.print(node.decision);
      }
      else {
          depth++;
          System.out.print(node.attribute);

          for(Map.Entry<String,Node> entry : node.nodes.entrySet()){
              System.out.println("");

              for (int i=0;i<depth;i++) System.out.print("\t");

              System.out.print("--"+entry.getKey()+"--> ");

              TurnTheLights_On(entry.getValue(),depth+2);
          }
      }
  }
    public void init(String file) throws IOException {

       readData(file);

       rootNode = expandBranches(table);

       System.out.println("---The Tree--\n");

       TurnTheLights_On(rootNode,0);

        for(Map.Entry<String, Integer> entry : categoryMap.entrySet()) {
            classes.add(entry.getKey());
        }

        CrossValidation xval= new CrossValidation(testTable,dataColumns,classes,rootNode);

        xval.buildConfusionMat();

       System.out.println("*Total data= "+(allData.length-1)+", Train Data= "+(table.length-1)+", Test Data= "+(testTable.length-1));
    }

    public String getAResult(){
        CrossValidation xval= new CrossValidation(testTable,dataColumns,classes,rootNode);
        return xval.getAResult();

    }

}
