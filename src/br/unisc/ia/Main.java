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

        JsonParser parser = new JsonParser();
        // List that contains the champion of each year and all other competitors team statistics
        List<LeagueChampion> leagueChampionList = new ArrayList<LeagueChampion>();

        for(int i = 0; i < args.length; i++) {

            JsonObject championsObject = null;
            try {
                championsObject = (JsonObject) parser.parse(new FileReader(args[i]));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            // Sets Champion id
            LeagueChampion leagueChampion = new LeagueChampion();
            JsonObject championsInfo = championsObject.get("championsInfo").getAsJsonObject();
            leagueChampion.setChampionId(championsInfo.get("championId").getAsInt());

            for (int x=0; x < championsObject.get("teamTableStats").getAsJsonArray().size(); x++)
            {
                JsonObject team = championsObject.get("teamTableStats").getAsJsonArray().get(x).getAsJsonObject();

                // Sets participant team attributes
                TeamStatistics teamStatistics = new TeamStatistics();

                teamStatistics.setRanking(team.get("ranking").getAsInt());
                System.out.println(teamStatistics.getRanking());

                teamStatistics.setTeamId(team.get("teamId").getAsInt());
                System.out.println(teamStatistics.getTeamId());

                teamStatistics.setTeamName(team.get("teamName").getAsString());
                System.out.println(teamStatistics.getTeamName());

                teamStatistics.setRating(team.get("rating").getAsFloat());
                teamStatistics.setRedCard(team.get("redCard").getAsInt());
                teamStatistics.setYellowCard(team.get("yellowCard").getAsInt());
                teamStatistics.setPossession(team.get("possession").getAsFloat());
                teamStatistics.setAerialWonPerGame(team.get("aerialWonPerGame").getAsInt());
                teamStatistics.setTacklePerGame(team.get("tacklePerGame").getAsFloat());
                teamStatistics.setInterceptionPerGame(team.get("interceptionPerGame").getAsInt());
                teamStatistics.setFoulsPerGame(team.get("foulsPerGame").getAsInt());
                teamStatistics.setOffsideGivenPerGame(team.get("offsideGivenPerGame").getAsInt());
                teamStatistics.setShotsConcededPerGame(team.get("shotsConcededPerGame").getAsFloat());
                teamStatistics.setShotsPerGame(team.get("shotsPerGame").getAsFloat());
                teamStatistics.setShotOnTargetPerGame(team.get("shotOnTargetPerGame").getAsFloat());
                teamStatistics.setDribbleWonPerGame(team.get("dribbleWonPerGame").getAsFloat());
                teamStatistics.setFoulsPerGame(team.get("foulGivenPerGame").getAsInt());
                teamStatistics.setPassSuccess(team.get("passSuccess").getAsInt());

                // Add the participant team to the list in the champion object
                leagueChampion.addTeamStatistics(teamStatistics);

            }
            // Add the chamion object to the global list
            leagueChampionList.add(leagueChampion);
        }

        Backpropagation neuralNet = new Backpropagation(78,12);
        DataSet trainDataSet = new DataSet(78,12);

        double[] trainSet;
        double[] trainOutput;

        // Iterate over all champions to generate binary entries to neural net
        for(int i = 0; i < leagueChampionList.size(); i++) {

            trainOutput = leagueChampionList.get(i).getChampionIdInBinary();
            System.out.println(trainOutput);

            for(int x = 0; x < leagueChampionList.get(i).getTeamStatisticsList().size(); x++) {
                TeamStatistics teamStatistics = leagueChampionList.get(i).getTeamStatisticsList().get(x);
                trainSet = teamStatistics.getBinaryStatistics();
                System.out.println(trainOutput);

                try {
                    trainDataSet.Add(new DataSetObject(trainSet, trainOutput));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            neuralNet.Learn(trainDataSet);

            Export.DataSet(trainDataSet, "D:\\Programas\\Desenv\\DefaultProjects\\ArtigoIAAvancado\\src\\br\\unisc\\ia\\arquivos\\trainingset.json");
            Export.NeuralNetworkStructure(neuralNet, "D:\\Programas\\Desenv\\DefaultProjects\\ArtigoIAAvancado\\src\\br\\unisc\\ia\\arquivos\\netStructure.json");
            Export.KnowledgeBase(neuralNet, "D:\\Programas\\Desenv\\DefaultProjects\\ArtigoIAAvancado\\src\\br\\unisc\\ia\\arquivos\\netKnowledge.json");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
