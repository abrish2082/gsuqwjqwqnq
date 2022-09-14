/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.perdiem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.convert.NumberConverter;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import securityBean.WorkFlow;
import securityBean.Constants;
import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.perDiem.FmsFieldAllowansBeanLocal;
import et.gov.eep.fcms.businessLogic.perDiem.FmsLuCountryBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.businessLogic.perDiem.fmsFieldAllowansForeignBeanLocal;
import et.gov.eep.commonApplications.businessLogic.WfFcmsProcessedBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowance;
import et.gov.eep.fcms.entity.perDiem.FmsLuCountry;
import et.gov.eep.fcms.entity.perDiem.FmsLuPerdimeRate;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.entity.admin.FmsSystemJobJunction;
import et.gov.eep.hrms.businessLogic.address.HrAddressesBeanLocal;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.employee.HrEmpAddresses;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuJobLevels;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.integration.HrAddressesBeanIntegrationLocal;

/**
 *
 * @author Mubarek jebel
 */
@Named(value = "fmsFieldAllowanceManagedBean")
@ViewScoped
public class FmsFieldAllowanceManagedBean implements Serializable {

    //  <editor-fold defaultstate="collapsed" desc=" address tab ">
    //Inject entitites
    @Inject
    SessionBean SessionBean;
    @Inject
    private FmsLuCountry fmsLuCountry;
    @Inject
    private FmsFieldAllowance fmsFieldAllowanceEnty;
    @Inject
    private FmsFieldAllowance fmsFieldAllowanceEnty2;
    @Inject
    private WfFcmsProcessed wfFcmsProcessed;
    @Inject
    WorkFlow workFlow;
    @Inject
    private HrEmployees employeeEnty;
    @Inject
    private HrEmployees empEnty;
    @Inject
    private HrSalaryScaleRanges hrSalaryScaleRanges;
    @Inject
    private HrLuJobLevels hrLuJobLevels;
    @Inject
    private FmsLuPerdimeRate fmsLuPerdimeRate;
    @Inject
    HrAddresses hrAddresses2;
    @Inject
    HrAddresses hrAddresses;
    @Inject
    HrEmpAddresses hrEmpAddresses;
    @Inject
    FmsLuSystem fmsLuSystem;
    @Inject
    FmsCostCenter fmsCostCenter;
    @Inject
    FmsSubsidiaryLedger fmsSubsidiaryLedger;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @Inject
    private FmsCostcSystemJunction fmsCostcSystemJunction;
    @Inject
    private FmsSystemJobJunction fmsSystemJobJunction;
    //Inject @EJB
    @EJB
    private WfFcmsProcessedBeanLocal wfFcmsProcessedBeanLocal;
    @EJB
    private FmsLuCountryBeanLocal fmsLuCountryBeanLocal;
    @EJB
    private FmsFieldAllowansBeanLocal fmsFieldAllowansBeanLocal;
    @EJB
    FmsLuSystemBeanLocal fmsLuSystemBeanLocal;
    @EJB
    FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal;
    @EJB
    FmsCostCenterBeanLocal fmsCostCenterBeanLocal;
    @EJB
    private FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    private subsidiaryBeanLocal subsidiaryLedgerBeanLocal;
    @EJB
    HrAddressesBeanLocal hrAddressesBeanLocal;
    @EJB
    private fmsFieldAllowansForeignBeanLocal foreignBeanLocal;
    @EJB
    private HrEmployeeBeanLocal hrEmployeeBean;
    @EJB
    HrAddressesBeanIntegrationLocal hrAddressesBeanIntegrationLocal;

    //variable declaration
    FmsFieldAllowance selectRequest;
    DataModel<FmsFieldAllowance> fieldAllowanceDataModel;
    private DataModel<WfFcmsProcessed> wfFcmsProcessedDataModel;
    private List<WfFcmsProcessed> wfFcmsProcessedList;
    private List<FmsFieldAllowance> fmsFAList;
    List<FmsFieldAllowance> allowancesList;
    List<FmsFieldAllowance> listempName;
    List<FmsCostcSystemJunction> ssCcJunctionList = new ArrayList<>();
    List<FmsSystemJobJunction> sysJobNOList;
    List<FmsGeneralLedger> glList;
    List<FmsSubsidiaryLedger> slList;
    List<FmsLuSystem> systemList;
    List<HrEmployees> emyployeeList;
    List<FmsFieldAllowance> fieldAllowanceList;
    List<FmsFieldAllowance> EmpNameList = new ArrayList<>();
    List<FmsLuCountry> luCountrys = null;
    boolean renderJobNo = false;
    final Integer projectType = 2;
    final Integer nonProjectType = 1;
    private int addressId;
    private int addressId2;
    private Integer depAddress;
    private Integer destAddress;
    private TreeNode addressRoot;
    private TreeNode addressRoot2;
    private TreeNode addressSelectedNode;
    private TreeNode addressSelectedNode2;
    private static List<HrAddresses> allAddresses;
    private static List<HrAddresses> addresses;
    int updateStatus = 0;
    int dayDifference = 0;
    int day = 0;
    int monthDifference = 0;
    int yearDifference = 0;
    private Date prepareDate;
    private NumberConverter numberConverter = new NumberConverter();
    Double DtimeAmount;
    Double RtimeAmount;
    Integer dateStatus = 11;
    Integer dateCompStatus;
    boolean txtRDatevisibility = false;
    private boolean renderBtnPrint = false;
    private boolean isCheckedNoneTravel = false;
    private boolean isCheckedNoneLodging = false;
    private boolean isRenderDecision = false;
    private boolean isRenderedIconNitification = false;
    private boolean isRenderedIconWorkFlow = false;
    private boolean isRenderedBtnNew = false;
    private String saveorUpdateBundle = "Save";
    private String allAddressUnitAsOne;
    private String allAddressUnitAsOne2;
    private String concatination2 = "";
    private String display_conn;
    String selectedDecision = "";
    String txtvisibility = "display";
    String id;
    String requestNumber = "";
    String fieldAllowanceEntyToDate;
    String fieldAllowanceEntyFromDate;
    String firstname;
    String country = "Ethiopia";
    String Dtime;
    String userName;
    Double miscellaneousExpense = 0.00;
    Double totalExpense = 0.00;
    Double breakfast = 0.0;
    Double lunch = 0.0;
    Double dinner = 0.0;
    double travel = 0.0;
    double totalMealsExpense = 0.0;
    double totalHotelExpense = 0.0;
    private boolean disableBtnCreate;
    private boolean renderPnlCreatePerDiemLocal = false;
    private boolean renderPnlManPage = true;

