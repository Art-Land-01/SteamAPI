package com.practice;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.Map;

import static com.practice.SteamAPI.getGamesInfo;

public class AllGamesStat {
    private String gameName;
    private int timePlayed;
    private Map<String,Integer> allGames = new HashMap<String,Integer>();

    public AllGamesStat(String steamID) {
        JsonNode games = getGamesInfo(steamID);
        allGames.put("deez",1);
        allGames.put(games.path("games").path("name").asText(),games.path("playtime_forever").asInt());
        System.out.println(allGames);

    }

}
