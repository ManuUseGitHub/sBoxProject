/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient;

import java.util.Observable;
import java.util.Observer;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 *
 * @author MAZE2
 */
public abstract class FeedBackMessenger implements Observer {

    protected final int CODE_INDEX = 0;
    protected final int MESSAGE_INDEX = 1;
    private boolean showing = false;

    protected void setBadOrGoodFeedback(Label lbl, String analized, String message) {
        if (analized.matches("^BAD.*")) {
            setBadFeedback(lbl, message);
        } else if (analized.matches("^GOOD.*")) {
            if (!analized.equals("GOOD_QUIT")) {
                setGoodFeedback(lbl, message);
            }
        }
    }

    private void setGoodFeedback(Label lbl, String s) {
        setFeedBack(lbl, s, Color.web("#555"));
    }

    protected void setEmptyFeedback(Label lbl) {
        setGoodFeedback(lbl, "");
    }

    protected void setBadFeedback(Label lbl, String s) {
        setFeedBack(lbl, s, Color.DARKRED);
    }

    protected String[] getParsedWithCodeOrDefault(String message) {
        String[] args;
        if ((args = MessageTaskParser.parseERR_WITH_CODE(message)) == null && (args = MessageTaskParser.parseOK_WITH_CODE(message)) == null) {
            args = new String[]{"-1", "..."};
        }
        return args;
    }

    protected void setFeedBack(Label lbl, String s, Color color) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lbl.setText(s);
                lbl.setTextFill(color);
            }
        });
    }

    @Override
    public abstract void update(Observable o, Object o1);

    /**
     * @return the showing
     */
    public boolean isShowing() {
        return showing;
    }

    /**
     * @param showing the showing to set
     */
    public void setShowing(boolean showing) {
        this.showing = showing;
    }
}
