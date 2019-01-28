package data.sql.dao;

import lombok.NonNull;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.MultiIdentifierLoadAccess;
import org.hibernate.Session;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class AbstractDAO<E extends Serializable> {

    protected final Session session;
    private final Class<E> entityClass;

    protected AbstractDAO(@NonNull Session session) {
        this.session = session;

        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[0];
    }

    protected Long save(@NonNull E reference) {
        return (Long) this.session.save(reference);
    }

    protected void saveOrUpdate(@NonNull E reference) {
        this.session.saveOrUpdate(reference);
    }

    protected Optional<E> find(@NonNull Serializable id) {
        return Optional.ofNullable(this.session.find(this.entityClass, id));
    }

    protected Optional<E> byId(@NonNull Serializable id) {
        IdentifierLoadAccess<E> identifierLoadAccess = this.session.byId(this.entityClass);
        return identifierLoadAccess.loadOptional(id);
    }

    protected Stream<E> byMultipleIds(@NonNull List<Serializable> ids) {
        MultiIdentifierLoadAccess<E> multiIdentifierLoadAccess = this.session.byMultipleIds(this.entityClass);
        return multiIdentifierLoadAccess.multiLoad(ids).stream();
    }

    protected void update(@NonNull E reference) {
        this.session.update(reference);
    }

    protected void delete(@NonNull E reference) {
        this.session.delete(reference);
    }

}
