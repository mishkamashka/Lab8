package ru.ifmo.se.serverSide;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class PSQLConnection {

    private Connection connection;

    public PSQLConnection(String serverName, int port, String bdName, String user, String password) throws SQLException{
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setServerName(serverName);
        dataSource.setPortNumber(port);
        dataSource.setDatabaseName(bdName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        this.connection = dataSource.getConnection();
    }

    public Connection getConnection() {
        return connection;
    }
}
