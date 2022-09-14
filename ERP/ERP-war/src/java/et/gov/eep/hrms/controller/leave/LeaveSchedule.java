/* LIVE schedule
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.leave;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.FmsLuBudgetYearBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.leave.HrLeaveScheduleTransferBeanLocal;
import et.gov.eep.hrms.businessLogic.leave.Leave_scheduleLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.leave.HrLeaveSchedule;
import et.gov.eep.hrms.entity.leave.HrLeaveScheduleDet;
import et.gov.eep.hrms.entity.leave.HrLeaveScheduleTransfer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
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

/**
 *
 * @author Desu
 */
@Named(value = "leaveSchedule")
@ViewScoped
public class LeaveSchedule implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="entity injections">
    @Inject
    SessionBean sessionBean;
    @Inject
    private HrLeaveSchedule leaveSchedule;
    @Inject
    private HrEmployees employee;
    @Inject
    private FmsAccountingPeriod accountingPeriod;
    @Inject
    private HrLeaveScheduleDet leaveScheduleDetile;
    @Inject
    private FmsLuBudgetYear fmsLuBudgetYear;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="EJB Declaration">
    @EJB
            FmsLuBudgetYearBeanLocal luBudgetYearBeanLocal;
    @EJB
    private Leave_scheduleLocal leavescheduleBeanLocal;
    @EJB
            HrEmployeeBeanLocal profileBeanLocal;
    @EJB
    private FmsAccountingPeriodBeanLocal accountingPeriodBeanLocal;
    @EJB
    private FmsLuBudgetYearBeanLocal budgetYearBeanLocal;
    @EJB
    private HrLeaveScheduleTransferBeanLocal leaveScheduleBean;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Variable declaration">
    private DataModel<HrLeaveScheduleDet> leaveScheduledet;
    String ActiveYear;
    int update = 0;
    boolean btnStatus = true;
    boolean btnStatuss = false;
    List<FmsLuBudgetYear> budgetYears = new ArrayList<>();
    private String saveUpdate = "Save";
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = true;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-document";
    private String SaveOrUpdateButton = "Save";
    private String AddOrModify = "Add";
    Set<String> checkCourse = new HashSet<>();
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
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
    
    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }
    
    public String getAddOrModify() {
        return AddOrModify;
    }
    
    public void setAddOrModify(String AddOrModify) {
        this.AddOrModify = AddOrModify;
    }
    
    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }
    
    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }
    
    public void createNewAdditionalAmount() {
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            //createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        }
    }
    
    public boolean isBtnStatuss() {
        return btnStatuss;
    }
    
    public void setBtnStatuss(boolean btnStatuss) {
        this.btnStatuss = btnStatuss;
    }
    
    public boolean isBtnStatus() {
        return btnStatus;
    }
    
    public void setBtnStatus(boolean btnStatus) {
        this.btnStatus = btnStatus;
    }
    
    public String getSaveUpdate() {
        return saveUpdate;
    }
    
    public void setSaveUpdate(String saveUpdate) {
        this.saveUpdate = saveUpdate;
    }
    
    public HrEmployees getEmployee() {
        if (employee == null) {
            employee = new HrEmployees();
        }
        return employee;
    }
    
    public void setEmployee(HrEmployees employee) {
        this.employee = employee;
    }
    
    public LeaveSchedule() {
    }
    
    public HrLeaveSchedule getLeaveSchedule() {
        if (leaveSchedule == null) {
            leaveSchedule = new HrLeaveSchedule();
        }
        return leaveSchedule;
    }
    
    public FmsLuBudgetYear getFmsLuBudgetYear() {
        if (fmsLuBudgetYear == null) {
            fmsLuBudgetYear = new FmsLuBudgetYear();
        }
        return fmsLuBudgetYear;
    }
    
    public void setFmsLuBudgetYear(FmsLuBudgetYear fmsLuBudgetYear) {
        this.fmsLuBudgetYear = fmsLuBudgetYear;
    }
    
    public void setLeaveSchedule(HrLeaveSchedule leaveSchedule) {
        this.leaveSchedule = leaveSchedule;
    }
    
    public DataModel<HrLeaveScheduleDet> getLeaveScheduledet() {
        if (leaveScheduledet == null) {
            leaveScheduledet = new ListDataModel<>();
        }
        return leaveScheduledet;
    }
    
    public void setLeaveScheduledet(DataModel<HrLeaveScheduleDet> leaveScheduledet) {
        this.leaveScheduledet = leaveScheduledet;
    }
    
    public HrLeaveScheduleDet getLeaveScheduleDetile() {
        if (leaveScheduleDetile == null) {
            leaveScheduleDetile = new HrLeaveScheduleDet();
        }
        return leaveScheduleDetile;
    }
    
    public void setLeaveScheduleDetile(HrLeaveScheduleDet leaveScheduleDetile) {
        this.leaveScheduleDetile = leaveScheduleDetile;
    }
    
    public void recreateModelDetail() {
        leaveScheduledet = null;
        leaveScheduledet = new ListDataModel(new ArrayList<>(leaveSchedule.getHrLeaveScheduleDetList()));
        
    }
    
    public SelectItem[] getLeavetYearList() {
        budgetYears = luBudgetYearBeanLocal.getLuBudgetYear();
        return JsfUtil.getSelectItems(budgetYears, true);
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Main methods ">
    public List<HrEmployees> searchEmployeeById(String employeeId) {
        employee = new HrEmployees();
        List<HrEmployees> registeredEmployee = null;
        employee.setEmpId(employeeId);
        registeredEmployee = profileBeanLocal.SearchByEmpId(employee);
        return registeredEmployee;
        
    }
    
    public ArrayList<String> getlistofMonth() {
        ArrayList<String> position = new ArrayList<>();
        position.add("september");
        position.add("october");
        position.add("May");
        return position;
    }
    
    public void addToTableLeaveScheduleRequest() {
        
        if (checkCourse.contains(leaveScheduleDetile.getLeaveMonth())) {
            JsfUtil.addFatalMessage("Duplicate Entry. Try again!");
        } else {
            
            leaveSchedule.addToDetail(leaveScheduleDetile);
            checkCourse.add(leaveScheduleDetile.getLeaveMonth());
            
            recreateModelDetail();
            leaveScheduleDetile = null;
            AddOrModify = "Add";
        }
    }
    
    public void getLeaveYear(ValueChangeEvent event) {
        try {
            if (event.getNewValue() != null) {
                fmsLuBudgetYear = (FmsLuBudgetYear) event.getNewValue();
                if (fmsLuBudgetYear != null && employee != null) {
                    
                    leaveSchedule = leavescheduleBeanLocal.findByBudgetYear(fmsLuBudgetYear, employee);
                    if (leaveSchedule != null) {
                        recreateModelDetail();
                        update = 1;
                    } else {
                        leaveSchedule = new HrLeaveSchedule();
                    }
                    
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
        }
        
    }
    
    public void saveLeaveScheduleRequest() {
        try {
            
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "saveLeaveScheduleRequest", dataset)) {
                leaveSchedule.setEmployeeId(employee);
                leaveSchedule.setLeaveYearId(fmsLuBudgetYear);
                leavescheduleBeanLocal.saveOrUpdate(leaveSchedule);
                if (update == 1) {
                    JsfUtil.addSuccessMessage("Updated Successfully");
                } else {
                    JsfUtil.addSuccessMessage("Submitted Successfully");
                }
                clearLeaveScheduleRequest();
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
            e.printStackTrace();
            JsfUtil.addFatalMessage("Something occured on save.");
        }
    }
    
    public void getEmployeeInfo(SelectEvent event) {
        String emplName = event.getObject().toString();
        
        HrEmployees employeeFirst = new HrEmployees();
        employeeFirst.setEmpId(emplName);
        
        employee = profileBeanLocal.getByEmpId(employee);
        leaveSchedule = new HrLeaveSchedule();
        leaveSchedule.setEmployeeId(employee);
        
    }
    
    public void populateLeaveSchedule() {
        leaveScheduleDetile = getLeaveScheduledet().getRowData();
        checkCourse.remove(leaveScheduleDetile.getLeaveMonth());
        AddOrModify = "Modify";
        
    }
    
    public void loadLeaveYear() {
        accountingPeriod = accountingPeriodBeanLocal.getCurretActivePeriod();
        accountingPeriod.setActivePeriod(accountingPeriod.getActivePeriod());
        leaveSchedule.setLeaveYearId(accountingPeriod.getLuBudgetYearId());
    }
    
    public void clearLeaveScheduleRequest() {
        
        leaveSchedule = null;
        leaveScheduledet = null;
        employee = null;
        update = 0;
        saveUpdate = "Save";
        fmsLuBudgetYear = null;
    }
//</editor-fold>
}
