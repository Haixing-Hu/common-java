////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

/**
 * 此枚举表示移动数据网络类型。
 *
 * @author 胡海星
 */
public enum DataNetworkType {
  /**
   * 2G(2.5), General Packet Radia Service 114kbps.
   *
   * <p>在国内就是联通2G网络。</p>
   */
  GPRS(2),

  /**
   * 2G，电信 Code Division Multiple Access 码分多址。
   *
   * <p>在国内就是电信2G网络。</p>
   */
  CDMA(2),

  /**
   * 2G(2.75G), Enhanced Data Rate for GSM Evolution 384kbps.
   *
   * <p>在国内就是移动2G网络。</p>
   */
  EDGE(2),

  /**
   * 2G, CDMA2000 1xRTT (RTT - 无线电传输技术) 144kbps 2G的过渡。
   */
  ONE_X_RTT(2),

  /**
   * 2G, Integrated Dispatch Enhanced Networks 集成数字增强型网络。
   */
  IDEN(2),

  /**
   * 2G.
   */
  GSM(2),

  /**
   * TD_SCDMA制式。
   *
   * <p>在国内这是移动3G网络。</p>
   */
  TD_SCDMA(3),

  /**
   * CDMA2000 制式。
   *
   * <p>在国内这是电信3G网络。</p>
   */
  CDMA2000(3),

  /**
   * 3G, 1.8mbps - 3.1mbps 属于3G过渡，3.5G.
   *
   * <p>在国内这是电信3G网络。</p>
   */
  EVDO_A(3),

  /**
   * 3G WCDMA 联通3G Universal MOBILE Telecommunication System 完整的3G移动通信技术
   * 标准.
   */
  UMTS(3),

  /**
   * 3G, (EVDO 全程 CDMA2000 1xEV-DO) Evolution - Data Only (Data Optimized)
   * 153.6kps - 2.4mbps 属于3G.
   */
  EVDO_0(3),

  /**
   * 3.5G, 高速下行分组接入 3.5G WCDMA High Speed Downlink Packet Access 14.4mbps.
   */
  HSDPA(3),

  /**
   * 3.5G, High Speed Uplink Packet Access 高速上行链路分组接入 1.4 - 5.8 mbps.
   */
  HSUPA(3),

  /**
   * 3G, (分HSDPA,HSUPA) High Speed Packet Access.
   */
  HSPA(3),

  /**
   * 3G, EV-DO Rev.B 14.7Mbps 下行 3.5G.
   */
  EVDO_B(3),

  /**
   * 3G, CDMA2000 向 LTE 4G 的中间产物 Evolved High Rate Packet Data HRPD 的升级.
   */
  EHRPD(3),

  /**
   * 3G, HSPAP 比 HSDPA 快些.
   */
  HSPAP(3),

  IWLAN(3),

  /**
   * 4G, Long Term Evolution FDD-LTE 和 TDD-LTE , 3G过渡，升级版 LTE Advanced 才
   * 是4G.
   */
  LTE(4),

  /**
   * 5G网络。
   */
  NR(5),

  /**
   * 未知。
   */
  UNKNOWN(0);

  private final int generation;

  DataNetworkType(final int generation) {
    this.generation = generation;
  }

  /**
   * 获取该网络是第几代技术。
   *
   * @return
   *     该网络技术是第几代，返回2表示2G，3表示3G，4表示4G，5表示5G。
   */
  public int generation() {
    return generation;
  }
}
