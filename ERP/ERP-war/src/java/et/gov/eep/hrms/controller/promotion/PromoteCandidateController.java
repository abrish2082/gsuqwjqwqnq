/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.promotion;

import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import static et.gov.eep.commonApplications.utility.JsfUtil.addErrorMessage;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrJobEducQualificationsBeanLocal;
import et.gov.eep.hrms.businessLogic.promotion.HrPromExprinceCriteriaBeanLocal;
import et.gov.eep.hrms.businessLogic.promotion.PromotionApplyBeanLocal;
import et.gov.eep.hrms.businessLogic.promotion.PromotionCriteriaBeanLocal;
import et.gov.eep.hrms.businessLogic.recruitment.HrRecruitmentRequestsBeanLocal;
import et.gov.eep.hrms.businessLogic.transfer.InternalMovementBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmpEducations;
import et.gov.eep.hrms.entity.employee.HrEmpExperiences;
import et.gov.eep.hrms.entity.employee.HrEmpTrainings;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationResults;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobEducQualifications;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.entity.organization.HrSalaryScales;
import et.gov.eep.hrms.entity.promotion.HrPromExprinceCriteria;
import et.gov.eep.hrms.entity.promotion.HrPromotionCriterias;
import et.gov.eep.hrms.entity.promotion.HrPromotionRequests;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisedJobs;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisements;
import et.gov.eep.hrms.entity.recruitment.HrRecruitmentRequests;
import et.gov.eep.hrms.entity.transfer.HrEmpInternalHistory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author user
 */
@Named("promoteCandControllers")
@ViewScoped
public class PromoteCandidateController implements Serializable {
  @Inject
    SessionBean sessionBean;
    @EJB
    WfHrProcessedBeanLocal hrWfProcessedBeanLocal;
    @Inject
    WfHrProcessed wfHrProcessed;
    DataModel<WfHrProcessed> workflowDataModel;

     @Inject
    WorkFlow workFlow;
    @EJB
    HrEmployeeBeanLocal hrEmployeeBeanLocal;

   

    @EJB
    InternalMovementBeanLocal internalMovementBeanLocal;

    @EJB
    HrDepartmentsBeanLocal departmentsBeanLocal;

    @EJB
    HrJobEducQualificationsBeanLocal hrJobEducQualificationsBeanLocal;

    @EJB
    HrPromExprinceCriteriaBeanLocal hrPromExprinceCriteriaBeanLocal;
    @EJB
    PromotionApplyBeanLocal promotionApplyBeanLocal;

    @EJB
    PromotionCriteriaBeanLocal promotionCriteriaBeanLocal;
    @EJB
    HrRecruitmentRequestsBeanLocal hrRecruitmentRequestsBeanLocal;

    @Inject
    HrPromotionRequests promotionApply;

    @Inject
    HrPromotionCriterias hrPromotionCriterias;

    @Inject
    HrAdvertisements hrAdvertisements;

    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrAdvertisedJobs advertisedJobsenty;

    @Inject
    HrEmpEducations hrEmpEducations;
    @Inject
    HrEmpTrainings hrEmpTraining;
    @Inject
    HrEmpExperiences hrEmpExperiences;
    @Inject
    HrEvaluationResults hrEvaluationResult;
//    @Inject
//    HrEmpInternalHistory hrEmpInternalHistory;
    @Inject
    HrJobTypes jobTypes;
    @Inject
    HrDepartments hrDepartments;
    @Inject
    HrSalaryScaleRanges hrSalaryScaleRanges;
    @Inject
    HrRecruitmentRequests recruitmentRequests;
    @Inject
    HrSalaryScales hrSalaryScales;

    List<HrSalaryScales> hrSalaryScaleses;

    List<HrEmpEducations> hrEmpEducationsList = new ArrayList<>();
    List<HrEmpTrainings> hrEmpTrainingsList = new ArrayList<>();
    List<HrEmpExperiences> hrEmpExperiencesList = new ArrayList<>();
    List<HrEvaluationResults> hrEvaluationResultsList = new ArrayList<>();
    List<HrEvaluationResults> hrEvaluationResultsListUI = new ArrayList<>();

    List<HrPromotionRequests> hrPromotionApplyses = new ArrayList();

    List<HrEmployees> employeelist = new ArrayList<>();
    List<HrPromotionRequests> hrPromoApplyList = new ArrayList<>();

    List<HrAdvertisedJobs> advertisedJobs;

