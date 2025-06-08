////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

/**
 * A provider providing serializer, deserializer, key serializer, key deserializer
 * of a specified type for the JACKSON framework.
 *
 * @param <T>
 *     the type of the object to be serialized and deserialized.
 * @author Haixing Hu
 */
public interface TypeRegister<T> {

  Class<T> getType();

  JsonSerializer<T> getSerializer();

  JsonDeserializer<T> getDeserializer();

  JsonSerializer<T> getKeySerializer();

  KeyDeserializer getKeyDeserializer();
}