/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stepping.view;

import Stepping.Stepper;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import Stepping.implementations.StepperImpl;

/**
 *
 * @author MAZE2
 */
public class StepObserver implements Observer {

    private final ProgressBar progress;
    private final Label percentage;
    private final Label stepText;

    public StepObserver(ProgressBar progress, Label progressPourcentage, Label progressText) {
        this.progress = progress;
        this.percentage = progressPourcentage;
        this.stepText = progressText;
    }

    @Override
    /**
     * permet de faire transparaitre l'avancement grâce aux composantes graphiques désignées
     */
    public void update(Observable o, Object arg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (o instanceof StepperImpl) {
                    progress.setProgress(((Stepper) o).getAdvancement());
                    percentage.setText(String.format("%.00f %%", (100 * ((Stepper) o).getAdvancement())));
                    stepText.setText(((Stepper) o).getAdvancementMEssage());
                }
            }
        });
    }
}
