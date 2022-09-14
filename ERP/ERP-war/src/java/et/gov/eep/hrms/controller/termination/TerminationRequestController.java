/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.termination;

import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
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
import org.insa.model.DocumentModel;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import webService.UserStatus;
import et.gov.eep.commonApplications.businessLogic.HrLuDmArchiveBeanLocal;
import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.controller.DataUpload;
import et.gov.eep.commonApplications.entity.HrLuDmArchive;
import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.GregorianCalendarManipulation;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.termination.TerminationRequestBeanLocal;
import et.gov.eep.hrms.businessLogic.termination.TerminationRequestUploadBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.termination.HrTerminationRequestUpload;
import et.gov.eep.hrms.entity.termination.HrTerminationRequests;
import et.gov.eep.hrms.entity.termination.HrTerminationTypes;

/**
 *
 * @author INSA
 */
@Named(value = "terminationRequestController")
@ViewScoped
public class TerminationRequestController implements Serializable {

    @Inject
    HrTerminationRequests hrTerminationRequest;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrTerminationTypes hrTerminationType;
    @Inject
    HrDepartments hrDepartments;
    @Inject
    HrJobTypes hrJobTypes;
    @Inject
    HrTerminationRequestUpload hrTerminationRequestUpload;
    @Inject
    WfHrProcessed wfHrProcessed;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    DataUpload dataUpload;
    @Inject
    HrLuDmArchive hrLuDmArchive;
    @EJB
    TerminationRequestBeanLocal terminationRequestBeanLocal;
    @EJB
    HrEmployeeBeanLocal employeeBean;
    @EJB
    TerminationRequestUploadBeanLocal terminationRequestUploadBeanLocal;
    @EJB
    WfHrProcessedBeanLocal wfHrProcessedBeanLocal;
    @EJB
    HrLuDmArchiveBeanLocal hrLuDmArchiveBeanLocal;

    private int updateStatus = 0;
    private int popupUpdateStatus = 0;
    private String saveOrUpdate = "Save";
    private String poupSaveOrUpdate = "Save";
    private String newOrSearch = "Add New";
    private String icone = "ui-icon-plus";
    private boolean searchPage = true;
    private boolean newPage = false;
    private boolean disableBtn = false;
    private boolean btnNewRender = false;
    private int selectedStatus;
    WorkFlow workFlow = new WorkFlow();
    UserStatus userStatus = new UserStatus();
    Status status = new Status();
    String fileName;
    String fileType;
    byte[] byteFile;
    List<byte[]> byteLists = new ArrayList<>();
    List<byte[]> byteListFinal = new ArrayList<>();
    List<String> fileNameLists = new ArrayList<>();
    List<String> fileTypeLists = new ArrayList<>();
    private boolean isFileSelected = false;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    List<HrTerminationTypes> terminationTypeList = new ArrayList<>();
    List<SelectItem> filterByStatus = new ArrayList<>();
    DataModel<HrTerminationRequests> terminationRequestDataModel = new ListDataModel<>();
    DataModel<WfHrProcessed> workflowDataModel = new ListDataModel<>();
    DataModel<HrTerminationRequestUpload> terminationReqUploadsDM;
    Set<String> checkTerminationName = new HashSet<>();
    private List<HrTerminationRequests> terminationList;
    private List<HrTerminationRequests> terminationRequestList = new ArrayList<>();

    public TerminationRequestController() {
    }

