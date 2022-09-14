/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.stock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import org.primefaces.context.RequestContext;
import org.primefaces.convert.NumberConverter;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.stock.FmsStockLedgerCardBeanLocal;
import et.gov.eep.fcms.entity.stock.FmsStockLedgerCard;
import et.gov.eep.mms.entity.MmsBinCard;
import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsIsiv;
import et.gov.eep.mms.entity.MmsIsivReceived;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsNonFixedAssetReturn;
import et.gov.eep.mms.entity.MmsRmg;
import et.gov.eep.mms.entity.MmsSiv;
import et.gov.eep.mms.entity.MmsSivDetail;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.integration.MmsGrnIntegrationBeanLocal;
import et.gov.eep.mms.integration.MmsISIVRBeanLocal;
import et.gov.eep.mms.integration.MmsIsivIntegrationBeanLocal;
import et.gov.eep.mms.integration.MmsItemRegistrationIntegrationBeanLocal;
import et.gov.eep.mms.integration.MmsRmgIntegrationBeanLocal;
import et.gov.eep.mms.integration.MmsSivIntegrationBeanLocal;
import et.gov.eep.mms.integration.MmsStoreInformationIntegrationBeanLocal;

/**
 *
 * @author Mubarek jebel
 */
@Named(value = "fmsStockLedgerCardControler")
@ViewScoped

