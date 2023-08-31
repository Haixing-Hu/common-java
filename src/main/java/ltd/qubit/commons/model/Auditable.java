////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

/**
 * This interface indicates that an entity class has properties that can be audited.
 *
 * <p>This interface documents the object&#39;s</p>
 * <ul>
 *   <li>creation time;</li>
 *   <li>last modification time;</li>
 *   <li>mark deletion time;</li>
 * </ul>
 *
 * @author Haixing Hu
 */
public interface Auditable extends Creatable, Modifiable, Deletable {
    // empty
}
