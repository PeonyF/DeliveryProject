package com.programmers.dto.order;

import com.programmers.domain.delivery.Delivery;
import com.programmers.domain.order.OrderList;
import com.programmers.domain.order.OrderState;
import com.programmers.domain.order.Payment;
import com.programmers.domain.orderItem.OrderItem;
import com.programmers.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderRequestDto {
    @NotNull
    private Long orderListId;
    @NotNull
    private User user;
    @NotNull
    private Menu menu;
    @NotNull
    private Delivery delivery;
    @NotNull
    private String paymentMethod;
    @NotNull
    private String state;
    @NotNull
    private int price;

    @Builder
    public OrderRequestDto(Long orderId, User user, Menu menu, Delivery delivery, String paymentMethod, String state, int price) {
        this.orderId = orderId;
        this.user = user;
        this.menu = menu;
        this.delivery = delivery;
        this.paymentMethod = paymentMethod;
        this.state = state;
        this.price = price;
    }
}
