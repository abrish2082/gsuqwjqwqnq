/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.budget;
//<editor-fold defaultstate="collapsed" desc="import">
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.Voucher.CashPaymentOrderBeanLocal;
import et.gov.eep.fcms.businessLogic.FmsAccountUseBeanLocal;
import et.gov.eep.fcms.businessLogic.FmsLuBudgetYearBeanLocal;
import et.gov.eep.fcms.businessLogic.Voucher.ChequePaymentVoucherbeanLocal;
import et.gov.eep.fcms.businessLogic.Voucher.FmsVoucherBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FMSOBTasksBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsBudgetControlBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsCBDisbursementBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsCBTasksBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsCapitalBudgetBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsCapitalBudgetDetailBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsOBDisbursementBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsOperatingBudgetBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsOperatingBudgetDetailBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.budgetCodeBeanLocal;
import et.gov.eep.fcms.businessLogic.budgetYearLookUpBeanLocal;
import et.gov.eep.fcms.entity.FmsAccountUse;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.Vocher.FmsCashPaymentOrder;
import et.gov.eep.fcms.entity.Vocher.FmsChequePaymentVoucher;
import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.entity.admin.FmsSystemJobJunction;
import et.gov.eep.fcms.entity.budget.FmsBudgetCode;
import et.gov.eep.fcms.entity.budget.FmsBudgetControl;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudget1;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetDetail;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetTasks;
import et.gov.eep.fcms.entity.budget.FmsCbDisbursement;
import et.gov.eep.fcms.entity.budget.FmsObDisbursement;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudget1;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetTasks;
import et.gov.eep.pms.entity.PmsWorkAuthorization;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.TabChangeEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

//</editor-fold>


/**
 *
 * @author Me
 */
@Named(value = "budgetControlController")
@ViewScoped
public class BudgetControlController implements Serializable {
    //<editor-fold defaultstate="collapsed" desc="Inject">

    @Inject
    SessionBean SessionBean;

    @Inject
    FmsBudgetControl fmsBudgetControl;
    @Inject
    FmsAccountUse fmsAccountUse;
    @Inject
    FmsCapitalBudget1 fmsCapitalBudget1;
    @Inject
    FmsCapitalBudgetDetail fmsCapitalBudgetDetail;
    @Inject
    FmsOperatingBudget1 fmsOperatingBudget1;
    @Inject
    FmsOperatingBudgetDetail fmsOperatingBudgetDetail;
    @Inject
    FmsLuBudgetYear fmsLuBudgetYear;
    @Inject
    FmsLuSystem fmsLuSystem;
    @Inject
    FmsCostCenter fmsCostCenter;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @Inject
    FmsSubsidiaryLedger fmsSubsidiaryLedger;
    @Inject
    FmsChequePaymentVoucher fmsChequePaymentVoucher;
    @Inject
    FmsCashPaymentOrder fmsCashPaymentOrder;
    @Inject
    FmsVoucher fmsVoucher;
    @Inject
    FmsBudgetCode fmsBudgetCode;
    @Inject
    FmsCostcSystemJunction fmsCostcSystemJunction;
    @Inject
    FmsObDisbursement obDisbursement;
    @Inject
    FmsOperatingBudgetTasks fmsOperatingBudgetTasks;
    @Inject
    FmsCbDisbursement cbDisbursement;
    @Inject
    FmsCapitalBudgetTasks fmsCapitalBudgetTasks;
    @Inject
    PmsWorkAuthorization pmsWorkAuthorization;
    @Inject
    FmsSystemJobJunction fmsSystemJobJunction;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="EJB">

    @EJB
    FmsOBDisbursementBeanLocal fmsOBDisbursementBeanLocal;
    @EJB
    FMSOBTasksBeanLocal fMSOBTasksBeanLocal;
    @EJB
    FmsCBDisbursementBeanLocal cBDisbursementBeanLocal;
    @EJB
    FmsCBTasksBeanLocal cBTasksBeanLocal;
    @EJB
    budgetCodeBeanLocal fmsBudgetCodeBeanLocal;
    @EJB
    FmsOperatingBudgetDetailBeanLocal fmsOperatingBudgetDetailBeanLocal;
    @EJB
    FmsOperatingBudgetBeanLocal fmsOperatingBudgetBeanLocal;
    @EJB
    FmsLuSystemBeanLocal fmsLuSystemBeanLocal;
    @EJB
    FmsCostCenterBeanLocal fmsCostCenterBeanLocal;
    @EJB
    FmsLuBudgetYearBeanLocal fmsLuBudgetYearBeanLocal;
    @EJB
    FmsCapitalBudgetBeanLocal fmsCapitalBudgetBeanLocal;
    @EJB
    FmsCapitalBudgetDetailBeanLocal fmsCapitalBudgetDetailBeanLocal;
    @EJB
    FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal;
    @EJB
    budgetYearLookUpBeanLocal budgetYearLookUpBeanLocal;
    @EJB
    FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    subsidiaryBeanLocal sLBeanLocal;
    @EJB
    CashPaymentOrderBeanLocal cashPaymentOrderBeanLocal;
    @EJB
    ChequePaymentVoucherbeanLocal chequePaymentVoucherbeanLocal;
    @EJB
    FmsVoucherBeanLocal fmsVoucherBeanLocal;
    @EJB
    FmsBudgetControlBeanLocal fmsBudgetControlBeanLocal;
    @EJB
    FmsAccountUseBeanLocal fmsAccountUseBeanLocal;
    @EJB
    FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal;
    @EJB
    subsidiaryBeanLocal fmsSubsidiaryBeanLocal;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    FmsAccountingPeriod currPeriod = new FmsAccountingPeriod();
    FmsLuBudgetYear nextPeriod = new FmsLuBudgetYear();
    List<FmsLuBudgetYear> budgetyrList = new ArrayList<>();
    List<FmsLuBudgetYear> listLuBudgetYearList = new ArrayList<>();
    List<FmsAccountUse> fmsAccountUsesList = new ArrayList<>();
    List<FmsSystemJobJunction> sysJobNOList;
    List<FmsCapitalBudget1> fmsCapitalBudgetList = new ArrayList<>();
    List<FmsCapitalBudgetDetail> fmsCapitalBudgetDetailList = new ArrayList<>();
    List<FmsCapitalBudgetTasks> capitalBudgetTasksList = new ArrayList<>();
    List<FmsCbDisbursement> cbDisbursementsList = new ArrayList<>();

    double capitalBgtbalance = 0.0;
    double preparedVoucherAmountPetty = 0.0;
    double deductedAmount = 0.0;
    double preparedVoucherAmountCheque = 0.0;
    double previousBalance = 0.0;
    double approvedAmount = 0.0;
    double remainBalance;
    BigDecimal payedAmount;
    BigDecimal newBalance;
    BigDecimal currentBalance;

