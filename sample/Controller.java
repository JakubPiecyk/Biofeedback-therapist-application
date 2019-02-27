package sample;


        import javafx.application.Platform;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;

        import javafx.scene.chart.*;
        import javafx.scene.control.*;



        import javafx.scene.paint.Color;
        import javafx.scene.shape.Circle;

        import java.util.concurrent.atomic.AtomicInteger;

public class Controller {



    private AtomicInteger tick = new AtomicInteger(0);
    private AtomicInteger tick1 = new AtomicInteger(0);
    private AtomicInteger tick3 = new AtomicInteger(0);




    @FXML
    private Slider ThresholdSlider;

    @FXML
    private  BarChart<String, Number> MindBar;

    @FXML
    private LineChart<Number, Number> XYEssence;

    @FXML
    private LineChart<Number, Number> ChoiceChart;

    @FXML

    private LineChart<Number,Number> XYwaves;

    @FXML
    private AreaChart<Number, Number> PulseRate;

    @FXML
    private Label ThresholdLabel;

    @FXML

    private  Label PulseLabel;

    @FXML

    private Button ConnectButton;

    @FXML

    private Button DisconnectButton;

    @FXML

    private Button DisconnectButtonTGC;

    @FXML

    private Button ConnectButtonTGC;

    @FXML

    private Button RobotConnectButton;

    @FXML

    private Button RobotDisconnectButton;

    @FXML

    private Label mindwavestatus;


    @FXML

    private Label StatusLabel;

    @FXML

    private Circle signalcircle1;

    @FXML

    private Circle signalcircle2;

    @FXML

    private Circle signalcircle3;

    @FXML

    private Circle RobotStatusCircle;

    @FXML

    private CheckBox CheckDelta;

    @FXML

    private CheckBox CheckTheta;

    @FXML

    private CheckBox CheckLAlpha;

    @FXML

    private CheckBox CheckHAlpha;

    @FXML

    private CheckBox CheckLBeta;

    @FXML

    private CheckBox CheckHBeta;

    @FXML

    private CheckBox CheckLGamma;

    @FXML

    private CheckBox CheckMGamma;

    @FXML

    private  Button ExitButton;

    private boolean StopRobot = false;
    private boolean RobotMove = false;

    private String RaspCommand;
    private String RobotCommand;

    RaspConnect raspConnect = new RaspConnect();
    RaspConnect robotConnect = new RaspConnect();

    MindWave RawData = new MindWave();

/*
OnButtonClick method checks which button was clicked and than it runs right assigned function to clicked button
 */
    public void OnButtonClick(ActionEvent e){


        if (e.getSource().equals(ConnectButton)){
            if(!raspConnect.isrunning()) {

                RaspCommand = "max";
                raspConnect.start();



                if (raspConnect.isrunning()) {

                    StatusLabel.setText("Connected");
                    StatusLabel.setTextFill(Color.GREEN);
                    ConnectButton.setDisable(true);
                    DisconnectButton.setDisable(false);
                }
            }

        }

        else if (e.getSource().equals(DisconnectButton)) {

            if (raspConnect.isrunning()) {

                RaspCommand = "q";
                StatusLabel.setText("Disconnected");
                StatusLabel.setTextFill(Color.RED);
                ConnectButton.setDisable(false);
                DisconnectButton.setDisable(true);


            }
        }

        else if (e.getSource().equals(DisconnectButtonTGC)) {

            RawData.stop();
            if(!RawData.isRunning()){

                mindwavestatus.setText("Disconnected");
                mindwavestatus.setTextFill(Color.RED);
                ConnectButtonTGC.setDisable(false);
                DisconnectButtonTGC.setDisable(true);
                RawData.setSetupDone(false);
            }

        }

        else if (e.getSource().equals(ConnectButtonTGC)) {

            RawData.setup();
            if(RawData.isRunning()){

                mindwavestatus.setText("Connected");
                mindwavestatus.setTextFill(Color.GREEN);
                ConnectButtonTGC.setDisable(true);
                DisconnectButtonTGC.setDisable(false);
            }

        }

        else if (e.getSource().equals((RobotConnectButton))){

            robotConnect.start();
            if(robotConnect.isrunning()){

                RobotConnectButton.setDisable(true);
                RobotDisconnectButton.setDisable(false);
                RobotStatusCircle.setFill(Color.GREEN);
                StopRobot=false;
            }

        }

        else if (e.getSource().equals((RobotDisconnectButton))) {

            RobotCommand = "q";
            if (robotConnect.isrunning()) {

                RobotDisconnectButton.setDisable(true);
                RobotConnectButton.setDisable(false);
                RobotStatusCircle.setFill(Color.RED);
                StopRobot=true;
            }

        }

        else if (e.getSource().equals((ExitButton))) {

            if(raspConnect.isrunning()){
                raspConnect.run("q");
            }

            if (robotConnect.isrunning()) {
                robotConnect.run("q");
            }
            System.exit(0);


        }





    }


