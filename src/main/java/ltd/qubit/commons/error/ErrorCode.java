////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

/**
 * The enumeration of error codes.
 *
 * @author Haixing Hu
 */
public enum ErrorCode {
  /**
   * 表示指定的对象已绑定了某个用户。
   */
  ALREADY_BIND_USER,

  /**
   * Indicates that the specified entity already exist.
   */
  ALREADY_EXIST,

  /**
   * 已退货。
   */
  ALREADY_RETURNED,

  /**
   * 表示预约已生效不能被删除。
   */
  APPOINTMENT_CANNOT_DELETE,

  /**
   * Indicates that the operation cannot be performed until the app get the
   * authentication.
   */
  APP_AUTHENTICATION_REQUIRED,

  /**
   * Indicates that the format of the request is invalid.
   */
  BAD_REQUEST,

  /**
   * Indicates that the specified entity was marked as blocked.
   */
  BLOCKED,

  /**
   * Indicates that the current user blocks a user.
   */
  BLOCK_USER,

  /**
   * Indicates that the current user was blocked by the other user.
   */
  BLOCKED_BY_USER,

  /**
   * Indicates an error occurs in the chat server.
   */
  CHAT_SERVER_ERROR,

  /**
   * Indicates that the complaint about an entity reported by a user has already
   * existed.
   */
  COMPLAINT_EXIST,

  /**
   * Indicates that another database error occurred.
   */
  DATABASE_ERROR,

  /**
   * Indicates that the specified entity was marked as deleted.
   */
  DELETED,

  /**
   * Indicates an error occurred while the current user try to delete itself.
   */
  DELETE_CURRENT_USER,

  /**
   * Indicates that an error is occurred while deleting an entity with a field
   * referenced by another entity as a foreign key.
   */
  DELETE_REFERENCED_FOREIGN_KEY,

  /**
   * Indicate that decrypting a message failed.
   */
  DECRYPT_MESSAGE_FAILED,

  /**
   * Indicates that the specified entity was disabled.
   */
  DISABLED,

  /**
   * 对消息计算数字摘要时失败。
   */
  DIGEST_MESSAGE_FAILED,

  /**
   * 购买者重复。
   */
  DUPLICATE_BUYER,

  /**
   * Indicates that the input value of an unique field is duplicated with an
   * existing value in that field.
   */
  DUPLICATE_KEY,

  /**
   * 商品重复。
   */
  DUPLICATE_PRODUCT,

  /**
   * 重复购买。
   */
  DUPLICATE_PURCHASE,

  /**
   * Indicates that a required field is empty.
   */
  EMPTY_FIELD,

  /**
   * 对消息进行编码时操作失败。
   */
  ENCODING_MESSAGE_FAILED,

  /**
   * Indicate that encrypting a message failed.
   */
  ENCRYPT_MESSAGE_FAILED,

  /**
   * 表示快递系统调用失败。
   *
   * FIXME: 没有对应异常类，这个错误代码在哪里用到？另外名称不够清晰。
   */
  EXPRESS_GENERATE_FAILURE,

  /**
   * Indicates that the value of a field is out of the range.
   */
  FIELD_OUT_OF_RANGE,

  /**
   * Indicates that the length of a field exceeds its limitation.
   */
  FIELD_TOO_LONG,

  /**
   * Indicates that the specified file does not exist.
   */
  FILE_NOT_EXIST,

  /**
   * Indicates that the friendship already exist.
   */
  FRIEND_EXIST,

  /**
   * Indicates that the friendship request already exist.
   */
  FRIEND_REQUEST_EXIST,

  /**
   * 表示被监护人未退款。
   */
  GUARDED_NOT_RETURN,

  /**
   * Indicates that the guardian of a person is the person itself.
   */
  GUARDIAN_IS_SELF,

  /**
   * 表示监护人未购买。
   */
  GUARDIAN_NOT_BUY,

  /**
   * 表示监护人处于退款中。
   */
  GUARDIAN_IS_REFUNDING,

  /**
   * Indicates that a HTTP request error occurred.
   */
  HTTP_ERROR,

  /**
   * Indicates that the entity is marked as inactive.
   */
  INACTIVE,

  /**
   * Indicates an internal error occurs, which is usually caused by the bug.
   */
  INTERNAL_ERROR,

  /**
   * Indicates that the value of an enumeration is invalid.
   */
  INVALID_ENUM_VALUE,

  /**
   * Indicates that the format of the email is invalid.
   */
  INVALID_EMAIL_FORMAT,

