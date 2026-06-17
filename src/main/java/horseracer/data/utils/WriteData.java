package horseracer.data.utils;

import java.io.File;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import horseracer.model.Stats;
import horseracer.model.User;

/**
 * Utility class for writing data to JSON files.
 * <p>
 * This class converts objects into JSON format and saves them
 * to a specified file using custom serializers.
 *
 * @author Riley Wong
 * @version 1.0.0
 */
public class WriteData {

  /**
   * Writes a list of objects to a file in JSON format.
   *
   * @param file_path the file path where the data will be saved
   * @param data the list of objects to write
   * @param <T> the type of objects in the list
   */
  public static <T> void writeData(String file_path, ArrayList<T> data) {
    try {
      File save_file = new File(file_path);

      ObjectMapper json_mapper = prepareMapper();

      json_mapper.writeValue(save_file, data);

    } catch (Exception e) {
      return;
    }
  }

  /**
   * Prepares an ObjectMapper with custom serializers.
   * <p>
   * This method registers serializers for User and Stats
   * to ensure proper JSON formatting.
   *
   * @return the configured ObjectMapper
   */
  private static ObjectMapper prepareMapper() {

    ObjectMapper json_mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule("CustomSerializer");
    module.addSerializer(User.class, new UserSerializer());
    module.addSerializer(Stats.class, new StatSerializer());
    json_mapper.registerModule(module);

    return json_mapper;
  }
}