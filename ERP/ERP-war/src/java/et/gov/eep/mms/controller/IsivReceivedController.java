/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
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
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import org.primefaces.event.SelectEvent;
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.businessLogic.MmsBinCardBeanLocal;
import et.gov.eep.mms.businessLogic.MmsIsivBeanLocal;
import et.gov.eep.mms.businessLogic.MmsIsivDetailBeanLocal;
import et.gov.eep.mms.businessLogic.MmsIsivReceivedBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreReqBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStorereqDetailBeanLocal;
import et.gov.eep.mms.entity.MmsBinCard;
import et.gov.eep.mms.entity.MmsIsiv;
import et.gov.eep.mms.entity.MmsIsivDetail;
import et.gov.eep.mms.entity.MmsIsivReceived;
import et.gov.eep.mms.entity.MmsIsivReceivedDtl;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.entity.MmsStorereq;
import et.gov.eep.mms.entity.MmsStorereqDetail;

/**
 *
 * @author minab
 */
@Named(value = "isivReceivedController")
@ViewScoped
public class IsivReceivedController implements Serializable {
//<editor-fold defaultstate="collapsed" desc="Entities">

    @Inject
    WfMmsProcessed wfMmsProcessed;
    @Inject
    MmsIsiv isivEntity;
    @Inject
    MmsIsivReceived isivReceivedEntity;
    @Inject
    MmsIsivReceivedDtl isivReceivedDtlEntity;
    @Inject
    MmsStorereq srEntity;
    @Inject
    private MmsBinCard mmsBinCard;
    @Inject
    private MmsItemRegistration mmsItemRegistration;
    @Inject
    MmsStorereqDetail srDetailentity;
    @Inject
    MmsIsivDetail isivdetail;
    @Inject
    MmsStoreInformation fromStore;
    @Inject
    private MmsStoreInformation storeEntity;
    @Inject
    ColumnNameResolver columnNameResolver;

    @Inject
    private MmsStoreInformation toStore;
    @Inject
    private HrDepartments hrDepartmentsEntity;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="EJB">    
    @EJB
    WfMmsProcessedBeanLocal wfMmsProcessedBeanLocal;
    @EJB
    MmsStoreInformationBeanLocal storebeanLocal;
    @EJB
    MmsIsivBeanLocal transferBeanLocal;
    @EJB
    MmsStoreReqBeanLocal srbeanLocal;
    @EJB
    MmsStorereqDetailBeanLocal srDetailBeanlocal;
    @EJB
    MmsIsivDetailBeanLocal isivDetailbeanLocal;
    @EJB
    MmsIsivReceivedBeanLocal isivReceivedInterface;
    @EJB
    MmsStoreInformationBeanLocal storeInfoInterface;
    @EJB
    MmsBinCardBeanLocal mmsBinCardBeanLocal;
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Fields">    
    DataModel<MmsIsivReceivedDtl> isivReceviedDetailsModel;
    private DataModel<MmsIsivReceived> mmsIsivReceivedSearchDataModel;
    ArrayList<MmsBinCard> bincardList = new ArrayList<>();
    List<WfMmsProcessed> WfList;
    DataModel<WfMmsProcessed> WorkflowDataModel = new ListDataModel<>();
    private List<MmsIsivReceived> mmsIsivRList;
    int updateStatus = 0;
    private boolean disableBtnCreate;
    private String saveorUpdateBundle = "Save";
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateISIV = false;
    private boolean renderPnlManPage = true;
    private boolean renderDecision = false;
    private String icone = "ui-icon-plus";
    private boolean isRenderedIconNitification = false;
    private boolean disableButtonUpdate = false;
    private boolean renderpnlToSearchPage;
    private String userName;
    private MmsIsivReceived selectedItem;
    String newPayNo;
    String lastPaymentNo = "0";
    private List<MmsIsivReceived> allIsivInfoList;
    private boolean saved = false;
    String selectedValue = "";
    List<MmsIsivReceivedDtl> IsIVDetailList;
    List<MmsIsiv> IsIVNoList;
    List<MmsStoreInformation> StoreList;
    private List<MmsIsivReceived> mmsReceivedColumnNameList;
    private List<String> mmsIsivRColumnNameList;
    ColumnNames columnNames = new ColumnNames();
    List<ColumnNames> ColumnNamesList1 = new ArrayList<>();
    List<ColumnNameResolver> ColumnNamesList = new ArrayList<>();
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Postconstraction">    
    public class ColumnNames implements Serializable {

