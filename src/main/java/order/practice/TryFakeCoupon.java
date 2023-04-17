package order.practice;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;

public class TryFakeCoupon {
    public static void main(String[] args) {
                BorisDataBaseJDBC sc = new BorisDataBaseJDBC();
                String sql = "INSERT INTO coupon (coupon_id, company_id, coupon_starttime, coupon_endtime, coupon_img, coupon_status, coupon_discount, coupon_quantity, coupon_used_quantity) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                try (Connection conn = DriverManager.getConnection(sc.URL, sc.USER, sc.PASSWORD);
                     PreparedStatement stmt = conn.prepareStatement(sql)) {

                    stmt.setInt(1, 7777);
                    stmt.setInt(2, 1);
                    stmt.setObject(3, LocalDateTime.of(2023, 2, 5, 0, 0, 0));
                    stmt.setObject(4, LocalDateTime.of(2023, 5, 2, 23, 59, 59));

                    FileInputStream in = new FileInputStream("C:\\Users\\Tibame_T14\\Desktop\\30off.PNG");
                    byte[] buf = new byte[in.available()];
                    in.read(buf);
                    stmt.setBytes(5, buf);

                    stmt.setInt(6, 1);
                    stmt.setFloat(7, 0.7f);
                    stmt.setInt(8, 10);
                    stmt.setInt(9, 0);
                    stmt.executeUpdate();
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                System.out.println("執行成功 ");

            }
        }

