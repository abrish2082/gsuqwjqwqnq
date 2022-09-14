/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.termination;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName; 
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.termination.ClearanceSettingBeanLocal;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.termination.HrClearanceSetting;
import et.gov.eep.hrms.integration.HrDepartmentsIntegrationBeanLocal;

/**
 *
 * @author Ob
 */
@Named(value = "clearanceSettingController")
@ViewScoped
public class ClearanceSettingController implements Serializable {

    @Inject
    HrClearanceSetting hrClearanceSetting;
    @Inject
    HrDepartments hrDepartment;
    @Inject
    SessionBean sessionBeanLocal;

    @EJB
    ClearanceSettingBeanLocal clearanceSettingBeanLocal;
    @EJB
    HrDepartmentsIntegrationBeanLocal departmentBeanLocal;

    private DataModel<HrClearanceSetting> clearanceSettingDataModel;
    private List<HrClearanceSetting> clearanceSettingList;
    private HrClearanceSetting selectedDepartment[];
    List< HrDepartments> deptList = new ArrayList<>();
    int updateStatus = 0;

    public ClearanceSettingController() {
    }

    @PostConstruct
    public void init() {
        clearanceSettingList = clearanceSettingBeanLocal.findAll();
        if (clearanceSettingList != null && clearanceSettingList.size() > 0) {
            updateStatus = 1;
            clearanceSettingDataModel = null;
            clearanceSettingDataModel = new ListDataModel(new ArrayList<>(clearanceSettingList));
        } else {
            deptList = departmentBeanLocal.findAllDepartmentInfo();
            for (int i = 0; i < deptList.size(); i++) {
                HrClearanceSetting tempObj = new HrClearanceSetting();
                tempObj.setDeptId(deptList.get(i));
                clearanceSettingList.add(tempObj);
                tempObj = null;
            }
            clearanceSettingDataModel = null;
            clearanceSettingDataModel = new ListDataModel(new ArrayList<>(clearanceSettingList));
        }

    }

    //<editor-fold defaultstate="collapsed" desc="getter setter">
    public HrClearanceSetting getHrClearanceSetting() {
        if (hrClearanceSetting == null) {
            hrClearanceSetting = new HrClearanceSetting();
        }
        return hrClearanceSetting;
    }

    public void setHrClearanceSetting(HrClearanceSetting hrClearanceSetting) {
        this.hrClearanceSetting = hrClearanceSetting;
    }

    public HrDepartments getHrDepartment() {
        if (hrDepartment == null) {
            hrDepartment = new HrDepartments();
        }
        return hrDepartment;
    }

    public void setHrDepartment(HrDepartments hrDepartment) {
        this.hrDepartment = hrDepartment;
    }

    public DataModel<HrClearanceSetting> getClearanceSettingDataModel() {
        if (clearanceSettingDataModel == null) {
            clearanceSettingDataModel = new ListDataModel();
        }
        return clearanceSettingDataModel;
    }

    public void setClearanceSettingDataModel(DataModel<HrClearanceSetting> clearanceSettingDataModel) {
        this.clearanceSettingDataModel = clearanceSettingDataModel;
    }

    public List<HrClearanceSetting> getClearanceSettingList() {
        return clearanceSettingList;
    }

    public void setClearanceSettingList(List<HrClearanceSetting> clearanceSettingList) {
        this.clearanceSettingList = clearanceSettingList;
    }

    public HrClearanceSetting[] getSelectedDepartment() {
        return selectedDepartment;
    }

    public void setSelectedDepartment(HrClearanceSetting[] selectedDepartment) {
        this.selectedDepartment = selectedDepartment;
    }
    //</editor-fold>

    public void recreateDataModel() {
        clearanceSettingDataModel = null;
        clearanceSettingDataModel = new ListDataModel(new ArrayList<>(hrClearanceSetting.getId()));
    }

    public void selectedDep(ValueChangeEvent event) {
        try {
            String tempo = event.getNewValue().toString();
            hrClearanceSetting = clearanceSettingDataModel.getRowData();
            if ("true".equals(tempo)) {
                hrClearanceSetting.setStatus(true);
            } else {
                hrClearanceSetting.setStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertPriority(ValueChangeEvent event) {
        try {
            String tempo = event.getNewValue().toString();
            hrClearanceSetting = clearanceSettingDataModel.getRowData();
            if ("true".equals(tempo)) {
                hrClearanceSetting.setPriority(hrClearanceSetting.getPriority());
            } else {
                hrClearanceSetting.setPriority(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveClearanceSetting() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveClearanceSetting", dataset)) {
                try {
                    if (updateStatus == 0) {
                        int j = clearanceSettingList.size();
                        for (int i = 0; i < j; i++) {
                            hrClearanceSetting = clearanceSettingList.get(i);
                            clearanceSettingBeanLocal.save(hrClearanceSetting);
                            hrClearanceSetting.setDeptId(hrDepartment);
                        }
                        JsfUtil.addSuccessMessage("Saved Successfully.");
                    } else {
                        int j = clearanceSettingList.size();
                        for (int i = 0; i < j; i++) {
                            hrClearanceSetting = clearanceSettingList.get(i);
                            clearanceSettingBeanLocal.edit(hrClearanceSetting);
                        }
                        JsfUtil.addSuccessMessage("Updated Successfully.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JsfUtil.addFatalMessage("Error occure while save update");
                }

            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveClearanceSetting");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
