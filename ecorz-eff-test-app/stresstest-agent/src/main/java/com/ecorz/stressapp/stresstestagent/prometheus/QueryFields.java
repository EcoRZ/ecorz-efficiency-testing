package com.ecorz.stressapp.stresstestagent.prometheus;

import java.text.DateFormat;
import java.util.Calendar;

public class QueryFields {
  private final String startDate;
  private final String endDate;
  private final String promUser;
  private final String promPassword;
  private final String promRestEnd;
  private final String node19Cpus;
  private final String node20Cpus;

  private QueryFields(String startDate, String endDate,
      String promUser, String promPassword, String promRestEnd,
      String node19Cpus, String node20Cpus) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.promUser = promUser;
    this.promPassword = promPassword;
    this.promRestEnd = promRestEnd;
    this.node19Cpus = node19Cpus;
    this.node20Cpus = node20Cpus;
  }

  public static class Builder {
    private String startDate_;
    private String endDate_;
    private String promUser_;
    private String promPassword_;
    private String promRestEnd_;
    private String node19Cpus_;
    private String node20Cpus_;

    private Builder() {
      DateFormat format_ = TimeGenerator.generateFormat();
      this.startDate_ = format_.format(Calendar.getInstance().getTime());
      this.endDate_ = format_.format(Calendar.getInstance().getTime());
    }

    public static Builder getBuilder() {
      return new Builder();
    }

    public Builder startDate(String startDate_) {
      this.startDate_ = startDate_;
      return this;
    }

    public Builder endDate(String endDate_) {
      this.endDate_ = endDate_;
      return this;
    }

    public Builder promUser(String promUser_) {
      this.promUser_ = promUser_;
      return this;
    }

    public Builder promPassword(String promPassword_) {
      this.promPassword_ = promPassword_;
      return this;
    }

    public Builder promRestEnd(String promRestEnd_) {
      this.promRestEnd_ = promRestEnd_;
      return this;
    }

    public Builder node19Cpus(String node19Cpus_) {
      this.node19Cpus_ = node19Cpus_;
      return this;
    }

    public Builder node20Cpus(String node20Cpus_) {
      this.node20Cpus_ = node20Cpus_;
      return this;
    }

    public QueryFields build() {
      return new QueryFields(startDate_, endDate_, promUser_,
          promPassword_, promRestEnd_, node19Cpus_, node20Cpus_);
    }
  }
}
