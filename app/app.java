package app;

import panels.AddExpensePanel;
import panels.MonthlyReportPanel;
import panels.ViewExpensesPanel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;


public class app implements ActionListener {
    JFrame frame;
    JLabel label;
    JPanel buttonPanel;
    JPanel centerWrapperPanel;
    JPanel mainPanel;
    JButton addExpenseButton;
    JButton viewExpensesButton;
    JButton monthlyReportButton;
    JButton exitButton;
    CardLayout layout;
    JPanel homePanel;
    JPanel addExpensePanel;
    JPanel viewExpensesPanel;
    JPanel monthlyReportPanel;  

    app(){
        frame = new JFrame("Personal Expense Tracker");
        label = new JLabel("Welcome to your Personal Expense Tracker!");
        buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        centerWrapperPanel = new JPanel(new GridBagLayout());
        addExpenseButton = new JButton("Add Expense");
        viewExpensesButton = new JButton("View Expenses");
        monthlyReportButton = new JButton("Monthly Report");
        exitButton = new JButton("Exit");

        //navigation using panels
        layout = new CardLayout();
        mainPanel = new JPanel(layout);
        homePanel = new JPanel(new BorderLayout());
        addExpensePanel = new AddExpensePanel(layout,mainPanel);
        viewExpensesPanel = new ViewExpensesPanel(layout,mainPanel);
        monthlyReportPanel = new MonthlyReportPanel(layout, mainPanel);

        //adding sub-panels inside main panel
        homePanel.add(label, BorderLayout.NORTH);
        homePanel.add(centerWrapperPanel,BorderLayout.CENTER);
        mainPanel.add(homePanel,"home");
        mainPanel.add(addExpensePanel,"addExpense");
        mainPanel.add(viewExpensesPanel,"viewExpenses");
        mainPanel.add(monthlyReportPanel,"monthlyReport");

        //adding action listeners to the button
        addExpenseButton.addActionListener(this);
        viewExpensesButton.addActionListener(this);
        monthlyReportButton.addActionListener(this);
        exitButton.addActionListener(this);

        // button sizing
        addExpenseButton.setPreferredSize(new Dimension(150, 40));
        viewExpensesButton.setPreferredSize(new Dimension(150, 40));
        monthlyReportButton.setPreferredSize(new Dimension(150, 40));
        exitButton.setPreferredSize(new Dimension(150, 40));

        // add buttons to panel
        buttonPanel.add(addExpenseButton);
        buttonPanel.add(viewExpensesButton);
        buttonPanel.add(monthlyReportButton);
        buttonPanel.add(exitButton);
        centerWrapperPanel.add(buttonPanel);

        // styles
        label.setOpaque(true);
        label.setBackground(Color.LIGHT_GRAY);
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        centerWrapperPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        addExpenseButton.setForeground(Color.BLACK);
        addExpenseButton.setBackground(Color.GRAY);
        viewExpensesButton.setForeground(Color.BLACK);
        viewExpensesButton.setBackground(Color.GRAY);
        monthlyReportButton.setForeground(Color.BLACK);
        monthlyReportButton.setBackground(Color.GRAY);
        exitButton.setForeground(Color.BLACK);
        exitButton.setBackground(Color.GRAY);

        //panel styles
        addExpensePanel.setBackground(Color.LIGHT_GRAY);
        viewExpensesPanel.setBackground(Color.LIGHT_GRAY);
        monthlyReportPanel.setBackground(Color.LIGHT_GRAY);

        // add components to frame
        frame.setLayout(new BorderLayout());
        frame.add(mainPanel);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        // set default parameters to the main container
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setVisible(true);
        frame.setSize(450, 410);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        label.setFocusable(true);
        label.requestFocusInWindow();
    }
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        if(source == addExpenseButton){
            layout.show(mainPanel,"addExpense");
        } else if(source == viewExpensesButton){
            layout.show(mainPanel,"viewExpenses");
        } else if(source == monthlyReportButton){
            layout.show(mainPanel,"monthlyReport");
        } else if(source == exitButton){
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
    }
    public static void main(String args[]) {
        new app();
    }
}
