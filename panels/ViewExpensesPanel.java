package panels;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import service.ExpenseService;

public class ViewExpensesPanel extends JPanel {
    private CardLayout layout;
    private JPanel parentPanel;
    private ExpenseService service;
    private JTable table;
    private JButton backButton;
    private JButton exportButton;

    public ViewExpensesPanel(CardLayout layout, JPanel parentPanel) {
        this.layout = layout;
        this.parentPanel = parentPanel;
        this.service = new ExpenseService();
        this.setLayout(new BorderLayout());

        String[] columnNames = {"Amount", "Category", "Date"};
        Object[][] data = service.viewExpenses();

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(28);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : new Color(220, 220, 220));
                } else {
                    c.setBackground(new Color(180, 200, 240));
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.LIGHT_GRAY);
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.LIGHT_GRAY);
        backButton = new JButton("⟵");
        backButton.addActionListener(e -> layout.show(parentPanel, "home"));
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(null);
        topPanel.add(backButton);
        exportButton = new JButton("Export to CSV");
        exportButton.setBackground(Color.DARK_GRAY);
        exportButton.setForeground(Color.WHITE);
        exportButton.setBorder(null);
        exportButton.addActionListener(e -> exportToCSV());
        topPanel.add(exportButton);
        this.setBackground(Color.LIGHT_GRAY);
        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }
    private void exportToCSV() {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Save As");
            chooser.setSelectedFile(new java.io.File("expenses.csv"));
            int result = chooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                java.io.File file = chooser.getSelectedFile();
                PrintWriter writer = new PrintWriter(new FileWriter(file));
                for (int i = 0; i < table.getColumnCount(); i++) {
                    writer.print(table.getColumnName(i));
                    if (i < table.getColumnCount() - 1) writer.print(",");
                }
                writer.println();
                for (int row = 0; row < table.getRowCount(); row++) {
                    for (int col = 0; col < table.getColumnCount(); col++) {
                        writer.print(table.getValueAt(row, col));
                        if (col < table.getColumnCount() - 1) writer.print(",");
                    }
                    writer.println();
                }

                writer.close();
                JOptionPane.showMessageDialog(this, "Exported successfully!");
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Export failed: " + ex.getMessage());
        }
    }
}
