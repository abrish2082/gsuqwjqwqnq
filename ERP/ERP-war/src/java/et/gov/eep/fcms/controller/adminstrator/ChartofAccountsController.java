/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.adminstrator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.BeginningBalanceBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.entity.FmsBeginningBalance;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.pms.businessLogic.PmsWorkAuthorizationBeanLocal;
import et.gov.eep.pms.entity.PmsWorkAuthorization;

/**
 *
 * @author Bin
 */
@Named(value = "chartofAccountsController")
@ViewScoped
public class ChartofAccountsController implements Serializable {
//<editor-fold defaultstate="collapsed" desc="@EJB and @Inject declaration">
//@EJB

    @EJB
    FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal;
    @EJB
    FmsCostCenterBeanLocal fmsCostCenterBeanLocal;
    @EJB
    FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    FmsLuSystemBeanLocal fmsLuSystemBeanLocal;
    @EJB
    private subsidiaryBeanLocal subLedgerBeanLocal;
    @EJB
    PmsWorkAuthorizationBeanLocal pmsWorkAuthorizationBeanLocal;
    @EJB
    private HrEmployeeBeanLocal hrEmployeeBean;
    @EJB
    BeginningBalanceBeanLocal beginningBalanceBeanLocal;
    @EJB
    FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal;

//@ Inject
    @Inject
    SessionBean SessionBean;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @Inject
    FmsSubsidiaryLedger fmsSubsid1aryLedger1;
    @Inject
    FmsLuSystem fmsLuSystem;
    @Inject
    FmsCostCenter fmsCostCenter;
    @Inject
    PmsWorkAuthorization pmsWorkAuthorization;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    private HrEmployees employeeEnty;
    @Inject
    FmsBeginningBalance fmsBeginningBalance;
    @Inject
    FmsAccountingPeriod AccountingPeriod;
    @Inject
    FmsCostcSystemJunction fmsCostcSystemJunction;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    BigDecimal entryAmount;
    Boolean jobOption = false;
    Boolean empOption = false;
    List<FmsSubsidiaryLedger> subsiList;
    List<FmsCostCenter> costCenterList;
    DataModel<FmsSubsidiaryLedger> subsiDModel;
    DataModel<FmsSubsidiaryLedger> subsidiaryLedgerDataModel;
    private String Conc;
    private String SLtyperadioVal;
    private String createOrSearchBundle = "New";
    private String headerTitle = "Chart of Account Search";
    private String icone = "ui-icon-plus";
    private String entryType;
    private String SaveOrUpdateButton = "Save";
    String id;
    private Boolean update = false;
    private boolean empBool;
    private boolean jobBool;
    private boolean ledgerOption = true;
    private boolean disableBtnCreate;
    private boolean renderPnlToSearchPage;
    private boolean isRenderCreate = true;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private FmsSubsidiaryLedger selectedAccount;
//</editor-fold>

    
       DataModel<FmsSubsidiaryLedger> fmsFmsSubsidiaryLedgerDataModel;

    public DataModel<FmsSubsidiaryLedger> getFmsSubsidiaryLedgerDataModel() {
        return fmsFmsSubsidiaryLedgerDataModel;
    }