    //  </editor-fold>
    public FmsFieldAllowanceManagedBean() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(3);
    }

    @PostConstruct
    public void init() {
        loadAddressTree();
        loadAddressTree2();
        generateReqNo();
        userName = SessionBean.getUserName();
        wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
        if (workFlow.isPrepareStatus()) {//Preparer
            isRenderDecision = false;
            isRenderedBtnNew = true;
        } else if (workFlow.isCheckStatus()) {//Checker
            isRenderedIconNitification = true;
            isRenderDecision = true;
            isRenderedBtnNew = false;
            renderPnlManPage = true;
            renderPnlCreatePerDiemLocal = false;
            requestNumber = "";
            fmsFAList = fmsFieldAllowansBeanLocal.findFaByWfStatus(Constants.PREPARE_VALUE);//to be chacked list
        } else if (workFlow.isApproveStatus()) {//Approver
            isRenderedIconNitification = true;
            isRenderDecision = true;
            isRenderedBtnNew = false;
            renderPnlManPage = true;
            renderPnlCreatePerDiemLocal = false;
            requestNumber = "";
            fmsFAList = fmsFieldAllowansBeanLocal.findFaByWfStatus(Constants.CHECK_APPROVE_VALUE);//to be approved List
        }
    }

    public void generateReqNo() {
        try {
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            int noOfRows = fmsFieldAllowansBeanLocal.countRow() + 1;
            requestNumber = "Field-Allowance-No-" + noOfRows + "/" + f.format(now);
            fmsFieldAllowanceEnty.setRequestNo(requestNumber);
        } catch (Exception e) {
        }
    }

    //  <editor-fold defaultstate="collapsed" desc=" Getter & Setters ">
    public HrAddresses getHrAddresses2() {
        if (hrAddresses2 == null) {
            hrAddresses2 = new HrAddresses();
        }
        return hrAddresses2;
    }

    public void setHrAddresses2(HrAddresses hrAddresses2) {
        this.hrAddresses2 = hrAddresses2;
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

    public FmsCostCenter getFmsCostCenter() {
        if (fmsCostCenter == null) {
            fmsCostCenter = new FmsCostCenter();
        }
        return fmsCostCenter;
    }

    public void setFmsCostCenter(FmsCostCenter fmsCostCenter) {
        this.fmsCostCenter = fmsCostCenter;
    }

    public FmsCostcSystemJunction getFmsCostcSystemJunction() {
        if (fmsCostcSystemJunction == null) {
            fmsCostcSystemJunction = new FmsCostcSystemJunction();
        }
        return fmsCostcSystemJunction;
    }

    public void setFmsCostcSystemJunction(FmsCostcSystemJunction fmsCostcSystemJunction) {
        this.fmsCostcSystemJunction = fmsCostcSystemJunction;
    }

    public FmsSystemJobJunction getFmsSystemJobJunction() {
        if (fmsSystemJobJunction == null) {
            fmsSystemJobJunction = new FmsSystemJobJunction();
        }
        return fmsSystemJobJunction;
    }

    public void setFmsSystemJobJunction(FmsSystemJobJunction fmsSystemJobJunction) {
        this.fmsSystemJobJunction = fmsSystemJobJunction;
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

    public FmsSubsidiaryLedger getFmsSubsidiaryLedger() {
        if (fmsSubsidiaryLedger == null) {
            fmsSubsidiaryLedger = new FmsSubsidiaryLedger();
        }
        return fmsSubsidiaryLedger;
    }

    public void setFmsSubsidiaryLedger(FmsSubsidiaryLedger fmsSubsidiaryLedger) {
        this.fmsSubsidiaryLedger = fmsSubsidiaryLedger;
    }

    public List<FmsCostcSystemJunction> getSsCcJunctionList() {
        if (ssCcJunctionList == null) {
            ssCcJunctionList = new ArrayList<>();
        }
        return ssCcJunctionList;
    }

    public void setSsCcJunctionList(List<FmsCostcSystemJunction> ssCcJunctionList) {
        this.ssCcJunctionList = ssCcJunctionList;
    }

    public List<FmsSystemJobJunction> getSysJobNOList() {
        if (sysJobNOList == null) {
            sysJobNOList = new ArrayList<>();
        }
        return sysJobNOList;
    }

    public void setSysJobNOList(List<FmsSystemJobJunction> sysJobNOList) {
        this.sysJobNOList = sysJobNOList;
    }

    public List<FmsGeneralLedger> getGlList() {
        if (glList == null) {
            glList = new ArrayList<>();
        }
        return glList;
    }

    public void setGlList(List<FmsGeneralLedger> glList) {
        this.glList = glList;
    }

    public List<FmsSubsidiaryLedger> getSlList() {
        if (slList == null) {
            slList = new ArrayList<>();
        }
        return slList;
    }

    public void setSlList(List<FmsSubsidiaryLedger> slList) {
        this.slList = slList;
    }

    public List<FmsLuSystem> getSystemList() {
        if (systemList == null) {
            systemList = new ArrayList<>();
        }
        systemList = fmsLuSystemBeanLocal.findAll();
        return systemList;
    }

    public void setSystemList(List<FmsLuSystem> systemList) {
        this.systemList = systemList;
    }

    public String getDisplay_conn() {
        return display_conn;
    }

    public void setDisplay_conn(String display_conn) {
        this.display_conn = display_conn;
    }

    public boolean isRenderJobNo() {
        return renderJobNo;
    }

    public void setRenderJobNo(boolean renderJobNo) {
        this.renderJobNo = renderJobNo;
    }

    public HrAddresses getHrAddresses() {
        if (hrAddresses == null) {
            hrAddresses = new HrAddresses();
        }
        return hrAddresses;
    }

    public void setHrAddresses(HrAddresses hrAddresses) {
        this.hrAddresses = hrAddresses;
    }

    public Integer getDepAddress() {
        return depAddress;
    }

    public void setDepAddress(Integer depAddress) {
        this.depAddress = depAddress;
    }

    public Integer getDestAddress() {
        return destAddress;
    }

    public void setDestAddress(Integer destAddress) {
        this.destAddress = destAddress;
    }

    public HrEmpAddresses getHrEmpAddresses() {
        if (hrEmpAddresses == null) {
            hrEmpAddresses = new HrEmpAddresses();
        }
        return hrEmpAddresses;
    }

    public void setHrEmpAddresses(HrEmpAddresses hrEmpAddresses) {
        this.hrEmpAddresses = hrEmpAddresses;
    }

    public TreeNode getAddressRoot() {
        return addressRoot;
    }

    public void setAddressRoot(TreeNode addressRoot) {
        this.addressRoot = addressRoot;
    }

    public TreeNode getAddressRoot2() {
        return addressRoot2;
    }

    public void setAddressRoot2(TreeNode addressRoot2) {
        this.addressRoot2 = addressRoot2;
    }

    public TreeNode getAddressSelectedNode() {
        return addressSelectedNode;
    }

    public void setAddressSelectedNode(TreeNode addressSelectedNode) {
        this.addressSelectedNode = addressSelectedNode;
    }

    public TreeNode getAddressSelectedNode2() {
        return addressSelectedNode2;
    }

    public void setAddressSelectedNode2(TreeNode addressSelectedNode2) {
        this.addressSelectedNode2 = addressSelectedNode2;
    }

    public static List<HrAddresses> getAllAddresses() {
        return allAddresses;
    }

    public static void setAllAddresses(List<HrAddresses> allAddresses) {
        FmsFieldAllowanceManagedBean.allAddresses = allAddresses;
    }

    public String getAllAddressUnitAsOne() {
        return allAddressUnitAsOne;
    }

    public void setAllAddressUnitAsOne(String allAddressUnitAsOne) {
        this.allAddressUnitAsOne = allAddressUnitAsOne;
    }

    public String getAllAddressUnitAsOne2() {
        return allAddressUnitAsOne2;
    }

    public void setAllAddressUnitAsOne2(String allAddressUnitAsOne2) {
        this.allAddressUnitAsOne2 = allAddressUnitAsOne2;
    }

    public DataModel<FmsFieldAllowance> getFieldAllowanceDataModel() {
        if (fieldAllowanceDataModel == null) {
            fieldAllowanceDataModel = new ListDataModel<>();
        }
        return fieldAllowanceDataModel;
    }

    public void setFieldAllowanceDataModel(DataModel<FmsFieldAllowance> fieldAllowanceDataModel) {
        this.fieldAllowanceDataModel = fieldAllowanceDataModel;
    }

    public String getReqNo() {
        return requestNumber;
    }

    public void setReqNo(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    public FmsFieldAllowance getSelectRequest() {
        return selectRequest;
    }

    public void setSelectRequest(FmsFieldAllowance selectRequest) {
        this.selectRequest = selectRequest;
    }

    public DataModel<WfFcmsProcessed> getWfFcmsProcessedDataModel() {
        if (wfFcmsProcessedDataModel == null) {
            wfFcmsProcessedDataModel = new ListDataModel<>();
        }
        return wfFcmsProcessedDataModel;
    }

    public void setWfFcmsProcessedDataModel(DataModel<WfFcmsProcessed> wfFcmsProcessedDataModel) {
        this.wfFcmsProcessedDataModel = wfFcmsProcessedDataModel;
    }

    public List<WfFcmsProcessed> getWfFcmsProcessedList() {
        if (wfFcmsProcessedList == null) {
            wfFcmsProcessedList = new ArrayList<>();
        }
        return wfFcmsProcessedList;
    }

    public void setWfFcmsProcessedList(List<WfFcmsProcessed> wfFcmsProcessedList) {
        this.wfFcmsProcessedList = wfFcmsProcessedList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public WorkFlow getWorkFlow() {
        if (workFlow == null) {
            workFlow = new WorkFlow();
        }
        return workFlow;
    }

    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
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

    public HrEmployees getEmpEnty() {

        if (empEnty == null) {
            empEnty = new HrEmployees();
        }
        return empEnty;
    }

    public String getTxtvisibility() {
        return txtvisibility;
    }

    public void setTxtvisibility(String txtvisibility) {
        this.txtvisibility = txtvisibility;
    }

    public boolean isTxtRDatevisibility() {
        return txtRDatevisibility;
    }

    public void setTxtRDatevisibility(boolean txtRDatevisibility) {
        this.txtRDatevisibility = txtRDatevisibility;
    }

    public boolean isRenderBtnPrint() {
        return renderBtnPrint;
    }

    public void setRenderBtnPrint(boolean renderBtnPrint) {
        this.renderBtnPrint = renderBtnPrint;
    }

    public boolean isIsCheckedNoneTravel() {
        return isCheckedNoneTravel;
    }

    public void setIsCheckedNoneTravel(boolean isCheckedNoneTravel) {
        this.isCheckedNoneTravel = isCheckedNoneTravel;
    }

    public boolean isIsCheckedNoneLodging() {
        return isCheckedNoneLodging;
    }

    public void setIsCheckedNoneLodging(boolean isCheckedNoneLodging) {
        this.isCheckedNoneLodging = isCheckedNoneLodging;
    }

    public boolean isIsRenderDecision() {
        return isRenderDecision;
    }

    public void setIsRenderDecision(boolean isRenderDecision) {
        this.isRenderDecision = isRenderDecision;
    }

    public boolean isIsRenderedBtnNew() {
        return isRenderedBtnNew;
    }

    public void setIsRenderedBtnNew(boolean isRenderedBtnNew) {
        this.isRenderedBtnNew = isRenderedBtnNew;
    }

    public String getSelectedDecision() {
        return selectedDecision;
    }

    public void setSelectedDecision(String selectedDecision) {
        this.selectedDecision = selectedDecision;
    }

    public boolean isIsRenderedIconNitification() {
        return isRenderedIconNitification;
    }

    public void setIsRenderedIconNitification(boolean isRenderedIconNitification) {
        this.isRenderedIconNitification = isRenderedIconNitification;
    }

    public boolean isIsRenderedIconWorkFlow() {
        return isRenderedIconWorkFlow;
    }

    public void setIsRenderedIconWorkFlow(boolean isRenderedIconWorkFlow) {
        this.isRenderedIconWorkFlow = isRenderedIconWorkFlow;
    }

    public void setEmpEnty(HrEmployees empEnty) {
        this.empEnty = empEnty;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public FmsFieldAllowance getFmsFieldAllowanceEnty() {
        if (fmsFieldAllowanceEnty == null) {
            fmsFieldAllowanceEnty = new FmsFieldAllowance();
        }
        return fmsFieldAllowanceEnty;
    }

    public void setFmsFieldAllowanceEnty(FmsFieldAllowance fmsFieldAllowanceEnty) {
        this.fmsFieldAllowanceEnty = fmsFieldAllowanceEnty;
    }

    public FmsFieldAllowance getFmsFieldAllowanceEnty2() {
        if (fmsFieldAllowanceEnty2 == null) {
            fmsFieldAllowanceEnty2 = new FmsFieldAllowance();
        }
        return fmsFieldAllowanceEnty2;
    }

    public void setFmsFieldAllowanceEnty2(FmsFieldAllowance fmsFieldAllowanceEnty2) {
        this.fmsFieldAllowanceEnty2 = fmsFieldAllowanceEnty2;
    }

    public WfFcmsProcessed getWfFcmsProcessed() {
        if (wfFcmsProcessed == null) {
            wfFcmsProcessed = new WfFcmsProcessed();
        }
        return wfFcmsProcessed;
    }

    public void setWfFcmsProcessed(WfFcmsProcessed wfFcmsProcessed) {
        this.wfFcmsProcessed = wfFcmsProcessed;
    }

    public HrEmployees getEmployeeEnty() {
        if (employeeEnty == null) {
            employeeEnty = new HrEmployees();
        }
        return employeeEnty;
    }

    public void setEmployeeEnty(HrEmployees employeeEnty) {
        this.employeeEnty = employeeEnty;
    }

    public Date getPrepareDate() {
        return prepareDate;
    }

    public void setPrepareDate(Date prepareDate) {
        this.prepareDate = prepareDate;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public FmsLuCountry getFmsLuCountry() {
        if (fmsLuCountry == null) {
            fmsLuCountry = new FmsLuCountry();
        }

        return fmsLuCountry;
    }

    public void setFmsLuCountry(FmsLuCountry fmsLuCountry) {
        this.fmsLuCountry = fmsLuCountry;
    }

    public List<FmsFieldAllowance> fieldAllowanceSearchlist(String prepDate) {
        allowancesList = null;
        fmsFieldAllowanceEnty.setPreparedDate(prepareDate);
        allowancesList = fmsFieldAllowansBeanLocal.searchFieldAllowance(fmsFieldAllowanceEnty);
        return allowancesList;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isRenderPnlCreatePerDiemLocal() {
        return renderPnlCreatePerDiemLocal;
    }

    public void setRenderPnlCreatePerDiemLocal(boolean renderPnlCreatePerDiemLocal) {
        this.renderPnlCreatePerDiemLocal = renderPnlCreatePerDiemLocal;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public List<FmsFieldAllowance> getFmsFAList() {
        if (fmsFAList == null) {
            fmsFAList = new ArrayList<>();
        }
        return fmsFAList;
    }

    public void setFmsFAList(List<FmsFieldAllowance> fmsFAList) {
        this.fmsFAList = fmsFAList;
    }

    //  </editor-fold>
    public void searchPerdiemLocal() {
        clearPage();
        disableBtnCreate = false;
        renderPnlCreatePerDiemLocal = false;
        renderPnlManPage = true;
    }

    public void getEmpId(SelectEvent event) {
        id = event.getObject().toString();
        employeeEnty.setEmpId(id);
        empEnty = hrEmployeeBean.getByEmpId(employeeEnty);
    }

    public List<HrEmployees> SearchByEmpId(String hrEmployee) {
        ArrayList<HrEmployees> employees = null;
        employeeEnty.setEmpId(hrEmployee);
        employees = hrEmployeeBean.SearchByEmpId(employeeEnty);
        return employees;
    }

    public void getDtime(ValueChangeEvent e) {
        Dtime = fmsFieldAllowanceEnty.getFromTime();
    }

    //valuechange event to CheckNoneTravelEvent
    public void onCheckNoneTravelEvent(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            if (event.getNewValue().toString().equalsIgnoreCase("true")) {//checked
                isCheckedNoneTravel = true;
                if (updateStatus == 0) {//on save
                    fmsFieldAllowanceEnty.setTravelExpense(0.00);
                    travel = fmsFieldAllowanceEnty.getTravelExpense();
                    getPerdiemRate();
                    calculateTotalExpense();
                } else {//on update
                    fmsFieldAllowanceEnty.setTravelExpense(0.00);
                    travel = fmsFieldAllowanceEnty.getTravelExpense();
                    travel = fmsFieldAllowanceEnty2.getTravelExpense();
                    totalHotelExpense = fmsFieldAllowanceEnty2.getHotelExpense();
                    totalMealsExpense = fmsFieldAllowanceEnty2.getMealsExpense();
                    miscellaneousExpense = fmsFieldAllowanceEnty2.getMiscellaneousExpense();
                    fmsFieldAllowanceEnty.setHotelExpense(totalHotelExpense);
                    fmsFieldAllowanceEnty.setMealsExpense(totalMealsExpense);
                    calculateTotalExpense();
                }
            } else {//unchecked
                isCheckedNoneTravel = false;
                if (updateStatus == 0) {//on save and unchecked
                    fmsFieldAllowanceEnty.setTravelExpense(null);
                } else {//on update and unchecked
                    travel = fmsFieldAllowanceEnty2.getTravelExpense();
                    fmsFieldAllowanceEnty.setTravelExpense(travel);
                    totalHotelExpense = fmsFieldAllowanceEnty.getHotelExpense();
                    totalMealsExpense = fmsFieldAllowanceEnty.getMealsExpense();
                    miscellaneousExpense = fmsFieldAllowanceEnty.getMiscellaneousExpense();
                    fmsFieldAllowanceEnty.setHotelExpense(totalHotelExpense);
                    fmsFieldAllowanceEnty.setMealsExpense(totalMealsExpense);
                    calculateTotalExpense();
                }
            }
        }
    }

    //valuechange event to CheckNoneLodgingEvent
    public void onCheckNoneLodgingEvent(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            if (event.getNewValue().toString().equalsIgnoreCase("true")) {
                isCheckedNoneLodging = true;
                fmsFieldAllowanceEnty.setHotelExpense(0.00);
                getPerdiemRate();
                calculateTotalExpense();
            } else {
                isCheckedNoneLodging = false;
                getPerdiemRate();
                calculateTotalExpense();
            }
        }
    }

    //select event for fromdate
    public void FromDate(SelectEvent event) {
        SimpleDateFormat simpledate = new SimpleDateFormat("YYYY-MM-DD");
        fieldAllowanceEntyFromDate = simpledate.format(fmsFieldAllowanceEnty.getFromDate());
        calc();
        if (dateStatus == 0 || dateStatus == 1) {
            txtRDatevisibility = true;
            txtvisibility = "hidden";
            JsfUtil.addFatalMessage("Mr/Mrs " + empEnty.getFirstName() + " is Not Returned");
        } else {
            txtRDatevisibility = false;
        }
    }

    //value change event
    public void getRtime(ValueChangeEvent e) {
        try {
            getPerdiemRate();
        } catch (Exception ex) {
            JsfUtil.addFatalMessage("Failed to process! Try again reloading the page");
            ex.printStackTrace();
        }
    }

    //validate address
    public void travel(ValueChangeEvent event) {
        travel = fmsFieldAllowanceEnty.getTravelExpense();
        calculateTotalExpense();
    }

    //value change 
    public void decisionHandler(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedDecision = event.getNewValue().toString();
        }
    }

    //value change event for system Change

    public void SystemChange(ValueChangeEvent event) {
        try {
            if (!event.getNewValue().toString().isEmpty()) {
                fmsLuSystem.setSystemCode(event.getNewValue().toString());
                fmsLuSystem = fmsLuSystemBeanLocal.getSysDetail(fmsLuSystem);
                fmsCostCenter.setSystemId(fmsLuSystem);
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    //value change event forcost center Change
    public void CostCenterChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsCostCenter.setSystemName(event.getNewValue().toString());
        }
    }

    //value change event for generalLChange
    public void getGeneralLedgerChange(ValueChangeEvent event) {
        try {
            if (!event.getNewValue().toString().isEmpty()) {
                fmsGeneralLedger.setGeneralLedgerCode(event.getNewValue().toString());
                fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getGLDetail(fmsGeneralLedger);
                fmsSubsidiaryLedger.setGeneralLedgerId(fmsGeneralLedger);
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    //value change event for SubsidiaryLChange
    public void getSubsidiaryLChange(ValueChangeEvent event) {
        try {
            if (!event.getNewValue().toString().isEmpty()) {
                fmsSubsidiaryLedger.setSubsidiaryId(Integer.parseInt(event.getNewValue().toString()));
                fmsSubsidiaryLedger = subsidiaryLedgerBeanLocal.getSubsidiaryInfo(fmsSubsidiaryLedger);

            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    //value change for handling change system
    public void onChangeSystem(ValueChangeEvent event) {
        try {
            if (event.getNewValue() != null) {
                fmsLuSystem = (FmsLuSystem) event.getNewValue();
                renderJobNo = false;
                if (fmsLuSystem.getType().toString().equals(projectType)) {
                    searchProjectType();
                    renderJobNo = true;
                    sysJobNOList = fmsLuSystem.getFmsSystemJobJunctionList();
                } else {
                    searchNonProjectType();
                }
                ssCcJunctionList = fmsLuSystem.getFmsCostcSystemJunctionList();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

//value change event to select
    public void onSelectFARequest(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                fmsFieldAllowanceEnty = (FmsFieldAllowance) event.getNewValue();
                populateFA();
                saveorUpdateBundle = "Save";
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Faile to process! Try Again Reloading the Page");
        }
    }

    //value change for handling GL

    public void onChangeGeneralLedger(ValueChangeEvent event) {
        try {
            if (!event.getNewValue().toString().isEmpty()) {
                fmsGeneralLedger.setGeneralLedgerCode(event.getNewValue().toString());
                fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getGLDetail(fmsGeneralLedger);
                fmsGeneralLedger = (FmsGeneralLedger) event.getNewValue();
                slList = subsidiaryLedgerBeanLocal.findBySsCcJuncAndGL(fmsCostcSystemJunction, fmsGeneralLedger);
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    //value change for handling subsidery ledger
    public void onChangeSubsidiaryLedger(ValueChangeEvent event) {
        try {
            if (!event.getNewValue().toString().isEmpty()) {
                fmsSubsidiaryLedger = (FmsSubsidiaryLedger) event.getNewValue();
                display_conn = fmsSubsidiaryLedger.getSubsidiaryCode();
                fmsFieldAllowanceEnty.setAccountCode(display_conn);
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    //value change for handling job number
    public void onChangeJobNo(ValueChangeEvent event) {
        try {
            if (event.getNewValue() != null) {
                fmsSystemJobJunction = (FmsSystemJobJunction) event.getNewValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    //select event
    public void selectRequestRow(SelectEvent event) {
        fmsFieldAllowanceEnty = (FmsFieldAllowance) event.getObject();
        populateFA();
        if (workFlow.isPrepareStatus()) {
            if (fmsFieldAllowanceEnty.getWfStatus() == 0 || fmsFieldAllowanceEnty.getWfStatus() == 4 || fmsFieldAllowanceEnty.getWfStatus() == 2) {
                workFlow.setReadonly(false);
            } else {
                workFlow.setReadonly(true);
            }
        }
    }

    //get perdiem rate

    public void getPerdiemRate() {
        try {
            BigDecimal levId = empEnty.getGradeId().getLevelId().getId();
            fmsLuPerdimeRate = foreignBeanLocal.getPer(levId.intValue());
            breakfast = (fmsLuPerdimeRate.getFood() * 0.2);//BREAKFAST
            lunch = (fmsLuPerdimeRate.getFood() * 0.4);//LUNCH
            dinner = (fmsLuPerdimeRate.getFood() * 0.4);//DINNER
            String Rtime = fmsFieldAllowanceEnty.getToTime();
            if (null != Dtime) {
                switch (Dtime) {
                    case "After 2AM":
                        DtimeAmount = lunch + dinner;
                        break;
                    case "After 7PM ":
                        DtimeAmount = dinner;
                        break;
                    case "After 2:30PM":
                        DtimeAmount = 0.0;
                        break;
                }
            }
            if (null != Rtime) {
                switch (Rtime) {
                    case "After 2AM":
                        RtimeAmount = breakfast;
                        break;
                    case "After 7PM":
                        RtimeAmount = breakfast + lunch;
                        break;
                    case "After 12PM":
                        RtimeAmount = breakfast + lunch + dinner;
                        break;
                }
            }
            totalHotelExpense = ((dayDifference - 1) * fmsLuPerdimeRate.getLodging());
            totalMealsExpense = (dayDifference - 2) * fmsLuPerdimeRate.getFood() + DtimeAmount + RtimeAmount;
            fmsFieldAllowanceEnty.setHotelExpense(totalHotelExpense);
            fmsFieldAllowanceEnty.setMealsExpense(totalMealsExpense);
        } catch (Exception ex) {
            JsfUtil.addFatalMessage("Failed to process! Try again reloading the page");
            ex.printStackTrace();
        }
    }

    //calculate
    public int calc() {
        fmsFieldAllowanceEnty.setEmpId(empEnty);
        FmsFieldAllowance des;
        des = fmsFieldAllowansBeanLocal.findEmploye(fmsFieldAllowanceEnty);
        if (fmsFieldAllowansBeanLocal.findEmploye(fmsFieldAllowanceEnty) != null) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");//SimpleDateFormat rdate = new SimpleDateFormat("YYYY-MM-DD");
                String datefrom = format.format(fmsFieldAllowanceEnty.getFromDate());//departure date
                String dateto = format.format(des.getToDate());//return/destination date

                String from2 = datefrom.substring(datefrom.lastIndexOf('-') + 1);
                String to1 = dateto.substring(dateto.lastIndexOf('-') + 1);

                if (Integer.parseInt(to1) > Integer.parseInt(from2)) {
                    dateStatus = 1;
                } else if (Integer.parseInt(to1) < Integer.parseInt(from2)) {
                    dateStatus = 2;
                } else {
                    dateStatus = 0;
                }
            } catch (Exception e) {
            }

        }
        return dateStatus;

    }

    //date comparision
    public int dateCompure() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String tdate = sdf.format(fmsFieldAllowanceEnty.getToDate());
        String fdate = sdf.format(fmsFieldAllowanceEnty.getFromDate());
        Date fromdate = sdf.parse(fdate);
        Date todate = sdf.parse(tdate);
        if (fromdate.after(todate)) {
            dateCompStatus = 1;
        } else if (fromdate.before(todate)) {
            dateCompStatus = 0;
        } else {
            dateCompStatus = 0;
        }
        return dateCompStatus;

    }

    //date diffrence
    public void datdiff(SelectEvent event) throws ParseException {
        SimpleDateFormat simpledate = new SimpleDateFormat("YYYY-MM-DD");
        String y = simpledate.format(fmsFieldAllowanceEnty.getToDate());
        dateCompure();
        if (dateCompStatus == 1) {
            txtRDatevisibility = true;
            txtvisibility = "hidden";
            JsfUtil.addFatalMessage("Invalid Date Selection!");
        } else {
            String day1 = fieldAllowanceEntyFromDate.substring(fieldAllowanceEntyFromDate.lastIndexOf('-') + 1);
            String day2 = y.substring(y.lastIndexOf('-') + 1);

            if (Integer.parseInt(day2) > Integer.parseInt(day1)) {
                day = Integer.parseInt(day2) + 1 - Integer.parseInt(day1);
            } else {
                day = Integer.parseInt(day1) - Integer.parseInt(day2);
            }
            String month1 = fieldAllowanceEntyFromDate.substring(fieldAllowanceEntyFromDate.indexOf('-') + 1, fieldAllowanceEntyFromDate.lastIndexOf('-'));
            String month2 = y.substring(y.indexOf('-') + 1, y.lastIndexOf('-'));
            if (Integer.parseInt(month1) > Integer.parseInt(month2)) {
                monthDifference = Integer.parseInt(month1) - Integer.parseInt(month2);
            } else {
                monthDifference = Integer.parseInt(month2) - Integer.parseInt(month1);
            }
            String year1 = fieldAllowanceEntyFromDate.substring(0, fieldAllowanceEntyFromDate.indexOf('-'));
            String year2 = y.substring(0, y.indexOf('-'));

            if (Integer.parseInt(year1) > Integer.parseInt(year2)) {
                yearDifference = Integer.parseInt(year1) - Integer.parseInt(year2);
            } else {
                yearDifference = Integer.parseInt(year2) - Integer.parseInt(year1);
            }
            dayDifference = yearDifference + monthDifference + day;
            fmsFieldAllowanceEnty.setNoOfDays(dayDifference);
        }
    }

//calculate Total Expense
    public void calculateTotalExpense() {
        try {
            if (updateStatus != 0) {
                totalMealsExpense = fmsFieldAllowanceEnty.getMealsExpense() * fmsFieldAllowanceEnty.getNoOfDays();//Total Meal Expense
                totalHotelExpense = fmsFieldAllowanceEnty.getHotelExpense() * fmsFieldAllowanceEnty.getNoOfDays();//Total Lodging Expense
                travel = fmsFieldAllowanceEnty.getTravelExpense();
                miscellaneousExpense = fmsFieldAllowanceEnty.getMiscellaneousExpense();
                totalExpense = miscellaneousExpense + totalMealsExpense + totalHotelExpense + travel;
                fmsFieldAllowanceEnty.setTotalExpense(totalExpense);
                JsfUtil.addInformationMessage("Total Expense value updated to  " + totalExpense);
            } else {
                totalMealsExpense = fmsFieldAllowanceEnty.getMealsExpense() * fmsFieldAllowanceEnty.getNoOfDays();//Total Meal Expense
                totalHotelExpense = fmsFieldAllowanceEnty.getHotelExpense() * fmsFieldAllowanceEnty.getNoOfDays();//Total Lodging Expense
                travel = fmsFieldAllowanceEnty.getTravelExpense();
                miscellaneousExpense = fmsFieldAllowanceEnty.getMiscellaneousExpense();
                totalExpense = miscellaneousExpense + totalMealsExpense + totalHotelExpense + travel;
                fmsFieldAllowanceEnty.setTotalExpense(totalExpense);
                JsfUtil.addInformationMessage("Total Expense Calculated!");

            }
        } catch (Exception e) {
        }
    }

    //total expense
    public void totalExpense() {
        calculateTotalExpense();
    }

    //validate address
    public void validateAddress() {
        if (fmsFieldAllowanceEnty.getDepartureAddressId() == null && fmsFieldAllowanceEnty.getReturnedAddressId() == null) {
            JsfUtil.addFatalMessage("Add Departure & Destination Address!");
        } else if (fmsFieldAllowanceEnty.getDepartureAddressId() == null) {
            JsfUtil.addFatalMessage("Add Departure Address!");
        } else if (fmsFieldAllowanceEnty.getReturnedAddressId() == null) {
            JsfUtil.addFatalMessage("Add Destination Address!");
        } else if (fmsFieldAllowanceEnty.getDepartureAddressId().equals(fmsFieldAllowanceEnty.getReturnedAddressId())) {
            JsfUtil.addFatalMessage("Destination Address Shoud be Differernt from Departure Address");
        }
    }

    //save
    public void saveFieldAllowanceLocal() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(SessionBean.getUserName(), "saveFieldAllowanceLocal", dataset)) {
            fmsFieldAllowanceEnty.setEmpId(empEnty);
            wfFcmsProcessed.setPerdiemRequestLocalId(fmsFieldAllowanceEnty);
            wfFcmsProcessed.setProcessedOn(wfFcmsProcessed.getProcessedOn());
            wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
            wfFcmsProcessed.setDecision(fmsFieldAllowanceEnty.getWfStatus());
            fmsFieldAllowanceEnty.setRequestNo(requestNumber);
            fmsFieldAllowanceEnty.setAccountCode(display_conn);
            try {
                validateAddress();
                if (updateStatus == 0) {
                    if (fmsFieldAllowanceEnty.getAccountCode() == null) {
                        JsfUtil.addFatalMessage("Add Account code.");
                    }
                    fmsFieldAllowanceEnty.setStatus("Requested");
                    fmsFieldAllowanceEnty.setPreparedBy(wfFcmsProcessed.getProcessedBy().toString());
                    fmsFieldAllowanceEnty.setWfStatus(Constants.PREPARE_VALUE);
                    wfFcmsProcessed.setDecision(fmsFieldAllowanceEnty.getWfStatus());
                    fmsFieldAllowansBeanLocal.create(fmsFieldAllowanceEnty);
                    wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                    JsfUtil.addSuccessMessage("Saved Successfully!");
                    clearPopup();
                    generateReqNo();
                } else if (updateStatus == 1 && fmsFieldAllowanceEnty.getStatus().equalsIgnoreCase("Settled")) {
                    JsfUtil.addFatalMessage("Ms/Mr " + fmsFieldAllowanceEnty.getEmpId().getFirstName() + " is Already Returned, Update is not allowed");
                } else {//update
                    if (workFlow.isPrepareStatus() && workFlow.isReadonly() == false) {
                        fmsFieldAllowansBeanLocal.edit(fmsFieldAllowanceEnty);
                        JsfUtil.addSuccessMessage("Updated Succesfully!");
                        clearPopup();
                        generateReqNo();
                    } else if (workFlow.isApproveStatus() || workFlow.isCheckStatus()) {
                        if (selectedDecision.equals("Approved") && workFlow.isApproveStatus()) {
                            fmsFieldAllowanceEnty.setWfStatus(Constants.APPROVE_VALUE);
                            wfFcmsProcessed.setDecision(Constants.APPROVE_VALUE);
                        } else if (selectedDecision.equals("Rejected") && workFlow.isApproveStatus()) {
                            fmsFieldAllowanceEnty.setWfStatus(Constants.APPROVE_REJECT_VALUE);
                            wfFcmsProcessed.setDecision(Constants.APPROVE_REJECT_VALUE);
                        } else if (selectedDecision.equals("Approved") && workFlow.isCheckStatus()) {
                            fmsFieldAllowanceEnty.setWfStatus(Constants.CHECK_APPROVE_VALUE);
                            wfFcmsProcessed.setDecision(Constants.CHECK_APPROVE_VALUE);
                        } else if (selectedDecision.equals("Rejected") && workFlow.isCheckStatus()) {
                            fmsFieldAllowanceEnty.setWfStatus(Constants.CHECK_REJECT_VALUE);
                            wfFcmsProcessed.setDecision(Constants.CHECK_REJECT_VALUE);
                        }
                        fmsFieldAllowansBeanLocal.edit(fmsFieldAllowanceEnty);
                        wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                        fmsFAList.remove(fmsFieldAllowanceEnty);
                        JsfUtil.addSuccessMessage("Saved Succesfully!");
                        clearPopup();
                        requestNumber = "";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                JsfUtil.addFatalMessage("Faild to process! Try again reloading the page.");
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

    //search local
    public void findFALocal() {
        fieldAllowanceList = new ArrayList<>();
        //search by Emp Name only
        if (!(empEnty.getFirstName().isEmpty()) && empEnty.getEmpId().isEmpty()) {
            fieldAllowanceList = fmsFieldAllowansBeanLocal.searchEmpByEmpName(empEnty);
            //search by EmpId only
        } else if (empEnty.getFirstName().isEmpty() && (!empEnty.getEmpId().isEmpty())) {
            fieldAllowanceList = fmsFieldAllowansBeanLocal.searchEmployeeByEmpId(empEnty);
            //search All Emp
        } else {
            fieldAllowanceList = fmsFieldAllowansBeanLocal.searchAllEmployee();
        }
        fieldAllowanceDataModel = new ListDataModel(fieldAllowanceList);
    }

    //concatination
    public void Concatenate3() {
        for (int i = 0; i < fieldAllowanceList.size(); i++) {
            String accountCode[] = fieldAllowanceList.get(i).getAccountCode().split("-");
            fmsLuSystem.setSystemId(Integer.parseInt(accountCode[0]));
            fmsLuSystem = fmsLuSystemBeanLocal.getSystembyId(fmsLuSystem);
            String CC_display = "";
            fmsCostCenter.setCostCenterId(Integer.parseInt(accountCode[1]));
            CC_display = fmsCostCenter.getSystemName();
            String SL_display = "SL";
            fmsGeneralLedger.setGeneralLedgerId(Integer.parseInt(accountCode[2]));
            fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getByGlId(fmsGeneralLedger);
            if (!"SL".equals(accountCode[3])) {
                fmsSubsidiaryLedger.setSubsidiaryId(Integer.parseInt(accountCode[3]));
                fmsSubsidiaryLedger = subsidiaryLedgerBeanLocal.getSubsidiaryInfo(fmsSubsidiaryLedger);
                SL_display = fmsSubsidiaryLedger.getSubsidiaryCode();
            }
            display_conn = fmsLuSystem.getSystemCode() + "-" + CC_display + "-" + fmsGeneralLedger.getGeneralLedgerCode() + "-" + SL_display;
            fmsFieldAllowanceEnty.setDisplay_conn(display_conn);
            fieldAllowanceList.get(i).setDisplay_conn(display_conn);
        }
    }

    //concatination
    public void Concatenate1() {
        String accountCode[] = fmsFieldAllowanceEnty.getAccountCode().split("-");
        fmsLuSystem.setSystemId(Integer.parseInt(accountCode[0]));
        fmsLuSystem = fmsLuSystemBeanLocal.getSystembyId(fmsLuSystem);
        String CC_display = "CC";
        String SL_display = "SL";

        fmsCostCenter.setCostCenterId(Integer.parseInt(accountCode[1]));
        CC_display = fmsCostCenter.getSystemName();

        fmsGeneralLedger.setGeneralLedgerId(Integer.parseInt(accountCode[2]));
        fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getByGlId(fmsGeneralLedger);

        if (!"SL".equals(accountCode[0])) {
            fmsSubsidiaryLedger.setSubsidiaryId(Integer.parseInt(accountCode[3]));
            fmsSubsidiaryLedger = subsidiaryLedgerBeanLocal.getSubsidiaryInfo(fmsSubsidiaryLedger);
            SL_display = fmsSubsidiaryLedger.getSubsidiaryCode();
        }
        concatination2 = fmsLuSystem.getSystemCode() + "-" + CC_display + "-" + fmsGeneralLedger.getGeneralLedgerCode() + "-" + SL_display;
        fmsFieldAllowanceEnty.setDisplay_conn(concatination2);
    }

    //populate
    public void populateFA() {
        try {
            fmsFieldAllowanceEnty2 = fmsFieldAllowanceEnty;
            empEnty = fmsFieldAllowanceEnty.getEmpId();
            requestNumber = fmsFieldAllowanceEnty.getRequestNo();
            renderBtnPrint = true;
            hrAddresses = fmsFieldAllowanceEnty.getReturnedAddressId();//Destination Address
            hrAddresses2 = fmsFieldAllowanceEnty.getDepartureAddressId();//Departure Address
            setAllAddressUnitAsOne2(hrAddresses2.getAddressName());// use search method to get value by address id from address tabl
            setAllAddressUnitAsOne(hrAddresses.getAddressName());
            renderPnlCreatePerDiemLocal = true;
            renderPnlManPage = false;
            isRenderedIconWorkFlow = true;
            disableBtnCreate = true;
            updateStatus = 1;
            saveorUpdateBundle = "Update";
            wfFcmsProcessed.setProcessedOn(wfFcmsProcessed.getProcessedOn());
            recreateWfDataModel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //recreate assign WfFcmsProcessedList to wfFcmsProcessedDataModel 
    public void recreateWfDataModel() {
        wfFcmsProcessedDataModel = null;
        wfFcmsProcessedDataModel = new ListDataModel(fmsFieldAllowanceEnty.getWfFcmsProcessedList());
    }

    //find all from fmsGeneralLedgerBeanLocal
    public SelectItem[] getGLdata() {
        return JsfUtil.getSelectItems(fmsGeneralLedgerBeanLocal.findAll(), true);
    }

    //find all from fmsLuSystemBeanLocal
    public SelectItem[] getListSys() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findAll(), true);
    }

    //find all from subsidiaryLedgerBeanLocal
    public SelectItem[] getSubList() {
        return JsfUtil.getSelectItems(subsidiaryLedgerBeanLocal.findAll(), true);
    }

    //select item for sub ledger
    public SelectItem[] findListSubL() {
        ArrayList<FmsSubsidiaryLedger> subsidaryList = new ArrayList<>();
        if (fmsGeneralLedger != null) {
            SelectItem[] listSl = null;
            subsidaryList = subsidiaryLedgerBeanLocal.findSubLedger(fmsGeneralLedger);
            if (subsidaryList.size() > 0) {
                listSl = new SelectItem[subsidaryList.size() + 1];
                listSl[0] = new SelectItem(null, "--- Select One ---");
                for (int i = 0; i < subsidaryList.size(); i++) {
                    listSl[i + 1] = new SelectItem(subsidaryList.get(i).getSubsidiaryId(), subsidaryList.get(i).getSubsidiaryCode());
                }
            }
            return listSl;
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--- Select One ---");
            return items;
        }
    }

    //add account detail
    public String addAccountInfoDetail() {
        try {
            display_conn = fmsSubsidiaryLedger.getSubsidiaryCode();
            fmsFieldAllowanceEnty.setAccountCode(display_conn);//to be saved
            fmsFieldAllowanceEnty.setDisplay_conn(display_conn);//to display
        } catch (Exception e) {
        }
        return null;
    }

    //concatination
    public void Concatenate() {
        try {
            String CC = "CC";
            String SL = "SL";
            String CC_display = "CC";
            String SL_display = "SL";

            if (fmsCostCenter.getCostCenterId() != null) {
                CC = fmsCostCenter.getSystemName();
                CC_display = fmsCostCenter.getCostCenterId().toString();
            }
            if (fmsSubsidiaryLedger.getSubsidiaryCode() != null) {
                SL = fmsSubsidiaryLedger.getSubsidiaryCode();
                SL_display = fmsSubsidiaryLedger.getSubsidiaryId().toString();

            }
            display_conn = fmsLuSystem.getSystemCode() + "-" + CC + "-" + fmsSubsidiaryLedger.getGeneralLedgerId().getGeneralLedgerCode() + "-" + SL;
            concatination2 = fmsLuSystem.getSystemId() + "-" + CC_display + "-" + fmsSubsidiaryLedger.getGeneralLedgerId().getGeneralLedgerId() + "-" + SL_display;
        } catch (Exception e) {
        }
    }

    //load address
    public void loadAddressTree() {
        allAddresses = hrAddressesBeanIntegrationLocal.findAllAddressUnit();
        addressRoot = new DefaultTreeNode("Root", null);
        populateAddressNode(allAddresses, 0, addressRoot);
    }

    //load address2
    public void loadAddressTree2() {
        allAddresses = hrAddressesBeanIntegrationLocal.findAllAddressUnit();
        addressRoot2 = new DefaultTreeNode("Root", null);
        populateAddressNode(allAddresses, 0, addressRoot2);
    }

    //populat
    public void populateAddressNode(List<HrAddresses> addressData, int id, TreeNode node) {
        addresses = new ArrayList<>();
        try {

            for (HrAddresses k : getAllAddresses()) {
                if (k.getParentId() == id) {
                    TreeNode childNode = new DefaultTreeNode(k.getAddressDescription() + "=>" + k.getAddressId(), node);
                    addresses.add(k);
                    populateAddressNode(addresses, k.getAddressId(), childNode);
                }
            }
        } catch (Exception e) {
        }
    }

    //<!--Departure Address-->
    public void onAddressNodeSelect2(NodeSelectEvent event) {
        addressSelectedNode2 = event.getTreeNode();
        addressId2 = Integer.parseInt((addressSelectedNode2.getData().toString()).split("=>")[1]);
        hrAddresses2.setAddressId(addressId2);
        hrAddresses2 = hrAddressesBeanIntegrationLocal.findAllAddressUnitAsOne(hrAddresses2);
        setAllAddressUnitAsOne2(hrAddresses2.getAddressName());
        fmsFieldAllowanceEnty.setDepartureAddressId(hrAddresses2);
    }

    // <!--Destination Address--> Return Address-->
    public void onAddressNodeSelect(NodeSelectEvent event) {
        addressSelectedNode = event.getTreeNode();
        addressId = Integer.parseInt((addressSelectedNode.getData().toString()).split("=>")[1]);
        hrAddresses.setAddressId(addressId);
        hrAddresses = hrAddressesBeanIntegrationLocal.findAllAddressUnitAsOne(hrAddresses);
        setAllAddressUnitAsOne(hrAddresses.getAddressName());
        fmsFieldAllowanceEnty.setReturnedAddressId(hrAddresses);
    }

    //<editor-fold defaultstate="collapsed" desc="Chart of Account Handler: Account Code Handler. Author: Mubarek Shejebel">
    //search project type
    public void searchProjectType() {
        glList = fmsGeneralLedgerBeanLocal.getByAccountType(projectType);
    }

    //search non project type
    public void searchNonProjectType() {
        glList = fmsGeneralLedgerBeanLocal.getByAccountType(nonProjectType);
    }

    //value change for handling cost center
    public void onChangeCostCenter(ValueChangeEvent event) {
        try {
            if (event.getNewValue() != null) {
                fmsCostcSystemJunction = (FmsCostcSystemJunction) event.getNewValue();
                fmsCostCenter = fmsCostcSystemJunction.getFmsCostCenter();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    //clear
    public void clearAccCodes() {
        fmsLuSystem = new FmsLuSystem();
        fmsGeneralLedger = new FmsGeneralLedger();
        fmsCostCenter = new FmsCostCenter();
        fmsSystemJobJunction = new FmsSystemJobJunction();
        fmsCostcSystemJunction = new FmsCostcSystemJunction();
        fmsSubsidiaryLedger = new FmsSubsidiaryLedger();
    }
//</editor-fold>
    //clear

    public void clearPopup() {
        fmsFieldAllowanceEnty = null;
        allAddressUnitAsOne2 = null;
        allAddressUnitAsOne = null;
        hrAddresses = null;
        empEnty = null;
        wfFcmsProcessed = null;
        saveorUpdateBundle = "Save";
        selectedDecision = "";
    }

    //clear
    private void clearPage() {
        fmsFieldAllowanceEnty = null;
        fieldAllowanceDataModel = null;
        empEnty = null;
        updateStatus = 0;
        saveorUpdateBundle = "Save";
    }

    //create and search render
    public void createNewPerdiemLocal() {
        clearPage();
        disableBtnCreate = false;
        renderPnlCreatePerDiemLocal = true;
        renderPnlManPage = false;
    }
}
