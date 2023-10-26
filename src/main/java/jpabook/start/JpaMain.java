package jpabook.start;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Team teamA =  new Team("TeamA");
            em.persist(teamA);
            Team teamB =  new Team("TeamB");
            em.persist(teamB);

            Member member1 = new Member("Member1", 10, teamA);
            em.persist(member1);
            Member member2 = new Member("Member2", 12, teamA);
            em.persist(member2);
            Member member3 = new Member("Member3", 14, teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            List<Team> result = em.createQuery("SELECT t from Team t", Team.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();

            for (Team team : result) {
                System.out.println("team = " + team.getName() + "team.members.size = " + team.getMembers().size());
            }

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

//    static void createMember() {
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//
//
//        try {
//            tx.begin();
//
//            tx.commit();
//        } catch(Exception e) {
//            e.printStackTrace();
//            tx.rollback();
//        } finally {
//            em.close();
//        }
//    }

//    static void mergeMember(Member member) {
//
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//
//        try {
//            tx.begin();
//
//            tx.commit();
//        } catch(Exception e) {
//            e.printStackTrace();
//            tx.rollback();
//        } finally {
//            em.close();
//        }
//    }
}
