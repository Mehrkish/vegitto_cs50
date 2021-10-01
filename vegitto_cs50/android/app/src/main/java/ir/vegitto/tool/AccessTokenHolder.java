package ir.vegitto.tool;

public class AccessTokenHolder {
    private static String accessToken = null;

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        AccessTokenHolder.accessToken = accessToken;
    }

    public static void removeToken() {
        accessToken = null;
    }
}
