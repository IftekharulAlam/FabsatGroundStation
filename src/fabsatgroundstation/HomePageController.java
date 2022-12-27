/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fabsatgroundstation;

import gnu.io.CommPortIdentifier;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * FXML Controller class
 *
 * @author IftekharulAlam
 */
public class HomePageController implements Initializable {

    String myPort = "";
    ArduinoSerial sensorData;
    Thread t;

    @FXML
    private TextArea textArea;
    @FXML
    private Button connectbutton;
    @FXML
    private ComboBox comPortComboBox;
    @FXML
    private Button disconnectbutton;

    @FXML
    private LineChart altitudechart;
    @FXML
    private NumberAxis y;
    @FXML
    private CategoryAxis x;
    @FXML
    private LineChart tempchart;
    @FXML
    private NumberAxis y1;
    @FXML
    private CategoryAxis x1;
    @FXML
    private LineChart voltageChart;
    @FXML
    private NumberAxis y2;
    @FXML
    private CategoryAxis x2;
    @FXML
    private LineChart titleXChart;
    @FXML
    private NumberAxis y11;
    @FXML
    private CategoryAxis x11;
    @FXML
    private LineChart titlYChart;
    @FXML
    private NumberAxis y111;
    @FXML
    private CategoryAxis x111;
    XYChart.Series series;
    XYChart.Series series1;
    XYChart.Series series2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comPortComboBox.getItems().clear();
        // TODO
        Enumeration portList = null;

        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {

            CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();

            comPortComboBox.getItems().add(portId.getName());

        }

    }

    @FXML
    private void connectButtonClicked(ActionEvent event) {
        myPort = comPortComboBox.getValue().toString();

        if (myPort.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Not valid");
        } else {
            sensorData = new ArduinoSerial(myPort);
            sensorData.initialize();
            series = new XYChart.Series();
            series.getData().add(new XYChart.Data("2011", 15));
            altitudechart.getData().add(series);
            series1 = new XYChart.Series();
            series1.getData().add(new XYChart.Data("2011", 15));
            tempchart.getData().add(series1);
            t = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(1000);
                            String s = sensorData.read();
                            textArea.appendText(s);
                            textArea.appendText("\n");
                            series.getData().add(new XYChart.Data(s, 15));
                            altitudechart.getData().add(series);
                            series1.getData().add(new XYChart.Data(s, 15));

                            tempchart.getData().add(series1);

                        } catch (Exception e) {

                        }
                    }
                }
            };
            t.start();

        }

    }

    @FXML
    private void disconnectButtonClicked(ActionEvent event) {
        sensorData.close();
        t.stop();

    }

    @FXML
    private void scanButtonclicked(ActionEvent event) {
        comPortComboBox.getItems().clear();
        // TODO
        Enumeration portList = null;

        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {

            CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();

            comPortComboBox.getItems().add(portId.getName());

        }
    }

}
