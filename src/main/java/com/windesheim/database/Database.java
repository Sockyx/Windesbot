package com.windesheim.database;

import com.windesheim.constant.DatabaseConstant;
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

        databaseConfig.addDataSourceProperty("useSSL",false);

        databaseConfig.addDataSourceProperty("cachePrepStmts", DatabaseConstant.cachePrepareStatements);
        databaseConfig.addDataSourceProperty("prepStmtCacheSize", DatabaseConstant.cachePrepareStatementSize);
        databaseConfig.addDataSourceProperty("prepStmtCacheSqlLimit", DatabaseConstant.prepareStatementSQLLimit);

        databaseSource = new HikariDataSource(databaseConfig);
    }

    public Connection getConnection() throws SQLException {
        return databaseSource.getConnection();
    }

    public static Database getInstance() {
        if(instance == null) {
            instance = new Database();
        }

        return instance;
    }

    public void close() {
        databaseSource.close();
    }


}
