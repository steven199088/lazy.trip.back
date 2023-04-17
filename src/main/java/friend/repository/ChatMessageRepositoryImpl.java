package friend.repository;

import common.HikariDataSource;
import friend.model.ChatMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatMessageRepositoryImpl implements ChatMessageRepository {
    @Override
    public boolean addMessage(ChatMessage chatMessage) {
        boolean hasAdded = false;
        String sql = "INSERT INTO `lazy`.`chatroom_message` (`sender_id`, `chatroom_id`, `message`, `sent_at`) VALUES (?, ?, ?, ?);";
        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, chatMessage.getSenderId());
            ps.setInt(2, chatMessage.getChatroomId());
            ps.setString(3, chatMessage.getMessage());
            ps.setInt(4, chatMessage.getSentAt());
            hasAdded = ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException("目前資料庫有異狀，無法儲存訊息");
        }
        return hasAdded;
    }

    @Override
    public List<ChatMessage> getAllMessages(Integer chatroomId) {
        List<ChatMessage> messages = new ArrayList<>();
        String sql = """
                SELECT member_name, member_username, sender_id, chatroom_id, message, sent_at FROM chatroom_message cms
                \tJOIN lazy.member m
                \t\tON cms.sender_id = m.member_id
                \tWHERE chatroom_id = ?;;
                """;
        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, chatroomId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChatMessage message = new ChatMessage();
                message.setSenderId(rs.getInt("sender_id"));
                String nickname = rs.getString("member_username") != null
                        ? rs.getString("member_username")
                        : rs.getString("member_name");
                message.setSenderNickname(nickname);
                message.setChatroomId(rs.getInt("chatroom_id"));
                message.setMessage(rs.getString("message"));
                message.setSentAt(rs.getInt("sent_at"));
                messages.add(message);
            }
        } catch (SQLException e) {
            throw new RuntimeException("目前資料庫有異狀，無法取得歷史訊息");
        }
        return messages;
    }
}