    public void setFmsSubsidiaryLedgerDataModel(DataModel<FmsSubsidiaryLedger> fmsFmsSubsidiaryLedgerDataModel) {
        this.fmsFmsSubsidiaryLedgerDataModel = fmsFmsSubsidiaryLedgerDataModel;
    }
     public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            fmsSubsid1aryLedger1.setColumnName(event.getNewValue().toString());
            fmsSubsid1aryLedger1.setColumnValue(null);
            System.out.println("fmsVoucher.getColumnName(); " + fmsSubsid1aryLedger1.getColumnName());
            System.out.println("fmsVoucher.getColumnValue(); " + fmsSubsid1aryLedger1.getColumnValue());

        }
    }
      
     List<FmsSubsidiaryLedger> FmsSubsidiaryLedgerSearchingParameterList;

    public List<FmsSubsidiaryLedger> getFmsSubsidiaryLedgerSearchingParameterList() {
        if (FmsSubsidiaryLedgerSearchingParameterList == null) {
            FmsSubsidiaryLedgerSearchingParameterList = new ArrayList<>();
            FmsSubsidiaryLedgerSearchingParameterList = subLedgerBeanLocal.getAllSubListList();
            System.out.println("FmsSubsidiaryLedgerSearchingParameterList==" + FmsSubsidiaryLedgerSearchingParameterList);
        }
        return FmsSubsidiaryLedgerSearchingParameterList;

    }

    public void setFmsSubsidiaryLedgerSearchingParameterList(List<FmsSubsidiaryLedger> FmsSubsidiaryLedgerSearchingParameterList) {
        this.FmsSubsidiaryLedgerSearchingParameterList = FmsSubsidiaryLedgerSearchingParameterList;
    }
    
       public void recreateJvDataModel() {
        fmsFmsSubsidiaryLedgerDataModel = null;
        fmsFmsSubsidiaryLedgerDataModel = new ListDataModel(new ArrayList(fmsFmsCostCenterList1));
    }
    List<FmsSubsidiaryLedger> fmsFmsCostCenterList1;

    public void search_vouchers() {
//        fmsLuSystem.setPreparedBy(sessionBeanLocal.getUserId());
//        fmsLuSystem.setType("CHPV");
        fmsFmsCostCenterList1 = subLedgerBeanLocal.searchAllVochNo(fmsSubsid1aryLedger1);
        recreateJvDataModel();
        fmsSubsid1aryLedger1 = new FmsSubsidiaryLedger();
    }
    
