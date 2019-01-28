package data.sql.manager;

import data.sql.config.DatabaseConfig;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
public abstract class AbstractManager extends AbstractMultitenantManager {

    protected AbstractManager() {
        super(DatabaseConfig.DEFAULT_ALIAS);
    }
}
