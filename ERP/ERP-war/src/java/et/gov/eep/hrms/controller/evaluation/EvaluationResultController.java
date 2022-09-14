/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.evaluation;

import java.io.Serializable;
import java.text.SimpleDateFormat; 
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
import org.primefaces.model.TreeNode;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.evaluation.EvaluationResultBeanLocal;
import et.gov.eep.hrms.businessLogic.evaluation.EvaluationSessionBeanLocal;
import et.gov.eep.hrms.businessLogic.transfer.TransferReqBeanLocal;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationCriteria;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationLevels;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationResultDetails;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationResults;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationSessions;
import et.gov.eep.hrms.entity.organization.HrJobTypes;

/**
 *
 * @author INSA
 */
@Named(value = "evaluationResultController")
@ViewScoped
public class EvaluationResultController implements Serializable {

    @Inject
    HrEvaluationResults hrEvaluationResult;
    @Inject
    HrEvaluationResultDetails hrEvaluationResultDetail;
    @Inject
    HrEvaluationSessions hrEvaluationSessions;
    @Inject
    HrEvaluationCriteria hrEvaluationCriteria;
    @Inject
    HrEvaluationLevels hrEvaluationLevels;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrEmployees evalutor;
    @Inject
    HrEmployees srcEmployees;
    @Inject
    HrDepartments hrDepartments;
    @Inject
    HrJobTypes hrJobTypes;
    @Inject
    HrSalaryScaleRanges hrSalaryScaleRanges;
    @Inject
    SessionBean sessionBeanLocal;

    @EJB
    private HrEmployeeBeanLocal hrEmployeeBean;
    @EJB
    EvaluationResultBeanLocal evaluationResultBeanLocal;
    @EJB
    TransferReqBeanLocal transferReqBeanLocal;
    @EJB
    EvaluationSessionBeanLocal evaluationSessionBeanLocal;

    int updateStatus = 0;
    private String saveorUpdateBundle = "Save";
    private String addorUpdate = "Add";
    private String newOrSearch = "New";
    private String icone = "ui-icon-plus";
    private boolean searchPage = true;
    private boolean newPage = false;
    private boolean btnNewRender = false;
    private TreeNode root;
    private TreeNode selectedNode;
    TreeNode DepartmentList;
    private String preparedDate;
    private String dsbEmployeeID = "false";
    private String dsbEmployeeName = "true";
    private String selected = "Employee ID";

    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    List<HrEvaluationSessions> activeSessions;
    List<HrEvaluationCriteria> criteriaList;
    private List<HrEvaluationResultDetails> resultDetailList;
    private List<HrEvaluationCriteria> listOfCriteria;
    private List<HrEvaluationLevels> listOfLevel;
    private List<HrEvaluationSessions> listOfSession;
    DataModel<HrEvaluationResults> evaluationResultDataModel;
    DataModel<HrEvaluationSessions> sessionDataModel;
    DataModel<HrEvaluationResultDetails> resultDetailDataModel;
    private List<HrEvaluationResults> resultList;
    private List<HrEvaluationLevels> levelList;
    Set<String> checkEvaluation = new HashSet<>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public EvaluationResultController() {
    }

    @PostConstruct
    public void init() {
        String currentDate = StringDateManipulation.toDayInEc();
        setActiveSessions(evaluationSessionBeanLocal.readActiveSession(currentDate));
        setPreparedDate(StringDateManipulation.toDayInEc());
    }

