package jpabook.start.service;


import jpabook.start.domain.house.*;
import jpabook.start.domain.user.Host;
import jpabook.start.repository.userRepository.HostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class HostService {

  private HostRepository hostRepository;

  public HostService(EntityManager em) {
    hostRepository = new HostRepository(em);
  }

  public void save(Host host) {
    hostRepository.save(host);
  }

  //1. 호스트는 숙소를 등록할 수 있다. : registHouse
  public House registHouse(Host host, Address address, int charge, String name, int capacity, int roomCount, int bathroomCount, HouseType status, String introduction) {
    House house = new House(charge, address, name, capacity, roomCount, bathroomCount, status, introduction);
    host.registHouse(house);
    return house;
  }


  //2. 호스트는 특정 기간에 정량 할인이나 정률 할인을 적용할 수 있다.
  public void applyDiscountPolicy(House house, int startDate, int endDate, SaleStatus status) {

    for(int i = startDate; i <= endDate; i++) {
      DateHouse findDateHouse = hostRepository.getDateHouseByDate(house, i);
      findDateHouse.setStatus(status);
      System.out.print(house.getName() + "(" + findDateHouse.getHouseDate() + ")일: ");
      calPrice(findDateHouse);
    }
  }

  //특정 기간의 할인 정책을 설정하여 calPrice 메소드를 실행하여 요금이 적절히 변경되는지 콘솔에서 확인
  public void calPrice(DateHouse datehouse) {
    SaleStatus findStatus = datehouse.getStatus();
    if(findStatus.equals(SaleStatus.QUANTITY)) {
      double v = datehouse.getDateCharge() * 0.85;
      datehouse.setDateCharge(v);
      System.out.println((int) v+ "원");
    } else if(findStatus.equals(SaleStatus.FIX)) {
      double v = datehouse.getDateCharge() - 5000;

      datehouse.setDateCharge(v);
      System.out.println((int) v + "원");
    }
  }

}
