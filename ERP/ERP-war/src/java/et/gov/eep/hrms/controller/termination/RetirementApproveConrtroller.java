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
import et.gov.eep.hrms.businessLogic.termination.RetirementApproveBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.termination.HrRetirementRequest;
import et.gov.eep.hrms.entity.termination.HrRetirementRequestUpload;

/**
 *
 * @author INSA
 */
@Named(value = "retirementApproveConrtroller")
@ViewScoped
public class RetirementApproveConrtroller implements Serializable {

    @Inject
    HrRetirementRequest hrRetirementRequest;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrDepartments hrDepartments;
    @Inject
    HrJobTypes hrJobTypes;
    @Inject
    HrRetirementRequestUpload hrRetirementRequestUpload;
    @Inject
    WfHrProcessed wfHrProcessed;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    WorkFlow workFlow;
    @Inject
    HrLuDmArchive hrLuDmArchive;
    @Inject
    DataUpload dataUpload;

    @EJB
    RetirementApproveBeanLocal retirementApproveBeanLocal;
    @EJB
    WfHrProcessedBeanLocal wfHrProcessedBeanLocal;

    private String newOrSearch = "New";
    private String icone = "ui-icon-document";
    private boolean searchPage = true;
    private boolean newPage = false;
    private boolean disableBtn = false;
    private boolean isFileSelected = false;

    List<SelectItem> filterByStatus = new ArrayList<>();
    DataModel<HrRetirementRequest> retirementDataModel = new ListDataModel<>();
    DataModel<WfHrProcessed> workflowDataModel = new ListDataModel<>();
    private List<HrRetirementRequest> retirementList;

    public RetirementApproveConrtroller() {
    }

    //<editor-fold defaultstate="collapsed" desc="getter setter">
    public HrRetirementRequestUpload getHrRetirementRequestUpload() {
        if (hrRetirementRequestUpload == null) {
            hrRetirementRequestUpload = new HrRetirementRequestUpload();
        }
        return hrRetirementRequestUpload;
    }

    public void setHrRetirementRequestUpload(HrRetirementRequestUpload hrRetirementRequestUpload) {
        this.hrRetirementRequestUpload = hrRetirementRequestUpload;
    }

    public HrRetirementRequest getHrRetirementRequest() {
        if (hrRetirementRequest == null) {
            hrRetirementRequest = new HrRetirementRequest();
        }
        return hrRetirementRequest;
    }

    public void setHrRetirementRequest(HrRetirementRequest hrRetirementRequest) {
        this.hrRetirementRequest = hrRetirementRequest;
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
        return retirementApproveBeanLocal.filterByStatus();
    }

    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }

    public DataModel<HrRetirementRequest> getRetirementDataModel() {
        return retirementDataModel = new ListDataModel(retirementApproveBeanLocal.loadRetirementList(status));
    }

    public void setRetirementDataModel(DataModel<HrRetirementRequest> retirementDataModel) {
        this.retirementDataModel = retirementDataModel;
    }

    public DataModel<WfHrProcessed> getWorkflowDataModel() {
        return workflowDataModel;
    }

    public void setWorkflowDataModel(DataModel<WfHrProcessed> workflowDataModel) {
        this.workflowDataModel = workflowDataModel;
    }

    public List<HrRetirementRequest> getRetirementList() {
        return retirementList = retirementApproveBeanLocal.findPreparedList();
    }

    public void setRetirementList(List<HrRetirementRequest> retirementList) {
        this.retirementList = retirementList;
    }
