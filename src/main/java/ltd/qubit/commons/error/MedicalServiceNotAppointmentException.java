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
 * 医疗服务项目未预约。
 *
 * @author pino
 */
public class MedicalServiceNotAppointmentException extends BusinessLogicException {

  private static final long serialVersionUID = 1796296442775916389L;

  public MedicalServiceNotAppointmentException() {
    super(ErrorCode.MEDICAL_SERVICE_NOT_APPOINTMENT);
  }
}
