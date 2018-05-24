/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

/**
 *
 * @author MAZE2
 */
public class Node {

    private final String id;
    private int status;

    public Node(String id) {
        this.id = id;
        setAlive();
    }
    
    public void setAlive(){
        status = 3;
    }
    
    public void decrement(){
        --status;
    }

    /**
     * @return the name
     */
    public String getId() {
        return id;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

}
