/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.promotion;

import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.organization.HrJobEducQualificationsBeanLocal;
import et.gov.eep.hrms.businessLogic.promotion.HrPromExprinceCriteriaBeanLocal;
import et.gov.eep.hrms.businessLogic.promotion.PromotionApplyBeanLocal;
import et.gov.eep.hrms.businessLogic.promotion.PromotionCriteriaBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmpEducations;
import et.gov.eep.hrms.entity.employee.HrEmpExperiences;
import et.gov.eep.hrms.entity.employee.HrEmpTrainings;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationResults;
import et.gov.eep.hrms.entity.organization.HrJobEducQualifications;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.promotion.HrPromExprinceCriteria;
import et.gov.eep.hrms.entity.promotion.HrPromotionCriterias;
import et.gov.eep.hrms.entity.promotion.HrPromotionRequests;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisedJobs;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisements;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import javax.xml.namespace.QName;
import javax.xml.bind.JAXBElement;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author user
 */
@Named("candEvaControllers")
@ViewScoped
public class CandidateShortListingController implements Serializable {

    @Inject
    SessionBean sessionBean;
    @EJB
    WfHrProcessedBeanLocal hrWfProcessedBeanLocal;
    @Inject
    WfHrProcessed wfHrProcessed;
    DataModel<WfHrProcessed> workflowDataModel;

    @EJB
    HrJobEducQualificationsBeanLocal hrJobEducQualificationsBeanLocal;

    @EJB
    HrPromExprinceCriteriaBeanLocal hrPromExprinceCriteriaBeanLocal;
    @EJB
    PromotionApplyBeanLocal promotionApplyBeanLocal;

    @EJB
    PromotionCriteriaBeanLocal promotionCriteriaBeanLocal;

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

    @Inject
    HrJobTypes jobTypes;

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
    //private HrPromotionRequests[] selectedCom;

    // private boolean selected;
    private boolean disableBtnCreate;
    private String createOrSearchBundle = "Search";
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    private boolean validFromTo = false;
    private String saveOrUpdateButton = "Save";
    private String icone = "ui-icon-document";
    List<HrAdvertisements> activeVacancies;
    private HrAdvertisedJobs selectedRow;
    private HrPromotionRequests selctedCandidate;

    //  <editor-fold defaultstate="collapsed" desc="Post Construct ">
    @PostConstruct
    public void init() {
        candidateList();
        loadExprienceWeight();
    }
// </editor-fold>

