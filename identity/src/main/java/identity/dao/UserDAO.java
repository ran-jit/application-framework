package identity.dao;

import identity.entity.UserEntity;
import com.google.inject.Inject;
import data.sql.dao.AbstractDAO;
import data.sql.utils.DatabaseUtil;

public class UserDAO extends AbstractDAO<UserEntity> {

    @Inject
    public UserDAO(DatabaseUtil databaseUtil) {
        super(databaseUtil);
    }
}
