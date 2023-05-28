/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fabsatgroundstation;

import gnu.io.CommPortIdentifier;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.shape.Rectangle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Iftekharul Alam
 */
public class MyFileController implements Initializable {

    private String myPort = "";
    private ArduinoSerial sensorData;
    private Thread t;
    XYChart.Series series;
    XYChart.Series series1;
    XYChart.Series series2;
    private String TEAM_ID = "";
    private String MISSION_TIME = "";
    private String PACKET_COUNT = "";
    private String MODE = "";
    private String STATE = "";
    private String ALTITUDE = "";
    private String HS_DEPLOYED = "";
    private String PC_DEPLOYED = "";
    private String MAST_RAISED = "";
    private String TEMPERATURE = "";
    private String PRESSURE = "";
    private String VOLTAGE = "";
    private String GPS_TIME = "";
    private String GPS_ALTITUDE = "";
    private String GPS_LATITUDE = "";
    private String GPS_LONGITUDE = "";
    private String GPS_SATS = "";
    private String TILT_X = "";
    private String TILT_Y = "";
    private String CMD_ECHO = "";

    @FXML
    private ComboBox comPortComboBox;
    @FXML
    private TextField sendDataField;
    @FXML
    private RadioButton modeF;
    @FXML
    private RadioButton modeS;
    @FXML
    private LineChart altitudechart;
    @FXML
    private NumberAxis y;
    @FXML
    private CategoryAxis x;
    @FXML
    private LineChart voltageChart;
    @FXML
    private NumberAxis y1;
    @FXML
    private CategoryAxis x1;
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
    @FXML
    private NumberAxis y3;
    @FXML
    private CategoryAxis x3;
    @FXML
    private LineChart temperatureChart;
    @FXML
    private NumberAxis y2;
    @FXML
    private CategoryAxis x2;
    @FXML
    private LineChart pressureChart;
    @FXML
    private NumberAxis y21;
    @FXML
    private CategoryAxis x21;
    @FXML
    private Label missionTimeRectangle;
    @FXML
    private TextArea telemetryLogTextArea;
    @FXML
    private RadioButton hs_deployed_P_radioButton;
    @FXML
    private RadioButton hs_deployed_N_radioButton;
    @FXML
    private LineChart gPSaltitudechart;
    @FXML
    private Button sendButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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

    @FXML
    private void sendButtonClicked(ActionEvent event) {
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
            series1.getData().add(new XYChart.Data("2010", 12));
            temperatureChart.getData().add(series1);
            t = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(1000);
                            String s = sensorData.read();

                            if (s.length() > 10) {

                                String array[] = s.split(",");
                                int y = Integer.parseInt(array[2]);

                                TEAM_ID = array[0];
                                MISSION_TIME = array[1];
                                PACKET_COUNT = array[2];
                                MODE = array[3];
                                STATE = array[4];
                                ALTITUDE = array[5];
                                HS_DEPLOYED = array[6];
                                PC_DEPLOYED = array[7];
                                MAST_RAISED = array[8];
                                TEMPERATURE = array[9];
                                PRESSURE = array[10];
                                VOLTAGE = array[11];
                                GPS_TIME = array[12];
                                GPS_ALTITUDE = array[13];
                                GPS_LATITUDE = array[14];
                                GPS_LONGITUDE = array[15];
                                GPS_SATS = array[16];
                                TILT_X = array[17];
                                TILT_Y = array[18];
                                CMD_ECHO = array[19];
                                telemetryLogTextArea.appendText(s);
                                telemetryLogTextArea.appendText("\n");
                            }
                            series.getData().add(new XYChart.Data(ALTITUDE, y));
                            altitudechart.getData().add(series);
                            series1.getData().add(new XYChart.Data(TEMPERATURE, y));
                            temperatureChart.getData().add(series1);
                            if (HS_DEPLOYED == "P") {
                                hs_deployed_P_radioButton.setSelected(true);
                                hs_deployed_N_radioButton.setSelected(false);
                            } else {
                                hs_deployed_P_radioButton.setSelected(false);
                                hs_deployed_N_radioButton.setSelected(true);

                            }

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
    private void fileSelectButton(ActionEvent event) {
    }

}
