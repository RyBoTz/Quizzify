//Run This
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.sql.Statement;

public class AdminMenu extends JFrame {
    private int loggedInUserId;
    private JPanel currentPanel;
    private JComboBox<String> courseComboBox;
    private JTextField courseNameField;
    private JTextField quizTitleField;
    private int quizId;
    private JTextArea quizDescriptionArea;
    private JTable quizTable;
    private JPanel menuPanel; 
    private JTable userTable; 
    private DefaultTableModel tableModel; 
    private JPanel editquizPanel;
    private JPanel quizbankPanel;
    
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
        button.setFocusPainted(false); // Remove focus rectangle
        button.setOpaque(false); // Make it non-opaque
        button.addActionListener(actionListener);

        return button;
    }
    public AdminMenu(int loggedInUserId) {
    this.loggedInUserId = loggedInUserId;
    setTitle("Admin Menu");
    setSize(1200, 800);
    setResizable(false); // Make the frame not resizable
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(null); // Use null layout for absolute positioning
    openMenuPanel();
    add (menuPanel);
    
    setLocationRelativeTo(null);
}

    class ImagePanel extends JPanel {
    private Image backgroundImage;

    public ImagePanel(String imagePath) {
        this.backgroundImage = new ImageIcon(imagePath).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
   
    private void openMenuPanel() {
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

    // Create Quiz Button
    JButton createQuizButton = createStyledButton("Create Quiz", e -> {
        // Remove all panels except for the menuPanel
        for (Component component : getContentPane().getComponents()) {
            if (component != menuPanel) {
                remove(component); // Remove any component that is not the menu panel
            }
        }
        openQuizMakerPanel(); 
        revalidate(); 
        repaint(); 
    });
    createQuizButton.setBounds(50, 200, 200, 30);
    menuPanel.add(createQuizButton);

    // Manage Quiz Button
    JButton manageQuizButton = createStyledButton("Manage Quiz", e -> {
        // Remove all panels except for the menuPanel
        for (Component component : getContentPane().getComponents()) {
            if (component != menuPanel) {
                remove(component); // Remove any component that is not the menu panel
            }
        }
        openManageQuizPanel(); 
        revalidate(); 
        repaint(); 
    });
    manageQuizButton.setBounds(50, 250, 200, 30);
    menuPanel.add(manageQuizButton);

    // Manage User Button
    JButton manageUserButton = createStyledButton("Manage User", e -> {
        // Remove all panels except for the menuPanel
        for (Component component : getContentPane().getComponents()) {
            if (component != menuPanel) {
                remove(component); // Remove any component that is not the menu panel
            }
        }
        openManageUserPanel(); 
        revalidate(); 
        repaint(); 
    });
    manageUserButton.setBounds(50, 300, 200, 30);
    menuPanel.add(manageUserButton);

    // Results Button
    JButton resultsButton = createStyledButton("Results", e -> {
        // Remove all panels except for the menuPanel
        for (Component component : getContentPane().getComponents()) {
            if (component != menuPanel) {
                remove(component); // Remove any component that is not the menu panel
            }
        }
        openResultsPanel(); 
        revalidate(); 
        repaint(); 
    });
    resultsButton.setBounds(50, 350, 200, 30);
    menuPanel.add(resultsButton);

    // Manage Quiz Button
    JButton editQuizButton = createStyledButton("Edit Questions", e -> {
        // Remove all panels except for the menuPanel
        for (Component component : getContentPane().getComponents()) {
            if (component != menuPanel) {
                remove(component); // Remove any component that is not the menu panel
            }
        }
        openEditQuizPanel(); 
        revalidate(); 
        repaint(); 
    });
    editQuizButton.setBounds(50, 400, 200, 30);
    menuPanel.add(editQuizButton);
    
    // Quiz Bank Button
    JButton quizbankButton = createStyledButton("Quiz Bank", e -> {
        // Remove all panels except for the menuPanel
        for (Component component : getContentPane().getComponents()) {
            if (component != menuPanel) {
                remove(component); // Remove any component that is not the menu panel
            }
        }
        openQuizBankPanel(); 
        revalidate(); 
        repaint(); 
    });
    quizbankButton.setBounds(50, 450, 200, 30); 
    menuPanel.add(quizbankButton); 
    
    // Logout Button
    JButton logoutButton = createStyledButton("Logout", e -> {
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // Close current frame and open Welcome frame
            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(menuPanel); // Assuming menuPanel is in the current frame
            if (currentFrame != null) {
                currentFrame.dispose(); // Close current frame
            }
            Welcome welcomeFrame = new Welcome(); // Create the Welcome frame
            welcomeFrame.setVisible(true); // Show the Welcome frame
        }
    });
    logoutButton.setBounds(50, 700, 200, 30);
    menuPanel.add(logoutButton);

        openManageUserPanel(); 
        revalidate(); 
        repaint(); 
}
    
    private void openQuizMakerPanel() {

    // Initialize and configure the main panel
    currentPanel = new JPanel() {
    private Image backgroundImage = new ImageIcon("C:\\Users\\medal\\Desktop\\Quizzify\\assets\\QuizmakerBG.png").getImage();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the superclass method to ensure proper rendering
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Draw the image scaled to panel size
    }
};
    currentPanel.setBounds(300, 0, 900, 800);
    currentPanel.setLayout(null);


    // Quiz title input
    JLabel quizTitleLabel = new JLabel("");
    quizTitleLabel.setBounds(50, 210, 150, 30);
    currentPanel.add(quizTitleLabel);

    quizTitleField = new JTextField();
    quizTitleField.setBounds(350, 210, 200, 30);
    currentPanel.add(quizTitleField);

    // Course selection
    JLabel courseLabel = new JLabel("");
    courseLabel.setBounds(50, 280, 150, 30);
    currentPanel.add(courseLabel);

    courseComboBox = new JComboBox<>();
    courseComboBox.setBounds(350, 280, 200, 30);
    loadCoursesIntoComboBox(); // Load courses from the database
    currentPanel.add(courseComboBox);

    // Add new course section
    JLabel newCourseLabel = new JLabel("");
    newCourseLabel.setBounds(50, 340, 150, 30);
    currentPanel.add(newCourseLabel);

    courseNameField = new JTextField();
    courseNameField.setBounds(350, 340, 200, 30);
    currentPanel.add(courseNameField);

    JButton addCourseButton = new JButton("Add Course");
    addCourseButton.setBounds(555, 340, 150, 30);
    addCourseButton.setBackground(Color.WHITE); // Opaque white background
    addCourseButton.setForeground(Color.BLACK); // White text
    addCourseButton.addActionListener(e -> addCourse());
    currentPanel.add(addCourseButton);

    // Quiz description input
    JLabel quizDescriptionLabel = new JLabel("");
    quizDescriptionLabel.setBounds(50, 450, 150, 30);
    currentPanel.add(quizDescriptionLabel);

    quizDescriptionArea = new JTextArea();
    
    quizDescriptionArea.setLineWrap(true);
    quizDescriptionArea.setWrapStyleWord(true);

    JScrollPane scrollPane = new JScrollPane(quizDescriptionArea);
    scrollPane.setBounds(243, 465, 400, 100);
    currentPanel.add(scrollPane);

    // Button to save and start the quiz
    JButton startQuizButton = new JButton("Start Creating Quiz");
    startQuizButton.setBounds(356, 600, 150, 30);
    startQuizButton.setBackground(Color.WHITE); // Opaque white background
    startQuizButton.setForeground(Color.BLACK); // White text
    
    startQuizButton.addActionListener(e -> saveQuiz());
    currentPanel.add(startQuizButton);
    
    // Add the current panel to the frame
    add(currentPanel);
    revalidate();
    repaint();
}

    private void saveQuiz() {
    // Retrieve user inputs
    String quizName = quizTitleField.getText().trim();
    String selectedCourse = (String) courseComboBox.getSelectedItem();
    String quizDescription = quizDescriptionArea.getText().trim();

    // Validate inputs
    if (quizName.isEmpty() || selectedCourse == null || quizDescription.isEmpty()) {
        JOptionPane.showMessageDialog(this, "All fields are required. Please enter a quiz title, select a course, and provide a description.", "Input Error", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Fetch course ID
    int courseId = getCourseIdByName(selectedCourse);
    if (courseId == -1) {
        JOptionPane.showMessageDialog(this, "The selected course does not exist. Please try again.", "Course Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Check for duplicate quiz
    if (quizExists(quizName, courseId)) {
        JOptionPane.showMessageDialog(this, "A quiz with the same name already exists for this course. Please choose a different name.", "Duplicate Quiz Error", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Insert quiz into database
    String insertQuizSQL = "INSERT INTO quizzes (quiz_name, course_id, user_id, quiz_description) VALUES (?, ?, ?, ?)";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(insertQuizSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {

        // Set values for the prepared statement
        statement.setString(1, quizName);
        statement.setInt(2, courseId);
        statement.setInt(3, loggedInUserId); // Assuming loggedInUserId is a class-level variable storing the user's ID
        statement.setString(4, quizDescription);

        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating quiz failed, no rows affected.");
        }

        // Retrieve the auto-generated quiz ID
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                quizId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating quiz failed, no ID obtained.");
            }
        }

        // Success message
        JOptionPane.showMessageDialog(this, "Quiz saved successfully!");

        // Clear fields after saving
        clearQuizForm();

        // Open the question maker panel
        openQuestionMakerPanel(quizId);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "An error occurred while saving the quiz: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
    // Helper method to clear input fields
    private void clearQuizForm() {
    quizTitleField.setText("");
    quizDescriptionArea.setText("");
    courseComboBox.setSelectedIndex(-1); // Reset combo box selection
}
    
    private void loadCoursesIntoComboBox() {
        courseComboBox.removeAllItems(); // Clear existing items
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT course_name FROM courses");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                courseComboBox.addItem(resultSet.getString("course_name"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading courses: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addCourse() {
    String courseName = courseNameField.getText().trim();
    if (courseName.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter a course name.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement("INSERT INTO courses (course_name, user_id) VALUES (?, ?)")) {
        statement.setString(1, courseName);
        statement.setInt(2, loggedInUserId); // Set the user ID
        statement.executeUpdate();
        JOptionPane.showMessageDialog(this, "Course added successfully!");
        courseNameField.setText(""); 
        loadCoursesIntoComboBox(); // Refresh the course combo box
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error adding course: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private boolean quizExists(String quizName, int courseId) {
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM quizzes WHERE quiz_name = ? AND course_id = ?")) {
        statement.setString(1, quizName);
        statement.setInt(2, courseId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1) > 0; // If count is greater than 0, the quiz exists
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error checking if quiz exists: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    return false; // Default to not existing if there's an error
}
    
    private int getCourseIdByName(String courseName) {
    int courseId = -1;
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement statement = conn.prepareStatement("SELECT course_id FROM courses WHERE course_name = ?")) {
        statement.setString(1, courseName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            courseId = resultSet.getInt("course_id");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error fetching course ID: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    return courseId;
}
    
    private int getCourseId(String courseName) {
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement("SELECT course_id FROM courses WHERE course_name = ?")) {
        statement.setString(1, courseName);
        ResultSet resultSet = statement.executeQuery();
        
        if (resultSet.next()) {
            return resultSet.getInt("course_id");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error retrieving course ID: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    return -1; // Return -1 if course ID is not found
}
    
    private void openQuestionMakerPanel(int quizId) {
    this.quizId = quizId; // Store the quizId in the class variable

    // Remove the current panel
    if (currentPanel != null) {
        remove(currentPanel);
    }

    JPanel questionMakerPanel = new JPanel() {
    private Image backgroundImage = new ImageIcon("C:\\Users\\medal\\Desktop\\Quizzify\\assets\\QuestionmakerBG.png").getImage();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); 
        }
    };

    questionMakerPanel.setBounds(300, 0, 900, 800); 
    questionMakerPanel.setLayout(null); 


    // Text field for the question
    JLabel questionTextLabel = new JLabel("");
    questionTextLabel.setBounds(50, 170, 150, 30);
    questionMakerPanel.add(questionTextLabel);
    JTextField questionTextField = new JTextField();
    questionTextField.setBounds(350, 170, 500, 30);
    questionMakerPanel.add(questionTextField);

    // Text fields for the options A to D
    JLabel optionALabel = new JLabel("");
    optionALabel.setBounds(50, 257, 150, 30);
    questionMakerPanel.add(optionALabel);
    JTextField optionAField = new JTextField();
    optionAField.setBounds(350, 257, 500, 30);
    questionMakerPanel.add(optionAField);

    JLabel optionBLabel = new JLabel("");
    optionBLabel.setBounds(50, 312, 150, 30);
    questionMakerPanel.add(optionBLabel);
    JTextField optionBField = new JTextField();
    optionBField.setBounds(350, 312, 500, 30);
    questionMakerPanel.add(optionBField);

    JLabel optionCLabel = new JLabel("");
    optionCLabel.setBounds(50, 367, 150, 30);
    questionMakerPanel.add(optionCLabel);
    JTextField optionCField = new JTextField();
    optionCField.setBounds(350, 367, 500, 30);
    questionMakerPanel.add(optionCField);

    JLabel optionDLabel = new JLabel("");
    optionDLabel.setBounds(50, 422, 150, 30);
    questionMakerPanel.add(optionDLabel);
    JTextField optionDField = new JTextField();
    optionDField.setBounds(350, 422, 500, 30);
    questionMakerPanel.add(optionDField);

    // Radio buttons for selecting the correct answer (A to D)
    JLabel correctAnswerLabel = new JLabel("");
    correctAnswerLabel.setBounds(50, 500, 150, 30);
    questionMakerPanel.add(correctAnswerLabel);

    JRadioButton optionARadioButton = new JRadioButton("Option A");
    optionARadioButton.setBounds(350, 480, 100, 30);
    optionARadioButton.setOpaque(false); // Make background transparent
    optionARadioButton.setForeground(new Color(63, 63, 122)); // Set text color (optional)
    optionARadioButton.setFont(new Font("Arial", Font.BOLD, 14)); // Customize font (optional)
    questionMakerPanel.add(optionARadioButton);

    JRadioButton optionBRadioButton = new JRadioButton("Option B");
    optionBRadioButton.setBounds(500, 480, 100, 30);
    optionBRadioButton.setOpaque(false); // Make background transparent
    optionBRadioButton.setForeground(new Color(63, 63, 122)); // Set text color (optional)
    optionBRadioButton.setFont(new Font("Arial", Font.BOLD, 14)); // Customize font (optional)
    questionMakerPanel.add(optionBRadioButton);

    JRadioButton optionCRadioButton = new JRadioButton("Option C");
    optionCRadioButton.setBounds(350, 530, 100, 30);
    optionCRadioButton.setOpaque(false); // Make background transparent
    optionCRadioButton.setForeground(new Color(63, 63, 122)); // Set text color (optional)
    optionCRadioButton.setFont(new Font("Arial", Font.BOLD, 14)); // Customize font (optional)
    questionMakerPanel.add(optionCRadioButton);

    JRadioButton optionDRadioButton = new JRadioButton("Option D");
    optionDRadioButton.setBounds(500, 540, 100, 30);
    optionDRadioButton.setOpaque(false); // Make background transparent
    optionDRadioButton.setForeground(new Color(63, 63, 122)); // Set text color (optional)
    optionDRadioButton.setFont(new Font("Arial", Font.BOLD, 14)); // Customize font (optional)
    questionMakerPanel.add(optionDRadioButton);

    // Group the buttons
    ButtonGroup group = new ButtonGroup();
    group.add(optionARadioButton);
    group.add(optionBRadioButton);
    group.add(optionCRadioButton);
    group.add(optionDRadioButton);

    // Buttons (Save, Next, and Back)
    JButton saveButton = new JButton("Save");
    saveButton.setBounds(200, 700, 150, 40);
    saveButton.setBackground(Color.WHITE); // Opaque white background
    saveButton.setForeground(Color.BLACK); // White text
    questionMakerPanel.add(saveButton);

    JButton nextButton = new JButton("Next");
    nextButton.setBounds(383, 700, 150, 40);
    nextButton.setBackground(Color.WHITE); // Opaque white background
    nextButton.setForeground(Color.BLACK); // White text
    questionMakerPanel.add(nextButton);

    JButton backButton = new JButton("Back");
    backButton.setBackground(Color.WHITE); // Opaque white background
    backButton.setForeground(Color.BLACK); // White text
    backButton.setBounds(566, 700, 150, 40);
    questionMakerPanel.add(backButton);
    
    nextButton.addActionListener(e -> {
        // Get the values from the fields
        String questionText = questionTextField.getText().trim();
        String optionA = optionAField.getText().trim();
        String optionB = optionBField.getText().trim();
        String optionC = optionCField.getText().trim();
        String optionD = optionDField.getText().trim();

        // Check fields if empty
        if (questionText.isEmpty() || optionA.isEmpty() || optionB.isEmpty() || optionC.isEmpty() || optionD.isEmpty()) {
            
            JOptionPane.showMessageDialog(null, "Please fill in all the fields: question and options A, B, C, D.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            
            if (!optionARadioButton.isSelected() && !optionBRadioButton.isSelected() && 
                !optionCRadioButton.isSelected() && !optionDRadioButton.isSelected()) {
                
                JOptionPane.showMessageDialog(null, "Please select the correct answer.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                
                saveQuestion(questionTextField, optionAField, optionBField, optionCField, optionDField, 
                             optionARadioButton, optionBRadioButton, optionCRadioButton, optionDRadioButton, 
                             quizId, loggedInUserId); 

                // Clear the fields 
                questionTextField.setText("");
                optionAField.setText("");
                optionBField.setText("");
                optionCField.setText("");
                optionDField.setText("");

                // Deselect all radio buttons
                optionARadioButton.setSelected(false);
                optionBRadioButton.setSelected(false);
                optionCRadioButton.setSelected(false);
                optionDRadioButton.setSelected(false);

                // show a confirmation message
                JOptionPane.showMessageDialog(null, "Question saved! Fields cleared for the next question.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    });
        
    // ActionListener for saveButton
    saveButton.addActionListener(e -> {
        String questionText = questionTextField.getText().trim();
        String optionA = optionAField.getText().trim();
        String optionB = optionBField.getText().trim();
        String optionC = optionCField.getText().trim();
        String optionD = optionDField.getText().trim();

        // Check if fields are empty
        if (questionText.isEmpty() || optionA.isEmpty() || optionB.isEmpty() || optionC.isEmpty() || optionD.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all the fields: question and options A, B, C, D.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (!optionARadioButton.isSelected() && !optionBRadioButton.isSelected() && 
                !optionCRadioButton.isSelected() && !optionDRadioButton.isSelected()) {
                // error
                JOptionPane.showMessageDialog(null, "Please select the correct answer.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                saveQuestion(questionTextField, optionAField, optionBField, optionCField, optionDField, 
                             optionARadioButton, optionBRadioButton, optionCRadioButton, optionDRadioButton, 
                             quizId, loggedInUserId); // Use the quizId here

                remove(questionMakerPanel);
                openQuizMakerPanel();  

                revalidate();
                repaint();
            }
        }
    });
    
    // go back to the quizmakerPanel
    backButton.addActionListener(e -> {
    remove(questionMakerPanel);

    openMenuPanel(); 
    openQuizMakerPanel(); 
    });

    add(questionMakerPanel);

    revalidate();
    repaint();
}
    
    private void saveQuestion(JTextField questionTextField, JTextField optionAField, JTextField optionBField, JTextField optionCField, JTextField optionDField, 
            JRadioButton optionARadioButton, JRadioButton optionBRadioButton, JRadioButton optionCRadioButton, JRadioButton optionDRadioButton, int quizId, int loggedInUserId) {
      String questionText = questionTextField.getText().trim();
    String optionA = optionAField.getText().trim();
    String optionB = optionBField.getText().trim();
    String optionC = optionCField.getText().trim();
    String optionD = optionDField.getText().trim();
    
    // Determine the correct answer based on selected radio button
    String correctAnswer = null;
    if (optionARadioButton.isSelected()) {
        correctAnswer = "A";
    } else if (optionBRadioButton.isSelected()) {
        correctAnswer = "B";
    } else if (optionCRadioButton.isSelected()) {
        correctAnswer = "C";
    } else if (optionDRadioButton.isSelected()) {
        correctAnswer = "D";
    }

    // Validate input
    if (questionText.isEmpty() || optionA.isEmpty() || optionB.isEmpty() || optionC.isEmpty() || optionD.isEmpty() || correctAnswer == null) {
        JOptionPane.showMessageDialog(null, "All fields must be filled out and a correct answer must be selected.", "Error", JOptionPane.ERROR_MESSAGE);
        return; 
    }

    // SQL command insert the question to database
    String insertSQL = "INSERT INTO questions (quiz_id, question_text, option_a, option_b, option_c, option_d, correct_answer, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
        
        preparedStatement.setInt(1, quizId);
        preparedStatement.setString(2, questionText);
        preparedStatement.setString(3, optionA);
        preparedStatement.setString(4, optionB);
        preparedStatement.setString(5, optionC);
        preparedStatement.setString(6, optionD);
        preparedStatement.setString(7, correctAnswer);
        preparedStatement.setInt(8, loggedInUserId);

        preparedStatement.executeUpdate();
        
        JOptionPane.showMessageDialog(null, "Question saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    } catch (SQLException e) {
        // Error
        JOptionPane.showMessageDialog(null, "Error saving question: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
    public class ButtonRenderer extends DefaultTableCellRenderer {
    private JButton button;

    public ButtonRenderer() {
        button = new JButton();
        button.setOpaque(true);
        button.setBackground(Color.RED); 
        button.setForeground(Color.WHITE);
    }
    
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row) {
        if (value != null) {
            button.setText(value.toString()); 
        } else {
            button.setText("Delete");
        }
        return button; 
    }
}
    
    private void deleteQuiz(int quizId) {
    try (Connection conn = DatabaseConnection.getConnection()) {
        conn.setAutoCommit(false); 

        // Delete questions related to the quiz
        String deleteQuestionsSQL = "DELETE FROM questions WHERE quiz_id = ?";
        try (PreparedStatement deleteQuestionsStmt = conn.prepareStatement(deleteQuestionsSQL)) {
            deleteQuestionsStmt.setInt(1, quizId);
            deleteQuestionsStmt.executeUpdate();
        }

        // Delete the quiz itself
        String deleteQuizSQL = "DELETE FROM quizzes WHERE quiz_id = ?";
        try (PreparedStatement deleteQuizStmt = conn.prepareStatement(deleteQuizSQL)) {
            deleteQuizStmt.setInt(1, quizId);
            deleteQuizStmt.executeUpdate();
        }

        conn.commit(); // Commit the transaction
        JOptionPane.showMessageDialog(this, "Quiz deleted successfully.");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error deleting quiz: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
    private class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;
    private int quizId;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        quizId = (int) table.getValueAt(row, 0); 
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            int confirm = JOptionPane.showConfirmDialog(
                currentPanel,
                "Are you sure you want to delete this quiz?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                deleteQuiz(quizId); 
                openManageQuizPanel(); 
            }
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
    
    // method for managing quiz
    private void openManageQuizPanel() {
    currentPanel = new ImagePanel("C:\\Users\\medal\\Desktop\\Quizzify\\assets\\ManagequizBG.png");
    currentPanel.setBounds(300, 0, 900, 800);
    currentPanel.setLayout(null); 


    // Column names
    String[] columnNames = {"Course Name", "Quiz Name", "Quiz ID"};
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Make table non-editable directly
        }
    };
    quizTable = new JTable(tableModel);

    //table selection
    quizTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    // Fetch quizzes from db
    refreshQuizTable(tableModel);

    // menu pops up
    JPopupMenu popupMenu = createPopupMenu(tableModel);
    quizTable.setComponentPopupMenu(popupMenu);


    JButton editQuizButton = new JButton("Edit Quiz");
    editQuizButton.setBounds(303, 700, 100, 30);
    editQuizButton.setBackground(Color.WHITE);
    editQuizButton.setForeground(Color.BLACK); 
    editQuizButton.addActionListener(e -> {
        int selectedRow = quizTable.getSelectedRow();
        if (selectedRow != -1) {
            int quizId = (int) tableModel.getValueAt(selectedRow, 2);
            openEditQuizDialog(quizId);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Please select a quiz to edit", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
        }
    });

    JButton deleteQuizButton = new JButton("Delete Quiz");
    deleteQuizButton.setBounds(450, 700, 100, 30);
    deleteQuizButton.setBackground(Color.WHITE); 
    deleteQuizButton.setForeground(Color.BLACK); 
    deleteQuizButton.addActionListener(e -> {
        int selectedRow = quizTable.getSelectedRow();
        if (selectedRow != -1) {
            int quizId = (int) tableModel.getValueAt(selectedRow, 2);
            deleteQuiz(quizId, selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Please select a quiz to delete", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
        }
    });

    JScrollPane scrollPane = new JScrollPane(quizTable);
    scrollPane.setBounds(12, 150, 860, 500);//

    currentPanel.add(scrollPane);
    currentPanel.add(editQuizButton);
    currentPanel.add(deleteQuizButton);

    add(currentPanel);
    revalidate();
    repaint();
}

    private JPopupMenu createPopupMenu(DefaultTableModel tableModel) {
    JPopupMenu popupMenu = new JPopupMenu();
    JMenuItem editItem = new JMenuItem("Edit Quiz");
    JMenuItem deleteItem = new JMenuItem("Delete Quiz");

    // Edit action
    editItem.addActionListener(e -> {
        int selectedRow = quizTable.getSelectedRow();
        if (selectedRow != -1) {
            int quizId = (int) tableModel.getValueAt(selectedRow, 2);
            int confirm = JOptionPane.showConfirmDialog(quizTable, 
                "Are you sure you want to edit this quiz?", 
                "Confirm Edit", 
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                openEditQuizDialog(quizId);
            }
        }
    });

    // Delete action
    deleteItem.addActionListener(e -> {
        int selectedRow = quizTable.getSelectedRow();
        if (selectedRow != -1) {
            int quizId = (int) tableModel.getValueAt(selectedRow, 2);
            int confirm = JOptionPane.showConfirmDialog(quizTable, 
                "Are you sure you want to delete this quiz?", 
                "Confirm Deletion", 
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                deleteQuiz(quizId, selectedRow);
            }
        }
    });

    popupMenu.add(editItem);
    popupMenu.add(deleteItem);
    return popupMenu;
}

    private void refreshQuizTable(DefaultTableModel tableModel) {
        // Clear rows
        tableModel.setRowCount(0);

        // Fetch quizzes from db
        String query = "SELECT quizzes.quiz_id, quizzes.quiz_name, courses.course_name " +
                       "FROM quizzes " +
                       "JOIN courses ON quizzes.course_id = courses.course_id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int quizId = rs.getInt("quiz_id");
                String quizName = rs.getString("quiz_name");
                String courseName = rs.getString("course_name");
                tableModel.addRow(new Object[]{courseName, quizName, quizId});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error fetching quizzes: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void populateCourseComboBox(JComboBox<String> courseComboBox) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT course_name FROM courses")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courseComboBox.addItem(rs.getString("course_name"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error fetching courses: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openEditQuizDialog(int quizId) {
    JDialog dialog = new JDialog(this, "Edit Quiz", true);
    dialog.setLayout(new GridLayout(0, 2, 10, 10));
    dialog.setSize(300, 200);

    // Course selection
    JLabel courseLabel = new JLabel("Select Course:");
    JComboBox<String> courseComboBox = new JComboBox<>();
    populateCourseComboBox(courseComboBox);

    // Quiz name input
    JLabel quizNameLabel = new JLabel("Quiz Name:");
    JTextField quizNameField = new JTextField();

    // Fetch current quiz details
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(
             "SELECT quizzes.quiz_name, courses.course_name " +
             "FROM quizzes " +
             "JOIN courses ON quizzes.course_id = courses.course_id " +
             "WHERE quiz_id = ?")) {
        stmt.setInt(1, quizId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            quizNameField.setText(rs.getString("quiz_name"));
            courseComboBox.setSelectedItem(rs.getString("course_name"));
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(dialog, 
            "Error fetching quiz details: " + e.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Update button
    JButton updateButton = new JButton("Update Quiz");
    updateButton.addActionListener(e -> {
        String courseName = (String) courseComboBox.getSelectedItem();
        String quizName = quizNameField.getText().trim();

        if (courseName == null || courseName.isEmpty()) {
            JOptionPane.showMessageDialog(dialog, 
                "Please select a course", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (quizName.isEmpty()) {
            JOptionPane.showMessageDialog(dialog, 
                "Please enter a quiz name", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirmation dialog
        int confirm = JOptionPane.showConfirmDialog(dialog, 
            "Are you sure you want to update this quiz?", 
            "Confirm Update", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Update quiz
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE quizzes SET quiz_name = ?, course_id = " +
                     "(SELECT course_id FROM courses WHERE course_name = ?) " +
                     "WHERE quiz_id = ?")) {
                stmt.setString(1, quizName);
                stmt.setString(2, courseName);
                stmt.setInt(3, quizId);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    // Refresh the table
                    refreshQuizTable((DefaultTableModel) quizTable.getModel());
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this, 
                        "Quiz updated successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(dialog, 
                        "Failed to update quiz", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, 
                    "Error updating quiz: " + ex.getMessage(), 
                    "Database Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    });

    // Add components to dialog
    dialog.add(courseLabel);
    dialog.add(courseComboBox);
    dialog.add(quizNameLabel);
    dialog.add(quizNameField);
    dialog.add(new JLabel()); // Empty label for spacing
    dialog.add(updateButton);

    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
}
    
    private void deleteQuiz(int quizId, int row) {
    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Are you sure you want to delete this quiz? This will also delete all associated questions.",
        "Confirm Deletion",
        JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // First, delete all associated questions
            try (PreparedStatement deleteQuestionsStmt = conn.prepareStatement(
                    "DELETE FROM questions WHERE quiz_id = ?")) {
                deleteQuestionsStmt.setInt(1, quizId);
                deleteQuestionsStmt.executeUpdate();
            }

            // Now delete the quiz
            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM quizzes WHERE quiz_id = ?")) {
                stmt.setInt(1, quizId);
                int deletedRows = stmt.executeUpdate();

                if (deletedRows > 0) {
                    // Remove the row from the table model
                    DefaultTableModel model = (DefaultTableModel) quizTable.getModel();
                    model.removeRow(row);
                    JOptionPane.showMessageDialog(this,
                        "Quiz and associated questions deleted successfully!",
                        "Deletion Successful",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error deleting quiz: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
    
    //panel for quizbank
    private void openQuizBankPanel() {
    quizbankPanel = new JPanel() {
        private Image backgroundImage = new ImageIcon("C:\\Users\\medal\\Desktop\\Quizzify\\assets\\QuizbankBG.png").getImage();

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // Ensure proper rendering
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Draw the image
        }
    };

    quizbankPanel.setBounds(300, 0, 900, 800); // Adjust these values as necessary
    quizbankPanel.setLayout(null);

    JComboBox<String> courseComboBox = new JComboBox<>();
    courseComboBox.setBounds(292, 92, 300, 30); // Adjust bounds as needed
    quizbankPanel.add(courseComboBox);

    JComboBox<String> quizComboBox = new JComboBox<>();
    quizComboBox.setBounds(292, 139, 300, 30); // Adjust bounds as needed
    quizbankPanel.add(quizComboBox);

    // Initialize combooboxes
    initializeComboBoxes(courseComboBox, quizComboBox);

    courseComboBox.addActionListener(e -> {
        String selectedCourse = (String) courseComboBox.getSelectedItem();
        if (selectedCourse != null && !selectedCourse.equals("Select a Course")) {
            loadQuizzesByCourseName(quizComboBox, selectedCourse);
        }
    });

    // Create table models for questions
    DefaultTableModel availableQuestionsModel = new DefaultTableModel(new String[]{"Question", "Option A", "Option B", "Option C", "Option D", "Correct Answer"}, 0);
    DefaultTableModel selectedQuestionsModel = new DefaultTableModel(new String[]{"Question", "Option A", "Option B", "Option C", "Option D", "Correct Answer"}, 0);

    JTable availableQuestionsTable = new JTable(availableQuestionsModel);
    JScrollPane availableScrollPane = new JScrollPane(availableQuestionsTable);
    availableScrollPane.setBounds(79, 192, 727, 206); // Adjust bounds as needed
    quizbankPanel.add(availableScrollPane);

    JTable selectedQuestionsTable = new JTable(selectedQuestionsModel);
    JScrollPane selectedScrollPane = new JScrollPane(selectedQuestionsTable);
    selectedScrollPane.setBounds(79, 468, 727, 206); // Adjust bounds as needed
    quizbankPanel.add(selectedScrollPane);

    JButton addButton = new JButton("Add");
    addButton.setBounds(329, 421, 100, 30);
    addButton.addActionListener(e -> {
        int selectedRow = availableQuestionsTable.getSelectedRow();
        if (selectedRow != -1) {
            Object[] questionData = new Object[6]; 
            for (int i = 0; i < 6; i++) {
                questionData[i] = availableQuestionsModel.getValueAt(selectedRow, i);
            }
            selectedQuestionsModel.addRow(questionData);
            availableQuestionsModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(quizbankPanel, "Please select a question to add.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    });
    quizbankPanel.add(addButton);

    // remove selected question 
    JButton removeButton = new JButton("Remove");
    removeButton.setBounds(455, 421, 100, 30); 
    removeButton.addActionListener(e -> {
        int selectedRow = selectedQuestionsTable.getSelectedRow();
        if (selectedRow != -1) {
            Object[] questionData = new Object[6]; 
            for (int i = 0; i < 6; i++) {
                questionData[i] = selectedQuestionsModel.getValueAt(selectedRow, i);
            }
            availableQuestionsModel.addRow(questionData);
            selectedQuestionsModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(quizbankPanel, "Please select a question to remove.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    });
    quizbankPanel.add(removeButton);

    JTextField newQuizNameField = new JTextField("");
    newQuizNameField.setBounds(79, 711, 300, 30); // Adjust bounds as needed
    quizbankPanel.add(newQuizNameField);

    JComboBox<String> newCourseComboBox = new JComboBox<>();
    newCourseComboBox.setBounds(392, 711, 300, 30); // Adjust bounds as needed
    quizbankPanel.add(newCourseComboBox);

    initializeCourseComboBox(newCourseComboBox);

    // Button to save the new quiz
    JButton saveButton = new JButton("Save Quiz");
    saveButton.setBounds(706, 711, 100, 30); // Adjust bounds as needed
    saveButton.addActionListener(e -> {
        String newQuizName = newQuizNameField.getText().trim();
        String selectedCourse = (String) newCourseComboBox.getSelectedItem(); // Use new course combo box
        saveNewQuiz(newQuizName, selectedCourse, selectedQuestionsModel);
    });
    quizbankPanel.add(saveButton);

    quizComboBox.addActionListener(e -> {
        String selectedQuiz = (String) quizComboBox.getSelectedItem();
        if (selectedQuiz != null && !"Select a Quiz".equals(selectedQuiz)) {
            loadQuestionsForQuiz(selectedQuiz, availableQuestionsModel);
        } else {
            availableQuestionsModel.setRowCount(0); // Clear questions if no quiz is selected
        }
    });

    getContentPane().add(quizbankPanel);
    revalidate(); 
    repaint(); 
}
    
    private void loadQuizzesByCourseName(JComboBox<String> quizComboBox, String courseName) {
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement getCourseIdStmt = conn.prepareStatement(
             "SELECT course_id FROM courses WHERE course_name = ?")) {
        
        getCourseIdStmt.setString(1, courseName);
        ResultSet courseIdRs = getCourseIdStmt.executeQuery();

        quizComboBox.removeAllItems();
        quizComboBox.addItem("Select a Quiz");

        if (courseIdRs.next()) {
            int courseId = courseIdRs.getInt("course_id");
            System.out.println("Loaded course ID: " + courseId);

            // Fetch quizzes
            try (PreparedStatement getQuizzesStmt = conn.prepareStatement(
                "SELECT quiz_name FROM quizzes WHERE course_id = ?")) {
                getQuizzesStmt.setInt(1, courseId); 
                ResultSet quizzesRs = getQuizzesStmt.executeQuery();

                boolean hasQuizzes = false;
                while (quizzesRs.next()) {
                    String quizName = quizzesRs.getString("quiz_name");
                    quizComboBox.addItem(quizName);
                    hasQuizzes = true;
                    System.out.println("Found quiz: " + quizName); 
                }
                    // error handling
                if (!hasQuizzes) {
                    JOptionPane.showMessageDialog(quizbankPanel, "No quizzes found for the selected course.", "No Data", JOptionPane.WARNING_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(quizbankPanel, "No course found with the name: " + courseName, "Error", JOptionPane.WARNING_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace(); 
        JOptionPane.showMessageDialog(quizbankPanel, "Failed to load quizzes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private void loadQuestionsForQuiz(String quizName, DefaultTableModel questionModel) {
    
    if (quizName == null || quizName.trim().isEmpty() || quizName.equals("Select a Quiz")) {
        questionModel.setRowCount(0);
        return;
    }

    // Clear questions
    questionModel.setRowCount(0);
    
    try (Connection conn = DatabaseConnection.getConnection();
         // Prepare  get quiz details
         PreparedStatement quizStmt = conn.prepareStatement(
             "SELECT quiz_id FROM quizzes WHERE quiz_name = ?")) {
        
        quizStmt.setString(1, quizName);
        
        try (ResultSet quizRs = quizStmt.executeQuery()) {
            if (quizRs.next()) {
                int quizId = quizRs.getInt("quiz_id");

                // Fetch questions for the specific quiz
                try (PreparedStatement questionStmt = conn.prepareStatement(
                    "SELECT question_id, question_text, option_a, option_b, option_c, option_d, correct_answer " +
                    "FROM questions WHERE quiz_id = ?")) {
                    
                    questionStmt.setInt(1, quizId);
                    
                    try (ResultSet rs = questionStmt.executeQuery()) {
                        while (rs.next()) {
                            questionModel.addRow(new Object[]{
                                rs.getString("question_text"),
                                rs.getString("option_a"),
                                rs.getString("option_b"),
                                rs.getString("option_c"),
                                rs.getString("option_d"),
                                rs.getString("correct_answer") // Add correct answer
                            });
                        }
                        
                        // If no questions found
                        if (questionModel.getRowCount() == 0) {
                            JOptionPane.showMessageDialog(quizbankPanel, 
                                "No questions found for the quiz: " + quizName, 
                                "Information", 
                                JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(quizbankPanel, 
                    "No quiz found with the name: " + quizName, 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    } catch (SQLException e) {        
        // Error
        JOptionPane.showMessageDialog(quizbankPanel, 
            "An error occurred while loading questions: " + e.getMessage(), 
            "Database Error", 
            JOptionPane.ERROR_MESSAGE);
    }
}

    private void initializeCourseComboBox(JComboBox<String> comboBox) {
    // Populate course combo box 
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement("SELECT course_name FROM courses");
         ResultSet rs = stmt.executeQuery()) {

        comboBox.addItem("Select a Course"); // Default option
        while (rs.next()) {
            comboBox.addItem(rs.getString("course_name"));
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(quizbankPanel,
                "Error loading courses: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
    private void saveNewQuiz(String quizName, String selectedCourse, DefaultTableModel selectedQuestionsModel) {
    if (quizName == null || quizName.trim().isEmpty() || selectedCourse == null || selectedCourse.equals("Select a Course")) {
        JOptionPane.showMessageDialog(quizbankPanel, "Please enter a quiz name and select a course.", "Warning", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try (Connection conn = DatabaseConnection.getConnection()) {
        try (PreparedStatement insertQuizStmt = conn.prepareStatement(
                "INSERT INTO quizzes (quiz_name, course_id) VALUES (?, (SELECT course_id FROM courses WHERE course_name = ?))")) {
            insertQuizStmt.setString(1, quizName);
            insertQuizStmt.setString(2, selectedCourse);
            insertQuizStmt.executeUpdate();
        }

        int newQuizId;
        try (PreparedStatement quizIdStmt = conn.prepareStatement("SELECT quiz_id FROM quizzes WHERE quiz_name = ?")) {
            quizIdStmt.setString(1, quizName);
            ResultSet rs = quizIdStmt.executeQuery();
            if (rs.next()) {
                newQuizId = rs.getInt("quiz_id");
            } else {
                throw new SQLException("Failed to retrieve the new quiz ID.");
            }
        }

        for (int i = 0; i < selectedQuestionsModel.getRowCount(); i++) {
            String questionText = (String) selectedQuestionsModel.getValueAt(i, 0);
            String optionA = (String) selectedQuestionsModel.getValueAt(i, 1);
            String optionB = (String) selectedQuestionsModel.getValueAt(i, 2);
            String optionC = (String) selectedQuestionsModel.getValueAt(i, 3);
            String optionD = (String) selectedQuestionsModel.getValueAt(i, 4);
            String correctAnswer = (String) selectedQuestionsModel.getValueAt(i, 5);

            try (PreparedStatement insertQuestionStmt = conn.prepareStatement(
                    "INSERT INTO questions (quiz_id, question_text, option_a, option_b, option_c, option_d, correct_answer) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
                insertQuestionStmt.setInt(1, newQuizId);
                insertQuestionStmt.setString(2, questionText);
                insertQuestionStmt.setString(3, optionA);
                insertQuestionStmt.setString(4, optionB);
                insertQuestionStmt.setString(5, optionC);
                insertQuestionStmt.setString(6, optionD);
                insertQuestionStmt.setString(7, correctAnswer);
                insertQuestionStmt.executeUpdate();
            }
        }

        JOptionPane.showMessageDialog(quizbankPanel, "Quiz saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        selectedQuestionsModel.setRowCount(0); // Clear the selected questions after saving
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(quizbankPanel, "An error occurred while saving the quiz: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
    // panel to edit quiz question
    private void openEditQuizPanel() {
      editquizPanel = new JPanel() {
          private Image backgroundImage = new ImageIcon("C:\\Users\\medal\\Desktop\\Quizzify\\assets\\EditquizBG.png").getImage();

          @Override
          protected void paintComponent(Graphics g) {
              super.paintComponent(g); 
              g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); 
          }
      };

      editquizPanel.setBounds(300, 0, 900, 800);
      editquizPanel.setLayout(null);

      // Course ComboBox
      JLabel courseLabel = new JLabel("");
      courseLabel.setBounds(50, 95, 100, 30);
      editquizPanel.add(courseLabel);

      JComboBox<String> courseComboBox = new JComboBox<>();
      courseComboBox.setBounds(347, 95, 200, 30);
      editquizPanel.add(courseComboBox);

      // Quiz ComboBox
      JLabel quizLabel = new JLabel("");
      quizLabel.setBounds(50, 135, 100, 30);
      editquizPanel.add(quizLabel);

      JComboBox<String> quizComboBox = new JComboBox<>();
      quizComboBox.setBounds(347, 135, 200, 30);
      editquizPanel.add(quizComboBox);

      // Initialize combo boxes
      initializeComboBoxes(courseComboBox, quizComboBox);

      // Quiz Table
      String[] columnNames = {"Question", "Option A", "Option B", "Option C", "Option D", "Correct Answer"};
      DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
      JTable quizzesTable = new JTable(tableModel);
      JScrollPane scrollPane = new JScrollPane(quizzesTable);
      scrollPane.setBounds(12, 170, 860, 500); // Adjusted for better placement
      editquizPanel.add(scrollPane);

      // Add Row Button
      JButton addRowButton = new JButton("Add Row");
      addRowButton.setBackground(Color.WHITE); // Opaque white background
      addRowButton.setForeground(Color.BLACK);
      addRowButton.setBounds(243, 700, 100, 30);
      addRowButton.addActionListener(e -> tableModel.addRow(new Object[]{"", "", "", "", "", ""}));
      editquizPanel.add(addRowButton);

      // Delete Row Button
      JButton deleteRowButton = new JButton("Delete Row");
      deleteRowButton.setBackground(Color.WHITE); // Opaque white background
      deleteRowButton.setForeground(Color.BLACK);
      deleteRowButton.setBounds(353, 700, 100, 30);
      deleteRowButton.addActionListener(e -> {
          int selectedRow = quizzesTable.getSelectedRow();
          if (selectedRow != -1) {
              tableModel.removeRow(selectedRow);
          } else {
              JOptionPane.showMessageDialog(editquizPanel, "Please select a row to delete.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
          }
      });
      editquizPanel.add(deleteRowButton);

      // Save Button
      JButton saveButton = new JButton("Save Changes");
      saveButton.setBackground(Color.WHITE); // Opaque white background
      saveButton.setForeground(Color.BLACK);
      saveButton.setBounds(463, 700, 150, 30);
      saveButton.addActionListener(e -> {
          String selectedQuiz = (String) quizComboBox.getSelectedItem();
          if (selectedQuiz != null) {
              saveQuizDataToDatabase(tableModel, selectedQuiz);
          } else {
              JOptionPane.showMessageDialog(editquizPanel, "Please select a quiz first.", "No Quiz Selected", JOptionPane.WARNING_MESSAGE);
          }
      });
      editquizPanel.add(saveButton);

      courseComboBox.addActionListener(e -> {
          String selectedCourse = (String) courseComboBox.getSelectedItem();
          if (selectedCourse != null) {
              loadQuizzesForCourse(quizComboBox, selectedCourse);
          }
      });

      quizComboBox.addActionListener(e -> {
          String selectedQuiz = (String) quizComboBox.getSelectedItem();
          if (selectedQuiz != null) {
              loadQuizData(tableModel, selectedQuiz);
          }
      });

      add(editquizPanel);
      revalidate();
      repaint();
  }

    private void initializeComboBoxes(JComboBox<String> courseComboBox, JComboBox<String> quizComboBox) {
      // Clear items
      courseComboBox.removeAllItems();
      quizComboBox.removeAllItems();

      // Placeholder
      courseComboBox.addItem("Select a Course");
      quizComboBox.addItem("Select a Quiz");

      // Load course
      try (Connection conn = DatabaseConnection.getConnection();
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery("SELECT course_name FROM courses")) {

          while (rs.next()) {
              courseComboBox.addItem(rs.getString("course_name"));
          }
      } catch (SQLException e) {
          e.printStackTrace();
          JOptionPane.showMessageDialog(editquizPanel, "Failed to load courses.", "Error", JOptionPane.ERROR_MESSAGE);
      }
  }

    private void loadCourses(JComboBox<String> courseComboBox) {
      try (Connection conn = DatabaseConnection.getConnection();
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery("SELECT course_name FROM courses")) {
          courseComboBox.removeAllItems();
          while (rs.next()) {
              courseComboBox.addItem(rs.getString("course_name"));
          }
      } catch (SQLException e) {
          e.printStackTrace();
          JOptionPane.showMessageDialog(editquizPanel, "Failed to load courses.", "Error", JOptionPane.ERROR_MESSAGE);
      }
  }

    private void loadQuizzesForCourse(JComboBox<String> quizComboBox, String courseName) {
      try (Connection conn = DatabaseConnection.getConnection();
           PreparedStatement getCourseIdStmt = conn.prepareStatement(
               "SELECT course_id FROM courses WHERE course_name = ?");
           PreparedStatement getQuizzesStmt = conn.prepareStatement(
               "SELECT quiz_name FROM quizzes WHERE course_id = ?")) {

          // Fetch course_id based on course_name
          getCourseIdStmt.setString(1, courseName);
          ResultSet courseIdRs = getCourseIdStmt.executeQuery();

          if (courseIdRs.next()) {
              int courseId = courseIdRs.getInt("course_id");
              System.out.println("Selected course_id: " + courseId); // Debugging output

              // Fetch quizzes for the course_id
              getQuizzesStmt.setInt(1, courseId);
              ResultSet quizzesRs = getQuizzesStmt.executeQuery();

              // Clear the quiz combo box and populate it
              quizComboBox.removeAllItems();
              boolean hasQuizzes = false;
              while (quizzesRs.next()) {
                  String quizName = quizzesRs.getString("quiz_name");
                  quizComboBox.addItem(quizName);
                  hasQuizzes = true;
                  System.out.println("Loaded quiz: " + quizName); // Debugging output
              }

              if (!hasQuizzes) {
                  JOptionPane.showMessageDialog(editquizPanel, "No quizzes found for the selected course.", "No Data", JOptionPane.WARNING_MESSAGE);
              }
          } else {
              quizComboBox.removeAllItems();
              JOptionPane.showMessageDialog(editquizPanel, "No course_id found for the selected course.", "Error", JOptionPane.WARNING_MESSAGE);
          }
      } catch (SQLException e) {
          e.printStackTrace();
          JOptionPane.showMessageDialog(editquizPanel, "Failed to load quizzes.", "Error", JOptionPane.ERROR_MESSAGE);
      }
  }

    private void loadQuizData(DefaultTableModel tableModel, String quizName) {
      try (Connection conn = DatabaseConnection.getConnection();
           PreparedStatement getQuizIdStmt = conn.prepareStatement(
               "SELECT quiz_id FROM quizzes WHERE quiz_name = ?");
           PreparedStatement getQuestionsStmt = conn.prepareStatement(
               "SELECT question_text, option_a, option_b, option_c, option_d, correct_answer " +
               "FROM questions WHERE quiz_id = ?")) {

          // Clear data in the table 
          tableModel.setRowCount(0);

          getQuizIdStmt.setString(1, quizName);
          ResultSet quizIdRs = getQuizIdStmt.executeQuery();

          if (quizIdRs.next()) {
              int quizId = quizIdRs.getInt("quiz_id");
              System.out.println("Selected quiz_id: " + quizId); 

              getQuestionsStmt.setInt(1, quizId);
              ResultSet rs = getQuestionsStmt.executeQuery();

              boolean hasQuestions = false;
              while (rs.next()) {
                  tableModel.addRow(new Object[]{
                      rs.getString("question_text"), 
                      rs.getString("option_a"),     
                      rs.getString("option_b"),      
                      rs.getString("option_c"),      
                      rs.getString("option_d"),      
                      rs.getString("correct_answer") 
                  });
                  hasQuestions = true;
              }

              if (!hasQuestions) {
                  JOptionPane.showMessageDialog(editquizPanel, "No questions found for this quiz.", "No Data", JOptionPane.INFORMATION_MESSAGE);
              }
          } else {
              JOptionPane.showMessageDialog(editquizPanel, "No quiz found with the selected name.", "Error", JOptionPane.WARNING_MESSAGE);
          }
      } catch (SQLException e) {
          e.printStackTrace();
          JOptionPane.showMessageDialog(editquizPanel, "Failed to load quiz data.", "Error", JOptionPane.ERROR_MESSAGE);
      }
  }

   private void saveQuizDataToDatabase(DefaultTableModel tableModel, String quizName) {
      try (Connection conn = DatabaseConnection.getConnection()) {
          int quizId = -1;
          try (PreparedStatement getQuizIdStmt = conn.prepareStatement(
                  "SELECT quiz_id FROM quizzes WHERE quiz_name = ?")) {
              getQuizIdStmt.setString(1, quizName);
              ResultSet rs = getQuizIdStmt.executeQuery();
              if (rs.next()) {
                  quizId = rs.getInt("quiz_id");
              } else {
                  JOptionPane.showMessageDialog(editquizPanel, "Quiz not found.", "Error", JOptionPane.ERROR_MESSAGE);
                  return;
              }
          }

          // Prepare statement to insert or update questions
          try (PreparedStatement stmt = conn.prepareStatement(
                  "INSERT INTO questions (quiz_id, question_text, option_a, option_b, option_c, option_d, correct_answer) " +
                  "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                  "ON DUPLICATE KEY UPDATE " +
                  "question_text = VALUES(question_text), " +
                  "option_a = VALUES(option_a), " +
                  "option_b = VALUES(option_b), " +
                  "option_c = VALUES(option_c), " +
                  "option_d = VALUES(option_d), " +
                  "correct_answer = VALUES(correct_answer)")) {

              // First, delete existing questions for this quiz to avoid duplicates
              try (PreparedStatement deleteStmt = conn.prepareStatement(
                      "DELETE FROM questions WHERE quiz_id = ?")) {
                  deleteStmt.setInt(1, quizId);
                  deleteStmt.executeUpdate();
              }

              // Prepare and execute batch insert for new questions
              for (int i = 0; i < tableModel.getRowCount(); i++) {
                  // Validate row data before inserting
                  String question = (String) tableModel.getValueAt(i, 0);
                  String optionA = (String) tableModel.getValueAt(i, 1);
                  String optionB = (String) tableModel.getValueAt(i, 2);
                  String optionC = (String) tableModel.getValueAt(i, 3);
                  String optionD = (String) tableModel.getValueAt(i, 4);
                  String correctAnswer = (String) tableModel.getValueAt(i, 5);

                  // Skip empty rows
                  if (question == null || question.trim().isEmpty()) {
                      continue;
                  }

                  stmt.setInt(1, quizId);
                  stmt.setString(2, question);
                  stmt.setString(3, optionA);
                  stmt.setString(4, optionB);
                  stmt.setString(5, optionC);
                  stmt.setString(6, optionD);
                  stmt.setString(7, correctAnswer);

                  stmt.addBatch();
              }

              // Execute batch insert
              stmt.executeBatch();

              JOptionPane.showMessageDialog(editquizPanel, "Quiz data saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
          }
      } catch (SQLException e) {
          e.printStackTrace();
          JOptionPane.showMessageDialog(editquizPanel, "Failed to save quiz data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
  }

    // method for managing users
    private void openManageUserPanel() {
        
    currentPanel = new ImagePanel("C:\\Users\\medal\\Desktop\\Quizzify\\assets\\ManageuserBG.png");
    currentPanel.setBounds(300, 0, 900, 800);
    currentPanel.setLayout(null); 

    // Components for search functionality
    JTextField searchField = new JTextField(15);
    searchField.setBounds(420, 100, 200, 30); 
    
    JButton searchButton = new JButton("Search");
    searchButton.setBackground(Color.WHITE); // Opaque white background
    searchButton.setForeground(Color.BLACK); // Black text
    searchButton.setBounds(630, 100, 100, 30); 
    
    String[] searchCriteria = {"Username", "Email", "Number"};
    JComboBox<String> searchCriteriaComboBox = new JComboBox<>(searchCriteria);
    searchCriteriaComboBox.setBounds(280, 100, 120, 30);
    
    JLabel roleLabel = new JLabel("Role:");
    roleLabel.setBounds(460, 100, 50, 30); 
    String[] roles = {"All", "Admin", "User"};
    
    JComboBox<String> roleComboBox = new JComboBox<>(roles);
    roleComboBox.setBounds(135, 100, 120, 30); 

    searchButton.addActionListener(e -> {
        String searchText = searchField.getText().trim();
        String selectedField = (String) searchCriteriaComboBox.getSelectedItem();
        String selectedRole = (String) roleComboBox.getSelectedItem();
        searchUsers(selectedField, searchText, selectedRole);
    });

    // Add search components to panel
    currentPanel.add(searchField);
    currentPanel.add(searchButton);
    currentPanel.add(searchCriteriaComboBox);
    currentPanel.add(roleLabel);
    currentPanel.add(roleComboBox);

    // Users table
    String[] columnNames = {"User ID", "Username", "Email", "Number", "Role"};
    tableModel = new DefaultTableModel(columnNames, 0); // Initialize tableModel
    userTable = new JTable(tableModel); // Initialize userTable
    loadUsers(tableModel);
    JScrollPane scrollPane = new JScrollPane(userTable);
    scrollPane.setBounds(12, 150, 860, 500); // Position and size of the scroll pane
    currentPanel.add(scrollPane);

    // Add action listener for the table
    userTable.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int row = userTable.getSelectedRow();
            if (row != -1) {
                if (e.getClickCount() == 2) {
                    int userId = (Integer) userTable.getValueAt(row, 0);
                    showEditUserDialog(userId);
                }
            }
        }
    });

    // Edit button
    JButton editButton = new JButton("Edit");
    editButton.setBackground(Color.WHITE); // Opaque white background
    editButton.setForeground(Color.BLACK); // Black text
    editButton.setBounds(303, 700, 100, 30); // Set position and size
    editButton.addActionListener(e -> {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            int userId = (Integer) userTable.getValueAt(selectedRow, 0);
            showEditUserDialog(userId);
        } else {
            JOptionPane.showMessageDialog(currentPanel, "Please select a user to edit.", "No User Selected", JOptionPane.WARNING_MESSAGE);
        }
    });
    
    // Delete button
    JButton deleteButton = new JButton("Delete");
    deleteButton.setBackground(Color.WHITE); // Opaque white background
    deleteButton.setForeground(Color.BLACK); // Black text
    deleteButton.setBounds(450, 700, 100, 30); // Set position and size
    deleteButton.addActionListener(e -> {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            int userId = (Integer) userTable.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(currentPanel, "Are you sure you want to delete this user?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                deleteUser(userId);
                tableModel.removeRow(selectedRow);
            }
        } else {
            JOptionPane.showMessageDialog(currentPanel, "Please select a user to delete.", "No User Selected", JOptionPane.WARNING_MESSAGE);
        }
    });

    currentPanel.add(editButton);
    currentPanel.add(deleteButton);

    add(currentPanel);
    revalidate();
    repaint();
}

    private void loadUsers(DefaultTableModel tableModel) {
    tableModel.setRowCount(0);
    
    String query = "SELECT user_id, username, email, number, role FROM users"; // Adjust query to include role
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            int userId = rs.getInt("user_id");
            String username = rs.getString("username");
            String email = rs.getString("email");
            String number = rs.getString("number"); 
            String role = rs.getString("role"); 

            tableModel.addRow(new Object[]{userId, username, email, number, role});
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error fetching users: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
    private void searchUsers(String selectedField, String searchText, String selectedRole) {
    // Reload users 
    StringBuilder queryBuilder = new StringBuilder("SELECT user_id, username, email, number, role FROM users WHERE 1=1");
    
    if (!searchText.isEmpty()) {
        switch (selectedField) {
            case "Username":
                queryBuilder.append(" AND username LIKE ?");
                break;
            case "Email":
                queryBuilder.append(" AND email LIKE ?");
                break;
            case "Number":
                queryBuilder.append(" AND number LIKE ?");
                break;
        }
    }
    
    // Filter by role 
    if (!selectedRole.equals("All")) {
        queryBuilder.append(" AND role = ?");
    }

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(queryBuilder.toString())) {
        
        int parameterIndex = 1; // To track the position of parameters in the SQL query
        
        if (!searchText.isEmpty()) {
            stmt.setString(parameterIndex++, "%" + searchText + "%");
        }
        
        if (!selectedRole.equals("All")) {
            stmt.setString(parameterIndex++, selectedRole);
        }
        
        ResultSet rs = stmt.executeQuery();
        
        tableModel.setRowCount(0); // Clear rows
        
        while (rs.next()) {
            int userId = rs.getInt("user_id");
            String username = rs.getString("username");
            String email = rs.getString("email");
            String number = rs.getString("number");
            String role = rs.getString("role");

            // Add row with user data
            tableModel.addRow(new Object[]{userId, username, email, number, role});
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error searching users: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private void showEditUserDialog(int userId) {
    JDialog editDialog = new JDialog();
    editDialog.setTitle("Edit User");
    editDialog.setLayout(new GridLayout(5, 2));
    editDialog.setSize(300, 250);

    // Center dialog on the screen
    editDialog.setLocationRelativeTo(this); 

    JTextField usernameField = new JTextField();
    JTextField emailField = new JTextField();
    JTextField numberField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JLabel passwordLabel = new JLabel("New Password:");

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement("SELECT username, email, number, role FROM users WHERE user_id = ?")) {
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            usernameField.setText(rs.getString("username"));
            emailField.setText(rs.getString("email"));
            numberField.setText(rs.getString("number"));
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error fetching user details: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    editDialog.add(new JLabel("Username:"));
    editDialog.add(usernameField);
    editDialog.add(new JLabel("Email:"));
    editDialog.add(emailField);
    editDialog.add(new JLabel("Number:"));
    editDialog.add(numberField);
    editDialog.add(passwordLabel);
    editDialog.add(passwordField);

    JButton saveButton = new JButton("Save");
    saveButton.addActionListener(e -> {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE users SET username = ?, email = ?, number = ?, password = CASE WHEN ? <> '' THEN ? ELSE password END WHERE user_id = ?")) {
            stmt.setString(1, usernameField.getText());
            stmt.setString(2, emailField.getText());
            stmt.setString(3, numberField.getText());
            stmt.setString(4, new String(passwordField.getPassword())); // Check if password field is empty
            stmt.setString(5, new String(passwordField.getPassword())); // Use password only if not empty
            stmt.setInt(6, userId);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(editDialog, "User updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Refresh the table
            DefaultTableModel tableModel = (DefaultTableModel) ((JTable) ((JScrollPane) currentPanel.getComponent(1)).getViewport().getView()).getModel();
            loadUsers(tableModel);

            editDialog.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error updating user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    JButton cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(e -> editDialog.dispose());

    editDialog.add(saveButton);
    editDialog.add(cancelButton); 

    editDialog.setModal(true);
    editDialog.setVisible(true);
}

    private void deleteUser(int userId) {
    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this user?", "Delete User", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE user_id = ?")) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "User deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Refresh the table
            DefaultTableModel tableModel = (DefaultTableModel) ((JTable) ((JScrollPane) currentPanel.getComponent(1)).getViewport().getView()).getModel();
            loadUsers(tableModel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error deleting user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
   
    // method for opening resusts
    private void openResultsPanel() {
        
        currentPanel = new ImagePanel("C:\\Users\\medal\\Desktop\\Quizzify\\assets\\QuizresultBG.png"); 
        currentPanel.setBounds(300, 0, 900, 800); 
        currentPanel.setLayout(null); 

        // Search Criteria ComboBox
        JLabel searchLabel = new JLabel("");
        searchLabel.setBounds(170, 90, 100, 30);
        currentPanel.add(searchLabel);

        JComboBox<String> searchCriteriaComboBox = new JComboBox<>();
        searchCriteriaComboBox.setBounds(250, 90, 150, 30);
        searchCriteriaComboBox.addItem("All");
        searchCriteriaComboBox.addItem("Username");
        searchCriteriaComboBox.addItem("Course Name");
        searchCriteriaComboBox.addItem("Attempt Date");
        searchCriteriaComboBox.addItem("Quiz Name");
        searchCriteriaComboBox.addItem("Correct Answers");
        searchCriteriaComboBox.addItem("Incorrect Answers");
        searchCriteriaComboBox.addItem("Total Questions");
        currentPanel.add(searchCriteriaComboBox);

        // Search Field
        JTextField searchField = new JTextField();
        searchField.setBounds(410, 90, 200, 30);
        currentPanel.add(searchField);

        // Search Button
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(620, 90, 100, 30);
        searchButton.setBackground(Color.WHITE); 
        searchButton.setForeground(Color.BLACK); 
        currentPanel.add(searchButton);

        // Table to display quiz results
        String[] columnNames = {"Quiz Name", "Username", "Course Name", "Total Questions", 
                                "Correct Answers", "Incorrect Answers", "Attempt Date", "Time Taken"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable resultsTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make the table non-editable
            }
        };

        // Hide the first column (result_id)
        resultsTable.getColumnModel().getColumn(0).setMinWidth(0);
        resultsTable.getColumnModel().getColumn(0).setMaxWidth(0);
        resultsTable.getColumnModel().getColumn(0).setPreferredWidth(0);

        // ScrollPane for the table
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setBounds(12, 150, 860, 500);
        currentPanel.add(scrollPane);

        // Buttons for Edit and Delete
        JButton editButton = new JButton("Edit");
        editButton.setBounds(335, 700, 100, 30);
        editButton.setBackground(Color.WHITE); // Opaque white background
        editButton.setForeground(Color.BLACK); // Black text
        currentPanel.add(editButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(455, 700, 100, 30);
        deleteButton.setBackground(Color.WHITE); // Opaque white background
        deleteButton.setForeground(Color.BLACK); // Black text
        currentPanel.add(deleteButton);

        // Load quiz results into the table
        loadQuizResults(tableModel, null, null); // Initially load all results

        // Add action listeners
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            String selectedCriteria = (String) searchCriteriaComboBox.getSelectedItem();
            loadQuizResults(tableModel, selectedCriteria, searchText.isEmpty() ? null : searchText);
        });

        editButton.addActionListener(e -> editSelectedResult(resultsTable, tableModel));
        deleteButton.addActionListener(e -> deleteSelectedResult(resultsTable, tableModel));

        // Add the unified panel to the frame
        add(currentPanel);
        revalidate();
        repaint();
    }

    private void loadQuizResults(DefaultTableModel tableModel, String selectedCriteria, String searchText) {
        tableModel.setRowCount(0); // Clear existing rows
        String query = "SELECT qr.result_id, q.quiz_name, u.username, c.course_name, " +
                       "qr.total_questions, qr.correct_answers, qr.incorrect_answers, " +
                       "qr.attempt_date, qr.time_taken " +
                       "FROM quiz_results qr " +
                       "JOIN quizzes q ON qr.quiz_id = q.quiz_id " +
                       "JOIN users u ON qr.user_id = u.user_id " +
                       "JOIN courses c ON q.course_id = c.course_id";

        // Search criteria
        if (searchText != null) {
            switch (selectedCriteria) {
                case "Username":
                    query += " WHERE u.username LIKE ?";
                    break;
                case "Course Name":
                    query += " WHERE c.course_name LIKE ?";
                    break;
                case "Attempt Date":
                    query += " WHERE qr.attempt_date LIKE ?";
                    break;
                case "Quiz Name":
                    query += " WHERE q.quiz_name LIKE ?";
                    break;
                case "Correct Answers":
                    query += " WHERE qr.correct_answers = ?";
                    break;
                case "Incorrect Answers":
                    query += " WHERE qr.incorrect_answers = ?";
                    break;
                case "Total Questions":
                    query += " WHERE qr.total_questions = ?";
                    break;
            }
        }

        // Clear the result
        resultIdMap.clear(); 

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            if (searchText != null) {
                stmt.setString(1, "%" + searchText + "%");
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int resultId = rs.getInt("result_id"); 
                String quizName = rs.getString("quiz_name");
                String username = rs.getString("username");
                String courseName = rs.getString("course_name");
                int totalQuestions = rs.getInt("total_questions");
                int correctAnswers = rs.getInt("correct_answers");
                int incorrectAnswers = rs.getInt("incorrect_answers");
                java.sql.Timestamp attemptDate = rs.getTimestamp("attempt_date");
                int timeTaken = rs.getInt("time_taken");

                int rowIndex = tableModel.getRowCount(); 
                tableModel.addRow(new Object[]{resultId, quizName, username, courseName, totalQuestions, 
                                               correctAnswers, incorrectAnswers, attemptDate, timeTaken});
                resultIdMap.put(rowIndex, resultId); 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading quiz results: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editSelectedResult(JTable table, DefaultTableModel tableModel) {
        int selectedRow = table.getSelectedRow(); 
        if (selectedRow == -1) { 
            JOptionPane.showMessageDialog(this, "Please select a row to edit.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer resultId = resultIdMap.get(selectedRow);
        if (resultId == null) { 
            JOptionPane.showMessageDialog(this, "Unable to find the result ID for the selected row.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        editQuizResult(resultId, tableModel, selectedRow);
    }

    private void deleteSelectedResult(JTable table, DefaultTableModel tableModel) {
        int selectedRow = table.getSelectedRow(); 
        if (selectedRow == -1) { 
            JOptionPane.showMessageDialog(this, "Please select a row to delete.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Retrieve the result_id 
        Integer resultId = resultIdMap.get(selectedRow);
        if (resultId == null) { 
            JOptionPane.showMessageDialog(this, "Invalid selection. Cannot find result ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

       
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this quiz result?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {

            deleteQuizResult(resultId, tableModel, selectedRow);
        }
    }
    
    private void editQuizResult(Integer resultId, DefaultTableModel tableModel, int selectedRow) {
    // Retrieve current values from the table model
    String quizName = (String) tableModel.getValueAt(selectedRow, 1); 
    String username = (String) tableModel.getValueAt(selectedRow, 2); 
    String courseName = (String) tableModel.getValueAt(selectedRow, 3);
    int totalQuestions = (int) tableModel.getValueAt(selectedRow, 4);
    int correctAnswers = (int) tableModel.getValueAt(selectedRow, 5); 
    int incorrectAnswers = (int) tableModel.getValueAt(selectedRow, 6); 

    // Panel for the edit dialog
    JPanel editPanel = new JPanel(new GridLayout(5, 2, 5, 5));
    JTextField correctAnswersField = new JTextField(String.valueOf(correctAnswers));
    JTextField incorrectAnswersField = new JTextField(String.valueOf(incorrectAnswers));

    // Add components to panel
    editPanel.add(new JLabel("Quiz Name:"));
    editPanel.add(new JLabel(quizName)); 
    editPanel.add(new JLabel("Username:"));
    editPanel.add(new JLabel(username)); 
    editPanel.add(new JLabel("Course Name:"));
    editPanel.add(new JLabel(courseName)); 
    editPanel.add(new JLabel("Correct Answers:"));
    editPanel.add(correctAnswersField);
    editPanel.add(new JLabel("Incorrect Answers:"));
    editPanel.add(incorrectAnswersField);

    // Show edit dialog
    int option = JOptionPane.showConfirmDialog(this, editPanel, "Edit Quiz Result", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (option == JOptionPane.OK_OPTION) {
        // Validate/Parse input values
        if (!validateAndUpdateAnswers(correctAnswersField, incorrectAnswersField, totalQuestions)) {
            return; 
        }

        // Confirmation dialog for updating quiz results
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to update this quiz result?", 
            "Confirm Update", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Update the db
                String updateQuery = "UPDATE quiz_results SET correct_answers = ?, incorrect_answers = ? WHERE result_id = ?";
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
                    stmt.setInt(1, Integer.parseInt(correctAnswersField.getText().trim()));
                    stmt.setInt(2, Integer.parseInt(incorrectAnswersField.getText().trim()));
                    stmt.setInt(3, resultId);
                    stmt.executeUpdate();
                }

                // Update the table model 
                tableModel.setValueAt(correctAnswersField.getText().trim(), selectedRow, 5);
                tableModel.setValueAt(incorrectAnswersField.getText().trim(), selectedRow, 6);

                JOptionPane.showMessageDialog(this, "Quiz result updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating quiz result: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

    private boolean validateAndUpdateAnswers(JTextField correctAnswersField, JTextField incorrectAnswersField, int totalQuestions) {
    try {
        int newCorrectAnswers = Integer.parseInt(correctAnswersField.getText().trim());
        int newIncorrectAnswers = Integer.parseInt(incorrectAnswersField.getText().trim());

        // Validate input
        if (newCorrectAnswers < 0 || newIncorrectAnswers < 0) {
            JOptionPane.showMessageDialog(this, "Answers cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (newCorrectAnswers + newIncorrectAnswers > totalQuestions) {
            JOptionPane.showMessageDialog(this, "The total of correct and incorrect answers cannot exceed total questions.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Please enter valid numbers for correct and incorrect answers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    return true;
}

    private void deleteQuizResult(Integer resultId, DefaultTableModel tableModel, int selectedRow) {
    if (resultId == null) {
        JOptionPane.showMessageDialog(this, "Invalid selection. Cannot find result ID.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Delete the result from the DB
    String deleteQuery = "DELETE FROM quiz_results WHERE result_id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {

        stmt.setInt(1, resultId);
        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected > 0) {
            // Remove row from table
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Quiz result deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error: No matching record found to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error deleting quiz result: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private String getQuizNameById(int quizId) {
    String quizName = "Unknown Quiz";
    String query = "SELECT quiz_name FROM quizzes WHERE quiz_id = ?";
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setInt(1, quizId);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                quizName = rs.getString("quiz_name");
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error fetching quiz name: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    return quizName;
}
    
    private Map<Integer, Integer> resultIdMap = new HashMap<>();
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminMenu adminMenu = new AdminMenu(1); 
            adminMenu.setVisible(true);
        });
    }

}