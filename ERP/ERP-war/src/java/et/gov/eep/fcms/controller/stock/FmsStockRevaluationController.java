/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.stock;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.stock.FmsStockItemInitialBalanceBeanLocal;
import et.gov.eep.fcms.businessLogic.stock.FmsStockLedgerCardBeanLocal;
import et.gov.eep.fcms.businessLogic.stock.FmsStockRevaluationHistoryBeanLocal;
import et.gov.eep.fcms.entity.stock.FmsStockLedgerCard;
import et.gov.eep.fcms.entity.stock.FmsStockRevaluationHistory;
import et.gov.eep.mms.businessLogic.MmsBinCardBeanLocal;
import et.gov.eep.mms.entity.MmsBinCard;
import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsGrnDetail;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.integration.MmsItemRegistrationIntegrationBeanLocal;
import et.gov.eep.mms.integration.MmsStoreInformationIntegrationBeanLocal;

/**
 *
 * @author memube
 */
@Named(value = "fmsStockRevaluationController")
@ViewScoped
public class FmsStockRevaluationController implements Serializable {

  //<editor-fold defaultstate="collapsed" desc="@Inject and @EJB">
    //Inject @EJB
    @EJB
    private FmsStockItemInitialBalanceBeanLocal initialBalanceBeanLocal;
    @EJB
    private MmsStoreInformationIntegrationBeanLocal storeInformationIntegrationBeanLocal;
    @EJB
    private MmsItemRegistrationIntegrationBeanLocal itemRegistrationIntegrationBeanLocal;
    @EJB
    private FmsStockLedgerCardBeanLocal fmsStockLedgerCardBeanLocal;
    @EJB
    private FmsStockRevaluationHistoryBeanLocal fmsStockRevaluationHistoryBeanLocal;
    @EJB
    private MmsBinCardBeanLocal mmsBinCardBeanLocal;
//Inject entities
    @Inject
    private MmsStoreInformation mmsStoreInformation;
    @Inject
    private MmsBinCard mmsBinCard;
    @Inject
    private MmsGrn mmsGrn;
    @Inject
    private MmsGrnDetail mmsGrnDetail;
    @Inject
    private MmsItemRegistration mmsItemRegistration;
    @Inject
    private FmsStockRevaluationHistory fmsStockRevaluationHistory;
    @Inject
    FmsStockLedgerCard fmsStockLedgerCard;
    @Inject
    SessionBean SessionBean;
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    List<FmsStockLedgerCard> stockLedgerCardList;
    private List<MmsItemRegistration> mmsMaterialModel;
    private List<FmsStockLedgerCard> fmsStockLedgerCardList;
    private List<FmsStockLedgerCard> selectedfmsSLC;
    private DataModel<FmsStockLedgerCard> fmsStockLedgerCardModel;
    private String icone = "ui-icon-plus";
    private String saveorUpdateBundle = "Save";
    private String createOrSearchBundle = "New";
    private boolean disableBtnCreate;
    private boolean renderChkBox = false;
    private boolean renderPnlCreateStockItem = false;
    private boolean initialStockPriceReadonly = false;
    private boolean renderPnlManPage = true;
    int updateStatus = 0;
    int materialId;
    BigDecimal stockValue = new BigDecimal(0.00);
    private NumberConverter numberConverter = new NumberConverter();
//</editor-fold>
    public FmsStockRevaluationController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(3);
    }