//<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    public FmsCostcSystemJunction getFmsCostcSystemJunction() {
        return fmsCostcSystemJunction;
    }

    public void setFmsCostcSystemJunction(FmsCostcSystemJunction fmsCostcSystemJunction) {
        this.fmsCostcSystemJunction = fmsCostcSystemJunction;
    }

    public DataModel<FmsSubsidiaryLedger> getSubsidiaryLedgerDataModel() {
        if (subsidiaryLedgerDataModel == null) {
            subsidiaryLedgerDataModel = new ListDataModel<>();
        }
        return subsidiaryLedgerDataModel;
    }

    public HrEmployees getEmployeeEnty() {
        return employeeEnty;
    }

    public void setEmployeeEnty(HrEmployees employeeEnty) {
        this.employeeEnty = employeeEnty;
    }

    public String getConc() {
        return Conc;
    }

    public void setConc(String Conc) {
        this.Conc = Conc;
    }

    public String getSLtyperadioVal() {
        return SLtyperadioVal;
    }

    public void setSLtyperadioVal(String SLtyperadioVal) {
        this.SLtyperadioVal = SLtyperadioVal;
    }

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    public boolean isLedgerOption() {
        return ledgerOption;
    }

    public void setLedgerOption(boolean ledgerOption) {
        this.ledgerOption = ledgerOption;
    }

    public boolean isEmpBool() {
        return empBool;
    }

    public void setEmpBool(boolean empBool) {
        this.empBool = empBool;
    }

    public boolean isJobBool() {
        return jobBool;
    }

    public void setJobBool(boolean jobBool) {
        this.jobBool = jobBool;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public BigDecimal getEntryAmount() {
        return entryAmount;
    }

    public void setEntryAmount(BigDecimal entryAmount) {
        this.entryAmount = entryAmount;
    }

    public PmsWorkAuthorization getPmsWorkAuthorization() {
        return pmsWorkAuthorization;
    }

    public void setPmsWorkAuthorization(PmsWorkAuthorization pmsWorkAuthorization) {
        this.pmsWorkAuthorization = pmsWorkAuthorization;
    }

    public List<FmsSubsidiaryLedger> getSubsiList() {
        return subsiList;
    }

    public void setSubsiList(List<FmsSubsidiaryLedger> subsiList) {
        this.subsiList = subsiList;
    }

    public DataModel<FmsSubsidiaryLedger> getSubsiDModel() {
        return subsiDModel;
    }

    public void setSubsiDModel(DataModel<FmsSubsidiaryLedger> subsiDModel) {
        this.subsiDModel = subsiDModel;
    }

    public FmsSubsidiaryLedger findSubL() {
        return this.subLedgerBeanLocal.getSubsidiaryInfo(fmsSubsid1aryLedger1);
    }

    public boolean isRenderPnlToSearchPage() {
        return renderPnlToSearchPage;
    }

    public void setRenderPnlToSearchPage(boolean renderPnlToSearchPage) {
        this.renderPnlToSearchPage = renderPnlToSearchPage;
    }

    public boolean isIsRenderCreate() {
        return isRenderCreate;
    }

    public void setIsRenderCreate(boolean isRenderCreate) {
        this.isRenderCreate = isRenderCreate;
    }

    public void setSubsidiaryLedgerDataModel(DataModel<FmsSubsidiaryLedger> subsidiaryLedgerDataModel) {
        this.subsidiaryLedgerDataModel = subsidiaryLedgerDataModel;
    }

    public List<FmsCostCenter> getCostCenterList() {
        if (costCenterList == null) {
            costCenterList = new ArrayList<>();
        }
        return costCenterList;
    }

    public void setCostCenterList(List<FmsCostCenter> costCenterList) {
        this.costCenterList = costCenterList;
    }

    public FmsBeginningBalance getFmsBeginningBalance() {
        if (fmsBeginningBalance == null) {
            fmsBeginningBalance = new FmsBeginningBalance();
        }
        return fmsBeginningBalance;
    }

    public void setFmsBeginningBalance(FmsBeginningBalance fmsBeginningBalance) {
        this.fmsBeginningBalance = fmsBeginningBalance;
    }

    public FmsCostCenter getFmsCostCenter() {
        if (fmsCostCenter == null) {
            fmsCostCenter = new FmsCostCenter();
        }
        return fmsCostCenter;
    }

    public void setFmsCostCenter(FmsCostCenter fmsCostCenter) {
        this.fmsCostCenter = fmsCostCenter;
    }

    public Boolean getJobOption() {
        return jobOption;
    }

    public void setJobOption(Boolean jobOption) {
        this.jobOption = jobOption;
    }

    public HrEmployees getHrEmployees() {
        return hrEmployees;
    }

    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
    }

    public Boolean getEmpOption() {
        return empOption;
    }

    public void setEmpOption(Boolean empOption) {
        this.empOption = empOption;
    }

    public FmsLuSystem getFmsLuSystem() {
        if (fmsLuSystem == null) {
            fmsLuSystem = new FmsLuSystem();
        }
        return fmsLuSystem;
    }

    public void setFmsLuSystem(FmsLuSystem fmsLuSystem) {
        this.fmsLuSystem = fmsLuSystem;
    }

    public FmsSubsidiaryLedger getFmsSubsid1aryLedger1() {
        if (fmsSubsid1aryLedger1 == null) {
            fmsSubsid1aryLedger1 = new FmsSubsidiaryLedger();
        }
        return fmsSubsid1aryLedger1;
    }

    public void setFmsSubsid1aryLedger1(FmsSubsidiaryLedger fmsSubsid1aryLedger1) {
        this.fmsSubsid1aryLedger1 = fmsSubsid1aryLedger1;
    }

    public FmsAccountingPeriod getAccountingPeriod() {
        if (AccountingPeriod == null) {
            AccountingPeriod = new FmsAccountingPeriod();
        }
        return AccountingPeriod;
    }

    public void setAccountingPeriod(FmsAccountingPeriod AccountingPeriod) {
        this.AccountingPeriod = AccountingPeriod;
    }

    public FmsGeneralLedger getFmsGeneralLedger() {
        if (fmsGeneralLedger == null) {
            fmsGeneralLedger = new FmsGeneralLedger();
        }
        return fmsGeneralLedger;
    }

    public void setFmsGeneralLedger(FmsGeneralLedger fmsGeneralLedger) {
        this.fmsGeneralLedger = fmsGeneralLedger;
    }

    public Boolean getUpdate() {
        return update;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isRenderPnlCreateGatePass() {
        return renderPnlCreateGatePass;
    }

    public void setRenderPnlCreateGatePass(boolean renderPnlCreateGatePass) {
        this.renderPnlCreateGatePass = renderPnlCreateGatePass;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public ChartofAccountsController() {
    }

    public SelectItem[] getSubList() {
        return JsfUtil.getSelectItems(subLedgerBeanLocal.findAll(), true);
    }

    public FmsSubsidiaryLedger getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(FmsSubsidiaryLedger selectedAccount) {
        this.selectedAccount = selectedAccount;
    }

    public void getActivePeriod() {

    }

    public SelectItem[] getSystemSearchList() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findAll(), true);
    }