        String Table_Col_Name;
        String Parsed_Col_name;

        //<editor-fold defaultstate="collapsed" desc="getter and setter">
        public String getTable_Col_Name() {
            return Table_Col_Name;
        }

        public void setTable_Col_Name(String Table_Col_Name) {
            this.Table_Col_Name = Table_Col_Name;
        }

        public String getParsed_Col_name() {
            return Parsed_Col_name;
        }

        public void setParsed_Col_name(String Parsed_Col_name) {
            this.Parsed_Col_name = Parsed_Col_name;
        }
//</editor-fold>

    }

    @PostConstruct
    public void init() {
        if (workflow.isApproveStatus()) {
            System.out.println("app or check");
            renderDecision = true;
            mmsIsivRList = isivReceivedInterface.findByStatus(Constants.PREPARE_VALUE);
            isRenderedIconNitification = true;
        } else if (workflow.isPrepareStatus()) {
            renderDecision = false;
            System.out.println("prep");

            mmsIsivRList = isivReceivedInterface.findByStatus(Constants.APPROVE_REJECT_VALUE);
            if (mmsIsivRList != null) {
                isRenderedIconNitification = mmsIsivRList.size() > 0;
            }
        }
        getMmsIsivRColumnNameList();
        wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
    }

    public IsivReceivedController() {
    }
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="getter and setter">  
    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public WfMmsProcessed getWfMmsProcessed() {
        if (wfMmsProcessed == null) {
            wfMmsProcessed = new WfMmsProcessed();
        }
        return wfMmsProcessed;
    }

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    public List<ColumnNameResolver> getColumnNamesList() {
        return ColumnNamesList;
    }

    public void setColumnNamesList(List<ColumnNameResolver> ColumnNamesList) {
        this.ColumnNamesList = ColumnNamesList;
    }

    public boolean isIsRenderedIconNitification() {
        return isRenderedIconNitification;
    }

    public void setIsRenderedIconNitification(boolean isRenderedIconNitification) {
        this.isRenderedIconNitification = isRenderedIconNitification;
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

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
    }

    public List<MmsIsivReceived> getMmsReceivedColumnNameList() {
        if (mmsReceivedColumnNameList == null) {
            mmsReceivedColumnNameList = new ArrayList<>();
//            mmsReceivedColumnNameList = isivReceivedInterface.getMmsReceivedColumnNameList();

        }
        return mmsReceivedColumnNameList;
    }

    public void setMmsReceivedColumnNameList(List<MmsIsivReceived> mmsReceivedColumnNameList) {
        this.mmsReceivedColumnNameList = mmsReceivedColumnNameList;
    }

//    public List<MmsIsivReceived> getMmsReceivedColumnNameList() {
//        if (mmsReceivedColumnNameList == null) {
//            mmsReceivedColumnNameList = new ArrayList<>();
//            mmsReceivedColumnNameList = isivReceivedInterface.getMmsReceivedColumnNameList();
//
//        }
//        return mmsReceivedColumnNameList;
//    }
//
//    public void setMmsReceivedColumnNameList(List<MmsIsivReceived> mmsReceivedColumnNameList) {
//        this.mmsReceivedColumnNameList = mmsReceivedColumnNameList;
//    }
    public void setWfMmsProcessed(WfMmsProcessed wfMmsProcessed) {
        this.wfMmsProcessed = wfMmsProcessed;
    }

    public List<WfMmsProcessed> getWfList() {
        return WfList;
    }

    public void setWfList(List<WfMmsProcessed> WfList) {
        this.WfList = WfList;
    }

    public DataModel<WfMmsProcessed> getWorkflowDataModel() {
        return WorkflowDataModel;
    }

