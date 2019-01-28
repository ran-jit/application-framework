package data.sql.manager;

import data.sql.config.DatabaseConfig;

public abstract class AbstractManager extends AbstractMultitenantManager {

    protected AbstractManager() {
        super(DatabaseConfig.DEFAULT_ALIAS);
    }

}
