package com.practice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameRepo {

    // Добавить игру в отслеживаемые
    public void save(String steamId, Game game) {
        String sql = "INSERT INTO tracked_games (steam_id, app_id, game_name) " +
                "VALUES (?, ?, ?) " +
                "ON CONFLICT (steam_id, app_id) DO NOTHING";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, steamId);
            stmt.setInt(2, game.getGameID());
            stmt.setString(3, game.getGameName());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Получить все отслеживаемые игры
    public List<Game> getTrackedGames(String steamId) {
        String sql = "SELECT app_id, game_name FROM tracked_games WHERE steam_id = ?";
        List<Game> games = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, steamId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                games.add(new Game(
                        rs.getInt("app_id"),
                        rs.getString("game_name"),
                        0
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    // Удалить игру из отслеживаемых
    public void delete(String steamId, int appId) {
        String sql = "DELETE FROM tracked_games WHERE steam_id = ? AND app_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, steamId);
            stmt.setInt(2, appId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Проверить отслеживается ли игра
    public boolean isTracked(String steamId, int appId) {
        String sql = "SELECT COUNT(*) FROM tracked_games WHERE steam_id = ? AND app_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, steamId);
            stmt.setInt(2, appId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
