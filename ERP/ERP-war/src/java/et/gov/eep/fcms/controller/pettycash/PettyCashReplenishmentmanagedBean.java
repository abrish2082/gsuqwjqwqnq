/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.pettycash;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.primefaces.convert.NumberConverter;
import org.primefaces.event.SelectEvent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.businessLogic.WfFcmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.pettyCash.FmsCasherAccountBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.pettyCash.FmsDailyCashRegisterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.businessLogic.pettyCash.FmsPettyCashReplenishmentBeanLocal;
import et.gov.eep.fcms.entity.pettyCash.FmsCasherAccount;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.pettyCash.FmsDailyCashRegister;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.entity.pettyCash.FmsPettyCashReplenishDtl;
import et.gov.eep.fcms.entity.pettyCash.FmsPettyCashReplenishment;
import et.gov.eep.hrms.entity.employee.HrEmployees;

/**
 *
 * @author Mubarek jebel
 */
@Named
@ViewScoped
public class PettyCashReplenishmentmanagedBean implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="@Inject and @EJB">
    //Inject entities
    @Inject
    SessionBean SessionBean;
    @Inject
    private FmsPettyCashReplenishment fmsPettyCashReplenishment;
    @Inject
    private FmsPettyCashReplenishDtl fmsPettyCashReplenishDtl;
    @Inject
    private FmsGeneralLedger generalLedger;
    @Inject
    private FmsSubsidiaryLedger subsidiaryLedger;
    @Inject
    private FmsCostCenter fmsCostCenter;
    @Inject
    FmsLuSystem fmsLuSystem;
    @Inject
    FmsVoucher fmsVoucher;
    @Inject
    private FmsDailyCashRegister dailycashRegister;
    @Inject
    FmsSubsidiaryLedger fmsSubsid1aryLedger1;
    @Inject
    FmsCasherAccount fmsCasherAccount;
    @Inject
    WorkFlow workFlow;
    @Inject
    private WfFcmsProcessed wfFcmsProcessed;
    @Inject
    private HrEmployees empEnty;
    //@EJB
    @EJB
    private FmsLuSystemBeanLocal fmsLuSystemBean;
    @EJB
    private FmsPettyCashReplenishmentBeanLocal fmsPettyCashReplenishmentBeanLocal;
    @EJB
    FmsCasherAccountBeanLocal fmsCasherAccountBeanLocal;
    @EJB
    FmsCostCenterBeanLocal fmsCostCenterBeanLocal;
    @EJB
    private FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    private subsidiaryBeanLocal subsidiaryLedgerBeanLocal;
    @EJB
    private FmsDailyCashRegisterBeanLocal cashRegisterBeanLocal;
    @EJB
    private WfFcmsProcessedBeanLocal wfFcmsProcessedBeanLocal;
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    FmsPettyCashReplenishment pettyCashReplenishmentObj;
    FmsPettyCashReplenishment pettyCashReplenishmentObj2;
    private FmsPettyCashReplenishment selectPCRRequest;
    DataModel<FmsPettyCashReplenishment> fmsPCRDataModel;
    DataModel<FmsDailyCashRegister> pettyCashPaymentDataModel;
    private DataModel<WfFcmsProcessed> wfFcmsProcessedDataModel;
    List<FmsDailyCashRegister> listdaily = new ArrayList<>();
    List<FmsDailyCashRegister> listTodatModel;
    List<FmsCasherAccount> cashierList;
    List<FmsPettyCashReplenishment> pcrList;
    private List<FmsPettyCashReplenishment> fmsPCRList;
    private List<WfFcmsProcessed> wfFcmsProcessedList;
    boolean isDisabledBtnSave = true;
    boolean optionRendered = false;
    boolean renderBtnReplanish = false;
    private boolean isRenderDecision = false;
    private boolean isRenderedIconNitification = false;
    private boolean isRenderedIconWorkFlow = false;
    private boolean isRenderedIconPrint = false;
    private boolean isRenderedBtnNew = false;
    private boolean renderPnlCreatePcRepReq = false;
    private boolean renderPnlManPage = true;
    private boolean isSticky = false;
    private BigDecimal totalEstablished = new BigDecimal(0.00);
    private BigDecimal totalPaid = new BigDecimal(0.00);
    private BigDecimal totalPaidAmt = new BigDecimal(0.00);
    private BigDecimal cashOnHand = new BigDecimal(0.00);
    private BigDecimal cashOnHandAmt = new BigDecimal(0.00);
    private BigDecimal cashOnHandOneThird = new BigDecimal(0.00);
    BigDecimal oneThirdValue = new BigDecimal(0.3333333333333333);
    Double reimbursedAmount = 0.00;
    Double reimbursedAmountinter = 0.00;
    int updateStatus = 0;
    int dailyCashRegStatus = -1;
    int authorizedStatus;
    String selectedDecision = "";
    String userName;
    private String saveorUpdateBundle = "Save";
    private String Conc2 = "";
    Date dateprocessedOn;
    private NumberConverter numberConverter = new NumberConverter();
