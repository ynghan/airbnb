package jpabook.start;

import jpabook.start.domain.house.HouseType;
import jpabook.start.service.GuestService;

import javax.persistence.*;


public class JpaMain {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            GuestService guestService = new GuestService(em);

//            Guest guest = new Guest("정영한", 26);
//            guestService.save(guest);
//
//            //게스트는 조건에 맞는 숙소를 조회할 수 있다.
            //체크인 체크아웃 날짜로 조회
            guestService.findHouse(22, 25);
            //수용인원으로 조회
            guestService.findHouse(22, 25, 6);
            //숙소 타입으로 조회
            guestService.findHouse(22, 25, 6, HouseType.ENTIRESPACE);

//            //게스트는 선택한 숙소의 상세 정보를 조회할 수 있다.
//            House findHouse = guestService.getDetailHouse("금오호텔");
//
//
//
//            //게스트는 특정 달을 선택하여 예약 현황을 확인할 수 있다.
//            guestService.getSpecificMonthReservation(findHouse);

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
