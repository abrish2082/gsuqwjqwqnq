/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.training;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.hrms.businessLogic.recruitment.HrRecruitmentRequestsBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrTdAnnualNeedRequestsBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrTdAnnualPlansBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrTdAnnualTraParticipantsBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrTdEmpTrainingsBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrTrainingCoursesBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.training.HrLuTrainingCategory;
import et.gov.eep.hrms.entity.training.HrTdAnnualNeedRequests;
import et.gov.eep.hrms.entity.training.HrTdAnnualPlanGroups;
import et.gov.eep.hrms.entity.training.HrTdAnnualPlans;
import et.gov.eep.hrms.entity.training.HrTdAnnualTraParticipants;
import et.gov.eep.hrms.entity.training.HrTdAnnualTrainingNeeds;
import et.gov.eep.hrms.entity.training.HrTdCourses;
import et.gov.eep.hrms.entity.training.HrTdTrainerProfiles;
import et.gov.eep.hrms.mapper.training.HrTdTrainerProfilesFacade;

/**
 *
 * @author Benin
 */
@Named(value = "annualTrainingPlanControlller")
@ViewScoped
public class AnnualTrainingPlanControlller implements Serializable {

    public AnnualTrainingPlanControlller() {
    }

    @Inject
    HrTdAnnualNeedRequests hrTdAnnualNeedRequests;
    @Inject
    HrTdAnnualPlans srcAnnualPlans;
    @Inject
    HrTdAnnualPlanGroups hrTdAnnualPlanGroup;
    @Inject
    HrTdAnnualTrainingNeeds hrTdAnnualTrainingNeeds;
    @Inject
    HrLuTrainingCategory hrLuTrainingCategory;
    @Inject
    HrTdCourses hrTdCourses;
    @Inject
    HrDepartments hrDepartments;
    @Inject
    HrTdTrainerProfiles hrTdTrainerProfiles;
    @Inject
    HrTdAnnualTraParticipants hrTdAnnualTraParticipants;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrTdAnnualPlans hrTdAnnualPlans;
    @Inject
    SessionBean sessionBeanLocal;

    @EJB
    HrTdAnnualPlansBeanLocal annualPlanBeanLocal;
    @EJB
    HrTdAnnualNeedRequestsBeanLocal annualNeedRequestsBeanLocal;
    @EJB
    HrTrainingCoursesBeanLocal hrTrainingCoursesBeanLocal;
    @EJB
    HrDepartmentsBeanLocal hrDepartmentsFacade;
    @EJB
    HrRecruitmentRequestsBeanLocal hrRecruitmentRequestsBeanLocal;
    @EJB
    HrTdTrainerProfilesFacade hrTdTrainerProfilesFacade;
    @EJB
    HrEmployeeBeanLocal hrEmployeeBeanLocal;
    @EJB
    HrTdAnnualTraParticipantsBeanLocal hrTdAnnualTraParticipantsBeanLocal;
    @EJB
    HrTdAnnualNeedRequestsBeanLocal anulNeedReqstsBnLocal;
    @EJB
    HrTdEmpTrainingsBeanLocal empTrainingBeanLocal;
    @EJB
    HrTdAnnualNeedRequestsBeanLocal hrTdAnulNeedReqstsBnLocal;

    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-document";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = true;
    private boolean renderPnlManPage = false;
    private String renderpnlContrat = "false";
    private boolean renderParticipant = false;
    private int updateStatus = 0;
    private String SaveOrUpdateButton = "Save";
    String preparedDate;
    private String disableBtnAdd = "true";
    Set<String> checkDuplication = new HashSet<>();
    private String addorUpdate = "Add";

