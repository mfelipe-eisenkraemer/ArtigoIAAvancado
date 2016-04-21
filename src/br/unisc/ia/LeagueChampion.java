package br.unisc.ia;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MateusFelipe on 21/04/2016.
 */
public class LeagueChampion {

    private int championId;
    private List<TeamStatistics> teamStatisticsList;

    public LeagueChampion(){
        this.teamStatisticsList = new ArrayList<TeamStatistics>();
    }

    public void addTeamStatistics( TeamStatistics teamStatistics ){
        this.teamStatisticsList.add(teamStatistics);
    }

    public int getChampionId() {
        return championId;
    }

    public void setChampionId(int championId) {
        this.championId = championId;
    }

    public List<TeamStatistics> getTeamStatisticsList() {
        return teamStatisticsList;
    }

    public void setTeamStatisticsList(List<TeamStatistics> teamStatisticsList) {
        this.teamStatisticsList = teamStatisticsList;
    }

    public double[] getChampionIdInBinary() {
        // 12 is the minimum size, because there is a teamid = 2576
        String binaryString = ConvertUtils.convertToBinary(this.getChampionId(), 12);
        return new double[ Integer.valueOf(binaryString) ];
    }
}
