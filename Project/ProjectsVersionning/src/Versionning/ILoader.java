/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Versionning;

import java.io.Reader;
import java.io.Serializable;
import java.nio.file.Path;

/**
 *
 * @author MAZE2
 */
public interface ILoader {

    <T extends Serializable> T load();
    
    void setFilePath(Path path);
}
