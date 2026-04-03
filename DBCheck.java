import java.sql.*;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class DBCheck {
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final byte[] KEY = "MySecretKey_1234".getBytes();

    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ticketdb", "root", "root");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id, description FROM tickets");
        while (rs.next()) {
            String id = rs.getString("id");
            String desc = rs.getString("description");
            System.out.println("ID: " + id + ", RAW DESC: " + desc);
            try {
                if (desc != null) {
                    SecretKeySpec secretKeySpec = new SecretKeySpec(KEY, "AES");
                    Cipher cipher = Cipher.getInstance(ALGORITHM);
                    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
                    byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(desc));
                    System.out.println("  Decrypted: " + new String(decrypted));
                }
            } catch (Exception e) {
                System.out.println("  FAILED TO DECRYPT: " + e.getMessage());
            }
        }
        conn.close();
    }
}
