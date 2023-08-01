////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

import java.util.List;

public class GenericTypeHolder {
  public GenericParent<String> stringParent;
  public GenericParent<Integer> integerParent;
  public List<Foo> foos;
  public GenericParent<Bar>[] barParents;
}
