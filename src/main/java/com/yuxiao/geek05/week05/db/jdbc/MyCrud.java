package com.yuxiao.geek05.week05.db.jdbc;

import com.yuxiao.geek05.week05.db.annotation.MyTransactional;
import com.yuxiao.geek05.week05.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangjunwei
 * @date 2021-09-05 10:21
 */
@Component
public class MyCrud {

    @Autowired
    private Connection connection;


    @MyTransactional(rollback = {Exception.class})
    public int insertStudent(Student student) throws SQLException {
        String sql = "insert into student(`student_id`, `name`) value(?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, student.getStudentId());
        preparedStatement.setString(2, student.getName());
        return preparedStatement.execute() ? 1 : 0;
    }



    public Student get(String studentId) throws SQLException {
        String sql = "select student_id, name from student where student_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, studentId);
        ResultSet resultSet = preparedStatement.executeQuery();
        Student student = new Student();
        while (resultSet.next()) {
            ResultSetMetaData rsMeta = resultSet.getMetaData();
            int columnCount=rsMeta.getColumnCount();
            for (int i=1; i<=columnCount; i++) {
                String columnName = rsMeta.getColumnName(i);
                if("student_id".equals(columnName)){
                    student.setStudentId(resultSet.getString(i));
                }
                if("name".equals(columnName)){
                    student.setName(resultSet.getString(i));
                }
            }
        }
        return student;
    }



    public int delete(String studentId) throws SQLException {
        String sql = "delete from student where student_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, studentId);
        return preparedStatement.execute() ? 1 : 0;
    }



    public List<Student> listStudent() {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from student");
            ResultSet resultSet = statement.executeQuery();
            List<Student> result = new ArrayList<>();
            Student student;
            while (resultSet.next()) {
                ResultSetMetaData rsMeta = resultSet.getMetaData();
                int columnCount=rsMeta.getColumnCount();
                student = new Student();
                for (int i=1; i<=columnCount; i++) {
                    String columnName = rsMeta.getColumnName(i);
                    if("student_id".equals(columnName)){
                        student.setStudentId(resultSet.getString(i));
                    }
                    if("name".equals(columnName)){
                        student.setName(resultSet.getString(i));
                    }
                }
                result.add(student);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


}
