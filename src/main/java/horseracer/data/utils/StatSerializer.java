package horseracer.data.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import horseracer.model.Stats;

/**
 * Custom serializer for converting Stats objects into JSON format.
 * <p>
 * This class defines how a Stats object is written to JSON
 * when saving user data.
 *
 * @author Riley Wong
 * @version 1.0.0
 */
public class StatSerializer extends StdSerializer<Stats> {

  /**
   * Default constructor for StatSerializer.
   */
  public StatSerializer() {
    this(null);
  }

  /**
   * Creates a StatSerializer with a specific class type.
   *
   * @param t the class type to serialize
   */
  public StatSerializer(Class<Stats> t) {
    super(t);
  }

  /**
   * Serializes a Stats object into JSON format.
   * <p>
   * Each field of the Stats object is written as a JSON key-value pair.
   *
   * @param stats the Stats object to serialize
   * @param json_generator the JSON generator used for writing output
   * @param serializer the serializer provider
   * @throws IOException if an error occurs during writing
   */
  @Override
  public void serialize(Stats stats,
                        JsonGenerator json_generator,
                        SerializerProvider serializer) throws IOException {

    json_generator.writeStartObject();
    json_generator.writeNumberField("accuracy", stats.getAccuracy());
    json_generator.writeNumberField("peakWpm", stats.getPeakWpm());
    json_generator.writeNumberField("averageWpm", stats.getAverageWpm());
    json_generator.writeNumberField("errorCount", stats.getErrorCount());
    json_generator.writeNumberField("totalTimeSeconds", stats.getTotalTimeSeconds());
    json_generator.writeNumberField("highScore", stats.getHighScore());
    json_generator.writeNumberField("highestLevelReached", stats.getHighestLevelReached());
    json_generator.writeNumberField("wordsTyped", stats.getWordsTyped());
    json_generator.writeNumberField("roundsPlayed", stats.getRoundsPlayed());
    json_generator.writeEndObject();
  }
}