package com.knoldus.service;

import com.knoldus.aspect.ClientFilter;
import com.knoldus.events.Employee;
import org.springframework.stereotype.Component;

@Component
public class Service {
    @ClientFilter(serviceName = "abc")
    public void run(Employee employee) {
        System.out.println(employee.getClientId());
    }
}
