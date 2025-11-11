import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ExpenseTracker extends JFrame implements ActionListener {
    private JTextField nameField, amountField;
    private JButton addButton, deleteButton;
    private DefaultListModel<String> expenseListModel;
    private JList<String> expenseList;
    private JLabel totalLabel;
    private double total = 0;
    private java.util.List<Double> amounts = new ArrayList<>();

    public ExpenseTracker() {
        setTitle("Expense Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Expense Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Amount (₹):"));
        amountField = new JTextField();
        inputPanel.add(amountField);

        addButton = new JButton("Add Expense");
        addButton.addActionListener(this);
        inputPanel.add(addButton);

        deleteButton = new JButton("Delete Selected");
        deleteButton.addActionListener(this);
        inputPanel.add(deleteButton);

        add(inputPanel, BorderLayout.NORTH);

        expenseListModel = new DefaultListModel<>();
        expenseList = new JList<>(expenseListModel);
        add(new JScrollPane(expenseList), BorderLayout.CENTER);

        totalLabel = new JLabel("Total: ₹0.00", JLabel.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(totalLabel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String name = nameField.getText().trim();
            String amountText = amountField.getText().trim();

            if (!name.isEmpty() && !amountText.isEmpty()) {
                try {
                    double amount = Double.parseDouble(amountText);
                    expenseListModel.addElement(name + " - ₹" + amount);
                    amounts.add(amount);
                    total += amount;
                    totalLabel.setText(String.format("Total: ₹%.2f", total));
                    nameField.setText("");
                    amountField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Enter a valid number for amount!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter both name and amount.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == deleteButton) {
            int selectedIndex = expenseList.getSelectedIndex();
            if (selectedIndex != -1) {
                total -= amounts.get(selectedIndex);
                amounts.remove(selectedIndex);
                expenseListModel.remove(selectedIndex);
                totalLabel.setText(String.format("Total: ₹%.2f", total));
            } else {
                JOptionPane.showMessageDialog(this, "Select an expense to delete.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExpenseTracker().setVisible(true));
    }
}
