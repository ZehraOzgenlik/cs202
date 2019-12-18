package tr.edu.ozyegin.cs202.service.department;

import tr.edu.ozyegin.cs202.model.Department;
import tr.edu.ozyegin.cs202.repository.DatabaseManager;
import tr.edu.ozyegin.cs202.util.Utils;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DepartmentService {

    public List<Department> getDepartments() throws IOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = DatabaseManager.getConnection().prepareStatement(
                    "SELECT departments.id, departments.name"
                            + " FROM departments"
            );
            resultSet = statement.executeQuery();

            List<Department> departments = new ArrayList<>();
            while (resultSet.next()) {
                Department department = new Department();
                department.setId(resultSet.getInt("departments.id"));
                department.setName(resultSet.getString("departments.name"));

                departments.add(department);
            }
            return departments;
        } catch (Exception e) {
            Utils.logError(e);
            throw new IOException(e);
        } finally {
            DatabaseManager.closeResultSet(resultSet);
            DatabaseManager.closeStatement(statement);
        }
    }
}
