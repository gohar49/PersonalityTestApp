// PersonalityTestApp.java
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PersonalityTestApp extends Application {

    private String name;
    private int age;
    private int questionIndex;
    private PersonalityQuestion[] questions;

    private Label questionLabel;
    private Slider answerSlider;
    private Label progressLabel;
    private ProgressBar progressBar;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.questions = new PersonalityQuestion[15];

        // Initialize questions
        questions[0] = new PersonalityQuestion("I am easily disturbed by loud noises.");
        questions[1] = new PersonalityQuestion("I often feel overwhelmed by my emotions.");
        questions[2] = new PersonalityQuestion("I enjoy meeting new people and making new friends.");
        questions[3] = new PersonalityQuestion("I prefer sticking to a well-established routine.");
        questions[4] = new PersonalityQuestion("I am fascinated by the intricacies of art and nature.");
        questions[5] = new PersonalityQuestion("I tend to avoid conflicts and disagreements.");
        questions[6] = new PersonalityQuestion("I find it easy to start conversations with strangers.");
        questions[7] = new PersonalityQuestion("I am very organized and detail-oriented.");
        questions[8] = new PersonalityQuestion("I am open to trying new and unconventional things.");
        questions[9] = new PersonalityQuestion("I often put others' needs before my own.");
        questions[10] = new PersonalityQuestion("I enjoy taking risks and trying new activities.");
        questions[11] = new PersonalityQuestion("I feel a deep connection to the world around me.");
        questions[12] = new PersonalityQuestion("I believe that rules should be followed strictly.");
        questions[13] = new PersonalityQuestion("I am always seeking new experiences and adventures.");
        questions[14] = new PersonalityQuestion("I value harmony and avoid confrontations.");

        Button startButton = new Button("Start");
        startButton.setOnAction(e -> {
            takeNameAndAge(primaryStage);
        });

        progressLabel = new Label("Progress: 0/" + questions.length);
        progressBar = new ProgressBar(0);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(startButton, 0, 3, 2, 1);

        Scene scene = new Scene(grid, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Personality Test App");
        primaryStage.show();
    }

    private void takeNameAndAge(Stage primaryStage) {
        // Take name and age in the beginning
        Stage inputStage = new Stage();
        inputStage.setTitle("Name and Age");

        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.setPadding(new Insets(10, 10, 10, 10));

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        Label ageLabel = new Label("Age:");
        TextField ageField = new TextField();

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            if (isValidInput(nameField.getText(), ageField.getText())) {
                name = nameField.getText();
                age = Integer.parseInt(ageField.getText());
                inputStage.close();
                displayQuestion(primaryStage);
            } else {
                showAlert("Invalid Input", "Please enter valid information for Name and Age.");
            }
        });

        inputGrid.add(nameLabel, 0, 0);
        inputGrid.add(nameField, 1, 0);
        inputGrid.add(ageLabel, 0, 1);
        inputGrid.add(ageField, 1, 1);
        inputGrid.add(submitButton, 0, 2, 2, 1);

        Scene inputScene = new Scene(inputGrid, 300, 150);
        inputStage.setScene(inputScene);
        inputStage.initModality(Modality.APPLICATION_MODAL);
        inputStage.showAndWait();
    }

    private boolean isValidInput(String name, String age) {
        return !name.isEmpty() && name.matches("[a-zA-Z]+") && age.matches("\\d+") &&
                Integer.parseInt(age) > 0 && Integer.parseInt(age) <= 150;
    }

    private void displayQuestion(Stage primaryStage) {
        if (questionIndex < 15) {
            PersonalityQuestion currentQuestion = questions[questionIndex];

            GridPane questionGrid = new GridPane();
            questionGrid.setHgap(10);
            questionGrid.setVgap(10);
            questionGrid.setPadding(new Insets(10, 10, 10, 10));

            Label currentQuestionLabel = new Label(currentQuestion.getQuestion());
            answerSlider = new Slider(1, 5, 3);

            Button nextButton = new Button("Next");
            nextButton.setOnAction(e -> {
                // Save the answer and move to the next question
                currentQuestion.setAnswer((int) answerSlider.getValue());
                questionIndex++;

                // Check if all questions are answered
                if (questionIndex < 15) {
                    // Update the question label and reset the slider
                    currentQuestionLabel.setText(questions[questionIndex].getQuestion());
                    answerSlider.setValue(3); // Reset to default value
                    progressLabel.setText("Progress: " + (questionIndex + 1) + "/" + questions.length);
                    progressBar.setProgress((double) (questionIndex + 1) / questions.length);
                } else {
                    // Last question, calculate and display results
                    displayResults(primaryStage);
                }
            });

            questionGrid.add(currentQuestionLabel, 0, 0);
            questionGrid.add(answerSlider, 0, 1);
            questionGrid.add(nextButton, 0, 2);

            VBox vbox = new VBox();
            vbox.getChildren().addAll(progressLabel, progressBar, questionGrid);

            Scene questionScene = new Scene(vbox, 400, 250);
            primaryStage.setScene(questionScene);
        }
    }

    private void displayResults(Stage primaryStage) {
        // Calculate personality scores (simplified for illustration)
        int extraversionScore = calculateScore(0, 4);
        int emotionalStabilityScore = calculateScore(1, 3);
        int agreeablenessScore = calculateScore(4, 9);
        int conscientiousnessScore = calculateScore(2, 7);
        int opennessScore = calculateScore(8, 14);

        // Display results in a new window
        Stage resultStage = new Stage();
        resultStage.setTitle("Personality Test Results");

        GridPane resultGrid = new GridPane();
        resultGrid.setHgap(10);
        resultGrid.setVgap(10);
        resultGrid.setPadding(new Insets(10, 10, 10, 10));

        Label resultLabel = new Label("Results for " + name + " (Age: " + age + "):");
        Label extraversionLabel = new Label("Extraversion: " + calculatePercentage(extraversionScore) + "%");
        Label emotionalStabilityLabel = new Label("Emotional Stability: " + calculatePercentage(emotionalStabilityScore) + "%");
        Label agreeablenessLabel = new Label("Agreeableness: " + calculatePercentage(agreeablenessScore) + "%");
        Label conscientiousnessLabel = new Label("Conscientiousness: " + calculatePercentage(conscientiousnessScore) + "%");
        Label opennessLabel = new Label("Openness: " + calculatePercentage(opennessScore) + "%");

        resultGrid.add(resultLabel, 0, 0, 2, 1);
        resultGrid.add(extraversionLabel, 0, 1, 2, 1);
        resultGrid.add(emotionalStabilityLabel, 0, 2, 2, 1);
        resultGrid.add(agreeablenessLabel, 0, 3, 2, 1);
        resultGrid.add(conscientiousnessLabel, 0, 4, 2, 1);
        resultGrid.add(opennessLabel, 0, 5, 2, 1);

        Scene resultScene = new Scene(resultGrid, 400, 250);
        resultStage.setScene(resultScene);

        // Set the main stage title back to the original
        primaryStage.setTitle("Personality Test App");

        // Show the results stage
        resultStage.show();
    }

    private int calculateScore(int questionStartIndex, int questionEndIndex) {
        int score = 0;
        for (int i = questionStartIndex; i <= questionEndIndex; i++) {
            score += questions[i].getAnswer();
        }
        return score;
    }

    private int calculatePercentage(int score) {
        // Adjusting the percentage calculation to be out of 75, as there are 15 questions with 5 options each
        return (score * 100) / 75;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}