    String monthVal = "";

    String operatingRenderd = "false";
    String capitalRenderd = "false";
    String renderdCheque = "true";
    String renderdPetty = "false";

    String budgetTab = "Operating Budget";
    String voucherTab = "PCPV";
    String cpoVoucher;

    String activeTab;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getter and setters">

    public String getMonthVal() {
        return monthVal;
    }

    public void setMonthVal(String monthVal) {
        this.monthVal = monthVal;
    }

    public BigDecimal getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(BigDecimal newBalance) {
        this.newBalance = newBalance;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public FmsAccountUse getFmsAccountUse() {
        if (fmsAccountUse == null) {
            fmsAccountUse = new FmsAccountUse();
        }
        return fmsAccountUse;
    }

    public void setFmsAccountUse(FmsAccountUse fmsAccountUse) {
        this.fmsAccountUse = fmsAccountUse;
    }

    public List<FmsCapitalBudget1> getFmsCapitalBudgetList() {
        return fmsCapitalBudgetList;
    }

    public void setFmsCapitalBudgetList(List<FmsCapitalBudget1> fmsCapitalBudgetList) {
        this.fmsCapitalBudgetList = fmsCapitalBudgetList;
    }

    public List<FmsCapitalBudgetDetail> getFmsCapitalBudgetDetailList() {
        return fmsCapitalBudgetDetailList;
    }

    public void setFmsCapitalBudgetDetailList(List<FmsCapitalBudgetDetail> fmsCapitalBudgetDetailList) {
        this.fmsCapitalBudgetDetailList = fmsCapitalBudgetDetailList;
    }

    public List<FmsCapitalBudgetTasks> getCapitalBudgetTasksList() {
        return capitalBudgetTasksList;
    }

    public void setCapitalBudgetTasksList(List<FmsCapitalBudgetTasks> capitalBudgetTasksList) {
        this.capitalBudgetTasksList = capitalBudgetTasksList;
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

    public List<FmsAccountUse> getFmsAccountUsesList() {
        if (fmsAccountUsesList == null) {
            fmsAccountUsesList = new ArrayList<>();
        }
        return fmsAccountUsesList;
    }

    public void setFmsAccountUsesList(List<FmsAccountUse> fmsAccountUsesList) {
        this.fmsAccountUsesList = fmsAccountUsesList;
    }

    public FmsBudgetCode getFmsBudgetCode() {
        if (fmsBudgetCode == null) {
            fmsBudgetCode = new FmsBudgetCode();
        }
        return fmsBudgetCode;
    }

    public void setFmsBudgetCode(FmsBudgetCode fmsBudgetCode) {
        this.fmsBudgetCode = fmsBudgetCode;
    }

    public PmsWorkAuthorization getPmsWorkAuthorization() {
        if (pmsWorkAuthorization == null) {
            pmsWorkAuthorization = new PmsWorkAuthorization();
        }
        return pmsWorkAuthorization;
    }

    public void setPmsWorkAuthorization(PmsWorkAuthorization pmsWorkAuthorization) {
        this.pmsWorkAuthorization = pmsWorkAuthorization;
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

    public FmsBudgetControl getFmsBudgetControl() {
        if (fmsBudgetControl == null) {
            fmsBudgetControl = new FmsBudgetControl();
        }
        return fmsBudgetControl;
    }

    public void setFmsBudgetControl(FmsBudgetControl fmsBudgetControl) {
        this.fmsBudgetControl = fmsBudgetControl;
    }

    public FmsCostcSystemJunction getFmsCostcSystemJunction() {
        return fmsCostcSystemJunction;
    }

    public void setFmsCostcSystemJunction(FmsCostcSystemJunction fmsCostcSystemJunction) {
        this.fmsCostcSystemJunction = fmsCostcSystemJunction;
    }

    public FmsCapitalBudget1 getFmsCapitalBudget1() {
        if (fmsCapitalBudget1 == null) {
            fmsCapitalBudget1 = new FmsCapitalBudget1();
        }
        return fmsCapitalBudget1;
    }

    public void setFmsCapitalBudget1(FmsCapitalBudget1 fmsCapitalBudget1) {
        this.fmsCapitalBudget1 = fmsCapitalBudget1;
    }

    public FmsCapitalBudgetDetail getFmsCapitalBudgetDetail() {
        if (fmsCapitalBudgetDetail == null) {
            fmsCapitalBudgetDetail = new FmsCapitalBudgetDetail();
        }
        return fmsCapitalBudgetDetail;
    }

    public void setFmsCapitalBudgetDetail(FmsCapitalBudgetDetail fmsCapitalBudgetDetail) {
        this.fmsCapitalBudgetDetail = fmsCapitalBudgetDetail;
    }

    public FmsOperatingBudget1 getFmsOperatingBudget1() {
        if (fmsOperatingBudget1 == null) {
            fmsOperatingBudget1 = new FmsOperatingBudget1();
        }
        return fmsOperatingBudget1;
    }

    public void setFmsOperatingBudget1(FmsOperatingBudget1 fmsOperatingBudget1) {
        this.fmsOperatingBudget1 = fmsOperatingBudget1;
    }

    public FmsOperatingBudgetDetail getFmsOperatingBudgetDetail() {
        if (fmsOperatingBudgetDetail == null) {
            fmsOperatingBudgetDetail = new FmsOperatingBudgetDetail();
        }
        return fmsOperatingBudgetDetail;
    }

    public void setFmsOperatingBudgetDetail(FmsOperatingBudgetDetail fmsOperatingBudgetDetail) {
        this.fmsOperatingBudgetDetail = fmsOperatingBudgetDetail;
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

    public FmsChequePaymentVoucher getFmsChequePaymentVoucher() {
        if (fmsChequePaymentVoucher == null) {
            fmsChequePaymentVoucher = new FmsChequePaymentVoucher();
        }
        return fmsChequePaymentVoucher;
    }

    public void setFmsChequePaymentVoucher(FmsChequePaymentVoucher fmsChequePaymentVoucher) {
        this.fmsChequePaymentVoucher = fmsChequePaymentVoucher;
    }

    public FmsCashPaymentOrder getFmsCashPaymentOrder() {
        if (fmsCashPaymentOrder == null) {
            fmsCashPaymentOrder = new FmsCashPaymentOrder();
        }
        return fmsCashPaymentOrder;
    }

    public void setFmsCashPaymentOrder(FmsCashPaymentOrder fmsCashPaymentOrder) {
        this.fmsCashPaymentOrder = fmsCashPaymentOrder;
    }

    public FmsOperatingBudgetTasks getFmsOperatingBudgetTasks() {
        return fmsOperatingBudgetTasks;
    }

    public void setFmsOperatingBudgetTasks(FmsOperatingBudgetTasks fmsOperatingBudgetTasks) {
        this.fmsOperatingBudgetTasks = fmsOperatingBudgetTasks;
    }

    public FmsObDisbursement getObDisbursement() {
        if (obDisbursement == null) {
            obDisbursement = new FmsObDisbursement();
        }
        return obDisbursement;
    }

    public void setObDisbursement(FmsObDisbursement obDisbursement) {
        this.obDisbursement = obDisbursement;
    }

    public FmsCbDisbursement getCbDisbursement() {
        return cbDisbursement;
    }

    public void setCbDisbursement(FmsCbDisbursement cbDisbursement) {
        this.cbDisbursement = cbDisbursement;
    }

    public FmsCapitalBudgetTasks getFmsCapitalBudgetTasks() {
        return fmsCapitalBudgetTasks;
    }

    public void setFmsCapitalBudgetTasks(FmsCapitalBudgetTasks fmsCapitalBudgetTasks) {
        this.fmsCapitalBudgetTasks = fmsCapitalBudgetTasks;
    }

    public FmsVoucher getFmsVoucher() {
        return fmsVoucher;
    }

    public void setFmsVoucher(FmsVoucher fmsVoucher) {
        this.fmsVoucher = fmsVoucher;
    }

    public double getCapitalBgtbalance() {
        return capitalBgtbalance;
    }

    public void setCapitalBgtbalance(double capitalBgtbalance) {
        this.capitalBgtbalance = capitalBgtbalance;
    }

    public double getPreparedVoucherAmountPetty() {
        return preparedVoucherAmountPetty;
    }

    public void setPreparedVoucherAmountPetty(double preparedVoucherAmountPetty) {
        this.preparedVoucherAmountPetty = preparedVoucherAmountPetty;
    }

    public double getDeductedAmount() {
        return deductedAmount;
    }

    public void setDeductedAmount(double deductedAmount) {
        this.deductedAmount = deductedAmount;
    }

    public double getPreparedVoucherAmountCheque() {
        return preparedVoucherAmountCheque;
    }

    public void setPreparedVoucherAmountCheque(double preparedVoucherAmountCheque) {
        this.preparedVoucherAmountCheque = preparedVoucherAmountCheque;
    }

    public double getPreviousBalance() {
        return previousBalance;
    }

    public void setPreviousBalance(double previousBalance) {
        this.previousBalance = previousBalance;
    }

    public double getApprovedAmount() {
        return approvedAmount;
    }

    public void setApprovedAmount(double approvedAmount) {
        this.approvedAmount = approvedAmount;
    }

    public double getRemainBalance() {
        return remainBalance;
    }

    public void setRemainBalance(double remainBalance) {
        this.remainBalance = remainBalance;
    }

    public BigDecimal getPayedAmount() {
        return payedAmount;
    }

    public void setPayedAmount(BigDecimal payedAmount) {
        this.payedAmount = payedAmount;
    }

    public String getOperatingRenderd() {
        return operatingRenderd;
    }

    public void setOperatingRenderd(String operatingRenderd) {
        this.operatingRenderd = operatingRenderd;
    }

    public String getCapitalRenderd() {
        return capitalRenderd;
    }

    public void setCapitalRenderd(String capitalRenderd) {
        this.capitalRenderd = capitalRenderd;
    }

    public String getRenderdCheque() {
        return renderdCheque;
    }

    public void setRenderdCheque(String renderdCheque) {
        this.renderdCheque = renderdCheque;
    }

    public String getRenderdPetty() {
        return renderdPetty;
    }

    public void setRenderdPetty(String renderdPetty) {
        this.renderdPetty = renderdPetty;
    }

    public String getBudgetTab() {
        return budgetTab;
    }

    public void setBudgetTab(String budgetTab) {
        this.budgetTab = budgetTab;
    }

    public String getVoucherTab() {
        return voucherTab;
    }

    public void setVoucherTab(String voucherTab) {
        this.voucherTab = voucherTab;
    }

    public String getCpoVoucher() {
        return cpoVoucher;
    }

    public void setCpoVoucher(String cpoVoucher) {
        this.cpoVoucher = cpoVoucher;
    }

    public String getActiveTab() {
        return activeTab;
    }

    public void setActiveTab(String activeTab) {
        this.activeTab = activeTab;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="BudgetControlController">
    public BudgetControlController() {
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methosd">

    public void onVoucherTabChange(TabChangeEvent event) {
        FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.getTab().getTitle());
        switch (event.getTab().getTitle()) {
            case "PCPV":
                voucherTab = "PCPV";
                clear();
                break;
            case "CPV":
                voucherTab = "CPV";
                clear();
                break;
        }
        tabChangeClear();
    }

    public SelectItem[] getLuBudgetYearSearchList() {
        listLuBudgetYearList = fmsLuBudgetYearBeanLocal.getLuBudgetYear();
        listLuBudgetYearList.size();

        currPeriod = fmsAccountingPeriodBeanLocal.getCurretActivePeriod();
        nextPeriod = new FmsLuBudgetYear();
        budgetyrList = new ArrayList<>();
        for (int i = 0; i < listLuBudgetYearList.size(); i++) {
            if (Objects.equals(currPeriod.getLuBudgetYearId().getLuBudgetYearId(), listLuBudgetYearList.get(i).getLuBudgetYearId())) {
                nextPeriod = listLuBudgetYearList.get(i);
            }
        }
        budgetyrList.add(currPeriod.getLuBudgetYearId());
        budgetyrList.add(nextPeriod);
        return JsfUtil.getSelectItems(budgetyrList, true);
    }

    public SelectItem[] getSystemSearchList() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findAll(), true);
    }

    public void SystemSearchChangeTO(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuSystem.setSystemCode(event.getNewValue().toString());
            fmsLuSystem = fmsLuSystemBeanLocal.getSysDetail(fmsLuSystem);
            fmsCostCenter.setSystemId(fmsLuSystem);
        }
    }

    public SelectItem[] getCostCenterSearchTO() {
        if (fmsLuSystem != null) {
            return JsfUtil.getSelectItems(fmsCostCenterBeanLocal.findAll(), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            return items;
        }
    }

    public void CostCenterChangeTO(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsCostCenter.setSystemName(event.getNewValue().toString());
            fmsCostCenter = fmsCostCenterBeanLocal.getCostDetail(fmsCostCenter);
            fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findByCCandSS(fmsLuSystem, fmsCostCenter);
        }
    }

    public void RequestCodeChangeTO(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            fmsOperatingBudget1 = (FmsOperatingBudget1) event.getNewValue();
            fmsOperatingBudget1 = fmsOperatingBudgetBeanLocal.fetchByRequestCode(fmsOperatingBudget1);           
            fmsOperatingBudgetDetail = new FmsOperatingBudgetDetail();
            fmsOperatingBudgetDetail.setOperatingBudgetId(fmsOperatingBudget1);
            fmsOperatingBudgetDetail = fmsOperatingBudgetDetailBeanLocal.fetchOB(fmsOperatingBudget1);           
            fmsOperatingBudgetDetailBeanLocal.fetchOBDetail(fmsOperatingBudget1);
            System.out.println("oo " + fmsSubsidiaryLedger.getSubsidiaryId());
            fmsOperatingBudgetTasks = fMSOBTasksBeanLocal.findByOBDtlAndSL(fmsOperatingBudgetDetail, fmsSubsidiaryLedger);
            obDisbursement = fmsOBDisbursementBeanLocal.fetchOBDisbByTaskId(fmsOperatingBudgetTasks);
            System.out.println("obDist " + obDisbursement.getDisbursementId());
            obBudgetDetail = fmsOperatingBudgetDetail;
        }
    }

    public SelectItem[] getMonth() {
        return JsfUtil.getSelectItems(month(), true);
    }

    public ArrayList<String> month() {
        ArrayList<String> mon = new ArrayList<>();
        mon.add("HAMLE");
        mon.add("NEHASIE");
        mon.add("MESKEREM");
        mon.add("TIKEMT");
        mon.add("HIDAR");
        mon.add("TAHSAS");
        mon.add("TIR");
        mon.add("YEKATIT");
        mon.add("MEGABIT");
        mon.add("MIYAZIYA");
        mon.add("GINBOT");
        mon.add("SENE");
        return mon;
    }

    public void monthValSetter(AjaxBehaviorEvent event) {

        //payedAmount = fmsChequePaymentVoucher.getAmountInFigure();
        payedAmount = fmsAccountUse.getDebit();
        System.out.println("payedAmount " + payedAmount);

        if (null != monthVal) {
            switch (monthVal) {
                case "HAMLE":
                    currentBalance = obDisbursement.getHamle();
                    System.out.println("pamount " + payedAmount);
                    System.out.println("cur blc " + currentBalance);
                    if (currentBalance.compareTo(payedAmount) == 1) {
                        newBalance = currentBalance.subtract(payedAmount);
                    } else {
                        JsfUtil.addFatalMessage("Remaining amount " + currentBalance + " is less than the paid " + payedAmount + ".");
                        clear();
                    }
                    break;
                case "NEHASIE":
                    currentBalance = obDisbursement.getNehasie();
                    if (currentBalance.compareTo(payedAmount) == 1) {
                        newBalance = currentBalance.subtract(payedAmount);
                    } else {
                        JsfUtil.addFatalMessage("Remaining amount " + currentBalance + " is less than the paid " + payedAmount + ".");
                        clear();
                    }
                    break;
                case "MESKEREM":
                    currentBalance = obDisbursement.getMeskerem();
                    if (currentBalance.compareTo(payedAmount) == 1) {
                        newBalance = currentBalance.subtract(payedAmount);
                    } else {
                        JsfUtil.addFatalMessage("Remaining amount " + currentBalance + " is less than the paid " + payedAmount + ".");
                        clear();
                    }
                    break;
                case "TIKEMT":
                    currentBalance = obDisbursement.getTikemt();
                    if (currentBalance.compareTo(payedAmount) == 1) {
                        newBalance = currentBalance.subtract(payedAmount);
                    } else {
                        JsfUtil.addFatalMessage("Remaining amount " + currentBalance + " is less than the paid " + payedAmount + ".");
                        clear();
                    }
                    break;
                case "HIDAR":
                    currentBalance = obDisbursement.getHidar();
                    if (currentBalance.compareTo(payedAmount) == 1) {
                        newBalance = currentBalance.subtract(payedAmount);
                    } else {
                        JsfUtil.addFatalMessage("Remaining amount " + currentBalance + " is less than the paid " + payedAmount + ".");
                        clear();
                    }
                    break;
                case "TAHSAS":
                    currentBalance = obDisbursement.getTahsas();
                    if (currentBalance.compareTo(payedAmount) == 1) {
                        newBalance = currentBalance.subtract(payedAmount);
                    } else {
                        JsfUtil.addFatalMessage("Remaining amount " + currentBalance + " is less than the paid " + payedAmount + ".");
                        clear();
                    }
                    break;
                case "TIR":
                    currentBalance = obDisbursement.getTir();
                    if (currentBalance.compareTo(payedAmount) == 1) {
                        newBalance = currentBalance.subtract(payedAmount);
                    } else {
                        JsfUtil.addFatalMessage("Remaining amount " + currentBalance + " is less than the paid " + payedAmount + ".");
                        clear();
                    }
                    break;
                case "YEKATIT":
                    currentBalance = obDisbursement.getYekatit();
                    if (currentBalance.compareTo(payedAmount) == 1) {
                        newBalance = currentBalance.subtract(payedAmount);
                    } else {
                        JsfUtil.addFatalMessage("Remaining amount " + currentBalance + " is less than the paid " + payedAmount + ".");
                        clear();
                    }
                    break;
                case "MEGABIT":
                    currentBalance = obDisbursement.getMegabit();
                    if (currentBalance.compareTo(payedAmount) == 1) {
                        newBalance = currentBalance.subtract(payedAmount);
                    } else {
                        JsfUtil.addFatalMessage("Remaining amount " + currentBalance + " is less than the paid " + payedAmount + ".");
                        clear();
                    }
                    break;
                case "MIYAZIYA":
                    currentBalance = obDisbursement.getMiyaziya();
                    if (currentBalance.compareTo(payedAmount) == 1) {
                        newBalance = currentBalance.subtract(payedAmount);
                    } else {
                        JsfUtil.addFatalMessage("Remaining amount " + currentBalance + " is less than the paid " + payedAmount + ".");
                        clear();
                    }
                    break;
                case "GINBOT":
                    currentBalance = obDisbursement.getGinbot();
                    if (currentBalance.compareTo(payedAmount) == 1) {
                        newBalance = currentBalance.subtract(payedAmount);
                    } else {
                        JsfUtil.addFatalMessage("Remaining amount " + currentBalance + " is less than the paid " + payedAmount + ".");
                        clear();
                    }
                    break;
                case "SENE":
                    currentBalance = obDisbursement.getSene();
                    if (currentBalance.compareTo(payedAmount) == 1) {
                        newBalance = currentBalance.subtract(payedAmount);
                    } else {
                        JsfUtil.addFatalMessage("Remaining amount " + currentBalance + " is less than the paid " + payedAmount + ".");
                        clear();
                    }
                    break;
            }
        }
    }

    public void monthValSetterCB(AjaxBehaviorEvent event) {
        if (null != monthVal) {
            switch (monthVal) {
                case "HAMLE":
                    currentBalance = cbDisbursement.getHamle();
                    if (currentBalance.compareTo(payedAmount) == 1) {
                        newBalance = currentBalance.subtract(payedAmount);
                    } else {
                        JsfUtil.addFatalMessage("Remaining amount " + currentBalance + " is less than the paid " + payedAmount + ".");
                        clear();
                    }
                    break;
                case "NEHASIE":
                    currentBalance = cbDisbursement.getNehasie();
                    if (currentBalance.compareTo(payedAmount) == 1) {
                        newBalance = currentBalance.subtract(payedAmount);
                    } else {
                        JsfUtil.addFatalMessage("Remaining amount " + currentBalance + " is less than the paid " + payedAmount + ".");
                        clear();
                    }
                    break;
                case "MESKEREM":
                    currentBalance = cbDisbursement.getMeskerem();
                    if (currentBalance.compareTo(payedAmount) == 1) {
                        newBalance = currentBalance.subtract(payedAmount);
                    } else {
                        JsfUtil.addFatalMessage("Remaining amount " + currentBalance + " is less than the paid " + payedAmount + ".");
                        clear();
                    }
                    break;
                case "TIKEMT":
                    currentBalance = cbDisbursement.getTikemt();
                    if (currentBalance.compareTo(payedAmount) == 1) {
                        newBalance = currentBalance.subtract(payedAmount);
                    } else {
                        JsfUtil.addFatalMessage("Remaining amount " + currentBalance + " is less than the paid " + payedAmount + ".");
                        clear();
                    }
                    break;
                case "HIDAR":
                    currentBalance = cbDisbursement.getHidar();
                    if (currentBalance.compareTo(payedAmount) == 1) {
                        newBalance = currentBalance.subtract(payedAmount);
                    } else {
                        JsfUtil.addFatalMessage("Remaining amount " + currentBalance + " is less than the paid " + payedAmount + ".");
                        clear();
                    }
                    break;
                case "TAHSAS":
                    currentBalance = cbDisbursement.getTahsas();
                    if (currentBalance.compareTo(payedAmount) == 1) {
                        newBalance = currentBalance.subtract(payedAmount);
                    } else {
                        JsfUtil.addFatalMessage("Remaining amount " + currentBalance + " is less than the paid " + payedAmount + ".");
                        clear();
                    }
                    break;
                case "TIR":
                    currentBalance = cbDisbursement.getTir();
                    if (currentBalance.compareTo(payedAmount) == 1) {
                        newBalance = currentBalance.subtract(payedAmount);
                    } else {
                        JsfUtil.addFatalMessage("Remaining amount " + currentBalance + " is less than the paid " + payedAmount + ".");
                        clear();
                    }
                    break;
                case "YEKATIT":
                    currentBalance = cbDisbursement.getYekatit();
                    if (currentBalance.compareTo(payedAmount) == 1) {
                        newBalance = currentBalance.subtract(payedAmount);
                    } else {
                        JsfUtil.addFatalMessage("Remaining amount " + currentBalance + " is less than the paid " + payedAmount + ".");
                        clear();
                    }
                    break;
                case "MEGABIT":
                    currentBalance = cbDisbursement.getMegabit();
                    if (currentBalance.compareTo(payedAmount) == 1) {
                        newBalance = currentBalance.subtract(payedAmount);
                    } else {
                        JsfUtil.addFatalMessage("Remaining amount " + currentBalance + " is less than the paid " + payedAmount + ".");
                        clear();
                    }
                    break;
                case "MIYAZIYA":
                    currentBalance = cbDisbursement.getMiyaziya();
                    if (currentBalance.compareTo(payedAmount) == 1) {
                        newBalance = currentBalance.subtract(payedAmount);
                    } else {
                        JsfUtil.addFatalMessage("Remaining amount " + currentBalance + " is less than the paid " + payedAmount + ".");
                        clear();
                    }
                    break;
                case "GINBOT":
                    currentBalance = cbDisbursement.getGinbot();
                    if (currentBalance.compareTo(payedAmount) == 1) {
                        newBalance = currentBalance.subtract(payedAmount);
                    } else {
                        JsfUtil.addFatalMessage("Remaining amount " + currentBalance + " is less than the paid " + payedAmount + ".");
                        clear();
                    }
                    break;
                case "SENE":
                    currentBalance = cbDisbursement.getSene();
                    if (currentBalance.compareTo(payedAmount) == 1) {
                        newBalance = currentBalance.subtract(payedAmount);
                    } else {
                        JsfUtil.addFatalMessage("Remaining amount " + currentBalance + " is less than the paid " + payedAmount + ".");
                        clear();
                    }
                    break;
            }
        }
    }

    public SelectItem[] GeneralLedgerTO() {
        if (fmsOperatingBudget1 != null) {
            return JsfUtil.getSelectItems(fmsOperatingBudgetDetailBeanLocal.fetchGLfromOBDetail(fmsOperatingBudget1), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            return items;
        }
    }

    FmsOperatingBudgetDetail obBudgetDetail;

    public FmsOperatingBudgetDetail getObBudgetDetail() {
        if (obBudgetDetail == null) {
            obBudgetDetail = new FmsOperatingBudgetDetail();
        }
        return obBudgetDetail;
    }

    public void setObBudgetDetail(FmsOperatingBudgetDetail obBudgetDetail) {
        this.obBudgetDetail = obBudgetDetail;
    }

    public void GeneralLedgerChangeTO(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsGeneralLedger.setGeneralLedgerCode(event.getNewValue().toString());
            fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getGlCode(fmsGeneralLedger);
            fmsOperatingBudgetDetail = fmsOperatingBudgetDetailBeanLocal.fetchByGLfromOBDetail(fmsGeneralLedger, fmsOperatingBudget1);
            obBudgetDetail = fmsOperatingBudgetDetail;
        }
    }

    public void searchYearValueChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuBudgetYear.setBudgetYear(event.getNewValue().toString());
            fmsLuBudgetYear = budgetYearLookUpBeanLocal.getYearInfo(fmsLuBudgetYear);
            fmsOperatingBudget1.setBudgetYear(fmsLuBudgetYear);
            fmsCapitalBudget1.setBudgetYear(fmsLuBudgetYear);       
        }
    }

    public void onChangeJobNo(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            fmsSystemJobJunction = (FmsSystemJobJunction) event.getNewValue();            
            pmsWorkAuthorization.setWorkAuthoId(fmsSystemJobJunction.getWorkAuthoFk().getWorkAuthoId());
            fmsCapitalBudgetList = fmsCapitalBudgetBeanLocal.findByCCSSJandPMSandBYRAuthorized(fmsLuBudgetYear, fmsCostcSystemJunction, pmsWorkAuthorization);
            System.out.println("fmsCapitalBudgetList size == " + fmsCapitalBudgetList.size());
        }
    }

    public SelectItem[] CBReqcodes() {
        if (fmsCostCenter != null && fmsCostcSystemJunction != null) {
            return JsfUtil.getSelectItems(fmsCapitalBudgetBeanLocal.findByCCSSJandPMSandBYRAuthorized(fmsLuBudgetYear, fmsCostcSystemJunction, pmsWorkAuthorization), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--- Select One ---");
            return items;
        }
    }

    public void RequestCodeChangeFrom(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsCapitalBudget1.setRequestCode(event.getNewValue().toString());
            fmsCapitalBudget1 = fmsCapitalBudgetBeanLocal.findByRequestCodeOnRequest(fmsCapitalBudget1);
            fmsCapitalBudgetDetail = new FmsCapitalBudgetDetail();
            fmsCapitalBudgetDetail.setCapitalBudgetId(fmsCapitalBudget1);
            fmsCapitalBudgetDetailList = fmsCapitalBudgetDetailBeanLocal.fetchSelectedCBRequest(fmsCapitalBudget1);

        }
    }

    public SelectItem[] getAccUseList() {
        return JsfUtil.getSelectItems(fmsAccountUsesList, true);
    }

    //mm
    public void accountUsPCPVeListener(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            String[] parts = event.getNewValue().toString().split("_");
            String voucherId = parts[0];
            String accUseId = parts[1];

            FmsVoucher voucherIdx = new FmsVoucher();
            voucherIdx.setVoucherId(voucherId);
            voucherIdx = fmsVoucherBeanLocal.getVoucherIDInfo(voucherIdx);
            fmsAccountUse.setVOUCHEID(voucherIdx);
            fmsAccountUse.setFmsAccountUseId(Integer.parseInt(accUseId));

            fmsAccountUse = fmsAccountUseBeanLocal.getAccUse(fmsAccountUse);

            String[] accParts = fmsAccountUse.getSubsidaryId().split("-");
            String sys = accParts[0];
            String costC = accParts[1];
            String genL = accParts[2];
            //String subsL = accParts[3];

            //fmsLuSystem.setSystemId(Integer.parseInt(sys));
            fmsLuSystem.setSystemCode(sys);
            fmsLuSystem = fmsLuSystemBeanLocal.getSystembyId(fmsLuSystem);
            fmsCostCenter.setSystemName(costC);
            fmsCostCenter = fmsCostCenterBeanLocal.getCostCenterId(fmsCostCenter);
            sysJobNOList = fmsLuSystem.getFmsSystemJobJunctionList();
            //if (!costC.matches("CC")) {
            //fmsCostCenter.setCostCenterId(Integer.parseInt(costC));

            // }
            //fmsGeneralLedger.setGeneralLedgerId(Integer.parseInt(genL));
            fmsGeneralLedger.setGeneralLedgerCode(genL);
            fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getGlCode(fmsGeneralLedger);

            //fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getByGlId(fmsGeneralLedger);
            //if (!subsL.matches("SL")) {
            //fmsSubsidiaryLedger = sLBeanLocal.findById(Integer.parseInt(subsL));
            fmsSubsidiaryLedger.setSubsidiaryCode(fmsAccountUse.getSubsidaryId());
            fmsSubsidiaryLedger = sLBeanLocal.getSlCode(fmsSubsidiaryLedger);

            fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findByCCandSS(fmsLuSystem, fmsCostCenter);
            fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findById(fmsCostcSystemJunction);
            payedAmount = fmsAccountUse.getDebit();
        }
    }
