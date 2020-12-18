
package database;

import model.PointEntity;
import model.User;

import javax.ejb.Stateful;
import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named("pointDB")
@Stateful
public class PointsDB implements Serializable {
    private final EntityManager em = Persistence.
            createEntityManagerFactory("Web_4").
            createEntityManager();
    public void addNewPoint(PointEntity point) {
        try {
            em.persist(point);
            em.flush();
        } catch (PersistenceException e) {
            point = null;
        }

    }

    public List<PointEntity> getPoints(User user) {
        return em.createNamedQuery("pointEntity.FindByUser", PointEntity.class)
                .setParameter("username", user).getResultStream().collect(Collectors.toList());
    }

    public int clear(String username) {
        System.out.println("pointsDb.clear");
/*        em.createNamedQuery("pointEntity.Clear")
                .setParameter("username", username).execute();*/
        Query query = em.createQuery(
                "DELETE FROM pointEntity AS point WHERE point.username = :username");
        query.setParameter("username", username);
        int result = query.executeUpdate();
        System.out.println(result);
        return 1;
    }
}

