package com.practice;

import java.util.*;
import java.util.stream.*;

public class WorkerBee {
    private final SteamAPI api;

    private final UserRepository save;
    private final GameRepo game;
    private final AchievementRepo achievement;

    Map<String,Integer> gameIndex = new HashMap<>();


    public WorkerBee(SteamAPI api) {
        this.api = api;
        this.save = new UserRepository();
        this.game = new GameRepo();
        this.achievement = new AchievementRepo();

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
    public void GameIDbyName(String steamID){
        List<Game> games = getPlayedGames(steamID);
        for(Game e:games)
        {
            gameIndex.put(e.getGameName().toLowerCase(),e.getGameID());
        }
    }
    public List<Game> searchGameByName (String request)
    {
        return gameIndex.entrySet()
                .stream()
                .filter(e->e.getKey().contains(request.toLowerCase()))
                .map(e->new Game(e.getValue(),e.getKey(),0))
                .collect(Collectors.toList());
    }


    public void savePlayer(String steamId){
        save.save(getPlayer(steamId),steamId);
    }
    public void trackGame(String steamID,String game_name){
        Game here = searchGameByName(game_name).get(0);

        //ОБЯЗАТЕЛЬНО НЕ ЗАБИТЬ ХУЙ И ДОБАВИТЬ ПРОВЕРКУ НА НУЛЬ И НЕСКОЛЬКО РЕЗУЛЬТАТОВ - хадес он только второй добавил

        game.save(steamID,here);
    }
    public void saveAchievements(String steamID,int gameID){
        achievement.saveAll(steamID,gameID,getAchievements(steamID,gameID));
        save.updateLastSync(steamID);
    }

}