    @PostConstruct
    public void init() {
        hrTerminationRequest.setRequestDate(StringDateManipulation.toDayInEc());
        wfHrProcessed.setProcessedOn(StringDateManipulation.toDayInEc());
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

    public HrTerminationTypes getHrTerminationType() {
        if (hrTerminationType == null) {
            hrTerminationType = new HrTerminationTypes();
        }
        return hrTerminationType;
    }

    public void setHrTerminationType(HrTerminationTypes hrTerminationType) {
        this.hrTerminationType = hrTerminationType;
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

    public DataUpload getDataUpload() {
        if (dataUpload == null) {
            dataUpload = new DataUpload();
        }
        return dataUpload;
    }

    public void setDataUpload(DataUpload dataUpload) {
        this.dataUpload = dataUpload;
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

    public String getSaveOrUpdate() {
        return saveOrUpdate;
    }

    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }

    public String getPoupSaveOrUpdate() {
        return poupSaveOrUpdate;
    }

    public void setPoupSaveOrUpdate(String poupSaveOrUpdate) {
        this.poupSaveOrUpdate = poupSaveOrUpdate;
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

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public boolean isDisableBtn() {
        return disableBtn;
    }

    public void setDisableBtn(boolean disableBtn) {
        this.disableBtn = disableBtn;
    }

    public boolean isIsFileSelected() {
        return isFileSelected;
    }

    public void setIsFileSelected(boolean isFileSelected) {
        this.isFileSelected = isFileSelected;
    }

    public List<HrTerminationTypes> getTerminationTypeList() {
        return terminationRequestBeanLocal.findall();
    }

    public void setTerminationTypeList(List<HrTerminationTypes> terminationTypeList) {
        this.terminationTypeList = terminationTypeList;
    }

    public List<SelectItem> getFilterByStatus() {
        return terminationRequestBeanLocal.filterByStatus();
    }

    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }

    public DataModel<HrTerminationRequests> getTerminationRequestDataModel() {
        return terminationRequestDataModel;
    }

    public void setTerminationRequestDataModel(DataModel<HrTerminationRequests> terminationRequestDataModel) {
        this.terminationRequestDataModel = terminationRequestDataModel;
    }

    public DataModel<HrTerminationRequestUpload> getTerminationReqUploadsDM() {
        return terminationReqUploadsDM;
    }

    public void setTerminationReqUploadsDM(DataModel<HrTerminationRequestUpload> terminationReqUploadsDM) {
        this.terminationReqUploadsDM = terminationReqUploadsDM;
    }

    public DataModel<WfHrProcessed> getWorkflowDataModel() {
        return workflowDataModel;
    }

    public void setWorkflowDataModel(DataModel<WfHrProcessed> workflowDataModel) {
        this.workflowDataModel = workflowDataModel;
    }

    public Set<String> getCheckTerminationName() {
        return checkTerminationName;
    }

    public void setCheckTerminationName(Set<String> checkTerminationName) {
        this.checkTerminationName = checkTerminationName;
    }
    //</editor-fold>

    public void workflowDataModel() {
        workflowDataModel = null;
        workflowDataModel = new ListDataModel(new ArrayList(hrTerminationRequest.getWfHrProcessedList()));
    }

    public void btnNewOrSearch() {
        switch (newOrSearch) {
            case "Add New":
                searchPage = false;
                newPage = true;
                btnNewRender = false;
                newOrSearch = "Search";
                setIcone("ui-icon-search");
                break;
            case "Search":
                searchPage = true;
                newPage = false;
                btnNewRender = false;
                newOrSearch = "Add New";
                setIcone("ui-icon-plus");
                break;
        }
    }

    public void newButtonAction() {
        resetTerminationRqst();
        newPage = true;
        searchPage = false;
        btnNewRender = false;
    }

    //<editor-fold defaultstate="collapsed" desc="main">
    public SelectItem[] getListOfTerminationName() {
        return JsfUtil.getSelectItems(terminationRequestBeanLocal.findall(), true);
    }

    //<editor-fold defaultstate="collapsed" desc="search">
    public ArrayList<HrEmployees> searchEmployeeName(String hrEmployee) {
        ArrayList<HrEmployees> employe = null;
        hrEmployees.setFirstName(hrEmployee);
        employe = employeeBean.SearchByFname(hrEmployees);
        return employe;
    }

    public void getEmployeeName(SelectEvent event) {
        try {
            hrEmployees.setFirstName(event.getObject().toString());
            hrEmployees = employeeBean.getByFirstName(hrEmployees);
            hrTerminationRequest.setEmpId(hrEmployees);
            hrDepartments = hrEmployees.getDeptId();
            hrJobTypes = hrEmployees.getJobId();
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }

    public ArrayList<HrTerminationTypes> searchByTerminationName(String terminationName) {
        ArrayList<HrTerminationTypes> terminationType = null;
        hrTerminationType.setTerminationName(terminationName);
        terminationType = terminationRequestBeanLocal.searchByTerminationName(hrTerminationType);
        poupSaveOrUpdate = "Update";
        popupUpdateStatus = 1;
        return terminationType;
    }

    public void getByTerminationName(SelectEvent event) {
        try {
            hrTerminationType.setTerminationName(event.getObject().toString());
            hrTerminationType = terminationRequestBeanLocal.getByTerminationName(hrTerminationType);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
  //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="popup">
    public void saveTerminationType() {
        if (hrTerminationType.getTerminationName() == null) {
            JsfUtil.addSuccessMessage("Field should be filled!");
        } else {
            if (terminationRequestBeanLocal.checkTerminationName(hrTerminationType) == null) {
                hrTerminationType.getTerminationName();
                hrTerminationType.getDescription();
                if ((checkTerminationName.contains(hrTerminationType.getTerminationName()) && checkTerminationName.contains(hrTerminationType.getDescription()))) {
                    JsfUtil.addFatalMessage("Termination name  " + hrTerminationType.getTerminationName() + "  is duplicated!. Try with another termination name.");
                } else {
                    try {
                        if (popupUpdateStatus == 0) {
                            terminationRequestBeanLocal.save(hrTerminationType);
                            JsfUtil.addSuccessMessage("Successfully Saved");
                            cancel();
                        } else {
                            terminationRequestBeanLocal.edit(hrTerminationType);
                            JsfUtil.addSuccessMessage("Successfully Update");
                            cancel();
                        }
                        terminationRequestBeanLocal.findall();
                        poupSaveOrUpdate = "Save";
                        popupUpdateStatus = 0;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                JsfUtil.addFatalMessage("Termination name  " + hrTerminationType.getTerminationName() + "  is already registred. Try with another termination name.");
            }
        }
    }

    public void cancel() {
        hrTerminationType = new HrTerminationTypes();
        popupUpdateStatus = 0;
        poupSaveOrUpdate = "Save";
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="save update">
    /**
     * This method converts E.C date (in dd/MM/YYYY) format to (in YYYY-MM-dd)
     *
     * @param date is a String date (in dd/MM/YYYY)
     * @return returns the java.util.Date() as yyyy-MM-dd
     */
    public String getDateFormat(String date) {
        Calendar cal = GregorianCalendarManipulation.convertDateInECToCalendar(date);
        return dateFormat.format(cal.getTime());
    }

    public void saveTerminationRequest() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveTerminationRequest", dataset)) {
                HrTerminationRequestUpload diagnosisUpload = new HrTerminationRequestUpload();
                if (hrTerminationRequest.getTerminationType().getTerminationName().equalsIgnoreCase("Resignation")) {
                    int termDateCurrDateDiff = StringDateManipulation.compareDate(getDateFormat(hrTerminationRequest.getTerminationDate()), StringDateManipulation.toDayInEc());
                    if (termDateCurrDateDiff >= 0) {
                        int termDateReqDateDiff = StringDateManipulation.compareDateDifference(hrTerminationRequest.getTerminationDate(), wfHrProcessed.getProcessedOn());
                        if (termDateReqDateDiff < 30) {
                            if (updateStatus == 0) {
                                for (int fileIndex = 0; fileIndex < byteLists.size(); fileIndex++) {
                                    hrLuDmArchive.setFileName(fileNameLists.get(fileIndex));
                                    hrLuDmArchive.setFileType(fileTypeLists.get(fileIndex));
                                    hrLuDmArchive.setUploadFile(byteListFinal.get(fileIndex));
                                    hrLuDmArchiveBeanLocal.create(hrLuDmArchive);
                                    diagnosisUpload.setDocumentId(hrLuDmArchive);
                                    diagnosisUpload.setTerminationId(hrTerminationRequest);
                                    hrTerminationRequest.getHrTerminationRequestUploadList().add(diagnosisUpload);
                                    diagnosisUpload = new HrTerminationRequestUpload();
                                    hrLuDmArchive = new HrLuDmArchive();
                                }
                                hrTerminationRequest.setStatus(Constants.PREPARE_VALUE);
                                hrTerminationRequest.setPreparedBy(String.valueOf(sessionBeanLocal.getUserId()));
                                hrTerminationRequest.setDescription(wfHrProcessed.getCommentGiven());
                                wfHrProcessed.setTerminationId(hrTerminationRequest);
                                wfHrProcessed.setDecision(hrTerminationRequest.getStatus());
                                wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
                                terminationRequestBeanLocal.save(hrTerminationRequest);
                                wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                                JsfUtil.addSuccessMessage("Successfully submitted but you are giving late or short notice, that’s a breach of contract, A minimum notice period for resignation is one month");
                                resetTerminationRqst();
                            } else {
                                for (int fileIndex = 0; fileIndex < byteLists.size(); fileIndex++) {
                                    hrLuDmArchive.setFileName(fileNameLists.get(fileIndex));
                                    hrLuDmArchive.setFileType(fileTypeLists.get(fileIndex));
                                    hrLuDmArchive.setUploadFile(byteListFinal.get(fileIndex));
                                    hrLuDmArchiveBeanLocal.edit(hrLuDmArchive);
                                    diagnosisUpload.setDocumentId(hrLuDmArchive);
                                    diagnosisUpload.setTerminationId(hrTerminationRequest);
                                    hrTerminationRequest.getHrTerminationRequestUploadList().add(diagnosisUpload);
                                    diagnosisUpload = new HrTerminationRequestUpload();
                                    hrLuDmArchive = new HrLuDmArchive();
                                }
                                terminationRequestBeanLocal.edit(hrTerminationRequest);
                                JsfUtil.addSuccessMessage("Successfully updated but you are giving late or short notice, that’s a breach of contract, A minimum notice period for resignation is one month");
                                resetTerminationRqst();
                            }
                        }
                    } else {
                        JsfUtil.addFatalMessage("Termination Date " + hrTerminationRequest.getTerminationDate() + " should be greater than today's date " + StringDateManipulation.toDayInEc());
                    }
                } else {
                    if (updateStatus == 0) {
                        for (int fileIndex = 0; fileIndex < byteLists.size(); fileIndex++) {
                            hrLuDmArchive.setFileName(fileNameLists.get(fileIndex));
                            hrLuDmArchive.setFileType(fileTypeLists.get(fileIndex));
                            hrLuDmArchive.setUploadFile(byteListFinal.get(fileIndex));
                            hrLuDmArchiveBeanLocal.create(hrLuDmArchive);
                            diagnosisUpload.setDocumentId(hrLuDmArchive);
                            diagnosisUpload.setTerminationId(hrTerminationRequest);
                            hrTerminationRequest.getHrTerminationRequestUploadList().add(diagnosisUpload);
                            diagnosisUpload = new HrTerminationRequestUpload();
                            hrLuDmArchive = new HrLuDmArchive();
                        }
                        hrTerminationRequest.setStatus(Constants.PREPARE_VALUE);
                        hrTerminationRequest.setPreparedBy(String.valueOf(sessionBeanLocal.getUserId()));
                        hrTerminationRequest.setDescription(wfHrProcessed.getCommentGiven());
                        wfHrProcessed.setTerminationId(hrTerminationRequest);
                        wfHrProcessed.setDecision(hrTerminationRequest.getStatus());
                        wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
                        terminationRequestBeanLocal.save(hrTerminationRequest);
                        wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                        JsfUtil.addSuccessMessage("Successfully saved");
                        resetTerminationRqst();
                    } else {
                        for (int fileIndex = 0; fileIndex < byteLists.size(); fileIndex++) {
                            hrLuDmArchive.setFileName(fileNameLists.get(fileIndex));
                            hrLuDmArchive.setFileType(fileTypeLists.get(fileIndex));
                            hrLuDmArchive.setUploadFile(byteListFinal.get(fileIndex));
                            hrLuDmArchiveBeanLocal.edit(hrLuDmArchive);
                            diagnosisUpload.setDocumentId(hrLuDmArchive);
                            diagnosisUpload.setTerminationId(hrTerminationRequest);
                            hrTerminationRequest.getHrTerminationRequestUploadList().add(diagnosisUpload);
                            diagnosisUpload = new HrTerminationRequestUpload();
                            hrLuDmArchive = new HrLuDmArchive();
                        }
                        terminationRequestBeanLocal.edit(hrTerminationRequest);
                        JsfUtil.addSuccessMessage("Successfully updated");
                        resetTerminationRqst();
                    }
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveTerminationRequest");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resetTerminationRqst() {
        hrTerminationRequest = new HrTerminationRequests();
        hrEmployees = new HrEmployees();
        hrDepartments = new HrDepartments();
        hrJobTypes = new HrJobTypes();
        wfHrProcessed = new WfHrProcessed();
        workflowDataModel = null;
        disableBtn = false;
        updateStatus = 0;
        saveOrUpdate = "Save";
        docValueModel = null;
    }
    //</editor-fold>

    int reqStatus = 0;

    private void populateTable() {
        try {
            List<HrTerminationRequests> readFilteredTermination = terminationRequestBeanLocal.loadTerminationList(reqStatus);
            terminationRequestDataModel = new ListDataModel(readFilteredTermination);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void filiterByStatus_Vcl(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            reqStatus = Integer.valueOf(event.getNewValue().toString());
            populateTable();
        }
    }

    public void populateTerminationRqst(SelectEvent event) {
        updateStatus = 1;
        hrTerminationRequest = (HrTerminationRequests) event.getObject();
        hrTerminationRequest.setId(hrTerminationRequest.getId());
        hrTerminationRequest = terminationRequestBeanLocal.getSelectedRequest(hrTerminationRequest.getId());
        hrEmployees = hrTerminationRequest.getEmpId();
        hrDepartments = hrEmployees.getDeptId();
        hrJobTypes = hrEmployees.getJobId();
        recreateDataModel();
        workflowDataModel();
        if (hrTerminationRequest.getStatus().equals(Constants.APPROVE_VALUE) || hrTerminationRequest.getStatus().equals(Constants.CHECK_APPROVE_VALUE)) {
            disableBtn = true;
        } else {
            disableBtn = false;
        }
        newPage = true;
        searchPage = false;
        btnNewRender = true;
        newOrSearch = "Search";
        saveOrUpdate = "Update";
        setIcone("ui-icon-search");
    }
    //</editor-fold> 

    public SelectItem[] getStatusList() {
        SelectItem[] items = new SelectItem[4];
        items[0] = new SelectItem("null", "--- Select ---");
        items[1] = new SelectItem(Constants.PREPARE_VALUE, "Load request list");
        items[2] = new SelectItem(Constants.APPROVE_VALUE, "Load approved list");
        items[3] = new SelectItem(Constants.APPROVE_REJECT_VALUE, "Load rejected list");
        return items;
    }

    private void searchApprove(Status _status) {
        try {
            terminationRequestList = terminationRequestBeanLocal.loadaApprove(_status, sessionBeanLocal.getUserId());
            terminationRequestDataModel = new ListDataModel<>(terminationRequestList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void searchReject(Status _status) {
        try {
            terminationRequestList = terminationRequestBeanLocal.loadReject(_status, sessionBeanLocal.getUserId());
            terminationRequestDataModel = new ListDataModel<>(terminationRequestList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void filiterByStatus(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedStatus = Integer.valueOf(String.valueOf(event.getNewValue()));
            if (selectedStatus == Constants.PREPARE_VALUE) {
                status.setStatus1(Constants.PREPARE_VALUE);
                searchApprove(status);
            } else if (selectedStatus == Constants.APPROVE_VALUE) {
                status.setStatus1(Constants.APPROVE_VALUE);
                status.setStatus2(Constants.CHECK_APPROVE_VALUE);
                searchApprove(status);
            } else if (selectedStatus == Constants.APPROVE_REJECT_VALUE) {
                status.setStatus1(Constants.APPROVE_REJECT_VALUE);
                status.setStatus2(Constants.CHECK_REJECT_VALUE);
                searchReject(status);
            }
        }
    }

    public SelectItem[] getStatus() {
        SelectItem[] items = new SelectItem[3];
        items[0] = new SelectItem("null", "--- Select ---");
        items[1] = new SelectItem(Constants.PREPARE_VALUE, "Load request and rejected list");
        items[2] = new SelectItem(Constants.APPROVE_VALUE, "Load approved list");
        return items;
    }

    public void searchByStatus(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedStatus = Integer.valueOf(String.valueOf(event.getNewValue()));
            if (selectedStatus == Constants.PREPARE_VALUE) {
                status.setStatus1(Constants.PREPARE_VALUE);
                status.setStatus2(Constants.CHECK_REJECT_VALUE);
                status.setStatus3(Constants.APPROVE_REJECT_VALUE);
                terminationList = terminationRequestBeanLocal.loadTerminationReqList(status, sessionBeanLocal.getUserId());
                terminationRequestDataModel = new ListDataModel(terminationList);
            } else if (selectedStatus == Constants.APPROVE_VALUE) {
                status.setStatus1(Constants.APPROVE_VALUE);
                status.setStatus2(Constants.CHECK_APPROVE_VALUE);
                terminationList = terminationRequestBeanLocal.loadTerminationList(status, sessionBeanLocal.getUserId());
                terminationRequestDataModel = new ListDataModel(terminationList);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="DMS service New">
    StreamedContent file;
    List<DocumentModel> newDoclist = new ArrayList<DocumentModel>();
    List<Integer> savedDocIds = new ArrayList<>();
    DmsHandler dmsHandler = new DmsHandler();
    DocumentModel documentModel = new DocumentModel();
    DataModel<DocumentModel> docValueModel;

    public DocumentModel getDocumentModel() {
        return documentModel;
    }

    public void setDocumentModel(DocumentModel documentModel) {
        this.documentModel = documentModel;
    }

    public DataModel<DocumentModel> getDocValueModel() {
        return docValueModel;
    }

    public void setDocValueModel(DataModel<DocumentModel> docValueModel) {
        this.docValueModel = docValueModel;
    }

    public void onRowSelect(SelectEvent event) {
        isFileSelected = true;
        hrTerminationRequestUpload = (HrTerminationRequestUpload) event.getObject();
        hrLuDmArchive = hrTerminationRequestUpload.getDocumentId();
    }

    public void recreateDataModel() {
        terminationReqUploadsDM = null;
        terminationReqUploadsDM = new ListDataModel<>(hrTerminationRequest.getHrTerminationRequestUploadList());
    }

    public void uploadListener(FileUploadEvent event) {
        try {
            InputStream inputStream;
            fileName = event.getFile().getFileName().split("\\.")[0];
            fileType = event.getFile().getFileName().split("\\.")[1];
            inputStream = event.getFile().getInputstream();
            byteFile = dataUpload.extractByteArray(inputStream);

            if (byteFile != null) {
                byteLists.add(byteFile);
            }
            for (int fileNumber = 0; fileNumber < byteLists.size(); fileNumber++) {
                fileNameLists.add(fileName);
                fileTypeLists.add(fileType);
                byteListFinal.add(byteFile);
            }
            JsfUtil.addSuccessMessage("Upload Successfully");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public StreamedContent getFile() throws Exception {
        if (isFileSelected = true) {
            dataUpload.getHrmsFileRefNumber(hrLuDmArchive);
        }
        return file;
    }

    public void remove() {
        if (isFileSelected = false) {
            JsfUtil.addFatalMessage("Select row file you want to delete");
        } else {
            terminationRequestUploadBeanLocal.remove(hrTerminationRequestUpload);
            hrTerminationRequest.getHrTerminationRequestUploadList().remove(hrTerminationRequestUpload);
            recreateDataModel();
            JsfUtil.addSuccessMessage("Document seccesfully deleted!");
        }
    }
    // </editor-fold>
}
