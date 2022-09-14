/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.controller;

    // <editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.commonApplications.businessLogic.EthiopianCalendarBeanLocal;
import et.gov.eep.commonApplications.businessLogic.PrmsLuDmArchiveBeanLocal;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.prms.businessLogic.Bank_Clearance_RegistrationBeanLocal;
import et.gov.eep.prms.businessLogic.ContractInformationBeanLocal;
import et.gov.eep.prms.businessLogic.InsuranceRegisterationBeanLocal;
import et.gov.eep.prms.businessLogic.VendorRegBeanLocal;
import et.gov.eep.prms.entity.PrmsBankClearance;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.entity.PrmsLcRigistration;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import securityBean.SessionBean;
import webService.AAA;
import webService.IAdministration;
import webService.EventEntry;
import java.io.InputStream;
import org.primefaces.event.FileUploadEvent;
import et.gov.eep.commonApplications.controller.DataUpload;
import org.insa.model.DocumentModel;
import org.primefaces.model.StreamedContent;
import securityBean.WorkFlow;
import securityBean.Constants;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import javax.annotation.PostConstruct;
import et.gov.eep.commonApplications.businessLogic.WfPrmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.prms.entity.PrmsLcRigistrationAmend;
// </editor-fold>

//Bank Declaration Clearance view scoped CDI Named Bean class
@Named("BCR_BankClearanceRegistrationController")
@ViewScoped
public class BankClearanceRegistrationController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    InsuranceRegisterationBeanLocal insuranceRegisterationBeanLocal;
    @EJB
    ContractInformationBeanLocal contractInformationBeanLocal;
    @EJB
    Bank_Clearance_RegistrationBeanLocal bank_Clearance_RegistrationBeanLocal;
    @EJB
    VendorRegBeanLocal vendorRegBeanLocal;
    @EJB
    WfPrmsProcessedBeanLocal wfPrmsProcessedBeanLocal;
    @EJB
    PrmsLuDmArchiveBeanLocal prmsLuDmArchiveBeanLocal;
    @EJB
    EthiopianCalendarBeanLocal ethiopianCalendarBeanLocal;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Inject Entities">
    @Inject
    SessionBean sessionBean;
    @Inject
    PrmsBankClearance bankClearanceRegistration;
    @Inject
    PrmsContract prmsContract;
    @Inject
    PrmsSupplyProfile prmsSupplyProfile;
    @Inject
    PrmsLcRigistration prmsLcRigistration;
    @Inject
    PrmsLcRigistrationAmend prmsLcRigistrationAmend;
    @Inject
    DataUpload dataUpload;
    @Inject
    WfPrmsProcessed wfPrmsProcessed;
    @Inject
    WorkFlow workFlow;
    @Inject
    PrmsLuDmArchive prmsLuDmArchive;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Declared Lists and models">
    List<PrmsBankClearance> bankClearanceRegistList;
    List<PrmsBankClearance> bankClearanceSearchParameterLst;
    List<PrmsSupplyProfile> supplierLists;
    List<PrmsContract> contractNoLists;
    List<PrmsLcRigistration> lcNoLists;
    List<PrmsLcRigistrationAmend> amendedLcNoLists;
    List<PrmsLuDmArchive> prmsLuDmArchivesList;
    List<Integer> docIds = new ArrayList<>();
    private DataModel<PrmsBankClearance> bankClearanceRegistModel;
    private DataModel<WfPrmsProcessed> wfPrmsProcessedDModel;
    private DataModel<PrmsLuDmArchive> prmsLuDmArchivesDModel;
    PrmsBankClearance bankClearanceSelection;
    PrmsLuDmArchive prmsLuDmArchiveSelection;
    DataModel<DocumentModel> docList;
    DocumentModel docModel;
    StreamedContent downloading;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Declared Variables">
    int saveStatus = 0;
    private String icone = "ui-icon-plus";
    private String saveorUpdateBundle = "Save";
    private String userName;
    private boolean disableBtnCreate = false;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;
    private boolean renderpnlToSearchPage;
    private boolean disableLCNo = false;
    private boolean isrenderDecision = false;
    private boolean isRowFileSelected = false;
    private String createOrSearchBundle = "New";
    String selectedDecision = "";
    private String activeIndex;
    int updateStatus = 0;
    int requestNotificationCounter = 0;
    int approvedStatus = 3;
    int fileRefNum;
    private String checkAWBbillType = "AWB";
    private String fileName;
    private String docFormat;
    String lastClearanceNo = "0";
    String newClearanceNo;
    private byte[] byteArrayData;
    private boolean renderAWB = true;
    private boolean renderBL = false;
    private double invoice;
    private double permit;
    private double cleared;
    private boolean isRenderCreate = false;

    // </editor-fold>
    public BankClearanceRegistrationController() {
    }

    // callBack life cycle method
    @PostConstruct
    public void init() {
        wfPrmsProcessed.setProcessedBy(sessionBean.getUserId());
        setUserName(workFlow.getUserName());
        if (workFlow.isPrepareStatus()) {
            isrenderDecision = false;
            isRenderCreate = true;
        }
        if (workFlow.isApproveStatus() || workFlow.isCheckStatus()) {
            isrenderDecision = true;
            isRenderCreate = false;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="getter and setter of objects">
    public PrmsSupplyProfile getPrmsSupplyProfile() {
        if (prmsSupplyProfile == null) {
            prmsSupplyProfile = new PrmsSupplyProfile();
        }
        return prmsSupplyProfile;
    }

    public void setPrmsSupplyProfile(PrmsSupplyProfile prmsSupplyProfile) {
        this.prmsSupplyProfile = prmsSupplyProfile;
    }

    public PrmsBankClearance getBankClearanceRegistration() {
        if (bankClearanceRegistration == null) {
            bankClearanceRegistration = new PrmsBankClearance();
        }
        return bankClearanceRegistration;
    }

    public void setBankClearanceRegistration(PrmsBankClearance bankClearanceRegistration) {
        this.bankClearanceRegistration = bankClearanceRegistration;
    }

    public DataUpload getDataUpload() {
        if (dataUpload == null) {
            dataUpload = new DataUpload();
        }
        return dataUpload;
    }

    public void setDataUpload(DataUpload dataUpload) {
        this.dataUpload = dataUpload;
    }

    public WfPrmsProcessed getWfPrmsProcessed() {
        if (wfPrmsProcessed == null) {
            wfPrmsProcessed = new WfPrmsProcessed();
        }
        return wfPrmsProcessed;
    }

    public void setWfPrmsProcessed(WfPrmsProcessed wfPrmsProcessed) {
        this.wfPrmsProcessed = wfPrmsProcessed;
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

    public PrmsContract getPrmsContracts() {
        if (prmsContract == null) {
            prmsContract = new PrmsContract();
        }
        return prmsContract;
    }

    public void setPrmsContracts(PrmsContract prmsContracts) {
        this.prmsContract = prmsContracts;
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

    public PrmsBankClearance getBankClearanceSelection() {
        return bankClearanceSelection;
    }

    public void setBankClearanceSelection(PrmsBankClearance bankClearanceSelection) {
        this.bankClearanceSelection = bankClearanceSelection;
    }

    public PrmsLuDmArchive getPrmsLuDmArchiveSelection() {
        return prmsLuDmArchiveSelection;
    }

    public void setPrmsLuDmArchiveSelection(PrmsLuDmArchive prmsLuDmArchiveSelection) {
        this.prmsLuDmArchiveSelection = prmsLuDmArchiveSelection;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getter and setter of variables">
    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
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

    public boolean isRenderPnlCreateParty() {
        return renderPnlCreateParty;
    }

    public void setRenderPnlCreateParty(boolean renderPnlCreateParty) {
        this.renderPnlCreateParty = renderPnlCreateParty;
    }

    public boolean isDisableLCNo() {
        return disableLCNo;
    }

    public void setDisableLCNo(boolean disableLCNo) {
        this.disableLCNo = disableLCNo;
    }

    public boolean isIsrenderDecision() {
        return isrenderDecision;
    }

    public void setIsrenderDecision(boolean isrenderDecision) {
        this.isrenderDecision = isrenderDecision;
    }

    public boolean isIsRowFileSelected() {
        return isRowFileSelected;
    }

    public void setIsRowFileSelected(boolean isRowFileSelected) {
        this.isRowFileSelected = isRowFileSelected;
    }

    public int getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(int saveStatus) {
        this.saveStatus = saveStatus;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
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

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public int getApprovedStatus() {
        return approvedStatus;
    }

    public void setApprovedStatus(int approvedStatus) {
        this.approvedStatus = approvedStatus;
    }

    public int getRequestNotificationCounter() {
        bankClearanceRegistList = bank_Clearance_RegistrationBeanLocal.getBankClearanceReq();
        requestNotificationCounter = bankClearanceRegistList.size();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(int requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    public double getInvoice() {
        return invoice;
    }

    public void setInvoice(double invoice) {
        this.invoice = invoice;
    }

    public double getPermit() {
        return permit;
    }

    public void setPermit(double permit) {
        this.permit = permit;
    }

    public double getCleared() {
        return cleared;
    }

    public void setCleared(double cleared) {
        this.cleared = cleared;
    }

    public boolean isRenderAWB() {
        return renderAWB;
    }

    public void setRenderAWB(boolean renderAWB) {
        this.renderAWB = renderAWB;
    }

    public boolean isRenderBL() {
        return renderBL;
    }

    public void setRenderBL(boolean renderBL) {
        this.renderBL = renderBL;
    }

    public String getCheckAWBbillType() {
        return checkAWBbillType;
    }

    public void setCheckAWBbillType(String checkAWBbillType) {
        this.checkAWBbillType = checkAWBbillType;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public boolean isIsRenderCreate() {
        return isRenderCreate;
    }

    public void setIsRenderCreate(boolean isRenderCreate) {
        this.isRenderCreate = isRenderCreate;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getter and setter of Lists and models">
    public List<PrmsBankClearance> getBankClearanceRegistList() {
        if (bankClearanceRegistList == null) {
            bankClearanceRegistList = new ArrayList<>();
        }
        return bankClearanceRegistList;
    }

    public void setBankClearanceRegistList(List<PrmsBankClearance> bankClearanceRegistList) {
        this.bankClearanceRegistList = bankClearanceRegistList;
    }

    public List<PrmsBankClearance> getBankClearanceSearchParameterLst() {
        if (bankClearanceSearchParameterLst == null) {
            bankClearanceSearchParameterLst = new ArrayList<>();
            bankClearanceSearchParameterLst = bank_Clearance_RegistrationBeanLocal.getBankClearanceSearchParameterLst();
        }
        return bankClearanceSearchParameterLst;
    }

    public void setBankClearanceSearchParameterLst(List<PrmsBankClearance> bankClearanceSearchParameterLst) {
        this.bankClearanceSearchParameterLst = bankClearanceSearchParameterLst;
    }

    public List<PrmsContract> getContractNoLists() {
        if (contractNoLists == null) {
            contractNoLists = new ArrayList<>();
        }
        contractNoLists = contractInformationBeanLocal.ContractList();
        return contractNoLists;
    }

    public void setContractNoLists(List<PrmsContract> contractNoLists) {
        this.contractNoLists = contractNoLists;
    }

    private void recreatebankClearanceRegistModel() {
        bankClearanceRegistModel = null;
        bankClearanceRegistModel = new ListDataModel(new ArrayList<>(getBankClearanceRegistList()));
    }

    public void recreateWfPrmsProcessed() {
        wfPrmsProcessedDModel = null;
        wfPrmsProcessedDModel = new ListDataModel<>(new ArrayList<>(bankClearanceRegistration.getWfPrmsProcessedLists()));
    }

    public void recreateFileUpload() {
        prmsLuDmArchivesDModel = null;
        prmsLuDmArchivesDModel = new ListDataModel<>(prmsLuDmArchivesList);
    }

    public List<PrmsSupplyProfile> getSupplierLists() {
        if (supplierLists == null) {
            supplierLists = new ArrayList<>();
        }
        supplierLists = vendorRegBeanLocal.getVondorName();
        return supplierLists;
    }

    public void setSupplierLists(List<PrmsSupplyProfile> supplierLists) {
        this.supplierLists = supplierLists;
    }

    public List<PrmsLcRigistration> getLcNoLists() {
        if (lcNoLists == null) {
            lcNoLists = new ArrayList<>();
            lcNoLists = bank_Clearance_RegistrationBeanLocal.getLcNolists(approvedStatus);
        }
        return lcNoLists;
    }

    public void setLcNoLists(List<PrmsLcRigistration> lcNoLists) {
        this.lcNoLists = lcNoLists;
    }

    public List<PrmsLcRigistrationAmend> getAmendedLcNoLists() {
        if (amendedLcNoLists == null) {
            amendedLcNoLists = new ArrayList<>();
        }
        return amendedLcNoLists;
    }

    public void setAmendedLcNoLists(List<PrmsLcRigistrationAmend> amendedLcNoLists) {
        this.amendedLcNoLists = amendedLcNoLists;
    }

    public List<PrmsLuDmArchive> getPrmsLuDmArchivesList() {
        if (prmsLuDmArchivesList == null) {
            prmsLuDmArchivesList = new ArrayList<>();
        }
        return prmsLuDmArchivesList;
    }

    public void setPrmsLuDmArchivesList(List<PrmsLuDmArchive> prmsLuDmArchivesList) {
        this.prmsLuDmArchivesList = prmsLuDmArchivesList;
    }

    public DataModel<PrmsBankClearance> getBankClearanceRegistModel() {
        if (bankClearanceRegistModel == null) {
            bankClearanceRegistModel = new ListDataModel();
        }
        return bankClearanceRegistModel;
    }

    public void setBankClearanceRegistModel(DataModel<PrmsBankClearance> bankClearanceRegistModel) {
        this.bankClearanceRegistModel = bankClearanceRegistModel;
    }

    public DataModel<WfPrmsProcessed> getWfPrmsProcessedDModel() {
        if (wfPrmsProcessedDModel == null) {
            wfPrmsProcessedDModel = new ListDataModel<>();
        }
        return wfPrmsProcessedDModel;
    }

    public void setWfPrmsProcessedDModel(DataModel<WfPrmsProcessed> wfPrmsProcessedDModel) {
        this.wfPrmsProcessedDModel = wfPrmsProcessedDModel;
    }

    public DataModel<PrmsLuDmArchive> getPrmsLuDmArchivesDModel() {
        return prmsLuDmArchivesDModel;
    }

    public void setPrmsLuDmArchivesDModel(DataModel<PrmsLuDmArchive> prmsLuDmArchivesDModel) {
        this.prmsLuDmArchivesDModel = prmsLuDmArchivesDModel;
    }

    public DataModel<DocumentModel> getDocList() {
        return docList;
    }

    public void setDocList(DataModel<DocumentModel> docList) {
        this.docList = docList;
    }

    public DocumentModel getDocModel() {
        docModel = dataUpload.getDocumentModel();
        return docModel;
    }

    public void setDocModel(DocumentModel docModel) {
        this.docModel = docModel;
    }

    /*
     * This Method will be called when download button is clicked
     */
    public StreamedContent getDownloading() throws Exception {
        if (isRowFileSelected == true) {
            System.out.println("Action of download");
            downloading = dataUpload.getPrmsFileRefNumber(prmsLuDmArchive);
            System.out.println("PREP = " + downloading);
        } else {
            JsfUtil.addFatalMessage("Pls Select a Row U want to Download");
        }
        return downloading;
    }

    public void setDownloading(StreamedContent downloading) {
        this.downloading = downloading;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="other methods">
    public String generateclearanceNo() {

        if (saveorUpdateBundle.equals("Update")) {
            newClearanceNo = bankClearanceRegistration.getClearanceNo();
            checkAWBbillType = bankClearanceRegistration.getBillType();

        } else {
            PrmsBankClearance lastClearanceNoObj = bank_Clearance_RegistrationBeanLocal.getLastClearanceNo();
            if (lastClearanceNoObj != null) {
                lastClearanceNo = lastClearanceNoObj.getClearanceId().toString();
            }
//            DateFormat f = new SimpleDateFormat("yyyy");
//            Date now = new Date();
            String eYear = ethiopianCalendarBeanLocal.getEthiopianCurrentDate();
            System.out.println("Current Ethiopian Year " + eYear);
            int newCNo = 0;
            if (lastClearanceNo.equals("0")) {
                newCNo = 1;
                newClearanceNo = "BC-" + newCNo + "/" + eYear;
            } else {
                String[] lastCNos = lastClearanceNo.split("-");
                String lastDatesPatern = lastCNos[0];

                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newCNo = Integer.parseInt(lastDatesPaterns[0]);

                newCNo = newCNo + 1;
                newClearanceNo = "BC-" + newCNo + "/" + eYear;

            }

        }
        return newClearanceNo;
    }

    public void populateForApp() {
        bankClearanceRegistration.setClearanceNo(bankClearanceRegistration.getClearanceNo());

        prmsSupplyProfile = bankClearanceRegistration.getSupplierId();
        prmsContract = bankClearanceRegistration.getContractId();
        prmsLcRigistration = bankClearanceRegistration.getLetterOfCreditNo();
        invoice = bankClearanceRegistration.getInvoiceAmount();
        permit = bankClearanceRegistration.getPermitAmount();
        cleared = bankClearanceRegistration.getClearedAmount();
        renderPnlCreateParty = true;
        renderPnlManPage = false;
        disableLCNo = true;
        renderpnlToSearchPage = true;
        if (bankClearanceRegistration.getAttachmentRefNumber() != null) {
            prmsLuDmArchive = bankClearanceRegistration.getAttachmentRefNumber();
//            docIds.add(fileRefNum);
//            getDocValiId();
            prmsLuDmArchivesList = prmsLuDmArchiveBeanLocal.getFileLists(prmsLuDmArchive);
            System.out.println("when searching doc id==" + prmsLuDmArchive.getDocumentId());
            System.out.println("File Name is " + prmsLuDmArchivesList.get(0).getFileName());

        }
        if (workFlow.isPrepareStatus()) {
            wfPrmsProcessed.setProcessedOn(bankClearanceRegistration.getProcessedDate());
        }
        saveorUpdateBundle = "Update";
        recreatebankClearanceRegistModel();
        recreateWfPrmsProcessed();
        recreateFileUpload();
    }

    public void createNewParty() {
//        clearBCRform();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            clearBCRform();
            renderPnlCreateParty = true;
            renderPnlManPage = false;
            disableLCNo = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateParty = false;
            renderPnlManPage = true;
            disableLCNo = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public void findBankClearanceInfo() {
       
        bankClearanceRegistration.setPreparedBy(workFlow.getUserAccount());
        bankClearanceRegistList = bank_Clearance_RegistrationBeanLocal.findClearanceNo(bankClearanceRegistration);
        recreatebankClearanceRegistModel();
        bankClearanceRegistration=new PrmsBankClearance();
    }

    public void getDocValiId() {
        docList = dataUpload.selectListOfFileByDocId(docIds);
        System.out.println("No of your document is " + docList.getRowCount());
    }

    public void goBackSearchPageBtnAction() {
        renderPnlManPage = true;
        renderPnlCreateParty = false;
        renderpnlToSearchPage = false;
    }

    public void handleupdateTotal() {
        permit = prmsLcRigistration.getPermitAmount();
        cleared = permit - invoice;
        if (cleared < 0) {
            JsfUtil.addFatalMessage("Invoice must >= Permit Amount");
        }
    }

    public void searchActionBC() {
        clearBCRform();
        renderPnlCreateParty = false;
        renderPnlManPage = true;
    }

    public String clearBCRform() {
        bankClearanceRegistration = null;
        prmsSupplyProfile = null;
        checkAWBbillType = "AWB";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        renderpnlToSearchPage = false;
        prmsContract = null;
        bankClearanceRegistList = null;
        bankClearanceRegistModel = null;
        wfPrmsProcessedDModel = null;
        prmsLuDmArchivesDModel = null;
        docList = null;
        permit = 0.0;
        cleared = 0.0;
        invoice = 0.0;
        prmsLcRigistration = null;
        wfPrmsProcessed.setProcessedOn(null);
        wfPrmsProcessed.setDecision(null);
        wfPrmsProcessed.setCommentGiven(null);
        return null;
    }
     // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Change Events">
    public void onRowSelect(SelectEvent event) {
        bankClearanceRegistration = (PrmsBankClearance) event.getObject();
        populateForApp();
        if (workFlow.isPrepareStatus()) {
            if (bankClearanceRegistration.getStatus() == 0 || bankClearanceRegistration.getStatus() == 4 || bankClearanceRegistration.getStatus() == 2) {
                System.out.println("Not");
                workFlow.setReadonly(false);
            } else {
                System.out.println("yes");
                workFlow.setReadonly(true);
            }
        }

    }

    public void changecheckAWBbillType(ValueChangeEvent event) {
        bankClearanceRegistration.setBillType(checkAWBbillType);
        if (null != event.getNewValue()) {
            if (event.getNewValue().toString().equalsIgnoreCase("AWB")) {
                setRenderAWB(true);
                setRenderBL(false);
                bankClearanceRegistration.setBillType("AWB");
            } else {
                setRenderAWB(false);
                setRenderBL(true);
                bankClearanceRegistration.setBillType("BL");
            }
        }
    }

    public void onRowSelectForApprove(SelectEvent event) {
        bankClearanceRegistration = (PrmsBankClearance) event.getObject();
        populateForApp();

    }

    public void onRowEdit(RowEditEvent event) {
        renderPnlCreateParty = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updateStatus = 1;

        int rowIndex = bankClearanceRegistModel.getRowIndex();
        bankClearanceRegistration = bankClearanceRegistList.get(rowIndex);
        recreatebankClearanceRegistModel();
    }

    public void RequestListChange(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            bankClearanceRegistration = (PrmsBankClearance) event.getNewValue();
            populateForApp();
        }
    }

    public void updateLists(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            prmsSupplyProfile.setId(event.getNewValue().toString());
            prmsSupplyProfile = bank_Clearance_RegistrationBeanLocal.getVondorName(prmsSupplyProfile);
        }
    }

    public void updateConNum(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            prmsContract.setContractId(new BigDecimal(event.getNewValue().toString()));
            prmsContract = bank_Clearance_RegistrationBeanLocal.getContNumber(prmsContract);
        }
    }

    public void getClearanceNo(SelectEvent event) {
        String clearanceNo = event.getObject().toString();
        bankClearanceRegistration.setClearanceNo(clearanceNo);
        bankClearanceRegistration = bank_Clearance_RegistrationBeanLocal.getClearanceNo(bankClearanceRegistration);
        saveStatus = 1;
        saveorUpdateBundle = "save";

    }

    public void getLcAmount(ValueChangeEvent ev) {
        if (null != ev.getNewValue()) {
            prmsLcRigistration = (PrmsLcRigistration) ev.getNewValue();
            amendedLcNoLists = bank_Clearance_RegistrationBeanLocal.checkAsLcIsAmendedByLcId(prmsLcRigistration);
            if (amendedLcNoLists.size() > 0) {
                prmsLcRigistrationAmend = bank_Clearance_RegistrationBeanLocal.getLcAmendedInfoByLcId(prmsLcRigistration);
                prmsLcRigistration.setContractId(prmsLcRigistrationAmend.getContractId());
                prmsLcRigistration.setPermitAmount(prmsLcRigistrationAmend.getPermitAmount());
                prmsLcRigistration.setForeignId(prmsLcRigistrationAmend.getForeignId());
                prmsLcRigistration.setLcAmount(prmsLcRigistrationAmend.getLcAmount());
            }
        }
    }

    public void handleDecisionOptions(ValueChangeEvent decision) {
        if (decision.getNewValue() != null) {
            selectedDecision = decision.getNewValue().toString();
        }
    }

    public void fileUploadListener(FileUploadEvent event) {
        InputStream fileByteFile_ = null;
        fileName = event.getFile().getFileName().split("\\.")[0];
        docFormat = event.getFile().getFileName().split("\\.")[1];
//        String fileNameWzExtent = event.getFile().getFileName();
//        String categoryBundle = "et/gov/eep/commonApplications/securityServer";
        try {
            fileByteFile_ = event.getFile().getInputstream();
        } catch (Exception ex) {
            System.out.println("Error catched" + ex.getMessage());

        }
        byteArrayData = dataUpload.extractByteArray(fileByteFile_);
//        fileRefNum = dataUpload.uploadListener(fileByteFile_, docFormat, fileName, fileNameWzExtent, categoryBundle);
        if (byteArrayData != null) {
            JsfUtil.addSuccessMessage("File Uploaded Successfully");
            System.out.println("File " + fileName + " is uploaded with" + docFormat + " document Format");
        }
    }

    public void whenrowSelected(SelectEvent evented) {
        prmsLuDmArchive = (PrmsLuDmArchive) evented.getObject();
        System.out.println("The selected File Name is==" + prmsLuDmArchive.getFileName() + " and its format is==" + prmsLuDmArchive.getFileType());
        isRowFileSelected = true;
        System.out.println("" + isRowFileSelected);
    }

    // Event changes when we select Column Name from Lists
    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            bankClearanceRegistration.setColumnName(event.getNewValue().toString());
            bankClearanceRegistration.setColumnValue(null);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="save or update methods">
    public String saveBankClearanceInfo() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "saveBankClearanceInfo", dataset)) {
                if (cleared < 0) {
                    JsfUtil.addFatalMessage("cleared Amount cannot be Less than Zero");
                } else {
                    if (saveorUpdateBundle.equals("Save")) {

                        if (updateStatus == 0) {

                            try {
                                generateclearanceNo();
                                bankClearanceRegistration.setClearanceNo(newClearanceNo);
                                bankClearanceRegistration.setLetterOfCreditNo(prmsLcRigistration);
                                bankClearanceRegistration.setInvoiceAmount(invoice);
                                bankClearanceRegistration.setPermitAmount(permit);
                                bankClearanceRegistration.setClearedAmount(cleared);
                                prmsLuDmArchive.setFileName(fileName);
                                prmsLuDmArchive.setFileType(docFormat);
                                prmsLuDmArchive.setUploadFile(byteArrayData);
                                prmsLuDmArchiveBeanLocal.saveFileInfo(prmsLuDmArchive);
                                bankClearanceRegistration.setAttachmentRefNumber(prmsLuDmArchive);
                                bankClearanceRegistration.setStatus(Constants.PREPARE_VALUE);
                                bankClearanceRegistration.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                                bankClearanceRegistration.setProcessedDate(wfPrmsProcessed.getProcessedOn());
                                bankClearanceRegistration.getWfPrmsProcessedLists().add(wfPrmsProcessed);
                                wfPrmsProcessed.setClearanceId(bankClearanceRegistration);
                                wfPrmsProcessed.setDecision(bankClearanceRegistration.getStatus().toString());
                                bank_Clearance_RegistrationBeanLocal.create(bankClearanceRegistration);
                                JsfUtil.addSuccessMessage("Bank Clearance Information is successfully Saved ");
                                return clearBCRform();
                            } catch (Exception e) {
                                e.printStackTrace();
                                JsfUtil.addFatalMessage("Something Error Occured on Data Saved" + e);
                                return null;
                            }
                        }
                    } else if (saveorUpdateBundle.equals("Update")) {
                        if (workFlow.isPrepareStatus()) {
                            try {
                                prmsLuDmArchive.setFileName(fileName);
                                prmsLuDmArchive.setFileType(docFormat);
                                prmsLuDmArchive.setUploadFile(byteArrayData);
                                if (prmsLuDmArchive.getFileName() != null) {
                                    bankClearanceRegistration.setAttachmentRefNumber(prmsLuDmArchive);
//                                    prmsLuDmArchiveBeanLocal.updateFileInfo(prmsLuDmArchive);
                                    prmsLuDmArchiveBeanLocal.saveFileInfo(prmsLuDmArchive);
                                }
                                bankClearanceRegistration.setStatus(Constants.PREPARE_VALUE);
                                bankClearanceRegistration.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                                bankClearanceRegistration.setProcessedDate(wfPrmsProcessed.getProcessedOn());
                                bankClearanceRegistration.setLetterOfCreditNo(prmsLcRigistration);
//                bankClearanceRegistration.setStatus(dictionary.getUPDATED());

//                                
                                wfPrmsProcessed.setClearanceId(bankClearanceRegistration);
                                wfPrmsProcessed.setDecision(bankClearanceRegistration.getStatus().toString());

                                bank_Clearance_RegistrationBeanLocal.update(bankClearanceRegistration);
//                                wfPrmsProcessedBeanLocal.edit(wfPrmsProcessed);
                                bankClearanceRegistration.getWfPrmsProcessedLists().add(wfPrmsProcessed);
                                JsfUtil.addSuccessMessage("Bank Clearance Information is successfully Updated ");
                                saveorUpdateBundle = "Save";
                                renderAWB = true;
                                renderBL = false;
                                checkAWBbillType = bankClearanceRegistration.getBillType();
                                return clearBCRform();

                            } catch (Exception e) {
                                JsfUtil.addFatalMessage("Something Error Occured on Data Modified");
                            }
                        } else if (workFlow.isApproveStatus() || workFlow.isCheckStatus()) {
                            System.out.println("when Approver");
                            if (selectedDecision.equals("Approved") && workFlow.isApproveStatus()) {
//                workFlow.setStatus(Constants.APPROVE_VALUE);
                                wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                                bankClearanceRegistration.setStatus(Constants.APPROVE_VALUE);
                            } else if (selectedDecision.equals("Rejected") && workFlow.isApproveStatus()) {
//                workFlow.setStatus(Constants.APPROVE_VALUE);
                                wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_REJECT_VALUE));
                                bankClearanceRegistration.setStatus(Constants.APPROVE_REJECT_VALUE);
                            } else if (selectedDecision.equals("Approved") && workFlow.isCheckStatus()) {
//                workFlow.setStatus(Constants.APPROVE_VALUE);
                                wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_APPROVE_VALUE));
                                bankClearanceRegistration.setStatus(Constants.CHECK_APPROVE_VALUE);
                            } else if (selectedDecision.equals("Rejected") && workFlow.isCheckStatus()) {
//                workFlow.setStatus(Constants.APPROVE_VALUE);
                                wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_REJECT_VALUE));
                                bankClearanceRegistration.setStatus(Constants.CHECK_REJECT_VALUE);
                            }
                            bank_Clearance_RegistrationBeanLocal.update(bankClearanceRegistration);
                            wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                            JsfUtil.addInformationMessage("Bank Clearance Information is Successfully Decided");
                            clearBCRform();
                        }
                    }
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBean.getSessionID());
                eventEntry.setUserId(sessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBean.getUserName());
                eventEntry.setUserLogin(test);
//              ..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {

        }
        return null;
    }
    // </editor-fold>

}
