package com.ecorz.stressapp.stresstestagent.prometheus;

import java.text.DateFormat;
import java.util.Calendar;

public class PromFields {
  private final PromMetaFields metaFields;
  private final PromQueryFields queryFields;

  public PromFields(
      PromMetaFields metaFields,
      PromQueryFields queryFields) {
    this.metaFields = metaFields;
    this.queryFields = queryFields;
  }

  public PromMetaFields getMetaFields() {
    return metaFields;
  }

  public PromQueryFields getQueryFields() {
    return queryFields;
  }

  public static class PromMetaFields {
    private final String promUser;
    private final String promPassword;
    private final String promRestEnd;
    private final String promQueryCmdIdent;
    private final String node19Cpus;
    private final String node20Cpus;

    private PromMetaFields(String promUser, String promPassword, String promRestEnd,
       String promQueryCmdIdent, String node19Cpus, String node20Cpus) {
      this.promUser = promUser;
      this.promPassword = promPassword;
      this.promRestEnd = promRestEnd;
      this.promQueryCmdIdent = promQueryCmdIdent;
      this.node19Cpus = node19Cpus;
      this.node20Cpus = node20Cpus;
    }

    public String getPromUser() {
      return promUser;
    }

    public String getPromPassword() {
      return promPassword;
    }

    public String getPromRestEnd() {
      return promRestEnd;
    }

    public String getPromQueryCmdIdent() {
      return promQueryCmdIdent;
    }

    public String getNode19Cpus() {
      return node19Cpus;
    }

    public String getNode20Cpus() {
      return node20Cpus;
    }

    public static class Builder {
      private String promUser_;
      private String promPassword_;
      private String promRestEnd_;
      private String promQueryCmdIdent_;
      private String node19Cpus_;
      private String node20Cpus_;

      private Builder() {
      }

      public static Builder getBuilder() {
        return new Builder();
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

      public Builder promQueryCmdIdent(String promQueryCmdIdent_) {
        this.promQueryCmdIdent_ = promQueryCmdIdent_;
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

      public PromMetaFields build() {
        return new PromMetaFields(promUser_, promPassword_,
            promRestEnd_, promQueryCmdIdent_, node19Cpus_, node20Cpus_);
      }
    }
  }

  public static class PromQueryFields {
    private final String startDate;
    private final String endDate;
    private final String timeStep;
    private final String encQueryTemplate;
    private final String node19Id;
    private final String node20Id;

    private PromQueryFields(String startDate, String endDate,
        String timeStep, String encQueryTemplate, String node19Id,
        String node20Id) {
      this.startDate = startDate;
      this.endDate = endDate;
      this.timeStep = timeStep;
      this.encQueryTemplate = encQueryTemplate;
      this.node19Id = node19Id;
      this.node20Id = node20Id;
    }

    public String getStartDate() {
      return startDate;
    }

    public String getEndDate() {
      return endDate;
    }

    public String getTimeStep() {
      return timeStep;
    }

    public String getEncQueryTemplate() {
      return encQueryTemplate;
    }

    public String getNode19Id() {
      return node19Id;
    }

    public String getNode20Id() {
      return node20Id;
    }

    public static class Builder {
      private String startDate_;
      private String endDate_;
      private String timeStep_;
      private String encQueryTemplate_;
      private String node19Id_;
      private String node20Id_;

      private Builder() {
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

      public Builder timeStep(String timeStep_) {
        this.timeStep_ = timeStep_;
        return this;
      }

      public Builder encQueryTemplate(String encQueryTemplate_) {
        this.encQueryTemplate_ = encQueryTemplate_;
        return this;
      }

      public Builder node19Id(String node19Id_) {
        this.node19Id_ = node19Id_;
        return this;
      }

      public Builder node20Id(String node20Id_) {
        this.node20Id_ = node20Id_;
        return this;
      }

      public PromQueryFields build() {
        return new PromQueryFields(startDate_, endDate_,
            timeStep_, encQueryTemplate_, node19Id_, node20Id_);
      }
    }
  }
}
