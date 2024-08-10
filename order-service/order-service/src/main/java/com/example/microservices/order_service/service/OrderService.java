package com.example.microservices.order_service.service;

import com.example.microservices.order_service.client.InventoryClient;
import com.example.microservices.order_service.dto.OrderRequest;
import com.example.microservices.order_service.model.Order;
import com.example.microservices.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public void placeOrder(OrderRequest orderRequest) {
      var isInProduct=inventoryClient.isInStock(orderRequest.skuCode(),orderRequest.quantity());

      if(isInProduct){
          Order order = new Order();
          order.setOrderNumber(UUID.randomUUID().toString());
          order.setPrice(orderRequest.price().multiply(BigDecimal.valueOf(orderRequest.quantity())));
          order.setSkuCode(orderRequest.skuCode());
          order.setQuantity(orderRequest.quantity());
          orderRepository.save(order);
      }
      else{
          throw new RuntimeException("Product with Skucode"+orderRequest.skuCode()+"is not in stock");
      }

    }

}
