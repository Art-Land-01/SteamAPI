package com.practice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpResponse;

public class SteamAPI{
    static ObjectMapper mapper = new ObjectMapper();
    private static final String API_KEY = "CB76C482937B0AC695B62EBB16D3A64C";
    private static final String BASE_URL = "https://api.steampowered.com/";


    public static void main(String[] args) {
        getGamesInfo("76561198145570899");
        getGameAchievments("76561198145570899",990080);

    }
    public static JsonNode getProfileInfo(String steamId){
        String url = BASE_URL + "ISteamUser/GetPlayerSummaries/v2/?key=" +
                API_KEY + "&steamids=" + steamId;


        try
        {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            System.out.println("Ответ сервера - "+ response.getStatusLine().getStatusCode());
            String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println(responseBody);





            ObjectMapper mapper = new ObjectMapper();
            JsonNode player = mapper.readTree(responseBody);
            return player.path("response").path("players").get(0);
        } catch (Exception e) {
            System.out.println("Connection failed");
        }
        return null;
    }
    public static JsonNode getGamesInfo(String steamId){
        String url = BASE_URL+"IPlayerService/GetOwnedGames/v0001/?key="+API_KEY+"&steamid="+steamId+"&include_appinfo=true&format=json";
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            System.out.println("Ответ сервера - " + response.getStatusLine().getStatusCode());
            String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println(responseBody);


            JsonNode games = mapper.readTree(responseBody);

            return games.path("response").path("games");
        }
        catch (Exception e) {e.printStackTrace();}

        return null;
    }
   // public static JsonNode getGameAchievements(String steamId, Integer appID){
        String url = BASE_URL+ "ISteamUserStats/GetPlayerAchievements/v0001/?appid="+appID+"&key="+API_KEY+"&steamid="+steamId+"&l=russian";
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            System.out.println("Ответ сервера - " + response.getStatusLine().getStatusCode());
            String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println(responseBody);
            JsonNode games = mapper.readTree(responseBody);
            return games.path("playerstats").path("achievments");
        }
        catch (Exception e) {e.printStackTrace();}
        return null;
    }
}
