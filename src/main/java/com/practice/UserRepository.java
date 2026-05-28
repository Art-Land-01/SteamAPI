package com.practice;

import java.sql.*;

public class UserRepository {

    // Сохранить или обновить пользователя
    public void save(PlayerStat player, String steamId) {
        String sql = "INSERT INTO users (steam_id, username, avatar_url) " +
                "VALUES (?, ?, ?) " +
                "ON CONFLICT (steam_id) DO UPDATE " +
                "SET username = EXCLUDED.username, avatar_url = EXCLUDED.avatar_url";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, steamId);
            stmt.setString(2, player.getName());
            stmt.setString(3, player.getAvatar_link());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exists(String steamId) {
        String sql = "SELECT COUNT(*) FROM users WHERE steam_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, steamId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Обновить время синхронизации
    public void updateLastSync(String steamId) {
        String sql = "UPDATE users SET last_sync = NOW() WHERE steam_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, steamId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}