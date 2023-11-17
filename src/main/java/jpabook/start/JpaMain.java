package jpabook.start;

import jpabook.start.domain.user.Guest;
import jpabook.start.domain.user.Host;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Host host = new Host();
            Guest guest = new Guest();
            em.persist(host);
            em.persist(guest);

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
