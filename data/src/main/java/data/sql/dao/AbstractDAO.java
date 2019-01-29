package data.sql.dao;

import data.sql.config.DatabaseConfig;
import data.sql.utils.DatabaseUtil;

import java.io.Serializable;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
public abstract class AbstractDAO<E extends Serializable> extends AbstractMultitenantDAO<E> {

    public AbstractDAO(DatabaseUtil databaseUtil) {
        super(DatabaseConfig.DEFAULT_ALIAS, databaseUtil);
    }
}