    public void setWorkflowDataModel(DataModel<WfMmsProcessed> WorkflowDataModel) {
        this.WorkflowDataModel = WorkflowDataModel;
    }

    public MmsIsiv getIsivEntity() {
        if (isivEntity == null) {
            isivEntity = new MmsIsiv();
        }
        return isivEntity;
    }

    public void setIsivEntity(MmsIsiv isivEntity) {
        this.isivEntity = isivEntity;
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

    public MmsBinCard getMmsBinCard() {
        if (mmsBinCard == null) {
            mmsBinCard = new MmsBinCard();
        }
        return mmsBinCard;
    }

    public void setMmsBinCard(MmsBinCard mmsBinCard) {
        this.mmsBinCard = mmsBinCard;
    }

    public MmsStoreInformation getFromStore() {
        return fromStore;
    }

    public void setFromStore(MmsStoreInformation fromStore) {
        this.fromStore = fromStore;
    }

    public MmsStoreInformation getStoreEntity() {
        if (storeEntity == null) {
            storeEntity = new MmsStoreInformation();
        }
        return storeEntity;
    }

    public void setStoreEntity(MmsStoreInformation storeEntity) {
        this.storeEntity = storeEntity;
    }

    public MmsStoreInformation getToStore() {
        return toStore;
    }

    public void setToStore(MmsStoreInformation toStore) {
        this.toStore = toStore;
    }

    public HrDepartments getHrDepartmentsEntity() {
        return hrDepartmentsEntity;
    }

    public void setHrDepartmentsEntity(HrDepartments hrDepartmentsEntity) {
        this.hrDepartmentsEntity = hrDepartmentsEntity;
    }

    public MmsIsivReceived getIsivReceivedEntity() {
        if (isivReceivedEntity == null) {
            isivReceivedEntity = new MmsIsivReceived();
        }
        return isivReceivedEntity;
    }

    public void setIsivReceivedEntity(MmsIsivReceived isivReceivedEntity) {
        this.isivReceivedEntity = isivReceivedEntity;
    }

    public MmsIsivReceivedDtl getIsivReceivedDtlEntity() {
        if (isivReceivedDtlEntity == null) {
            isivReceivedDtlEntity = new MmsIsivReceivedDtl();
        }
        return isivReceivedDtlEntity;
    }

    public void setIsivReceivedDtlEntity(MmsIsivReceivedDtl isivReceivedDtlEntity) {
        this.isivReceivedDtlEntity = isivReceivedDtlEntity;
    }

    public MmsIsivDetail getIsivdetail() {
        return isivdetail;
    }

    public void setIsivdetail(MmsIsivDetail isivdetail) {
        this.isivdetail = isivdetail;
    }

    public DataModel<MmsIsivReceivedDtl> getIsivReceviedDetailsModel() {
        if (isivReceviedDetailsModel == null) {
            isivReceviedDetailsModel = new ListDataModel<>();
        }
        return isivReceviedDetailsModel;
    }

    public void setIsivReceviedDetailsModel(DataModel<MmsIsivReceivedDtl> isivReceviedDetailsModel) {
        this.isivReceviedDetailsModel = isivReceviedDetailsModel;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public DataModel<MmsIsivReceived> getMmsIsivReceivedSearchDataModel() {
        if (mmsIsivReceivedSearchDataModel == null) {
            mmsIsivReceivedSearchDataModel = new ListDataModel<>();
        }
        return mmsIsivReceivedSearchDataModel;
    }

    public void setMmsIsivReceivedSearchDataModel(DataModel<MmsIsivReceived> mmsIsivReceivedSearchDataModel) {
        this.mmsIsivReceivedSearchDataModel = mmsIsivReceivedSearchDataModel;
    }

    public MmsIsivReceived getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(MmsIsivReceived selectedItem) {
        this.selectedItem = selectedItem;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isRenderPnlCreateISIV() {
        return renderPnlCreateISIV;
    }

    public void setRenderPnlCreateISIV(boolean renderPnlCreateISIV) {
        this.renderPnlCreateISIV = renderPnlCreateISIV;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isDisableButtonUpdate() {
        return disableButtonUpdate;
    }

    public void setDisableButtonUpdate(boolean disableButtonUpdate) {
        this.disableButtonUpdate = disableButtonUpdate;
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public List<MmsStoreInformation> getStoreList() {
        StoreList = storeInfoInterface.findAllStoreInfo();
        return StoreList;
    }

    public List<MmsIsiv> getIsIVNoList() {
        return IsIVNoList;
    }

    public void setIsIVNoList(List<MmsIsiv> IsIVNoList) {
        this.IsIVNoList = IsIVNoList;
    }
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="handler,save,sreach and clear">  
    /*This method is used to create New ISIV1 Info
     */
    public void createNewISIV1Info() {

        clearPage();
        saveorUpdateBundle = "Save";

        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateISIV = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-search");

        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateISIV = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-plus");
        }

    }
    /*This method is used to go Back Search Button Action
     */

    public void goBackSearchButtonAction() {
        renderpnlToSearchPage = false;
        renderPnlCreateISIV = false;
        renderPnlManPage = true;
    }
    /*This method is used to create New Isiv Info
     */

    public void createNewIsivInfo() {

        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateISIV = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateISIV = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
    /*This method is used to btn Search Action
     */

    public void btn_Search_Action() {
        clearPage();
        renderPnlCreateISIV = false;
        renderPnlManPage = true;
    }
    /*This method is used to handle Select Store Name
     */

    public void handleSelectStoreName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            Integer Id = Integer.parseInt(event.getNewValue().toString());
            storeEntity.setStoreId(Id);
            System.out.println("=====Store Id @Handleer======" + storeEntity.getStoreId());
            IsIVNoList = transferBeanLocal.approvedIsivList(storeEntity, Constants.APPROVE_VALUE);

        }

    }
    /*This method is used to handle Select Isiv No
     */

    public void handleSelectIsivNo(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            isivReceivedEntity.getMmsIsivReceivedDtlList().clear();
            isivReceviedDetailsModel = null;
            isivEntity.setTransferNo(event.getNewValue().toString());
            isivEntity = transferBeanLocal.getIsivInfoByIsivNo(isivEntity);
            System.out.println("======isisv==========" + isivEntity.getFromStore().getStoreName());
            isivReceivedEntity.setTransferId(isivEntity);
            isivReceivedEntity.setIssuingStoreId(isivEntity.getFromStore());
            isivReceivedEntity.setReceivingStoreId(isivEntity.getToStore());
            for (int i = 0; i < isivEntity.getMmsIsivDetailList().size(); i++) {
                isivReceivedDtlEntity = new MmsIsivReceivedDtl();
                isivReceivedDtlEntity.setMaterialId(isivEntity.getMmsIsivDetailList().get(i).getItemId());
                isivReceivedDtlEntity.setQuantity(isivEntity.getMmsIsivDetailList().get(i).getQuantity());
                isivReceivedDtlEntity.setUnitPrice(isivEntity.getMmsIsivDetailList().get(i).getUnitPrice());
                mmsItemRegistration = new MmsItemRegistration();
                mmsItemRegistration.setMaterialId(isivReceivedDtlEntity.getMaterialId().getMaterialId());
                mmsItemRegistration.setStoreId(isivReceivedEntity.getReceivingStoreId());
                toStore = new MmsStoreInformation();
                toStore = isivEntity.getToStore();
                isivReceivedEntity.addIsivDetialInfo(isivReceivedDtlEntity);
                recreateIsivRecievedDataModel();
            }
        }

    }
    /*This method is used to recreate Isiv Recieved DataModel
     */

    private void recreateIsivRecievedDataModel() {
        isivReceviedDetailsModel = null;
        isivReceviedDetailsModel = new ListDataModel(new ArrayList<>(isivReceivedEntity.getMmsIsivReceivedDtlList()));
    }
    /*This method is used to recreate Search Isiv Received DataModel
     */

    private void recreateSearchIsivReceivedDataModel() {
        mmsIsivReceivedSearchDataModel = null;
        mmsIsivReceivedSearchDataModel = new ListDataModel(new ArrayList<>(allIsivInfoList));
    }

    /*This method is used to column Name Change Event
     */
    public void columnNameChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            System.out.println("event.getNewValue().toString()==" + event.getNewValue().toString());
            columnNameResolver.setCol_Name_FromTable(event.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            isivReceivedEntity.setColumnName(columnNameResolver.getCol_Name_FromTable());
            isivReceivedEntity.setColumnValue(null);
        }
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }

    public List<String> getMmsIsivRColumnNameList() {
        mmsIsivRColumnNameList = isivReceivedInterface.getMmsReceivedColumnNameList();
        if (mmsIsivRColumnNameList == null) {
            mmsIsivRColumnNameList = new ArrayList<>();
        }
        System.out.println("=======mmsRmgColumnNameList==" + mmsIsivRColumnNameList);
        if (mmsIsivRColumnNameList.size() > 0) {
            ColumnNamesList = new ArrayList<>();
            for (int i = 0; i < mmsIsivRColumnNameList.size(); i++) {
                ColumnNameResolver obj = new ColumnNameResolver();
                obj.setCol_Name_FromTable((mmsIsivRColumnNameList.get(i).toLowerCase()));
                obj.setParsed_Col_Name(ColumnParser((mmsIsivRColumnNameList.get(i).toLowerCase())));
                ColumnNamesList.add(obj);
            }

        }
        return mmsIsivRColumnNameList;
    }

    public void setMmsIsivRColumnNameList(List<String> mmsIsivRColumnNameList) {
        this.mmsIsivRColumnNameList = mmsIsivRColumnNameList;
    }

    /*This method is used to generate Transfer No
     */
    public String generateTransferNo() {
        if (updateStatus == 1) {
            newPayNo = isivReceivedEntity.getReceivingTransferNo();
        } else {
            MmsIsivReceived lastPaymentNoObj = isivReceivedInterface.getLastTransferNo();
            if (lastPaymentNoObj != null) {
                lastPaymentNo = lastPaymentNoObj.getRecievingId().toString();
            }
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            int newPayment = 0;
            if (lastPaymentNo.equals("0")) {
                newPayment = 1;
                newPayNo = "ISIV-" + newPayment + "/" + f.format(now);
            } else {
                String[] lastInspNos = lastPaymentNo.split("-");
                String lastDatesPatern = lastInspNos[0];
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newPayment = Integer.parseInt(lastDatesPaterns[0]);
                newPayment = newPayment + 1;
                newPayNo = "ISIV-" + newPayment + "/" + f.format(now);
            }

        }
        return newPayNo;
    }
    /*This method is used to on Select Isiv RRequest
     */

    public void onSelectisivRRequest(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                isivReceivedEntity = (MmsIsivReceived) event.getNewValue();
                populateUI();

            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Faile to process! Try Again Reloading the Page");
        }
    }
    /*This method is used to Workflow Save
     */

