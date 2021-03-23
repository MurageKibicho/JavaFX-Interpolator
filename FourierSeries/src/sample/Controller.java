package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    //Initialize Widgets
    double[] newPoints = {0.5, 1.5, 2.5, 3.5, 4.5, 5.0};
    double newYValue = 0;
    String[] xCoordinatesString = {"0.0", "1.0", "2.0", "3.0", "4.0", "5.0"};
    double[] xCoordinates = {0.0, 1.0, 2.0, 3.0, 4.0, 5.0};
    double[] yCoordinates = {2, 3, 12, 70, 100, 147};

    @FXML
    public Button calculateButton;
    public LineChart<String, Number> redChart;
    public Text redSeriesText;

    //Initialize widgets
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void calculateButtonClick(ActionEvent event){
        XYChart.Series<String, Number> redSeries = new XYChart.Series<>();
        redSeries.setName("Predicted Values");
        XYChart.Series<String, Number> blueSeries = new XYChart.Series<>();
        blueSeries.setName("Actual values");
        double[] newYvalues = new double[newPoints.length];
        for(int i = 0; i < xCoordinates.length; i++){
            newYvalues[i] = interpolate(xCoordinates, yCoordinates,newPoints[i]);

            redSeries.getData().add(
                    new XYChart.Data<String, Number>
                                        (String.valueOf(xCoordinates[i]),
                                            newYvalues[i]));
            blueSeries.getData().add(
                    new XYChart.Data<String, Number>
                            (xCoordinatesString[i],
                                    yCoordinates[i]));
        }
        redSeriesText.setText("Coefficients: \n" + Arrays.toString(newYvalues));
        redChart.getData().addAll(redSeries, blueSeries);
    }
    public double interpolate(double x[], double y[], double newPoint){
        int numberOfPoints = x.length;
        double eachResult = 0;

        for(int i = 0; i < numberOfPoints; i ++){
            double term = y[i];
            for (int j = 0; j < numberOfPoints; j++){
                if (j != i){
                    term = term*(newPoint - x[j]) / (x[i] -x[j]);
                }
            }
            eachResult += term;
        }
        return eachResult;
    }
}
