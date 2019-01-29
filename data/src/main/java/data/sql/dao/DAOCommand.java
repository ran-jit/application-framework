package data.sql.dao;

import data.sql.exception.DataException;
import org.hibernate.Session;

public interface DAOCommand<V> {
    V execute(Session session) throws DataException;
}
