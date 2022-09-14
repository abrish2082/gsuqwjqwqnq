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
import et.gov.eep.fcms.businessLogic.stock.FmsTotalStockValueBeanLocal;
import et.gov.eep.fcms.entity.stock.FmsTotalStockValue;
import et.gov.eep.mms.businessLogic.MmsBinCardBeanLocal;
import et.gov.eep.mms.businessLogic.MmsGrnDetailBeanLocal;
import et.gov.eep.mms.businessLogic.MmsSivBeanLocal;
import et.gov.eep.mms.businessLogic.MmsSivDtlBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreReqBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStorereqDetailBeanLocal;
import et.gov.eep.mms.entity.MmsBinCard;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsSiv;
import et.gov.eep.mms.entity.MmsSivDetail;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.entity.MmsStorereq;
import et.gov.eep.mms.entity.MmsStorereqDetail;
import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsGrnDetail;

/**
 *
 * @author minab
 */
@Named(value = "sivController")
@ViewScoped
public class SivController implements Serializable {
//<editor-fold defaultstate="collapsed" desc="Entities">

    @Inject
    FmsTotalStockValue fmsTotalStockValue;
    @Inject
    MmsSiv siv;
    @Inject
    private MmsBinCard mmsBinCard;
    @Inject
    MmsSivDetail sivDetail;
    @Inject
    MmsSivDetail sivDetailobj;
    @Inject
    private MmsStoreInformation storeEntity;
    @Inject
    private MmsGrn mmsGrn;
    @Inject
    MmsStorereqDetail storeRequisitionDetail;
    @Inject
    MmsStorereq storeRequisitionEntity;

    @Inject
    WfMmsProcessed wfMmsProcessed;
    @Inject
    MmsStorereq mmsStorereq;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    @Inject
    MmsItemRegistration ItemRegistration;
    @Inject
    ColumnNameResolver columnNameResolver;
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="EJB">        
    @EJB
    MmsStoreReqBeanLocal storeRequisitionBeanLocal;
    @EJB
    MmsStoreInformationBeanLocal storebeanLocal;
    @EJB
    MmsGrnDetailBeanLocal grndetailBeanLocal;
    @Inject
    MmsStorereqDetailBeanLocal srdetailBeanLocal;
    @EJB
    MmsSivBeanLocal sivbeanLocal;
    @EJB
    MmsSivDtlBeanLocal sivdetailBeanLocal;
    @EJB
    WfMmsProcessedBeanLocal wfMmsProcessedBeanLocal;
    @EJB
    FmsTotalStockValueBeanLocal fmsTotalStockValueBeanLocal;
    @EJB
    MmsBinCardBeanLocal binCardBeanLocal;
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Fields">    
    DataModel<WfMmsProcessed> WorkflowDataModel = new ListDataModel<>();
    List<WfMmsProcessed> WfList;
    DataModel<MmsStorereqDetail> sivDetailsModel;
    private List<MmsSiv> mmsSivList;
    String btn_saveUpdate = "Create";
    int updateStatus = 0;
    String SivNumber = "";
    private String saveorUpdateBundle = "Save";

    private String createOrSearchBundle = "New";
    private boolean renderDecision = false;
    private boolean renderPnlCreateSIV = false;
    private boolean renderPnlManPage = true;

    private boolean disableButtonUpdate = false;
    private boolean renderpnlToSearchPage;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private String selectedValue = "";
    private String userName;
    private boolean isRenderedIconNitification = false;
    private boolean isRenderedIconWorkflow = false;
    private List<MmsGrnDetail> mmsGrnDetailsList;

    private DataModel<WfMmsProcessed> WfMmsProcessedDataModel;
    List<MmsStoreInformation> StoreList;
    private DataModel<MmsSiv> mmsSivSearchInfoDataModel;
    private List<MmsSiv> allSivInfoList;
    String newPayNo;
    String lastPaymentNo = "0";
    private boolean saved = false;
    ArrayList<MmsBinCard> bincardList = new ArrayList<>();
    List<MmsStorereq> srList = new ArrayList<>();
    int index = 0;
    private List<MmsSiv> mmsSivColumnNameList;
    private List<String> sivColumnNameList;
    ColumnNames columnNames = new ColumnNames();
    List<ColumnNames> ColumnNamesList1 = new ArrayList<>();
    List<ColumnNameResolver> ColumnNamesList = new ArrayList<>();
//</editor-fold>

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
//<editor-fold defaultstate="collapsed" desc="Postconstraction">        

    public SivController() {
    }

    @PostConstruct
    public void init() {
        System.out.println("=====ID========" + SessionBean.getUserId());
        if (workflow.isApproveStatus()) {
            mmsSivList = sivbeanLocal.findSivListByWfStatus(Constants.PREPARE_VALUE);
            System.out.println("app or check");
            renderDecision = true;
            isRenderedIconNitification = true;
        } else if (workflow.isPrepareStatus()) {
            System.out.println("prep");
            renderDecision = false;
            mmsSivList = sivbeanLocal.findByStatus(Constants.APPROVE_REJECT_VALUE);
            System.out.println("=================reject===" + mmsSivList);
//            if (mmsSivList != null) {
            isRenderedIconNitification = mmsSivList.size() > 0;
            System.out.println("=====isRenderedIconNitification=======" + isRenderedIconNitification);
//            }
        }
        getSivColumnNameList();
        wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
    }
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="getter and setter"> 
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

