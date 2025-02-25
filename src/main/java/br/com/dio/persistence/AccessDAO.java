package br.com.dio.persistence;

import br.com.dio.persistence.entity.AccessEntity;
import br.com.dio.persistence.entity.ModuleEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccessDAO {

    public void insert(AccessEntity access) {
        var sql = "INSERT INTO accesses (employee_id, module_id) VALUES (?, ?)";
        try (
                var connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement(sql);
            )
        {
            statement.setLong(1, access.getEmployee().getId());
            statement.setLong(2, access.getModule().getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<AccessEntity> findAccessesByEmployeeId(final long employeeId) {
        var accesses = new ArrayList<AccessEntity>();
        try(
                var connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement("SELECT * FROM accesses WHERE employee_id = ?");
           ) {
            statement.setLong(1, employeeId);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            while (resultSet.next()) {
                var access = new AccessEntity();
                access.setModule(new ModuleEntity());
                access.getModule().setId(resultSet.getLong("module_id"));
                accesses.add(access);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return accesses;
    }
}
