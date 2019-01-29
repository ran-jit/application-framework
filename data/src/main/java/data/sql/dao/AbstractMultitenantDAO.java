package data.sql.dao;

import data.sql.commons.Preconditions;
import data.sql.config.DatabaseConfig;
import data.sql.exception.DataException;
import data.sql.utils.DatabaseUtil;
import lombok.NonNull;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.MultiIdentifierLoadAccess;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
public abstract class AbstractMultitenantDAO<E extends Serializable> {

    private final String tenantAlias;
    private final Class<E> entityClass;
    private final DatabaseUtil databaseUtil;

    protected AbstractMultitenantDAO(@NonNull String tenantAlias, DatabaseUtil databaseUtil) {
        this.tenantAlias = Preconditions.checkNotEmpty(tenantAlias);
        this.databaseUtil = databaseUtil;

        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[0];
    }

    private Session getSession() {
        SessionFactory sessionFactory = databaseUtil.getSessionFactory(DatabaseConfig.builder().alias(this.tenantAlias).build());
        return sessionFactory.openSession();
    }

    private Transaction beginTransaction(@NonNull Session session) {
        return session.beginTransaction();
    }

    private void commitTransaction(@NonNull Transaction transaction) {
        transaction.commit();
    }

    private void rollbackTransaction(Transaction transaction) {
        if (transaction != null) {
            transaction.rollback();
        }
    }

    private void closeSession(Session session) {
        if (session != null) {
            session.close();
        }
    }

    protected <V> V execute(DAOCommand<V> command) throws DataException {
        Session session = null;
        V value;
        try {
            session = getSession();
            value = command.execute(session);
        } catch (Exception ex) {
            throw new DataException(ex);
        } finally {
            closeSession(session);
        }
        return value;
    }

    protected void executeAsync(DAOCommand command) throws DataException {
        Session session = null;
        try {
            session = getSession();
            command.execute(session);
        } catch (Exception ex) {
            throw new DataException(ex);
        } finally {
            closeSession(session);
        }
    }

    protected <V> V executeTransaction(DAOCommand<V> command) throws DataException {
        Session session = null;
        Transaction transaction = null;
        V value;
        try {
            session = getSession();
            transaction = beginTransaction(session);
            value = command.execute(session);
            commitTransaction(transaction);
        } catch (Exception ex) {
            rollbackTransaction(transaction);
            throw new DataException(ex);
        } finally {
            closeSession(session);
        }
        return value;
    }

    protected void executeAsyncTransaction(DAOCommand command) throws DataException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession();
            transaction = beginTransaction(session);
            command.execute(session);
            commitTransaction(transaction);
        } catch (Exception ex) {
            rollbackTransaction(transaction);
            throw new DataException(ex);
        } finally {
            closeSession(session);
        }
    }

    protected Long save(@NonNull E reference) throws DataException {
        return (Long) executeTransaction(session -> session.save(reference));
    }

    protected void saveOrUpdate(@NonNull E reference) throws DataException {
        executeAsyncTransaction(session -> {
            session.saveOrUpdate(reference);
            return null;
        });
    }

    protected Optional<E> find(@NonNull Serializable id) throws DataException {
        return Optional.ofNullable(execute(session -> session.find(this.entityClass, id)));
    }

    protected Optional<E> byId(@NonNull Serializable id) throws DataException {
        return execute(session -> {
            IdentifierLoadAccess<E> identifierLoadAccess = session.byId(this.entityClass);
            return identifierLoadAccess.loadOptional(id);
        });
    }

    protected Stream<E> byMultipleIds(@NonNull List<Serializable> ids) throws DataException {
        return execute(session -> {
            MultiIdentifierLoadAccess<E> multiIdentifierLoadAccess = session.byMultipleIds(this.entityClass);
            return multiIdentifierLoadAccess.multiLoad(ids).stream();
        });
    }

    protected void update(@NonNull E reference) throws DataException {
        executeAsyncTransaction(session -> {
            session.update(reference);
            return null;
        });
    }

    protected void delete(@NonNull E reference) throws DataException {
        executeAsyncTransaction(session -> {
            session.delete(reference);
            return null;
        });
    }
}
