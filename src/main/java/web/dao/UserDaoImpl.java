package web.dao;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import web.exception.DaoUserLayerException;
import web.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    private final void consumerFor(Consumer<EntityManager> entityConsumer) {
        try {
            entityConsumer.accept(entityManager);
        }catch (HibernateException e) {
            throw new DaoUserLayerException("DAO Exception");
        }
    }

    private final <R> R functionFor(Function<EntityManager, R>  entityFunction) {
        try {
            return entityFunction.apply(entityManager);
        }catch (HibernateException e) {
            throw new DaoUserLayerException("DAO Exception");
        }
    }

    @Override
    public void createUser(User user) {
        consumerFor(entityManager -> entityManager.persist(user));
    }

    @Override
    public List<User> getAllUsers() {
        return functionFor(entityManager1 -> entityManager
                .createQuery("SELECT u FROM User u", User.class).getResultList());
    }

    @Override
    public void deleteUserById(Long id) {
        consumerFor(entityManager -> entityManager.remove(entityManager.find(User.class, id)));
    }

    @Override
    public void updateUserById(Long id, String firstName, String lastName, short age) {
        consumerFor(entityManager -> {
            User user = getUserById(id);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAge(age);
            entityManager.merge(user);
        });
    }

    @Override
    public User getUserById(Long id) {
        return functionFor(entityManager -> entityManager.find(User.class, id));
    }
}
