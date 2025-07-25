package br.com.dio;

import br.com.dio.persistence.*;
import br.com.dio.persistence.entity.AccessEntity;
import br.com.dio.persistence.entity.ContactEntity;
import br.com.dio.persistence.entity.EmployeeEntity;
import br.com.dio.persistence.entity.ModuleEntity;
import net.datafaker.Faker;
import org.flywaydb.core.Flyway;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.Locale;
import java.util.stream.Stream;

import static java.time.ZoneOffset.UTC;

public class Main {

    // a classe abaixo não foi utilizado porque ele não usa o tratamento de params, já o EmployeeParamDAO usa,
    // o que impede o ataque no sql do sistema através da entrada de dados pelo client-side.
//  private final static EmployeeDAO employeeDAO = new EmployeeDAO();
    private final static EmployeeParamDAO employeeDAO = new EmployeeParamDAO();
    private final static EmployeeAuditDAO employeeAuditDAO = new EmployeeAuditDAO();
    private final static ContactDAO contactDAO = new ContactDAO();
    private final static ModuleDAO moduleDAO = new ModuleDAO();
    private final static AccessDAO accessDAO = new AccessDAO();
    private final static Faker faker = new Faker(Locale.of("pt", "BR"));

    public static void main(String[] args) {
        // como não foram feitas modificações no flyway, ele procurará, por padrão na pasta resources/db/migration
        var flyway = Flyway.configure()
                .dataSource("jdbc:mysql://localhost:3306/jdbc-sample", "root", "32493403")
                .load();
        flyway.migrate();

        var modules = moduleDAO.findModulesByEmployeeId(11);
        System.out.println(modules);

//        var modules = moduleDAO.findAll();
//        var employee = new EmployeeEntity();
//        employee.setName(faker.name().fullName());
//        employee.setSalary(BigDecimal.valueOf(5000));
//        employee.setBirthday(OffsetDateTime.of(faker.date().birthdayLocalDate(16, 40), LocalTime.MIN, UTC));
//        employee.setModules(modules);
//        employeeDAO.insert(employee);

//        var module = new ModuleEntity();
//        module.setName("Estoque");
//        moduleDAO.insert(module);
//        var employee = employeeDAO.findById(5);
//        var access = new AccessEntity();
//        access.setEmployee(employee);
//        access.setModule(module);
//        accessDAO.insert(access);

//        var module = moduleDAO.findById(1);
//        var employee1 = employeeDAO.findById(5);
//        var employee2 = employeeDAO.findById(6);
//        var access1 = new AccessEntity();
//        access1.setModule(module);
//        access1.setEmployee(employee1);
//        accessDAO.insert(access1);
//        var access2 = new AccessEntity();
//        access2.setModule(module);
//        access2.setEmployee(employee2);
//        accessDAO.insert(access2);

//        var module = new ModuleEntity();
//        module.setName("Financeiro");
//        moduleDAO.insert(module);

//        employeeDAO.findAll().forEach(System.out::println);

//        var employee = new EmployeeEntity();
//        employee.setName("Luiz Poderoso");
//        employee.setSalary(new BigDecimal(2500));
//        employee.setBirthday(OffsetDateTime.now().minusYears(17).minusMonths(7).minusDays(22));
//        employeeDAO.insert(employee);
//
//        var contact1 = new ContactEntity();
//        contact1.setDescription("luiz@poderoso.com");
//        contact1.setType("e-mail");
//        contact1.setEmployee(employee);
//        contactDAO.insert(contact1);
//
//        var contact2 = new ContactEntity();
//        contact2.setDescription("(79) xxxxx-xxxx");
//        contact2.setType("phone");
//        contact2.setEmployee(employee);
//        contactDAO.insert(contact2);

//        // usando o faker para gerar 4000 entidades
//        var entities = Stream.generate(() -> {
//            var employee = new EmployeeEntity();
//            employee.setName(faker.name().fullName());
//            employee.setSalary(new BigDecimal(faker.number().digits(4)));
//            employee.setBirthday(OffsetDateTime.of(faker.date().birthdayLocalDate(16, 40), LocalTime.MIN, UTC));
//            return employee;
//        }).limit(4000).toList();
//
//        employeeDAO.insertBatch(entities);

//        var entity = new EmployeeEntity();
//        entity.setName("Beta");
//        entity.setSalary(new BigDecimal(25000));
//        entity.setBirthday(OffsetDateTime.now().minusYears(4).minusMonths(6));
//        employeeDAO.insertWithProcedure(entity);
//
//        System.out.println(employeeDAO.findById(entity.getId()));

//        employeeDAO.findAll().forEach(e -> System.out.println(e));
//        var employee = new EmployeeEntity();
//        employee.setName("Bilu");
//        employee.setSalary(new BigDecimal(1));
//        employee.setBirthday(OffsetDateTime.now().minusYears(34));
//        employeeDAO.insert(employee);

//        employee = new EmployeeEntity();
//        employee.setName("Luiz");
//        employee.setSalary(new BigDecimal(5000));
//        employee.setBirthday(OffsetDateTime.now().minusYears(17));
//        employeeDAO.insert(employee);
//
//        employee = new EmployeeEntity();
//        employee.setName("Maria");
//        employee.setSalary(new BigDecimal(7000));
//        employee.setBirthday(OffsetDateTime.now().minusYears(16));
//        employeeDAO.insert(employee);
//
//        employee = new EmployeeEntity();
//        employee.setName("Lario");
//        employee.setSalary(new BigDecimal(61923123));
//        employee.setBirthday(OffsetDateTime.now().minusYears(162));
//        employeeDAO.insert(employee);
//
//         System.out.println(employeeDAO.findById(1));
//
//         employee = new EmployeeEntity();
//         employee.setId(1);
//         employee.setName("Aurora");
//         employee.setSalary(new BigDecimal(32500));
//         employee.setBirthday(OffsetDateTime.now().minusYears(3).minusMonths(2));
//         employeeDAO.update(employee);

//        employeeDAO.delete(4);
    }

}
