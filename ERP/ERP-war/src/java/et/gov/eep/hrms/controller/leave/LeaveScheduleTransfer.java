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
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

/**
 *
 * @author Desu
 */
@Named(value = "LeaveScheduleTransfer")
@ViewScoped
public class LeaveScheduleTransfer implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity Injection">
    @Inject
    private HrLeaveSchedule leaveSchedule;
    @Inject
    private HrLeaveScheduleDet leaveScheduleDetile;
    @Inject
    private FmsLuBudgetYear fmsLuBudgetYear;
    @Inject
    private HrLeaveScheduleTransfer scheduleTransfer;
    @Inject
    private HrEmployees employee;
    @Inject
    private FmsAccountingPeriod accountingPeriod;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="EJB Declaration">
    @EJB
    FmsLuBudgetYearBeanLocal luBudgetYearBeanLocal;
    @EJB
    private Leave_scheduleLocal LeavescheduleLocal;
    @EJB
    HrEmployeeBeanLocal profileBeanLocal;
    @EJB
    private FmsAccountingPeriodBeanLocal accountingPeriodBeanLocal;
    @EJB
    private FmsLuBudgetYearBeanLocal budgetYearBeanLocal;
    @EJB
    private HrLeaveScheduleTransferBeanLocal scheduleTransferBean;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Variable declaration">
    private DataModel<HrLeaveScheduleDet> leaveScheduledet;
    String ActiveYear;
    int noOfSchReq, noOfTrReq;
    int update = 0;
    boolean diplayTransfer = false;
    boolean showTransfer = true;
    boolean btnStatus = true;
    boolean btnStatuss = false;
    private String saveUpdate = "Create";
    List scheduleList = new ArrayList<HrLeaveSchedule>();
    List transferList = new ArrayList<HrLeaveScheduleTransfer>();
    List<FmsLuBudgetYear> budgetYears = new ArrayList<>();
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter and setter">
    public int getNoOfSchReq() {
        return noOfSchReq;
    }

    public void setNoOfSchReq(int noOfSchReq) {
        this.noOfSchReq = noOfSchReq;
    }

    public int getNoOfTrReq() {
        return noOfTrReq;
    }

    public void setNoOfTrReq(int noOfTrReq) {
        this.noOfTrReq = noOfTrReq;
    }

    public boolean isShowTransfer() {
        return showTransfer;
    }

    public void setShowTransfer(boolean showTransfer) {
        this.showTransfer = showTransfer;
    }

    public boolean isDiplayTransfer() {
        return diplayTransfer;
    }

    public void setDiplayTransfer(boolean diplayTransfer) {
        this.diplayTransfer = diplayTransfer;
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

    public LeaveScheduleTransfer() {
    }

    public HrLeaveScheduleTransfer getScheduleTransfer() {
        if (scheduleTransfer == null) {
            scheduleTransfer = new HrLeaveScheduleTransfer();
        }
        return scheduleTransfer;
    }

    public void setScheduleTransfer(HrLeaveScheduleTransfer scheduleTransfer) {
        this.scheduleTransfer = scheduleTransfer;
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

    public SelectItem[] newTransferReqList() {
        if (transferList.size() > 0) {
            return JsfUtil.getSelectItems(transferList, true);
        } else {
            return null;
        }

    }

    public List getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List scheduleList) {
        this.scheduleList = scheduleList;
    }

    public List getTransferList() {
        return transferList;
    }

    public void setTransferList(List transferList) {
        this.transferList = transferList;
    }

    public ArrayList<String> getlistofMonth() {
        ArrayList<String> position = new ArrayList<>();
        position.add("september");
        position.add("october");
        position.add("May");
        return position;
    }

    public SelectItem[] newScheduleReqList() {
        if (scheduleList.size() > 0) {
            return JsfUtil.getSelectItems(scheduleList, true);
        } else {
            return null;
        }
    }

    public SelectItem[] getLeavetYearList() {
        budgetYears = luBudgetYearBeanLocal.getLuBudgetYear();
        return JsfUtil.getSelectItems(budgetYears, true);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="post construct">
    @PostConstruct
    public void init() {
        transferList = new ArrayList<HrLeaveScheduleTransfer>();
        scheduleList = new ArrayList<HrLeaveSchedule>();
        
        transferList = scheduleTransferBean.findAllRequests();
        scheduleList = LeavescheduleLocal.loadScheduleRequests();
        
        if (transferList != null && scheduleList != null) {
            noOfSchReq = scheduleList.size();
            noOfTrReq = transferList.size();
        }
        
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="main methods">
    public List<HrEmployees> searchEmployeeById(String employeeId) {
        employee = new HrEmployees();
        List<HrEmployees> reqEmp = null;
        
        employee.setEmpId(employeeId);
        
        reqEmp = profileBeanLocal.searchByEmpById(employee);
        return reqEmp;
    }
    
    public void addToTable() {
        leaveScheduleDetile.setDescription(leaveScheduleDetile.getDescription());
        leaveScheduleDetile.setLeaveMonth(leaveScheduleDetile.getLeaveMonth());
        leaveSchedule.addToDetail(leaveScheduleDetile);
        recreateModelDetail();
        leaveScheduleDetile = null;
        
    }
    
    public void changedTransfer(ValueChangeEvent event) {
        
        if (!event.getNewValue().toString().isEmpty()) {
            diplayTransfer = true;
            showTransfer = false;
            saveUpdate = "update";
            System.err.println("diplayTransfer======" + diplayTransfer);
            String temp[] = event.getNewValue().toString().split("--");
            int trId = Integer.parseInt(temp[0]);
            System.err.println("ter==" + trId);
            
            scheduleTransfer = scheduleTransferBean.find(trId);
            leaveSchedule = scheduleTransfer.getScheduleId();
            recreateModelDetail();
        }
    }
    
    public void changedSchedule(ValueChangeEvent event) {
        
        if (!event.getNewValue().toString().isEmpty()) {
            diplayTransfer = false;
            showTransfer = true;
            saveUpdate = "update";
            System.err.println("diplayTransfer======" + diplayTransfer);
            String temp[] = event.getNewValue().toString().split("--");
            int schId = Integer.parseInt(temp[0]);
            System.err.println("schId===" + schId);
            leaveSchedule = LeavescheduleLocal.findById(schId);
            recreateModelDetail();
            
        }
    }
    
    public void changedDecision(ValueChangeEvent event) {
        
        if (!event.getNewValue().toString().isEmpty()) {
            if (diplayTransfer) {
                if ("Approved".equals(event.getNewValue().toString())) {
                    scheduleTransfer.setStatus("1");
                } else if ("Rejected".equals(event.getNewValue().toString())) {
                    scheduleTransfer.setStatus("-1");
                }
            } else {
                leaveSchedule.setStatus(event.getNewValue().toString());
            }
            
        }
    }
    
    public void getLeaveYear(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuBudgetYear.setBudgetYear(event.getNewValue().toString());
            fmsLuBudgetYear = budgetYearBeanLocal.findByBudjetYear(fmsLuBudgetYear);
            if (fmsLuBudgetYear != null) {
                leaveSchedule.setLeaveYearId(fmsLuBudgetYear);
                leaveSchedule = LeavescheduleLocal.findByEmp(leaveSchedule);
                if (leaveSchedule != null && "Pending".equals(leaveSchedule.getStatus())) {
                    btnStatus = true;
                    btnStatuss = false;
                    update = 1;
                    saveUpdate = "Update";
                    recreateModelDetail();
                } else if (leaveSchedule != null && "Approved".equals(leaveSchedule.getStatus())) {
                    btnStatus = false;
                    btnStatuss = true;
                    recreateModelDetail();
                } else {
                    saveUpdate = "Create";
                    update = 0;
                    btnStatus = true;
                    btnStatuss = false;
                    leaveSchedule = new HrLeaveSchedule();
                    leaveSchedule.setLeaveYearId(fmsLuBudgetYear);
                    leaveSchedule.setEmployeeId(employee);
                    leaveScheduledet = null;
                }
            }
        }
    }
    
    public void saveOrUpdate() {
        
        try {
            if (diplayTransfer) {
                if (modifySchedule()) {
                    scheduleTransferBean.edit(scheduleTransfer);
                    JsfUtil.addSuccessMessage("Schedule transfer request updated.");
                    clear();
                }
            } else {
                if (LeavescheduleLocal.edit(leaveSchedule)) {
                    JsfUtil.addSuccessMessage("Schedule request updated.");
                    clear();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            JsfUtil.addSuccessMessage("Unable to save data.");
        }
    }
    
    public boolean modifySchedule() {
        boolean temp = false;
        System.err.println("gebtoal");
        for (int i = 0; i < leaveSchedule.getHrLeaveScheduleDetList().size(); i++) {
//            if (leaveSchedule.getHrLeaveScheduleDetList().get(i).getPriority() == 1) {
//                HrLeaveScheduleDet newObj = new HrLeaveScheduleDet();
//                newObj = leaveSchedule.getHrLeaveScheduleDetList().get(i);
//                System.err.println("the size before " + leaveSchedule.getHrLeaveScheduleDetList().size());
//                leaveSchedule.getHrLeaveScheduleDetList().remove(newObj);
//                System.err.println("the size after " + leaveSchedule.getHrLeaveScheduleDetList().size());
//                newObj.setLeaveMonth(scheduleTransfer.getMonthTo());
//                leaveSchedule.getHrLeaveScheduleDetList().add(newObj);
//                System.err.println("the size again " + leaveSchedule.getHrLeaveScheduleDetList().size());
//                LeavescheduleLocal.edit(leaveSchedule);
//                System.err.println("the schedule object also updated");
//                temp = true;
//                newObj = null;
//                break;
//            }
        }
        
        return temp;
    }
    
    public void getEmployeeInfo(SelectEvent event) {
        
        String emplId = event.getObject().toString();
        HrEmployees employeeFirst = new HrEmployees();
        employeeFirst.setEmpId(emplId);
        employee = profileBeanLocal.findById(employeeFirst);
        leaveSchedule = new HrLeaveSchedule();
        leaveSchedule.setEmployeeId(employee);
        
    }
    
    public void updateSchedule() {
        leaveScheduleDetile = getLeaveScheduledet().getRowData();
        
    }
    
    public void loadLeaveYear() {
        accountingPeriod = accountingPeriodBeanLocal.getCurretActivePeriod();
        accountingPeriod.setActivePeriod(accountingPeriod.getActivePeriod());
        leaveSchedule.setLeaveYearId(accountingPeriod.getLuBudgetYearId());
    }
    
    public void clear() {
        leaveScheduleDetile = null;
        leaveSchedule = null;
        leaveScheduledet = null;
        employee = null;
        scheduleTransfer = null;
        int update = 0;
        diplayTransfer = false;
        showTransfer = true;
        btnStatus = true;
        btnStatuss = false;
        String saveUpdate = "Create";
    }
    
    public void UpData() {
        leaveScheduleDetile = leaveScheduledet.getRowData();
        
    }
    
    public void updateData() {
        leaveScheduleDetile = leaveScheduledet.getRowData();
        
    }
//</editor-fold>
}
