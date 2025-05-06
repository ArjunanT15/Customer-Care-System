import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class CustomerCareSystemApp {

    // Professional Color Scheme with better contrast for black text
    private static final Color PRIMARY_COLOR = new Color(180, 220, 255);  // Light Blue
    private static final Color SECONDARY_COLOR = new Color(240, 240, 240);  // Light Gray
    private static final Color ACCENT_COLOR = new Color(180, 255, 180);  // Light Green
    private static final Color WARNING_COLOR = new Color(255, 180, 180);  // Light Red
    private static final Color TEXT_COLOR = Color.BLACK;  // Black text for better visibility

    // Database Connection
    private static Connection connectToDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/CustomerCareDB";
            String username = "root";
            String password = "dbms";
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage());
            return null;
        }
    }

    // Professional Button Styling with black text
    private static void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(TEXT_COLOR);  // Using black text
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(bgColor.darker());
                button.setForeground(TEXT_COLOR);  // Keep text black on hover
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(bgColor);
                button.setForeground(TEXT_COLOR);
            }
        });
    }

    // Login Page with Professional Design
    private static class LoginPage extends JFrame {
        public LoginPage() {
            setTitle("Customer Care System - Login");
            getContentPane().setBackground(SECONDARY_COLOR);
            setLayout(new BorderLayout(10, 10));

            // Header Panel
            JPanel headerPanel = new JPanel();
            headerPanel.setBackground(PRIMARY_COLOR);
            headerPanel.setBorder(new EmptyBorder(15, 0, 15, 0));
            JLabel titleLabel = new JLabel("CUSTOMER CARE SYSTEM", JLabel.CENTER);
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
            titleLabel.setForeground(TEXT_COLOR);  // Black text
            headerPanel.add(titleLabel);
            add(headerPanel, BorderLayout.NORTH);

            // Form Panel
            JPanel formPanel = new JPanel(new GridBagLayout());
            formPanel.setBackground(SECONDARY_COLOR);
            formPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.anchor = GridBagConstraints.WEST;

            // Username Field
            gbc.gridx = 0; gbc.gridy = 0;
            JLabel userLabel = new JLabel("Username:");
            userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            userLabel.setForeground(TEXT_COLOR);
            formPanel.add(userLabel, gbc);

            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
            JTextField usernameField = new JTextField(20);
            usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            formPanel.add(usernameField, gbc);

            // Password Field
            gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
            JLabel passLabel = new JLabel("Password:");
            passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            passLabel.setForeground(TEXT_COLOR);
            formPanel.add(passLabel, gbc);

            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
            JPasswordField passwordField = new JPasswordField(20);
            passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            formPanel.add(passwordField, gbc);

            // Login Button
            gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.NONE;
            gbc.insets = new Insets(15, 0, 0, 0);
            gbc.anchor = GridBagConstraints.CENTER;
            JButton loginButton = new JButton("LOGIN");
            styleButton(loginButton, PRIMARY_COLOR);
            loginButton.addActionListener(e -> {
                if (usernameField.getText().equals("admin") &&
                        new String(passwordField.getPassword()).equals("admin123")) {
                    new CustomerCareSystem();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials",
                            "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            formPanel.add(loginButton, gbc);

            add(formPanel, BorderLayout.CENTER);

            setSize(450, 350);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
        }
    }

    // Main System Frame with Professional Design
    private static class CustomerCareSystem extends JFrame {
        public CustomerCareSystem() {
            setTitle("Customer Care System");
            getContentPane().setBackground(SECONDARY_COLOR);
            setLayout(new BorderLayout());

            // Main Panel with padding
            JPanel mainPanel = new JPanel(new GridLayout(2, 2, 20, 20));
            mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
            mainPanel.setBackground(SECONDARY_COLOR);

            // Create menu buttons
            mainPanel.add(createMenuButton("Raise Issue", PRIMARY_COLOR, e -> new RaiseIssueFrame()));
            mainPanel.add(createMenuButton("View Issues", PRIMARY_COLOR, e -> new ViewIssuesFrame()));
            mainPanel.add(createMenuButton("Solutions", PRIMARY_COLOR, e -> new SolutionsFrame()));
            mainPanel.add(createMenuButton("Contact", PRIMARY_COLOR, e -> new ContactFrame()));

            add(mainPanel, BorderLayout.CENTER);

            setSize(500, 300);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
        }

        private JButton createMenuButton(String text, Color color, ActionListener action) {
            JButton button = new JButton(text);
            button.setFont(new Font("Segoe UI", Font.BOLD, 14));
            button.setBackground(color);
            button.setForeground(TEXT_COLOR);  // Black text
            button.setFocusPainted(false);
            button.setPreferredSize(new Dimension(150, 80));
            button.setBorder(new EmptyBorder(10, 10, 10, 10));
            button.addActionListener(action);

            // Hover effect
            button.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    button.setBackground(color.darker());
                    button.setForeground(TEXT_COLOR);
                }
                public void mouseExited(MouseEvent evt) {
                    button.setBackground(color);
                    button.setForeground(TEXT_COLOR);
                }
            });

            return button;
        }
    }

    // Raise Issue Frame with Highly Visible Submit Button
    private static class RaiseIssueFrame extends JFrame {
        private JComboBox<String> categoryBox = new JComboBox<>(
                new String[]{"Mobile", "Personal", "Electronic", "Service"});

        public RaiseIssueFrame() {
            setTitle("Raise New Issue");
            getContentPane().setBackground(SECONDARY_COLOR);
            setLayout(new BorderLayout());

            // Form Panel with padding
            JPanel formPanel = new JPanel(new GridBagLayout());
            formPanel.setBackground(SECONDARY_COLOR);
            formPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.anchor = GridBagConstraints.WEST;

            // Name Field
            gbc.gridx = 0; gbc.gridy = 0;
            JLabel nameLabel = new JLabel("Customer Name:");
            nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            nameLabel.setForeground(TEXT_COLOR);
            formPanel.add(nameLabel, gbc);

            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
            JTextField nameField = new JTextField(20);
            nameField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            formPanel.add(nameField, gbc);

            // Email Field
            gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
            JLabel emailLabel = new JLabel("Email:");
            emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            emailLabel.setForeground(TEXT_COLOR);
            formPanel.add(emailLabel, gbc);

            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
            JTextField emailField = new JTextField(20);
            emailField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            formPanel.add(emailField, gbc);

            // Issue Field
            gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
            JLabel issueLabel = new JLabel("Issue Description:");
            issueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            issueLabel.setForeground(TEXT_COLOR);
            formPanel.add(issueLabel, gbc);

            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
            JTextArea issueField = new JTextArea(3, 20);
            issueField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            issueField.setLineWrap(true);
            issueField.setWrapStyleWord(true);
            formPanel.add(new JScrollPane(issueField), gbc);

            // Category Field
            gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
            JLabel categoryLabel = new JLabel("Category:");
            categoryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            categoryLabel.setForeground(TEXT_COLOR);
            formPanel.add(categoryLabel, gbc);

            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
            categoryBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            categoryBox.setPreferredSize(new Dimension(200, 30));
            formPanel.add(categoryBox, gbc);

            // Submit Button - Highly Visible
            gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
            gbc.insets = new Insets(20, 0, 0, 0);
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.CENTER;
            JButton submitButton = new JButton("SUBMIT ISSUE");
            submitButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
            submitButton.setBackground(ACCENT_COLOR);
            submitButton.setForeground(TEXT_COLOR);  // Black text
            submitButton.setFocusPainted(false);
            submitButton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createRaisedBevelBorder(),
                    BorderFactory.createEmptyBorder(8, 25, 8, 25)
            ));
            submitButton.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    submitButton.setBackground(new Color(150, 255, 150));
                    submitButton.setForeground(TEXT_COLOR);
                }
                public void mouseExited(MouseEvent evt) {
                    submitButton.setBackground(ACCENT_COLOR);
                    submitButton.setForeground(TEXT_COLOR);
                }
            });
            submitButton.addActionListener(e -> {
                if (validateInput(nameField.getText(), emailField.getText(), issueField.getText())) {
                    submitIssue(nameField.getText(), emailField.getText(),
                            issueField.getText(), (String)categoryBox.getSelectedItem());
                } else {
                    JOptionPane.showMessageDialog(this, "Please fill all fields",
                            "Validation Error", JOptionPane.WARNING_MESSAGE);
                }
            });
            formPanel.add(submitButton, gbc);

            add(formPanel, BorderLayout.CENTER);

            setSize(550, 450);
            setLocationRelativeTo(null);
            setVisible(true);
        }

        private boolean validateInput(String... fields) {
            for (String field : fields) {
                if (field.trim().isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        private void submitIssue(String name, String email, String issue, String category) {
            try (Connection conn = connectToDatabase()) {
                // Insert customer
                String insertCustomer = "INSERT INTO customers (name, email) VALUES (?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(insertCustomer, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, name);
                    stmt.setString(2, email);
                    stmt.executeUpdate();

                    // Get generated customer ID
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        int customerId = rs.getInt(1);

                        // Insert ticket
                        String insertTicket = "INSERT INTO tickets (customer_id, issue_description, category, status) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement ticketStmt = conn.prepareStatement(insertTicket)) {
                            ticketStmt.setInt(1, customerId);
                            ticketStmt.setString(2, issue);
                            ticketStmt.setString(3, category);
                            ticketStmt.setString(4, "Open");
                            ticketStmt.executeUpdate();

                            // Show success message
                            JOptionPane.showMessageDialog(this,
                                    "Issue submitted successfully!\nSolutions will be displayed.",
                                    "Success", JOptionPane.INFORMATION_MESSAGE);

                            // Show solution
                            showSolution(category);
                            dispose();
                        }
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                        "Database error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void showSolution(String category) {
            String[] solutions = getSolutionsForCategory(category);

            JDialog solutionDialog = new JDialog();
            solutionDialog.setTitle(category + " Solutions");
            solutionDialog.setLayout(new BorderLayout());
            solutionDialog.getContentPane().setBackground(SECONDARY_COLOR);

            JTextArea solutionArea = new JTextArea(String.join("\n\n", solutions));
            solutionArea.setEditable(false);
            solutionArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            solutionArea.setBackground(SECONDARY_COLOR);
            solutionArea.setForeground(TEXT_COLOR);
            solutionArea.setBorder(new EmptyBorder(15, 15, 15, 15));

            solutionDialog.add(new JScrollPane(solutionArea), BorderLayout.CENTER);

            JButton closeButton = new JButton("CLOSE");
            styleButton(closeButton, PRIMARY_COLOR);
            closeButton.addActionListener(e -> solutionDialog.dispose());

            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(SECONDARY_COLOR);
            buttonPanel.setBorder(new EmptyBorder(0, 0, 15, 0));
            buttonPanel.add(closeButton);
            solutionDialog.add(buttonPanel, BorderLayout.SOUTH);

            solutionDialog.setSize(400, 300);
            solutionDialog.setLocationRelativeTo(null);
            solutionDialog.setVisible(true);
        }

        private String[] getSolutionsForCategory(String category) {
            switch (category) {
                case "Mobile":
                    return new String[]{
                            "1. Restart your device - Power off completely and restart",
                            "2. Check for updates - Go to Settings > System > System Update",
                            "3. Reset network settings - Settings > System > Reset > Reset Wi-Fi",
                            "4. Contact manufacturer if issue persists"
                    };
                case "Personal":
                    return new String[]{
                            "1. Reset password - Use 'Forgot Password' feature",
                            "2. Clear browser cache - Chrome/Firefox settings > Clear browsing data",
                            "3. Check service status - Visit our status page at status.example.com",
                            "4. Contact support for further assistance"
                    };
                case "Electronic":
                    return new String[]{
                            "1. Check power connections - Ensure all cables are properly connected",
                            "2. Consult manual - Refer to troubleshooting section in user manual",
                            "3. Update firmware - Download latest from manufacturer's website",
                            "4. Visit authorized service center for hardware issues"
                    };
                case "Service":
                    return new String[]{
                            "1. Verify subscription - Check your account status",
                            "2. Check maintenance schedule - See planned outages on our website",
                            "3. Restart application - Close and reopen the application",
                            "4. Contact technical support for service-specific issues"
                    };
                default:
                    return new String[]{"No solutions available for this category"};
            }
        }
    }

    // View Issues Frame
    private static class ViewIssuesFrame extends JFrame {
        public ViewIssuesFrame() {
            setTitle("View Issues");
            getContentPane().setBackground(SECONDARY_COLOR);
            setLayout(new BorderLayout());

            // Main Panel with padding
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
            mainPanel.setBackground(SECONDARY_COLOR);

            DefaultTableModel model = new DefaultTableModel(
                    new String[]{"ID", "Customer Name", "Issue", "Status"}, 0);
            JTable table = new JTable(model);
            table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            table.setForeground(TEXT_COLOR);
            table.setRowHeight(25);

            // Load data
            try (Connection conn = connectToDatabase()) {
                String query = "SELECT t.id, c.name, t.issue_description, t.status " +
                        "FROM tickets t JOIN customers c ON t.customer_id = c.id";
                ResultSet rs = conn.createStatement().executeQuery(query);
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("issue_description"),
                            rs.getString("status")
                    });
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error loading issues: " + e.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }

            mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

            // Button Panel
            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(SECONDARY_COLOR);
            buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

            JButton markComplete = new JButton("MARK COMPLETE");
            styleButton(markComplete, ACCENT_COLOR);
            markComplete.addActionListener(e -> markComplete(table, model));

            JButton refreshButton = new JButton("REFRESH");
            styleButton(refreshButton, PRIMARY_COLOR);
            refreshButton.addActionListener(e -> refreshData(model));

            JButton backButton = new JButton("BACK");
            styleButton(backButton, WARNING_COLOR);
            backButton.addActionListener(e -> dispose());

            buttonPanel.add(markComplete);
            buttonPanel.add(refreshButton);
            buttonPanel.add(backButton);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);

            add(mainPanel);
            setSize(650, 400);
            setLocationRelativeTo(null);
            setVisible(true);
        }

        private void markComplete(JTable table, DefaultTableModel model) {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select an issue first",
                        "Selection Required", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int ticketId = (int) model.getValueAt(row, 0);
            try (Connection conn = connectToDatabase()) {
                String update = "UPDATE tickets SET status = 'Completed' WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(update);
                stmt.setInt(1, ticketId);
                stmt.executeUpdate();

                model.setValueAt("Completed", row, 3);
                JOptionPane.showMessageDialog(this, "Issue marked as completed",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error updating status: " + e.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void refreshData(DefaultTableModel model) {
            model.setRowCount(0); // Clear existing data
            try (Connection conn = connectToDatabase()) {
                String query = "SELECT t.id, c.name, t.issue_description, t.status " +
                        "FROM tickets t JOIN customers c ON t.customer_id = c.id";
                ResultSet rs = conn.createStatement().executeQuery(query);
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("issue_description"),
                            rs.getString("status")
                    });
                }
                JOptionPane.showMessageDialog(this, "Data refreshed successfully",
                        "Refresh Complete", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error refreshing data: " + e.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Solutions Frame
    private static class SolutionsFrame extends JFrame {
        public SolutionsFrame() {
            setTitle("Solutions Guide");
            getContentPane().setBackground(SECONDARY_COLOR);
            setLayout(new BorderLayout());

            // Main Panel with padding
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
            mainPanel.setBackground(SECONDARY_COLOR);

            JTabbedPane tabs = new JTabbedPane();
            tabs.setFont(new Font("Segoe UI", Font.BOLD, 12));
            tabs.setForeground(TEXT_COLOR);

            tabs.addTab("Mobile", createSolutionPanel(getMobileSolutions()));
            tabs.addTab("Personal", createSolutionPanel(getPersonalSolutions()));
            tabs.addTab("Electronic", createSolutionPanel(getElectronicSolutions()));
            tabs.addTab("Service", createSolutionPanel(getServiceSolutions()));

            mainPanel.add(tabs, BorderLayout.CENTER);

            // Back Button
            JButton backButton = new JButton("BACK");
            styleButton(backButton, PRIMARY_COLOR);
            backButton.addActionListener(e -> dispose());

            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(SECONDARY_COLOR);
            buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
            buttonPanel.add(backButton);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);

            add(mainPanel);
            setSize(500, 350);
            setLocationRelativeTo(null);
            setVisible(true);
        }

        private JPanel createSolutionPanel(String[] solutions) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(new EmptyBorder(15, 15, 15, 15));
            panel.setBackground(SECONDARY_COLOR);

            for (String solution : solutions) {
                JLabel label = new JLabel(solution);
                label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                label.setForeground(TEXT_COLOR);
                label.setAlignmentX(Component.LEFT_ALIGNMENT);
                panel.add(label);
                panel.add(Box.createVerticalStrut(8));
            }
            return panel;
        }

        private String[] getMobileSolutions() {
            return new String[]{
                    "1. Restart your device - Power off completely and restart",
                    "2. Check for updates - Go to Settings > System > System Update",
                    "3. Reset network settings - Settings > System > Reset > Reset Wi-Fi",
                    "4. Contact manufacturer if issue persists"
            };
        }

        private String[] getPersonalSolutions() {
            return new String[]{
                    "1. Reset password - Use 'Forgot Password' feature",
                    "2. Clear browser cache - Chrome/Firefox settings > Clear browsing data",
                    "3. Check service status - Visit our status page at status.example.com",
                    "4. Contact support for further assistance"
            };
        }

        private String[] getElectronicSolutions() {
            return new String[]{
                    "1. Check power connections - Ensure all cables are properly connected",
                    "2. Consult manual - Refer to troubleshooting section in user manual",
                    "3. Update firmware - Download latest from manufacturer's website",
                    "4. Visit authorized service center for hardware issues"
            };
        }

        private String[] getServiceSolutions() {
            return new String[]{
                    "1. Verify subscription - Check your account status",
                    "2. Check maintenance schedule - See planned outages on our website",
                    "3. Restart application - Close and reopen the application",
                    "4. Contact technical support for service-specific issues"
            };
        }
    }

    // Contact Frame
    private static class ContactFrame extends JFrame {
        public ContactFrame() {
            setTitle("Contact Support");
            getContentPane().setBackground(SECONDARY_COLOR);
            setLayout(new BorderLayout());

            // Main Panel with padding
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
            mainPanel.setBackground(SECONDARY_COLOR);

            JLabel header = new JLabel("Support Contacts");
            header.setFont(new Font("Segoe UI", Font.BOLD, 18));
            header.setForeground(TEXT_COLOR);
            header.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainPanel.add(header);
            mainPanel.add(Box.createVerticalStrut(25));

            mainPanel.add(createContactItem("✉ Email:", "support@company.com"));
            mainPanel.add(Box.createVerticalStrut(15));
            mainPanel.add(createContactItem("📞 Phone:", "1800-123-4567"));
            mainPanel.add(Box.createVerticalStrut(15));
            mainPanel.add(createContactItem("🕒 Hours:", "Monday-Friday: 9AM-5PM"));
            mainPanel.add(Box.createVerticalStrut(15));
            mainPanel.add(createContactItem("🏢 Address:", "123 Support Street, Tech City"));
            mainPanel.add(Box.createVerticalStrut(25));

            // Back Button
            JButton backButton = new JButton("BACK");
            styleButton(backButton, PRIMARY_COLOR);
            backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            backButton.addActionListener(e -> dispose());
            mainPanel.add(backButton);

            add(mainPanel);
            setSize(400, 350);
            setLocationRelativeTo(null);
            setVisible(true);
        }

        private JPanel createContactItem(String label, String value) {
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
            panel.setBackground(SECONDARY_COLOR);
            panel.setMaximumSize(new Dimension(350, 30));

            JLabel iconLabel = new JLabel(label);
            iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            iconLabel.setForeground(TEXT_COLOR);
            iconLabel.setPreferredSize(new Dimension(80, 20));

            JLabel valueLabel = new JLabel(value);
            valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            valueLabel.setForeground(TEXT_COLOR);

            panel.add(iconLabel);
            panel.add(valueLabel);

            return panel;
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            LoginPage login = new LoginPage();
            login.setVisible(true);
        });
    }
}