    public void WfSave() {
        wfMmsProcessed.setRecievingId(isivReceivedEntity);
        wfMmsProcessedBeanLocal.create(wfMmsProcessed);
    }
    /*This method is used to Save ISIVRecevied
     */

    public void saveIsivReceived() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveIsivReceived", dataset)) {
                if (updateStatus == 0) {
                    try {
                        generateTransferNo();
                        isivReceivedEntity.setReceivingTransferNo(newPayNo);
                        isivReceivedEntity.addIsivRecivedIdToWorkflow(wfMmsProcessed);
                        isivReceivedEntity.setProcessedBy(SessionBean.getUserId());
                        isivReceivedEntity.setStatus(Constants.PREPARE_VALUE);
                        isivReceivedEntity.setProcessedOn(wfMmsProcessed.getProcessedOn());
                        isivReceivedEntity.setCommentGiven(wfMmsProcessed.getCommentGiven());
                        isivReceivedInterface.create(isivReceivedEntity);
                        isivEntity.setStatusWf(Constants.ISIV_PROCESSED_AND_RECEIVED);
                        transferBeanLocal.edit(isivEntity);

                        JsfUtil.addSuccessMessage("ISIV Data is Created- " + isivReceivedEntity.getReceivingTransferNo());
                    } catch (Exception e) {
                        JsfUtil.addFatalMessage("Something Error Occured on Data Saved");
                    }
                    clearPage();
                } else if (updateStatus == 1 && workflow.isPrepareStatus()) {
                    isivReceivedEntity.setStatus(Constants.APPROVE_VALUE);
                    isivReceivedEntity.setProcessedOn(wfMmsProcessed.getProcessedOn());
                    isivReceivedEntity.setCommentGiven(wfMmsProcessed.getCommentGiven());
                    isivReceivedInterface.edit(isivReceivedEntity);
                    JsfUtil.addSuccessMessage("ISIV Data is modified");
                    mmsIsivRList.remove(isivReceivedEntity);
                    clearPage();
                } else if (updateStatus == 1 && (workflow.isApproveStatus() || workflow.isCheckStatus())) {
                    if (selectedValue.equalsIgnoreCase("Approve") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_VALUE);
                        isivReceivedEntity.setStatus(Constants.APPROVE_VALUE);
                        wfMmsProcessed.setDecision(Constants.APPROVE_VALUE);
                        saveWorkflowAndClear();
                        if (saved) {
                            System.out.println("=========Isiv no=====" + isivEntity.getTransferNo());
                            boolean status = updateBinCardISIV(isivEntity.getTransferNo());
                            System.out.println("====status controller===" + status);
                            clearPage();
                        } else {
                            JsfUtil.addFatalMessage("Procedure execution failed");
                        }
                    } else if (selectedValue.equalsIgnoreCase("Approve") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        isivReceivedEntity.setStatus(Constants.CHECK_APPROVE_VALUE);
                        wfMmsProcessed.setDecision(Constants.CHECK_APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        isivReceivedEntity.setStatus(Constants.APPROVE_REJECT_VALUE);
                        wfMmsProcessed.setDecision(Constants.APPROVE_REJECT_VALUE);
                        saveWorkflowAndClear();
                        clearPage();
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        isivReceivedEntity.setStatus(Constants.CHECK_REJECT_VALUE);
                        wfMmsProcessed.setDecision(Constants.CHECK_REJECT_VALUE);
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
        } catch (Exception ex) {

        }
    }
    /*This method is used to save Workflow And Clear
     */

