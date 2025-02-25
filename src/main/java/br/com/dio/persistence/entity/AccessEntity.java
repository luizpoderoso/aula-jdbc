package br.com.dio.persistence.entity;

import lombok.Data;

@Data
public class AccessEntity {
    private EmployeeEntity employee;

    private ModuleEntity module;
}