    public FmsTotalStockValue getFmsTotalStockValue() {
        if (fmsTotalStockValue == null) {
            fmsTotalStockValue = new FmsTotalStockValue();
        }
        return fmsTotalStockValue;
    }

    public void setFmsTotalStockValue(FmsTotalStockValue fmsTotalStockValue) {
        this.fmsTotalStockValue = fmsTotalStockValue;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public List<MmsSiv> getMmsSivList() {
        return mmsSivList;
    }

    public void setMmsSivList(List<MmsSiv> mmsSivList) {
        this.mmsSivList = mmsSivList;
    }

    public boolean isIsRenderedIconWorkflow() {
        return isRenderedIconWorkflow;
    }

    public void setIsRenderedIconWorkflow(boolean isRenderedIconWorkflow) {
        this.isRenderedIconWorkflow = isRenderedIconWorkflow;
    }

    public DataModel<WfMmsProcessed> getWfMmsProcessedDataModel() {
        if (WfMmsProcessedDataModel == null) {
            WfMmsProcessedDataModel = new ListDataModel<>();
        }
        return WfMmsProcessedDataModel;
    }

    public void setWfMmsProcessedDataModel(DataModel<WfMmsProcessed> WfMmsProcessedDataModel) {
        this.WfMmsProcessedDataModel = WfMmsProcessedDataModel;
    }

    /**
     *
     */
    public List<MmsGrnDetail> getMmsGrnDetailsList() {
        return mmsGrnDetailsList;
    }

    public void setMmsGrnDetailsList(List<MmsGrnDetail> mmsGrnDetailsList) {
        this.mmsGrnDetailsList = mmsGrnDetailsList;
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

    public List<MmsStorereq> getSrList() {
        if (srList == null) {
            srList = new ArrayList<>();
        }
        return srList;
    }

    public void setSrList(List<MmsStorereq> srList) {
        this.srList = srList;
    }

    public List<MmsStorereq> getListofSR() {

        //srList = storeRequisitionBeanLocal.findAllwithApprovedStatus(1);
        return srList;

    }

    public WfMmsProcessed getWfMmsProcessed() {
        if (wfMmsProcessed == null) {
            wfMmsProcessed = new WfMmsProcessed();
        }
        return wfMmsProcessed;
    }

    public void setWfMmsProcessed(WfMmsProcessed wfMmsProcessed) {
        this.wfMmsProcessed = wfMmsProcessed;
    }

    public DataModel<WfMmsProcessed> getWorkflowDataModel() {
        return WorkflowDataModel;
    }

    public void setWorkflowDataModel(DataModel<WfMmsProcessed> WorkflowDataModel) {
        this.WorkflowDataModel = WorkflowDataModel;
    }

    public List<WfMmsProcessed> getWfList() {
        return WfList;
    }

    public void setWfList(List<WfMmsProcessed> WfList) {
        this.WfList = WfList;
    }

    /**
     *
     * @return
     */
    public String getSivNumber() {
        return SivNumber;
    }

    /**
     *
     * @param SivNumber
     */
    public void setSivNumber(String SivNumber) {
        this.SivNumber = SivNumber;
    }

    /**
     *
     * @return
     */
    public MmsSiv getSiv() {
        if (siv == null) {
            siv = new MmsSiv();
        }
        return siv;
    }

    /**
     *
     * @return
     */
    public MmsStorereq getStoreRequisitionEntity() {
        if (storeRequisitionEntity == null) {
            storeRequisitionEntity = new MmsStorereq();
        }
        return storeRequisitionEntity;
    }

    /**
     *
     * @param storeRequisitionEntity
     */
    public void setStoreRequisitionEntity(MmsStorereq storeRequisitionEntity) {
        this.storeRequisitionEntity = storeRequisitionEntity;
    }

    /**
     *
     * @param siv
     */
    public void setSiv(MmsSiv siv) {
        this.siv = siv;
    }

    /**
     *
     * @return
     */
    public MmsStorereqDetail getStoreRequisitionDetail() {
        if (storeRequisitionDetail == null) {
            storeRequisitionDetail = new MmsStorereqDetail();
        }
        return storeRequisitionDetail;
    }

    /**
     *
     * @param storeRequisitionDetail
     */
    public void setStoreRequisitionDetail(MmsStorereqDetail storeRequisitionDetail) {

        this.storeRequisitionDetail = storeRequisitionDetail;
    }

    /**
     *
     * @return
     */
    public String getBtn_saveUpdate() {
        return btn_saveUpdate;
    }

    /**
     *
     * @param btn_saveUpdate
     */
    public void setBtn_saveUpdate(String btn_saveUpdate) {
        this.btn_saveUpdate = btn_saveUpdate;
    }

    /**
     *
     * @return
     */
    public int getUpdateStatus() {
        return updateStatus;
    }

    /**
     *
     * @param updateStatus
     */
    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return
     */
    public MmsSivDetail getSivDetail() {
        if (sivDetail == null) {
            sivDetail = new MmsSivDetail();
        }
        return sivDetail;
    }

    /**
     *
     * @param sivDetail
     */
    public void setSivDetail(MmsSivDetail sivDetail) {
        this.sivDetail = sivDetail;
    }

    /**
     *
     * @return
     */
    public DataModel<MmsStorereqDetail> getSivDetailsModel() {
        if (sivDetailsModel == null) {
            sivDetailsModel = new ListDataModel<>();
        }
        return sivDetailsModel;
    }

    public MmsStorereq getMmsStorereq() {
        if (mmsStorereq == null) {
            mmsStorereq = new MmsStorereq();
        }
        return mmsStorereq;
    }

    public void setMmsStorereq(MmsStorereq mmsStorereq) {
        this.mmsStorereq = mmsStorereq;
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

    public boolean isRenderPnlCreateSIV() {
        return renderPnlCreateSIV;
    }

    public void setRenderPnlCreateSIV(boolean renderPnlCreateSIV) {
        this.renderPnlCreateSIV = renderPnlCreateSIV;
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

    public DataModel<MmsSiv> getMmsSivSearchInfoDataModel() {
        if (mmsSivSearchInfoDataModel == null) {
            mmsSivSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsSivSearchInfoDataModel;
    }

    public void setMmsSivSearchInfoDataModel(DataModel<MmsSiv> mmsSivSearchInfoDataModel) {
        this.mmsSivSearchInfoDataModel = mmsSivSearchInfoDataModel;
    }
    //for row selection
    private MmsSiv selectedItem;

    public MmsSiv getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(MmsSiv selectedItem) {
        this.selectedItem = selectedItem;
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

    public List<MmsStoreInformation> getStoreList() {
        StoreList = storebeanLocal.findAllStoreInfo();
        return StoreList;
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

    public boolean isIsRenderedIconNitification() {
        return isRenderedIconNitification;
    }

    public void setIsRenderedIconNitification(boolean isRenderedIconNitification) {
        this.isRenderedIconNitification = isRenderedIconNitification;
    }

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="handler,save,sreach and clear">  
    /*This method is used to getSelectedSRInfo
     */
    public void getSelectedSRInfo(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            siv.getMmsSivDetailList().clear();
            sivDetailsModel = new ListDataModel<>();
            storeRequisitionEntity = (MmsStorereq) event.getNewValue();

            siv.setSrNo(storeRequisitionEntity.getSrNo());
            siv.setStoreReqId(storeRequisitionEntity);
            siv.setDates(storeRequisitionEntity.getRequestedDate());
            siv.setStoreId(storeRequisitionEntity.getRequestStore());
            System.out.println("=======size of SR Detail====" + storeRequisitionEntity.getMmsStorereqDetailList().size());
            for (int i = 0; i < storeRequisitionEntity.getMmsStorereqDetailList().size(); i++) {
                int requestedQunatity = 0;
                if (storeRequisitionEntity.getSrCases().equals("specific Identification")) {
                    mmsGrn = new MmsGrn();
                    mmsGrn = storeRequisitionEntity.getMmsStorereqDetailList().get(i).getGrnId();
                    System.out.println("When SR is Specific with GRN id " + mmsGrn.getGrnId());
                    int specRequestedQuantity;
                    specRequestedQuantity = storeRequisitionEntity.getMmsStorereqDetailList().get(i).getQuantity().intValue();
                    int itemId = storeRequisitionEntity.getMmsStorereqDetailList().get(i).getItemId().getMaterialId();

                    sivForSpecificIdentificationMethod(itemId, mmsGrn, specRequestedQuantity);
                } else {
                    sivDetail = new MmsSivDetail();
                    fmsTotalStockValue = new FmsTotalStockValue();
                    fmsTotalStockValue = fmsTotalStockValueBeanLocal.getWeightedAverageValueByMatCode(storeRequisitionEntity.getMmsStorereqDetailList().get(i).getItemId().getMatCode());
                    sivDetail.setItemId(storeRequisitionEntity.getMmsStorereqDetailList().get(i).getItemId());
                    sivDetail.setQuantity(storeRequisitionEntity.getMmsStorereqDetailList().get(i).getQuantity());
                    mmsBinCard = new MmsBinCard();
                    mmsBinCard = storeRequisitionEntity.getMmsStorereqDetailList().get(i).getItemId().getMmsBinCard();

                    siv.addSivDetialInfo(sivDetail);
                }
            }

            recreateSivDataModel();
            System.out.println("========after recreate ===========" + sivDetailsModel.getRowCount());

        }
    }

    public List<MmsSiv> getMmsSivColumnNameList() {
        if (mmsSivColumnNameList == null) {
            mmsSivColumnNameList = new ArrayList<>();
//            mmsSivColumnNameList = sivbeanLocal.getMmsSivColumnNameList();

        }
        return mmsSivColumnNameList;
    }

    public void setMmsSivColumnNameList(List<MmsSiv> mmsSivColumnNameList) {
        this.mmsSivColumnNameList = mmsSivColumnNameList;
    }

    /*This method is used to column Name Change Event
     */
    public void columnNameChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            System.out.println("event.getNewValue().toString()==" + event.getNewValue().toString());
            columnNameResolver.setCol_Name_FromTable(event.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            siv.setColumnName(columnNameResolver.getCol_Name_FromTable());
            siv.setColumnValue(null);
        }

    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }

    public List<String> getSivColumnNameList() {
        sivColumnNameList = sivbeanLocal.getMmsSivColumnNameList();
        if (sivColumnNameList == null) {
            sivColumnNameList = new ArrayList<>();
        }
        System.out.println("=======mmsRmgColumnNameList==" + sivColumnNameList);
        if (sivColumnNameList.size() > 0) {
            ColumnNamesList = new ArrayList<>();
            for (int i = 0; i < sivColumnNameList.size(); i++) {
                ColumnNameResolver obj = new ColumnNameResolver();
                obj.setCol_Name_FromTable((sivColumnNameList.get(i).toLowerCase()));
                obj.setParsed_Col_Name(ColumnParser((sivColumnNameList.get(i).toLowerCase())));
                ColumnNamesList.add(obj);
            }

        }
        return sivColumnNameList;
    }

    public void setSivColumnNameList(List<String> sivColumnNameList) {
        this.sivColumnNameList = sivColumnNameList;
    }

    /*This method is used to siv For Fifo Method
     */
    public void sivForFifoMethod(int itemId, int requestedQuantity) {
        //Search GRN Detail with the provided id
        int remainingRequest = requestedQuantity;
        mmsGrnDetailsList = grndetailBeanLocal.searchMmsGrnDetailByItemId(itemId);
        System.out.println("=====remaning qunatity at 0===" + mmsGrnDetailsList.get(0).getRemainingQuantity());
        System.out.println("==requested Quantity=" + requestedQuantity);

        if (mmsGrnDetailsList.get(0).getRemainingQuantity() >= requestedQuantity) {
            System.out.println("==============inside if of sivfor fifo====");
            remainingRequest = mmsGrnDetailsList.get(0).getRemainingQuantity() - remainingRequest;
            setValuesOfSivDetail(0, index, remainingRequest);
            mmsGrnDetailsList.get(0).setRemainingQuantity(remainingRequest);
        } else if (mmsGrnDetailsList.get(0).getRemainingQuantity() < requestedQuantity) {
            System.out.println("==============inside else if of sivfor fifo====");
            for (int i = 0; i < mmsGrnDetailsList.size(); i++) {
                if (mmsGrnDetailsList.get(i).getRemainingQuantity() > remainingRequest && remainingRequest > 0) {
                    System.out.println("=====remaning quantity Next index===========" + mmsGrnDetailsList.get(i).getRemainingQuantity());
                    System.out.println("======inside  if of 2nd secniro");
                    remainingRequest = mmsGrnDetailsList.get(i).getRemainingQuantity() - remainingRequest;
                    setValuesOfSivDetail(i, index, mmsGrnDetailsList.get(i).getRemainingQuantity() - remainingRequest);
                    mmsGrnDetailsList.get(i).setRemainingQuantity(remainingRequest);
                    break;
                } else {
                    if (remainingRequest >= 1) {

                        System.out.println("==inside else of second senario");
                        remainingRequest = remainingRequest - mmsGrnDetailsList.get(i).getRemainingQuantity();
                        setValuesOfSivDetail(i, index, mmsGrnDetailsList.get(i).getRemainingQuantity());
                        mmsGrnDetailsList.get(i).setRemainingQuantity(0);
                    } else {
                        break;
                    }
                }
            }
        }

    }
    /*This method is used to siv For Specific Identification Method
     */

    public void sivForSpecificIdentificationMethod(int itemId, MmsGrn mmsGrn, int specRequestedQuantity) {
        int grnCurrentQty = 0;
        System.out.println("Item Id== " + itemId + ",GRN-No " + mmsGrn.getGrnNo() + " and Req Qty == " + specRequestedQuantity);
        mmsGrnDetailsList = grndetailBeanLocal.listOfGrnDetailsByItemId(itemId, mmsGrn);
        for (int grnDtlIndex = 0; grnDtlIndex < mmsGrnDetailsList.size(); grnDtlIndex++) {
            grnCurrentQty = mmsGrnDetailsList.get(grnDtlIndex).getReceivedQuantity().intValue();
            sivDetail = new MmsSivDetail();
            if (grnCurrentQty >= specRequestedQuantity && specRequestedQuantity > 0) {
                grnCurrentQty = grnCurrentQty - specRequestedQuantity;
                System.out.println("When " + specRequestedQuantity + " Requested of GRN Received Qty @ INDEX " + grnDtlIndex + " after deducted==  " + grnCurrentQty);
                mmsGrnDetailsList.get(grnDtlIndex).setReceivedQuantity(BigDecimal.valueOf(grnCurrentQty));
                sivDetail.setQuantity(BigDecimal.valueOf((double) specRequestedQuantity));
                sivDetail.setGrnId(mmsGrnDetailsList.get(grnDtlIndex).getGrnId());
                sivDetail.setItemId(mmsGrnDetailsList.get(grnDtlIndex).getItemId());
                sivDetail.setUnitPrice(mmsGrnDetailsList.get(grnDtlIndex).getUnitPrice());
                sivDetail.setTotalPrice(sivDetail.getUnitPrice().multiply(sivDetail.getQuantity()));
                siv.addSivDetialInfo(sivDetail);
            }
        }
    }
    /*This method is used to set Values Of Siv Detail
     */

    public void setValuesOfSivDetail(int i, int index, int deductedQuantiy) {
        System.out.println("===inside set values=====");
        // ItemRegistration.setMaterialId(itemId);
        sivDetail = new MmsSivDetail();
        mmsGrn = new MmsGrn();
        mmsGrn = mmsGrnDetailsList.get(i).getGrnId();
        System.out.println("===GrnID======" + mmsGrnDetailsList.get(i).getGrnId());
        System.out.println("===========index======" + i);
        sivDetail.setItemId(storeRequisitionEntity.getMmsStorereqDetailList().get(index).getItemId());
        sivDetail.setQuantity(BigDecimal.valueOf((double) deductedQuantiy));
        sivDetail.setGrnId(mmsGrn);
        sivDetail.setUnitPrice(mmsGrnDetailsList.get(i).getUnitPrice());
        sivDetail.setTotalPrice(sivDetail.getUnitPrice().multiply(sivDetail.getQuantity()));
        siv.addSivDetialInfo(sivDetail);

    }
    /*This method is used to On Select Siv Request
     */

    public void onSelectSivRequest(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                siv = (MmsSiv) event.getNewValue();

            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Faile to process! Try Again Reloading the Page");
        }
    }

    /*This method is used to btn Save Actions
     */
    public String btnSave_Actions() {
        if (updateStatus == 0) {
            try {
                List<MmsStorereqDetail> srDetailList;
                srDetailList = srdetailBeanLocal.getlistofSrDetails(storeRequisitionEntity);

                int size = storeRequisitionEntity.getMmsStorereqDetailList().size();

                MmsSivDetail lastId = sivdetailBeanLocal.getlastSivDtlId();
                Integer Id = 0;
                if (lastId != null) {
                    Id = lastId.getSivDetId() + 1;

                }
                if (Id == 0) {
                    Id = 1;
                }
                siv.setSrNo(storeRequisitionEntity.getSrNo());
                siv.setDates(storeRequisitionEntity.getRequestedDate());
                siv.setIssuedTo(storeRequisitionEntity.getRequestedBy().getFirstName());
                siv.setStoreId(storeRequisitionEntity.getRequestStore());
                for (int i = 0; i < size; i++) {
                    MmsSivDetail sivdetailObj = new MmsSivDetail();

                    sivdetailObj.setItemId(srDetailList.get(i).getItemId());
                    sivdetailObj.setQuantity(srDetailList.get(i).getQuantity());
                    mmsBinCard = new MmsBinCard();
                    mmsBinCard = srDetailList.get(i).getItemId().getMmsBinCard();
                    BigDecimal currentqunt = mmsBinCard.getCurrentQuantity();
                    mmsBinCard.setCurrentQuantity(currentqunt.subtract(sivdetailObj.getQuantity().abs()));
                    bincardList.add(mmsBinCard);

                    siv.addSivDetialInfo(sivdetailObj);

                    sivdetailObj.setSivDetId(Id);

                    Id++;

                }
                sivbeanLocal.create(siv);
                storeRequisitionEntity.setStatus(1);
                storeRequisitionBeanLocal.edit(storeRequisitionEntity);
                int binsize = bincardList.size();
                for (int i = 0; i < binsize; i++) {
                    binCardBeanLocal.edit(bincardList.get(i));
                }
                JsfUtil.addSuccessMessage("SIV Data Is Created");
                return clearPage();
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Something Error Occured on Data Created");
                clearPage();
                return null;
            }
        } else {
            try {
                sivbeanLocal.edit(siv);
                JsfUtil.addSuccessMessage("Siv data is Modified");
                saveorUpdateBundle = "Save";
                return clearPage();
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Something Error Occured on Data Modified");
                clearPage();

            }
            return null;
        }

    }
    /*This method is used to WorkFlow Save
     */

    public void Wfsave() {
        wfMmsProcessed.setSivId(siv);
        wfMmsProcessedBeanLocal.create(wfMmsProcessed);
    }
    /*This method is used to save Siv
     */

    public void saveSiv() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(SessionBean.getUserName(), "saveSiv", dataset)) {

            if (updateStatus == 0 && workflow.isPrepareStatus()) {
                if (siv.getMmsSivDetailList().size() <= 0) {
                    JsfUtil.addFatalMessage("Saving Without Data Not Allowed");
                } else {
                    try {
                        generateSivNo();
                        siv.setSivNo(newPayNo);
                        wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
                        siv.setProcessedOn(wfMmsProcessed.getProcessedOn());
                        siv.setCommentGiven(wfMmsProcessed.getCommentGiven());
                        siv.setProcessedBy(SessionBean.getUserId());
                        siv.setStatus(Constants.PREPARE_VALUE);
                        wfMmsProcessed.setDecision(siv.getStatus());
                        sivbeanLocal.create(siv);
                        Wfsave();
                        storeRequisitionEntity.setStatus(Constants.SR_PROCESSED_BY_SIV);
                        storeRequisitionBeanLocal.edit(storeRequisitionEntity);
                        JsfUtil.addSuccessMessage("SIV Data is Saved" + siv.getSivNo());
                    } catch (Exception e) {
                        JsfUtil.addFatalMessage("Something Error Occured on Data Saved");
                    }
                    clearPage();
                }
            } else if (updateStatus == 1 && workflow.isPrepareStatus()) {
                try {
                    siv.setProcessedOn(wfMmsProcessed.getProcessedOn());
                    siv.setStatus(Constants.PREPARE_VALUE);
                    wfMmsProcessed.setDecision(siv.getStatus());
                    siv.setProcessedBy(SessionBean.getUserId());
                    sivbeanLocal.edit(siv);
                    wfMmsProcessedBeanLocal.saveOrUpdate(wfMmsProcessed);
                    JsfUtil.addSuccessMessage("SIV Data is Updated ");
//                    clearPage();
                } catch (Exception ex) {

                }
                clearPage();
            } else if (updateStatus == 1 && (workflow.isApproveStatus())) {
                if (selectedValue.equalsIgnoreCase("Approve") && workflow.isApproveStatus()) {
//                    try {
                    System.out.println("12");
                    workflow.setUserStatus(Constants.APPROVE_VALUE);
                    siv.setStatus(Constants.APPROVE_VALUE);
                    wfMmsProcessed.setDecision(siv.getStatus());
                    if (saved) {
                        boolean status = updateBinCard(storeEntity.getStoreId(), storeRequisitionEntity.getSrNo());
                        clearPage();
                    } else {
                        JsfUtil.addFatalMessage("Procedure execution failed");
                    }
                } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isApproveStatus()) {
                    workflow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                    siv.setStatus(Constants.APPROVE_REJECT_VALUE);
                    wfMmsProcessed.setDecision(siv.getStatus());
                }
                wfMmsProcessedBeanLocal.create(wfMmsProcessed);
                sivbeanLocal.edit(siv);
                mmsSivList.remove(siv);
                JsfUtil.addSuccessMessage("Fixed Asset Return Data Modified");
                clearPage();

            }
        } else {
            JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
            EventEntry eventEntry = new EventEntry();
            eventEntry.setSessionId(SessionBean.getSessionID());
            eventEntry.setUserId(SessionBean.getUserId());
            QName qualifiedName = new QName("", "project");
            JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
            eventEntry.setUserLogin(test);
////..... add more information by calling fields defined in the object 
            security.addEventLog(eventEntry, dataset);
        }
    }
    /*This method is used to save Work Flow And Clear
     */

    public void saveWorkFlowAndClear() {

        siv.addSivIdToWorkflow(wfMmsProcessed);
        sivbeanLocal.edit(siv);
        wfMmsProcessedBeanLocal.saveOrUpdate(wfMmsProcessed);
        mmsSivList.remove(siv);
        saved = true;
    }
    /*This method is used to increament Id
     */

    public int increamentId() {
        int id = sivDetail.getSivDetId();
        id = id++;
        return id;
    }

    /*This method is used to clear Save
     */
    public String clearSave() {
        siv = null;
        storeRequisitionDetail = null;
        return null;
    }
    /*This method is used to clear Page
     */

    public String clearPage() {
        siv = new MmsSiv();
        updateStatus = 0;
        storeEntity = new MmsStoreInformation();
        storeRequisitionEntity = new MmsStorereq();
        storeRequisitionDetail = new MmsStorereqDetail();
        sivDetailsModel = null;
        saveorUpdateBundle = "Save";
        mmsSivSearchInfoDataModel = new ListDataModel<>();
        WorkflowDataModel = new ListDataModel<>();
        wfMmsProcessed = new WfMmsProcessed();
        sivDetail = null;
        setSelectedValue(null);
        mmsGrn = null;
        return null;
    }

    /*This method is used to recreate Siv Data Model
     */
    private void recreateSivDataModel() {
        System.out.println("====datamodel==");
        sivDetailsModel = null;
        sivDetailsModel = new ListDataModel(new ArrayList<>(siv.getMmsSivDetailList()));
        System.out.println("====datamodel==" + sivDetailsModel.getRowCount());

    }
    /*This method is used to clear Popup
     */

    private void clearPopup() {
        sivDetail = null;
    }
    /*This method is used to update Grn Detail
     */

    public void updateGrnDetail() {
        storeRequisitionDetail = getSivDetailsModel().getRowData();
    }

    /*This method is used to add Siv Info Detail
     */
    public String addSivInfoDetail() {
        siv.addSivDetialInfo(sivDetail);
        recreateSivDataModel();
        clearPopup();
        return null;
    }

    /*This method is used to search By SivNO
     */
    public ArrayList<MmsSiv> searchBySivNO(String sivNo) {
        ArrayList<MmsSiv> sivNumber = null;
        siv.setSivNo(sivNo);
        sivNumber = sivbeanLocal.searchBySivNo(siv);
        btn_saveUpdate = "Update";
        return sivNumber;
    }

    /*This method is used to search By Store Requisition NO
     */
    public ArrayList<MmsStorereq> searchByStoreRequisitionNO(String SRNo) {
        ArrayList<MmsStorereq> SRNumber = null;
        storeRequisitionEntity.setSrNo(SRNo);
        return SRNumber;
    }

    /*This method is used to get By Siv Numbers
     */
    public void getBySivNumbers(SelectEvent event) {

        siv = (MmsSiv) event.getObject();
        //read detail and fill
        MmsSivDetail sivdetailObject;
        sivdetailObject = sivdetailBeanLocal.getDetailbySivId(siv);
        for (int i = 0; i < siv.getMmsSivDetailList().size(); i++) {
            sivDetail = new MmsSivDetail();
            sivDetail.setItemId(siv.getMmsSivDetailList().get(i).getItemId());

            sivDetail.setQuantity(siv.getMmsSivDetailList().get(i).getQuantity());

            sivDetail.setUnitPrice(siv.getMmsSivDetailList().get(i).getUnitPrice());

            siv.addSivDetialInfo(sivDetail);
        }
        recreateSivDataModel();

        SivNumber = siv.getSivNo();
        //end filling data
        updateStatus = 1;
        btn_saveUpdate = "Update";

    }
    /*This method is used to get By SR Numbers
     */

    public void getBySRNumbers(SelectEvent event) {
        String selectedSrNo = event.getObject().toString();
        storeRequisitionEntity = storeRequisitionBeanLocal.getSrInfoBySrvNo(selectedSrNo);
        int srDtlSize = storeRequisitionEntity.getMmsStorereqDetailList().size();
        if (srDtlSize > 0) {
            recreateSivDataModel();

        }
    }

    /*This method is used to generate Siv No
     */
    public String generateSivNo() {
        if (updateStatus == 1) {
            newPayNo = siv.getSivNo();
        } else {
            MmsSiv lastPaymentNoObj = sivbeanLocal.getLastSIvNo();
            if (lastPaymentNoObj != null) {
                lastPaymentNo = lastPaymentNoObj.getSivId().toString();
            }

            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();

            int newPayment = 0;
            if (lastPaymentNo.equals("0")) {
                newPayment = 1;
                newPayNo = "SIV-" + newPayment + "/" + f.format(now);
            } else {

                String[] lastInspNos = lastPaymentNo.split("-");
                String lastDatesPatern = lastInspNos[0];

                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newPayment = Integer.parseInt(lastDatesPaterns[0]);
                newPayment = newPayment + 1;
                newPayNo = "SIV-" + newPayment + "/" + f.format(now);
            }
        }
        return newPayNo;
    }
    /*This method is used to go Back Search Button Action
     */

    public void goBackSearchButtonAction() {
        renderpnlToSearchPage = false;
        renderPnlCreateSIV = false;
        renderPnlManPage = true;
    }
    /*This method is used to create New SIV Info
     */

    public void createNewSIVInfo() {

        clearPage();
        saveorUpdateBundle = "Save";
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateSIV = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateSIV = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
    /*This method is used to btn Search Action
     */

    public void btn_Search_Action() {
        clearPage();
        renderPnlCreateSIV = false;
        renderPnlManPage = true;
    }
    /*This method is used to search Siv Information
     */

    public void searchSivInformation() {
        System.out.println("in search");
        siv.setProcessedBy(SessionBean.getUserId());
        System.out.println("processor " + siv.getProcessedBy());
        allSivInfoList = sivbeanLocal.getSivListsByParameter(columnNameResolver, siv, siv.getColumnValue());
        recerateSivSerachModel();
//        siv.setProcessedBy(SessionBean.getUserId());
//        if (storeEntity.getStoreId() != null && siv.getSivNo().isEmpty()) {
//            siv.setProcessedBy(SessionBean.getUserId());
//            allSivInfoList = sivbeanLocal.searchByStoreIdAndprocessedBy(storeEntity, siv);
//            recerateSivSerachModel();
//
//        } else if (storeEntity.getStoreId() != null && !siv.getSivNo().isEmpty()) {
//            allSivInfoList = sivbeanLocal.searchBySivNoAndStoreIdAndProcessedBy(siv, storeEntity);
//            recerateSivSerachModel();
//        }

    }
    /*This method is used to recerate Siv Serach Model
     */

    private void recerateSivSerachModel() {

        mmsSivSearchInfoDataModel = null;
        mmsSivSearchInfoDataModel = new ListDataModel(new ArrayList<>(allSivInfoList));
    }
    /*This method is used to on Row Edit Temp
     */

    public void onRowEditTemp(SelectEvent event) {
        siv = (MmsSiv) event.getObject();
        populateUI();
    }
    /*This method is used to on Row Edit
     */

    public void onRowEdit(SelectEvent event) {
        siv = (MmsSiv) event.getObject();
        int size = siv.getWfMmsProcessedList().size();
        if (workflow.isApproveStatus()) {
            if (siv.getWfMmsProcessedList().get(size - 1).getDecision() == Constants.CHECK_APPROVE_VALUE || siv.getWfMmsProcessedList().get(size - 1).getDecision() == Constants.APPROVE_VALUE) {
                setSelectedValue("Approve");
            } else if (siv.getWfMmsProcessedList().get(size - 1).getDecision() == Constants.CHECK_REJECT_VALUE || siv.getWfMmsProcessedList().get(size - 1).getDecision() == Constants.APPROVE_REJECT_VALUE) {
                setSelectedValue("Reject");
            }
            wfMmsProcessed.setProcessedOn(siv.getWfMmsProcessedList().get(size - 1).getProcessedOn());
            wfMmsProcessed.setCommentGiven(siv.getWfMmsProcessedList().get(size - 1).getCommentGiven());

        } else {
            //if the transaction is processed by others(checker,approver) disable editing for preparer
            if (size > 1) {

                workflow.setReadonly(true);
                disableButtonUpdate = true;
                workflow.setButtonStatus(true);
            }
        }

        populateUI();

    }
    /*This method is used to populateUI
     */

    public void populateUI() {
        storeRequisitionEntity = siv.getStoreReqId();
        storeEntity = siv.getStoreId();
        StoreList = new ArrayList<>();
        StoreList.add(storeEntity);
        srList = new ArrayList<>();
        srList.add(storeRequisitionEntity);
        renderPnlCreateSIV = true;
        renderPnlManPage = false;
        renderpnlToSearchPage = true;
        saveorUpdateBundle = "Update";
        setIsRenderedIconWorkflow(true);
        updateStatus = 1;
        wfMmsProcessed.setProcessedOn(siv.getProcessedOn());
        recreateSivDataModel();
        recreateWfDataModel();
        saveorUpdateButtonHandler();
    }
    /*This method is used to save or Update Button Handler
     */

    public void saveorUpdateButtonHandler() {
        if (workflow.isPrepareStatus()) {
            if (siv.getStatus().equals(Constants.APPROVE_VALUE) || siv.getStatus().equals(Constants.CHECK_APPROVE_VALUE)) {
                disableButtonUpdate = true;
            }
        } else if (workflow.isCheckStatus() || workflow.isApproveStatus()) {
            if (siv.getStatus().equals(Constants.APPROVE_VALUE)) {
                disableButtonUpdate = true;
            }
        }

    }
    /*This method is used to handle Select Store Name
     */

    public void handleSelectStoreName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            Integer Id = Integer.parseInt(event.getNewValue().toString());
            storeEntity.setStoreId(Id);
        }

    }
    /*This method is used to handle Select Store Name SIV
     */

