package com.practice;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class PlayerStat {
    private String name,last_seen,game_playing,avatar_link;
    private String current_status = status.get(0);
    private static final Map<Integer,String> status = Map.of(0, "Offline", 1,
            "Online", 2 , "Busy", 3 , "Away", 4 ,
            "Snooze", 5 , "Looking to trade", 6, "Looking to play");

    public String getName() {
        return name;
    }
    public String getLast_seen() {
        return last_seen;
    }
    public String getGame_playing() {
        return game_playing;
    }
    public String getCurrent_status() {
        return current_status;
    }
    public String getAvatar_link() {
        return avatar_link;
    }


    public PlayerStat(String name,String avatar_link,int current_status,int last_seen,String game_playing)
    {
        this.name = name;
        this.avatar_link = avatar_link;
        this.current_status = status.get(current_status);
        this.game_playing = (game_playing);

        Instant instant = Instant.ofEpochSecond(last_seen);
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        this.last_seen = zonedDateTime.format(formatter);
    }


    public static void getAllInfo(PlayerStat player)
    {
        System.out.println(player.name);
        if(player.current_status.equals("Offline")) System.out.println(player.last_seen);
        else System.out.println(player.current_status);
        if(!player.game_playing.replaceAll(" ","").equals("")) System.out.println(player.game_playing);
    }



}