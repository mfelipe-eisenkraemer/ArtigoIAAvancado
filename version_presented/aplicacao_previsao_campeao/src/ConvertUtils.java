import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by MateusFelipe on 21/04/2016.
 */
public class ConvertUtils {

    public static String convertToBinary( int value, int minSize ){
        String binaryString = Integer.toBinaryString(value);
        while( binaryString.length() < minSize ){
            binaryString = "0".concat(binaryString);
        }
        return binaryString;
    }

    public static double[] convertBinaryStringToDoubleArray(double[] doubleList, String binaryString) {
        String[] listString = binaryString.split("(?!^)");
        for(int x=0;x < listString.length;x++){
            doubleList = addElementAtEndOfArray(doubleList, Integer.valueOf(listString[x]));
        }

        return doubleList;
    }

    private static double[] addElementAtEndOfArray(double[] arrayToCopy, int value) {
        double[] result = Arrays.copyOf(arrayToCopy, arrayToCopy.length + 1);
        result[result.length - 1] = value;
        return result;
    }

    public static LeagueChampion convertDatasetToLeagueChampion(String filePath) {
        JsonParser parser = new JsonParser();

        JsonObject championsObject = null;
        try {
            championsObject = (JsonObject) parser.parse(new FileReader(filePath));
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
            //System.out.println(teamStatistics.getRanking());

            teamStatistics.setTeamId(team.get("teamId").getAsInt());
            //System.out.println(teamStatistics.getTeamId());

            teamStatistics.setTeamName(team.get("teamName").getAsString());
            //System.out.println("TEAM:" + teamStatistics.getTeamId() + "--" + teamStatistics.getTeamName());

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

        return leagueChampion;
    }

    public static double[] concatenateTwoArrays(double[] a, double[] b) {
        int aLen = a.length;
        int bLen = b.length;
        double[] c= new double[aLen+bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }
}
