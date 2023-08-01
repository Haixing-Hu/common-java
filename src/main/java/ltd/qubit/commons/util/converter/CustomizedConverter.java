////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.converter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * The annotation for the customized converters.
 *
 * @author Haixing Hu
 */
@Target({ ElementType.TYPE, ElementType.FIELD })
public @interface CustomizedConverter {
}
