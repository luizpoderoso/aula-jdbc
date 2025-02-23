package br.com.dio.persistence;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectionUtil {
    public static Connection getConnection() throws SQLException {
        // quando se utiliza a porta padrão do DB (no caso do MySQL é a 3306), ela pode ser omitida.
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc-sample", "root", "32493403");
    }
}
