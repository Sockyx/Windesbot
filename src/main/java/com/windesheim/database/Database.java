package com.windesheim.database;

import com.windesheim.constant.DatabaseConstant;
import com.windesheim.logging.Logger;
import com.windesheim.logging.MessageType;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * A class for handling database interaction.
 *
 * @author Lucas Ouwens
 */
public class Database {

    private static Database instance = null;
    private HikariDataSource databaseSource;

    public Database() {

        HikariConfig databaseConfig = new HikariConfig();

        databaseConfig.setJdbcUrl(
                "jdbc:" +
                        DatabaseConstant.databaseType +
                        "://" + DatabaseConstant.databaseServer +
                        ":" + DatabaseConstant.databasePort +
                        "/" + DatabaseConstant.databaseName + DatabaseConstant.getters
        );

        databaseConfig.setUsername(DatabaseConstant.databaseUsername);
        databaseConfig.setPassword(DatabaseConstant.databasePassword);

        databaseConfig.addDataSourceProperty("useSSL", false);

        databaseConfig.addDataSourceProperty("cachePrepStmts", DatabaseConstant.cachePrepareStatements);
        databaseConfig.addDataSourceProperty("prepStmtCacheSize", DatabaseConstant.cachePrepareStatementSize);
        databaseConfig.addDataSourceProperty("prepStmtCacheSqlLimit", DatabaseConstant.prepareStatementSQLLimit);

        databaseSource = new HikariDataSource(databaseConfig);

        Logger.log("The database connection has been initialised", MessageType.INFO);
    }

    public Connection getConnection() throws SQLException {
        return databaseSource.getConnection();
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    public void close() {
        Logger.log("Closing the database connection...", MessageType.INFO);
        databaseSource.close();
        Logger.log("Database connection has been closed.", MessageType.INFO);
    }


}
