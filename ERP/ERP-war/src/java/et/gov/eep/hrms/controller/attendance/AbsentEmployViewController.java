/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.attendance;

import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.entity.attendance.AbsentEmployeesView;
import et.gov.eep.hrms.mapper.attendace.AbsentEmployeesViewFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author meles
 */
@Named(value = "absentEmployViewController")
@ViewScoped
public class AbsentEmployViewController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Initializations">
    @EJB
            AbsentEmployeesViewFacade AbsentEmployeesViewFacade;
    
    private DataModel<AbsentEmployeesView> absentEmployeeView;
    
    String month;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="post Construct">
    @PostConstruct
    public void init() {
        absentEmployeeView = new ListDataModel(AbsentEmployeesViewFacade.findReport());
        calculate();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public String getMonth() {
        return month;
    }
    
    public void setMonth(String month) {
        this.month = month;
    }
    
    public DataModel<AbsentEmployeesView> getAbsentEmployeeView() {
        return absentEmployeeView;
    }
    
    public void setAbsentEmployeeView(DataModel<AbsentEmployeesView> absentEmployeeView) {
        this.absentEmployeeView = absentEmployeeView;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Main methods">
    public void calculate() {
        String Shday[] = StringDateManipulation.toDayInEc().split("-");
        Integer month1 = Integer.parseInt(Shday[1]);
        if (month1.equals(1)) {
            month = "September";
        } else if (month1.equals(2)) {
            month = "October";
        } else if (month1.equals(3)) {
            month = "November";
        } else if (month1.equals(4)) {
            month = "December";
        } else if (month1.equals(5)) {
            month = "January";
        } else if (month1.equals(6)) {
            month = "February";
        } else if (month1.equals(7)) {
            month = "March";
        } else if (month1.equals(8)) {
            month = "April";
        } else if (month1.equals(9)) {
            month = "May";
        } else if (month1.equals(10)) {
            month = "June";
        } else if (month1.equals(11)) {
            month = "July";
        } else if (month1.equals(12)) {
            month = "Augest";
        }
        
    }
    
    public AbsentEmployViewController() {
    }
//</editor-fold>
}
