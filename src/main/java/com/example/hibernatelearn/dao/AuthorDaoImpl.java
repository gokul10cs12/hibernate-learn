package com.example.hibernatelearn.dao;

import com.example.hibernatelearn.domain.Author;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class AuthorDaoImpl implements AuthorDao {
    private final DataSource dataSource;
    Connection connection=null;
    Statement statement= null;
    PreparedStatement preparedStatement =null;
    ResultSet resultSet = null;



    public AuthorDaoImpl(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    @Override
    public Author getById(Long id) {


        try {
            connection = dataSource.getConnection();
            //changes for implementing prepared statement.
            preparedStatement = connection.prepareStatement("SELECT  * FROM author where id =?");// this will cache
            // the statement in to the connection to avoid identifying the query as new one each time. and use less cache.
            // also the query compiled only once which improved the performance.

            //bind the parameter
            preparedStatement.setLong(1, id);
//            resultSet = statement.executeQuery("SELECT  * FROM author where id =" + id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return getAuthorFromResult(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                closeAll(resultSet, preparedStatement, connection);

            }catch (SQLException e){
                e.printStackTrace();
            }

        }
        return null;
    }


    @Override
    public Author getAuthorByName(String firstName, String lastName) {

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT  * FROM author where first_name =? OR last_name=?");// this will cache
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            resultSet = preparedStatement.executeQuery(); // execute query only for queries, not for updates.

            if (resultSet.next()){
                return getAuthorFromResult(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeAll(resultSet, preparedStatement, connection);
            }catch (SQLException e){
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    public Author saveNewAuthor(Author author) {

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO author (first_name, last_name) values (?,?)");// this will cache
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            preparedStatement.execute(); // execute() required for insertion.because
            // we are not getting the result back. because of insertion.

            Statement statement = connection.createStatement();

            resultSet =statement.executeQuery("SELECT LAST_INSERT_ID()");// mySQL only query to get the last inserted id value

            if (resultSet.next()){
                Long savedId = resultSet.getLong(1);
                return this.getById(savedId);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeAll(resultSet, preparedStatement, connection);
            }catch (SQLException e){
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    public Author updateAuthor(Author author) {
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE author set first_name =?, last_name=? where author.id=?");// this will cache
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            preparedStatement.setLong(3, author.getId());
            preparedStatement.execute(); // execute() required for update.because
            // we are not getting the result back. because of update.

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeAll(resultSet, preparedStatement, connection);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return this.getById(author.getId());
    }

    @Override
    public void deleteAuthorById(Long id) {
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("delete from author where id=?");// this will cache
            preparedStatement.setLong(1, id);
            preparedStatement.execute(); // execute() required for update.because
            // we are not getting the result back. because of update.

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeAll(null, preparedStatement, connection);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    private Author getAuthorFromResult(ResultSet resultSet) throws SQLException {
        Author author= new Author();
        author.setId(resultSet.getLong("id"));
        author.setFirstName(resultSet.getString("first_name"));
        author.setLastName(resultSet.getString("last_name"));
        return author;
    }

    private void closeAll(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) throws SQLException {
        if (resultSet != null){
            resultSet.close();
        }
        if (preparedStatement != null){
            resultSet.close();
        }
        if (connection !=null){
            connection.close();
        }
    }
}
