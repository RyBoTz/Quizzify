import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Welcome extends JFrame {
    private CardLayout cardLayout;
    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;

    public Welcome() {
        setTitle("Welcome");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        setLayout(cardLayout);

        loginPanel = new LoginPanel();
        
        registerPanel = new RegisterPanel();
        registerPanel.setBackground(new Color(228, 212, 255));
        add(loginPanel, "LoginPanel");
        add(registerPanel, "RegisterPanel");

        cardLayout.show(getContentPane(), "LoginPanel"); 

        setVisible(true);
    }

    private class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private Image backgroundImage = new ImageIcon("C:\\Users\\medal\\Desktop\\Quizzify\\assets\\LoginBG.png").getImage();

    public LoginPanel() {
        setLayout(null);

        JLabel usernameLabel = new JLabel("USERNAME:");
        usernameLabel.setBounds(450, 320, 300, 50);
        usernameLabel.setForeground(Color.WHITE);   
        usernameLabel.setFont(new Font("Anybody SemiBold", Font.BOLD, 17));
        add(usernameLabel);

        usernameField = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE); 
                g.fillRect(0, getHeight() - 4, getWidth(), - 4); // line bellow 
            }
        };
        usernameField.setBounds(555, 320, 200, 50);
        usernameField.setOpaque(false); 
        usernameField.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0)); 
        usernameField.setForeground(Color.WHITE); 
        usernameField.setFont(new Font("Anybody SemiBold", Font.BOLD, 24)); 
        add(usernameField);

        JLabel passwordLabel = new JLabel("PASSWORD:");
        passwordLabel.setBounds(443, 380, 300, 50);
        passwordLabel.setForeground(Color.WHITE);   
        passwordLabel.setFont(new Font("Anybody SemiBold", Font.BOLD, 17));
        add(passwordLabel);

        passwordField = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE); 
                g.fillRect(0, getHeight() - 4, getWidth(), 4); 
            }
        };
        passwordField.setBounds(555, 380, 200, 50);
        passwordField.setOpaque(false); 
        passwordField.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0)); 
        passwordField.setForeground(Color.WHITE); 
        passwordField.setFont(new Font("Anybody SemiBold", Font.BOLD, 24)); 
        add(passwordField);

        JButton loginButton = new JButton("LOGIN") {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isPressed()) {
                    g.setColor(new Color(200, 200, 200)); 
                    g.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    g.setColor(new Color(0, 0, 0, 0)); 
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
                super.paintComponent(g);
            }
        };

        // Set button properties
        loginButton.setBounds(450, 470, 300, 40);
        loginButton.setForeground(Color.WHITE); 
        loginButton.setFont(new Font("Arial", Font.BOLD, 16)); 
        loginButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); 
        loginButton.setContentAreaFilled(false); 
        loginButton.setFocusPainted(false); 
        loginButton.setOpaque(false); 
        loginButton.addActionListener(new LoginButtonListener());

        add(loginButton);

        JButton registerButton = new JButton("Register") {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isPressed()) {
                    g.setColor(new Color(200, 200, 200)); 
                    g.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    g.setColor(new Color(0, 0, 0, 0)); 
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
                super.paintComponent(g);
            }
        };

        // Set button properties
        registerButton.setBounds(450, 530, 300, 40);
        registerButton.setForeground(Color.WHITE); 
        registerButton.setFont(new Font("Arial", Font.BOLD, 16)); 
        registerButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); 
        registerButton.setContentAreaFilled(false); 
        registerButton.setFocusPainted(false); 
        registerButton.setOpaque(false); 
        registerButton.addActionListener(e -> cardLayout.show(getContentPane(), "RegisterPanel"));
        add(registerButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleLogin();
        }
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in both username and password.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT user_id, username, role FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int loggedInUserId = rs.getInt("user_id");
                        String loggedInUsername = rs.getString("username");
                        String loggedInUserRole = rs.getString("role");
                        JOptionPane.showMessageDialog(this, "Login successful!");

                        // Proceed to user or admin menu
                        if (loggedInUserRole.equals("admin")) {
                            openAdminMenu(loggedInUserId);
                        } else {
                            openUserMenu(loggedInUserId);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid username or password.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error.");
        }
    }
}

    private class RegisterPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField; 
    private JTextField phoneNumberField; 
    private JRadioButton userRoleButton; 
    private JRadioButton adminRoleButton; 
    private Image backgroundImage = new ImageIcon("C:\\Users\\medal\\Desktop\\Quizzify\\assets\\RegisterBG.png").getImage();

    public RegisterPanel() {
        setLayout(null);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(450, 250, 100, 30);
        usernameLabel.setForeground(Color.WHITE);   
        usernameLabel.setFont(new Font("Anybody SemiBold", Font.BOLD, 17));
        add(usernameLabel);

        usernameField = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE); 
                g.fillRect(0, getHeight() - 4, getWidth(), 4); 
            }
        };
         usernameField.setBounds(550, 250, 200, 30);
         usernameField.setOpaque(false); 
         usernameField.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0)); 
         usernameField.setForeground(Color.WHITE); 
         usernameField.setFont(new Font("Anybody SemiBold", Font.BOLD, 24)); 
        add( usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(450, 300, 100, 30);
        passwordLabel.setForeground(Color.WHITE);   
        passwordLabel.setFont(new Font("Anybody SemiBold", Font.BOLD, 17));
        add(passwordLabel);

        passwordField = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE); 
                g.fillRect(0, getHeight() - 4, getWidth(), 4); 
            }
        };
        passwordField.setBounds(550, 300, 200, 30);
        passwordField.setOpaque(false); 
        passwordField.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0)); 
        passwordField.setForeground(Color.WHITE); 
        passwordField.setFont(new Font("Anybody SemiBold", Font.BOLD, 24)); 
        add(passwordField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(485, 350, 100, 30);
        emailLabel.setForeground(Color.WHITE);   
        emailLabel.setFont(new Font("Anybody SemiBold", Font.BOLD, 17));
        add(emailLabel);

        emailField = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE); 
                g.fillRect(0, getHeight() - 4, getWidth(), 4); 
            }
        };
        emailField.setBounds(550, 350, 200, 30);
        emailField.setOpaque(false); 
        emailField.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0)); 
        emailField.setForeground(Color.WHITE); 
        emailField.setFont(new Font("Anybody SemiBold", Font.BOLD, 24)); 
        add(emailField);

        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setBounds(410, 400, 200, 30);
        phoneLabel.setForeground(Color.WHITE);   
        phoneLabel.setFont(new Font("Anybody SemiBold", Font.BOLD, 17));
        add(phoneLabel);

        phoneNumberField = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE); 
                g.fillRect(0, getHeight() - 4, getWidth(), 4); 
            }
        };
        phoneNumberField.setBounds(550, 400, 200, 30);
        phoneNumberField.setOpaque(false); 
        phoneNumberField.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0)); 
        phoneNumberField.setForeground(Color.WHITE);
        phoneNumberField.setFont(new Font("Anybody SemiBold", Font.BOLD, 24)); 
        add(phoneNumberField);

        // Role selection
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(450, 450, 100, 30);
        roleLabel.setForeground(Color.WHITE);   
        roleLabel.setFont(new Font("Anybody SemiBold", Font.BOLD, 17));
        add(roleLabel);


        // User role radio button
        userRoleButton = new JRadioButton("User", true); 
        userRoleButton.setBounds(550, 450, 120, 40); 
        userRoleButton.setOpaque(false); 
        userRoleButton.setForeground(Color.WHITE);  
        userRoleButton.setFont(new Font("Anybody SemiBold", Font.BOLD, 16));  
        add(userRoleButton);

        // Admin role radio button
        adminRoleButton = new JRadioButton("Admin");
        adminRoleButton.setBounds(680, 450, 120, 40);  
        adminRoleButton.setOpaque(false); 
        adminRoleButton.setForeground(Color.WHITE); 
        adminRoleButton.setFont(new Font("Anybody SemiBold", Font.BOLD, 16)); 
        add(adminRoleButton);

        // Group the radio buttons
        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(userRoleButton);
        roleGroup.add(adminRoleButton);

        // Create a transparent button  
        JButton registerButton = new JButton("Register") {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isPressed()) {
                    g.setColor(new Color(200, 200, 200));  
                    g.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    g.setColor(new Color(0, 0, 0, 0));  
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
                super.paintComponent(g);
            }
        };

        // Set button properties
        registerButton.setBounds(500, 550, 100, 30);
        registerButton.setForeground(Color.WHITE);  
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));  
        registerButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); 
        registerButton.setContentAreaFilled(false);  
        registerButton.setFocusPainted(false); 
        registerButton.setOpaque(false);  
        registerButton.addActionListener(e -> handleRegister());
        add(registerButton);

        // Create the back button   
        JButton backButton = new JButton("Back") {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isPressed()) {
                    g.setColor(new Color(200, 200, 200));  
                    g.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    g.setColor(new Color(0, 0, 0, 0));  
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
                super.paintComponent(g);
            }
        };

        // Set button properties back button
        backButton.setBounds(610, 550, 100, 30);
        backButton.setForeground(Color.WHITE);  
        backButton.setFont(new Font("Arial", Font.BOLD, 16));  
        backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));  
        backButton.setContentAreaFilled(false);  
        backButton.setFocusPainted(false); 
        backButton.setOpaque(false);  
        backButton.addActionListener(e -> cardLayout.show(getContentPane(), "LoginPanel"));
        add(backButton);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
    
    private void handleRegister() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String email = emailField.getText();
        String phoneNumber = phoneNumberField.getText();
        String role = userRoleButton.isSelected() ? "user" : "admin"; // Determine role

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO users (username, password, email, number, role) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, email);
                stmt.setString(4, phoneNumber);
                stmt.setString(5, role); 
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Registration successful! You can log in now.");
                cardLayout.show(getContentPane(), "LoginPanel"); 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database error. Registration failed.");
        }
    }
}

    private void openAdminMenu(int userId) {
        AdminMenu adminMenu = new AdminMenu(userId);
        adminMenu.setVisible(true);
        this.dispose();  
    }

    private void openUserMenu(int userId) {
        UserMenu userMenu = new UserMenu(userId);
        userMenu.setVisible(true);
        this.dispose();  
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Welcome::new);
    }
}