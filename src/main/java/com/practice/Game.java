package com.practice;

public class Game {
    private int gameID,timePlayed;
    private String gameName;

    public int getGameID() {
        return gameID;
    }

    public int getTimePlayed() {
        return timePlayed;
    }

    public String getGameName() {
        return gameName;
    }

    public Game(int gameID, String gameName, int timePlayed){
        this.gameID = gameID;
        this.gameName = gameName;
        this.timePlayed = timePlayed;
    }

}
