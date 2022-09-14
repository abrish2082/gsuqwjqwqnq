/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.displines;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.model.ArrayDataModel;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import securityBean.Constants;
import securityBean.WorkFlow;
import webService.UserStatus;
import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.controller.DataUpload;
import et.gov.eep.commonApplications.entity.HrLuDmArchive;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.displine.DisplineRequestBeanLocal;
import et.gov.eep.hrms.businessLogic.displine.HrDisciplineOffencePenalityBeanLocal;
import et.gov.eep.hrms.businessLogic.displine.HrDisciplineOffenceTypesBeanLocal;
import et.gov.eep.hrms.businessLogic.displine.HrDisciplinePenlitysBeansLocal;
import et.gov.eep.hrms.businessLogic.displine.HrEmpDisciplinesBeanLocal;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.entity.displine.HrDisciplineFileUpload;
import et.gov.eep.hrms.entity.displine.HrDisciplineOffencePenality;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.displine.HrDisciplineOffenceTypes;
import et.gov.eep.hrms.entity.displine.HrDisciplinePenaltyTypes;
import et.gov.eep.hrms.entity.displine.HrDisciplinePenlitys;
import et.gov.eep.hrms.entity.displine.HrDisciplineRequests;
import et.gov.eep.hrms.entity.displine.HrEmpDisciplines;
import et.gov.eep.hrms.integration.HREmployeesBeanLocal;
import et.gov.eep.hrms.mapper.displine.HrDisciplineOffencePenalityFacade;
import et.gov.eep.hrms.mapper.displine.HrDisciplineRequestsFacade;
import et.gov.eep.hrms.mapper.displine.HrEmpDisciplinesFacade;

/**
 *
 * @author user
 */
@Named(value = "disciplineApproveControllers")
@ViewScoped
public class DisciplineApproveController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Entity, bussiness logic and variables">
    @EJB
    WfHrProcessedBeanLocal wfHrProcessedBeanLocal;
    @EJB
    private HREmployeesBeanLocal hrEmployeeBean;
    @EJB
    DisplineRequestBeanLocal requestBeanLocal;
    @EJB
    HrDisciplineOffencePenalityBeanLocal hrDisciplineOffencePenalityBeanLocal;
    @Inject
    HrDisciplineRequests disciplineRequests;
    @EJB
    HrDisciplineOffenceTypesBeanLocal offenceTypesBeanLocal;
    @EJB
    HrDisciplinePenlitysBeansLocal disciplinePenlitysBeansLocal;
    @EJB
    HrDisciplineOffencePenalityFacade disciplineOffencePenaltyFacade;
    @EJB
    HrEmpDisciplinesBeanLocal hrEmpDisciplinesBeanLocal;
    @EJB
    HrEmployeeBeanLocal hrEmployeeBeanLocal;
    @EJB
    HrDisciplineRequestsFacade disciplineRequestsFacade;

    @EJB
    HrEmpDisciplinesFacade hrEmpDisciplinesFacade;

    @Inject
    WfHrProcessed wfHrProcessed;
    @Inject
    HrDisciplinePenlitys disciplinePenlitys;
    @Inject
    HrDisciplineOffenceTypes offenceTypes;
    @Inject
    HrEmployees hrEmployeesAccuser, hrEmployee;
    @Inject
    HrEmployees hrEmployeesRequester;
    @Inject
    HrDisciplinePenaltyTypes hrDisciplinePenaltyTypes;
    @Inject
    HrDisciplineOffencePenality hrDisciplineOffencePenality;
    @Inject
    HrDepartments departmentsOfAccuser, departmentsOfAccused;
    @Inject
    HrDisciplineFileUpload hrDisciplineFileUpload;
    @Inject
    HrJobTypes hrJobTypesOfAccuser, hrJobTypesOfAccused;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    WorkFlow workFlow;
    @Inject
    HrEmpDisciplines hrEmpDisciplines;
    @Inject
    HrLuDmArchive hrLuDmArchive;
    @Inject
    DataUpload dataUpload;

    private HrDisciplineRequests selectedRow;
    DataModel<HrDisciplineRequests> disciplineRequestDataModel = new ListDataModel<>();
    DataModel<WfHrProcessed> hrWorkFlowDataModel = new ListDataModel<>();
    DataModel<HrEmpDisciplines> hrDisciplineHistoryModel = new ListDataModel<>();

    List<HrDisciplineOffencePenality> penalityForoffenceList;
    List<HrDisciplinePenaltyTypes> penalityLists;
    List<HrDisciplineRequests> hrDisciplineRequestsList;
    List<HrDisciplineOffencePenality> ForoffenceList;
    private List<HrDisciplineRequests> disciplineApprovedList;
    List<HrEmpDisciplines> ListOfDisciplineHistory = new ArrayList();

    private String saveorUpdateBundle = "Create";
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private String saveOrUpdateButton = "Save";
    private String fullName;
    private String slectedC = "Accept";

    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    private boolean disableBtnCreate;
    private boolean renderPnlApproved = false;
    private boolean checkBoxReqst = false;
    private boolean checkBoxApprove = false;
    private boolean pnlAccept = true;
    private boolean pnlOther = false;
    private boolean disabledCrud = false;
    private boolean isFileSelected = false;
    UserStatus userStatus = new UserStatus();
    List<HrEmpDisciplines> deciplinePhaseOutList = new ArrayList<>();
    List<HrEmpDisciplines> phaseOutList = new ArrayList<>();

    int update = 0;
    private String penaltyClassification;
    List<HrDisciplineRequests> requestList = new ArrayList<>();
    String approveddate = "";
    String preparedDate = "";