  /**
   * Indicates that the format of a field is invalid.
   */
  INVALID_FIELD_FORMAT,

  /**
   * Indicates that the value of a field is invalid.
   */
  INVALID_FIELD_VALUE,

  /**
   * Indicates that the characters of the value of a field is invalid.
   */
  INVALID_FIELD_VALUE_CHARACTER,

  /**
   * Indicates that the format of a file is invalid.
   */
  INVALID_FILE_FORMAT,

  /**
   * 监护人年龄错误。
   */
  INVALID_GUARDIAN_AGE,

  /**
   * Indicates that the format of the identity card number is invalid.
   */
  INVALID_IDENTITY_CARD_FORMAT,

  /**
   * Indicates that the format of a json object is invalid.
   */
  INVALID_JSON_FORMAT,

  /**
   * 订单信息错误。
   */
  INVALID_ORDER,

  /**
   * 订单客户信息错误。
   */
  INVALID_ORDER_CLIENT,

  /**
   * Indicates that the format of the password is invalid.
   */
  INVALID_PASSWORD_FORMAT,

  /**
   * Indicates that the format of the phone number is invalid.
   */
  INVALID_PHONE_FORMAT,

  /**
   * 产品价格错误。
   */
  INVALID_PRICE,

  /**
   * 产品数量错误错误。
   */
  INVALID_PRODUCT_COUNT,

  /**
   * 退款金额错误。
   */
  INVALID_REFUNDABLE,

  /**
   * Indicates the digital signature is invalid.
   */
  INVALID_SIGNATURE,

  /**
   * 表示状态错误。
   */
  INVALID_STATUS,

  /**
   * Indicates that the app or user's access token is invalid.
   */
  INVALID_TOKEN,

  /**
   * 表示类型错误。
   */
  INVALID_TYPE,

  /**
   * Indicates that the format of the username is invalid.
   */
  INVALID_USERNAME_FORMAT,

  /**
   * Indicates that another I/O error occurred.
   */
  IO_ERROR,

  /**
   * Indicates that the operation cannot be performed since the limitation of
   * the system has been reached.
   */
  LIMITATION_REACHED,

  /**
   * Indicates that an entity was locked.
   */
  LOCKED,

  /**
   * Indicates that the operation cannot be performed until the user logged into
   * the system.
   */
  LOGIN_REQUIRED,

  /**
   * Indicates that the operation cannot be performed until the current user
   * logged out from the system.
   */
  LOGOUT_REQUIRED,

  /**
   * Thrown to indicate that a user try to make a friend with himself.
   */
  MAKE_FRIEND_WITH_SELF,

  /**
   * 表示医疗服务未预约。
   */
  MEDICAL_SERVICE_NOT_APPOINTMENT,

  /**
   * 表示医疗服务可用次数为零。
   */
  MEDICAL_SERVICE_COUNT_ZERO,

  /**
   * 表示出生日期不匹配。
   */
  MISMATCH_BIRTHDAY,

  /**
   * 表示证件不匹配。
   */
  MISMATCH_CREDENTIAL,

  /**
   * 表示货币单位不匹配。
   */
  MISMATCH_CURRENCY,

  /**
   * 表示电子邮箱不匹配。
   */
  MISMATCH_EMAIL,

  /**
   * 表示性别不匹配。
   */
  MISMATCH_GENDER,

  /**
   * 表示手机号码不匹配。
   */
  MISMATCH_MOBILE,

  /**
   * 支付信息不匹配。
   */
  MISMATCH_PAYMENT,

  /**
   * 表示姓名不匹配。
   */
  MISMATCH_PERSON_NAME,

  /**
   * Thrown to indicate that a product must be bought for the buyer himself.
   */
  MUST_BUY_FOR_SELF,

  /**
   * Indicates that there is no enough inventory of the product.
   */
  NO_ENOUGH_INVENTORY,

  /**
   * Indicates that the current user has no privilege to perform the operation.
   */
  NO_PRIVILEGE,

  /**
   * Indicates that the specified entity was not marked as deleted.
   */
  NOT_DELETED,

  /**
   * Indicates that the specified entity does not exist.
   */
  NOT_EXIST,

  /**
   * 未购买过。
   */
  NOT_PURCHASED,

  /**
   * Indicates no error.
   */
  NONE,

  /**
   * Indicates that the specified entity was obsolted.
   */
  OBSOLETED,

  /**
   * Indicates that the operation to be performed was forbidden by the system.
   */
  OPERATION_FORBIDDEN,

