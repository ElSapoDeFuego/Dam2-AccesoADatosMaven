package tarea14;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class MyDataSource {
	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource dataSource;

	static {
		config.setJdbcUrl("jdbc:mysql://localhost:3306/instituto");
		config.setUsername("root");
		config.setPassword("alumno");
		config.addDataSourceProperty("maximumPoolSize", 5);
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		
		dataSource = new HikariDataSource(config);
	}

	private MyDataSource() {
	}

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
}
