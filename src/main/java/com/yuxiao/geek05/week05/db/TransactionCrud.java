package com.yuxiao.geek05.week05.db;

import com.yuxiao.geek05.week05.db.annotation.MyTransactional;
import com.yuxiao.geek05.week05.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author yangjunwei
 * @date 2021-09-05 21:52
 */
@Component
public class TransactionCrud {

    @Autowired
    private MyJdbcTemplate myJdbcTemplate;


    @MyTransactional(rollback = {Exception.class})
    public int insertStudent(Student student) throws SQLException {
        String sql = "insert into student(`student_id`, `name`) value(?, ?)";
        myJdbcTemplate.execute(sql, student.getStudentId(), student.getName());
        // 测试通过自定义MyTransactional和AOP进行事务管理
        int i = 1 / 0;
        return 1;
    }



    public Student getByStudentId(String studentId) throws SQLException {
        String sql = "select * from student where student_id = ?";
        ResultSet resultSet = myJdbcTemplate.executeQuery(sql, studentId);
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
