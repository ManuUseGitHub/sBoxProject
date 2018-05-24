/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Versionning.dataource;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author MAZE2
 */
public interface IProfilsListt extends Serializable {

    /**
     * @return the profils
     */
    ArrayList<Profil> getProfils();

    /**
     * @param profils the profils to set
     */
    void setProfils(ArrayList<Profil> profils);
    
}
