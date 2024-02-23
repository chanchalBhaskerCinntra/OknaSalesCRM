package com.cinntra.okana.model;

  import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class LogInResponse implements Serializable {
    @SerializedName("odata.metadata")
    @Expose
    private String metadata;
    @SerializedName("SessionId")
    @Expose
    private String SessionId;
    @SerializedName("Version")
    @Expose
    private String Version;
    @SerializedName("SessionTimeout")
    @Expose
    String SessionTimeout;

    public String getMetadata()
    {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getSessionTimeout() {
        return SessionTimeout;
    }

    public void setSessionTimeout(String sessionTimeout) {
        SessionTimeout = sessionTimeout;
    }
}
