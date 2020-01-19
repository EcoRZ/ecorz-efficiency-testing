package com.ecorz.stressapp.stresstestagent.prometheus;

import java.util.Calendar;
import java.util.Date;

public class QueryFields {
  private final Date startDate;
  private final Date endDate;

  private QueryFields(Date startDate, Date endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public static class Builder {
    private Date startDate_;
    private Date endDate_;

    private Builder() {
      this.startDate_ = Calendar.getInstance().getTime();
      this.endDate_ = Calendar.getInstance().getTime();
    }

    public static Builder getBuilder() {
      return new Builder();
    }

    public Builder startDate(Date startDate_) {
      this.startDate_ = startDate_;
      return this;
    }

    public Builder endDate(Date endDate_) {
      this.endDate_ = endDate_;
      return this;
    }

    public QueryFields build() {
      return new QueryFields(startDate_, endDate_);
    }
  }
}
