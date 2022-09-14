/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.stock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.stock.FmsStockItemInitialBalanceBeanLocal;
import et.gov.eep.fcms.entity.FmsStockLedgerCardModel;
import et.gov.eep.fcms.entity.stock.FmsStockLedgerCard;
import et.gov.eep.mms.businessLogic.MmsInventoryBalancingBeanLocal;
import et.gov.eep.mms.entity.MmsBinCard;
import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsGrnDetail;
import et.gov.eep.mms.entity.MmsInventoryBalanceSheet;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.integration.MmsItemRegistrationIntegrationBeanLocal;
import et.gov.eep.mms.integration.MmsStoreInformationIntegrationBeanLocal;

/**
 *
 * @author Mubarek jebel
 */
@Named(value = "inventoryCountSheetController")
@ViewScoped
public class InventoryCountSheetController implements Serializable {

    /**
     * Creates a new instance of InventoryCountSheetController
     */
    public InventoryCountSheetController() {
    }
    //<editor-fold defaultstate="collapsed" desc="@Inject and @EJB">
    //Inject @EJB
    @EJB
    private MmsInventoryBalancingBeanLocal mmsInventoryBalancingBeanLocal;
    @EJB
    private FmsStockItemInitialBalanceBeanLocal initialBalanceBeanLocal;
    @EJB
    private MmsStoreInformationIntegrationBeanLocal storeInformationIntegrationBeanLocal;
    @EJB
    private MmsItemRegistrationIntegrationBeanLocal itemRegistrationIntegrationBeanLocal;
//Inject entities
    @Inject
    MmsInventoryBalanceSheet mmsInventoryBalanceSheet;
    @Inject
    private MmsStoreInformation storeInformation;
    @Inject
    private MmsBinCard mmsBinCard;
    @Inject
    private MmsGrn mmsGrn;
    @Inject
    private MmsGrnDetail mmsGrnDetail;
    @Inject
    private MmsItemRegistration mmsItemRegistration;
    @Inject
    SessionBean SessionBean;
    //</editor-fold>

     //<editor-fold defaultstate="collapsed" desc="variable declaration">
    MmsInventoryBalanceSheet item = new MmsInventoryBalanceSheet();
    private List<MmsInventoryBalanceSheet> selectedMmsItem = new ArrayList<>();
    private List<MmsInventoryBalanceSheet> selectedMmsItem2 = new ArrayList<>();
    private List<MmsInventoryBalanceSheet> MmsInventoryBalanceSheetModel;
    private List<FmsStockLedgerCardModel> fmsStockLedgerCardModel;
    private String icone = "ui-icon-document";
    private String activeIndex;
    private String saveorUpdateBundle = "Create";
    private String createOrSearchBundle = "New";
    private boolean disableBtnCreate;
    private boolean renderChkBox = false;
    private boolean renderPnlCreateStockItem = false;
    private boolean renderPnlManPage = true;
    private boolean renderpnlToSearchPage;
    int updateStatus = 0;
    int storeID;
    double stockValue = 0.0;
    //</editor-fold>

