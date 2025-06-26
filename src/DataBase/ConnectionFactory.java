package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection(String enderecoIP, String enderecoPorta, String nomeBanco, String usuario, String senha) throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://"+enderecoIP+":"+enderecoPorta+"/"+nomeBanco, usuario, senha);
    }
}
