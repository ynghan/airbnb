package jpabook.start;

import jpabook.start.domain.house.House;
import jpabook.start.domain.house.HouseType;
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

            GuestService guestService = new GuestService(em);

            //게스트는 조건에 맞는 숙소를 조회할 수 있다.



            //게스트는 선택한 숙소의 상세 정보를 조회할 수 있다.


            //게스트는 특정 달을 선택하여 예약 현황을 확인할 수 있다.
//            guestService.getSpecificMonthReservation(findHouse);

            //게스트는 체크인, 체크아웃 날짜와 인원을 입력하여 예약을 진행한다.


            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