     //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    public MmsStoreInformation getStoreInformation() {
        if (storeInformation == null) {
            storeInformation = new MmsStoreInformation();
        }
        return storeInformation;
    }

    public void setStoreInformation(MmsStoreInformation storeInformation) {
        this.storeInformation = storeInformation;
    }

    public double getStockValue() {
        return stockValue;
    }

    public void setStockValue(double stockValue) {
        this.stockValue = stockValue;
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

    public MmsItemRegistration getMmsItemRegistration() {
        return mmsItemRegistration;
    }

    public void setMmsItemRegistration(MmsItemRegistration mmsItemRegistration) {
        this.mmsItemRegistration = mmsItemRegistration;
    }

    public List<MmsInventoryBalanceSheet> getMmsInventoryBalanceSheetModel() {
        if (MmsInventoryBalanceSheetModel == null) {
            MmsInventoryBalanceSheetModel = new ArrayList<>();
        }
        return MmsInventoryBalanceSheetModel;
    }

    public void setMmsInventoryBalanceSheetModel(List<MmsInventoryBalanceSheet> MmsInventoryBalanceSheetModel) {
        this.MmsInventoryBalanceSheetModel = MmsInventoryBalanceSheetModel;
    }

    public List<FmsStockLedgerCardModel> getFmsStockLedgerCardModel() {
        if (fmsStockLedgerCardModel == null) {
            fmsStockLedgerCardModel = new ArrayList<>();
        }
        return fmsStockLedgerCardModel;
    }

    public void setFmsStockLedgerCardModel(List<FmsStockLedgerCardModel> fmsStockLedgerCardModel) {
        this.fmsStockLedgerCardModel = fmsStockLedgerCardModel;
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

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
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

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public List<MmsStoreInformation> getUnregisteredMartialBYStoreNameList() {
        return storeInformationIntegrationBeanLocal.findAllStoreInfo();
    }

    public List<MmsStoreInformation> getUnBalancedMartialBYStoreNameList() {
        return storeInformationIntegrationBeanLocal.getUnBalancedMartialByStoreName();
    }

    public List<MmsInventoryBalanceSheet> getSelectedMmsItem() {
        return selectedMmsItem;
    }

    public void setSelectedMmsItem(List<MmsInventoryBalanceSheet> selectedMmsItem) {
        this.selectedMmsItem = selectedMmsItem;
    }

    public List<MmsInventoryBalanceSheet> getSelectedMmsItem2() {
        return selectedMmsItem2;
    }

    public void setSelectedMmsItem2(List<MmsInventoryBalanceSheet> selectedMmsItem2) {
        this.selectedMmsItem2 = selectedMmsItem2;
    }
//</editor-fold>

    //value chage event for unregistered material list
    public void getUnregisteredMartialList(ValueChangeEvent event) {
        try {
            storeInformation.setStoreId(Integer.parseInt(event.getNewValue().toString()));
            if (null != event.getNewValue()) {
                fmsStockLedgerCardModel = new ArrayList<>();
                fmsStockLedgerCardModel = mmsInventoryBalancingBeanLocal.findByStoreIdAndStatus(storeInformation);
                for (int i = 0; i < fmsStockLedgerCardModel.size(); i++) {
                    if (fmsStockLedgerCardModel.get(i).getIbsCountingValue() > fmsStockLedgerCardModel.get(i).getSlcTotalQuanty()) {//overage
                        int overage = Integer.parseInt(String.valueOf(fmsStockLedgerCardModel.get(i).getIbsCountingValue())) - fmsStockLedgerCardModel.get(i).getSlcTotalQuanty();
                        fmsStockLedgerCardModel.get(i).setOverage(overage);
                        fmsStockLedgerCardModel.get(i).setShortage(0);
                    } else if (fmsStockLedgerCardModel.get(i).getIbsCountingValue() < fmsStockLedgerCardModel.get(i).getSlcTotalQuanty()) {
                        int shortage = fmsStockLedgerCardModel.get(i).getSlcTotalQuanty() - (Integer.parseInt(String.valueOf(fmsStockLedgerCardModel.get(i).getIbsCountingValue())));
                        fmsStockLedgerCardModel.get(i).setShortage(shortage);
                        fmsStockLedgerCardModel.get(i).setOverage(0);
                    } else {

                    }
                }
                if (fmsStockLedgerCardModel.isEmpty()) {
                    JsfUtil.addFatalMessage("No New Inventery Count Sheet is ready for balancing in this store.");
                }
            }
        } catch (Exception e) {
        }
    }

    //check
    public void check(SelectEvent events) {
        item = (MmsInventoryBalanceSheet) events.getObject();
        selectedMmsItem2.add(item);
    }

    //unckech
    public void uncheck(UnselectEvent uncheckevent) {
        item = (MmsInventoryBalanceSheet) uncheckevent.getObject();
        if (selectedMmsItem2.contains(item)) {
            selectedMmsItem2.remove(item);
        }
    }

    //checked
    public void checked(SelectEvent events) {
    }

    //unchecked
    public void unchecked(UnselectEvent uncheckevent) {
    }

    //save
    public void saveInventoryCountSheetFCMS() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(SessionBean.getUserName(), "saveInventoryCountSheetFCMS", dataset)) {
            int selectedSize = selectedMmsItem2.size();
            try {
                if (fmsStockLedgerCardModel.isEmpty()) {
                    JsfUtil.addFatalMessage("Select a Store Name Frist.");
                } else if (selectedSize > 0) {
                    for (int i = 0; i < selectedSize; i++) {
                        MmsInventoryBalanceSheet mmsIBS = new MmsInventoryBalanceSheet();
                        mmsIBS.setIbsId(selectedMmsItem2.get(i).getIbsId());
                        mmsIBS.setBudgetYear(selectedMmsItem2.get(i).getBudgetYear());
                        mmsIBS.setCountingValue(selectedMmsItem2.get(i).getCountingValue());
                        mmsIBS.setDiffrence(selectedMmsItem2.get(i).getDiffrence());
                        mmsIBS.setOldValue(selectedMmsItem2.get(i).getOldValue());
                        mmsIBS.setRemark(selectedMmsItem2.get(i).getRemark());
                        mmsIBS.setInvCountId(selectedMmsItem2.get(i).getInvCountId());
                        mmsIBS.setMaterialId(selectedMmsItem2.get(i).getMaterialId());
                        mmsIBS.setStoreId(selectedMmsItem2.get(i).getStoreId());
                        mmsIBS.setApprovedDate(selectedMmsItem2.get(i).getApprovedDate());
                        mmsIBS.setStatus(2);
                        mmsInventoryBalancingBeanLocal.edit(mmsIBS);

                        FmsStockLedgerCard fmsSLCard = new FmsStockLedgerCard();
                        fmsSLCard.setMaterialId(mmsIBS.getMaterialId().getMaterialId());
                        fmsSLCard.setMaterialName(mmsIBS.getMaterialId().getMatName());
                        fmsSLCard.setMaterialCode(mmsIBS.getMaterialId().getMatCode());
                        fmsSLCard.setStoreNo(mmsIBS.getMaterialId().getStoreId().getStoreNo());
                        fmsSLCard.setCurrentPrice(((mmsIBS.getMaterialId().getMmsBinCard().getUnitPrice()).multiply(new BigDecimal(mmsIBS.getCountingValue().doubleValue()))));
                        fmsSLCard.setWeightAvgPrice(((mmsIBS.getMaterialId().getMmsBinCard().getUnitPrice().doubleValue()) * (mmsIBS.getCountingValue())));
                        initialBalanceBeanLocal.saveorUpdate(fmsSLCard);
                    }
                    JsfUtil.addSuccessMessage("Saved Successfully!");
                    cleanpage();
                } else {
                    JsfUtil.addFatalMessage("Select at Least One Row to Save.");
                }
            } catch (Exception e) {
                JsfUtil.addFatalMessage("Failed to Save. Try Agian Reloading the Page");
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

    //clear
    public void cleanpage() {
        selectedMmsItem2 = null;
        fmsStockLedgerCardModel.clear();
    }

    //clear
    private void clearPage() {
        storeInformation = null;
        updateStatus = 0;
        saveorUpdateBundle = "Create";
    }

    //create and search render
    public void createNewStockInitialBalance() {
        clearPage();
        saveorUpdateBundle = "Create";
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
            setIcone("ui-icon-document");
        }
    }

}
