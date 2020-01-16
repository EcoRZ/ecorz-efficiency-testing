package com.ecorz.stressapp.common.run;

import java.io.File;
import java.util.Objects;

public class RunFileParams {
  public final File fileById;

  public RunFileParams(File fileById) {
    this.fileById = fileById;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RunFileParams that = (RunFileParams) o;
    return fileById.equals(that.fileById);
  }

  @Override
  public int hashCode() {

    return Objects.hash(fileById);
  }

}
