
package et.gov.eep.prms.controller;

//<editor-fold defaultstate="collapsed" desc="Import">
import et.gov.eep.commonApplications.businessLogic.PrmsLuDmArchiveBeanLocal;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.organization.HrDepAddressesBeanLocal;
import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.organization.HrDepAddresses;
import et.gov.eep.prms.businessLogic.ImportShippingInstructBeanLocal;
import et.gov.eep.prms.entity.PrmsImportShippingInstruct;
import et.gov.eep.prms.entity.PrmsLcRigistration;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.mapper.organization.HrDepartmentsFacade;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import java.io.InputStream;
import org.primefaces.context.RequestContext;
import webService.AAA;
import webService.IAdministration;
import webService.EventEntry;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.FileUploadEvent;
import securityBean.SessionBean;
import et.gov.eep.commonApplications.controller.DataUpload;
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import org.insa.model.DocumentModel;
import org.primefaces.model.StreamedContent;
import securityBean.WorkFlow;
import securityBean.Constants;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.prms.entity.PrmsLcRigistrationAmend;
//</editor-fold>

@Named(value = "ImportShippingInstructController")
@ViewScoped
     //<editor-fold defaultstate="collapsed" desc="Inject EJBs">
public class ImportShippingInstructController implements Serializable {
    
    @EJB
            ImportShippingInstructBeanLocal importShippingInstructBeanLocal;
    @EJB
            HrDepartmentsFacade hrDepartmentsFacade;
    @EJB
            WfMmsProcessedBeanLocal wfMmsProcessedBeanLocal;
    @EJB
            HrDepAddressesBeanLocal hrDepAddressesBeanLocal;
    @EJB
            PrmsLuDmArchiveBeanLocal prmsLuDmArchiveBeanLocal;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Inject Entities">
    @Inject
            PrmsImportShippingInstruct prmsImportShippingInstruct;
    @Inject
            HrDepartments departments;
    @Inject
            PrmsLcRigistration prmsLcRigistration;
    @Inject
            PrmsLcRigistrationAmend prmsLcRigistrationAmend;
    @Inject
            HrDepAddresses hrDepAddresses;
    @Inject
            HrAddresses hrAddresses;
    @Inject
            DataUpload dataUpload;
    @Inject
            WorkFlow workFlow;
    @Inject
            WfMmsProcessed wfMmsProcessed;
    @Inject
            SessionBean sessionBean;
    @Inject
            PrmsLuDmArchive prmsLuDmArchive;
     @Inject
    ColumnNameResolver columnNameResolver;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Declare fields">
    private String saveorUpdateBundle = "Save";
    private String selectedDecision = "";
    int saveStatus = 0;
    int requestNotificationCounter = 0;
    private String createOrSearchBundle = "New";
    private boolean disableBtnCreate = false;
    private String duplicattion = null;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;
    private boolean renderpnlToSearchPage;
    private boolean isRenderDecision = false;
    private boolean isRenderCreate = false;
    private boolean isRowFileSelected = false;
    private String activeIndex;
    private String icone = "ui-icon-plus";
    private String userName;
    private String fileName;
    private String docFormat;
    private byte[] byteArrayFile;
    private static List<HrDepartments> araListe;
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    int updateStatus = 0;
    int docReferenceId;
    private int approvedStatus = 3;
    StreamedContent downloadIt;
    DocumentModel docModel;
    PrmsImportShippingInstruct selectImportShippingInstruct;
    PrmsLuDmArchive prmsLuDmArchiveSelection;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Declare lists and models">
    DataModel<PrmsImportShippingInstruct> prmsImportShippingInstructModel;
    DataModel<DocumentModel> documentModelDM;
    DataModel<PrmsLuDmArchive> prmsLuDmArchiveDModel;
    List<PrmsImportShippingInstruct> ImportShippingInstructList;
    List<PrmsLcRigistrationAmend> lcNoAmededLists;
    List<PrmsLuDmArchive> prmsLuDmArchiveList;
    Set<String> importShippingInstructDetlCheck = new HashSet<>();
    List<PrmsLcRigistration> lcNoLists;
    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    List<Integer> docIdlist = new ArrayList<>();
    List<DocumentModel> documentModelList = new ArrayList<>();
    DataModel<WfMmsProcessed> wfMmsProcessedDModel;
     List<ColumnNameResolver> ColumnNameResolverList = new ArrayList<>();
//</editor-fold>
  
