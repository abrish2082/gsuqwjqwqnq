/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.termination;

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
import org.insa.client.DmsHandler;
import org.insa.model.DocList;
import org.insa.model.DocumentModel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.controller.DataUpload;
import et.gov.eep.commonApplications.entity.HrLuDmArchive;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.termination.TerminationApproveBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.termination.HrTerminationRequestUpload;
import et.gov.eep.hrms.entity.termination.HrTerminationRequests;

/**
 *
 * @author INSA
 */
@Named(value = "terminationApproveController")
@ViewScoped
public class TerminationApproveController implements Serializable {

    @Inject
    HrTerminationRequests hrTerminationRequest;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrDepartments hrDepartments;
    @Inject
    HrJobTypes hrJobTypes;
    @Inject
    HrTerminationRequestUpload hrTerminationRequestUpload;
    @Inject
    HrLuDmArchive hrLuDmArchive;
    @Inject
    WfHrProcessed wfHrProcessed;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    WorkFlow workFlow;
    @Inject
    DataUpload dataUpload;
    @EJB
    TerminationApproveBeanLocal terminationApproveBeanLocal;
    @EJB
    WfHrProcessedBeanLocal wfHrProcessedBeanLocal;

    List<SelectItem> filterByStatus = new ArrayList<>();
    DataModel<HrTerminationRequests> terminationApproveDataModel = new ListDataModel<>();
    DataModel<WfHrProcessed> workflowDataModel = new ListDataModel<>();
    private List<HrTerminationRequests> terminationList;
    DataModel<HrTerminationRequestUpload> terminationUploadDataModel;

    private String newOrSearch = "New";
    private String icone = "ui-icon-document";
    private boolean searchPage = true;
    private boolean newPage = false;
    private boolean disableBtn = false;
    private boolean isFileSelected = false;

    public TerminationApproveController() {
    }

    //<editor-fold defaultstate="collapsed" desc="getter setter">
    public HrTerminationRequestUpload getHrTerminationRequestUpload() {
        if (hrTerminationRequestUpload == null) {
            hrTerminationRequestUpload = new HrTerminationRequestUpload();
        }
        return hrTerminationRequestUpload;
    }

    public void setHrTerminationRequestUpload(HrTerminationRequestUpload hrTerminationRequestUpload) {
        this.hrTerminationRequestUpload = hrTerminationRequestUpload;
    }

    public HrLuDmArchive getHrLuDmArchive() {
        if (hrLuDmArchive == null) {
            hrLuDmArchive = new HrLuDmArchive();
        }
        return hrLuDmArchive;
    }

    public void setHrLuDmArchive(HrLuDmArchive hrLuDmArchive) {
        this.hrLuDmArchive = hrLuDmArchive;
    }

    public HrTerminationRequests getHrTerminationRequest() {
        if (hrTerminationRequest == null) {
            hrTerminationRequest = new HrTerminationRequests();
        }
        return hrTerminationRequest;
    }

    public void setHrTerminationRequest(HrTerminationRequests hrTerminationRequest) {
        this.hrTerminationRequest = hrTerminationRequest;
    }

