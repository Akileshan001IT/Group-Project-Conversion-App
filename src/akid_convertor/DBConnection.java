/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package akid_convertor;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.*;

/**
 *
 * @author Imalka_c
 */
public class DBConnection {

    Signup sign = new Signup();
    Login login = new Login();
    FogotPassword forgotPassword = new FogotPassword();

    String username;
    String firstname;
    String lastname;
    String gender;
    String password;

    void insertData(String username, String password, String firstname, String lastname, String gender) {
        try {
            //create the registration to the driver - 01
            Class.forName("org.mariadb.jdbc.Driver");

            //create the connection - 02
            Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/groupproject", "root", "");

            String checkQuery = "SELECT COUNT(*) FROM signupdetails WHERE Username = ?";
            PreparedStatement checkStatement = con.prepareStatement(checkQuery);
            checkStatement.setString(1, username);
            ResultSet resultSet = checkStatement.executeQuery();

            resultSet.next();
            int count = resultSet.getInt(1);

            if (count == 0) {
                String insertQuery = "INSERT INTO signupdetails (Username,Password,Firstname,Lastname,Gender) values(?,?,?,?,?)";
                PreparedStatement insertStatement = con.prepareStatement(insertQuery);
                insertStatement.setString(1, username);
                insertStatement.setString(2, password);
                insertStatement.setString(3, firstname);
                insertStatement.setString(4, lastname);
                insertStatement.setString(5, gender);
                insertStatement.executeUpdate();

                JOptionPane.showMessageDialog(sign, "SignUp Successful \nNow go for login.", "Information", JOptionPane.INFORMATION_MESSAGE);
                new Login().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(sign, "Username has already taken. Sorry try again");
                new Signup().setVisible(true);
            }
        } catch (Exception e) {
            System.out.println("Hello " + e);
            JOptionPane.showMessageDialog(sign, "Error in signup to database. \nPlease try again later.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    void checkData(String username, String password) {
        try {
            //create the registration to the driver - 01
            Class.forName("org.mariadb.jdbc.Driver");

            //create the connection - 02
            Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/groupproject", "root", "");

            //create SQL statement object - 03
            Statement statement = con.createStatement();

            //Get the resultSet object - 04
            ResultSet resultSet = statement.executeQuery("SELECT * FROM signupdetails WHERE Username='" + username + "' and Password='" + password + "'");
            if (resultSet.next()) {
                JOptionPane.showMessageDialog(login, "Login Successful", "Information", JOptionPane.INFORMATION_MESSAGE);
                new Dashboard(username).setVisible(true);

            } else {
                JOptionPane.showMessageDialog(login, "ID number or Password can not find", "Warning", JOptionPane.WARNING_MESSAGE);
                new Login().setVisible(true);
            }

        } catch (Exception e) {
            System.out.println("Hello " + e);
            JOptionPane.showMessageDialog(login, "Error in login to system.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    void getData(String username) {
        try {

            //create the registration to the driver - 01
            Class.forName("org.mariadb.jdbc.Driver");

            //create the connection - 02
            Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/groupproject", "root", "");

            //create SQL statement object - 03
            Statement statement = con.createStatement();

            //Get the resultSet object - 04
            ResultSet resultSet = statement.executeQuery("SELECT * FROM signupdetails WHERE Username ='" + username + "'");

            //Print the resultSet - 05
            while (resultSet.next()) {
                this.username = resultSet.getString(2);
                this.firstname = resultSet.getString(4);
                this.lastname = resultSet.getString(5);
                this.gender = resultSet.getString(6);
                this.password = resultSet.getString(3);
            }

            //close the connection - 06
            con.close();

        } catch (Exception e) {
            System.out.println("Hello " + e);
        }
    }

    void changePassword(String username, String lastname, String newPassword) {
        try {

            //create the registration to the driver - 01
            Class.forName("org.mariadb.jdbc.Driver");

            //create the connection - 02
            Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/groupproject", "root", "");

            //create SQL statement object - 03
            Statement statement = con.createStatement();

            //Get the resultSet object - 04
            ResultSet resultSet = statement.executeQuery("SELECT Lastname from signupdetails WHERE Username ='" + username + "'");

            //Print the resultSet - 05
            if (resultSet.next()) {
                String lastnameCheck = resultSet.getString("Lastname");
                if (lastnameCheck.equals(lastname)) {
                    PreparedStatement stmt = con.prepareStatement("UPDATE signupdetails SET Password = ? WHERE Username = ?");
                    stmt.setString(2, username);
                    stmt.setString(1, newPassword);

                    stmt.executeUpdate();

                    JOptionPane.showMessageDialog(forgotPassword, "Password update successfully.", "Information", JOptionPane.INFORMATION_MESSAGE);

                } else {
                    JOptionPane.showMessageDialog(forgotPassword, "Lastname do not match...", "Warning", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(forgotPassword, "Username cannot found...", "Warning", JOptionPane.ERROR_MESSAGE);
            }

            //close the connection - 06
            con.close();

        } catch (Exception e) {
            System.out.println("Hello, " + e);
        }
    }

    void saveData(String username, String fromType, String toType, double preValue, double pastValue) {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        try {
            //create the registration to the driver - 01
            Class.forName("org.mariadb.jdbc.Driver");

            //create the connection - 02
            Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/groupproject", "root", "");

            //Push data to database
            PreparedStatement stmt = con.prepareStatement("INSERT INTO historydetails (Username,Date,Time,FromType,ToType,PreValue,PastValue) values(?,?,?,?,?,?,?)");
            stmt.setString(1, username);
            stmt.setDate(2, Date.valueOf(date));
            stmt.setTime(3, Time.valueOf(time));
            stmt.setString(4, fromType);
            stmt.setString(5, toType);
            stmt.setDouble(6, preValue);
            stmt.setDouble(7, pastValue);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(sign, "Data added to the history.", "Information", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            System.out.println("Hello " + e);
        }
    }

}
