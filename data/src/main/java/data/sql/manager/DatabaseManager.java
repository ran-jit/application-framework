package data.sql.manager;

import com.google.common.collect.Maps;
import data.sql.config.DatabaseConfig;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.SessionFactory;

import java.util.Map;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class DatabaseManager {

    private static final Map<DatabaseConfig, SessionFactory> SESSION_FACTORY_MAP = Maps.newConcurrentMap();

    public synchronized static void init(@NonNull DatabaseConfig databaseInfo) {
        SESSION_FACTORY_MAP.put(databaseInfo, sessionFactory(databaseInfo));
    }

    private static SessionFactory sessionFactory(DatabaseConfig databaseInfo) {
        // TODO
        return null;
    }

    static SessionFactory getSessionFactory(DatabaseConfig databaseInfo) {
        return SESSION_FACTORY_MAP.get(databaseInfo);
    }
}
