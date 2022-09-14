/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.leave;

import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.leave.LeaveSettingBeanLocal;
import et.gov.eep.hrms.entity.leave.HrLeaveSetting;
import et.gov.eep.hrms.entity.leave.HrLuLeaveTypes;
import et.gov.eep.hrms.mapper.leave.HrLuLeaveTypesFacade;

import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author prg
 */
@Named("leaveType")
@ViewScoped
public class LeaveType implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Enrity Injection">
    @Inject
            SessionBean sessionBean;
    @Inject
    private HrLeaveSetting hrLeaveSetting;
    @Inject
            HrLuLeaveTypes hrLuLeaveTypes;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="EJB Declaration">
    @EJB
            HrLuLeaveTypesFacade luLeaveTypesFacade;
    @EJB
            LeaveSettingBeanLocal settingBeanLocal;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Variable declaration">
    boolean renderBalance = false;
    int update = 0;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = true;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-document";
    private String saveOrUpdateButton = "Save";
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter and Setter">
    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }

    public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }
 boolean renderMaximumDate = false;

    public boolean isRenderMaximumDate() {
        return renderMaximumDate;
    }

    public void setRenderMaximumDate(boolean renderMaximumDate) {
        this.renderMaximumDate = renderMaximumDate;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getSaveOrUpdateButton() {
        return saveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String saveOrUpdateButton) {
        this.saveOrUpdateButton = saveOrUpdateButton;
    }

    public boolean isRenderBalance() {
        return renderBalance;
    }

    public void setRenderBalance(boolean renderBalance) {
        this.renderBalance = renderBalance;
    }

    public HrLeaveSetting getHrLeaveSetting() {
        if (hrLeaveSetting == null) {
            hrLeaveSetting = new HrLeaveSetting();
        }
        return hrLeaveSetting;
    }

    public void setHrLeaveSetting(HrLeaveSetting hrLeaveSetting) {
        this.hrLeaveSetting = hrLeaveSetting;
    }

    public HrLuLeaveTypes getHrLuLeaveTypes() {
        if (hrLuLeaveTypes == null) {
            hrLuLeaveTypes = new HrLuLeaveTypes();
        }
        return hrLuLeaveTypes;
    }

    public void setHrLuLeaveTypes(HrLuLeaveTypes hrLuLeaveTypes) {
        this.hrLuLeaveTypes = hrLuLeaveTypes;
    }

//    public SelectItem[] getEmployeTypeList() {
//        return JsfUtil.getSelectItems(employeTypeList(), true);
//    }
//    public SelectItem[] getGender() {
//        return JsfUtil.getSelectItems(gender(), true);
//    }
    public SelectItem[] getLeavetypeList() {
        return JsfUtil.getSelectItems(luLeaveTypesFacade.findAll(), true);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="main methods ">
    
    public void leaveTyp(ValueChangeEvent event) {
        try {
            if (event.getNewValue() != null) {
                saveOrUpdateButton = "Save";
                hrLuLeaveTypes = (HrLuLeaveTypes) event.getNewValue();
                
                hrLeaveSetting = settingBeanLocal.getByLeaveType(hrLuLeaveTypes);
                if (hrLeaveSetting != null) {
                    if (hrLuLeaveTypes.getLeaveName().equalsIgnoreCase("Annual Leave")) {
                        hrLeaveSetting.setToBalance(true);
                        
                    } else {
                        hrLeaveSetting.setToBalance(false);
                    }
                    saveOrUpdateButton = "Update";
                    update = 1;
                } else {
                    hrLeaveSetting = new HrLeaveSetting();
                }
                
            }
        } catch (Exception e) {
            
            JsfUtil.addFatalMessage("Somthing Occured");
        }
        
    }
    
    //<editor-fold defaultstate="collapsed" desc="save method">
    public void saveLeaveSetting() {
        try {
            
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "saveLeaveSetting", dataset)) {
//                 put ur code here...!
                if (hrLeaveSetting.getMinNumOfDays() > hrLeaveSetting.getMaxNumOfDays()) {
                    JsfUtil.addFatalMessage("Minimum Number of Days Shall Not Exceed Maximim Number of days");
                } else {
                    hrLeaveSetting.setProcessedBy(sessionBean.getUserId());
                    settingBeanLocal.saveOrUpdate(hrLeaveSetting);
                    if (update == 0) {
                        JsfUtil.addSuccessMessage("Saved Successfuly.");
                        
                    } else {
                        
                        JsfUtil.addSuccessMessage("Update Successful.");
                    }
                    clearLeaveSetting();
                }
                
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBean.getSessionID());
                eventEntry.setUserId(sessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBean.getUserName());
                eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Somthing Occured on Save");
            
        }
        
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="clear method">
    public void clearLeaveSetting() {
        hrLeaveSetting = null;
        hrLuLeaveTypes = null;
        saveOrUpdateButton = "Save";
        update = 0;
    }
//</editor-fold>
//</editor-fold>
}