//</editor-fold>

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
                setIcone("ui-icon-document");
                break;
        }
    }

    public void workflowDataModel() {
        workflowDataModel = null;
        workflowDataModel = new ListDataModel(new ArrayList(hrRetirementRequest.getWfHrProcessedList()));
    }

    public void searchRetirementApp() {
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

    public void saveRetirementApprove() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveRetirementApprove", dataset)) {

                if (hrEmployees.getId() == null || hrRetirementRequest.getId() == null) {
                    JsfUtil.addFatalMessage("Can not approve empty data.");
                } else {
                    if (selectedValue.equalsIgnoreCase("1") && workFlow.isApproveStatus()) {
                        workFlow.setUserStatus(Constants.APPROVE_VALUE);
                        hrRetirementRequest.setStatus(workFlow.getUserStatus());
                    } else if (selectedValue.equalsIgnoreCase("1") && workFlow.isCheckStatus()) {
                        workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        hrRetirementRequest.setStatus(workFlow.getUserStatus());
                    } else if (selectedValue.equalsIgnoreCase("2") && workFlow.isApproveStatus()) {
                        workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        hrRetirementRequest.setStatus(workFlow.getUserStatus());
                    } else if (selectedValue.equalsIgnoreCase("2") && workFlow.isCheckStatus()) {
                        workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        hrRetirementRequest.setStatus(workFlow.getUserStatus());
                    }
                    wfHrProcessed.setRetirementId(hrRetirementRequest);
                    wfHrProcessed.setDecision(hrRetirementRequest.getStatus());
                    wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
                    retirementApproveBeanLocal.edit(hrRetirementRequest);
                    wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                    JsfUtil.addSuccessMessage("Successfullly saved");
                    clearPage();
                    retirementDataModel = new ListDataModel(retirementApproveBeanLocal.loadRetirementList(status));
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveRetirementApprove");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearPage() {
        hrRetirementRequest = new HrRetirementRequest();
        hrEmployees = new HrEmployees();
        hrDepartments = new HrDepartments();
        hrJobTypes = new HrJobTypes();
        wfHrProcessed = new WfHrProcessed();
        disableBtn = false;
    }

    int status = 0;

    private void populateTable() {
        try {
            List<HrRetirementRequest> readFilteredRetirement = retirementApproveBeanLocal.searchByStatus(status);
            retirementDataModel = new ListDataModel(readFilteredRetirement);
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

    public void populateTermination() {
        hrEmployees = hrRetirementRequest.getEmpId();
        hrDepartments = hrEmployees.getDeptId();
        hrJobTypes = hrEmployees.getJobId();
        recreateDmsDataModel();
        workflowDataModel();
        if (hrRetirementRequest.getStatus().equals(Constants.PREPARE_VALUE)) {
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
        hrRetirementRequest = (HrRetirementRequest) event.getNewValue();
        populateTermination();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('widNotf').hide();");
    }

    public void populateDatatable(SelectEvent event) {
        hrRetirementRequest = (HrRetirementRequest) event.getObject();
        populateTermination();
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="DMS service"> 
    StreamedContent file;
    DataModel<HrRetirementRequestUpload> retirementUploadDataModel;

    public DataModel<HrRetirementRequestUpload> getRetirementUploadDataModel() {
        return retirementUploadDataModel;
    }

    public void setRetirementUploadDataModel(DataModel<HrRetirementRequestUpload> retirementUploadDataModel) {
        this.retirementUploadDataModel = retirementUploadDataModel;
    }

    public void recreateDmsDataModel() {
        retirementUploadDataModel = null;
        retirementUploadDataModel = new ListDataModel<>(hrRetirementRequest.getHrRetirementRequestUploadList());
    }

    public void onRowSelected(SelectEvent event) {
        hrRetirementRequestUpload = (HrRetirementRequestUpload) event.getObject();
        hrLuDmArchive = hrRetirementRequestUpload.getDocumentId();
        isFileSelected = true;
    }

    public StreamedContent getFile() throws Exception {
        if (isFileSelected == true) {
            file = dataUpload.getHrmsFileRefNumber(hrLuDmArchive);
        } else {
            JsfUtil.addFatalMessage("Please Select Row File U want to Download");
        }
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public void remove() {
        hrRetirementRequestUpload.setId(hrRetirementRequestUpload.getId());
        retirementApproveBeanLocal.remove(hrRetirementRequestUpload);
        hrRetirementRequest.getHrRetirementRequestUploadList().remove(hrRetirementRequestUpload);
        recreateDmsDataModel();
        JsfUtil.addSuccessMessage("Document Seccesfully Deleted!!! ");

    }

    // </editor-fold>
}
