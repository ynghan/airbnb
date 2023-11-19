package jpabook.start;

import jpabook.start.domain.house.Address;
import jpabook.start.domain.house.House;
import jpabook.start.domain.house.HouseType;
import jpabook.start.domain.house.SaleStatus;
import jpabook.start.domain.user.Host;
import jpabook.start.service.GuestService;
import jpabook.start.service.HostService;

import javax.persistence.*;


public class JpaMain {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            HostService hostService = new HostService(em);
            GuestService guestService = new GuestService(em);

            Host host1 = new Host();
            hostService.save(host1);
            Address address1 = new Address("구미시","양호동", "대학로 61");

            Host host2 = new Host();
            hostService.save(host2);
            Address address2 = new Address("대구광역시","수성구", "범어1동");

            House guemohHotel = hostService.registHouse(host1, address1, 250000, "금오호텔", 8, 3, 2, HouseType.PARTSPACE, "넓고 쾌적합니다.");
            House daeguHotel = hostService.registHouse(host2, address2,320000, "대구호텔", 10, 4, 3, HouseType.ENTIRESPACE, "아름다운 자연 경관과 넓은 마당, 매우 넓고 쾌적합니다.");

            hostService.applyDiscountPolicy(guemohHotel,20, 21, SaleStatus.FIX);
            hostService.applyDiscountPolicy(daeguHotel,20, 21, SaleStatus.QUANTITY);

//            Guest guest = new Guest("정영한", 26);
//            guestService.save(guest);
//
//            //게스트는 조건에 맞는 숙소를 조회할 수 있다.
            //수용인원으로 조회
            guestService.readByCapacity(6);
            //숙소 타입으로 조회
            guestService.readByHouseByType(HouseType.ENTIRESPACE);
            //체크인 체크아웃 날짜로 조회
            guestService.readByDateRange(22, 25);

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
