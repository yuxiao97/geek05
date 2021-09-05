package com.yuxiao.geek05.week05.db;

import com.yuxiao.geek05.week05.db.annotation.MyTransactional;
import com.yuxiao.geek05.week05.pojo.Student;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;

/**
 * @author yangjunwei
 * @date 2021-09-05 21:52
 */
@Component
public class TransactionCrud {

    @Autowired
    private TransactionConfig transactionConfig;

    @Autowired
    private HikariDataSource hikariDataSource;

    @MyTransactional(rollback = {Exception.class})
    public int insertStudent(Student student) throws SQLException {
        Connection connection = transactionConfig.getConnectionThreadLocal().get();
        String sql = "insert into student(`student_id`, `name`) value(?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, student.getStudentId());
        preparedStatement.setString(2, student.getName());
        preparedStatement.execute();
        // 测试通过自定义MyTransactional和AOP进行事务管理
        int i = 1 / 0;
        return 1;
    }



    public Student getByStudentId(String studentId) throws SQLException {
        Connection connection = hikariDataSource.getConnection();
        String sql = "select * from student where student_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, studentId);
        ResultSet resultSet = preparedStatement.executeQuery();
        Student student = null;
        if(resultSet.next()) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            student = new Student();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                if("student_id".equals(columnName)) {
                    student.setStudentId(resultSet.getString(i));
                } else if("name".equals(columnName)) {
                    student.setName(resultSet.getString(i));
                }
            }
        }
        return student;
    }

}
