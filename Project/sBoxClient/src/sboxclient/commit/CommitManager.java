/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.commit;

import Stepping.implementations.SoftStepper;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import sboxclient.Analysis;
import sboxclient.server.SBoxServerClient;
import sboxclient.TaskAnalyser;
import sboxclient.nextOkActionable.NextOkActionnableTask;
import sboxclient.pull.ZypTransferer;

/**
 *
 * @author tonioush
 */
public class CommitManager implements Observer {

    private final SBoxServerClient client;
    private final SoftStepper stepper;
    private File file;
    private boolean committing;

    public CommitManager(SBoxServerClient client, Observer stepObserver) {
        stepper = SoftStepper.newStepper(stepObserver);
        committing = false;
        this.client = client;
    }
    @Override
    public void update(Observable o, Object o1) {
        if (o instanceof CommitTask) {

            CommitTask task = (CommitTask) o;
            String analysed = new TaskAnalyser().analyse(task.getMessage());

            if (analysed.matches("^GOOD.*") && !committing) {
                committing = true;
                tryZip(task);
            }
        }
    }

    public void tryZip(NextOkActionnableTask o) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //1 mise en tempon d'une version zip
                    File folder = new File("temp");
                    folder.mkdir();
                    int nbrTreatment = folder.listFiles().length;
                    
                    ZypTransferer.zipFolder(file, file = new File(String.format("temp/temporaire%d.zip",nbrTreatment)), stepper);
                    
                    stepper.setMaximum((int)Analysis.size(file.toPath())/57-1);

                    //3 transfère du zip ligne par ligne
                    o.setNextOkCloseable(true);
                    ZypTransferer.sendFileToServer(file, client, stepper);
                    deleteTempon();
                    //client.sendToServer("QUERY");
                } catch (IOException e) {
                    deleteTempon();
                }
            }

            private void deleteTempon() {
                if (file != null) {
                    file.delete();
                    file.getParentFile().delete();
                }
            }
        }).start();

    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) {
        this.file = file;
    }

}
