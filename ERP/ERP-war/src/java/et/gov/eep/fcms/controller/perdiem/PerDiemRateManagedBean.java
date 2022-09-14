/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.perdiem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
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
import et.gov.eep.fcms.entity.perDiem.FmsLuPerdimeRate;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.perDiem.PerdimeRateBeanLocal;
import et.gov.eep.fcms.entity.perDiem.FmsLuAdditionalAmount;
import et.gov.eep.fcms.mapper.perDiem.FmsLuPerdimeRateFacade;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuJobLevels;
import et.gov.eep.hrms.businessLogic.lookup.HrLuJobLevelsBeanLocal;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.organization.HrJobTypes;

/**
 *
 * @author Mubarek jebel
 */
@Named(value = "perDiemRateManagedBean")
@ViewScoped
public class PerDiemRateManagedBean implements Serializable {

//<editor-fold defaultstate="collapsed" desc=" Inject and @EJB">
    //Inject entities
    @Inject
    SessionBean SessionBean;
    @Inject
    FmsLuPerdimeRate fmsLuPerdimeRate;
    @Inject
    HrEmployees employees;
    @Inject
    HrLuJobLevels hrLuJobLevels;
    @Inject
    private FmsLuAdditionalAmount additionalAmount;
    @Inject
    HrLuGrades hrLuGrades;
    //Inject @EJB
    @EJB
    private HrEmployeeBeanLocal hrEmployeeBean;
    @EJB
    private PerdimeRateBeanLocal perdimeRateBeanLocal;
    @EJB
    private HrLuJobLevelsBeanLocal hrLuJobLevelsBeanLocal;
    @EJB
    FmsLuPerdimeRateFacade fmsLuPerdimeRateFacade;
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    List<HrEmployees> employeeGList;
    List<HrLuJobLevels> employeelList = new ArrayList<>();
    List<HrJobTypes> jobTypeses;
    List<HrLuGrades> hrJobGradesList;
    List<FmsLuPerdimeRate> fmsPerdRatelist;
    private String saveorUpdateBundle = "Save";
    List<FmsLuAdditionalAmount> addtinalList;
    List<FmsLuPerdimeRate> list = new ArrayList<>();
    DataModel<FmsLuPerdimeRate> perdimeRateDModel;
    boolean disable = false;
    Integer joblevel;
    FmsLuPerdimeRate selectLevel;
    private boolean disableBtnCreate;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreatePerDiem = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-plus";
    int updateStatus = 0;
//</editor-fold>

    public PerDiemRateManagedBean() {
    }
//<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">

    public FmsLuPerdimeRate getSelectLevel() {
        return selectLevel;
    }

    public void setSelectLevel(FmsLuPerdimeRate selectLevel) {
        this.selectLevel = selectLevel;
    }

    public boolean getDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public FmsLuPerdimeRate getFmsLuPerdimeRate() {
        if (fmsLuPerdimeRate == null) {
            fmsLuPerdimeRate = new FmsLuPerdimeRate();
        }
        return fmsLuPerdimeRate;
    }

    public void setFmsLuPerdimeRate(FmsLuPerdimeRate fmsLuPerdimeRate) {
        this.fmsLuPerdimeRate = fmsLuPerdimeRate;
    }

