package tn.esprit.Controllers;

import javafx.scene.chart.PieChart;

public class DashboardBack {

    private PieChart salesPieChart;
    public void initialize() {
        PieChart.Data slice1 = new PieChart.Data("Produit A", 30);
        PieChart.Data slice2 = new PieChart.Data("Produit B", 70);

        salesPieChart.getData().addAll(slice1, slice2);
    }
}
