package cz.muni.fi.pv243.et.controller;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@SessionScoped
public class NavigationHolder implements Serializable {

    private String backUrl;

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }
}