//</editor-fold>

    public PettyCashReplenishmentmanagedBean() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(3);
    }

    @PostConstruct
    public void init() {
        userName = SessionBean.getUserName();
        workFlow.setPrepareStatus(true);//this is a temporery  preparer value
        if (workFlow.isPrepareStatus()) {//Preparer or requester
            empEnty.setEmpId("ggg1234");//getEmpId from SessionBean and setEmpId to empEnty Ex. EmpId= ggg1234 is for  Alemu Tadese from INSA Dep't
            fmsCasherAccount = fmsCasherAccountBeanLocal.getCashierAccByEmpId(empEnty);
            wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
            isRenderDecision = false;
            getCashierBalance();
            if (cashOnHand.doubleValue() <= cashOnHandOneThird.doubleValue() && pettyCashReplenishmentObj2 == null) {//IF cashOnHand<= 1/3*totalEstablished AND IF THERE IS NO REQUEST TO BE PROCESSED
                isRenderedBtnNew = true;
                setIsDisabledBtnSave(false);
            } else if (cashOnHand.doubleValue() <= cashOnHandOneThird.doubleValue() && pettyCashReplenishmentObj2 != null && pettyCashReplenishmentObj2.getWfStatus() != Constants.AUTHORIZED) {//IF cashOnHand<= 1/3*totalEstablished AND IF THERE IS REQUEST WHICH IS NOT_AUTHORIZED OR NOTT_REPLENISHED AND IF THE REQUEST IS BEING PROCESSED
                isRenderedBtnNew = false;
                setIsDisabledBtnSave(true);
            } else {
                isRenderedBtnNew = false;
                setIsDisabledBtnSave(true);
            }
            fmsPCRList = fmsPettyCashReplenishmentBeanLocal.findPCRByCashierIdAndWfCheckRejectValue(fmsCasherAccount, Constants.CHECK_REJECT_VALUE);//chacked rejected list
            if (fmsPCRList.size() > 0) {
                isRenderedIconNitification = true;
            }
            findPCRForCheckerAndApprover();// find replenish't list for search page
        } else if (workFlow.isCheckStatus()) {//Checker
            isRenderedIconNitification = true;
            isRenderDecision = true;
            isRenderedBtnNew = false;
            renderPnlManPage = true;
            renderPnlCreatePcRepReq = false;
            dailyCashRegStatus = Constants.PREPARE_VALUE;// 0 to be Checked
            fmsPCRList = fmsPettyCashReplenishmentBeanLocal.findPCRByWfStatus(Constants.PREPARE_VALUE);//to be chacked list
        } else if (workFlow.isApproveStatus()) {//Approver
            isRenderedIconNitification = true;
            isRenderDecision = true;
            isRenderedBtnNew = false;
            renderPnlManPage = true;
            renderPnlCreatePcRepReq = false;
            dailyCashRegStatus = Constants.CHECK_APPROVE_VALUE;//3 to be Approved
            fmsPCRList = fmsPettyCashReplenishmentBeanLocal.findPCRByWfStatus(Constants.CHECK_APPROVE_VALUE);//to be approved List
        } else if (workFlow.isAuthorizeStatus()) {//AUTHORIZER
            isRenderedIconNitification = true;
            isRenderDecision = true;
            isRenderedBtnNew = false;
            isRenderedIconPrint = true;
            renderPnlManPage = true;
            renderPnlCreatePcRepReq = false;
            dailyCashRegStatus = Constants.APPROVE_VALUE;//TO BE AUTHORIZED = 10
            fmsPCRList = fmsPettyCashReplenishmentBeanLocal.findPCRByWfStatus(Constants.APPROVE_VALUE);//to be AUTHORIZED List
        }
    }
