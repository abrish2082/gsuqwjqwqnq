/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.termination;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.businessLogic.HrLuDmArchiveBeanLocal;
import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.controller.DataUpload;
import et.gov.eep.commonApplications.entity.HrLuDmArchive;
import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.termination.RetirementRequestBeanLocal;
import et.gov.eep.hrms.businessLogic.termination.RetirementRequestuploadBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.termination.HrRetirementRequest;
import et.gov.eep.hrms.entity.termination.HrRetirementRequestUpload;

/**
 *
 * @author INSA
 */
@Named(value = "retirementRequestConrtroller")
@ViewScoped
public class RetirementRequestConrtroller implements Serializable {

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
    DataUpload dataUpload;
    @Inject
    HrLuDmArchive hrLuDmArchive;

    @EJB
    RetirementRequestBeanLocal retirementRequestBeanLocal;
    @EJB
    HrEmployeeBeanLocal employeeBean;
    @EJB
    RetirementRequestuploadBeanLocal retirementRequestuploadBeanLocal;
    @EJB
    WfHrProcessedBeanLocal wfHrProcessedBeanLocal;
    @EJB
    HrLuDmArchiveBeanLocal hrLuDmArchiveBeanLocal;

    private int updateStatus = 0;
    String experience;
    String employeeAge;
    String requestDate;
    String retirementDate;
    private String saveOrUpdate = "Save";
    private String newOrSearch = "Add New";
    private String icone = "ui-icon-plus";
    private boolean searchPage = true;
    private boolean newPage = false;
    private boolean disableBtn = false;
    private boolean btnNewRender = false;
    private int selectedStatus;
    WorkFlow workFlow = new WorkFlow();
    Status status = new Status();

    List<SelectItem> filterByStatus = new ArrayList<>();
    DataModel<HrRetirementRequest> retirementDataModel = new ListDataModel<>();
    DataModel<WfHrProcessed> workflowDataModel = new ListDataModel<>();
    private List<HrRetirementRequest> retirementRequestList = new ArrayList<>();
    DataModel<HrRetirementRequestUpload> retirementUploadDataModel;

    public RetirementRequestConrtroller() {
    }

