package jpabook.start.service;

import jpabook.start.domain.booking.Book;
import jpabook.start.domain.booking.BookStatus;
import jpabook.start.domain.house.Address;
import jpabook.start.domain.house.House;
import jpabook.start.domain.house.HouseType;
import jpabook.start.domain.house.SaleStatus;
import jpabook.start.domain.review.Review;
import jpabook.start.domain.review.StarScore;
import jpabook.start.domain.user.Guest;
import jpabook.start.domain.user.Host;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class InitDb {


  public InitDb(EntityManager em) {
    new InitService(em);
  }

  @Component
  @Transactional
  @RequiredArgsConstructor
  static class InitService {

    public InitService(EntityManager em) {
      this.em = em;
      this.hostService = new HostService(em);
      this.guestService = new GuestService(em);
      dbInit1();
    }
    private EntityManager em;
    private HostService hostService;
    GuestService guestService;

    /**
     * 게스트 4명, 호스트 4명 계정 미리 만들기
     * 숙소 3건(전체 숙소 2건, 개인실 1건) 이상
     * 숙소가 반드시 실제 숙소 정보와 동일할 필요는 없음
     * 나머지 한 명의 호스트 계정으로 숙소 등록을 시도
     * 후기 등록
     * 숙소별 최소 3건 등록
     * 추가적으로 이후 설명한 기능 검사에 적절한 사전 데이터 준비
     */
    public void dbInit1() {
      Guest guest1 = new Guest("user1", 10);
      Guest guest2 = new Guest("user2", 20);
      Guest guest3 = new Guest("user3", 30);
      Guest guest4 = new Guest("user4", 40);

      em.persist(guest1);
      em.persist(guest2);
      em.persist(guest3);
      em.persist(guest4);

      Host host1 = new Host("host1", 10);
      Host host2 = new Host("host2", 20);
      Host host3 = new Host("host3", 30);
      Host host4 = new Host("host4", 40);

      em.persist(host1);
      em.persist(host2);
      em.persist(host3);
      em.persist(host4);


      Address address1 = new Address("구미시","양호동", "대학로 61");
      Address address2 = new Address("대구광역시","수성구", "범어1동");
      Address address3 = new Address("부산광역시","부산진구", "초읍동");

      /**
       * 1) 호스트는 숙소를 등록할 수 있다
       */
      House GUMI_HOTEL = hostService.registHouse(host1, address1, 250000, "구미호텔", 5, 5, 1, HouseType.ENTIRESPACE, "넓고 쾌적합니다.");
      House DAEGU_HOTEL = hostService.registHouse(host2, address2, 300000, "대구호텔", 10, 10, 2, HouseType.ENTIRESPACE, "바베큐 장비와 조식을 제공합니다.");
      House BUSAN_HOTEL = hostService.registHouse(host3, address3, 350000, "부산호텔", 15, 15, 3, HouseType.PARTSPACE, "바다 풍경을 즐길 수 있습니다.");
      System.out.println();


      /**
       * 2) 호스트는 특정 기간에 정량 할인이나 정률 할인을 적용할 수 있다.
       */
      hostService.applyDiscountPolicy(GUMI_HOTEL,22, 23, SaleStatus.FIX);
      hostService.applyDiscountPolicy(DAEGU_HOTEL,22, 23, SaleStatus.QUANTITY);
      hostService.applyDiscountPolicy(BUSAN_HOTEL,24, 24, SaleStatus.QUANTITY);
      System.out.println();

//      List<House> houses = guestService.findHouse(22, 25);
//      guestService.sortByPrice(houses, 22, 25);
//      guestService.sortByStarPoint(houses);

//      guestService.findHouse(22, 25, 6, HouseType.ENTIRESPACE);

      /**
       * 3) 게스트는 조건에 맞는 숙소를 조회할 수 있다.
       */
      List<House> findHouses = guestService.findHouse(24, 26, 8);
      guestService.sortByPrice(findHouses, 24, 26);


      /**
       * 4) 게스트는 선택한 숙소의 상세 정보를 조회할 수 있다.
       */
      House findHouse1 = guestService.getDetailHouse("부산호텔");
      House findHouse2 = guestService.getDetailHouse("대구호텔");
      House findHouse3 = guestService.getDetailHouse("구미호텔");

      /**
       * 5) 체크인, 체크아웃 날짜와 인원을 입력하여 예약을 진행한다.
       */
      Book book1 = guestService.bookHouse(guest1, findHouse1, 8, 24, 26);
      Book book2 = guestService.bookHouse(guest1, findHouse2, 6, 22, 24);
      Book book3 = guestService.bookHouse(guest1, findHouse3, 4, 26, 28);

      guestService.getDetailHouse("부산호텔");
      guestService.getDetailHouse("대구호텔");
      guestService.getDetailHouse("구미호텔");


      /**
       * 6) 게스트는 예약한 숙소를 취소할 수 있다.
       */
      guestService.bookCancel(book1);



      /**
       * 7) 게스트는 1)전체 2)체크아웃이 완료된 숙소 3)체크인 예정인 숙소로 조회 가능하고
       *    해당 리스트는 날짜 기준 내림차순으로 정렬
       */
      guestService.reservationHistory(guest1);


      /**
       * 8) 게스트는 체크아웃이 완료된 숙소에 별점(1~5)과 후기를 작성할 수 있다.
       */
      Review review = new Review(book2, StarScore.FIVE, "시설이 정말 좋았다.");
      guestService.addComments(review);
      guestService.reservationHistory(guest1);

      guestService.getDetailHouse("대구호텔");

      /**
       * 9) 호스트는 지정한 달의 매출을 확인할 수 있다.
       * 후기 등록하면 금액만큼 호스트에게 돈을 지불
       */
      hostService.readMonthAmount(host2);

      /**
       * 10) 상속 관계, 값 타입, MappedSuperclass 를 반드시 적용해야 하며 적용한 코드를 보이고 설명
       */

    }
  }
}
