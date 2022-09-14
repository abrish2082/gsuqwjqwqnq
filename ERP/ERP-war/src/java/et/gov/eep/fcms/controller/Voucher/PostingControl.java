package et.gov.eep.fcms.controller.Voucher;
//<editor-fold defaultstate="collapsed" desc="import">

import et.gov.eep.fcms.businessLogic.Voucher.FmsVoucherBeanLocal;
import et.gov.eep.fcms.entity.FmsAccountUse;
import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.convert.NumberConverter;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
//</editor-fold>

@Named(value = "postingControl")
@ViewScoped
public class PostingControl implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Inject">
    @Inject
    private FmsVoucher voucher;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @Inject
    FmsSubsidiaryLedger fmsSubsid1aryLedger1;
    @Inject
    FmsLuSystem fmsLuSystem;
    @Inject
    FmsCostCenter fmsCostCenter;
    @Inject
    FmsAccountUse accountUseEnty;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="EJB">
    @EJB
    private FmsVoucherBeanLocal fmsVoucherBeanLocal;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    private FmsVoucher selectedVouches[];
    List<FmsVoucher> vouchersList = new ArrayList<>();
    String rederedCheckBox = "true";
    private String PRStatus;
    private String ActionDebitCredit = "";
    private BigDecimal ValueDebitCredit = new BigDecimal(0.0);
    private DataModel<FmsVoucher> vouchersDetailDataModel;
    DataModel<FmsAccountUse> accountUseDetailDataModel;
    int dataTableUpdateStatus = 0;
    private NumberConverter numberConverter = new NumberConverter();

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public FmsVoucher getVoucher() {
        if (voucher == null) {
            voucher = new FmsVoucher();
        }
        return voucher;
    }

    public void setVoucher(FmsVoucher voucher) {

        this.voucher = voucher;
    }

    public FmsVoucher[] getSelectedVouches() {

        return selectedVouches;
    }

    public void setSelectedVouches(FmsVoucher[] selectedVouches) {
        this.selectedVouches = selectedVouches;
    }

    public String getRederedCheckBox() {
        return rederedCheckBox;
    }

    public void setRederedCheckBox(String rederedCheckBox) {
        this.rederedCheckBox = rederedCheckBox;
    }

    public DataModel<FmsVoucher> getVouchersDetailDataModel() {
        if (vouchersDetailDataModel == null) {
            vouchersDetailDataModel = new ListDataModel<>();
        }
        return vouchersDetailDataModel;
    }

    public void setVouchersDetailDataModel(DataModel<FmsVoucher> vouchersDetailDataModel) {
        this.vouchersDetailDataModel = vouchersDetailDataModel;
    }

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="check and uncheck">
    public void check(SelectEvent events) {
        System.err.println("check");
    }

    public void uncheck(UnselectEvent uncheckevent) {
        System.err.println("uncheck");
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreate Model Detail">
    public void recreateModelDetail() {
        vouchersDetailDataModel = null;
        vouchersDetailDataModel = new ListDataModel(vouchersList);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreate Account Model Detail">
    public void recreateAccountModelDetail() {
        System.err.println("fgggggggggggggggggggggggggggggggggggggggggggggggg");
        accountUseDetailDataModel = null;
        accountUseDetailDataModel = new ListDataModel(getVouchersDetailDataModel().getRowData().getFmsAccountUseList());
        System.out.println(accountUseDetailDataModel.getRowCount());
        System.out.println(getVouchersDetailDataModel().getRowData().getStatus());

    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="update Payment Requistion Detail">
    public void updatePaymentRequistionDetail() {
        // dataTableUpdateStatus = 1;
//        scmsPaymentRequisition = getPaymentReqDetailDataModel().getRowData();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="btnGenerate_Action">

    public String btnGenerate_Action() {
        vouchersList = fmsVoucherBeanLocal.handleSearchPost();
        recreateModelDetail();
        return null;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="update Account Use Detail">

    public void updateAccountUseDetail() {
        dataTableUpdateStatus = 1;
        accountUseEnty = getAccountUseDetailDataModel().getRowData();
        accountUseEnty.setSubsidaryId(getAccountUseDetailDataModel().getRowData().getSubsidaryId());
//        fmsLuSystem = accountUseEnty.getSubsidaryId().getCostCenterId().getSystemId();
//        fmsCostCenter = accountUseEnty.getSubsidaryId().getCostCenterId();
//                        fmsGeneralLedger = accountUseEnty.getSubsidaryId().getGeneralLedgerId();
//                        fmsSubsid1aryLedger1 = accountUseEnty.getSubsidaryId();
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
    
    public PostingControl() {
    }
    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">

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
    public FmsGeneralLedger getFmsGeneralLedger() {
        if (fmsGeneralLedger == null) {
            fmsGeneralLedger = new FmsGeneralLedger();
        }
        return fmsGeneralLedger;
    }

    /**
     *
     * @param fmsGeneralLedger
     */
    public void setFmsGeneralLedger(FmsGeneralLedger fmsGeneralLedger) {
        this.fmsGeneralLedger = fmsGeneralLedger;
    }

    /**
     *
     * @return
     */
    public FmsSubsidiaryLedger getFmsSubsid1aryLedger1() {
        if (fmsSubsid1aryLedger1 == null) {
            fmsSubsid1aryLedger1 = new FmsSubsidiaryLedger();
        }
        return fmsSubsid1aryLedger1;
    }

    /**
     *
     * @param fmsSubsid1aryLedger1
     */
    public void setFmsSubsid1aryLedger1(FmsSubsidiaryLedger fmsSubsid1aryLedger1) {
        this.fmsSubsid1aryLedger1 = fmsSubsid1aryLedger1;
    }

    /**
     *
     * @return
     */
    public FmsLuSystem getFmsLuSystem() {
        if (fmsLuSystem == null) {
            fmsLuSystem = new FmsLuSystem();
        }
        return fmsLuSystem;
    }

    /**
     *
     * @param fmsLuSystem
     */
    public void setFmsLuSystem(FmsLuSystem fmsLuSystem) {
        this.fmsLuSystem = new FmsLuSystem();
    }

    /**
     *
     * @return
     */
    public FmsCostCenter getFmsCostCenter() {
        if (fmsCostCenter == null) {
            fmsCostCenter = new FmsCostCenter();
        }
        return fmsCostCenter;
    }

    /**
     *
     * @param fmsCostCenter
     */
    public void setFmsCostCenter(FmsCostCenter fmsCostCenter) {
        this.fmsCostCenter = fmsCostCenter;
    }

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
    public FmsAccountUse getAccountUseEnty() {
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
    //</editor-fold> 
}
