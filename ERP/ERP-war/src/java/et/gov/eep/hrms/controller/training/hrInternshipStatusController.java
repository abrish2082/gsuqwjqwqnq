/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.training;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct; 
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.training.HrInternshipStudentDetailsBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrTdStatusBeanLocal;
import et.gov.eep.hrms.entity.training.HrTdIspStudentDetails;
import et.gov.eep.hrms.entity.training.HrTdIspStudents;

/**
 *
 * @author insa
 */
@Named(value = "hrInternshipStatusController")
@ViewScoped
public class hrInternshipStatusController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity, bussiness logic & Variables">
    @Inject
    HrTdIspStudentDetails hrTdIspStudentDetails;

    @Inject
    HrTdIspStudents hrTdIspStudents;

    @Inject
    SessionBean sessionBeanLocal;

    @EJB
    HrTdStatusBeanLocal hrTdStatusBeanLocal;

    @EJB
    HrInternshipStudentDetailsBeanLocal hrInternDetailsBeanLocal;

    int checker = 0;
    private String SaveOrUpdateButton = "Save";
    private String headerTitle = "Internship Status";
    private String saveorUpdateBundle = "Update";
    private String icone = "ui-icon-document";
    private List<HrTdIspStudents> semisters;
    private List<HrTdIspStudents> budgetYear;
    private List<HrTdIspStudentDetails> placedStudentList = new ArrayList<>();
    DataModel<HrTdIspStudentDetails> statusdetailmodel;
//</editor-fold>

    @PostConstruct
    public void init() {
        budgetYear = hrTdStatusBeanLocal.findYear();
    }
    //<editor-fold defaultstate="collapsed" desc="Getters  & setters">

    public int getChecker() {
        return checker;
    }

    public void setChecker(int checker) {
        this.checker = checker;
    }

    public List<HrTdIspStudents> getSemisters() {
        if (semisters == null) {
            semisters = new ArrayList<>();
        }
        return semisters;
    }

    public void setSemisters(List<HrTdIspStudents> semisters) {
        this.semisters = semisters;
    }

    public List<HrTdIspStudents> getBudgetYear() {
        return budgetYear;
    }

    public void setBudgetYear(List<HrTdIspStudents> budgetYear) {
        this.budgetYear = budgetYear;
    }

    public List<HrTdIspStudentDetails> getPlacedStudentList() {
        return placedStudentList;
    }

    public void setPlacedStudentList(List<HrTdIspStudentDetails> placedStudentList) {
        this.placedStudentList = placedStudentList;
    }

    public DataModel<HrTdIspStudentDetails> getStatusdetailmodel() {
        if (statusdetailmodel == null) {
            statusdetailmodel = new ArrayDataModel<>();
        }
        return statusdetailmodel;
    }

    public void setStatusdetailmodel(DataModel<HrTdIspStudentDetails> statusdetailmodel) {
        this.statusdetailmodel = statusdetailmodel;
    }

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public HrTdIspStudentDetails getHrTdIspStudentDetails() {
        if (hrTdIspStudentDetails == null) {
            hrTdIspStudentDetails = new HrTdIspStudentDetails();
        }
        return hrTdIspStudentDetails;
    }

    public void setHrTdIspStudentDetails(HrTdIspStudentDetails hrTdIspStudentDetails) {
        this.hrTdIspStudentDetails = hrTdIspStudentDetails;
    }

    public HrTdIspStudents getHrTdIspStudents() {
        if (hrTdIspStudents == null) {
            hrTdIspStudents = new HrTdIspStudents();
        }
        return hrTdIspStudents;
    }

    public void setHrTdIspStudents(HrTdIspStudents hrTdIspStudents) {
        this.hrTdIspStudents = hrTdIspStudents;
    }

    //</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc="Main Methods">
    public void findByYear(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            Integer years = Integer.parseInt(event.getNewValue().toString());
            semisters = hrTdStatusBeanLocal.findByYear(years);
        }
    }

    public void semisiter_vcl(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            hrTdIspStudents = (HrTdIspStudents) event.getNewValue();
            placedStudentList = hrTdStatusBeanLocal.findBysemister(hrTdIspStudents);
            recreatDatamodel();

        }
    }

    public void recreatDatamodel() {
        statusdetailmodel = null;
        statusdetailmodel = new ListDataModel(new ArrayList<>(hrTdIspStudents.getHrTdIspStudentDetailsList()));
    }

    public void saveInternshipStatus() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveInternshipStatus", dataset)) {
                try {
                    if ((!(statusdetailmodel.getRowCount() > 0))) {
                        JsfUtil.addFatalMessage("Data Table Should be filled");
                    } else {
                        List<HrTdIspStudentDetails> hrTdIspStudentsList = hrTdIspStudents.getHrTdIspStudentDetailsList();
                        for (HrTdIspStudentDetails studDetail : hrTdIspStudentsList) {
                            if (!studDetail.getStatus().equals(3) && !studDetail.getStatus().equals(-3)) {
                                setChecker(1);
                            }
                        }
                        editStudDetail(hrTdIspStudents);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JsfUtil.addFatalMessage("Error Occurred");
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveInternshipStatus");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void editStudDetail(HrTdIspStudents hrTdIspStudents) {
        if (checker != 1) {
            hrInternDetailsBeanLocal.update(hrTdIspStudents);
            JsfUtil.addSuccessMessage("Modified Sucessfully");
            checker = 0;
            clearInternshipStatus();
        } else {
            JsfUtil.addFatalMessage("Please Select Status");
            checker = 0;
        }

    }

    public void clearInternshipStatus() {
        statusdetailmodel = new ArrayDataModel<>();
        hrTdIspStudents = new HrTdIspStudents();
    }

    public void onRowSelect(SelectEvent event) {
        hrTdIspStudentDetails = ((HrTdIspStudentDetails) event.getObject());
        hrTdIspStudentDetails.setStatus(hrTdIspStudentDetails.getStatus());
        FacesMessage msg = new FacesMessage(hrTdIspStudentDetails.getId() + " is Selected");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    //</editor-fold>
}
