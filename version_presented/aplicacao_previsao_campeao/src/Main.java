import ADReNA_API.Data.DataSet;
import ADReNA_API.Data.DataSetObject;
import ADReNA_API.NeuralNetwork.Backpropagation;
import ADReNA_API.Util.Export;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class Main {

    public static void main(String[] args) {

        if (args.length < 4){
            System.out.println("\nUsage: \njava -jar predictChampions.jar <intermediate_layers> <neurons_per_layers Use: x,y,z> <input/file_train_1.json> ... <input/file_train_N.json> <input/file_to_recognize.json>\n");
            return;
        }
        
        System.out.println("\n---Welcome to Predict The Champion System---\n");
        // List that contains the champion of each year and all other competitors team statistics
        List<LeagueChampion> leagueChampionList = new ArrayList<LeagueChampion>();

        for(int i = 2; i < args.length-1; i++) {
            LeagueChampion leagueChampion = ConvertUtils.convertDatasetToLeagueChampion(args[i]);
            leagueChampionList.add(leagueChampion);
        }

        // Create the net layout, hidden layers and neuron at each layer
        int numberOfIntermediateLayers = Integer.valueOf(args[0]); //3
        int neuralNetLayout[] = new int[numberOfIntermediateLayers];
        
        String[] strNumberOfNeuronsPerLayer = args[1].split(",");
        int[] numberOfNeuronsPerLayer = new int[numberOfIntermediateLayers];//{4,6,5};
        
        if(strNumberOfNeuronsPerLayer.length != numberOfIntermediateLayers ){
            System.out.println("\nError: Neurons per Layers Length must be equal to the number of Intermediate Layers");
            System.out.println("\nUsage: \njava -jar predictChampions.jar <intermediate_layers> <neurons_per_layes Use: x,y,z> <file_train_1.json> ... <file_train_N.json> <file_to_recognize.json>\n");
            return;
        }
        
        int aux = 0;
        for(int i = 0; i < strNumberOfNeuronsPerLayer.length; i++) {
            numberOfNeuronsPerLayer[aux] = Integer.valueOf(strNumberOfNeuronsPerLayer[i]); 
            aux++;
        }
        
        for (int x = 0; x < numberOfIntermediateLayers ; x++){
            neuralNetLayout[x] = numberOfNeuronsPerLayer[x];
        }

        int numberOfTeamsAtEachFile = 32;
        Backpropagation neuralNet = new Backpropagation(74 * numberOfTeamsAtEachFile,12, neuralNetLayout);
        DataSet trainDataSet = new DataSet(74 * numberOfTeamsAtEachFile,12);

        double[] trainSet = new double[0];
        double[] trainOutput;

        // Iterate over all champions to generate binary entries to neural net
        for(int i = 0; i < leagueChampionList.size(); i++) {

            trainSet = new double[0];
            trainOutput = leagueChampionList.get(i).getChampionIdInBinary();

            for(int x = 0; x < leagueChampionList.get(i).getTeamStatisticsList().size(); x++) {
                TeamStatistics teamStatistics = leagueChampionList.get(i).getTeamStatisticsList().get(x);
                double[] currentSet = teamStatistics.getBinaryStatistics();
                // concatenate all the team statistics of each league
                trainSet = ConvertUtils.concatenateTwoArrays(trainSet, currentSet);
            }

            // Add the train set to the dataset
            try {
                DataSetObject dataSetObj = new DataSetObject(trainSet, trainOutput);
                trainDataSet.Add(dataSetObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            System.out.println("Training Neural Net");
            
            String projectDirectory = System.getProperty("user.dir") + File.separator + "output"  + File.separator;
            
            neuralNet.Learn(trainDataSet);

            Export.DataSet(trainDataSet, projectDirectory + "trainingset.json");
            Export.NeuralNetworkStructure(neuralNet, projectDirectory + "netStructure.json");
            Export.KnowledgeBase(neuralNet, projectDirectory + "netKnowledge.json");

            System.out.println("Neural net trained");
            System.out.println("\n--------------------------------------\n");
            System.out.println("Recognizing neural net ...\n");
            
            String recognizeArgs[] = new String[1];
            recognizeArgs[0] = args[args.length-1];
            Recognize.main(recognizeArgs);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
