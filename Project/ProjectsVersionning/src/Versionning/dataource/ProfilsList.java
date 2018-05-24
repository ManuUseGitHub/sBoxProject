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
public class ProfilsList implements IProfilsListt{
    private ArrayList<Profil> profils;

    /**
     * @return the profils
     */
    @Override
    public ArrayList<Profil> getProfils() {
        return profils;
    }

    /**
     * @param profils the profils to set
     */
    @Override
    public void setProfils(ArrayList<Profil> profils) {
        this.profils = profils;
    }
}
