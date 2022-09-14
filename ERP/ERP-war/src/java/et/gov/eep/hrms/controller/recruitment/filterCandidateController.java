/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.recruitment;

import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.lookup.HrLuEducLevelsBeanLocal;
import et.gov.eep.hrms.businessLogic.recruitment.HrCandidateSelectedBeanLocal;
import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisedJobs;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisements;
import et.gov.eep.hrms.entity.recruitment.HrCandidateSelected;
import et.gov.eep.hrms.entity.recruitment.HrCandidiates;
import et.gov.eep.hrms.entity.recruitment.HrExamPercentages;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author Benin
 */
@Named(value = "filterCandidateController")
@ViewScoped
public class filterCandidateController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity Injection">
    @Inject
            HrCandidateSelected hrCandidateSelected;
    @Inject
            HrAdvertisements hrAdvertisements;
    @Inject
            HrAdvertisedJobs hrAdvertisedJobs;
    @Inject
            HrJobTypes hrJobTypes;
    @Inject
            HrCandidiates hrCandidiates;
    @Inject
            HrLuEducLevels hrLuEducLevels;
    @Inject
            HrExamPercentages hrExamPercentages;
    @Inject
            SessionBean sessionBeanLocal;
    @Inject
            WfHrProcessed wfHrProcessed;
    @Inject
            WorkFlow workflow;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="EJB Declaration">
    
    @EJB
            HrCandidateSelectedBeanLocal hrCandidateSelectedBeanLocal;
    @EJB
            HrLuEducLevelsBeanLocal hrLuEducLevelsBeanLocal;
    @EJB
            WfHrProcessedBeanLocal workFlowBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Variable declaration">
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private boolean renderPnlSearch = true;
    private boolean renderPnlManPage = false;
    private String SaveOrUpdateButton = "Save";
    private int selectedRowIndex = -1;
    private final String advertType = "Outside";
    private String selectedJob = null;
    private String sqlFilter = "";
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="List and DataModel declaration">
    List<HrAdvertisements> bachCodes = new ArrayList<>();
    List<HrAdvertisedJobs> advertisedJobs = new ArrayList<>();
    List<HrLuEducLevels> educLevels;
    List<HrCandidiates> shortListCandidates = new ArrayList<>();
    List<HrCandidateSelected> filterCandidates = new ArrayList<>();
    DataModel<HrAdvertisedJobs> hrAdvertisedJobsDataModel = new ListDataModel<>();
    DataModel<HrCandidateSelected> filterCandidateDataModel = new ListDataModel<>();
    DataModel<WfHrProcessed> workflowDataModel = new ListDataModel<>();
//</editor-fold>

    /**
     * Creates a new instance of filterCandidateController
     */
   
    //<editor-fold defaultstate="collapsed" desc="Post Construct">
    @PostConstruct
    public void init() {
        setBachCodes(hrCandidateSelectedBeanLocal.bachCodes(advertType));
        ckbShortListed.setSelected(true);
    }