    List<HrTdTrainerProfiles> hrTdTrainerProfilesList = new ArrayList<>();
    List<HrTdCourses> hrTdCoursesList = new ArrayList<>();
    List<HrTdAnnualNeedRequests> budgetYearList;
    List<HrTdAnnualTrainingNeeds> annualCourseList;
    List<HrTdAnnualTrainingNeeds> annualTrainingList;
    DataModel<HrTdAnnualPlanGroups> annualPlansGroupDataModel = new ListDataModel<>();
    DataModel<HrTdAnnualNeedRequests> annualNeedRequestsDataModel = new ListDataModel<>();
    DataModel<HrTdAnnualTrainingNeeds> annualTrainingNeedDataModel = new ListDataModel<>();
    private DataModel<HrTdAnnualPlans> annualTrainingPlanDataModel;
    DataModel<HrTdAnnualPlans> annualPlanDataModel;
    DataModel<HrTdAnnualTraParticipants> hrTdAnnulTraParticipantM = new ListDataModel<>();
    DataModel<HrTdAnnualTrainingNeeds> hrTdAnnualTrainingNeedsDataModel = new ListDataModel<>();

    @PostConstruct
    public void init() {
        budgetYearList = annualPlanBeanLocal.findBudgetYear();
        setBudgetYears(readBudgetYears());
        hrTdTrainerProfilesList = hrTdAnulNeedReqstsBnLocal.hrTdTrainerProfilesList();
        setPreparedDate(dateFormat(StringDateManipulation.toDayInEc()));
    }

    //  <editor-fold defaultstate="collapsed" desc="getter Setter">   
    public DataModel<HrTdAnnualTrainingNeeds> getHrTdAnnualTrainingNeedsDataModel() {
        return hrTdAnnualTrainingNeedsDataModel;
    }

    public void setHrTdAnnualTrainingNeedsDataModel(DataModel<HrTdAnnualTrainingNeeds> hrTdAnnualTrainingNeedsDataModel) {
        this.hrTdAnnualTrainingNeedsDataModel = hrTdAnnualTrainingNeedsDataModel;
    }

    public DataModel<HrTdAnnualTraParticipants> getHrTdAnnulTraParticipantM() {
        return hrTdAnnulTraParticipantM;
    }

    public void setHrTdAnnulTraParticipantM(DataModel<HrTdAnnualTraParticipants> hrTdAnnulTraParticipantM) {
        this.hrTdAnnulTraParticipantM = hrTdAnnulTraParticipantM;
    }

    public HrTdAnnualNeedRequests getHrTdAnnualNeedRequests() {
        if (hrTdAnnualNeedRequests == null) {
            hrTdAnnualNeedRequests = new HrTdAnnualNeedRequests();
        }
        return hrTdAnnualNeedRequests;
    }

    public void setHrTdAnnualNeedRequests(HrTdAnnualNeedRequests hrTdAnnualNeedRequests) {
        this.hrTdAnnualNeedRequests = hrTdAnnualNeedRequests;
    }

    public HrTdAnnualPlans getSrcAnnualPlans() {
        return srcAnnualPlans;
    }

    public void setSrcAnnualPlans(HrTdAnnualPlans srcAnnualPlans) {
        this.srcAnnualPlans = srcAnnualPlans;
    }

    public HrTdAnnualPlanGroups getHrTdAnnualPlanGroup() {
        if (hrTdAnnualPlanGroup == null) {
            hrTdAnnualPlanGroup = new HrTdAnnualPlanGroups();
        }
        return hrTdAnnualPlanGroup;
    }

    public void setHrTdAnnualPlanGroup(HrTdAnnualPlanGroups hrTdAnnualPlanGroup) {
        this.hrTdAnnualPlanGroup = hrTdAnnualPlanGroup;
    }

    public HrTdAnnualTrainingNeeds getHrTdAnnualTrainingNeeds() {
        if (hrTdAnnualTrainingNeeds == null) {
            hrTdAnnualTrainingNeeds = new HrTdAnnualTrainingNeeds();
        }
        return hrTdAnnualTrainingNeeds;
    }

