package br.com.dio.persistence;

import br.com.dio.persistence.entity.ContactEntity;
import com.mysql.cj.jdbc.StatementImpl;

import java.sql.SQLException;
import java.sql.Timestamp;

import static java.time.ZoneOffset.UTC;

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

}
