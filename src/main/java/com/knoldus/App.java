package com.knoldus;

import com.knoldus.config.AppConfig;
import com.knoldus.events.Employee;
import com.knoldus.service.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        Service service = applicationContext.getBean(Service.class);
        Employee employee = applicationContext.getBean(Employee.class);
        employee.setClientId("__naas_smoke_test_client_abc");
        employee.setName("Nikunj");
        service.run(employee);
    }
}
