////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

/**
 * 此接口表示实体类具有可被审计的属性，即记录了对象的
 * <ul>
 *   <li>创建时间；</li>
 *   <li>最后一次修改时间；</li>
 *   <li>标记删除时间；</li>
 * </ul>
 *
 * @author 胡海星
 */
public interface Auditable extends Creatable, Modifiable, Deletable {
    // empty
}
