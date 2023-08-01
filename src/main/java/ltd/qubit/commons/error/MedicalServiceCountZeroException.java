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
 * 表示医疗服务项目次数为零。
 *
 * @author pino
 */
public class MedicalServiceCountZeroException extends BusinessLogicException {

  private static final long serialVersionUID = 3846236782286447274L;

  public MedicalServiceCountZeroException() {
    super(ErrorCode.MEDICAL_SERVICE_COUNT_ZERO);
  }

}
