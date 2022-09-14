/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.Voucher;
//<editor-fold defaultstate="collapsed" desc="import block">

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.Voucher.ChequePaymentVoucherbeanLocal;
import et.gov.eep.fcms.businessLogic.FmsAccountUseBeanLocal;
import et.gov.eep.fcms.businessLogic.perDiem.FmsFieldAllowansBeanLocal;
import et.gov.eep.fcms.businessLogic.Voucher.FmsVoucherBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsVouchersNoRangeBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.entity.FmsAccountUse;
import et.gov.eep.fcms.entity.FmsAccountUseResult;
import et.gov.eep.fcms.entity.Vocher.FmsChequePaymentVoucher;
import et.gov.eep.fcms.entity.FmsVatRecieptVoucher;
import et.gov.eep.fcms.entity.FmsDocumentFollowup;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowance;
import et.gov.eep.fcms.entity.FmsLuPaymentType;
import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsLuVouchersType;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.entity.admin.FmsVouchersNoRange;
import et.gov.eep.fcms.mapper.FmsLuPaymentTypeFacade;
import et.gov.eep.hrms.businessLogic.insure.HrinsurancePaymentLocal;
import et.gov.eep.hrms.businessLogic.medical.CashRefundApproveBeanLocal;
import et.gov.eep.hrms.businessLogic.medical.LocalMedSettlementBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollMonthlyTransactionLocal;
import et.gov.eep.hrms.entity.insurance.HrInsurancePaymentDetail;
import et.gov.eep.hrms.entity.medical.HrLocalMedSettlementDetail;
import et.gov.eep.hrms.entity.medical.HrLocalMedSettlements;
import et.gov.eep.hrms.entity.payroll.HrPayrollMonTransactions;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
import et.gov.eep.pms.businessLogic.PmsProjectPaymentReqBeanLocal;
import et.gov.eep.pms.entity.PmsProjectPaymentRequest;
import et.gov.eep.prms.businessLogic.RequestforPaymentBeanLocal;
import et.gov.eep.prms.entity.PrmsPaymentRequisition;
import et.gov.eep.prms.entity.PrmsLuVatTypeLookup;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.convert.NumberConverter;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import securityBean.WorkFlow;
import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import et.gov.eep.commonApplications.businessLogic.WfFcmsProcessedBeanLocal;
import et.gov.eep.commonApplications.localbean;
import et.gov.eep.fcms.businessLogic.FmsLuBudgetYearBeanLocal;
import et.gov.eep.fcms.businessLogic.FmsVatRecieptVoucherBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.Bond.BondForeignScheduleBeanLocal;
import et.gov.eep.fcms.businessLogic.Bond.BondLocalScheduleBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsOperatingBudgetBeanLocal;
import et.gov.eep.fcms.businessLogic.budgetYearLookUpBeanLocal;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsSystemJobJunction;
import et.gov.eep.fcms.entity.Bond.FmsBondForeign;
import et.gov.eep.fcms.entity.Bond.FmsBondForeignSchedule;
import et.gov.eep.fcms.entity.Bond.FmsBondLocalSchedule;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudget1;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPeriodsBeanLocal;
import et.gov.eep.prms.businessLogic.VatTypeLookupBeanLocal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import securityBean.Constants;
import webService.AAA;
import webService.IAdministration;
import securityBean.SessionBean;
import webService.EventEntry;
//</editor-fold>

