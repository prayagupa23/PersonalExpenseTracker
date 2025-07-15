package panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;
import service.ExpenseService;

public class MonthlyReportPanel extends JPanel {

    private CardLayout layout;
    private JPanel parentPanel;
    private ExpenseService service;
    private JTable reportTable;
    private JButton backButton;

    public MonthlyReportPanel(CardLayout layout, JPanel parentPanel) {
        this.layout = layout;
        this.parentPanel = parentPanel;
        this.service = new ExpenseService();

        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.LIGHT_GRAY);

        backButton = new JButton("⟵");
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(null);
        backButton.addActionListener(e -> layout.show(parentPanel, "home"));

        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);
        Map<String, Double> categoryTotals = service.getMonthlyCategoryTotals();

        String[] columnNames = { "Category", "Total This Month" };
        Object[][] data = new Object[categoryTotals.size()][2];

        int i = 0;
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            data[i][0] = entry.getKey();
            data[i][1] = String.format("%.2f", entry.getValue()); // Format to 2 decimal places
            i++;
        }
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        reportTable = new JTable(model);
        reportTable.setFillsViewportHeight(true);
        reportTable.setRowHeight(28);
        JScrollPane scrollPane = new JScrollPane(reportTable);
        scrollPane.getViewport().setBackground(Color.LIGHT_GRAY);

        add(scrollPane, BorderLayout.CENTER);
    }
}
