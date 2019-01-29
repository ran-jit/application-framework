package data.sql.dao;

import data.sql.exception.DataException;
import org.hibernate.Session;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
public interface DAOCommand<V> {
    V execute(Session session) throws DataException;
}
