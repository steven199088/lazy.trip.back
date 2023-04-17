package order.practice;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TryFakeRoomtype_Img {
    public static void main(String[] args) {
        BorisDataBaseJDBC sc = new BorisDataBaseJDBC();
        String sql1 = "INSERT INTO roomtype_img (roomtype_id, roomtype_img) "
                + "VALUES (?,?)";
        String sql = "update company set company_img = ? where company_id = 3";

        try (Connection conn = DriverManager.getConnection(sc.URL, sc.USER, sc.PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql1)) {

//            stmt.setInt(1, 20236666);
            stmt.setInt(1, 2);
            FileInputStream in = new FileInputStream("C:\\Users\\Tibame_T14\\Desktop\\緯育天下飯店_三人房.jpg");
            byte[] buf = new byte[in.available()];
            in.read(buf);
            stmt.setBytes(2, buf);

            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("執行成功 ");
    }
}