@Named(value = "chequePaymentVoucherController")
@ViewScoped
public class ChequePaymentVoucherController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="EJBS">
    @EJB
    private ChequePaymentVoucherbeanLocal chequeVoucherBeanLocal;
    @EJB
    private FmsAccountUseBeanLocal fmsAccountUseBeanLocal;
    @EJB
    private FmsVoucherBeanLocal fmsVoucherBeanLocal;
    @EJB
    private FmsVatRecieptVoucherBeanLocal fmsVatRecieptVoucherBeanLocal;
    @EJB
    FmsLuPaymentTypeFacade paymentTypeFacade;
    @EJB
    BondForeignScheduleBeanLocal bondForeignScheduleBeanLocal;
    @EJB
    BondLocalScheduleBeanLocal bondLocalScheduleBeanLocal;
    @EJB
    LocalMedSettlementBeanLocal localMedSettlementBeanLocal;
    @EJB
    CashRefundApproveBeanLocal cashRefundApproveBeanLocal;
    @EJB
    HrPayrollMonthlyTransactionLocal hrPayrollMonthlyTransactionLocal;
    @EJB
    HrPayrollPeriodsBeanLocal hrPayrollPeriodsFacadeLocal;
    @EJB
    PmsProjectPaymentReqBeanLocal pmsProjectPaymentReqBeanLocal;
    @EJB
    FmsFieldAllowansBeanLocal fieldAllowansBeanLocal;
    @EJB
    HrinsurancePaymentLocal hrinsurancePaymentLocal;
    @EJB
    RequestforPaymentBeanLocal prmsRequestforPaymentBeanLocal;
    @EJB
    FmsVouchersNoRangeBeanLocal noRangeBeanLocal;
    @EJB
    FmsOperatingBudgetBeanLocal fmsOperatingBudgetBeanLocal;
    @EJB
    FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal;
    @EJB
    FmsLuBudgetYearBeanLocal fmsLuBudgetYearBeanLocal;
    @EJB
    budgetYearLookUpBeanLocal budgetYearLookUpBeanLocal;
    @EJB
    WfFcmsProcessedBeanLocal wfFcmsProcessedBeanLocal;
    @EJB
    FmsLuSystemBeanLocal fmsLuSystemBeanLocal;
    @EJB
    VatTypeLookupBeanLocal vatTypeLookupBeanLocal;
    @EJB
    FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal;
    @EJB
    private FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    private subsidiaryBeanLocal subsidiaryLedgerBeanLocal;

    //</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc="Inject">
    @Inject
    localbean Languagelocalbean;
    @Inject
    FmsOperatingBudget1 fmsOperatingBudget1;
    @Inject
    FmsLuBudgetYear fmsLuBudgetYear;
    @Inject
    FmsDocumentFollowup fmsDocumentFollowup;
    @Inject
    FmsChequePaymentVoucher chequePaymentVoucher;
    @Inject
    FmsVatRecieptVoucher fmsVatRecieptVoucher;
    @Inject
    FmsAccountUse accountUseEnty;
    @Inject
    FmsBondLocalSchedule bondLocalSchedule;
    @Inject
    FmsVoucher fmsVoucher;
    @Inject
    FmsVouchersNoRange fmsVouchersNoRange;
    @Inject
    PrmsLuVatTypeLookup luVatTypeLookup;
    @Inject
    WorkFlow workFlow;
    @Inject
    WfFcmsProcessed wfFcmsProcessed;
    @Inject
    private FmsLuPaymentType luPaymentType;
    @Inject
    FmsBondForeignSchedule bondForeignSchedule;
    @Inject
    HrLocalMedSettlementDetail hrLocalMedSettlementDetail;
    @Inject
    HrLocalMedSettlements hrLocalMedSettlements;
    @Inject
    HrPayrollMonTransactions hrPayrollMonTransactions;
    @Inject
    PmsProjectPaymentRequest pmsProjectPaymentRequest;
    @Inject
    FmsFieldAllowance fieldAllowance;
    @Inject
    PrmsPaymentRequisition prmsPaymentRequisition;
    @Inject
    SessionBean sessionBeanLocal;
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
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter and setters">
    public SelectItem[] getLuBudgetYearList() {
        listLuBudgetYearList = fmsLuBudgetYearBeanLocal.getLuBudgetYear();
        listLuBudgetYearList.size();

        currPeriod = fmsAccountingPeriodBeanLocal.getCurretActivePeriod();
        nextPeriod = new FmsLuBudgetYear();
        budgetyrList = new ArrayList<>();
        for (int i = 0; i < listLuBudgetYearList.size(); i++) {
            if (Objects.equals(currPeriod.getLuBudgetYearId().getLuBudgetYearId(), listLuBudgetYearList.get(i).getLuBudgetYearId())) {
                nextPeriod = listLuBudgetYearList.get(i + 1);
            }
        }
        budgetyrList.add(currPeriod.getLuBudgetYearId());
        budgetyrList.add(nextPeriod);
        return JsfUtil.getSelectItems(budgetyrList, true);
    }

    public void yearValueChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuBudgetYear.setBudgetYear(event.getNewValue().toString());
            fmsLuBudgetYear = budgetYearLookUpBeanLocal.getYearInfo(fmsLuBudgetYear);
            fmsOperatingBudget1.setBudgetYear(fmsLuBudgetYear);
        }
    }

    public void handleDecision(ValueChangeEvent decision) {
        if (decision.getNewValue() != null) {
            selectedDecision = decision.getNewValue().toString();
        }
    }

    public void changeSignature(ValueChangeEvent e) {
        if (e.getNewValue().toString().contains("1")) {
            types = "1";

            System.err.println("-eeeeeeee--" + e.getNewValue().toString());
        } else {
            types = "2";
        }

    }

    public HrPayrollMonTransactions getHrPayrollMonTransactions() {
        if (hrPayrollMonTransactions == null) {
            hrPayrollMonTransactions = new HrPayrollMonTransactions();
        }
        return hrPayrollMonTransactions;
    }

    public void setHrPayrollMonTransactions(HrPayrollMonTransactions hrPayrollMonTransactions) {
        this.hrPayrollMonTransactions = hrPayrollMonTransactions;
    }

    public HrLocalMedSettlementDetail getHrLocalMedSettlementDetail() {
        if (hrLocalMedSettlementDetail == null) {
            hrLocalMedSettlementDetail = new HrLocalMedSettlementDetail();
        }
        return hrLocalMedSettlementDetail;
    }

    public void setHrLocalMedSettlementDetail(HrLocalMedSettlementDetail hrLocalMedSettlementDetail) {
        this.hrLocalMedSettlementDetail = hrLocalMedSettlementDetail;
    }

    public HrLocalMedSettlements getHrLocalMedSettlements() {
        if (hrLocalMedSettlements == null) {
            hrLocalMedSettlements = new HrLocalMedSettlements();
        }
        return hrLocalMedSettlements;
    }

    public void setHrLocalMedSettlements(HrLocalMedSettlements hrLocalMedSettlements) {
        this.hrLocalMedSettlements = hrLocalMedSettlements;
    }

    public List<HrLocalMedSettlementDetail> getHrmedSettlmentDetailList() {
        if (hrmedSettlmentDetailList == null) {
            hrmedSettlmentDetailList = new ArrayList<>();
        }
        return hrmedSettlmentDetailList;
    }

    public void setHrmedSettlmentDetailList(List<HrLocalMedSettlementDetail> hrmedSettlmentDetailList) {
        this.hrmedSettlmentDetailList = hrmedSettlmentDetailList;
    }

    public List<HrLocalMedSettlements> getHrmedSettlmentList() {
        if (hrmedSettlmentList == null) {
            hrmedSettlmentList = new ArrayList<>();
        }
        return hrmedSettlmentList;
    }

    public void setHrmedSettlmentList(List<HrLocalMedSettlements> hrmedSettlmentList) {
        this.hrmedSettlmentList = hrmedSettlmentList;
    }

    public List<FmsFieldAllowance> getFieldAllowances() {
        if (fieldAllowances == null) {
            fieldAllowances = new ArrayList<>();
        }
        return fieldAllowances;
    }

    public FmsFieldAllowance getFieldAllowance() {
        if (fieldAllowance == null) {
            fieldAllowance = new FmsFieldAllowance();
        }
        return fieldAllowance;
    }

    public void setFieldAllowance(FmsFieldAllowance fieldAllowance) {
        this.fieldAllowance = fieldAllowance;
    }

    public void setFieldAllowances(List<FmsFieldAllowance> fieldAllowances) {
        this.fieldAllowances = fieldAllowances;
    }

    public List<String> getReferenceNumbers() {
        if (referenceNumbers == null) {
            referenceNumbers = new ArrayList<>();
        }
        return referenceNumbers;
    }

    public void setReferenceNumbers(List<String> referenceNumbers) {
        this.referenceNumbers = referenceNumbers;
    }

    public DataModel<FmsVoucher> getVoucherDataModel() {
        if (fmsVoucherDataModel == null) {
            fmsVoucherDataModel = new ListDataModel<>();
        }
        return fmsVoucherDataModel;
    }

    public void setVoucherDataModel(DataModel<FmsVoucher> fmsVoucherDataModel) {
        this.fmsVoucherDataModel = fmsVoucherDataModel;
    }

    public int getInterCatListSizes() {
        return interCatListSizes;
    }

    public void setInterCatListSizes(int interCatListSizes) {
        this.interCatListSizes = interCatListSizes;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getIcone1() {
        return icone1;
    }

    public void setIcone1(String icone1) {
        this.icone1 = icone1;
    }

    public boolean isPrinte() {
        return printe;
    }

    public void setPrinte(boolean printe) {
        this.printe = printe;
    }

    public Set<String> getCheckSubsidiaryCode() {
        return checkSubsidiaryCode;
    }

    public void setCheckSubsidiaryCode(Set<String> checkSubsidiaryCode) {
        this.checkSubsidiaryCode = checkSubsidiaryCode;
    }

    public Set<BigInteger> getCheckDebitCredit() {
        return checkDebitCredit;
    }

    public void setCheckDebitCredit(Set<BigInteger> checkDebitCredit) {
        this.checkDebitCredit = checkDebitCredit;
    }

    public int getGLListSize() {
        return GLListSize;
    }

    public void setGLListSize(int GLListSize) {
        this.GLListSize = GLListSize;
    }

//    public FmsSubsidiaryLedger getSubsidiaryLedger() {
//        return subsidiaryLedger;
//    }
//
//    public void setSubsidiaryLedger(FmsSubsidiaryLedger subsidiaryLedger) {
//        this.subsidiaryLedger = subsidiaryLedger;
//    }
    public FmsAccountUse getSelectedAccountUses() {
        return selectedAccountUses;
    }

    public void setSelectedAccountUses(FmsAccountUse selectedAccountUses) {
        this.selectedAccountUses = selectedAccountUses;
    }

    public boolean isCc() {
        return cc;
    }

    public void setCc(boolean cc) {
        this.cc = cc;
    }

    public boolean isSl() {
        return sl;
    }

    public void setSl(boolean sl) {
        this.sl = sl;
    }

    public List<FmsVoucher> getSelectedJv() {
        return selectedJv;
    }

    public void setSelectedJv(List<FmsVoucher> selectedJv) {
        this.selectedJv = selectedJv;
    }

    public int getIntermidiateCatStatus() {
        return IntermidiateCatStatus;
    }

    public void setIntermidiateCatStatus(int IntermidiateCatStatus) {
        this.IntermidiateCatStatus = IntermidiateCatStatus;
    }

    public int getCatagorynameStatus() {
        return catagorynameStatus;
    }

    public void setCatagorynameStatus(int catagorynameStatus) {
        this.catagorynameStatus = catagorynameStatus;
    }

    public boolean isAddButtonStatus() {
        return addButtonStatus;
    }

    public void setAddButtonStatus(boolean addButtonStatus) {
        this.addButtonStatus = addButtonStatus;
    }

    public String getAdd_colaps() {
        return add_colaps;
    }

    public void setAdd_colaps(String add_colaps) {
        this.add_colaps = add_colaps;
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

    public String getCreateOrSearchBundle1() {
        return createOrSearchBundle1;
    }

    public void setCreateOrSearchBundle1(String createOrSearchBundle1) {
        this.createOrSearchBundle1 = createOrSearchBundle1;
    }

    public boolean isRenderPnlCreateJv() {
        return renderPnlCreateJv;
    }

    public void setRenderPnlCreateJv(boolean renderPnlCreateJv) {
        this.renderPnlCreateJv = renderPnlCreateJv;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public Boolean getSelectOptionStatus() {
        return selectOptionStatus;
    }

    public void setSelectOptionStatus(Boolean selectOptionStatus) {
        this.selectOptionStatus = selectOptionStatus;
    }

    public Boolean getEditRemoveStatus() {
        return editRemoveStatus;
    }

    public void setEditRemoveStatus(Boolean editRemoveStatus) {
        this.editRemoveStatus = editRemoveStatus;
    }

    public Boolean getVoid_dis() {
        return void_dis;
    }

    public void setVoid_dis(Boolean void_dis) {
        this.void_dis = void_dis;
    }

    public List<FmsVoucher> getListVoucher() {
        return listVoucher;
    }

    public void setListVoucher(List<FmsVoucher> listVoucher) {
        this.listVoucher = listVoucher;
    }

    public int getMajorCatagorylistId() {
        return MajorCatagorylistId;
    }

    public void setMajorCatagorylistId(int MajorCatagorylistId) {
        this.MajorCatagorylistId = MajorCatagorylistId;
    }

    public FmsVouchersNoRange getFmsVouchersNoRange() {
        if (fmsVouchersNoRange == null) {
            fmsVouchersNoRange = new FmsVouchersNoRange();
        }
        return fmsVouchersNoRange;
    }

    public void setFmsVouchersNoRange(FmsVouchersNoRange fmsVouchersNoRange) {
        this.fmsVouchersNoRange = fmsVouchersNoRange;
    }

    public PrmsLuVatTypeLookup getLuVatTypeLookup() {
        if (luVatTypeLookup == null) {
            luVatTypeLookup = new PrmsLuVatTypeLookup();
        }
        return luVatTypeLookup;
    }

    public void setLuVatTypeLookup(PrmsLuVatTypeLookup luVatTypeLookup) {
        this.luVatTypeLookup = luVatTypeLookup;
    }

    public List<FmsGeneralLedger> getListGeneralLedger() {
        return listGeneralLedger;
    }

    public void setListGeneralLedger(List<FmsGeneralLedger> listGeneralLedger) {
        this.listGeneralLedger = listGeneralLedger;
    }

//    public FmsGeneralLedger getFmsGeneralLedger() {
//        if (fmsGeneralLedger == null) {
//            fmsGeneralLedger = new FmsGeneralLedger();
//        }
//        return fmsGeneralLedger;
//    }
    /**
     *
     * @param fmsGeneralLedger
     */
//    public void setFmsGeneralLedger(FmsGeneralLedger fmsGeneralLedger) {
//        this.fmsGeneralLedger = fmsGeneralLedger;
//    }
    /**
     *
     * @return
     */
//    public FmsSubsidiaryLedger getFmsSubsid1aryLedger1() {
//        if (fmsSubsid1aryLedger1 == null) {
//            fmsSubsid1aryLedger1 = new FmsSubsidiaryLedger();
//
//        }
//        return fmsSubsid1aryLedger1;
//    }
    /**
     *
     * @param fmsSubsid1aryLedger1
     */
//    public void setFmsSubsid1aryLedger1(FmsSubsidiaryLedger fmsSubsid1aryLedger1) {
//        this.fmsSubsid1aryLedger1 = fmsSubsid1aryLedger1;
//    }
    /**
     *
     * @return
     */
//    public FmsLuSystem getFmsLuSystem() {
//        if (fmsLuSystem == null) {
//            fmsLuSystem = new FmsLuSystem();
//        }
//        return fmsLuSystem;
//    }
    /**
     *
     * @param fmsLuSystem
     */
//    public void setFmsLuSystem(FmsLuSystem fmsLuSystem) {
//        this.fmsLuSystem = fmsLuSystem;
//    }
    /**
     *
     * @return
     */
//    public FmsCostCenter getFmsCostCenter() {
//        if (fmsCostCenter == null) {
//            fmsCostCenter = new FmsCostCenter();
//        }
//        return fmsCostCenter;
//    }
    /**
     *
     * @param fmsCostCenter
     */
//    public void setFmsCostCenter(FmsCostCenter fmsCostCenter) {
//        this.fmsCostCenter = fmsCostCenter;
//    }
    /**
     *
     * @return
     */
    public FmsDocumentFollowup getFmsDocumentFollowup() {
        if (fmsDocumentFollowup == null) {
            fmsDocumentFollowup = new FmsDocumentFollowup();

        }
        return fmsDocumentFollowup;
    }

    /**
     *
     * @param fmsDocumentFollowup
     */
    public void setFmsDocumentFollowup(FmsDocumentFollowup fmsDocumentFollowup) {
        this.fmsDocumentFollowup = fmsDocumentFollowup;
    }

    /**
     *
     * @return
     */
    public FmsChequePaymentVoucher getChequePaymentVoucher() {
        if (chequePaymentVoucher == null) {
            chequePaymentVoucher = new FmsChequePaymentVoucher();
        }
        return chequePaymentVoucher;
    }

    /**
     *
     * @param chequePaymentVoucher
     */
    public void setChequePaymentVoucher(FmsChequePaymentVoucher chequePaymentVoucher) {
        this.chequePaymentVoucher = chequePaymentVoucher;
    }

    public FmsVatRecieptVoucher getFmsVatRecieptVoucher() {
        if (fmsVatRecieptVoucher == null) {
            fmsVatRecieptVoucher = new FmsVatRecieptVoucher();
        }
        return fmsVatRecieptVoucher;
    }

    public void setFmsVatRecieptVoucher(FmsVatRecieptVoucher fmsVatRecieptVoucher) {
        this.fmsVatRecieptVoucher = fmsVatRecieptVoucher;
    }

    /**
     *
     * @return
     */
    public BigDecimal getValueDebitCredit() {
        return ValueDebitCredit;
    }

    /**
     *
     * @param ValueDebitCredit
     */
    public void setValueDebitCredit(BigDecimal ValueDebitCredit) {
        this.ValueDebitCredit = ValueDebitCredit;
    }

    /**
     *
     * @return
     */
    public String getActionDebitCredit() {

        return ActionDebitCredit;
    }

    /**
     *
     * @param ActionDebitCredit
     */
    public void setActionDebitCredit(String ActionDebitCredit) {
        this.ActionDebitCredit = ActionDebitCredit;
    }

    /**
     *
     * @return
     */
//    public FmsGeneralLedger getGeneralLedger() {
//        if (generalLedger == null) {
//            generalLedger = new FmsGeneralLedger();
//        }
//        return generalLedger;
//    }
    /**
     *
     * @param generalLedger
     */
//    public void setGeneralLedger(FmsGeneralLedger generalLedger) {
//        this.generalLedger = generalLedger;
//    }
    /**
     *
     * @return
     */
    public DataModel<FmsAccountUse> getAccountUseDetailDataModel() {
        if (accountUseDetailDataModel == null) {
            accountUseDetailDataModel = new ListDataModel<>();
        }
        return accountUseDetailDataModel;
    }

    /**
     *
     * @param accountUseDetailDataModel
     */
    public void setAccountUseDetailDataModel(DataModel<FmsAccountUse> accountUseDetailDataModel) {
        this.accountUseDetailDataModel = accountUseDetailDataModel;
    }

    /**
     *
     * @return
     */
    public DataModel<FmsChequePaymentVoucher> getChequePaymentDataModel() {
        if (chequePaymentDataModel == null) {
            chequePaymentDataModel = new ArrayDataModel<>();
        }
        return chequePaymentDataModel;
    }

    /**
     *
     * @param chequePaymentDataModel
     */
    public void setChequePaymentDataModel(DataModel<FmsChequePaymentVoucher> chequePaymentDataModel) {
        this.chequePaymentDataModel = chequePaymentDataModel;
    }

    public FmsAccountUse getAccountUseEnty() {
        if (accountUseEnty == null) {
            accountUseEnty = new FmsAccountUse();
        }
        return accountUseEnty;
    }

    /**
     *
     * @param accountUseEnty
     */
    public void setAccountUseEnty(FmsAccountUse accountUseEnty) {
        this.accountUseEnty = accountUseEnty;
    }

    /**
     *
     * @return
     */
    public FmsVoucher getFmsVoucher() {
        if (fmsVoucher == null) {
            fmsVoucher = new FmsVoucher();
        }
        return fmsVoucher;
    }

    /**
     *
     * @param fmsVoucher
     */
    public void setFmsVoucher(FmsVoucher fmsVoucher) {
        this.fmsVoucher = fmsVoucher;
    }

    //--------------------------------------------
    /**
     *
     * @return
     */
    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    /**
     *
     * @param saveorUpdateBundle
     */
    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    /**
     *
     * @return
     */
    public String getConc() {
        return Conc;
    }

    /**
     *
     * @param Conc
     */
    public void setConc(String Conc) {
        this.Conc = Conc;
    }

    /**
     *
     * @return
     */
    public int getUpdteStatus() {
        return updteStatus;
    }

    /**
     *
     * @param updteStatus
     */
    public void setUpdteStatus(int updteStatus) {
        this.updteStatus = updteStatus;
    }

    public FmsBondLocalSchedule getBondLocalSchedule() {
        if (bondLocalSchedule == null) {
            bondLocalSchedule = new FmsBondLocalSchedule();
        }
        return bondLocalSchedule;
    }

    public void setBondLocalSchedule(FmsBondLocalSchedule bondLocalSchedule) {
        this.bondLocalSchedule = bondLocalSchedule;
    }

    public FmsLuPaymentType getLuPaymentType() {
        if (luPaymentType == null) {
            luPaymentType = new FmsLuPaymentType();
        }
        return luPaymentType;
    }

    public void setLuPaymentType(FmsLuPaymentType luPaymentType) {
        this.luPaymentType = luPaymentType;
    }

    public List<FmsLuPaymentType> getFmsLuPaymentType() {
        FmsLuPaymentType flpt = new FmsLuPaymentType();
        flpt.setPayment("PV");
        return paymentTypeFacade.findByPaymentType(flpt);
    }

    public void setFmsLuPaymentType(List<FmsLuPaymentType> fmsLuPaymentType) {
        this.fmsLuPaymentType = fmsLuPaymentType;
    }

    public List<FmsBondLocalSchedule> getBondLocalSchedules() {
        if (bondLocalSchedules == null) {
            bondLocalSchedules = new ArrayList<>();
        }
        return bondLocalSchedules;
    }

    public void setBondLocalSchedules(List<FmsBondLocalSchedule> bondLocalSchedules) {
        this.bondLocalSchedules = bondLocalSchedules;
    }

    public FmsBondForeignSchedule getBondForeignSchedule() {
        if (bondForeignSchedule == null) {
            bondForeignSchedule = new FmsBondForeignSchedule();
        }
        return bondForeignSchedule;
    }

    public void setBondForeignSchedule(FmsBondForeignSchedule bondForeignSchedule) {
        this.bondForeignSchedule = bondForeignSchedule;
    }

    public List<FmsBondForeignSchedule> getBondForeignSchedules() {
        bondForeignSchedules = bondForeignScheduleBeanLocal.searchForingns_Bondpayed();
        return bondForeignSchedules;
    }

    public void setBondForeignSchedules(List<FmsBondForeignSchedule> bondForeignSchedules) {
        this.bondForeignSchedules = bondForeignSchedules;
    }

    public DataModel<FmsAccountUse> getAccountUseDetailDataModelposting() {
        accountUseDetailDataModelposting = new ListDataModel(fmsVouchersDetailDataModel.getRowData().getFmsAccountUseList());
        return accountUseDetailDataModelposting;
    }

    public void setAccountUseDetailDataModelposting(DataModel<FmsAccountUse> accountUseDetailDataModelposting) {
        this.accountUseDetailDataModelposting = accountUseDetailDataModelposting;
    }

    public boolean isCreateNEWrend() {
        return createNEWrend;
    }

    public void setCreateNEWrend(boolean createNEWrend) {
        this.createNEWrend = createNEWrend;
    }

    public boolean isNew1() {
        return new1;
    }

    public void setNew1(boolean new1) {
        this.new1 = new1;
    }

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    /**
     *
     * @param numberConverter
     */
    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public double getTotalamt() {
        return totalamt;
    }

    public void setTotalamt(double totalamt) {
        this.totalamt = totalamt;
    }

    public String getSubLName() {
        return subLName;
    }

    public void setSubLName(String subLName) {
        this.subLName = subLName;
    }

    public String getCostName() {
        return costName;
    }

    public void setCostName(String costName) {
        this.costName = costName;
    }

    public String getGeneralLName() {
        return generalLName;
    }

    public void setGeneralLName(String generalLName) {
        this.generalLName = generalLName;
    }

    private String sysName;

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public SelectItem[] getGLdata() {
        return JsfUtil.getSelectItems(fmsGeneralLedgerBeanLocal.findAll(), true);
    }

    public List<FmsGeneralLedger> getFmsGeneralLedgerList() {
        return fmsGeneralLedgerBeanLocal.findAll();
    }

    public void setFmsGeneralLedgerList(List<FmsGeneralLedger> FmsGeneralLedgerList) {
        this.FmsGeneralLedgerList = FmsGeneralLedgerList;
    }
    private List<FmsLuSystem> listOfSystems;

    public List<FmsLuSystem> getListOfSystems() {
        listOfSystems = fmsLuSystemBeanLocal.findAll();
        return listOfSystems;
    }

    public void setListOfSystems(List<FmsLuSystem> listOfSystems) {
        this.listOfSystems = listOfSystems;
    }

    public SelectItem[] getListSys() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findAll(), true);
    }

    public boolean isSaveVochNo() {
        return saveVochNo;
    }

    public void setSaveVochNo(boolean saveVochNo) {
        this.saveVochNo = saveVochNo;
    }

    public boolean isPopvocjNo() {
        return popvocjNo;
    }

    public void setPopvocjNo(boolean popvocjNo) {
        this.popvocjNo = popvocjNo;
    }

    public boolean isRefdisable() {
        return refdisable;
    }

    public void setRefdisable(boolean refdisable) {
        this.refdisable = refdisable;
    }

    public boolean isRefbondlocal() {
        return refbondlocal;
    }

    public void setRefbondlocal(boolean refbondlocal) {
        this.refbondlocal = refbondlocal;
    }

    public boolean isRefbondforeign() {
        return refbondforeign;
    }

    public void setRefbondforeign(boolean refbondforeign) {
        this.refbondforeign = refbondforeign;
    }

    public boolean isOthers() {
        return others;
    }

    public void setOthers(boolean others) {
        this.others = others;
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

    public List<PrmsLuVatTypeLookup> getTaxList() {
        if (taxList == null) {
            taxList = new ArrayList<>();
        }
        taxList = vatTypeLookupBeanLocal.findAll();
        return taxList;
    }

    public void setTaxList(List<PrmsLuVatTypeLookup> taxList) {
        this.taxList = taxList;
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

    public String getVoucherNO() {
        return fmsVoucherNO;
    }

    public void setVoucherNO(String fmsVoucherNO) {
        this.fmsVoucherNO = fmsVoucherNO;
    }

    public List<FmsVoucher> getFmsVouchersLists() {
        if (fmsVouchersLists == null) {
            fmsVouchersLists = new ArrayList<>();
        }
        return fmsVouchersLists;
    }

    public void setFmsVouchersLists(List<FmsVoucher> fmsVouchersLists) {
        this.fmsVouchersLists = fmsVouchersLists;
    }

    public DataModel<FmsVoucher> getVouchersDetailDataModel() {
        if (fmsVouchersDetailDataModel == null) {
            fmsVouchersDetailDataModel = new ListDataModel<>();
        }
        return fmsVouchersDetailDataModel;
    }

    /**
     *
     * @param fmsVouchersDetailDataModel
     */
    public void setVouchersDetailDataModel(DataModel<FmsVoucher> fmsVouchersDetailDataModel) {
        this.fmsVouchersDetailDataModel = fmsVouchersDetailDataModel;
    }

    /**
     *
     * @return
     */
    public List<FmsVoucher> getVouchersList() {

        return fmsVouchersList;
    }

    /**
     *
     * @param fmsVouchersList
     */
    public void setVouchersList(List<FmsVoucher> fmsVouchersList) {
        this.fmsVouchersList = fmsVouchersList;
    }

    /**
     *
     * @return
     */
    public int getDataTableUpdateStatus() {
        return dataTableUpdateStatus;
    }

    /**
     *
     * @param dataTableUpdateStatus
     */
    public void setDataTableUpdateStatus(int dataTableUpdateStatus) {
        this.dataTableUpdateStatus = dataTableUpdateStatus;
    }

    /**
     *
     * @return
     */
    public String getPRStatus() {
        return PRStatus;
    }

    /**
     *
     * @param PRStatus
     */
    public void setPRStatus(String PRStatus) {
        this.PRStatus = PRStatus;
    }

    public String getRederedCheckBox() {
        return rederedCheckBox;
    }

    /**
     *
     * @param rederedCheckBox
     */
    public void setRederedCheckBox(String rederedCheckBox) {
        this.rederedCheckBox = rederedCheckBox;
    }

    public List<FmsVoucher> getSelectedVouches2() {
        if (selectedVouches2 == null) {
            selectedVouches2 = new ArrayList<>();
        }
        return selectedVouches2;
    }

    public void setSelectedVouches2(List<FmsVoucher> selectedVouches2) {
        this.selectedVouches2 = selectedVouches2;
    }

    /**
     *
     * @return
     */
    public List<FmsVoucher> getSelectedVouches() {
        if (selectedVouches == null) {
            selectedVouches = new ArrayList<>();
        }
        return selectedVouches;
    }

    /**
     *
     * @param selectedVouches
     */
    public void setSelectedVouches(List<FmsVoucher> selectedVouches) {
        this.selectedVouches = selectedVouches;
    }

    public FmsVoucher getVoucher() {
        if (fmsVoucher == null) {
            fmsVoucher = new FmsVoucher();
        }
        return fmsVoucher;
    }

    public void setVoucher(FmsVoucher fmsVoucher) {
        this.fmsVoucher = fmsVoucher;
    }

    public String getVoucherId() {
        return fmsVoucherId;
    }

    public void setVoucherId(String fmsVoucherId) {
        this.fmsVoucherId = fmsVoucherId;
    }

    public List<FmsAccountUseResult> getAccountUseResults() {
        return accountUseResults;
    }

    public void setAccountUseResults(List<FmsAccountUseResult> accountUseResults) {
        this.accountUseResults = accountUseResults;
    }

    public FmsAccountUseResult getAccountUseResult() {
        return accountUseResult;
    }

    public void setAccountUseResult(FmsAccountUseResult accountUseResult) {
        this.accountUseResult = accountUseResult;
    }

    public double getCredit() {
        return Credit;
    }

    public void setCredit(double Credit) {
        this.Credit = Credit;
    }

    public double getDebit() {
        return Debit;
    }

    public FmsLuBudgetYear getFmsLuBudgetYear() {
        return fmsLuBudgetYear;
    }

    public List<FmsCostCenter> getCostCenterList() {
        if (costCenterList == null) {
            costCenterList = new ArrayList<>();
        }
        return costCenterList;
    }

    public List<Object> getAccountCredit() {
        return accountCredit;
    }

    public void setAccountCredit(List<Object> accountCredit) {
        this.accountCredit = accountCredit;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void setCostCenterList(List<FmsCostCenter> costCenterList) {
        this.costCenterList = costCenterList;
    }

    public List<FmsSubsidiaryLedger> getSubsidaryList() {
        if (subsidaryList == null) {
            subsidaryList = new ArrayList<>();

        }
        return subsidaryList;
    }

    public void setSubsidaryList(List<FmsSubsidiaryLedger> subsidaryList) {
        this.subsidaryList = subsidaryList;
    }

    public void setFmsLuBudgetYear(FmsLuBudgetYear fmsLuBudgetYear) {
        this.fmsLuBudgetYear = fmsLuBudgetYear;
    }

    public void setDebit(double Debit) {
        this.Debit = Debit;
    }

    public String getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(String processedBy) {
        this.processedBy = processedBy;
    }

    public Boolean getDecision() {
        return decision;
    }

    public void setDecision(Boolean decision) {
        this.decision = decision;
    }

    public DataModel<WfFcmsProcessed> getWorkflowDataModel() {
//        if (workflowDataModel == null) {
//            workflowDataModel = new ArrayDataModel<>();
//        }
        return workflowDataModel;
    }

    public void setWorkflowDataModel(DataModel<WfFcmsProcessed> workflowDataModel) {
        this.workflowDataModel = workflowDataModel;
    }

    public boolean isTaxrend() {
        return taxrend;
    }

    public void setTaxrend(boolean taxrend) {
        this.taxrend = taxrend;
    }

    public String getWITHHOLD() {
        return WITHHOLD;
    }

    public void setWITHHOLD(String WITHHOLD) {
        this.WITHHOLD = WITHHOLD;
    }

    public String getVatTax() {
        return VatTax;
    }

    public void setVatTax(String VatTax) {
        this.VatTax = VatTax;
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

    public WfFcmsProcessed getWfFcmsProcessed() {
        if (wfFcmsProcessed == null) {
            wfFcmsProcessed = new WfFcmsProcessed();
        }
        return wfFcmsProcessed;
    }

    public void setWfFcmsProcessed(WfFcmsProcessed wfFcmsProcessed) {
        this.wfFcmsProcessed = wfFcmsProcessed;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Object and variable declaration">
    List<FmsGeneralLedger> listGeneralLedger = null;
    int GLListSize = 0;
    Set<String> checkSubsidiaryCode = new HashSet<>();
    Set<BigInteger> checkDebitCredit = new HashSet<>();
    List<FmsSubsidiaryLedger> fmsSubsidiaryLedgerList;
    double Credit = 0.0;
    double Debit = 0.0;
    double totalamt = 0.0;
    boolean cc = true;
    private String costName;
    List<FmsGeneralLedger> FmsGeneralLedgerList;
    List<FmsCostCenter> FmsCostCenterList;
    boolean refbondlocal = false;
    boolean refbondforeign = false;
    boolean others = false;
    String amountInword;
    boolean refdisable = true;
    String reftype = "nun";

    List<FmsCostcSystemJunction> ssCcJunctionList = new ArrayList<>();
    List<FmsSystemJobJunction> sysJobNOList;
    List<FmsGeneralLedger> glList;
    List<FmsSubsidiaryLedger> slList;
    List<FmsLuSystem> systemList;
    List<PrmsLuVatTypeLookup> taxList;
    private String Conc = "";
    private String display_conn;
    boolean renderJobNo = false;
    final Integer projectType = 2;
    final Integer nonProjectType = 1;
    String fmsVoucherNO = "";
    private String PRStatus;
    private DataModel<FmsVoucher> fmsVouchersDetailDataModel;
    List<FmsVoucher> fmsVouchersList = new ArrayList<>();
    String rederedCheckBox = "true";
    private List<FmsVoucher> selectedVouches = new ArrayList<>();
    private List<FmsVoucher> selectedVouches2;
    List<FmsCostCenter> costCenterList;
    List<FmsSubsidiaryLedger> subsidaryList;
    int dataTableUpdateStatus = 0;
    List<FmsVoucher> listVoucher = null;
    int MajorCatagorylistId = 0;
    DataModel<FmsAccountUse> accountUseDetailDataModel;
    DataModel<FmsAccountUse> accountUseDetailDataModelposting;
    DataModel<FmsVoucher> fmsVoucherDataModel;
    DataModel<FmsChequePaymentVoucher> chequePaymentDataModel;
    DataModel<WfFcmsProcessed> workflowDataModel = new ListDataModel<>();
    private FmsAccountUse selectedAccountUses;
    private List<FmsVoucher> selectedJv;
    private List<FmsLuPaymentType> fmsLuPaymentType;
    private List<FmsBondLocalSchedule> bondLocalSchedules;
    List<FmsLuBudgetYear> budgetyrList = new ArrayList<>();
    private List<FmsBondForeignSchedule> bondForeignSchedules;
    List<FmsLuBudgetYear> listLuBudgetYearList = new ArrayList<>();
    List<HrLocalMedSettlementDetail> hrmedSettlmentDetailList;
    List<HrLocalMedSettlements> hrmedSettlmentList;
    List<HrPayrollMonTransactions> hrPayrollMonTransactionsList;
    List<PrmsPaymentRequisition> prmsPaymentRequisitionsList;
    List<PmsProjectPaymentRequest> pmsProjectPaymentReqsList;
    List<FmsFieldAllowance> fieldAllowances;
    List<HrInsurancePaymentDetail> hrInsurancePaymentList;
    List<String> referenceNumbers;
    List<FmsVoucher> fmsVouchersLists;
    int updteStatus = 0;
    int onupdate = 0;
    private String selectedDecision = "";
    private String saveorUpdateBundle = "Create";
    private String ActionDebitCredit = "";
    private BigDecimal ValueDebitCredit = new BigDecimal(0.0);
    int interCatListSizes = 0;
    int IntermidiateCatStatus = 0;
    int catagorynameStatus = 0;
    boolean addButtonStatus = false;
    boolean taxrend = false;
    private boolean disableBtnCreate;
    private String createOrSearchBundle = "New";
    private String createOrSearchBundle1 = "New";
    private boolean renderPnlCreateJv = false;
    private boolean renderPnlCreateJv1 = false;
    private boolean renderPnlManPage = true;
    private boolean printe = false;
    private String icone = "ui-icon-plus";
    private String icone1 = "ui-icon-plus";
    Boolean selectOptionStatus = false;
    Boolean editRemoveStatus = false;
    Boolean void_dis = true;
    boolean createNEWrend = false;

//    String WITHHOLD = "0.00";
    Boolean decision = false;
    FmsAccountingPeriod currPeriod = new FmsAccountingPeriod();
    FmsLuBudgetYear nextPeriod = new FmsLuBudgetYear();
    boolean saveVochNo = false;
    boolean popvocjNo = false;
    boolean new1 = false;
    String fmsVoucherId = null;
    String WITHHOLD;
    String processedBy = "";
    String types = "Prepared By";
    String VatTax = "0.00";
    String add_colaps = "add";
    private List<Object> accountCredit;
    private HashMap<String, BigDecimal> accountDebit = new HashMap<>();
    private HashMap<String, BigDecimal> accountcredit = new HashMap<>();
    private HashMap<HashMap, HashMap> account = new HashMap<>();
    private DataModel<HashMap> accounthashmapModel;
    private String accountNo;
    private BigDecimal value;
    private List<Entry<String, BigDecimal>> entries;
    private List<Entry<String, BigDecimal>> entries_;
    FmsAccountUseResult accountUseResult;
    List<FmsAccountUseResult> accountUseResults = new ArrayList<>();
    List<FmsVoucher> jvList;
    private String generalLName;
    boolean sl = true;
    private String subLName;
    private NumberConverter numberConverter = new NumberConverter();

    //</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc="PostConstruct - implimentation">
    @PostConstruct
    public void init() {
        ItemStausBarChartCreator();
        String refNo = null;
        Integer seqNo = 0;
        fmsVouchersNoRange.setStatus(Constants.CHECK_APPROVE_VALUE);
        FmsLuVouchersType luVouchersType = new FmsLuVouchersType();
        luVouchersType.setId(2);
        fmsVouchersNoRange.setTypeId(luVouchersType);
        fmsVoucher.setPreparedBy(workFlow.getUserName());
        fmsVoucher.setProcessedBy(workFlow.getUserName());
        if (workFlow.isPrepareStatus()) {
            processedBy = "Prepared By";
            decision = true;
        } else if (workFlow.isApproveStatus()) {
            processedBy = "Approved By";
            decision = false;
        } else if (workFlow.isCheckStatus()) {
            processedBy = "check By";
            decision = true;
        }
        if (Languagelocalbean.getLangsession().getAttribute("lang") != null) {
            if (Languagelocalbean.getLangsession().getAttribute("lang").toString().equalsIgnoreCase("am")) {
                Languagelocalbean.changeLanguage("ET");
            } else {
                Languagelocalbean.changeLanguage(Languagelocalbean.getLangsession().getAttribute("lang").toString());
            }
        }
    }

    //</editor-fold>
    List<FmsVoucher> fmsVouchersList1;

    List<FmsVoucher> FmsVoucherSearchingParameterList;

    public List<FmsVoucher> getFmsVoucherSearchingParameterList() {
        if (FmsVoucherSearchingParameterList == null) {
            FmsVoucherSearchingParameterList = new ArrayList<>();
            FmsVoucherSearchingParameterList = fmsVoucherBeanLocal.getFmsVoucherSearchingParameterList();
            System.out.println("FmsVoucherSearchingParameterList==" + FmsVoucherSearchingParameterList);
        }
        return FmsVoucherSearchingParameterList;

    }

    public void setFmsVoucherSearchingParameterList(List<FmsVoucher> FmsVoucherSearchingParameterList) {
        this.FmsVoucherSearchingParameterList = FmsVoucherSearchingParameterList;
    }

    //<editor-fold defaultstate="collapsed" desc="handleselectOptions">
    public void handleselectOptions(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selectedDecision = event.getNewValue().toString();
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="convertAmountInWored">
    public void convertAmountInWored(double AmountInfigure) {
        System.err.println("inside the event");
        try {

            double num = AmountInfigure;
            int birr = (int) Math.floor(num);
            int cents = (int) Math.floor((num - birr) * 100);
            int cent1;
            cent1 = (int) ((num - birr) * 10000);
            if (!(cent1 % 10 == 0)) {
                double cent_1 = cent1 / 100.0;
                cents = (int) Math.ceil(cent_1);
            }
            String amountInwordConverted = EnglishNumberToWords.convert(birr) + " Birr" + " and "
                    + EnglishNumberToWords.convert(cents) + " Cents";
            amountInword = amountInwordConverted;
        } catch (Exception ex) {
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Cheque Payment Voucher Controller">
    public ChequePaymentVoucherController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(3);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="create New cheque payment voucher">
    public void createNewJv1() {
        System.out.println("=== createNewJv1 ===");
        clearPage();
        clearPopup();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderPnlCreateJv1 = true;
        renderPnlManPage = false;
        createOrSearchBundle1 = "New";
        setIcone1("ui-icon-plus");
        selectOptionStatus = false;
        saveVochNo = false;
        new1 = false;
        createNEWrend = false;
    }

    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="save - Update">
    public String saveChequePaymentVoucher() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(sessionBeanLocal.getUserName(), "saveChequePaymentVoucher", dataset)) {
//                 put ur code here...! 
            double amt = chequePaymentVoucher.getAmountInFigure().doubleValue();
            convertAmountInWored(amt);
            chequePaymentVoucher.setAmountInWord(amountInword);
            if (((amt == Debit) && (Debit == Credit)) || (onupdate == 1)) {
                System.out.println("  if (((amt == Debit) && (Debit == Credit)))");
                if (updteStatus == 0) {
                    String fmsVoucherID, preparedate;
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    preparedate = dateFormat.format(chequePaymentVoucher.getPreparedDate());
                    fmsVoucherID = preparedate + "-" + Integer.parseInt(fmsVouchersNoRange.getCurrentNo()) + "-" + "ChPV";
                    fmsVoucher.setVoucherId(fmsVoucherID);
                    if (fmsVoucherBeanLocal.getfmsVoucherDup(fmsVoucher)) {
                        System.err.println("Value is Duplicated");
                        JsfUtil.addSuccessMessage("Value is Duplicated");
                    } else {
                        try {
                            chequePaymentVoucher.setPreparedBy(String.valueOf(sessionBeanLocal.getUserId()));
                            chequePaymentVoucher.setStatus(String.valueOf(Constants.PREPARE_VALUE));
                            chequePaymentVoucher.setSourceJeId("0");
                            fmsVoucher.setProcessedBy(String.valueOf(wfFcmsProcessed.getProcessedBy()));
                            fmsVoucher.setVoucherId(fmsVoucherID);
                            fmsVoucher.setStatus(Constants.PREPARE_VALUE);
                            fmsVoucher.setType("CHPV");
                            fmsVoucher.setVoucherNo(fmsVouchersNoRange.getCurrentNo());
                            fmsVoucher.addToFmsCheckPaymentDetail(chequePaymentVoucher);
                            fmsVoucher.setProcessedBy(String.valueOf(sessionBeanLocal.getUserId()));
                            fmsVoucher.setProcessedDate(fmsVoucher.getPreparedDate());
                            fmsVoucher.setPreparedBy(String.valueOf(sessionBeanLocal.getUserId()));
                            fmsDocumentFollowup.setDocumentReference("23526");
                            fmsDocumentFollowup.setType("ChPV");
                            int currentNo1 = Integer.parseInt(fmsVouchersNoRange.getCurrentNo()) + 1;
                            fmsVouchersNoRange.setCurrentNo(String.valueOf(currentNo1));
                            noRangeBeanLocal.edit(fmsVouchersNoRange);
                            wfFcmsProcessed.setFmsVoucherId(fmsVoucher);
                            System.out.println("wfFcmsProcessed.setFmsVoucherId(fmsVoucher);==" + wfFcmsProcessed.getFmsVoucherId());
                            wfFcmsProcessed.setDecision(fmsVoucher.getStatus());
                            System.out.println("1 " + wfFcmsProcessed.getDecision());
                            wfFcmsProcessed.setCommentGiven(fmsVoucher.getPreparedRemark());
                            System.out.println("2 " + wfFcmsProcessed.getCommentGiven());
                            wfFcmsProcessed.setProcessedOn(String.valueOf(fmsVoucher.getPreparedDate()));
                            System.out.println("3 " + wfFcmsProcessed.getProcessedOn());
                            wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(workFlow.getUserAccount()));
                            System.out.println("4 " + wfFcmsProcessed.getProcessedBy());
//                            System.out.println("vvvvvv" + fmsVatRecieptVoucher.getVatRecieptValue());
                            fmsVoucherBeanLocal.create(fmsVoucher);
                            JsfUtil.addSuccessMessage("Cheque Payment Voucher Data Created");
                            wfFcmsProcessedBeanLocal.saveUpdate(wfFcmsProcessed);
                            return clearPage();
                        } catch (Exception e) {
                            JsfUtil.addFatalMessage("Something Occured on Data Created");
                            return null;
                        }
                    }
                } else if (updteStatus == 1) {
                    fmsVoucherBeanLocal.edit(fmsVoucher);
                    JsfUtil.addSuccessMessage("Cheque Payment Voucher Data is Updated");
                    selectOptionStatus = false;
                    return clearPage();
                } else {
                    JsfUtil.addFatalMessage("Pls check for balance");
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);
            }
            return null;
        }
        return null;
    }

    public String clearPage() {
        fmsVoucher = null;
        chequePaymentVoucher = null;
        fmsLuSystem = null;
        luPaymentType = null;
        accountUseEnty = null;
        accountUseDetailDataModel = null;
        updteStatus = 0;
        return null;
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="create New cheque payment voucher">
    public void createNewJv() {
        System.out.println("=== createNewJv ===");
        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equalsIgnoreCase("New")) {
            renderPnlCreateJv = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
            saveVochNo = true;
            new1 = false;
            createNEWrend = false;
        } else if (createOrSearchBundle.equalsIgnoreCase("Search")) {
            renderPnlCreateJv = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
            saveVochNo = false;
            createNEWrend = false;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="populate">
    // this methode is used for populating the searched cheque payment voucher
    public void populate(SelectEvent event) {
        fmsVoucher = (FmsVoucher) event.getObject();
        fmsVoucher = fmsVoucherBeanLocal.getVoucherIDInfo(fmsVoucher); // searche and store the voucher that is selected frome the interface by voucher ID key
        clearPopup();
        popvocjNo = true;
        entries_ = new ArrayList<>(accountcredit.entrySet());
        System.out.println("entries_===" + entries_.size());
        for (int i = 0; i < entries_.size(); i++) {
            accountUseResult = new FmsAccountUseResult();
            accountUseResult.setAC_NO_CREDIT(entries_.get(i).getKey());
            accountUseResult.setCREDIT(entries_.get(i).getValue().doubleValue());
            accountUseResults.add(accountUseResult);
            Credit = accountUseResult.getCREDIT();
        }
        entries = new ArrayList<>(accountDebit.entrySet());
        for (int i = 0; i < entries.size(); i++) {
            if ((entries.size() <= entries_.size()) || i < entries_.size()) {
                accountUseResult = new FmsAccountUseResult();
                accountUseResult = accountUseResults.get(i);
                accountUseResult.setAC_NO_DEBIT(entries.get(i).getKey());
                accountUseResult.setDEBIT(entries.get(i).getValue().doubleValue());
                accountUseResults.set(i, accountUseResult);
                Debit = accountUseResult.getDEBIT();
            } else {
                accountUseResult = new FmsAccountUseResult();
                accountUseResult.setAC_NO_DEBIT(entries.get(i).getKey());
                accountUseResult.setDEBIT(entries.get(i).getValue().doubleValue());
                accountUseResults.add(accountUseResult);
                Debit = accountUseResult.getDEBIT();
            }
        }
        recreateModelDetail();
        workflowDataModel();
        recreateJVModelDetail();
        addButtonStatus = true;
        selectOptionStatus = true;
        editRemoveStatus = true;
        updteStatus = 1;
        renderPnlCreateJv = true;
        renderPnlManPage = false;
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        setIcone1("ui-icon-plus");
        disableBtnCreate = true;
        updteStatus = 1;
        onupdate = 1;
        printe = true;
        new1 = true;
        createNEWrend = true;

    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Concatenate">
    public void Concatenate() {
        String CC = "CC";
        String SL = "SL";
        String CC_display = "CC";
        String SL_display = "SL";
        if (fmsCostCenter.getCostCenterId() != null) {
            CC = fmsCostCenter.getCostCenterId().toString();
            CC_display = fmsCostCenter.getSystemName();
        }
        if (fmsSubsidiaryLedger.getSubsidiaryCode() != null) {
            SL = fmsSubsidiaryLedger.getSubsidiaryId().toString();
            SL_display = fmsSubsidiaryLedger.getSubsidiaryCode();
        }
        display_conn = fmsLuSystem.getSystemCode() + "-" + CC_display + "-" + fmsSubsidiaryLedger.getGeneralLedgerId().getGeneralLedgerCode() + "-" + SL_display;
        Conc = fmsLuSystem.getSystemId() + "-" + CC + "-" + fmsSubsidiaryLedger.getGeneralLedgerId().getGeneralLedgerId() + "-" + SL;

    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="reset">
    public void reset() {
        fmsOperatingBudget1 = new FmsOperatingBudget1();
        fmsCostCenter = new FmsCostCenter();
        fmsLuSystem = new FmsLuSystem();
        fmsLuBudgetYear = new FmsLuBudgetYear();
        fmsGeneralLedger = new FmsGeneralLedger();
        wfFcmsProcessed = new WfFcmsProcessed();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="workflowDataModel">
    public void workflowDataModel() {
        workflowDataModel = null;
        workflowDataModel = new ListDataModel(new ArrayList(fmsVoucher.getWfFcmsProcessedList()));
        for (int i = 0; i < fmsVoucher.getWfFcmsProcessedList().size(); i++) {
            if (fmsVoucher.getWfFcmsProcessedList().get(i).getDecision().equals(0)) {
                fmsVoucher.getWfFcmsProcessedList().get(i).setChangedDecision("Prepared");
            } else if (fmsVoucher.getWfFcmsProcessedList().get(i).getDecision().equals(1) || fmsVoucher.getWfFcmsProcessedList().get(i).getDecision().equals(39)) {
                fmsVoucher.getWfFcmsProcessedList().get(i).setChangedDecision("Voucher Posted");
            } else if (fmsVoucher.getWfFcmsProcessedList().get(i).getDecision().equals(2) || fmsVoucher.getWfFcmsProcessedList().get(i).getDecision().equals(38)) {
                fmsVoucher.getWfFcmsProcessedList().get(i).setChangedDecision("Voucher voided");
            }
        }
        workflowDataModel = new ListDataModel(new ArrayList(fmsVoucher.getWfFcmsProcessedList()));
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Search">
    public void recreateJvDataModel() {
        fmsVoucherDataModel = null;
        fmsVoucherDataModel = new ListDataModel(new ArrayList(fmsVouchersList1));
    }

    public void search_vouchers() {
        try {

            fmsVoucher.setPreparedBy(workFlow.getUserName());
            fmsVoucher.setType("CHPV");
            fmsVouchersList1 = fmsVoucherBeanLocal.searchAllVochNo(fmsVoucher);
            System.out.println("fmsVouchersList1==" + fmsVouchersList1);
            recreateJvDataModel();
            fmsVoucher = new FmsVoucher();
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Sorry!. No fmsVoucher begins with " + fmsVoucher.getColumnValue() + ". Try again.");
            throw e;
        }
    }

    public void searchchequePayment() {
        System.out.println("fmsVoucher.getVoucherId()   " + fmsVoucher.getVoucherId());
        try {
            //find all
            if (fmsVoucherId.isEmpty()) {
                fmsVoucher.setType("CHPV");
                jvList = fmsVoucherBeanLocal.findAllbytype(fmsVoucher);
                recreateJvDataModel();
            } else if (!(fmsVoucherId.isEmpty())) {
                //find by begins with
                fmsVoucher.setVoucherId(fmsVoucherId);
                jvList = fmsVoucherBeanLocal.searchVoucheidTypeCHPV(fmsVoucher);
                recreateJvDataModel();
            } else {
                JsfUtil.addFatalMessage("Sorry!.there is no fmsVoucher begins with " + fmsVoucher.getVoucherId() + ". Try again.");
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Sorry!. No fmsVoucher begins with " + fmsVoucher.getVoucherId() + ". Try again.");
            throw e;
        }
    }

    public void getJVInformation(SelectEvent event) {

        String selectedVoucherId = event.getObject().toString();
        fmsVoucher.setVoucherId(selectedVoucherId);
        fmsVoucher = fmsVoucherBeanLocal.getVoucherIDInfo(fmsVoucher);
        String splitedVoucherNO[] = fmsVoucher.getVoucherId().split("-");
        clearPopup();
        setVoucherNO(splitedVoucherNO[1]);
        recreateModelDetail();
        recreateJVModelDetail();
        addButtonStatus = true;
        selectOptionStatus = true;
        editRemoveStatus = true;
        updteStatus = 1;
        saveorUpdateBundle = "Update";
        printe = true;

    }

    public void getJVInformation1(SelectEvent event) {

        String selectedVoucherId = event.getObject().toString();
        fmsVoucher.setVoucherId(selectedVoucherId);
        fmsVoucher = fmsVoucherBeanLocal.getVoucherIDInfo(fmsVoucher);
        String splitedVoucherNO[] = fmsVoucher.getVoucherId().split("-");
        setVoucherNO(splitedVoucherNO[1]);
        recreateModelDetail();
        recreateJVModelDetail();
        updteStatus = 1;
        saveorUpdateBundle = "Update";
        printe = true;

    }

    public void populateAccDetail(SelectEvent event) {
        accountUseEnty = (FmsAccountUse) event.getObject();
        dataTableUpdateStatus = 1;
        String subsidari;
        subsidari = accountUseEnty.getSubsidaryId();
        int x = 0;
        String[] col = subsidari.split("-");
        int sys = Integer.parseInt(col[0]);
        fmsLuSystem.setSystemId(sys);
        int gl = Integer.parseInt(col[2]);
        fmsGeneralLedger.setGeneralLedgerId(gl);
        if (!"CC".equals(col[1])) {
            int cc = Integer.parseInt(col[1]);
            fmsCostCenter.setCostCenterId(cc);
        }
        if (!"SL".equals(col[3])) {
            int sl = Integer.parseInt(col[3]);
            fmsSubsidiaryLedger.setSubsidiaryId(sl);
        }
        if (accountUseEnty.getCredit().doubleValue() > 0) {
            ValueDebitCredit = accountUseEnty.getCredit();
            ActionDebitCredit = "Credit";
        }
        if (accountUseEnty.getDebit().doubleValue() > 0) {
            ValueDebitCredit = accountUseEnty.getDebit();
            ActionDebitCredit = "Debit";
        }
    }

    public SelectItem[] getpaymentTypeList() {
        return JsfUtil.getSelectItems(paymentTypeFacade.findAll(), true);

    }

    public SelectItem[] getBondpaymentRfList() {
        return JsfUtil.getSelectItems(bondLocalScheduleBeanLocal.searchpayedSchedule(), true);

    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="AccountUse,GeneralLedger,Subsidiary - Implimentation">
    public void optionHandiler() {
        if (!addButtonStatus) {
            addButtonStatus = true;
            add_colaps = "Colaps";
        } else {
            addButtonStatus = false;
            add_colaps = "add";
        }

    }

    public List<FmsCostCenter> getFmsCostCenterList() {
        fmsLuSystem = fmsCostCenter.getSystemId();
        List<FmsCostCenter> costcenterList = new ArrayList<>();
        if (fmsLuSystem != null) {
            if (costcenterList.size() > 0) {
                cc = false;
            } else {
                cc = true;
            }
        } else {
            JsfUtil.addErrorMessage("Duplicate Credit is not allowed with duplicate Subsidiary Code!");
        }
        return null;
    }

    public void setFmsCostCenterList(List<FmsCostCenter> FmsCostCenterList) {
        this.FmsCostCenterList = FmsCostCenterList;
    }

    public void SystemChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuSystem.setSystemCode(event.getNewValue().toString());
            fmsLuSystem = fmsLuSystemBeanLocal.getSysDetail(fmsLuSystem);
            fmsCostCenter.setSystemId(fmsLuSystem);
            fmsVouchersNoRange = fmsVoucherBeanLocal.getVoucherNo(fmsLuSystem);
        }
    }

    public void getGeneralLedgerChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsGeneralLedger.setGeneralLedgerCode(event.getNewValue().toString());
            fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getGLDetail(fmsGeneralLedger);
            fmsSubsidiaryLedger.setGeneralLedgerId(fmsGeneralLedger);

        }
    }

    public void getSubsidiaryLChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsSubsidiaryLedger.setSubsidiaryId(Integer.parseInt(event.getNewValue().toString()));
            System.out.println("subsidary ladger selectd value" + fmsSubsidiaryLedger);
        }

    }

    public String addSubsidiaryLedgerDetail() {
        accountUseEnty = new FmsAccountUse();
        accountUseEnty.setSubsidaryId(fmsSubsidiaryLedger.getSubsidiaryCode());
        System.out.println("accountUseEnty.getSubsidaryId()-----" + accountUseEnty.getSubsidaryId());
        if (ActionDebitCredit.equals("Debit")) {
            accountUseEnty.setDebit(ValueDebitCredit);
            accountUseEnty.setCredit(new BigDecimal(0.00));
            accountUseEnty.setAmt(0 - accountUseEnty.getDebit().doubleValue());

        }
        if (ActionDebitCredit.equals("Credit")) {
            accountUseEnty.setDebit(new BigDecimal(0.00));
            accountUseEnty.setCredit(ValueDebitCredit);
            accountUseEnty.setAmt(accountUseEnty.getCredit().doubleValue());

        }
        //check duplicate Debit Credit with same SL code

        if (checkSubsidiaryCode.contains(accountUseEnty.getSubsidaryId())) {
            if (checkDebitCredit.contains(accountUseEnty.getDebit()) && ActionDebitCredit.equals("Debit")) {
                JsfUtil.addErrorMessage("Duplicate Debit is not allowed with duplicate Subsidiary Code!");
                return null;
            } else if (checkDebitCredit.contains(accountUseEnty.getCredit()) && ActionDebitCredit.equals("Credit")) {
                JsfUtil.addErrorMessage("Duplicate Credit is not allowed with duplicate Subsidiary Code!");
                return null;
            }
        } else {
            Debit = Debit + accountUseEnty.getDebit().doubleValue();
            Credit = Credit + accountUseEnty.getCredit().doubleValue();
            fmsVoucher.addToFmsSubsidiaryLSrDetail(accountUseEnty);
            checkSubsidiaryCode.add(accountUseEnty.getSubsidaryId());
            totalamt = Credit - Debit;
            recreateModelDetail();
            return clearAccWithoutSystem();
        }
        return null;
    }

    public String clearPopup() {
        accountUseEnty = null;
        accountUseEnty = new FmsAccountUse();
        ValueDebitCredit = new BigDecimal(0.00);
        ActionDebitCredit = "";
        clearAccCodes();
        return null;
    }

    public void updateAccountUseDetail() {
        dataTableUpdateStatus = 1;
        accountUseEnty = getAccountUseDetailDataModel().getRowData();
        accountUseEnty.setSubsidaryId(getAccountUseDetailDataModel().getRowData().getSubsidaryId());
        if (accountUseEnty.getCredit().doubleValue() > 0) {
            ValueDebitCredit = accountUseEnty.getCredit();
            ActionDebitCredit = "Credit";
        }
        if (accountUseEnty.getDebit().doubleValue() > 0) {
            ValueDebitCredit = accountUseEnty.getDebit();
            ActionDebitCredit = "Debit";
        }
    }

    public void recreateJVModelDetail() {
        System.out.println("fmsVoucher.getFmsChequePaymentVoucher()==" + fmsVoucher.getFmsChequePaymentVoucher());
        chequePaymentVoucher = fmsVoucher.getFmsChequePaymentVoucher();
    }

    public void recreateModelDetail() {
        accountUseDetailDataModel = null;
        accountUseDetailDataModel = new ListDataModel(fmsVoucher.getFmsAccountUseList());
    }

    public void remove() {
        accountUseEnty = null;
        accountUseEnty = getAccountUseDetailDataModel().getRowData();
        fmsVoucher.getFmsAccountUseList().remove(accountUseEnty);
        recreateModelDetail();
    }
//</editor-fold>  
    // <editor-fold defaultstate="collapsed" desc="Ref - No">

    public List<FmsBondLocalSchedule> fetchBondLocalSchedules() {
        referenceNumbers = new ArrayList<>();
        bondLocalSchedules = bondLocalScheduleBeanLocal.searchpayedSchedule();
        for (int i = 0; i < bondLocalSchedules.size(); i++) {
            String ref = null;
            ref = bondLocalSchedules.get(i).getLocalBondId().getSerialNo() + "/" + bondLocalSchedules.get(i).getNoOfInstallmen();
            getReferenceNumbers().add(ref);
        }
        return bondLocalSchedules;
    }

    public List<FmsBondForeignSchedule> fetchBondForeignSchedules() {
        referenceNumbers = new ArrayList<>();
        bondForeignSchedules = bondForeignScheduleBeanLocal.searchForingns_Bondpayed();
        for (int i = 0; i < bondForeignSchedules.size(); i++) {
            String ref = null;
            ref = bondForeignSchedules.get(i).getBondForeignId().getSerialNo() + "/" + bondForeignSchedules.get(i).getNoOfInstallmen();
            getReferenceNumbers().add(ref);
        }
        return bondForeignSchedules;
    }

    public void ref(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            reftype = event.getNewValue().toString();
            luPaymentType.setPaymentType(event.getNewValue().toString());
            if (luPaymentType.getPaymentType().matches("Local Bond")) {
                fetchBondLocalSchedules();
                refbondforeign = true;
            } else if (reftype.matches("Foreign Bond")) {
                fetchBondForeignSchedules();
                refbondforeign = true;
            } else if (reftype.matches("Medical")) {
                referenceNumbers = new ArrayList<>();
                hrmedSettlmentDetailList = localMedSettlementBeanLocal.fetchApprovedPayments();
                if (!hrmedSettlmentDetailList.isEmpty()) {
                    for (HrLocalMedSettlementDetail hrmedSettlmentDetailList1 : hrmedSettlmentDetailList) {
                        HrLocalMedSettlements localMedObject;
                        localMedObject = cashRefundApproveBeanLocal.getSelectedRequest(hrmedSettlmentDetailList1.getId());
                        getReferenceNumbers().add(localMedObject.getReferenceNo());
                    }
                    refNoList();
                    refbondforeign = true;
                } else {
                    JsfUtil.addFatalMessage("No data found");
                }
                others = true;
            } else if (reftype.matches("Court Order")) {
                referenceNumbers = new ArrayList<>();
                hrPayrollMonTransactionsList = hrPayrollMonthlyTransactionLocal.fetchCourtOrderPayments();
                if (!hrPayrollMonTransactionsList.isEmpty()) {
                    for (HrPayrollMonTransactions hrPayrollMonTransactionsList1 : hrPayrollMonTransactionsList) {
                        HrPayrollMonTransactions monthlyPayrollObject;
                        HrPayrollPeriods hrPayrollPeriodsObj;
                        monthlyPayrollObject = hrPayrollMonthlyTransactionLocal.getSelectedPayment(hrPayrollMonTransactionsList1.getId());
                        hrPayrollPeriodsObj = hrPayrollPeriodsFacadeLocal.findPayrollPeriodById(monthlyPayrollObject.getPayrollPeriodId().getId().intValue());
                        getReferenceNumbers().add(monthlyPayrollObject.getId() + " - " + monthlyPayrollObject.getEmpId().toString() + " - " + hrPayrollPeriodsObj.getPaymentMadeForTheMonthOf());
                    }
                    refNoList();
                    refbondforeign = true;
                } else {
                    JsfUtil.addFatalMessage("No data found");
                }
                others = true;

            } else if (reftype.matches("Projects")) {
                referenceNumbers = new ArrayList<>();
                pmsProjectPaymentReqsList = pmsProjectPaymentReqBeanLocal.fetchNewProjectPayments();
                if (!pmsProjectPaymentReqsList.isEmpty()) {
                    pmsProjectPaymentReqsList.stream().forEach((pmsProjectPaymentReqsList1) -> {
                        getReferenceNumbers().add(pmsProjectPaymentReqsList1.getPaymentNo());
                    });
                    refNoList();
                    refbondforeign = true;
                } else {
                    JsfUtil.addFatalMessage("No data found");
                }
                others = true;

            } else if (reftype.matches("Field Allowance")) {
                referenceNumbers = new ArrayList<>();
                fieldAllowances = fieldAllowansBeanLocal.searchnotexitvocher();
                if (!fieldAllowances.isEmpty()) {
                    System.out.println("if not empty==== ");
                    fieldAllowances.stream().forEach((fieldAllowanceobj) -> {
                        getReferenceNumbers().add(fieldAllowanceobj.getRequestNo());
                    });
                    refNoList();
                    refbondforeign = true;
                } else {
                    System.out.println("if its empty==== ");
                    refbondforeign = true;
                    JsfUtil.addFatalMessage("No data found");
                }
                others = true;
            } else if (reftype.matches("Payment Requisition")) {
                referenceNumbers = new ArrayList<>();
                prmsPaymentRequisitionsList = prmsRequestforPaymentBeanLocal.getPrmsPaymentRequisitions();
                if (!prmsPaymentRequisitionsList.isEmpty()) {
                    for (PrmsPaymentRequisition prmsPaymentRequisitions_ : prmsPaymentRequisitionsList) {
                        getReferenceNumbers().add(prmsPaymentRequisitions_.getReqPaymentNo());
                    }
                    refNoList();
                    refbondforeign = true;
                    others = true;
                } else {
                    JsfUtil.addFatalMessage("No data found");
                }
            } else if (reftype.matches("Insurance")) {
                referenceNumbers = new ArrayList<>();
                hrInsurancePaymentList = hrinsurancePaymentLocal.fetchNewInsurancePayments();
                if (!hrInsurancePaymentList.isEmpty()) {
                    for (HrInsurancePaymentDetail hrInsurancePaymentList1 : hrInsurancePaymentList) {

                        getReferenceNumbers().add(hrInsurancePaymentList1.getReferenceNo());
                    }
                    refNoList();
                    refbondforeign = true;
                    others = true;
                } else {
                    JsfUtil.addFatalMessage("No data found");
                }
            } else if (reftype.matches("Other")) {
                refbondforeign = false;
                others = false;

            }
        }
    }

    public SelectItem[] refNoList() {
        SelectItem[] listSl = null;
        if (referenceNumbers.size() > 0) {
            listSl = new SelectItem[referenceNumbers.size() + 1];
            listSl[0] = new SelectItem(null, "--- Select One ---");
            for (int i = 0; i < referenceNumbers.size(); i++) {
                listSl[i + 1] = new SelectItem(referenceNumbers.get(i));
            }
        }
        return listSl;
    }

    public void referenceNumEvent(ValueChangeEvent event) {
        if (!(event.getNewValue().toString() == null)) {
            String ref = event.getNewValue().toString();
            if (luPaymentType.getPaymentType().equalsIgnoreCase("Medical")) {
                HrLocalMedSettlements localMedObject;
                HrLocalMedSettlementDetail localMedDetailObject;
                localMedObject = cashRefundApproveBeanLocal.fetchSettlement(event.getNewValue().toString());
                localMedDetailObject = localMedSettlementBeanLocal.fetchPayment(localMedObject);
                chequePaymentVoucher.setAmountInFigure(new BigDecimal(localMedDetailObject.getAmount()));
                chequePaymentVoucher.setPaidTo(localMedObject.getRequesterId().getFirstName());
                chequePaymentVoucher.setReferenceNumber(localMedObject.getReferenceNo());
                others = true;
                taxrend = true;
            } else if (luPaymentType.getPaymentType().equalsIgnoreCase("Local Bond")) {
                String splitref[] = event.getNewValue().toString().split("/");
                FmsBondLocalSchedule bondLocalSchedule = new FmsBondLocalSchedule();
                bondLocalSchedule.setSerialNo(splitref[0]);
                int inst = Integer.valueOf(splitref[1]);
                bondLocalSchedule.setNoOfInstallmen(inst);
                bondLocalSchedule = bondLocalScheduleBeanLocal.getSchedule_instlment(bondLocalSchedule);
                chequePaymentVoucher.setAmountInFigure(new BigDecimal(bondLocalSchedule.getPrincipal() + bondLocalSchedule.getPrincipal()));
                chequePaymentVoucher.setPaidTo(bondLocalSchedule.getLocalBondId().getBuyerFullName());
                others = true;
            } else if (luPaymentType.getPaymentType().equalsIgnoreCase("Foreign Bond")) {
                String splitref[] = event.getNewValue().toString().split("/");
                FmsBondForeignSchedule bondForeignSchedule = new FmsBondForeignSchedule();
                FmsBondForeign bondForeign = new FmsBondForeign();
                bondForeign.setSerialNo(splitref[0]);
                bondForeignSchedule.setBondForeignId(bondForeign);
                int instlement = Integer.valueOf(splitref[1]);
                bondForeignSchedule.setNoOfInstallmen(instlement);
                bondForeignSchedule = bondForeignScheduleBeanLocal.getForeignSchedule_intslment(bondForeignSchedule);
                chequePaymentVoucher.setAmountInFigure(new BigDecimal(bondForeignSchedule.getInterest()));
                chequePaymentVoucher.setPaidTo(bondForeignSchedule.getBondForeignId().getBuyerFullName());
                others = true;
            } else if (luPaymentType.getPaymentType().equalsIgnoreCase("Court Order")) {
                HrPayrollMonTransactions monthlyPayrollObject;
                HrPayrollPeriods hrPayrollPeriodsObj;
                String IdPayroll = event.getNewValue().toString();
                String[] splitIdPyrl = IdPayroll.split("-");
                String id = splitIdPyrl[0];
                Long pkId = Long.parseLong(id.trim());
                String Emp = splitIdPyrl[1];
                monthlyPayrollObject = hrPayrollMonthlyTransactionLocal.getSelectedPayment(pkId);
                hrPayrollPeriodsObj = hrPayrollPeriodsFacadeLocal.findPayrollPeriodById(monthlyPayrollObject.getPayrollPeriodId().getId().intValue());
                chequePaymentVoucher.setAmountInFigure((monthlyPayrollObject.getAmount()));
                chequePaymentVoucher.setPaidTo(Emp);
                chequePaymentVoucher.setReferenceNumber(String.valueOf(monthlyPayrollObject.getId()));
                others = true;
            } else if (luPaymentType.getPaymentType().equalsIgnoreCase("Projects")) {
                PmsProjectPaymentRequest localPrjPaymentObject;
                localPrjPaymentObject = pmsProjectPaymentReqBeanLocal.getSelectePayment(event.getNewValue().toString());
                chequePaymentVoucher.setAmountInFigure(new BigDecimal(localPrjPaymentObject.getAmount()));
                chequePaymentVoucher.setPaidTo(localPrjPaymentObject.getJobNo());
                chequePaymentVoucher.setReferenceNumber(localPrjPaymentObject.getPaymentNo());
                others = true;
            } else if (luPaymentType.getPaymentType().equalsIgnoreCase("Insurance")) {
                HrInsurancePaymentDetail localInsuranceObject;
                localInsuranceObject = hrinsurancePaymentLocal.getSelectedPayment(event.getNewValue().toString());
                chequePaymentVoucher.setAmountInFigure(localInsuranceObject.getMedicalExpence());
                chequePaymentVoucher.setPaidTo(localInsuranceObject.getPaymentRequestId().getNameOfBank());
                chequePaymentVoucher.setReferenceNumber(localInsuranceObject.getReferenceNo());
                others = true;
            } else if (luPaymentType.getPaymentType().equalsIgnoreCase("Payment Requisition")) {
                PrmsPaymentRequisition paymentRequisition;
                paymentRequisition = prmsRequestforPaymentBeanLocal.getPrmsPaymentReq(event.getNewValue().toString());
                chequePaymentVoucher.setAmountInFigure(new BigDecimal(paymentRequisition.getTotalAmount()));
                chequePaymentVoucher.setPaidTo(paymentRequisition.getPaymentRecievedBy());
                chequePaymentVoucher.setReferenceNumber(paymentRequisition.getReqPaymentNo());
                others = true;
            } else if (luPaymentType.getPaymentType().equalsIgnoreCase("Field Allowance")) {
                FmsFieldAllowance fieldAllowanceObj = new FmsFieldAllowance();
                fieldAllowanceObj.setRequestNo(event.getNewValue().toString());
                fieldAllowance = fieldAllowansBeanLocal.getByrequestno(fieldAllowanceObj);
                chequePaymentVoucher.setAmountInFigure(new BigDecimal(fieldAllowance.getTotalExpense()));
                chequePaymentVoucher.setPaidTo(fieldAllowance.getEmpId().getFirstName());
                System.out.println("fieldAllowance.getEmpId().getFirstName()====" + fieldAllowance.getEmpId().getFirstName());
                chequePaymentVoucher.setReferenceNumber(fieldAllowance.getRequestNo());
                others = true;
                taxrend = true;
            } else if (luPaymentType.getPaymentType().equalsIgnoreCase("Other")) {
                others = false;

            }
        }
    }

    public void refno() {
        if (reftype != null) {
            if (reftype.matches("Local Bond")) {
                refdisable = false;
                getBondpaymentRfList();
                getBondLocalSchedules();
            } else if (reftype.matches("Foreign Bond")) {
            } else {
            }
        } else {
        }
    }

    public void handleChangeTaxType(ValueChangeEvent event) {
        luVatTypeLookup = (PrmsLuVatTypeLookup) event.getNewValue();
        BigDecimal Net = new BigDecimal(0.0);
        DecimalFormat df = new DecimalFormat("#.##");
        BigDecimal percent100 = new BigDecimal(100);
        BigDecimal withholds = new BigDecimal(0.0);
        BigDecimal vat = new BigDecimal(0.0);
        BigDecimal rate = new BigDecimal(0.0);
        double whitholdRates = 0.00;
        double vatRates = 0.00;
        double pefvate = 1.15;
        if (types.contains("1")) {
            if (luVatTypeLookup.getVatType().toString().contains("withholding")) {
                whitholdRates = luVatTypeLookup.getVatRate();
                Net = chequePaymentVoucher.getAmountInFigure();
                if (Net.doubleValue() > 10000) {
                    percent100 = rate.divide(percent100, 2, RoundingMode.HALF_UP);
                    withholds = chequePaymentVoucher.getAmountInFigure().multiply(BigDecimal.valueOf(whitholdRates));//0.02
                    Net = chequePaymentVoucher.getAmountInFigure().subtract(withholds);
                    WITHHOLD = (df.format(withholds));
                    chequePaymentVoucher.setWithhold(withholds);
                    chequePaymentVoucher.setAmountInFigure(Net);
                    setVatTax(String.valueOf(0.0));
                    fmsVoucher.setVatWithholdStatus("UW");
                } else {
                    Net = chequePaymentVoucher.getAmountInFigure();
                    setVatTax(String.valueOf(0.0));
                    setWITHHOLD(String.valueOf(0.0));
                    JsfUtil.addInformationMessage("There is no WITHOLDING for less than 10000 Birr");
                }
            } else if (luVatTypeLookup.getVatType().toString().contains("vat")) {
                vatRates = luVatTypeLookup.getVatRate();
                Net = chequePaymentVoucher.getAmountInFigure();
                if (Net.doubleValue() > 5000) {
                    percent100 = rate.divide(percent100, 2, RoundingMode.HALF_UP);
                    System.err.println("-15%---" + percent100);
                    vat = chequePaymentVoucher.getAmountInFigure().divide((BigDecimal.valueOf(pefvate)), 2, RoundingMode.HALF_UP);
                    vat = vat.multiply(BigDecimal.valueOf(vatRates));//0.15
                    Net = chequePaymentVoucher.getAmountInFigure().subtract(vat);
                    VatTax = (df.format(vat));
                    chequePaymentVoucher.setVats(vat);
                    setWITHHOLD(String.valueOf(0.0));
                    chequePaymentVoucher.setAmountInFigure(Net);
                    fmsVoucher.setVatWithholdStatus("UV");
                } else {
                    setVatTax(String.valueOf(0.0));
                    setWITHHOLD(String.valueOf(0.0));
                    Net = chequePaymentVoucher.getAmountInFigure();
                    JsfUtil.addInformationMessage("no vate for < 5000 birr");
                }
            } else if (luVatTypeLookup.getVatType().toString().contains("vat and withholding")) {
                pefvate = luVatTypeLookup.getVatRate();
                Net = chequePaymentVoucher.getAmountInFigure();
                if (Net.doubleValue() > 10000) {
                    System.err.println("-serviceNetsd--" + Net);
                    vat = (chequePaymentVoucher.getAmountInFigure().divide((BigDecimal.valueOf(pefvate)), 2, RoundingMode.HALF_UP)).multiply(BigDecimal.valueOf(vatRates));//0.15
                    withholds = chequePaymentVoucher.getAmountInFigure().multiply(BigDecimal.valueOf(whitholdRates));//0.02   
                    System.err.println("-serviceNetsd-- vat" + vat);
                    Net = Net.subtract(vat.add(withholds));
                    WITHHOLD = (df.format(withholds));
                    VatTax = (df.format(vat));
                    chequePaymentVoucher.setAmountInFigure(Net);
                } else {
                    Net = chequePaymentVoucher.getAmountInFigure();
                }
            }
        } else if (types.contains("2")) {
            System.err.println("-service--" + types);
            if (luVatTypeLookup.getVatType().toString().contains("withholding")) {
                whitholdRates = luVatTypeLookup.getVatRate();
                Net = chequePaymentVoucher.getAmountInFigure();
                System.err.println("-serviceNetsd--" + Net);
                System.err.println("-service--" + event.getNewValue().toString());
                if (Net.doubleValue() > 500) {
                    System.err.println("-service>--" + Net);
                    withholds = chequePaymentVoucher.getAmountInFigure().multiply(BigDecimal.valueOf(whitholdRates));//0.02               
                    Net = chequePaymentVoucher.getAmountInFigure().subtract(withholds);
                    WITHHOLD = (df.format(withholds));
                    chequePaymentVoucher.setWithhold(withholds);
                    setVatTax(String.valueOf(0.0));
                    chequePaymentVoucher.setAmountInFigure(Net);
                    fmsVoucher.setVatWithholdStatus("UW");
                } else {
                    setVatTax(String.valueOf(0.0));
                    setWITHHOLD(String.valueOf(0.0));
                    Net = chequePaymentVoucher.getAmountInFigure();
                }
            } else if (luVatTypeLookup.getVatType().toString().contains("vat")) {
                vatRates = luVatTypeLookup.getVatRate();
                Net = chequePaymentVoucher.getAmountInFigure();
                if (Net.doubleValue() > 500) {
                    vat = (chequePaymentVoucher.getAmountInFigure().divide((BigDecimal.valueOf(pefvate)), 2, RoundingMode.HALF_UP)).multiply(BigDecimal.valueOf(vatRates));//0.15
                    Net = chequePaymentVoucher.getAmountInFigure().subtract(vat);
                    VatTax = (df.format(vat));
                    chequePaymentVoucher.setVats(vat);
                    setWITHHOLD(String.valueOf(0.0));
                    chequePaymentVoucher.setAmountInFigure(Net);
                    fmsVoucher.setVatWithholdStatus("UV");
                } else {
                    setVatTax(String.valueOf(0.0));
                    setWITHHOLD(String.valueOf(0.0));
                    Net = chequePaymentVoucher.getAmountInFigure();
                }
            } else if (luVatTypeLookup.getVatType().toString().contains("vat and withholding")) {
                pefvate = luVatTypeLookup.getVatRate();
                Net = chequePaymentVoucher.getAmountInFigure();
                System.err.println("-service333--" + Net);
                if (Net.doubleValue() > 500) {
                    System.err.println("-service22222--" + Net);
                    vat = (chequePaymentVoucher.getAmountInFigure().divide((BigDecimal.valueOf(pefvate)), 2, RoundingMode.HALF_UP)).multiply(BigDecimal.valueOf(vatRates));//0.15
                    withholds = chequePaymentVoucher.getAmountInFigure().multiply(BigDecimal.valueOf(whitholdRates));//0.02                    
                    Net = Net.subtract(vat.add(withholds));
                    VatTax = (df.format(vat));
                    chequePaymentVoucher.setWithhold(withholds);
                    chequePaymentVoucher.setAmountInFigure(Net);
                } else {
                    Net = chequePaymentVoucher.getAmountInFigure();
                }
            }
        }

    }

    //</editor-fold> 
    // this methode is for changing the status of decision to a string 
    //<editor-fold defaultstate="collapsed" desc="Chart of Account Handler: Account Code Handler. Author: Mubarek Shejebel">
    public void CostCenterChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsCostCenter.setSystemName(event.getNewValue().toString());
            fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findByCCandSS(fmsLuSystem, fmsCostCenter);
            int obReqCount = fmsOperatingBudgetBeanLocal.CCSSJuncDuplicationChecker(fmsLuBudgetYear, fmsCostcSystemJunction);
            if (obReqCount > 0) {
                JsfUtil.addFatalMessage("A request has already been made with cost center " + fmsCostCenter.getSystemName() + " on " + fmsLuBudgetYear.getBudgetYear() + " budget year.");
                reset();
            } else {
                int count = fmsOperatingBudgetBeanLocal.RowCount() + 1;
                String conc = "Opr-" + fmsLuSystem.getSystemCode() + "-" + fmsCostCenter.getSystemName() + "-" + count;
                fmsOperatingBudget1.setRequestCode(conc);
                fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findByCCandSS(fmsLuSystem, fmsCostCenter);
                fmsOperatingBudget1.setCcSsJunction(fmsCostcSystemJunction);
            }
        }
    }

    public void onChangeSystem(ValueChangeEvent event) {
        try {

            if (event.getNewValue() != null) {
                fmsLuSystem = (FmsLuSystem) event.getNewValue();
                renderJobNo = false;
                System.out.println("fmsLuSystem.getType().toString()===" + fmsLuSystem.getType().toString());
//                String tempp = fmsLuSystem.getType().toString();
                if (Integer.valueOf(fmsLuSystem.getType().toString()) == projectType) {

                    //tempp.equals(projectType)) {
                    System.out.println("first case====");
                    searchProjectType();
                    renderJobNo = true;
                    sysJobNOList = fmsLuSystem.getFmsSystemJobJunctionList();
                } else {
                    System.out.println("second case====");
                    searchNonProjectType();
                }
                ssCcJunctionList = fmsLuSystem.getFmsCostcSystemJunctionList();
                fmsVouchersNoRange = fmsVoucherBeanLocal.getVoucherNo(fmsLuSystem);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    public void searchProjectType() {
        glList = fmsGeneralLedgerBeanLocal.getByAccountType(projectType);
    }

    public void searchNonProjectType() {
        glList = fmsGeneralLedgerBeanLocal.getByAccountType(nonProjectType);
    }

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

    public void onChangeGeneralLedger(ValueChangeEvent event) {
        try {
            if (!event.getNewValue().toString().isEmpty()) {
                fmsGeneralLedger.setGeneralLedgerCode(event.getNewValue().toString());
                fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getGLDetail(fmsGeneralLedger);
                fmsGeneralLedger = (FmsGeneralLedger) event.getNewValue();
                slList = subsidiaryLedgerBeanLocal.findBySsCcJuncAndGL(fmsCostcSystemJunction, fmsGeneralLedger);
                System.out.println("slList=====" + slList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    public void onChangeSubsidiaryLedger(ValueChangeEvent event) {
        try {
            if (!event.getNewValue().toString().isEmpty()) {
                fmsSubsidiaryLedger = (FmsSubsidiaryLedger) event.getNewValue();
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

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

    public void clearAccCodes() {
        fmsLuSystem = new FmsLuSystem();
        fmsGeneralLedger = new FmsGeneralLedger();
        fmsCostCenter = new FmsCostCenter();
        fmsSystemJobJunction = new FmsSystemJobJunction();
        fmsCostcSystemJunction = new FmsCostcSystemJunction();
        fmsSubsidiaryLedger = new FmsSubsidiaryLedger();
    }

    public String clearAccWithoutSystem() {
        accountUseEnty = null;
        accountUseEnty = new FmsAccountUse();
        ValueDebitCredit = new BigDecimal(0.00);
        ActionDebitCredit = "";
        fmsGeneralLedger = new FmsGeneralLedger();
        fmsCostCenter = new FmsCostCenter();
        fmsSubsidiaryLedger = new FmsSubsidiaryLedger();
        fmsCostcSystemJunction = new FmsCostcSystemJunction();
        fmsSystemJobJunction = new FmsSystemJobJunction();

        return null;
    }
//</editor-fold>

    private BarChartModel ItemStatusBarModel;
    ChartSeries Chart = new ChartSeries();
    int totalItems = 0;
    List<String> ItemStatsuTypes = new ArrayList<>();
    List<VoucherStatus> ItemStatusList = new ArrayList<>();
    VoucherStatus itemStatus = new VoucherStatus();

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            fmsVoucher.setColumnName(event.getNewValue().toString());
            fmsVoucher.setColumnValue(null);
            System.out.println("fmsVoucher.getColumnName(); " + fmsVoucher.getColumnName());
            System.out.println("fmsVoucher.getColumnValue(); " + fmsVoucher.getColumnValue());

        }
    }

    public class VoucherStatus implements Serializable {

        private String voucherStatus;
        private int total;

        public String getVoucherStatus() {
            return voucherStatus;
        }

        public void setVoucherStatus(String voucherStatus) {
            this.voucherStatus = voucherStatus;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public VoucherStatus ItemByStatus(String status, int totalCount) {
            this.voucherStatus = status;
            this.total = totalCount;
            return this;
        }
    }

    public BarChartModel getItemStatusBarModel() {
        return ItemStatusBarModel;
    }

    public void setItemStatusBarModel(BarChartModel ItemStatusBarModel) {
        this.ItemStatusBarModel = ItemStatusBarModel;
    }

    public ChartSeries getChart() {
        return Chart;
    }

    public void setChart(ChartSeries Chart) {
        this.Chart = Chart;
    }

    public List<String> getItemStatsuTypes() {
        return ItemStatsuTypes;
    }

    public void setItemStatsuTypes(List<String> ItemStatsuTypes) {
        this.ItemStatsuTypes = ItemStatsuTypes;
    }

    public List<VoucherStatus> getItemStatusList() {
        return ItemStatusList;
    }

    public void setItemStatusList(List<VoucherStatus> ItemStatusList) {
        this.ItemStatusList = ItemStatusList;
    }

    public void ItemStausBarChartCreator() {
        ItemStatsuTypes = chequeVoucherBeanLocal.findAllItemStatuses();
        System.out.println("befor the if statment ==ItemStatsuTypes====" + ItemStatsuTypes);
        for (int i = 0; i < ItemStatsuTypes.size(); i++) {
            int itmeCount = 0;
            itmeCount = chequeVoucherBeanLocal.ConutBYItemType(ItemStatsuTypes.get(i));

            ItemStatusList.add(itemStatus.ItemByStatus(ItemStatsuTypes.get(i), itmeCount));
            System.out.println("ItemStatsuTypes.get(i)==" + ItemStatsuTypes.get(i));
            System.out.println("itmeCount==" + itmeCount);
            Chart.setLabel("Item Status");
            Chart.set(ItemStatsuTypes.get(i), itmeCount);
            totalItems = totalItems + itmeCount;
        }
        createBarModel();
    }

    private void createBarModel() {
        ItemStatusBarModel = initBarModel1();
        ItemStatusBarModel.setTitle("Item Grouped By Their Status ");
        ItemStatusBarModel.setLegendPosition("ne");
        ItemStatusBarModel.setAnimate(true);
        Axis xAxis = ItemStatusBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Item Status");
        xAxis.setTickAngle(0);
        Axis yAxis = ItemStatusBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Total Item");
        yAxis.setMin(0);
        yAxis.setMax(totalItems);
    }

    private BarChartModel initBarModel1() {
        BarChartModel model = new BarChartModel();
        model.addSeries(Chart);
        return model;
    }

}
