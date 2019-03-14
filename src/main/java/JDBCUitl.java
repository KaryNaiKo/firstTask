import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCUitl {
    private static String url="jdbc:postgresql://localhost:5432/test";
    private static String username="postgres";
    private static String password="password";

    public static List<Data> getData() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM data");

            List<Data> list = new ArrayList<>();
            while (resultSet.next()){
                int id = Integer.parseInt(resultSet.getString(1));
                String data1 = resultSet.getString(2);
                String data2 = resultSet.getString(3);
                Data data = new Data(id, data1, data2);
                list.add(data);
            }
            resultSet.close();
            statement.close();
            connection.close();

            return list;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
