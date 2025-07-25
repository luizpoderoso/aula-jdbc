package br.com.dio.persistence;

import br.com.dio.persistence.entity.ContactEntity;
import br.com.dio.persistence.entity.EmployeeEntity;
import com.mysql.cj.jdbc.StatementImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {

    public void insert(final ContactEntity entity) {
        try (
                var connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement(
                        "INSERT INTO contacts (description, type, employee_id) values (?, ?, ?);"
                );
        ) {
            statement.setString(1, entity.getDescription());
            statement.setString(2, entity.getType());
            statement.setLong(3, entity.getEmployee().getId());

            statement.executeUpdate();

            if (statement instanceof StatementImpl impl) {
                entity.setId(impl.getLastInsertID());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<ContactEntity> findAllByEmployeeId(long employeeId) {
        var entities = new ArrayList<ContactEntity>();
        try (
                var connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement("SELECT * FROM contacts where employee_id = ?")
        ) {
            statement.setLong(1, employeeId);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            while (resultSet.next()) {
                var entity = new ContactEntity();
                entity.setId(resultSet.getLong("id"));
                entity.setDescription(resultSet.getString("description"));
                entity.setType(resultSet.getString("type"));
                entity.setEmployee(new EmployeeEntity());
                entity.getEmployee().setId(resultSet.getLong("employee_id"));
                entities.add(entity);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return entities;
    }

}