    public HrEmployees getHrEmployees() {
        if (hrEmployees == null) {
            hrEmployees = new HrEmployees();
        }
        return hrEmployees;
    }

    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
    }

    public HrDepartments getHrDepartments() {
        if (hrDepartments == null) {
            hrDepartments = new HrDepartments();
        }
        return hrDepartments;
    }

    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }

    public HrJobTypes getHrJobTypes() {
        if (hrJobTypes == null) {
            hrJobTypes = new HrJobTypes();
        }
        return hrJobTypes;
    }

    public void setHrJobTypes(HrJobTypes hrJobTypes) {
        this.hrJobTypes = hrJobTypes;
    }

    public WfHrProcessed getWfHrProcessed() {
        if (wfHrProcessed == null) {
            wfHrProcessed = new WfHrProcessed();
        }
        return wfHrProcessed;
    }

    public void setWfHrProcessed(WfHrProcessed wfHrProcessed) {
        this.wfHrProcessed = wfHrProcessed;
    }

    public List<HrTerminationRequests> getTerminationList() {
        return terminationList = terminationApproveBeanLocal.findPreparedList();
    }

    public void setTerminationList(List<HrTerminationRequests> terminationList) {
        this.terminationList = terminationList;
    }

    public String getNewOrSearch() {
        return newOrSearch;
    }

    public void setNewOrSearch(String newOrSearch) {
        this.newOrSearch = newOrSearch;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isSearchPage() {
        return searchPage;
    }

    public void setSearchPage(boolean searchPage) {
        this.searchPage = searchPage;
    }

    public boolean isNewPage() {
        return newPage;
    }

    public void setNewPage(boolean newPage) {
        this.newPage = newPage;
    }

    public boolean isDisableBtn() {
        return disableBtn;
    }

    public void setDisableBtn(boolean disableBtn) {
        this.disableBtn = disableBtn;
    }

    public List<SelectItem> getFilterByStatus() {
        return terminationApproveBeanLocal.filterByStatus();
    }

    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }

    public DataModel<HrTerminationRequests> getTerminationApproveDataModel() {
        return terminationApproveDataModel = new ListDataModel(terminationApproveBeanLocal.searchByStatus(status));
    }

    public void setTerminationApproveDataModel(DataModel<HrTerminationRequests> terminationApproveDataModel) {
        this.terminationApproveDataModel = terminationApproveDataModel;
    }

    public DataModel<HrTerminationRequestUpload> getTerminationUploadDataModel() {
        return terminationUploadDataModel;
    }

    public void setTerminationUploadDataModel(DataModel<HrTerminationRequestUpload> terminationUploadDataModel) {
        this.terminationUploadDataModel = terminationUploadDataModel;
    }

    public DataModel<WfHrProcessed> getWorkflowDataModel() {
        return workflowDataModel;
    }

    public void setWorkflowDataModel(DataModel<WfHrProcessed> workflowDataModel) {
        this.workflowDataModel = workflowDataModel;
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

    //</editor-fold>
    public void workflowDataModel() {
        workflowDataModel = null;
        workflowDataModel = new ListDataModel(new ArrayList(hrTerminationRequest.getWfHrProcessedList()));
    }

    public void recreateUploadDataModel() {
        terminationUploadDataModel = null;
        terminationUploadDataModel = new ListDataModel<>(hrTerminationRequest.getHrTerminationRequestUploadList());
    }

    public void btnNewOrSearch() {
        switch (newOrSearch) {
            case "New":
                searchPage = false;
                newPage = true;
                newOrSearch = "Search";
                setIcone("ui-icon-search");
                break;
            case "Search":
                searchPage = true;
                newPage = false;
                newOrSearch = "New";
                setIcone("ui-icon-search");
                break;
        }
    }

    public void searchTerminationApp() {
        searchPage = true;
        newPage = false;
    }

    //<editor-fold defaultstate="collapsed" desc="main">
    String selectedValue = "";

    public void handleselectOptions(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selectedValue = event.getNewValue().toString();
        }
    }

    public void saveTerminationApprove() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveTerminationApprove", dataset)) {
                try {
                    if (hrEmployees.getId() == null || hrTerminationRequest.getId() == null) {
                        JsfUtil.addFatalMessage("Can not approve empty data.");
                    } else {
                        if (selectedValue.equalsIgnoreCase("1") && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_VALUE);
                            hrTerminationRequest.setStatus(workFlow.getUserStatus());
                        } else if (selectedValue.equalsIgnoreCase("1") && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                            hrTerminationRequest.setStatus(workFlow.getUserStatus());
                        } else if (selectedValue.equalsIgnoreCase("2") && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                            hrTerminationRequest.setStatus(workFlow.getUserStatus());
                        } else if (selectedValue.equalsIgnoreCase("2") && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                            hrTerminationRequest.setStatus(workFlow.getUserStatus());
                        }
                        wfHrProcessed.setTerminationId(hrTerminationRequest);
                        wfHrProcessed.setDecision(hrTerminationRequest.getStatus());
                        wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
                        terminationApproveBeanLocal.edit(hrTerminationRequest);
                        wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                        terminationList = terminationApproveBeanLocal.findPreparedList();
                        JsfUtil.addSuccessMessage("Successfullly saved");
                        clearPage();
                        terminationApproveDataModel = new ListDataModel(terminationApproveBeanLocal.loadTerminationList(status));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JsfUtil.addFatalMessage("Error occure while save update");
                }

            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveTerminationApprove");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearPage() {
        hrTerminationRequest = new HrTerminationRequests();
        hrEmployees = new HrEmployees();
        hrDepartments = new HrDepartments();
        hrJobTypes = new HrJobTypes();
        wfHrProcessed = new WfHrProcessed();
        disableBtn = false;
        docValueModel = null;
    }

    int status = 0;

    private void populateTable() {
        try {
            List<HrTerminationRequests> readFilteredTermination = terminationApproveBeanLocal.loadTerminationList(status);
            terminationApproveDataModel = new ListDataModel(readFilteredTermination);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void filiterByStatus_Vcl(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            status = Integer.valueOf(event.getNewValue().toString());
            populateTable();
        }
    }

    public void findByStatus(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            status = Integer.valueOf(event.getNewValue().toString());
            List<HrTerminationRequests> readTerminationList = terminationApproveBeanLocal.searchByStatus(status);
            terminationApproveDataModel = new ListDataModel(readTerminationList);
        }
    }

    public void populateTermination() {
        hrEmployees = hrTerminationRequest.getEmpId();
        hrDepartments = hrEmployees.getDeptId();
        hrJobTypes = hrEmployees.getJobId();
        recreateUploadDataModel();
        workflowDataModel();
        if (hrTerminationRequest.getStatus().equals(Constants.PREPARE_VALUE)) {
            disableBtn = false;
        } else {
            disableBtn = true;
        }
        newPage = true;
        searchPage = false;
        newOrSearch = "Search";
        setIcone("ui-icon-search");
    }

    public void populateNotification(ValueChangeEvent event) {
        hrTerminationRequest = (HrTerminationRequests) event.getNewValue();
        populateTermination();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('widNotf').hide();");
    }

    public void populateDatatable(SelectEvent event) {
        hrTerminationRequest = (HrTerminationRequests) event.getObject();
        populateTermination();
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="DMS service"> 
    StreamedContent file;
    DataModel<DocumentModel> docValueModel;
    DocumentModel documentModel = new DocumentModel();

    public DocumentModel getDocumentModel() {
        return documentModel;
    }

    public void setDocumentModel(DocumentModel documentModel) {
        this.documentModel = documentModel;
    }

    public void onRowSelect(SelectEvent event) {
        isFileSelected = true;
        hrTerminationRequestUpload = (HrTerminationRequestUpload) event.getObject();
        hrLuDmArchive = hrTerminationRequestUpload.getDocumentId();
    }

    public void recreateDataModel() {
        DmsHandler dmsHandler = new DmsHandler();
        DocList docList = new DocList();
        List<String> docId = new ArrayList<>();
        for (int i = 0; i < hrTerminationRequest.getHrTerminationRequestUploadList().size(); i++) {
            System.out.println("===========dociD============== " + hrTerminationRequest.getHrTerminationRequestUploadList().get(i).getDocumentId().toString());
            docId.add(hrTerminationRequest.getHrTerminationRequestUploadList().get(i).getDocumentId().toString());
        }
        docList.setDocIds(docId);
        System.out.println(" ============dociD after============= " + docList.getDocIds().size());
        DocList docListNew = dmsHandler.getDocumentsById(docList);
        if (docListNew != null) {
            System.out.println(" ============dociD from DB============= " + docListNew.getDocList());
            docValueModel = new ListDataModel(docListNew.getDocList());
        }
    }

    public DataModel<DocumentModel> getDocValueModel() {
        return docValueModel;
    }

    public void setDocValueModel(DataModel<DocumentModel> docValueModel) {
        this.docValueModel = docValueModel;
    }

    public StreamedContent getFile() throws Exception {
        if (isFileSelected == true) {
            file = dataUpload.getHrmsFileRefNumber(hrLuDmArchive);
        } else {
            JsfUtil.addFatalMessage("Select row file you want to download");
        }
        return file;
    }
    // </editor-fold>
}
