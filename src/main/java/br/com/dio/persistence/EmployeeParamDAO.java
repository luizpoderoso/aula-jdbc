package br.com.dio.persistence;

import br.com.dio.persistence.entity.ContactEntity;
import br.com.dio.persistence.entity.EmployeeEntity;
import com.mysql.cj.jdbc.StatementImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.ZoneOffset.UTC;
import static java.util.TimeZone.LONG;

public class EmployeeParamDAO {

    public void insertWithProcedure(final EmployeeEntity entity) {
        try (
                var connection = ConnectionUtil.getConnection();
                var statement = connection.prepareCall(
                        "call prc_insert_employee(?, ?, ?, ?);"
                )
        ) {
            statement.registerOutParameter(1, LONG);

            statement.setString(2, entity.getName());
            statement.setBigDecimal(3, entity.getSalary());
            var timestamp = Timestamp.valueOf(entity.getBirthday().atZoneSimilarLocal(UTC).toLocalDateTime());
            statement.setTimestamp(4, timestamp);

            statement.execute();
            entity.setId(statement.getLong(1));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void insert(final EmployeeEntity entity) {
        try (
                var connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement(
                        "INSERT INTO employees (name, salary, birthday) values (?, ?, ?);"
                );
        ) {
            statement.setString(1, entity.getName());
            statement.setBigDecimal(2, entity.getSalary());
            var timestamp = Timestamp.valueOf(entity.getBirthday().atZoneSimilarLocal(UTC).toLocalDateTime());
            statement.setTimestamp(3, timestamp);

            statement.executeUpdate();

            if (statement instanceof StatementImpl impl) {
                entity.setId(impl.getLastInsertID());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // method para insert em lotes
    public void insertBatch(final List<EmployeeEntity> entities) {
        // o primeiro try/catch tenta fazer a conexão, caso ocorra exceção, essa será relacionada à conexão
        try (var connection = ConnectionUtil.getConnection()) {
            var sql = "INSERT INTO employees (name, salary, birthday) values (?, ?, ?);";
            // o segundo try/catch tenta preparar a transação, caso ocorra exceção, essa será relacionada a mesma
            try (var statement = connection.prepareStatement(sql)) {
                // desabilita a atomicidade automática do sql,
                // passando a responsabilidade da transação para o dev fazer de forma manual
                connection.setAutoCommit(false);
                var i = 1;
                for (var entity : entities) {
                    statement.setString(1, entity.getName());
                    statement.setBigDecimal(2, entity.getSalary());
                    var timestamp = Timestamp.valueOf(entity.getBirthday().atZoneSimilarLocal(UTC).toLocalDateTime());
                    statement.setTimestamp(3, timestamp);
                    statement.addBatch();
                    // dividir o batch em lotes de mil
                    if (i == 1000) {
                        statement.executeBatch();
                        i = 1;
                    }
                    i++;
                }
                connection.commit();
            } catch (SQLException ex) {
                // reverte a transação caso o statement (transação) falhe
                connection.rollback();
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update(final EmployeeEntity entity) {
        try (
                var connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement(
                        "UPDATE employees SET name = ?, salary = ?, birthday = ? WHERE id = ?;"
                )
        ) {
            statement.setString(1, entity.getName());
            statement.setBigDecimal(2, entity.getSalary());
            statement.setTimestamp(3,
                    Timestamp.valueOf(entity.getBirthday().atZoneSimilarLocal(UTC).toLocalDateTime())
            );
            statement.setLong(4, entity.getId());

            statement.executeUpdate();
            statement.getUpdateCount();
            System.out.printf("Foram afetados %s registros na base de dados.", statement.getUpdateCount());
            if (statement instanceof StatementImpl impl) {
                entity.setId(impl.getLastInsertID());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void delete(final long id) {
        try (
                var connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement("DELETE FROM employees WHERE id = ?;")
        ) {
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<EmployeeEntity> findAll() {
        List<EmployeeEntity> entities = new ArrayList<>();
        var sql = """
                 SELECT e.*,
                        c.id contact_id,
                        c.description,
                        c.type
                 FROM employees e\s
                 INNER JOIN contacts c\s
                 ON c.employee_id = e.id;
                """;
        try (
                var connection = ConnectionUtil.getConnection();
                var statement = connection.createStatement()
        ) {
            statement.executeQuery(sql);
            var resultSet = statement.getResultSet();
            while (resultSet.next()) {
                var entity = new EmployeeEntity();
                entity.setId(resultSet.getLong("id"));
                entity.setName(resultSet.getString("name"));
                entity.setSalary(resultSet.getBigDecimal("salary"));
                var birthdayInstant = resultSet.getTimestamp("birthday").toInstant();
                entity.setBirthday(OffsetDateTime.ofInstant(birthdayInstant, UTC));
                entities.add(entity);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return entities;
    }

    public EmployeeEntity findById(long id) {
        EmployeeEntity entity = new EmployeeEntity();
        // inner join traz apenas os employees que tenham contacts
        // utilizando um left join, haverá retorno de todos os employees, incluindo os que não tenham contacts
        var sql = """
                 SELECT e.id employee_id,
                        e.name,
                        e.salary,
                        e.birthday,
                        c.id contact_id,
                        c.description,
                        c.type\s
                 FROM employees e\s
                 LEFT JOIN contacts c\s
                 ON c.employee_id = e.id\s
                 WHERE e.id = ?;
                """;
        try (
                var connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            statement.executeQuery();
            var resultSet = statement.getResultSet();

            if (resultSet.next()) {
                entity.setId(resultSet.getLong("employee_id"));
                entity.setName(resultSet.getString("name"));
                entity.setSalary(resultSet.getBigDecimal("salary"));
                var birthdayInstant = resultSet.getTimestamp("birthday").toInstant();
                entity.setBirthday(OffsetDateTime.ofInstant(birthdayInstant, UTC));
                // definindo contact
                if (resultSet.getLong("contact_id") != 0) {
                    var contact = new ContactEntity();
                    contact.setId(resultSet.getLong("contact_id"));
                    contact.setDescription(resultSet.getString("description"));
                    contact.setType(resultSet.getString("type"));
                    entity.setContact(contact);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return entity;
    }

    private String formatOffsetDateTime(final OffsetDateTime dateTime) {
        var utcDateTime = dateTime.withOffsetSameInstant(UTC);
        return utcDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
