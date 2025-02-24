package br.com.dio.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum OperationEnum {
    INSERT,
    UPDATE,
    DELETE;

    // basicamente ele pega o valor de dbOperation (I, U ou D) e retorna INSERT, UPDATE ou DELETE, dependendo da letra.
    public static OperationEnum getByDbOperation(final String dbOperation) {
        return Stream.of(OperationEnum.values())
                .filter(o -> o.name().startsWith(dbOperation.toUpperCase()))
                .findFirst()
                .orElseThrow();
    }
}
