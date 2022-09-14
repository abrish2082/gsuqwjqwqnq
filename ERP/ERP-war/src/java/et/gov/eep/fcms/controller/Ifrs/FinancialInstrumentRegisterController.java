/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.Ifrs;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.convert.NumberConverter;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.Bond.LuBondTypeBeanLocal;
import et.gov.eep.fcms.businessLogic.Ifrs.FmsLuFinancialAssetTypeBeanLocal;
import et.gov.eep.fcms.businessLogic.Ifrs.FinancialInstrumentDetailBeanLocal;
import et.gov.eep.fcms.businessLogic.Ifrs.FmsLuFinaInstrumentMeasureBeanLocal;
import et.gov.eep.fcms.businessLogic.Ifrs.FinancialInstrumentRegisterBeanLocal;
import et.gov.eep.fcms.businessLogic.Ifrs.FmsLuFinanInstrumentTypeBeanLocal;
import et.gov.eep.fcms.businessLogic.LuCurrencyBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.entity.Bond.FmsLuBondType;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.fcms.entity.Ifrs.FmsLuFinancialAssetType;
import et.gov.eep.fcms.entity.Ifrs.FinancialInstrumentDetail;
import et.gov.eep.fcms.entity.Ifrs.FmsLuFinaInstrumentMeasure;
import et.gov.eep.fcms.entity.Ifrs.FinancialInstrumentRegister;
import et.gov.eep.fcms.entity.Ifrs.FmsLuFinanInstrumentType;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.mapper.FmsLuBondTypeFacade;
import et.gov.eep.fcms.mapper.FmsLuCurrencyFacade;
import et.gov.eep.fcms.mapper.Ifrs.FmsLuFinancialAssetTypeFacade;
import et.gov.eep.fcms.mapper.Ifrs.FinancialInstrumentDetailFacade;
import et.gov.eep.fcms.mapper.Ifrs.FmsLuFinaInstrumentMeasureFacade;
import et.gov.eep.fcms.mapper.Ifrs.FinancialInstrumentRegisterFacade;
import et.gov.eep.fcms.mapper.Ifrs.FmsLuFinanInstrumentTypeFacade;
import et.gov.eep.fcms.mapper.admin.FmsGeneralLedgerFacade;
import et.gov.eep.fcms.mapper.admin.FmsSubsidiaryLedgerFacade;

/**
 *
 * @author mz
 */
@Named(value = "financialInstrumentRegisterController")
@ViewScoped
public class FinancialInstrumentRegisterController implements Serializable {
//<editor-fold defaultstate="collapsed" desc="Inject Entities and @ EJB">
    //Inject @EJB

    @EJB
    FinancialInstrumentRegisterBeanLocal financialInstrRegBeanLocal;
    @EJB
    FmsLuFinanInstrumentTypeBeanLocal finInstrumentTypeBeanLocal;
    @EJB
    FmsLuFinaInstrumentMeasureBeanLocal finInstMeasureBeanLocal;
    @EJB
    FmsLuFinancialAssetTypeBeanLocal financialAssetTypeBeanLocal;
    @EJB
    FmsLuFinanInstrumentTypeFacade finInstrumentTypeFacade;
    @EJB
    FinancialInstrumentRegisterFacade finInstRegFacade;
    @EJB
    FmsLuFinaInstrumentMeasureFacade finInstMeasureFacade;
    @EJB
    FmsLuFinancialAssetTypeFacade finAssetTypeFacade;
    @EJB
    FmsSubsidiaryLedgerFacade subsidiaryLedgerFacade;
    @EJB
    FmsGeneralLedgerFacade generalLedgerFacade;
    @EJB
    FmsGeneralLedgerBeanLocal generalLedgerBeanLocal;
    @EJB
    subsidiaryBeanLocal subsidiaryBeanLocal;
    @EJB
    FmsLuCurrencyFacade currencyFacade;
    @EJB
    LuCurrencyBeanLocal currencyBeanLocal;
    @EJB
    FinancialInstrumentDetailFacade financialDetailFacade;
    @EJB
    FinancialInstrumentDetailBeanLocal financialDetailBeanLocal;
    @EJB
    FmsLuBondTypeFacade bondTypeFacade;
    @EJB
    LuBondTypeBeanLocal bondTypeBeanLocal;
    //Inject entities
    @Inject
    FmsLuCurrency fmsLuCurrency;
    @Inject
    FinancialInstrumentRegister finInstrumentRegister;
    @Inject
    FmsLuFinancialAssetType finAssetType;
    @Inject
    FmsLuFinaInstrumentMeasure finInstMeasure;
    @Inject
    FmsLuFinanInstrumentType finInstType;
    @Inject
    FmsSubsidiaryLedger subsidiaryLedger;
    @Inject
    FmsGeneralLedger generalLedger;
    @Inject
    FinancialInstrumentDetail financialInstrumentDetail;
    @Inject
    FinancialInstrumentDetail financialInstrumentDetail2;
    @Inject
    FmsLuBondType luBondType;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    ColumnNameResolver columnNameResolver;
    List<ColumnNameResolver> ColumnNameResolverList = new ArrayList<>();
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variables declaration">
    //Declaring instance variables
    DataModel<FinancialInstrumentRegister> financialRegAddDataModel;
    DataModel<FinancialInstrumentDetail> financialDetailPLorOCIDModel;
    DataModel<FinancialInstrumentDetail> financialDetailPLDModel;
    DataModel<FinancialInstrumentDetail> financialDetailOCIDModel;
    DataModel<FinancialInstrumentDetail> financialDetailAmortisedDModel;
    DataModel<FinancialInstrumentDetail> instrumentDetailsDModel;
    DataModel<FmsGeneralLedger> generalLedgerDataModel;
    FinancialInstrumentDetail financialDetailSelect;
    private FinancialInstrumentRegister financialRegSelect;
    private List<FinancialInstrumentRegister> financialRegisterList;
    private List<FinancialInstrumentDetail> financialDetailList;
    List<FmsSubsidiaryLedger> subsideryCodeList;
    List<FmsGeneralLedger> generalLedgerList;
    List<FmsLuBondType> LubondTypeList;
    List<FmsLuFinanInstrumentType> finInstrumentList;
    List<FmsLuFinancialAssetType> assetTypeList;
    List<FmsLuFinaInstrumentMeasure> finInstMeasureList;
    List<FmsGeneralLedger> glList = null;
    String saveUpdate = "Save";
    String finInstrumentType;
    String finMeasurType;
    String status;
    Integer generalLedgerid;
    Date transactionDate;
    Double initialRecognition;
    Double FVTPL;
    Double FVTOCI;
    Double AmortizedCost;
    Double redemption;
    Double carryingAmountBeginning;
    Double carryingAmountEnding;
    Double interestRecived;
    Double cashRate;
    Double cashRecived;
    private boolean disableBtnCreate;
    private boolean addEnable = false;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    private boolean disableFVTPLvarpnl = false;
    private boolean disableFVTOCIvarpnl = false;
    private boolean disableAmortCostvarpnl = false;
    private String icone = "ui-icon-plus";
    private boolean disableDetTab = false;
    int updateStatus = 1;
    int financialDetailPopulate = 0;
    private NumberConverter numberConverter = new NumberConverter();
//</editor-fold>

