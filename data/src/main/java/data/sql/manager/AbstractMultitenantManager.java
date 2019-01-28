package data.sql.manager;

import data.sql.commons.Preconditions;
import data.sql.config.DatabaseConfig;
import lombok.NonNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
public abstract class AbstractMultitenantManager extends DatabaseManager {

    protected final String tenantAlias;

    protected AbstractMultitenantManager(@NonNull String tenantAlias) {
        this.tenantAlias = Preconditions.checkNotEmpty(tenantAlias);
    }

    protected Session getSession() {
        SessionFactory sessionFactory = getSessionFactory(DatabaseConfig.builder().alias(this.tenantAlias).build());
        return sessionFactory.openSession();
    }

    protected Transaction beginTransaction(@NonNull Session session) {
        return session.beginTransaction();
    }

    protected void commitTransaction(@NonNull Transaction transaction) {
        transaction.commit();
    }

    protected void rollbackTransaction(Transaction transaction) {
        if (transaction != null) {
            transaction.rollback();
        }
    }

    protected void closeSession(Session session) {
        if (session != null) {
            session.close();
        }
    }
}
