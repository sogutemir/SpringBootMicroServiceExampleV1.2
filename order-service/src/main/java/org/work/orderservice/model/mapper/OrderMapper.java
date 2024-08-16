package org.work.orderservice.model.mapper;

import org.springframework.beans.BeanUtils;
import org.work.orderservice.model.dto.OrderDto;
import org.work.orderservice.model.entity.Order;

import java.util.Objects;

public class OrderMapper extends BaseMapper<Order, OrderDto> {

    @Override
    public Order convertToEntity(OrderDto dto, Object... args) {

        Order order = new Order();
        if(!Objects.isNull(dto)){
            BeanUtils.copyProperties(dto, order);
        }
        return order;
    }

    @Override
    public OrderDto convertToDto(Order entity, Object... args) {

        OrderDto orderDto = new OrderDto();
        if(!Objects.isNull(entity)) {
            BeanUtils.copyProperties(entity, orderDto);
        }
        return orderDto;    }

}