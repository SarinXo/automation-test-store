package property;

import java.util.List;

public class AppProperty {

    private List<PageInfoProperty> pages;
    private Integer timeout;

    public List<PageInfoProperty> getPages() {
        return pages;
    }

    public void setPages(List<PageInfoProperty> pages) {
        this.pages = pages;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

}
