/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.environnement;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 *
 * @author MAZE2
 */
public class SingletonWhohasList {
    private final Set<String> infoprovideList;

    private static SingletonWhohasList INSTANTCE = new SingletonWhohasList();

    private SingletonWhohasList() {
        infoprovideList = (Set<String>)Collections.synchronizedSet(new HashSet<String>());
    }
    
    public static SingletonWhohasList getINSTANTCE() {
        return INSTANTCE;
    }
    
    public void addWhohasTask(String who,String id,String version){
        synchronized(infoprovideList){
            infoprovideList.add(String.format("WHOHAS %s %s %s",who,id,version));
        }
    }
    
    public void addWhohasTask(String whohasTask){
        synchronized(infoprovideList){
            infoprovideList.add(whohasTask);
        }
    }
    
    public void deleteWhohasTask(String infoTask){
        synchronized(infoprovideList){
            infoprovideList.remove(infoTask);
        }
    }
    
    public boolean isHandling(String whohas){
        synchronized(infoprovideList){
            return infoprovideList.contains(whohas);
        }
    }
}
