/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stepping.implementations;

import Stepping.Stepper;
import java.util.Observable;
import java.util.Observer;

/**
 * Utiliser ce stepper pour une utilisation console uniquement
 *
 * @author MAZE2
 */
public class VerboseStepper extends StepperImpl {

    public VerboseStepper() {
        addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if (o instanceof StepperImpl) {
                    System.out.println(String.format("[%.00f %%] %s", (100 * ((Stepper) o).getAdvancement()), ((Stepper) o).getAdvancementMEssage()));
                }
            }
        });
    }

    public void steppingWhileProcessing(int step, String message) {
        setAdvancement(step);
        setQueuedAdvMessageTimed(message, 5000);
    }

    public void steppingDirectly(int step, String message) {
        setAdvancement(step);
        setDirectAdvancementMessage(message);
    }
}
