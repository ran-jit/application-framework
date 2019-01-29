package data.sql.utils;

import com.google.common.collect.Maps;
import data.sql.config.DatabaseConfig;
import lombok.NonNull;
import org.hibernate.SessionFactory;

import java.util.Map;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
public class DatabaseUtil {
    private final Map<DatabaseConfig, SessionFactory> sessionFactoryMap = Maps.newConcurrentMap();

    public synchronized void init(@NonNull DatabaseConfig databaseInfo) {
        sessionFactoryMap.put(databaseInfo, sessionFactory(databaseInfo));
    }

    private static SessionFactory sessionFactory(DatabaseConfig databaseInfo) {
        // TODO
        return null;
    }

    public SessionFactory getSessionFactory(DatabaseConfig databaseInfo) {
        return sessionFactoryMap.get(databaseInfo);
    }
}