    private ArrayList<HrPromotionRequests> selectedRows;
    List<HrJobEducQualifications> hrJobEducQualificationsesList;
    HrEmpInternalHistory hrEmpInternalHistory = new HrEmpInternalHistory();
    //private HrPromotionRequests[] selectedCom;

    // private boolean selected;
    private boolean disableBtnCreate;
    private String saveorUpdateBundle = "Create";
    private String createOrSearchBundle = "Search";
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    private boolean validFromTo = false;
    private String icone = "ui-icon-document";
    List<HrAdvertisements> activeVacancies;
    private HrAdvertisedJobs selectedRow;

    ArrayList<HrRecruitmentRequests> hrRecruitmentRequestList;

    //  <editor-fold defaultstate="collapsed" desc="Post Construct ">
    @PostConstruct
    public void init() {
        candidateList();
        loadExprienceWeight();
    }
// </editor-fold>

    //  <editor-fold defaultstate="collapsed" desc="Render getter and setter emp training, exprience,education">
    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }

    public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
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

    public ArrayList<HrPromotionRequests> getSelectedRows() {
        return selectedRows;
    }

    public DataModel<WfHrProcessed> getWorkflowDataModel() {
        return workflowDataModel;
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getter and setter ">
    public void setWorkflowDataModel(DataModel<WfHrProcessed> workflowDataModel) {
        this.workflowDataModel = workflowDataModel;
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

    public List<HrJobEducQualifications> getHrJobEducQualificationsesList() {
        return hrJobEducQualificationsesList;
    }

    public void setHrJobEducQualificationsesList(List<HrJobEducQualifications> hrJobEducQualificationsesList) {
        this.hrJobEducQualificationsesList = hrJobEducQualificationsesList;
    }

    public ArrayList<HrRecruitmentRequests> getHrRecruitmentRequestList() {
        return hrRecruitmentRequestList;
    }

    public void setHrRecruitmentRequestList(ArrayList<HrRecruitmentRequests> hrRecruitmentRequestList) {
        this.hrRecruitmentRequestList = hrRecruitmentRequestList;
    }

    public List<HrSalaryScales> getHrSalaryScaleses() {
        return hrSalaryScaleses;
    }

    public void setHrSalaryScaleses(List<HrSalaryScales> hrSalaryScaleses) {
        this.hrSalaryScaleses = hrSalaryScaleses;
    }

    public HrJobTypes getJobTypes() {
        return jobTypes;
    }

    public void setJobTypes(HrJobTypes jobTypes) {
        this.jobTypes = jobTypes;
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

    public HrSalaryScaleRanges getHrSalaryScaleRanges() {
        if (hrSalaryScaleRanges == null) {
            hrSalaryScaleRanges = new HrSalaryScaleRanges();
        }
        return hrSalaryScaleRanges;
    }

    public HrSalaryScales getHrSalaryScales() {
        if (hrSalaryScales == null) {
            hrSalaryScales = new HrSalaryScales();
        }
        return hrSalaryScales;
    }

    public void setHrSalaryScales(HrSalaryScales hrSalaryScales) {
        this.hrSalaryScales = hrSalaryScales;
    }

    public HrEmpInternalHistory getHrEmpInternalHistory() {
        if (hrEmpInternalHistory == null) {
            hrEmpInternalHistory = new HrEmpInternalHistory();
        }
        return hrEmpInternalHistory;
    }

    public void setHrEmpInternalHistory(HrEmpInternalHistory hrEmpInternalHistory) {
        this.hrEmpInternalHistory = hrEmpInternalHistory;
    }

    public void setHrSalaryScaleRanges(HrSalaryScaleRanges hrSalaryScaleRanges) {
        this.hrSalaryScaleRanges = hrSalaryScaleRanges;
    }

    public HrEmpEducations getHrEmpEducations() {
        if (hrEmpEducations == null) {
            hrEmpEducations = new HrEmpEducations();
        }
        return hrEmpEducations;
    }

    public void setHrEmpEducations(HrEmpEducations hrEmpEducations) {
        this.hrEmpEducations = hrEmpEducations;
    }

    public HrEmpTrainings getHrEmpTraining() {
        if (hrEmpTraining == null) {
            hrEmpTraining = new HrEmpTrainings();
        }
        return hrEmpTraining;
    }

    public void setHrEmpTraining(HrEmpTrainings hrEmpTraining) {
        this.hrEmpTraining = hrEmpTraining;
    }

    public HrEmpExperiences getHrEmpExperiences() {
        if (hrEmpExperiences == null) {
            hrEmpExperiences = new HrEmpExperiences();

        }
        return hrEmpExperiences;
    }

    public void setHrEmpExperiences(HrEmpExperiences hrEmpExperiences) {
        this.hrEmpExperiences = hrEmpExperiences;
    }

    public List<HrEmpEducations> getHrEmpEducationsList() {
        return hrEmpEducationsList;
    }

    public void setHrEmpEducationsList(List<HrEmpEducations> hrEmpEducationsList) {
        this.hrEmpEducationsList = hrEmpEducationsList;
    }

    public List<HrEmpTrainings> getHrEmpTrainingsList() {
        return hrEmpTrainingsList;
    }

    public void setHrEmpTrainingsList(List<HrEmpTrainings> hrEmpTrainingsList) {
        this.hrEmpTrainingsList = hrEmpTrainingsList;
    }

    public List<HrEmpExperiences> getHrEmpExperiencesList() {
        return hrEmpExperiencesList;
    }

    public void setHrEmpExperiencesList(List<HrEmpExperiences> hrEmpExperiencesList) {
        this.hrEmpExperiencesList = hrEmpExperiencesList;
    }

    public List<HrEvaluationResults> getHrEvaluationResultsList() {

        return hrEvaluationResultsList;
    }

    public void setHrEvaluationResultsList(List<HrEvaluationResults> hrEvaluationResultsList) {
        this.hrEvaluationResultsList = hrEvaluationResultsList;

    }

    public List<HrEvaluationResults> getHrEvaluationResultsListUI() {
        return hrEvaluationResultsListUI;
    }

    public void setHrEvaluationResultsListUI(List<HrEvaluationResults> hrEvaluationResultsListUI) {
        this.hrEvaluationResultsListUI = hrEvaluationResultsListUI;
    }

    public HrPromotionCriterias getHrPromotionCriterias() {
        if (hrPromotionCriterias == null) {
            hrPromotionCriterias = new HrPromotionCriterias();
        }
        return hrPromotionCriterias;
    }

    public void setHrPromotionCriterias(HrPromotionCriterias hrPromotionCriterias) {
        this.hrPromotionCriterias = hrPromotionCriterias;
    }

    public void setSelectedRows(ArrayList<HrPromotionRequests> selectedRows) {
        this.selectedRows = selectedRows;
    }

    public HrAdvertisedJobs getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(HrAdvertisedJobs selectedRow) {
        this.selectedRow = selectedRow;
    }

    public List<HrPromotionRequests> getHrPromoApplyList() {
        return hrPromoApplyList;
    }

    public void setHrPromoApplyList(List<HrPromotionRequests> hrPromoApplyList) {
        this.hrPromoApplyList = hrPromoApplyList;
    }

    public List<HrAdvertisements> getActiveVacancies() {
        return activeVacancies = promotionApplyBeanLocal.activeVacancies();
    }

    public void activeVacancy_processValueChange(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            for (HrAdvertisements activeVacancie : activeVacancies) {
                hrAdvertisements = activeVacancie;
                int advertId = Integer.parseInt(event.getNewValue().toString());
                advertisedJobs = promotionApplyBeanLocal.readAdverJobsQualification(advertId);
                hrPromotionApplyses = promotionApplyBeanLocal.candidateShortList();
            }
        }
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

    public HrAdvertisedJobs getAdvertisedJobsenty() {
        if (advertisedJobsenty == null) {
            advertisedJobsenty = new HrAdvertisedJobs();
        }
        return advertisedJobsenty;
    }

    public void setAdvertisedJobsenty(HrAdvertisedJobs advertisedJobsenty) {
        this.advertisedJobsenty = advertisedJobsenty;
    }

    public List<HrAdvertisedJobs> getAdvertisedJobs() {
        return advertisedJobs;
    }

    public void setAdvertisedJobs(List<HrAdvertisedJobs> advertisedJobs) {
        this.advertisedJobs = advertisedJobs;
    }

    public List<HrEmployees> getEmployeelist() {
        return employeelist;
    }

    public void setEmployeelist(List<HrEmployees> employeelist) {
        this.employeelist = employeelist;
    }

    public List<HrPromotionRequests> getHrPromotionRequestses() {
        return hrPromotionApplyses;
    }

    public void setHrPromotionRequestses(List<HrPromotionRequests> hrPromotionApplyses) {
        this.hrPromotionApplyses = hrPromotionApplyses;
    }

    public void candidateList() {
        hrPromotionApplyses = promotionApplyBeanLocal.candidateShortList();
    }
//</editor-fold>

    public void recreateWorkflowDataModel() {
        workflowDataModel = null;
        workflowDataModel = new ListDataModel(new ArrayList(promotionApply.getHrWorkFlowPromList()));
    }

    public void candidShortList(ValueChangeEvent event) {
        hrAdvertisements = new HrAdvertisements();
        
        try {
            renderPnlManPage = false;
            renderPnlCreateAdditional = true;
            createOrSearchBundle = "Search";
            employeelist.clear();
//        recreateWorkflowDataModel();
            if (null != event.getNewValue()) {
                String jobsId = event.getNewValue().toString();
                hrPromoApplyList = promotionApplyBeanLocal.candList(jobsId);
                String recruitmentReqstID = "";
                for (HrPromotionRequests hrPromoApplyList1 : hrPromoApplyList) {
                    promotionApply = (HrPromotionRequests) hrPromoApplyList1;
                    advertisedJobsenty = promotionApply.getAdvertJobId();
                    hrAdvertisements = advertisedJobsenty.getAdvertId();

                    recruitmentReqstID = advertisedJobsenty.getRecruitRequestId();
                    jobTypes = advertisedJobsenty.getJobId();
                    hrJobEducQualificationsesList = hrJobEducQualificationsBeanLocal.getByJobID(jobTypes);
                }
                if (jobTypes != null) {
                    hrSalaryScaleRanges = jobTypes.getJobGradeId();

                    if (hrSalaryScaleRanges != null) {
                        hrSalaryScaleses = hrSalaryScaleRanges.getHrSalaryScalesList();
                    } else {
                        JsfUtil.addErrorMessage("PayGrade value for Job Title is null");
                    }

                } else {
                    JsfUtil.addErrorMessage("Job ID value is null");
                }

                if (recruitmentReqstID != null) {

                    recruitmentRequests = hrRecruitmentRequestsBeanLocal.getByRecruitmentReqstID(recruitmentReqstID);
                    if (recruitmentRequests != null) {
                        hrDepartments = recruitmentRequests.getDeptId();
                    } else {
                        JsfUtil.addErrorMessage("Recruitment Request Obj value is null");
                    }
                } else {
                    JsfUtil.addErrorMessage("Recruitment Request ID value is null");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void selectedScale(ValueChangeEvent scale) {
        hrSalaryScales = (HrSalaryScales) scale.getNewValue();
    }

    public void onRowUnselect(UnselectEvent event) {
        promotionApply = ((HrPromotionRequests) event.getObject());
        hrEmployees = promotionApply.getRequesterId();
        promotionApply.setStatus(0);
        FacesMessage msg = new FacesMessage(promotionApply.getRequesterId().getEmpId() + " is UnSelected");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowSelect(SelectEvent event) {
        promotionApply = ((HrPromotionRequests) event.getObject());
        hrEmployees = promotionApply.getRequesterId();
        promotionApply.setStatus(1);
        recreateWorkflowDataModel();
        FacesMessage msg = new FacesMessage(promotionApply.getRequesterId().getEmpId() + " is Selected");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void muti(AjaxBehaviorEvent event) {
        for (int i = 0; i < hrPromoApplyList.size(); i++) {
            promotionApply = hrPromoApplyList.get(i);
            hrEmployees = promotionApply.getRequesterId();
            promotionApply.setStatus(1);
        }

    }

    public void unselectAllRowsOfDT() {
        for (int i = 0; i < hrPromoApplyList.size(); i++) {
            promotionApply = hrPromoApplyList.get(i);
            promotionApply.setStatus(0);
        }
        hrEmployees = null;
    }

    public void maintainHistory() {
        if (hrEmployees != null) {
            System.out.println("---hrEmployees--" + hrEmployees);
            hrEmpInternalHistory.setEmpId(hrEmployees);
            if (hrEmployees.getDeptId() != null) {
                hrEmpInternalHistory.setOldDepId(hrEmployees.getDeptId().getDepId());
            } else if (hrEmployees.getJobId() != null) {
                hrEmpInternalHistory.setOldJobId(hrEmployees.getJobId().getId());
            } else if (hrEmployees.getSalaryStep() != null) {
                hrEmpInternalHistory.setOldSalaryStep(hrEmployees.getSalaryStep().getSalaryStep().toString());
            }

            System.out.println("---hrEmpInternalHistory--" + hrEmpInternalHistory);
            internalMovementBeanLocal.saveOrUpdate(hrEmpInternalHistory);

        } else {
            JsfUtil.addErrorMessage("Please select candidate");
        }

    }

    public void updateEmployeePosition() {
        if (hrEmployees != null) {
            if (!Objects.equals(hrEmployees.getSalaryStep(), hrSalaryScales.getSalaryStepId())) {
                hrEmployees.setDeptId(hrDepartments);
                hrEmployees.setJobId(jobTypes);
                hrEmployees.setSalaryStep(hrSalaryScales.getSalaryStepId());
                hrEmployees.setGradeId(hrSalaryScaleRanges);
                hrEmployees.setBasicSalary(hrSalaryScales.getSalary());
                hrEmployeeBeanLocal.saveOrUpdate(hrEmployees);
            } else {
                JsfUtil.addErrorMessage("You are already promoted for this job title");
            }

        } else {
            JsfUtil.addErrorMessage("Employee null value");
        }

    }

    public void savePromoSelectedCadidate() {
        try {

            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "savePromoSelectedCadidate", dataset)) {
//                 put ur code here...!
                WorkFlow workFlow = new WorkFlow();
                if (hrEmployees != null) {
                    for (HrPromotionRequests promotionApply : hrPromoApplyList) {
                        promotionApplyBeanLocal.saveOrUpdate(promotionApply);
                        updateEmployeePosition();
                        maintainHistory();
                        System.out.println("---promotionApply status-1--" + promotionApply.getStatus());
                        if (promotionApply.getStatus() == 1 && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_VALUE);
                            promotionApply.setStatus(workFlow.getUserStatus());
                        } else if (promotionApply.getStatus() == 1 && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                            promotionApply.setStatus(workFlow.getUserStatus());
                        } else if (promotionApply.getStatus() == 0 && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                            promotionApply.setStatus(workFlow.getUserStatus());
                        } else if (promotionApply.getStatus() == 0 && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                            promotionApply.setStatus(workFlow.getUserStatus());
                        }
                        wfHrProcessed.setDecision(promotionApply.getStatus());
                        System.out.println("---hrWfProcessed-1--" + wfHrProcessed);
                        wfHrProcessed.setPromotionId(promotionApply);
                        hrWfProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                    }

                    JsfUtil.addSuccessMessage("Submitted Successfully");
                    clear();
                } else {
                    FacesMessage msg = new FacesMessage("Employee null value");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    addErrorMessage("Employee null value");
                }

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBean.getSessionID());
                eventEntry.setUserId(sessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBean.getUserName());
                eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);

            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error Occurred");
        }

    }

    public void clear() {
//        hrAdvertisements = null;
//        hrSalaryScaleses = null;
//        advertisedJobsenty = null;
//        hrSalaryScales = null;
        hrSalaryScales = null;
        hrEmpInternalHistory = null;
        hrEmployees = null;
        promotionApply = null;
    }

    public HrPromotionRequests getPromotionApply() {
        if (promotionApply == null) {
            promotionApply = new HrPromotionRequests();
        }
        return promotionApply;
    }

    public void setPromotionApply(HrPromotionRequests promotionApply) {
        this.promotionApply = promotionApply;
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

    public void createNewAdditionalAmount() {
        saveorUpdateBundle = "Create";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            setIcone("ui-icon-document");
        }
    }

    public void populate(SelectEvent events) {
        promotionApply = null;
        promotionApply = (HrPromotionRequests) events.getObject();
        createOrSearchBundle = "Update";
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        createOrSearchBundle = "Search";
    }
    // promotion Evalution

    public void formToValueChange(ValueChangeEvent event) {
        hrAdvertisements = new HrAdvertisements();
        if (null != event.getNewValue()) {
            int advertId = Integer.parseInt(event.getNewValue().toString());
            advertisedJobs = promotionApplyBeanLocal.readAdverJobsQualification(advertId);

        }
    }
    DataModel<HrPromotionRequests> hrProApplyM = new ListDataModel<>();

    public DataModel<HrPromotionRequests> getHrProApplyM() {
        return hrProApplyM;
    }

    public void setHrProApplyM(DataModel<HrPromotionRequests> hrProApplyM) {
        this.hrProApplyM = hrProApplyM;
    }

    List<HrPromotionRequests> applysesList = new ArrayList<>();

    public List<HrPromotionRequests> getApplysesList() {
        return applysesList;
    }

    public void setApplysesList(List<HrPromotionRequests> applysesList) {
        this.applysesList = applysesList;
    }

    public void populateCadidate(SelectEvent events) {
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        createOrSearchBundle = "Search";
        advertisedJobsenty = null;
        advertisedJobsenty = (HrAdvertisedJobs) events.getObject();
        System.out.println("advertisedJobsenty--" + advertisedJobsenty);
        applysesList = promotionApplyBeanLocal.getByJobsId(advertisedJobsenty);
        System.out.println("applysesList---" + applysesList);
        hrProApplyM = new ListDataModel(applysesList);

    }

    public void edit() {
        setErrorMsg("");
        hrEvaluationResultsListUI.clear();
        promotionApply = null;
        promotionApply = hrProApplyM.getRowData();
        hrEmployees = promotionApply.getRequesterId();
        hrEmpEducationsList = promotionApplyBeanLocal.findEmpEducation(hrEmployees);
        hrEmpTrainingsList = promotionApplyBeanLocal.findEmpTrainings(hrEmployees);
        hrEmpExperiencesList = promotionApplyBeanLocal.findEmpExprience(hrEmployees);
        hrEvaluationResultsList = promotionApplyBeanLocal.findEmpEvalution(hrEmployees);
        if (hrEvaluationResultsList != null) {
            for (HrEvaluationResults evaluationResults : hrEvaluationResultsList) {
                int totalPerfor = hrEvaluationResultsList.get(0).getId()
                        + hrEvaluationResultsList.get(1).getId()
                        + hrEvaluationResultsList.get(2).getId();

            }
            setHrEvaluationResultsListUI(hrEvaluationResultsList);
        }

        totalResult();
    }

    public void showEducAndExprience() {
        promotionApply = hrProApplyM.getRowData();
        System.out.println("inside edit-------1--" + promotionApply);
    }

    public void totalResult() {
        promotionApply.setTotal(promotionApply.getEducResult()
                + promotionApply.getExamResult()
                + promotionApply.getExprResult()
                + promotionApply.getPrfmResult()
                + promotionApply.getTrainingResult());
    }

    List<HrPromotionCriterias> criteria = new ArrayList<>();

    public List<HrPromotionCriterias> getCriteria() {
        return criteria;
    }

    public void setCriteria(List<HrPromotionCriterias> criteria) {
        this.criteria = criteria;
    }

    private HtmlOutputText txtEducWegiht = new HtmlOutputText();

    public HtmlOutputText getTxtEducWegiht() {
        return txtEducWegiht;
    }

    public void setTxtEducWegiht(HtmlOutputText hot) {
        this.txtEducWegiht = hot;
    }
    int criteriaId;

    List<HrPromExprinceCriteria> hrPromExprinceCriterias = new ArrayList<>();

    public List<HrPromExprinceCriteria> getHrPromExprinceCriterias() {
        return hrPromExprinceCriterias;
    }

    public void setHrPromExprinceCriterias(List<HrPromExprinceCriteria> hrPromExprinceCriterias) {
        this.hrPromExprinceCriterias = hrPromExprinceCriterias;
    }

    public void loadExprienceWeight() {
        hrPromExprinceCriterias = hrPromExprinceCriteriaBeanLocal.findAllExprienceRelations();
    }
    double exprienceResult;

    public double getExprienceResult() {
        return exprienceResult;
    }

    public void setExprienceResult(double exprienceResult) {
        this.exprienceResult = exprienceResult;
    }

    public void calcExprienceResult() {
        exprienceResult = 0.0;
        double eachRow = 0.0;
        for (int i = 0; i < hrPromExprinceCriterias.size(); i++) {
            HrPromExprinceCriteria get = hrPromExprinceCriterias.get(i);
            eachRow = get.getWeight() * get.getYearExprience();
            exprienceResult += eachRow;

        }
        promotionApply.setExprResult(exprienceResult);
    }

    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void newDepartment(ValueChangeEvent newDept) {
        String newDepartment = newDept.getNewValue().toString();

        String[] parts = newDepartment.split("-");
        String recrutmentId = parts[0];
        String depName = parts[1];
    }

}
