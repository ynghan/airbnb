package jpabook.start.service;

import jpabook.start.domain.house.Address;
import jpabook.start.domain.house.House;
import jpabook.start.domain.house.HouseType;
import jpabook.start.domain.house.SaleStatus;
import jpabook.start.domain.review.Review;
import jpabook.start.domain.user.Guest;
import jpabook.start.domain.user.Host;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class InitDb {

  private final InitService initService;

  @PostConstruct
  public void init() {
    initService.dbInit1();
  }


  @Component
  @Transactional
  @RequiredArgsConstructor
  static class InitService {
    private final EntityManager em;
    private final HostService hostService;

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
//      Member member = new Member();
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

      House GUMI_HOTEL = hostService.registHouse(host1, address1, 250000, "구미호텔", 5, 2, 1, HouseType.ENTIRESPACE, "넓고 쾌적합니다.");
      House DAEGU_HOTEL = hostService.registHouse(host2, address2, 300000, "대구호텔", 10, 3, 2, HouseType.ENTIRESPACE, "바베큐 장비와 조식을 제공합니다.");
      House BUSAN_HOTEL = hostService.registHouse(host3, address3, 350000, "부산호텔", 15, 4, 3, HouseType.PARTSPACE, "바다 풍경을 즐길 수 있습니다.");


//      hostService.applyDiscountPolicy(GUMI_HOTEL,20, 21, SaleStatus.FIX);
//      hostService.applyDiscountPolicy(DAEGU_HOTEL,22, 23, SaleStatus.QUANTITY);
//      hostService.applyDiscountPolicy(BUSAN_HOTEL,24, 24, SaleStatus.QUANTITY);

      new Review();

    }
  }
}
