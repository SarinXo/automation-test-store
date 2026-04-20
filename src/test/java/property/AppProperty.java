package property;

import java.util.Map;

public class AppProperty {

    private Map<String, String> url;
    private Integer timeout;

    public Map<String, String> getUrl() {
        return url;
    }

    public void setUrl(Map<String, String> url) {
        this.url = url;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

}
