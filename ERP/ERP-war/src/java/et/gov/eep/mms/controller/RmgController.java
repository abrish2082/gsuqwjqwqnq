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
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
import et.gov.eep.mms.businessLogic.MmsInspectionBeanLocal;
import et.gov.eep.mms.businessLogic.MmsInspectionDetailBeanLocal;
import et.gov.eep.mms.businessLogic.MmsProjectBeanLocal;
import et.gov.eep.mms.businessLogic.MmsRmgBeanLocal;
import et.gov.eep.mms.businessLogic.MmsRmgdetailBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.entity.MmsInspection;
import et.gov.eep.mms.entity.MmsInspectionDetail;
import et.gov.eep.mms.entity.MmsProject;
import et.gov.eep.mms.entity.MmsRmg;
import et.gov.eep.mms.entity.MmsRmgDetail;
import et.gov.eep.mms.entity.MmsStoreInformation;

/**
 *
 * @author minab
 */
@Named(value = "rmgController")
@ViewScoped
public class RmgController implements Serializable {

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
//<editor-fold defaultstate="collapsed" desc="Entities">

    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    @Inject
    WfMmsProcessed wfMmsProcessed;
    @Inject
    MmsRmg rmgentity;
    @Inject
    ColumnNameResolver columnNameResolver;
    @Inject
    MmsStoreInformation storeEntity;
    @Inject
    MmsInspection inspectionEntity;
    @Inject
    MmsInspectionDetail inspectiondetailEntity;
    @Inject
    MmsRmgDetail rmgdetail;
    @Inject
    MmsProject project;
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="EJB">            
    @EJB
    WfMmsProcessedBeanLocal wfMmsProcessedBeanLocal;
    @EJB
    MmsInspectionBeanLocal inspectionBeanlocal;
    @EJB
    MmsRmgBeanLocal rmgBeanLocal;
    @EJB
    MmsInspectionDetailBeanLocal inspectiondetailBeanlocal;
    @EJB
    MmsRmgdetailBeanLocal rmgdetailBeanLocal;
    @EJB
    MmsProjectBeanLocal projectbeanlocal;
    @EJB
    MmsStoreInformationBeanLocal storeInterface;
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Fields">        
    String btn_SaveUpdate = "Save";
    int updateStatus = 0;
    String inspectionResultNo = "";
    String disableinspectionresult = "false";
    private String saveorUpdateBundle = "Save";
    private boolean isRenderedIconNitification = false;
    private String createOrSearchBundle = "New";
    private boolean renderDecision = false;
    private boolean renderPnlCreateRmg = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private String userName;
    private boolean isRenderedIconWorkflow = false;
    private boolean renderpnlToSearchPage;
    private String selectedValue = "";
    private List<MmsRmg> mmsRmgList;
    DataModel<MmsInspectionDetail> InspectionDetailsModel;
    DataModel<MmsRmgDetail> rmgDetailDataModel;
    List<WfMmsProcessed> WfList;
    DataModel<WfMmsProcessed> WorkflowDataModel = new ListDataModel<>();
    String newPayNo;
    String lastPaymentNo = "0";
    List<MmsInspection> InspectionResultList = new ArrayList<>();
    List<MmsStoreInformation> StoreList;
    private List<MmsRmg> allRmgInfoList;
    private DataModel<MmsRmg> mmsRmgSearchInfoDataModel;
    MmsInspection tempobj = new MmsInspection();
    private MmsRmg selectedItem;
    private List<String> mmsRmgColumnNameList;
    ColumnNames columnNames = new ColumnNames();
    List<ColumnNames> ColumnNamesList1 = new ArrayList<>();
    List<ColumnNameResolver> ColumnNamesList = new ArrayList<>();

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Postconstraction">   
    public RmgController() {
    }

