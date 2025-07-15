package panels;
import javax.swing.*;
import com.github.lgooddatepicker.components.*;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.*;
import java.time.*;
import service.ExpenseService;
//import java.time.format.DateTimeFormatter;

public class AddExpensePanel extends JPanel implements ActionListener {
    private CardLayout layout;
    private JPanel parentPanel;

    // labels
    JLabel amountLabel;
    JLabel categoryLabel;
    JLabel dateLabel;
    JLabel descLabel;
    JLabel warningLabel;
    JLabel invalidTypeLabel;
    JLabel statusLabel;

    // fields
    JTextField amountTextField;
    DatePicker datePicker;
    JTextArea descArea;
    JComboBox<String> categoriesBox;
    JButton submitButton;
    JButton backButton;

    public AddExpensePanel(CardLayout layout, JPanel parentPanel) {
        this.layout = layout;
        this.parentPanel = parentPanel;


        // labels
        amountLabel = new JLabel("Enter Amount:");
        categoryLabel = new JLabel("Category:");
        dateLabel = new JLabel("Date:");
        descLabel = new JLabel("Description:");
        warningLabel = new JLabel();
        warningLabel.setForeground(Color.red);
        invalidTypeLabel = new JLabel();
        invalidTypeLabel.setForeground(Color.red);
        statusLabel = new JLabel();
        statusLabel.setForeground(Color.green);

        // fields
        amountTextField = new JTextField();
        String[] categories = { "Select Category", "Essentials", "Lifestyle", "Savings & Investments", "Others" };
        datePicker = new DatePicker();
        descArea = new JTextArea(30, 80);
        categoriesBox = new JComboBox<String>(categories);
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        categoriesBox.setVisible(true);
        backButton = new JButton("⟵");
        backButton.addActionListener(e->layout.show(parentPanel, "home"));

        // set bounds
        amountLabel.setBounds(30, 30, 100, 30);
        amountTextField.setBounds(150, 30, 200, 30);
        categoryLabel.setBounds(30, 80, 100, 30);
        categoriesBox.setBounds(150, 80, 200, 30);
        dateLabel.setBounds(30, 130, 100, 30);
        datePicker.setBounds(150, 130, 200, 30);
        descLabel.setBounds(30, 180, 100, 30);
        descArea.setBounds(150, 180, 200, 80);
        submitButton.setBounds(30, 275, 320, 30);
        warningLabel.setBounds(30, 300, 175, 30);
        invalidTypeLabel.setBounds(30, 325, 300, 30);
        statusLabel.setBounds(30, 300, 175, 30);
        backButton.setBounds(5, 5, 50, 25);

        backButton.setBackground(Color.LIGHT_GRAY);
        backButton.setBorder(null);
        submitButton.setBackground(Color.gray);
        submitButton.setForeground(Color.black);

        // add components to main container
        this.add(amountLabel);
        this.add(amountTextField);
        this.add(categoryLabel);
        this.add(categoriesBox);
        this.add(dateLabel);
        this.add(datePicker);
        this.add(descLabel);
        this.add(descArea);
        this.add(submitButton);
        this.add(warningLabel);
        this.add(invalidTypeLabel);
        this.add(statusLabel);
        this.add(backButton);

        this.setLayout(null);
    }

    public void actionPerformed(ActionEvent e) {
        boolean fieldCheck = ((amountTextField.getText().trim().isEmpty())
                || (categoriesBox.getSelectedItem().toString().equals("Select Category"))
                || (datePicker.getDate() == null));
        if (fieldCheck == true) {
            warningLabel.setText("Fields cannot be null!");
        } else {
            warningLabel.setText(null);
            try {
                double amount = Double.parseDouble(amountTextField.getText());
                String category = categoriesBox.getSelectedItem().toString();
                LocalDate selectedDate = datePicker.getDate();
                //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
                //String formattedDate = selectedDate.format(formatter);
                String description = descArea.getText();
                invalidTypeLabel.setText(null);

                ExpenseService service = new ExpenseService();
                boolean success = service.addExpense(amount, category, selectedDate, description);
                if(success){
                    statusLabel.setText("Expense added successfully!");
                } else{
                    statusLabel.setText("Error in adding expense!");
                }
            } catch (NumberFormatException e1) {
                invalidTypeLabel.setText("Invalid Input Type: " + e1.getMessage());
                System.out.println(e1.getMessage());
            }
        }
    }
}