    public void saveWorkflowAndClear() {
        isivReceivedEntity.addIsivRecivedIdToWorkflow(wfMmsProcessed);
        isivReceivedInterface.edit(isivReceivedEntity);
        wfMmsProcessedBeanLocal.create(wfMmsProcessed);
        mmsIsivRList.remove(isivReceivedEntity);
        saved = true;
        JsfUtil.addSuccessMessage("ISIV Data Modified");
    }

    public boolean updateBinCardISIV(String isivNo) {
        try {

            int status;

            status = mmsBinCardBeanLocal.incrementBinCardQuantityForISIV(isivNo);

            return status == 0;
        } catch (Exception ex) {

            return false;
        }

    }
    /*This method is used to search Isiv Information
     */

    public void searchIsivInformation() {
        System.out.println("in search");
        isivReceivedEntity.setProcessedBy(SessionBean.getUserId());
        System.out.println("processor " + isivReceivedEntity.getProcessedBy());
        allIsivInfoList = isivReceivedInterface.getReceivedListsByParameter(columnNameResolver, isivReceivedEntity, isivReceivedEntity.getColumnValue());
        recreateSearchIsivReceivedDataModel();

    }
    /*This method is used to On Row Edit
     */

    public void onRowEdit(SelectEvent event) {
        isivReceivedEntity = (MmsIsivReceived) event.getObject();
        int size = isivReceivedEntity.getWfMmsProcessedList().size();
        if (workflow.isApproveStatus()) {

            if (isivReceivedEntity.getWfMmsProcessedList().get(size - 1).getDecision() == Constants.CHECK_APPROVE_VALUE || isivReceivedEntity.getWfMmsProcessedList().get(size - 1).getDecision() == Constants.APPROVE_VALUE) {
                setSelectedValue("Approve");
            } else if (isivReceivedEntity.getWfMmsProcessedList().get(size - 1).getDecision() == Constants.CHECK_REJECT_VALUE || isivReceivedEntity.getWfMmsProcessedList().get(size - 1).getDecision() == Constants.APPROVE_REJECT_VALUE) {
                setSelectedValue("Reject");
            }
            wfMmsProcessed.setProcessedOn(isivReceivedEntity.getWfMmsProcessedList().get(size - 1).getProcessedOn());

        } else {
        }

        populateUI();
    }
    /*This method is used to populateUI
     */