  /**
   * Indicates that the operations are too frequent.
   */
  OPERATION_TOO_FREQUENT,

  /**
   * Indicates that the opposite of a label is the label itself.
   */
  OPPOSITE_IS_SELF,

  /**
   * Indicates that the order has been expired.
   */
  ORDER_EXPIRED,

  /**
   * 支付的款项不匹配。
   */
  PAID_MONEY_MISMATCH,

  /**
   * Indicates that the parent of a label is the label itself.
   */
  PARENT_IS_SELF,

  /**
   * Indicates that the password is mismatched.
   */
  PASSWORD_MISMATCH,

  /**
   * 表示药房系统连接失败。
   *
   * FIXME: 没有对应异常类，这个错误代码在哪里用到？另外名称不够清晰。
   */
  PHARMACY_CLIENT_FAILURE,

  /**
   * 表示处方订单不能退货。
   */
  PRESCRIPTION_CANNOT_RETURN,

  /**
   * 表示处方没有找到可供货药店。
   */
  PRESCRIPTION_CANNOT_FIND_SELLER,

  /**
   * 表示产品不能被退货。
   */
  PRODUCT_CANNOT_RETURN,

  /**
   * Indicates that the specified entity was readonly and cannot be modified.
   */
  READONLY,

  /**
   * Indicates that an error is occurred while adding or updating an entity with
   * a non-exist foreign key.
   */
  REFER_TO_NON_EXIST_FOREIGN_KEY,

  /**
   * HTTP请求的方法不被支持。
   */
  REQUEST_METHOD_NOT_SUPPORTED,

  /**
   * 退货后再次重购。
   */
  RETURNED_PURCHASE_AGAIN,

  /**
   * Indicates that the entity has already been reviewed, and thus cannot be
   * updated nor deleted.
   */
  REVIEWED,

  /**
   * Indicates that the security key is mismatched.
   */
  SECURITY_KEY_MISMATCH,

  /**
   * 表示产品的销售尚未开始。
   */
  SELLING_NOT_START,

  /**
   * 表示产品的销售已经结束。
   */
  SELLING_HAS_END,

  /**
   * Indicates that failed to send an email.
   */
  SEND_EMAIL_FAILED,

  /**
   * Indicates the failure of sending SMS.
   */
  SEND_SMS_FAILED,

  /**
   * 表示调用服务失败。
   */
  SERVICE_FAILURE,

  /**
   * Indicates that the session was expired.
   */
  SESSION_EXPIRED,

  /**
   * 对消息进行数字签名时操作失败。
   */
  SIGN_MESSAGE_FAILED,

  /**
   * Indicates that the target of an operation is the current user himself.
   */
  TARGET_IS_SELF,

  /**
   * Indicates that the token was expired.
   */
  TOKEN_EXPIRED,

  /**
   * Indicates that the user login failed too many times.
   */
  TOO_MANY_LOGIN_FAILURES,

  /**
   * Indicates that the app authorize failed too many times.
   */
  TOO_MANY_AUTHENTICATION_FAILURES,

  /**
   * Indicates the unknown error.
   */
  UNKNOWN,

  /**
   * Indicates that the operation is unnecessary.
   */
  UNNECESSARY_OPERATION,

  /**
   * 表示没有资格进行续保。
   */
  UNQUALIFIED_RENEWAL,

  /**
   * Indicates that the algorithm is not supported.
   */
  UNSUPPORTED_ALGORITHM,

  /**
   * Indicates that the class is not supported.
   */
  UNSUPPORTED_CLASS,

  /**
   * Indicates that the content type is not supported.
   */
  UNSUPPORTED_CONTENT_TYPE,

  /**
   * Indicates that the file type is not supported.
   */
  UNSUPPORTED_FILE_TYPE,

  /**
   * Indicates that the specified version is not supported.
   */
  UNSUPPORTED_VERSION,

  /**
   * Indicates that the files to be uploaded is too large.
   */
  UPLOAD_FILE_TOO_LARGE,

  /**
   * Indicates that the username was occupied.
   */
  USERNAME_OCCUPIED,

  /**
   * Indicates that the verification code has been expired.
   */
  VERIFY_CODE_EXPIRED,

  /**
   * Indicates that the verification code is matched.
   */
  VERIFY_CODE_MISMATCH,

  /**
   * 对数字签名进行验证时操作失败。
   */
  VERIFY_SIGNATURE_FAILED,
}
