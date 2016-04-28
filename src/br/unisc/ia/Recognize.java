package br.unisc.ia;

import ADReNA_API.NeuralNetwork.INeuralNetwork;
import ADReNA_API.Util.Import;

import java.util.ArrayList;
import java.util.List;

public class Recognize {

    public static void main(String[] args) {

        // List that contains the champion of each year and all other competitors team statistics
        List<LeagueChampion> leagueChampionList = new ArrayList<LeagueChampion>();

        for(int i = 0; i < args.length; i++) {
            LeagueChampion leagueChampion = ConvertUtils.convertDatasetToLeagueChampion(args[i]);
            leagueChampionList.add(leagueChampion);
        }

        System.out.println("\n \n \n");

        INeuralNetwork neuralNet = null;

        try {
            String projectDirectory = System.getProperty("user.dir") + "\\src\\br\\unisc\\ia\\arquivos\\";

            neuralNet = Import.NeuralNetworkStructure(projectDirectory + "netStructure.json");
            Import.KnowledgeBase(neuralNet, projectDirectory + "netKnowledge.json");
        } catch (Exception e) {
            e.printStackTrace();
        }

        double[] testSet = new double[0];

        // Iterate over all champions to generate binary entries to neural net
        for(int i = 0; i < leagueChampionList.size(); i++) {
            for(int x = 0; x < leagueChampionList.get(i).getTeamStatisticsList().size(); x++) {
                TeamStatistics teamStatistics = leagueChampionList.get(i).getTeamStatisticsList().get(x);
                double[] currentSet = teamStatistics.getBinaryStatistics();
                // concatenate all the team statistics of each league
                testSet = ConvertUtils.concatenateTwoArrays(testSet, currentSet);
            }

            double[] resposta = new double[0];
            try {
                resposta = neuralNet.Recognize(testSet);
            } catch (Exception e) {e.printStackTrace(); }

            String binaryChampion = "";
            for (int y = 0; y < resposta.length; y++){
                binaryChampion = binaryChampion.concat( String.valueOf( Math.round(resposta[y]) ) );
                System.out.println(resposta[y]);
            }

            System.out.println("Recognized champion id is: " + Integer.parseInt(binaryChampion, 2));
        }
    }
}