//</editor-fold>

    //value change event for system search change
    public void SystemSearchChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuSystem.setSystemCode(event.getNewValue().toString());
            fmsLuSystem = fmsLuSystemBeanLocal.getSysDetail(fmsLuSystem);
            costCenterList = fmsCostCenterBeanLocal.findMappedCostCenter(fmsLuSystem);
            if (fmsCostCenter != null || fmsGeneralLedger != null || hrEmployees.getEmpId() != null || pmsWorkAuthorization.getJobNo() != null) {
                hrEmployees = new HrEmployees();
                pmsWorkAuthorization = new PmsWorkAuthorization();
                fmsCostCenter = new FmsCostCenter();
                fmsGeneralLedger = new FmsGeneralLedger();
                fmsSubsid1aryLedger1.setSubsidiaryCode(null);
                fmsSubsid1aryLedger1.setGeneralLedgerId(null);
            }
        }
    }

    //value change event for cost search change
    public void CostSearchChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsCostCenter.setSystemName(event.getNewValue().toString());
            fmsCostCenter = fmsCostCenterBeanLocal.getCCDetail(fmsCostCenter);
            fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findByCCandSS(fmsLuSystem, fmsCostCenter);
            fmsSubsid1aryLedger1.setSsCcJunction(fmsCostcSystemJunction);
            if (fmsGeneralLedger != null || hrEmployees.getEmpId() != null || pmsWorkAuthorization.getJobNo() != null) {
                hrEmployees = new HrEmployees();
                pmsWorkAuthorization = new PmsWorkAuthorization();
                fmsGeneralLedger = new FmsGeneralLedger();
                fmsSubsid1aryLedger1.setGeneralLedgerId(null);
                fmsSubsid1aryLedger1.setSubsidiaryCode(null);

            }
        }
    }

    //value change event for GL listner
    public void glListener(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsGeneralLedger.setGeneralLedgerCode(event.getNewValue().toString());
            fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getGLDetail(fmsGeneralLedger);
            if (hrEmployees.getEmpId() != null || pmsWorkAuthorization.getJobNo() != null) {
                hrEmployees = new HrEmployees();
                pmsWorkAuthorization = new PmsWorkAuthorization();
                fmsSubsid1aryLedger1.setSubsidiaryCode(null);
            }
            fmsSubsid1aryLedger1.setSubsidiaryCode(fmsLuSystem.getSystemCode()
                    + "-" + fmsCostCenter.getSystemName() + "-" + fmsGeneralLedger.getGeneralLedgerCode());
            ledgerOption = false;
        }
    }

    //value change event for Ledger Type Checker
    public void LedgerTypeChecker(ValueChangeEvent e) {
        if (e.getNewValue() != null && !e.getNewValue().equals("")) {
            if (empBool) {
            } else if (jobBool) {
                fmsSubsid1aryLedger1.setEmpId(null);
                hrEmployees = null;
                findJobList();
                SLtyperadioVal = "Job";
            } else if (e.getNewValue().toString().equalsIgnoreCase("Neither")) {
                fmsSubsid1aryLedger1.setJobid(null);
                fmsSubsid1aryLedger1.setEmpId(null);
                hrEmployees = null;
                SLtyperadioVal = "Neither";
            }
        }
    }

    //value change event for Ledger Type
    public void LedgerType(ValueChangeEvent e) {
        if (e.getNewValue() != null && !e.getNewValue().equals("")) {
            if (e.getNewValue().toString().equalsIgnoreCase("Employee")) {
                fmsSubsid1aryLedger1.setJobid(null);
                SLtyperadioVal = "Employee";
            } else if (e.getNewValue().toString().equalsIgnoreCase("Job")) {
                fmsSubsid1aryLedger1.setEmpId(null);
                hrEmployees = null;
                findJobList();
                SLtyperadioVal = "Job";
            } else if (e.getNewValue().toString().equalsIgnoreCase("Neither")) {
                fmsSubsid1aryLedger1.setJobid(null);
                fmsSubsid1aryLedger1.setEmpId(null);
                hrEmployees = null;
                SLtyperadioVal = "Neither";
            }
        }
    }

