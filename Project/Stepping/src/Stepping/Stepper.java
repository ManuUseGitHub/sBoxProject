/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stepping;

/**
 *
 * @author MAZE2
 */
public interface Stepper {

    double getAdvancement();

    String getAdvancementMEssage();

    /**
     * Méthode d'affichage stendard qui permet de mettre à jour le message à afficher avec une pause de <b>'100'</b>
     * millisecondes d'attente pour affihcer le message suivant. En d'autres termes, Les messages survenant entre le début d'attente et les <b>'100'<b/>
     * ne seront pas commit aux observeurs chargés de modifier l'affichage des messages de la progression.
     * @param message le message a changer
     */
    void setAdvancementMessage(String message);

    /**
     * Permet de mettre à jour le message
     * à afficher directement et avec une pause de <b>'0'(zero)</b>
     * millisecondes d'attente pour affihcer le message suivant
     * @param message le message a changer
     */
    void setDirectAdvancementMessage(String message);

    /**
     * Met à jour le maximum. <b color="red">IMPORTANT</b>, doit être appeler
     * en premier lieux avant de vouloir afficher la progression pour afficher
     * la progression réelle.
     * @param maximum
     */
    void setMaximum(int maximum);

    /**
     * Méthode d'affichage qui permet de mettre à jour le message à afficher. Une pause de <b>'500'</b>
     * millisecondes d'attente sont nécessaires pour affihcer le message suivant. En d'autres termes, Les messages survenant entre le début d'attente et les <b>'500'<b/>
     * ne seront pas commit aux observeurs chargés de modifier l'affichage des messages de la progression.
     * @param string
     * @param message le message a changer
     */
    void setQueuedAdvMessage(String string);

    /**
     * Mets à jour le message de progression si le temps d'attente d'affichage des nouveaux messages est atteint.
     * Ensuite, les suivants messages devront attendre un temps défini en millisecondes pour être affichés à leur tour.
     * On pourrait pensé qu'ils sont mis en attente dans une file mais non, les nouveaux messages survenants
     * entre le début d'attente en milliseconde et la fin de ce temps ne seront pas traités.
     * @param string le message à afficher.
     * @param millis Le temps à attendre pour afficher les messages suivants
     */
    void setQueuedAdvMessageTimed(String string, long millis);

    /**
     * Permet de mettre à jour le message à afficher avec une pause de <b>'0'(zero)</b>
     * millisecondes d'attente pour affihcer le message suivant.
     * @param message le message a changer
     */
    void setQuickAdvancementMessage(String message);

    /**
     * Cette méthode mets à jour le niveau de progression grâce à un nombre d'étapes à atteindre sur un maximum
     * @param actualStep
     */
    void updateAdvancement(int actualStep);

    /**
     * cette méthode met à jour le niveau de progression grâce à un coéficient variant de 0.0 à 1.0 idéalement
     * @param pourcentage le coéficient
     */
    void updateAdvancement(double pourcentage);
    
    /**
     * Cette méthode mets à jour le niveau de progression grâce à un nombre d'étapes à atteindre sur un maximum sans notifications
     * @param actualStep le nombre d'étape faites
     */
    void setAdvancement(int actualStep);
    
    /**
     * cette méthode met à jour le niveau de progression grâce à un coéficient variant de 0.0 à 1.0 idéalement et sans notifications
     * @param pourcentage le coéficient
     */
    void setAdvancement(double pourcentage);
    
}
