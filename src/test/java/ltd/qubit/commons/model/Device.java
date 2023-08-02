////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.annotation.Reference;
import ltd.qubit.commons.annotation.Unique;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示智能盒子设备。
 *
 * @author 胡海星
 */
public class Device implements Identifiable, WithApp, WithName, Stateful,
    WithComment, WithLocation, Auditable, Emptyful, Normalizable,
    Assignable<Device> {

  private static final long serialVersionUID = 8554519533779934750L;

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 所属 App 的基本信息。
   */
  @Reference(entity = App.class, property = "info")
  private StatefulInfo app;

  /**
   * 名称。
   */
  @Size(min = 1, max = 128)
  private String name;

  /**
   * 型号。
   */
  @Size(min = 1, max = 128)
  private String model;

  /**
   * 硬件版本号。
   */
  @Size(min = 1, max = 128)
  private String version;

  /**
   * 详细描述。
   */
  @Nullable
  private String description;

  /**
   * 设备运行的操作系统类型。
   *
   * <p>例如：Platform.WINDOWS</p>
   */
  private Platform osType;

  /**
   * 设备运行的操作系统的名称。
   *
   * <p>例如：“Windows 10 专业版”</p>
   */
  @Size(min = 1, max = 128)
  private String osName;

  /**
   * 设备运行的操作系统的版本号。
   *
   * <p>例如：“21H1”</p>
   */
  @Size(min = 1, max = 128)
  private String osVersion;

  /**
   * 客户端应用名称。
   */
  @Size(min = 1, max = 128)
  private String clientAppName;

  /**
   * 客户端应用版本。
   */
  @Size(min = 1, max = 128)
  private String clientAppVersion;

  /**
   * 设备插的SIM卡的信息。
   *
   * <p>有些设备提供可以4G/5G上网的SIM卡。</p>
   */
  @Reference
  @Nullable
  private SimCard simCard;

  /**
   * 设备当前所处的位置。
   *
   * <p>注意可能和设备部署地址的地理位置不同。</p>
   */
  @Nullable
  private Location location;

  /**
   * 硬件设备唯一ID
   */
  @Size(min = 1, max = 128)
  @Unique
  @Nullable
  private String udid;

  /**
   * 网卡MAC地址。
   */
  @Size(min = 1, max = 128)
  @Nullable
  private String macAddress;

  /**
   * 设备当前公网IP地址。
   */
  @Size(min = 1, max = 128)
  @Nullable
  private String ipAddress;

  /**
   * 设备所有者信息。
   */
  @Reference(entity = Person.class, property = "info")
  @Nullable
  private PersonInfo owner;

  /**
   * 设备部署地址。
   *
   * <p>注意设备部署地址的地理位置可能和设备当前所处地理位置不同。</p>
   */
  @Reference
  @Nullable
  private Address deployAddress;

  /**
   * 对象状态。
   */
  private State state;

  /**
   * 备注。
   *
   * <p>备注(comment)是由系统管理员填写，描述(description)可以由用户自己填写。</p>
   */
  @Nullable
  private String comment;

  /**
   * 注册(激活)时间。
   */
  @Precision(value = TimeUnit.SECONDS)
  @Nullable
  private Instant registerTime;

  /**
   * 上次启动时间。
   */
  @Precision(value = TimeUnit.SECONDS)
  @Nullable
  private Instant lastStartupTime;

  /**
   * 上次心跳连接时间。
   */
  @Precision(value = TimeUnit.SECONDS)
  @Nullable
  private Instant lastHeartbeatTime;

  /**
   * 创建时间。
   */
  @Precision(value = TimeUnit.SECONDS)
  private Instant createTime;

  /**
   * 最后一次修改时间。
   */
  @Precision(value = TimeUnit.SECONDS)
  @Nullable
  private Instant modifyTime;

  /**
   * 标记删除时间。
   */
  @Precision(value = TimeUnit.SECONDS)
  @Nullable
  private Instant deleteTime;

  public Device() {
    // empty
  }

  public Device(final Device other) {
    assign(other);
  }

  @Override
  public void assign(final Device other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    app = Assignment.clone(other.app);
    name = other.name;
    model = other.model;
    version = other.version;
    description = other.description;
    osType = other.osType;
    osName = other.osName;
    osVersion = other.osVersion;
    clientAppName = other.clientAppName;
    clientAppVersion = other.clientAppVersion;
    simCard = Assignment.clone(other.simCard);
    location = Assignment.clone(other.location);
    udid = other.udid;
    macAddress = other.macAddress;
    ipAddress = other.ipAddress;
    owner = Assignment.clone(other.owner);
    deployAddress = Assignment.clone(other.deployAddress);
    state = other.state;
    comment = other.comment;
    registerTime = other.registerTime;
    lastStartupTime = other.lastStartupTime;
    lastHeartbeatTime = other.lastHeartbeatTime;
    createTime = other.createTime;
    modifyTime = other.modifyTime;
    deleteTime = other.deleteTime;
  }

  @Override
  public Device clone() {
    return new Device(this);
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public StatefulInfo getApp() {
    return app;
  }

  public void setApp(final StatefulInfo app) {
    this.app = app;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getModel() {
    return model;
  }

  public void setModel(final String model) {
    this.model = model;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(final String version) {
    this.version = version;
  }

  @Nullable
  public String getDescription() {
    return description;
  }

  public void setDescription(@Nullable final String description) {
    this.description = description;
  }

  public Platform getOsType() {
    return osType;
  }

  public void setOsType(final Platform osType) {
    this.osType = osType;
  }

  public String getOsName() {
    return osName;
  }

  public void setOsName(final String osName) {
    this.osName = osName;
  }

  public String getOsVersion() {
    return osVersion;
  }

  public void setOsVersion(final String osVersion) {
    this.osVersion = osVersion;
  }

  public String getClientAppName() {
    return clientAppName;
  }

  public void setClientAppName(final String clientAppName) {
    this.clientAppName = clientAppName;
  }

  public String getClientAppVersion() {
    return clientAppVersion;
  }

  public void setClientAppVersion(final String clientAppVersion) {
    this.clientAppVersion = clientAppVersion;
  }

  @Nullable
  public SimCard getSimCard() {
    return simCard;
  }

  public void setSimCard(@Nullable final SimCard simCard) {
    this.simCard = simCard;
  }

  @Nullable
  public Location getLocation() {
    return location;
  }

  public void setLocation(@Nullable final Location location) {
    this.location = location;
  }

  @Nullable
  public String getUdid() {
    return udid;
  }

  public void setUdid(@Nullable final String udid) {
    this.udid = udid;
  }

  @Nullable
  public String getMacAddress() {
    return macAddress;
  }

  public void setMacAddress(@Nullable final String macAddress) {
    this.macAddress = macAddress;
  }

  @Nullable
  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(@Nullable final String ipAddress) {
    this.ipAddress = ipAddress;
  }

  @Nullable
  public PersonInfo getOwner() {
    return owner;
  }

  public void setOwner(@Nullable final PersonInfo owner) {
    this.owner = owner;
  }

  @Nullable
  public Address getDeployAddress() {
    return deployAddress;
  }

  public void setDeployAddress(@Nullable final Address deployAddress) {
    this.deployAddress = deployAddress;
  }

  public State getState() {
    return state;
  }

  public void setState(final State state) {
    this.state = state;
  }

  @Nullable
  public String getComment() {
    return comment;
  }

  public void setComment(@Nullable final String comment) {
    this.comment = comment;
  }

  @Nullable
  public Instant getRegisterTime() {
    return registerTime;
  }

  public void setRegisterTime(@Nullable final Instant registerTime) {
    this.registerTime = registerTime;
  }

  @Nullable
  public Instant getLastStartupTime() {
    return lastStartupTime;
  }

  public void setLastStartupTime(@Nullable final Instant lastStartupTime) {
    this.lastStartupTime = lastStartupTime;
  }

  @Nullable
  public Instant getLastHeartbeatTime() {
    return lastHeartbeatTime;
  }

  public void setLastHeartbeatTime(@Nullable final Instant lastHeartbeatTime) {
    this.lastHeartbeatTime = lastHeartbeatTime;
  }

  public Instant getCreateTime() {
    return createTime;
  }

  public void setCreateTime(final Instant createTime) {
    this.createTime = createTime;
  }

  @Nullable
  public Instant getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(@Nullable final Instant modifyTime) {
    this.modifyTime = modifyTime;
  }

  @Nullable
  public Instant getDeleteTime() {
    return deleteTime;
  }

  public void setDeleteTime(@Nullable final Instant deleteTime) {
    this.deleteTime = deleteTime;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Device other = (Device) o;
    return Equality.equals(id, other.id)
        && Equality.equals(app, other.app)
        && Equality.equals(name, other.name)
        && Equality.equals(model, other.model)
        && Equality.equals(version, other.version)
        && Equality.equals(description, other.description)
        && Equality.equals(osType, other.osType)
        && Equality.equals(osName, other.osName)
        && Equality.equals(osVersion, other.osVersion)
        && Equality.equals(clientAppName, other.clientAppName)
        && Equality.equals(clientAppVersion, other.clientAppVersion)
        && Equality.equals(simCard, other.simCard)
        && Equality.equals(location, other.location)
        && Equality.equals(udid, other.udid)
        && Equality.equals(macAddress, other.macAddress)
        && Equality.equals(ipAddress, other.ipAddress)
        && Equality.equals(owner, other.owner)
        && Equality.equals(deployAddress, other.deployAddress)
        && Equality.equals(state, other.state)
        && Equality.equals(comment, other.comment)
        && Equality.equals(registerTime, other.registerTime)
        && Equality.equals(lastStartupTime, other.lastStartupTime)
        && Equality.equals(lastHeartbeatTime, other.lastHeartbeatTime)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(modifyTime, other.modifyTime)
        && Equality.equals(deleteTime, other.deleteTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, app);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, model);
    result = Hash.combine(result, multiplier, version);
    result = Hash.combine(result, multiplier, description);
    result = Hash.combine(result, multiplier, osType);
    result = Hash.combine(result, multiplier, osName);
    result = Hash.combine(result, multiplier, osVersion);
    result = Hash.combine(result, multiplier, clientAppName);
    result = Hash.combine(result, multiplier, clientAppVersion);
    result = Hash.combine(result, multiplier, simCard);
    result = Hash.combine(result, multiplier, location);
    result = Hash.combine(result, multiplier, udid);
    result = Hash.combine(result, multiplier, macAddress);
    result = Hash.combine(result, multiplier, ipAddress);
    result = Hash.combine(result, multiplier, owner);
    result = Hash.combine(result, multiplier, deployAddress);
    result = Hash.combine(result, multiplier, state);
    result = Hash.combine(result, multiplier, comment);
    result = Hash.combine(result, multiplier, registerTime);
    result = Hash.combine(result, multiplier, lastStartupTime);
    result = Hash.combine(result, multiplier, lastHeartbeatTime);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, modifyTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("app", app)
        .append("name", name)
        .append("model", model)
        .append("version", version)
        .append("description", description)
        .append("osType", osType)
        .append("osName", osName)
        .append("osVersion", osVersion)
        .append("clientAppName", clientAppName)
        .append("clientAppVersion", clientAppVersion)
        .append("simCard", simCard)
        .append("location", location)
        .append("udid", udid)
        .append("macAddress", macAddress)
        .append("ipAddress", ipAddress)
        .append("owner", owner)
        .append("deployAddress", deployAddress)
        .append("state", state)
        .append("comment", comment)
        .append("registerTime", registerTime)
        .append("lastStartupTime", lastStartupTime)
        .append("lastHeartbeatTime", lastHeartbeatTime)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }
}
