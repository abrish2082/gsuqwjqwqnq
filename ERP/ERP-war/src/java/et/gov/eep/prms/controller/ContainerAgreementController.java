

package et.gov.eep.prms.controller;

//<editor-fold defaultstate="collapsed" desc="imorts">
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.prms.businessLogic.ContainerAgreementBeanLocal;
import et.gov.eep.prms.businessLogic.PortEntryBeanLocal;
import et.gov.eep.prms.businessLogic.InsuranceNotificationToBankBeanLocal;
import et.gov.eep.prms.entity.PrmsContainerAgreement;
import et.gov.eep.prms.entity.PrmsPortEntry;
import et.gov.eep.mms.entity.MmsLuWareHouse;
import et.gov.eep.prms.entity.PrmsGoodsEntrance;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import webService.AAA;
import webService.IAdministration;
import webService.EventEntry;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import securityBean.SessionBean;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import javax.annotation.PostConstruct;
import securityBean.WorkFlow;
import securityBean.Constants;
//</editor-fold>

@Named(value = "ContAgreementController")
@ViewScoped

     //<editor-fold defaultstate="collapsed" desc="inject EJBs">
public class ContainerAgreementController implements Serializable {
    
    @EJB
            ContainerAgreementBeanLocal containerAgreementBeanLocal;
    @EJB
            PortEntryBeanLocal portEntryBeanLocal;
    @EJB
            InsuranceNotificationToBankBeanLocal insuranceNotificationToBankBeanLocal;
    @EJB
            WfMmsProcessedBeanLocal wfMmsProcessedBeanLocal;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="inject Entities">
    @Inject
            PrmsContainerAgreement prmsContainerAgreement;
    @Inject
            PrmsPortEntry portFrom;
    @Inject
            PrmsPortEntry portTo;
    @Inject
            PrmsPortEntry portVoyage;
    @Inject
            PrmsPortEntry portDry;
    @Inject
            PrmsPortEntry papmsPortEntry;
    @Inject
            PrmsGoodsEntrance prmsGoodsEntrance;
    @Inject
            WfMmsProcessed wfMmsProcessed;
    @Inject
            WorkFlow workFlow;
    @Inject
            MmsLuWareHouse mmsLuWareHouse;
    @Inject
    SessionBean SessionBean;
     @Inject
    ColumnNameResolver columnNameResolver;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declare fields">
    int saveStatus = 0;
    int updateStatus = 0;
    int requestNotificationCounter = 0;
    int approvedStatus = 3;
    private String activeIndex;
    private String icone = "ui-icon-plus";
    private String createOrSearchBundle = "New";
    private String saveorUpdateBundle = "Save";
    private String selectedDecision = "";
    private String userName;
    private boolean disableBtnCreate = false;
    private boolean renderPnlManPage = true;
    private boolean renderpnlToSearchPage;
    private boolean renderPnlCreateParty = false;
    private boolean isRenderDecision = false;
    private boolean isRenderCreate = true;
    private String _userName = "UserID";
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Declare list and models">
    private DataModel<PrmsContainerAgreement> prmsContainerAgreementModel;
    private DataModel<WfMmsProcessed> wfMmsProcessedDModel;
    List<PrmsContainerAgreement> prmsContainerAgreementList;
    private UploadedFile file;
    PrmsContainerAgreement prmscontainerAgreementSelection;
    List<PrmsPortEntry> nameOfPort;
    List<PrmsPortEntry> nameOfPortTo;
    List<PrmsPortEntry> nameOfPortVoyage;
    List<MmsLuWareHouse> mmsWarhouseLists;
    List<PrmsPortEntry> dryPort;
    List<PrmsGoodsEntrance> goodsEntranceNoLists;
    List<ColumnNameResolver> ColumnNameResolverList = new ArrayList<>();
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="postConstruct">
    @PostConstruct
    public void init() {
        ColumnNameResolverList = containerAgreementBeanLocal.getColumnNameList();
        wfMmsProcessed.setProcessedBy(workFlow.getUserAccount());
        setUserName(workFlow.getUserName());
        wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
          setUserName(SessionBean.getUserName());        
            System.out.println("is App==" + workFlow.isApproveStatus() + "is chech==" + workFlow.isCheckStatus() + "is prepa==" + workFlow.isPrepareStatus());
        if (workFlow.isPrepareStatus()) {
            isRenderDecision = false;
            isRenderCreate = true;
        
            
        }
        if (workFlow.isApproveStatus() || workFlow.isCheckStatus()) {
            isRenderDecision = true;
            isRenderCreate = false;
            
            
        }
       
       
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Other methods">
    public void goBackToSearchpageBtnAction() {
        renderpnlToSearchPage = false;
        renderPnlCreateParty = false;
        renderPnlManPage = true;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getter and setter of variables">
    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }
    
    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
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
    
    public int getSaveStatus() {
        return saveStatus;
    }
    
    public void setSaveStatus(int saveStatus) {
        this.saveStatus = saveStatus;
    }
    
    public int getUpdateStatus() {
        return updateStatus;
    }
    
    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }
    
    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }
    
    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }
    
    public String getActiveIndex() {
        return activeIndex;
    }
    
    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }
    
    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }
    
    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }
    
    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }
    
    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }
    
    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }
    
    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }
    
    public boolean isRenderPnlCreateParty() {
        return renderPnlCreateParty;
    }
    
    public void setRenderPnlCreateParty(boolean renderPnlCreateParty) {
        this.renderPnlCreateParty = renderPnlCreateParty;
    }
    
    public boolean isIsRenderDecision() {
        return isRenderDecision;
    }
    
    public void setIsRenderDecision(boolean isRenderDecision) {
        this.isRenderDecision = isRenderDecision;
    }
    
    public boolean isIsRenderCreate() {
        return isRenderCreate;
    }
    
    public void setIsRenderCreate(boolean isRenderCreate) {
        this.isRenderCreate = isRenderCreate;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getter and setter of objects">
    public PrmsContainerAgreement getPrmsContainerAgreement() {
        if (prmsContainerAgreement == null) {
            prmsContainerAgreement = new PrmsContainerAgreement();
        }
        return prmsContainerAgreement;
    }
    
    public void setPrmsContainerAgreement(PrmsContainerAgreement prmsContainerAgreement) {
        this.prmsContainerAgreement = prmsContainerAgreement;
    }
    
    public PrmsPortEntry getPortFrom() {
        if (portFrom == null) {
            portFrom = new PrmsPortEntry();
        }
        return portFrom;
    }
    
    public void setPortFrom(PrmsPortEntry portFrom) {
        this.portFrom = portFrom;
    }
    
    public PrmsPortEntry getPortTo() {
        if (portTo == null) {
            portTo = new PrmsPortEntry();
        }
        return portTo;
    }
    
    public void setPortTo(PrmsPortEntry portTo) {
        this.portTo = portTo;
    }
    
    public PrmsPortEntry getPortVoyage() {
        if (portVoyage == null) {
            portVoyage = new PrmsPortEntry();
        }
        return portVoyage;
    }
    
    public void setPortVoyage(PrmsPortEntry portVoyage) {
        this.portVoyage = portVoyage;
    }
    
    public PrmsPortEntry getPortDry() {
        if (portDry == null) {
            portDry = new PrmsPortEntry();
        }
        return portDry;
    }
    
    public void setPortDry(PrmsPortEntry portDry) {
        this.portDry = portDry;
    }
    
    public PrmsPortEntry getPapmsPortEntry() {
        if (papmsPortEntry == null) {
            papmsPortEntry = new PrmsPortEntry();
        }
        return papmsPortEntry;
    }
    
    public void setPapmsPortEntry(PrmsPortEntry papmsPortEntry) {
        this.papmsPortEntry = papmsPortEntry;
    }
    
    public PrmsGoodsEntrance getPrmsGoodsEntrance() {
        if (prmsGoodsEntrance == null) {
            prmsGoodsEntrance = new PrmsGoodsEntrance();
        }
        return prmsGoodsEntrance;
    }
    
    public void setPrmsGoodsEntrance(PrmsGoodsEntrance prmsGoodsEntrance) {
        this.prmsGoodsEntrance = prmsGoodsEntrance;
    }
    
    public MmsLuWareHouse getMmsLuWareHouse() {
        if (mmsLuWareHouse == null) {
            mmsLuWareHouse = new MmsLuWareHouse();
        }
        return mmsLuWareHouse;
    }
    
    public void setMmsLuWareHouse(MmsLuWareHouse mmsLuWareHouse) {
        this.mmsLuWareHouse = mmsLuWareHouse;
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
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getter and setter of lists and models">
    public List<PrmsContainerAgreement> getPrmsContainerAgreementList() {
        if (prmsContainerAgreementList == null) {
            prmsContainerAgreementList = new ArrayList<>();
        }
        return prmsContainerAgreementList;
    }
    
    public void setPrmsContainerAgreementList(List<PrmsContainerAgreement> prmsContainerAgreementList) {
        this.prmsContainerAgreementList = prmsContainerAgreementList;
    }
    
    public List<PrmsGoodsEntrance> getGoodsEntranceNoLists() {
        if (goodsEntranceNoLists == null) {
            goodsEntranceNoLists = new ArrayList<>();
            goodsEntranceNoLists = containerAgreementBeanLocal.getgoodsEntranceNoLists(approvedStatus);
        }
        return goodsEntranceNoLists;
    }
    
    public void setGoodsEntranceNoLists(List<PrmsGoodsEntrance> goodsEntranceNoLists) {
        this.goodsEntranceNoLists = goodsEntranceNoLists;
    }
    
    public DataModel<PrmsContainerAgreement> getPrmsContainerAgreementModel() {
        if (prmsContainerAgreementModel == null) {
            prmsContainerAgreementModel = new ListDataModel<>();
        }
        return prmsContainerAgreementModel;
    }
    
    public void setPrmsContainerAgreementModel(DataModel<PrmsContainerAgreement> prmsContainerAgreementModel) {
        this.prmsContainerAgreementModel = prmsContainerAgreementModel;
    }
    
    public DataModel<WfMmsProcessed> getWfMmsProcessedDModel() {
        if (wfMmsProcessedDModel == null) {
            wfMmsProcessedDModel = new ListDataModel<>();
        }
        return wfMmsProcessedDModel;
    }
    
    public void setWfMmsProcessedDModel(DataModel<WfMmsProcessed> wfMmsProcessedDModel) {
        this.wfMmsProcessedDModel = wfMmsProcessedDModel;
    }

    public ColumnNameResolver getColumnNameResolver() {
          if (columnNameResolver == null) {
            columnNameResolver = new ColumnNameResolver();
        }
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    public List<ColumnNameResolver> getColumnNameResolverList() {
        return ColumnNameResolverList;
    }

    public void setColumnNameResolverList(List<ColumnNameResolver> ColumnNameResolverList) {
        this.ColumnNameResolverList = ColumnNameResolverList;
    }
    
    
//</editor-fold>
   
    //<editor-fold defaultstate="collapsed" desc="Event changes">
    public void handleDecisionOptions(ValueChangeEvent decision) {
        if (decision.getNewValue() != null) {
            selectedDecision = decision.getNewValue().toString();
        }
    }
    
    public String getSelectedDecision() {
        return selectedDecision;
    }
    
    public void setSelectedDecision(String selectedDecision) {
        this.selectedDecision = selectedDecision;
    }
    public void changeEventColumnName(ValueChangeEvent changeEvent) {
        if (changeEvent.getNewValue() != null) {
            columnNameResolver.setCol_Name_FromTable(changeEvent.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            prmsContainerAgreement.setColumnName(columnNameResolver.getCol_Name_FromTable());
        }
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col.toLowerCase()+":";
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="FileUpload">
    public UploadedFile getFile() {
        return file;
    }
    
    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public void upload() {
        if (file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Search & RecreateDatamodel">
    public PrmsContainerAgreement getPrmscontainerAgreementSelection() {
        return prmscontainerAgreementSelection;
    }
    
    public void setPrmscontainerAgreementSelection(PrmsContainerAgreement prmscontainerAgreementSelection) {
        this.prmscontainerAgreementSelection = prmscontainerAgreementSelection;
    }
    
    public void searchContainerAgreementInfo() {      
        System.out.println("in search");
        prmsContainerAgreement.setPreparedBy(SessionBean.getUserId());
        prmsContainerAgreementList = containerAgreementBeanLocal.getContainerListsByParameter(prmsContainerAgreement);
        recreateprmsContainerAgreementModel();
        
    }
    
    private void recreateprmsContainerAgreementModel() {
        prmsContainerAgreementModel = null;
        prmsContainerAgreementModel = new ListDataModel(new ArrayList<>(getPrmsContainerAgreementList()));
    }
    
    public void recreateWfMmsProcessed() {
        wfMmsProcessedDModel = null;
        wfMmsProcessedDModel = new ListDataModel<>(new ArrayList<>(prmsContainerAgreement.getWfMmsProcessedList()));
    }
    String msg = "Impossible to Save From and To Port by thesame id/Name";
    
    public List<MmsLuWareHouse> getMmsWarhouseLists() {
        setMmsWarhouseLists(containerAgreementBeanLocal.storeLocationLists());
        return mmsWarhouseLists;
    }
    
    public void setMmsWarhouseLists(List<MmsLuWareHouse> mmsWarhouseLists) {
        this.mmsWarhouseLists = mmsWarhouseLists;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Update & Save method">
    public String saveContainerAgreementInfo() {
        
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveContainerAgreementInfo", dataset)) {
                if (saveorUpdateBundle.equals("Save")) {
                    if (saveStatus == 0) {
                        
                        try {
                            generateFormNo();
                            if (saveorUpdateBundle.equals("Save")) {
                                prmsContainerAgreement.setFormNo(newFormNo);
                                prmsContainerAgreement.setPortfkId(portFrom);
                                prmsContainerAgreement.setWarehouseLocation(mmsLuWareHouse);
                                prmsContainerAgreement.setDryPort(portDry);
                                prmsContainerAgreement.setGoodEntranceId(prmsGoodsEntrance);
                                prmsContainerAgreement.setStatus(Constants.PREPARE_VALUE);
                                wfMmsProcessed.setContainerId(prmsContainerAgreement);
                                prmsContainerAgreement.getWfMmsProcessedList().add(wfMmsProcessed);
                                wfMmsProcessed.setDecision(prmsContainerAgreement.getStatus());
                                prmsContainerAgreement.setPreparedBy(wfMmsProcessed.getProcessedBy());
                                prmsContainerAgreement.setProcessedOn(wfMmsProcessed.getProcessedOn());
                                System.out.println("42");
                                containerAgreementBeanLocal.create(prmsContainerAgreement);
                                JsfUtil.addSuccessMessage("New Container Agreement is Saved successfully with " + newFormNo);
                                return ClearContainerAgree();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            JsfUtil.addFatalMessage("some thing is going to error" + e);
                            ClearContainerAgree();
                            return null;
                            
                        }
                    }
                } else if (saveorUpdateBundle.equals("Update") && workFlow.isPrepareStatus()) {
                    try {
                        prmsContainerAgreement.getFormNo();
                        prmsContainerAgreement.setPortfkId(portFrom);
                        prmsContainerAgreement.setDryPort(portDry);
                        prmsContainerAgreement.setWarehouseLocation(mmsLuWareHouse);
                        containerAgreementBeanLocal.update(prmsContainerAgreement);
                        JsfUtil.addSuccessMessage("Agreement information is Updated Successfully at " + prmsContainerAgreement.getFormNo());
                        saveorUpdateBundle = "Save";
                        return ClearContainerAgree();
                    } catch (Exception e) {
                        JsfUtil.addFatalMessage("error== w/n data modification" + e);
                        ClearContainerAgree();
                    }
                    
                } else if ((saveorUpdateBundle.equals("Update")) && (workFlow.isApproveStatus() || workFlow.isCheckStatus())) {
                    System.out.println("when Approving");
                    wfMmsProcessed.setContainerId(prmsContainerAgreement);
                    if (selectedDecision.equals("Approved") && workFlow.isApproveStatus()) {
                        wfMmsProcessed.setDecision(Constants.APPROVE_VALUE);
                        prmsContainerAgreement.setStatus(Constants.APPROVE_VALUE);
                    } else if (selectedDecision.equals("Rejected") && workFlow.isApproveStatus()) {
                        wfMmsProcessed.setDecision(Constants.APPROVE_REJECT_VALUE);
                        prmsContainerAgreement.setStatus(Constants.APPROVE_REJECT_VALUE);
                    } else if (selectedDecision.equals("Approved") && workFlow.isCheckStatus()) {
                        wfMmsProcessed.setDecision(Constants.CHECK_APPROVE_VALUE);
                        prmsContainerAgreement.setStatus(Constants.CHECK_APPROVE_VALUE);
                    } else if (selectedDecision.equals("Rejected") && workFlow.isCheckStatus()) {
                        wfMmsProcessed.setDecision(Constants.CHECK_REJECT_VALUE);
                        prmsContainerAgreement.setStatus(Constants.CHECK_REJECT_VALUE);
                    }
                    wfMmsProcessedBeanLocal.create(wfMmsProcessed);
                    containerAgreementBeanLocal.update(prmsContainerAgreement);
                    JsfUtil.addSuccessMessage("You already Gave a Decision");
                    saveorUpdateBundle = "Save";
                    return ClearContainerAgree();
                    
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
            ex.printStackTrace();
            
        }
        
        return null;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Clear Pages">
    private String ClearContainerAgree() {
        prmsContainerAgreement = null;
        portDry = null;
        portFrom = null;
        mmsLuWareHouse = null;
        prmsContainerAgreementList = null;
        prmsContainerAgreementModel = null;
        prmsGoodsEntrance = null;
        wfMmsProcessed.setProcessedOn(null);
        selectedDecision = "";
        wfMmsProcessed.setCommentGiven(null);
        renderPnlCreateParty = true;
        renderPnlManPage = false;
        return null;
        
    }
    
//</editor-fold>
    public void changeWareHouses(ValueChangeEvent WareHouseEvent) {
        if (WareHouseEvent.getNewValue() != null) {
            mmsLuWareHouse.setId(Integer.parseInt(WareHouseEvent.getNewValue().toString()));
            mmsLuWareHouse = containerAgreementBeanLocal.changeWareHouses(mmsLuWareHouse);
        }
    }
    
    public void updatePortFrom(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            portFrom.setPortId(new BigDecimal(event.getNewValue().toString()));
            portFrom = containerAgreementBeanLocal.portNameUpdate(portFrom);
        }
    }
    
    public void updatePortTo(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            portTo.setPortId(new BigDecimal(event.getNewValue().toString()));
            portTo = containerAgreementBeanLocal.portNameUpdate(papmsPortEntry);
        }
    }
    
    public void updatePortVoyage(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            portVoyage.setPortId(new BigDecimal(event.getNewValue().toString()));
            portVoyage = containerAgreementBeanLocal.portNameUpdate(papmsPortEntry);
        }
    }
    
    public void updateDryPort(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            portDry.setPortId(new BigDecimal(event.getNewValue().toString()));
            portDry = containerAgreementBeanLocal.portNameUpdate(papmsPortEntry);
        }
        
    }
    
    public void updateGoodsEnterNo(ValueChangeEvent change) {
        if (!change.getNewValue().toString().isEmpty()) {
            prmsGoodsEntrance = (PrmsGoodsEntrance) change.getNewValue();
            prmsContainerAgreement.setGoodEntranceId(prmsGoodsEntrance);
        }
        
    }
    
    public String getLastFrmNo() {
        return lastFrmNo;
    }
    
    public void setLastFrmNo(String lastFrmNo) {
        this.lastFrmNo = lastFrmNo;
    }
    
    public String getNewFormNo() {
        return newFormNo;
    }
    
    public void setNewFormNo(String newFormNo) {
        this.newFormNo = newFormNo;
    }
    
    public void createNewContAgree() {
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage=false;
        if (createOrSearchBundle.equals("New")) {
            ClearContainerAgree();
            renderPnlCreateParty = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        }
        else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateParty = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
    
    String lastFrmNo = "0";
    String newFormNo;
    
    public String generateFormNo() {
        
        if (saveorUpdateBundle.equals("Update")) {
            
            newFormNo = prmsContainerAgreement.getFormNo();
            
        } else {
            
            PrmsContainerAgreement lastFrmNoObj = containerAgreementBeanLocal.getLastFormNo();
            if (lastFrmNoObj != null) {
                lastFrmNo = lastFrmNoObj.getContainerId().toString();
            }
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            
            int newForm = 0;
            if (lastFrmNo.equals("0")) {
                newForm = 1;
                newFormNo = "CA-" + newForm + "/" + f.format(now);
                
            } else {
                String[] lastFormNos = lastFrmNo.split("-");
                String lastDatesPatern = lastFormNos[0];
                
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newForm = Integer.parseInt(lastDatesPaterns[0]);
                
                newForm = newForm + 1;
                newFormNo = "CA-" + newForm + "/" + f.format(now);
            }
            
        }
        return newFormNo;
        
    }
    
    public int getRequestNotificationCounter() {
        prmsContainerAgreementList = containerAgreementBeanLocal.getcontAgreeReqLists();
        requestNotificationCounter = prmsContainerAgreementList.size();
        return requestNotificationCounter;
    }
    
    public void setRequestNotificationCounter(int requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }
    
    public int getApprovedStatus() {
        return approvedStatus;
    }
    
    public void setApprovedStatus(int approvedStatus) {
        this.approvedStatus = approvedStatus;
    }
    
    
    public void RequestListChange(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsContainerAgreement = (PrmsContainerAgreement) event.getNewValue();
            populateDataForApp();
        }
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Rowselect & update">
    public void onRowSelect(SelectEvent event) {
        prmsContainerAgreement = (PrmsContainerAgreement) event.getObject();
        populateDataForApp();
    }
    
    public void populateDataForApp() {
        prmsContainerAgreement.setFormNo(prmsContainerAgreement.getFormNo());
        portFrom = prmsContainerAgreement.getPortfkId();
        mmsLuWareHouse = prmsContainerAgreement.getWarehouseLocation();
        prmsGoodsEntrance = prmsContainerAgreement.getGoodEntranceId();
        if (workFlow.isPrepareStatus()) {
            wfMmsProcessed.setProcessedOn(prmsContainerAgreement.getProcessedOn());
        }
        portDry = prmsContainerAgreement.getDryPort();
        prmsContainerAgreement = containerAgreementBeanLocal.getSelectedRequest(prmsContainerAgreement.getFormNo());
        renderPnlManPage = false;
        renderPnlCreateParty = true;
        renderpnlToSearchPage=true;
        saveorUpdateBundle = "Update";
        recreateprmsContainerAgreementModel();
        recreateWfMmsProcessed();
    }
    
    public void onRowEdit(RowEditEvent event) {
        renderPnlCreateParty = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        saveStatus = 1;
        
        int rowIndex = prmsContainerAgreementModel.getRowIndex();
        prmsContainerAgreement = prmsContainerAgreementList.get(rowIndex);
        recreateprmsContainerAgreementModel();
        
    }
    
    public Date getminReturnDate() {
        return this.prmsContainerAgreement.getContEntranceDate();
    }
    
    public List<PrmsPortEntry> getNameOfPort() {
        if (nameOfPort == null) {
            nameOfPort = new ArrayList<>();
        }
        nameOfPort = insuranceNotificationToBankBeanLocal.fromPortEntry();
        return nameOfPort;
    }
    
    public void setNameOfPort(List<PrmsPortEntry> nameOfPort) {
        this.nameOfPort = nameOfPort;
    }
    
    public List<PrmsPortEntry> getNameOfPortTo() {
        if (nameOfPortTo == null) {
            nameOfPortTo = new ArrayList<>();
            nameOfPortTo = insuranceNotificationToBankBeanLocal.fromPortEntry();
            
        }
        return nameOfPortTo;
    }
    
    public void setNameOfPortTo(List<PrmsPortEntry> nameOfPortTo) {
        this.nameOfPortTo = nameOfPortTo;
    }
    
    public List<PrmsPortEntry> getDryPort() {
        if (dryPort == null) {
            dryPort = new ArrayList<>();
        }
        dryPort = portEntryBeanLocal.DryPortList();
        return dryPort;
    }
    
    public void setDryPort(List<PrmsPortEntry> dryPort) {
        this.dryPort = dryPort;
    }
}
//</editor-fold>