    //  <editor-fold defaultstate="collapsed" desc="Render getter and setter emp training, exprience,education">
    public String getSaveOrUpdateButton() {
        return saveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String saveOrUpdateButton) {
        this.saveOrUpdateButton = saveOrUpdateButton;
    }

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

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getter and setter ">
    public WfHrProcessed getWfHrProcessed() {
        if (wfHrProcessed == null) {
            wfHrProcessed = new WfHrProcessed();
        }
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

    public void recreateWorkflowDataModel() {
        workflowDataModel = null;
        workflowDataModel = new ListDataModel(new ArrayList(promotionApply.getHrWorkFlowPromList()));
        System.out.println("--workflowDataModel---" + workflowDataModel);
    }

    public List<HrJobEducQualifications> getHrJobEducQualificationsesList() {
        return hrJobEducQualificationsesList;
    }

    public void setHrJobEducQualificationsesList(List<HrJobEducQualifications> hrJobEducQualificationsesList) {
        this.hrJobEducQualificationsesList = hrJobEducQualificationsesList;
    }

    public HrPromotionRequests getSelctedCandidate() {
        return selctedCandidate;
    }

    public void setSelctedCandidate(HrPromotionRequests selctedCandidate) {
        this.selctedCandidate = selctedCandidate;
    }

    public HrJobTypes getJobTypes() {
        return jobTypes;
    }

    public void setJobTypes(HrJobTypes jobTypes) {
        this.jobTypes = jobTypes;
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
//</editor-fold>

    double workExpWeight = 0.0;
    double educLevlWeight = 0.0;
    double performanceWeight = 0.0;
    double interviewOrTestWeight = 0.0;
    double trainingWeight = 0.0;

    public double getWorkExpWeight() {
        return workExpWeight;
    }

    public double getEducLevlWeight() {
        return educLevlWeight;
    }

    public double getPerformanceWeight() {
        return performanceWeight;
    }

    public double getInterviewOrTestWeight() {
        return interviewOrTestWeight;
    }

    public double getTrainingWeight() {
        return trainingWeight;
    }

    public void candidateList() {
        hrPromotionApplyses = promotionApplyBeanLocal.candidateShortList();
        if (hrPromotionApplyses.size() > 0) {
            criteria = promotionCriteriaBeanLocal.readAllCriterias();
            for (int i = 0; i < criteria.size(); i++) {
                HrPromotionCriterias get = criteria.get(i);
                if (1 == get.getLuPromoCriteriaNameId().getId()) {
                    workExpWeight = get.getWeight();
                } else if (2 == get.getLuPromoCriteriaNameId().getId()) {
                    educLevlWeight = get.getWeight();
                } else if (3 == get.getLuPromoCriteriaNameId().getId()) {
                    performanceWeight = get.getWeight();
                } else if (4 == get.getLuPromoCriteriaNameId().getId()) {
                    interviewOrTestWeight = get.getWeight();
                } else if (5 == get.getLuPromoCriteriaNameId().getId()) {
                    trainingWeight = get.getWeight();
                }
            }
        }
    }
    public void candidShortList(ValueChangeEvent event) {
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        createOrSearchBundle = "Search";
        employeelist.clear();
        if (null != event.getNewValue()) {
            String jobsId = event.getNewValue().toString();
            hrPromoApplyList = promotionApplyBeanLocal.candList(jobsId);

            for (HrPromotionRequests hrPromoApplyList1 : hrPromoApplyList) {
                promotionApply = (HrPromotionRequests) hrPromoApplyList1;
                advertisedJobsenty = promotionApply.getAdvertJobId();
                hrAdvertisements = advertisedJobsenty.getAdvertId();
                jobTypes = advertisedJobsenty.getJobId();
                hrJobEducQualificationsesList = hrJobEducQualificationsBeanLocal.getByJobID(jobTypes);
            }
        }
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

    public void onRowUnselect(UnselectEvent event) {
        promotionApply = ((HrPromotionRequests) event.getObject());
        promotionApply.setStatus(0);
        FacesMessage msg = new FacesMessage("Candidadate ---Unselected" + promotionApply);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowSelect(SelectEvent event) {
        promotionApply = ((HrPromotionRequests) event.getObject());
        promotionApply.setStatus(1);
        FacesMessage msg = new FacesMessage(promotionApply.getRequesterId().getEmpId() + " is Selected");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void muti(AjaxBehaviorEvent event) {
        for (int i = 0; i < hrPromoApplyList.size(); i++) {
            promotionApply = hrPromoApplyList.get(i);
            promotionApply.setStatus(1);
        }

    }

    public void clear() {
        workflowDataModel = null;
        promotionApply = null;
        hrEmployees = null;
        update = 0;
        saveOrUpdateButton = "Save";
        hrEmpEducationsList = null;

        hrEmpTrainingsList = null;

        hrEmpExperiencesList = null;
        hrEvaluationResultsList = null;
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
        applysesList = promotionApplyBeanLocal.getByJobsId(advertisedJobsenty);
        hrProApplyM = new ListDataModel(applysesList);

    }

    public void giveCandidateResult(SelectEvent events) {
        try {

            System.out.println("--events--" + events.toString());
            promotionApply = null;
            promotionApply = (HrPromotionRequests) events.getObject();
            System.out.println("--promotionApply--" + promotionApply);
            //promotionApply = hrProApplyM.getRowData();
            hrEmployees = promotionApply.getRequesterId();
            hrEmpEducationsList = promotionApplyBeanLocal.findEmpEducation(hrEmployees);

            hrEmpTrainingsList = promotionApplyBeanLocal.findEmpTrainings(hrEmployees);

            hrEmpExperiencesList = promotionApplyBeanLocal.findEmpExprience(hrEmployees);
            hrEvaluationResultsList = promotionApplyBeanLocal.findEmpEvalution(hrEmployees);

            recreateWorkflowDataModel();
            totalResult();

            promotionApply.setTotal(totalResult());
            if (promotionApply.getTotal() > 0) {
                System.out.println("----totalResult()-" + totalResult());
                saveOrUpdateButton = "Update";

                update = 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showEducAndExprience() {
        promotionApply = hrProApplyM.getRowData();
    }

    public double totalResult() {
        return promotionApply.getEducResult()
                + promotionApply.getExamResult()
                + promotionApply.getExprResult()
                + promotionApply.getPrfmResult()
                + promotionApply.getTrainingResult();
    }
    int update = 0;

    public void savePromotionEvalutionResult() {
        try {

            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "savePromotionEvalutionResult", dataset)) {
//                 put ur code here...!
                if (hrEmployees == null || promotionApply == null) {
                    JsfUtil.addFatalMessage("No candidate selected,please select candidate ");

                } else if (promotionApply.getExprResult() > workExpWeight) {
                    JsfUtil.addFatalMessage("Experiance result can't be greater than " + workExpWeight);

                } else if (promotionApply.getEducResult() > educLevlWeight) {
                    JsfUtil.addFatalMessage("Education result can't be greater than " + educLevlWeight);

                } else if (promotionApply.getExamResult() > interviewOrTestWeight) {
                    JsfUtil.addFatalMessage("Exam result can't be greater than " + interviewOrTestWeight);

                } else if (promotionApply.getTrainingResult() > trainingWeight) {
                    JsfUtil.addFatalMessage("Training result can't be greater than " + trainingWeight);

                } else if (promotionApply.getPrfmResult() > performanceWeight) {

                    JsfUtil.addFatalMessage("Performance result can't be greater than " + performanceWeight);
                } else {

                    promotionApply.setStatus(0);
                    wfHrProcessed.setDecision(promotionApply.getStatus());
                    wfHrProcessed.setProcessedBy(sessionBean.getUserId());
                    wfHrProcessed.setPromotionId(promotionApply);

                    System.out.println("---hrWfProcessed-1--" + wfHrProcessed);
                    hrWfProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                    promotionApplyBeanLocal.saveOrUpdate(promotionApply);

                    if (update == 0) {
                        JsfUtil.addSuccessMessage("Saved Successfully");
                    } else {
                        JsfUtil.addSuccessMessage("Update Successful.");
                    }

                    clear();
                    renderPnlCreateAdditional = true;

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
            e.printStackTrace();
            JsfUtil.addErrorMessage("Error Occured");
        }

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

}
