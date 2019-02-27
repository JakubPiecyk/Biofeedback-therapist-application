package sample;

import neurosky.ThinkGearSocket;
import processing.core.PApplet;

/*

I've made this class to lose dependency on PApplet

Here u can easily access methods to get each wave value separately
Before you try to get any value from headset, you have to open connection with headset by using method setup()
All wave values have default value of zero to avoid null pointer exception in live charts

 */
public class MindWave extends PApplet {

    private int meditation;
    private int attention;
    private double delta_wave = 0;
    private int theta_wave = 0;
    private int low_alpha_wave = 0;
    private int high_alpha_wave = 0;
    private int low_beta_wave = 0;
    private int high_beta_wave = 0;
    private int low_gamma_wave = 0;
    private int mid_gamma_wave = 0;
    private int blinkSt;
    private int signal = 200;
    private boolean SetupDone = false;

    ThinkGearSocket neuroSocket = new ThinkGearSocket(this);

    public void setup() {

            try {
                neuroSocket.start();
                this.SetupDone = true;
            } catch (Exception e) {
                this.SetupDone = false;
            }
    }

    public void eegEvent(int delta, int theta, int low_alpha, int high_alpha, int low_beta, int high_beta, int low_gamma, int mid_gamma) {


            this.delta_wave = delta;
            this.theta_wave = theta;
            this.low_alpha_wave = low_alpha;
            this.high_alpha_wave = high_alpha;
            this.low_beta_wave=1;
            this.low_beta_wave = low_beta;
            this.high_beta_wave = high_beta;
            this.low_gamma_wave=low_gamma;
            this.mid_gamma_wave = mid_gamma;


    }

    public void blinkEvent(int blinkStrength)
    {
        this.blinkSt = blinkStrength;
        // blink = 1;
    }

    public void attentionEvent(int attentionLevel)
    {
        this.attention = attentionLevel;
    }

    public void meditationEvent(int meditationLevel)
    {
        this.meditation = meditationLevel;
    }

    public void poorSignalEvent(int sig) {
        this.signal = sig;
        System.out.println(sig);
    }

    public void rawEvent(int[] raw) {
        //println("rawEvent Level: " + raw);
    }

    public void stop(){
        neuroSocket.stop();
    }







    // GETTERS

    public int getMeditation() {
        return meditation;
    }

    public int getAttention() {
        return attention;
    }



    public double getDelta_wave() {
        return delta_wave;
    }

    public int getTheta_wave() {
        return theta_wave;
    }

    public int getLow_alpha_wave() {

        return low_alpha_wave;
    }
    public int getHigh_alpha_wave() {
        return high_alpha_wave;
    }

    public int getLow_beta_wave() {
        return low_beta_wave;
    }

    public int getHigh_beta_wave() {
        return high_beta_wave;
    }

    public int getLow_gamma_wave() {
        return low_gamma_wave;
    }

    public int getMid_gamma_wave() {
        return mid_gamma_wave;
    }

    public int getBlinkSt() {
        return blinkSt;
    }

    public int getSignal() {


        return signal;
    }

    public boolean getSetupDone() {
        return SetupDone;
    }

    public boolean isRunning() {
        return neuroSocket.isRunning();
    }

    public void setSetupDone(boolean setupDone) {
        SetupDone = setupDone;
    }
}
