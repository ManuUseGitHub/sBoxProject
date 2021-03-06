/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author MAZE2
 */
public class Analysis {
     /** 
   * Attempts to calculate the size of a file or directory.
   * 
   * <p> Since the operation is non-atomic, the returned value may be inaccurate. 
   * However, this method is quick and does its best.
   * 
   * http://stackoverflow.com/questions/2149785/get-size-of-folder-or-file/19877372#19877372
   */
  public static long size (Path path) {

        final AtomicLong size = new AtomicLong(0);

        try
        {
            Files.walkFileTree (path, new SimpleFileVisitor<Path>() 
            {
                  @Override public FileVisitResult 
                visitFile(Path file, BasicFileAttributes attrs) {

                        size.addAndGet (attrs.size());
                        return FileVisitResult.CONTINUE;
                    }

                  @Override public FileVisitResult 
                visitFileFailed(Path file, IOException exc) {

                        System.out.println("skipped: " + file + " (" + exc + ")");
                        // Skip folders that can't be traversed
                        return FileVisitResult.CONTINUE;
                    }

                  @Override public FileVisitResult
                postVisitDirectory (Path dir, IOException exc) {

                        if (exc != null)
                            System.out.println("had trouble traversing: " + dir + " (" + exc + ")");
                        // Ignore errors traversing a folder
                        return FileVisitResult.CONTINUE;
                    }
            });
        }
        catch (IOException e)
        {
            throw new AssertionError ("walkFileTree will not throw IOException if the FileVisitor does not");
        }

        return size.get();
    }
}
