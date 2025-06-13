package app.controller;

import app.model.Birthday;
import app.util.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.sql.*;
import java.time.LocalDate;

public class BirthdayController {

    @FXML
    private TextField nameField;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private TableView<Birthday> tableView;

    @FXML
    private TableColumn<Birthday, String> nameColumn;

    @FXML
    private TableColumn<Birthday, LocalDate> dobColumn;

    private ObservableList<Birthday> birthdayList = FXCollections.observableArrayList();
    private Birthday selectedBirthday;

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        dobColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getDateOfBirth()));

        tableView.setItems(birthdayList);
        loadBirthdays();

        tableView.setOnMouseClicked(this::handleRowSelect);
    }

    private void loadBirthdays() {
        birthdayList.clear();
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM birthdays")) {
            while (rs.next()) {
                birthdayList.add(new Birthday(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDate("date_of_birth").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBirthday() {
        String name = nameField.getText();
        LocalDate dob = dobPicker.getValue();

        if (name.isEmpty() || dob == null) {
            showAlert("All fields are required.");
            return;
        }

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO birthdays (name, date_of_birth) VALUES (?, ?)")) {
            ps.setString(1, name);
            ps.setDate(2, Date.valueOf(dob));
            ps.executeUpdate();
            loadBirthdays();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBirthday() {
        if (selectedBirthday == null) {
            showAlert("No entry selected to update.");
            return;
        }

        String name = nameField.getText();
        LocalDate dob = dobPicker.getValue();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE birthdays SET name=?, date_of_birth=? WHERE id=?")) {
            ps.setString(1, name);
            ps.setDate(2, Date.valueOf(dob));
            ps.setInt(3, selectedBirthday.getId());
            ps.executeUpdate();
            loadBirthdays();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBirthday() {
        if (selectedBirthday == null) {
            showAlert("No entry selected to delete.");
            return;
        }

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM birthdays WHERE id=?")) {
            ps.setInt(1, selectedBirthday.getId());
            ps.executeUpdate();
            loadBirthdays();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleRowSelect(MouseEvent event) {
        selectedBirthday = tableView.getSelectionModel().getSelectedItem();
        if (selectedBirthday != null) {
            nameField.setText(selectedBirthday.getName());
            dobPicker.setValue(selectedBirthday.getDateOfBirth());
        }
    }

    private void clearFields() {
        nameField.clear();
        dobPicker.setValue(null);
        selectedBirthday = null;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