    @PostConstruct
    public void init() {
        if (workflow.isApproveStatus() || workflow.isCheckStatus()) {
            if (workflow.isApproveStatus()) {
                System.out.println("app or check");
                mmsRmgList = rmgBeanLocal.findByStatus(Constants.PREPARE_VALUE);
            }
            renderDecision = true;
            isRenderedIconNitification = true;
        } else if (workflow.isPrepareStatus()) {
            System.out.println("prep");
            renderDecision = false;
            InspectionResultList = inspectionBeanlocal.findAllByStatus(Constants.APPROVE_VALUE);
            mmsRmgList = rmgBeanLocal.findByStatus(Constants.APPROVE_REJECT_VALUE);
            if (mmsRmgList != null) {
                isRenderedIconNitification = mmsRmgList.size() > 0;
            }

        }
        getMmsRmgColumnNameList();
        wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
    }

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="getter and setter">    
    public boolean isIsRenderedIconWorkflow() {
        return isRenderedIconWorkflow;
    }

    public void setIsRenderedIconWorkflow(boolean isRenderedIconWorkflow) {
        this.isRenderedIconWorkflow = isRenderedIconWorkflow;
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
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

    public boolean isIsRenderedIconNitification() {
        return isRenderedIconNitification;
    }

    public void setIsRenderedIconNitification(boolean isRenderedIconNitification) {
        this.isRenderedIconNitification = isRenderedIconNitification;
    }

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
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

    /**
     *
     * @return
     */
    public MmsRmgDetail getRmgdetail() {
        if (rmgdetail == null) {
            rmgdetail = new MmsRmgDetail();
        }
        return rmgdetail;
    }

    /**
     *
     * @param rmgdetail
     */
    public void setRmgdetail(MmsRmgDetail rmgdetail) {
        this.rmgdetail = rmgdetail;
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

    /**
     *
     * @return
     */
    public MmsRmg getRmgentity() {
        if (rmgentity == null) {
            rmgentity = new MmsRmg();
        }
        return rmgentity;
    }

    /**
     *
     * @param rmgentity
     */
    public void setRmgentity(MmsRmg rmgentity) {
        this.rmgentity = rmgentity;
    }

    /**
     *
     * @return
     */
    public String getBtn_SaveUpdate() {
        return btn_SaveUpdate;
    }

    /**
     *
     * @param btn_SaveUpdate
     */
    public void setBtn_SaveUpdate(String btn_SaveUpdate) {
        this.btn_SaveUpdate = btn_SaveUpdate;
    }

    /**
     *
     * @return
     */
    public MmsInspectionDetail getInspectiondetailEntity() {
        if (inspectiondetailEntity == null) {
            inspectiondetailEntity = new MmsInspectionDetail();
        }
        return inspectiondetailEntity;
    }

    /**
     *
     * @param inspectiondetailEntity
     */
    public void setInspectiondetailEntity(MmsInspectionDetail inspectiondetailEntity) {
        this.inspectiondetailEntity = inspectiondetailEntity;
    }

    /**
     *
     * @return
     */
    public MmsProject getProject() {
        if (project == null) {
            project = new MmsProject();
        }
        return project;
    }

    /**
     *
     * @param project
     */
    public void setProject(MmsProject project) {
        this.project = project;
    }

    /**
     *
     * @return
     */
    public String getDisableinspectionresult() {
        return disableinspectionresult;
    }

    /**
     *
     * @param disableinspectionresult
     */
    public void setDisableinspectionresult(String disableinspectionresult) {
        this.disableinspectionresult = disableinspectionresult;
    }

    public DataModel<MmsRmgDetail> getRmgDetailDataModel() {
        if (rmgDetailDataModel == null) {
            rmgDetailDataModel = new ListDataModel<>();

        }
        return rmgDetailDataModel;

    }

    public void setRmgDetailDataModel(DataModel<MmsRmgDetail> rmgDetailDataModel) {
        this.rmgDetailDataModel = rmgDetailDataModel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public boolean isRenderPnlCreateRmg() {
        return renderPnlCreateRmg;
    }

    public void setRenderPnlCreateRmg(boolean renderPnlCreateRmg) {
        this.renderPnlCreateRmg = renderPnlCreateRmg;
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

    public DataModel<MmsRmg> getMmsRmgSearchInfoDataModel() {
        if (mmsRmgSearchInfoDataModel == null) {
            mmsRmgSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsRmgSearchInfoDataModel;
    }

    public void setMmsRmgSearchInfoDataModel(DataModel<MmsRmg> mmsRmgSearchInfoDataModel) {
        this.mmsRmgSearchInfoDataModel = mmsRmgSearchInfoDataModel;
    }
    //for row selection

    public MmsRmg getSelectedItem() {
        if (mmsRmgSearchInfoDataModel == null) {
            mmsRmgSearchInfoDataModel = new ListDataModel<>();
        }
        return selectedItem;
    }

    public void setSelectedItem(MmsRmg selectedItem) {
        this.selectedItem = selectedItem;
    }

    public DataModel<MmsInspectionDetail> getInspectionDetailsModel() {
        if (InspectionDetailsModel == null) {
            InspectionDetailsModel = new ListDataModel<>();
        }
        return InspectionDetailsModel;
    }

    /**
     *
     * @param InspectionDetailsModel
     */
    public void setInspectionDetailsModel(DataModel<MmsInspectionDetail> InspectionDetailsModel) {
        this.InspectionDetailsModel = InspectionDetailsModel;
    }

    public List<MmsInspection> getInspectionResultList() {
        if (InspectionResultList == null) {
            InspectionResultList = new ArrayList<>();
        }
        return InspectionResultList;
    }

    public void setInspectionResultList(List<MmsInspection> InspectionResultList) {
        this.InspectionResultList = InspectionResultList;
    }

    public MmsInspection getInspectionEntity() {
        if (inspectionEntity == null) {
            inspectionEntity = new MmsInspection();
        }
        return inspectionEntity;
    }

    /**
     *
     * @param inspectionEntity
     */
    public void setInspectionEntity(MmsInspection inspectionEntity) {
        this.inspectionEntity = inspectionEntity;
    }

    /**
     *
     * @return
     */
    public String getInspectionResultNo() {
        return inspectionResultNo;
    }

    /**
     *
     * @param inspectionResultNo
     */
    public void setInspectionResultNo(String inspectionResultNo) {
        this.inspectionResultNo = inspectionResultNo;
    }

    public ColumnNames getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(ColumnNames columnNames) {
        this.columnNames = columnNames;
    }

    public List<ColumnNameResolver> getColumnNamesList() {
        return ColumnNamesList;
    }

    public void setColumnNamesList(List<ColumnNameResolver> ColumnNamesList) {
        this.ColumnNamesList = ColumnNamesList;
    }

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    

    public List<String> getMmsRmgColumnNameList() {
        mmsRmgColumnNameList = rmgBeanLocal.getMmsRmgColumnNameList();
        if (mmsRmgColumnNameList == null) {
            mmsRmgColumnNameList = new ArrayList<>();
        }
        System.out.println("=======mmsRmgColumnNameList==" + mmsRmgColumnNameList);
        if (mmsRmgColumnNameList.size() > 0) {
            ColumnNamesList = new ArrayList<>();
            for (int i = 0; i < mmsRmgColumnNameList.size(); i++) {
//                ColumnNames obj = new ColumnNames();
                ColumnNameResolver obj = new ColumnNameResolver();
                obj.setCol_Name_FromTable((mmsRmgColumnNameList.get(i).toLowerCase()));
                obj.setParsed_Col_Name(ColumnParser((mmsRmgColumnNameList.get(i).toLowerCase())));
                ColumnNamesList.add(obj);
                
            }

        }
        return mmsRmgColumnNameList;
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }

    public void setMmsRmgColumnNameList(List<String> mmsRmgColumnNameList) {
        this.mmsRmgColumnNameList = mmsRmgColumnNameList;
    }

    /**
     *
     * @return
     */
    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="handler,save,sreach and clear">  
    /*This method is used to btn Save Actions
     */
    public String btnSave_Actions() {
        generateRmgNo();
        rmgentity.setRmgNo(newPayNo);
        if (updateStatus == 0) {
            try {
                List<MmsInspectionDetail> inspectionDetailList;
                inspectionDetailList = inspectiondetailBeanlocal.getlistofInspectionDetails(inspectionEntity);

                int size = inspectionEntity.getMmsInspectionDetailList().size();

                for (int i = 0; i < size; i++) {

                    MmsRmgDetail rmgdetailObj = new MmsRmgDetail();
                    rmgdetailObj.setItemId(inspectionDetailList.get(i).getItemId());

                    rmgdetailObj.setUnitPrice(BigDecimal.valueOf(inspectionDetailList.get(i).getUnitPrice()));
                    rmgdetailObj.setQuantity(inspectionDetailList.get(i).getQuantity());
                    rmgentity.addRmgDetialInfo(rmgdetailObj);
                    rmgdetail.setRmgDetId(increamentId());

                }
                inspectionEntity.setStatus(1);
                inspectionBeanlocal.edit(inspectionEntity);

                for (int i = 0; i < size; i++) {

                    MmsRmgDetail rmgdetailObj = new MmsRmgDetail();
                    rmgdetailObj.setItemId(inspectionDetailList.get(i).getItemId());

                    rmgdetailObj.setUnitPrice((BigDecimal.valueOf(inspectionDetailList.get(i).getUnitPrice())));
                    rmgdetailObj.setQuantity(inspectionDetailList.get(i).getQuantity());
                    rmgdetailObj.setRemark(inspectionDetailList.get(i).getRemark());

                    rmgentity.addRmgDetialInfo(rmgdetailObj);
                    rmgdetail.setRmgDetId(increamentId());

                }
                inspectionEntity.setStatus(1);
                inspectionBeanlocal.edit(inspectionEntity);
                rmgBeanLocal.create(rmgentity);
                JsfUtil.addSuccessMessage("RMG Data is Created");
                return clearPage();

            } catch (EJBTransactionRolledbackException e) {
                JsfUtil.addErrorMessage("Something Error Occured on Data Saved");
                clearPage();
                return null;
            }
        } else {
            try {
                rmgentity.setInspectionResultNo(inspectionResultNo);
                rmgBeanLocal.edit(rmgentity);
                JsfUtil.addSuccessMessage("RMG data is Modified");
                btn_SaveUpdate = "Save";
                return clearPage();
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Something Error Occured on Data Modified");
                clearPage();

            }

        }
        return null;
    }
    /*This method is used to handle select Options Work flow
     */

    public void handleselectOptionswf(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selectedValue = event.getNewValue().toString();
        }
    }
    /*This method is used to column Name Change Event
     */

    public void columnNameChangeEvent(ValueChangeEvent event) {      
         if (event.getNewValue() != null) {
            System.out.println("event.getNewValue().toString()=="+event.getNewValue().toString());
            columnNameResolver.setCol_Name_FromTable(event.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            rmgentity.setColumnName(columnNameResolver.getCol_Name_FromTable());
            rmgentity.setColumnValue(null);
        }
    }
    /*This method is used to on Select Rmg Request
     */

    public void onSelectRmgRequest(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                rmgentity = (MmsRmg) event.getNewValue();

                populatUIRmg();

            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Faile to process! Try Again Reloading the Page");
        }
    }
    /*This method is used to Save workflow
     */

    public void Wfsave() {
        wfMmsProcessed.setRmgId(rmgentity);
        wfMmsProcessedBeanLocal.create(wfMmsProcessed);
    }
    /*This method is used to Save RMG
     */

    public void saveRmg() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveRmg", dataset)) {
                if (updateStatus == 0) {
                    System.out.println("rmg ifo save===" + wfMmsProcessed.getCommentGiven());
                    System.out.println("rmg entity===" + rmgentity.getRmgNo());
                    generateRmgNo();
                    rmgentity.setRmgNo(newPayNo);
                    rmgentity.setProcessedOn(wfMmsProcessed.getProcessedOn());
                    rmgentity.setProcessedBy(wfMmsProcessed.getProcessedBy());
                    rmgentity.setCommentGiven(wfMmsProcessed.getCommentGiven());
                    rmgentity.setStatus(Constants.PREPARE_VALUE);
                    wfMmsProcessed.setDecision(Constants.PREPARE_VALUE);
                    Wfsave();
                    rmgBeanLocal.create(rmgentity);

                    inspectionEntity.setStatus(Constants.INSPECTION_PROCESSED_BY_RMG);
                    inspectionBeanlocal.edit(inspectionEntity);
                    JsfUtil.addSuccessMessage("RMG Data Saved" + rmgentity.getRmgNo());
                    clearPage();
                } else if (updateStatus == 1 && workflow.isPrepareStatus()) {
                    rmgentity.setInspectionId(inspectionEntity);
                    rmgentity.setProcessedOn(wfMmsProcessed.getProcessedOn());
                    rmgentity.setProcessedBy(wfMmsProcessed.getProcessedBy());
                    rmgentity.setCommentGiven(wfMmsProcessed.getCommentGiven());
                    rmgentity.setStatus(Constants.PREPARE_VALUE);
                    wfMmsProcessed.setDecision(Constants.PREPARE_VALUE);
//                    setWorkFlowValuesRmg();
                    rmgBeanLocal.edit(rmgentity);
//               
                    mmsRmgList.remove(rmgentity);

                    JsfUtil.addSuccessMessage("RMG Data is Updated");
                    clearPage();
                } else if (updateStatus == 1 && (workflow.isApproveStatus() || workflow.isCheckStatus())) {
                    System.out.println("12");
                    if (selectedValue.equalsIgnoreCase("Approve") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_VALUE);
                        rmgentity.setStatus(Constants.APPROVE_VALUE);
                        wfMmsProcessed.setDecision(Constants.APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Approve") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        rmgentity.setStatus(Constants.CHECK_APPROVE_VALUE);
                        wfMmsProcessed.setDecision(Constants.CHECK_APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        rmgentity.setStatus(Constants.APPROVE_REJECT_VALUE);
                        wfMmsProcessed.setDecision(Constants.APPROVE_REJECT_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        rmgentity.setStatus(Constants.CHECK_REJECT_VALUE);
                        wfMmsProcessed.setDecision(Constants.CHECK_REJECT_VALUE);
                    }
                    wfMmsProcessed.setRmgId(rmgentity);

                    wfMmsProcessedBeanLocal.create(wfMmsProcessed);
                    rmgBeanLocal.edit(rmgentity);
                    mmsRmgList.remove(rmgentity);

                    clearPage();
                    JsfUtil.addSuccessMessage("RMG Data Updated");

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
    /*This method is used to Set WorkFlow Values Rmg
     */

    public void setWorkFlowValuesRmg() {
        rmgentity.setProcessedBy(SessionBean.getUserId());
        rmgentity.setProcessedOn(wfMmsProcessed.getProcessedOn());
        rmgentity.setStatus(Constants.PREPARE_VALUE);
    }

    /**
     *
     * @return
     */
    public int increamentId() {
        int id = rmgdetail.getRmgDetId();
        id = id++;
        return id;
    }
    /*This method is used to clearPage
     */

    public String clearPage() {
        rmgentity = new MmsRmg();
        updateStatus = 0;
        rmgdetail = new MmsRmgDetail();
        inspectionEntity = new MmsInspection();
        storeEntity = new MmsStoreInformation();
        InspectionDetailsModel = null;
        rmgDetailDataModel = null;
        wfMmsProcessed = null;
        setSelectedValue(null);
        return null;
    }

    /*This method is used to recreate Rmg DataModel
     */
    private void recreateRmgDataModel() {
        InspectionDetailsModel = null;
        InspectionDetailsModel = new ListDataModel(new ArrayList<>(inspectionEntity.getMmsInspectionDetailList()));
    }
    /*This method is used to add Rmg Info Detail
     */

    public String addRmgInfoDetail() {
        rmgentity.addRmgDetialInfo(rmgdetail);
        recreateRmgDataModel();
        return null;
    }

    /*This method is used to generateRmgNo
     */
    public String generateRmgNo() {
        if (updateStatus == 1) {
            newPayNo = rmgentity.getRmgNo();
        } else {
            MmsRmg lastPaymentNoObj = rmgBeanLocal.getLastrmgNo();
            if (lastPaymentNoObj != null) {
                lastPaymentNo = lastPaymentNoObj.getRmgId().toString();
            }

            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();

            int newPayment = 0;
            if (lastPaymentNo.equals("0")) {
                newPayment = 1;
                newPayNo = "RMG-" + newPayment + "/" + f.format(now);
            } else {

                String[] lastInspNos = lastPaymentNo.split("-");
                String lastDatesPatern = lastInspNos[0];

                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newPayment = Integer.parseInt(lastDatesPaterns[0]);
                newPayment = newPayment + 1;
                newPayNo = "RMG-" + newPayment + "/" + f.format(now);

            }
        }
        return newPayNo;
    }
    /*This method is used to get Selected Insp NumberInfo
     */

    public void getSelectedInspNumberInfo(ValueChangeEvent event) {
        String selectedInspNo = event.getNewValue().toString();
        inspectionEntity = inspectionBeanlocal.getInspectionInfoByInspNo(selectedInspNo);
        recreateRmgDataModel();

    }
    /*This method is used to handle Select InspectionNo
     */

    public void handleSelectInspectionNo(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            rmgDetailDataModel = null;
            inspectionEntity = (MmsInspection) event.getNewValue();

            for (int i = 0; i < inspectionEntity.getMmsInspectionDetailList().size(); i++) {
                rmgdetail = new MmsRmgDetail();
                rmgdetail.setItemId(inspectionEntity.getMmsInspectionDetailList().get(i).getItemId());
                rmgdetail.setQuantity(inspectionEntity.getMmsInspectionDetailList().get(i).getQuantity());
                rmgdetail.setRemark(inspectionEntity.getMmsInspectionDetailList().get(i).getRemark());

                rmgentity.addRMGDetialInfo(rmgdetail);
                recreateRmgDetailDataModel();

            }
            rmgentity.setInspectionId(inspectionEntity);

        }
    }
    /*This method is used to recreate Rmg Detail DataModel
     */

    public void recreateRmgDetailDataModel() {
        rmgDetailDataModel = null;
        rmgDetailDataModel = new ListDataModel(new ArrayList<>(rmgentity.getMmsRmgDetailList()));
    }
    /*This method is used to getStoreList
     */

    public List<MmsStoreInformation> getStoreList() {
        StoreList = storeInterface.findAllStoreInfo();
        return StoreList;
    }
    /*This method is used to handle Select StoreName
     */

    public void handleSelectStoreName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            Integer Id = Integer.parseInt(event.getNewValue().toString());
            storeEntity.setStoreId(Id);
            rmgentity.setRecevingStore(storeEntity);

//         
        }

    }
    /*This method is used to search By RmgNo
     */

    public ArrayList<MmsRmg> searchByRmgNo(String inspnumber) {
        ArrayList<MmsRmg> rmgNo = null;
        rmgentity.setRmgNo(inspnumber);
        rmgNo = rmgBeanLocal.searchByrmgNo(rmgentity);
        return rmgNo;

    }

    /*This method is used to get by RmgNo
     */
    public void getbyRmgNo(SelectEvent event) {
        rmgentity = (MmsRmg) event.getObject();
        rmgentity.getMmsRmgDetailList().clear();
        rmgDetailDataModel = null;
        MmsRmgDetail rmgdetailObject;
        rmgdetailObject = rmgdetailBeanLocal.getDetailbyId(rmgentity);
        for (int i = 0; i < rmgentity.getMmsRmgDetailList().size(); i++) {
            inspectiondetailEntity = new MmsInspectionDetail();
            inspectiondetailEntity.setItemId(rmgentity.getMmsRmgDetailList().get(i).getItemId());

            inspectiondetailEntity.setQuantity(rmgentity.getMmsRmgDetailList().get(i).getQuantity());

            inspectiondetailEntity.setUnitPrice(rmgentity.getMmsRmgDetailList().get(i).getUnitPrice().doubleValue());

            inspectionEntity.addInspectionDetialInfo(inspectiondetailEntity);
        }
        recreateRmgDataModel();

        inspectionResultNo = rmgentity.getInspectionResultNo();
        updateStatus = 1;
        disableinspectionresult = "true";
        btn_SaveUpdate = "Update";

    }

    /*This method is used to search Project JobNo
     */
    public ArrayList<MmsProject> searchProjectJobNo(String jobNo) {
        ArrayList<MmsProject> projects = null;
        project.setJobNo(jobNo);
        projects = projectbeanlocal.searchProjectJobNo(project);
        return projects;
    }

    /*This method is used to get project By JobNo
     */
    public void getByJobNo(SelectEvent event) {
        project = (MmsProject) event.getObject();
        project.getJobNo();

    }

    /*This method is used to go Back Search Button Action
     */
    public void goBackSearchButtonAction() {
        renderpnlToSearchPage = false;
        renderPnlCreateRmg = false;
        renderPnlManPage = true;
    }
    /*This method is used to create New RMG Info
     */

    public void createNewRMGInfo() {

        clearPage();
        saveorUpdateBundle = "Save";
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateRmg = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateRmg = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }

    }
    /*This method is used to btn Search Action RMG
     */

    public void btn_Search_Action_RMG() {
        clearPage();
        renderPnlCreateRmg = false;
        renderPnlManPage = true;
    }
    /*This method is used to search Rmg Information
     */

    public void searchRmgInformation() {
        System.out.println("in search");
        rmgentity.setProcessedBy(SessionBean.getUserId());
        System.out.println("processor " + rmgentity.getProcessedBy());
//        allRmgInfoList = rmgBeanLocal.getRMGListsByParameter(rmgentity);
        allRmgInfoList = rmgBeanLocal.searchByCol_NameAndCol_Value(columnNameResolver, rmgentity, rmgentity.getColumnValue());
        recerateRmgSerachModel();
//        if (workflow.isApproveStatus()) {
//            allRmgInfoList = rmgBeanLocal.findByStatus(Constants.APPROVE_VALUE);
//            recerateRmgSerachModel();
//        } else {
//            rmgentity.setProcessedBy(SessionBean.getUserId());
//
//            if (!rmgentity.getRmgNo().isEmpty()) {
//
//                allRmgInfoList = rmgBeanLocal.searchByrmgNoAndProcessedBy(rmgentity);
//
//                recerateRmgSerachModel();
//
//            } else {
//                allRmgInfoList = rmgBeanLocal.searchRMGByProcessedBy(rmgentity);
//                recerateRmgSerachModel();
//            }
//        }
    }
    /*This method is used to recerate Rmg SerachModel
     */

    private void recerateRmgSerachModel() {

        mmsRmgSearchInfoDataModel = null;
        mmsRmgSearchInfoDataModel = new ListDataModel(new ArrayList<>(allRmgInfoList));
    }
    /*This method is used to on Row Edit
     */

    public void onRowEdit(SelectEvent event) {

        rmgentity = (MmsRmg) event.getObject();
        populatUIRmg();
    }
    /*This method is used to populat UI Rmg
     */

    public void populatUIRmg() {
        inspectionEntity = (MmsInspection) rmgentity.getInspectionId();
        InspectionResultList = new ArrayList<>();
        InspectionResultList.add(inspectionEntity);
        System.out.println("===========inspection list size after====" + InspectionResultList.size());
        System.out.println("===========inspection====" + inspectionEntity);
        storeEntity = rmgentity.getRecevingStore();
        renderPnlCreateRmg = true;
        renderpnlToSearchPage = true;
        renderPnlManPage = false;

        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "New";
        setCreateOrSearchBundle(createOrSearchBundle);
        setIcone("ui-icon-plus");

        updateStatus = 1;
        if (workflow.isPrepareStatus()) {
            System.out.println("============" + rmgentity.getProcessedOn());
            wfMmsProcessed.setProcessedOn(rmgentity.getProcessedOn());
        }
        recreateRmgDetailDataModel();
        setIsRenderedIconWorkflow(true);
        recreateWfDataModel();

    }
    /*This method is used to recreate workflow DataModel
     */

    public void recreateWfDataModel() {
        WorkflowDataModel = null;
        for (int i = 0; i < rmgentity.getWfMmsProcessedList().size(); i++) {
            if (rmgentity.getWfMmsProcessedList().get(i).getDecision() != null) {
                if (rmgentity.getWfMmsProcessedList().get(i).getDecision() == 1 || rmgentity.getWfMmsProcessedList().get(i).getDecision() == 3) {
                    rmgentity.getWfMmsProcessedList().get(i).setWfDecison("Approved");
                } else if (rmgentity.getWfMmsProcessedList().get(i).getDecision() == 2 || rmgentity.getWfMmsProcessedList().get(i).getDecision() == 4) {
                    rmgentity.getWfMmsProcessedList().get(i).setWfDecison("Rejected");
                }
            } else {
                rmgentity.getWfMmsProcessedList().get(i).setWfDecison("Requested");
            }

        }

        WorkflowDataModel = new ListDataModel(rmgentity.getWfMmsProcessedList());
    }
    //</editor-fold>
}
