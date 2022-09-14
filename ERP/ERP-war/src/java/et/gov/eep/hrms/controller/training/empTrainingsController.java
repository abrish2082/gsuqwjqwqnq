/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.training;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
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
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.employee.HrEmpTrainingsBeanLocal;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrLuTrainingCategoryBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrTdAnnualNeedRequestsBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrTdAnnualTraParticipantsBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrTdAnnualTrainingNeedsBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrTdEmpTrainingsBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrTdUnplanTraParticipantBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrTdUnplanTrainingRequestBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrTrainerProfilesBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrTrainingCoursesBeanLocal;
import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.employee.HrEmpTrainings;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationSessions;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.training.HrLuTrainingCategory;
import et.gov.eep.hrms.entity.training.HrTdAnnualNeedRequests;
import et.gov.eep.hrms.entity.training.HrTdAnnualTraParticipants;
import et.gov.eep.hrms.entity.training.HrTdAnnualTrainingNeeds;
import et.gov.eep.hrms.entity.training.HrTdCourses;
import et.gov.eep.hrms.entity.training.HrTdEmpTrainings;
import et.gov.eep.hrms.entity.training.HrTdTrainerProfiles;
import et.gov.eep.hrms.entity.training.HrTdUnplanTraParticipant;
import et.gov.eep.hrms.entity.training.HrTdUnplanTrainingRequest;

/**
 *
 * @author Abdi_Pc
 */