    public void getActiveSession(SelectEvent event) {
        try {
            hrEvaluationResult.setSessionId(hrEvaluationSessions);
            setActiveSessions(activeSessions);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public HrEvaluationResults getHrEvaluationResult() {
        if (hrEvaluationResult == null) {
            hrEvaluationResult = new HrEvaluationResults();
        }
        return hrEvaluationResult;
    }

    public void setHrEvaluationResult(HrEvaluationResults hrEvaluationResult) {
        this.hrEvaluationResult = hrEvaluationResult;
    }

    public HrEvaluationResultDetails getHrEvaluationResultDetail() {
        if (hrEvaluationResultDetail == null) {
            hrEvaluationResultDetail = new HrEvaluationResultDetails();
        }
        return hrEvaluationResultDetail;
    }

    public void setHrEvaluationResultDetail(HrEvaluationResultDetails hrEvaluationResultDetail) {
        this.hrEvaluationResultDetail = hrEvaluationResultDetail;
    }

    public HrEvaluationSessions getHrEvaluationSessions() {
        if (hrEvaluationSessions == null) {
            hrEvaluationSessions = new HrEvaluationSessions();
        }
        return hrEvaluationSessions;
    }

    public void setHrEvaluationSessions(HrEvaluationSessions hrEvaluationSessions) {
        this.hrEvaluationSessions = hrEvaluationSessions;
    }

    public HrEvaluationCriteria getHrEvaluationCriteria() {
        if (hrEvaluationCriteria == null) {
            hrEvaluationCriteria = new HrEvaluationCriteria();
        }
        return hrEvaluationCriteria;
    }

    public void setHrEvaluationCriteria(HrEvaluationCriteria hrEvaluationCriteria) {
        this.hrEvaluationCriteria = hrEvaluationCriteria;
    }

    public HrEvaluationLevels getHrEvaluationLevels() {
        if (hrEvaluationLevels == null) {
            hrEvaluationLevels = new HrEvaluationLevels();
        }
        return hrEvaluationLevels;
    }

    public void setHrEvaluationLevels(HrEvaluationLevels hrEvaluationLevels) {
        this.hrEvaluationLevels = hrEvaluationLevels;
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

    public HrEmployees getEvalutor() {
        if (evalutor == null) {
            evalutor = new HrEmployees();
        }
        return evalutor;
    }

    public void setEvalutor(HrEmployees evalutor) {
        this.evalutor = evalutor;
    }

    public HrEmployees getSrcEmployees() {
        return srcEmployees;
    }

    public void setSrcEmployees(HrEmployees srcEmployees) {
        this.srcEmployees = srcEmployees;
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
        return hrJobTypes;
    }

    public void setHrJobTypes(HrJobTypes hrJobTypes) {
        this.hrJobTypes = hrJobTypes;
    }

    public HrSalaryScaleRanges getHrSalaryScaleRanges() {
        if (hrSalaryScaleRanges == null) {
            hrSalaryScaleRanges = new HrSalaryScaleRanges();
        }
        return hrSalaryScaleRanges;
    }

    public void setHrSalaryScaleRanges(HrSalaryScaleRanges hrSalaryScaleRanges) {
        this.hrSalaryScaleRanges = hrSalaryScaleRanges;
    }

    public DataModel<HrEvaluationResults> getEvaluationResultDataModel() {
        if (evaluationResultDataModel == null) {
            evaluationResultDataModel = new ArrayDataModel<>();
        }
        return evaluationResultDataModel;
    }

    public void setEvaluationResultDataModel(DataModel<HrEvaluationResults> evaluationResultDataModel) {
        this.evaluationResultDataModel = evaluationResultDataModel;
    }

    public DataModel<HrEvaluationSessions> getSessionDataModel() {
        if (sessionDataModel == null) {
            sessionDataModel = new ArrayDataModel<>();
        }
        return sessionDataModel;
    }

    public void setSessionDataModel(DataModel<HrEvaluationSessions> sessionDataModel) {
        this.sessionDataModel = sessionDataModel;
    }

    public DataModel<HrEvaluationResultDetails> getResultDetailDataModel() {
        if (resultDetailDataModel == null) {
            resultDetailDataModel = new ArrayDataModel<>();
        }
        return resultDetailDataModel;
    }

    public void setResultDetailDataModel(DataModel<HrEvaluationResultDetails> resultDetailDataModel) {
        this.resultDetailDataModel = resultDetailDataModel;
    }

    public List<HrEvaluationResults> getResultList() {
        if (resultList == null) {
            resultList = new ArrayList<>();
        }
        return resultList;
    }

    public void setResultList(List<HrEvaluationResults> resultList) {
        this.resultList = resultList;
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

    public List<HrEvaluationCriteria> getListOfCriteria() {
        listOfCriteria = evaluationResultBeanLocal.findAllCriteria();
        return listOfCriteria;
    }

    public void setListOfCriteria(List<HrEvaluationCriteria> listOfCriteria) {
        this.listOfCriteria = listOfCriteria;
    }

    public List<HrEvaluationLevels> getListOfLevel() {
        listOfLevel = evaluationResultBeanLocal.findAllLevel();
        return listOfLevel;
    }

    public void setListOfLevel(List<HrEvaluationLevels> listOfLevel) {
        this.listOfLevel = listOfLevel;
    }

    String toDayDate = StringDateManipulation.toDayInEc();

    public List<HrEvaluationSessions> getListOfSession() {
        return listOfSession;
    }

    public void setListOfSessions(List<HrEvaluationSessions> listOfSession) {
        this.listOfSession = listOfSession;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getAddorUpdate() {
        return addorUpdate;
    }

    public void setAddorUpdate(String addorUpdate) {
        this.addorUpdate = addorUpdate;
    }

    public Set<String> getCheckEvaluation() {
        return checkEvaluation;
    }

    public void setCheckEvaluation(Set<String> checkEvaluation) {
        this.checkEvaluation = checkEvaluation;
    }

    public List<HrEvaluationSessions> getActiveSessions() {
        return activeSessions;
    }

    public void setActiveSessions(List<HrEvaluationSessions> activeSessions) {
        this.activeSessions = activeSessions;
    }

    public List<HrEvaluationCriteria> getCriteriaList() {
        return criteriaList;
    }

    public void setCriteriaList(List<HrEvaluationCriteria> criteriaList) {
        this.criteriaList = criteriaList;
    }

    public List<HrEvaluationResultDetails> getResultDetailList() {
        return resultDetailList;
    }

    public void setResultDetailList(List<HrEvaluationResultDetails> resultDetailList) {
        this.resultDetailList = resultDetailList;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public String getPreparedDate() {
        return preparedDate;
    }

    public void setPreparedDate(String preparedDate) {
        this.preparedDate = preparedDate;
    }

    public String getDsbEmployeeID() {
        return dsbEmployeeID;
    }

    public void setDsbEmployeeID(String dsbEmployeeID) {
        this.dsbEmployeeID = dsbEmployeeID;
    }

    public String getDsbEmployeeName() {
        return dsbEmployeeName;
    }

    public void setDsbEmployeeName(String dsbEmployeeName) {
        this.dsbEmployeeName = dsbEmployeeName;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Save Update">
    public void saveResult() {
        if ((hrEvaluationResult.getEmpId().getId()).equals(hrEvaluationResult.getEvaluatorId().getId())) {
            JsfUtil.addFatalMessage("Can not Evaluate yourself");
        } else {
            try {
                if (updateStatus == 0) {
                    hrEvaluationResult.setStatus(0);
                    evaluationResultBeanLocal.save(hrEvaluationResult);
                    JsfUtil.addSuccessMessage("Saved Successfully");
                    clearPage();
                } else if (updateStatus == 1) {
                    evaluationResultBeanLocal.edit(hrEvaluationResult);
                    JsfUtil.addSuccessMessage("Modified Successfully");
                    updateStatus = 0;
                    saveorUpdateBundle = "Save";
                    clearPage();
                }
            } catch (Exception ex) {
                JsfUtil.addFatalMessage("Unable to Save data");
            }
        }
    }

    public void saveEvaluationResult() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveEvaluationResult", dataset)) {
                if ((!(getResultDetailDataModel().getRowCount() > 0))) {
                    JsfUtil.addFatalMessage("Data table shoud be filled");
                } else if (listOfCriteria.size() != resultDetailDataModel.getRowCount()) {
                    JsfUtil.addFatalMessage("Please evaluate by all criteria");
                } else {
                    try {
                        hrEvaluationResult.setStatus(0);
                        hrEvaluationResult.setEvaluationDate(preparedDate);
                        evaluationResultBeanLocal.saveorUpdate(hrEvaluationResult);
                        if (updateStatus == 0) {
                            JsfUtil.addSuccessMessage("Successfully saved");
                            clearPage();
                        } else if (updateStatus == 1) {
                            clearPage();
                            JsfUtil.addSuccessMessage("Successfully modified");
                        }
                    } catch (Exception e) {
                        JsfUtil.addFatalMessage("Error occurs while save update");
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveEvaluationResult");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String clearPage() {
        hrEvaluationResult = null;
        hrEvaluationResultDetail = null;
        resultDetailDataModel = null;
        hrEvaluationSessions = null;
        hrEvaluationCriteria = null;
        hrEvaluationLevels = null;
        hrEmployees = null;
        selected = null;
        evalutor = null;
        total = 0.0;
        level = null;
        return null;
    }

    public void resetEvaluationResult() {
        hrEvaluationResult = new HrEvaluationResults();
        hrEvaluationResultDetail = new HrEvaluationResultDetails();
        resultDetailDataModel = new ArrayDataModel<>();
        hrEvaluationCriteria = new HrEvaluationCriteria();
        hrEvaluationSessions = new HrEvaluationSessions();
        hrEvaluationLevels = new HrEvaluationLevels();
        hrEmployees = new HrEmployees();
        evalutor = new HrEmployees();
        total = 0.0;
        level = null;
        updateStatus = 0;
        saveorUpdateBundle = "Save";
        addorUpdate = "Add";
    }
    // </editor-fold>

    public void session_valueChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            hrEvaluationSessions.setId(Integer.parseInt(event.getNewValue().toString()));
            hrEvaluationSessions = evaluationSessionBeanLocal.findById(hrEvaluationSessions);
            hrEvaluationResult.setSessionId(hrEvaluationSessions);
        }
    }

    public void criteriaValueChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            hrEvaluationCriteria.setId(Integer.parseInt(event.getNewValue().toString()));
            hrEvaluationCriteria = evaluationResultBeanLocal.findByCriteriaId(hrEvaluationCriteria);
        }
    }

    public void levelValueChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            hrEvaluationLevels.setId(Integer.parseInt(event.getNewValue().toString()));
            hrEvaluationLevels = evaluationResultBeanLocal.findByLevelId(hrEvaluationLevels);
        }
    }

    public void sessionLister(HrEvaluationSessions session) {
        if (hrEvaluationSessions != null) {
            listOfSession = evaluationResultBeanLocal.fetchSession(hrEvaluationSessions);
            sessionDataModel = new ListDataModel(new ArrayList(listOfSession));
        }
    }

    public List<String> getSessions() {
        return evaluationResultBeanLocal.searchByStatus(2010 - 1015);
    }

    public SelectItem[] getListOfSessions() {
        return JsfUtil.getSelectItems(evaluationResultBeanLocal.searchByStatus(Integer.SIZE), true);
    }

    public void searchCriteria(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selected = event.getNewValue().toString();
            if (selected.equalsIgnoreCase("Employee ID")) {
                dsbEmployeeID = "false";
                dsbEmployeeName = "true";
            } else {
                dsbEmployeeID = "true";
                dsbEmployeeName = "false";
            }
        }
    }

    public ArrayList<HrEmployees> searchEmpByName(String hrEmployee) {
        ArrayList<HrEmployees> employe = null;
        hrEmployees.setFirstName(hrEmployee);
        employe = hrEmployeeBean.SearchByFname(hrEmployees);
        return employe;
    }

    public void getByEmpName(SelectEvent event) {
        try {
            hrEmployees = (HrEmployees) event.getObject();
//            hrEmployees.setFirstName(event.getObject().toString());
//            hrEmployees = hrEmployeeBean.getByFirstName(hrEmployees);
            hrEvaluationResult.setEmpId(hrEmployees);
            hrJobTypes = hrEmployees.getJobId();
        } catch (Exception ex) {
            ex.printStackTrace();
            JsfUtil.addFatalMessage("Can:t get value");
        }
    }

    public ArrayList<HrEmployees> searchEmpByID(String hrEmployee) {
        ArrayList<HrEmployees> employe = null;
        hrEmployees.setEmpId(hrEmployee);
        employe = hrEmployeeBean.SearchByEmpId(hrEmployees);
        return employe;
    }

    public void getEmpByID(SelectEvent event) {
        try {
            if (event.getObject() != null) {
                hrEmployees = (HrEmployees) event.getObject();
                hrEvaluationResult.setEmpId(hrEmployees);
                hrJobTypes = hrEmployees.getJobId();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JsfUtil.addFatalMessage("Can't get value");
        }
    }

    public ArrayList<HrEmployees> searchEvaluatorName(String hrEmp) {
        ArrayList<HrEmployees> evaluatorEmp = null;
        evalutor.setFirstName(hrEmp);
        evaluatorEmp = hrEmployeeBean.SearchByFname(evalutor);
        return evaluatorEmp;
    }

    public void getEvaluatorName(SelectEvent event) {
        try {
            evalutor.setFirstName(event.getObject().toString());
            evalutor = hrEmployeeBean.getByFirstName(evalutor);
            hrEvaluationResult.setEvaluatorId(evalutor);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }

    public void addResultDetail() {
        if (hrEvaluationCriteria != null) {
            hrEvaluationResultDetail.setEvaluationCriteriaId(hrEvaluationCriteria);
        }
        if (checkEvaluation.contains(hrEvaluationResultDetail.getEvaluationCriteriaId().getCriteriaName())) {
            JsfUtil.addFatalMessage("Duplicate Criteria Name!. Try again.");
        } else if (hrEvaluationResultDetail.getEvaluationResult() > 100) {
            JsfUtil.addFatalMessage("Result can not be greater than 100! Try again.");
        } else if (hrEvaluationResultDetail.getEvaluationResult() < 0) {
            JsfUtil.addFatalMessage("Result can not be negative! Try again.");
        } else {
            hrEvaluationResult.addEvaResultDetail(hrEvaluationResultDetail);
            checkEvaluation.add(hrEvaluationResultDetail.getEvaluationCriteriaId().getCriteriaName());
            calculatingTotal();
            checkLevelName();
            checkLevelForTotal();
            recreateDataModel();
            clearResultDetail();
        }
    }

    double weight;
    double total;
    String level;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void calculatingTotal() {
        total = 0.0;
        weight = hrEvaluationResultDetail.getEvaluationCriteriaId().getWeight();
        int size = hrEvaluationResult.getHrEvaluationResultDetailsList().size();
        for (int i = 0; i < size; i++) {
            hrEvaluationResultDetail = hrEvaluationResult.getHrEvaluationResultDetailsList().get(i);
        }
        hrEvaluationResultDetail.setEvaluationValue((hrEvaluationResultDetail.getEvaluationResult()) * weight / 100);
        for (HrEvaluationResultDetails hrEvaluationResultDetailsList : hrEvaluationResult.getHrEvaluationResultDetailsList()) {
            total += hrEvaluationResultDetailsList.getEvaluationValue();
        }
    }

    public void checkLevelName() {
        levelList = new ArrayList<>();
        levelList = evaluationResultBeanLocal.findAllLevel();
        for (int i = 0; i <= levelList.size(); i++) {
            if ((hrEvaluationResultDetail.getEvaluationResult() >= levelList.get(i).getMinimumPoint())
                    && (hrEvaluationResultDetail.getEvaluationResult() <= levelList.get(i).getMaximumPoint())) {
                hrEvaluationResultDetail.setEvaluationLevel(levelList.get(i).getEvaluationLevel());
                break;
            } else {
                hrEvaluationResultDetail.setEvaluationLevel("Out of criteria");
            }
        }
    }

    public void checkLevelForTotal() {
        levelList = new ArrayList<>();
        levelList = evaluationResultBeanLocal.findAllLevel();
        for (int i = 0; i <= levelList.size(); i++) {
            if ((total >= levelList.get(i).getMinimumPoint()) && (total <= levelList.get(i).getMaximumPoint())) {
                level = levelList.get(i).getEvaluationLevel();
                break;
            } else {
                level = "Out of criteria";
            }
        }
    }

    public void evaluationLevel() {
        if ((hrEvaluationResultDetail.getEvaluationResult() >= 80) && (hrEvaluationResultDetail.getEvaluationResult() <= 100)) {
            hrEvaluationResultDetail.setEvaluationLevel("Excellent");
        } else if ((hrEvaluationResultDetail.getEvaluationResult() >= 60) && (hrEvaluationResultDetail.getEvaluationResult() < 80)) {
            hrEvaluationResultDetail.setEvaluationLevel("Very Good");
        } else if ((hrEvaluationResultDetail.getEvaluationResult() >= 40) && (hrEvaluationResultDetail.getEvaluationResult() < 60)) {
            hrEvaluationResultDetail.setEvaluationLevel("Good");
        } else if ((hrEvaluationResultDetail.getEvaluationResult() >= 20) && (hrEvaluationResultDetail.getEvaluationResult() < 40)) {
            hrEvaluationResultDetail.setEvaluationLevel("Fair");
        } else if ((hrEvaluationResultDetail.getEvaluationResult() < 20) && (hrEvaluationResultDetail.getEvaluationResult() >= 0)) {
            hrEvaluationResultDetail.setEvaluationLevel("Requires Improvment");
        }
    }

    public void recreateDataModel() {
        resultDetailDataModel = null;
        resultDetailDataModel = new ListDataModel(new ArrayList(hrEvaluationResult.getHrEvaluationResultDetailsList()));
    }

    public void clearResultDetail() {
        hrEvaluationResultDetail = null;
        hrEvaluationCriteria = null;
        hrEvaluationLevels = null;
    }

    public void editDataTable() {
        hrEvaluationResultDetail = resultDetailDataModel.getRowData();
        addorUpdate = "Update";
//        }
        hrEvaluationCriteria = hrEvaluationResultDetail.getEvaluationCriteriaId();
    }

    public void displayResult() {
        if (srcEmployees.getEmpId().isEmpty() && srcEmployees.getFirstName().isEmpty()) {
            resultList = evaluationResultBeanLocal.findAllResult();
            evaluationResultDataModel = new ListDataModel(resultList);
        } else if (srcEmployees.getEmpId() != null && srcEmployees.getFirstName().isEmpty()) {
            resultList = evaluationResultBeanLocal.findByEmpId(srcEmployees);
            evaluationResultDataModel = new ListDataModel(resultList);
        } else if (srcEmployees.getEmpId().isEmpty() && srcEmployees.getFirstName() != null) {
            resultList = evaluationResultBeanLocal.findByName(srcEmployees);
            evaluationResultDataModel = new ListDataModel(resultList);
        } else if (srcEmployees.getEmpId() != null && srcEmployees.getFirstName() != null) {
            resultList = evaluationResultBeanLocal.findByTwo(srcEmployees, srcEmployees);
            evaluationResultDataModel = new ListDataModel(resultList);
        }
    }

    public void resultInfo() {
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
                setIcone("ui-icon-plus");
                break;
        }
    }

    public void newButtonAction() {
        clearPage();
        newPage = true;
        searchPage = false;
        btnNewRender = false;
    }

    public void calculatingTotalValue() {
        total = 0.0;
        for (HrEvaluationResultDetails hrEvaluationResultDetailsList : hrEvaluationResult.getHrEvaluationResultDetailsList()) {
            total += hrEvaluationResultDetailsList.getEvaluationValue();
        }
    }

    public void populate(SelectEvent event) {
        resultDetailDataModel = null;
        hrEvaluationResult = (HrEvaluationResults) event.getObject();
        setHrEmployees(hrEvaluationResult.getEmpId());
        hrEvaluationSessions = hrEvaluationResult.getSessionId();
        preparedDate = hrEvaluationResult.getEvaluationDate();
        calculatingTotalValue();
        checkLevelForTotal();
        newPage = true;
        searchPage = false;
        btnNewRender = true;
        newOrSearch = "Search";
        updateStatus = 1;
        saveorUpdateBundle = "Update";
        setIcone("ui-icon-search");
        recreateDataModel();
    }

}
