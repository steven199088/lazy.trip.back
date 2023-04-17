package order.practice;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TryFakeCompany {
    public static void main(String[] args) {
        BorisDataBaseJDBC sc = new BorisDataBaseJDBC();
//        String sql = "INSERT INTO company (company_img) VALUES (?) where company_id = ?";
        String sql = "update lazy.search set search_img = ? where search_id = ?";

        try (Connection conn = DriverManager.getConnection(sc.URL, sc.USER, sc.PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            FileInputStream in = new FileInputStream("C:\\Users\\Tibame_T14\\Desktop\\緯育花園酒店.jpg");
            byte[] buf = new byte[in.available()];
            in.read(buf);
            stmt.setBytes(1, buf);
            stmt.setInt(2, 1);

            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("執行成功 ");
    }
}
