/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.ALIVE;

import node.Node;
import Server.notimpl.base.MCController;
import node.MultiCastSender;
import util.Parser;
import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author MAZE2
 */
public class AliveController extends MCController {

    private final Parser parser;
    private final CopyOnWriteArrayList<Node> living;

    public AliveController(MultiCastSender sender) {

        super(new AliveTaskAnalyser(), sender);

        parser = new Parser();
        living = new CopyOnWriteArrayList<>(new ArrayList<Node>());

        new Thread(lessAliveForAll()).start();
    }

    @Override
    public void update(Observable o, Object arg) {
        super.update(o, arg);
        if (getAnalyseResult().equals("VIVANT")) {
            setNodeAlive(getMessage());
        }
    }

    private void setNodeAlive(String msg) {
        synchronized (living) {
            boolean isNew = true;
            for (Node hi : living) {
                //System.err.println(String.format("id: %s, status: %d", hi.getId(), hi.getStatus()));
                if (hi.getId().equals(parser.parseOneGroup(msg,AliveTaskAnalyser.ALIVE))) {
                    hi.setAlive();
                    isNew = false;
                    break;
                }
            }
            if (isNew) {
                living.add(new Node(parser.parseOneGroup(msg,AliveTaskAnalyser.ALIVE)));
            }
        }
    }

    private Runnable lessAliveForAll() {
        return new Runnable() {
            @Override
            public void run() {
                while (true) {
                    waitingSeconds(30);
                    synchronized (living) {
                        for (Node hi : living) {

                            hi.decrement();

                            if (hi.getStatus() <= 0) {
                                System.err.println(String.format("id: %s, removed", hi.getId()));
                                living.remove(hi);
                            }
                        }
                    }
                }
            }
        };
    }
}
