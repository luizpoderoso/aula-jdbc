package br.com.dio;

import br.com.dio.persistence.ConnectionUtil;
import br.com.dio.persistence.EmployeeAuditDAO;
import br.com.dio.persistence.EmployeeDAO;
import br.com.dio.persistence.EmployeeParamDAO;
import br.com.dio.persistence.entity.EmployeeEntity;
import org.flywaydb.core.Flyway;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.OffsetDateTime;

public class Main {

    // a classe abaixo não foi utilizado porque ele não usa o tratamento de params, já o EmployeeParamDAO usa,
    // o que impede o ataque no sql do sistema através da entrada de dados pelo client-side.
//  private final static EmployeeDAO employeeDAO = new EmployeeDAO();
    private final static EmployeeParamDAO employeeDAO = new EmployeeParamDAO();
    private final static EmployeeAuditDAO employeeAuditDAO = new EmployeeAuditDAO();

    public static void main(String[] args) {
        // como não foram feitas modificações no flyway, ele procurará, por padrão na pasta resources/db/migration
        var flyway = Flyway.configure()
                .dataSource("jdbc:mysql://localhost:3306/jdbc-sample", "root", "32493403")
                .load();
        flyway.migrate();

        var entity = new EmployeeEntity();
        entity.setName("Beta");
        entity.setSalary(new BigDecimal(25000));
        entity.setBirthday(OffsetDateTime.now().minusYears(4).minusMonths(6));
        employeeDAO.insertWithProcedure(entity);

        System.out.println(employeeDAO.findById(entity.getId()));

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
