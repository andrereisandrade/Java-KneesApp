package br.com.kneesapp.sql;

import java.sql.Connection;
import org.postgresql.ds.PGPoolingDataSource;

/**
 *
 * @author andre
 */
public class JConnectionDB {

    private PGPoolingDataSource dataSource;

    public Connection getConnection() throws Exception {
        Connection conn = dataSource.getConnection();
        conn.setAutoCommit(false);
        return conn;
    }
    //Inicio Singleton

    private JConnectionDB() {
        dataSource = new PGPoolingDataSource();
        dataSource.setDataSourceName("kneesapp_LTDA"); //Nome qualquer
        dataSource.setServerName("localhost");//ip ou nome do servidor
        //dataSource.setServerName("ec2-18-220-159-137.us-east-2.compute.amazonaws.com");//ip ou nome do servidor
        dataSource.setDatabaseName("kneesapp_db");//nome do banco que se quer utilizar
        dataSource.setUser("postgres");
        dataSource.setPassword("master");
        dataSource.setPortNumber(5432);
        dataSource.setMaxConnections(30);
        dataSource.setInitialConnections(10);

    }
    private static JConnectionDB instance;

    public static JConnectionDB getInstance() {
        if (instance == null) {
            instance = new JConnectionDB();
        }
        return instance;
    }
}
