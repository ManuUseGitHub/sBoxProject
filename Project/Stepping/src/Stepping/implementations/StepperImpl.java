package Stepping.implementations;

import Stepping.Stepper;
import java.util.Observable;
import java.util.Observer;

/**
 * Implémentation de l'interface stepper qui permet de garder l'état de l'avancement 
 * des étapes d'une opération relativement lourde en temps d'exécution. Des méthodes 
 * permettent de réguler le flux d'apparition des messages mis à jour.
 * @author MAZE2
 */
public class StepperImpl extends Observable implements Stepper {

    public static StepperImpl newStepper(Observer observer) {
        StepperImpl stepper = new StepperImpl();
        stepper.addObserver(observer);
        stepper.refresh();
        return stepper;
    }

    private int actualStep = 0;
    private int maximum;
    private double advancement;
    private String advancementMEssage;
    private boolean queuingMessage;

    public StepperImpl() {
        maximum = 0;
        advancementMEssage = "";
    }

    public StepperImpl(int maximum) {
        this();
        actualStep = 0;
        this.maximum = maximum;
    }

    /**
     * Permet de mettre à jour le message à afficher avec une pause de <b>'0'(zero)</b> 
     * millisecondes d'attente pour affihcer le message suivant.
     * @param message le message a changer
     */
    @Override
    public void setQuickAdvancementMessage(String message) {
        setQueuedAdvMessageTimed(message, 0);
    }

    /**
     * Permet de mettre à jour le message 
     * à afficher directement et avec une pause de <b>'0'(zero)</b> 
     * millisecondes d'attente pour affihcer le message suivant
     * @param message le message a changer
     */
    @Override
    public void setDirectAdvancementMessage(String message) {
        queuingMessage = false;
        setQueuedAdvMessageTimed(message, 0);
    }

    /**
     * Méthode d'affichage stendard qui permet de mettre à jour le message à afficher avec une pause de <b>'100'</b> 
     * millisecondes d'attente pour affihcer le message suivant. En d'autres termes, Les messages survenant entre le début d'attente et les <b>'100'<b/> 
     * ne seront pas commit aux observeurs chargés de modifier l'affichage des messages de la progression.
     * @param message le message a changer
     */
    @Override
    public void setAdvancementMessage(String message) {
        setQueuedAdvMessageTimed(message, 100);
    }

    /**
     * Méthode d'affichage qui permet de mettre à jour le message à afficher. Une pause de <b>'500'</b> 
     * millisecondes d'attente sont nécessaires pour affihcer le message suivant. En d'autres termes, Les messages survenant entre le début d'attente et les <b>'500'<b/> 
     * ne seront pas commit aux observeurs chargés de modifier l'affichage des messages de la progression.
     * @param string
     * @param message le message a changer
     */
    @Override
    public void setQueuedAdvMessage(String string) {
        setQueuedAdvMessageTimed(string, 500);
    }

    /**
     * Mets à jour le message de progression si le temps d'attente d'affichage des nouveaux messages est atteint.
     * Ensuite, les suivants messages devront attendre un temps défini en millisecondes pour être affichés à leur tour.
     * On pourrait pensé qu'ils sont mis en attente dans une file mais non, les nouveaux messages survenants 
     * entre le début d'attente en milliseconde et la fin de ce temps ne seront pas traités.
     * @param string le message à afficher.
     * @param millis Le temps à attendre pour afficher les messages suivants
     */
    @Override
    public void setQueuedAdvMessageTimed(String string, long millis) {
        if (!queuingMessage) {
            queuingMessage = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        advancementMEssage = string;
                        ConsoleClearer.getINSTANCE().clearLastLine();
                        refresh();
                        Thread.sleep(millis);
                        queuingMessage = false;
                    } catch (InterruptedException ex) {
                    }
                }
            }).start();
        }
    }

    /**
     * Initie la notification des observers
     */
    protected void refresh() {
        setChanged();
        notifyObservers();
    }

    /**
     * Cette méthode mets à jour le niveau de progression grâce à un nombre d'étapes à atteindre sur un maximum sans notifications
     * @param actualStep 
     */
    @Override
    public void setAdvancement(int actualStep) {
        this.actualStep = this.actualStep <= actualStep ? this.actualStep : actualStep;
        advancement = actualStep * 1.0 / maximum;
    }
    
    /**
     * cette méthode met à jour le niveau de progression grâce à un coéficient variant de 0.0 à 1.0 idéalement et sans notifications
     * @param pourcentage le coéficient
     */
    @Override
    public void setAdvancement(double pourcentage) {
        advancement = pourcentage;
    }
    
    /**
     * Cette méthode mets à jour le niveau de progression grâce à un nombre d'étapes à atteindre sur un maximum
     * @param actualStep 
     */
    @Override
    public void updateAdvancement(int actualStep) {
        this.actualStep = this.actualStep <= actualStep ? this.actualStep : actualStep;
        advancement = actualStep * 1.0 / maximum;
        refresh();
    }
    
    /**
     * cette méthode met à jour le niveau de progression grâce à un coéficient variant de 0.0 à 1.0 idéalement
     * @param pourcentage le coéficient
     */
    @Override
    public void updateAdvancement(double pourcentage) {
        advancement = pourcentage;
        refresh();
    }

    @Override
    public double getAdvancement() {
        return advancement;
    }

    @Override
    public String getAdvancementMEssage() {
        return advancementMEssage;
    }

    /**
     * Met à jour le maximum. <b color="red">IMPORTANT</b>, doit être appeler 
     * en premier lieux avant de vouloir afficher la progression pour afficher 
     * la progression réelle. 
     * @param maximum 
     */
    @Override
    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

}
