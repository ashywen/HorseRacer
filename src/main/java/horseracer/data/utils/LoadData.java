
package horseracer.data.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

/**
 * Utility class for loading data from JSON files.
 *
 * @author Riley Wong
 * @version 1.0.0
 */
public class LoadData {

  /**
   * Read the player data JSON file and initiate the stored user's data into an
   * array list
   *
   * @param file_path the file path to the data file
   * @param obj       reference to the class we want the arraylist to contain
   * @param err       Exception class that wants to be thrown
   * 
   * @throws Exception throws a given exception when load fails
   */
  public static <T> ArrayList<T> loadData(String file_path, Class<T> obj, Class<? extends Exception> err)
      throws Exception {
    try {
      File user_data_file = new File(file_path);

      // Create the file reader
      ObjectMapper json_reader = new ObjectMapper();

      // Read the file into a list
      ArrayList<T> mapped_data = json_reader.readValue(user_data_file,
          json_reader.getTypeFactory().constructCollectionType(ArrayList.class, obj));

      return mapped_data;

    } catch (Exception e) {
      // Construct the exception from the given class reference
      Constructor<? extends Exception> construct = err.getConstructor();
      construct.getClass();
      Exception except = construct.newInstance();

      throw except;
    }
  }
}
