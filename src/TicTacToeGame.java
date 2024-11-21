import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TicTacToeGame extends Application {
    private static final int SIZE = 5;
    private Button[][] buttons = new Button[SIZE][SIZE];
    private char currentPlayer = 'X';
    private Label statusLabel = new Label("Player X's turn");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        GridPane grid = new GridPane();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                buttons[row][col] = new Button("");
                buttons[row][col].setMinSize(50, 50);
                buttons[row][col].setOnAction(e -> handleButtonClick((Button) e.getSource()));
                grid.add(buttons[row][col], col, row);
            }
        }

        root.setCenter(grid);
        root.setBottom(statusLabel);

        Scene scene = new Scene(root, 300, 350);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tic Tac Toe 5x5");
        primaryStage.show();
    }

    private void handleButtonClick(Button button) {
        if (button.getText().isEmpty()) {
            button.setText(String.valueOf(currentPlayer));
            if (checkWin()) {
                showAlert("Player " + currentPlayer + " wins!");
                resetBoard();
            } else if (isBoardFull()) {
                showAlert("It's a tie!");
                resetBoard();
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                statusLabel.setText("Player " + currentPlayer + "'s turn");
            }
        }
    }

    private boolean checkWin() {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < SIZE; i++) {
            if (checkLine(buttons[i][0], buttons[i][1], buttons[i][2], buttons[i][3], buttons[i][4]) ||
                checkLine(buttons[0][i], buttons[1][i], buttons[2][i], buttons[3][i], buttons[4][i])) {
                return true;
            }
        }
        return checkLine(buttons[0][0], buttons[1][1], buttons[2][2], buttons[3][3], buttons[4][4]) ||
               checkLine(buttons[0][4], buttons[1][3], buttons[2][2], buttons[3][1], buttons[4][0]);
    }

    private boolean checkLine(Button... buttons) {
        String text = buttons[0].getText();
        if (text.isEmpty()) return false;
        for (Button button : buttons) {
            if (!button.getText().equals(text)) {
                return false;
            }
        }
        return true;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void resetBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                buttons[row][col].setText("");
            }
        }
        currentPlayer = 'X';
        statusLabel.setText("Player X's turn");
    }
}