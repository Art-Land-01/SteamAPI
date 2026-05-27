package com.practice;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.*;

public class Parser {
    public static PlayerStat account(JsonNode base){
        JsonNode data = base.path("response").path("players").get(0);
        return new PlayerStat(
                data.path("personaname").asText("Имя не найдено"),
                data.path("avatarfull").asText("not found"),
                data.path("personastate").asInt(),
                data.path("lastlogoff").asInt(),
                data.path("gameextrainfo").asText()
        );
    }
    public static GameAchievements achievements (JsonNode base)
    {
        JsonNode data = base.path("playerstats").path("achievements");
        List<GameAchievements.Achievement> achievements = new ArrayList<>();
        GameAchievements ass = new GameAchievements(achievements);
        ass.setGameName(base.path("playerstats").path("gameName").asText());

        for(JsonNode a:data)
        {
            achievements.add
                    (
                    new GameAchievements.Achievement(
                                    a.path("apiname").asInt(),
                            a.path("name").asText(),
                            a.path("description").asText(),
                            a.path("achieved").asBoolean())


            );
        }
        return ass;
    }

    //в запросе
    //steamid
    //64 bit Steam ID to return achievement list for.
    //appid
    //The ID for the game you're requesting
    //l (Optional)
    //Language. If specified, it will return language data for the requested language.

    //в ответе
    //A list of achievements.
    //apiname
    //The API name of the achievement
    //achieved
    //Whether or not the achievement has been completed.
    //unlocktime
    //Date when the achievement was unlocked.
    //name (optional)
    //Localized achievement name
    //description (optional)
    //Localized description of the achievement



    public static List<Game> gamesOwned(JsonNode base){

        JsonNode data = base.path("response").path("games");
        List<Game> games = new ArrayList<>();

        for(JsonNode a:data)
        {
            games.add(
                    new Game(
                            a.path("appid").asInt(),
                            a.path("name").asText(),
                            a.path("playtime_forever").asInt())
            );
        }
        return games;
    }
}
