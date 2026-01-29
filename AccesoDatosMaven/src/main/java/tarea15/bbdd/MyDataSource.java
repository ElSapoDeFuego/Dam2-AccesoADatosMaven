package tarea15.bbdd;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class MyDataSource {
	private static final Logger logger = LogManager.getLogger(MyDataSource.class);
	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource dataSource;
	static {
		Properties props = new Properties();
		try (InputStream input = MyDataSource.class.getClassLoader().getResourceAsStream("db.properties")) {
			if (input == null) {
				throw new RuntimeException("No se encuentra el archivo db.properties");
			}
		props.load(input);
		
		config.setJdbcUrl(props.getProperty("db.url"));
		config.setUsername(props.getProperty("db.user"));
		config.setPassword(props.getProperty("db.pass"));
		config.setMaximumPoolSize(10);
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		
		dataSource = new HikariDataSource(config);
		} catch (IOException e) {
			logger.error("Error crítico al cargar la configuración de la BD: {}", e.getMessage());
		}
	}

	private MyDataSource() {

	}

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
}
