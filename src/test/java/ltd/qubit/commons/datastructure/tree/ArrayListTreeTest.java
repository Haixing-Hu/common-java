////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.tree;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class ArrayListTreeTest {

  @Test
  public void test() throws JsonProcessingException {
    final Tree<String, String> root = new ArrayListTree<>("root", "root");
    root.addChild(new ArrayListTree<>("child1", "child1"));
    root.addChild(new ArrayListTree<>("child2", "child2"));
    root.addChild(new ArrayListTree<>("child3", "child3"));
    final JsonMapper mapper = new JsonMapper();
    final String json = mapper.writeValueAsString(root);
    System.out.println(json);
//    final TypeReference<ArrayListTree<String, String>> type
//        = new TypeReference<ArrayListTree<String, String>>(){};
//    final ArrayListTree<String, String> obj = mapper.readValue(json, type);
//    assertEquals(root, obj);
  }
}