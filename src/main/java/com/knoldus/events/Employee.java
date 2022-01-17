package com.knoldus.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.knoldus.aspect.ClientFilter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@Data
@ToString
@JsonFormat
public class Employee {
    String name;
//    @ClientFilter
    String clientId;
}