@Named(value = "empTrainingsController")
@ViewScoped
public class empTrainingsController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity, business logic & variables">
    @EJB
    HrTdEmpTrainingsBeanLocal hrTdEmpTrainingsBeanLocal;
    @EJB
    HrTdUnplanTraParticipantBeanLocal HrTdUnplanTraParticipantBeanLocal;
    @EJB
    HrTdAnnualTraParticipantsBeanLocal hrTdAnnualTraParticipantsBeanLocal;
    @EJB
    HrTdUnplanTrainingRequestBeanLocal hrTdUnplanTrainingRequestBeanLocal;
    @EJB
    HrDepartmentsBeanLocal hrDepartmentsBeanLocal;
    @EJB
    HrLuTrainingCategoryBeanLocal hrLuTrainingCategoryBeanLocal;
    @EJB
    HrTrainingCoursesBeanLocal hrTrainingCoursesBeanLocal;
    @EJB
    HrTrainerProfilesBeanLocal hrTrainerProfilesBeanLocal;
    @EJB
    HrEmployeeBeanLocal hrEmployeeBeanLocal;
    @EJB
    HrTdAnnualNeedRequestsBeanLocal hrTdAnnualNeedRequestsBeanLocal;
    @EJB
    HrTdAnnualTrainingNeedsBeanLocal hrTdAnnualTrainingNeedsBeanLocal;
    @EJB
    HrEmpTrainingsBeanLocal hrEmpTrainingsBeanLocal;

    @Inject
    HrAddresses hrAddresses;
    @Inject
    HrEmpTrainings hrEmpTrainings;
    @Inject
    HrTdEmpTrainings hrTdEmpTrainings;
    @Inject
    HrTdAnnualTraParticipants hrTdAnnualTraParticipants;
    @Inject
    HrTdAnnualNeedRequests hrTdAnnualNeedRequests;
    @Inject
    HrTdAnnualTrainingNeeds hrTdAnnualTrainingNeeds;
    @Inject
    HrLuTrainingCategory hrLuTrainingCategory;
    @Inject
    HrTdUnplanTraParticipant hrTdUnplanTraParticipant;
    @Inject
    HrTdUnplanTrainingRequest hrTdUnplanTrainingRequest;
    @Inject
    HrDepartments hrDepartments;
    @Inject
    HrJobTypes hrJobTypes;
    @Inject
    HrTdCourses hrTdCourses;
    @Inject
    HrTdTrainerProfiles hrTdTrainerProfiles;
    @Inject
    HrEvaluationSessions hrEvaluationSessions;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    SessionBean sessionBeanLocal;

    private String plannedSearch = "true";
    private String unplannedSearch = "false";
    private String updateTrainner = "false";
    private String addorUpdate = "Add";
    private String saveorUpdateBundle = "Save";
    private String createOrSearchBundle = "New";
    private String headerTitle = "Search....";
    private String icone = "ui-icon-plus";
    int updateStatus = 0;

    private Boolean plannnedCourse = false;
    private Boolean unplannedCourse = false;
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private boolean btnNewRender = false;

    List<HrTdTrainerProfiles> institutionList;
    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    List<HrLuTrainingCategory> trainingCatagoryList = new ArrayList<>();
    List<HrTdCourses> trainningCourseList = new ArrayList<>();
    List<HrTdUnplanTrainingRequest> unplannedBudgetYearList;
    List<HrTdAnnualNeedRequests> plannedBudgetYearList;
    List<HrTdCourses> unplannedcourseList;
    List<HrTdAnnualTrainingNeeds> annualCourseList;
    List<HrTdUnplanTraParticipant> traineeList;
    List<HrTdAnnualTraParticipants> plannedTraineeList;
    List<HrTdEmpTrainings> plannedResponseList;
    List<HrTdEmpTrainings> unplannedResponseList;
    List<HrTdEmpTrainings> participantList;
    List<HrTdEmpTrainings> participantListOld;
    List<HrTdCourses> annCourseList;
    private List<SelectItem> budgetYears;

    DataModel<HrTdEmpTrainings> empHistoryDataModel = new ListDataModel<>();
    DataModel<HrTdEmpTrainings> empHistoryDataModelUpdate = new ListDataModel<>();
    DataModel<HrTdUnplanTraParticipant> participantDataModel = new ListDataModel<>();
    DataModel<HrTdAnnualTraParticipants> plannedParticipantDataModel = new ListDataModel<>();

    //</editor-fold>
    @PostConstruct
    public void init() {
        plannedBudgetYearList = hrTdEmpTrainingsBeanLocal.findBudgetYear();
        unplannedBudgetYearList = hrTdEmpTrainingsBeanLocal.findUBudgetYear();
        trainingCatagoryList = hrTdUnplanTrainingRequestBeanLocal.findAll(hrLuTrainingCategory);
        institutionList = hrTdUnplanTrainingRequestBeanLocal.findAll(hrTdTrainerProfiles);
        plannedResponseList = hrTdEmpTrainingsBeanLocal.findPlannedParticipant(hrTdEmpTrainings);
        unplannedResponseList = hrTdEmpTrainingsBeanLocal.findUnPlannedParticipant(hrTdEmpTrainings);
        hrTdEmpTrainings.setType(1);
    }

    //<editor-fold defaultstate="collapsed" desc="Getters setters">
    public List<HrTdUnplanTraParticipant> getTraineeList() {
        return traineeList;
    }

    public void setTraineeList(List<HrTdUnplanTraParticipant> traineeList) {
        this.traineeList = traineeList;
    }

    public List<HrTdAnnualTraParticipants> getPlannedTraineeList() {
        return plannedTraineeList;
    }

    public void setPlannedTraineeList(List<HrTdAnnualTraParticipants> plannedTraineeList) {
        this.plannedTraineeList = plannedTraineeList;
    }

    public List<HrTdEmpTrainings> getPlannedResponseList() {
        return plannedResponseList;
    }

    public void setPlannedResponseList(List<HrTdEmpTrainings> plannedResponseList) {
        this.plannedResponseList = plannedResponseList;
    }

    public List<HrTdEmpTrainings> getUnplannedResponseList() {
        return unplannedResponseList;
    }

    public void setUnplannedResponseList(List<HrTdEmpTrainings> unplannedResponseList) {
        this.unplannedResponseList = unplannedResponseList;
    }

    public List<HrTdTrainerProfiles> getInstitutionList() {
        return institutionList;
    }

    public List<HrTdEmpTrainings> getParticipantList() {
        if (participantList == null) {
            participantList = new ArrayList<>();
        }
        return participantList;
    }

    public void setParticipantList(List<HrTdEmpTrainings> participantList) {
        this.participantList = participantList;
    }

    public List<HrTdEmpTrainings> getParticipantListOld() {
        if (participantListOld == null) {
            participantListOld = new ArrayList<>();
        }
        return participantListOld;
    }

    public void setParticipantListOld(List<HrTdEmpTrainings> participantListOld) {
        this.participantListOld = participantListOld;
    }

    public void setInstitutionList(List<HrTdTrainerProfiles> institutionList) {
        this.institutionList = institutionList;
    }

    public List<HrLuTrainingCategory> getTrainingCatagoryList() {
        return trainingCatagoryList;
    }

    public void setTrainingCatagoryList(List<HrLuTrainingCategory> trainingCatagoryList) {
        this.trainingCatagoryList = trainingCatagoryList;
    }

    public List<HrTdCourses> getUnplannedcourseList() {
        return unplannedcourseList;
    }

    public void setUnplannedcourseList(List<HrTdCourses> unplannedcourseList) {
        this.unplannedcourseList = unplannedcourseList;
    }

    public List<HrTdCourses> getTrainningCourseList() {
        return trainningCourseList;
    }

    public void setTrainningCourseList(List<HrTdCourses> trainningCourseList) {
        this.trainningCourseList = trainningCourseList;
    }

    public List<HrTdCourses> getAnnCourseList() {
        return annCourseList;
    }

    public void setAnnCourseList(List<HrTdCourses> annCourseList) {
        this.annCourseList = annCourseList;
    }

    public List<HrTdAnnualTrainingNeeds> getAnnualCourseList() {
        return annualCourseList;
    }

    public void setAnnualCourseList(List<HrTdAnnualTrainingNeeds> annualCourseList) {
        this.annualCourseList = annualCourseList;
    }

    public HrLuTrainingCategory getHrLuTrainingCategory() {
        return hrLuTrainingCategory;
    }

    public void setHrLuTrainingCategory(HrLuTrainingCategory hrLuTrainingCategory) {
        this.hrLuTrainingCategory = hrLuTrainingCategory;
    }

    public HrJobTypes getHrJobTypes() {
        return hrJobTypes;
    }

    public void setHrJobTypes(HrJobTypes hrJobTypes) {
        this.hrJobTypes = hrJobTypes;
    }

    public HrEmployees getHrEmployees() {
        return hrEmployees;
    }

    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
    }

    public HrTdEmpTrainings getHrTdEmpTrainings() {
        return hrTdEmpTrainings;
    }

    public void setHrTdEmpTrainings(HrTdEmpTrainings hrTdEmpTrainings) {
        this.hrTdEmpTrainings = hrTdEmpTrainings;
    }

    public HrTdAnnualNeedRequests getHrTdAnnualNeedRequests() {
        return hrTdAnnualNeedRequests;
    }

    public void setHrTdAnnualNeedRequests(HrTdAnnualNeedRequests hrTdAnnualNeedRequests) {
        this.hrTdAnnualNeedRequests = hrTdAnnualNeedRequests;
    }

    public HrTdAnnualTrainingNeeds getHrTdAnnualTrainingNeeds() {
        return hrTdAnnualTrainingNeeds;
    }

    public HrTdUnplanTrainingRequest getHrTdUnplanTrainingRequest() {
        return hrTdUnplanTrainingRequest;
    }

    public void setHrTdUnplanTrainingRequest(HrTdUnplanTrainingRequest hrTdUnplanTrainingRequest) {
        this.hrTdUnplanTrainingRequest = hrTdUnplanTrainingRequest;
    }

    public void setHrTdAnnualTrainingNeeds(HrTdAnnualTrainingNeeds hrTdAnnualTrainingNeeds) {
        this.hrTdAnnualTrainingNeeds = hrTdAnnualTrainingNeeds;
    }

    public HrTdCourses getHrTdCourses() {
        return hrTdCourses;
    }

    public void setHrTdCourses(HrTdCourses hrTdCourses) {
        this.hrTdCourses = hrTdCourses;
    }

    public HrTdTrainerProfiles getHrTdTrainerProfiles() {
        return hrTdTrainerProfiles;
    }

    public void setHrTdTrainerProfiles(HrTdTrainerProfiles hrTdTrainerProfiles) {
        this.hrTdTrainerProfiles = hrTdTrainerProfiles;
    }

    public HrEvaluationSessions getHrEvaluationSessions() {
        return hrEvaluationSessions;
    }

    public void setHrEvaluationSessions(HrEvaluationSessions hrEvaluationSessions) {
        this.hrEvaluationSessions = hrEvaluationSessions;
    }

    public DataModel<HrTdEmpTrainings> getEmpHistoryDataModel() {
        return empHistoryDataModel;
    }

    public void setEmpHistoryDataModel(DataModel<HrTdEmpTrainings> empHistoryDataModel) {
        this.empHistoryDataModel = empHistoryDataModel;
    }

    public DataModel<HrTdEmpTrainings> getEmpHistoryDataModelUpdate() {
        return empHistoryDataModelUpdate;
    }

    public void setEmpHistoryDataModelUpdate(DataModel<HrTdEmpTrainings> empHistoryDataModelUpdate) {
        this.empHistoryDataModelUpdate = empHistoryDataModelUpdate;
    }

    public DataModel<HrTdUnplanTraParticipant> getParticipantDataModel() {
        return participantDataModel;
    }

    public void setParticipantDataModel(DataModel<HrTdUnplanTraParticipant> participantDataModel) {
        this.participantDataModel = participantDataModel;
    }

    public DataModel<HrTdAnnualTraParticipants> getPlannedParticipantDataModel() {
        return plannedParticipantDataModel;
    }

    public void setPlannedParticipantDataModel(DataModel<HrTdAnnualTraParticipants> plannedParticipantDataModel) {
        this.plannedParticipantDataModel = plannedParticipantDataModel;
    }

    public HrTdUnplanTraParticipant getHrTdUnplanTraParticipant() {
        return hrTdUnplanTraParticipant;
    }

    public void setHrTdUnplanTraParticipant(HrTdUnplanTraParticipant hrTdUnplanTraParticipant) {
        this.hrTdUnplanTraParticipant = hrTdUnplanTraParticipant;
    }

    public HrTdAnnualTraParticipants getHrTdAnnualTraParticipants() {
        return hrTdAnnualTraParticipants;
    }

    public void setHrTdAnnualTraParticipants(HrTdAnnualTraParticipants hrTdAnnualTraParticipants) {
        this.hrTdAnnualTraParticipants = hrTdAnnualTraParticipants;
    }

    public String getPlannedSearch() {
        return plannedSearch;
    }

    public void setPlannedSearch(String plannedSearch) {
        this.plannedSearch = plannedSearch;
    }

    public String getUnplannedSearch() {
        return unplannedSearch;
    }

    public void setUnplannedSearch(String unplannedSearch) {
        this.unplannedSearch = unplannedSearch;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isRenderPnlCreateGatePass() {
        return renderPnlCreateGatePass;
    }

    public void setRenderPnlCreateGatePass(boolean renderPnlCreateGatePass) {
        this.renderPnlCreateGatePass = renderPnlCreateGatePass;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public String getAddorUpdate() {
        return addorUpdate;
    }

    public void setAddorUpdate(String addorUpdate) {
        this.addorUpdate = addorUpdate;
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

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
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

    public List<SelectItem> getBudgetYears() {
        return budgetYears;
    }

    public void setBudgetYears(List<SelectItem> budgetYears) {
        this.budgetYears = budgetYears;
    }

    public List<HrTdUnplanTrainingRequest> getUnplannedBudgetYearList() {
        return unplannedBudgetYearList;
    }

    public void setUnplannedBudgetYearList(List<HrTdUnplanTrainingRequest> unplannedBudgetYearList) {
        this.unplannedBudgetYearList = unplannedBudgetYearList;
    }

    public List<HrTdAnnualNeedRequests> getPlannedBudgetYearList() {
        return plannedBudgetYearList;
    }

    public void setPlannedBudgetYearList(List<HrTdAnnualNeedRequests> plannedBudgetYearList) {
        this.plannedBudgetYearList = plannedBudgetYearList;
    }

    public Boolean getPlannnedCourse() {
        return plannnedCourse;
    }

    public void setPlannnedCourse(Boolean plannnedCourse) {
        this.plannnedCourse = plannnedCourse;
    }

    public Boolean getUnplannedCourse() {
        return unplannedCourse;
    }

    public void setUnplannedCourse(Boolean unplannedCourse) {
        this.unplannedCourse = unplannedCourse;
    }

    public String getUpdateTrainner() {
        return updateTrainner;
    }

    public void setUpdateTrainner(String updateTrainner) {
        this.updateTrainner = updateTrainner;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Search">
    String select = "";

    public void createNewInfo() {
        disableBtnCreate = false;
        switch (createOrSearchBundle) {
            case "New":
                if (updateStatus == 0) {
                    renderPnlCreateGatePass = true;
                    renderPnlManPage = false;
                    updateTrainner = "true";
                    unplannedCourse = false;
                    plannnedCourse = false;
                    btnNewRender = false;
                    createOrSearchBundle = "Search";
                    saveorUpdateBundle = "save";
                    setIcone("ui-icon-search");
                    break;
                } else {
                    renderPnlCreateGatePass = true;
                    renderPnlManPage = false;
                    btnNewRender = false;
                    updateTrainner = "false";
                    createOrSearchBundle = "Search";
                    saveorUpdateBundle = "save";
                    setIcone("ui-icon-search");
                    break;
                }
            case "Search":
                renderPnlCreateGatePass = false;
                btnNewRender = false;
                updateTrainner = "false";
                renderPnlManPage = true;
                createOrSearchBundle = "New";
                headerTitle = "Search...";
                setIcone("ui-icon-plus");
                break;
        }
    }

    public void newButtonAction() {
        clearPlanned();
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = false;
    }
    Integer empId;

    public void populatePlanned(SelectEvent event) {
        hrTdAnnualTraParticipants = (HrTdAnnualTraParticipants) event.getObject();
        hrTdAnnualTrainingNeeds = hrTdAnnualTraParticipants.getAnnTraNeedId();
        hrEmployees = hrTdAnnualTraParticipants.getEmpId();
        empId = hrEmployees.getId();
        participantList = hrTdEmpTrainingsBeanLocal.findByEmpId(hrEmployees);
        recreateDataModel();
        hrJobTypes = hrTdAnnualTraParticipants.getEmpId().getJobId();
        hrLuTrainingCategory = hrTdAnnualTrainingNeeds.getTrainingId().getCategoryId();
        hrTdCourses = hrTdAnnualTrainingNeeds.getTrainingId();
        hrTdAnnualTrainingNeeds.setTrainingId(hrTdCourses);
        hrTdAnnualTrainingNeeds.setTrainingId(hrTdAnnualTrainingNeeds.getTrainingId());
        hrTdCourses.setCourseName(hrTdCourses.getCourseName());
        trainningCourseList = hrLuTrainingCategoryBeanLocal.findbyID(hrLuTrainingCategory);
        renderPnlCreateGatePass = true;
        btnNewRender = true;
        plannnedCourse = true;
        unplannedCourse = false;
        renderPnlManPage = false;
        updateTrainner = "false";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        updateStatus = 1;
    }

    public void populateUplanned(SelectEvent event) {
        hrTdUnplanTraParticipant = (HrTdUnplanTraParticipant) event.getObject();
        hrTdUnplanTrainingRequest = hrTdUnplanTraParticipant.getUnpTraReqId();
        hrEmployees = hrTdUnplanTraParticipant.getEmpId();
        participantList = hrTdEmpTrainingsBeanLocal.findByEmpId(hrEmployees);
        recreateDataModel();
        hrJobTypes = hrTdUnplanTraParticipant.getEmpId().getJobId();
        hrLuTrainingCategory = hrTdUnplanTrainingRequest.getTrainingId().getCategoryId();
        hrTdCourses.setCourseName(hrTdCourses.getCourseName());
        hrTdUnplanTrainingRequest.setTrainingId(hrTdCourses);
        hrTdUnplanTrainingRequest.setTrainingId(hrTdUnplanTrainingRequest.getTrainingId());
        hrTdCourses = hrTdUnplanTrainingRequest.getTrainingId();
        trainningCourseList = hrLuTrainingCategoryBeanLocal.findbyID(hrLuTrainingCategory);
        renderPnlCreateGatePass = true;
        btnNewRender = true;
        renderPnlManPage = false;
        plannnedCourse = false;
        unplannedCourse = true;
        updateTrainner = "false";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        updateStatus = 1;
    }
    String slectedC = "Select One";

    public void handleTraining(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            slectedC = event.getNewValue().toString();
            if (slectedC.equalsIgnoreCase("1")) {
                plannedSearch = "true";
                plannnedCourse = true;
                unplannedSearch = "false";
                unplannedCourse = false;
                updateTrainner = "false";
            } else {
                unplannedSearch = "true";
                unplannedCourse = true;
                plannedSearch = "false";
                plannnedCourse = false;
                updateTrainner = "false";
            }
        }
    }

    public List<SelectItem> readBudgetYears() {
        List<SelectItem> budgetYear = new ArrayList<>();
        String[] toDay = StringDateManipulation.toDayInEc().split("-");
        int year = Integer.valueOf(toDay[0]) + 1;
        while (year > 2000) {
            budgetYear.add(new SelectItem(String.valueOf(year), String.valueOf(year - 1) + "/" + String.valueOf(year)));
            year--;
        }
        return budgetYear;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Main">
    public void saveEmployeeTrainingHistory() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveEmployeeTrainingHistory", dataset)) {
                if (hrTdEmpTrainings.getType().equals(1)) {
                    try {
                        if (updateStatus == 1) {
                            hrTdEmpTrainings.setEmpId(hrTdAnnualTraParticipants.getEmpId());
                            hrTdEmpTrainings.setTrainingId(hrTdAnnualTrainingNeeds.getTrainingId());
                            hrTdEmpTrainings.setInsId(hrTdAnnualTrainingNeeds.getInstitutionId());
                            hrTdEmpTrainings.setStartDate(hrTdAnnualTrainingNeeds.getTentativeStartDate());
                            hrTdEmpTrainings.setEndDate(hrTdAnnualTrainingNeeds.getTentativeEndDate());
                            hrTdEmpTrainings.setSponsoredBy(hrTdAnnualTrainingNeeds.getSponsoredBy());
                            hrTdEmpTrainings.setRemark(hrTdAnnualTrainingNeeds.getRemark());
                            hrTdEmpTrainings.setType(hrTdEmpTrainings.getType());
                            hrTdAnnualTraParticipants.setStatus("1");
                            hrTdAnnualTraParticipantsBeanLocal.edit(hrTdAnnualTraParticipants);
                            hrTdEmpTrainingsBeanLocal.create(hrTdEmpTrainings);
                            participantList = hrTdEmpTrainingsBeanLocal.findByEmpId(hrEmployees);
                            recreateDataModel();
                            JsfUtil.addSuccessMessage("Saved Successfully");
                            clearPlanned();
                        } else if (updateStatus == 2) {
                            hrTdEmpTrainingsBeanLocal.edit(hrTdEmpTrainings);
                            updateStatus = 2;
                            recreateDataModel();
                            JsfUtil.addSuccessMessage("Successfully Updated");
                            hrEmpTrainings = new HrEmpTrainings();
                        }
                    } catch (Exception ex) {
                        JsfUtil.addFatalMessage("Error occured while saving");
                    }
//        
                } else if (hrTdEmpTrainings.getType().equals(2)) {
                    try {
                        if (updateStatus == 1) {
                            hrTdUnplanTraParticipant.setEmpId(hrTdUnplanTraParticipant.getEmpId());
                            hrTdEmpTrainings.setEmpId(hrTdUnplanTraParticipant.getEmpId());
                            hrTdEmpTrainings.setTrainingId(hrTdUnplanTrainingRequest.getTrainingId());
                            hrTdEmpTrainings.setInsId(hrTdUnplanTrainingRequest.getInstitutionId());
                            hrTdEmpTrainings.setStartDate(hrTdUnplanTrainingRequest.getStartDate());
                            hrTdEmpTrainings.setEndDate(hrTdUnplanTrainingRequest.getEndDate());
                            hrTdEmpTrainings.setSponsoredBy(hrTdUnplanTrainingRequest.getSponsoredBy());
                            hrTdEmpTrainings.setRemark(hrTdUnplanTrainingRequest.getRemark());
                            hrTdEmpTrainings.setType(hrTdEmpTrainings.getType());
                            hrTdUnplanTraParticipant.setStatus(1);
                            HrTdUnplanTraParticipantBeanLocal.edit(hrTdUnplanTraParticipant);
                            hrTdEmpTrainingsBeanLocal.create(hrTdEmpTrainings);
                            participantList = hrTdEmpTrainingsBeanLocal.findByEmpId(hrEmployees);
                            recreateDataModel();
                            JsfUtil.addSuccessMessage("Saved Successfully");
                            clearUnplanned();
                        } else if (updateStatus == 2) {
                            hrTdEmpTrainingsBeanLocal.edit(hrTdEmpTrainings);
                            updateStatus = 2;
                            recreateDataModel();
                            JsfUtil.addSuccessMessage("Successfully Updated");
                            hrEmpTrainings = new HrEmpTrainings();
                        }
                    } catch (Exception ex) {
                        JsfUtil.addFatalMessage("Error occured while saving");
                    }
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveEmployeeTrainingHistory");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    String selected = "select desition";
    List<HrEmpTrainings> hrEmpTrainingsList;
    List<HrTdEmpTrainings> tempList = new ArrayList<>();

    public void updateEmployeeTrainingHistory() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "updateEmployeeTrainingHistory", dataset)) {
                try {
                    if (editStatus == 1) {
                        for (int i = 0; i < tempList.size(); i++) {
                            if (tempList.get(i).getStatus() == 2) {
                                hrEmpTrainings = new HrEmpTrainings();
                                hrEmpTrainings.setSponsoredBy(tempList.get(i).getSponsoredBy());
                                hrEmpTrainings.setStartDate(tempList.get(i).getStartDate());
                                hrEmpTrainings.setEndDate(tempList.get(i).getEndDate());
                                hrEmpTrainings.setEmpId(tempList.get(i).getEmpId());
                                hrEmpTrainings.setInstitution(tempList.get(i).getInsId().getInstitutionName());
                                hrEmpTrainings.setTrainingTitle(tempList.get(i).getTrainingId().getCourseName());
                                hrEmpTrainingsBeanLocal.creat(hrEmpTrainings);
                                hrTdEmpTrainingsBeanLocal.edit(tempList.get(i));
                                hrTdEmpTrainings = new HrTdEmpTrainings();
                            } else {
                                hrTdEmpTrainingsBeanLocal.edit(tempList.get(i));
                                hrTdEmpTrainings = new HrTdEmpTrainings();
                            }
                        }
                    }

                    participantListOld = hrTdEmpTrainingsBeanLocal.findByEmpIds(hrEmployees);
                    recreateDataModelUpdate();
                    JsfUtil.addSuccessMessage("Uppdated Successfully");
                    addorUpdate = "Add";
                } catch (Exception ee) {
                    JsfUtil.addFatalMessage("Error occured while Uppdating");
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "updateEmployeeTrainingHistory");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resetEmployeeTrainingHistory() {
        clearPlanned();
        clearUnplanned();
    }

    public void recreateDataModel() {
        empHistoryDataModel = null;
        for (int i = 0; i < participantList.size(); i++) {
            if (participantList.get(i).getStatus().equals(1) && participantList.get(i).getType().equals(1)) {
                participantList.get(i).setChangedStstus("Attending");
                participantList.get(i).setChangedType("Planned");
            } else if (participantList.get(i).getStatus().equals(1) && participantList.get(i).getType().equals(2)) {
                participantList.get(i).setChangedStstus("Attending");
                participantList.get(i).setChangedType("Unplanned");
            } else if (participantList.get(i).getStatus().equals(2) && participantList.get(i).getType().equals(1)) {
                participantList.get(i).setChangedStstus("Completed");
                participantList.get(i).setChangedType("Planned");
            } else if (participantList.get(i).getStatus().equals(2) && participantList.get(i).getType().equals(2)) {
                participantList.get(i).setChangedStstus("Completed");
                participantList.get(i).setChangedType("Unplanned");
            } else if (participantList.get(i).getStatus().equals(3) && participantList.get(i).getType().equals(1)) {
                participantList.get(i).setChangedStstus("Cancelled");
                participantList.get(i).setChangedType("Planned");
            } else if (participantList.get(i).getStatus().equals(3) && participantList.get(i).getType().equals(2)) {
                participantList.get(i).setChangedStstus("Cancelled");
                participantList.get(i).setChangedType("Unplanned");
            } else if (participantList.get(i).getStatus().equals(4) && participantList.get(i).getType().equals(1)) {
                participantList.get(i).setChangedStstus("Failed");
                participantList.get(i).setChangedType("Planned");
            } else if (participantList.get(i).getStatus().equals(4) && participantList.get(i).getType().equals(2)) {
                participantList.get(i).setChangedStstus("Failed");
                participantList.get(i).setChangedType("Unplanned");
            } else if (participantList.get(i).getStatus().equals(5) && participantList.get(i).getType().equals(1)) {
                participantList.get(i).setChangedStstus("Not Show");
                participantList.get(i).setChangedType("Planned");
            } else if (participantList.get(i).getStatus().equals(5) && participantList.get(i).getType().equals(2)) {
                participantList.get(i).setChangedStstus("Not Show");
                participantList.get(i).setChangedType("Unplanned");
            }
        }

        empHistoryDataModel = new ListDataModel(new ArrayList(participantList));
    }

    public void recreateDataModelUpdate() {
        empHistoryDataModelUpdate = null;
        for (int i = 0; i < participantListOld.size(); i++) {
            if (participantListOld.get(i).getStatus().equals(1) && participantListOld.get(i).getType().equals(1)) {
                participantListOld.get(i).setChangedStstus("Attending");
                participantListOld.get(i).setChangedType("Planned");
            } else if (participantListOld.get(i).getStatus().equals(1) && participantListOld.get(i).getType().equals(2)) {
                participantListOld.get(i).setChangedStstus("Attending");
                participantListOld.get(i).setChangedType("Unplanned");
            } else if (participantListOld.get(i).getStatus().equals(2) && participantListOld.get(i).getType().equals(1)) {
                participantListOld.get(i).setChangedStstus("Completed");
                participantListOld.get(i).setChangedType("Planned");
            } else if (participantListOld.get(i).getStatus().equals(2) && participantListOld.get(i).getType().equals(2)) {
                participantListOld.get(i).setChangedStstus("Completed");
                participantListOld.get(i).setChangedType("Unplanned");
            } else if (participantListOld.get(i).getStatus().equals(3) && participantListOld.get(i).getType().equals(1)) {
                participantListOld.get(i).setChangedStstus("Cancelled");
                participantListOld.get(i).setChangedType("Planned");
            } else if (participantListOld.get(i).getStatus().equals(3) && participantListOld.get(i).getType().equals(2)) {
                participantListOld.get(i).setChangedStstus("Cancelled");
                participantListOld.get(i).setChangedType("Unplanned");
            } else if (participantListOld.get(i).getStatus().equals(4) && participantListOld.get(i).getType().equals(1)) {
                participantListOld.get(i).setChangedStstus("Failed");
                participantListOld.get(i).setChangedType("Planned");
            } else if (participantListOld.get(i).getStatus().equals(4) && participantListOld.get(i).getType().equals(2)) {
                participantListOld.get(i).setChangedStstus("Failed");
                participantListOld.get(i).setChangedType("Unplanned");
            } else if (participantListOld.get(i).getStatus().equals(5) && participantListOld.get(i).getType().equals(1)) {
                participantListOld.get(i).setChangedStstus("Not Show");
                participantListOld.get(i).setChangedType("Planned");
            } else if (participantListOld.get(i).getStatus().equals(5) && participantListOld.get(i).getType().equals(2)) {
                participantListOld.get(i).setChangedStstus("Not Show");
                participantListOld.get(i).setChangedType("Unplanned");
            }
        }
        empHistoryDataModelUpdate = new ListDataModel(new ArrayList(participantListOld));
    }
    int editStatus = 0;

    public void addToDataTable() {
        if (editStatus == 1) {
            tempList.add(hrTdEmpTrainings);
            clearPopup();
            recreateDataModelUpdate();
        } else {
            participantList.add(hrTdEmpTrainings);
            clearPopup();
            recreateDataModelUpdate();
        }
    }

    public void clearPopup() {
        hrTdEmpTrainings = new HrTdEmpTrainings();
//        hrEmpTrainings = new HrEmpTrainings();
    }
    private HrTdEmpTrainings selectedEmpTrainings;

    public HrTdEmpTrainings getSelectedEmpTrainings() {
        return selectedEmpTrainings;
    }

    public void setSelectedEmpTrainings(HrTdEmpTrainings selectedEmpTrainings) {
        this.selectedEmpTrainings = selectedEmpTrainings;
    }

    public void edit(SelectEvent event) {
        hrTdEmpTrainings = (HrTdEmpTrainings) event.getObject();
        hrTdCourses = hrTdEmpTrainings.getTrainingId();
        hrTdTrainerProfiles = hrTdEmpTrainings.getInsId();
        editStatus = 1;
        addorUpdate = "Modify";
    }

    public void editStatus(SelectEvent event) {
        hrTdEmpTrainings = (HrTdEmpTrainings) event.getObject();
        updateStatus = 2;
        saveorUpdateBundle = "Modify";
    }

    public void clearPlanned() {
        hrTdAnnualTrainingNeeds = new HrTdAnnualTrainingNeeds();
        hrLuTrainingCategory = new HrLuTrainingCategory();
        hrTdAnnualTraParticipants = new HrTdAnnualTraParticipants();
        hrEmployees = new HrEmployees();
        hrJobTypes = new HrJobTypes();
        hrTdEmpTrainings = new HrTdEmpTrainings();
    }

    public void clearUnplanned() {
        hrTdUnplanTrainingRequest = new HrTdUnplanTrainingRequest();
        hrLuTrainingCategory = new HrLuTrainingCategory();
        hrTdUnplanTraParticipant = new HrTdUnplanTraParticipant();
        hrEmployees = new HrEmployees();
        hrJobTypes = new HrJobTypes();
        hrTdEmpTrainings = new HrTdEmpTrainings();
    }

    public ArrayList<HrEmployees> searchEmployeeByName(String hrEmployee) {
        ArrayList<HrEmployees> employee = null;
        hrEmployees.setFirstName(hrEmployee);
        employee = hrEmployeeBeanLocal.searchEmployeeByName(hrEmployees);
        updateTrainner = "true";
        plannnedCourse = false;
        unplannedCourse = false;
        return employee;
    }

    public void getByFirstName(SelectEvent event) {
        try {
            hrEmployees = (HrEmployees) event.getObject();
            participantListOld = hrTdEmpTrainingsBeanLocal.findByEmpIds(hrEmployees);
            recreateDataModelUpdate();
            hrDepartments = hrEmployees.getDeptId();
            hrJobTypes = hrEmployees.getJobId();
            updateTrainner = "true";
            plannnedCourse = false;
            unplannedCourse = false;
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }

    public ArrayList<HrEmployees> searchEmployeeById(String empId) {
        ArrayList<HrEmployees> employee = null;
        hrEmployees.setEmpId(empId);
        employee = hrEmployeeBeanLocal.SearchByEmpId(hrEmployees);
        updateTrainner = "true";
        plannnedCourse = false;
        unplannedCourse = false;
        return employee;
    }

    public void getById(SelectEvent event) {
        try {
            hrEmployees.setEmpId(event.getObject().toString());
            hrEmployees = hrEmployeeBeanLocal.getByEmpId(hrEmployees);
            participantListOld = hrTdEmpTrainingsBeanLocal.findByEmpIds(hrEmployees);
            recreateDataModelUpdate();
            hrDepartments = hrEmployees.getDeptId();
            hrJobTypes = hrEmployees.getJobId();
            updateTrainner = "true";
            plannnedCourse = false;
            unplannedCourse = false;
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }

    private String disableEmpID = "false";
    private String disableEmpName = "true";
    String selectedEmp = "Employee ID";

    public String getDisableEmpID() {
        return disableEmpID;
    }

    public void setDisableEmpID(String disableEmpID) {
        this.disableEmpID = disableEmpID;
    }

    public String getDisableEmpName() {
        return disableEmpName;
    }

    public void setDisableEmpName(String disableEmpName) {
        this.disableEmpName = disableEmpName;
    }

    public String getSelectedEmp() {
        return selectedEmp;
    }

    public void setSelectedEmp(String selectedEmp) {
        this.selectedEmp = selectedEmp;
    }

    public void searchCriteria(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selectedEmp = event.getNewValue().toString();
            if (selectedEmp.equalsIgnoreCase("Employee ID")) {
                disableEmpID = "false";
                disableEmpName = "true";
            } else {
                disableEmpID = "true";
                disableEmpName = "false";
            }
        }
    }

    public void plannedBYVCL(ValueChangeEvent event) {
        if (event.getNewValue().toString() != null) {
            hrTdAnnualNeedRequests.setBudgetYear(hrTdAnnualNeedRequests.getBudgetYear());
            hrTdAnnualNeedRequests.setBudgetYear(Integer.parseInt(event.getNewValue().toString()));
            annCourseList = hrTdUnplanTrainingRequestBeanLocal.SearchTraningNeedByBudgetYear1(hrTdAnnualNeedRequests);

        }
    }

    public void trainingCoursePlanedVCL(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrTdCourses = (HrTdCourses) event.getNewValue();
        }
    }

    public void unplannnedBYVCL(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            Integer budgetYear = Integer.valueOf(event.getNewValue().toString());
            unplannedcourseList = hrTdUnplanTrainingRequestBeanLocal.findByBudgetYear(budgetYear);
        }
    }

    public void trainingCourseUnplanedVCL(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrTdCourses = (HrTdCourses) event.getNewValue();
        }
    }

    public void trainingCatagoryVCL(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrLuTrainingCategory.setId(Integer.parseInt(event.getNewValue().toString()));
            trainningCourseList = hrLuTrainingCategoryBeanLocal.findbyID(hrLuTrainingCategory);
        }
    }

    public void trainingCourseVCL(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrTdCourses = (HrTdCourses) event.getNewValue();
            hrTdUnplanTrainingRequest.setTrainingId(hrTdCourses);
        }
    }

    public void institutionVCL(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrTdTrainerProfiles = (HrTdTrainerProfiles) event.getNewValue();
            hrTdUnplanTrainingRequest.setInstitutionId(hrTdTrainerProfiles);
        }
    }

    BigDecimal courseIds;
    int year;

    public void vclForPlanned(ValueChangeEvent event) {
        if (event.getNewValue().toString() != null) {
            hrTdCourses = (HrTdCourses) event.getNewValue();
            courseIds = hrTdCourses.getId();
            year = hrTdAnnualNeedRequests.getBudgetYear();
            plannedTraineeList = hrTdUnplanTrainingRequestBeanLocal.searchTrainerByCourseid(courseIds, year);

        }
    }
    BigDecimal courseId;

    public void vclForUnplanned(ValueChangeEvent event) {
        if (event.getNewValue().toString() != null) {
            hrTdCourses = (HrTdCourses) event.getNewValue();
            courseId = hrTdCourses.getId();
            traineeList = hrTdUnplanTrainingRequestBeanLocal.searchTrainerByCourse(courseId);

        }
    }
    //</editor-fold>
}