    //<editor-fold defaultstate="collapsed" desc="other methods">
    public void goBackToSearchpageBtnAction() {
        renderpnlToSearchPage = false;
        renderPnlCreateParty = false;
        renderPnlManPage = true;
    }
//</editor-fold>
    //Defualt Non argument methodst
    public ImportShippingInstructController() {
        
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getter and setter of variables">
    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }
    
    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }
    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }
    
    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }
    
    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }
    
    public String getDuplicattion() {
        return duplicattion;
    }
    
    public void setDuplicattion(String duplicattion) {
        this.duplicattion = duplicattion;
    }
    
    public boolean isRenderPnlCreateParty() {
        return renderPnlCreateParty;
    }
    
    public void setRenderPnlCreateParty(boolean renderPnlCreateParty) {
        this.renderPnlCreateParty = renderPnlCreateParty;
    }
    
    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }
    
    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
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
    
    public boolean isIsRowFileSelected() {
        return isRowFileSelected;
    }
    
    public void setIsRowFileSelected(boolean isRowFileSelected) {
        this.isRowFileSelected = isRowFileSelected;
    }
    
    public String getActiveIndex() {
        return activeIndex;
    }
    
    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
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
    
    public int getSaveStatus() {
        return saveStatus;
    }
    
    public void setSaveStatus(int saveStatus) {
        this.saveStatus = saveStatus;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public int getApprovedStatus() {
        return approvedStatus;
    }
    
    public void setApprovedStatus(int approvedStatus) {
        this.approvedStatus = approvedStatus;
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter and Setter of objects">
    public PrmsImportShippingInstruct getSelectImportShippingInstruct() {
        if (selectImportShippingInstruct == null) {
            selectImportShippingInstruct = new PrmsImportShippingInstruct();
        }
        return selectImportShippingInstruct;
    }
    
    public void setSelectImportShippingInstruct(PrmsImportShippingInstruct selectImportShippingInstruct) {
        this.selectImportShippingInstruct = selectImportShippingInstruct;
    }
    
    public PrmsLuDmArchive getPrmsLuDmArchiveSelection() {
        return prmsLuDmArchiveSelection;
    }
    
    public void setPrmsLuDmArchiveSelection(PrmsLuDmArchive prmsLuDmArchiveSelection) {
        this.prmsLuDmArchiveSelection = prmsLuDmArchiveSelection;
    }
    
    public PrmsImportShippingInstruct getPrmsImportShippingInstruct() {
        if (prmsImportShippingInstruct == null) {
            prmsImportShippingInstruct = new PrmsImportShippingInstruct();
        }
        return prmsImportShippingInstruct;
    }
    
    public void setPrmsImportShippingInstruct(PrmsImportShippingInstruct prmsImportShippingInstruct) {
        this.prmsImportShippingInstruct = prmsImportShippingInstruct;
    }
    
    public PrmsLcRigistration getPrmsLcRigistration() {
        if (prmsLcRigistration == null) {
            prmsLcRigistration = new PrmsLcRigistration();
        }
        return prmsLcRigistration;
    }
    
    public void setPrmsLcRigistration(PrmsLcRigistration prmsLcRigistration) {
        this.prmsLcRigistration = prmsLcRigistration;
    }
    
    public PrmsLcRigistrationAmend getPrmsLcRigistrationAmend() {
        if (prmsLcRigistrationAmend == null) {
            prmsLcRigistrationAmend = new PrmsLcRigistrationAmend();
        }
        return prmsLcRigistrationAmend;
    }
    
    public void setPrmsLcRigistrationAmend(PrmsLcRigistrationAmend prmsLcRigistrationAmend) {
        this.prmsLcRigistrationAmend = prmsLcRigistrationAmend;
    }
    
    public DataUpload getDataUpload() {
        return dataUpload;
    }
    
    public void setDataUpload(DataUpload dataUpload) {
        this.dataUpload = dataUpload;
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
    
    public PrmsLuDmArchive getPrmsLuDmArchive() {
        if (prmsLuDmArchive == null) {
            prmsLuDmArchive = new PrmsLuDmArchive();
        }
        return prmsLuDmArchive;
    }
    
    public void setPrmsLuDmArchive(PrmsLuDmArchive prmsLuDmArchive) {
        this.prmsLuDmArchive = prmsLuDmArchive;
    }
    
    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }
    
    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter and Setter of Lists and models">
    public DataModel<PrmsImportShippingInstruct> getprmsImportShippingInstructModel() {
        if (prmsImportShippingInstructModel == null) {
            prmsImportShippingInstructModel = new ListDataModel<>();
        }
        return prmsImportShippingInstructModel;
    }
    
    public void setprmsImportShippingInstructModel(DataModel<PrmsImportShippingInstruct> prmsImportShippingInstructModel) {
        this.prmsImportShippingInstructModel = prmsImportShippingInstructModel;
    }
    
    public DataModel<DocumentModel> getDocumentModelDM() {
        return documentModelDM;
    }

    public List<ColumnNameResolver> getColumnNameResolverList() {
        return ColumnNameResolverList;
    }

    public void setColumnNameResolverList(List<ColumnNameResolver> ColumnNameResolverList) {
        this.ColumnNameResolverList = ColumnNameResolverList;
    }
    
    public void setDocumentModelDM(DataModel<DocumentModel> documentModelDM) {
        this.documentModelDM = documentModelDM;
    }
    
    public DataModel<PrmsLuDmArchive> getPrmsLuDmArchiveDModel() {
        return prmsLuDmArchiveDModel;
    }

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }
    
    public void setPrmsLuDmArchiveDModel(DataModel<PrmsLuDmArchive> prmsLuDmArchiveDModel) {
        this.prmsLuDmArchiveDModel = prmsLuDmArchiveDModel;
    }
    
    public List<PrmsImportShippingInstruct> getImportShippingInstructList() {
        if (ImportShippingInstructList == null) {
            ImportShippingInstructList = new ArrayList<>();
        }
        return ImportShippingInstructList;
    }
    
    public void setImportShippingInstructList(List<PrmsImportShippingInstruct> ImportShippingInstructList) {
        this.ImportShippingInstructList = ImportShippingInstructList;
    }
    
    public List<PrmsLcRigistrationAmend> getLcNoAmededLists() {
        if (lcNoAmededLists == null) {
            lcNoAmededLists = new ArrayList<>();
        }
        return lcNoAmededLists;
    }
    
    public void setLcNoAmededLists(List<PrmsLcRigistrationAmend> lcNoAmededLists) {
        this.lcNoAmededLists = lcNoAmededLists;
    }
    
    public List<PrmsLuDmArchive> getPrmsLuDmArchiveList() {
        if (prmsLuDmArchiveList == null) {
            prmsLuDmArchiveList = new ArrayList<>();
        }
        return prmsLuDmArchiveList;
    }
    
    public void setPrmsLuDmArchiveList(List<PrmsLuDmArchive> prmsLuDmArchiveList) {
        this.prmsLuDmArchiveList = prmsLuDmArchiveList;
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
    
    public Set<String> getimportShippingInstructDetlCheck() {
        return importShippingInstructDetlCheck;
    }
    
    public void setimportShippingInstructDetlCheck(Set<String> importShippingInstructDetlCheck) {
        this.importShippingInstructDetlCheck = importShippingInstructDetlCheck;
    }
    
    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }
     public DataModel<PrmsImportShippingInstruct> getPrmsImportShippingInstructModel() {
        if (prmsImportShippingInstructModel == null) {
            prmsImportShippingInstructModel = new ListDataModel<>();
        }
        return prmsImportShippingInstructModel;
    }
    
    public void setPrmsImportShippingInstructModel(DataModel<PrmsImportShippingInstruct> prmsImportShippingInstructModel) {
        this.prmsImportShippingInstructModel = prmsImportShippingInstructModel;
    }
    
    public void recreatImportShippingInstructModel() {
        prmsImportShippingInstructModel = null;
        prmsImportShippingInstructModel = new ListDataModel<>(new ArrayList<>(getImportShippingInstructList()));
    }
    
    public void recreateWfMmsProcessed() {
        wfMmsProcessedDModel = null;
        wfMmsProcessedDModel = new ListDataModel<>(new ArrayList<>(prmsImportShippingInstruct.getWfMmsProcessedList()));
    }
    
    public void recreateFileUpload() {
        docIdlist = null;
        docIdlist = new ArrayList<>();
    }
//</editor-fold>
   
    //<editor-fold defaultstate="collapsed" desc="Rowselect and update">
    public void rowSelect(SelectEvent event) {
        prmsImportShippingInstruct = (PrmsImportShippingInstruct) event.getObject();
        populateDataForApp();
        
    }
    
    public void populateDataForApp() {
        prmsImportShippingInstruct.setId(prmsImportShippingInstruct.getId());
        prmsImportShippingInstruct = importShippingInstructBeanLocal.getSelectedRequest(prmsImportShippingInstruct.getId());
        prmsLcRigistration = prmsImportShippingInstruct.getLcId();
        departments = prmsImportShippingInstruct.getFromDepId();
        hrDepAddresses = hrDepAddressesBeanLocal.findDepartmentAddress(departments);
        renderPnlManPage = false;
        renderPnlCreateParty = true;
        renderpnlToSearchPage=true;
        if (prmsImportShippingInstruct.getFileReferenceNumber() != null) {
            prmsLuDmArchive = prmsImportShippingInstruct.getFileReferenceNumber();
            prmsLuDmArchiveList = prmsLuDmArchiveBeanLocal.getFileLists(prmsLuDmArchive);
            System.out.println(" ---w/n searching--Docid==" + prmsLuDmArchive.getDocumentId() + "    doc id from list=" + prmsLuDmArchiveList.get(0));
            System.out.println("File Name===" + prmsLuDmArchiveList.toString());
            for (int row = 0; row < prmsLuDmArchiveList.size(); row++) {
                fileName = prmsLuDmArchiveList.get(row).getFileName();
                docFormat = prmsLuDmArchiveList.get(row).getFileType();
                byteArrayFile = prmsLuDmArchiveList.get(row).getUploadFile();
            }
        }
        if (workFlow.isPrepareStatus()) {
            wfMmsProcessed.setProcessedOn(prmsImportShippingInstruct.getProcessedOn());
        }
        saveorUpdateBundle = "Update";
        recreatImportShippingInstructModel();
        recreateWfMmsProcessed();
        recreateFileUpload();
    }
    
    public void createNewImportShippingInstruct() {
        ClearPrmsImportShippingInstruct();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage=false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateParty = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateParty = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
    
    private String ClearPrmsImportShippingInstruct() {
        prmsImportShippingInstruct = null;
        prmsLcRigistration = null;
        departments = null;
        hrDepAddresses = null;
        renderPnlCreateParty = true;
        renderPnlManPage = false;
        prmsImportShippingInstructModel = null;
        ImportShippingInstructList = null;
        wfMmsProcessedDModel = null;
        documentModelDM = null;
        wfMmsProcessed.setProcessedOn(null);
        selectedDecision = "";
        wfMmsProcessed.setCommentGiven(null);
        return null;
    }
    
    public void changeLcNoEvent(ValueChangeEvent chnage) {
        if (chnage.getNewValue() != null) {
            prmsLcRigistration = (PrmsLcRigistration) chnage.getNewValue();
            System.out.println("Lc no" + prmsLcRigistration.getLcNo() + "Lc id" + prmsLcRigistration.getLcId() + "=====sup name=" + prmsLcRigistration.getContractId().getSuppId().getVendorName()
                    + "city" + prmsLcRigistration.getContractId().getSuppId().getCountryId());
            lcNoAmededLists = importShippingInstructBeanLocal.checkingAsLcIsAmendedByLcId(prmsLcRigistration);
            if (lcNoAmededLists.size() > 0) {
                prmsLcRigistrationAmend = importShippingInstructBeanLocal.getLcAmendedInfoByLcId(prmsLcRigistration);
                prmsLcRigistration.setLcAmount(prmsLcRigistrationAmend.getLcAmount());
                prmsLcRigistration.setForeignId(prmsLcRigistrationAmend.getForeignId());
                prmsLcRigistration.setContractId(prmsLcRigistrationAmend.getContractId());
            }
        }
    }
    
    public String doSomeAction() {
        System.out.println("check Dept from");
        if (departments.getDepName() == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "no node selected", "No node selected for From Attribute");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return null;
    }
    
    public void handleDecisionOptions(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedDecision = event.getNewValue().toString();
        }
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Save and Update methods">
    public String saveImportShippingInstruct() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "saveImportShippingInstruct", dataset)) {
                if (saveorUpdateBundle.equals("Save")) {
                    if (saveStatus == 0) {
                        try {
                            generateISINo();
                            prmsImportShippingInstruct.setIsiNo(newISINo);
                            prmsImportShippingInstruct.setFromDepId(departments);
                            prmsImportShippingInstruct.setLcId(prmsLcRigistration);
                            prmsImportShippingInstruct.setStatus(Constants.PREPARE_VALUE);
                            prmsImportShippingInstruct.setPreparedBy(wfMmsProcessed.getProcessedBy());
                            prmsImportShippingInstruct.setProcessedOn(wfMmsProcessed.getProcessedOn());
                            prmsImportShippingInstruct.getWfMmsProcessedList().add(wfMmsProcessed);
                            wfMmsProcessed.setDecision(prmsImportShippingInstruct.getStatus());
                            wfMmsProcessed.setIsId(prmsImportShippingInstruct);
                            prmsLuDmArchive.setFileName(fileName);
                            prmsLuDmArchive.setFileType(docFormat);
                            prmsLuDmArchive.setUploadFile(byteArrayFile);
                            prmsLuDmArchiveBeanLocal.saveFileInfo(prmsLuDmArchive);
                            prmsImportShippingInstruct.setFileReferenceNumber(prmsLuDmArchive);
                            importShippingInstructBeanLocal.create(prmsImportShippingInstruct);
                            JsfUtil.addSuccessMessage("Import Shipping is Saved successfully  at==" + newISINo);
                            return ClearPrmsImportShippingInstruct();
                            
                        } catch (Exception e) {
                            e.printStackTrace();
                            JsfUtil.addFatalMessage("some thing is going to error" + e);
                            ClearPrmsImportShippingInstruct();
                            return null;
                        }
                    }
                } else if (saveorUpdateBundle.equals("Update") && (workFlow.isPrepareStatus())) {
                    try {
                        prmsImportShippingInstruct.setLcId(prmsLcRigistration);
                        wfMmsProcessed.setIsId(prmsImportShippingInstruct);
                        prmsLuDmArchive.setFileName(fileName);
                        prmsLuDmArchive.setFileType(docFormat);
                        prmsLuDmArchive.setUploadFile(byteArrayFile);
                        prmsLuDmArchiveBeanLocal.updateFileInfo(prmsLuDmArchive);
                        prmsImportShippingInstruct.setFileReferenceNumber(prmsLuDmArchive);
                        importShippingInstructBeanLocal.update(prmsImportShippingInstruct);
                        JsfUtil.addSuccessMessage("Import shipping information is updated at " + prmsImportShippingInstruct.getIsiNo());
                        saveorUpdateBundle = "Save";
                        return ClearPrmsImportShippingInstruct();
                    } catch (Exception e) {
                        JsfUtil.addFatalMessage("error== w/n data modification" + e);
                        ClearPrmsImportShippingInstruct();
                    }
                } else if ((saveorUpdateBundle.equals("Update")) && (workFlow.isApproveStatus() || workFlow.isCheckStatus())) {
                    System.out.println("when Approver");
                    if (selectedDecision.equals("Approved") && workFlow.isApproveStatus()) {
                        wfMmsProcessed.setDecision(Constants.APPROVE_VALUE);
                        prmsImportShippingInstruct.setStatus(Constants.APPROVE_VALUE);
                    } else if (selectedDecision.equals("Rejected") && workFlow.isApproveStatus()) {
                        wfMmsProcessed.setDecision(Constants.APPROVE_REJECT_VALUE);
                        prmsImportShippingInstruct.setStatus(Constants.APPROVE_REJECT_VALUE);
                    } else if (selectedDecision.equals("Approved") && workFlow.isCheckStatus()) {
                        wfMmsProcessed.setDecision(Constants.CHECK_APPROVE_VALUE);
                        prmsImportShippingInstruct.setStatus(Constants.CHECK_APPROVE_VALUE);
                    } else if (selectedDecision.equals("Rejected") && workFlow.isCheckStatus()) {
                        wfMmsProcessed.setDecision(Constants.CHECK_REJECT_VALUE);
                        prmsImportShippingInstruct.setStatus(Constants.CHECK_REJECT_VALUE);
                    }
                    wfMmsProcessedBeanLocal.create(wfMmsProcessed);
                    importShippingInstructBeanLocal.update(prmsImportShippingInstruct);
                    JsfUtil.addSuccessMessage("You already Gave a Decision");
                    saveorUpdateBundle = "Save";
                    return ClearPrmsImportShippingInstruct();
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBean.getSessionID());
                eventEntry.setUserId(sessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBean.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            
        }
        return null;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Handler">
    public String getLastISINo() {
        return lastISINo;
    }
    
    public void setLastISINo(String lastISINo) {
        this.lastISINo = lastISINo;
    }
    
    public String getNewISINo() {
        return newISINo;
    }
    
    public void setNewISINo(String newISINo) {
        this.newISINo = newISINo;
    }
    
    String lastISINo = "0";
    String newISINo;
    
    public String generateISINo() {
        if (saveorUpdateBundle.equals("Update")) {
            newISINo = prmsImportShippingInstruct.getIsiNo();
        } else {
            
            PrmsImportShippingInstruct lastISINoObj = importShippingInstructBeanLocal.getLastISINo();
            if (lastISINoObj != null) {
                lastISINo = lastISINoObj.getId();
            }
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            
            int newISI;
            if (lastISINo.equals("0")) {
                newISI = 1;
                newISINo = "ISINo-" + newISI + "/" + f.format(now);
                
            } else {
                String[] lastISINos = lastISINo.split("-");
                String lastDatesPatern = lastISINos[0];
                
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newISI = Integer.parseInt(lastDatesPaterns[0]);
                newISI = newISI + 1;
                newISINo = "ISINo-" + newISI + "/" + f.format(now);
            }
        }
        return newISINo;
        
    }
    
    public Date minAcceptedDate() {
        
        return this.prmsImportShippingInstruct.getPreparedDate();
    }
    public void changeEventColumnName(ValueChangeEvent changeEvent) {
        if (changeEvent.getNewValue() != null) {
            columnNameResolver.setCol_Name_FromTable(changeEvent.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            prmsImportShippingInstruct.setColumnName(columnNameResolver.getCol_Name_FromTable());
        }
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col.toLowerCase()+":";
    }

    public void searchImportShippingInstructByISINo() {
        prmsImportShippingInstruct.setPreparedBy(sessionBean.getUserId());
        ImportShippingInstructList = importShippingInstructBeanLocal.getShippingListsByParameter(prmsImportShippingInstruct);
        recreatImportShippingInstructModel();
    }
    
    public List<PrmsLcRigistration> getLcNoLists() {
        if (lcNoLists == null) {
            lcNoLists = new ArrayList<>();
            lcNoLists = importShippingInstructBeanLocal.LcList(approvedStatus);
        }
        return lcNoLists;
    }
    
    public void setLcNoLists(List<PrmsLcRigistration> lcNoLists) {
        this.lcNoLists = lcNoLists;
    }
    
    public List<HrDepartments> getAllDepartmentsList() {
        return allDepartmentsList;
    }
    
    public void setAllDepartmentsList(List<HrDepartments> allDepartmentsList) {
        this.allDepartmentsList = allDepartmentsList;
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Postconstruct">
    @PostConstruct
    public void init() {
        allDepartmentsList = hrDepartmentsFacade.findAll();
        root = new DefaultTreeNode("Root", null);
        recursive(allDepartmentsList, 0, root);
        root2 = getRoot();
          ColumnNameResolverList = importShippingInstructBeanLocal.getColumnNameList();
         wfMmsProcessed.setProcessedBy(sessionBean.getUserId());
         setUserName(sessionBean.getUserName());
         System.out.println("is App==" + workFlow.isApproveStatus() + "is chech==" + workFlow.isCheckStatus() + "is prepa==" + workFlow.isPrepareStatus());
        wfMmsProcessed.setProcessedBy(sessionBean.getUserId());
        setUserName(sessionBean.getUserName());
        System.out.println("prep " + workFlow.isPrepareStatus() + "App " + workFlow.isApproveStatus() + "Check " + workFlow.isCheckStatus());
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
    
    //<editor-fold defaultstate="collapsed" desc="FileUpload">
    public HrDepartments getDepartments() {
        if (departments == null) {
            departments = new HrDepartments();
        }
        return departments;
    }
    
    public void setDepartments(HrDepartments departments) {
        this.departments = departments;
    }
    
    public void recursive(List<HrDepartments> liste, int id, TreeNode node) {
        araListe = new ArrayList<>();
        if (allDepartmentsList != null) {
            for (HrDepartments k : allDepartmentsList) {
                if (k.getParentId() != null && k.getParentId() == id) {
                    TreeNode childNode = new DefaultTreeNode(k.getDepId() + "--" + k.getDepName(), node);
                    araListe.add(k);
                    recursive(araListe, k.getDepId(), childNode);
                }
            }
        }
    }
    
    public TreeNode getRoot() {
        return root;
    }
    
    public void setRoot(TreeNode root) {
        this.root = root;
    }
    
    public TreeNode getRoot2() {
        return root2;
    }
    
    public void setRoot2(TreeNode root2) {
        this.root2 = root2;
    }
    
    public TreeNode getSelectedNode() {
        return selectedNode;
    }
    
    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }
    
    public void onNodeSelect(NodeSelectEvent event) {
        
        selectedNode = event.getTreeNode();
        int key = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);
        
        departments = importShippingInstructBeanLocal.getSelectDepartement(key);
        
        prmsImportShippingInstruct.setFromDepId(departments);
        findDepAddresses();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('dlgDep').hide();");
        
    }
    
    public HrDepAddresses getHrDepAddresses() {
        if (hrDepAddresses == null) {
            hrDepAddresses = new HrDepAddresses();
        }
        return hrDepAddresses;
    }
    
    public void setHrDepAddresses(HrDepAddresses hrDepAddresses) {
        this.hrDepAddresses = hrDepAddresses;
    }
    
    public HrAddresses getHrAddresses() {
        return hrAddresses;
    }
    
    public void setHrAddresses(HrAddresses hrAddresses) {
        this.hrAddresses = hrAddresses;
    }
    
    public void findDepAddresses() {
        System.out.println("To get Address====");
        hrDepAddresses = hrDepAddressesBeanLocal.findDepartmentAddress(departments);
        System.out.println("==DepId===" + hrDepAddresses.getId()
                + "----Location Id===" + hrDepAddresses.getLocationId()
                + "---hrAddress===" + hrDepAddresses.getLocationId().getAddressName());
    }
    
    public DocumentModel getDocModel() {
        return docModel;
    }
    
    public void setDocModel(DocumentModel docModel) {
        this.docModel = docModel;
    }
    
    //<editor-fold defaultstate="collapsed" desc="FileUpload">
    public void handleFileUpload(FileUploadEvent eve) {
        InputStream fileByteFile_ = null;
        fileName = eve.getFile().getFileName().split("\\.")[0];
        docFormat = eve.getFile().getFileName().split("\\.")[1];
        try {
            fileByteFile_ = eve.getFile().getInputstream();
        } catch (Exception e) {
            System.out.println("==Error===" + e.getMessage());
        }
        byteArrayFile = dataUpload.extractByteArray(fileByteFile_);
        if (byteArrayFile != null) {
            System.out.println("value is not -ve");
            JsfUtil.addSuccessMessage(fileName + " With Format" + docFormat + " and Uploaded Successfully!!!");
        }
    }
    
    public StreamedContent getDownloadIt() throws Exception {
        if (isRowFileSelected == true) {
            System.out.println("When Downloading");
            downloadIt = dataUpload.getPrmsFileRefNumber(prmsLuDmArchive);
            System.out.println("Your File ==" + downloadIt);
        } else {
            JsfUtil.addFatalMessage("Pls Select a Row File U want to Download");
        }
        
        return downloadIt;
    }
    
    public void setDownloadIt(StreamedContent downloadIt) {
        this.downloadIt = downloadIt;
    }
    
    public void whenRowSelected(SelectEvent selected) {
        System.out.println("Row is Selected");
        prmsLuDmArchive = (PrmsLuDmArchive) selected.getObject();
        System.out.println("you Selected File Name ===" + prmsLuDmArchive.getFileName() + " and File Format===" + prmsLuDmArchive.getFileType());
        setIsRowFileSelected(true);
    }
    
    public void getDocValue() {
        documentModelDM = dataUpload.selectListOfFileByDocId(docIdlist);
        System.out.println("Doc Size====" + documentModelDM.getRowCount());
    }
    
    public int getRequestNotificationCounter() {
        ImportShippingInstructList = importShippingInstructBeanLocal.getImportshipsRequested();
        requestNotificationCounter = ImportShippingInstructList.size();
        return requestNotificationCounter;
    }
    
    public void setRequestNotificationCounter(int requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }
    
    public String getSelectedDecision() {
        return selectedDecision;
    }
    
    public void setSelectedDecision(String selectedDecision) {
        this.selectedDecision = selectedDecision;
    }
    
    public void RequestListChange(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsImportShippingInstruct = (PrmsImportShippingInstruct) event.getNewValue();
            populateDataForApp();
        }
    }
    
}
//</editor-fold>
//</editor-fold>
