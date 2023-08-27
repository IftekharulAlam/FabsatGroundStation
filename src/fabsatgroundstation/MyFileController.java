/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fabsatgroundstation;

import gnu.io.CommPortIdentifier;
import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
    XYChart.Series series_altitude;
    XYChart.Series series_temparature;
    XYChart.Series series_gps_altitude;
    XYChart.Series series_voltage;
    XYChart.Series series_tilt_x;
    XYChart.Series series_tilt_y;
    XYChart.Series series_pressure;

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
    private int y_data = 0;

    @FXML
    private ComboBox comPortComboBox;
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
    @FXML
    private RadioButton pc_deployed_C_radioButton;
    @FXML
    private RadioButton pc_deployed_N_radioButton;
    @FXML
    private RadioButton mast_raised_M_radioButton;
    @FXML
    private RadioButton mast_raised_N_radioButton;
    @FXML
    private Label packet_count_received;
    @FXML
    private Label teamID_label;
    @FXML
    private Text cmd_echo_label;
    @FXML
    private Label my;
    @FXML
    private Label gps_time_label;
    @FXML
    private Label gps_altitude_label;
    @FXML
    private Label no_of_sats_label;
    @FXML
    private Label gps_longitude_label;
    @FXML
    private Label gps_lattitude_label2;
    @FXML
    private ComboBox comboBox_command;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        comboBox_command.getItems().addAll(
                "CMD,1041,CX,ON",
                "CMD,1041,CX,OFF",
                "CMD,1041,ST,13:35:39",
                "CMD,1041,SIM,ENABLE",
                "CMD,1041,SIM,DISABLE",
                "CMD,1041,SIM,ACTIVATE",
                "CMD,1041,CAL,ON"
        );
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
//            series = new XYChart.Series();
//            series.getData().add(new XYChart.Data("2011", 15));
//            altitudechart.getData().add(series);
//            series1 = new XYChart.Series();
//            series1.getData().add(new XYChart.Data("2010", 12));
//            temperatureChart.getData().add(series1);
//            
            series_altitude = new XYChart.Series();
            series_altitude.getData().add(new XYChart.Data("2011", 15));
            altitudechart.getData().add(series_altitude);

            series_temparature = new XYChart.Series();
            series_temparature.getData().add(new XYChart.Data("2011", 15));
            temperatureChart.getData().add(series_temparature);

            series_gps_altitude = new XYChart.Series();
            series_gps_altitude.getData().add(new XYChart.Data("2011", 15));
            gPSaltitudechart.getData().add(series_gps_altitude);

            series_voltage = new XYChart.Series();
            series_voltage.getData().add(new XYChart.Data("2011", 15));
            voltageChart.getData().add(series_voltage);

            series_tilt_x = new XYChart.Series();
            series_tilt_x.getData().add(new XYChart.Data("2011", 15));
            titleXChart.getData().add(series_tilt_x);

            series_tilt_y = new XYChart.Series();
            series_tilt_y.getData().add(new XYChart.Data("2011", 15));
            titlYChart.getData().add(series_tilt_y);

            series_pressure = new XYChart.Series();
            series_pressure.getData().add(new XYChart.Data("2011", 15));
            pressureChart.getData().add(series_pressure);

            t = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(1000);
                            String s = sensorData.read();

                            if (s.length() > 10) {

                                String array[] = s.split(",");
                                y_data = Integer.parseInt(array[2]);

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
                                VOLTAGE = array[10];
                                PRESSURE = array[11];
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

                            try {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        //GUI STUFF
                                        teamID_label.setText(TEAM_ID);
                                        missionTimeRectangle.setText(MISSION_TIME);
                                        packet_count_received.setText(PACKET_COUNT);
                                        cmd_echo_label.setText(CMD_ECHO);
                                        gps_time_label.setText(GPS_TIME);
                                        gps_altitude_label.setText(GPS_ALTITUDE);
                                        no_of_sats_label.setText(GPS_SATS);
                                        gps_longitude_label.setText(GPS_LONGITUDE);
                                        gps_lattitude_label2.setText(GPS_LATITUDE);

                                    }
                                });

                                if (HS_DEPLOYED.matches("P")) {
                                    hs_deployed_P_radioButton.setSelected(true);
                                    hs_deployed_N_radioButton.setSelected(false);
                                } else {
                                    hs_deployed_P_radioButton.setSelected(false);
                                    hs_deployed_N_radioButton.setSelected(true);

                                }
                                if (PC_DEPLOYED.matches("C")) {
                                    System.out.println("Detected");
                                    pc_deployed_C_radioButton.setSelected(true);
                                    pc_deployed_N_radioButton.setSelected(false);
                                } else {
                                    pc_deployed_C_radioButton.setSelected(false);
                                    pc_deployed_N_radioButton.setSelected(true);

                                }
                                if (MAST_RAISED.matches("M")) {

                                    mast_raised_M_radioButton.setSelected(true);
                                    mast_raised_N_radioButton.setSelected(false);
                                } else {
                                    mast_raised_M_radioButton.setSelected(false);
                                    mast_raised_N_radioButton.setSelected(true);

                                }
                                if (MODE.matches("F")) {

                                    modeF.setSelected(true);
                                    modeS.setSelected(false);
                                } else {
                                    modeF.setSelected(false);
                                    modeS.setSelected(true);

                                }

                                series_altitude.getData().add(new XYChart.Data(ALTITUDE, y_data));
                                altitudechart.getData().add(series_altitude);

                                series_temparature.getData().add(new XYChart.Data(TEMPERATURE, y_data));
                                temperatureChart.getData().add(series_temparature);

                                series_gps_altitude.getData().add(new XYChart.Data(GPS_ALTITUDE, y_data));
                                gPSaltitudechart.getData().add(series_gps_altitude);

                                series_voltage.getData().add(new XYChart.Data(VOLTAGE, y_data));
                                voltageChart.getData().add(series_voltage);

                                series_tilt_x.getData().add(new XYChart.Data(TILT_X, y_data));
                                titleXChart.getData().add(series_tilt_x);

                                series_tilt_y.getData().add(new XYChart.Data(TILT_Y, y_data));
                                titlYChart.getData().add(series_tilt_y);

                                series_pressure.getData().add(new XYChart.Data(PRESSURE, y_data));
                                pressureChart.getData().add(series_pressure);

                            } catch (Exception e) {

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
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();

        File selectedFile = fileChooser.showOpenDialog(stage);

    }

    @FXML
    private void filesaveButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();

        File selectedFile = fileChooser.showSaveDialog(stage);
    }

}
