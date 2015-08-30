package pack.wolf.com.pifi.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AccessToken implements Serializable {

    @SerializedName(value = "access_token")
    private String accessToken;

    @SerializedName(value = "token_type")
    private String tokenType;

    @SerializedName(value = "userId")
    private String userId;

    public String getAccessToken() {
        return accessToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
