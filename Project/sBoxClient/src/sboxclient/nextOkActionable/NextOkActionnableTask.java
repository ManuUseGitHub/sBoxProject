/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.nextOkActionable;

import sboxclient.Task;

/**
 *
 * @author MAZE2
 */
public abstract class NextOkActionnableTask extends Task{
    private boolean nextOkCloseable;
    /**
     * @return the nextOkCloseable
     */
    public boolean isNextOkActionnable() {
        return nextOkCloseable;
    }

    /**
     * @param nextOkCloseable the nextOkCloseable to set
     */
    public void setNextOkCloseable(boolean nextOkCloseable) {
        this.nextOkCloseable = nextOkCloseable;
    }
}
