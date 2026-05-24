package com.practice;

import java.util.List;
import java.util.stream.Collectors;

public class WorkerBee {
    private final SteamAPI api;

    public WorkerBee(SteamAPI api) {
        this.api = api;
    }
    public PlayerStat getPlayer(String steamID){
        return Parser.account(api.getProfileInfo(steamID));
    }
    public List<Game> getPlayedGames(String steamID){
        return Parser.gamesOwned(api.getGamesInfo(steamID))
                .stream()
                .filter(e->e.getTimePlayed()>0)
                .collect(Collectors.toList());
    }
    public GameAchievements getAchievements(String steamID,int appID){
        return Parser.achievements(api.getGameAchievements(steamID,appID));

    }
}
