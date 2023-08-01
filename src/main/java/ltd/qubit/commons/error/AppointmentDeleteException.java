////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

/**
 * 预约单删除异常。
 *
 * @author 王佳
 */
public class AppointmentDeleteException extends BusinessLogicException {

  private static final long serialVersionUID = 3104633352054555682L;

  public AppointmentDeleteException() {
    super(ErrorCode.APPOINTMENT_CANNOT_DELETE);
  }
}