public class FmsStockLedgerCardControler implements Serializable {
 //<editor-fold defaultstate="collapsed" desc="@Inject and @EJB">
    //Inject @EJB
    @EJB
    FmsStockLedgerCardBeanLocal ledgerCardBeanLocal;
    @EJB
    MmsGrnIntegrationBeanLocal grnIntegrationBeanLocal;
    @EJB
    MmsISIVRBeanLocal mmsISIVRBeanLocal;
    @EJB
    MmsItemRegistrationIntegrationBeanLocal itemRegistrationIntegrationBeanLocal;
    @EJB
    MmsSivIntegrationBeanLocal sivIntegrationBeanLocal;
    @EJB
    MmsIsivIntegrationBeanLocal isivIntegrationBeanLocal;
    @EJB
    private MmsStoreInformationIntegrationBeanLocal mmsStoreInformationIntegrationBeanLocal;
    @EJB
    MmsRmgIntegrationBeanLocal rmgIntegrationBeanLocal;
//Inject entitites
    @Inject
    FmsStockLedgerCard fmsStockLedgerCard;
    @Inject
    MmsBinCard mmsBinCard;
    @Inject
    MmsStoreInformation mmsStoreInformation;
    @Inject
    SessionBean SessionBean;
//</editor-fold>
     //<editor-fold defaultstate="collapsed" desc="variable declaration">
    FmsStockLedgerCard select;
    String referenceNumber = "";
    String referencetype = "";
    String selectedType = "";
    private String saveorUpdateBundle = "Save";
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateStockLedger = true;
    private boolean renderPnlManPage = false;
    private boolean renderBtnSave;
    private boolean isRenderedRdoType;
    private int toSaveStatus;
    double sums = 0;
    double weight_Average_Price = 0;
    Double prvious_Quantity;
    List<FmsStockLedgerCard> ledgerCardList;
    private List<MmsItemRegistration> materialCodeList;
    private List<MmsStoreInformation> storeList;
    private List<MmsItemRegistration> itemRegistrationListModel;
    private List<FmsStockLedgerCard> stockLedgerCardList;
    private List<FmsStockLedgerCard> selectedValue;
    private List<MmsSivDetail> isivDetailListModel = new ArrayList();
    private List<MmsSiv> mmsSivList = new ArrayList();
    private List<MmsGrn> mmsGrnList = new ArrayList();
    private List<MmsIsiv> mmsIsivList = new ArrayList<>();
    private List<MmsNonFixedAssetReturn> mmsSrnList = new ArrayList<>();
    private List<MmsIsivReceived> mmsIsivRList = new ArrayList<>();
    private List<MmsRmg> mmsRmgList = new ArrayList<>();
    DataModel<FmsStockLedgerCard> fmsStockLedgerCardDModel;
    private DataModel<FmsStockLedgerCard> slcDataModel;
    private DataModel<FmsStockLedgerCard> stockLedgerListModel;
    private DataModel<MmsItemRegistration> itemRegistrationModel;
    private NumberConverter numberConverter = new NumberConverter();
//</editor-fold>
    /**
     * Creates a new instance of StockLedgerCardControler
     */
    public FmsStockLedgerCardControler() {

        numberConverter.setPattern("#,##0.0000##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(3);

    }
//<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">

    public List<FmsStockLedgerCard> getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(List<FmsStockLedgerCard> selectedValue) {
        this.selectedValue = selectedValue;
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

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public MmsBinCard getMmsBinCard() {
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

    public FmsStockLedgerCard getSelect() {
        return select;
    }

    public void setSelect(FmsStockLedgerCard select) {
        this.select = select;
    }

    public boolean isRenderPnlCreateStockLedger() {
        return renderPnlCreateStockLedger;
    }

    public void setRenderPnlCreateStockLedger(boolean renderPnlCreateStockLedger) {
        this.renderPnlCreateStockLedger = renderPnlCreateStockLedger;
    }

    public DataModel<FmsStockLedgerCard> getStockLedgerListModel() {
        if (stockLedgerListModel == null) {
            stockLedgerListModel = new ListDataModel<>();
        }
        return stockLedgerListModel;
    }

    public void setStockLedgerListModel(DataModel<FmsStockLedgerCard> stockLedgerListModel) {
        this.stockLedgerListModel = stockLedgerListModel;
    }

    public List<MmsItemRegistration> getItemRegistrationListModel() {
        if (itemRegistrationListModel == null) {
            itemRegistrationListModel = new ArrayList<>();
        }
        return itemRegistrationListModel;
    }

    public void setItemRegistrationListModel(List<MmsItemRegistration> itemRegistrationListModel) {
        this.itemRegistrationListModel = itemRegistrationListModel;
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

    public DataModel<FmsStockLedgerCard> getSlcDataModel() {
        if (slcDataModel == null) {
            slcDataModel = new ListDataModel<>();
        }
        return slcDataModel;
    }

    public void setSlcDataModel(DataModel<FmsStockLedgerCard> slcDataModel) {
        this.slcDataModel = slcDataModel;
    }

    public List<MmsItemRegistration> getMaterialCodeList() {
        return materialCodeList;
    }

    public void setMaterialCodeList(List<MmsItemRegistration> materialCodeList) {
        this.materialCodeList = materialCodeList;
    }

    public List<MmsStoreInformation> getStoreList() {
        storeList = mmsStoreInformationIntegrationBeanLocal.findAllStoreInfo();
        return storeList;
    }

    public void setStoreList(List<MmsStoreInformation> storeList) {
        this.storeList = storeList;
    }

    public List<MmsSivDetail> getIsivDetailListModel() {
        return isivDetailListModel;
    }

    public void setIsivDetailListModel(List<MmsSivDetail> isivDetailListModel) {
        this.isivDetailListModel = isivDetailListModel;
    }

    public List<MmsSiv> getMmsSivList() {
        if (mmsSivList == null) {
            mmsSivList = new ArrayList<>();
        }
        return mmsSivList;
    }

    public void setMmsSivList(List<MmsSiv> mmsSivList) {
        this.mmsSivList = mmsSivList;
    }

    public List<MmsIsiv> getMmsIsivList() {
        if (mmsIsivList == null) {
            mmsIsivList = new ArrayList<>();
        }
        return mmsIsivList;
    }

    public void setMmsIsivList(List<MmsIsiv> mmsIsivList) {
        this.mmsIsivList = mmsIsivList;
    }

    public List<MmsNonFixedAssetReturn> getMmsSrnList() {
        if (mmsSrnList == null) {
            mmsSrnList = new ArrayList<>();
        }
        return mmsSrnList;
    }

    public void setMmsSrnList(List<MmsNonFixedAssetReturn> mmsSrnList) {
        this.mmsSrnList = mmsSrnList;
    }

    public List<MmsIsivReceived> getMmsIsivRList() {
        if (mmsIsivRList == null) {
            mmsIsivRList = new ArrayList<>();
        }
        return mmsIsivRList;
    }

    public void setMmsIsivRList(List<MmsIsivReceived> mmsIsivRList) {
        this.mmsIsivRList = mmsIsivRList;
    }

    public List<MmsRmg> getMmsRmgList() {

        if (mmsRmgList == null) {
            mmsRmgList = new ArrayList<>();
        }

        return mmsRmgList;
    }

    public void setMmsRmgList(List<MmsRmg> mmsRmgList) {
        this.mmsRmgList = mmsRmgList;
    }

    public List<MmsGrn> getMmsGrnList() {
        if (mmsGrnList == null) {
            mmsGrnList = new ArrayList<>();
        }
        return mmsGrnList;
    }

    public void setMmsGrnList(List<MmsGrn> mmsGrnList) {
        this.mmsGrnList = mmsGrnList;
    }

    public List<FmsStockLedgerCard> getLedgerCardList() {
        return ledgerCardList;
    }

    public void setLedgerCardList(List<FmsStockLedgerCard> ledgerCardList) {
        this.ledgerCardList = ledgerCardList;
    }

    public DataModel<FmsStockLedgerCard> getFmsStockLedgerCardDModel() {
        return fmsStockLedgerCardDModel;
    }

    public void setFmsStockLedgerCardDModel(DataModel<FmsStockLedgerCard> fmsStockLedgerCardDModel) {
        this.fmsStockLedgerCardDModel = fmsStockLedgerCardDModel;
    }

    public boolean isRenderBtnSave() {
        return renderBtnSave;
    }

    public void setRenderBtnSave(boolean renderBtnSave) {
        this.renderBtnSave = renderBtnSave;
    }

    public boolean isIsRenderedRdoType() {
        return isRenderedRdoType;
    }

    public void setIsRenderedRdoType(boolean isRenderedRdoType) {
        this.isRenderedRdoType = isRenderedRdoType;
    }

    public int getToSaveStatus() {
        return toSaveStatus;
    }

    public void setToSaveStatus(int toSaveStatus) {
        this.toSaveStatus = toSaveStatus;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public double getSums() {
        return sums;
    }

    public void setSums(double sums) {
        this.sums = sums;
    }

    public double getWeight_Average_Price() {
        return weight_Average_Price;
    }

    public void setWeight_Average_Price(double weight_Average_Price) {
        this.weight_Average_Price = weight_Average_Price;
    }

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
    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    /**
     *
     * @param disableBtnCreate
     */
    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    /**
     *
     * @return
     */
    public boolean isRenderPnlCreateStock() {
        return renderPnlCreateStockLedger;
    }

    /**
     *
     * @param renderPnlCreateStock
     */
    public void setRenderPnlCreateStock(boolean renderPnlCreateStock) {
        this.renderPnlCreateStockLedger = renderPnlCreateStock;
    }

    /**
     *
     * @return
     */
    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    /**
     *
     * @param renderPnlManPage
     */
    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    /**
     *
     * @return
     */
    public DataModel<MmsItemRegistration> getItemRegistrationModel() {

        if (itemRegistrationModel == null) {
            itemRegistrationModel = new ListDataModel(itemRegistrationListModel);
        }

        return itemRegistrationModel;
    }

    public void setItemRegistrationModel(DataModel<MmsItemRegistration> itemRegistrationModel) {
        this.itemRegistrationModel = itemRegistrationModel;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    /**
     *
     * @param createOrSearchBundle
     */
    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    /**
     *
     * @return
     */
    public String getIcone() {
        return icone;
    }

    /**
     *
     * @param icone
     */
    public void setIcone(String icone) {
        this.icone = icone;
    }
//</editor-fold>

    //find material code
    public List<FmsStockLedgerCard> getAllMatCode() {
        return ledgerCardList = ledgerCardBeanLocal.findByMatCode(fmsStockLedgerCard);
    }

    //recreate to assign ledgerCardList to fmsStockLedgerCardDModel
    private void recreateDataModel() {
        fmsStockLedgerCardDModel = null;
        fmsStockLedgerCardDModel = new ListDataModel(new ArrayList<>(ledgerCardList));
    }

    //recreate to assign stockLedgerCardList to slcDataModel
    private void recreateDataModel1() {
        try {
            slcDataModel = null;
            slcDataModel = new ListDataModel(new ArrayList<>(stockLedgerCardList));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //searcg material code
    public void searchMatCode(ValueChangeEvent event) {
        fmsStockLedgerCard.setMaterialCode(event.getNewValue().toString());
        ledgerCardList = ledgerCardBeanLocal.getMatCode(fmsStockLedgerCard);
        recreateDataModel();
    }

    //get materual code on change
    public void findMaterialCodeOnChange(ValueChangeEvent e) {
        referencetype = (e.getNewValue().toString().split("-")[0] + "-" + e.getNewValue().toString().split("-")[1]);
        if (null != e.getNewValue() && !e.getNewValue().equals("")) {
            if (e.getNewValue().toString().split("-")[0].equalsIgnoreCase("siv")) {
                referenceNumber = e.getNewValue().toString();
                stockLedgerCardList = ledgerCardBeanLocal.searchSIVListToSave(referenceNumber, mmsStoreInformation.getStoreNo());
                recreateDataModel1();
            } else if (e.getNewValue().toString().split("-")[0].equalsIgnoreCase("grn")) {
                referenceNumber = e.getNewValue().toString();
                stockLedgerCardList = ledgerCardBeanLocal.searchGRNListToSave(referenceNumber, mmsStoreInformation.getStoreNo());
                recreateDataModel1();
            } else if (referencetype.equalsIgnoreCase("ISIV-I")) {
                referenceNumber = e.getNewValue().toString();
                stockLedgerCardList = ledgerCardBeanLocal.searchISIVIListToSave(referenceNumber, mmsStoreInformation.getStoreNo());
                recreateDataModel1();
            } else if (referencetype.equalsIgnoreCase("ISIV-R")) {
                referenceNumber = e.getNewValue().toString();
                stockLedgerCardList = ledgerCardBeanLocal.searchISIVRListToSave(referenceNumber, mmsStoreInformation.getStoreNo());
                recreateDataModel1();
            } else if (e.getNewValue().toString().split("-")[0].equalsIgnoreCase("SIRNo")) {

                referenceNumber = e.getNewValue().toString();
                stockLedgerCardList = ledgerCardBeanLocal.searchSRNListToSave(referenceNumber, mmsStoreInformation.getStoreNo());
                recreateDataModel1();
            } else {
                JsfUtil.addFatalMessage("Select appropriate option!");
            }
        }
    }

    //value change event for change store
    public void onChengeStore(ValueChangeEvent e) {
        if (e.getNewValue() != null) {
            mmsStoreInformation = (MmsStoreInformation) e.getNewValue();
            mmsGrnList = null;
            mmsSivList = null;
            mmsIsivList = null;
            mmsRmgList = null;
            mmsIsivRList = null;
            mmsSrnList = null;
            slcDataModel = null;
            selectedType = "";
        }
    }

    //find transaction type
    public void findTranType(ValueChangeEvent e) {
        selectedType = e.getNewValue().toString();
        fetchRefList();
    }
    
//find SR detail
    public void findSR_DetialByMaterialCodeAndSivNum(ValueChangeEvent e) {
        if (null != e.getNewValue() && !e.getNewValue().equals("")) {
            if (referenceNumber.split("-")[0].equalsIgnoreCase("siv")) {
                stockLedgerCardList = ledgerCardBeanLocal.searchSIVListToSave(referenceNumber, mmsStoreInformation.getStoreNo());
                recreateDataModel1();
                prvious_Quantity = itemRegistrationListModel.get(0).getMmsBinCard().getInitalQuantity().doubleValue();
                if (prvious_Quantity == null) {
                    JsfUtil.addErrorMessage("Material Initial Balance is not Registered!");
                    renderBtnSave = false;
                } else {
                    renderBtnSave = true;
                }
            } else if (referenceNumber.split("-")[0].equalsIgnoreCase("grn")) {
                stockLedgerCardList = ledgerCardBeanLocal.searchGRNListToSave(referenceNumber, mmsStoreInformation.getStoreNo());
                recreateDataModel1();
                prvious_Quantity = itemRegistrationListModel.get(0).getMmsBinCard().getInitalQuantity().doubleValue();
                if (prvious_Quantity == null) {
                    JsfUtil.addErrorMessage("Material Initial Balance is not Registered!");
                    renderBtnSave = false;
                } else {
                    renderBtnSave = true;
                }
            }
        }
        clearDrd();
    }

    //get lists
    public SelectItem[] getList() {
        if (mmsSivList != null) {
            return JsfUtil.getSelectItems(mmsSivList, true);
        } else if (mmsGrnList != null) {
            return JsfUtil.getSelectItems(mmsGrnList, true);
        } else if (mmsIsivList != null) {
            return JsfUtil.getSelectItems(mmsIsivList, true);
        } else if (mmsIsivRList != null) {
            return JsfUtil.getSelectItems(mmsIsivRList, true);
        } else if (mmsSrnList != null) {
            return JsfUtil.getSelectItems(mmsSrnList, true);
        } else {
            return null;
        }
    }

    //fetch reference list

    public void fetchRefList() {
        if (selectedType != null && !selectedType.equals("")) {
            slcDataModel = null;
            if (selectedType.equalsIgnoreCase("siv")) {//SIV
                mmsGrnList = null;
                mmsIsivList = null;
                mmsRmgList = null;
                mmsIsivRList = null;
                mmsSrnList = null;
                mmsSivList = sivIntegrationBeanLocal.searchSIVList(mmsStoreInformation);//find by approved status
            } else if (selectedType.equalsIgnoreCase("grn")) {//GRN
                mmsSivList = null;
                mmsIsivList = null;
                mmsRmgList = null;
                mmsIsivRList = null;
                mmsSrnList = null;
                mmsGrnList = grnIntegrationBeanLocal.searchGRNList(mmsStoreInformation);//find by approved status
            } else if (selectedType.equalsIgnoreCase("isiv-I")) {//ISIV-I
                mmsSivList = null;
                mmsGrnList = null;
                mmsRmgList = null;
                mmsIsivRList = null;
                mmsSrnList = null;
                mmsIsivList = isivIntegrationBeanLocal.searchISIVList(mmsStoreInformation);
            } else if (selectedType.equalsIgnoreCase("isiv-R")) {//ISIV-R
                mmsSivList = null;
                mmsGrnList = null;
                mmsRmgList = null;
                mmsIsivList = null;
                mmsSrnList = null;
                mmsIsivRList = mmsISIVRBeanLocal.searchISIVRList(mmsStoreInformation);
            } else if (selectedType.equalsIgnoreCase("SRN")) {//SRN
                mmsSivList = null;
                mmsGrnList = null;
                mmsRmgList = null;
                mmsIsivList = null;
                mmsSrnList = mmsISIVRBeanLocal.searchSRNList(mmsStoreInformation);
            } else {
                mmsSivList = null;
                mmsGrnList = null;
                mmsIsivList = null;
                mmsIsivRList = null;
                mmsRmgList = rmgIntegrationBeanLocal.searchRMGList();//find by approved status
            }
        }
    }

    //sum
    public String sum(String x, String y) {
        toSaveStatus = 0;
        if (null != referenceNumber && !referenceNumber.equals("") && null != x && !x.equals("") && null != y && !y.equals("")) {
            if (referenceNumber.split("-")[0].equalsIgnoreCase("siv")) {
                sums = Double.parseDouble(x) - Double.parseDouble(y);
                return String.valueOf(sums);
            } else {
                sums = Double.parseDouble(x) + Double.parseDouble(y);
                return String.valueOf(sums);
            }
        } else {
            toSaveStatus = 1;
            return "0";
        }

    }

    //calculation
    public String cal_WAP(String amountInBirr, String approved_Qnty, String prv_Qantity) {
        if (null != amountInBirr && !amountInBirr.equals("") && null != approved_Qnty && !approved_Qnty.equals("")
                && null != prv_Qantity && !prv_Qantity.equals("")) {
            if (null != referenceNumber && !referenceNumber.equals("")) {
                if (referenceNumber.split("-")[0].equalsIgnoreCase("siv")) {
                    weight_Average_Price = Double.parseDouble(amountInBirr);
                } else {
                    weight_Average_Price = ((Double.parseDouble(amountInBirr) * Double.parseDouble(approved_Qnty))
                            + (Double.parseDouble(amountInBirr) * Double.parseDouble(prv_Qantity)))
                            / (Double.parseDouble(approved_Qnty) + Double.parseDouble(prv_Qantity));

                }
            }

            return String.valueOf(weight_Average_Price);
        } else {

            return String.valueOf(0);
        }

    }

    //save
    public String saveStockLedgerCard() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(SessionBean.getUserName(), "saveStockLedgerCard", dataset)) {
            try {
                RequestContext context = RequestContext.getCurrentInstance();
                int status = ledgerCardBeanLocal.saveStockLedger(referenceNumber, mmsStoreInformation.getStoreNo());

                if (status == 1) {
                    context.execute("PF('statusDialog').hide();");
                    JsfUtil.addSuccessMessage("Saved Successfully!");

                } else {
                    context.execute("PF('statusDialog').hide();");
                    JsfUtil.addFatalMessage("Failed to Save. Try Again Reloading the Page!");

                }
                fetchRefList();
                clearPage();
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Something Occured On Data Created. Try Again Reloading the Page! ");
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
        return null;
    }

    //select event
    public void selectRow(SelectEvent event) {

    }

    //search
    public void searchMatCodeANDStoreNoByParameter() {
        if (fmsStockLedgerCard.getMaterialCode() != null && fmsStockLedgerCard.getStoreNo().isEmpty()) {
            selectedValue = ledgerCardBeanLocal.findByMatCode(fmsStockLedgerCard);
            stockLedgerListModel = new ListDataModel(selectedValue);

        } else if (fmsStockLedgerCard.getMaterialCode().isEmpty() && fmsStockLedgerCard.getStoreNo() != null) {
            selectedValue = ledgerCardBeanLocal.findByStoreNo(fmsStockLedgerCard);
            stockLedgerListModel = new ListDataModel(selectedValue);

        } else if (fmsStockLedgerCard.getMaterialCode() != null && fmsStockLedgerCard.getStoreNo() != null) {
            selectedValue = ledgerCardBeanLocal.findByAll(fmsStockLedgerCard);
            stockLedgerListModel = new ListDataModel(selectedValue);
        } else {
            JsfUtil.addFatalMessage("Failed to Search! use search Parameters");
        }
    }

    //clear
    public void cleanPage() {
        fmsStockLedgerCard = null;
        itemRegistrationModel = null;
        stockLedgerCardList = null;
        slcDataModel = null;
        itemRegistrationListModel = null;
        mmsGrnList = null;
        mmsSivList = null;

    }

    //clear
    public String clearDrd() {
        mmsSivList = null;
        return null;
    }

    //clear
    private void clearPage() {
        slcDataModel = null;
        referenceNumber = null;
        saveorUpdateBundle = "Save";
    }

    //create and serach render
    public void createNewStockLedgerInfo() {
        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateStockLedger = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateStockLedger = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
}
