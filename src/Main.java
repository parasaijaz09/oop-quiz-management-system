import java.util.ArrayList;
import java.util.Scanner;

// ==================== INTERFACE ====================

/*
 Interface used for grading functionality
 */
interface Gradable {

    // Method to calculate percentage score
    int calculateScore(int correctAnswers, int totalQuestions);

    // Method to return grade
    String getGrade(int score);
}

// ==================== ABSTRACT CLASS ====================

/*
 Abstract class representing a generic question
 */
abstract class Question {

    // Encapsulated/Private variables
    private String questionText;
    private String category;
    private String correctAnswer;

    // Constructor
    public Question(String questionText, String category, String correctAnswer) {
        this.questionText = questionText;
        this.category = category;
        this.correctAnswer = correctAnswer;
    }

    // Getter methods
    public String getQuestionText() { return questionText; }

    public String getCategory() { return category; }

    public String getCorrectAnswer() { return correctAnswer; }

    // Abstract method for checking answers
    public abstract boolean checkAnswer(String userAnswer);

    // Method to display question
    public void displayQuestion() {
        System.out.println("Category: " + category);
        System.out.println("Q: " + questionText);
    }
}

// ==================== MCQ QUESTION ====================

/*
 Class representing Multiple Choice Questions
 Inherits Question class
 */
class MCQQuestion extends Question {

    // MCQ options
    private String optionA, optionB, optionC, optionD;

    // Constructor
    public MCQQuestion(String questionText, String category, String correctAnswer,
                       String optionA, String optionB, String optionC, String optionD) {

        super(questionText, category, correctAnswer);

        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
    }

    // Getter methods
    public String getOptionA() { return optionA; }

    public String getOptionB() { return optionB; }

    public String getOptionC() { return optionC; }

    public String getOptionD() { return optionD; }

    // Overridden method for checking answers
    @Override
    public boolean checkAnswer(String userAnswer) {
        return getCorrectAnswer().equalsIgnoreCase(userAnswer);
    }

    // Overridden method for displaying MCQ
    @Override
    public void displayQuestion() {

        super.displayQuestion();

        System.out.println("A: " + optionA);
        System.out.println("B: " + optionB);
        System.out.println("C: " + optionC);
        System.out.println("D: " + optionD);
    }
}

// ==================== TRUE FALSE QUESTION ====================

/*
 Class representing True/False Questions
 Inherits Question class
 */
class TrueFalseQuestion extends Question {

    // Constructor
    public TrueFalseQuestion(String questionText, String category, String correctAnswer) {
        super(questionText, category, correctAnswer);
    }

    // Overridden method for checking answers
    @Override
    public boolean checkAnswer(String userAnswer) {
        return getCorrectAnswer().equalsIgnoreCase(userAnswer);
    }

    // Overridden method for displaying question
    @Override
    public void displayQuestion() {

        super.displayQuestion();

        System.out.println("Options: True / False");
    }
}

// ==================== SCORE MANAGER ====================

/*
 Class responsible for score calculation and grading
 Implements Gradable interface
 */
class ScoreManager implements Gradable {

    // Encapsulated variables
    private int correctAnswers;
    private int totalQuestions;

    // Constructor
    public ScoreManager(int correctAnswers, int totalQuestions) {
        this.correctAnswers = correctAnswers;
        this.totalQuestions = totalQuestions;
    }

    // Getter methods
    public int getCorrectAnswers() { return correctAnswers; }

    public int getTotalQuestions() { return totalQuestions; }

    // Method for calculating score percentage
    @Override
    public int calculateScore(int correctAnswers, int totalQuestions) {

        if (totalQuestions == 0)
            return 0;

        return (correctAnswers * 100) / totalQuestions;
    }

    // Method for assigning grades
    @Override
    public String getGrade(int score) {

        if (score >= 90)
            return "A";

        else if (score >= 80)
            return "B";

        else if (score >= 70)
            return "C";

        else if (score >= 60)
            return "D";

        else
            return "F";
    }
}

// ==================== QUIZ ====================

/*
 Class representing a quiz
 Uses composition with Question class
 */
class Quiz {

    // Quiz details
    private String quizTitle;
    private String category;

