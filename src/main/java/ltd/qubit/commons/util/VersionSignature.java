////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.io.InputUtils;
import ltd.qubit.commons.io.OutputUtils;
import ltd.qubit.commons.io.error.InvalidSignatureException;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A class used to sign and verify the version signature.
 *
 * @author Haixing Hu
 */
@Immutable
public final class VersionSignature implements Serializable {

  private static final long serialVersionUID = 6492524387270673783L;

  public static final boolean DEFAULT_BACKWARD_COMPATIBLE = true;

  private final long uid;
  private final Version version;
  private final boolean backwardCompatible;

  public VersionSignature(final Version version) {
    uid = serialVersionUID;
    this.version = requireNonNull("version", version);
    backwardCompatible = DEFAULT_BACKWARD_COMPATIBLE;
  }

  public VersionSignature(final long uid, final Version version) {
    this.uid = uid;
    this.version = requireNonNull("version", version);
    backwardCompatible = DEFAULT_BACKWARD_COMPATIBLE;
  }

  public VersionSignature(final long uid, final Version version,
      final boolean backwardCompatible) {
    this.uid = uid;
    this.version = requireNonNull("version", version);
    this.backwardCompatible = backwardCompatible;
  }

  public long getUid() {
    return uid;
  }

  public Version getVersion() {
    return version;
  }

  public boolean isBackwardCompatible() {
    return backwardCompatible;
  }

  public Version verify(final InputStream in) throws IOException {
    final long uid = InputUtils.readLong(in);
    if (uid != this.uid) {
      throw new InvalidSignatureException();
    }
    final Version version = VersionBinarySerializer.INSTANCE.deserialize(in, false);
    if (backwardCompatible) {
      if (version.compareTo(this.version) > 0) {
        // the actual version is newer than this.version
        throw new VersionMismatchException(this.version, version);
      }
    } else {
      if (! version.equals(this.version)) {
        // the actual version does not equal this.version
        throw new VersionMismatchException(this.version, version);
      }
    }
    return version;
  }

  public void sign(final OutputStream out) throws IOException {
    OutputUtils.writeLong(out, uid);
    VersionBinarySerializer.INSTANCE.serialize(out, version);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("uid", uid)
               .appendToString("version", version.toString())
               .toString();
  }
}
