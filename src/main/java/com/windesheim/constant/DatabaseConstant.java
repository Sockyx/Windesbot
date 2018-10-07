package com.windesheim.constant;

/**
 * Constants for the database connection
 *
 * @author Lucas Ouwens
 */
public class DatabaseConstant {

    public static final String databaseServer = "localhost";
    public static final int databasePort = 3306;

    public static final String databaseType = "mysql";

    public static final String databaseUsername = "<database_username>";
    public static final String databasePassword = "<database_password>";

    public static final String databaseName = "windesbot";

    public static final String cachePrepareStatements = "true";

    public static final String cachePrepareStatementSize = "250";

    public static final String prepareStatementSQLLimit = "2048";

    // somehow does not work without this
    public static final String getters = "?useLegacyDatetimeCode=false&serverTimezone=UTC";


}