    public void handleSelectStoreNameSIV(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            storeEntity.setStoreId(Integer.parseInt(event.getNewValue().toString()));
            srList = storeRequisitionBeanLocal.findAllwithApprovedStatus(storeEntity, Constants.APPROVE_VALUE);
        }

    }
    /*This method is used to on Select Request
     */

    public void onSelectRequest(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                siv = (MmsSiv) event.getNewValue();
                System.out.println("=========siv detail=====" + siv.getMmsSivDetailList().size());
                populateUI();
            }
        } catch (Exception e) {
        }
    }
    /*This method is used to update Bin Card
     */

    public boolean updateBinCard(int storeId, String srNo) {
        try {
            int status;
            status = binCardBeanLocal.deductFromBinCard(storeId, srNo);
            return status == 0;
        } catch (Exception ex) {
            return false;
        }

    }
    /*This method is used to recreateWfDataModel
     */

    public void recreateWfDataModel() {
        WfMmsProcessedDataModel = null;
        for (int i = 0; i < siv.getWfMmsProcessedList().size(); i++) {
            if (siv.getWfMmsProcessedList().get(i).getDecision() != null) {
                if (siv.getWfMmsProcessedList().get(i).getDecision() == 1 || siv.getWfMmsProcessedList().get(i).getDecision() == 3) {
                    siv.getWfMmsProcessedList().get(i).setWfDecison("Approved");
                } else if (siv.getWfMmsProcessedList().get(i).getDecision() == 2 || siv.getWfMmsProcessedList().get(i).getDecision() == 4) {
                    siv.getWfMmsProcessedList().get(i).setWfDecison("Rejected");
                }
            } else {
                siv.getWfMmsProcessedList().get(i).setWfDecison("Requested");
            }

        }

        WfMmsProcessedDataModel = new ListDataModel(siv.getWfMmsProcessedList());
    }
//</editor-fold>
}
