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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.stock.FmsStockItemInitialBalanceBeanLocal;
import et.gov.eep.fcms.entity.stock.FmsStockLedgerCard;
import et.gov.eep.mms.entity.MmsBinCard;
import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsGrnDetail;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.integration.MmsItemRegistrationIntegrationBeanLocal;
import et.gov.eep.mms.integration.MmsStoreInformationIntegrationBeanLocal;

/**
 *
 * @author Mubarek jebel
 */
@Named("fmsStockItemInitialBalanceController")
@ViewScoped
public class FmsStockItemInitialBalanceController implements Serializable {
 //<editor-fold defaultstate="collapsed" desc="@Inject and @EJB">
    //Inject @EJB
    @EJB
    private FmsStockItemInitialBalanceBeanLocal initialBalanceBeanLocal;
    @EJB
    private MmsStoreInformationIntegrationBeanLocal storeInformationIntegrationBeanLocal;
    @EJB
    private MmsItemRegistrationIntegrationBeanLocal itemRegistrationIntegrationBeanLocal;
    //Inject entities
    @Inject
    SessionBean SessionBean;
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
//</editor-fold>
     //<editor-fold defaultstate="collapsed" desc="variable declaration">
       MmsItemRegistration item = new MmsItemRegistration();
    private List<MmsItemRegistration> mmsMaterialModel;
    private List<MmsItemRegistration> selectedMmsItem = new ArrayList<>();
    private List<MmsItemRegistration> selectedMmsItem2 = new ArrayList<>();
    private boolean disableBtnCreate;
    private boolean renderChkBox = false;
    private boolean renderPnlCreateStockItem = false;
    private boolean initialStockPriceReadonly = false;
    private boolean renderPnlManPage = true;
    private String saveorUpdateBundle = "Create";
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-document";
    private String activeIndex;
    int updateStatus = 0;
    int storeID;
    double stockValue = 0.0;
//</editor-fold>
    public FmsStockItemInitialBalanceController() {
    }
//<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">

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

    public List<MmsItemRegistration> getMmsMaterialModel() {
        if (mmsMaterialModel == null) {
            mmsMaterialModel = new ArrayList<>();
        }
        return mmsMaterialModel;
    }

    public void setMmsMaterialModel(List<MmsItemRegistration> mmsMaterialModel) {
        this.mmsMaterialModel = mmsMaterialModel;
    }

    public List<MmsItemRegistration> getSelectedMmsItem2() {
        return selectedMmsItem2;
    }

    public void setSelectedMmsItem2(List<MmsItemRegistration> selectedMmsItem2) {
        this.selectedMmsItem2 = selectedMmsItem2;
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

    public List<MmsItemRegistration> getSelectedMmsItem() {
        return selectedMmsItem;
    }

    public void setSelectedMmsItem(List<MmsItemRegistration> selectedMmsItem) {
        this.selectedMmsItem = selectedMmsItem;
    }
//</editor-fold>

//New item item flag = 1
    public void getUnregisteredMartialList(ValueChangeEvent event) {
        renderChkBox = false;
        initialStockPriceReadonly = true;
        try {
            if (null != event.getNewValue()) {
                mmsMaterialModel = null;
                mmsMaterialModel = itemRegistrationIntegrationBeanLocal.searchByStoreId(Integer.parseInt(event.getNewValue().toString()));
                if (!mmsMaterialModel.isEmpty()) {
                    storeID = mmsMaterialModel.get(0).getStoreId().getStoreId();
                } else {
                    JsfUtil.addFatalMessage("No New item is ready for registration.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Faild to process. Try again relaoding the page.");
        }
    }

//OLD item item flag = 0
    public void getUnregisteredMartialList1(ValueChangeEvent event) {
        int itemFlag = 0;
        initialStockPriceReadonly = false;
        if (null != event.getNewValue()) {
            if (storeID == 0) {
                JsfUtil.addFatalMessage("Please select store name first, and then check	\n"
                        + "Has weighted Av. price, to get materials that has weighted av. price!");
            } else {
                mmsMaterialModel = null;
                mmsMaterialModel = itemRegistrationIntegrationBeanLocal.searchByStoreId1(storeID, itemFlag);
            }
            if (mmsMaterialModel.isEmpty()) {
                JsfUtil.addFatalMessage("No New item ready for registration.");
            }
        }
    }

    //cell edit event
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell value Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage("Message", msg);
        }
    }

    //check
    public void check(SelectEvent events) {
        item = (MmsItemRegistration) events.getObject();
        selectedMmsItem2.add(item);
    }

    //uncheck
    public void uncheck(UnselectEvent uncheckevent) {
        item = (MmsItemRegistration) uncheckevent.getObject();
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
    public void saveStockItemInitialBalance() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(SessionBean.getUserName(), "saveStockItemInitialBalance", dataset)) {
            int mmsMaterialModelSize = selectedMmsItem2.size();
            int mmsMaterialModelSize1 = mmsMaterialModel.size();
            try {
                if (mmsMaterialModelSize1 == 0) {
                    JsfUtil.addFatalMessage("Select a Store Name Frist.");
                } else if (mmsMaterialModelSize > 0) {
                    for (int i = 0; i < mmsMaterialModelSize; i++) {
                        MmsItemRegistration mmsItemReg = selectedMmsItem2.get(i);
                        BigDecimal unitPrice = mmsItemReg.getMmsBinCard().getUnitPrice();
                        if ((unitPrice.compareTo(new BigDecimal(0.00)) == 0) || unitPrice == null) {
                            JsfUtil.addFatalMessage("The value of initial stock price should be > 0.00.");
                        } else {
                            FmsStockLedgerCard fmsSLCard = new FmsStockLedgerCard();
                            fmsSLCard.setMaterialId(mmsItemReg.getMaterialId());
                            fmsSLCard.setMaterialName(mmsItemReg.getMatName());
                            fmsSLCard.setMaterialCode(mmsItemReg.getMatCode());
                            fmsSLCard.setCurrentQuantity(mmsItemReg.getMmsBinCard().getInitalQuantity());
                            fmsSLCard.setCurrentTotalQuantity(mmsItemReg.getMmsBinCard().getInitalQuantity());//???
                            fmsSLCard.setPrvQuantity(mmsItemReg.getMmsBinCard().getInitalQuantity());
                            fmsSLCard.setStoreNo(mmsItemReg.getStoreId().getStoreNo());
                            fmsSLCard.setCurrentPrice((unitPrice.multiply((mmsItemReg.getMmsBinCard().getInitalQuantity()))));
                            fmsSLCard.setWeightAvgPrice((unitPrice.multiply((mmsItemReg.getMmsBinCard().getInitalQuantity()))).doubleValue());
                            initialBalanceBeanLocal.saveorUpdate(fmsSLCard);
                        }
                    }
                    JsfUtil.addSuccessMessage("Saved Successfully!");
                    cleanpage();
                } else {
                    JsfUtil.addFatalMessage("Select at Least One Row to Save.");
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

    //clear
    public void cleanpage() {
        selectedMmsItem2 = null;
        mmsMaterialModel.clear();
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