//</editor-fold>

    @PostConstruct
    public void init() {
//        seachByOffenceCode();
        wfHrProcessed.setProcessedOn(dateFormat(StringDateManipulation.toDayInEc()));
        approveddate = dateFormat(StringDateManipulation.toDayInEc());
        requestList = requestBeanLocal.findRequestForApproval();
//        phaseOutList = requestBeanLocal.findPhaseOutList();
        phaseOutList = hrEmpDisciplinesBeanLocal.findPhaseOutList();
        PhaseOut();

    }
    // <editor-fold defaultstate="collapsed" desc="Getters setters"> 

    public String getApproveddate() {
        return approveddate;
    }

    public void setApproveddate(String approveddate) {
        this.approveddate = approveddate;
    }

    public String getPreparedDate() {
        return preparedDate;
    }

    public void setPreparedDate(String preparedDate) {
        this.preparedDate = preparedDate;
    }

    public List<HrEmpDisciplines> getListOfDisciplineHistory() {
        if (ListOfDisciplineHistory == null) {
            ListOfDisciplineHistory = new ArrayList<>();
        }
        return ListOfDisciplineHistory;
    }

    public void setListOfDisciplineHistory(List<HrEmpDisciplines> ListOfDisciplineHistory) {
        this.ListOfDisciplineHistory = ListOfDisciplineHistory;
    }

    public List<HrEmpDisciplines> getDeciplinePhaseOutList() {
        return deciplinePhaseOutList;
    }

    public void setDeciplinePhaseOutList(List<HrEmpDisciplines> deciplinePhaseOutList) {
        this.deciplinePhaseOutList = deciplinePhaseOutList;
    }

    public boolean isDisabledCrud() {
        return disabledCrud;
    }

    public void setDisabledCrud(boolean disabledCrud) {
        this.disabledCrud = disabledCrud;
    }

    public HrEmpDisciplines getHrEmpDisciplines() {
        if (hrEmpDisciplines == null) {
            hrEmpDisciplines = new HrEmpDisciplines();
        }
        return hrEmpDisciplines;
    }

    public void setHrEmpDisciplines(HrEmpDisciplines hrEmpDisciplines) {
        this.hrEmpDisciplines = hrEmpDisciplines;
    }

    public DataModel<HrEmpDisciplines> getHrDisciplineHistoryModel() {
        if (hrDisciplineHistoryModel == null) {
            hrDisciplineHistoryModel = new ArrayDataModel<>();
        }
        return hrDisciplineHistoryModel;
    }

    public void setHrDisciplineHistoryModel(DataModel<HrEmpDisciplines> hrDisciplineHistoryModel) {
        this.hrDisciplineHistoryModel = hrDisciplineHistoryModel;
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

    public List<HrDisciplineRequests> getRequestList() {
        if (requestList == null) {
            requestList = new ArrayList<>();
        }
        return requestList;
    }

    public void setRequestList(List<HrDisciplineRequests> requestList) {
        this.requestList = requestList;
    }

    public DataModel<WfHrProcessed> getHrWorkFlowDataModel() {
        return hrWorkFlowDataModel;
    }

    public void setHrWorkFlowDataModel(DataModel<WfHrProcessed> hrWorkFlowDataModel) {
        this.hrWorkFlowDataModel = hrWorkFlowDataModel;
    }

    public List<HrDisciplineOffencePenality> getForoffenceList() {
        return ForoffenceList;
    }

    public void setForoffenceList(List<HrDisciplineOffencePenality> ForoffenceList) {
        this.ForoffenceList = ForoffenceList;
    }

    public boolean isPnlAccept() {
        return pnlAccept;
    }

    public void setPnlAccept(boolean pnlAccept) {
        this.pnlAccept = pnlAccept;
    }

    public boolean isPnlOther() {
        return pnlOther;
    }

    public void setPnlOther(boolean pnlOther) {
        this.pnlOther = pnlOther;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSlectedC() {
        return slectedC;
    }

    public void setSlectedC(String slectedC) {
        this.slectedC = slectedC;
    }

    public boolean isCheckBoxReqst() {
        return checkBoxReqst;
    }

    public void setCheckBoxReqst(boolean checkBoxReqst) {
        this.checkBoxReqst = checkBoxReqst;
    }

    public boolean isCheckBoxApprove() {
        return checkBoxApprove;
    }

    public void setCheckBoxApprove(boolean checkBoxApprove) {
        this.checkBoxApprove = checkBoxApprove;
    }

    public boolean isRenderPnlApproved() {
        return renderPnlApproved;
    }

    public void setRenderPnlApproved(boolean renderPnlApproved) {
        this.renderPnlApproved = renderPnlApproved;
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

    public String getSaveOrUpdateButton() {
        return saveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String saveOrUpdateButton) {
        this.saveOrUpdateButton = saveOrUpdateButton;
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

    public HrDisciplineRequests getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(HrDisciplineRequests selectedRow) {
        this.selectedRow = selectedRow;
    }

    public HrEmployees getHrEmployeesAccuser() {
        if (hrEmployeesAccuser == null) {
            hrEmployeesAccuser = new HrEmployees();
        }
        return hrEmployeesAccuser;
    }

    public void setHrEmployees(HrEmployees hrEmployeesAccuser) {
        this.hrEmployeesAccuser = hrEmployeesAccuser;
    }

    public HrEmployees getHrEmployee() {
        if (hrEmployee == null) {
            hrEmployee = new HrEmployees();
        }
        return hrEmployee;
    }

    public void setHrEmployee(HrEmployees hrEmployee) {
        this.hrEmployee = hrEmployee;
    }

    public DataModel<HrDisciplineRequests> getDisciplineRequestDataModel() {
        return disciplineRequestDataModel;
    }

    public void setDisciplineRequestDataModel(DataModel<HrDisciplineRequests> disciplineRequestDataModel) {
        this.disciplineRequestDataModel = disciplineRequestDataModel;
    }

    public HrEmployees getHrEmployeesRequester() {
        if (hrEmployeesRequester == null) {
            hrEmployeesRequester = new HrEmployees();
        }
        return hrEmployeesRequester;
    }

    public void setHrEmployeesRequester(HrEmployees hrEmployeesRequester) {
        this.hrEmployeesRequester = hrEmployeesRequester;
    }

    public HrJobTypes getHrJobTypes() {
        if (hrJobTypesOfAccuser == null) {
            hrJobTypesOfAccuser = new HrJobTypes();
        }
        return hrJobTypesOfAccuser;
    }

    public void setHrJobTypes(HrJobTypes hrJobTypesOfAccuser) {
        this.hrJobTypesOfAccuser = hrJobTypesOfAccuser;
    }

    public HrJobTypes getHrJobTypesOfAccused() {
        if (hrJobTypesOfAccused == null) {
            hrJobTypesOfAccused = new HrJobTypes();
        }
        return hrJobTypesOfAccused;
    }

    public void setHrJobTypesOfAccused(HrJobTypes hrJobTypesOfAccused) {
        this.hrJobTypesOfAccused = hrJobTypesOfAccused;
    }

    public HrDepartments getDepartments() {
        if (departmentsOfAccuser == null) {
            departmentsOfAccuser = new HrDepartments();
        }
        return departmentsOfAccuser;
    }

    public void setDepartments(HrDepartments departmentsOfAccuser) {
        this.departmentsOfAccuser = departmentsOfAccuser;
    }

    public HrDepartments getDepartmentsOfAccused() {
        if (departmentsOfAccused == null) {
            departmentsOfAccused = new HrDepartments();
        }
        return departmentsOfAccused;
    }

    public void setDepartmentsOfAccused(HrDepartments departmentsOfAccused) {
        this.departmentsOfAccused = departmentsOfAccused;
    }

    public HrDisciplineRequests getDisciplineRequests() {
        if (disciplineRequests == null) {
            disciplineRequests = new HrDisciplineRequests();
        }
        return disciplineRequests;
    }

    public void setDisciplineRequests(HrDisciplineRequests disciplineRequests) {
        this.disciplineRequests = disciplineRequests;
    }

    public HrDisciplineOffenceTypes getOffenceTypes() {
        if (offenceTypes == null) {
            offenceTypes = new HrDisciplineOffenceTypes();
        }
        return offenceTypes;
    }

    public void setOffenceTypes(HrDisciplineOffenceTypes offenceTypes) {
        this.offenceTypes = offenceTypes;
    }

    public HrDisciplinePenlitys getDisciplinePenlitys() {
        if (disciplinePenlitys == null) {
            disciplinePenlitys = new HrDisciplinePenlitys();
        }
        return disciplinePenlitys;
    }

    public void setDisciplinePenlitys(HrDisciplinePenlitys disciplinePenlitys) {
        this.disciplinePenlitys = disciplinePenlitys;
    }

    public HrDisciplineFileUpload getHrDisciplineFileUpload() {
        if (hrDisciplineFileUpload == null) {
            hrDisciplineFileUpload = new HrDisciplineFileUpload();
        }
        return hrDisciplineFileUpload;
    }

    public void setHrDisciplineFileUpload(HrDisciplineFileUpload hrDisciplineFileUpload) {
        this.hrDisciplineFileUpload = hrDisciplineFileUpload;
    }

    List<HrDisciplineOffenceTypes> offenceTypesesList;

    public List<HrDisciplineOffenceTypes> getOffenceTypesesList() {
        return offenceTypesesList;
    }

    public void setOffenceTypesesList(List<HrDisciplineOffenceTypes> offenceTypesesList) {
        this.offenceTypesesList = offenceTypesesList;
    }
    List<HrDisciplinePenlitys> disciplinePenlityses;

    public List<HrDisciplinePenlitys> getDisciplinePenlityses() {
        return disciplinePenlityses;
    }

    public void setDisciplinePenlityses(List<HrDisciplinePenlitys> disciplinePenlityses) {
        this.disciplinePenlityses = disciplinePenlityses;
    }

    public void offenceLevel(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            offenceTypes = new HrDisciplineOffenceTypes();
            offenceTypes = (HrDisciplineOffenceTypes) event.getNewValue();
            disciplinePenlityses = disciplinePenlitysBeansLocal.findByOffenceId(offenceTypes);
        }
    }

    public HrDisciplinePenaltyTypes getHrDisciplinePenaltyTypes() {
        if (hrDisciplinePenaltyTypes == null) {
            hrDisciplinePenaltyTypes = new HrDisciplinePenaltyTypes();
        }
        return hrDisciplinePenaltyTypes;
    }

    public void setHrDisciplinePenaltyTypes(HrDisciplinePenaltyTypes hrDisciplinePenaltyTypes) {
        this.hrDisciplinePenaltyTypes = hrDisciplinePenaltyTypes;
    }

    public HrDisciplineOffencePenality getHrDisciplineOffencePenality() {
        return hrDisciplineOffencePenality;
    }

    public void setHrDisciplineOffencePenality(HrDisciplineOffencePenality hrDisciplineOffencePenality) {
        this.hrDisciplineOffencePenality = hrDisciplineOffencePenality;
    }

    public List<HrDisciplinePenaltyTypes> getPenalityLists() {
        return penalityLists;
    }

    public void setPenalityLists(List<HrDisciplinePenaltyTypes> penalityLists) {
        this.penalityLists = penalityLists;
    }

    public List<HrDisciplineOffencePenality> getPenalityForoffenceList() {
        if (penalityForoffenceList == null) {
            penalityForoffenceList = new ArrayList<>();
        }
        return penalityForoffenceList;
    }

    public void setPenalityForoffenceList(List<HrDisciplineOffencePenality> penalityForoffenceList) {
        this.penalityForoffenceList = penalityForoffenceList;
    }

    public List<HrDisciplineRequests> getHrDisciplineRequestsList() {
        return hrDisciplineRequestsList;
    }

    public void setHrDisciplineRequestsList(List<HrDisciplineRequests> hrDisciplineRequestsList) {
        this.hrDisciplineRequestsList = hrDisciplineRequestsList;
    }

    public List<HrDisciplineRequests> getDisciplineApprovedList() {
        if (disciplineApprovedList == null) {
            disciplineApprovedList = new ArrayList<>();
        }
        return disciplineApprovedList;
    }

    public void setDisciplineApprovedList(List<HrDisciplineRequests> disciplineApprovedList) {
        this.disciplineApprovedList = disciplineApprovedList;
    }

    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Search">
    public void createNewAdditionalAmount() {
        saveorUpdateBundle = "Create";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlApproved = true;
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    int status = 0;

    public List<SelectItem> getFilterByStatusForApprove() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Select ---"));
        selectItems.add(new SelectItem(String.valueOf("0"), "Load Request List"));
        selectItems.add(new SelectItem(String.valueOf("1"), "Load Approved List"));
        selectItems.add(new SelectItem(String.valueOf("2"), "Load Reject List"));
        selectItems.add(new SelectItem(String.valueOf("3"), "Load All List"));
        return selectItems;
    }

    private void populateTable() {
        try {
            disciplineApprovedList = requestBeanLocal.loadPenalityRequest(status);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void valueChangeListnerDisciplineApprove(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            status = (Integer.parseInt(event.getNewValue().toString()));
            populateTable();
        }
    }

    public void populate(SelectEvent events) {
        disciplineRequests = (HrDisciplineRequests) events.getObject();
        populateDisciplineReq();
    }

    public void populateDisciplineReq() {
        if (disciplineRequests.getStatus().equalsIgnoreCase(String.valueOf(Constants.APPROVE_VALUE)) || disciplineRequests.getStatus().equalsIgnoreCase(String.valueOf(Constants.CHECK_APPROVE_VALUE))) {
            approveddate = disciplineRequests.getApprovedOn();
        } else {
            getApproveddate();
        }
        preparedDate = disciplineRequests.getPreparedOn();
        hrEmployeesRequester = disciplineRequests.getRequesterId();
        departmentsOfAccused = hrEmployeesRequester.getDeptId();
        hrJobTypesOfAccused = hrEmployeesRequester.getJobId();
        hrEmployeesAccuser = disciplineRequests.getOffenderId();
        departmentsOfAccuser = hrEmployeesAccuser.getDeptId();
        hrJobTypesOfAccuser = hrEmployeesAccuser.getJobId();
        offenceTypes = disciplineRequests.getOffenceTypeId();
        hrDisciplineOffencePenality.setOffenceTypeId(offenceTypes);
        hrDisciplineOffencePenality.setRepetition(BigInteger.valueOf(disciplineRequests.getRepititionOfOffence()));
        hrDisciplineOffencePenality = hrDisciplineOffencePenalityBeanLocal.FindByOffenceTypeAndRepition(hrDisciplineOffencePenality);
        hrDisciplinePenaltyTypes = hrDisciplineOffencePenality.getPenaltyId();
        penaltyClassification = hrDisciplineOffencePenality.getPenaltyId().getPenaltyClassification();
        recreateDmsDataModel();
        recreatWorkFlowDataModel();
        phaseOutTimeCalculation();
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        createOrSearchBundle = "Search";
        icone = "ui-icon-search";
    }

    public void populateNotifcation(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                disciplineRequests = (HrDisciplineRequests) event.getNewValue();
                populateDisciplineReq();
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('widNotf').hide();");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Faile to process! Try Again");
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

    public void PhaseOut() {
        String currntDate[] = dateFormat(StringDateManipulation.toDayInEc()).split("/");
        int currentDay = Integer.parseInt(currntDate[0]);
        int currentMonth = Integer.parseInt(currntDate[1]);
        int currentYear = Integer.parseInt(currntDate[2]);
        for (int j = 0; j < phaseOutList.size(); j++) {
            String dicissionDate[] = phaseOutList.get(j).getDecisionDate().split("/");
            int dicissionDay = Integer.parseInt(dicissionDate[0]);
            int dicissionMonth = Integer.parseInt(dicissionDate[1]);
            int dicissionYear = Integer.parseInt(dicissionDate[2]);

            int phaseOut = (currentYear - dicissionYear) * 365 + (currentMonth - dicissionMonth) * 30 + (currentDay - dicissionDay);
            String phaseOutTime = phaseOutList.get(j).getRequestId().getOffenceTypeId().getPhaseoutPeriod();
            int phaseOutPeriod = Integer.parseInt(phaseOutTime) * 30;
            int difference = phaseOutPeriod - phaseOut;
            if (difference < 0) {
                deciplinePhaseOutList.add(phaseOutList.get(j));

            }
        }
    }

    public void populateNotifcationPhaseout(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                disciplineRequests = (HrDisciplineRequests) event.getNewValue();
                hrEmpDisciplines.setStatus(1);
                saveOrUpdateButton = "Update";
                populateDisciplineReq();
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('widNotfPhaseOut').hide();");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Faile to process! Try Again");
        }
    }

//</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Main">
    public void penalityDetails(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            disciplinePenlitys = null;
            disciplinePenlitys = (HrDisciplinePenlitys) event.getNewValue();
        }
    }

    public void seachByOffenceCode() {
        offenceTypesesList = offenceTypesBeanLocal.findByOffenceCode();
    }

    public void offenceIdChanged(ValueChangeEvent event) {
        offenceTypes = offenceTypesBeanLocal.displayByOffenceCode(event.getNewValue().toString());
        disciplineRequests.setOffenceTypeId(offenceTypes);
    }

    public ArrayList<HrEmployees> SearchByFname(String employee) {
        ArrayList<HrEmployees> employees = null;
        hrEmployeesAccuser.setFirstName(employee);
        employees = hrEmployeeBean.searchEmployeeByName(hrEmployeesAccuser);
        return employees;
    }

    public void getByFirstName(SelectEvent event) {
        hrEmployeesAccuser.setFirstName((event.getObject().toString()));
        hrEmployeesAccuser = hrEmployeeBean.getByFirstName(hrEmployeesAccuser);
        departmentsOfAccuser = hrEmployeesAccuser.getDeptId();
        hrJobTypesOfAccuser = hrEmployeesAccuser.getJobId();
        disciplineRequests.setOffenderId(hrEmployeesAccuser);
    }

    public ArrayList<HrEmployees> SearchByFnameAccused(String employee) {
        ArrayList<HrEmployees> employees = null;
        hrEmployeesRequester.setFirstName(employee);
        employees = hrEmployeeBean.searchEmployeeByName(hrEmployeesRequester);
        return employees;
    }

    public void getByFirstNameAccused(SelectEvent event) {
        hrEmployeesRequester.setFirstName((event.getObject().toString()));
        hrEmployeesRequester = hrEmployeeBean.getByFirstName(hrEmployeesRequester);
        departmentsOfAccused = hrEmployeesRequester.getDeptId();
        hrJobTypesOfAccused = hrEmployeesRequester.getJobId();
        disciplineRequests.setRequesterId(hrEmployeesRequester);
    }

    public DataModel<HrDisciplineRequests> findListByFname() {
        if (hrEmployeesRequester.getFirstName() != null) {
            disciplineRequestDataModel = new ListDataModel(new ArrayList(requestBeanLocal.searchEmployeeByName(hrEmployeesRequester)));
        }
        return null;
    }
    Integer phaseOutTime = 0;

    public Integer getPhaseOutTime() {
        return phaseOutTime;
    }

    public void setPhaseOutTime(Integer phaseOutTime) {
        this.phaseOutTime = phaseOutTime;
    }

    public void phaseOutTimeCalculation() {
        disciplineRequests.setOffenceDate(disciplineRequests.getOffenceDate());
        HrDisciplineRequests disciplineReq = new HrDisciplineRequests();
        disciplineRequests.setOffenceTypeId(disciplineRequests.getOffenceTypeId());
        disciplineRequests.setOffenderId(hrEmployeesAccuser);
        disciplineReq = requestBeanLocal.findDisciplineReqByOffenderIdAndOffenceType(disciplineRequests);
        if (disciplineReq != null) {
            String offenceDate[] = disciplineReq.getOffenceDate().split("/");
            int offenceDay = Integer.parseInt(offenceDate[0]);
            int offenceMonth = Integer.parseInt(offenceDate[1]);
            int offenceYear = Integer.parseInt(offenceDate[2]);
            String currentDate[] = dateFormat(StringDateManipulation.toDayInEc()).split("/");
            int currentDay = Integer.parseInt(currentDate[0]);
            int currentMonth = Integer.parseInt(currentDate[1]);
            int currentYear = Integer.parseInt(currentDate[2]);
            int dayBetween = (currentDay - offenceDay) + ((currentMonth - offenceMonth) * 30) + ((currentYear - offenceYear) * 360);
            int monthBetween = dayBetween / 30;
//            int newMonthDifferece = Integer.parseInt(offenceTypes.getPhaseoutPeriod()) - monthBetween;
            int newDayDifference = Integer.parseInt(offenceTypes.getPhaseoutPeriod()) * 30 - dayBetween;
            if (newDayDifference > 0) {
                phaseOutTime = Integer.parseInt(offenceTypes.getPhaseoutPeriod()) + monthBetween;
            } else if (newDayDifference < 0) {
                phaseOutTime = Integer.parseInt(offenceTypes.getPhaseoutPeriod());
                disabledCrud = true;
                JsfUtil.addFatalMessage("Phase out period is reched");
            }
        } else {
            phaseOutTime = Integer.parseInt(offenceTypes.getPhaseoutPeriod());
        }
    }

    public void handleDecition(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            slectedC = event.getNewValue().toString();
            if (slectedC.equalsIgnoreCase("Accept")) {
                pnlAccept = true;
                pnlOther = false;
            } else {
                pnlAccept = false;
                pnlOther = true;
            }
        }
    }

    public void saveDisciplineApprove() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveDisciplineApprove", dataset)) {
                if (disciplineRequests.getId() == null) {
                    JsfUtil.addFatalMessage(" Empty Information can't Approved");
                } else {
                    if (slectedC.equalsIgnoreCase("Accept")) {
                        disciplineRequests.setDecisionOnPenality(hrDisciplinePenaltyTypes.getPenaltyName());
                        saveWorkFlowInformation();
                        JsfUtil.addSuccessMessage("Successfully Save.");
                        reset();
                    } else {
                        saveWorkFlowInformation();
                        JsfUtil.addSuccessMessage("Successfully Save.");
                        reset();
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveDisciplineApprove");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void reset() {
        disciplineRequests = new HrDisciplineRequests();
        hrEmployeesAccuser = new HrEmployees();
        hrEmployeesRequester = new HrEmployees();
        offenceTypes = new HrDisciplineOffenceTypes();
        hrJobTypesOfAccuser = new HrJobTypes();
        hrJobTypesOfAccused = new HrJobTypes();
        departmentsOfAccused = new HrDepartments();
        departmentsOfAccuser = new HrDepartments();
        hrDisciplineOffencePenality = new HrDisciplineOffencePenality();
        hrDisciplinePenaltyTypes = new HrDisciplinePenaltyTypes();
        setPhaseOutTime(null);
        fileUploadDataModel = null;
        setFullName(null);
        wfHrProcessed.setCommentGiven(null);
        workFlow = new WorkFlow();
        saveOrUpdateButton = "Save";
        update = 0;

    }
    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="DMS service ">
    StreamedContent file;
    DataModel<HrDisciplineFileUpload> fileUploadDataModel;

    public DataModel<HrDisciplineFileUpload> getFileUploadDataModel() {
        return fileUploadDataModel;
    }

    public void setFileUploadDataModel(DataModel<HrDisciplineFileUpload> fileUploadDataModel) {
        this.fileUploadDataModel = fileUploadDataModel;
    }

    public void recreateDmsDataModel() {
        fileUploadDataModel = null;
        fileUploadDataModel = new ListDataModel<>(disciplineRequests.getHrDisciplineFileUploadList());
    }

    public void onRowSelected(SelectEvent event) {
        hrDisciplineFileUpload = (HrDisciplineFileUpload) event.getObject();
        hrLuDmArchive = hrDisciplineFileUpload.getDocumentId();
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
        hrDisciplineFileUpload.setId(hrDisciplineFileUpload.getId());
        requestBeanLocal.remove(hrDisciplineFileUpload);
        disciplineRequests.getHrDisciplineFileUploadList().remove(hrDisciplineFileUpload);
        recreateDmsDataModel();
        JsfUtil.addSuccessMessage("Document Seccesfully Deleted!!! ");

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Work Flow ">
    String selectedValue = "";

    public void handleSelectOptions(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selectedValue = event.getNewValue().toString();
        }
    }

    public void recreatWorkFlowDataModel() {
        hrWorkFlowDataModel = null;
        for (int i = 0; i < disciplineRequests.getHrWorkFlowProccedList().size(); i++) {
            if (disciplineRequests.getHrWorkFlowProccedList().get(i).getDecision() == 0) {
                disciplineRequests.getHrWorkFlowProccedList().get(i).setChangedStstus("Request");
            } else if (disciplineRequests.getHrWorkFlowProccedList().get(i).getDecision() == 1 || disciplineRequests.getHrWorkFlowProccedList().get(i).getDecision() == 3) {
                disciplineRequests.getHrWorkFlowProccedList().get(i).setChangedStstus("Approved");
            } else if (disciplineRequests.getHrWorkFlowProccedList().get(i).getDecision() == 2 || disciplineRequests.getHrWorkFlowProccedList().get(i).getDecision() == 4) {
                disciplineRequests.getHrWorkFlowProccedList().get(i).setChangedStstus("Rejected");
            }
        }
        hrWorkFlowDataModel = new ListDataModel(new ArrayList(disciplineRequests.getHrWorkFlowProccedList()));
    }

    public void saveWorkFlowInformation() {
        if (selectedValue.equalsIgnoreCase("1") && workFlow.isApproveStatus()) {
            workFlow.setUserStatus(Constants.APPROVE_VALUE);
            disciplineRequests.setStatus(String.valueOf(workFlow.getUserStatus()));
        } else if (selectedValue.equalsIgnoreCase("2") && workFlow.isApproveStatus()) {
            workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
            disciplineRequests.setStatus(String.valueOf(workFlow.getUserStatus()));
        } else if (selectedValue.equalsIgnoreCase("1") && workFlow.isCheckStatus()) {
            workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
            disciplineRequests.setStatus(String.valueOf(workFlow.getUserStatus()));
        } else if (selectedValue.equalsIgnoreCase("2") && workFlow.isCheckStatus()) {
            workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
            disciplineRequests.setStatus(String.valueOf(workFlow.getUserStatus()));
        }
        disciplineRequests.setApprovedOn(approveddate);
        requestBeanLocal.saveOrUpdate(disciplineRequests);
        saveEmployeeDescipline();
        wfHrProcessed.setDecision(Integer.valueOf(disciplineRequests.getStatus()));
        wfHrProcessed.setDisciplineId(disciplineRequests);
        wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
        wfHrProcessed.setProcessedOn(disciplineRequests.getApprovedOn());
        wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
    }

    public void saveEmployeeDescipline() {
        hrEmpDisciplines.setRequestId(disciplineRequests);
        hrEmpDisciplines.setDecisionDate(disciplineRequests.getApprovedOn());
        hrEmpDisciplines.setDecisionOnPenality(disciplineRequests.getDecisionOnPenality());
        hrEmpDisciplines.setDescriptionOfOffence(disciplineRequests.getDescriptionOfOffence());
        hrEmpDisciplines.setOffenceTypeId(disciplineRequests.getOffenceTypeId());
        hrEmpDisciplines.setRepititionOfOffence(BigInteger.valueOf(disciplineRequests.getRepititionOfOffence()));
        hrEmpDisciplines.setPenaltyClassfication(penaltyClassification);
        hrEmpDisciplines.setStatus(0);
        hrEmpDisciplinesBeanLocal.saveOrUpdate(hrEmpDisciplines);
    }

    public void updateEmployeeDescipline() {
        try {
            for (int i = 0; i < deciplinePhaseOutList.size(); i++) {
                hrEmpDisciplinesBeanLocal.edit(deciplinePhaseOutList.get(i));
            }
            hrEmpDisciplines = new HrEmpDisciplines();
            JsfUtil.addSuccessMessage("Successfully updated");
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Try again");
        }

    }
    String selectedStatusValue = "";

    public void handleSelectionEvent(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selectedStatusValue = event.getNewValue().toString();
        }
    }

    public void onRowSelect(SelectEvent event) {
        hrEmpDisciplines = ((HrEmpDisciplines) event.getObject());
        hrEmpDisciplines.setStatus(hrEmpDisciplines.getStatus());
        FacesMessage msg = new FacesMessage(hrEmpDisciplines.getId() + " is Selected");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Discipline History ">
    public ArrayList<HrEmployees> searchEmployeeById(String empId) {
        ArrayList<HrEmployees> employee = null;
        hrEmployee.setEmpId(empId);
        employee = hrEmployeeBeanLocal.SearchByEmpId(hrEmployee);
        return employee;
    }

    public void getById(SelectEvent event) {
        try {
            hrEmployee.setEmpId(event.getObject().toString());
            hrEmployee = hrEmployeeBeanLocal.getByEmpId(hrEmployee);
            hrEmployee.setId(hrEmployee.getId());
            Integer offenderId = hrEmployee.getId();
            ListOfDisciplineHistory = requestBeanLocal.findDisciplinByOffenderId(offenderId);
            hrDisciplineHistoryModel = new ListDataModel(ListOfDisciplineHistory);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }
    //</editor-fold>
}
