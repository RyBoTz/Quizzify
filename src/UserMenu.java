import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class UserMenu extends JFrame {
    private int loggedInUserId;
    
    private JPanel menuPanel;
    private JPanel profilePagePanel;
    private JPanel takeQuizPanel;
    private JPanel takingQuizPanel;
    private JButton backToMenuButton;
    private boolean quizCompleted = false;  
    private JButton createStyledButton(String text, ActionListener actionListener) {
    JButton button = new JButton(text) {
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
    
    button.setForeground(Color.WHITE);
    button.setFont(new Font("Arial", Font.BOLD, 16)); 
    button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); 
    button.setContentAreaFilled(false); 
    button.setFocusPainted(false); 
    button.setOpaque(false);
    button.addActionListener(actionListener);
    
    return button;
}
    
    public UserMenu(int userId) {
        this.loggedInUserId = userId;
        setTitle("User Menu");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        
        openMenuPanel();
    }
   
    private void openMenuPanel() {
    
    removeAllPanels();
    
    menuPanel = new JPanel() {
        private Image backgroundImage = new ImageIcon("C:\\Users\\medal\\Desktop\\Quizzify\\assets\\LeftpanelBG.png").getImage();

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    };
    
    menuPanel.setBounds(0, 0, 300, 800);
    menuPanel.setLayout(null);

    JButton startQuizButton = createStyledButton("Start Quiz", e -> openTakeQuizPanel());
    startQuizButton.setBounds(50, 200, 200, 40);
    
    menuPanel.add(startQuizButton);

    JButton profilePageButton = createStyledButton("Profile Page", e -> openProfilePagePanel());
    profilePageButton.setBounds(50, 300, 200, 40);
    menuPanel.add(profilePageButton);
    
    JButton ResultButton = createStyledButton("Result", e -> openResultPagePanel());
    ResultButton.setBounds(50, 400, 200, 40);
//  menuPanel.add(ResultButton);
        
    JButton logoutButton = createStyledButton("Logout", e -> logoutAction());
    logoutButton.setBounds(50, 700, 200, 40);
    menuPanel.add(logoutButton);
    
    
    
    add(menuPanel);
    revalidate();
    repaint();
}

    private void removeAllPanels() {
        // Remove existing panels
        JPanel[] panels = {menuPanel, profilePagePanel, takeQuizPanel, takingQuizPanel};
        for (JPanel panel : panels) {
            if (panel != null) {
                remove(panel);
            }
        }
    }
    
    private void openTakeQuizPanel() {
    
     removeAllPanels();

    takeQuizPanel = new JPanel() {
    private Image backgroundImage = new ImageIcon("C:\\Users\\medal\\Desktop\\Quizzify\\assets\\TakequizBG.png").getImage();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
};

    takeQuizPanel.setBounds(0, 0, 1200, 800);
    takeQuizPanel.setLayout(null);

    JLabel questionCountLabel = new JLabel("Questions: 0");
    questionCountLabel.setFont(new Font("Arial", Font.PLAIN, 18));
    questionCountLabel.setBounds(0, 315, 1200, 30); 
    questionCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
    takeQuizPanel.add(questionCountLabel);

    // Back to menu button
    JButton backToMenuButton = new JButton("Back to Menu");
    backToMenuButton.setBounds(450, 700, 300, 40); 
    backToMenuButton.setBackground(Color.WHITE); // Opaque background
    backToMenuButton.setForeground(Color.BLACK); // White text
    backToMenuButton.addActionListener(e -> openMenuPanel());
    takeQuizPanel.add(backToMenuButton);

    
    JComboBox<String> courseComboBox = new JComboBox<>();
    courseComboBox.setBounds(553, 170, 300, 30); // Centered horizontally
    courseComboBox.addItem("Course");

    JLabel descriptionLabel = new JLabel();
    descriptionLabel.setFont(new Font("Arial", Font.BOLD, 15));
    descriptionLabel.setBounds(0, 390, 1200, 50); 
    descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
    takeQuizPanel.add(descriptionLabel);

    // Populate course combo box
    populateCourseComboBox(courseComboBox, descriptionLabel, questionCountLabel);

    // Quiz combo box
    JComboBox<String> quizComboBox = new JComboBox<>();
    quizComboBox.setBounds(553, 260, 300, 30); 
    quizComboBox.addItem("Quiz");
    takeQuizPanel.add(quizComboBox);

    setupComboBoxListeners(courseComboBox, quizComboBox, descriptionLabel, questionCountLabel);

    // Start Quiz button
    JButton startQuizButton = new JButton("Start Quiz");
    startQuizButton.setBounds(450, 650, 300, 40);
    startQuizButton.setBackground(Color.WHITE); // Opaque background
    startQuizButton.setForeground(Color.BLACK); // White text
    startQuizButton.addActionListener(e -> {
    // Check the selected quiz before removing
    if (quizComboBox.getSelectedIndex() > 0) {
        String selectedQuiz = (String) quizComboBox.getSelectedItem();
        
        for (Component component : getContentPane().getComponents()) {
            if (component instanceof JPanel) {
                remove(component);
            }
        }

        openTakingQuizPanel(selectedQuiz); 
    } else {
      
        JOptionPane.showMessageDialog(this, "Please select a quiz to start.", "Warning", JOptionPane.WARNING_MESSAGE);
    }

    revalidate(); 
    repaint();    
});

    takeQuizPanel.add(startQuizButton);

    add(takeQuizPanel);
    revalidate();
    repaint();
}

    private void populateCourseComboBox(JComboBox<String> courseComboBox, JLabel descriptionLabel, JLabel questionCountLabel) {
        String courseQuery = "SELECT course_id, course_name FROM courses";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(courseQuery)) {
            ResultSet rs = ps.executeQuery();
            boolean hasCourses = false;
            while (rs.next()) {
                int courseId = rs.getInt("course_id");
                String courseName = rs.getString("course_name");
                courseComboBox.addItem(courseName);
                courseComboBox.putClientProperty(courseName, courseId);
                hasCourses = true;
            }
            if (!hasCourses) {
                JOptionPane.showMessageDialog(this, "No available courses.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading courses from the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        takeQuizPanel.add(courseComboBox);
    }

    private void setupComboBoxListeners(JComboBox<String> courseComboBox, JComboBox<String> quizComboBox, 
                                         JLabel descriptionLabel, JLabel questionCountLabel) {
        
        courseComboBox.addActionListener(e -> {
            if (courseComboBox.getSelectedIndex() > 0) {
                String selectedCourseName = (String) courseComboBox.getSelectedItem();
                int selectedCourseId = (int) courseComboBox.getClientProperty(selectedCourseName);
                DefaultComboBoxModel<String> quizComboBoxModel = new DefaultComboBoxModel<>();
                quizComboBoxModel.addElement("Select a quiz");
                
                String quizQuery = "SELECT quiz_name FROM quizzes WHERE course_id = ?";
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement ps = conn.prepareStatement(quizQuery)) {
                    ps.setInt(1, selectedCourseId);
                    ResultSet rs = ps.executeQuery();
                    boolean hasQuizzes = false;
                    while (rs.next()) {
                        quizComboBoxModel.addElement(rs.getString("quiz_name"));
                        hasQuizzes = true;
                    }
                    if (!hasQuizzes) {
                        JOptionPane.showMessageDialog(this, "No quizzes available for this course.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error loading quizzes from the database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                quizComboBox.setModel(quizComboBoxModel);
            } else {
                quizComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Select a quiz"}));
            }
        });

        quizComboBox.addActionListener(e -> {
            if (quizComboBox.getSelectedIndex() > 0) {
                String selectedQuiz = (String) quizComboBox.getSelectedItem();
                int quizId = getQuizIdFromName(selectedQuiz);
                
                if (quizId != -1) {
                    String questionCountQuery = "SELECT COUNT(*) AS question_count FROM questions WHERE quiz_id = ?";
                    String descriptionQuery = "SELECT quiz_description FROM quizzes WHERE quiz_id = ?";

                    try (Connection conn = DatabaseConnection.getConnection();
                         PreparedStatement psCount = conn.prepareStatement(questionCountQuery);
                         PreparedStatement psDescription = conn.prepareStatement(descriptionQuery)) {
                         
                        psCount.setInt(1, quizId);
                        ResultSet rsCount = psCount.executeQuery();
                        if (rsCount.next()) {
                            int questionCount = rsCount.getInt("question_count");
                            questionCountLabel.setText("Questions: " + questionCount);
                        }

                        psDescription.setInt(1, quizId);
                        ResultSet rsDescription = psDescription.executeQuery();
                        if (rsDescription.next()) {
                            String quizDescription = rsDescription.getString("quiz_description");
                            descriptionLabel.setText(quizDescription);
                        } else {
                            descriptionLabel.setText("No description available for this quiz.");
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Error loading quiz details.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    questionCountLabel.setText("Questions: 0");
                    descriptionLabel.setText("");
                }
            } else {
                questionCountLabel.setText("Questions: 0");
                descriptionLabel.setText("");
            }
        });
    }
    
    private void openProfilePagePanel() {
    // Remove the existing profile panel
    if (profilePagePanel != null) {
        remove(profilePagePanel);
    }

    profilePagePanel = new JPanel() {
        private Image backgroundImage = new ImageIcon("C:\\Users\\medal\\Desktop\\Quizzify\\assets\\ProfileBG.png").getImage();

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    };
    profilePagePanel.setBounds(300, 0, 900, 800);
    profilePagePanel.setLayout(null);


    String username = "";
    String email = "";
    String number = "";
    String role = "";

    // Fetch user details from the database
    try (Connection connection = DatabaseConnection.getConnection()) {
        String query = "SELECT username, email, number, role FROM users WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, loggedInUserId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    username = resultSet.getString("username");
                    email = resultSet.getString("email");
                    number = resultSet.getString("number");
                    role = resultSet.getString("role");
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    // Display current user details
    JLabel currentUsernameLabel = new JLabel("");
    currentUsernameLabel.setBounds(200, 150, 150, 30); // Centered label
    profilePagePanel.add(currentUsernameLabel);

    JTextField usernameField = new JTextField(username);
    usernameField.setBounds(440, 150, 200, 30); // Align with the label
    profilePagePanel.add(usernameField);

    JLabel currentEmailLabel = new JLabel("");
    currentEmailLabel.setBounds(200, 200, 150, 30);
    profilePagePanel.add(currentEmailLabel);

    JTextField emailField = new JTextField(email);
    emailField.setBounds(440, 200, 200, 30);
    profilePagePanel.add(emailField);

    JLabel currentPhoneLabel = new JLabel("");
    currentPhoneLabel.setBounds(200, 250, 150, 30);
    profilePagePanel.add(currentPhoneLabel);

    JTextField phoneField = new JTextField(number);
    phoneField.setBounds(440, 250, 200, 30);
    profilePagePanel.add(phoneField);

    JLabel currentRoleLabel = new JLabel("");
    currentRoleLabel.setBounds(100, 300, 150, 30);
    profilePagePanel.add(currentRoleLabel);
    
    // role dispsplay pwede edit or no
    JLabel roleLabel = new JLabel(role); 
    roleLabel.setBounds(440, 300, 200, 30);
    roleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set font to Arial, bold, size 16
    profilePagePanel.add(roleLabel);

    // Fields cchanging password
    JLabel currentPasswordLabel = new JLabel("");
    currentPasswordLabel.setBounds(200, 400, 150, 30);
    profilePagePanel.add(currentPasswordLabel);

    JPasswordField currentPasswordField = new JPasswordField();
    currentPasswordField.setBounds(440, 400, 200, 30);
    profilePagePanel.add(currentPasswordField);

    JLabel newPasswordLabel = new JLabel("");
    newPasswordLabel.setBounds(200, 450, 150, 30);
    profilePagePanel.add(newPasswordLabel);

    JPasswordField newPasswordField = new JPasswordField();
    newPasswordField.setBounds(440, 450, 200, 30);
    profilePagePanel.add(newPasswordField);

    JLabel confirmPasswordLabel = new JLabel("");
    confirmPasswordLabel.setBounds(200, 500, 150, 30);
    profilePagePanel.add(confirmPasswordLabel);

    JPasswordField confirmPasswordField = new JPasswordField();
    confirmPasswordField.setBounds(440, 500, 200, 30);
    profilePagePanel.add(confirmPasswordField);

    // Save button
    JButton saveButton = new JButton("Save Changes");
    saveButton.setBounds(344, 650, 200, 30); // Center the button
    saveButton.setBackground(Color.WHITE); // Opaque white background
    saveButton.setForeground(Color.BLACK); // White text
    
    saveButton.addActionListener(e -> {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Check if current password is correct
            String checkPasswordQuery = "SELECT password FROM users WHERE user_id = ?";
            String oldPassword = null;
            try (PreparedStatement checkPasswordStatement = connection.prepareStatement(checkPasswordQuery)) {
                checkPasswordStatement.setInt(1, loggedInUserId);
                try (ResultSet passwordResultSet = checkPasswordStatement.executeQuery()) {
                    if (passwordResultSet.next()) {
                        oldPassword = passwordResultSet.getString("password");

                        // Verify current password
                        String enteredCurrentPassword = new String(currentPasswordField.getPassword());
                        if (!oldPassword.equals(enteredCurrentPassword)) {
                            JOptionPane.showMessageDialog(this, "Current password is incorrect.");
                            return;
                        }
                    }
                }
            }

            // Get the new password 
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            String updateQuery = "UPDATE users SET username = ?, email = ?, number = ?";
            if (!newPassword.isEmpty()) { 
                updateQuery += ", password = ?";
            }
            updateQuery += " WHERE user_id = ?";

            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setString(1, usernameField.getText());
                updateStatement.setString(2, emailField.getText());
                updateStatement.setString(3, phoneField.getText());
                if (!newPassword.isEmpty()) {
                    updateStatement.setString(4, newPassword); 
                    // Set new password
                    updateStatement.setInt(5, loggedInUserId);
                } else {
                    updateStatement.setInt(4, loggedInUserId); 
                    // Only set user_id if password not updated
                }
                
                int rowsAffected = updateStatement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Profile updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Error updating profile.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    });
    profilePagePanel.add(saveButton);

    add(profilePagePanel);
    revalidate();
    repaint();
}
    
    // METHOD TAKING QUIZ
    private void openTakingQuizPanel(String quizName) {
        // Remove panel if it exists
        if (takingQuizPanel != null) {
            remove(takingQuizPanel);
        }

        // Check if quiz completed
        if (quizCompleted) {
            JOptionPane.showMessageDialog(this, "You have already completed this quiz.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return; // Exit the method if the quiz has been completed
        }

        int quizId = getQuizIdFromName(quizName);
        // Error
        if (quizId == -1) {
            JOptionPane.showMessageDialog(this, "Invalid quiz selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a new panel for the taking quiz screen
        takingQuizPanel = new JPanel() {
        private Image backgroundImage = new ImageIcon("C:\\Users\\medal\\Desktop\\Quizzify\\assets\\TakingquizBG.png").getImage();

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    };

        takingQuizPanel.setBounds(0, 0, 1200, 800);
        takingQuizPanel.setLayout(null);

        JLabel headerLabel = new JLabel(quizName, SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 30));
        headerLabel.setForeground(Color.BLACK);
        headerLabel.setBounds(0, 20, 1200, 50); 
        takingQuizPanel.add(headerLabel);

        JLabel descriptionLabel = new JLabel();
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        descriptionLabel.setBounds(50, 80, 1100, 50); 
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        takingQuizPanel.add(descriptionLabel); 

        String descriptionQuery = "SELECT quiz_description FROM quizzes WHERE quiz_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(descriptionQuery)) {
            ps.setInt(1, quizId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String quizDescription = rs.getString("quiz_description");
                descriptionLabel.setText("<html><div style='text-align: center;'>" + quizDescription + "</div></html>"); 
            } else {
                descriptionLabel.setText("No description available for this quiz.");
            }

            takingQuizPanel.revalidate();
            takingQuizPanel.repaint();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading quiz description: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        backToMenuButton = new JButton("Back to Menu");
        backToMenuButton.setBounds(493, 480, 200, 40); 
        backToMenuButton.setBackground(Color.WHITE); 
        backToMenuButton.setForeground(Color.BLACK); 
        backToMenuButton.setBorder(BorderFactory.createLineBorder(Color.BLACK)); 
        backToMenuButton.setFocusPainted(false); 
        backToMenuButton.setOpaque(true);
        backToMenuButton.setVisible(false);
        takingQuizPanel.add(backToMenuButton);

        backToMenuButton.addActionListener(e -> {
            openMenuPanel();
            remove(takingQuizPanel);
        });

       
        JLabel questionLabel = new JLabel("Question will appear here");
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        int panelWidth = takingQuizPanel.getWidth();
        int labelWidth = 890; 
        int xPosition = (panelWidth - labelWidth) / 2; // Calculate the x position to center the label

        questionLabel.setBounds(xPosition, 150, labelWidth, 30); // Set bound to center it
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center alignment
        takingQuizPanel.add(questionLabel);

        // Button group for the options
        ButtonGroup optionsGroup = new ButtonGroup();

        // Buttons with no background
        JRadioButton optionA = createTransparentOptionButton("A: ", 200);
        JRadioButton optionB = createTransparentOptionButton("B: ", 250);
        JRadioButton optionC = createTransparentOptionButton("C: ", 300);
        JRadioButton optionD = createTransparentOptionButton("D: ", 350);

        // Add buttons to group and panel
        optionsGroup.add(optionA);
        optionsGroup.add(optionB);
        optionsGroup.add(optionC);
        optionsGroup.add(optionD);

        // Center the radio buttons
        int radioButtonX = (takingQuizPanel.getWidth() - 400) / 2; // Calculate center X position
        optionA.setBounds(radioButtonX, 200, 400, 30);
        optionB.setBounds(radioButtonX, 250, 400, 30);
        optionC.setBounds(radioButtonX, 300, 400, 30);
        optionD.setBounds(radioButtonX, 350, 400, 30);

        takingQuizPanel.add(optionA);
        takingQuizPanel.add(optionB);
        takingQuizPanel.add(optionC);
        takingQuizPanel.add(optionD);

        // Create progress bar
        JProgressBar progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setBackground(Color.WHITE); // Opaque background
        progressBar.setForeground(Color.GRAY); 
        progressBar.setBounds(100, 720, 1000, 30); 
        takingQuizPanel.add(progressBar);

        // Query to get the questions/optionss for the quiz
        String questionQuery = "SELECT question_text, option_a, option_b, option_c, option_d, correct_answer FROM questions WHERE quiz_id = ?";

        var questions = new ArrayList<Map<String, String>>();

        // Fetch questionsfrom DB
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(questionQuery)) {
            ps.setInt(1, quizId);
            ResultSet rs = ps.executeQuery();

            // Collect all questions to list
            while (rs.next()) {
                var question = new HashMap<String, String>();
                question.put("question_text", rs.getString("question_text"));
                question.put("option_a", rs.getString("option_a"));
                question.put("option_b", rs.getString("option_b"));
                question.put("option_c", rs.getString("option_c"));
                question.put("option_d", rs.getString("option_d"));
                question.put("correct_answer", rs.getString("correct_answer"));
                questions.add(question);
            }

            // Check if questions found
            if (questions.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No questions found for this quiz.", "Error", JOptionPane.ERROR_MESSAGE);
                openTakeQuizPanel();
                return;
            }

            // Track current question index and correct answers
            final int[] currentQuestionIndex = {0};
            final int[] correctAnswers = {0};
            final int[] totalQuestions = {questions.size()};
            final long startTime = System.currentTimeMillis();

            // Create navigation buttons
            JButton prevButton = new JButton("Previous");
            prevButton.setBounds(342, 400, 200, 40);
            prevButton.setBackground(Color.WHITE); // Opaque background
            prevButton.setForeground(Color.BLACK); // White text
            prevButton.setEnabled(false); 

            JButton nextButton = new JButton("Next");
            nextButton.setBackground(Color.WHITE); // Opaque background
            nextButton.setForeground(Color.BLACK); // White text
            nextButton.setBounds(643, 400, 200, 40);
            

            // Method to display progress bar
            Runnable updateQuestionDisplay = () -> {
                Map<String, String> currentQuestion = questions.get(currentQuestionIndex[0]);
                questionLabel.setText(currentQuestion.get("question_text"));
                optionA.setText("A: " + currentQuestion.get("option_a"));
                optionB.setText("B: " + currentQuestion.get("option_b"));
                optionC.setText("C: " + currentQuestion.get("option_c"));
                optionD.setText("D: " + currentQuestion.get("option_d"));
                prevButton.setEnabled(currentQuestionIndex[0] > 0);
                nextButton.setText(currentQuestionIndex[0] == questions.size() - 1 ? "Finish" : "Next");

                // Updates progress bar
                int progress = (int) ((currentQuestionIndex[0] + 1) * 100.0 / totalQuestions[0]);
                progressBar.setValue(progress);
                progressBar.setString("Question " + (currentQuestionIndex[0] + 1) + " of " + totalQuestions[0]);
            };

            // Previous button
            prevButton.addActionListener(e -> {
                if (currentQuestionIndex[0] > 0) {
                    currentQuestionIndex[0]--;
                    updateQuestionDisplay.run();
                    optionsGroup.clearSelection();
                }
            });

            // Next button
            nextButton.addActionListener(e -> {
                // Check if answer is correct
                String selectedAnswer = getSelectedAnswer(optionsGroup);
                if (selectedAnswer != null && selectedAnswer.equals(questions.get(currentQuestionIndex[0]).get("correct_answer"))) {
                    correctAnswers[0]++;
                }

                if (currentQuestionIndex[0] < questions.size() - 1) {
                    currentQuestionIndex[0]++;
                    updateQuestionDisplay.run();
                    optionsGroup.clearSelection();
                } else {
                    // Finish quiz and save result without
                    long endTime = System.currentTimeMillis();
                    int timeTaken = (int) ((endTime - startTime) / 1000); // Time in seconds
                    double score = (correctAnswers[0] * 100.00) / totalQuestions[0];

                    saveQuizResult(
                        loggedInUserId,  // User ID
                        quizId,          // Quiz ID 
                        totalQuestions[0], 
                        correctAnswers[0], 
                        totalQuestions[0] - correctAnswers[0], 
                        score,           // Score 
                        timeTaken
                    );          

                    // Set quiz as completed 
                    quizCompleted = true; 

                    JOptionPane.showMessageDialog(this, 
                        "You've completed the quiz!\n" + 
                        "Raw Score: " + correctAnswers[0] + "/" + totalQuestions[0] + "\n" + 
                        "Percentage Score: " + score + "%", 
                        "Quiz Completed", 
                        JOptionPane.INFORMATION_MESSAGE);

                    prevButton.setEnabled(false);
                    nextButton.setEnabled(false);
                    backToMenuButton.setVisible(true); // Show back to menu button
                }
            });

            // Add button to panel
            takingQuizPanel.add(prevButton);
            takingQuizPanel.add(nextButton);

            // Load first question
            updateQuestionDisplay.run();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading quiz questions.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        add(takingQuizPanel);
        revalidate();
        repaint();
    }
    
    private JRadioButton createTransparentOptionButton(String text, int y) {
    JRadioButton radioButton = new JRadioButton(text);
    radioButton.setFont(new Font("Arial", Font.PLAIN, 16));
    radioButton.setBounds(100, y, 400, 30);
    radioButton.setOpaque(false); 
    radioButton.setBackground(new Color(0, 0, 0, 0)); 
    return radioButton;
}
    
    private int getQuizIdFromName(String quizName) {
        // Retrieve quiz ID from quiz name
        String query = "SELECT quiz_id FROM quizzes WHERE quiz_name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, quizName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("quiz_id"); // Return the quiz ID
            }
        } catch (SQLException e) {
        }
        return -1;
    }
    
    private void saveQuizResult(int userId, int quizId, int totalQuestions, int correctAnswers, int incorrectAnswers, double score, int timeTaken) {
        // Format the score to 2 decimal places
    DecimalFormat df = new DecimalFormat("#.00");
    double percentage = Double.parseDouble(df.format(score));

    String insertResultQuery = "INSERT INTO quiz_results (user_id, quiz_id, total_questions, correct_answers, incorrect_answers, score, percentage, time_taken) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(insertResultQuery)) {
        ps.setInt(1, userId); // Use userId instead of loggedInUserId
        ps.setInt(2, quizId);
        ps.setInt(3, totalQuestions);
        ps.setInt(4, correctAnswers);
        ps.setInt(5, incorrectAnswers);
        ps.setDouble(6, score);
        ps.setDouble(7, percentage); // Use the formatted percentage
        ps.setInt(8, timeTaken);
        ps.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error saving quiz result", "Error", JOptionPane.ERROR_MESSAGE);
    }
}  
    
    // Method to create a radio button
    private JRadioButton createOptionButton(String text, int yPosition) {
    JRadioButton button = new JRadioButton(text);
    button.setBounds(100, yPosition, 700, 30);
    return button;
}

    private String getSelectedAnswer(ButtonGroup optionsGroup) {
    for (Enumeration<AbstractButton> buttons = optionsGroup.getElements(); buttons.hasMoreElements();) {
        AbstractButton button = buttons.nextElement();
        if (button.isSelected()) {
            return button.getText().substring(0, 1); 
        }
    }
    return null; 
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserMenu userMenu = new UserMenu(1); 
            userMenu.setVisible(true); 
        });
    }

    // METHOD SHOW RESULT
    private JButton openResultPagePanel() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private void logoutAction() {
    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        // Close the current JFrame
        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(menuPanel);
        if (currentFrame != null) {
            currentFrame.dispose(); // Close the JFrame
        }

        Welcome welcomeFrame = new Welcome();
        welcomeFrame.setVisible(true); 
    }
}   
    
}