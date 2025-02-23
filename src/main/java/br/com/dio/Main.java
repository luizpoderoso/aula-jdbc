package br.com.dio;

import br.com.dio.persistence.ConnectionUtil;
import br.com.dio.persistence.EmployeeDAO;
import br.com.dio.persistence.entity.EmployeeEntity;
import org.flywaydb.core.Flyway;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.OffsetDateTime;

public class Main {

    private final static EmployeeDAO employeeDAO = new EmployeeDAO();

    public static void main(String[] args) {
        // como não foram feitas modificações no flyway, ele procurará, por padrão na pasta resources/db/migration
        var flyway = Flyway.configure()
                .dataSource("jdbc:mysql://localhost:3306/jdbc-sample", "root", "32493403")
                .outOfOrder(true)
                .load();
        flyway.migrate();

/*
 System.out.println(employeeDAO.findById(2));
 var employee = new EmployeeEntity();
 employee.setId(1);
 employee.setName("Aurora");
 employee.setSalary(new BigDecimal(3250));
 employee.setBirthday(OffsetDateTime.now().minusYears(3).minusMonths(2));
 employeeDAO.update(employee);
*/

/*
        var employee = new EmployeeEntity();
        employee.setName("Juru");
        employee.setSalary(new BigDecimal(50000));
        employee.setBirthday(OffsetDateTime.now().minusYears(34));

        System.out.println(employee);
        employeeDAO.insert(employee);
        System.out.println(employee);
*/
    }

}
