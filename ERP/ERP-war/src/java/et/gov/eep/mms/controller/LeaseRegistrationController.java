/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.controller;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import org.insa.model.DocumentModel;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;
import et.gov.eep.commonApplications.businessLogic.MmsLuDmArchiveBeanLocal;
import et.gov.eep.commonApplications.controller.DataUpload;
import et.gov.eep.commonApplications.entity.MmsLuDmArchive;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.fcms.mapper.FmsLuCurrencyFacade;
import et.gov.eep.ifrs.businessLogic.DepreciationSetupBeanLocal;
import et.gov.eep.ifrs.entity.IfrsDepreciationSetup;
import et.gov.eep.mms.businessLogic.LeaseRegistrationBeanLocal;
import et.gov.eep.mms.entity.IfrsLease;

/**
 *
 * @author Minab
 */
@Named(value = "leaseRegistrationController")
@ViewScoped
public class LeaseRegistrationController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="EJB">
    @EJB
    LeaseRegistrationBeanLocal leaseRegistrationBeanLocal;
    @EJB
    DepreciationSetupBeanLocal depreciationSetupBeanLocal;
    @EJB
    MmsLuDmArchiveBeanLocal mmsLuDmArchiveBeanLocal;
    @EJB
    FmsLuCurrencyFacade currencyFacade;
    //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="Entities">
    @Inject
    private IfrsLease lease;
    @Inject
    IfrsDepreciationSetup depreciationSetup;
    @Inject
    DataUpload dataUpload;
    @Inject
    MmsLuDmArchive mmsLuDmArchive;
    @Inject
    SessionBean SessionBean;
    @Inject
    FmsLuCurrency fmsLuCurrency;
    private boolean disableBtnCreate;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateDisposal = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-plus";
    private String saveorUpdateBundle = "Save";
    private DataModel<IfrsLease> IfrsLeaseSearchInfoDataModel;
    int updateStatus = 0;
    List<IfrsLease> allReturnInfoList;
    int update = 0;
    private IfrsLease hpSelect;
    private boolean renderPnlCreateLeaseInformation = false;
    private DataModel<IfrsLease> mmsLeasesSearchInfoDataModel;
    private StreamedContent download;
    DataModel<DocumentModel> DMdocumentModel;
    DataModel<MmsLuDmArchive> mmsLuDmArchivesDModel;
    List<Integer> documentId;
    private boolean isFileSelected = false;
    MmsLuDmArchive mmsLuDmArchiveSelection;
    List<MmsLuDmArchive> mmsLuDmArchiveList = new ArrayList<>();
    List<IfrsLease> allLeasesInfoList;
    String docFormat;
    String fileName;
    byte[] byteFile;
    List<IfrsLease> allLeaseInfoList;

    //</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="gettre and setter">
    public LeaseRegistrationController() {
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isRenderPnlCreateDisposal() {
        return renderPnlCreateDisposal;
    }

    public void setRenderPnlCreateDisposal(boolean renderPnlCreateDisposal) {
        this.renderPnlCreateDisposal = renderPnlCreateDisposal;
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

    public IfrsLease getHpSelect() {
        return hpSelect;
    }

    public void setHpSelect(IfrsLease hpSelect) {
        this.hpSelect = hpSelect;
    }

    public IfrsLease getLease() {
        if (lease == null) {

            lease = new IfrsLease();
        }
        return lease;
    }

    public void setLease(IfrsLease lease) {
        this.lease = lease;
    }

    public IfrsDepreciationSetup getDepreciationSetup() {
        if (depreciationSetup == null) {
            depreciationSetup = new IfrsDepreciationSetup();
        }
        return depreciationSetup;
    }

    public void setDepreciationSetup(IfrsDepreciationSetup depreciationSetup) {
        this.depreciationSetup = depreciationSetup;
    }

    public DataModel<IfrsLease> getMmsLeasesSearchInfoDataModel() {
        if (mmsLeasesSearchInfoDataModel == null) {
            mmsLeasesSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsLeasesSearchInfoDataModel;
    }

    public void setMmsLeasesSearchInfoDataModel(DataModel<IfrsLease> mmsLeasesSearchInfoDataModel) {
        this.mmsLeasesSearchInfoDataModel = mmsLeasesSearchInfoDataModel;
    }

    public boolean isRenderPnlCreateLeaseInformation() {
        return renderPnlCreateLeaseInformation;
    }

    public void setRenderPnlCreateLeaseInformation(boolean renderPnlCreateLeaseInformation) {
        this.renderPnlCreateLeaseInformation = renderPnlCreateLeaseInformation;
    }

    public DataModel<IfrsLease> getIfrsLeaseSearchInfoDataModel() {
        if (IfrsLeaseSearchInfoDataModel == null) {
            IfrsLeaseSearchInfoDataModel = new ListDataModel<>();

        }
        return IfrsLeaseSearchInfoDataModel;
    }

    public void setIfrsLeaseSearchInfoDataModel(DataModel<IfrsLease> IfrsLeaseSearchInfoDataModel) {
        this.IfrsLeaseSearchInfoDataModel = IfrsLeaseSearchInfoDataModel;
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

    public MmsLuDmArchive getMmsLuDmArchive() {
        if (mmsLuDmArchive == null) {
            mmsLuDmArchive = new MmsLuDmArchive();
        }
        return mmsLuDmArchive;
    }

    public void setMmsLuDmArchive(MmsLuDmArchive mmsLuDmArchive) {
        this.mmsLuDmArchive = mmsLuDmArchive;
    }

    public DataModel<DocumentModel> getDMdocumentModel() {
        return DMdocumentModel;
    }

    public void setDMdocumentModel(DataModel<DocumentModel> DMdocumentModel) {
        this.DMdocumentModel = DMdocumentModel;
    }

    public DataModel<MmsLuDmArchive> getMmsLuDmArchivesDModel() {
        return mmsLuDmArchivesDModel;
    }

    public void setMmsLuDmArchivesDModel(DataModel<MmsLuDmArchive> mmsLuDmArchivesDModel) {
        this.mmsLuDmArchivesDModel = mmsLuDmArchivesDModel;
    }

    public MmsLuDmArchive getMmsLuDmArchiveSelection() {
        if (mmsLuDmArchiveSelection == null) {
            mmsLuDmArchiveSelection = new MmsLuDmArchive();
        }
        return mmsLuDmArchiveSelection;
    }

    public void setMmsLuDmArchiveSelection(MmsLuDmArchive mmsLuDmArchiveSelection) {
        this.mmsLuDmArchiveSelection = mmsLuDmArchiveSelection;
    }

    public void btn_Search_Action() {
        //clearPage();
        renderPnlCreateDisposal = false;
        renderPnlManPage = true;
    }

    public FmsLuCurrency getFmsLuCurrency() {
        if (fmsLuCurrency == null) {
            fmsLuCurrency = new FmsLuCurrency();
        }
        return fmsLuCurrency;
    }

    public void setFmsLuCurrency(FmsLuCurrency fmsLuCurrency) {
        this.fmsLuCurrency = fmsLuCurrency;
    }

    public StreamedContent getDownload() {
        System.out.println("Dowlaod Here");
        if (isFileSelected == true) {
            System.out.println("yes u selected");
            download = dataUpload.getMmsFileRefNumber(mmsLuDmArchive);
        }
        return download;
    }

    public void setDownload(StreamedContent download) {
        this.download = download;
    }

    public List<IfrsDepreciationSetup> getListofGroups() {

        List<IfrsDepreciationSetup> list = null;
        list = depreciationSetupBeanLocal.depreciationList();
        System.err.println("list size=" + list.size());
        return list;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }
    //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="Save recreate">
    
    /*This method is used to Create New Store Informations
     */
    public void createNewStoreInfo() {
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateLeaseInformation = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateLeaseInformation = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
    /*This method is used to Row Select
     */

    public void rowSelect(SelectEvent event) {
        lease = (IfrsLease) event.getObject();
        lease.setLeaseId(lease.getLeaseId());
        renderPnlManPage = false;
        renderPnlCreateLeaseInformation = true;
        renderPnlCreateDisposal = true;
        saveorUpdateBundle = "Update";
        updateStatus = 1;
        setIcone("ui-icon-search");
        createOrSearchBundle = "Search";
        disableBtnCreate = true;
        depreciationSetup = lease.getFagId();
        System.out.println("=====currence in save" + fmsLuCurrency);
        fmsLuCurrency = lease.getLeaseCurrency();
        System.out.println("=======depreciationtype" + lease.getFagId());

        if (lease.getReferenceNumber() != null) {
            mmsLuDmArchive = lease.getReferenceNumber();
            mmsLuDmArchiveList = mmsLuDmArchiveBeanLocal.getFileList(mmsLuDmArchive);
        }
        recreateFileUpload();
    }
    /*This method is used to Handling File Uploading
     */

    public void uploadHandling(FileUploadEvent event) {
        InputStream inputStreamFile = null;
        docFormat = event.getFile().getFileName().split("\\.")[1];
        fileName = event.getFile().getFileName().split("\\.")[0];
        try {
            inputStreamFile = event.getFile().getInputstream();
            byteFile = dataUpload.extractByteArray(inputStreamFile);
            if (byteFile != null) {
                System.out.println("byte File---" + byteFile);
                System.out.println("File Name " + fileName + " with Format " + docFormat + " is Succesfully Uploaded");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDocValue() {
        DMdocumentModel = dataUpload.selectListOfFileByDocId(documentId);
        System.out.println("No of your Doc is " + DMdocumentModel.getRowCount());
    }

    public void onRowSelectFileUploaded(SelectEvent ev) {
        System.out.println("when File Row Selected");
        mmsLuDmArchiveSelection = (MmsLuDmArchive) ev.getObject();
        System.out.println("File Name===" + mmsLuDmArchiveSelection.getFileName());
        isFileSelected = true;
    }

    public void recreateFileUpload() {
        mmsLuDmArchivesDModel = null;
        mmsLuDmArchivesDModel = new ListDataModel(mmsLuDmArchiveList);
    }
    /*This method is used to  SearchLease
     */

    public void SearchLease() {
        if (lease.getLeaseCode() != null) {
            allLeaseInfoList = leaseRegistrationBeanLocal.searchLeaseInformationByLeaseCode(lease);
            System.out.println("===========userId==========" + lease.getLeaseCode());
            recerateStoreSearchModel();
        } else {
            allLeaseInfoList = leaseRegistrationBeanLocal.findbyAll();
            System.out.println("======allLeaseInfoList==========" + allLeaseInfoList.size());
            recerateStoreSearchModel();
        }

    }

    private void recerateStoreSearchModel() {
        mmsLeasesSearchInfoDataModel = null;
        mmsLeasesSearchInfoDataModel = new ListDataModel(new ArrayList<>(allLeaseInfoList));
    }
    /*This method is used to  Save Lease Informations
     */

    public void saveLeaseInfo() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveLeaseInfo", dataset)) {
                if (update == 0) {
                    lease.setLeaseCurrency(fmsLuCurrency);
                    System.out.println("=====currence in save" + fmsLuCurrency);
                    lease.setFagId(depreciationSetup);
                    System.out.println("=======depreciation=== " + depreciationSetup);
                    leaseRegistrationBeanLocal.saveOrUpdate(lease);
                    JsfUtil.addSuccessMessage("Saved Successfuly.");
                } else {
                    lease.setLeaseCurrency(fmsLuCurrency);
                    System.out.println("=====currence in save" + fmsLuCurrency);
                    lease.setFagId(depreciationSetup);
                    System.out.println("=======depreciation=== " + depreciationSetup);
                    leaseRegistrationBeanLocal.saveOrUpdate(lease);
                    JsfUtil.addSuccessMessage("Update Successful.");
                }

                if (lease.getReferenceNumber() != null) {
                    mmsLuDmArchive = lease.getReferenceNumber();
                    mmsLuDmArchiveList = mmsLuDmArchiveBeanLocal.getFileList(mmsLuDmArchive);
                }

                clearComponent();
            } else {
                et.gov.eep.commonApplications.utility.JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Utility.JsfUtil.addErrorMessage("Something is wrong!!");
        }
    }
    /*This method is used to handling selecting Currency
     */

    public void handleSelectCurrency(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            fmsLuCurrency = (FmsLuCurrency) event.getNewValue();
            lease.setLeaseCurrency(fmsLuCurrency);
        }
    }
    /*This method is used to  get list of currency 
     */

    public SelectItem[] getCurrencyList() {
        return JsfUtil.getSelectItems(currencyFacade.findAll(), true);

    }
    /*This method is used to  Clear Componet
     */

    public void clearComponent() {
        lease = new IfrsLease();
        fmsLuCurrency = new FmsLuCurrency();
        mmsLeasesSearchInfoDataModel = new ListDataModel<>();
        depreciationSetup = null;
    }

    private void recerateDispSerachModel() {
        IfrsLeaseSearchInfoDataModel = null;
        IfrsLeaseSearchInfoDataModel = new ListDataModel(new ArrayList<>(allReturnInfoList));
    }

}
//</editor-fold> 
