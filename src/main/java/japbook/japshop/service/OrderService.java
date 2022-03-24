package japbook.japshop.service;

import japbook.japshop.domain.Delivery;
import japbook.japshop.domain.Member;
import japbook.japshop.domain.Order;
import japbook.japshop.domain.OrderItem;
import japbook.japshop.domain.item.Item;
import japbook.japshop.repository.ItemRepository;
import japbook.japshop.repository.MemberRepository;
import japbook.japshop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     * @param memberId
     * @param itemId
     * @param count
     * @return
     */
    @Transactional
    public Long Order(Long memberId, Long itemId, int count) {

        // 엔티티 조회
        Member member  = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     * @param orderId
     */
    public void cancelOrder(Long orderId) {
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소
        /**
         * 엔티티가 변경되면 JPA가 알아서 UPDATE 쿼리를 날려준다.
         */
        order.cancel();
    }


    // 검색 기능
//    public List<Order> findOrders(OrderSearch orderSearch) {
//        return orderRepository.findAll(orderSearch);
//    }
}