//

    public void accountUseListener(ValueChangeEvent event) {      
        if (!event.getNewValue().toString().isEmpty()) {
            String[] parts = event.getNewValue().toString().split("_");
            String voucherId = parts[0];
            String accUseId = parts[1];

            FmsVoucher voucherIdx = new FmsVoucher();
            voucherIdx.setVoucherId(voucherId);
            voucherIdx = fmsVoucherBeanLocal.getVoucherIDInfo(voucherIdx);
            fmsAccountUse.setVOUCHEID(voucherIdx);
            fmsAccountUse.setFmsAccountUseId(Integer.parseInt(accUseId));

            fmsAccountUse = fmsAccountUseBeanLocal.getAccUse(fmsAccountUse);

            String[] accParts = fmsAccountUse.getSubsidaryId().split("-");
            String sys = accParts[0];
            String costC = accParts[1];
            String genL = accParts[2];
            fmsLuSystem.setSystemCode(sys);
            System.out.println("sys: "+sys);
            fmsLuSystem = fmsLuSystemBeanLocal.getSystembyId(fmsLuSystem);
            fmsCostCenter.setSystemName(costC);
            fmsCostCenter = fmsCostCenterBeanLocal.getCostCenterId(fmsCostCenter);
            sysJobNOList = fmsLuSystem.getFmsSystemJobJunctionList();
            //if (!costC.matches("CC")) {
            //fmsCostCenter.setCostCenterId(Integer.parseInt(costC));

            // }
            //fmsGeneralLedger.setGeneralLedgerId(Integer.parseInt(genL));
            fmsGeneralLedger.setGeneralLedgerCode(genL);
            fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getGlCode(fmsGeneralLedger);

            //fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getByGlId(fmsGeneralLedger);
            //if (!subsL.matches("SL")) {
            //fmsSubsidiaryLedger = sLBeanLocal.findById(Integer.parseInt(subsL));
            fmsSubsidiaryLedger.setSubsidiaryCode(fmsAccountUse.getSubsidaryId());
            System.out.println("ff2 " + fmsSubsidiaryLedger.getSubsidiaryCode());
            fmsSubsidiaryLedger = sLBeanLocal.getSlCode(fmsSubsidiaryLedger);

            fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findByCCandSS(fmsLuSystem, fmsCostCenter);
            System.out.println("fmsCostcSystemJunction " + fmsCostcSystemJunction.getId());
            fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findById(fmsCostcSystemJunction);
            payedAmount = fmsAccountUse.getDebit();
        }
    }

    public SelectItem[] OBReqcodesTO() {
        if (fmsCostCenter != null && fmsCostcSystemJunction != null) {
            //System.out.println("when OB "+fmsCostCenter+" ccsj "+fmsCostcSystemJunction);
            return JsfUtil.getSelectItems(fmsOperatingBudgetBeanLocal.findByBudgetYearAndCostCenterAuthorized(fmsLuBudgetYear, fmsCostcSystemJunction), true);

        } else {
            System.out.println("else");
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--- Select One ---");
            return items;
        }
    }

    public void BudgetCodeChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsBudgetCode.setBudgetCode(event.getNewValue().toString());
            fmsBudgetCode = fmsBudgetCodeBeanLocal.findBudgetCode(fmsBudgetCode);
            fmsCapitalBudgetDetail = fmsCapitalBudgetDetailBeanLocal.fetchCapBudgetDetail(fmsLuBudgetYear, fmsCostCenter, fmsBudgetCode);
            capitalBudgetTasksList = cBTasksBeanLocal.findByCBudgetDetail(fmsCapitalBudgetDetail);
            fmsCapitalBudgetTasks = cBTasksBeanLocal.fetchCBTaskData(fmsCapitalBudgetDetail, fmsSubsidiaryLedger);
            cbDisbursement = cBDisbursementBeanLocal.fetchCBDisbByTaskId(fmsCapitalBudgetTasks);
        }
    }

    public void SubsidiaryLedgerChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsCapitalBudgetTasks = (FmsCapitalBudgetTasks) event.getNewValue();
            if (fmsCapitalBudgetTasks.getRemainingAmount().compareTo(payedAmount) == 1) {
                newBalance = fmsCapitalBudgetTasks.getRemainingAmount().subtract(payedAmount);
            } else {
                JsfUtil.addFatalMessage("Remaining amount " + fmsCapitalBudgetTasks.getRemainingAmount() + " is less than the payed " + payedAmount + ".");
                clear();
            }
        }
    }

    public SelectItem[] getBudgetCodesTO() {
        if (fmsCostCenter != null) {
            return JsfUtil.getSelectItems(fmsCapitalBudgetDetailBeanLocal.fetchCBDetail(fmsLuBudgetYear, fmsCostCenter), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            return items;
        }
    }

    public List<FmsCashPaymentOrder> fetchCashPaymentOrderList() {
        return cashPaymentOrderBeanLocal.getPreparedCPO();
    }

    public List<FmsChequePaymentVoucher> fetchChequePaymentVoucherList() {
        return chequePaymentVoucherbeanLocal.fetchchequeVoucherLists();
    }

    public void pcpvListener(ValueChangeEvent event) {
        try {
            if (event.getNewValue() != null) {
                fmsVoucher.setVoucherId(event.getNewValue().toString());
                fmsVoucher = fmsVoucherBeanLocal.getVoucherIDInfo(fmsVoucher);
                fmsCashPaymentOrder.setFmsVOUCHERID(fmsVoucher);
                fmsCashPaymentOrder = cashPaymentOrderBeanLocal.getCashPaymentOrder(fmsCashPaymentOrder);
                fmsAccountUsesList = new ArrayList<>();
                fmsAccountUsesList = fmsAccountUseBeanLocal.fetchCPOAccUse(fmsCashPaymentOrder);
                payedAmount = fmsCashPaymentOrder.getAmountInFigure();
                renderdPetty = "true";
                renderdCheque = "false";
//                calculateResult();
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void chpvListener(ValueChangeEvent event) {

        try {
            if (event.getNewValue() != null) {
                // fmsChequePaymentVoucher.getFmsVOUCHERID().setVoucherId(event.getNewValue().toString()); 
                //mm
                fmsVoucher.setVoucherId(event.getNewValue().toString());
                fmsVoucher = fmsVoucherBeanLocal.getVoucherIDInfo(fmsVoucher);
                fmsChequePaymentVoucher.setFmsVOUCHERID(fmsVoucher);
                fmsChequePaymentVoucher = chequePaymentVoucherbeanLocal.getApprovedChequePayment(fmsChequePaymentVoucher);
                fmsAccountUsesList = new ArrayList<>();
                fmsAccountUsesList = fmsAccountUseBeanLocal.fetchCHPVAccUse(fmsChequePaymentVoucher);
                payedAmount = fmsChequePaymentVoucher.getAmountInFigure();
                renderdPetty = "true";
                renderdCheque = "false";
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void onBudgetTabChange(TabChangeEvent event) {
        FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.getTab().getTitle());
        switch (event.getTab().getTitle()) {
            case "Operating Budget":

                budgetTab = "Operating Budget";
                activeTab = event.getTab().getTitle();
                clear();
                break;
            case "Capital Budget":
                budgetTab = "Capital Budget";
                activeTab = event.getTab().getTitle();
                clear();
                break;
        }
        tabChangeClear();
    }

    public void clear() {
        fmsLuBudgetYear = new FmsLuBudgetYear();
        fmsLuSystem = new FmsLuSystem();
        fmsCostCenter = new FmsCostCenter();
        fmsChequePaymentVoucher = new FmsChequePaymentVoucher();
        fmsCashPaymentOrder = new FmsCashPaymentOrder();
        fmsCapitalBudgetDetail = new FmsCapitalBudgetDetail();
        fmsOperatingBudgetDetail = new FmsOperatingBudgetDetail();
        obDisbursement = new FmsObDisbursement();
        cbDisbursement = new FmsCbDisbursement();
        fmsAccountUse = new FmsAccountUse();
        fmsAccountUsesList = new ArrayList<>();
        fmsGeneralLedger = new FmsGeneralLedger();
        fmsSubsidiaryLedger = new FmsSubsidiaryLedger();
        fmsCapitalBudget1 = new FmsCapitalBudget1();
        pmsWorkAuthorization = new PmsWorkAuthorization();
        fmsBudgetCode = new FmsBudgetCode();
        fmsBudgetControl = new FmsBudgetControl();
        fmsCapitalBudgetDetailList = new ArrayList<>();
        fmsCapitalBudgetList = new ArrayList<>();
        sysJobNOList = new ArrayList<>();

        preparedVoucherAmountCheque = 0.0;
        deductedAmount = 0.0;
        previousBalance = 0.0;
        approvedAmount = 0.0;
        remainBalance = 0.0;
        payedAmount = BigDecimal.ZERO;
        newBalance = BigDecimal.ZERO;
        currentBalance = BigDecimal.ZERO;
        monthVal = new String();
    }

    public void tabChangeClear() {
        fmsLuBudgetYear = new FmsLuBudgetYear();
        fmsLuSystem = new FmsLuSystem();
        fmsCostCenter = new FmsCostCenter();
        fmsChequePaymentVoucher = new FmsChequePaymentVoucher();
        fmsCashPaymentOrder = new FmsCashPaymentOrder();
        fmsCapitalBudgetDetail = new FmsCapitalBudgetDetail();
        fmsOperatingBudgetDetail = new FmsOperatingBudgetDetail();

        preparedVoucherAmountCheque = 0.0;
        deductedAmount = 0.0;
        previousBalance = 0.0;
        approvedAmount = 0.0;
        remainBalance = 0.0;
        cpoVoucher = null;
        payedAmount = null;
    }

    public void saveBCVoucher() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveBCVoucher", dataset)) {
                if ("Operating Budget".equals(budgetTab)) {
                    if (null != monthVal) {
                        switch (monthVal) {
                            case "HAMLE":
                                obDisbursement.setHamle(newBalance);
                                break;
                            case "NEHASIE":
                                obDisbursement.setNehasie(newBalance);
                                break;
                            case "MESKEREM":
                                obDisbursement.setMeskerem(newBalance);
                                break;
                            case "TIKEMT":
                                obDisbursement.setTikemt(newBalance);
                                break;
                            case "HIDAR":
                                obDisbursement.setHidar(newBalance);
                                break;
                            case "TAHSAS":
                                obDisbursement.setTahsas(newBalance);
                                break;
                            case "TIR":
                                obDisbursement.setTir(newBalance);
                                break;
                            case "YEKATIT":
                                obDisbursement.setYekatit(newBalance);
                                break;
                            case "MEGABIT":
                                obDisbursement.setMegabit(newBalance);
                                break;
                            case "MIYAZIYA":
                                obDisbursement.setMiyaziya(newBalance);
                                break;
                            case "GINBOT":
                                obDisbursement.setGinbot(newBalance);
                                break;
                            case "SENE":
                                obDisbursement.setSene(newBalance);
                                break;
                        }
                    }
                    if ("PCPV".equals(voucherTab) && obBudgetDetail != null && newBalance != null) {
                        fmsOBDisbursementBeanLocal.edit(obDisbursement);
                        fmsCashPaymentOrder.setStatus("2");
                        cashPaymentOrderBeanLocal.edit(fmsCashPaymentOrder);

                        fmsBudgetControl.setObTaskId(obDisbursement.getObTaskId());
                        fmsBudgetControl.setCbTaskId(null);
                        fmsBudgetControl.setCashVId(fmsCashPaymentOrder);
                        fmsBudgetControl.setChequeVId(null);
                        fmsBudgetControl.setCostCenterId(fmsCostCenter);
                        fmsBudgetControl.setGeneralLedgerId(fmsGeneralLedger);
                        fmsBudgetControl.setSubsidiaryId(fmsSubsidiaryLedger);
                        fmsBudgetControl.setPaidAmount(payedAmount);
                        fmsBudgetControl.setMonthName(monthVal);
                        fmsBudgetControlBeanLocal.create(fmsBudgetControl);

                        JsfUtil.addSuccessMessage("Data Saved successfully.");
                    } else if ("CPV".equals(voucherTab) && newBalance != null) {
//                    obBudgetDetail.setRemainingBalance(newBalance);
//                    fmsOperatingBudgetDetailBeanLocal.edit(obBudgetDetail);
                        fmsOBDisbursementBeanLocal.edit(obDisbursement);
                        fmsChequePaymentVoucher.setStatus("3");
                        chequePaymentVoucherbeanLocal.edit(fmsChequePaymentVoucher);

                        fmsBudgetControl.setObTaskId(obDisbursement.getObTaskId());
                        fmsBudgetControl.setCbTaskId(null);
                        fmsBudgetControl.setCashVId(null);
                        fmsBudgetControl.setChequeVId(fmsChequePaymentVoucher);
                        fmsBudgetControl.setCostCenterId(fmsCostCenter);
                        fmsBudgetControl.setGeneralLedgerId(fmsGeneralLedger);
                        fmsBudgetControl.setSubsidiaryId(fmsSubsidiaryLedger);
                        fmsBudgetControl.setPaidAmount(payedAmount);
                        fmsBudgetControl.setMonthName(monthVal);
                        fmsBudgetControlBeanLocal.create(fmsBudgetControl);
                        JsfUtil.addSuccessMessage("Data Saved successfully.");
                    }
                }
                if ("Capital Budget".equals(budgetTab)) {
                    if (null != monthVal) {
                        switch (monthVal) {
                            case "HAMLE":
                                cbDisbursement.setHamle(newBalance);
                                break;
                            case "NEHASIE":
                                cbDisbursement.setNehasie(newBalance);
                                break;
                            case "MESKEREM":
                                cbDisbursement.setMeskerem(newBalance);
                                break;
                            case "TIKEMT":
                                cbDisbursement.setTikemt(newBalance);
                                break;
                            case "HIDAR":
                                cbDisbursement.setHidar(newBalance);
                                break;
                            case "TAHSAS":
                                cbDisbursement.setTahsas(newBalance);
                                break;
                            case "TIR":
                                cbDisbursement.setTir(newBalance);
                                break;
                            case "YEKATIT":
                                cbDisbursement.setYekatit(newBalance);
                                break;
                            case "MEGABIT":
                                cbDisbursement.setMegabit(newBalance);
                                break;
                            case "MIYAZIYA":
                                cbDisbursement.setMiyaziya(newBalance);
                                break;
                            case "GINBOT":
                                cbDisbursement.setGinbot(newBalance);
                                break;
                            case "SENE":
                                cbDisbursement.setSene(newBalance);
                                break;
                        }
                    }
                    if ("PCPV".equals(voucherTab) && fmsCapitalBudgetDetail != null && newBalance != null) {

                        cBDisbursementBeanLocal.edit(cbDisbursement);
                        fmsCashPaymentOrder.setStatus("2");
                        cashPaymentOrderBeanLocal.edit(fmsCashPaymentOrder);

                        fmsBudgetControl.setObTaskId(null);
                        fmsBudgetControl.setCbTaskId(cbDisbursement.getCbTaskId());
                        fmsBudgetControl.setCashVId(fmsCashPaymentOrder);
                        fmsBudgetControl.setChequeVId(null);
                        fmsBudgetControl.setCostCenterId(fmsCostCenter);
                        fmsBudgetControl.setGeneralLedgerId(fmsBudgetCode.getGeneralLedger());
                        fmsBudgetControl.setSubsidiaryId(fmsSubsidiaryLedger);
                        fmsBudgetControl.setPaidAmount(payedAmount);
                        fmsBudgetControl.setMonthName(monthVal);
                        fmsBudgetControlBeanLocal.create(fmsBudgetControl);

                        JsfUtil.addSuccessMessage("Data Saved successfully.");
                    } else if ("CPV".equals(voucherTab) && fmsCapitalBudgetDetail != null && newBalance != null) {

                        cBDisbursementBeanLocal.edit(cbDisbursement);
                        fmsChequePaymentVoucher.setStatus("3");
                        chequePaymentVoucherbeanLocal.edit(fmsChequePaymentVoucher);

                        fmsBudgetControl.setObTaskId(null);
                        fmsBudgetControl.setCbTaskId(cbDisbursement.getCbTaskId());
                        fmsBudgetControl.setCashVId(null);
                        fmsBudgetControl.setChequeVId(fmsChequePaymentVoucher);
                        fmsBudgetControl.setCostCenterId(fmsCostCenter);
                        fmsBudgetControl.setGeneralLedgerId(fmsBudgetCode.getGeneralLedger());
                        fmsBudgetControl.setSubsidiaryId(fmsSubsidiaryLedger);
                        fmsBudgetControl.setPaidAmount(payedAmount);
                        fmsBudgetControl.setMonthName(monthVal);
                        fmsBudgetControlBeanLocal.create(fmsBudgetControl);

                        JsfUtil.addSuccessMessage("Data Saved successfully.");
                    }
                }
                clear();

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);

            }
        } catch (Exception ex) {
        }

    }
    //</editor-fold>

}
