/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.Voucher;

  //<editor-fold defaultstate="collapsed" desc="Import block">
import et.gov.eep.commonApplications.businessLogic.WfFcmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import et.gov.eep.commonApplications.localbean;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.bank.FmsCodedTransactionBeanLocal;
import et.gov.eep.fcms.businessLogic.Voucher.FmsVoucherBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsVouchersNoRangeBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.controller.Constants;
import et.gov.eep.fcms.entity.FmsAccountUse;
import et.gov.eep.fcms.entity.bank.FmsCodedTransaction;
import et.gov.eep.fcms.entity.FmsDocumentFollowup;
import et.gov.eep.fcms.entity.Vocher.FmsJournalVoucher;
import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsLuVouchersType;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.entity.admin.FmsVouchersNoRange;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollSummeryBeanLocal;
import et.gov.eep.hrms.entity.payroll.HrPayrollSummery;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.convert.NumberConverter;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
//</editor-fold>

@Named

@ViewScoped
public class postingJournalVoucherController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="EJB ">
    @EJB
    FmsVoucherBeanLocal fmsVoucherBeanLocal;
    @EJB
    FmsCostCenterBeanLocal fmsCostCenterBeanLocal;
    @EJB
    FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    FmsLuSystemBeanLocal fmsLuSystemBeanLocal;
    @EJB
    subsidiaryBeanLocal subLedgerBeanLocal;
    @EJB
    FmsCodedTransactionBeanLocal fmsCodedTransactionBeanLocal;
    @EJB
    HrPayrollSummeryBeanLocal hrPayrollSummeryBeanLocal;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Injections">
    @Inject
    localbean Languagelocalbean;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @Inject
    FmsSubsidiaryLedger fmsSubsid1aryLedger1;
    @Inject
    FmsLuSystem fmsLuSystem;
    @Inject
    FmsCostCenter fmsCostCenter;
    @Inject
    FmsCodedTransaction fmsCodedTransaction;
    @Inject
    HrPayrollSummery hrPayrollSummery;
    @Inject
    FmsDocumentFollowup fmsDocumentFollowup;
    @Inject
    FmsJournalVoucher journalVoucherEnty;
    @Inject
    FmsAccountUse accountUseEnty;
    @Inject
    FmsVoucher fmsVoucher;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Object and variable declaration">
    DataModel<FmsAccountUse> accountUseDetailDataModel;
    DataModel<FmsVoucher> voucherDataModel;
    DataModel<FmsJournalVoucher> journalVoucherDataModel;
    int updteStatus = 0;
    private String saveorUpdateBundle = Constants.getComponentBundle("Create");
    private String ActionDebitCredit = "";
    private boolean disableBtnCreate;
    private boolean renderBtnPrint = false;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateJv = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-plus";
    private BigDecimal ValueDebitCredit = new BigDecimal(0.0);
    private Boolean printe = false;
    int interCatListSizes = 0;
    int IntermidiateCatStatus = 0;
    int catagorynameStatus = 0;
    Boolean addButtonStatus = false;
    Boolean selectOptionStatus = false;
    Boolean editRemoveStatus = false;
    private String selCodedTransactionVal = "codedTransaction";
    private String selPayrollSummaryVal = "payrollSummary";
    private String selOtherVal = "other";
    private String selectOption = "";
    Boolean void_dis = true;
    DataModel<FmsAccountUse> accountUseDetailDataModelposting;
    private List<FmsVoucher> selectedJv;
    private FmsAccountUse selectedAccountUses;
    private List<FmsLuSystem> listOfSystems;
    private String sysName;
    private String costName;
    private String generalLName;
    private String subLName;
    boolean cc = true;
    boolean sl = true;
    List<FmsCodedTransaction> codedTranList;
    List<HrPayrollSummery> payrollSummeryList;
    Set<String> checkSubsidiaryCode = new HashSet<>();
    List<HashMap> list = new ArrayList<HashMap>() {
    };
    List<FmsGeneralLedger> listGeneralLedger = null;
    int GLListSize = 0;
    List<FmsVoucher> jvList;
    private String Conc = "";
    private String display_conn;
    int dataTableUpdateStatus = 0;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="posting Journal Voucher Controller">
    public postingJournalVoucherController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(3);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreate Jv DataModel">
    public void recreateJvDataModel() {
        voucherDataModel = null;
        voucherDataModel = new ListDataModel(new ArrayList(jvList));
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="create Journal VocherReg">
    public void createJournalVocherReg() {
        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateJv = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateJv = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="concatenateAC">
    public void concatenateAC() {// on populate JV
        for (int i = 0; i < fmsVoucher.getFmsAccountUseList().size(); i++) {
            String splitedaccoutid[] = fmsVoucher.getFmsAccountUseList().get(i).getSubsidaryId().split("-");
            fmsLuSystem.setSystemId(Integer.parseInt(splitedaccoutid[0]));
            fmsLuSystem = fmsLuSystemBeanLocal.getSystembyId(fmsLuSystem);
            String CC_display = "CC";
            String SL_display = "SL";
            if (!"CC".equals(splitedaccoutid[1])) {
                fmsCostCenter.setCostCenterId(Integer.parseInt(splitedaccoutid[1]));
                fmsCostCenter = fmsCostCenterBeanLocal.getCostCenterId(fmsCostCenter);
                CC_display = fmsCostCenter.getSystemName();
            }

            fmsGeneralLedger.setGeneralLedgerId(Integer.parseInt(splitedaccoutid[2]));
            fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getByGlId(fmsGeneralLedger);
            if (!"SL".equals(splitedaccoutid[3])) {
                fmsSubsid1aryLedger1.setSubsidiaryId(Integer.parseInt(splitedaccoutid[3]));
                fmsSubsid1aryLedger1 = subLedgerBeanLocal.getSubsidiaryInfo(fmsSubsid1aryLedger1);
                SL_display = fmsSubsid1aryLedger1.getSubsidiaryCode();
            }
            display_conn = fmsLuSystem.getSystemCode() + "-" + CC_display + "-" + fmsGeneralLedger.getGeneralLedgerCode() + "-" + SL_display;
            fmsVoucher.getFmsAccountUseList().get(i).setDisplay_conn(display_conn);

        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="populate">

    public void populate(SelectEvent event) {
        fmsVoucher = (FmsVoucher) event.getObject();
        String splitedVoucherNO[] = fmsVoucher.getVoucherId().split("-");
        clearPopup();
        setVoucherNO(splitedVoucherNO[1]);
        concatenateAC();
        recreateModelDetail();
        recreateJVModelDetail();
        addButtonStatus = true;
        selectOptionStatus = true;
        editRemoveStatus = true;
        renderBtnPrint = true;
        updteStatus = 1;
        renderPnlCreateJv = true;
        renderPnlManPage = false;
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updteStatus = 1;
        printe = true;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="populate Account Detail">

    public void populateAccDetail(SelectEvent event) {
        accountUseEnty = (FmsAccountUse) event.getObject();
//         fmsGeneralLedger = accountUseEnty.getSubsidaryId().getGeneralLedgerId();
//        fmsSubsid1aryLedger1 = accountUseEnty.getSubsidaryId();
//        dataTableUpdateStatus = 1;
        String subsidari = accountUseEnty.getSubsidaryId();
//        accountUseEnty = getAccountUseDetailDataModel().getRowData();
//        accountUseEnty.setSubsidaryId(getAccountUseDetailDataModel().getRowData().getSubsidaryId());
//        subsidari = getAccountUseDetailDataModel().getRowData().getSubsidaryId();
//        HashMap<String, Integer> words_fre = new HashMap<String, Integer>();
        int x = 0;
        for (String retval : subsidari.split("-")) {
            System.out.println(retval);
            if (x == 0) {
                int sys = Integer.parseInt(retval);
                fmsLuSystem.setSystemId(sys);
                x = 1;
            } else if (x == 1 && !retval.matches("CC")) {
                int sys = Integer.parseInt(retval);
                fmsCostCenter.setCostCenterId(sys);
                x = 2;
            } else if (x == 2) {
                int sys = Integer.parseInt(retval);
                fmsGeneralLedger.setGeneralLedgerId(sys);
                x = 3;
            } else if (x == 3 && !retval.matches("SL")) {
                int sys = Integer.parseInt(retval);
                fmsSubsid1aryLedger1.setSubsidiaryId(sys);
            }

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

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="AccountUse,GeneralLedger,Subsidiary - Implimentation">
    public String journalVoucherAddSubsidiaryLedgerDetail() {
        try {
            FmsAccountUse accountUseEnty = new FmsAccountUse();
            Concatenate();
            accountUseEnty.setSubsidaryId(Conc);
            accountUseEnty.setDisplay_conn(display_conn);
            if (ActionDebitCredit.equals("Debit")) {
                accountUseEnty.setDebit(ValueDebitCredit);
                accountUseEnty.setCredit(new BigDecimal(0.0));
            }
            if (ActionDebitCredit.equals("Credit")) {
                accountUseEnty.setDebit(new BigDecimal(0.0));
                accountUseEnty.setCredit(ValueDebitCredit);
            }
            //check duplicate Debit Credit with same SL code
            if (checkSubsidiaryCode.contains(accountUseEnty.getSubsidaryId())) {
                JsfUtil.addFatalMessage("Debit/Credit is not allowed with duplicate Subsidiary Code!");
                return null;
            } else {
                fmsVoucher.addToFmsSubsidiaryLSrDetail(accountUseEnty);
                checkSubsidiaryCode.add(accountUseEnty.getSubsidaryId());
                recreateModelDetail();
                editRemoveStatus = true;
                return clearPopup();
            }
        } catch (Exception ex) {
            JsfUtil.addFatalMessage("Failed to add! Try again reloading the page!");
        }
        return null;
    }

    public SelectItem[] getCostCenterBySystemLU() {

        fmsLuSystem = fmsCostCenter.getSystemId();
        List<FmsCostCenter> costcenterList = new ArrayList<>();

        if (fmsLuSystem != null) {
            SelectItem[] listcc = null;
            costcenterList = fmsCostCenterBeanLocal.findCostCenter(fmsLuSystem);
            if (costcenterList.size() > 0) {
                cc = false;

            } else {
                cc = true;
            }
            return JsfUtil.getSelectItems(fmsCostCenterBeanLocal.findCostCenter(fmsLuSystem), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            return items;
        }
    }

    public void SystemChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuSystem.setSystemCode(event.getNewValue().toString());
            fmsLuSystem = fmsLuSystemBeanLocal.getSysDetail(fmsLuSystem);
            sysName = fmsLuSystem.getSystemName();
            fmsCostCenter.setSystemId(fmsLuSystem);
        }
    }

    public void CostCenterChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            fmsCostCenter.setSystemName(event.getNewValue().toString());
            fmsCostCenter = fmsCostCenterBeanLocal.getCostDetail(fmsCostCenter);
            costName = fmsCostCenter.getDescription();
        }
    }

    public void getGeneralLedgerChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            fmsGeneralLedger.setGeneralLedgerCode(event.getNewValue().toString());
            fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getGLDetail(fmsGeneralLedger);
            generalLName = fmsGeneralLedger.getAccountTitle();
            fmsSubsid1aryLedger1.setGeneralLedgerId(fmsGeneralLedger);

        }
    }

    public void getSubsidiaryLChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsSubsid1aryLedger1.setSubsidiaryId(Integer.parseInt(event.getNewValue().toString()));
            fmsSubsid1aryLedger1 = subLedgerBeanLocal.getSubsidiaryInfo(fmsSubsid1aryLedger1);
            subLName = fmsSubsid1aryLedger1.getAccountTitle();

        }
    }

    public SelectItem[] findListSubL() {
        ArrayList<FmsSubsidiaryLedger> subsidaryList = new ArrayList<>();
        if (fmsGeneralLedger != null) {
            SelectItem[] listSl = null;

            subsidaryList = subLedgerBeanLocal.findSubLedger(fmsGeneralLedger);
            if (subsidaryList.size() > 0) {
                sl = false;
                listSl = new SelectItem[subsidaryList.size() + 1];
                listSl[0] = new SelectItem(null, "--- Select One ---");
                for (int i = 0; i < subsidaryList.size(); i++) {
                    listSl[i + 1] = new SelectItem(subsidaryList.get(i).getSubsidiaryId(), subsidaryList.get(i).getSubsidiaryCode());
                }
            } else {
                sl = true;

            }
            return listSl;
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--Select CC & GL--");
            return items;
        }
    }

    public void concatenateCT() {//for coded transactions
        try {
            int size = codedTranList.size();
            for (int i = 0; i < size; i++) {
                String accountCode[] = codedTranList.get(i).getSubsidiaryId().split("-");
                fmsLuSystem.setSystemId(Integer.parseInt(accountCode[0]));//SS
                fmsLuSystem = fmsLuSystemBeanLocal.getSystembyId(fmsLuSystem);
                String CC_display = "CC";
                String SL_display = "SL";
                if (!"CC".contains(accountCode[1])) {//CC
                    fmsCostCenter.setCostCenterId(Integer.parseInt(accountCode[1]));
                    fmsCostCenter = fmsCostCenterBeanLocal.getCostCenterId(fmsCostCenter);
                    CC_display = fmsCostCenter.getSystemName();
                }
                fmsGeneralLedger.setGeneralLedgerId(Integer.parseInt(accountCode[2]));//GL
                fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getByGlId(fmsGeneralLedger);
                if (!"SL".equals(accountCode[0])) {//SL
                    fmsSubsid1aryLedger1.setSubsidiaryId(Integer.parseInt(accountCode[3]));
                    fmsSubsid1aryLedger1 = subLedgerBeanLocal.getSubsidiaryInfo(fmsSubsid1aryLedger1);
                    SL_display = fmsSubsid1aryLedger1.getSubsidiaryCode();
                }
                display_conn = fmsLuSystem.getSystemCode() + "-" + CC_display + "-" + fmsGeneralLedger.getGeneralLedgerCode() + "-" + SL_display;
                codedTranList.get(i).setDisplay_conn(display_conn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void concatenatePS() {//for payroll summery
        try {
            int size = payrollSummeryList.size();
            for (int i = 0; i < size; i++) {
                String SS_display = "SS";
                String CC_display = "CC";
                String SL_display = "SL";
                String accountCode[] = payrollSummeryList.get(i).getAccountCode().split("-");
                if (!"SS".equals(accountCode[0])) {
                    fmsLuSystem.setSystemId(Integer.parseInt(accountCode[0]));//SS
                    fmsLuSystem = fmsLuSystemBeanLocal.getSystembyId(fmsLuSystem);
                    SS_display = fmsLuSystem.getSystemCode();
                }
                if (!"CC".contains(accountCode[1])) {//CC
                    fmsCostCenter.setCostCenterId(Integer.parseInt(accountCode[1]));
                    fmsCostCenter = fmsCostCenterBeanLocal.getCostCenterId(fmsCostCenter);
                    CC_display = fmsCostCenter.getSystemName();
                }
                fmsGeneralLedger.setGeneralLedgerId(Integer.parseInt(accountCode[2]));//GL
                fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getByGlId(fmsGeneralLedger);
                if (!"SL".equals(accountCode[3])) {
                    fmsSubsid1aryLedger1.setSubsidiaryId(Integer.parseInt(accountCode[3]));//SL
                    fmsSubsid1aryLedger1 = subLedgerBeanLocal.getSubsidiaryInfo(fmsSubsid1aryLedger1);
                    SL_display = fmsSubsid1aryLedger1.getSubsidiaryCode();
                }
                display_conn = SS_display + "-" + CC_display + "-" + fmsGeneralLedger.getGeneralLedgerCode() + "-" + SL_display;
                payrollSummeryList.get(i).setDisplay_conn(display_conn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="optionHandiler">
    public void optionHandiler(ValueChangeEvent event) {
        try {
            accountUseDetailDataModel = null;
            String selected = journalVoucherEnty.getPrepareRemark();

            if (selected.matches("codedTransaction")) {
                codedTranList = fmsCodedTransactionBeanLocal.getCodedTransactionInfo(fmsLuSystem);
                if (codedTranList.isEmpty()) {
                    JsfUtil.addFatalMessage("No Coded Transactions Ready for JV Yet.");
                } else {
                    addButtonStatus = false;
                    concatenateCT();
                    for (FmsCodedTransaction fmsc : codedTranList) {
                        FmsAccountUse ac = new FmsAccountUse();
                        ac.setCredit(BigDecimal.valueOf(fmsc.getCredit()));
                        ac.setDebit(BigDecimal.valueOf(fmsc.getDebit()));
                        ac.setSubsidaryId(fmsc.getSubsidiaryId());
                        ac.setDisplay_conn(fmsc.getDisplay_conn());
                        fmsVoucher.addToFmsSubsidiaryLSrDetail(ac);
                    }
                    accountUseDetailDataModel = new ListDataModel(fmsVoucher.getFmsAccountUseList());
                }

            } else if (selected.matches("payrollSummary")) {
                accountUseDetailDataModel = null;
                payrollSummeryList = hrPayrollSummeryBeanLocal.getPayrollMonthlyTrn();
                if (payrollSummeryList.isEmpty()) {
                    JsfUtil.addFatalMessage("No Payroll Summary is Ready for JV Yet.");
                } else {
                    concatenatePS();
                    for (HrPayrollSummery hrPs : payrollSummeryList) {
                        FmsAccountUse ac = new FmsAccountUse();
                        ac.setCredit(hrPs.getCredit());
                        ac.setDebit(hrPs.getDebit());
                        ac.setSubsidaryId(hrPs.getAccountCode());
                        ac.setDisplay_conn(hrPs.getDisplay_conn());
                        fmsVoucher.addToFmsSubsidiaryLSrDetail(ac);
                    }
                    addButtonStatus = false;
                    accountUseDetailDataModel = new ListDataModel(fmsVoucher.getFmsAccountUseList());
                }
            } else if (selected.matches("other")) {
                accountUseDetailDataModel = null;
                addButtonStatus = true;
            } else {
                JsfUtil.addFatalMessage("Failed to Retrieve Data!");
            }
        } catch (Exception ex) {
            JsfUtil.addFatalMessage("Failed to Retrieve Data!");
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="clear Popup">
    public String clearPopup() {
        accountUseEnty = null;
        fmsLuSystem = null;
        fmsSubsid1aryLedger1 = null;
        fmsCostCenter = null;
        fmsGeneralLedger = null;
        accountUseEnty = new FmsAccountUse();
        ValueDebitCredit = new BigDecimal(0.00);
        ActionDebitCredit = "";
        fmsGeneralLedger = new FmsGeneralLedger();
        fmsLuSystem = new FmsLuSystem();
        fmsSubsid1aryLedger1 = new FmsSubsidiaryLedger();
        fmsCostCenter = new FmsCostCenter();
        return null;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="update Account Use Detail">

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
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreate Model Detail">

    public void recreateModelDetail() {
        fmsLuSystem = new FmsLuSystem();
        fmsCostCenter = new FmsCostCenter();
        fmsGeneralLedger = new FmsGeneralLedger();
        fmsSubsid1aryLedger1 = new FmsSubsidiaryLedger();
        accountUseDetailDataModel = null;
        accountUseDetailDataModel = new ListDataModel(fmsVoucher.getFmsAccountUseList());
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
        if (fmsSubsid1aryLedger1.getSubsidiaryCode() != null) {
            SL = fmsSubsid1aryLedger1.getSubsidiaryId().toString();
            SL_display = fmsSubsid1aryLedger1.getSubsidiaryCode();

        }

        display_conn = fmsLuSystem.getSystemCode() + "-" + CC_display + "-" + fmsSubsid1aryLedger1.getGeneralLedgerId().getGeneralLedgerCode() + "-" + SL_display;
        Conc = fmsLuSystem.getSystemId() + "-" + CC + "-" + fmsSubsid1aryLedger1.getGeneralLedgerId().getGeneralLedgerId() + "-" + SL;

    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="delete Account Use Detail">

    public void deleteAccountUseDetail() {
        dataTableUpdateStatus = 1;
        fmsVoucher.removedata(getAccountUseDetailDataModel().getRowData());
        JsfUtil.addSuccessMessage("Data is removed");
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreate JV ModelDetail">

    public void recreateJVModelDetail() {
        journalVoucherEnty = fmsVoucher.getFmsJournalVoucher();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">

    /**
     *
     * @return
     */
    public String getSubLName() {
        return subLName;
    }

    public void setSubLName(String subLName) {
        this.subLName = subLName;
    }

    public SelectItem[] getGLdata() {
        return JsfUtil.getSelectItems(fmsGeneralLedgerBeanLocal.findAll(), true);
    }

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

    public SelectItem[] getSubList() {
        return JsfUtil.getSelectItems(subLedgerBeanLocal.findAll(), true);
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
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

    public List<FmsCodedTransaction> getCodedTranList() {
        if (codedTranList == null) {
            codedTranList = new ArrayList<>();
        }
        return codedTranList;
    }

    public void setCodedTranList(List<FmsCodedTransaction> codedTranList) {
        this.codedTranList = codedTranList;
    }

    public List<HrPayrollSummery> getPayrollMonTranList() {
        if (payrollSummeryList == null) {
            payrollSummeryList = new ArrayList<>();
        }
        return payrollSummeryList;
    }

    public void setPayrollMonTranList(List<HrPayrollSummery> payrollSummeryList) {
        this.payrollSummeryList = payrollSummeryList;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isRenderBtnPrint() {
        return renderBtnPrint;
    }

    public void setRenderBtnPrint(boolean renderBtnPrint) {
        this.renderBtnPrint = renderBtnPrint;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
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

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public DataModel<FmsVoucher> getVoucherDataModel() {
        if (voucherDataModel == null) {
            voucherDataModel = new ListDataModel<>();
        }
        return voucherDataModel;
    }

    public void setVoucherDataModel(DataModel<FmsVoucher> voucherDataModel) {
        this.voucherDataModel = voucherDataModel;
    }

    public Boolean getEditRemoveStatus() {
        return editRemoveStatus;
    }

    public void setEditRemoveStatus(Boolean editRemoveStatus) {
        this.editRemoveStatus = editRemoveStatus;
    }

    public Boolean getSelectOptionStatus() {
        return selectOptionStatus;
    }

    public void setSelectOptionStatus(Boolean selectOptionStatus) {
        this.selectOptionStatus = selectOptionStatus;
    }

    public String getSelCodedTransactionVal() {
        return selCodedTransactionVal;
    }

    public void setSelCodedTransactionVal(String selCodedTransactionVal) {
        this.selCodedTransactionVal = selCodedTransactionVal;
    }

    public String getSelPayrollSummaryVal() {
        return selPayrollSummaryVal;
    }

    public void setSelPayrollSummaryVal(String selPayrollSummaryVal) {
        this.selPayrollSummaryVal = selPayrollSummaryVal;
    }

    public String getSelOtherVal() {
        return selOtherVal;
    }

    public void setSelOtherVal(String selOtherVal) {
        this.selOtherVal = selOtherVal;
    }

    public FmsCodedTransaction getFmsCodedTransaction() {
        if (fmsCodedTransaction == null) {
            fmsCodedTransaction = new FmsCodedTransaction();
        }
        return fmsCodedTransaction;
    }

    public void setFmsCodedTransaction(FmsCodedTransaction fmsCodedTransaction) {
        this.fmsCodedTransaction = fmsCodedTransaction;
    }

    public HrPayrollSummery getHrPayrollSummery() {
        if (hrPayrollSummery == null) {
            hrPayrollSummery = new HrPayrollSummery();
        }
        return hrPayrollSummery;
    }

    public void setHrPayrollSummery(HrPayrollSummery hrPayrollSummery) {
        this.hrPayrollSummery = hrPayrollSummery;
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

    public FmsCodedTransactionBeanLocal getFmsCodedTransactionBeanLocal() {
        return fmsCodedTransactionBeanLocal;
    }

    public void setFmsCodedTransactionBeanLocal(FmsCodedTransactionBeanLocal fmsCodedTransactionBeanLocal) {
        this.fmsCodedTransactionBeanLocal = fmsCodedTransactionBeanLocal;
    }

    public void setFmsGeneralLedgerBeanLocal(FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal) {
        this.fmsGeneralLedgerBeanLocal = fmsGeneralLedgerBeanLocal;
    }

    public FmsLuSystemBeanLocal getFmsLuSystemBeanLocal() {
        return fmsLuSystemBeanLocal;
    }

    public void setFmsLuSystemBeanLocal(FmsLuSystemBeanLocal fmsLuSystemBeanLocal) {
        this.fmsLuSystemBeanLocal = fmsLuSystemBeanLocal;
    }

    public subsidiaryBeanLocal getSubLedgerBeanLocal() {
        return subLedgerBeanLocal;
    }

    public void setSubLedgerBeanLocal(subsidiaryBeanLocal subLedgerBeanLocal) {
        this.subLedgerBeanLocal = subLedgerBeanLocal;
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

    public FmsSubsidiaryLedger getFmsSubsid1aryLedger1() {
        if (fmsSubsid1aryLedger1 == null) {
            fmsSubsid1aryLedger1 = new FmsSubsidiaryLedger();
        }
        return fmsSubsid1aryLedger1;
    }

    public void setFmsSubsid1aryLedger1(FmsSubsidiaryLedger fmsSubsid1aryLedger1) {
        this.fmsSubsid1aryLedger1 = fmsSubsid1aryLedger1;
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

    public FmsDocumentFollowup getFmsDocumentFollowup() {
        if (fmsDocumentFollowup == null) {
            fmsDocumentFollowup = new FmsDocumentFollowup();

        }
        return fmsDocumentFollowup;
    }

    public void setFmsDocumentFollowup(FmsDocumentFollowup fmsDocumentFollowup) {
        this.fmsDocumentFollowup = fmsDocumentFollowup;
    }

    public FmsJournalVoucher getJournalVoucherEnty() {
        if (journalVoucherEnty == null) {
            journalVoucherEnty = new FmsJournalVoucher();
        }
        return journalVoucherEnty;
    }

    public void setJournalVoucherEnty(FmsJournalVoucher journalVoucherEnty) {
        this.journalVoucherEnty = journalVoucherEnty;
    }

    public BigDecimal getValueDebitCredit() {
        return ValueDebitCredit;
    }

    public void setValueDebitCredit(BigDecimal ValueDebitCredit) {
        this.ValueDebitCredit = ValueDebitCredit;
    }

    public String getActionDebitCredit() {
        return ActionDebitCredit;
    }

    public void setActionDebitCredit(String ActionDebitCredit) {
        this.ActionDebitCredit = ActionDebitCredit;
    }

    public Boolean getPrinte() {
        return printe;
    }

    public void setPrinte(Boolean printe) {
        this.printe = printe;
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

    public Boolean getVoid_dis() {
        return void_dis;
    }

    public void setVoid_dis(Boolean void_dis) {
        this.void_dis = void_dis;
    }

    public DataModel<FmsAccountUse> getAccountUseDetailDataModel() {
        if (accountUseDetailDataModel == null) {
            accountUseDetailDataModel = new ListDataModel<>();
        }
        return accountUseDetailDataModel;
    }

    public void setAccountUseDetailDataModel(DataModel<FmsAccountUse> accountUseDetailDataModel) {
        this.accountUseDetailDataModel = accountUseDetailDataModel;
    }

    private List<FmsAccountUse> acUseDetil;

    public List<FmsAccountUse> getAcUseDetil() {

        return acUseDetil;
    }

    public void setAcUseDetil(List<FmsAccountUse> acUseDetil) {
        this.acUseDetil = acUseDetil;
    }

// enty and buss-bean and local
    public FmsAccountUse getAccountUseEnty() {
        if (accountUseEnty == null) {
            accountUseEnty = new FmsAccountUse();
        }
        return accountUseEnty;
    }

    public void setAccountUseEnty(FmsAccountUse accountUseEnty) {
        this.accountUseEnty = accountUseEnty;
    }

    public FmsVoucher getFmsVoucher() {
        if (fmsVoucher == null) {
            fmsVoucher = new FmsVoucher();
        }
        return fmsVoucher;
    }

    public void setFmsVoucher(FmsVoucher fmsVoucher) {
        this.fmsVoucher = fmsVoucher;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getConc() {
        return Conc;
    }

    public void setConc(String Conc) {
        this.Conc = Conc;
    }

    public Boolean getAddButtonStatus() {
        return addButtonStatus;
    }

    public void setAddButtonStatus(Boolean addButtonStatus) {
        this.addButtonStatus = addButtonStatus;
    }

    public List<FmsVoucher> getSelectedJv() {
        return selectedJv;
    }

    public void setSelectedJv(List<FmsVoucher> selectedJv) {
        this.selectedJv = selectedJv;
    }

    public FmsAccountUse getSelectedAccountUses() {
        return selectedAccountUses;
    }

    public void setSelectedAccountUses(FmsAccountUse selectedAccountUses) {
        this.selectedAccountUses = selectedAccountUses;
    }

    public List<FmsVoucher> getJvList() {
        if (jvList == null) {
            jvList = new ArrayList();
        }
        return jvList;
    }

    public void setJvList(List<FmsVoucher> jvList) {
        this.jvList = jvList;
    }

    public DataModel<FmsAccountUse> getAccountUseDetailDataModelposting() {
        accountUseDetailDataModelposting = new ListDataModel(vouchersDetailDataModel.getRowData().getFmsAccountUseList());
        return accountUseDetailDataModelposting;
    }

    public void setAccountUseDetailDataModelposting(DataModel<FmsAccountUse> accountUseDetailDataModelposting) {
        this.accountUseDetailDataModelposting = accountUseDetailDataModelposting;
    }

    //</editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="save - Update">
    public void saveJournalVoucher() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(sessionBeanLocal.getUserName(), "saveJournalVoucher", dataset)) {
//                 put ur code here...! 
            try {
                String selected = journalVoucherEnty.getPrepareRemark();
//            if (ValidDebitCredit()) {
                if (updteStatus == 0) {
                    String voucherID, preparedate;
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    preparedate = dateFormat.format(journalVoucherEnty.getPreparedDate());
                    voucherID = preparedate + "-" + getVoucherNO() + "-" + "JV";
                    fmsVoucher.setVoucherId(voucherID);
                    if (fmsVoucherBeanLocal.getfmsVoucherDup(fmsVoucher)) {
                        JsfUtil.addFatalMessage("Voucher No. value is Duplicated. Try again reloading the page!");
                    } else {

                        if ((!(getAccountUseDetailDataModel().getRowCount() > 0))) {
                            JsfUtil.addFatalMessage("Failed. Data table shoud be filled");
                        } else {
                            try {
                                journalVoucherEnty.setSourceJeId("0");
                                fmsVoucher.setStatus(2);
                                fmsDocumentFollowup.setDocumentReference("12345");
                                fmsDocumentFollowup.setType("JV");
                                fmsVoucher.setType("JV");
                                fmsVoucher.addToFmsDocumentFollowupDetail(fmsDocumentFollowup);
                                fmsVoucher.addToFmsJournalVoucherDetail(journalVoucherEnty);
                                fmsVoucherBeanLocal.create(fmsVoucher);
                                int currentNo = Integer.parseInt(getVoucherNO()) + 1;
                                vouchersNoRange.setCurrentNo(String.valueOf(currentNo));
                                noRangeBeanLocal.edit(vouchersNoRange);
                                //coded Transaction Option
                                if (selected.matches("codedTransaction")) {
                                    if (ValidDebitCredit()) {
                                        for (int i = 0; i < codedTranList.size(); i++) {
                                            fmsCodedTransaction = new FmsCodedTransaction();
                                            fmsCodedTransaction.setCodedTransactonId(codedTranList.get(i).getCodedTransactonId());
                                            fmsCodedTransaction.setSubsidiaryId(codedTranList.get(i).getSubsidiaryId());
                                            fmsCodedTransaction.setDebit(codedTranList.get(i).getDebit());
                                            fmsCodedTransaction.setCredit(codedTranList.get(i).getCredit());
                                            fmsCodedTransaction.setTranRef(codedTranList.get(i).getTranRef());
                                            fmsCodedTransaction.setStatus(1);
                                            fmsCodedTransaction.setRecon_status(0);
                                            fmsCodedTransaction.setType(codedTranList.get(i).getType());
                                            fmsCodedTransactionBeanLocal.edit(fmsCodedTransaction);
                                        }
                                    } else {
                                        JsfUtil.addFatalMessage("Debit & Credit Value Should be Balanced!");
                                    }
                                }
                                //payroll Summary Option
                                if (selected.matches("payrollSummary")) {
                                    for (int i = 0; i < payrollSummeryList.size(); i++) {
                                        hrPayrollSummery = new HrPayrollSummery();
                                        hrPayrollSummery.setId(payrollSummeryList.get(i).getId());
                                        hrPayrollSummery.setAccountCode(payrollSummeryList.get(i).getAccountCode());
                                        hrPayrollSummery.setCredit(payrollSummeryList.get(i).getCredit());
                                        hrPayrollSummery.setDebit(payrollSummeryList.get(i).getDebit());
                                        hrPayrollSummery.setEdCode(payrollSummeryList.get(i).getEdCode());
                                        hrPayrollSummery.setPaymentLevel(payrollSummeryList.get(i).getPaymentLevel());
                                        hrPayrollSummery.setPayrollId(payrollSummeryList.get(i).getPayrollId());
                                        hrPayrollSummery.setStatus(1);
                                        hrPayrollSummeryBeanLocal.edit(hrPayrollSummery);
                                    }
                                }
                                JsfUtil.addSuccessMessage("Saved Successfully!");
                                clearPage();
                            } catch (Exception e) {
                                JsfUtil.addFatalMessage("Failed to save. Try Again Reloading the Page");
                            }
                        }
                    }
                } else if (updteStatus == 1) {
                    if ((!(getAccountUseDetailDataModel().getRowCount() > 0))) {
                        JsfUtil.addFatalMessage("Data table shoud be filled");
                        //return null;
                    }
                    fmsVoucher.setStatus(2);
                    fmsVoucherBeanLocal.edit(fmsVoucher);
                    JsfUtil.addSuccessMessage("Updated Successfully!");
                    selectOptionStatus = false;
                    clearPage();
                }

            } catch (Exception e) {
                JsfUtil.addFatalMessage("Failed to Update. Try Again Reloading the Page");
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
        // return null;

//            } 
        //return null;
    }

    public String clearPage() {
        clearPopup();
        fmsVoucher = null;
        journalVoucherEnty = null;
        accountUseEnty = null;
        accountUseDetailDataModel = null;
        voucherDataModel = null;
        updteStatus = 0;
        return null;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Search">
    List<FmsVoucher> listVoucher = null;
    int MajorCatagorylistId = 0;

    public List<FmsVoucher> JVSearchlist(String voucheid) {
        listVoucher = null;
        fmsVoucher.setVoucherId(voucheid);
        listVoucher = fmsVoucherBeanLocal.searchVoucheidTypeJV(fmsVoucher);
        return listVoucher;
    }
    String voucherId = null;

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public void searchJournalVoucherByVoucherId() {
        try {
            System.out.println("---------voucherId--aa------" + voucherId);
            //find all
            jvList = new ArrayList<>();
            if (voucherId.isEmpty()) {
                System.out.println("---------voucherId-------- " + voucherId);
                jvList = fmsVoucherBeanLocal.searchAllJvByType(fmsVoucher);
                System.out.println("---------jvList----------- " + jvList);
                recreateJvDataModel();
            } else if (!(voucherId.isEmpty())) {
//                //find by begins with
                fmsVoucher.setVoucherId(voucherId);
                jvList = fmsVoucherBeanLocal.searchVoucheidTypeJV(fmsVoucher);
                recreateJvDataModel();
            } else {
                JsfUtil.addFatalMessage("No data found. Try again.");
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Failed to search jv, Please try again reloading the page.");
            e.printStackTrace();
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

    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" PostConstruct getCurrentNumber - Implimentation"> 
    @EJB
    FmsVouchersNoRangeBeanLocal noRangeBeanLocal;
    String voucherNO = "";

    public String getVoucherNO() {
        return voucherNO;
    }

    public void setVoucherNO(String voucherNO) {
        this.voucherNO = voucherNO;
    }

    FmsVouchersNoRange vouchersNoRange = new FmsVouchersNoRange();

    @PostConstruct
    public void getCurrentVoucherNumber() {
        String refNo = null;
        Integer seqNo = 0;
        vouchersNoRange.setStatus(1);
        FmsLuVouchersType luVouchersType = new FmsLuVouchersType();
        luVouchersType.setId(3);
        vouchersNoRange.setTypeId(luVouchersType);
        vouchersNoRange = noRangeBeanLocal.getCurrentVoucherNumber(vouchersNoRange);
        setVoucherNO(vouchersNoRange.getCurrentNo());
        if (Languagelocalbean.getLangsession().getAttribute("lang") != null) {
            if (Languagelocalbean.getLangsession().getAttribute("lang").toString().equalsIgnoreCase("am")) {
                Languagelocalbean.changeLanguage("ET");
            } else {
                Languagelocalbean.changeLanguage(Languagelocalbean.getLangsession().getAttribute("lang").toString());
            }
        }
    }
    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="- Number To Word convertor-Implimentation">
    private NumberConverter numberConverter = new NumberConverter();

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" calc and check Debit Credit balance - Implimentation"> 

    public BigDecimal toBigDecimal(double val) {
        BigDecimal newVal = new BigDecimal(val);
        return newVal;
    }

    public boolean ValidDebitCredit() {
        double creditValid = 0.0;
        double debitValid = 0.0;
        int size = fmsVoucher.getFmsAccountUseList().size();
        for (int i = 0; i < size; i++) {
            creditValid = creditValid + fmsVoucher.getFmsAccountUseList().get(i).getCredit().longValue();
            debitValid = debitValid + fmsVoucher.getFmsAccountUseList().get(i).getDebit().longValue();
        }
        if (creditValid == debitValid) {
            return true;
        } else {
            return false;
        }

    }
    // </editor-fold> 
    //<editor-fold defaultstate="collapsed" desc="posting - Implimentation"> 
    private List<FmsVoucher> selectedVouches = null;

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

    List<FmsVoucher> vouchersList = new ArrayList<>();

    String rederedCheckBox = "true";

    /**
     *
     * @return
     */
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

    /**
     *
     * @param events
     */
    public void check(SelectEvent events) {
        System.out.println("check");
        fmsVoucher = new FmsVoucher();
        fmsVoucher = (FmsVoucher) events.getObject();
        listVoucher.add(fmsVoucher);
        System.out.println("checked" + listVoucher.size());
        void_dis = false;
    }

    public void allcheck(SelectEvent events) {
        System.out.println("check");
        fmsVoucher = new FmsVoucher();
        listVoucher = (List<FmsVoucher>) events.getObject();
        System.out.println("checked" + listVoucher.size());
        void_dis = false;
        updteStatus = 3;

    }

    public void uncheck(UnselectEvent uncheckevent) {
        System.err.println("uncheck");
        fmsVoucher = (FmsVoucher) uncheckevent.getObject();
        if (listVoucher.contains(fmsVoucher)) {
            listVoucher.remove(fmsVoucher);
            if (listVoucher.size() == 0) {
                void_dis = true;
            }
        }
    }

    public void alluncheck(UnselectEvent uncheckevent) {
        System.out.println("uncheck");
        listVoucher.clear();
        void_dis = true;
    }
    private String PRStatus;
    private DataModel<FmsVoucher> vouchersDetailDataModel;

    /**
     *
     * @return
     */
    public DataModel<FmsVoucher> getVouchersDetailDataModel() {
        if (vouchersDetailDataModel == null) {
            vouchersDetailDataModel = new ListDataModel<>();
        }
        return vouchersDetailDataModel;
    }

    /**
     *
     * @param vouchersDetailDataModel
     */
    public void setVouchersDetailDataModel(DataModel<FmsVoucher> vouchersDetailDataModel) {
        this.vouchersDetailDataModel = vouchersDetailDataModel;
    }
//                        DataModel<FmsAccountUse> accountUseDetailDataModelrow;

    /**
     *
     * @return
     */
    public List<FmsVoucher> getVouchersList() {

        return vouchersList;
    }

    /**
     *
     * @param vouchersList
     */
    public void setVouchersList(List<FmsVoucher> vouchersList) {
        this.vouchersList = vouchersList;
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

    /**
     *
     */
    public void recreateVoucherModelDetail() {
        vouchersDetailDataModel = null;
        vouchersDetailDataModel = new ListDataModel(vouchersList);
    }

    /**
     *
     */
    public void recreateAccountModelDetail() {
        accountUseDetailDataModel = null;
        accountUseDetailDataModel = new ListDataModel(getVouchersDetailDataModel().getRowData().getFmsAccountUseList());
        System.out.println(accountUseDetailDataModel.getRowCount());
        System.out.println(getVouchersDetailDataModel().getRowData().getStatus());

    }
    @Inject
    WfFcmsProcessed wfFcmsProcessed;

    @EJB
    WfFcmsProcessedBeanLocal wfFcmsProcessedBeanLocal;

    public WfFcmsProcessed getWfFcmsProcessed() {
        if (wfFcmsProcessed == null) {
            wfFcmsProcessed = new WfFcmsProcessed();
        }
        return wfFcmsProcessed;
    }

    public void setWfFcmsProcessed(WfFcmsProcessed wfFcmsProcessed) {
        this.wfFcmsProcessed = wfFcmsProcessed;
    }

    public void post_journalVoucher_data() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(sessionBeanLocal.getUserName(), "post_journalVoucher_data", dataset)) {
            //  put ur code here...!
            if (selectedVouches.size() > 0) {
                try {
                    selectedVouches.stream().map((selectedVouche) -> {
                        FmsVoucher fmsVoucher = new FmsVoucher();
                        fmsVoucher = selectedVouche;
                        return fmsVoucher;
                    }).map((fmsVoucher) -> {
                        fmsVoucher.setStatus(3);
                        return fmsVoucher;
                    }).forEach((fmsVoucher) -> {
                        wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(sessionBeanLocal.getUserId()));
                        wfFcmsProcessed.setDecision(securityBean.Constants.VOUCHER_POST);
                        System.out.println("decision==" + wfFcmsProcessed.getDecision());
                        wfFcmsProcessed.setCommentGiven(fmsVoucher.getPreparedRemark());
                        wfFcmsProcessed.setProcessedOn(String.valueOf(fmsVoucher.getProcessedDate()));
                        fmsVoucher.setProcessedBy(wfFcmsProcessed.getProcessedOn());
                        wfFcmsProcessed.setFmsVoucherId(fmsVoucher);
                        //                    fmsVoucherBeanLocal.edit(selectedVouches.get(i));
                        fmsVoucherBeanLocal.edit(fmsVoucher);
                        wfFcmsProcessedBeanLocal.saveUpdate(wfFcmsProcessed);
                    });
                    JsfUtil.addSuccessMessage("Voucher Data is Post Done");
                    clearPage();

                } catch (Exception e) {
                    JsfUtil.addFatalMessage("Something Occured on Data post");
                    //return null;
                }

            } else {
                JsfUtil.addFatalMessage("Select one to Post");
                // return null;
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
    }

    public void void_journalVoucher_data() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(sessionBeanLocal.getUserName(), "void_journalVoucher_data", dataset)) {
//                 put ur code here...!
            if (selectedVouches.size() > 0) {
                try {
                    selectedVouches.stream().map((selectedVouche) -> {
                        FmsVoucher fmsVoucherNEW = new FmsVoucher();
                        fmsVoucherNEW = selectedVouche;
                        return fmsVoucherNEW;
                    }).map((fmsVoucherNEW) -> {
                        fmsVoucherNEW.setStatus(38);
                        fmsVoucher.setReason(fmsVoucher.getReason());
                        return fmsVoucherNEW;
                    }).forEach((fmsVoucherNEW) -> {
                        wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(sessionBeanLocal.getUserId()));
                        wfFcmsProcessed.setDecision(securityBean.Constants.VOUCHER_VOID);
                        wfFcmsProcessed.setCommentGiven(fmsVoucher.getPreparedRemark());
                        wfFcmsProcessed.setProcessedOn(String.valueOf(fmsVoucher.getProcessedDate()));
                        fmsVoucher.setProcessedBy(wfFcmsProcessed.getProcessedOn());
                        // wfFcmsProcessedBeanLocal.saveUpdate(wfFcmsProcessed);
                        fmsVoucher.setStatus(securityBean.Constants.VOUCHER_VOID);
                        //                    fmsVoucherBeanLocal.edit(selectedVouches.get(i));
                        fmsVoucherBeanLocal.edit(fmsVoucherNEW);
                    });
                    JsfUtil.addSuccessMessage("Voucher Data is Void Done");
                    clearPage();

                } catch (Exception e) {
                    JsfUtil.addFatalMessage("Something Occured on Data Void");
                    //return null;
                }

            } else {
                JsfUtil.addFatalMessage("Select one to Void");
                //return null;
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
    }

    public String GeneratePostingJv() {
        vouchersList = fmsVoucherBeanLocal.handleSearchPostJv();
        recreateVoucherModelDetail();
        updteStatus = 3;
        listVoucher = null;
        listVoucher = new ArrayList<>();
        return null;
    }

    //</editor-fold> 
}