    // HAS-A relationship with Question class
    private ArrayList<Question> questions;

    // Constructor
    public Quiz(String quizTitle, String category) {

        this.quizTitle = quizTitle;
        this.category = category;
        this.questions = new ArrayList<>();
    }

    // Getter methods
    public String getQuizTitle() { return quizTitle; }

    public String getCategory() { return category; }

    // Method for adding questions
    public void addQuestion(Question q) {
        questions.add(q);
    }

    // Method for returning total questions
    public int getTotalQuestions() {
        return questions.size();
    }

    // Method for conducting quiz
    public int conductQuiz() {

        Scanner scanner = new Scanner(System.in);

        int correct = 0;

        System.out.println("\n=============================");
        System.out.println("  Quiz: " + quizTitle);
        System.out.println("  Category: " + category);
        System.out.println("=============================\n");

        // Loop through all questions
        for (int i = 0; i < questions.size(); i++) {

            Question q = questions.get(i);
            System.out.println("Q" + (i + 1) + ": ");
            q.displayQuestion();
            System.out.print("Your Answer: ");

            String userAnswer = scanner.nextLine();

            // Polymorphism used here
            if (q.checkAnswer(userAnswer)) {

                System.out.println("✓ Correct!\n");

                correct++;

            } else {

                System.out.println("✗ Wrong! Correct answer: "
                        + q.getCorrectAnswer() + "\n");
            }
        }

        return correct;
    }
}

// ==================== STUDENT ====================

/*
 Class representing a student in the quiz system
 */
class Student {

    // Student details
    private String name;
    private String studentId;
    private int totalScore;
    private String grade;

    // Constructor
    public Student(String name, String studentId) {

        this.name = name;
        this.studentId = studentId;

        this.totalScore = 0;
        this.grade = "";
    }

    // Getter methods
    public String getName() { return name; }

    public String getStudentId() { return studentId; }

    public int getTotalScore() { return totalScore; }

    public String getGrade() { return grade; }

    // Setter methods
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    // Method for displaying final result
    public void displayResult() {

        System.out.println("\n=============================");
        System.out.println("        RESULT CARD");
        System.out.println("=============================");

        System.out.println("Name       : " + name);
        System.out.println("Student ID : " + studentId);
        System.out.println("Score      : " + totalScore + "%");
        System.out.println("Grade      : " + grade);

        System.out.println("=============================\n");
    }
}

// ==================== MAIN ====================

/*
 Main class of Quiz Management System
 */
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Taking student information
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your Student ID: ");
        String id = scanner.nextLine();

        // Creating student object
        Student student = new Student(name, id);

        // Creating quiz object
        Quiz quiz = new Quiz("OOP Quiz", "Programming");

        // Adding MCQ questions
        quiz.addQuestion(new MCQQuestion(
                "Which concept hides data from outside?", "OOP", "A",
                "Encapsulation", "Inheritance", "Polymorphism", "Abstraction"
        ));

        quiz.addQuestion(new MCQQuestion(
                "Which keyword is used to inherit a class in Java?", "OOP", "B",
                "super", "extends", "implements", "this"
        ));

        quiz.addQuestion(new MCQQuestion(
                "What does JVM stand for?", "Java", "A",
                "Java Virtual Machine", "Java Variable Method",
                "Java Verified Module", "Just Virtual Machine"
        ));

        // Adding True/False questions
        quiz.addQuestion(new TrueFalseQuestion(
                "Java is a platform-independent language.", "Java", "True"
        ));

        quiz.addQuestion(new TrueFalseQuestion(
                "An abstract class can be instantiated directly.", "OOP", "False"
        ));

        // Conducting quiz
        int correctAnswers = quiz.conductQuiz();

        // Creating ScoreManager object
        ScoreManager scoreManager =
                new ScoreManager(correctAnswers, quiz.getTotalQuestions());

        // Calculating score and grade
        int score = scoreManager.calculateScore(
                correctAnswers,
                quiz.getTotalQuestions());

        String grade = scoreManager.getGrade(score);

        // Storing results in student object
        student.setTotalScore(score);
        student.setGrade(grade);

        // Displaying final result
        student.displayResult();

    }
}