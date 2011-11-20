package tr.edu.gsu.mataws.preprocessing.decomposition.wrappers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class WrapperForFastObjectSaver {

    /**
     * Stores serializable objects. IMPORTANT: THOSE OBJECTS SHOULD HAVE A serialVersionUID!
     *     private static final long serialVersionUID = 1L;
     * @throws IOException 
     */
    public static void saveToFile(String filename, Serializable serializableObject) throws IOException {
      FileOutputStream fos = new FileOutputStream(filename);
      ObjectOutputStream oos = new ObjectOutputStream( fos );
      oos.writeObject( serializableObject );
      oos.close();
    }

    
    /**
     * Load a serialized dictionary.
     * @throws IOException
     */
    public static synchronized Object load(String filename) throws IOException {
      //System.out.println(filename);
      String s = filename.substring(1);
      filename = System.getProperty("user.dir") + File.separator + "resources" + File.separator + s;
      File file = new File(filename);
      FileInputStream f = new FileInputStream(file);
      ObjectInputStream oos = new ObjectInputStream(f);
      try {
        return oos.readObject();
      } catch (ClassNotFoundException e) {
        IOException ioe = new IOException();
        ioe.initCause(e);
        throw ioe;
      }
    }
}