    public void initialize() {





        DisconnectButtonTGC.setDisable(true);
        DisconnectButton.setDisable(true);
        RobotDisconnectButton.setDisable(true);




/*
This thread is responsible for Signal Visualisation on the interface.
It checks poorsignal value and assign circe amount and colors for that value. For example 3 green circles are indicates best signal value
 */


        Thread signalThread = new Thread(()  -> {

            while(true){

                try {
                    Thread.sleep(1000);



                    signalcircle1.setStroke(Color.WHITESMOKE);
                    signalcircle2.setStroke(Color.WHITESMOKE);
                    signalcircle3.setStroke(Color.WHITESMOKE);

                    if (RawData.getSignal() == 0) {

                        signalcircle1.setStroke(Color.WHITESMOKE);
                        signalcircle2.setStroke(Color.WHITESMOKE);
                        signalcircle3.setStroke(Color.WHITESMOKE);

                        signalcircle1.setFill(Color.GREEN);
                        signalcircle2.setFill(Color.GREEN);
                        signalcircle3.setFill(Color.GREEN);

                    } else if (RawData.getSignal() > 0 && RawData.getSignal() < 25) {

                        signalcircle1.setStroke(Color.WHITESMOKE);
                        signalcircle2.setStroke(Color.WHITESMOKE);
                        signalcircle3.setStroke(Color.WHITESMOKE);

                        signalcircle1.setFill(Color.GREEN);
                        signalcircle2.setFill(Color.GREEN);
                        signalcircle3.setFill(Color.WHITESMOKE);
                    } else if (RawData.getSignal() > 25 && RawData.getSignal() < 55) {

                        signalcircle1.setStroke(Color.WHITESMOKE);
                        signalcircle2.setStroke(Color.WHITESMOKE);
                        signalcircle3.setStroke(Color.WHITESMOKE);

                        signalcircle1.setFill(Color.ORANGE);
                        signalcircle2.setFill(Color.ORANGE);
                        signalcircle3.setFill(Color.WHITESMOKE);

                    } else if (RawData.getSignal() == 200) {

                        signalcircle1.setStroke(Color.RED);
                        signalcircle2.setStroke(Color.RED);
                        signalcircle3.setStroke(Color.RED);

                        signalcircle1.setFill(Color.WHITESMOKE);
                        signalcircle2.setFill(Color.WHITESMOKE);
                        signalcircle3.setFill(Color.WHITESMOKE);

                    } else if (RawData.getSignal() > 55 && RawData.getSignal() < 255 && RawData.getSignal()!=200) {


                        signalcircle1.setStroke(Color.WHITESMOKE);
                        signalcircle2.setStroke(Color.WHITESMOKE);
                        signalcircle3.setStroke(Color.WHITESMOKE);

                        signalcircle1.setFill(Color.RED);
                        signalcircle2.setFill(Color.RED);
                        signalcircle3.setFill(Color.RED);

                    }

                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }



        });
        signalThread.setDaemon(true);
        signalThread.start();
/*
This is initialisation of data series lists for live charts
 */

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Delta");
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Theta");
        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        series3.setName("Low Alpha");
        XYChart.Series<String, Number> series4 = new XYChart.Series<>();
        series4.setName("High Alpha");
        XYChart.Series<String, Number> series5 = new XYChart.Series<>();
        series5.setName("Low Beta");
        XYChart.Series<String, Number> series6 = new XYChart.Series<>();
        series6.setName("High Beta");
        XYChart.Series<String, Number> series7 = new XYChart.Series<>();
        series7.setName("Low Gamma");
        XYChart.Series<String, Number> series8 = new XYChart.Series<>();
        series8.setName("Mid Gamma");


        XYChart.Series<Number, Number> attention = new XYChart.Series<>();
        attention.setName("Attention");
        XYChart.Series<Number, Number> meditation= new XYChart.Series<>();
        meditation.setName("Meditation");
        XYChart.Series<Number, Number> thresh= new XYChart.Series<>();
        thresh.setName("Threshold");

        XYChart.Series<Number, Number> series1a = new XYChart.Series<>();
        series1a.setName("Delta");
        XYChart.Series<Number, Number> series2a = new XYChart.Series<>();
        series2a.setName("Theta");
        XYChart.Series<Number, Number> series3a = new XYChart.Series<>();
        series3a.setName("Low Alpha");
        XYChart.Series<Number, Number> series4a = new XYChart.Series<>();
        series4a.setName("High Alpha");
        XYChart.Series<Number, Number> series5a = new XYChart.Series<>();
        series5a.setName("Low Beta");
        XYChart.Series<Number, Number> series6a = new XYChart.Series<>();
        series6a.setName("High Beta");
        XYChart.Series<Number, Number> series7a = new XYChart.Series<>();
        series7a.setName("Low Gamma");
        XYChart.Series<Number, Number> series8a = new XYChart.Series<>();
        series8a.setName("Mid Gamma");


        XYChart.Series<Number, Number> series1b = new XYChart.Series<>();
        series1b.setName("Delta");
        XYChart.Series<Number, Number> series2b = new XYChart.Series<>();
        series2b.setName("Theta");
        XYChart.Series<Number, Number> series3b = new XYChart.Series<>();
        series3b.setName("Low Alpha");
        XYChart.Series<Number, Number> series4b = new XYChart.Series<>();
        series4b.setName("High Alpha");
        XYChart.Series<Number, Number> series5b = new XYChart.Series<>();
        series5b.setName("Low Beta");
        XYChart.Series<Number, Number> series6b = new XYChart.Series<>();
        series6b.setName("High Beta");
        XYChart.Series<Number, Number> series7b = new XYChart.Series<>();
        series7b.setName("Low Gamma");
        XYChart.Series<Number, Number> series8b = new XYChart.Series<>();
        series8b.setName("Mid Gamma");

        XYChart.Series<Number, Number> pulseseries = new XYChart.Series<>();
        pulseseries.setName("Pulse");



        MindBar.getData().add(series1);
        MindBar.getData().add(series2);
        MindBar.getData().add(series3);
        MindBar.getData().add(series4);
        MindBar.getData().add(series5);
        MindBar.getData().add(series6);
        MindBar.getData().add(series7);
        MindBar.getData().add(series8);

        XYEssence.getData().add(attention);
        XYEssence.getData().add(meditation);
        XYEssence.getData().add(thresh);

        XYwaves.getData().add(series1a);
        XYwaves.getData().add(series2a);
        XYwaves.getData().add(series3a);
        XYwaves.getData().add(series4a);
        XYwaves.getData().add(series5a);
        XYwaves.getData().add(series6a);
        XYwaves.getData().add(series7a);
        XYwaves.getData().add(series8a);

        ChoiceChart.getData().add(series1b);
        ChoiceChart.getData().add(series2b);
        ChoiceChart.getData().add(series3b);
        ChoiceChart.getData().add(series4b);
        ChoiceChart.getData().add(series5b);
        ChoiceChart.getData().add(series6b);
        ChoiceChart.getData().add(series7b);
        ChoiceChart.getData().add(series8b);

        PulseRate.getData().add(pulseseries);

/*
This thread is responsible for getting data points values and pass it to the charts.
To make data more comparable on charts i've used log10 values of EEG waves
In this thread there is also a function that checks if attention value is passing the Threshold value and if it is, the command to move robot is send to raspberry PI
 */
        Thread updateThread = new Thread(()  -> {

            while (true) {

                try {
                    Thread.sleep(1000);



// DATA FOR BAR CHART

                    if(RawData.getSetupDone()) {


                        Platform.runLater(() -> series1.getData().add(new XYChart.Data<>(" ", Math.log10(RawData.getDelta_wave()))));
                        Platform.runLater(() -> series2.getData().add(new XYChart.Data<>(" ", Math.log10(RawData.getTheta_wave()))));
                        Platform.runLater(() -> series3.getData().add(new XYChart.Data<>(" ", Math.log10(RawData.getLow_alpha_wave()))));
                        Platform.runLater(() -> series4.getData().add(new XYChart.Data<>(" ", Math.log10(RawData.getHigh_alpha_wave()))));
                        Platform.runLater(() -> series5.getData().add(new XYChart.Data<>(" ", Math.log10(RawData.getLow_beta_wave()))));
                        Platform.runLater(() -> series6.getData().add(new XYChart.Data<>(" ", Math.log10(RawData.getHigh_beta_wave()))));
                        Platform.runLater(() -> series7.getData().add(new XYChart.Data<>(" ", Math.log10(RawData.getLow_gamma_wave()))));
                        Platform.runLater(() -> series8.getData().add(new XYChart.Data<>(" ", Math.log10(RawData.getMid_gamma_wave()))));

// DATA FOR ESSENCE CHART

                        Platform.runLater(() -> thresh.getData().add(new XYChart.Data<>(tick.get(), ThresholdSlider.getValue())));
                        Platform.runLater(() -> attention.getData().add(new XYChart.Data<>(tick.get(), RawData.getAttention())));
                        Platform.runLater(() -> meditation.getData().add(new XYChart.Data<>(tick.get(), RawData.getMeditation())));


                        if (attention.getData().size() > 20) attention.getData().remove(0);
                        if (meditation.getData().size() > 20) meditation.getData().remove(0);
                        if (thresh.getData().size() > 20) thresh.getData().remove(0);


// DATA FOR CHOICE CHART


                        if (CheckDelta.isSelected()) {
                            Platform.runLater(() -> series1b.getData().add(new XYChart.Data<>(tick1.get(), Math.log10(RawData.getDelta_wave()))));

                        } else if (series1b.getData().size() > 0) series1b.getData().remove(0);


                        if (CheckTheta.isSelected()) {
                            Platform.runLater(() -> series2b.getData().add(new XYChart.Data<>(tick1.get(), Math.log10(RawData.getTheta_wave()))));

                        } else if (series2b.getData().size() > 0) series2b.getData().remove(0);

                        if (CheckHAlpha.isSelected()) {
                            Platform.runLater(() -> series3b.getData().add(new XYChart.Data<>(tick1.get(), Math.log10(RawData.getLow_alpha_wave()))));

                        } else if (series3b.getData().size() > 0) series3b.getData().remove(0);

                        if (CheckLAlpha.isSelected()) {
                            Platform.runLater(() -> series4b.getData().add(new XYChart.Data<>(tick1.get(), Math.log10(RawData.getHigh_alpha_wave()))));

                        } else if (series4b.getData().size() > 0) series4b.getData().remove(0);

                        if (CheckLBeta.isSelected()) {
                            Platform.runLater(() -> series5b.getData().add(new XYChart.Data<>(tick1.get(), Math.log10(RawData.getLow_beta_wave()))));

                        } else if (series5b.getData().size() > 0) series5b.getData().remove(0);

                        if (CheckHBeta.isSelected()) {
                            Platform.runLater(() -> series6b.getData().add(new XYChart.Data<>(tick1.get(), Math.log10(RawData.getHigh_beta_wave()))));

                        } else if (series6b.getData().size() > 0) series6b.getData().remove(0);

                        if (CheckLGamma.isSelected()) {
                            Platform.runLater(() -> series7b.getData().add(new XYChart.Data<>(tick1.get(), Math.log10(RawData.getLow_gamma_wave()))));

                        } else if (series7b.getData().size() > 0) series7b.getData().remove(0);

                        if (CheckMGamma.isSelected()) {
                            Platform.runLater(() -> series8b.getData().add(new XYChart.Data<>(tick1.get(), Math.log10(RawData.getMid_gamma_wave()))));

                        } else if (series8b.getData().size() > 0) series8b.getData().remove(0);


                        if (series1b.getData().size() > 10) series1b.getData().remove(0);
                        if (series2b.getData().size() > 10) series2b.getData().remove(0);
                        if (series3b.getData().size() > 10) series3b.getData().remove(0);
                        if (series4b.getData().size() > 10) series4b.getData().remove(0);
                        if (series5b.getData().size() > 10) series5b.getData().remove(0);
                        if (series6b.getData().size() > 10) series6b.getData().remove(0);
                        if (series7b.getData().size() > 10) series7b.getData().remove(0);
                        if (series8b.getData().size() > 10) series8b.getData().remove(0);

                        tick1.incrementAndGet();

//DATA FOR XYWAVES CHART

                        Platform.runLater(() -> series1a.getData().add(new XYChart.Data<>(tick.get(), Math.log10(RawData.getDelta_wave()))));
                        Platform.runLater(() -> series2a.getData().add(new XYChart.Data<>(tick.get(), Math.log10(RawData.getTheta_wave()))));
                        Platform.runLater(() -> series3a.getData().add(new XYChart.Data<>(tick.get(), Math.log10(RawData.getLow_alpha_wave()))));
                        Platform.runLater(() -> series4a.getData().add(new XYChart.Data<>(tick.get(), Math.log10(RawData.getHigh_alpha_wave()))));
                        Platform.runLater(() -> series5a.getData().add(new XYChart.Data<>(tick.get(), Math.log10(RawData.getLow_beta_wave()))));
                        Platform.runLater(() -> series6a.getData().add(new XYChart.Data<>(tick.get(), Math.log10(RawData.getHigh_beta_wave()))));
                        Platform.runLater(() -> series7a.getData().add(new XYChart.Data<>(tick.get(), Math.log10(RawData.getLow_gamma_wave()))));
                        Platform.runLater(() -> series8a.getData().add(new XYChart.Data<>(tick.get(), Math.log10(RawData.getMid_gamma_wave()))));


                        if (series1a.getData().size() > 15) series1a.getData().remove(0);
                        if (series2a.getData().size() > 15) series2a.getData().remove(0);
                        if (series3a.getData().size() > 15) series3a.getData().remove(0);
                        if (series4a.getData().size() > 15) series4a.getData().remove(0);
                        if (series5a.getData().size() > 15) series5a.getData().remove(0);
                        if (series6a.getData().size() > 15) series6a.getData().remove(0);
                        if (series7a.getData().size() > 15) series7a.getData().remove(0);
                        if (series8a.getData().size() > 15) series8a.getData().remove(0);

                        tick.incrementAndGet();




//AREA CHART




                    }
                    if(raspConnect.isrunning()) {
                        Platform.runLater(() -> pulseseries.getData().add(new XYChart.Data<>(tick3.get(), raspConnect.getHR())));
                        tick3.incrementAndGet();
                        if (pulseseries.getData().size() > 15) pulseseries.getData().remove(0);



                    }


                    RobotMove = RawData.getAttention() > ThresholdSlider.getValue();


// Robot Commands

                    if (RobotMove  && RawData.getAttention()>=0 && RawData.getAttention()<=50) {
                        RobotCommand = "w";
                    }
                    else if (RobotMove && RawData.getAttention()>=50 && RawData.getAttention()<=75) {
                        RobotCommand = "s";
                    }

                    else if (RobotMove  && RawData.getAttention()>=75 && RawData.getAttention()<100) {
                        RobotCommand = "a";
                    }

                    else if (RobotMove  && RawData.getAttention()==100) {
                        RobotCommand = "d";
                    }

                    else if(!RobotMove ) {
                        RobotCommand = "Stop";
                    }





                    if (robotConnect.isrunning()) {
                        robotConnect.run(RobotCommand);
                    }

                    if (raspConnect.isrunning()) {
                        raspConnect.run(RaspCommand);
                        System.out.println(raspConnect.getHR());
                        Platform.runLater(() -> PulseLabel.setText(raspConnect.getHRate()));
                    }




//Threshold Label value update

                    Platform.runLater(() -> ThresholdLabel.setText("  " + ThresholdSlider.getValue()));

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        updateThread.setDaemon(true);
        updateThread.start();





    }

}
