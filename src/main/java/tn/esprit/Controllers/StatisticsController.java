package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import tn.esprit.entities.Baby;
import tn.esprit.services.BabyServices;
import tn.esprit.services.InfoMedicauxServices;

import java.util.ArrayList;
import java.util.List;

public class StatisticsController {

    @FXML
    private PieChart pieChart;
    @FXML
    private BarChart<String, Number> barChart;

    public void initialize() {

        InfoMedicauxServices infoMedicauxServices = new InfoMedicauxServices();
        // Retrieve the data for the pie chart
        int totalBabies = infoMedicauxServices.getTotalNumberOfBabies();
        int boysCount = infoMedicauxServices.getNumberOfBoys();
        int girlsCount = infoMedicauxServices.getNumberOfGirls();

        // Calculate the percentage of boys and girls
        double boysPercentage = (double) boysCount / totalBabies * 100;
        double girlsPercentage = (double) girlsCount / totalBabies * 100;

        // Create pie chart data
        List<PieChart.Data> pieChartData = new ArrayList<>();
        pieChartData.add(new PieChart.Data("Boys (" + String.format("%.2f%%", boysPercentage) + ")", boysPercentage));
        pieChartData.add(new PieChart.Data("Girls (" + String.format("%.2f%%", girlsPercentage) + ")", girlsPercentage));

        // Set the data to the pie chart
        pieChart.setData(FXCollections.observableArrayList(pieChartData));

        // Customize label format for each pie chart section
        pieChart.getData().forEach(data ->
                data.nameProperty().setValue(String.format("%.2f%% - %s", data.getPieValue(), data.getName()))
        );
        BabyServices babyServices = new BabyServices();
        List<Baby> babies = babyServices.getAll();

        // Prepare data for the bar chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Baby baby : babies) {
            series.getData().add(new XYChart.Data<>(baby.getNom(), baby.getPoids()));
        }

        // Set data to the bar chart
        barChart.getData().add(series);
    }
}