//<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">

    public BigDecimal getStockValue() {
        return stockValue;
    }

    public void setStockValue(BigDecimal stockValue) {
        this.stockValue = stockValue;
    }

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public MmsGrnDetail getMmsGrnDetail() {
        if (mmsGrnDetail == null) {
            mmsGrnDetail = new MmsGrnDetail();
        }
        return mmsGrnDetail;
    }

    public void setMmsGrnDetail(MmsGrnDetail mmsGrnDetail) {
        this.mmsGrnDetail = mmsGrnDetail;
    }

    public MmsGrn getMmsGrn() {
        if (mmsGrn == null) {
            mmsGrn = new MmsGrn();
        }
        return mmsGrn;
    }

    public void setMmsGrn(MmsGrn mmsGrn) {
        this.mmsGrn = mmsGrn;
    }

    public MmsBinCard getMmsBinCard() {
        if (mmsBinCard == null) {
            mmsBinCard = new MmsBinCard();
        }
        return mmsBinCard;
    }

    public void setMmsBinCard(MmsBinCard mmsBinCard) {
        this.mmsBinCard = mmsBinCard;
    }

    public MmsStoreInformation getMmsStoreInformation() {
        if (mmsStoreInformation == null) {
            mmsStoreInformation = new MmsStoreInformation();
        }
        return mmsStoreInformation;
    }

    public void setMmsStoreInformation(MmsStoreInformation mmsStoreInformation) {
        this.mmsStoreInformation = mmsStoreInformation;
    }

    public MmsItemRegistration getMmsItemRegistration() {
        if (mmsItemRegistration == null) {
            mmsItemRegistration = new MmsItemRegistration();
        }
        return mmsItemRegistration;
    }

    public void setMmsItemRegistration(MmsItemRegistration mmsItemRegistration) {
        this.mmsItemRegistration = mmsItemRegistration;
    }

    public FmsStockRevaluationHistory getFmsStockRevaluationHistory() {
        if (fmsStockRevaluationHistory == null) {
            fmsStockRevaluationHistory = new FmsStockRevaluationHistory();
        }
        return fmsStockRevaluationHistory;
    }

    public void setFmsStockRevaluationHistory(FmsStockRevaluationHistory fmsStockRevaluationHistory) {
        this.fmsStockRevaluationHistory = fmsStockRevaluationHistory;
    }

    public FmsStockLedgerCard getFmsStockLedgerCard() {
        if (fmsStockLedgerCard == null) {
            fmsStockLedgerCard = new FmsStockLedgerCard();
        }
        return fmsStockLedgerCard;
    }

    public void setFmsStockLedgerCard(FmsStockLedgerCard fmsStockLedgerCard) {
        this.fmsStockLedgerCard = fmsStockLedgerCard;
    }

    public List<MmsItemRegistration> getMmsMaterialModel() {
        if (mmsMaterialModel == null) {
            mmsMaterialModel = new ArrayList<>();
        }
        return mmsMaterialModel;
    }

    public void setMmsMaterialModel(List<MmsItemRegistration> mmsMaterialModel) {
        this.mmsMaterialModel = mmsMaterialModel;
    }

    public DataModel<FmsStockLedgerCard> getFmsStockLedgerCardModel() {
        if (fmsStockLedgerCardModel == null) {
            fmsStockLedgerCardModel = new ListDataModel<>();
        }
        return fmsStockLedgerCardModel;
    }

    public void setFmsStockLedgerCardModel(DataModel<FmsStockLedgerCard> fmsStockLedgerCardModel) {
        this.fmsStockLedgerCardModel = fmsStockLedgerCardModel;
    }

    public List<FmsStockLedgerCard> getFmsStockLedgerCardList() {
        if (fmsStockLedgerCardList == null) {
            fmsStockLedgerCardList = new ArrayList<>();
        }
        return fmsStockLedgerCardList;
    }

    public void setFmsStockLedgerCardList(List<FmsStockLedgerCard> fmsStockLedgerCardList) {
        this.fmsStockLedgerCardList = fmsStockLedgerCardList;
    }

    public List<FmsStockLedgerCard> getStockLedgerCardList() {
        if (stockLedgerCardList == null) {
            stockLedgerCardList = new ArrayList<>();
        }
        return stockLedgerCardList;
    }

    public void setStockLedgerCardList(List<FmsStockLedgerCard> stockLedgerCardList) {
        this.stockLedgerCardList = stockLedgerCardList;
    }

    public boolean isRenderChkBox() {
        return renderChkBox;
    }

    public void setRenderChkBox(boolean renderChkBox) {
        this.renderChkBox = renderChkBox;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isInitialStockPriceReadonly() {
        return initialStockPriceReadonly;
    }

    public void setInitialStockPriceReadonly(boolean initialStockPriceReadonly) {
        this.initialStockPriceReadonly = initialStockPriceReadonly;
    }

    public boolean isRenderPnlCreateStockItem() {
        return renderPnlCreateStockItem;
    }

    public void setRenderPnlCreateStockItem(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateStockItem = renderPnlCreateAdditional;
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

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public List<MmsStoreInformation> getUnregisteredMartialBYStoreNameList() {
        return storeInformationIntegrationBeanLocal.getUnregisteredMartialByStoreName();
    }

    public List<FmsStockLedgerCard> getSelectedfmsSLC() {
        return selectedfmsSLC;
    }

    public void setSelectedfmsSLC(List<FmsStockLedgerCard> selectedfmsSLC) {
        this.selectedfmsSLC = selectedfmsSLC;
    }
//</editor-fold>

    //value change for material list (unregistered)
    public void getUnregisteredMartialList(ValueChangeEvent event) {
        try {
            clearPage();
            if (null != event.getNewValue()) {
                mmsStoreInformation = (MmsStoreInformation) event.getNewValue();
                fmsStockLedgerCardList = null;
                fmsStockLedgerCardList = fmsStockLedgerCardBeanLocal.searchByStoreId(mmsStoreInformation.getStoreId());
                if (fmsStockLedgerCardList.isEmpty()) {
                    JsfUtil.addFatalMessage("No data found.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Failed to Process. Try again relaoding the Page");
        }
    }

    //value change for select material code
    public void onSelectMaterialCode(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                fmsStockLedgerCard = (FmsStockLedgerCard) event.getNewValue();
                materialId = fmsStockLedgerCard.getMaterialId();
                stockValue = BigDecimal.valueOf(fmsStockLedgerCard.getWeightAvgPrice()).multiply(new BigDecimal(fmsStockLedgerCard.getCurrentTotalQuantity().doubleValue()));
                mmsBinCard = mmsBinCardBeanLocal.findItemByMatId(materialId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Failed to Process. Try again relaoding the Page");
        }
    }

    //calculate
    public void calculateWtAvP() {
        try {
            stockValue = fmsStockRevaluationHistory.getNewPrice().multiply(new BigDecimal(fmsStockLedgerCard.getCurrentTotalQuantity().doubleValue()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //save
    public void saveStockRevaluation() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(SessionBean.getUserName(), "saveStockRevaluation", dataset)) {

            try {
                fmsStockRevaluationHistory.setSlcId(fmsStockLedgerCard);
                fmsStockRevaluationHistory.setOldPrice(fmsStockLedgerCard.getCurrentPrice());
                fmsStockLedgerCard.setWeightAvgPrice(fmsStockRevaluationHistory.getNewPrice().doubleValue());
                mmsBinCard.setUnitPrice(BigDecimal.valueOf(fmsStockLedgerCard.getWeightAvgPrice()));
                if (updateStatus == 0) {
                    fmsStockLedgerCardBeanLocal.edit(fmsStockLedgerCard);
                    fmsStockRevaluationHistoryBeanLocal.create(fmsStockRevaluationHistory);
                    mmsBinCardBeanLocal.edit(mmsBinCard);
                    JsfUtil.addSuccessMessage("Saved Successfully!");
                    clearPage();
                } else {
                    mmsBinCardBeanLocal.edit(mmsBinCard);
                    fmsStockLedgerCardBeanLocal.edit(fmsStockLedgerCard);
                    fmsStockRevaluationHistoryBeanLocal.edit(fmsStockRevaluationHistory);
                    JsfUtil.addSuccessMessage("Updated Successfully!");
                    clearPage();
                }

            } catch (Exception e) {
                JsfUtil.addFatalMessage("Failed to Save. Try Agian Reloading the Page.");
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

    //search
    public void searchFmsStockLedgerCard() {
        stockLedgerCardList = fmsStockLedgerCardBeanLocal.findAll(fmsStockLedgerCard);
        fmsStockLedgerCardModel = new ListDataModel(stockLedgerCardList);
    }

    //populate
    public void populateStockRevaluation(SelectEvent event) {
        fmsStockLedgerCard = (FmsStockLedgerCard) event.getObject();
        fmsStockRevaluationHistory.setSlcId(fmsStockLedgerCard);
        materialId = fmsStockLedgerCard.getMaterialId();
        stockValue = BigDecimal.valueOf(fmsStockLedgerCard.getWeightAvgPrice()).multiply(new BigDecimal(fmsStockLedgerCard.getCurrentTotalQuantity().doubleValue()));
        mmsBinCard = mmsBinCardBeanLocal.findItemByMatId(materialId);
        renderPnlCreateStockItem = true;
        renderPnlManPage = false;
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        updateStatus = 1;
    }

    //clear
    public void clearPage() {
        fmsStockLedgerCard = null;
        fmsStockRevaluationHistory = null;
        mmsBinCard = null;
        mmsStoreInformation = null;
        fmsStockLedgerCardModel = new ListDataModel<>();
        fmsStockLedgerCardList = new ArrayList<>();
        stockValue = new BigDecimal(0.00);
        updateStatus = 0;
        saveorUpdateBundle = "Save";
    }

    //create and search render
    public void createNewStockRevaluation() {
        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateStockItem = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateStockItem = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
}
