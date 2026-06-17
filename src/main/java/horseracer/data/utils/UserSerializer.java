package horseracer.data.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import horseracer.model.User;

/**
 * Custom serializer for converting User objects into JSON format.
 * <p>
 * This class defines how a User object is written to JSON,
 * including its username, password, role, and associated statistics.
 *
 * @author Riley Wong
 * @version 1.0.0
 */
public class UserSerializer extends StdSerializer<User> {

  /**
   * Default constructor for UserSerializer.
   */
  public UserSerializer() {
    this(null);
  }

  /**
   * Creates a UserSerializer with a specific class type.
   *
   * @param t the class type to serialize
   */
  public UserSerializer(Class<User> t) {
    super(t);
  }

  /**
   * Serializes a User object into JSON format.
   * <p>
   * The user's fields are written as JSON key-value pairs,
   * including nested serialization of the Stats object.
   *
   * @param user the User object to serialize
   * @param json_generator the JSON generator used for writing output
   * @param serializer the serializer provider
   * @throws IOException if an error occurs during writing
   */
  @Override
  public void serialize(User user,
                        JsonGenerator json_generator,
                        SerializerProvider serializer) throws IOException {

    json_generator.writeStartObject();
    json_generator.writeStringField("username", user.getUsername());
    json_generator.writeStringField("password", user.getPassword());
    json_generator.writeStringField("role", user.getRole().toString());
    json_generator.writeObjectField("stats", user.getStats());
    json_generator.writeEndObject();
  }
}