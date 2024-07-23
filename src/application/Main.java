package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main extends Application {

    private TableView<DriverPoints> table = new TableView<>();
    private ObservableList<DriverPoints> data = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Drivers with Most Points");

        // Definir las columnas
        TableColumn<DriverPoints, String> driverRefCol = new TableColumn<>("Driver Reference");
        driverRefCol.setCellValueFactory(new PropertyValueFactory<>("driverRef"));
        driverRefCol.setPrefWidth(200); // Puedes ajustar el ancho predeterminado si lo deseas

        TableColumn<DriverPoints, Number> pointsCol = new TableColumn<>("Total Points");
        pointsCol.setCellValueFactory(new PropertyValueFactory<>("totalPoints"));
        pointsCol.setPrefWidth(150); // Puedes ajustar el ancho predeterminado si lo deseas

        table.getColumns().addAll(driverRefCol, pointsCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Cargar datos desde la base de datos
        loadDataFromDatabase();

        // Configurar el BorderPane y la escena
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(table);

        Scene scene = new Scene(borderPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadDataFromDatabase() {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/Formula 1";
        String jdbcUser = "postgres";
        String jdbcPassword = "julio2004";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             Statement statement = connection.createStatement()) {

            // Consulta SQL
            String sql = "SELECT d.driver_ref AS driver_ref, SUM(r.points) AS total_points " +
                         "FROM results r " +
                         "JOIN drivers d ON r.driver_id = d.driver_id " +
                         "GROUP BY d.driver_ref " +
                         "ORDER BY total_points DESC";
            ResultSet resultSet = statement.executeQuery(sql);

            // Agregar datos a la lista observable
            while (resultSet.next()) {
                String driverRef = resultSet.getString("driver_ref");
                int totalPoints = resultSet.getInt("total_points");

                data.add(new DriverPoints(driverRef, totalPoints));
            }
            table.setItems(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