    public HrEmployees getEmployees() {
        if (employees == null) {
            employees = new HrEmployees();
        }
        return employees;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public void setEmployees(HrEmployees employees) {
        this.employees = employees;
    }

    public HrLuGrades getHrLuGrades() {
        if (hrLuGrades == null) {
            hrLuGrades = new HrLuGrades();
        }
        return hrLuGrades;
    }

    public void setHrLuGrades(HrLuGrades hrLuGrades) {
        this.hrLuGrades = hrLuGrades;
    }

    public List<HrEmployees> getEmployeeGList() {
        if (employeeGList == null) {
            employeeGList = new ArrayList<>();
        }
        return employeeGList;
    }

    public void setEmployeeGList(List<HrEmployees> employeeGList) {
        this.employeeGList = employeeGList;
    }

    public List<HrLuGrades> getHrJobGradesList() {
        if (hrJobGradesList == null) {
            hrJobGradesList = new ArrayList<>();
        }
        return hrJobGradesList;
    }

    public void setHrJobGradesList(List<HrLuGrades> hrJobGradesList) {
        this.hrJobGradesList = hrJobGradesList;
    }

    public HrLuJobLevels getHrLuJobLevels() {
        if (hrLuJobLevels == null) {
            hrLuJobLevels = new HrLuJobLevels();
        }
        return hrLuJobLevels;
    }

    public void setHrLuJobLevels(HrLuJobLevels hrLuJobLevels) {
        this.hrLuJobLevels = hrLuJobLevels;
    }

    public List<FmsLuPerdimeRate> getFmsPerdRatelist() {
        if (fmsPerdRatelist == null) {
            fmsPerdRatelist = new ArrayList<>();
        }
        return fmsPerdRatelist;
    }

    public void setFmsPerdRatelist(List<FmsLuPerdimeRate> fmsPerdRatelist) {
        this.fmsPerdRatelist = fmsPerdRatelist;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isRenderPnlCreatePerDiem() {
        return renderPnlCreatePerDiem;
    }

    public void setRenderPnlCreatePerDiem(boolean renderPnlCreatePerDiem) {
        this.renderPnlCreatePerDiem = renderPnlCreatePerDiem;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public DataModel<FmsLuPerdimeRate> getPerdimeRateDModel() {
        if (perdimeRateDModel == null) {
            perdimeRateDModel = new ListDataModel<>();
        }
        return perdimeRateDModel;
    }

    public void setPerdimeRateDModel(DataModel<FmsLuPerdimeRate> perdimeRateDModel) {
        this.perdimeRateDModel = perdimeRateDModel;
    }
        //search additional list
    public List<FmsLuAdditionalAmount> getAddtinalList() {
        return addtinalList = perdimeRateBeanLocal.findAddtionalList(additionalAmount);
    }

    public void setAddtinalList(List<FmsLuAdditionalAmount> addtinalList) {
        this.addtinalList = addtinalList;
    }

    //search list of level
    public List<HrLuJobLevels> getEmployeelList() {
        return employeelList = perdimeRateBeanLocal.listoflevel(hrLuJobLevels);
    }

    public void setEmployeelList(List<HrLuJobLevels> employeelList) {
        this.employeelList = employeelList;
    }
//</editor-fold>

    //select event
    public void selectLevelRow(SelectEvent event) {
        disable = true;
        fmsLuPerdimeRate = (FmsLuPerdimeRate) event.getObject();
        hrLuJobLevels = fmsLuPerdimeRate.getJobLevelId();
        hrLuGrades = fmsLuPerdimeRate.getJobGradeId();
        hrJobGradesList = new ArrayList<>();
        hrJobGradesList.add(hrLuGrades);
        renderPnlCreatePerDiem = true;
        renderPnlManPage = false;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updateStatus = 1;
        saveorUpdateBundle = "Update";
    }

    //value change event
    public void getGradeListJoined(ValueChangeEvent e) {
        try {
            perdimeRateDModel = new ListDataModel<>();
            hrLuJobLevels = (HrLuJobLevels) e.getNewValue();
            joblevel = hrLuJobLevels.getId().intValue();
            jobTypeses = perdimeRateBeanLocal.findByJobLevelIdJoined1(hrLuJobLevels);
            if (jobTypeses.isEmpty()) {
                JsfUtil.addFatalMessage("No data found!");
            }
        } catch (Exception ex) {
        }
    }

        //value change event for grade selection
    public void onSelectGrade(ValueChangeEvent e) {
        try {
            perdimeRateDModel = new ListDataModel<>();
            hrLuGrades = (HrLuGrades) e.getNewValue();
        } catch (Exception ex) {
        }
    }

         //value change event for change level
    public void onChangeLevel(ValueChangeEvent event) {
        try {
            hrLuJobLevels = (HrLuJobLevels) event.getNewValue();
            joblevel = hrLuJobLevels.getId().intValue();
            hrJobGradesList = perdimeRateBeanLocal.findByJobLevelId1(hrLuJobLevels);
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to Process. Try Again Relaodin the PAge.");
        }
    }

    //save perdiem
    public void savePerDiemRate() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(SessionBean.getUserName(), "savePerDiemRate", dataset)) {
            fmsLuPerdimeRate.setJobGradeId(hrLuGrades);
            fmsLuPerdimeRate.setJobLevelId(hrLuJobLevels);
            if (updateStatus == 1) {
                perdimeRateBeanLocal.edit(fmsLuPerdimeRate);
                clearPage();
                JsfUtil.addSuccessMessage("Updated Successful.");
                disable = false;
                updateStatus = 0;
                saveorUpdateBundle = "Save";
            } else {//on Save
                if (fmsLuPerdimeRate.getJobGradeId() == null & fmsLuPerdimeRate.getJobLevelId() == null) {
                    JsfUtil.addFatalMessage("Please, Select Level and Grade");
                }
                if (fmsLuPerdimeRateFacade.search(fmsLuPerdimeRate) == null) {
                    perdimeRateBeanLocal.Creat(fmsLuPerdimeRate);
                    clearPage();
                    JsfUtil.addSuccessMessage("Saved Successfully.");
                } else {
                    JsfUtil.addFatalMessage("The Job Grade is Already Registered.");
                }
            }
        } else {
            JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
            EventEntry eventEntry = new EventEntry();
            eventEntry.setSessionId(SessionBean.getSessionID());
            eventEntry.setUserId(SessionBean.getUserId());
            QName qualifiedName = new QName("", "project");
            JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
            eventEntry.setUserLogin(test);
            security.addEventLog(eventEntry, dataset);
        }
    }

//search
    public void searchPRateByParameter() {
        try {
            list = new ArrayList<>();
            //search all
            if ((hrLuJobLevels == null) && (hrLuGrades == null)) {
                list = perdimeRateBeanLocal.findAll();
                //findByLevel
            } else if (hrLuJobLevels != null && hrLuGrades == null) {
                list = perdimeRateBeanLocal.findPRByLevel(hrLuJobLevels.getId());
                //findByLevelAndGrade
            } else if (hrLuJobLevels != null && !(hrLuGrades == null)) {
                list = perdimeRateBeanLocal.findPRByLevelAndGrade(hrLuJobLevels.getId(), hrLuGrades);
            } else {
                JsfUtil.addFatalMessage("No data found!");
            }
            perdimeRateDModel = new ListDataModel<>(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //clear
    private void clearPage() {
        fmsLuPerdimeRate = new FmsLuPerdimeRate();
        hrLuJobLevels = new HrLuJobLevels();
        hrLuGrades = new HrLuGrades();
        disable = false;
        updateStatus = 0;
        saveorUpdateBundle = "Save";
    }

    //create and search render
    public void createNewPerDiem() {
        clearPage();
        saveorUpdateBundle = "Save";
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreatePerDiem = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreatePerDiem = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
}