    public void populateUI() {
        isivEntity = isivReceivedEntity.getTransferId();
        storeEntity = isivReceivedEntity.getReceivingStoreId();
        renderPnlCreateISIV = true;
        renderPnlManPage = false;
        renderpnlToSearchPage = true;

        saveorUpdateBundle = "Update";
        createOrSearchBundle = "New";
        setCreateOrSearchBundle(createOrSearchBundle);
        setIcone("ui-icon-plus");
        if (workflow.isPrepareStatus()) {
            System.out.println("yes i'm preparer");
            wfMmsProcessed.setProcessedOn(isivReceivedEntity.getProcessedOn());
            System.out.println("date====" + isivReceivedEntity.getProcessedOn());
        }
        isivEntity = isivReceivedEntity.getTransferId();
        IsIVNoList = new ArrayList<>();
        IsIVNoList.add(isivEntity);
        setCreateOrSearchBundle(createOrSearchBundle);
        updateStatus = 1;

        recreateIsivRecievedDataModel();
        recreateWfDataModel();
        saveorUpdateButtonHandler();

    }
    /*This method is used to recreate Workflow DataModel
     */

    public void recreateWfDataModel() {
        WorkflowDataModel = null;
        for (int i = 0; i < isivReceivedEntity.getWfMmsProcessedList().size(); i++) {
            if (isivReceivedEntity.getWfMmsProcessedList().get(i).getDecision() != null) {
                if (isivReceivedEntity.getWfMmsProcessedList().get(i).getDecision() == 1 || isivReceivedEntity.getWfMmsProcessedList().get(i).getDecision() == 3) {
                    isivReceivedEntity.getWfMmsProcessedList().get(i).setWfDecison("Approved");
                } else if (isivReceivedEntity.getWfMmsProcessedList().get(i).getDecision() == 2 || isivReceivedEntity.getWfMmsProcessedList().get(i).getDecision() == 4) {
                    isivReceivedEntity.getWfMmsProcessedList().get(i).setWfDecison("Rejected");
                }
            } else {
                isivReceivedEntity.getWfMmsProcessedList().get(i).setWfDecison("Requested");
            }

        }
        WorkflowDataModel = new ListDataModel(isivReceivedEntity.getWfMmsProcessedList());
    }
    /*This method is used to Save or Update Button Handler
     */

    public void saveorUpdateButtonHandler() {
        if (workflow.isPrepareStatus()) {
            if (isivReceivedEntity.getStatus().equals(Constants.APPROVE_VALUE) || isivReceivedEntity.getStatus().equals(Constants.CHECK_APPROVE_VALUE)) {
                disableButtonUpdate = true;
            }
        } else if (workflow.isCheckStatus() || workflow.isApproveStatus()) {
            if (isivReceivedEntity.getStatus().equals(Constants.APPROVE_VALUE)) {
                disableButtonUpdate = true;
            }
        }

    }
    /*This method is used to clear Page
     */

    public void clearPage() {
        isivEntity = new MmsIsiv();
        isivdetail = new MmsIsivDetail();
        isivReceivedEntity = new MmsIsivReceived();
        isivReceivedDtlEntity = new MmsIsivReceivedDtl();
        storeEntity = new MmsStoreInformation();
        isivReceviedDetailsModel = null;
        mmsIsivReceivedSearchDataModel = new ListDataModel<>();
        wfMmsProcessed = null;
        setSelectedValue(null);
    }
//</editor-fold>
}
