package service;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import io.github.cdimascio.dotenv.Dotenv;

public class ExpenseService {
    Dotenv dotenv = Dotenv.load();
    String url = dotenv.get("DATABASE_URL");
    String user = dotenv.get("DATABASE_USER");
    String password = dotenv.get("DATABASE_PASSWORD");

    public boolean addExpense(double amount, String category, LocalDate date, String description) {
        boolean status = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO expense(amount, category, date, description) VALUES (?, ?, ?, ?)");

            statement.setDouble(1, amount);
            statement.setString(2, category);
            statement.setDate(3, java.sql.Date.valueOf(date));
            statement.setString(4, description);

            int ins = statement.executeUpdate();
            connection.close(); // close before returning

            if (ins == 1) {
                status = true;
            }
        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
        }

        return status;
    }

    public Object[][] viewExpenses() {
        List<Object[]> tempList = new ArrayList<>();
        double amount;
        String category;
        Date date;
        try {
            String query = "SELECT amount,category,date FROM expense";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet rSet = statement.executeQuery(query);
            while (rSet.next()) {
                amount = rSet.getDouble("amount");
                category = rSet.getString("category");
                date = rSet.getDate("date");
                tempList.add(new Object[] { amount, category, date });
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
        }
        Object dataObject[][] = new Object[tempList.size()][3];
        for (int i = 0; i < tempList.size(); i++) {
            dataObject[i] = tempList.get(i);
        }
        return dataObject;
    }

    public Map<String, Double> getMonthlyCategoryTotals() {
        Map<String, Double> totals = new HashMap<>();

        String query = "SELECT category, SUM(amount) as total " +
                "FROM expense " +
                "WHERE MONTH(date) = MONTH(CURRENT_DATE()) AND YEAR(date) = YEAR(CURRENT_DATE()) " +
                "GROUP BY category";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                String category = rs.getString("category");
                double total = rs.getDouble("total");
                totals.put(category, total);
            }

            connection.close();

        } catch (Exception e) {
            System.out.println("Exception in getMonthlyCategoryTotals: " + e.getMessage());
        }

        return totals;
    }

}