//<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public FmsPettyCashReplenishment getFmsPettyCashReplenishment() {
        if (fmsPettyCashReplenishment == null) {
            fmsPettyCashReplenishment = new FmsPettyCashReplenishment();
        }
        return fmsPettyCashReplenishment;
    }

    public void setFmsPettyCashReplenishment(FmsPettyCashReplenishment fmsPettyCashReplenishment) {
        this.fmsPettyCashReplenishment = fmsPettyCashReplenishment;
    }

    public FmsPettyCashReplenishDtl getFmsPettyCashReplenishDtl() {
        if (fmsPettyCashReplenishDtl == null) {
            fmsPettyCashReplenishDtl = new FmsPettyCashReplenishDtl();
        }
        return fmsPettyCashReplenishDtl;
    }

    public void setFmsPettyCashReplenishDtl(FmsPettyCashReplenishDtl fmsPettyCashReplenishDtl) {
        this.fmsPettyCashReplenishDtl = fmsPettyCashReplenishDtl;
    }

    public List<FmsPettyCashReplenishment> getFmsPCRList() {
        if (fmsPCRList == null) {
            fmsPCRList = new ArrayList<>();
        }
        return fmsPCRList;
    }

    public void setFmsPCRList(List<FmsPettyCashReplenishment> fmsPCRList) {
        this.fmsPCRList = fmsPCRList;
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
        return wfFcmsProcessedList;
    }

    public void setWfFcmsProcessedList(List<WfFcmsProcessed> wfFcmsProcessedList) {
        this.wfFcmsProcessedList = wfFcmsProcessedList;
    }

    public boolean isIsRenderDecision() {
        return isRenderDecision;
    }

    public void setIsRenderDecision(boolean isRenderDecision) {
        this.isRenderDecision = isRenderDecision;
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

    public boolean isIsRenderedIconPrint() {
        return isRenderedIconPrint;
    }

    public void setIsRenderedIconPrint(boolean isRenderedIconPrint) {
        this.isRenderedIconPrint = isRenderedIconPrint;
    }

    public boolean isIsRenderedBtnNew() {
        return isRenderedBtnNew;
    }

    public void setIsRenderedBtnNew(boolean isRenderedBtnNew) {
        this.isRenderedBtnNew = isRenderedBtnNew;
    }

    public boolean isRenderPnlCreatePcRepReq() {
        return renderPnlCreatePcRepReq;
    }

    public void setRenderPnlCreatePcRepReq(boolean renderPnlCreatePcRepReq) {
        this.renderPnlCreatePcRepReq = renderPnlCreatePcRepReq;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isIsSticky() {
        return isSticky;
    }

    public void setIsSticky(boolean isSticky) {
        this.isSticky = isSticky;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSelectedDecision() {
        return selectedDecision;
    }

    public void setSelectedDecision(String selectedDecision) {
        this.selectedDecision = selectedDecision;
    }

    public String getConc2() {
        return Conc2;
    }

    public void setConc2(String Conc2) {
        this.Conc2 = Conc2;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public FmsSubsidiaryLedger getFmsSubsid1aryLedger1() {
        return fmsSubsid1aryLedger1;
    }

    public void setFmsSubsid1aryLedger1(FmsSubsidiaryLedger fmsSubsid1aryLedger1) {
        this.fmsSubsid1aryLedger1 = fmsSubsid1aryLedger1;
    }

    public FmsCostCenterBeanLocal getFmsCostCenterBeanLocal() {
        return fmsCostCenterBeanLocal;
    }

    public void setFmsCostCenterBeanLocal(FmsCostCenterBeanLocal fmsCostCenterBeanLocal) {
        this.fmsCostCenterBeanLocal = fmsCostCenterBeanLocal;
    }

    public FmsGeneralLedgerBeanLocal getFmsGeneralLedgerBeanLocal() {
        return fmsGeneralLedgerBeanLocal;
    }

    public void setFmsGeneralLedgerBeanLocal(FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal) {
        this.fmsGeneralLedgerBeanLocal = fmsGeneralLedgerBeanLocal;
    }

    public subsidiaryBeanLocal getSubsidiaryLedgerBeanLocal() {
        return subsidiaryLedgerBeanLocal;
    }

    public void setSubsidiaryLedgerBeanLocal(subsidiaryBeanLocal subsidiaryLedgerBeanLocal) {
        this.subsidiaryLedgerBeanLocal = subsidiaryLedgerBeanLocal;
    }

    public FmsGeneralLedger getGeneralLedger() {
        return generalLedger;
    }

    public void setGeneralLedger(FmsGeneralLedger generalLedger) {
        this.generalLedger = generalLedger;
    }

    public FmsSubsidiaryLedger getSubsidiaryLedger() {
        return subsidiaryLedger;
    }

    public void setSubsidiaryLedger(FmsSubsidiaryLedger subsidiaryLedger) {
        this.subsidiaryLedger = subsidiaryLedger;
    }

    public boolean getOptionRendered() {
        return optionRendered;
    }

    public void setOptionRendered(boolean optionRendered) {
        this.optionRendered = optionRendered;
    }

    public boolean isRenderBtnReplanish() {
        return renderBtnReplanish;
    }

    public void setRenderBtnReplanish(boolean renderBtnReplanish) {
        this.renderBtnReplanish = renderBtnReplanish;
    }

    public Double getReimbursedAmount() {
        return reimbursedAmount;
    }

    public void setReimbursedAmount(Double reimbursedAmount) {
        this.reimbursedAmount = reimbursedAmount;
    }

    public Double getReimbursedAmountinter() {
        return reimbursedAmountinter;
    }

    public void setReimbursedAmountinter(Double reimbursedAmountinter) {
        this.reimbursedAmountinter = reimbursedAmountinter;
    }

    public FmsCasherAccount getFmsCasherAccount() {
        if (fmsCasherAccount == null) {
            fmsCasherAccount = new FmsCasherAccount();
        }
        return fmsCasherAccount;
    }

    public void setFmsCasherAccount(FmsCasherAccount fmsCasherAccount) {
        this.fmsCasherAccount = fmsCasherAccount;
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

    public HrEmployees getEmpEnty() {
        if (empEnty == null) {
            empEnty = new HrEmployees();
        }
        return empEnty;
    }

    public void setEmpEnty(HrEmployees empEnty) {
        this.empEnty = empEnty;
    }

    public FmsCostCenter getFmsCostCenter() {
        return fmsCostCenter;
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

    public void setFmsCostCenter(FmsCostCenter fmsCostCenter) {
        this.fmsCostCenter = fmsCostCenter;
    }

    public FmsLuSystemBeanLocal getFmsLuSystemBean() {
        return fmsLuSystemBean;
    }

    public void setFmsLuSystemBean(FmsLuSystemBeanLocal fmsLuSystemBean) {
        this.fmsLuSystemBean = fmsLuSystemBean;
    }

    public FmsLuSystem getFmsLuSystem() {
        return fmsLuSystem;
    }

    public void setFmsLuSystem(FmsLuSystem fmsLuSystem) {
        this.fmsLuSystem = fmsLuSystem;
    }

    public DataModel<FmsDailyCashRegister> getPettyCashPaymentDataModel() {
        if (pettyCashPaymentDataModel == null) {
            pettyCashPaymentDataModel = new ListDataModel<>();
        }
        return pettyCashPaymentDataModel;
    }

    public void setPettyCashPaymentDataModel(DataModel<FmsDailyCashRegister> pettyCashPaymentDataModel) {
        this.pettyCashPaymentDataModel = pettyCashPaymentDataModel;
    }

    public FmsVoucher getFmsVoucher() {
        return fmsVoucher;
    }

    public void setFmsVoucher(FmsVoucher fmsVoucher) {
        this.fmsVoucher = fmsVoucher;
    }

    public List<FmsCasherAccount> getCashierList() {
        cashierList = fmsCasherAccountBeanLocal.findAllCash();
        return cashierList;
    }

    public void setCashierList(List<FmsCasherAccount> cashierList) {
        this.cashierList = cashierList;
    }

    public FmsDailyCashRegister getDailycashRegister() {
        if (dailycashRegister == null) {
            dailycashRegister = new FmsDailyCashRegister();
        }
        return dailycashRegister;
    }

    public void setDailycashRegister(FmsDailyCashRegister dailycashRegister) {
        this.dailycashRegister = dailycashRegister;
    }

    public boolean isIsDisabledBtnSave() {
        return isDisabledBtnSave;
    }

    public void setIsDisabledBtnSave(boolean isDisabledBtnSave) {
        this.isDisabledBtnSave = isDisabledBtnSave;
    }

    public List<FmsDailyCashRegister> getListTodatModel() {

        if (listTodatModel == null) {
            listTodatModel = new ArrayList<>();
        }
        return listTodatModel;
    }

    public void setListTodatModel(List<FmsDailyCashRegister> listTodatModel) {
        this.listTodatModel = listTodatModel;
    }

    public DataModel<FmsPettyCashReplenishment> getFmsPCRDataModel() {
        if (fmsPCRDataModel == null) {
            fmsPCRDataModel = new ListDataModel<>();
        }
        return fmsPCRDataModel;
    }

    public void setFmsPCRDataModel(DataModel<FmsPettyCashReplenishment> fmsPCRDataModel) {
        this.fmsPCRDataModel = fmsPCRDataModel;
    }

    public FmsPettyCashReplenishment getPettyCashReplenishmentObj() {
        if (pettyCashReplenishmentObj == null) {
            pettyCashReplenishmentObj = new FmsPettyCashReplenishment();
        }
        return pettyCashReplenishmentObj;
    }

    public void setPettyCashReplenishmentObj(FmsPettyCashReplenishment pettyCashReplenishmentObj) {
        this.pettyCashReplenishmentObj = pettyCashReplenishmentObj;
    }

    public FmsPettyCashReplenishment getPettyCashReplenishmentObj2() {
        if (pettyCashReplenishmentObj2 == null) {
            pettyCashReplenishmentObj2 = new FmsPettyCashReplenishment();
        }
        return pettyCashReplenishmentObj2;
    }

    public void setPettyCashReplenishmentObj2(FmsPettyCashReplenishment pettyCashReplenishmentObj2) {
        this.pettyCashReplenishmentObj2 = pettyCashReplenishmentObj2;
    }

    public FmsPettyCashReplenishment getSelectPCRRequest() {
        return selectPCRRequest;
    }

    public void setSelectPCRRequest(FmsPettyCashReplenishment selectPCRRequest) {
        this.selectPCRRequest = selectPCRRequest;
    }

    public BigDecimal getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(BigDecimal totalPaid) {
        this.totalPaid = totalPaid;
    }

    public BigDecimal getCashOnHand() {
        return cashOnHand;
    }

    public void setCashOnHand(BigDecimal cashOnHand) {
        this.cashOnHand = cashOnHand;
    }
//</editor-fold>

    //update daily cash register status
    public void updateDailyCashRegisterStatus() {
        for (FmsDailyCashRegister listdaily1 : listdaily) {
            FmsDailyCashRegister fmdcr = new FmsDailyCashRegister();
            fmdcr.setId(listdaily1.getId());
            fmdcr.setPettyCashId(listdaily1.getPettyCashId());
            fmdcr.setChasherId(listdaily1.getChasherId());
            fmdcr.setAmount(listdaily1.getAmount());
            fmdcr.setRegistrationDate(listdaily1.getRegistrationDate());
            if (workFlow.isPrepareStatus()) {
                fmdcr.setStatus(Constants.PREPARE_VALUE);// 0 REQUESTED/ FOR REPLENISHMENT
                fmsPettyCashReplenishment.setPreparedDate(dateprocessedOn);
                fmsPettyCashReplenishment.setPreparedRemark(wfFcmsProcessed.getCommentGiven());
                setIsDisabledBtnSave(true);
            } else if (workFlow.isCheckStatus() && selectedDecision.equals("Approved")) {
                fmdcr.setStatus(Constants.CHECK_APPROVE_VALUE);// 1 CHECKED NOT REPLENISHED
            } else if (workFlow.isCheckStatus() && selectedDecision.equals("Rejected")) {
                fmdcr.setStatus(Constants.CHECK_REJECT_VALUE);// 2 CHECK_REJECT_VALUE NOT REPLENISHED
            } else if (workFlow.isApproveStatus() && selectedDecision.equals("Approved")) {
                fmdcr.setStatus(Constants.APPROVE_VALUE);//3 APPROVE_VALUE
            } else if (workFlow.isApproveStatus() && selectedDecision.equals("Rejected")) {
                fmdcr.setStatus(Constants.APPROVE_REJECT_VALUE);//4 APPROVE_REJECT_VALUE
            } else if (workFlow.isAuthorizeStatus() && selectedDecision.equals("Approved")) {
                fmdcr.setStatus(Constants.AUTHORIZED);//10 AUTHORIZED AND/OR REPLENISHED
            } else if (workFlow.isAuthorizeStatus() && selectedDecision.equals("Rejected")) {
                fmdcr.setStatus(Constants.AUTHORIZED_REJECT_VALUE);//11 AUTHORIZED_REJECT_VALUE 
            }
            cashRegisterBeanLocal.edit(fmdcr);
        }
    }

    //save
    public void saveFcmsPCReplanishment() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveFcmsPCReplanishment", dataset)) {

                fmsPettyCashReplenishment.setCashierId(fmsCasherAccount);
                fmsPettyCashReplenishment.setAmount(totalPaid);
                dateprocessedOn = new SimpleDateFormat("yyyy-MM-dd").parse(wfFcmsProcessed.getProcessedOn());
                wfFcmsProcessed.setFmsPettyCashReplenishId(fmsPettyCashReplenishment);
                wfFcmsProcessed.setProcessedOn(wfFcmsProcessed.getProcessedOn());
                wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                wfFcmsProcessed.setDecision(fmsPettyCashReplenishment.getWfStatus());
                if (updateStatus == 0) {
                    fmsPettyCashReplenishment.setPreparedBy(wfFcmsProcessed.getProcessedBy().toString());
                    fmsPettyCashReplenishment.setWfStatus(Constants.PREPARE_VALUE);
                    wfFcmsProcessed.setDecision(fmsPettyCashReplenishment.getWfStatus());
                    updateDailyCashRegisterStatus();
                    fmsPettyCashReplenishmentBeanLocal.create(fmsPettyCashReplenishment);
                    wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                    JsfUtil.addSuccessMessage("Your Request has been Submitted Successfully!");
                } else {//update
                    if (workFlow.isPrepareStatus() && workFlow.isReadonly() == false) {
                        updateDailyCashRegisterStatus();
                        fmsPettyCashReplenishmentBeanLocal.edit(fmsPettyCashReplenishment);
                        JsfUtil.addSuccessMessage("Updated Succesfully!");
                    } else if (workFlow.isApproveStatus() || workFlow.isCheckStatus() || workFlow.isAuthorizeStatus()) {
                        if (selectedDecision.equals("Approved") && workFlow.isApproveStatus()) {
                            fmsPettyCashReplenishment.setWfStatus(Constants.APPROVE_VALUE);
                            wfFcmsProcessed.setDecision(Constants.APPROVE_VALUE);
                        } else if (selectedDecision.equals("Rejected") && workFlow.isApproveStatus()) {
                            fmsPettyCashReplenishment.setWfStatus(Constants.APPROVE_REJECT_VALUE);
                            wfFcmsProcessed.setDecision(Constants.APPROVE_REJECT_VALUE);
                        } else if (selectedDecision.equals("Approved") && workFlow.isCheckStatus()) {
                            fmsPettyCashReplenishment.setWfStatus(Constants.CHECK_APPROVE_VALUE);
                            wfFcmsProcessed.setDecision(Constants.CHECK_APPROVE_VALUE);
                        } else if (selectedDecision.equals("Rejected") && workFlow.isCheckStatus()) {
                            fmsPettyCashReplenishment.setWfStatus(Constants.CHECK_REJECT_VALUE);
                            wfFcmsProcessed.setDecision(Constants.CHECK_REJECT_VALUE);
                        } else if (selectedDecision.equals("Approved") && workFlow.isAuthorizeStatus()) {
                            fmsPettyCashReplenishment.setWfStatus(Constants.AUTHORIZED);
                            wfFcmsProcessed.setDecision(Constants.AUTHORIZED);
                        } else if (selectedDecision.equals("Rejected") && workFlow.isAuthorizeStatus()) {
                            fmsPettyCashReplenishment.setWfStatus(Constants.AUTHORIZED_REJECT_VALUE);
                            wfFcmsProcessed.setDecision(Constants.AUTHORIZED_REJECT_VALUE);
                        }
                        fmsPettyCashReplenishmentBeanLocal.edit(fmsPettyCashReplenishment);
                        wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                        updateDailyCashRegisterStatus();
                        fmsPCRList.remove(fmsPettyCashReplenishment);
                        JsfUtil.addSuccessMessage("Saved Succesfully!");
                        clearPage();
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
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to process! Try again reloading the page.");
        }

    }

    //find Petty ash Replenishment list
    public void findPCR() {
        pcrList = new ArrayList<>();
        pcrList = fmsPettyCashReplenishmentBeanLocal.findPCRByCashierId(fmsCasherAccount);
        fmsPCRDataModel = new ListDataModel(pcrList);
    }

    //find Petty ash Replenishment (PCR)list
    public void findPCRForCheckerAndApprover() {
        try {
            pcrList = new ArrayList<>();
            fmsPCRDataModel = new ListDataModel<>();
            if (!(empEnty.getFirstName() == null)) {//search by Cashier Name only
                pcrList = fmsPettyCashReplenishmentBeanLocal.findPCRByCashierName(fmsCasherAccount);
            } else if ((empEnty.getFirstName() == null) && workFlow.isPrepareStatus() == true) {//only for logged in cashier
                pcrList = fmsPettyCashReplenishmentBeanLocal.findPCRByCashierId(fmsCasherAccount);
            } else if ((empEnty.getFirstName() == null) && workFlow.isPrepareStatus() == false) {//search all
                pcrList = fmsPettyCashReplenishmentBeanLocal.findAllPCR();
            } else {
                JsfUtil.addFatalMessage("No data found. Try again relaoding the page.");
            }
            fmsPCRDataModel = new ListDataModel(pcrList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //select event for PCR
    public void onSelectPCRRow(SelectEvent event) {
        try {
            fmsPettyCashReplenishment = new FmsPettyCashReplenishment();
            pettyCashReplenishmentObj2 = new FmsPettyCashReplenishment();
            fmsPettyCashReplenishment = (FmsPettyCashReplenishment) event.getObject();
            pettyCashReplenishmentObj = new FmsPettyCashReplenishment();
            pettyCashReplenishmentObj = fmsPettyCashReplenishment;
            recreateWfDataModel();
            populatePCR();
            if (workFlow.isPrepareStatus()) {
                if (fmsPettyCashReplenishment.getWfStatus() == 0 || fmsPettyCashReplenishment.getWfStatus() == 4 || fmsPettyCashReplenishment.getWfStatus() == 2) {
                    workFlow.setReadonly(false);
                    wfFcmsProcessed.setCommentGiven(fmsPettyCashReplenishment.getPreparedRemark());
                } else {
                    workFlow.setReadonly(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to process! Try Again Reloading the Page");
        }
    }

    //select PCR request
    public void onSelectPCRRequest(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                fmsPettyCashReplenishment = new FmsPettyCashReplenishment();
                pettyCashReplenishmentObj2 = new FmsPettyCashReplenishment();
                fmsPettyCashReplenishment = (FmsPettyCashReplenishment) event.getNewValue();
                pettyCashReplenishmentObj = new FmsPettyCashReplenishment();
                pettyCashReplenishmentObj = fmsPettyCashReplenishment;
                recreateWfDataModel();
                populatePCR();
                saveorUpdateBundle = "Save";
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to process! Try Again Reloading the Page");
        }
    }

    //populate
    public void populatePCR() {
        try {
            fmsCasherAccount = fmsPettyCashReplenishment.getCashierId();
            cashOnHand = new BigDecimal(0.00);
            cashOnHandAmt = new BigDecimal(0.00);
            totalPaid = new BigDecimal(0.00);
            totalPaidAmt = new BigDecimal(0.00);
            totalEstablished = new BigDecimal(0.00);
            reimbursedAmountinter = 0.00;
            getCashierBalance();
            updateStatus = 1;
            renderPnlCreatePcRepReq = true;
            renderPnlManPage = false;
            saveorUpdateBundle = "Update";
            isRenderedIconWorkFlow = true;
            isDisabledBtnSave = false;
            getDailyCashRegByCashierIdAndWfStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //search
    public void searchPCRR() {
        clearPage();
        renderPnlCreatePcRepReq = false;
        renderPnlManPage = true;
    }

    //get cashier balance
    public void getCashierBalance() {
        try {
            listdaily = new ArrayList<>();
            totalEstablished = BigDecimal.valueOf(fmsCasherAccount.getAmount());
            pettyCashReplenishmentObj2 = fmsPettyCashReplenishmentBeanLocal.getNonReplenishedByCashierId(fmsCasherAccount);//get pettyCashReplenishmentObj2 IF THE REQUEST IS NOT_AUTHORIZED OR NOT_REPLENISHED AND BEING PROCESSED
            if (pettyCashReplenishmentObj2 != null && pettyCashReplenishmentObj2.getWfStatus() != Constants.AUTHORIZED) {//IF NOT_AUTHORIZED OR NOT_REPLENISHED AND IF THE REQUEST IS BEING PROCESSED

                dailyCashRegStatus = pettyCashReplenishmentObj2.getWfStatus();
            } else {//IF pettyCashReplenishmentObj2 == null, fmsPettyCashReplenishment = pettyCashReplenishmentObj
                fmsPettyCashReplenishment = pettyCashReplenishmentObj;
                dailyCashRegStatus = Constants.FMS_DAILY_CASH_REGISTERED_STATUS;//45
            }
            listdaily = cashRegisterBeanLocal.findByCashierIdAndStatus(fmsCasherAccount, dailyCashRegStatus);
            for (int i = 0; i < listdaily.size(); i++) {
                totalPaid = totalPaid.add(listdaily.get(i).getAmount());//calculate total paid amount before the current transaction
                if (listdaily.get(i).getStatus() == Constants.AUTHORIZED) {//IF AUTHORIZED OR REPLENISHED
                    totalPaid = totalPaid.subtract(listdaily.get(i).getAmount());
                }
            }
            cashOnHand = totalEstablished.subtract(totalPaid);
            cashOnHandOneThird = totalEstablished.multiply(oneThirdValue);//cashOnHandOneThird = totalEstablished *  1/3;
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to Process. Try again reloding the page.");
        }
    }

    //calculate Reimbursement 
    public void calculateReimbursement() {
        try {
            for (int i = 0; i < listdaily.size(); i++) {
                totalPaidAmt = totalPaidAmt.add(listdaily.get(i).getAmount());
                if (listdaily.get(i).getStatus() == Constants.AUTHORIZED) {//AUTHORIZED / REPLENISHED
                    totalPaidAmt = totalPaidAmt.subtract(listdaily.get(i).getAmount());
                    cashOnHandAmt = totalEstablished.subtract(totalPaidAmt);
                    authorizedStatus = Constants.AUTHORIZED;
                } else {//NOT_AUTHORIZED / NOT_REPLENISHED
                    authorizedStatus = listdaily.get(i).getStatus();
                }
                dailycashRegister.setAmount(listdaily.get(i).getAmount());
                dailycashRegister.setRegistrationDate(listdaily.get(i).getRegistrationDate());
                dailycashRegister.setVocherNo(listdaily.get(i).getPettyCashId().getFmsVOUCHERID().getVoucherId());
                dailycashRegister.setPurpose(listdaily.get(i).getPettyCashId().getFmsVOUCHERID().getPurpose());
                dailycashRegister.setAmountOnhand(listdaily.get(i).getAmountOnhand());
                reimbursedAmount = reimbursedAmount - listdaily.get(i).getAmount().doubleValue();
                listdaily.get(i).setReAmount(reimbursedAmount);
                listdaily.get(i).setAmount(dailycashRegister.getAmount());
                listdaily.get(i).setVocherNo(dailycashRegister.getVocherNo());
                listdaily.get(i).setPurpose(dailycashRegister.getPurpose());
                listdaily.get(i).setAmountOnhand(dailycashRegister.getAmountOnhand());
                if (updateStatus == 0 && dailyCashRegStatus == Constants.FMS_DAILY_CASH_REGISTERED_STATUS) {
                    fmsPettyCashReplenishDtl = new FmsPettyCashReplenishDtl();
                    fmsPettyCashReplenishDtl.setDailyCashRegisterId(listdaily.get(i));
                    fmsPettyCashReplenishment.addPettyCashReplenishmentDetail(fmsPettyCashReplenishDtl);
                }
            }
            recreateModel();
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to Process. Try again reloding the page.");
        }
    }

    //get daily cash register
    public void getDailyCashRegByCashierIdAndWfStatus() {
        try {
            listdaily = new ArrayList<>();
            totalEstablished = BigDecimal.valueOf(fmsCasherAccount.getAmount());
            reimbursedAmount = fmsCasherAccount.getAmount();
            reimbursedAmountinter = fmsCasherAccount.getAmount();
            dailycashRegister.setChasherId(fmsCasherAccount);
            if (updateStatus == 0) {//THIS WORKS FOR REQUESTER ONLY, TO SUBMIT THE REQUEST
                listdaily = cashRegisterBeanLocal.findByCashierIdAndStatus(fmsCasherAccount, dailyCashRegStatus);//get DCR list by Cahier Id && Constants.FMS_DAILY_CASH_REGISTERED_STATUS for Requester/Cashier
            } else {//THIS WORKS FOR Requester/Cashier/Checker/Approver/Authorizer
                listdaily = cashRegisterBeanLocal.findByCashierIdAndWfStatus(fmsCasherAccount, fmsPettyCashReplenishment);//get DCR list by Cahier Id && wfSTATUS for Requester/Cashier/Checker/Approver/Authorizer
            }
            calculateReimbursement();
            if ((updateStatus == 0) && (fmsPettyCashReplenishment.getWfStatus() == Constants.AUTHORIZED) && (authorizedStatus == Constants.AUTHORIZED) && (cashOnHand.compareTo(totalEstablished) == 0)) {//if PettY Cash is Replenished
                JsfUtil.addInformationMessage("Status: You Have Sufficient Cash on Hand. You Can Register Petty Cash Until Your Cash on Hand Becomes 0.00.");
                setIsDisabledBtnSave(true);
                isSticky = false;
            } else if ((updateStatus != 0) && (fmsPettyCashReplenishment.getWfStatus() == Constants.AUTHORIZED) && (authorizedStatus == Constants.AUTHORIZED) && (cashOnHand.compareTo(totalEstablished) == 0)) {//if PettY Cash is Replenished
                JsfUtil.addInformationMessage("Status: The Selected Cashier Have Sufficient Cash on Hand. He/She Can Register Petty Cash Until Your Cash on Hand Becomes 0.00.");
                setIsDisabledBtnSave(true);
                isSticky = false;
            } else if ((updateStatus == 0) && (fmsPettyCashReplenishment.getWfStatus() != Constants.AUTHORIZED && authorizedStatus != Constants.FMS_DAILY_CASH_REGISTERED_STATUS) && (authorizedStatus != Constants.AUTHORIZED) && (cashOnHand.compareTo(totalEstablished) == -1)) {
                JsfUtil.addInformationMessage("Status: Your Request is Being Processed. Please Contact the Checker/Approver/Authorizer for help");
                setIsDisabledBtnSave(true);
                isSticky = false;
            } else if ((updateStatus != 0) && (fmsPettyCashReplenishment.getWfStatus() != Constants.AUTHORIZED && authorizedStatus != Constants.FMS_DAILY_CASH_REGISTERED_STATUS) && (authorizedStatus != Constants.AUTHORIZED) && (cashOnHand.compareTo(totalEstablished) == -1)) {
                setIsDisabledBtnSave(false);
                isSticky = false;
            } else if ((fmsPettyCashReplenishment == null && authorizedStatus == Constants.FMS_DAILY_CASH_REGISTERED_STATUS) && (authorizedStatus != Constants.AUTHORIZED) && (cashOnHand.compareTo(totalEstablished) == -1)) {
                setIsDisabledBtnSave(false);
                isSticky = false;
            } else {
                if (fmsPettyCashReplenishment != null && fmsPettyCashReplenishment.getWfStatus() == Constants.AUTHORIZED) {
                    JsfUtil.addInformationMessage("Status: The Selected Request is Already Replenished. Please Contact the Checker/Approver/Authorizer for help");
                    setIsDisabledBtnSave(true);
                    isSticky = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //recreate to assign listdaily list to pettyCashPaymentDataModel
    public void recreateModel() {
        pettyCashPaymentDataModel = null;
        pettyCashPaymentDataModel = new ListDataModel(listdaily);
    }

    //recreate to assign getWfFcmsProcessedList value to wfFcmsProcessedDataModel
    public void recreateWfDataModel() {
        wfFcmsProcessedDataModel = null;
        wfFcmsProcessedDataModel = new ListDataModel(fmsPettyCashReplenishment.getWfFcmsProcessedList());
    }

    //concatinate
    public void Concatenate2() {
        String cashierccountCode[] = fmsCasherAccount.getAccountCode().split("-");
        fmsLuSystem.setSystemId(Integer.parseInt(cashierccountCode[0]));
        fmsLuSystem = fmsLuSystemBean.getSystembyId(fmsLuSystem);
        String SL_display = "SL";
        generalLedger.setGeneralLedgerId(Integer.parseInt(cashierccountCode[2]));
        generalLedger = fmsGeneralLedgerBeanLocal.getByGlId(generalLedger);
        if (!"SL".equals(cashierccountCode[0])) {
            fmsSubsid1aryLedger1.setSubsidiaryId(Integer.parseInt(cashierccountCode[3]));
            fmsSubsid1aryLedger1 = subsidiaryLedgerBeanLocal.getSubsidiaryInfo(fmsSubsid1aryLedger1);
            SL_display = fmsSubsid1aryLedger1.getSubsidiaryCode();
        }
        Conc2 = fmsLuSystem.getSystemCode() + "-" + cashierccountCode[1] + "-" + generalLedger.getGeneralLedgerCode() + "-" + SL_display;
        fmsCasherAccount.setDisplay_conn(Conc2);
    }

    //clear
    private void clearPageNew() {
        fmsPettyCashReplenishment = new FmsPettyCashReplenishment();
        updateStatus = 0;
        getDailyCashRegByCashierIdAndWfStatus();
        wfFcmsProcessed = new WfFcmsProcessed();
        wfFcmsProcessedDataModel = new ListDataModel<>();
        saveorUpdateBundle = "Save";
        selectedDecision = "";
    }

    //clear
    private void clearPage() {
        fmsPettyCashReplenishment = new FmsPettyCashReplenishment();
        fmsCasherAccount = new FmsCasherAccount();
        listdaily = new ArrayList<>();
        pettyCashPaymentDataModel = new ListDataModel();
        wfFcmsProcessed = new WfFcmsProcessed();
        isDisabledBtnSave = true;
        isRenderedBtnNew = false;
        cashOnHand = new BigDecimal(0.00);
        cashOnHandAmt = new BigDecimal(0.00);
        totalPaid = new BigDecimal(0.00);
        totalPaidAmt = new BigDecimal(0.00);
        totalEstablished = new BigDecimal(0.00);
        reimbursedAmountinter = 0.00;
        updateStatus = 0;
        saveorUpdateBundle = "Save";
        selectedDecision = "";
    }
    
    //create render
    public void createNewPCRR() {
        clearPageNew();
        renderPnlCreatePcRepReq = true;
        renderPnlManPage = false;
    }
}
