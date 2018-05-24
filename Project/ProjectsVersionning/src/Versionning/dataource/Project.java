/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Versionning.dataource;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author MAZE2
 */
public class Project implements IProject {

    private int version;
    private String name;

    public Project(int version, String name) {
        this.version = version;
        this.name = name;
    }

    public Project() {
    }

    @Override
    public String toString() {
        return String.format("%s %d", name, version);
    }
    
    @Override
    public String getListFormat(){
        return String.format("%s:%d;", name, version);
    }

    /**
     * @return the version
     */
    @Override
    public int getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    @Override
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Project ? 
                (((Project) o).getName()).equals(name) && (((Project) o).getVersion()) == version : 
                false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.version;
        hash = 97 * hash + Objects.hashCode(this.name);
        return hash;
    }
}