//</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="getters & setters">
    public HrCandidateSelected getHrCandidateSelected() {
        if (hrCandidateSelected == null) {
            hrCandidateSelected = new HrCandidateSelected();
        }
        return hrCandidateSelected;
    }

    public void setHrCandidateSelected(HrCandidateSelected hrCandidateSelected) {
        this.hrCandidateSelected = hrCandidateSelected;
    }

    public HrAdvertisements getHrAdvertisements() {
        if (hrAdvertisements == null) {
            hrAdvertisements = new HrAdvertisements();
        }
        return hrAdvertisements;
    }

    public void setHrAdvertisements(HrAdvertisements hrAdvertisements) {
        this.hrAdvertisements = hrAdvertisements;
    }

    public HrAdvertisedJobs getHrAdvertisedJobs() {
        if (hrAdvertisedJobs == null) {
            hrAdvertisedJobs = new HrAdvertisedJobs();
        }
        return hrAdvertisedJobs;
    }

    public void setHrAdvertisedJobs(HrAdvertisedJobs hrAdvertisedJobs) {
        this.hrAdvertisedJobs = hrAdvertisedJobs;
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

    public HrCandidiates getHrCandidiates() {
        if (hrCandidiates == null) {
            hrCandidiates = new HrCandidiates();
        }
        return hrCandidiates;
    }

    public void setHrCandidiates(HrCandidiates hrCandidiates) {
        this.hrCandidiates = hrCandidiates;
    }

    public HrLuEducLevels getHrLuEducLevels() {
        return hrLuEducLevels;
    }

    public void setHrLuEducLevels(HrLuEducLevels hrLuEducLevels) {
        this.hrLuEducLevels = hrLuEducLevels;
    }

    public HrLuEducLevelsBeanLocal getHrLuEducLevelsBeanLocal() {
        return hrLuEducLevelsBeanLocal;
    }

    public void setHrLuEducLevelsBeanLocal(HrLuEducLevelsBeanLocal hrLuEducLevelsBeanLocal) {
        this.hrLuEducLevelsBeanLocal = hrLuEducLevelsBeanLocal;
    }

    public HrExamPercentages getHrExamPercentages() {
        return hrExamPercentages;
    }

    public void setHrExamPercentages(HrExamPercentages hrExamPercentages) {
        this.hrExamPercentages = hrExamPercentages;
    }

    public WfHrProcessed getWfHrProcessed() {
        return wfHrProcessed;
    }

    public void setWfHrProcessed(WfHrProcessed wfHrProcessed) {
        this.wfHrProcessed = wfHrProcessed;
    }

    public DataModel<WfHrProcessed> getWorkflowDataModel() {
        return workflowDataModel;
    }

    public void setWorkflowDataModel(DataModel<WfHrProcessed> workflowDataModel) {
        this.workflowDataModel = workflowDataModel;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isRenderPnlSearch() {
        return renderPnlSearch;
    }

    public void setRenderPnlSearch(boolean renderPnlSearch) {
        this.renderPnlSearch = renderPnlSearch;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    public String getSelectedJob() {
        return selectedJob;
    }

    public void setSelectedJob(String selectedJob) {
        this.selectedJob = selectedJob;
    }

    public List<HrAdvertisements> getBachCodes() {
        return bachCodes;
    }

    public void setBachCodes(List<HrAdvertisements> bachCodes) {
        this.bachCodes = bachCodes;
    }

    public List<HrAdvertisedJobs> getAdvertisedJobs() {
        return advertisedJobs;
    }

    public void setAdvertisedJobs(List<HrAdvertisedJobs> advertisedJobs) {
        this.advertisedJobs = advertisedJobs;
    }

    public List<HrLuEducLevels> getEducLevels() {
        return educLevels;
    }

    public void setEducLevels(List<HrLuEducLevels> educLevels) {
        this.educLevels = educLevels;
    }

    public List<HrCandidiates> getShortListCandidates() {
        return shortListCandidates;
    }

    public void setShortListCandidates(List<HrCandidiates> shortListCandidates) {
        this.shortListCandidates = shortListCandidates;
    }

    public DataModel<HrAdvertisedJobs> getHrAdvertisedJobsDataModel() {
        return hrAdvertisedJobsDataModel;
    }

    public void setHrAdvertisedJobsDataModel(DataModel<HrAdvertisedJobs> hrAdvertisedJobsDataModel) {
        this.hrAdvertisedJobsDataModel = hrAdvertisedJobsDataModel;
    }

    public DataModel<HrCandidateSelected> getFilterCandidateDataModel() {
        return filterCandidateDataModel;
    }

    public void setFilterCandidateDataModel(DataModel<HrCandidateSelected> filterCandidateDataModel) {
        this.filterCandidateDataModel = filterCandidateDataModel;
    }

    public List<HrCandidateSelected> getFilterCandidates() {
        return filterCandidates;
    }

    public void setFilterCandidates(List<HrCandidateSelected> filterCandidates) {
        this.filterCandidates = filterCandidates;
    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="component binding">
    private HtmlSelectBooleanCheckbox ckbShortListed = new HtmlSelectBooleanCheckbox();

    public HtmlSelectBooleanCheckbox getCkbShortListed() {
        return ckbShortListed;
    }

    public void setCkbShortListed(HtmlSelectBooleanCheckbox ckbShortListed) {
        this.ckbShortListed = ckbShortListed;
    }

    private HtmlSelectBooleanCheckbox ckbToBeFiltered = new HtmlSelectBooleanCheckbox();

    public HtmlSelectBooleanCheckbox getCkbToBeFiltered() {
        return ckbToBeFiltered;
    }

    public void setCkbToBeFiltered(HtmlSelectBooleanCheckbox ckbToBeFiltered) {
        this.ckbToBeFiltered = ckbToBeFiltered;
    }

    private HtmlSelectBooleanCheckbox ckbFiltered = new HtmlSelectBooleanCheckbox();

    public HtmlSelectBooleanCheckbox getCkbFiltered() {
        return ckbFiltered;
    }

    public void setCkbFiltered(HtmlSelectBooleanCheckbox ckbFiltered) {
        this.ckbFiltered = ckbFiltered;
    }

    private boolean ckbShortListedSelected;

    public boolean isCkbShortListedSelected() {
        return ckbShortListedSelected;
    }

    public void setCkbShortListedSelected(boolean ckbShortListedSelected) {
        this.ckbShortListedSelected = ckbShortListedSelected;
    }

    private boolean ckbToBeFilteredSelected;

    public boolean isCkbToBeFilteredSelected() {
        return ckbToBeFilteredSelected;
    }

    public void setCkbToBeFilteredSelected(boolean ckbToBeFilteredSelected) {
        this.ckbToBeFilteredSelected = ckbToBeFilteredSelected;
    }

    private boolean ckbFilteredSelected;

    public boolean isCkbFilteredSelected() {
        return ckbFilteredSelected;
    }

    public void setCkbFilteredSelected(boolean ckbFilteredSelected) {
        this.ckbFilteredSelected = ckbFilteredSelected;
    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="main">
    public void btnSearch() {
        renderPnlSearch = true;
        renderPnlManPage = false;
        JsfUtil.addInformationMessage("Load table and populate");
    }

    public void createNewGatePassInfo() {
        SaveOrUpdateButton = "Save";
        switch (createOrSearchBundle) {
            case "New":
                renderPnlSearch = false;
                renderPnlManPage = true;
                createOrSearchBundle = "Search";
                setIcone("ui-icon-search");
                break;
            case "Search":
                renderPnlSearch = true;
                renderPnlManPage = false;
                createOrSearchBundle = "New";
                setIcone("ui-icon-plus");
                break;
        }
    }

    public void bachCode_vcl(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            int advertId = Integer.valueOf(event.getNewValue().toString());
            hrAdvertisements.setId(advertId);
            setAdvertisedJobs(hrCandidateSelectedBeanLocal.advertizedJobs(advertId));
            recreateAdvertisedJobsDataModel();
        }
    }

    public void recreateAdvertisedJobsDataModel() {
        hrAdvertisedJobsDataModel = null;
        hrAdvertisedJobsDataModel = new ListDataModel(new ArrayList(advertisedJobs));
    }

    public void recreateFilterCandidateDataModel(List<HrCandidateSelected> filterCandidates) {
        filterCandidateDataModel = null;
        filterCandidateDataModel = new ListDataModel(new ArrayList<>(filterCandidates));
    }

    public void advertizedJobs(SelectEvent event) {
        if (event.getObject() != null) {
            renderPnlManPage = true;
            renderPnlSearch = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
            setIcone("ui-icon-search");
            hrAdvertisedJobs = (HrAdvertisedJobs) event.getObject();
            setSelectedJob(hrAdvertisedJobs.getId().toString());
            readAssessmentWeight();
            loadCandidate();
        } else {
            setSelectedJob(null);
        }
    }

    public void readAssessmentWeight() {
        hrExamPercentages = hrCandidateSelectedBeanLocal.selectExamPercentage(hrAdvertisements.getId());
        hrExamPercentages.setTotalPercentage(Float.valueOf(hrExamPercentages.getInterviewPercentage())
                + Float.valueOf(hrExamPercentages.getWrittenPercentage())
                + Float.valueOf(hrExamPercentages.getPracticalPercentage())
                + Float.valueOf(hrExamPercentages.getOtherPercentage()));
    }

    public int calculateCandidateAgeInYears(String dateOfBirth) {
        String today = StringDateManipulation.toDayInEc();
        int candidateAgeInDays = StringDateManipulation.datesDifferenceInDays(today, dateOfBirth);
        int candidateAgeInYears = candidateAgeInDays / 365;
//        int months = (employeeAgeInDays % 365) / 30;
//        candidateAgeInYears = (months >= 6) ? candidateAgeInYears + 1 : candidateAgeInYears;
        return candidateAgeInYears;
    }

    public void ckbShortListed_vcl(ValueChangeEvent vce) {
        if (ckbShortListed.isSelected()) {
            ckbFiltered.setSelected(false);
        }
    }

    public void ckbToBeFiltered_vcl(ValueChangeEvent vce) {
        if (ckbToBeFiltered.isSelected()) {
            ckbFiltered.setSelected(false);
        }
    }

    public void ckbFiltered_vcl(ValueChangeEvent vce) {
        if (ckbFiltered.isSelected()) {
            ckbToBeFiltered.setSelected(false);
            ckbShortListed.setSelected(false);
        }
    }

    public String dateFormat(String date) {
        String[] dateFromUI;
        String calcDateOfBirth;
        if (date != null && date.contains("/")) {
            dateFromUI = date.split("/");
            calcDateOfBirth = dateFromUI[2] + "-" + dateFromUI[1] + "-" + dateFromUI[0];
            return calcDateOfBirth;
        }
        return null;
    }

    public void loadCandidate() {
        try {
            getFilterCandidates().clear();
//            if (ddnJobList.getValue() != null) {
            float writtenPercentage = 1;
            float writtenExam = 1;
            float interviewPercentage = 1;
            float interviewExam = 1;
            float practicalPercentage = 1;
            float practicalExam = 1;
            float otherResult = 1;
            float otherResultPercentage = 1;
            float totalResult = 0;
            String statusLabel = "Shortlisted";
            String advertJobId = getSelectedJob();
//            setSelectdValueFilter();
            if (ckbShortListed.isSelected() && ckbToBeFiltered.isSelected()) {
                sqlFilter += "  AND (HR_CANDIDIATES.STATUS = " + HrCandidiates.SHORTLISTED
                        + " OR HR_CANDIDATE.STATUS = " + HrCandidiates.FILTERING_REQUEST + ")";
            } else if (ckbShortListed.isSelected()) {
                sqlFilter += "  AND HR_CANDIDIATES.STATUS = " + HrCandidiates.SHORTLISTED;
            } else if (ckbToBeFiltered.isSelected()) {
                sqlFilter += "  AND HR_CANDIDIATES.STATUS = " + HrCandidiates.FILTERING_REQUEST;
            } else if (ckbFiltered.isSelected()) {
                sqlFilter += "  AND HR_CANDIDIATES.STATUS >= " + HrCandidiates.FILTERED;
            }
            System.out.println("advertJobId =" + advertJobId);
            System.out.println("sqlFilter =" + sqlFilter);
            List<HrCandidateSelected> selectedcandidiates = new ArrayList<>();
            selectedcandidiates = hrCandidateSelectedBeanLocal.readApprovedCandidates(advertJobId, sqlFilter);

//            HrExamPercentages examPercentage = hrCandidateSelectedBeanLocal.selectExamPercentage(hrAdvertisements.getId());
            if (hrExamPercentages != null && !hrExamPercentages.toString().isEmpty()) {
                if (Integer.valueOf(hrExamPercentages.getWrittenPercentage()) != 0) {
                    writtenPercentage = Float.parseFloat(hrExamPercentages.getWrittenPercentage());
                } else {
                    writtenPercentage = 100;
                }
                if (Integer.valueOf(hrExamPercentages.getPracticalPercentage()) != 0) {
                    practicalPercentage = Float.parseFloat(hrExamPercentages.getPracticalPercentage());
                } else {
                    practicalPercentage = 100;
                }
                if (Integer.valueOf(hrExamPercentages.getInterviewPercentage()) != 0) {
                    interviewPercentage = Float.parseFloat(hrExamPercentages.getInterviewPercentage());
                } else {
                    interviewPercentage = 100;
                }
                if (Integer.valueOf(hrExamPercentages.getOtherPercentage()) != 0) {
                    otherResultPercentage = Float.parseFloat(hrExamPercentages.getOtherPercentage());
                } else {
                    otherResultPercentage = 100;
                }
            }
            if (selectedcandidiates != null) {
                for (HrCandidateSelected selectedCandidate : selectedcandidiates) {
                    writtenExam = Float.parseFloat(selectedCandidate.getExamResult().toString());
                    interviewExam = Float.parseFloat(selectedCandidate.getInterviewResult().toString());
                    practicalExam = Float.parseFloat(selectedCandidate.getPracticalResult().toString());
                    otherResult = Float.parseFloat(selectedCandidate.getOtherResult().toString());
                    totalResult = writtenExam + interviewExam + practicalExam + otherResult;
                    if (hrExamPercentages != null) {
                        writtenExam = (writtenExam * writtenPercentage) / 100;
                        interviewExam = (interviewExam * interviewPercentage) / 100;
                        practicalExam = (practicalExam * practicalPercentage) / 100;
                        otherResult = (otherResult * otherResultPercentage) / 100;
                        totalResult = writtenExam + interviewExam + practicalExam + otherResult;
                    }
                    int status = Integer.valueOf(selectedCandidate.getStatus().toString());
                    statusLabel = (status == HrCandidiates.SHORTLISTED) ? "Shortlisted"
                            : (status == HrCandidiates.FILTERING_REQUEST) ? "Selected"
                                    : (status == HrCandidiates.FILTERED) ? "Approved"
                                            : (status == HrCandidiates.SELECTED_FOR_RECRUITMENT) ? "Certified" : "Unknown";
                    selectedCandidate.setStatusLabel(statusLabel);
                    selectedCandidate.setAge(calculateCandidateAgeInYears(dateFormat(selectedCandidate.getDateOfBirth())));
                    selectedCandidate.setExamResult(writtenExam);
                    selectedCandidate.setInterviewResult(interviewExam);
                    selectedCandidate.setPracticalResult(practicalExam);
                    selectedCandidate.setOtherResult(otherResult);
                    selectedCandidate.setTotalResult(totalResult);
                    filterCandidates.add(selectedCandidate);
                }
                recreateFilterCandidateDataModel(filterCandidates);
            }
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean disableSubmit;

    public boolean isDisableSubmit() {
        return disableSubmit;
    }

    public void setDisableSubmit(boolean disableSubmit) {
        this.disableSubmit = disableSubmit;
    }

    public void rowSelector() {
        selectedRowIndex = getFilterCandidateDataModel().getRowIndex();
        if (selectedRowIndex > -1) {
            int status = getFilterCandidates().get(selectedRowIndex).getStatus();
            getFilterCandidates().get(selectedRowIndex).setApproved(true);
            hrCandidateSelected = getFilterCandidates().get(selectedRowIndex);
            if (status == HrCandidiates.SHORTLISTED) {
                setDisableSubmit(false);
            } else if (status == HrCandidiates.SELECTED_FOR_RECRUITMENT
                    || status == HrCandidiates.REGISTERED_EMPLOYEE) {
                setDisableSubmit(true);
            }
        }
    }

    private List<HrCandidateSelected> selectedCandidates(int prevStatus, int currStatus) {
        try {
            List<HrCandidateSelected> selectedCandidates = new ArrayList<>();
            if (getFilterCandidates().size() > 0) {
                for (HrCandidateSelected candidate : getFilterCandidates()) {
                    if (candidate.isApproved() && candidate.getStatus() == prevStatus) {
                        HrCandidateSelected candidateList = new HrCandidateSelected();
                        candidateList.setStatus(currStatus);
                        candidateList.setFiliterApprovedBy(String.valueOf(sessionBeanLocal.getUserId()));
                        candidateList.setFiliterApprovedOn(StringDateManipulation.toDayInEc());
                        candidateList.setCandidateId(candidate.getCandidateId());
                        selectedCandidates.add(candidateList);
                    }
                }
            }
            return selectedCandidates;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void btnSubmit_action() {
        int update = hrCandidateSelectedBeanLocal.saveFilteredCandidate(selectedCandidates(HrCandidiates.SHORTLISTED, HrCandidiates.FILTERING_REQUEST));
        if (update > 0) {
            loadCandidate();
//            cleareRecmm();
//            btnSubmit.setDisabled(true);
//            setDisableComponents(true);
            JsfUtil.addSuccessMessage("Selected candidates' list for recruitment is approved successfully!");
        } else {
            JsfUtil.addFatalMessage("Error occurs while submitting selected candidates for recruitment");
        }
    }

    public void btnApprove_action() {
        int update = hrCandidateSelectedBeanLocal.saveFilteredCandidate(selectedCandidates(HrCandidiates.FILTERING_REQUEST, HrCandidiates.FILTERED));
        if (update > 0) {
            loadCandidate();
//            cleareRecmm();
//            btnSubmit.setDisabled(true);
//            setDisableComponents(true);
            JsfUtil.addSuccessMessage("Selected candidates' list for recruitment is approved successfully!");
        } else {
            JsfUtil.addFatalMessage("Error occurs while submitting selected candidates for recruitment");
        }
    }

    public void workflowDataModel() {
        workflowDataModel = null;
        workflowDataModel = new ListDataModel(new ArrayList(hrCandidateSelected.getWfHrProcessedList()));
    }

    public void saveFilterCandidates() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(sessionBeanLocal.getUserName(), "saveFilterCandidates", dataset)) {

            int update = hrCandidateSelectedBeanLocal.saveFilteredCandidate(selectedCandidates(HrCandidiates.SHORTLISTED, HrCandidiates.SELECTED_FOR_RECRUITMENT));//HrCandidiates.SHORTLISTED will be corrected by HrCandidiates.FILTERED
            if (update > 0) {
                if (workflow.isPrepareStatus()) {
                    loadCandidate();
//            cleareRecmm();
//            btnSubmit.setDisabled(true);
//            setDisableComponents(true);
                    JsfUtil.addSuccessMessage("Selected candidates' list for recruitment is submited successfully!");
                } else if (workflow.isApproveStatus() || workflow.isCheckStatus()) {
                    loadCandidate();
                    JsfUtil.addSuccessMessage("Selected candidates' list for recruitment is approved successfully!");
                }
            } else {
                JsfUtil.addFatalMessage("Error occurs while submitting selected candidates for recruitment");
            }

        } else {
            JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
            EventEntry eventEntry = new EventEntry();
            eventEntry.setSessionId(sessionBeanLocal.getSessionID());
            eventEntry.setUserId(sessionBeanLocal.getUserId());
            QName qualifiedName = new QName("", "project");
            JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
            eventEntry.setUserLogin(userName);
            JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
            eventEntry.setModule(module);
            JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveFilterCandidates");
            eventEntry.setProgram(program);
            security.addEventLog(eventEntry, dataset);
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="assessment result popup">
    String candidateName = null;
    private boolean renderExamResultPopup;

    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public boolean isRenderExamResultPopup() {
        return renderExamResultPopup;
    }

    public void setRenderExamResultPopup(boolean renderExamResultPopup) {
        this.renderExamResultPopup = renderExamResultPopup;
    }
//</editor-fold>

    public String cmdlExamResult() {
        float writtenPercentage;
        float interviewPercentage;
        float practicalPercentage;
        float otherPercentage;
        if (selectedRowIndex > -1) {
            HrCandidateSelected candidate = getFilterCandidates().get(selectedRowIndex);
//            if (candidate.getSatus() == 2 && canPrepare) {
            String candidateId = String.valueOf(candidate.getCandidateId());
            if (candidateId != null) {
                setCandidateName(candidate.getFirstName() + " " + candidate.getMiddleName() + " " + candidate.getLastName());
                if (hrExamPercentages != null && !hrExamPercentages.toString().isEmpty()) {
                    writtenPercentage = Float.valueOf(hrExamPercentages.getWrittenPercentage());
                    interviewPercentage = Float.valueOf(hrExamPercentages.getInterviewPercentage());
                    practicalPercentage = Float.valueOf(hrExamPercentages.getPracticalPercentage());
                    otherPercentage = Float.valueOf(hrExamPercentages.getOtherPercentage());
                    candidate.setExamResult(candidate.getExamResult() * 100 / writtenPercentage);
                    candidate.setInterviewResult(candidate.getInterviewResult() * 100 / interviewPercentage);
                    candidate.setPracticalResult(candidate.getPracticalResult() * 100 / practicalPercentage);
                    candidate.setOtherResult(candidate.getOtherResult() * 100 / otherPercentage);
                    hrCandidateSelected = candidate;
                    renderExamResultPopup = true;
//                    btnUpdateExam.setDisabled(false);
                }
            }
//            } else {
//                JsfUtil.addSuccessMessage("selected candidate assessment result can't be edited");
//            }
        } else {
            JsfUtil.addFatalMessage("Please, first select candidate from assessment result table");
        }
        return null;
    }

    public HrCandidateSelected readCandidateAssessmentResult() {
        HrCandidateSelected candidateAssessmentResult = new HrCandidateSelected();
        candidateAssessmentResult.setExamResult(hrCandidateSelected.getExamResult());
        candidateAssessmentResult.setInterviewResult(hrCandidateSelected.getInterviewResult());
        candidateAssessmentResult.setPracticalResult(hrCandidateSelected.getPracticalResult());
        candidateAssessmentResult.setOtherResult(hrCandidateSelected.getOtherResult());
        candidateAssessmentResult.setFiliterRecommendation(hrCandidateSelected.getFiliterRecommendation());
        candidateAssessmentResult.setFiliterRemark(hrCandidateSelected.getFiliterRemark());
        candidateAssessmentResult.setFiliterOn(StringDateManipulation.toDayInEc());
        candidateAssessmentResult.setFiliterBy("filiter by");
        candidateAssessmentResult.setCandidateId(getFilterCandidates().get(selectedRowIndex).getCandidateId());
        return candidateAssessmentResult;
    }

    public String btnSaveAssessmentResult_action() {
        int checkSave = hrCandidateSelectedBeanLocal.saveCandidateAssessmentResult(readCandidateAssessmentResult());
        if (checkSave == 1) {
//            btnUpdateExam.setDisabled(true);
//            setDisableExamResult(true);
            loadCandidate();
            renderExamResultPopup = false;
            JsfUtil.addSuccessMessage("Candidate exam result is updated successfully !");
        } else {
            JsfUtil.addFatalMessage("Candidate exam result is not updated. Please try again ?");
        }
        return null;
    }

    public void btnResetAssessmentResult_action() {
        hrCandidateSelected.setExamResult(null);
        hrCandidateSelected.setInterviewResult(null);
        hrCandidateSelected.setPracticalResult(null);
        hrCandidateSelected.setOtherResult(null);
        hrCandidateSelected.setFiliterRecommendation(null);
        hrCandidateSelected.setFiliterRemark(null);
    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="filter criteria popup">
    private Float writtenResultCriteria;
    private Float interviewResultCriteria;
    private Float practicalResultCriteria;
    private Float otherResultCriteria;

    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public Float getWrittenResultCriteria() {
        return writtenResultCriteria;
    }

    public void setWrittenResultCriteria(Float writtenResultCriteria) {
        this.writtenResultCriteria = writtenResultCriteria;
    }

    public Float getInterviewResultCriteria() {
        return interviewResultCriteria;
    }

    public void setInterviewResultCriteria(Float interviewResultCriteria) {
        this.interviewResultCriteria = interviewResultCriteria;
    }

    public Float getPracticalResultCriteria() {
        return practicalResultCriteria;
    }

    public void setPracticalResultCriteria(Float practicalResultCriteria) {
        this.practicalResultCriteria = practicalResultCriteria;
    }

    public Float getOtherResultCriteria() {
        return otherResultCriteria;
    }

    public void setOtherResultCriteria(Float otherResultCriteria) {
        this.otherResultCriteria = otherResultCriteria;
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="component binding">
    private HtmlSelectBooleanCheckbox ckbWrittenExamTaken = new HtmlSelectBooleanCheckbox();
    private HtmlSelectBooleanCheckbox ckbPracticalExamTaken = new HtmlSelectBooleanCheckbox();
    private HtmlSelectBooleanCheckbox ckbInterviewExamTaken = new HtmlSelectBooleanCheckbox();

    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public HtmlSelectBooleanCheckbox getCkbWrittenExamTaken() {
        return ckbWrittenExamTaken;
    }

    public void setCkbWrittenExamTaken(HtmlSelectBooleanCheckbox ckbWrittenExamTaken) {
        this.ckbWrittenExamTaken = ckbWrittenExamTaken;
    }

    public HtmlSelectBooleanCheckbox getCkbPracticalExamTaken() {
        return ckbPracticalExamTaken;
    }

    public void setCkbPracticalExamTaken(HtmlSelectBooleanCheckbox ckbPracticalExamTaken) {
        this.ckbPracticalExamTaken = ckbPracticalExamTaken;
    }

    public HtmlSelectBooleanCheckbox getCkbInterviewExamTaken() {
        return ckbInterviewExamTaken;
    }

    public void setCkbInterviewExamTaken(HtmlSelectBooleanCheckbox ckbInterviewExamTaken) {
        this.ckbInterviewExamTaken = ckbInterviewExamTaken;
    }
//</editor-fold>

//</editor-fold>
    public String setFilterCriteria() {
        boolean result = true;
        sqlFilter = "";
        float practicalPercentage = 100;
        float interviewPercentage = 100;
        float writtenPercentage = 100;
        float otherResultPercentage = 100;
        float totalResult = 0;

//        HrExamPercentages examPercentage = hrCandidateSelectedBeanLocal.selectExamPercentage(hrAdvertisements.getId());
        if (hrExamPercentages != null && !hrExamPercentages.toString().isEmpty()) {
            if (Integer.valueOf(hrExamPercentages.getWrittenPercentage()) != 0) {
                writtenPercentage = Float.parseFloat(hrExamPercentages.getWrittenPercentage());
            }
            if (Integer.valueOf(hrExamPercentages.getInterviewPercentage()) != 0) {
                interviewPercentage = Float.parseFloat(hrExamPercentages.getInterviewPercentage());
            }
            if (Integer.valueOf(hrExamPercentages.getPracticalPercentage()) != 0) {
                practicalPercentage = Float.parseFloat(hrExamPercentages.getPracticalPercentage());
            }
            if (Integer.valueOf(hrExamPercentages.getOtherPercentage()) != 0) {
                otherResultPercentage = Float.parseFloat(hrExamPercentages.getOtherPercentage());
            }
        }

        if (ckbWrittenExamTaken.isSelected()) {
            sqlFilter += " AND HR_CANDIDATE_SELECTED.EXAM_RESULT<>0 ";
        }
        if (ckbPracticalExamTaken.isSelected()) {
            sqlFilter += " AND HR_CANDIDATE_SELECTED.PRACTICAL_RESULT<>0 ";

        }
        if (ckbInterviewExamTaken.isSelected()) {
            sqlFilter += " AND HR_CANDIDATE_SELECTED.INTERVIEW_RESULT<>0 ";
        }

        Float numWritten = getWrittenResultCriteria();

        if (!String.valueOf(numWritten).isEmpty() && numWritten != null) {
//            float writtenResult = numWritten * writtenPercentage / 100; //the written criteria value is converted in to percentage
            float writtenResult = numWritten * 100 / writtenPercentage; //the written criteria value is converted in to decimal b/c the user assumed to enter a criteria value as percentage & the value in the data base is in decimal
            sqlFilter += "  AND  HR_CANDIDATE_SELECTED.EXAM_RESULT >= " + writtenResult + " ";
        }
//            else {
//            result = false;
//            JsfUtil.addSuccessMessage("Invalid number format on the written exam result input filed.");
//        }

        Float numInterview = getInterviewResultCriteria();

        if (!String.valueOf(numInterview).isEmpty() && numInterview != null) {
//            float interviewResult = numInterview * interviewPercentage / 100; //the interview criteria value is converted in to percentage
            float interviewResult = numInterview * 100 / interviewPercentage; //the interview criteria value is converted in to decimal b/c the user assumed to enter a criteria value as percentage & the value in the data base is in decimal 
            sqlFilter += "  AND  HR_CANDIDATE_SELECTED.INTERVIEW_RESULT >= " + interviewResult + "";
        }
//            else {
//            result = false;
//            JsfUtil.addSuccessMessage("Invalid number format on the interview exam result input filed.");
//        }

        Float numPractical = getPracticalResultCriteria();

        if (!String.valueOf(numPractical).isEmpty() && numPractical != null) {
//            float practicalResult = numPractical * practicalPercentage / 100; //the practical criteria value is converted in to percentage
            float practicalResult = numPractical * 100 / practicalPercentage; //the practical criteria value is converted in to decimal b/c the user assumed to enter a criteria value as percentage & the value in the data base is in decimal 
            sqlFilter += "  AND  HR_CANDIDATE_SELECTED.PRACTICAL_RESULT >= " + practicalResult + "";
        }

//        else {
//            result = false;
//            JsfUtil.addSuccessMessage("Invalid number format on the practical exam result input filed.");
//        }
//        Float numOther = getOtherResultCriteria();
//        if (!String.valueOf(numOther).isEmpty() && numOther != null) {
//           float otherResultResult=numOther * otherResultPercentage / 100; /the other criteria value is converted in to percentage
//        float otherResultResult = numOther * 100 / otherResultPercentage; //the other criteria value is converted in to decimal b/c the user assumed to enter a criteria value as percentage & the value in the data base is in decimal 
//           sqlFilter += "  AND  HR_CANDIDATE_SELECTED.OTHER_RESULT >= " + otherResultResult + "";
//        
//        } else {
//            result = false;
//            JsfUtil.addSuccessMessage("Invalid number format on the other result input filed.");
//        }
        if (!result) {
            sqlFilter = "";
        }
        return null;
    }

    public String saveFilterFilterCriteria() {
//        renderFilterCriteriaPopup.setRendered(false);
        setFilterCriteria();
        loadCandidate();
        return null;
    }

    public void btnResetFilterCriteria() {
        sqlFilter = "";
        loadCandidate();
    }

    private boolean renderFilterCriteriaPopup;

    public boolean isRenderFilterCriteriaPopup() {
        return renderFilterCriteriaPopup;
    }

    public void setRenderFilterCriteriaPopup(boolean renderFilterCriteriaPopup) {
        this.renderFilterCriteriaPopup = renderFilterCriteriaPopup;
    }

    public String popUpCriteria() {
        renderFilterCriteriaPopup = true;
        return null;
    }
//</editor-fold>
}
