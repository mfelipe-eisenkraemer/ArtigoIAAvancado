package br.unisc.ia;

import java.util.Arrays;

/**
 * Created by MateusFelipe on 21/04/2016.
 */
public class TeamStatistics {

   private int championId;

    private int ranking;
    private int teamId;
    private String teamName;
    private float rating;
    private int redCard;
    private int yellowCard;
    private float possession;
    private int aerialWonPerGame;
    private float tacklePerGame;
    private int interceptionPerGame;
    private int foulsPerGame;
    private int offsideGivenPerGame;
    private float shotsConcededPerGame;
    private float shotsPerGame;
    private float shotOnTargetPerGame;
    private float dribbleWonPerGame;
    private int foulGivenPerGame;
    private float passSuccess;

    public TeamStatistics(){

    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getRedCard() {
        return redCard;
    }

    public void setRedCard(int redCard) {
        this.redCard = redCard;
    }

    public int getYellowCard() {
        return yellowCard;
    }

    public void setYellowCard(int yellowCard) {
        this.yellowCard = yellowCard;
    }

    public float getPossession() {
        return possession;
    }

    public void setPossession(float possession) {
        this.possession = possession;
    }

    public int getAerialWonPerGame() {
        return aerialWonPerGame;
    }

    public void setAerialWonPerGame(int aerialWonPerGame) {
        this.aerialWonPerGame = aerialWonPerGame;
    }

    public float getTacklePerGame() {
        return tacklePerGame;
    }

    public void setTacklePerGame(float tacklePerGame) {
        this.tacklePerGame = tacklePerGame;
    }

    public int getInterceptionPerGame() {
        return interceptionPerGame;
    }

    public void setInterceptionPerGame(int interceptionPerGame) {
        this.interceptionPerGame = interceptionPerGame;
    }

    public int getFoulsPerGame() {
        return foulsPerGame;
    }

    public void setFoulsPerGame(int foulsPerGame) {
        this.foulsPerGame = foulsPerGame;
    }

    public int getOffsideGivenPerGame() {
        return offsideGivenPerGame;
    }

    public void setOffsideGivenPerGame(int offsideGivenPerGame) {
        this.offsideGivenPerGame = offsideGivenPerGame;
    }

    public float getShotsConcededPerGame() {
        return shotsConcededPerGame;
    }

    public void setShotsConcededPerGame(float shotsConcededPerGame) {
        this.shotsConcededPerGame = shotsConcededPerGame;
    }

    public float getShotsPerGame() {
        return shotsPerGame;
    }

    public void setShotsPerGame(float shotsPerGame) {
        this.shotsPerGame = shotsPerGame;
    }

    public float getShotOnTargetPerGame() {
        return shotOnTargetPerGame;
    }

    public void setShotOnTargetPerGame(float shotOnTargetPerGame) {
        this.shotOnTargetPerGame = shotOnTargetPerGame;
    }

    public float getDribbleWonPerGame() {
        return dribbleWonPerGame;
    }

    public void setDribbleWonPerGame(float dribbleWonPerGame) {
        this.dribbleWonPerGame = dribbleWonPerGame;
    }

    public int getFoulGivenPerGame() {
        return foulGivenPerGame;
    }

    public void setFoulGivenPerGame(int foulGivenPerGame) {
        this.foulGivenPerGame = foulGivenPerGame;
    }

    public float getPassSuccess() {
        return passSuccess;
    }

    public void setPassSuccess(float passSuccess) {
        this.passSuccess = passSuccess;
    }

    public int getChampionId() {
        return championId;
    }

    public void setChampionId(int championId) {
        this.championId = championId;
    }

    public double[] getBinaryStatistics() {
        TeamStatistics teamStatistics = this;

        // min size is 6 because we have more than 31 rankings
        //String binaryRanking = ConvertUtils.convertToBinary(teamStatistics.getRanking(), 6);

        // min size is 4 because teams can take at least 15 red cards
        String binaryRedCards = ConvertUtils.convertToBinary(teamStatistics.getRedCard(), 5);

        // min size is 5 because teams can take at least 31 yellow cards
        String binaryYellowCards = ConvertUtils.convertToBinary(teamStatistics.getYellowCard(), 6);

        // min size is 8 because possession can be 100%
        float possessionPercentage = teamStatistics.getPossession() * 100;
        int integerPossession = Math.round(possessionPercentage);
        String binaryPossession = "";

        // Creating the intervals for each possession, with this we can separate them in groups
        if(integerPossession < 40){
            binaryPossession = "001";
        }
        else if(integerPossession >= 40 && integerPossession <50){
            binaryPossession = "010";
        }
        else if(integerPossession >= 50 && integerPossession <=60){
            binaryPossession = "011";
        }
        else if(integerPossession > 60 && integerPossession <=70){
            binaryPossession = "100";
        }
        else { // if(integerPossession > 70)
            binaryPossession = "101";
        }

        // min size is 4 because teams can take at least 15 aerial wins per game
        String binaryAerialWon = ConvertUtils.convertToBinary(teamStatistics.getAerialWonPerGame(), 5);

        // min size is 5 because teams can take at least 31 tackles
        int integerTacklePerGame = Math.round(teamStatistics.getTacklePerGame());
        String binaryTacklePerGame = ConvertUtils.convertToBinary(integerTacklePerGame, 6);

        // min size is 5 because teams can take at least 31 interceptions
        int integerInterceptionPerGame = Math.round(teamStatistics.getInterceptionPerGame());
        String binaryInterceptionPerGame = ConvertUtils.convertToBinary(integerInterceptionPerGame, 6);

        // min size is 5 because teams can take at least 31 fouls
        int integerFoulsPerGame = Math.round(teamStatistics.getInterceptionPerGame());
        String binaryFoulsPerGame = ConvertUtils.convertToBinary(integerFoulsPerGame, 6);

        // min size is 4 because teams can take at least 15 offsides
        String binaryOffsideGivenPerGame = ConvertUtils.convertToBinary(teamStatistics.getOffsideGivenPerGame(), 5);

        // min size is 5 because teams can take at least 31 shots conceded per game
        int integerShotsConcededPerGame = Math.round(teamStatistics.getShotsConcededPerGame());
        String binaryShotsConcededPerGame = ConvertUtils.convertToBinary(integerShotsConcededPerGame, 6);

        // min size is 5 because teams can take at least 31 shots per game
        int integerShotsPerGame = Math.round(teamStatistics.getShotsPerGame());
        String binaryShotsPerGame = ConvertUtils.convertToBinary(integerShotsPerGame, 6);

        // min size is 5 because teams can take at least 31 shots on target per game
        int integerShotOnTargetPerGame = Math.round(teamStatistics.getShotOnTargetPerGame());
        String binaryShotOnTargetPerGame = ConvertUtils.convertToBinary(integerShotOnTargetPerGame, 6);

        // min size is 4 because teams can take at least 15 offsides
        int integerDribbleWonPerGame = Math.round(teamStatistics.getDribbleWonPerGame());
        String binaryDribbleWonPerGame = ConvertUtils.convertToBinary(integerDribbleWonPerGame, 5);

        // min size is 5 because teams can take at least 31 shots on target per game
        int integerFoulGivenPerGame = Math.round(teamStatistics.getFoulGivenPerGame());
        String binaryFoulGivenPerGame = ConvertUtils.convertToBinary(integerFoulGivenPerGame, 6);

        // min size is 8 because possession can be 100%
        float passSuccessPercentage = teamStatistics.getPassSuccess() * 100;
        int integerPassSuccess = Math.round(passSuccessPercentage);
        String binaryPassSuccess = "";

        // Creating the intervals for pass success, with this we can separate them in groups
        if(integerPassSuccess < 70){
            binaryPassSuccess = "001";
        }
        else if(integerPassSuccess >= 70 && integerPassSuccess < 80){
            binaryPassSuccess = "010";
        }
        else if(integerPassSuccess >= 80 && integerPassSuccess < 85){
            binaryPassSuccess = "011";
        }
        else if(integerPassSuccess >= 85 && integerPassSuccess < 90){
            binaryPassSuccess = "100";
        }
        else { // if(integerPassSuccess >= 90
            binaryPassSuccess = "101";
        }


        double[] finalSet = {};

        //finalSet = ConvertUtils.convertBinaryStringToDoubleArray(finalSet, binaryRanking);
        finalSet = ConvertUtils.convertBinaryStringToDoubleArray(finalSet, binaryRedCards);
        finalSet = ConvertUtils.convertBinaryStringToDoubleArray(finalSet, binaryYellowCards);
        finalSet = ConvertUtils.convertBinaryStringToDoubleArray(finalSet, binaryPossession);
        finalSet = ConvertUtils.convertBinaryStringToDoubleArray(finalSet, binaryAerialWon);
        finalSet = ConvertUtils.convertBinaryStringToDoubleArray(finalSet, binaryTacklePerGame);
        finalSet = ConvertUtils.convertBinaryStringToDoubleArray(finalSet, binaryInterceptionPerGame);
        finalSet = ConvertUtils.convertBinaryStringToDoubleArray(finalSet, binaryFoulsPerGame);
        finalSet = ConvertUtils.convertBinaryStringToDoubleArray(finalSet, binaryOffsideGivenPerGame);
        finalSet = ConvertUtils.convertBinaryStringToDoubleArray(finalSet, binaryShotsConcededPerGame);
        finalSet = ConvertUtils.convertBinaryStringToDoubleArray(finalSet, binaryShotsPerGame);
        finalSet = ConvertUtils.convertBinaryStringToDoubleArray(finalSet, binaryShotOnTargetPerGame);
        finalSet = ConvertUtils.convertBinaryStringToDoubleArray(finalSet, binaryDribbleWonPerGame);
        finalSet = ConvertUtils.convertBinaryStringToDoubleArray(finalSet, binaryFoulGivenPerGame);
        finalSet = ConvertUtils.convertBinaryStringToDoubleArray(finalSet, binaryPassSuccess);
        return finalSet;
    }


}