    @PostConstruct
    public void init() {
//        hrRetirementRequest.setRequestDate(StringDateManipulation.toDayInEc());
//        wfHrProcessed.setProcessedOn(StringDateManipulation.toDayInEc());
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

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getEmployeeAge() {
        return employeeAge;
    }

    public void setEmployeeAge(String employeeAge) {
        this.employeeAge = employeeAge;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRetirementDate() {
        return retirementDate;
    }

    public void setRetirementDate(String retirementDate) {
        this.retirementDate = retirementDate;
    }

    public List<SelectItem> getFilterByStatus() {
        return retirementRequestBeanLocal.filterByStatus();
    }

    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }

    public DataModel<HrRetirementRequest> getRetirementDataModel() {
        return retirementDataModel;
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

    public DataModel<HrRetirementRequestUpload> getRetirementUploadDataModel() {
        return retirementUploadDataModel;
    }

    public void setRetirementUploadDataModel(DataModel<HrRetirementRequestUpload> retirementUploadDataModel) {
        this.retirementUploadDataModel = retirementUploadDataModel;
    }
    //</editor-fold>

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
        resetRetirementRqst();
        newPage = true;
        searchPage = false;
        btnNewRender = false;
    }

    public void workflowDataModel() {
        workflowDataModel = null;
        workflowDataModel = new ListDataModel(new ArrayList(hrRetirementRequest.getWfHrProcessedList()));
    }

    //<editor-fold defaultstate="collapsed" desc="year calculation">
    public void calculateAge() {
        String dateofBirthInDay[] = hrEmployees.getDob().split("/");
        int inDay = Integer.parseInt(dateofBirthInDay[0]);
        String dateofBirthInMonth[] = hrEmployees.getDob().split("/");
        int inMonth = Integer.parseInt(dateofBirthInMonth[1]);
        String dateofBirthInyear[] = hrEmployees.getDob().split("/");
        int inYear = Integer.parseInt(dateofBirthInyear[2]);
        String toDayDay[] = StringDateManipulation.toDayInEc().split("-");
        int day = Integer.parseInt(toDayDay[2]);
        String toDayMonth[] = StringDateManipulation.toDayInEc().split("-");
        int month = Integer.parseInt(toDayMonth[1]);
        String toDayYear[] = StringDateManipulation.toDayInEc().split("-");
        int year = Integer.parseInt(toDayYear[0]);
        int ageDay = ((day - inDay) + ((month - inMonth) * 30) + ((year - inYear) * 365));
        int ageInYear = ageDay / 365;
        int ageInMonth = ((ageDay % 365) / 30);
        employeeAge = (ageInYear + " " + "Year and " + ageInMonth + " month");
    }

    public void calculateServiceYear() {
        String hireDateInDay[] = hrEmployees.getHireDate().split("/");
        int inDay = Integer.parseInt(hireDateInDay[0]);
        String hireDateInMonth[] = hrEmployees.getHireDate().split("/");
        int inMonth = Integer.parseInt(hireDateInMonth[1]);
        String hireDateInyear[] = hrEmployees.getHireDate().split("/");
        int inYear = Integer.parseInt(hireDateInyear[2]);
        String toDayDay[] = StringDateManipulation.toDayInEc().split("-");
        int day = Integer.parseInt(toDayDay[2]);
        String toDayMonth[] = StringDateManipulation.toDayInEc().split("-");
        int month = Integer.parseInt(toDayMonth[1]);
        String toDayYear[] = StringDateManipulation.toDayInEc().split("-");
        int year = Integer.parseInt(toDayYear[0]);
        int experienceDay = ((day - inDay) + ((month - inMonth) * 30) + ((year - inYear) * 365));
        int experienceInYear = experienceDay / 365;
        int experienceInMonth = ((experienceDay % 365) / 30);
        experience = (experienceInYear + " " + "Year and " + experienceInMonth + " month");
    }

    public void calculateServiceYearForPopulate() {
        String hireDateInDay[] = hrRetirementRequest.getEmpId().getHireDate().split("/");
        int inDay = Integer.parseInt(hireDateInDay[0]);
        String hireDateInMonth[] = hrRetirementRequest.getEmpId().getHireDate().split("/");
        int inMonth = Integer.parseInt(hireDateInMonth[1]);
        String hireDateInyear[] = hrRetirementRequest.getEmpId().getHireDate().split("/");
        int inYear = Integer.parseInt(hireDateInyear[2]);
        String toDayDay[] = StringDateManipulation.toDayInEc().split("-");
        int day = Integer.parseInt(toDayDay[2]);
        String toDayMonth[] = StringDateManipulation.toDayInEc().split("-");
        int month = Integer.parseInt(toDayMonth[1]);
        String toDayYear[] = StringDateManipulation.toDayInEc().split("-");
        int year = Integer.parseInt(toDayYear[0]);
        int experienceDay = ((day - inDay) + ((month - inMonth) * 30) + ((year - inYear) * 365));
        int experienceInYear = experienceDay / 365;
        int experienceInMonth = ((experienceDay % 365) / 30);
        experience = (experienceInYear + " " + "Year and " + experienceInMonth + " month");
    }

    public void checkNoticePeriod() {
        if ((hrRetirementRequest.getRetirementDate() != null) && (hrRetirementRequest.getRetirementDate().compareTo("") != 0)) {
            if (hrRetirementRequest.getRetirementDate().contains("/")) {
                String[] dateFromUI = hrRetirementRequest.getRetirementDate().split("/");
                retirementDate = hrRetirementRequest.getRetirementDate();
                retirementDate = dateFromUI[2] + "/" + dateFromUI[1] + "/" + dateFromUI[0];
                setRetirementDate(dateFromUI[2] + "-" + dateFromUI[1] + "-" + dateFromUI[0]);
                String today = StringDateManipulation.toDayInEc();
                int calculatedRetirementDate = StringDateManipulation.differenceInYears(today, retirementDate);
                if (calculatedRetirementDate < 30) {
                    checkRetirementDate();
                } else {
                    JsfUtil.addFatalMessage("You notice period for retirement is one month");
                }
            }
        }
    }

    public void checkRetirementDate() {
        if ((hrRetirementRequest.getRequestDate() != null) && (hrRetirementRequest.getRequestDate().compareTo("") != 0)
                && (hrRetirementRequest.getRetirementDate() != null) && (hrRetirementRequest.getRetirementDate().compareTo("") != 0)) {
            if (hrRetirementRequest.getRequestDate().contains("/")) {
                String dateOfRequest;
                String dateOfRetirement;
                String[] dateOfRequestFromUI = hrRetirementRequest.getRequestDate().split("/");
                String[] dateOfRetirementFromUI = hrRetirementRequest.getRetirementDate().split("/");
                dateOfRequest = dateOfRequestFromUI[2] + "-" + dateOfRequestFromUI[1] + "-" + dateOfRequestFromUI[0];
                dateOfRetirement = dateOfRetirementFromUI[2] + "-" + dateOfRetirementFromUI[1] + "-" + dateOfRetirementFromUI[0];
                int calculateDiff = StringDateManipulation.differenceInYears(dateOfRetirement, dateOfRequest);
                if (calculateDiff >= 0) {
                    saveRetirementRequest();
                } else {
                    JsfUtil.addFatalMessage("Retirement date should be greater than request date");
                }
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="main">
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
            hrRetirementRequest.setEmpId(hrEmployees);
            hrDepartments = hrEmployees.getDeptId();
            hrJobTypes = hrEmployees.getJobId();
            calculateAge();
            calculateServiceYear();
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }

    public void checkDate() {
        if (hrRetirementRequest.getRetirementDate() != null) {
            checkNoticePeriod();
        } else {
            JsfUtil.addFatalMessage("Retirement Date is required");
        }
    }
    int dateDifference;

    public void dateValidation() {
        String startMonth[] = wfHrProcessed.getProcessedOn().split("/");
        int isSartMonth = Integer.parseInt(startMonth[1]);
        String startYear[] = wfHrProcessed.getProcessedOn().split("/");
        int isStartYear = Integer.parseInt(startYear[2]);
        String startDate[] = wfHrProcessed.getProcessedOn().split("/");
        int isStartDate = Integer.parseInt(startDate[0]);
        String endDay[] = hrRetirementRequest.getRetirementDate().split("/");
        int isEndDay = Integer.parseInt(endDay[0]);
        String endMonth[] = hrRetirementRequest.getRetirementDate().split("/");
        int isEndMonth = Integer.parseInt(endMonth[1]);
        String endYear[] = hrRetirementRequest.getRetirementDate().split("/");
        int isEndYear = Integer.parseInt(endYear[2]);
        dateDifference = ((isEndDay - isStartDate) + ((isEndMonth - isSartMonth) * 30) + ((isEndYear - isStartYear) * 365));
    }

    public void saveRetirementRequest() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveRetirementRequest", dataset)) {
                HrRetirementRequestUpload diagnosisUpload = new HrRetirementRequestUpload();
                dateValidation();
                if (dateDifference < 0) {
                    JsfUtil.addFatalMessage("Retirement date should be greater than processed date. Try again!");
                } else {
                    try {
                        if (updateStatus == 0) {
                            for (int fileIndex = 0; fileIndex < byteLists.size(); fileIndex++) {
                                hrLuDmArchive.setFileName(fileNameLists.get(fileIndex));
                                hrLuDmArchive.setFileType(fileTypeLists.get(fileIndex));
                                hrLuDmArchive.setUploadFile(byteListFinal.get(fileIndex));
                                hrLuDmArchiveBeanLocal.create(hrLuDmArchive);
                                diagnosisUpload.setDocumentId(hrLuDmArchive);
                                diagnosisUpload.setRetirementId(hrRetirementRequest);
                                hrRetirementRequest.getHrRetirementRequestUploadList().add(diagnosisUpload);
                                diagnosisUpload = new HrRetirementRequestUpload();
                                hrLuDmArchive = new HrLuDmArchive();
                            }
                            hrRetirementRequest.setStatus(Constants.PREPARE_VALUE);
                            hrRetirementRequest.setPreparedBy(String.valueOf(sessionBeanLocal.getUserId()));
                            hrRetirementRequest.setDescription(wfHrProcessed.getCommentGiven());
                            wfHrProcessed.setRetirementId(hrRetirementRequest);
                            wfHrProcessed.setDecision(hrRetirementRequest.getStatus());
                            wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
                            wfHrProcessed.setProcessedOn(hrRetirementRequest.getRequestDate());
                            retirementRequestBeanLocal.save(hrRetirementRequest);
                            wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                            JsfUtil.addSuccessMessage("Successfully saved");
                            resetRetirementRqst();
                        } else {
                            for (int fileIndex = 0; fileIndex < byteLists.size(); fileIndex++) {
                                hrLuDmArchive.setFileName(fileNameLists.get(fileIndex));
                                hrLuDmArchive.setFileType(fileTypeLists.get(fileIndex));
                                hrLuDmArchive.setUploadFile(byteListFinal.get(fileIndex));
                                hrLuDmArchiveBeanLocal.edit(hrLuDmArchive);
                                diagnosisUpload.setDocumentId(hrLuDmArchive);
                                diagnosisUpload.setRetirementId(hrRetirementRequest);
                                hrRetirementRequest.getHrRetirementRequestUploadList().add(diagnosisUpload);
                                diagnosisUpload = new HrRetirementRequestUpload();
                                hrLuDmArchive = new HrLuDmArchive();
                            }
                            retirementRequestBeanLocal.edit(hrRetirementRequest);
                            JsfUtil.addSuccessMessage("Successfully updated");
                            resetRetirementRqst();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        JsfUtil.addFatalMessage("Error occure while save update");
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveRetirementRequest");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resetRetirementRqst() {
        hrRetirementRequest = new HrRetirementRequest();
        hrEmployees = new HrEmployees();
        hrDepartments = new HrDepartments();
        hrJobTypes = new HrJobTypes();
        wfHrProcessed = new WfHrProcessed();
        workflowDataModel = null;
        disableBtn = false;
        updateStatus = 0;
        saveOrUpdate = "Save";
    }

    int reqStatus = 0;

    private void populateTable() {
        try {
            List<HrRetirementRequest> readFilteredRetirement = retirementRequestBeanLocal.loadRetirementList(reqStatus);
            retirementDataModel = new ListDataModel(readFilteredRetirement);
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
        hrRetirementRequest = (HrRetirementRequest) event.getObject();
        hrRetirementRequest.setId(hrRetirementRequest.getId());
        hrRetirementRequest = retirementRequestBeanLocal.getSelectedRequest(hrRetirementRequest.getId());
        hrEmployees = hrRetirementRequest.getEmpId();
        hrDepartments = hrEmployees.getDeptId();
        hrJobTypes = hrEmployees.getJobId();
        calculateAge();
        calculateServiceYearForPopulate();
        recreateUploadDataModel();
        workflowDataModel();
        if (hrRetirementRequest.getStatus().equals(Constants.APPROVE_VALUE) || hrRetirementRequest.getStatus().equals(Constants.CHECK_APPROVE_VALUE)) {
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

    // <editor-fold defaultstate="collapsed" desc="DMS service">
    StreamedContent file;
    String fileName;
    String fileType;
    byte[] byteFile;
    List<byte[]> byteLists = new ArrayList<>();
    List<byte[]> byteListFinal = new ArrayList<>();
    List<String> fileNameLists = new ArrayList<>();
    List<String> fileTypeLists = new ArrayList<>();
    private boolean isFileSelected = false;

    public void onRowSelect(SelectEvent event) {
        isFileSelected = true;
        hrRetirementRequestUpload = (HrRetirementRequestUpload) event.getObject();
        hrLuDmArchive = hrRetirementRequestUpload.getDocumentId();
    }

    public void recreateUploadDataModel() {
        retirementUploadDataModel = null;
        retirementUploadDataModel = new ListDataModel<>(hrRetirementRequest.getHrRetirementRequestUploadList());
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
            retirementRequestuploadBeanLocal.remove(hrRetirementRequestUpload);
            hrRetirementRequest.getHrRetirementRequestUploadList().remove(hrRetirementRequestUpload);
            recreateUploadDataModel();
            JsfUtil.addSuccessMessage("Document seccesfully deleted!");
        }
    }
    // </editor-fold>

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
            retirementRequestList = retirementRequestBeanLocal.loadaApprove(_status, sessionBeanLocal.getUserId());
            retirementDataModel = new ListDataModel<>(retirementRequestList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void searchReject(Status _status) {
        try {
            retirementRequestList = retirementRequestBeanLocal.loadReject(_status, sessionBeanLocal.getUserId());
            retirementDataModel = new ListDataModel<>(retirementRequestList);
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

    public void searchByStatus(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            List<HrRetirementRequest> retirementList = new ArrayList<>();;
            selectedStatus = Integer.valueOf(String.valueOf(event.getNewValue()));
            if (selectedStatus == Constants.PREPARE_VALUE) {
                status.setStatus1(Constants.PREPARE_VALUE);
                status.setStatus2(Constants.CHECK_REJECT_VALUE);
                status.setStatus3(Constants.APPROVE_REJECT_VALUE);
                retirementList = retirementRequestBeanLocal.loadRetirementReqList(status, sessionBeanLocal.getUserId());
                retirementDataModel = new ListDataModel(retirementList);
            } else if (selectedStatus == Constants.APPROVE_VALUE) {
                status.setStatus1(Constants.APPROVE_VALUE);
                status.setStatus2(Constants.CHECK_APPROVE_VALUE);
                retirementList = retirementRequestBeanLocal.loadRetirementList(status, sessionBeanLocal.getUserId());
                retirementDataModel = new ListDataModel(retirementList);
            }
        }
    }
}
