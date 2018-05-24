package Stepping.implementations;

import java.util.Observer;

/**
 *
 * @author MAZE2
 */
public class SoftStepper extends StepperImpl{
    public static SoftStepper newStepper(Observer observer) {
        SoftStepper stepper = new SoftStepper();
        stepper.addObserver(observer);
        stepper.refresh();
        return stepper;
    }
    public void terminer() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
        setDirectAdvancementMessage(" Fini , remise à zéro");
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1000; i >= 0; i--) {
                    try {
                        Thread.sleep(1);
                        updateAdvancement(i*0.001);
                    } catch (InterruptedException ex) {
                    }
                }
                setDirectAdvancementMessage("");
            }
        }).run();
    }
}
