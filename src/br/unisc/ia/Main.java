package br.unisc.ia;

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

public class Main {

    public static void main(String[] args) {

        // List that contains the champion of each year and all other competitors team statistics
        List<LeagueChampion> leagueChampionList = new ArrayList<LeagueChampion>();

        for(int i = 0; i < args.length; i++) {
            LeagueChampion leagueChampion = ConvertUtils.convertDatasetToLeagueChampion(args[i]);
            leagueChampionList.add(leagueChampion);
        }

        System.out.println("\n \n \n");

        // Create the net layout, hidden layers and neuron at each layer
        int numberOfIntermediateLayers = 3;
        int neuralNetLayout[] = new int[numberOfIntermediateLayers];
        int[] numberOfNeuronsPerLayer = {4,6,5};
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
            neuralNet.Learn(trainDataSet);

            String projectDirectory = System.getProperty("user.dir") + "\\src\\br\\unisc\\ia\\arquivos\\";

            Export.DataSet(trainDataSet, projectDirectory + "trainingset.json");
            Export.NeuralNetworkStructure(neuralNet, projectDirectory + "netStructure.json");
            Export.KnowledgeBase(neuralNet, projectDirectory + "netKnowledge.json");

            System.out.print("Neural net trained\n\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
