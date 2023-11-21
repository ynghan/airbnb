package jpabook.start;

import jpabook.start.domain.house.House;
import jpabook.start.domain.house.HouseType;
import jpabook.start.domain.review.StarScore;
import jpabook.start.service.GuestService;
import jpabook.start.service.InitDb;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.*;


@SpringBootApplication
public class JpaMain {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            new InitDb(em);


            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
