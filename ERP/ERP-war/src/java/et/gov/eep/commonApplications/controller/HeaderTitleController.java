/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.controller;

import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Sadik
 */
@Named(value = "headerTitleController")
@ViewScoped
public class HeaderTitleController implements Serializable{
private  String titleString;
    /**
     * Creates a new instance of HeaderTitleController
     */
    public HeaderTitleController() {
    }
    
    public void getRequest(ServletRequest request){
        System.out.println("=inside method=====");
        HttpServletRequest request1=(HttpServletRequest)request;
        String[] split = request1.getServletPath().split("/");
        int length=split.length-1;
        setTitleString(split[length]);
        System.out.println("===title===="+getTitleString());
        
    }

    public String getTitleString() {
        return titleString;
    }

    public void setTitleString(String titleString) {
        this.titleString = titleString;
    }
    
    
}
     