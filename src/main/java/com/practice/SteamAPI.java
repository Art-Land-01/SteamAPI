package com.practice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpResponse;

public class SteamAPI{
    private static final String API_KEY = "CB76C482937B0AC695B62EBB16D3A64C";
    private static final String BASE_URL = "https://api.steampowered.com/";
    private static final CloseableHttpClient client = HttpClientBuilder.create().build();
    private static final ObjectMapper mapper = new ObjectMapper();


    public static void main(String[] args) {
      getGameAchievements("76561198145570899",990080);

    }


    private static String base(String url) {
        try {
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            System.out.println("Ответ сервера - " + response.getStatusLine().getStatusCode());
            String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println(responseBody+"\n"+new Object(){}.getClass().getEnclosingMethod().getName());
            return responseBody;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonNode getProfileInfo(String steamId){
        String url = BASE_URL + "ISteamUser/GetPlayerSummaries/v2/?key=" +
                API_KEY + "&steamids=" + steamId;
        try
        {
            return mapper.readTree(base(url));
            //player.path("response").path("players").get(0); в парсер
        } catch (Exception e) {
            System.out.println("Connection failed");
        }
        return null;
    }



    public static JsonNode getGamesInfo(String steamId){
        String url = BASE_URL+"IPlayerService/GetOwnedGames/v0001/?key="+API_KEY+"&steamid="+steamId+"&include_appinfo=true&format=json";
        try {
            return mapper.readTree(base(url));
            //games.path("response").path("games"); в парсер
        }
        catch (Exception e) {System.out.println("Connection failed");}

        return null;
    }


    public static JsonNode getGameAchievements(String steamId, Integer appID){
        String url = BASE_URL+ "ISteamUserStats/GetPlayerAchievements/v0001/?appid="+appID+"&key="+API_KEY+"&steamid="+steamId+"&l=russian";
        try {
            return mapper.readTree(base(url));
        }
        catch (Exception e) {

        }
        return null;

    }
}