//value change event for job id
    public void JobId(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            pmsWorkAuthorization.setJobNo(event.getNewValue().toString());
            pmsWorkAuthorization = pmsWorkAuthorizationBeanLocal.findJobID(pmsWorkAuthorization);
            fmsSubsid1aryLedger1.setJobid(pmsWorkAuthorization);
            if (hrEmployees.getEmpId() != null) {
                fmsSubsid1aryLedger1.setSubsidiaryCode(fmsLuSystem.getSystemCode()
                        + "-" + fmsCostCenter.getSystemName() + "-"
                        + fmsGeneralLedger.getGeneralLedgerCode());

            } else {
                fmsSubsid1aryLedger1.setSubsidiaryCode(fmsLuSystem.getSystemCode()
                        + "-" + fmsCostCenter.getSystemName() + "-"
                        + fmsGeneralLedger.getGeneralLedgerCode());
            }
        }
    }

//value change event for subsidery ledger
    public void getSubsidiaryLChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsSubsid1aryLedger1.setSubsidiaryId(Integer.parseInt(event.getNewValue().toString()));
            fmsSubsid1aryLedger1 = subLedgerBeanLocal.getSubsidiaryInfo(fmsSubsid1aryLedger1);
            if (fmsSubsid1aryLedger1.getCredit() != null) {
                entryAmount = fmsSubsid1aryLedger1.getCredit();
                entryType = "Credit";
            } else {
                entryAmount = fmsSubsid1aryLedger1.getDebit();
                entryType = "Debit";
            }
            SaveOrUpdateButton = "Update";
        }
    }

    //select event
    public void populate(SelectEvent event) {
        fmsSubsid1aryLedger1 = (FmsSubsidiaryLedger) event.getObject();
        if (fmsSubsid1aryLedger1.getCredit() != null) {
            entryAmount = fmsSubsid1aryLedger1.getCredit();
            entryType = "Credit";
        } else {
            entryAmount = fmsSubsid1aryLedger1.getDebit();
            entryType = "Debit";
        }
        if (fmsSubsid1aryLedger1.getEmpId() != null) {
            hrEmployees = fmsSubsid1aryLedger1.getEmpId();
        } else if (fmsSubsid1aryLedger1.getJobid() != null) {
        }

        if (fmsSubsid1aryLedger1.getEmpId() != null) {
            SLtyperadioVal = "Employee";
        } else if (fmsSubsid1aryLedger1.getJobid() != null) {
            SLtyperadioVal = "Job";
        } else {
            SLtyperadioVal = "Neither";
        }
        fmsLuSystem = fmsSubsid1aryLedger1.getSsCcJunction().getFmsLuSystem();
        costCenterList = fmsCostCenterBeanLocal.findMappedCostCenter(fmsLuSystem);
        fmsCostCenter = fmsSubsid1aryLedger1.getSsCcJunction().getFmsCostCenter();
        if (fmsSubsid1aryLedger1.getEmpId() != null) {
            hrEmployees = fmsSubsid1aryLedger1.getEmpId();
            empBool = false;
        }
        if (fmsSubsid1aryLedger1.getJobid() != null) {
            pmsWorkAuthorization = fmsSubsid1aryLedger1.getJobid();
            jobBool = false;
        }
        update = true;
        renderPnlToSearchPage = true;
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        createOrSearchBundle = "New";
        headerTitle = "Chart of Account Registration";
        setIcone("ui-icon-plus");
        SaveOrUpdateButton = "Update";
    }

    //select event
    public void getByAccName(SelectEvent event) {
        fmsGeneralLedger.setAccountTitle(event.getObject().toString());
        fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getByAccountTitle(fmsGeneralLedger);
    }

    //select event 
    public void getEmpId(SelectEvent event) {
        hrEmployees = (HrEmployees) event.getObject();
        if (pmsWorkAuthorization.getJobNo() != null) {
            fmsSubsid1aryLedger1.setSubsidiaryCode(fmsLuSystem.getSystemCode()
                    + "-" + fmsCostCenter.getSystemName() + "-" + fmsGeneralLedger.getGeneralLedgerCode());
        } else {
            fmsSubsid1aryLedger1.setSubsidiaryCode(fmsLuSystem.getSystemCode()
                    + "-" + fmsCostCenter.getSystemName()
                    + "-" + fmsGeneralLedger.getGeneralLedgerCode());
        }
    }

    //search employee id
    public List<HrEmployees> SearchByEmpId(String empId) {
        ArrayList<HrEmployees> employees = new ArrayList<>();
        employeeEnty.setEmpId(empId);
        employees = hrEmployeeBean.searchByEmpById(employeeEnty);
        return employees;
    }

    //search subsidiary code
    public List<FmsSubsidiaryLedger> SearchBySubsidiaryCode(String slCode) {
        ArrayList<FmsSubsidiaryLedger> slCodes = new ArrayList<>();
        fmsSubsid1aryLedger1.setSubsidiaryCode(slCode);
        slCodes = subLedgerBeanLocal.searchSubsidiaryCode(fmsSubsid1aryLedger1);
        return slCodes;
    }

    //search subsidiary
    public void SubsidiariesSearch() {
        subsiList = new ArrayList<>();
        if (fmsSubsid1aryLedger1 == null) {
            subsiList = subLedgerBeanLocal.findAll();
            subsiDModel = new ListDataModel(new ArrayList(subsiList));
        } else {
            subsiList = subLedgerBeanLocal.searchSubsidiaries(fmsSubsid1aryLedger1);
            subsiDModel = new ListDataModel(new ArrayList(subsiList));
        }
    }
