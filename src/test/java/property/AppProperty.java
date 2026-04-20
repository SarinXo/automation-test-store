package property;

import java.util.Map;

public class AppProperty {

    private Map<String, String> pageUrls;
    private Integer timeout;

    public Map<String, String> getPageUrls() {
        return pageUrls;
    }

    public void setPageUrls(Map<String, String> pageUrls) {
        this.pageUrls = pageUrls;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

}