    public FinancialInstrumentRegisterController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(2);
    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public String getSaveUpdate() {
        return saveUpdate;
    }

    public void setSaveUpdate(String saveUpdate) {
        this.saveUpdate = saveUpdate;
    }

    public FmsLuCurrency getFmsLuCurrency() {
        if (fmsLuCurrency == null) {
            fmsLuCurrency = new FmsLuCurrency();
        }
        return fmsLuCurrency;
    }

    public void setFmsLuCurrency(FmsLuCurrency fmsLuCurrency) {
        this.fmsLuCurrency = fmsLuCurrency;
    }

    public String getFinInstrumentType() {
        return finInstrumentType;
    }

    public void setFinInstrumentType(String finInstrumentType) {
        this.finInstrumentType = finInstrumentType;
    }

    public String getFinMeasurType() {
        return finMeasurType;
    }

    public void setFinMeasurType(String finMeasurType) {
        this.finMeasurType = finMeasurType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getGeneralLedgerid() {
        return generalLedgerid;
    }

    public void setGeneralLedgerid(Integer generalLedgerid) {
        this.generalLedgerid = generalLedgerid;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getInitialRecognition() {
        if (initialRecognition == null) {
            initialRecognition = new Double(0.0);
        }

        return initialRecognition;
    }

    public void setInitialRecognition(Double initialRecognition) {
        this.initialRecognition = initialRecognition;
    }

    public Double getFVTPL() {
        if (FVTPL == null) {
            FVTPL = new Double(0.0);
        }
        return FVTPL;
    }

    public void setFVTPL(Double FVTPL) {
        this.FVTPL = FVTPL;
    }

    public Double getFVTOCI() {
        return FVTOCI;
    }

    public void setFVTOCI(Double FVTOCI) {
        this.FVTOCI = FVTOCI;
    }

    public Double getAmortizedCost() {
        return AmortizedCost;
    }

    public void setAmortizedCost(Double AmortizedCost) {
        this.AmortizedCost = AmortizedCost;
    }

    public Double getRedemption() {
        return redemption;
    }

    public void setRedemption(Double redemption) {
        this.redemption = redemption;
    }

    public Double getCarryingAmountBeginning() {
        return carryingAmountBeginning;
    }

    public void setCarryingAmountBeginning(Double carryingAmountBeginning) {
        this.carryingAmountBeginning = carryingAmountBeginning;
    }

    public Double getCarryingAmountEnding() {
        return carryingAmountEnding;
    }

    public void setCarryingAmountEnding(Double carryingAmountEnding) {
        this.carryingAmountEnding = carryingAmountEnding;
    }

    public Double getInterestRecived() {
        return interestRecived;
    }

    public void setInterestRecived(Double interestRecived) {
        this.interestRecived = interestRecived;
    }

    public Double getCashRate() {
        return cashRate;
    }

    public void setCashRate(Double cashRate) {
        this.cashRate = cashRate;
    }

    public Double getCashRecived() {
        return cashRecived;
    }

    public void setCashRecived(Double cashRecived) {
        this.cashRecived = cashRecived;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isAddEnable() {
        return addEnable;
    }

    public void setAddEnable(boolean addEnable) {
        this.addEnable = addEnable;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }

    public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
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

    public boolean isDisableDetTab() {
        return disableDetTab;
    }

    public void setDisableDetTab(boolean disableDetTab) {
        this.disableDetTab = disableDetTab;
    }

    public boolean isDisableFVTPLvarpnl() {
        return disableFVTPLvarpnl;
    }

    public void setDisableFVTPLvarpnl(boolean disableFVTPLvarpnl) {
        this.disableFVTPLvarpnl = disableFVTPLvarpnl;
    }

    public boolean isDisableFVTOCIvarpnl() {
        return disableFVTOCIvarpnl;
    }

    public void setDisableFVTOCIvarpnl(boolean disableFVTOCIvarpnl) {
        this.disableFVTOCIvarpnl = disableFVTOCIvarpnl;
    }

    public boolean isDisableAmortCostvarpnl() {
        return disableAmortCostvarpnl;
    }

    public void setDisableAmortCostvarpnl(boolean disableAmortCostvarpnl) {
        this.disableAmortCostvarpnl = disableAmortCostvarpnl;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public int getfinancialDetailPopulate() {
        return financialDetailPopulate;
    }

    public void setfinancialDetailPopulate(int financialDetailPopulate) {
        this.financialDetailPopulate = financialDetailPopulate;
    }

    public FmsSubsidiaryLedger getSubsidiaryLedger() {
        if (subsidiaryLedger == null) {
            subsidiaryLedger = new FmsSubsidiaryLedger();
        }
        return subsidiaryLedger;
    }

    public void setSubsidiaryLedger(FmsSubsidiaryLedger subsidiaryLedger) {
        this.subsidiaryLedger = subsidiaryLedger;
    }

    public FmsGeneralLedger getGeneralLedger() {
        if (generalLedger == null) {
            generalLedger = new FmsGeneralLedger();
        }
        return generalLedger;
    }

    public void setGeneralLedger(FmsGeneralLedger generalLedger) {
        this.generalLedger = generalLedger;
    }

    public FinancialInstrumentDetail getFinancialInstrumentDetail() {
        if (financialInstrumentDetail == null) {
            financialInstrumentDetail = new FinancialInstrumentDetail();
        }
        return financialInstrumentDetail;
    }

    public void setFinancialInstrumentDetail(FinancialInstrumentDetail financialInstrumentDetail) {
        this.financialInstrumentDetail = financialInstrumentDetail;
    }

    public FinancialInstrumentDetail getFinancialInstrumentDetail2() {

        return financialInstrumentDetail2;
    }

    public void setFinancialInstrumentDetail2(FinancialInstrumentDetail financialInstrumentDetail2) {
        this.financialInstrumentDetail2 = financialInstrumentDetail2;
    }

    public FmsLuBondType getLuBondType() {
        if (luBondType == null) {
            luBondType = new FmsLuBondType();

        }
        return luBondType;
    }

    public void setLuBondType(FmsLuBondType luBondType) {
        this.luBondType = luBondType;
    }

    public List<FmsSubsidiaryLedger> getSubsideryCodeList() {
        return subsideryCodeList;
    }

    public void setSubsideryCodeList(List<FmsSubsidiaryLedger> subsideryCodeList) {
        this.subsideryCodeList = subsideryCodeList;
    }

    public List<FmsGeneralLedger> getGeneralLedgerList() {
        return generalLedgerList;
    }

    public void setGeneralLedgerList(List<FmsGeneralLedger> generalLedgerList) {
        this.generalLedgerList = generalLedgerList;
    }

    public List<FmsLuBondType> getLubondTypeList() {
        if (LubondTypeList == null) {
            LubondTypeList = new ArrayList<>();
            LubondTypeList = bondTypeBeanLocal.searchLuBondType();
        }
        return LubondTypeList;
    }

    public void setLubondTypeList(List<FmsLuBondType> LubondTypeList) {
        this.LubondTypeList = LubondTypeList;
    }

    public List<FmsLuFinanInstrumentType> getFinInstrumentList() {
        if (finInstrumentList == null) {
            finInstrumentList = new ArrayList<>();
        }
        return finInstrumentList;
    }

    public void setFinInstrumentList(List<FmsLuFinanInstrumentType> finInstrumentList) {
        this.finInstrumentList = finInstrumentList;
    }

    public List<FmsLuFinancialAssetType> getAssetTypeList() {
        if (assetTypeList == null) {
            assetTypeList = new ArrayList<>();
        }
        return assetTypeList;
    }

    public void setAssetTypeList(List<FmsLuFinancialAssetType> assetTypeList) {
        this.assetTypeList = assetTypeList;
    }

    public FinancialInstrumentRegisterBeanLocal getFinancialInstrRegBeanLocal() {
        return financialInstrRegBeanLocal;
    }

    public void setFinancialInstrRegBeanLocal(FinancialInstrumentRegisterBeanLocal financialInstrRegBeanLocal) {
        this.financialInstrRegBeanLocal = financialInstrRegBeanLocal;
    }

    public FmsLuFinanInstrumentTypeBeanLocal getFinInstrumentTypeBeanLocal() {
        return finInstrumentTypeBeanLocal;
    }

    public void setFinInstrumentTypeBeanLocal(FmsLuFinanInstrumentTypeBeanLocal finInstrumentTypeBeanLocal) {
        this.finInstrumentTypeBeanLocal = finInstrumentTypeBeanLocal;
    }

    public FmsLuFinaInstrumentMeasureBeanLocal getFinInstMeasureBeanLocal() {
        return finInstMeasureBeanLocal;
    }

    public void setFinInstMeasureBeanLocal(FmsLuFinaInstrumentMeasureBeanLocal finInstMeasureBeanLocal) {
        this.finInstMeasureBeanLocal = finInstMeasureBeanLocal;
    }

    public FmsLuFinancialAssetTypeBeanLocal getFmsLuFinancialAssetTypeBeanLocal() {
        return financialAssetTypeBeanLocal;
    }

    public void setFmsLuFinancialAssetTypeBeanLocal(FmsLuFinancialAssetTypeBeanLocal financialAssetTypeBeanLocal) {
        this.financialAssetTypeBeanLocal = financialAssetTypeBeanLocal;
    }

    public FmsLuFinanInstrumentTypeFacade getFinInstrumentTypeFacade() {
        return finInstrumentTypeFacade;
    }

    public void setFinInstrumentTypeFacade(FmsLuFinanInstrumentTypeFacade finInstrumentTypeFacade) {
        this.finInstrumentTypeFacade = finInstrumentTypeFacade;
    }

    public FinancialInstrumentRegisterFacade getFinInstRegFacade() {
        return finInstRegFacade;
    }

    public void setFinInstRegFacade(FinancialInstrumentRegisterFacade finInstRegFacade) {
        this.finInstRegFacade = finInstRegFacade;
    }

    public FmsLuFinaInstrumentMeasureFacade getFinInstMeasureFacade() {
        return finInstMeasureFacade;
    }

    public void setFinInstMeasureFacade(FmsLuFinaInstrumentMeasureFacade finInstMeasureFacade) {
        this.finInstMeasureFacade = finInstMeasureFacade;
    }

    public FmsLuFinancialAssetTypeFacade getFinAssetTypeFacade() {
        return finAssetTypeFacade;
    }

    public void setFinAssetTypeFacade(FmsLuFinancialAssetTypeFacade finAssetTypeFacade) {
        this.finAssetTypeFacade = finAssetTypeFacade;
    }

    public FinancialInstrumentRegister getFinInstrumentRegister() {
        if (finInstrumentRegister == null) {
            finInstrumentRegister = new FinancialInstrumentRegister();
        }
        return finInstrumentRegister;
    }

    public void setFinInstrumentRegister(FinancialInstrumentRegister finInstrumentRegister) {
        this.finInstrumentRegister = finInstrumentRegister;
    }

    public FmsLuFinancialAssetType getFinAssetType() {
        return finAssetType;
    }

    public void setFinAssetType(FmsLuFinancialAssetType finAssetType) {
        this.finAssetType = finAssetType;
    }

    public FmsLuFinaInstrumentMeasure getFinInstMeasure() {
        return finInstMeasure;
    }

    public void setFinInstMeasure(FmsLuFinaInstrumentMeasure finInstMeasure) {
        this.finInstMeasure = finInstMeasure;
    }

    public FmsLuFinanInstrumentType getFinInstType() {
        return finInstType;
    }

    public void setFinInstType(FmsLuFinanInstrumentType finInstType) {
        this.finInstType = finInstType;
    }

    public DataModel<FinancialInstrumentRegister> getFinancialRegAddDataModel() {
        if (financialRegAddDataModel == null) {
            financialRegAddDataModel = new ArrayDataModel<>();
        }
        return financialRegAddDataModel;
    }

    public void setFinancialRegAddDataModel(DataModel<FinancialInstrumentRegister> financialRegAddDataModel) {
        this.financialRegAddDataModel = financialRegAddDataModel;
    }

    public List<FinancialInstrumentRegister> getFinancialRegisterList() {
        if (financialRegisterList == null) {
            financialRegisterList = new ArrayList<>();
        }
        return financialRegisterList;
    }

    public void setFinancialRegisterList(List<FinancialInstrumentRegister> financialRegisterList) {
        this.financialRegisterList = financialRegisterList;
    }

    public List<FinancialInstrumentDetail> getFinancialDetailList() {
        if (financialDetailList == null) {
            financialDetailList = new ArrayList<>();
        }
        return financialDetailList;
    }

    public void setFinancialDetailList(List<FinancialInstrumentDetail> financialDetailList) {
        this.financialDetailList = financialDetailList;
    }

    public FinancialInstrumentRegister getFinancialRegSelect() {
        return financialRegSelect;
    }

    public void setFinancialRegSelect(FinancialInstrumentRegister financialRegSelect) {
        this.financialRegSelect = financialRegSelect;
    }

    public List<FmsLuFinaInstrumentMeasure> getFinInstMeasureList() {
        if (finInstMeasureList == null) {
            finInstMeasureList = new ArrayList<>();
        }
        return finInstMeasureList;
    }

    public void setFinInstMeasureList(List<FmsLuFinaInstrumentMeasure> finInstMeasureList) {
        this.finInstMeasureList = finInstMeasureList;
    }

    public DataModel<FinancialInstrumentDetail> getFinancialDetailPLorOCIDModel() {
        if (financialDetailPLorOCIDModel == null) {
            financialDetailPLorOCIDModel = new ListDataModel<>();
        }
        return financialDetailPLorOCIDModel;
    }

    public void setFinancialDetailPLorOCIDModel(DataModel<FinancialInstrumentDetail> financialDetailPLorOCIDModel) {
        this.financialDetailPLorOCIDModel = financialDetailPLorOCIDModel;
    }

    public DataModel<FinancialInstrumentDetail> getFinancialDetailPLDModel() {
        if (financialDetailPLDModel == null) {
            financialDetailPLDModel = new ListDataModel<>();
        }
        return financialDetailPLDModel;
    }

    public void setFinancialDetailPLDModel(DataModel<FinancialInstrumentDetail> financialDetailPLDModel) {
        this.financialDetailPLDModel = financialDetailPLDModel;
    }

    public DataModel<FinancialInstrumentDetail> getFinancialDetailOCIDModel() {
        if (financialDetailOCIDModel == null) {
            financialDetailOCIDModel = new ListDataModel<>();
        }
        return financialDetailOCIDModel;
    }

    public void setFinancialDetailOCIDModel(DataModel<FinancialInstrumentDetail> financialDetailOCIDModel) {
        this.financialDetailOCIDModel = financialDetailOCIDModel;
    }

    public DataModel<FinancialInstrumentDetail> getFinancialDetailAmortisedDModel() {
        if (financialDetailAmortisedDModel == null) {
            financialDetailAmortisedDModel = new ListDataModel<>();
        }
        return financialDetailAmortisedDModel;
    }

    public void setFinancialDetailAmortisedDModel(DataModel<FinancialInstrumentDetail> financialDetailAmortisedDModel) {
        this.financialDetailAmortisedDModel = financialDetailAmortisedDModel;
    }

    public DataModel<FinancialInstrumentDetail> getInstrumentDetailsDModel() {
        if (instrumentDetailsDModel == null) {
            instrumentDetailsDModel = new ListDataModel<>();
        }
        return instrumentDetailsDModel;
    }

    public void setInstrumentDetailsDModel(DataModel<FinancialInstrumentDetail> instrumentDetailsDModel) {
        this.instrumentDetailsDModel = instrumentDetailsDModel;
    }

    public DataModel<FmsGeneralLedger> getGeneralLedgerDataModel() {
        return generalLedgerDataModel;
    }

    public void setGeneralLedgerDataModel(DataModel<FmsGeneralLedger> generalLedgerDataModel) {
        this.generalLedgerDataModel = generalLedgerDataModel;
    }

    public FinancialInstrumentDetail getFinancialDetailSelect() {
        return financialDetailSelect;
    }

    public void setFinancialDetailSelect(FinancialInstrumentDetail financialDetailSelect) {
        this.financialDetailSelect = financialDetailSelect;
    }

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    public List<ColumnNameResolver> getColumnNameResolverList() {
        return ColumnNameResolverList = financialInstrRegBeanLocal.findColumns();
    }

    public void setColumnNameResolverList(List<ColumnNameResolver> ColumnNameResolverList) {
        this.ColumnNameResolverList = ColumnNameResolverList;
    }

    //</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc="recreate methods">
    // recreate method to assign value of FinancialRegisterList to financialRegAddDataModel
    public void recreateModelDetailPopup() {
        financialRegAddDataModel = null;
        financialRegAddDataModel = new ListDataModel(getFinancialRegisterList());
    }

    // recreate method to assign value of FinancialInstrumentDetailList to financialDetailPLorOCIDModel
    public void recreateFinancialDetailDataModel() {
        financialDetailPLorOCIDModel = null;
        financialDetailPLorOCIDModel = new ListDataModel(finInstrumentRegister.getFinancialInstrumentDetailList());
    }
// recreate method to assign value of FinancialInstrumentDetailList to financialDetailPLorOCIDModel

    public void recreateFinDetailModelPop() {
        financialDetailPLorOCIDModel = null;
        financialDetailPLorOCIDModel = new ListDataModel(finInstrumentRegister.getFinancialInstrumentDetailList());
    }
// recreate method to assign value of FinancialInstrumentDetailList to financialDetailPLDModel

    public void recreatFinPLDetailDataModel() {
        financialDetailPLDModel = null;
        financialDetailPLDModel = new ListDataModel(new ArrayList<>(finInstrumentRegister.getFinancialInstrumentDetailList()));
    }
// recreate method to assign value of FinancialInstrumentDetailList to financialDetailOCIDModel

    public void recreatFinOCIDetailDataModel() {
        financialDetailOCIDModel = null;
        financialDetailOCIDModel = new ListDataModel(new ArrayList(finInstrumentRegister.getFinancialInstrumentDetailList()));
    }
// recreate method to assign value of FinancialInstrumentDetailList to financialDetailAmortisedDModel

    public void recreatFinAMortiseDetailDataModel() {
        financialDetailAmortisedDModel = null;
        financialDetailAmortisedDModel = new ListDataModel(new ArrayList(finInstrumentRegister.getFinancialInstrumentDetailList()));;

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="select items">
//list for searching glList from generalLedgerBeanLocal
    public List<FmsGeneralLedger> getGlList() {
        glList = generalLedgerBeanLocal.getGeneralLedgerCodeList();
        return glList;
    }
//select items to find all list

    public SelectItem[] getCurrencyList() {
        return JsfUtil.getSelectItems(currencyFacade.findAll(), true);

    }

    public SelectItem[] getSubsidiaryLedgerList() {
        return JsfUtil.getSelectItems(subsidiaryLedgerFacade.findAll(), true);

    }

    public SelectItem[] getGeneralLedgerLists() {

        return JsfUtil.getSelectItems(generalLedgerFacade.findAll(), true);

    }

    public SelectItem[] getFinancialInstMeasurmentList() {
        return JsfUtil.getSelectItems(finInstMeasureFacade.findAll(), true);

    }

    public SelectItem[] getFinancialAssetTypeList() {
        return JsfUtil.getSelectItems(finAssetTypeFacade.findAll(), true);

    }

    public SelectItem[] getFinancialInstTypeList() {
        return JsfUtil.getSelectItems(finInstrumentTypeFacade.findAll(), true);

    }

    public SelectItem[] getBondTypeList() {
        return JsfUtil.getSelectItems(bondTypeFacade.findAll(), true);

    }
//</editor-fold>

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            columnNameResolver.setCol_Name_FromTable(event.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(event.getNewValue().toString().toLowerCase() + ":"));
        }
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        return col;
    }

    public List<FinancialInstrumentRegister> searchByFinancialInstColumn(String finanacialInstAtri) {
        finInstrumentRegister.setColumnValue(finanacialInstAtri);
        financialRegisterList = financialInstrRegBeanLocal.searchByCol_NameAndCol_Value(columnNameResolver, finInstrumentRegister.getColumnValue());
        return financialRegisterList;
    }

    public void getByFinancialInstColumn(SelectEvent event) {
        try {
            if (event.getObject() != null) {
                finInstrumentRegister = (FinancialInstrumentRegister) event.getObject();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //save method for IFRS

    public void saveFinancialInstrument() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveFinancialInstrument", dataset)) {
                if (updateStatus == 1) {
                    finInstrumentRegister.setGeneralLedger(generalLedger);
                    finInstrumentRegister.setFmsLuFinanInstrumentType(finInstType);
                    finInstrumentRegister.setFinancialMeasurement(finInstMeasure);
                    finInstrumentRegister.setFmsLuFinancialAssetType(finAssetType);
                    finInstrumentRegister.setCurrency(fmsLuCurrency);
                    financialInstrumentDetail.setBondType(finInstrumentRegister.getBondType());
                    financialInstrumentDetail.setInterestRate(finInstrumentRegister.getInterestRate());
                    financialInstrumentDetail.setIssuedDate(finInstrumentRegister.getIssuedDate());
                    financialInstrumentDetail.setInitialAmount(finInstrumentRegister.getCarryngAmount());
                    financialInstrumentDetail.setCarryingAmountBeginning(finInstrumentRegister.getCarryngAmount());
                    financialInstrumentDetail.setCarryingAmountEnding(0.0);
                    financialInstrumentDetail.setStatus(finInstrumentRegister.getAssetStatus());
                    finInstrumentRegister.getFinancialInstrumentDetailList().add(financialInstrumentDetail);
                    financialInstrRegBeanLocal.create(finInstrumentRegister);
                    JsfUtil.addSuccessMessage("Registared successfully");
                    saveUpdateClear();

                } else if (updateStatus == 0) {
                    finInstrumentRegister.setGeneralLedger(generalLedger);
                    finInstrumentRegister.setFmsLuFinanInstrumentType(finInstType);
                    finInstrumentRegister.setFinancialMeasurement(finInstMeasure);
                    finInstrumentRegister.setFmsLuFinancialAssetType(finAssetType);
                    finInstrumentRegister.setCurrency(fmsLuCurrency);
                    financialInstrumentDetail.setBondType(finInstrumentRegister.getBondType());
                    finInstrumentRegister.getFinancialInstrumentDetailList().add(financialInstrumentDetail);
                    financialInstrRegBeanLocal.edit(finInstrumentRegister);
                    JsfUtil.addSuccessMessage("updated successfully");
                    saveUpdateClear();
                    updateStatus = 1;
                    saveUpdate = "Save";
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
        } catch (Exception ex) {
        }
    }

    //add method to add FinancialInstrumentDetailList to financialInstrumentDetail
    public void addToFinancialDetail() {
        financialInstrumentDetail.setFinancialInstrumentId(finInstrumentRegister);
        finInstrumentRegister.getFinancialInstrumentDetailList().add(financialInstrumentDetail);
        recreateFinancialDetailDataModel();
        financialInstrumentDetail = new FinancialInstrumentDetail();
    }

    //save method fon financial detail
    public void saveFinanDetail() {
        financialInstrumentDetail.setFinancialInstrumentId(finInstrumentRegister);
        if (finMeasurType.equals("FVTPL")) {//FVTPL =Fair Value Through Profit Or Loss

            if (financialDetailPLDModel.getRowCount() > 0) {
                financialInstrumentDetail.setFairValueAtPL(FVTPL);
                financialInstrumentDetail.setInitialRecognition(initialRecognition);
                financialInstrumentDetail.setStatus(financialInstrumentDetail.getStatus());
                finInstrumentRegister.addToFinanceInstrumentDetail(financialInstrumentDetail);
                JsfUtil.addSuccessMessage("Transation Detail is Successfully Saved");
                saveUpdateClear();
            } else {
                JsfUtil.addFatalMessage("Please add Detail data before Save");
            }
        } else if (finMeasurType.equals("FVTOCI")) {//FVTOCI =Fair Value Through Other Comprehensive Income
            if (financialDetailOCIDModel.getRowCount() > 0) {
                financialInstrumentDetail.setFinancialInstrumentId(finInstrumentRegister);
                financialInstrumentDetail.setInitialRecognition(initialRecognition);
                financialInstrumentDetail.setFairValueAtOCI(FVTOCI);
                financialInstrumentDetail.setStatus(financialInstrumentDetail.getStatus());
                finInstrumentRegister.addToFinanceInstrumentDetail(financialInstrumentDetail);
                JsfUtil.addSuccessMessage("Transation Detail is Successfully Added");
                saveUpdateClear();
            } else {
                JsfUtil.addFatalMessage("Please add Detail data before Save");
            }
        } else if (finMeasurType.equals("Amortized Cost")) {//Amortized Cost
            if (financialDetailAmortisedDModel.getRowCount() > 0) {

                financialInstrumentDetail2.setFinancialInstrumentId(finInstrumentRegister);
                financialInstrumentDetail2.setCarryingAmountBeginning(carryingAmountBeginning);
                financialInstrumentDetail2.setRedemption(redemption);
                financialInstrumentDetail2.setCashReceived(cashRecived);
                financialInstrumentDetail2.setTransactionDate(transactionDate);
                financialInstrumentDetail2.setCashflow(cashRate);
                financialInstrumentDetail2.setInterestReceived(interestRecived);
                financialInstrumentDetail2.setCarryingAmountEnding(carryingAmountEnding);
                finInstrumentRegister.getFinancialInstrumentDetailList().add(financialInstrumentDetail2);
                JsfUtil.addSuccessMessage("Transation Detail is Successfully Added");
                saveUpdateClear();
            } else {
                JsfUtil.addFatalMessage("Please add Detail data before Save");
            }
        } else {
            JsfUtil.addFatalMessage("Pls Add Detail Data Before Saving");
        }

    }

    //save method fon financial detail2
    public void saveFinanDetail1() {
        if ((financialDetailPLDModel.getRowCount() > 0 || financialDetailOCIDModel.getRowCount() > 0)
                && (financialDetailOCIDModel.getRowCount() > 0 || financialDetailAmortisedDModel.getRowCount() > 0)) {
            financialInstrumentDetail.setFinancialInstrumentId(finInstrumentRegister);
            finInstrumentRegister.addToFinanceInstrumentDetail(financialInstrumentDetail);
            JsfUtil.addSuccessMessage("Transation Detail is Successfully Added");
        } else {
            JsfUtil.addFatalMessage("Pls Add Detail Data Before Saving");
        }

    }

    //add method to add finInstrumentRegister to addToProfitORLossDetail
    public void addToProfitORLossDetail() {
        financialInstrumentDetail.setFinancialInstrumentId(finInstrumentRegister);
    }

    //add method to add finInstrumentRegister to addToOCIDetail
    public void addToOCIDetail() {
        financialInstrumentDetail.setFinancialInstrumentId(finInstrumentRegister);
        recreatFinOCIDetailDataModel();
    }

//add method to add finInstrumentRegister to addToAmortizedDetail
    public void addToAmortizedDetail() {
        financialInstrumentDetail.setFinancialInstrumentId(finInstrumentRegister);
        recreatFinAMortiseDetailDataModel();
    }

    //save mathod for profit or loss detail
    public void savePLDetail() {
        if (financialDetailPLDModel.getRowCount() > 0) {
            financialInstrumentDetail.setFinancialInstrumentId(finInstrumentRegister);
            financialInstrumentDetail.setInitialAmount(finInstrumentRegister.getCarryngAmount());
            financialInstrumentDetail.setBondType(finInstrumentRegister.getBondType());
            finInstrumentRegister.addToFinanceInstrumentDetail(financialInstrumentDetail);
            JsfUtil.addSuccessMessage("Transation Detail PL is Successfully Saved");
        } else {
            JsfUtil.addFatalMessage("Pls Add Detail Data Before Saving");
        }

    }

//save mathod for OCIDetail detail
    public void saveOCIDetail() {
        if (financialDetailOCIDModel.getRowCount() > 0) {
            financialInstrumentDetail.setFinancialInstrumentId(finInstrumentRegister);
            financialInstrumentDetail.setInitialAmount(finInstrumentRegister.getCarryngAmount());
            financialInstrumentDetail.setBondType(finInstrumentRegister.getBondType());
            finInstrumentRegister.addToFinanceInstrumentDetail(financialInstrumentDetail);
            financialInstrRegBeanLocal.edit(finInstrumentRegister);
            JsfUtil.addSuccessMessage("Transation is Successfully Saved");
            saveUpdateClear();
        } else {
            JsfUtil.addFatalMessage("Pls Add Detail Data Before Saving");
        }

    }

    //save mathod for Amoritised Detail
    public void saveAmoritisedDetail() {
        if (financialDetailAmortisedDModel.getRowCount() > 0) {
            financialInstrumentDetail.setFinancialInstrumentId(finInstrumentRegister);
            financialInstrumentDetail.setInitialAmount(finInstrumentRegister.getCarryngAmount());
            financialInstrumentDetail.setBondType(finInstrumentRegister.getBondType());
            finInstrumentRegister.addToFinanceInstrumentDetail(financialInstrumentDetail);
            financialInstrRegBeanLocal.edit(finInstrumentRegister);
            JsfUtil.addSuccessMessage("Transation is Successfully Saved");
            saveUpdateClear();
        } else {
            JsfUtil.addFatalMessage("Pls Add Detail Data Before Saving");
        }

    }

    //search all method from financialInstrRegBeanLocal
    public void searchAll() {
        if (finInstrumentRegister.getSubsidiaryLedger() == null) {
            financialRegisterList = financialInstrRegBeanLocal.searchAll();
        }
        recreateModelDetailPopup();
    }

//select event for financialDetailPopulate
    public void financialDetailPopulate(SelectEvent event) {
        financialInstrumentDetail = (FinancialInstrumentDetail) event.getObject();
        financialDetailPopulate = 1;
    }

    // select event for register number to assign render values
    public void getRegNo(SelectEvent event) {
        finInstrumentRegister = (FinancialInstrumentRegister) event.getObject();
        glList = generalLedgerBeanLocal.getGeneralLedgerCodeList();
        generalLedger = finInstrumentRegister.getGeneralLedger();
        fmsLuCurrency = finInstrumentRegister.getCurrency();
        generalLedger = generalLedgerBeanLocal.findByMasterId(generalLedger.getGeneralLedgerId());
        finInstType = finInstrumentRegister.getFmsLuFinanInstrumentType();
        subsideryCodeList = subsidiaryBeanLocal.findSubsideryCodeByGlCode(generalLedger);
        subsidiaryLedger = finInstrumentRegister.getSubsidiaryLedger();
        finInstMeasure = finInstrumentRegister.getFinancialMeasurement();
        financialInstrumentDetail = financialDetailBeanLocal.getFinancialInstRegDetInfo(finInstrumentRegister);
        finAssetType = finInstrumentRegister.getFmsLuFinancialAssetType();
        if (finInstrumentRegister.getFmsLuFinanInstrumentType().getName().equals("Asset")) {
            finInstMeasureList = finInstMeasureBeanLocal.searchall();

            finMeasurType = finInstrumentRegister.getFinancialMeasurement().getName();
            if (finMeasurType.equals("FVTPL")) {
                disableFVTPLvarpnl = true;
                disableFVTOCIvarpnl = false;
                disableAmortCostvarpnl = false;
            } else if (finMeasurType.equals("FVTOCI")) {
                disableFVTOCIvarpnl = true;
                disableFVTPLvarpnl = false;
                disableAmortCostvarpnl = false;
            } else if (finMeasurType.equals("Amortized Cost")) {
                disableAmortCostvarpnl = true;
                disableFVTOCIvarpnl = false;
                disableFVTPLvarpnl = false;
                financialDetailList = finInstrumentRegister.getFinancialInstrumentDetailList();
            }

        } else if (finInstrumentRegister.getFmsLuFinanInstrumentType().getName().equals("Liability")) {
            finInstMeasureList = finInstMeasureBeanLocal.defualtAndPL();
            finMeasurType = finInstrumentRegister.getFinancialMeasurement().getName();
            if (finMeasurType.equals("FVTPL")) {
                disableFVTPLvarpnl = true;
                disableFVTOCIvarpnl = false;
                disableAmortCostvarpnl = false;
            } else if (finMeasurType.equals("Amortized Cost")) {
                disableAmortCostvarpnl = true;
                disableFVTOCIvarpnl = false;
                disableFVTPLvarpnl = false;
            }

        }
        renderPnlCreateAdditional = true;
        renderPnlManPage = false;
        addEnable = true;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        saveUpdate = "Update";
        updateStatus = 0;
    }

    //value change event for currency setting
    public void handleSelectCurrency(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            fmsLuCurrency = (FmsLuCurrency) event.getNewValue();
            finInstrumentRegister.setCurrency(fmsLuCurrency);
        }
    }

    //value change event for General ledger and subsidery ledger
    public SelectItem[] handleSelectGlCode(ValueChangeEvent event) {
        int id = Integer.parseInt(event.getNewValue().toString());
        if (event.getNewValue() != null) {
            generalLedger = null;
            generalLedger = generalLedgerBeanLocal.findByMasterId(id);
            finInstrumentRegister.setGeneralLedger(generalLedger);
            subsideryCodeList = subsidiaryBeanLocal.findSubsideryCodeByGlCode(generalLedger);
            return Utility.JsfUtil.getSelectItems(getSubsideryCodeList(), true);
        } else {
            return Utility.JsfUtil.getSelectItems(null, true);
        }
    }

    //value change event for finInstMeasureList
    public void handleMeasurementType(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            finInstrumentType = event.getNewValue().toString();
            if (finInstrumentType.equals("Asset")) {
                finInstMeasureList = finInstMeasureBeanLocal.searchall();

            } else if (finInstrumentType.equals("Liability")) {
                finInstMeasureList = finInstMeasureBeanLocal.defualtAndPL();

            }
        }

    }

    //value change event for assigning render values
    public void addMeasurmentType(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            finMeasurType = event.getNewValue().toString();
            if (finMeasurType.equals("FVTPL")) {
                disableFVTPLvarpnl = true;
                disableFVTOCIvarpnl = false;
                disableAmortCostvarpnl = false;
            } else if (finMeasurType.equals("FVTOCI")) {
                disableFVTOCIvarpnl = true;
                disableFVTPLvarpnl = false;
                disableAmortCostvarpnl = false;
            } else if (finMeasurType.equals("Amortized Cost")) {
                disableAmortCostvarpnl = true;
                disableFVTOCIvarpnl = false;
                disableFVTPLvarpnl = false;
            }

        }
    }

    //calculate transaction method for IFRS
    public void calculateTransaction() {
        finMeasurType = finInstrumentRegister.getFinancialMeasurement().getName();
        if (finMeasurType.equals("FVTPL")) {
            financialInstrumentDetail.setFinancialInstrumentId(finInstrumentRegister);
            if (financialInstrumentDetail.getTransactionCost() != null) {
                initialRecognition = new Double(0.0);
                initialRecognition = finInstrumentRegister.getCarryngAmount() * financialInstrumentDetail.getSingleCost() * financialInstrumentDetail.getTransactionCost();
            } else {

                initialRecognition = finInstrumentRegister.getCarryngAmount() * financialInstrumentDetail.getSingleCost();
            }
            FVTPL = financialInstrumentDetail.getFairValue() - initialRecognition;
            transactionDate = financialInstrumentDetail.getTransactionDate();
            status = financialInstrumentDetail.getStatus();
            financialInstrumentDetail.setFairValueAtPL(FVTPL);
            financialInstrumentDetail.setInitialRecognition(initialRecognition);
            financialInstrumentDetail.setTransactionDate(transactionDate);
            financialInstrumentDetail.setStatus(status);
            finInstrumentRegister.getFinancialInstrumentDetailList().add(financialInstrumentDetail);
            recreatFinPLDetailDataModel();
            financialInstrumentDetail = new FinancialInstrumentDetail();

        } else if (finMeasurType.equals("FVTOCI")) {
            financialInstrumentDetail.setFinancialInstrumentId(finInstrumentRegister);
            initialRecognition = finInstrumentRegister.getCarryngAmount() + financialInstrumentDetail.getTransactionCost();
            FVTOCI = financialInstrumentDetail.getFairValue() - initialRecognition;
            financialInstrumentDetail.setFairValueAtOCI(FVTOCI);
            financialInstrumentDetail.setInitialRecognition(initialRecognition);
            transactionDate = financialInstrumentDetail.getTransactionDate();
            status = financialInstrumentDetail.getStatus();
            financialInstrumentDetail.setTransactionDate(transactionDate);
            financialInstrumentDetail.setStatus(status);
            finInstrumentRegister.getFinancialInstrumentDetailList().add(financialInstrumentDetail);
            recreatFinOCIDetailDataModel();
            financialInstrumentDetail = new FinancialInstrumentDetail();

        } else if (finMeasurType.equals("Amortized Cost")) {
            financialDetailList = new ArrayList<>();
            Calendar c = Calendar.getInstance();
            c.setTime(financialInstrumentDetail.getTransactionDate());
            financialInstrumentDetail.setFinancialInstrumentId(finInstrumentRegister);
            int maturity = 0;
            if (financialInstrumentDetail.getBondType().equals("Annual")) {
                maturity = financialInstrumentDetail.getMaturity();
                for (int yearCount = 0; yearCount < maturity; yearCount++) {
                    financialInstrumentDetail2 = new FinancialInstrumentDetail();
                    if (yearCount == 0) {
                        carryingAmountBeginning = financialInstrumentDetail.getSingleCost() * financialInstrumentDetail.getInitialAmount();
                        redemption = carryingAmountBeginning * financialInstrumentDetail.getPremiumRate();
                        transactionDate = financialInstrumentDetail.getTransactionDate();
                        cashRate = carryingAmountBeginning * financialInstrumentDetail.getInterestRate();
                        interestRecived = carryingAmountBeginning * financialInstrumentDetail.getEffectiveInterest();
                        carryingAmountEnding = (carryingAmountBeginning + interestRecived) - cashRate;
                        cashRecived = carryingAmountBeginning * financialInstrumentDetail.getInterestRate() * maturity;

                    } else {
                        carryingAmountBeginning = financialDetailList.get(yearCount - 1).getCarryingAmountEnding();
                        cashRecived = carryingAmountBeginning * financialInstrumentDetail.getInterestRate() * maturity;
                        cashRate = carryingAmountBeginning * financialInstrumentDetail.getInterestRate();
                        interestRecived = carryingAmountBeginning * financialInstrumentDetail.getEffectiveInterest();
                        carryingAmountEnding = (carryingAmountBeginning + interestRecived) - cashRate;
                        c.add(Calendar.YEAR, financialInstrumentDetail.getMaturity());
                        transactionDate = c.getTime();
                    }
                    financialInstrumentDetail2.setStatus(financialInstrumentDetail.getStatus());
                    financialInstrumentDetail2.setIssuedDate(financialInstrumentDetail.getIssuedDate());
                    financialInstrumentDetail2.setCarryingAmountBeginning(carryingAmountBeginning);
                    financialInstrumentDetail2.setRedemption(redemption);
                    financialInstrumentDetail2.setCashReceived(cashRecived);
                    financialInstrumentDetail2.setTransactionDate(transactionDate);
                    financialInstrumentDetail2.setCashflow(cashRate);
                    financialInstrumentDetail2.setInterestReceived(interestRecived);
                    financialInstrumentDetail2.setCarryingAmountEnding(carryingAmountEnding);
                    financialDetailList.add(financialInstrumentDetail2);
                    finInstrumentRegister.setFinancialInstrumentDetailList(financialDetailList);
                }
                recreatFinAMortiseDetailDataModel();
            } else if (financialInstrumentDetail.getBondType().equals("Semi Annual")) {
                maturity = financialInstrumentDetail.getMaturity() * 2;
                for (int yearCount = 0; yearCount < maturity; yearCount++) {
                    financialInstrumentDetail2 = new FinancialInstrumentDetail();
                    if (yearCount == 0) {
                        carryingAmountBeginning = financialInstrumentDetail.getSingleCost() * financialInstrumentDetail.getInitialAmount();
                        redemption = carryingAmountBeginning * financialInstrumentDetail.getPremiumRate();
                        transactionDate = financialInstrumentDetail.getTransactionDate();
                        cashRate = carryingAmountBeginning * financialInstrumentDetail.getInterestRate();
                        interestRecived = carryingAmountBeginning * financialInstrumentDetail.getEffectiveInterest();
                        carryingAmountEnding = (carryingAmountBeginning + interestRecived) - cashRate;
                        cashRecived = carryingAmountBeginning * financialInstrumentDetail.getInterestRate() * maturity;
                    } else {
                        carryingAmountBeginning = financialDetailList.get(yearCount - 1).getCarryingAmountEnding();
                        cashRecived = carryingAmountBeginning * financialInstrumentDetail.getInterestRate() * maturity;
                        cashRate = carryingAmountBeginning * financialInstrumentDetail.getInterestRate();
                        interestRecived = carryingAmountBeginning * financialInstrumentDetail.getEffectiveInterest();
                        carryingAmountEnding = (carryingAmountBeginning + interestRecived) - cashRate;
                        c.add(Calendar.YEAR, financialInstrumentDetail.getMaturity());
                        transactionDate = c.getTime();
                        c.setTime(transactionDate);
                        c.add(Calendar.MONTH, 6);
                        transactionDate = c.getTime();
                    }
                    financialInstrumentDetail2.setStatus(financialInstrumentDetail.getStatus());
                    financialInstrumentDetail2.setIssuedDate(financialInstrumentDetail.getIssuedDate());
                    financialInstrumentDetail2.setCarryingAmountBeginning(carryingAmountBeginning);
                    financialInstrumentDetail2.setRedemption(redemption);
                    financialInstrumentDetail2.setCashReceived(cashRecived);
                    financialInstrumentDetail2.setTransactionDate(transactionDate);
                    financialInstrumentDetail2.setCashflow(cashRate);
                    financialInstrumentDetail2.setInterestReceived(interestRecived);
                    financialInstrumentDetail2.setCarryingAmountEnding(carryingAmountEnding);
                    financialDetailList.add(financialInstrumentDetail2);
                    finInstrumentRegister.setFinancialInstrumentDetailList(financialDetailList);
                }
                recreatFinAMortiseDetailDataModel();

            } else if (financialInstrumentDetail.getBondType().equals("Quarter Annual")) {
                maturity = financialInstrumentDetail.getMaturity() * 4;
                for (int yearCount = 0; yearCount < maturity; yearCount++) {
                    financialInstrumentDetail2 = new FinancialInstrumentDetail();
                    if (yearCount == 0) {
                        carryingAmountBeginning = financialInstrumentDetail.getSingleCost() * financialInstrumentDetail.getInitialAmount();
                        redemption = carryingAmountBeginning * financialInstrumentDetail.getPremiumRate();
                        transactionDate = financialInstrumentDetail.getTransactionDate();
                        cashRate = carryingAmountBeginning * financialInstrumentDetail.getInterestRate();
                        interestRecived = carryingAmountBeginning * financialInstrumentDetail.getEffectiveInterest();
                        carryingAmountEnding = (carryingAmountBeginning + interestRecived) - cashRate;
                        cashRecived = carryingAmountBeginning * financialInstrumentDetail.getInterestRate() * maturity;
                    } else {
                        carryingAmountBeginning = financialDetailList.get(yearCount - 1).getCarryingAmountEnding();
                        cashRecived = carryingAmountBeginning * financialInstrumentDetail.getInterestRate() * maturity;
                        cashRate = carryingAmountBeginning * financialInstrumentDetail.getInterestRate();
                        interestRecived = carryingAmountBeginning * financialInstrumentDetail.getEffectiveInterest();
                        carryingAmountEnding = (carryingAmountBeginning + interestRecived) - cashRate;
                        transactionDate = c.getTime();
                        c.setTime(transactionDate);
                        c.add(Calendar.MONTH, 3);
                        transactionDate = c.getTime();
                    }
                    financialInstrumentDetail2.setStatus(financialInstrumentDetail.getStatus());
                    financialInstrumentDetail2.setIssuedDate(financialInstrumentDetail.getIssuedDate());
                    financialInstrumentDetail2.setCarryingAmountBeginning(carryingAmountBeginning);
                    financialInstrumentDetail2.setRedemption(redemption);
                    financialInstrumentDetail2.setCashReceived(cashRecived);
                    financialInstrumentDetail2.setTransactionDate(transactionDate);
                    financialInstrumentDetail2.setCashflow(cashRate);
                    financialInstrumentDetail2.setInterestReceived(interestRecived);
                    financialInstrumentDetail2.setCarryingAmountEnding(carryingAmountEnding);
                    financialDetailList.add(financialInstrumentDetail2);
                    finInstrumentRegister.setFinancialInstrumentDetailList(financialDetailList);
                }
                recreatFinAMortiseDetailDataModel();
            } else if (financialInstrumentDetail.getBondType().equals("Monthly")) {
                maturity = financialInstrumentDetail.getMaturity() * 12;
                for (int yearCount = 0; yearCount < maturity; yearCount++) {
                    financialInstrumentDetail2 = new FinancialInstrumentDetail();
                    if (yearCount == 0) {
                        carryingAmountBeginning = financialInstrumentDetail.getSingleCost() * financialInstrumentDetail.getInitialAmount();
                        redemption = carryingAmountBeginning * financialInstrumentDetail.getPremiumRate();
                        transactionDate = financialInstrumentDetail.getTransactionDate();
                        cashRate = carryingAmountBeginning * financialInstrumentDetail.getInterestRate();
                        interestRecived = carryingAmountBeginning * financialInstrumentDetail.getEffectiveInterest();
                        carryingAmountEnding = (carryingAmountBeginning + interestRecived) - cashRate;
                        cashRecived = carryingAmountBeginning * financialInstrumentDetail.getInterestRate() * maturity;
                    } else {
                        carryingAmountBeginning = financialDetailList.get(yearCount - 1).getCarryingAmountEnding();
                        cashRecived = carryingAmountBeginning * financialInstrumentDetail.getInterestRate() * maturity;
                        cashRate = carryingAmountBeginning * financialInstrumentDetail.getInterestRate();
                        interestRecived = carryingAmountBeginning * financialInstrumentDetail.getEffectiveInterest();
                        carryingAmountEnding = (carryingAmountBeginning + interestRecived) - cashRate;
                        c.add(Calendar.YEAR, financialInstrumentDetail.getMaturity());
                        transactionDate = c.getTime();
                        c.setTime(transactionDate);
                        c.add(Calendar.MONTH, 1);
                        transactionDate = c.getTime();
                    }
                    financialInstrumentDetail2.setStatus(financialInstrumentDetail.getStatus());
                    financialInstrumentDetail2.setIssuedDate(financialInstrumentDetail.getIssuedDate());
                    financialInstrumentDetail2.setCarryingAmountBeginning(carryingAmountBeginning);
                    financialInstrumentDetail2.setRedemption(redemption);
                    financialInstrumentDetail2.setCashReceived(cashRecived);
                    financialInstrumentDetail2.setTransactionDate(transactionDate);
                    financialInstrumentDetail2.setCashflow(cashRate);
                    financialInstrumentDetail2.setInterestReceived(interestRecived);
                    financialInstrumentDetail2.setCarryingAmountEnding(carryingAmountEnding);
                    financialDetailList.add(financialInstrumentDetail2);
                    finInstrumentRegister.setFinancialInstrumentDetailList(financialDetailList);
                }
                recreatFinAMortiseDetailDataModel();

            }

        }

        clearPopUp();
    }

    //clear method
    public void clearPopUp() {
        financialInstrumentDetail = new FinancialInstrumentDetail();
        finInstrumentRegister = new FinancialInstrumentRegister();
    }

    //save update clear
    private void saveUpdateClear() {
        financialInstrumentDetail = new FinancialInstrumentDetail();
        finInstrumentRegister = new FinancialInstrumentRegister();
        generalLedger = new FmsGeneralLedger();
        subsidiaryLedger = new FmsSubsidiaryLedger();
        subsideryCodeList = new ArrayList<>();
        generalLedgerList = new ArrayList<>();
        finInstMeasureList = new ArrayList<>();
        financialRegAddDataModel = new ListDataModel<>();
        financialDetailPLorOCIDModel = new ListDataModel<>();
        financialDetailAmortisedDModel = new ListDataModel<>();
        financialDetailPLDModel = new ListDataModel<>();
        financialDetailOCIDModel = new ListDataModel<>();
        fmsLuCurrency = new FmsLuCurrency();
        finAssetType = new FmsLuFinancialAssetType();
        finInstType = new FmsLuFinanInstrumentType();
        glList = new ArrayList<>();
        generalLedgerid = null;
        saveUpdate = "Save";
    }

    //create and search render method
    public void createNewAdditionalAmount() {
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            disableDetTab = true;
            disableBtnCreate = true;
            addEnable = true;
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            disableDetTab = false;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
        saveUpdateClear();
    }

}
