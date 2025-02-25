package br.com.dio.persistence;

import br.com.dio.persistence.entity.AccessEntity;
import br.com.dio.persistence.entity.ModuleEntity;
import com.mysql.cj.jdbc.StatementImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModuleDAO {
    private final AccessDAO accessDAO = new AccessDAO();

    public void insert(final ModuleEntity module) {
        try (
                var connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement("INSERT INTO modules (name) VALUES (?)");
        ) {
            statement.setString(1, module.getName());
            statement.executeUpdate();
            if (statement instanceof StatementImpl impl) {
                module.setId(impl.getLastInsertID());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public ModuleEntity findById(final long id) {
        var module = new ModuleEntity();
        try (
                var connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement("SELECT * FROM modules WHERE id = ?");
        ) {
            statement.setLong(1, id);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            if (resultSet.next()) {
                module.setId(resultSet.getLong("id"));
                module.setName(resultSet.getString("name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return module;
    }

    public List<ModuleEntity> findAll() {
        var modules = new ArrayList<ModuleEntity>();
        try (
                var connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement("SELECT * FROM modules");
        ) {
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            while (resultSet.next()) {
                var module = new ModuleEntity();
                module.setId(resultSet.getLong("id"));
                module.setName(resultSet.getString("name"));
                modules.add(module);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return modules;
    }

    public List<ModuleEntity> findModulesByEmployeeId(final long employeeId) {
        var modules = new ArrayList<ModuleEntity>();
        var accesses = accessDAO.findAccessesByEmployeeId(employeeId);
        var i = 0;
        var sql = "SELECT * FROM modules WHERE id = " + accesses.get(i).getModule().getId();
        while (i < accesses.size() - 1) {
            i++;
            sql = sql.concat(" UNION SELECT * FROM modules WHERE id = " + accesses.get(i).getModule().getId());
        }
        try (
                var connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement(sql)
        ) {
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            while (resultSet.next()) {
                var module = new ModuleEntity();
                module.setId(resultSet.getLong("id"));
                module.setName(resultSet.getString("name"));
                modules.add(module);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return modules;
    }
}
