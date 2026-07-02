package com.github.switchover.example.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {
    // ..
    public String findOrder(Long id) {
        return "order-" + id;
    }
}
