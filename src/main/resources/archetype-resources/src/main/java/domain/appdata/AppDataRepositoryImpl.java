package ${package}.domain.appdata;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
public class AppDataRepositoryImpl implements AppDataInterface {

    @PersistenceContext
    private EntityManager entityManager;

    public int complicatedQuery() {
        String query = "UPDATE appdata set data='HELLO WORLD'";

        return entityManager.createNativeQuery(query).executeUpdate();
    }
}
