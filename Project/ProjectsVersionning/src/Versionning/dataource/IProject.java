/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Versionning.dataource;

import java.io.Serializable;

/**
 *
 * @author MAZE2
 */
public interface IProject extends Serializable {

    boolean equals(Object o);

    String getListFormat();

    /**
     * @return the name
     */
    String getName();

    /**
     * @return the version
     */
    int getVersion();

    int hashCode();

    /**
     * @param name the name to set
     */
    void setName(String name);

    /**
     * @param version the version to set
     */
    void setVersion(int version);
    
}
