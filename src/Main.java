import db.DB;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Connection conn;
        PreparedStatement st = null;

        try {
            conn = DB.getConnection();
            st = conn.prepareStatement(
                    "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentID)" +
                    "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            System.out.print("Entre com o nome: ");
            String name = sc.nextLine();
            System.out.print("Entre com o email: ");
            String email = sc.next();
            System.out.print("Entre com a data de nascimento (dd/MM/yyyy): ");
            String birthDate = sc.next();
            System.out.print("Entre com o salario base: ");
            double baseSalary = sc.nextDouble();
            System.out.print("Entre com o id do departamento: ");
            int departmentId = sc.nextInt();

            st.setString(1, name);
            st.setString(2, email);
            st.setDate(3, new java.sql.Date(sdf.parse(birthDate).getTime()));
            st.setDouble(4, baseSalary);
            st.setInt(5, departmentId);

            int rowsAffected = st.executeUpdate();

            if(rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("Criado com sucesso! Id: " + id);
                }
                DB.closeResultSet(rs);
            } else {
                System.out.println("Nenhuma linha alterada");
            }

        } catch (SQLException | ParseException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
            DB.closeConnection();
        }
    }
}