    public void setHrTdAnnualTrainingNeeds(HrTdAnnualTrainingNeeds hrTdAnnualTrainingNeeds) {
        this.hrTdAnnualTrainingNeeds = hrTdAnnualTrainingNeeds;
    }

    public HrLuTrainingCategory getHrLuTrainingCategory() {
        if (hrLuTrainingCategory == null) {
            hrLuTrainingCategory = new HrLuTrainingCategory();
        }
        return hrLuTrainingCategory;
    }

    public void setHrLuTrainingCategory(HrLuTrainingCategory hrLuTrainingCategory) {
        this.hrLuTrainingCategory = hrLuTrainingCategory;
    }

    public HrTdAnnualPlans getHrTdAnnualPlans() {
        if (hrTdAnnualPlans == null) {
            hrTdAnnualPlans = new HrTdAnnualPlans();
        }
        return hrTdAnnualPlans;
    }

    public void setHrTdAnnualPlans(HrTdAnnualPlans hrTdAnnualPlans) {
        this.hrTdAnnualPlans = hrTdAnnualPlans;
    }

    public HrTdCourses getHrTdCourses() {
        if (hrTdCourses == null) {
            hrTdCourses = new HrTdCourses();
        }
        return hrTdCourses;
    }

    public void setHrTdCourses(HrTdCourses hrTdCourses) {
        this.hrTdCourses = hrTdCourses;
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

    public HrTdTrainerProfiles getHrTdTrainerProfiles() {
        if (hrTdTrainerProfiles == null) {
            hrTdTrainerProfiles = new HrTdTrainerProfiles();
        }
        return hrTdTrainerProfiles;
    }

    public void setHrTdTrainerProfiles(HrTdTrainerProfiles hrTdTrainerProfiles) {
        this.hrTdTrainerProfiles = hrTdTrainerProfiles;
    }

    public HrTdAnnualTraParticipants getHrTdAnnualTraParticipants() {
        if (hrTdAnnualTraParticipants == null) {
            hrTdAnnualTraParticipants = new HrTdAnnualTraParticipants();
        }
        return hrTdAnnualTraParticipants;
    }

    public void setHrTdAnnualTraParticipants(HrTdAnnualTraParticipants hrTdAnnualTraParticipants) {
        this.hrTdAnnualTraParticipants = hrTdAnnualTraParticipants;
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

    public boolean isRenderParticipant() {
        return renderParticipant;
    }

    public void setRenderParticipant(boolean renderParticipant) {
        this.renderParticipant = renderParticipant;
    }

    public String getRenderpnlContrat() {
        return renderpnlContrat;
    }

    public void setRenderpnlContrat(String renderpnlContrat) {
        this.renderpnlContrat = renderpnlContrat;
    }

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    public String getAddorUpdate() {
        return addorUpdate;
    }

    public void setAddorUpdate(String addorUpdate) {
        this.addorUpdate = addorUpdate;
    }

    public List<HrTdCourses> getHrTdCoursesList() {
        return hrTdCoursesList;
    }

    public String getPreparedDate() {
        return preparedDate;
    }

    public void setPreparedDate(String preparedDate) {
        this.preparedDate = preparedDate;
    }

    public String getDisableBtnAdd() {
        return disableBtnAdd;
    }

    public void setDisableBtnAdd(String disableBtnAdd) {
        this.disableBtnAdd = disableBtnAdd;
    }

    public void setHrTdCoursesList(List<HrTdCourses> hrTdCoursesList) {
        this.hrTdCoursesList = hrTdCoursesList;
    }

    public List<HrTdAnnualNeedRequests> getBudgetYearList() {
        return budgetYearList;
    }

    public void setBudgetYearList(List<HrTdAnnualNeedRequests> budgetYearList) {
        this.budgetYearList = budgetYearList;
    }

    public List<HrTdAnnualTrainingNeeds> getAnnualTrainingList() {
        return annualTrainingList;
    }

    public void setAnnualTrainingList(List<HrTdAnnualTrainingNeeds> annualTrainingList) {
        this.annualTrainingList = annualTrainingList;
    }

    public List<HrTdTrainerProfiles> getHrTdTrainerProfilesList() {
        return hrTdTrainerProfilesList;
    }

    public void setHrTdTrainerProfilesList(List<HrTdTrainerProfiles> hrTdTrainerProfilesList) {
        this.hrTdTrainerProfilesList = hrTdTrainerProfilesList;
    }

    public DataModel<HrTdAnnualPlanGroups> getAnnualPlansGroupDataModel() {
        if (annualPlansGroupDataModel == null) {
            annualPlansGroupDataModel = new ListDataModel<>();
        }
        return annualPlansGroupDataModel;
    }

    public void setAnnualPlansGroupDataModel(DataModel<HrTdAnnualPlanGroups> annualPlansGroupDataModel) {
        this.annualPlansGroupDataModel = annualPlansGroupDataModel;
    }

    public DataModel<HrTdAnnualNeedRequests> getAnnualNeedRequestsDataModel() {
        return annualNeedRequestsDataModel;
    }

    public void setAnnualNeedRequestsDataModel(DataModel<HrTdAnnualNeedRequests> annualNeedRequestsDataModel) {
        this.annualNeedRequestsDataModel = annualNeedRequestsDataModel;
    }

    public DataModel<HrTdAnnualTrainingNeeds> getAnnualTrainingNeedDataModel() {
        if (annualTrainingNeedDataModel == null) {
            annualTrainingNeedDataModel = new ArrayDataModel<>();
        }
        return annualTrainingNeedDataModel;
    }

    public void setAnnualTrainingNeedDataModel(DataModel<HrTdAnnualTrainingNeeds> annualTrainingNeedDataModel) {
        this.annualTrainingNeedDataModel = annualTrainingNeedDataModel;
    }

    public DataModel<HrTdAnnualPlans> getAnnualPlanDataModel() {
        if (annualPlanDataModel == null) {
            annualPlanDataModel = new ArrayDataModel<>();
        }
        return annualPlanDataModel;
    }

    public void setAnnualPlanDataModel(DataModel<HrTdAnnualPlans> annualPlanDataModel) {
        this.annualPlanDataModel = annualPlanDataModel;
    }

    public List<HrTdAnnualPlans> getTrainingNeedSummaryList() {
        if (trainingNeedSummaryList == null) {
            trainingNeedSummaryList = new ArrayList<>();
        }
        return trainingNeedSummaryList;
    }

    public void setTrainingNeedSummaryList(List<HrTdAnnualPlans> trainingNeedSummaryList) {
        this.trainingNeedSummaryList = trainingNeedSummaryList;
    }

    public DataModel<HrTdAnnualPlans> getAnnualTrainingPlanDataModel() {
        if (annualTrainingPlanDataModel == null) {
            annualTrainingPlanDataModel = new ListDataModel<>();
        }
        return annualTrainingPlanDataModel;
    }

    public void setAnnualTrainingPlanDataModel(DataModel<HrTdAnnualPlans> annualTrainingPlanDataModel) {
        this.annualTrainingPlanDataModel = annualTrainingPlanDataModel;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="budgetYears"> 
    private List<SelectItem> budgetYears;

    public List<SelectItem> getBudgetYears() {
        return budgetYears;
    }

    public void setBudgetYears(List<SelectItem> budgetYears) {
        this.budgetYears = budgetYears;
    }

    public List<SelectItem> readBudgetYears() {
        List<SelectItem> budgetYear = new ArrayList<>();
        String[] toDay = StringDateManipulation.toDayInEc().split("-");
        int year = Integer.valueOf(toDay[0]) + 1;
        while (year >= 2000) {
            budgetYear.add(new SelectItem(String.valueOf(year), String.valueOf(year)));
            year--;
        }
        return budgetYear;
    }
    //</editor-fold>

    public void newPage() {
        renderPnlCreateGatePass = false;
        renderPnlManPage = true;
        hrTdAnnualNeedRequests = null;
        hrDepartments = null;
        hrTdAnnualTrainingNeeds = null;
        updateStatus = 0;
        SaveOrUpdateButton = "Save";
    }

    public void searchPage() {
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
    }

    public void selectTrainNeed(SelectEvent event) {
        hrTdAnnualPlans = (HrTdAnnualPlans) event.getObject();
        hrTdAnnualTraParticipants.setAnnTraNeedId(hrTdAnnualTrainingNeeds);
        hrTdTrainerProfiles = hrTdAnnualTrainingNeeds.getInstitutionId();
        hrTdCourses.setId(hrTdAnnualTrainingNeeds.getTrainingId().getId());
        hrLuTrainingCategory = hrTdCourses.getCategoryId();
//        hrTdAnnualTraParticipantsDataModel = new ListDataModel(new ArrayList(hrTdAnnualTraParticipantsBeanLocal.SrchAnnTraNeedIdToBeApproved(hrTdAnnualTraParticipants)));
        SaveOrUpdateButton = "Update";
    }

    private int year = 0;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    List<HrTdCourses> approvedAnnTraNeeds = new ArrayList<>();
    List<HrTdAnnualPlans> hrTdAnnualPlansTraNeeds = new ArrayList<>();

    public List<HrTdCourses> getApprovedAnnTraNeeds() {
        return approvedAnnTraNeeds;
    }

    public void setApprovedAnnTraNeeds(List<HrTdCourses> aatn) {
        this.approvedAnnTraNeeds = aatn;
    }

    public List<HrTdAnnualPlans> getHrTdAnnualPlansTraNeeds() {
        return hrTdAnnualPlansTraNeeds;
    }

    public void setHrTdAnnualPlansTraNeeds(List<HrTdAnnualPlans> hrTdAnnualPlansTraNeeds) {
        this.hrTdAnnualPlansTraNeeds = hrTdAnnualPlansTraNeeds;
    }

    public void ddnYear_processValueChange(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            year = Integer.valueOf(vce.getNewValue().toString());
            List<Object[]> crsList = annualPlanBeanLocal.readApprovedTraNeeds(year);
            HrTdCourses crs;
            for (Object[] res : crsList) {
                crs = new HrTdCourses();
                BigDecimal bd = new BigDecimal(res[0].toString());
                crs.setId(bd);
                crs.setCourseName(res[1].toString());
                approvedAnnTraNeeds.add(crs);
            }
        }
    }

    public void solbAppAnnTraNeeds_processValueChange(SelectEvent event) {
        if (event.getObject() != null) {
            HrTdCourses tra = (HrTdCourses) event.getObject();
            getApprovedAnnTraNeedDetail(Integer.parseInt(tra.getId().toString()));
            renderPnlCreateGatePass = false;
            renderPnlManPage = true;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        }
    }

    private void getApprovedAnnTraNeedDetail(int traId) {
        hrTdAnnualPlansTraNeeds = null;
        if (year > 0 && traId > 0) {
            List<Object[]> needList = annualPlanBeanLocal.readApprovedAnnTraNeedDetail(year, traId);
            HrTdAnnualPlans apln;
            for (Object[] res : needList) {
                apln = new HrTdAnnualPlans();
                BigDecimal bd = new BigDecimal(res[0].toString());
                HrTdCourses crs = new HrTdCourses();
                crs.setId(bd);
                apln.setTrainingId(crs);
                apln.setTotalParticipant(Integer.parseInt(res[1].toString()));
                apln.setNumberOfRerquest(Integer.parseInt(res[2].toString()));
                apln.setAnnTreNeedId(res[2].toString());
                hrTdAnnualPlansTraNeeds.add(apln);
            }
        }
    }

    public void getCourse(ValueChangeEvent event) {
        hrTdAnnualTrainingNeeds.setTrainingId(hrTdCourses);
    }

    public void recreateTrainingNeed() {
        annualTrainingNeedDataModel = null;
        annualTrainingNeedDataModel = new ListDataModel(new ArrayList<>(trainingNeedSummaryList));
    }

    public void populateAnnaulTrain(SelectEvent event) {
        hrTdAnnualPlans = (HrTdAnnualPlans) event.getObject();
        renderPnlCreateGatePass = false;
        renderPnlManPage = true;
        updateStatus = 1;
        SaveOrUpdateButton = "Update";
        recreateAnnualTraningPlan();
    }

    public void recreatePlanGroup() {
        annualPlansGroupDataModel = null;
        annualPlansGroupDataModel = new ListDataModel(new ArrayList<>(hrTdAnnualPlans.getHrTdAnnualPlanGroupsList()));
    }

    public void clearPlanGroup() {
        hrTdAnnualPlanGroup = null;
        hrTdTrainerProfiles = null;
    }

    public void recreateAnnualTraningPlan() {
        annualTrainingPlanDataModel = null;
        annualTrainingPlanDataModel = new ListDataModel(new ArrayList<>(trainingNeedSummaryList));
    }

    public void addDetail() {
        hrTdAnnualPlans = annualTrainingPlanDataModel.getRowData();
        recreatePlanGroup();
        disableBtnAdd = "false";
    }

    int dateDifference;

    public void dateValidation() {
        String startMonth[] = hrTdAnnualPlanGroup.getStartDate().split("/");
        int isSartMonth = Integer.parseInt(startMonth[1]);
        String startYear[] = hrTdAnnualPlanGroup.getStartDate().split("/");
        int isStartYear = Integer.parseInt(startYear[2]);
        String startDate[] = hrTdAnnualPlanGroup.getStartDate().split("/");
        int isStartDate = Integer.parseInt(startDate[0]);
        String endDay[] = hrTdAnnualPlanGroup.getEndDate().split("/");
        int isEndDay = Integer.parseInt(endDay[0]);
        String endMonth[] = hrTdAnnualPlanGroup.getEndDate().split("/");
        int isEndMonth = Integer.parseInt(endMonth[1]);
        String endYear[] = hrTdAnnualPlanGroup.getEndDate().split("/");
        int isEndYear = Integer.parseInt(endYear[2]);
        dateDifference = ((isEndDay - isStartDate) + ((isEndMonth - isSartMonth) * 30) + ((isEndYear - isStartYear) * 365));
    }

    public void addPlanGroup() {
        dateValidation();
        if (dateDifference < 0) {
            JsfUtil.addFatalMessage("End date should be greater than Start date. Try again!");
        } else if (checkDuplication.contains(hrTdAnnualPlanGroup.getInsId().getInstitutionName())
                && checkDuplication.contains(hrTdAnnualPlanGroup.getGroupName())) {
            JsfUtil.addFatalMessage("Institution Name and Group Name can't be duplicate");
        } else if (hrTdAnnualPlanGroup.getParticipantNeeded() > hrTdAnnualPlans.getTotalParticipant()) {
            JsfUtil.addFatalMessage("No. of participant should not be greater than total participant");
        } else {
//            hrTdAnnualPlans.getHrTdAnnualPlanGroupsList().add(hrTdAnnualPlanGroup);
            hrTdAnnualPlans.addPlanGroup(hrTdAnnualPlanGroup);
            checkDuplication.add(hrTdAnnualPlanGroup.getInsId().getInstitutionName());
            checkDuplication.add(hrTdAnnualPlanGroup.getGroupName());
            recreatePlanGroup();
            clearPlanGroup();
        }
    }

    public void institution_vlc(ValueChangeEvent event) {
        BigDecimal Ins = new BigDecimal(event.getNewValue().toString());
        hrTdTrainerProfiles.setId(Ins);
        hrTdTrainerProfiles = hrTdTrainerProfilesFacade.findInstituetId(hrTdTrainerProfiles);
        hrTdAnnualPlanGroup.setInsId(hrTdTrainerProfiles);
    }

    ArrayList<HrTdAnnualTrainingNeeds> trainingNeedList;
    private List<HrTdAnnualPlans> trainingNeedSummaryList;
    String[] targetArray;
    int newPlanId;

    public ArrayList<HrTdAnnualTrainingNeeds> getTrainingNeedList() {
        if (trainingNeedList == null) {
            trainingNeedList = new ArrayList<>();
        }
        return trainingNeedList;
    }

    public void setTrainingNeedList(ArrayList<HrTdAnnualTrainingNeeds> trainingNeedList) {
        this.trainingNeedList = trainingNeedList;
    }

    public void budgetYear_vlc(ValueChangeEvent event) {
        if (event.getNewValue().toString() != null) {
            HrTdAnnualPlans lastId = new HrTdAnnualPlans();
            lastId = annualPlanBeanLocal.getMaximumId();
            if (lastId != null) {
                newPlanId = lastId.getId();
            } else {
                newPlanId = 0;
            }
            hrTdAnnualPlans.setYear(Integer.parseInt(event.getNewValue().toString()));
            trainingNeedSummaryList = annualPlanBeanLocal.findPlanByYear(hrTdAnnualPlans);
            if (trainingNeedSummaryList != null && trainingNeedSummaryList.size() > 0) {
                updateStatus = 1;
                SaveOrUpdateButton = "Update";
                annualTrainingPlanDataModel = null;
                annualTrainingPlanDataModel = new ListDataModel(new ArrayList<>(trainingNeedSummaryList));
            } else {
                hrTdAnnualNeedRequests.setBudgetYear(Integer.parseInt(event.getNewValue().toString()));
                annualCourseList = annualPlanBeanLocal.searchTraningCourseByYear(hrTdAnnualNeedRequests);
                List<String> temp = null;
                temp = new ArrayList<>();
                String department = "", courceName = "";
                int totalPartc = 0;
                for (int i = 0; i < annualCourseList.size(); i++) {
                    temp.add(annualCourseList.get(i).getTrainingId().getCourseName());
                }
                Set uniqueValues = new HashSet(temp);
                targetArray = (String[]) uniqueValues.toArray(new String[uniqueValues.size()]);
                trainingNeedList = new ArrayList<>();
                for (int z = 0; z < targetArray.length; z++) {
                    HrTdAnnualPlans plan = new HrTdAnnualPlans();
                    String comma = " ";
                    String courseList = targetArray[z];
                    for (int i = 0; i < annualCourseList.size(); i++) {
                        if (courseList.equals(annualCourseList.get(i).getTrainingId().getCourseName())) {
                            department = department + comma + annualCourseList.get(i).getAnnualNeedRequestId().getDeptId().getDepName();
                            totalPartc = totalPartc + annualCourseList.get(i).getNoOfNominee();
                            comma = ", ";
                            if (!courceName.equalsIgnoreCase(annualCourseList.get(i).getTrainingId().getCourseName())) {
                                courceName = annualCourseList.get(i).getTrainingId().getCourseName();
                                int bYear = new Integer(String.valueOf(hrTdAnnualNeedRequests.getBudgetYear()));
                                newPlanId += 1;
                                plan.setId(newPlanId);
                                plan.setYear(bYear);
                                plan.setTrainingId(annualCourseList.get(i).getTrainingId());
                                plan.setPreparedBy(String.valueOf(sessionBeanLocal.getUserId()));
                                plan.setPreparedOn(preparedDate);
                            }
                        }
                    }
                    plan.setTotalParticipant(totalPartc);
                    plan.setDept(department);
                    getTrainingNeedSummaryList().add(plan);
                    plan = null;
                    department = "";
                    totalPartc = 0;
                }
                recreateAnnualTraningPlan();
            }
        }
    }

    public String dateFormat(String date) {
        String[] dateFromUI;
        String dateInDB;
        if (date != null && date.contains("-")) {
            dateFromUI = date.split("-");
            dateInDB = dateFromUI[2] + "/" + dateFromUI[1] + "/" + dateFromUI[0];
            return dateInDB;
        }
        return null;
    }

    public void editPlanGroup() {
        hrTdAnnualPlanGroup = annualPlansGroupDataModel.getRowData();
        hrTdTrainerProfiles = hrTdAnnualPlanGroup.getInsId();
        setHrTdCourses(hrTdAnnualPlans.getTrainingId());
        addorUpdate = "Modify";
    }

    public void recreateParticipant() {
        hrTdAnnulTraParticipantM = null;
        hrTdAnnulTraParticipantM = new ListDataModel(new ArrayList<>(hrTdAnnualTrainingNeeds.getHrTdAnulTraPaticptsList()));
    }
    List<HrTdAnnualTraParticipants> ParticipantList = new ArrayList<>();

    public List<HrTdAnnualTraParticipants> getParticipantList() {
        if (ParticipantList == null) {
            ParticipantList = new ArrayList<>();
        }
        return ParticipantList;
    }

    public void setParticipantList(List<HrTdAnnualTraParticipants> ParticipantList) {
        this.ParticipantList = ParticipantList;
    }

    public void addDetailToParticipant() {
//        hrTdAnnualPlanGroup = annualPlansGroupDataModel.getRowData();
        renderParticipant = true;
        ParticipantList = annualPlanBeanLocal.viewParticipantDetail();
        //hrTdAnnualTrainingNeeds = hrTdAnnualTrainingNeedsDataModel.getRowData();
//        recreateParticipant();
//        hrTdCourses = hrTdAnnualTrainingNeeds.getTrainingId();
//        hrTdCoursesList = hrTrainingCoursesBeanLocal.findByCatagory(hrTdCourses);
//        setHrTdCourses(hrTdAnnualTrainingNeeds.getTrainingId());
    }

    public void saveAnnualTrainingPlan() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveAnnualTrainingPlan", dataset)) {
                if ((!(annualPlansGroupDataModel.getRowCount() > 0))) {
                    JsfUtil.addFatalMessage("Plan data table shoud be filled");
                } else {
                    for (int i = 0; i < trainingNeedSummaryList.size(); i++) {
                        trainingNeedSummaryList.get(i).setPreparedBy(String.valueOf(sessionBeanLocal.getUserId()));
                        trainingNeedSummaryList.get(i).setPreparedOn(preparedDate);
                        annualPlanBeanLocal.save(trainingNeedSummaryList.get(i));
                    }
                    if (updateStatus == 0) {
                        JsfUtil.addSuccessMessage("Successfully Saved");
                    } else {
                        JsfUtil.addSuccessMessage("Successfully Update");
                    }
                    clearAnnualTrainingPlan();
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveAnnualTrainingPlan");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JsfUtil.addFatalMessage("Error occured while saving");
        }
    }

    public void clearAnnualTrainingPlan() {
        hrTdAnnualNeedRequests = null;
        annualTrainingPlanDataModel = null;
        annualPlansGroupDataModel = null;
        SaveOrUpdateButton = "Save";
    }

    public void recreateDataModel(List<HrTdAnnualPlans> recreateDataModel) {
        annualPlanDataModel = null;
        annualPlanDataModel = new ListDataModel(new ArrayList<>(recreateDataModel));
    }

    public void serachByBudgetYear(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            int bYear = Integer.parseInt(event.getNewValue().toString());
            recreateDataModel(annualPlanBeanLocal.findByYear(bYear));
        }
    }

}