//select item to find subsidiary list

    public SelectItem[] findListSubL() {
        ArrayList<FmsSubsidiaryLedger> subsidaryList = new ArrayList<>();
        if (fmsGeneralLedger != null) {
            SelectItem[] listSl = null;

            subsidaryList = subLedgerBeanLocal.findSubLedger(fmsGeneralLedger);
            if (subsidaryList.size() > 0) {
                listSl = new SelectItem[subsidaryList.size() + 1];
                listSl[0] = new SelectItem(null, "Select One");
                for (int i = 0; i < subsidaryList.size(); i++) {
                    listSl[i + 1] = new SelectItem(subsidaryList.get(i).getSubsidiaryId(), subsidaryList.get(i).getSubsidiaryCode());
                }
            }
            return listSl;
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--Select CC & GL--");
            return items;
        }
    }

    //select item for find all job list
    public SelectItem[] findJobList() {
        return JsfUtil.getSelectItems(pmsWorkAuthorizationBeanLocal.findAll(), true);
    }

    //select item for find all Gl data
    public SelectItem[] getGLdata() {
        return JsfUtil.getSelectItems(fmsGeneralLedgerBeanLocal.findAll(), true);
    }

    //select item to get status
    public SelectItem[] getStatus() {
        return JsfUtil.getSelectItems(stat(), true);
    }

    //search button action
    public void goBackSearchButtonAction() {
        renderPnlToSearchPage = false;
        renderPnlCreateGatePass = false;
        renderPnlManPage = true;

    }

    //check job option
    public void jobOptionChecker() {
        if (jobOption == true) {
            fmsSubsid1aryLedger1.setEmpId(null);
        } else {
            fmsSubsid1aryLedger1.setJobid(null);
        }
    }

    //empty employee option checker
    public void empOptionChecker() {
        if (empOption == true) {
            fmsSubsid1aryLedger1.setJobid(null);
        } else {
            fmsSubsid1aryLedger1.setEmpId(null);
        }
    }

    //generate account code
    public void generateAccCode() {
        fmsSubsid1aryLedger1.setSubsidiaryCode(fmsLuSystem.getSystemCode()
                + "-" + fmsCostCenter.getSystemName() + "-" + fmsGeneralLedger.getGeneralLedgerCode()
                + "-" + hrEmployees.getEmpId() + "-" + pmsWorkAuthorization.getJobNo());
    }

    //array list for returning position
    public ArrayList<String> stat() {
        ArrayList<String> position = new ArrayList<>();
        position.add("Active");
        position.add("Inactive");
        return position;
    }

    //debit credit entry value
    public void DebCredEntryVal() {
        if (entryType.matches("Debit")) {
            fmsSubsid1aryLedger1.setDebit(entryAmount);
            fmsSubsid1aryLedger1.setCredit(null);
            fmsBeginningBalance.setDebit(entryAmount);
            fmsBeginningBalance.setCredit(null);
            fmsBeginningBalance.setAmount(entryAmount);
            createSubLedger();
        } else if (entryType.matches("Credit")) {
            fmsSubsid1aryLedger1.setCredit(entryAmount);
            fmsSubsid1aryLedger1.setDebit(null);
            fmsBeginningBalance.setCredit(entryAmount);
            fmsBeginningBalance.setDebit(null);
            fmsBeginningBalance.setAmount(entryAmount);
            createSubLedger();
        }
    }

    //create subsidery ledger
    public void createSubLedger() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "createSubLedger", dataset)) {

                AccountingPeriod = fmsAccountingPeriodBeanLocal.getCurretActivePeriod();
                fmsSubsid1aryLedger1.setEmpId(hrEmployees);
                if (update == false) {
                    subLedgerBeanLocal.create(fmsSubsid1aryLedger1);

                    fmsBeginningBalance.setSlCode(fmsSubsid1aryLedger1);
                    fmsBeginningBalance.setAccountigPeriodId(AccountingPeriod);
                    beginningBalanceBeanLocal.create(fmsBeginningBalance);

                    JsfUtil.addSuccessMessage("Saved Successfully.");
                } else {
                    FmsGeneralLedger GLID = fmsSubsid1aryLedger1.getGeneralLedgerId();
                    fmsSubsid1aryLedger1.setGeneralLedgerId(GLID);
                    if (entryType.matches("Debit")) {
                        fmsSubsid1aryLedger1.setCredit(null);
                    } else {
                        fmsSubsid1aryLedger1.setDebit(null);
                    }
                    subLedgerBeanLocal.edit(fmsSubsid1aryLedger1);
                    JsfUtil.addSuccessMessage("Updated Successfully.");
                    update = false;
                }
                resetSL();

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
        } catch (Exception ex) {
        }
    }

    //delete subsidery ledger
    public void deleteSubLedger() {
        subLedgerBeanLocal.delete(fmsSubsid1aryLedger1);
        JsfUtil.addSuccessMessage("Deleted Successfully.");
        fmsSubsid1aryLedger1 = new FmsSubsidiaryLedger();
    }

    //reset subsidery ledger
    public void resetSL() {
        try {
            fmsSubsid1aryLedger1 = new FmsSubsidiaryLedger();
            fmsCostCenter = new FmsCostCenter();
            fmsGeneralLedger = new FmsGeneralLedger();
            fmsLuSystem = new FmsLuSystem();
            subsiDModel = null;
            hrEmployees = new HrEmployees();
            pmsWorkAuthorization = new PmsWorkAuthorization();
            Conc = null;
            entryAmount = null;
            entryType = null;
            SaveOrUpdateButton = "Save";
        } catch (Exception e) {
            throw e;
        }
    }

//create and search 
    public void createNewSLView() {
        renderPnlToSearchPage = false;
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateGatePass = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            headerTitle = "Chart of Account Registration";
            SaveOrUpdateButton = "Save";
            setIcone("ui-icon-plus");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGatePass = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            headerTitle = "Chart of Account Search";
            SaveOrUpdateButton = "Update";
            setIcone("ui-icon-plus");
        }
        resetSL();
    }

}
