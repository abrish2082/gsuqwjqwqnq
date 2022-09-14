package et.gov.eep.hrms.controller.employee;

import javax.inject.Named;
import javax.faces.view.ViewScoped;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import java.io.Serializable;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.StringDateManipulation;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.hrms.businessLogic.address.HrAddressesBeanLocal;
import et.gov.eep.hrms.businessLogic.employee.HrEmpContractsBeanLocal;
import et.gov.eep.hrms.businessLogic.employee.HrEmpFamilyBeanLocal;
import et.gov.eep.hrms.businessLogic.lookup.HrLuNationalityBeanLocal;
import et.gov.eep.hrms.businessLogic.lookup.HrLuTitlesBeanLocal;
import et.gov.eep.hrms.businessLogic.lookup.HrRelegionBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrJobTypesBeanLocal;
import et.gov.eep.hrms.businessLogic.recruitment.HrRecruitmentRequestsBeanLocal;
import et.gov.eep.hrms.entity.lookup.HrLuNationalities;
import et.gov.eep.hrms.entity.lookup.HrLuTitles;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.lookup.HrLuSalarySteps;
import et.gov.eep.hrms.entity.employee.HrEmpFamilies;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuReligions;
import et.gov.eep.hrms.entity.employee.HrEmpSkill;
import et.gov.eep.hrms.entity.employee.HrEmpAddresses;
import et.gov.eep.hrms.entity.employee.HrEmpEducations;
import et.gov.eep.hrms.entity.employee.HrEmpExperiences;
import et.gov.eep.hrms.entity.employee.HrEmpLanguages;
import et.gov.eep.hrms.entity.employee.HrEmpReferences;
import et.gov.eep.hrms.entity.employee.HrEmpTrainings;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.event.FileUploadEvent;
import et.gov.eep.hrms.entity.lookup.HrLuBanks;
import et.gov.eep.hrms.entity.lookup.HrLuBankBranches;
import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.lookup.HrLuEducTypes;
import et.gov.eep.hrms.entity.lookup.HrLuLanguages;
import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrDeptJobs;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.entity.organization.HrSalaryScales;
import et.gov.eep.hrms.integration.HrAddressesBeanIntegrationLocal;
import et.gov.eep.hrms.businessLogic.lookup.HrLuGradesBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPgPlDeptBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPlPgBeanLocal;
import et.gov.eep.hrms.businessLogic.recruitment.HrCandidateRegistrationBeanLocal;
import et.gov.eep.hrms.businessLogic.termination.TerminationRequestBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmpContracts;
import et.gov.eep.hrms.entity.employee.HrEmpMemberships;
import et.gov.eep.hrms.entity.lookup.HrLuMemberships;
import et.gov.eep.hrms.entity.payroll.HrPayrollPlPg;
import et.gov.eep.hrms.entity.payroll.HrPayrollPlPgDept;
import et.gov.eep.hrms.entity.recruitment.HrCandidateEmpHistories;
import et.gov.eep.hrms.entity.recruitment.HrCandidateLanguages;
import et.gov.eep.hrms.entity.recruitment.HrCandidateReferences;
import et.gov.eep.hrms.entity.recruitment.HrCandidateTrainings;
import et.gov.eep.hrms.entity.recruitment.HrCandidiateEducations;
import et.gov.eep.hrms.entity.recruitment.HrCandidiates;
import et.gov.eep.hrms.mapper.employee.HrEmployeesFacade;
import et.gov.eep.hrms.mapper.organization.HrDeptJobsFacade;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DonutChartModel;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.MeterGaugeChartModel;
import org.primefaces.model.chart.PieChartModel;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
//import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Biniyam Mathewos
 */
@Named("hrEmployeeController")
@ViewScoped
public class HrEmployeeController implements Serializable {

    //<editor-fold defaultstate="Collapsed" desc="@EJB declaration">
    @EJB
    private HrEmployeeBeanLocal hrEmployeeBean;
    @EJB
    private HrRelegionBeanLocal hrRelegionBeanLocal;
    @EJB
    private HrLuNationalityBeanLocal hrLuNationalityBeanLocal;
    @EJB
    private HrLuTitlesBeanLocal hrLuTitlesBeanLocal;
    @EJB
    private HrDeptJobsFacade hrDeptJobsFacade;
    @EJB
    HrDepartmentsBeanLocal hrDepartmentsFacade;
    @EJB
    private HrRecruitmentRequestsBeanLocal hrRecruitmentRequestsBeanLocal;
    @EJB
    private HrEmpFamilyBeanLocal hrEmpFamilyBeanLocal;
    @EJB
    HrJobTypesBeanLocal hrJobTypesBeanLocal;
    @EJB
    HrLuGradesBeanLocal hrLuGradesFacade;
    @EJB
    HrAddressesBeanIntegrationLocal hrAddressesBeanIntegrationLocal;
    @EJB
    HrAddressesBeanLocal hrAddressesBeanLocal;
    @EJB
    HrPayrollPlPgBeanLocal hrPayrollPlPgBeanLocal;
    @EJB
    FmsCostCenterBeanLocal fmsCostCenterBeanLocal;
    @EJB
    FmsLuSystemBeanLocal fmsLuSystemBeanLocal;
    @EJB
    HrEmployeesFacade hrEmployeesFacade;
    @EJB
    HrCandidateRegistrationBeanLocal hrCandidateRegistrationBeanLocal;
    @EJB
    TerminationRequestBeanLocal terminationRequestBeanLocal;
    //</editor-fold>

    //<editor-fold defaultstate="Collapsed" desc="@Entity Inject">
    @Inject
    HrEmployees hrEmployees;
    @Inject
    SessionBean SessionBean;
    @Inject
    HrLuReligions hrLuReligions;
    @Inject
    HrLuTitles hrLuTitles;
    @Inject
    HrDepartments hrDepartments;
    @Inject
    HrLuNationalities hrLuNationalities;
    @Inject
    HrEmpEducations hrEmpEducations;
    @Inject
    HrEmpLanguages hrEmpLanguages;
    @Inject
    HrEmpFamilies hrEmpFamilies;
    @Inject
    HrEmpTrainings hrEmpTraining;
    @Inject
    HrEmpExperiences hrEmpExperiences;
    @Inject
    HrEmpSkill hrEmpSkill;
    @Inject
    HrEmpReferences hrEmpReferences;
    @Inject
    HrJobTypes hrJobTypes;
    @Inject
    HrSalaryScaleRanges hrSalaryScaleRanges;
    @Inject
    HrLuGrades hrLuGrades;
    @Inject
    HrLuSalarySteps hrLuSalarySteps;
    @Inject
    HrSalaryScales hrSalaryScales;
    @Inject
    HrLuBanks hrLuBanks;
    @Inject
    HrLuBankBranches hrLuBankBranches;
    @Inject
    HrLuEducTypes hrLuEducTypes;
    @Inject
    HrLuLanguages hrLuLanguages;
    @Inject
    HrEmpAddresses hrEmpAddresses;
    @Inject
    HrDeptJobs hrDeptJobs;
    @Inject
    HrLuEducLevels hrLuEducLevels;
    @Inject
    HrAddresses hrAddresses;
    @Inject
    FmsCostcSystemJunction fmsCostcSystemJunction;
    //mahi
    @Inject
    HrLuMemberships hrLuMemberships;
    @Inject
    HrEmpMemberships hrEmpMemberships;
    @Inject
    HrPayrollPlPgDept hrPayrollPlPgDept;
    @Inject
    HrPayrollPlPg hrPayrollPlPg;
    @Inject
    FmsCostCenter fmsCostCenter;
    @Inject
    FmsLuSystem fmsLuSystem;
    @Inject
    HrCandidiates hrCandidiates;

    //mahi
    String payLocation;
    String payGroup;
    String costCenter;
    String system;
    String jobLevel;
    String jobCode;
    Integer requestNotificationCounter = 0;
    //</editor-fold>

    //<editor-fold defaultstate="Collapsed" desc="Variables and Objects Declaration">
    private String SaveOrUpdateButton = "Save";
    private StreamedContent imgfile;
    private String tab = "disabled";
    private String tabToggle = "";
    int update = 0;
    private boolean skip;
    boolean btnaddvisibility = true;
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private boolean renderCandidate = true;
    private String renderpnlContrat = "false";
    private byte[] byteData;
    private UploadedFile file;
    private String filename;
    StringDateManipulation stringDateManipulation = new StringDateManipulation();
    String dateOfBirth;
    String foraddressRef;
    String hiredate;
    String hiredate2nd;
    String contenddate;
    String contenddate2nd;
    String hdate;
    String hdate2nd;
    String sdateedu;
    String sdateedu2nd;
    String edateedu;
    String edateedu2nd;
    String sdatet;
    String sdatet2nd;
    String edatet;
    String edatet2nd;
    String sdateex;
    String sdateex2nd;
    String edateex;
    String edateex2nd;
    private BarChartModel EmpStatusBarModel;
    private PieChartModel pieModel1;
    private LineChartModel lineModel;
    private DonutChartModel donutModel;
    private HorizontalBarChartModel horizontalBarModel;
    private MeterGaugeChartModel meterGaugeModel;

    String dateOfBirth2nd;
    Double empSalary;
    String empType;
    String hiddenVisipllit = "hidden";
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    TreeNode DepartmentList;
    Set<String> declaration = new HashSet<>();
    String duplicattion = null;
    private static List<HrDepartments> araListe;
    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    SelectItem[] listOfJob;
    List<HrDeptJobs> jobObjList;
    List<HrJobTypes> allJobTypes;
    private ArrayList<HrLuGrades> payGradeList = null;
    private ArrayList<HrLuSalarySteps> ListOfSalarySteps = null;
    DataModel<HrEmpEducations> hrEmpEducationsDataModel;
    DataModel<HrEmpLanguages> hrEmpLanguagesDataModel;
    DataModel<HrEmpFamilies> hrEmpFamiliesDataModel;
    DataModel<HrEmpTrainings> hrEmpTrainDataModel;
    DataModel<HrEmpAddresses> hrEmpAddressDataModel;
    DataModel<HrEmpExperiences> hrEmpExpriancesDataModel;
    DataModel<HrEmpSkill> hrEmpSkillDataModel;
    DataModel<HrEmpMemberships> hrEmpMembershipsesDataModel;
    DataModel<HrEmpReferences> hrEmpReferenceDataModel;
    //mahi
    Set<String> dupmember = new HashSet<>();
    Set<String> dupfam = new HashSet<>();
    Set<String> dupfamlist = new HashSet<>();
    @EJB
    private HrEmpContractsBeanLocal hrEmpContractsBeanLocal;

    @Inject
    HrEmpContracts hrEmpContracts;

    public HrPayrollPlPgDept getHrPayrollPlPgDept() {
        if (hrPayrollPlPgDept == null) {
            hrPayrollPlPgDept = new HrPayrollPlPgDept();
        }
        return hrPayrollPlPgDept;
    }

    public void setHrPayrollPlPgDept(HrPayrollPlPgDept hrPayrollPlPgDept) {
        this.hrPayrollPlPgDept = hrPayrollPlPgDept;
    }

    public Set<String> getDupmember() {
        return dupmember;
    }

    public void setDupmember(Set<String> dupmember) {
        this.dupmember = dupmember;
    }

    public Set<String> getDupfam() {
        return dupfam;
    }

    public void setDupfam(Set<String> dupfam) {
        this.dupfam = dupfam;
    }

    boolean renderForGraphics = true;
    boolean renderForGraphicsDb = false;

    public boolean isRenderForGraphics() {
        return renderForGraphics;
    }

    public void setRenderForGraphics(boolean renderForGraphics) {
        this.renderForGraphics = renderForGraphics;
    }

    public boolean isRenderForGraphicsDb() {
        return renderForGraphicsDb;
    }

    public void setRenderForGraphicsDb(boolean renderForGraphicsDb) {
        this.renderForGraphicsDb = renderForGraphicsDb;
    }

    public Integer getRequestNotificationCounter() {
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(Integer requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }
  List<HrEmployees> hrEmployeesnotificationList = new ArrayList<>();
    List<HrEmployees> hrEmployeesList = new ArrayList<>();

    public List<HrEmployees> getHrEmployeesList() {
        return hrEmployeesList;
    }

    public void setHrEmployeesList(List<HrEmployees> hrEmployeesList) {
        this.hrEmployeesList = hrEmployeesList;
    }

    public boolean isRenderCandidate() {
        return renderCandidate;
    }

    public void setRenderCandidate(boolean renderCandidate) {
        this.renderCandidate = renderCandidate;
    }

    //mahi
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="post construct">
    public HrEmployeeController() {
    }

    @PostConstruct
    public void init() {
        empStatusChartCreator();
        createPieModel();
        createLineModels();
        createDonutModels();
        createHorizontalBarModel();
        createMeterGaugeModels();
        allDepartmentsList = hrDepartmentsFacade.findAll();
        root = new DefaultTreeNode("Root", null);
        recursive(allDepartmentsList, 0, root);
        root2 = getRoot();
        loadAddressTree();
        requestcounter();
        hrEmployees.setEmploymentType("Permanent");
        hrEmployees.setSex("Male");
        basicSalary = true;
        renderForGraphics = false;
        renderForGraphicsDb = true;
        candidateListDataModel = new ListDataModel(hrCandidateRegistrationBeanLocal.readCandidiates(HrCandidiates.SELECTED_FOR_RECRUITMENT, HrCandidiates.EMPLOYEE));
//        hrEmployees.setLabourUnion(0);
//        hrEmployees.setIsOffDuty(0);
    }
//</editor-fold>

    //<editor-fold defaultstate="Collapsed" desc="Getter and Setter">
    public String getRenderpnlContrat() {
        return renderpnlContrat;
    }

    public void setRenderpnlContrat(String renderpnlContrat) {
        this.renderpnlContrat = renderpnlContrat;
    }

    public List<HrEmployees> getHrEmployeesnotificationList() {
        return hrEmployeesnotificationList;
    }

    public void setHrEmployeesnotificationList(List<HrEmployees> hrEmployeesnotificationList) {
        this.hrEmployeesnotificationList = hrEmployeesnotificationList;
    }

    public List<HrJobTypes> getAllJobTypes() {
        return allJobTypes;
    }

    public void setAllJobTypes(List<HrJobTypes> allJobTypes) {
        this.allJobTypes = allJobTypes;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getRoot2() {
        return root2;
    }

    public void setRoot2(TreeNode root2) {
        this.root2 = root2;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public SelectItem[] getListOfJob() {
        return listOfJob;
    }

    public void setListOfJob(SelectItem[] listOfJob) {
        this.listOfJob = listOfJob;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getHiredate() {
        return hiredate;
    }

    public void setHiredate(String hiredate) {
        this.hiredate = hiredate;
    }

    public String getHiredate2nd() {
        return hiredate2nd;
    }

    public void setHiredate2nd(String hiredate2nd) {
        this.hiredate2nd = hiredate2nd;
    }

    public String getHdate() {
        return hdate;
    }

    public void setHdate(String hdate) {
        this.hdate = hdate;
    }

    public String getHdate2nd() {
        return hdate2nd;
    }

    public void setHdate2nd(String hdate2nd) {
        this.hdate2nd = hdate2nd;
    }

    public String getContenddate() {
        return contenddate;
    }

    public void setContenddate(String contenddate) {
        this.contenddate = contenddate;
    }

    public String getContenddate2nd() {
        return contenddate2nd;
    }

    public BarChartModel getEmpStatusBarModel() {
        return EmpStatusBarModel;
    }

    public void setEmpStatusBarModel(BarChartModel EmpStatusBarModel) {
        this.EmpStatusBarModel = EmpStatusBarModel;
    }

    public PieChartModel getPieModel1() {
        return pieModel1;
    }

    public void setPieModel1(PieChartModel pieModel1) {
        this.pieModel1 = pieModel1;
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }

    public DonutChartModel getDonutModel() {
        return donutModel;
    }

    public void setDonutModel(DonutChartModel donutModel) {
        this.donutModel = donutModel;
    }

    public HorizontalBarChartModel getHorizontalBarModel() {
        return horizontalBarModel;
    }

    public void setHorizontalBarModel(HorizontalBarChartModel horizontalBarModel) {
        this.horizontalBarModel = horizontalBarModel;
    }

    public void setContenddate2nd(String contenddate2nd) {
        this.contenddate2nd = contenddate2nd;
    }

    public MeterGaugeChartModel getMeterGaugeModel() {
        return meterGaugeModel;
    }

    public void setMeterGaugeModel(MeterGaugeChartModel meterGaugeModel) {
        this.meterGaugeModel = meterGaugeModel;
    }

    public String getSdateedu() {
        return sdateedu;
    }

    public void setSdateedu(String sdateedu) {
        this.sdateedu = sdateedu;
    }

    public String getSdateedu2nd() {
        return sdateedu2nd;
    }

    public void setSdateedu2nd(String sdateedu2nd) {
        this.sdateedu2nd = sdateedu2nd;
    }

    public String getSdatet() {
        return sdatet;
    }

    public void setSdatet(String sdatet) {
        this.sdatet = sdatet;
    }

    public String getSdatet2nd() {
        return sdatet2nd;
    }

    public void setSdatet2nd(String sdatet2nd) {
        this.sdatet2nd = sdatet2nd;
    }

    public String getEdatet() {
        return edatet;
    }

    public void setEdatet(String edatet) {
        this.edatet = edatet;
    }

    public String getEdatet2nd() {
        return edatet2nd;
    }

    public void setEdatet2nd(String edatet2nd) {
        this.edatet2nd = edatet2nd;
    }

    public String getEdateedu() {
        return edateedu;
    }

    public void setEdateedu(String edateedu) {
        this.edateedu = edateedu;
    }

    public String getEdateedu2nd() {
        return edateedu2nd;
    }

    public void setEdateedu2nd(String edateedu2nd) {
        this.edateedu2nd = edateedu2nd;
    }

    public String getSdateex() {
        return sdateex;
    }

    public void setSdateex(String sdateex) {
        this.sdateex = sdateex;
    }

    public String getSdateex2nd() {
        return sdateex2nd;
    }

    public void setSdateex2nd(String sdateex2nd) {
        this.sdateex2nd = sdateex2nd;
    }

    public String getEdateex() {
        return edateex;
    }

    public void setEdateex(String edateex) {
        this.edateex = edateex;
    }

    public String getEdateex2nd() {
        return edateex2nd;
    }

    public void setEdateex2nd(String edateex2nd) {
        this.edateex2nd = edateex2nd;
    }

    public String getDateOfBirth2nd() {
        return dateOfBirth2nd;
    }

    public void setDateOfBirth2nd(String dateOfBirth2nd) {
        this.dateOfBirth2nd = dateOfBirth2nd;
    }

    public String getForaddressRef() {
        return foraddressRef;
    }

    public void setForaddressRef(String foraddressRef) {
        this.foraddressRef = foraddressRef;
    }

    public Double getEmpSalary() {
        return empSalary;
    }

    public void setEmpSalary(Double empSalary) {
        this.empSalary = empSalary;
    }

    public boolean isBtnaddvisibility() {
        return btnaddvisibility;
    }

    public void setBtnaddvisibility(boolean btnaddvisibility) {
        this.btnaddvisibility = btnaddvisibility;
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

    public List<HrDeptJobs> getJobObjList() {
        return jobObjList;
    }

    public void setJobObjList(List<HrDeptJobs> jobObjList) {
        this.jobObjList = jobObjList;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
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

    public HrLuEducLevels getHrLuEducLevels() {
        if (hrLuEducLevels == null) {
            hrLuEducLevels = new HrLuEducLevels();
        }
        return hrLuEducLevels;
    }

    public void setHrLuEducLevels(HrLuEducLevels hrLuEducLevels) {
        this.hrLuEducLevels = hrLuEducLevels;
    }
    //mahi

    public HrLuMemberships getHrLuMemberships() {
        if (hrLuMemberships == null) {

            hrLuMemberships = new HrLuMemberships();
        }
        return hrLuMemberships;
    }

    public void setHrLuMemberships(HrLuMemberships hrLuMemberships) {
        this.hrLuMemberships = hrLuMemberships;
    }

    public HrEmpMemberships getHrEmpMemberships() {
        if (hrEmpMemberships == null) {

            hrEmpMemberships = new HrEmpMemberships();
        }
        return hrEmpMemberships;
    }

    public void setHrEmpMemberships(HrEmpMemberships hrEmpMemberships) {
        this.hrEmpMemberships = hrEmpMemberships;
    }

    public String getPayLocation() {
        return payLocation;
    }

    public void setPayLocation(String payLocation) {
        this.payLocation = payLocation;
    }

    public String getPayGroup() {
        return payGroup;
    }

    public void setPayGroup(String payGroup) {
        this.payGroup = payGroup;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public boolean isBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(boolean basicSalary) {
        this.basicSalary = basicSalary;
    }

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getTabToggle() {
        return tabToggle;
    }

    public void setTabToggle(String tabToggle) {
        this.tabToggle = tabToggle;
    }

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
    }

    public HrLuReligions getHrLuReligions() {
        if (hrLuReligions == null) {
            hrLuReligions = new HrLuReligions();
        }
        return hrLuReligions;
    }

    public void setHrLuReligions(HrLuReligions hrLuReligions) {
        this.hrLuReligions = hrLuReligions;
    }

    public HrLuTitles getHrLuTitles() {
        if (hrLuTitles == null) {
            hrLuTitles = new HrLuTitles();
        }
        return hrLuTitles;
    }

    public HrDeptJobs getHrDeptJobs() {
        if (hrDeptJobs == null) {
            hrDeptJobs = new HrDeptJobs();
        }
        return hrDeptJobs;
    }

    public void setHrDeptJobs(HrDeptJobs hrDeptJobs) {
        this.hrDeptJobs = hrDeptJobs;
    }

    public void setHrLuTitles(HrLuTitles hrLuTitles) {
        this.hrLuTitles = hrLuTitles;
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

    public SelectItem[] getListOfBanks() {
        return JsfUtil.getSelectItems(hrEmployeeBean.findAllBanks(), true);
    }

    public HrLuNationalities getHrLuNationalities() {
        if (hrLuNationalities == null) {
            hrLuNationalities = new HrLuNationalities();
        }
        return hrLuNationalities;
    }

    public void setHrLuNationalities(HrLuNationalities hrLuNationalities) {
        this.hrLuNationalities = hrLuNationalities;
    }

    public SelectItem[] getListOfDepartment() {
        return JsfUtil.getSelectItems(hrEmployeeBean.findAllDepartment(), true);
    }

    public SelectItem[] getListOfNantionality() {
        return JsfUtil.getSelectItems(hrLuNationalityBeanLocal.findAll(), true);
    }

    public SelectItem[] getListOfSalaryRange() {
        return JsfUtil.getSelectItems(hrEmployeeBean.findAllGrades(), true);
    }

    public SelectItem[] getListOfSalarySteps() {
        return JsfUtil.getSelectItems(hrEmployeeBean.findAllSteps(), true);
    }

    private HrEmployees selectedEmployee;

    public HrEmployees getSelectedEmployee() {
        if (selectedEmployee == null) {
            selectedEmployee = new HrEmployees();
        }
        return selectedEmployee;
    }

    public void setSelectedEmployee(HrEmployees selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public SelectItem[] getListOfTitle() {
        return JsfUtil.getSelectItems(hrLuTitlesBeanLocal.findAll(), true);
    }

    public HrAddresses getHrAddresses() {
        if (hrAddresses == null) {
            hrAddresses = new HrAddresses();
        }
        return hrAddresses;
    }

    public void setHrAddresses(HrAddresses hrAddresses) {
        this.hrAddresses = hrAddresses;
    }

    DataModel<HrEmployees> employeesListDataModel;

    public DataModel<HrEmployees> getEmployeesListDataModel() {
        return employeesListDataModel;
    }

    public void setEmployeesListDataModel(DataModel<HrEmployees> employeesListDataModel) {
        this.employeesListDataModel = employeesListDataModel;
    }
    List<HrEmployees> EmployeeList = new ArrayList<>();

    public List<HrEmployees> getEmployeeList() {
        return EmployeeList;
    }

    public void setEmployeeList(List<HrEmployees> EmployeeList) {
        this.EmployeeList = EmployeeList;
    }

    private HrEmployees selectedEmp;

    public HrEmployees getSelectedEmp() {
        return selectedEmp;
    }

    public void setSelectedEmp(HrEmployees selectedEmp) {
        this.selectedEmp = selectedEmp;
    }

    public String getHiddenVisipllit() {
        return hiddenVisipllit;
    }

    public void setHiddenVisipllit(String hiddenVisipllit) {
        this.hiddenVisipllit = hiddenVisipllit;
    }

    public byte[] getByteData() {
        return byteData;
    }

    public void setByteData(byte[] byteData) {
        this.byteData = byteData;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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

    public HrSalaryScaleRanges getHrSalaryScaleRanges() {
        if (hrSalaryScaleRanges == null) {
            hrSalaryScaleRanges = new HrSalaryScaleRanges();
        }
        return hrSalaryScaleRanges;
    }

    public void setHrSalaryScaleRanges(HrSalaryScaleRanges hrSalaryScaleRanges) {
        this.hrSalaryScaleRanges = hrSalaryScaleRanges;
    }

    public HrLuGrades getHrLuGrades() {
        if (hrLuGrades == null) {
            hrLuGrades = new HrLuGrades();
        }
        return hrLuGrades;
    }

    public void setHrLuGrades(HrLuGrades hrLuGrades) {
        this.hrLuGrades = hrLuGrades;
    }

    public HrLuSalarySteps getHrLuSalarySteps() {
        if (hrLuSalarySteps == null) {
            hrLuSalarySteps = new HrLuSalarySteps();
        }
        return hrLuSalarySteps;
    }

    public void setHrLuSalarySteps(HrLuSalarySteps hrLuSalarySteps) {
        this.hrLuSalarySteps = hrLuSalarySteps;
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

    public HrLuBanks getHrLuBanks() {
        if (hrLuBanks == null) {
            hrLuBanks = new HrLuBanks();
        }
        return hrLuBanks;
    }

    public void setHrLuBanks(HrLuBanks hrLuBanks) {
        this.hrLuBanks = hrLuBanks;
    }

    public HrLuBankBranches getHrLuBankBranches() {
        if (hrLuBankBranches == null) {
            hrLuBankBranches = new HrLuBankBranches();
        }
        return hrLuBankBranches;
    }

    public void setHrLuBankBranches(HrLuBankBranches hrLuBankBranches) {
        this.hrLuBankBranches = hrLuBankBranches;
    }
    //  </editor-fold>

    //  <editor-fold defaultstate="collapsed" desc=" family tab ">
    int selectedRowIndexFamily = -1;

    public HrEmpFamilies getHrEmpFamilies() {
        if (hrEmpFamilies == null) {
            hrEmpFamilies = new HrEmpFamilies();
        }
        return hrEmpFamilies;
    }

    public void setHrEmpFamilies(HrEmpFamilies hrEmpFamilies) {
        this.hrEmpFamilies = hrEmpFamilies;
    }

    public DataModel<HrEmpFamilies> getHrEmpFamiliesDataModel() {
        if (hrEmpFamiliesDataModel == null) {
            hrEmpFamiliesDataModel = new ListDataModel<>();
        }
        return hrEmpFamiliesDataModel;
    }

    public void setHrEmpFamiliesDataModel(DataModel<HrEmpFamilies> hrEmpFamiliesDataModel) {
        this.hrEmpFamiliesDataModel = hrEmpFamiliesDataModel;
    }

    public void btnRowSelector() {
        selectedRowIndexFamily = getHrEmpFamiliesDataModel().getRowIndex();
        displayFamily();
    }

    public void deleteSelectedEmployeeFamily() {
        hrEmpFamilies = getHrEmpFamiliesDataModel().getRowData();
        hrEmpFamilyBeanLocal.delete(hrEmpFamilies);
        hrEmployees.getHrEmpFamiliesList().remove(hrEmpFamilies);
        if (hrEmployees.getHrEmpMembershipsesList().size() > 0) {
            recreateEmpFamily();
        }
        hrEmpFamilies.setStatus("0");
    }

    private void displayFamily() {
        clearFamilyTab();
        if (selectedRowIndexFamily > -1) {
            hrEmpFamilies = getHrEmpFamiliesDataModel().getRowData();
            dupfam.remove(hrEmpFamilies.getRelationshipType());
        }
    }

    private boolean checkRelationShipDuplicate() {
        for (HrEmpFamilies family : hrEmployees.getHrEmpFamiliesList()) {
            if (family.getRelationshipType() != null) {
                if (family.getRelationshipType().equalsIgnoreCase("Mother") || family.getRelationshipType().equalsIgnoreCase("Father")) {
                    return false;
                }
            }
        }
        return true;
    }

    public void addEmpFamily() {
        if (selectedRowIndexmembershipe > -1) {
            hrEmpFamilies.setId(hrEmployees.getHrEmpFamiliesList().get(selectedRowIndexFamily).getId());
            hrEmployees.getHrEmpFamiliesList().remove(selectedRowIndexFamily);

            if (!dupfam.contains(hrEmpFamilies.getRelationshipType())) {
                hrEmployees.getHrEmpFamiliesList().add(selectedRowIndexFamily, hrEmpFamilies);
                JsfUtil.addSuccessMessage("Succesfully Updated!");
                clearFamilyTab();
            } else {
                JsfUtil.addFatalMessage("Duplicate Entry!");
            }
        } else {
            if (!dupfam.contains(hrEmpFamilies.getRelationshipType())) {
                hrEmployees.addToEmpFamily(hrEmpFamilies);
                JsfUtil.addSuccessMessage("Succesfully Added!");
                recreateEmpFamily();
                clearFamilyTab();
            } else {
                JsfUtil.addFatalMessage("Duplicate Entry!");
            }
        }
        selectedRowIndexmembershipe = -1;
    }

    Set<String> checkEvaluation = new HashSet<>();

    String selected;
    String rendeerEndDate = "true";

    public String getRendeerEndDate() {
        return rendeerEndDate;
    }

    public void setRendeerEndDate(String rendeerEndDate) {
        this.rendeerEndDate = rendeerEndDate;
    }
    boolean endDateRendeerTxt = false;
    boolean endDateRendeerLbl = false;

    public boolean isEndDateRendeerTxt() {
        return endDateRendeerTxt;
    }

    public void setEndDateRendeerTxt(boolean endDateRendeerTxt) {
        this.endDateRendeerTxt = endDateRendeerTxt;
    }

    public boolean isEndDateRendeerLbl() {
        return endDateRendeerLbl;
    }

    public void setEndDateRendeerLbl(boolean endDateRendeerLbl) {
        this.endDateRendeerLbl = endDateRendeerLbl;
    }
    boolean lblPlace = false;
    boolean txtPlace = false;
    boolean lblPhone = false;
    boolean txtPhone = false;

    public boolean isLblPlace() {
        return lblPlace;
    }

    public void setLblPlace(boolean lblPlace) {
        this.lblPlace = lblPlace;
    }

    public boolean isTxtPlace() {
        return txtPlace;
    }

    public void setTxtPlace(boolean txtPlace) {
        this.txtPlace = txtPlace;
    }

    public boolean isLblPhone() {
        return lblPhone;
    }

    public void setLblPhone(boolean lblPhone) {
        this.lblPhone = lblPhone;
    }

    public boolean isTxtPhone() {
        return txtPhone;
    }

    public void setTxtPhone(boolean txtPhone) {
        this.txtPhone = txtPhone;
    }

    public void emergencyContact(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selected = event.getNewValue().toString();
            if (selected.equalsIgnoreCase("Yes")) {
                lblPlace = true;
                txtPlace = true;
                lblPhone = true;
                txtPhone = true;
            } else {
                lblPlace = false;
                txtPlace = false;
                lblPhone = false;
                txtPhone = false;
            }
        }
    }

    public void handelEndDate(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selected = event.getNewValue().toString();
            if (selected.equalsIgnoreCase("Permanent")) {
                rendeerEndDate = "true";
                endDateRendeerLbl = false;
                endDateRendeerTxt = false;
            } else {
                rendeerEndDate = "false";
                endDateRendeerLbl = true;
                endDateRendeerTxt = true;
            }
        }
    }

    public void recreateEmpFamily() {
        hrEmpFamiliesDataModel = null;
        hrEmpFamiliesDataModel = new ListDataModel(new ArrayList(hrEmployees.getHrEmpFamiliesList()));
        for (int i = 0; i < hrEmployees.getHrEmpFamiliesList().size(); i++) {
            if (hrEmployees.getHrEmpFamiliesList().get(i).getEmergencyContact().equalsIgnoreCase("Yes")) {
                dupfam.add(hrEmployees.getHrEmpFamiliesList().get(i).getRelationshipType());
                lblPlace = true;
                txtPlace = true;
                lblPhone = true;
                txtPhone = true;
            } else {
                dupfam.add(hrEmployees.getHrEmpFamiliesList().get(i).getRelationshipType());
                lblPlace = false;
                txtPlace = false;
                lblPhone = false;
                txtPhone = false;
            }

        }
    }

    private String clearFamilyTab() {
        hrEmpFamilies = null;
        return null;
    }

    public void updateEmpFamily() {
        hrEmpFamilies = getHrEmpFamiliesDataModel().getRowData();
    }

    //  </editor-fold>
    //  </editor-fold>
//    public void updateEmpFamily() {
//        hrEmpFamilies = getHrEmpFamiliesDataModel().getRowData();
//    }
    //  </editor-fold>
    //  <editor-fold defaultstate="collapsed" desc=" address tab ">
    int selectedRowIndexAdress = -1;

    public void btnRowSelectorAddress() {
        selectedRowIndexAdress = getHrEmpAddressDataModel().getRowIndex();
        displayAddress();
    }

    private void displayAddress() {

        if (selectedRowIndexAdress > -1) {
            hrEmpAddresses = getHrEmpAddressDataModel().getRowData();
            if (hrEmpAddresses.getAddressId() != null) {
                setAllAddressUnitAsOne(hrEmpAddresses.getAddressId().getAddressName());
            } else {
                setAllAddressUnitAsOne("");
            }

        }
        // clearAddressTab();
    }

    private boolean checkDuplicateAddress() {
        for (HrEmpAddresses addrs : hrEmployees.getHrEmpAddressesList()) {
            if (addrs.getAddressType() != null) {
                if (addrs.getAddressType().equalsIgnoreCase(hrEmpAddresses.getAddressType())) {
                    return false;
                } else {
                }
            }
        }
        return true;
    }

    public void addEmpAddress() {
        if (selectedRowIndexAdress > -1) {
            hrEmpAddresses.setId(hrEmployees.getHrEmpAddressesList().get(selectedRowIndexAdress).getId());
            hrEmployees.getHrEmpAddressesList().remove(selectedRowIndexAdress);
            if (checkDuplicateAddress()) {
                hrEmployees.getHrEmpAddressesList().add(selectedRowIndexAdress, hrEmpAddresses);
                JsfUtil.addSuccessMessage("Successfully Added!");
                clearAddressTab();
            } else {
                JsfUtil.addFatalMessage("Duplicate Entry!");
            }
        } else {
            if (checkDuplicateAddress()) {
                hrEmployees.addToEmpAdress(hrEmpAddresses);
                recreateEmpAddress();
                JsfUtil.addSuccessMessage("Successfully Added");
                clearAddressTab();
            } else {
                JsfUtil.addFatalMessage("Duplicate Entry!");
            }
        }
        selectedRowIndexAdress = -1;
    }

    public void recreateEmpAddress() {
        hrEmpAddressDataModel = null;
        hrEmpAddressDataModel = new ListDataModel(new ArrayList(hrEmployees.getHrEmpAddressesList()));
    }

    private String clearAddressTab() {
        hrEmpAddresses = null;
        setAllAddressUnitAsOne("");
        return null;
    }

    public void updateEmpAddress() {
        hrEmpAddresses = getHrEmpAddressDataModel().getRowData();
    }

    public DataModel<HrEmpAddresses> getHrEmpAddressDataModel() {
        if (hrEmpAddressDataModel == null) {
            hrEmpAddressDataModel = new ListDataModel<>();
        }
        return hrEmpAddressDataModel;
    }

    public void setHrEmpAddressDataModel(DataModel<HrEmpAddresses> hrEmpAddressDataModel) {
        this.hrEmpAddressDataModel = hrEmpAddressDataModel;
    }

    //  </editor-fold>
    //  <editor-fold defaultstate="collapsed" desc=" education tab ">
    int selectedRowIndexEducation = -1;

    public HrEmpEducations getHrEmpEducations() {
        if (hrEmpEducations == null) {
            hrEmpEducations = new HrEmpEducations();
        }
        return hrEmpEducations;
    }

    public void setHrEmpEducations(HrEmpEducations hrEmpEducations) {
        this.hrEmpEducations = hrEmpEducations;
    }

    public HrLuEducTypes getHrLuEducTypes() {
        if (hrLuEducTypes == null) {
            hrLuEducTypes = new HrLuEducTypes();
        }
        return hrLuEducTypes;
    }

    public void setHrLuEducTypes(HrLuEducTypes hrLuEducTypes) {
        this.hrLuEducTypes = hrLuEducTypes;
    }

    public DataModel<HrEmpEducations> getHrEmpEducationsDataModel() {
        if (hrEmpEducationsDataModel == null) {
            hrEmpEducationsDataModel = new ListDataModel<>();
        }
        return hrEmpEducationsDataModel;
    }

    public void setHrEmpEducationsDataModel(DataModel<HrEmpEducations> hrEmpEducationsDataModel) {
        this.hrEmpEducationsDataModel = hrEmpEducationsDataModel;
    }

    public void btnRowSelectorEducation() {
        selectedRowIndexEducation = getHrEmpEducationsDataModel().getRowIndex();
        displayEducation();
    }

    private void displayEducation() {
        //clearEducation();
        if (selectedRowIndexEducation > -1) {
            hrEmpEducations = getHrEmpEducationsDataModel().getRowData();
            hrLuEducLevels = hrEmpEducations.getEducLevelId();
            hrLuEducTypes = hrEmpEducations.getEducTypeId();
            if (hrEmpEducations.getAddressId() != null) {
                setAllEmpEduAddressUnitAsOne(hrEmpEducations.getAddressId().getAddressName());
            } else {
                setAllEmpEduAddressUnitAsOne("");
            }
        }
    }

    private boolean checkDuplicateEducation() {
        for (HrEmpEducations education : hrEmployees.getHrEmpEducationsList()) {
            if (education.getEducLevelId().getEducLevel() != null && education.getEducTypeId().getEducType() != null) {
                if (education.getEducLevelId().getEducLevel().equalsIgnoreCase(hrEmpEducations.getEducLevelId().getEducLevel())
                        && education.getEducTypeId().getEducType().equalsIgnoreCase(hrEmpEducations.getEducTypeId().getEducType())) {
                    return false;
                }
            }
        }

        return true;
    }

//    public void startdateValidation() {
//        System.out.println("inside hire date validation");
//        if ((hrEmpEducations.getStartDate() != null) && (hrEmpEducations.getStartDate().compareTo("") != 0)
//                && (hrEmpEducations.getEndDate() != null) && (hrEmpEducations.getEndDate().compareTo("") != 0)) {
//
//            if ((hrEmpEducations.getStartDate().contains("/")) && (hrEmpEducations.getEndDate().contains("/"))) {
//                String[] dateFromSDUI = hrEmpEducations.getStartDate().split("/");
//                String[] dateFromEDUI = hrEmpEducations.getEndDate().split("/");
//
//                sdateedu = hrEmpEducations.getStartDate();
//                sdateedu = dateFromSDUI[2] + "/" + dateFromSDUI[1] + "/" + dateFromSDUI[0];
//                setSdateedu2nd(dateFromSDUI[2] + "-" + dateFromSDUI[1] + "-" + dateFromSDUI[0]);
//                edateedu = hrEmpEducations.getEndDate();
//                edateedu = dateFromEDUI[2] + "/" + dateFromEDUI[1] + "/" + dateFromEDUI[0];
//                setEdateedu2nd(dateFromEDUI[2] + "-" + dateFromEDUI[1] + "-" + dateFromEDUI[0]);
//
////               int test= et.gov.eep.commonApplications.utility.date.StringDateManipulation.compareDate(contenddate2nd,hdate2nd);
//                int calculatedContDate = StringDateManipulation.compareDate(edateedu2nd, sdateedu2nd);
//                System.out.println("calculatedContDate====" + calculatedContDate);
//
//                if (calculatedContDate > 0) {
//
//                } else {
//                    JsfUtil.addFatalMessage("End Date Must Be After Start Date");
//                }
//            }
//        }
//
//    }
    public void addEmpEducation() {
        if (selectedRowIndexEducation > -1) {
            hrEmpEducations.setId(hrEmployees.getHrEmpEducationsList().get(selectedRowIndexEducation).getId());
            hrEmployees.getHrEmpEducationsList().remove(selectedRowIndexEducation);
            if (checkDuplicateEducation()) {

                //mahi for start date  validation
                if ((hrEmpEducations.getStartDate() != null) && (hrEmpEducations.getStartDate().compareTo("") != 0)
                        && (hrEmpEducations.getEndDate() != null) && (hrEmpEducations.getEndDate().compareTo("") != 0)) {

                    if ((hrEmpEducations.getStartDate().contains("/")) && (hrEmpEducations.getEndDate().contains("/"))) {
                        String[] dateFromSDUI = hrEmpEducations.getStartDate().split("/");
                        String[] dateFromEDUI = hrEmpEducations.getEndDate().split("/");

                        sdateedu = hrEmpEducations.getStartDate();
                        sdateedu = dateFromSDUI[2] + "/" + dateFromSDUI[1] + "/" + dateFromSDUI[0];
                        setSdateedu2nd(dateFromSDUI[2] + "-" + dateFromSDUI[1] + "-" + dateFromSDUI[0]);
                        edateedu = hrEmpEducations.getEndDate();
                        edateedu = dateFromEDUI[2] + "/" + dateFromEDUI[1] + "/" + dateFromEDUI[0];
                        setEdateedu2nd(dateFromEDUI[2] + "-" + dateFromEDUI[1] + "-" + dateFromEDUI[0]);

//               int test= et.gov.eep.commonApplications.utility.date.StringDateManipulation.compareDate(contenddate2nd,hdate2nd);
                        int calculatedContDate = StringDateManipulation.compareDate(edateedu2nd, sdateedu2nd);
                        if (calculatedContDate > 0) {
                            hrEmployees.getHrEmpEducationsList().add(selectedRowIndexEducation, hrEmpEducations);
                            JsfUtil.addSuccessMessage("Succesfully Updated!!");
                            clearEducation();
                        } else {
                            JsfUtil.addFatalMessage("End Date Must Be After Start Date");
                        }
                    }
                }

            } else {
                JsfUtil.addFatalMessage("Duplicate Entry!");
            }
        } else {
            if (checkDuplicateEducation()) {

                //mahi for start date  validation
                if ((hrEmpEducations.getStartDate() != null) && (hrEmpEducations.getStartDate().compareTo("") != 0)
                        && (hrEmpEducations.getEndDate() != null) && (hrEmpEducations.getEndDate().compareTo("") != 0)) {

                    if ((hrEmpEducations.getStartDate().contains("/")) && (hrEmpEducations.getEndDate().contains("/"))) {
                        String[] dateFromSDUI = hrEmpEducations.getStartDate().split("/");
                        String[] dateFromEDUI = hrEmpEducations.getEndDate().split("/");

                        sdateedu = hrEmpEducations.getStartDate();
                        sdateedu = dateFromSDUI[2] + "/" + dateFromSDUI[1] + "/" + dateFromSDUI[0];
                        setSdateedu2nd(dateFromSDUI[2] + "-" + dateFromSDUI[1] + "-" + dateFromSDUI[0]);
                        edateedu = hrEmpEducations.getEndDate();
                        edateedu = dateFromEDUI[2] + "/" + dateFromEDUI[1] + "/" + dateFromEDUI[0];
                        setEdateedu2nd(dateFromEDUI[2] + "-" + dateFromEDUI[1] + "-" + dateFromEDUI[0]);

//               int test= et.gov.eep.commonApplications.utility.date.StringDateManipulation.compareDate(contenddate2nd,hdate2nd);
                        int calculatedContDate = StringDateManipulation.compareDate(edateedu2nd, sdateedu2nd);
                        if (calculatedContDate > 0) {
                            hrEmployees.addToEmpEducation(hrEmpEducations);
                            JsfUtil.addSuccessMessage("Succesfully Added!!");
                            recreateEmpEducation();
                            clearEducation();
                        } else {
                            JsfUtil.addFatalMessage("End Date Must Be After Start Date");
                        }
                    }
                }

            } else {
                JsfUtil.addFatalMessage("Duplicate Entry!");
            }
        }
        selectedRowIndexEducation = -1;
    }

    public void recreateEmpEducation() {
        hrEmpEducationsDataModel = null;
        hrEmpEducationsDataModel = new ListDataModel(new ArrayList<>(hrEmployees.getHrEmpEducationsList()));
    }

    private String clearEducation() {
        hrEmpEducations = null;
        hrLuEducLevels = null;
        hrLuEducTypes = null;
        allEmpEduAddressUnitAsOne = null;
        return null;
    }

    public void updateEmpEducation() {
        hrEmpEducations = getHrEmpEducationsDataModel().getRowData();
    }

    //  </editor-fold>
    //  <editor-fold defaultstate="collapsed" desc=" Training tab ">
    int selectedRowIndexTrainging = -1;

    public HrEmpTrainings getHrEmpTraining() {
        if (hrEmpTraining == null) {
            hrEmpTraining = new HrEmpTrainings();
        }
        return hrEmpTraining;
    }

    public void setHrEmpTraining(HrEmpTrainings hrEmpTraining) {
        this.hrEmpTraining = hrEmpTraining;
    }

    public DataModel<HrEmpTrainings> getHrEmpTrainDataModel() {
        if (hrEmpTrainDataModel == null) {
            hrEmpTrainDataModel = new ListDataModel<>();
        }
        return hrEmpTrainDataModel;
    }

    public void setHrEmpTrainDataModel(DataModel<HrEmpTrainings> hrEmpTrainDataModel) {
        this.hrEmpTrainDataModel = hrEmpTrainDataModel;
    }

    public void btnRowSelectorTraining() {
        selectedRowIndexTrainging = getHrEmpTrainDataModel().getRowIndex();
        displayTraining();
    }

    private void displayTraining() {
        //ClearTrainingTab();
        if (selectedRowIndexTrainging > -1) {
            hrEmpTraining = getHrEmpTrainDataModel().getRowData();
            if (hrEmpEducations.getAddressId() != null) {
                setAllEmpTrnAddressUnitAsOne(hrEmpTraining.getAddressId().getAddressName());
            } else {
                setAllEmpTrnAddressUnitAsOne("");
            }
        }
    }

    private boolean checkDuplicateTraining() {
        for (HrEmpTrainings train : hrEmployees.getHrEmpTrainingsList()) {
            if (train.getTrainingTitle() != null && train.getInstitution() != null) {
                if (train.getTrainingTitle().equalsIgnoreCase(hrEmpTraining.getTrainingTitle())
                        && train.getInstitution().equalsIgnoreCase(hrEmpTraining.getInstitution())) {
                    return false;
                }
            }
        }
        return true;
    }

    public void addToEmpTrainings() {
        if (selectedRowIndexTrainging > -1) {
            hrEmpTraining.setId(hrEmployees.getHrEmpTrainingsList().get(selectedRowIndexTrainging).getId());
            hrEmployees.getHrEmpTrainingsList().remove(selectedRowIndexTrainging);
            if (checkDuplicateTraining()) {

                //mahi for start date  validation
                if ((hrEmpTraining.getStartDate() != null) && (hrEmpTraining.getStartDate().compareTo("") != 0)
                        && (hrEmpTraining.getEndDate() != null) && (hrEmpTraining.getEndDate().compareTo("") != 0)) {

                    if ((hrEmpTraining.getStartDate().contains("/")) && (hrEmpTraining.getEndDate().contains("/"))) {
                        String[] dateFromSDTUI = hrEmpTraining.getStartDate().split("/");
                        String[] dateFromEDTUI = hrEmpTraining.getEndDate().split("/");

                        sdatet = hrEmpTraining.getStartDate();
                        sdatet = dateFromSDTUI[2] + "/" + dateFromSDTUI[1] + "/" + dateFromSDTUI[0];
                        setSdatet2nd(dateFromSDTUI[2] + "-" + dateFromSDTUI[1] + "-" + dateFromSDTUI[0]);
                        edatet = hrEmpTraining.getEndDate();
                        edatet = dateFromEDTUI[2] + "/" + dateFromEDTUI[1] + "/" + dateFromEDTUI[0];
                        setEdatet2nd(dateFromEDTUI[2] + "-" + dateFromEDTUI[1] + "-" + dateFromEDTUI[0]);

//               int test= et.gov.eep.commonApplications.utility.date.StringDateManipulation.compareDate(contenddate2nd,hdate2nd);
                        int calculatedContDates = StringDateManipulation.compareDate(edatet2nd, sdatet2nd);
                        if (calculatedContDates > 0) {
                            hrEmployees.getHrEmpTrainingsList().add(selectedRowIndexTrainging, hrEmpTraining);
                            JsfUtil.addSuccessMessage("Succesfully Updated!");
                            recreateEmpTrain();
                            ClearTrainingTab();
                        } else {
                            JsfUtil.addFatalMessage("End Date Must Be After Start Date");
                        }
                    }
                }

            } else {
                JsfUtil.addFatalMessage("Duplicate Entry!");
            }

        } else {
            if (checkDuplicateTraining()) {

                //mahi for start date  validation
                if ((hrEmpTraining.getStartDate() != null) && (hrEmpTraining.getStartDate().compareTo("") != 0)
                        && (hrEmpTraining.getEndDate() != null) && (hrEmpTraining.getEndDate().compareTo("") != 0)) {

                    if ((hrEmpTraining.getStartDate().contains("/")) && (hrEmpTraining.getEndDate().contains("/"))) {
                        String[] dateFromSDTUI = hrEmpTraining.getStartDate().split("/");
                        String[] dateFromEDTUI = hrEmpTraining.getEndDate().split("/");

                        sdatet = hrEmpTraining.getStartDate();
                        sdatet = dateFromSDTUI[2] + "/" + dateFromSDTUI[1] + "/" + dateFromSDTUI[0];
                        setSdatet2nd(dateFromSDTUI[2] + "-" + dateFromSDTUI[1] + "-" + dateFromSDTUI[0]);
                        edatet = hrEmpTraining.getEndDate();
                        edatet = dateFromEDTUI[2] + "/" + dateFromEDTUI[1] + "/" + dateFromEDTUI[0];
                        setEdatet2nd(dateFromEDTUI[2] + "-" + dateFromEDTUI[1] + "-" + dateFromEDTUI[0]);

//               int test= et.gov.eep.commonApplications.utility.date.StringDateManipulation.compareDate(contenddate2nd,hdate2nd);
                        int calculatedContDatete = StringDateManipulation.compareDate(edatet2nd, sdatet2nd);
                        if (calculatedContDatete > 0) {
                            hrEmployees.addToEmpTrain(hrEmpTraining);
                            JsfUtil.addSuccessMessage("Succesfully Added!");
                            recreateEmpTrain();
                            ClearTrainingTab();
                        } else {
                            JsfUtil.addFatalMessage("End Date Must Be After Start Date");
                        }
                    }
                }

            } else {
                JsfUtil.addFatalMessage("Duplicate Entry!");
            }
        }
        selectedRowIndexTrainging = -1;
    }

    public void recreateEmpTrain() {
        hrEmpTrainDataModel = null;
        hrEmpTrainDataModel = new ListDataModel(new ArrayList(hrEmployees.getHrEmpTrainingsList()));
    }

    private String ClearTrainingTab() {
        hrEmpTraining = null;
        return null;
    }

    public void updateEmpTrain() {
        hrEmpTraining = getHrEmpTrainDataModel().getRowData();
    }

//  </editor-fold>
    //  <editor-fold defaultstate="collapsed" desc=" Language tab ">
    // new lang for update
    int selectedRowIndexLanguage = -1;

    public HrLuLanguages getHrLuLanguages() {
        if (hrLuLanguages == null) {
            hrLuLanguages = new HrLuLanguages();
        }
        return hrLuLanguages;
    }

    public void setHrLuLanguages(HrLuLanguages hrLuLanguages) {
        this.hrLuLanguages = hrLuLanguages;
    }

    public HrEmpLanguages getHrEmpLanguages() {
        if (hrEmpLanguages == null) {
            hrEmpLanguages = new HrEmpLanguages();
        }
        return hrEmpLanguages;
    }

    public void setHrEmpLanguages(HrEmpLanguages hrEmpLanguages) {
        this.hrEmpLanguages = hrEmpLanguages;
    }

    public DataModel<HrEmpLanguages> getHrEmpLanguagesDataModel() {
        if (hrEmpLanguagesDataModel == null) {
            hrEmpLanguagesDataModel = new ListDataModel<>();
        }
        return hrEmpLanguagesDataModel;
    }

    public void setHrEmpLanguagesDataModel(DataModel<HrEmpLanguages> hrEmpLanguagesDataModel) {
        this.hrEmpLanguagesDataModel = hrEmpLanguagesDataModel;
    }

    public void btnRowSelectorLanguage() {
        selectedRowIndexLanguage = getHrEmpLanguagesDataModel().getRowIndex();
        displayLanguage();
    }

    private void displayLanguage() {
        // clearLanguage();
        if (selectedRowIndexLanguage > -1) {
            hrEmpLanguages = getHrEmpLanguagesDataModel().getRowData();
        }
    }

    private boolean checkDuplicateLanguage() {
        for (HrEmpLanguages language : hrEmployees.getHrEmpLanguagesList()) {
            if (language.getLanguageId().getLanguageName() != null) {
                if (language.getLanguageId().getLanguageName().equalsIgnoreCase(hrEmpLanguages.getLanguageId().getLanguageName())) {
                    return false;
                }
            }
        }
        return true;
    }

    public void addEmpLanguage() {
        if (selectedRowIndexLanguage > -1) {
            hrEmpLanguages.setId(hrEmployees.getHrEmpLanguagesList().get(selectedRowIndexLanguage).getId());
            hrEmployees.getHrEmpLanguagesList().remove(selectedRowIndexLanguage);
            if (checkDuplicateLanguage()) {
                hrEmployees.getHrEmpLanguagesList().add(selectedRowIndexLanguage, hrEmpLanguages);
                recreateEmpLanguage();
                JsfUtil.addSuccessMessage("Succesfully Added!");
                clearLanguage();
            } else {
                JsfUtil.addFatalMessage("Duplicate Entry!");
            }
        } else {
            if (checkDuplicateLanguage()) {
                hrEmployees.addToEmpLang(hrEmpLanguages);
                recreateEmpLanguage();
                JsfUtil.addSuccessMessage("Succesfully Added!");
                clearLanguage();
            } else {
                JsfUtil.addFatalMessage("Duplicate Entry!");
            }
        }
        selectedRowIndexLanguage = -1;
    }

    public void recreateEmpLanguage() {
        hrEmpLanguagesDataModel = null;
        hrEmpLanguagesDataModel = new ListDataModel(new ArrayList<>(hrEmployees.getHrEmpLanguagesList()));
    }

    private String clearLanguage() {
        hrEmpLanguages = null;
        hrLuLanguages = null;
        return null;
    }

    public void updateEmpLaguage() {
        hrEmpLanguages = getHrEmpLanguagesDataModel().getRowData();
    }

    //end
    //  </editor-fold>
    //  <editor-fold defaultstate="collapsed" desc=" Skill tab ">
    int selectedRowIndexSkill = -1;

    public HrEmpSkill getHrEmpSkill() {
        if (hrEmpSkill == null) {
            hrEmpSkill = new HrEmpSkill();
        }
        return hrEmpSkill;
    }

    public void setHrEmpSkill(HrEmpSkill hrEmpSkill) {
        this.hrEmpSkill = hrEmpSkill;
    }

    public DataModel<HrEmpSkill> getHrEmpSkillDataModel() {
        if (hrEmpSkillDataModel == null) {
            hrEmpSkillDataModel = new ListDataModel<>();
        }
        return hrEmpSkillDataModel;
    }

    public void setHrEmpSkillDataModel(DataModel<HrEmpSkill> hrEmpSkillDataModel) {
        this.hrEmpSkillDataModel = hrEmpSkillDataModel;
    }

    public void btnRowSelectorSkill() {
        selectedRowIndexSkill = getHrEmpSkillDataModel().getRowIndex();
        displaySkill();
    }

    private void displaySkill() {
        //CleanSkill();
        if (selectedRowIndexSkill > -1) {
            hrEmpSkill = getHrEmpSkillDataModel().getRowData();
        }
    }

    private boolean checkDuplicateSkill() {
        for (HrEmpSkill skill : hrEmployees.getHrEmpSkillList()) {
            if (skill.getSkillType() != null && skill.getSkillLevel() != null) {
                if (skill.getSkillType().equalsIgnoreCase(hrEmpSkill.getSkillType())
                        && skill.getSkillLevel().equalsIgnoreCase(hrEmpSkill.getSkillLevel())) {
                    return false;
                }
            }
        }
        return true;
    }

    public void addEmpSkill() {
        if (selectedRowIndexSkill > -1) {
            hrEmpSkill.setId(hrEmployees.getHrEmpSkillList().get(selectedRowIndexSkill).getId());
            hrEmployees.getHrEmpSkillList().remove(selectedRowIndexSkill);
            if (checkDuplicateSkill()) {
                hrEmployees.getHrEmpSkillList().add(selectedRowIndexSkill, hrEmpSkill);
                JsfUtil.addSuccessMessage("Succesfully Updated!");
            } else {
                JsfUtil.addFatalMessage("Duplicate Entry!");
            }
        } else {
            if (checkDuplicateSkill()) {
                hrEmployees.addToEmpSkill(hrEmpSkill);
                JsfUtil.addSuccessMessage("Succesfully Added!");
                recreateEmpSkill();
            } else {
                JsfUtil.addFatalMessage("Duplicate Entry!");
            }
        }
        CleanSkill();
        selectedRowIndexSkill = -1;
    }

    public void recreateEmpSkill() {
        hrEmpSkillDataModel = null;
        hrEmpSkillDataModel = new ListDataModel(new ArrayList(hrEmployees.getHrEmpSkillList()));
    }

    private String CleanSkill() {
        hrEmpSkill = null;
        return null;
    }

    public void updateEmpSkill() {
        hrEmpSkill = getHrEmpSkillDataModel().getRowData();
    }

    //  </editor-fold>
    //  <editor-fold defaultstate="collapsed" desc=" membership tab ">
    //mahi
    int selectedRowIndexmembershipe = -1;

    public DataModel<HrEmpMemberships> getHrEmpMembershipsesDataModel() {
        if (hrEmpMembershipsesDataModel == null) {
            hrEmpMembershipsesDataModel = new ListDataModel<>();
        }
        return hrEmpMembershipsesDataModel;
    }

    public void setHrEmpMembershipsesDataModel(DataModel<HrEmpMemberships> hrEmpMembershipsesDataModel) {
        this.hrEmpMembershipsesDataModel = hrEmpMembershipsesDataModel;

    }

    public void btnRowSelectorMembershipe() {
        selectedRowIndexmembershipe = getHrEmpMembershipsesDataModel().getRowIndex();

        displaymemebrshipe();
    }

    private void displaymemebrshipe() {
        // CleanMembrshipe();
        if (selectedRowIndexmembershipe > -1) {
            hrEmpMemberships = getHrEmpMembershipsesDataModel().getRowData();
            hrLuMemberships = hrEmpMemberships.getMembershipId();

            dupmember.remove(hrEmpMemberships.getMembershipId().getMembership());
        }
    }

    private String CleanMembrshipe() {
        hrEmpMemberships = null;
        hrLuMemberships = null;
        return null;
    }

    public void recreateEmpmembershipe() {
        hrEmpMembershipsesDataModel = null;
        hrEmpMembershipsesDataModel = new ListDataModel(new ArrayList(hrEmployees.getHrEmpMembershipsesList()));
        for (int i = 0; i < hrEmployees.getHrEmpMembershipsesList().size(); i++) {

            dupmember.add(hrEmployees.getHrEmpMembershipsesList().get(i).getMembershipId().getMembership());
        }
    }

//    private boolean checkDuplicateMembershipe() {
//        for (HrEmpMemberships membershipe : hrEmployees.getHrEmpMembershipsesList()) {
//            if (membershipe.getMembershipId() != null) {
//                if (membershipe.getMembershipId().getId().equals(hrEmpMemberships.getMembershipId().getId())) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
    //hashset add
    public void addEmpMembershipe() {

        if (selectedRowIndexmembershipe > -1) {
//            hrEmpMemberships.setMembershipId(hrLuMemberships);
            hrEmpMemberships.setId(hrEmployees.getHrEmpMembershipsesList().get(selectedRowIndexmembershipe).getId());
            hrEmployees.getHrEmpMembershipsesList().remove(selectedRowIndexmembershipe);
            if (!dupmember.contains(hrLuMemberships.getMembership())) {
                hrEmployees.getHrEmpMembershipsesList().add(selectedRowIndexmembershipe, hrEmpMemberships);
                dupmember.add(hrLuMemberships.getMembership());
                JsfUtil.addSuccessMessage("Succesfully Added!");
                CleanMembrshipe();
            } else {
                JsfUtil.addFatalMessage("Duplicate Entry!");
            }
        } else {
//            System.out.println("s4econd if====" + hrEmpMemberships.getMembershipId().getMembership());
//             System.out.println("hrEmpMemberships.getMembershipId().getMembership()===" +hrEmpMemberships.getMembershipId().getId());
//            System.out.println("hrEmpMemberships.getMembershipId().getMembership()===" +hrEmpMemberships.getMembershipId().getMembership());
            if (!dupmember.contains(hrLuMemberships.getMembership())) {
                hrEmpMemberships.setMembershipId(hrLuMemberships);
                hrEmployees.addToEmpMembershipe(hrEmpMemberships);
                dupmember.add(hrLuMemberships.getMembership());
                JsfUtil.addSuccessMessage("Succesfully Added!");
                recreateEmpmembershipe();
                CleanMembrshipe();
            } else {
                JsfUtil.addFatalMessage("Duplicate Entry!");
            }
        }
//        CleanMembrshipe();
        selectedRowIndexmembershipe = -1;
    }

//    public void addEmpMembershipe() {
//
//        if (selectedRowIndexmembershipe > -1) {
//
//            hrEmpMemberships.setId(hrEmployees.getHrEmpMembershipsesList().get(selectedRowIndexmembershipe).getId());
//            hrEmployees.getHrEmpMembershipsesList().remove(selectedRowIndexmembershipe);
//            if (checkDuplicateMembershipe()) {
//                hrEmployees.getHrEmpMembershipsesList().add(selectedRowIndexmembershipe, hrEmpMemberships);
//                JsfUtil.addSuccessMessage("Succesfully Updated!");
//                CleanMembrshipe();
//            } else {
//                JsfUtil.addFatalMessage("Duplicate Entry!");
//            }
//        } else {
//            if (checkDuplicateMembershipe()) {
//                hrEmpMemberships.setMembershipId(hrLuMemberships);
//                hrEmployees.addToEmpMembershipe(hrEmpMemberships);
//                JsfUtil.addSuccessMessage("Succesfully Added!");
//                recreateEmpmembershipe();
//                CleanMembrshipe();
//            } else {
//                JsfUtil.addFatalMessage("Duplicate Entry!");
//            }
//        }
//        selectedRowIndexSkill = -1;
//    }
    public void updateEmpmembershipe() {
        hrEmpMemberships = getHrEmpMembershipsesDataModel().getRowData();
//          hrLuMemberships = hrEmpMemberships.getMembershipId();
        dupmember.remove(hrEmpMemberships.getMembershipId().getMembership());
//        dupmember.remove(hrEmpMemberships.getMembershipId().getMembership());

    }

    public List<HrLuMemberships> getListOfMembershipes() {
        return hrEmployeeBean.findAllMembershipses();
    }

    public void getChangedMembershipesObject(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrLuMemberships = hrEmployeeBean.getChangedMembershipesObject(BigDecimal.valueOf(Double.valueOf(event.getNewValue().toString())));

        }
    }

    //  </editor-fold>
    //  <editor-fold defaultstate="collapsed" desc=" expriance tab ">
    int selectedRowIndexExpriance = -1;

    public HrEmpExperiences getHrEmpExperiences() {
        if (hrEmpExperiences == null) {
            hrEmpExperiences = new HrEmpExperiences();
        }
        return hrEmpExperiences;
    }

    public void setHrEmpExperiences(HrEmpExperiences hrEmpExperiences) {
        this.hrEmpExperiences = hrEmpExperiences;
    }

    public DataModel<HrEmpExperiences> getHrEmpExpriancesDataModel() {
        if (hrEmpExpriancesDataModel == null) {
            hrEmpExpriancesDataModel = new ListDataModel<>();
        }
        return hrEmpExpriancesDataModel;
    }

    public void setHrEmpExpriancesDataModel(DataModel<HrEmpExperiences> hrEmpExpriancesDataModel) {
        this.hrEmpExpriancesDataModel = hrEmpExpriancesDataModel;
    }

    public void btnRowSelectorExperiance() {
        selectedRowIndexExpriance = getHrEmpExpriancesDataModel().getRowIndex();
        displayExperiance();
    }

    private void displayExperiance() {
        //ClearExpriance();
        if (selectedRowIndexExpriance > -1) {
            hrEmpExperiences = getHrEmpExpriancesDataModel().getRowData();
            if (hrEmpExperiences.getAddressId() != null) {
                setAllEmpExprianceAddressUnitAsOne(hrEmpExperiences.getAddressId().getAddressName());
            } else {
                setAllEmpExprianceAddressUnitAsOne("");
            }

        }
    }

    private boolean checkDuplicateExperience() {
        for (HrEmpExperiences expriance : hrEmployees.getHrEmpExperiencesList()) {
            if (expriance.getInstitution() != null && expriance.getJobTitle() != null) {
                if (expriance.getInstitution().equalsIgnoreCase(hrEmpExperiences.getInstitution())
                        && expriance.getJobTitle().equalsIgnoreCase(hrEmpExperiences.getJobTitle())) {
                    return false;
                }
            }
        }
        return true;
    }

    public void addEmpExperiance() {
        if (selectedRowIndexExpriance > -1) {
            hrEmpExperiences.setId(hrEmployees.getHrEmpExperiencesList().get(selectedRowIndexExpriance).getId());
            hrEmployees.getHrEmpExperiencesList().remove(selectedRowIndexExpriance);
            if (checkDuplicateExperience()) {
                //mahi for start date  validation
                if ((hrEmpExperiences.getStartDate() != null) && (hrEmpExperiences.getStartDate().compareTo("") != 0)
                        && (hrEmpExperiences.getEndDate() != null) && (hrEmpExperiences.getEndDate().compareTo("") != 0)) {
                    if ((hrEmpExperiences.getStartDate().contains("/")) && (hrEmpExperiences.getEndDate().contains("/"))) {
                        String[] dateFromSDEXUI = hrEmpExperiences.getStartDate().split("/");
                        String[] dateFromEDEXUI = hrEmpExperiences.getEndDate().split("/");

                        sdateex = hrEmpExperiences.getStartDate();
                        sdateex = dateFromSDEXUI[2] + "/" + dateFromSDEXUI[1] + "/" + dateFromSDEXUI[0];
                        setSdateex2nd(dateFromSDEXUI[2] + "-" + dateFromSDEXUI[1] + "-" + dateFromSDEXUI[0]);
                        edateex = hrEmpExperiences.getEndDate();
                        edateex = dateFromEDEXUI[2] + "/" + dateFromEDEXUI[1] + "/" + dateFromEDEXUI[0];
                        setEdateex2nd(dateFromEDEXUI[2] + "-" + dateFromEDEXUI[1] + "-" + dateFromEDEXUI[0]);

//               int test= et.gov.eep.commonApplications.utility.date.StringDateManipulation.compareDate(contenddate2nd,hdate2nd);
                        int calculatedContDateex = StringDateManipulation.compareDate(edateex2nd, sdateex2nd);
                        if (calculatedContDateex > 0) {
                            if (hrEmpExperiences.getSalary() != 0) {
                                hrEmployees.getHrEmpExperiencesList().add(selectedRowIndexExpriance, hrEmpExperiences);
                                JsfUtil.addSuccessMessage("Succesfully added");
                                recreateEmpExpriances();
                                ClearExpriance();
                            } else {
                                JsfUtil.addFatalMessage("Salary should not be zero");
                            }
                        } else {
                            JsfUtil.addFatalMessage("End date must be after start date");
                        }
                    }
                }
            } else {
                JsfUtil.addFatalMessage("Duplicate entry");
            }
        } else {
            if (checkDuplicateExperience()) {
                if ((hrEmpExperiences.getStartDate() != null) && (hrEmpExperiences.getStartDate().compareTo("") != 0)
                        && (hrEmpExperiences.getEndDate() != null) && (hrEmpExperiences.getEndDate().compareTo("") != 0)) {

                    if ((hrEmpExperiences.getStartDate().contains("/")) && (hrEmpExperiences.getEndDate().contains("/"))) {
                        String[] dateFromSDEXUI = hrEmpExperiences.getStartDate().split("/");
                        String[] dateFromEDEXUI = hrEmpExperiences.getEndDate().split("/");

                        sdateex = hrEmpExperiences.getStartDate();
                        sdateex = dateFromSDEXUI[2] + "/" + dateFromSDEXUI[1] + "/" + dateFromSDEXUI[0];
                        setSdateex2nd(dateFromSDEXUI[2] + "-" + dateFromSDEXUI[1] + "-" + dateFromSDEXUI[0]);
                        edateex = hrEmpExperiences.getEndDate();
                        edateex = dateFromEDEXUI[2] + "/" + dateFromEDEXUI[1] + "/" + dateFromEDEXUI[0];
                        setEdateex2nd(dateFromEDEXUI[2] + "-" + dateFromEDEXUI[1] + "-" + dateFromEDEXUI[0]);

                        int calculatedContDatee = StringDateManipulation.compareDate(edateex2nd, sdateex2nd);
                        if (calculatedContDatee > 0) {
                            if (hrEmpExperiences.getSalary() != 0) {
                                hrEmployees.addToEmpExpriance(hrEmpExperiences);
                                JsfUtil.addSuccessMessage("Succesfully added");
                                recreateEmpExpriances();
                                ClearExpriance();
                            } else {
                                JsfUtil.addFatalMessage("Salary should not be zero.");
                            }
                        } else {
                            JsfUtil.addFatalMessage("End date must be after start date.");
                        }
                    }
                }
            } else {
                JsfUtil.addFatalMessage("Duplicate entry");
            }
        }
        selectedRowIndexExpriance = -1;
    }

    public void recreateEmpExpriances() {
        hrEmpExpriancesDataModel = null;
        hrEmpExpriancesDataModel = new ListDataModel(new ArrayList(hrEmployees.getHrEmpExpriancesList()));

    }

    private String ClearExpriance() {
        hrEmpExperiences = null;
        setAllEmpExprianceAddressUnitAsOne("");
        return null;
    }

    public void updateEmpExpriance() {
        hrEmpExperiences = getHrEmpExpriancesDataModel().getRowData();
    }

    //  </editor-fold>
    //  <editor-fold defaultstate="collapsed" desc=" references tab ">
    int selectedRowIndexReferences = -1;

    public HrEmpReferences getHrEmpReferences() {
        if (hrEmpReferences == null) {
            hrEmpReferences = new HrEmpReferences();
        }
        return hrEmpReferences;
    }

    public void setHrEmpReferences(HrEmpReferences hrEmpReferences) {
        this.hrEmpReferences = hrEmpReferences;
    }

    public DataModel<HrEmpReferences> getHrEmpReferenceDataModel() {
        if (hrEmpReferenceDataModel == null) {
            hrEmpReferenceDataModel = new ListDataModel<>();
        }
        return hrEmpReferenceDataModel;
    }

    public void setHrEmpReferenceDataModel(DataModel<HrEmpReferences> hrEmpReferenceDataModel) {
        this.hrEmpReferenceDataModel = hrEmpReferenceDataModel;
    }

    public void recreateEmpRefencePop() {
        hrEmpReferenceDataModel = null;
        hrEmpReferenceDataModel = new ListDataModel(new ArrayList(hrEmployees.getHrEmpReferencesList()));
    }

    public void btnRowSelectorReference() {
        selectedRowIndexReferences = getHrEmpReferenceDataModel().getRowIndex();
        displayReference();
    }

    private void displayReference() {
        // ClearRefeneceTab();
        if (selectedRowIndexReferences > -1) {
            hrEmpReferences = getHrEmpReferenceDataModel().getRowData();
        }
    }

    private boolean checkDuplicateReference() {
        for (HrEmpReferences reference : hrEmployees.getHrEmpReferencesList()) {
            if (reference.getNameOfReferee() != null && reference.getRelationshipType() != null) {
                if (reference.getNameOfReferee().equalsIgnoreCase(hrEmpReferences.getNameOfReferee())
                        && reference.getRelationshipType().equalsIgnoreCase(hrEmpReferences.getRelationshipType())) {
                    return false;
                }
            }
        }
        return true;
    }

    public void addEmpReference() {
        if (selectedRowIndexReferences > -1) {
            hrEmpReferences.setId(hrEmployees.getHrEmpReferencesList().get(selectedRowIndexReferences).getId());
            hrEmployees.getHrEmpReferencesList().remove(selectedRowIndexReferences);
            if (checkDuplicateReference()) {
                hrEmployees.getHrEmpReferencesList().add(selectedRowIndexReferences, hrEmpReferences);
                JsfUtil.addSuccessMessage("Succesfully updated");
                recreateEmpReference();
                ClearRefeneceTab();
            } else {
                JsfUtil.addFatalMessage("Duplicate entry!");
            }
        } else {
            if (checkDuplicateReference()) {
                hrEmployees.addToEmpReference(hrEmpReferences);
                JsfUtil.addSuccessMessage("Succesfully added");
                recreateEmpReference();
                ClearRefeneceTab();
            } else {
                JsfUtil.addFatalMessage("Duplicate entry!");
            }
        }
        selectedRowIndexReferences = -1;
    }

    private void ClearRefeneceTab() {
        hrEmpReferences = null;
        setAllEmpRfrAddressUnitAsOne("");
    }

    public void updateEmpReference() {
        hrEmpReferences = getHrEmpReferenceDataModel().getRowData();
    }

    public void recreateEmpReference() {
        hrEmpReferenceDataModel = null;
        hrEmpReferenceDataModel = new ListDataModel(new ArrayList(hrEmployees.getHrEmpReferencesList()));

    }

    //  </editor-fold>
    //  <editor-fold defaultstate="collapsed" desc="Image Upload and Display">
//    public void handleImageUpload(FileUploadEvent event) {
//        file = event.getFile();
//        try {
//            byteData = IOUtils.toByteArray(file.getInputstream());
//            hrEmployees.setPhoto(byteData);
//            hrEmployeeBean.save(hrEmployees);
//            FacesMessage msg = new FacesMessage("####Succesful####", event.getFile().getFileName() + " is uploaded.");
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//    public void handleFileUpload(FileUploadEvent event) {
//        file = event.getFile();
//        try {
//            byteData = IOUtils.toByteArray(file.getInputstream());
//            hrEmployees.setPhoto(byteData);
//            hrEmployeeBean.edit(hrEmployees);
//            FacesMessage msg = new FacesMessage("####Succesful####", event.getFile().getFileName() + " is uploaded.");
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
    public StreamedContent getImgfile() {
        return imgfile;
    }

    public void setImgfile(StreamedContent imgfile) {
        this.imgfile = imgfile;
    }

    public StreamedContent loadContent(HrEmployees hrEmployee) {
        hrEmployees.setPhoto(hrEmployees.getPhoto());
        if (hrEmployees.getPhoto() != null) {
            byte[] data = (byte[]) (hrEmployees.getPhoto());
            this.imgfile = new DefaultStreamedContent(new ByteArrayInputStream(data), "image/jpeg");
        }
        return imgfile;
    }

    //  </editor-fold>
    //  <editor-fold defaultstate="collapsed" desc="ValueChangeEvent">
    public void jobChangedListener(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrJobTypes.setId(Integer.valueOf(event.getNewValue().toString()));
            hrJobTypes = hrRecruitmentRequestsBeanLocal.findByName(hrJobTypes.getId());
            hrEmployees.setJobId(hrJobTypes);
            hrDeptJobs.setJobId(hrJobTypes);
            hrSalaryScaleRanges = hrJobTypes.getJobGradeId();
            hrLuGrades = hrSalaryScaleRanges.getGradeId();
            hrEmployees.setGradeId(hrSalaryScaleRanges);
            hrEmployees.setJobId(hrJobTypes);
            setJobLevel(hrJobTypes.getJobLevelId().getJobLevel());
            setJobCode(hrJobTypes.getJobCode());
        }
    }

    public SelectItem[] getSalarySetpsByJobTitle() {
        if (hrJobTypes.getJobTitle() != null) {
            SelectItem[] steps = JsfUtil.getSelectItems(hrEmployeeBean.searchSalaryStepsInfo(hrSalaryScaleRanges), true);
            return steps;
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--- Select ---");
            return items;
        }
    }

    public void religionChanged(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            hrLuReligions.setReligion(event.getNewValue().toString());
            hrLuReligions = hrEmployeeBean.findReligion(hrLuReligions);
            hrEmployees.setReligionId(hrLuReligions);
        }
    }

    public SelectItem[] getListOfReligion() {
        return JsfUtil.getSelectItems(hrRelegionBeanLocal.findAll(), true);
    }

    public void TitleChanged(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrLuTitles.setTitle(event.getNewValue().toString());
            hrLuTitles = hrEmployeeBean.findTitles(hrLuTitles);
            hrEmployees.setTitleId(hrLuTitles);
        }
    }

    public void DepartmentChanged(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrDepartments.setDepName(event.getNewValue().toString());
            hrDepartments = hrEmployeeBean.findDepartment(hrDepartments);
        }

    }

    public void BankChanged(ValueChangeEvent event) {
        if (event.getNewValue() != null && !event.getNewValue().toString().isEmpty()) {
            hrLuBanks.setBankName(event.getNewValue().toString());
            hrLuBanks = hrEmployeeBean.findBanks(hrLuBanks);
            hrEmployees.setBankName(hrLuBanks);
        }
    }

    public void BankBranchChanged(ValueChangeEvent event) {
        if (event.getNewValue() != null && !event.getNewValue().toString().isEmpty()) {
            hrLuBankBranches.setBranchName(event.getNewValue().toString());
            hrLuBankBranches = hrEmployeeBean.findBankBranchs(hrLuBankBranches);
            hrEmployees.setBankBranch(hrLuBankBranches);
        }
    }

    public SelectItem[] getBranchByBank() {
        if (hrLuBanks.getBankName() != null) {
            SelectItem[] steps = JsfUtil.getSelectItems(hrEmployeeBean.searchBankBranchInfo(hrLuBanks), true);
            return steps;
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--- Select ---");
            return items;
        }
    }

    public void NantionalityChanged(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrLuNationalities.setNationality(event.getNewValue().toString());
            hrLuNationalities = hrEmployeeBean.findNationality(hrLuNationalities);
            hrEmployees.setNationalityId(hrLuNationalities);
        }
    }

    public SelectItem[] getJobTitleByDepartment() {
        if (hrDepartments != null) {
            return JsfUtil.getSelectItems(hrEmployeeBean.searchJobInfo(hrDepartments), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--- Select ---");
            return items;
        }
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onCellEditSkill(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            hrEmployeeBean.EditSkill(hrEmpSkill);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public SelectItem[] getListOfJobTitle() {
        return JsfUtil.getSelectItems(hrEmployeeBean.findAllJobTitles(), true);
    }

//    List<HrLuLanguages> languages = new ArrayList<>();
//    public List<HrLuLanguages> getLanguages() {
//        return languages = hrEmployeeBean.findAllHrLuLanguages();
//    }
//
//    public void setLanguages(List<HrLuLanguages> languages) {
//        this.languages = languages;
//    }
    public SelectItem[] getListOfLanguages() {
        return JsfUtil.getSelectItems(hrEmployeeBean.findAllHrLuLanguages(), true);
    }

    public SelectItem[] getEducationTypes() {
        return JsfUtil.getSelectItems(hrEmployeeBean.findEducationTypes(), true);
    }

    public SelectItem[] getEducationLeves() {
        return JsfUtil.getSelectItems(hrEmployeeBean.findEducationLeves(), true);
    }

    public void ChangedEduLevel(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            hrLuEducLevels.setEducLevel(event.getNewValue().toString());
            hrLuEducLevels = hrEmployeeBean.findbyluEduLevel(hrLuEducLevels);
            hrEmpEducations.setEducLevelId(hrLuEducLevels);
        }
    }

    public void ChangedEduType(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            hrLuEducTypes.setEducType(event.getNewValue().toString());
            hrLuEducTypes = hrEmployeeBean.findbyluEduType(hrLuEducTypes);
            hrEmpEducations.setEducTypeId(hrLuEducTypes);
        }
    }

    public SelectItem[] getListOfTitleProfetion() {
        return JsfUtil.getSelectItems(hrEmployeeBean.findAllHrLuTitles(), true);
    }

    public SelectItem[] getDeptList() {
        return JsfUtil.getSelectItems(hrDepartmentsFacade.getDeptID(), true);
    }

    public void changeJobTitle(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            jobObjList = hrDeptJobsFacade.getListOfJobs(e.getNewValue().toString());
        }
    }

//    public void salaryScaleChanged(ValueChangeEvent event) {
//        if (event.getNewValue() != null) {
//            hrLuSalarySteps.setSalaryStep(event.getNewValue().toString());
//            hrLuSalarySteps = hrEmployeeBean.searchHrLuSalaryStepsInfo(hrLuSalarySteps);
//            hrSalaryScales.setSalaryRangeId(hrSalaryScaleRanges);
//            hrSalaryScales.setSalaryStepId(hrLuSalarySteps);
//            hrSalaryScales = hrEmployeeBean.checkStepIdRep(hrSalaryScaleRanges, hrLuSalarySteps);
//            hrEmployees.setSalaryStep(hrLuSalarySteps);
//            setEmpSalary(hrSalaryScales.getSalary());
//            hrEmployees.setBasicSalary(empSalary);
//            System.out.println("salary  ====" + hrEmployees.getBasicSalary());
//        }
//    }
    public ArrayList<HrSalaryScales> getSalarySetps() {
        ArrayList<HrSalaryScales> salarySteps = null;
        if (hrDepartments != null && hrJobTypes != null) {
            salarySteps = new ArrayList<>();
            if (hrJobTypes.getJobGradeId() != null) {
                hrSalaryScaleRanges = hrJobTypes.getJobGradeId();
                salarySteps = hrEmployeeBean.searchSalaryStepsInfo(hrSalaryScaleRanges);
            }
        }
        return salarySteps;
    }

    private boolean basicSalary;

    public void vcl_salaryStep(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            String salStep = event.getNewValue().toString();
            if (salStep.equalsIgnoreCase("0")) {
                hrEmployees.setBasicSalary(Double.parseDouble(salStep));
                hrEmployees.setSalaryStep(null);
                basicSalary = false;
            } else if (salStep.equalsIgnoreCase("-1")) {
                hrEmployees.setBasicSalary(hrSalaryScaleRanges.getMaxSalary());
                hrEmployees.setSalaryStep(null);
                basicSalary = true;
            } else if (salStep.equalsIgnoreCase("-2")) {
                hrEmployees.setBasicSalary(hrSalaryScaleRanges.getMinSalary());
                hrEmployees.setSalaryStep(null);
                basicSalary = true;
            } else {
                basicSalary = true;
                hrLuSalarySteps.setSalaryStep(Integer.parseInt(event.getNewValue().toString()));
                hrLuSalarySteps = hrEmployeeBean.searchHrLuSalaryStepsInfo(hrLuSalarySteps);
                hrSalaryScales.setSalaryRangeId(hrSalaryScaleRanges);
                hrSalaryScales.setSalaryStepId(hrLuSalarySteps);
                hrSalaryScales = hrEmployeeBean.checkStepIdRep(hrSalaryScaleRanges, hrLuSalarySteps);
                hrEmployees.setSalaryStep(hrLuSalarySteps);
                setEmpSalary(hrSalaryScales.getSalary());
                hrEmployees.setBasicSalary(empSalary);
            }
        } else {
            JsfUtil.addErrorMessage("Please Select Salary Step");
        }
    }
    @Inject
    HrPayrollPgPlDeptBeanLocal hrPayrollPgPlDeptBeanLocal;

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int key = Integer.valueOf((selectedNode.getData().toString()).split("--")[0]);
        hrDepartments.setDepName((selectedNode.getData().toString()).split("--")[1]);
        hrDepartments = hrRecruitmentRequestsBeanLocal.getSelectDepartement(key);
        hrDepartments.setDepId(key);
        hrEmployees.setDeptId(hrDepartments);
        allJobTypes = hrRecruitmentRequestsBeanLocal.listOfJobType(hrDepartments.getDepId());
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlgDpt').hide();");
        hrPayrollPlPgDept = hrPayrollPgPlDeptBeanLocal.findBydepOfPlPg(hrEmployees.getDeptId().getDepId());
        if (hrPayrollPlPgDept != null) {
            setPayLocation(hrPayrollPlPgDept.getPayLocationPayGroupId().getPayLocation().toString());
            setPayGroup(hrPayrollPlPgDept.getPayLocationPayGroupId().getPayGroup().toString());
        } else {
            hrPayrollPlPgDept = new HrPayrollPlPgDept();
        }
        fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findbyDeptId(hrDepartments.getDepId());
        if (fmsCostcSystemJunction != null) {
            if (fmsCostcSystemJunction.getFmsCostCenter() != null) {
                setCostCenter(fmsCostcSystemJunction.getFmsCostCenter().getSystemName());
            } else {
                setCostCenter("");
            }
            if (fmsCostcSystemJunction.getFmsLuSystem() != null) {
                setSystem(fmsCostcSystemJunction.getFmsLuSystem().getSystemCode());
            } else {
                setSystem("");
            }
        } else {
            fmsCostcSystemJunction = new FmsCostcSystemJunction();
        }

    }

    //  </editor-fold>
    //  <editor-fold defaultstate="collapsed" desc="Address Tree">
    private TreeNode addressRoot;
    private TreeNode addressSelectedNode;
    private static List<HrAddresses> allAddresses;
    private static List<HrAddresses> addresses;
    private int addressId;
    private String allAddressUnitAsOne;
    private String allEmpEduAddressUnitAsOne;
    private String allEmpExprianceAddressUnitAsOne;
    private String allEmpRfrAddressUnitAsOne;
    private String allEmpTrnAddressUnitAsOne;

    //  <editor-fold defaultstate="collapsed" desc=" Getter & Setters ">
    public HrEmpAddresses getHrEmpAddresses() {
        if (hrEmpAddresses == null) {
            hrEmpAddresses = new HrEmpAddresses();
        }
        return hrEmpAddresses;
    }

    public void setHrEmpAddresses(HrEmpAddresses hrEmpAddresses) {
        this.hrEmpAddresses = hrEmpAddresses;
    }

    public TreeNode getAddressRoot() {
        return addressRoot;
    }

    public void setAddressRoot(TreeNode addressRoot) {
        this.addressRoot = addressRoot;
    }

    public TreeNode getAddressSelectedNode() {
        return addressSelectedNode;
    }

    public void setAddressSelectedNode(TreeNode addressSelectedNode) {
        this.addressSelectedNode = addressSelectedNode;
    }

    public static List<HrAddresses> getAllAddresses() {
        return allAddresses;
    }

    public static void setAllAddresses(List<HrAddresses> allAddresses) {
        HrEmployeeController.allAddresses = allAddresses;
    }

    public String getAllAddressUnitAsOne() {
        return allAddressUnitAsOne;
    }

    public void setAllAddressUnitAsOne(String allAddressUnitAsOne) {
        this.allAddressUnitAsOne = allAddressUnitAsOne;
    }

    public String getAllEmpEduAddressUnitAsOne() {
        return allEmpEduAddressUnitAsOne;
    }

    public void setAllEmpEduAddressUnitAsOne(String allEmpEduAddressUnitAsOne) {
        this.allEmpEduAddressUnitAsOne = allEmpEduAddressUnitAsOne;
    }

    public String getAllEmpExprianceAddressUnitAsOne() {
        return allEmpExprianceAddressUnitAsOne;
    }

    public void setAllEmpExprianceAddressUnitAsOne(String allEmpExprianceAddressUnitAsOne) {
        this.allEmpExprianceAddressUnitAsOne = allEmpExprianceAddressUnitAsOne;
    }

    public String getAllEmpTrnAddressUnitAsOne() {
        return allEmpTrnAddressUnitAsOne;
    }

    public void setAllEmpTrnAddressUnitAsOne(String allEmpTrnAddressUnitAsOne) {
        this.allEmpTrnAddressUnitAsOne = allEmpTrnAddressUnitAsOne;
    }

    public String getAllEmpRfrAddressUnitAsOne() {
        return allEmpRfrAddressUnitAsOne;
    }

    public void setAllEmpRfrAddressUnitAsOne(String allEmpRfrAddressUnitAsOne) {
        this.allEmpRfrAddressUnitAsOne = allEmpRfrAddressUnitAsOne;
    }

    //  </editor-fold>
    public void loadAddressTree() {
        allAddresses = hrAddressesBeanLocal.findAll();
        addressRoot = new DefaultTreeNode("Root", null);
        populateAddressNode(allAddresses, 0, addressRoot);
    }

    public void populateAddressNode(List<HrAddresses> addressData, int id, TreeNode node) {
        addresses = new ArrayList<>();
        for (HrAddresses k : getAllAddresses()) {
            if (k.getParentId() == id) {
                TreeNode childNode = new DefaultTreeNode(k.getAddressDescription() + "=>" + k.getAddressId(), node);
                addresses.add(k);
                populateAddressNode(addresses, k.getAddressId(), childNode);
            }
        }
    }

    public void onAddressNodeSelect(NodeSelectEvent event) {
        addressSelectedNode = event.getTreeNode();
        addressId = Integer.parseInt((addressSelectedNode.getData().toString()).split("=>")[1]);
        hrAddresses.setAddressId(addressId);
        hrAddresses = hrAddressesBeanIntegrationLocal.findAllAddressUnitAsOne(hrAddresses);
        setAllAddressUnitAsOne(hrAddresses.getAddressName());
        hrEmpAddresses.setAddressId(hrAddresses);
    }

    public void onEmpEduAddressNodeSelect(NodeSelectEvent event) {
        addressSelectedNode = event.getTreeNode();
        addressId = Integer.parseInt((addressSelectedNode.getData().toString()).split("=>")[1]);
        hrAddresses.setAddressId(addressId);
        hrAddresses = hrAddressesBeanIntegrationLocal.findAllAddressUnitAsOne(hrAddresses);
        setAllEmpEduAddressUnitAsOne(hrAddresses.getAddressName());
        hrEmpEducations.setAddressId(hrAddresses);
    }

    public void onEmpTrnAddressNodeSelect(NodeSelectEvent event) {
        addressSelectedNode = event.getTreeNode();
        addressId = Integer.parseInt((addressSelectedNode.getData().toString()).split("=>")[1]);
        hrAddresses.setAddressId(addressId);
        hrAddresses = hrAddressesBeanIntegrationLocal.findAllAddressUnitAsOne(hrAddresses);
        setAllEmpTrnAddressUnitAsOne(hrAddresses.getAddressName());
        hrEmpTraining.setAddressId(hrAddresses);
    }

    public void onEmpExpAddressNodeSelect(NodeSelectEvent event) {
        addressSelectedNode = event.getTreeNode();
        addressId = Integer.parseInt((addressSelectedNode.getData().toString()).split("=>")[1]);
        hrAddresses.setAddressId(addressId);
        hrAddresses = hrAddressesBeanIntegrationLocal.findAllAddressUnitAsOne(hrAddresses);
        setAllEmpExprianceAddressUnitAsOne(hrAddresses.getAddressName());
        hrEmpExperiences.setAddressId(hrAddresses);
    }

    public void onEmpRfrAddressNodeSelect(NodeSelectEvent event) {
        addressSelectedNode = event.getTreeNode();
        addressId = Integer.parseInt((addressSelectedNode.getData().toString()).split("=>")[1]);
        hrAddresses.setAddressId(addressId);
        hrAddresses = hrAddressesBeanIntegrationLocal.findAllAddressUnitAsOne(hrAddresses);
        setAllEmpRfrAddressUnitAsOne(hrAddresses.getAddressName());
//        hrEmpReferences.setContactAddress(hrAddresses);
    }

    public void onTabEmpAddressNodeSelect(NodeSelectEvent event) {
        addressSelectedNode = event.getTreeNode();
        addressId = Integer.parseInt((addressSelectedNode.getData().toString()).split("=>")[1]);
        hrAddresses.setAddressId(addressId);
        hrAddresses = hrAddressesBeanIntegrationLocal.findAllAddressUnitAsOne(hrAddresses);
        setAllAddressUnitAsOne(hrAddresses.getAddressName());
        hrEmpEducations.setAddressId(hrAddresses);
    }

    //  </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="auto complete search">
//    public ArrayList<HrEmployees> findListByFname_EmpID() {
//        if (hrEmployees.getFirstName().isEmpty() && hrEmployees.getEmpId().isEmpty()) {
//            JsfUtil.addFatalMessage("Please Search Either by Employee Id or Employee Name");
//        } else if (hrEmployees.getEmpId() != null && hrEmployees.getFirstName().isEmpty()) {
//            employeesListDataModel = new ListDataModel(new ArrayList(hrEmployeeBean.SearchByEmpId(hrEmployees)));
//        } else if (!hrEmployees.getFirstName().isEmpty() && hrEmployees.getEmpId().isEmpty()) {
//            employeesListDataModel = new ListDataModel(new ArrayList(hrEmployeeBean.SearchByEmpId(hrEmployees)));
//        } else {
//            JsfUtil.addFatalMessage("Please Insert Either Employee Id or Employee Name");
//        }
//        return null;
//    }
//
//    public void selectEmployee(SelectEvent event) {
//        tabToggle = "tab";
//        tab = "";
//        update = 1;
//        SaveOrUpdateButton = "Update";
//        hrEmployees = (HrEmployees) event.getObject();
//        hrEmployees = hrEmployeeBean.getByEmpId(hrEmployees);
//        CheckEmployeeType(hrEmployees);
//        hrDepartments = hrEmployees.getDeptId();
//        hrDepartments.setDepName(hrDepartments.getDepName());
//        if (hrEmployees.getPhoto() != null) {
//            renderForGraphics = false;
//            renderForGraphicsDb = true;
//        } else {
//            renderForGraphics = true;
//            renderForGraphicsDb = false;
//        }
//        hrPayrollPlPgDept = hrPayrollPgPlDeptBeanLocal.findBydepOfPlPg(hrEmployees.getDeptId().getDepId());
//        if (hrPayrollPlPgDept != null) {
//            setPayLocation(hrPayrollPlPgDept.getPayLocationPayGroupId().getPayLocation().toString());
//            setPayGroup(hrPayrollPlPgDept.getPayLocationPayGroupId().getPayGroup().toString());
//        } else {
//            hrPayrollPlPgDept = new HrPayrollPlPgDept();
//        }
//        fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findByDepIdSystemCostCenter(hrDepartments);
//        if (fmsCostcSystemJunction != null) {
//            setCostCenter(fmsCostcSystemJunction.getFmsCostCenter().getSystemName());
//            setSystem(fmsCostcSystemJunction.getFmsLuSystem().getSystemCode());
//        } else {
//            fmsCostcSystemJunction = new FmsCostcSystemJunction();
//        }
//        if (hrEmployees.getSalaryStep() == null) {
//            basicSalary = false;
//        }
//        allJobTypes = hrRecruitmentRequestsBeanLocal.listOfJobType(hrDepartments.getDepId());
//        hrJobTypes = hrEmployees.getJobId();
//        hrDeptJobs.setJobId(hrJobTypes);
//        setJobCode(hrJobTypes.getJobCode());
//        setJobLevel(hrJobTypes.getJobLevelId().getJobLevel());
//        hrSalaryScaleRanges = hrJobTypes.getJobGradeId();
//        CheckSalaryScaleRanges(hrEmployees);
//        CheckSalarySteps(hrEmployees);
//        setEmpSalary(hrEmployees.getBasicSalary());
//        hrLuBanks = hrEmployees.getBankName();
//        hrLuBankBranches = hrEmployees.getBankBranch();
//        hrLuNationalities = hrEmployees.getNationalityId();
//        hrLuTitles = hrEmployees.getTitleId();
//        renderPnlCreateGatePass = true;
//        renderPnlManPage = false;
//        renderCandidate=false;
//        createOrSearchBundle = "Search";
//        setIcone("ui-icon-search");
//        setIcone("ui-icon-search");
//        recreateEmpTrain();
//        recreateEmpFamily();
//        recreateEmpLanguage();
//        recreateEmpEducation();
//        recreateEmpRefencePop();
//        recreateEmpSkill();
//        recreateEmpExpriances();
//        recreateEmpAddress();
//        recreateEmpmembershipe();
//    }
    private boolean compareSalary() {
        for (HrSalaryScales salaryScale : hrEmployeeBean.searchSalaryStepsInfo(hrSalaryScaleRanges)) {
            if (salaryScale.getSalary() != null && hrEmployeeBean.searchSalaryStepsInfo(hrSalaryScaleRanges) != null) {
                if (hrEmployees.getBasicSalary().equals(hrSalaryScales.getSalary())) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<HrEmployees> searchByEmpId(String empId) {
        ArrayList<HrEmployees> employeeId = null;
        hrEmployees.setEmpId(empId);
        employeeId = hrEmployeeBean.SearchByEmpId(hrEmployees);
        return employeeId;
    }

    public void getByEmpId(SelectEvent event) {
        try {
            if (event.getObject() != null) {
                tabToggle = "tab";
                tab = "";
                update = 1;
                SaveOrUpdateButton = "Update";
                hrEmployees = (HrEmployees) event.getObject();
                CheckEmployeeType(hrEmployees);
                hrDepartments = hrEmployees.getDeptId();
                hrDepartments.setDepName(hrDepartments.getDepName());
                if (hrEmployees.getPhoto() != null) {
                    renderForGraphics = false;
                    renderForGraphicsDb = true;
                } else {
                    renderForGraphics = true;
                    renderForGraphicsDb = false;
                }
                hrPayrollPlPgDept = hrPayrollPgPlDeptBeanLocal.findBydepOfPlPg(hrEmployees.getDeptId().getDepId());
                if (hrPayrollPlPgDept != null) {
                    setPayLocation(hrPayrollPlPgDept.getPayLocationPayGroupId().getPayLocation().toString());
                    setPayGroup(hrPayrollPlPgDept.getPayLocationPayGroupId().getPayGroup().toString());
                } else {
                    hrPayrollPlPgDept = new HrPayrollPlPgDept();
                }
                fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findbyDeptId(hrDepartments.getDepId());
                if (fmsCostcSystemJunction != null) {
                    if (fmsCostcSystemJunction.getFmsCostCenter() != null) {
                        setCostCenter(fmsCostcSystemJunction.getFmsCostCenter().getSystemName());
                    } else {
                        setCostCenter("");
                    }
                    if (fmsCostcSystemJunction.getFmsLuSystem() != null) {
                        setSystem(fmsCostcSystemJunction.getFmsLuSystem().getSystemCode());
                    } else {
                        setSystem("");
                    }
                } else {
                    fmsCostcSystemJunction = new FmsCostcSystemJunction();
                }
                if (hrEmployees.getSalaryStep() == null) {
                    basicSalary = false;
                }
                allJobTypes = hrRecruitmentRequestsBeanLocal.listOfJobType(hrDepartments.getDepId());
                hrJobTypes = hrEmployees.getJobId();
                hrDeptJobs.setJobId(hrJobTypes);
                setJobCode(hrJobTypes.getJobCode());
                setJobLevel(hrJobTypes.getJobLevelId().getJobLevel());
                hrSalaryScaleRanges = hrJobTypes.getJobGradeId();
                hrLuSalarySteps = hrEmployees.getSalaryStep();
                System.out.println("hrEmployees.getSalaryStep()" + hrEmployees.getSalaryStep());
                hrLuSalarySteps.setSalaryStep(hrEmployees.getSalaryStep().getId());
                System.out.println("hrEmployees.getSalaryStep().getId()" + hrEmployees.getSalaryStep().getId());
                hrLuSalarySteps.setSalaryStep(hrLuSalarySteps.getSalaryStep());
                System.out.println("hrLuSalarySteps.getSalaryStep()" + hrLuSalarySteps.getSalaryStep());
//                CheckSalaryScaleRanges(hrEmployees);
                // CheckSalarySteps(hrEmployees);
                if (hrEmployees.getSalaryStep() != null) {
                    hrLuSalarySteps.setSalaryStep(hrEmployees.getSalaryStep().getId());
                    System.out.println("==============" + hrLuSalarySteps.getSalaryStep());
                } else {
                    if (hrEmployees.getBasicSalary().equals(hrSalaryScaleRanges.getMinSalary())) {
                        hrLuSalarySteps.setSalaryStep(-2);
                    } else if (hrEmployees.getBasicSalary().equals(hrSalaryScaleRanges.getMaxSalary())) {
                        hrLuSalarySteps.setSalaryStep(-1);
                    } //                else if (compareSalary()) {
                    //                    if (hrEmployees.getSalaryStep().equals(hrSalaryScales.getSalaryStepId()) && hrSalaryScaleRanges.equals(hrSalaryScales.getSalaryRangeId())) {
                    //                        hrLuSalarySteps.setSalaryStep(hrEmployees.getSalaryStep().getId());
                    //                    } else {
                    //                        hrLuSalarySteps.setSalaryStep(0);
                    //                    }
                    //                }
                    else {
                        hrLuSalarySteps.setSalaryStep(0);
                    }
                }
                setEmpSalary(hrEmployees.getBasicSalary());
                hrLuBanks = hrEmployees.getBankName();
                hrLuBankBranches = hrEmployees.getBankBranch();
                hrLuNationalities = hrEmployees.getNationalityId();
                hrLuTitles = hrEmployees.getTitleId();
                CheckEmployeeType(hrEmployees);
                renderPnlCreateGatePass = true;
                renderPnlManPage = true;
                renderCandidate = false;
                createOrSearchBundle = "Search";
                setIcone("ui-icon-search");
                setIcone("ui-icon-search");
                recreateEmpTrain();
                recreateEmpFamily();
                recreateEmpLanguage();
                recreateEmpEducation();
                recreateEmpRefencePop();
                recreateEmpSkill();
                recreateEmpExpriances();
                recreateEmpAddress();
                recreateEmpmembershipe();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Exception");
        }
    }

//    public ArrayList<HrEmployees> searchByFname(String fname) {
//        ArrayList<HrEmployees> firstName = null;
//        hrEmployees.setFirstName(fname);
//        firstName = hrEmployeeBean.SearchByFname(hrEmployees);
//        return firstName;
//    }
//    public void getByFirstName(SelectEvent event) {
//          try {
//            if (event.getObject() != null) {
//                tabToggle = "tab";
//                tab = "";
//                update = 1;
//                SaveOrUpdateButton = "Update";
//                hrEmployees = (HrEmployees) event.getObject();
//                CheckEmployeeType(hrEmployees);
//                hrDepartments = hrEmployees.getDeptId();
//                hrDepartments.setDepName(hrDepartments.getDepName());
//                if (hrEmployees.getPhoto() != null) {
//                    renderForGraphics = false;
//                    renderForGraphicsDb = true;
//                } else {
//                    renderForGraphics = true;
//                    renderForGraphicsDb = false;
//                }
//                hrPayrollPlPgDept = hrPayrollPgPlDeptBeanLocal.findBydepOfPlPg(hrEmployees.getDeptId().getDepId());
//                if (hrPayrollPlPgDept != null) {
//                    setPayLocation(hrPayrollPlPgDept.getPayLocationPayGroupId().getPayLocation().toString());
//                    setPayGroup(hrPayrollPlPgDept.getPayLocationPayGroupId().getPayGroup().toString());
//                } else {
//                    hrPayrollPlPgDept = new HrPayrollPlPgDept();
//                }
//                fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findbyDeptId(hrDepartments.getDepId());
//                if (fmsCostcSystemJunction != null) {
//                    if (fmsCostcSystemJunction.getFmsCostCenter() != null) {
//                        setCostCenter(fmsCostcSystemJunction.getFmsCostCenter().getSystemName());
//                    } else {
//                        setCostCenter("");
//                    }
//                    if (fmsCostcSystemJunction.getFmsLuSystem() != null) {
//                        setSystem(fmsCostcSystemJunction.getFmsLuSystem().getSystemCode());
//                    } else {
//                        setSystem("");
//                    }
//                } else {
//                    fmsCostcSystemJunction = new FmsCostcSystemJunction();
//                }
//                if (hrEmployees.getSalaryStep() == null) {
//                    basicSalary = false;
//                }
//                allJobTypes = hrRecruitmentRequestsBeanLocal.listOfJobType(hrDepartments.getDepId());
//                hrJobTypes = hrEmployees.getJobId();
//                hrDeptJobs.setJobId(hrJobTypes);
//                setJobCode(hrJobTypes.getJobCode());
//                setJobLevel(hrJobTypes.getJobLevelId().getJobLevel());
//                hrSalaryScaleRanges = hrJobTypes.getJobGradeId();
////                CheckSalaryScaleRanges(hrEmployees);
////                CheckSalarySteps(hrEmployees);
//                if (hrEmployees.getSalaryStep() != null) {
//                } else {
//                    basicSalary = false;
//                }
//                if (hrEmployees.getBasicSalary().equals(hrSalaryScaleRanges.getMinSalary())) {
////                    if (hrEmployees.getSalaryStep().equals(hrSalaryScales.getSalaryStepId()) && hrSalaryScaleRanges.equals(hrSalaryScales.getSalaryRangeId())) {
//                    hrLuSalarySteps.setSalaryStep(-2);
////                    } else {
////                        hrLuSalarySteps.setSalaryStep(0);
////                    }
//
//                } else if (hrEmployees.getBasicSalary().equals(hrSalaryScaleRanges.getMaxSalary())) {
////                    if (hrEmployees.getSalaryStep().equals(hrSalaryScales.getSalaryStepId()) && hrSalaryScaleRanges.equals(hrSalaryScales.getSalaryRangeId())) {
//                    hrLuSalarySteps.setSalaryStep(-1);
////                    } else {
////                        hrLuSalarySteps.setSalaryStep(0);
////                    }
//                } else if (compareSalary()) {
//                    if (hrEmployees.getSalaryStep().equals(hrSalaryScales.getSalaryStepId()) && hrSalaryScaleRanges.equals(hrSalaryScales.getSalaryRangeId())) {
//                        hrLuSalarySteps.setSalaryStep(hrEmployees.getSalaryStep().getId());
//                    } else {
//                        hrLuSalarySteps.setSalaryStep(0);
//                    }
//                } else {
//                    hrLuSalarySteps.setSalaryStep(0);
//                }
//                setEmpSalary(hrEmployees.getBasicSalary());
//                hrLuBanks = hrEmployees.getBankName();
//                hrLuBankBranches = hrEmployees.getBankBranch();
//                hrLuNationalities = hrEmployees.getNationalityId();
//                hrLuTitles = hrEmployees.getTitleId();
//                CheckEmployeeType(hrEmployees);
//                renderPnlCreateGatePass = true;
//                renderPnlManPage = true;
//                renderCandidate = false;
//                createOrSearchBundle = "Search";
//                setIcone("ui-icon-search");
//                setIcone("ui-icon-search");
//                recreateEmpTrain();
//                recreateEmpFamily();
//                recreateEmpLanguage();
//                recreateEmpEducation();
//                recreateEmpRefencePop();
//                recreateEmpSkill();
//                recreateEmpExpriances();
//                recreateEmpAddress();
//                recreateEmpmembershipe();
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            System.err.println("Exception");
//        }
//    }
    public void Clear() {
        hrEmployees = null;
        hrDepartments = null;
        hrLuNationalities = null;
        hrLuTitles = null;
        hrJobTypes = null;
        hrLuBankBranches = null;
        hrLuSalarySteps = null;
        hrLuSalarySteps = null;
        hrLuBanks = null;
        hrLuGrades = null;
        fmsCostCenter = null;
        payLocation = null;
        payGroup = null;
        costCenter = null;
        system = null;
        jobLevel = null;
        jobCode = null;
        SaveOrUpdateButton = "Save";
        update = 0;
    }
//</editor-fold>
    //  <editor-fold defaultstate="collapsed" desc="Main">

    public void requestcounter() {
        hrEmployeesList = hrEmployeesFacade.findAll();
        String currentDate[] = StringDateManipulation.toDayInEc().split("-");
        int currentday = Integer.parseInt(currentDate[2]);
        int currentmonth = Integer.parseInt(currentDate[1]);
        int currentyear = Integer.parseInt(currentDate[0]);
        for (int j = 0; j < hrEmployeesList.size(); j++) {
            int expday = 0;
            if (hrEmployeesList.get(j).getEmploymentType() != null) {
                if (hrEmployeesList.get(j).getEmploymentType().equalsIgnoreCase("Contract")) {
                    if (hrEmployeesList.get(j).getContractEndDate() != null) {
                        String endDate[] = hrEmployeesList.get(j).getContractEndDate().split("/");
                        int endday = Integer.parseInt(endDate[0]);
                        int endmonth = Integer.parseInt(endDate[1]);
                        int endyear = Integer.parseInt(endDate[2]);
                        expday = (endday - currentday) + ((endmonth - currentmonth) * 30) + ((endyear - currentyear) * 365);
                        if (expday >= 0 && expday <= 60) {
                            hrEmployeesnotificationList.add(hrEmployeesList.get(j));
                        }
                    }

                }
            }
        }
        requestNotificationCounter = hrEmployeesnotificationList.size();
    }

    public void createNewGatePassInfo() {
        SaveOrUpdateButton = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateGatePass = true;
            renderPnlManPage = false;
            renderCandidate = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
            renderForGraphics = true;
            renderForGraphicsDb = false;
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGatePass = false;
            renderPnlManPage = true;
            renderCandidate = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
            if (hrEmployees.getPhoto() != null) {
                renderForGraphics = false;
                renderForGraphicsDb = true;
            } else {
                renderForGraphics = true;
                renderForGraphicsDb = false;
            }
        }
    }

    public void recursive(List<HrDepartments> liste, int id, TreeNode node) {
        araListe = new ArrayList<>();
        if (allDepartmentsList != null) {
            for (HrDepartments k : allDepartmentsList) {
                if (k.getParentId() != null && k.getParentId() == id) {
                    TreeNode childNode = new DefaultTreeNode(k.getDepId() + "--" + k.getDepName(), node);
                    araListe.add(k);
                    recursive(araListe, k.getDepId(), childNode);
                }
            }
        }
    }

    public void activeTabs() {
        if (hrEmployees.getEmpId() != null && hrEmployees.getFirstName() != null && hrEmployees.getMiddleName() != null
                && hrEmployees.getLastName() != null && hrEmployees.getEmploymentType() != null && hrEmployees.getSex() != null
                && hrEmployees.getDob() != null) {
            tabToggle = "tab";
            tab = "";
        }

    }

    public void onDeptFilled(HrDepartments dep) {
        hrRecruitmentRequestsBeanLocal.listOfJobType(dep.getDepId());
    }

    public void recreateEmployeeModel() {
        employeesListDataModel = null;
        employeesListDataModel = new ListDataModel(EmployeeList);
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Employee Selected", ((HrEmployees) event.getObject()).getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Employee Unselected", ((HrEmployees) event.getObject()).getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void renderContractEndDate(SelectEvent event) {
        String contractType = hrEmployees.getEmploymentType();
        if (contractType.equalsIgnoreCase("Contract")) {
            hiddenVisipllit = "display";
        } else {
            hiddenVisipllit = "hidden";
        }
    }

    public void onNodeSelectSearch(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int key = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);
        hrDepartments = hrRecruitmentRequestsBeanLocal.getSelectDepartement(key);
        hrEmployees.setDeptId(hrDepartments);

    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public FmsCostcSystemJunction getFmsCostcSystemJunction() {
        if (fmsCostcSystemJunction == null) {
            fmsCostcSystemJunction = new FmsCostcSystemJunction();
        }
        return fmsCostcSystemJunction;
    }

    public void setFmsCostcSystemJunction(FmsCostcSystemJunction fmsCostcSystemJunction) {
        this.fmsCostcSystemJunction = fmsCostcSystemJunction;
    }

    @EJB
    FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal;

    public void CheckSalarySteps(HrEmployees hrEmployees) {
        if (hrEmployees.getSalaryStep() != null && hrEmployees.getBasicSalary() != 0) {
            hrLuSalarySteps.setSalaryStep(hrEmployees.getSalaryStep().getSalaryStep());
        } else {
            hrLuSalarySteps = new HrLuSalarySteps();
        }
    }

    public void CheckSalaryScaleRanges(HrEmployees hrEmployees) {
        if (hrSalaryScaleRanges != null) {
            hrLuGrades = hrSalaryScaleRanges.getGradeId();
        } else {
            hrLuGrades = new HrLuGrades();
        }
    }

    public void CheckEmployeeType(HrEmployees hrEmployees) {
        if (hrEmployees.getEmploymentType().equalsIgnoreCase("Permanent")) {
            endDateRendeerLbl = false;
            endDateRendeerTxt = false;
        } else {
            endDateRendeerLbl = true;
            endDateRendeerTxt = true;
        }
    }

    public void ageController() {
        if ((hrEmployees.getDob() != null) && (hrEmployees.getDob().compareTo("") != 0)) {
            if ((hrEmployees.getDob().contains("/")) && (hrEmployees.getHireDate().contains("/"))) {

                String currentDate[] = StringDateManipulation.toDayInEc().split("-");
                int currentday = Integer.parseInt(currentDate[2]);
                int currentmonth = Integer.parseInt(currentDate[1]);
                int currentyear = Integer.parseInt(currentDate[0]);

                String[] DOBdate = hrEmployees.getDob().split("/");
                int dobday = Integer.parseInt(DOBdate[0]);
                int dobmonth = Integer.parseInt(DOBdate[1]);
                int dobyear = Integer.parseInt(DOBdate[2]);

                String[] hiredate = hrEmployees.getHireDate().split("/");
                int hireday = Integer.parseInt(hiredate[0]);
                int hiremonth = Integer.parseInt(hiredate[1]);
                int hireyear = Integer.parseInt(hiredate[2]);

                int calculatedAge = (currentday - dobday) + ((currentmonth - dobmonth) * 30) + ((currentyear - dobyear) * 365);
                int calculateHireDate = (hireday - dobday) + ((hiremonth - dobmonth) * 30) + ((hireyear - dobyear) * 365);
                double age = calculatedAge / 365;
                double hireage = calculateHireDate / 365;
                if ((age >= 18 && age < 100) && (hireage >= 18 && hireage < 100)) {
                    if (hrEmployees.getEmploymentType().equalsIgnoreCase("Contract")) {
                        hiredateValidation();
                    } else {
                        heightChecker();
                    }
                } else {
                    JsfUtil.addFatalMessage("Date of birth or hired date is incorrect");
                }
            }
        }
    }

    //mahi
    public void hiredateValidation() {
        if ((hrEmployees.getHireDate() != null) && (hrEmployees.getHireDate().compareTo("") != 0)
                && (hrEmployees.getContractEndDate() != null) && (hrEmployees.getContractEndDate().compareTo("") != 0)
                && (hrEmployees.getEmploymentType().equalsIgnoreCase("Contract"))) {
            if ((hrEmployees.getHireDate().contains("/")) && (hrEmployees.getContractEndDate().contains("/"))) {
                String[] dateFromHDUI = hrEmployees.getHireDate().split("/");
                String[] dateFromCDUI = hrEmployees.getContractEndDate().split("/");
                hdate = hrEmployees.getHireDate();
                hdate = dateFromHDUI[2] + "/" + dateFromHDUI[1] + "/" + dateFromHDUI[0];
                setHdate2nd(dateFromHDUI[2] + "-" + dateFromHDUI[1] + "-" + dateFromHDUI[0]);
                contenddate = hrEmployees.getContractEndDate();
                contenddate = dateFromCDUI[2] + "/" + dateFromCDUI[1] + "/" + dateFromCDUI[0];
                setContenddate2nd(dateFromCDUI[2] + "-" + dateFromCDUI[1] + "-" + dateFromCDUI[0]);
                int calculatedContDate = StringDateManipulation.compareDate(contenddate2nd, hdate2nd);
                if (calculatedContDate > 0) {
                    heightChecker();
                } else {
                    JsfUtil.addFatalMessage("Contract end date must be after hired date");
                }
            }
        }
    }

    public void hireDateController() {
        if ((hrEmployees.getHireDate() != null) && (hrEmployees.getHireDate().compareTo("") != 0)
                && (hrEmployees.getContractEndDate() != null) && (hrEmployees.getContractEndDate().compareTo("") != 0)) {
            if (hrEmployees.getHireDate().contains("/")) {
                String hireOfDate;
                String conEndOfDate;
                String[] hireDateFromUI = hrEmployees.getHireDate().split("/");
                String[] hireEndDateFromUI = hrEmployees.getContractEndDate().split("/");
                hireOfDate = hireDateFromUI[2] + "-" + hireDateFromUI[1] + "-" + hireDateFromUI[0];
                conEndOfDate = hireEndDateFromUI[2] + "-" + hireEndDateFromUI[1] + "-" + hireEndDateFromUI[0];
                int calculateDiff = stringDateManipulation.differenceInDays(conEndOfDate, hireOfDate);
                if (calculateDiff >= 1) {
                    heightChecker();
                } else {
                    JsfUtil.addFatalMessage("Contract end date must be after hired date");
                }
            }
        }
    }

    public void heightChecker() {
        if ((hrEmployees.getHeight() != null)) {
            if (hrEmployees.getHeight() >= 50 && hrEmployees.getHeight() <= 300) {
                weightChecker();
            } else {
                JsfUtil.addFatalMessage("Please insert height between 50 and 300");
            }
        } else {
            weightChecker();
        }
    }

    public void weightChecker() {
        if ((hrEmployees.getWeight() != null)) {
            if (hrEmployees.getWeight() >= 30 && hrEmployees.getWeight() <= 200) {
                CREATEEMPLOYEE();
            } else {
                JsfUtil.addFatalMessage("Please insert weight between 30 and 200");
            }
        } else {
            CREATEEMPLOYEE();
        }
    }
    //  </editor-fold>
    //<editor-fold defaultstate="Collapsed" desc="CRUD">
    HrEmployees hrEmployeesNew;

    public HrEmployees getHrEmployeesNew() {
        return hrEmployeesNew;
    }

    public void setHrEmployeesNew(HrEmployees hrEmployeesNew) {
        this.hrEmployeesNew = hrEmployeesNew;
    }

    public void CREATEEMPLOYEE() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "CREATEEMPLOYEE", dataset)) {
                try {
                    if (update == 0) {
                        if (hrEmployeeBean.checkEmpId(hrEmployees.getEmpId()) == null) {
                            hrEmployees.setPhoto(byteData);
                            hrEmployees.setEmpStatus(1);
                            if (saveCandidate) {
                                hrCandidiates.setStatus(HrCandidiates.REGISTERED_EMPLOYEE);
                                hrEmployeeBean.saveCandidate(hrCandidiates, hrEmployees);
                            } else {
                                hrEmployeeBean.saveOrUpdate(hrEmployees);
                            }
                            if (hrEmployees.getEmploymentType().equalsIgnoreCase("Contract")) {
                                hrEmpContracts.setFromDate(hrEmployees.getHireDate());
                                hrEmpContracts.setToDate(hrEmployees.getContractEndDate());
                                hrEmpContracts.setEmpId(hrEmployees);
                                hrEmpContracts.setReason("first contract");
                                hrEmpContracts.setStatus("Active");
                                hrEmpContractsBeanLocal.SaveOrUpdate(hrEmpContracts);
                            }
                            if (hrEmployees.getPhoto() != null) {
                                renderForGraphics = false;
                                renderForGraphicsDb = true;
                            } else {
                                renderForGraphics = true;
                                renderForGraphicsDb = false;
                            }
                            tabToggle = "tab";
                            tab = "";
                            update = 1;
                            SaveOrUpdateButton = "Update";
                            JsfUtil.addSuccessMessage("Successfully Saved.");
                        } else {
                            JsfUtil.addFatalMessage("Duplicate Employee Id.");
                        }
                    } else {
                        if (byteData != null) {
                            hrEmployees.setPhoto(byteData);
                            renderForGraphics = false;
                            renderForGraphicsDb = true;
                        }
                        hrEmployeeBean.saveOrUpdate(hrEmployees);
                        if (hrEmployees.getEmploymentType().equalsIgnoreCase("Contract")) {
                            hrEmpContracts.setFromDate(hrEmployees.getHireDate());
                            hrEmpContracts.setToDate(hrEmployees.getContractEndDate());
                            hrEmpContracts.setEmpId(hrEmployees);
                            hrEmpContracts.setReason("first contract");
                            hrEmpContractsBeanLocal.SaveOrUpdate(hrEmpContracts);
                        }
                        JsfUtil.addSuccessMessage("Successfully updated.");
                    }
                    clearDataTable();
                } catch (Exception ex) {
                    JsfUtil.addFatalMessage("Error occured while saving.");
                    ex.printStackTrace();
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void clearDataTable() {
        hrEmpEducations = null;
        hrEmpLanguages = null;
        hrEmpFamilies = null;
        hrEmpTraining = null;
        hrEmpAddresses = null;
        hrEmpExperiences = null;
        hrEmpSkill = null;
        hrEmpMemberships = null;
        hrEmpReferences = null;
        saveCandidate = false;
    }

    public ArrayList<HrSalaryScales> getSalarySetpsByJobTitles() {
        if (hrJobTypes.getJobTitle() != null) {
            return hrEmployeeBean.searchSalaryStepsInfo(hrSalaryScaleRanges);
        } else {
            return null;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="photo upload">
    public void handleFileUpload(FileUploadEvent event) {
        filename = event.getFile().getFileName().split("\\.")[0];
        try {
            byteData = extractByteArray(event.getFile().getInputstream());
            hrEmployees.setPhoto(byteData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] extractByteArray(InputStream inputStream1) {
        try {
            byte[] byteFile = null;
            int len = 0;
            len = inputStream1.available();
            byteFile = new byte[len];
            inputStream1.read(byteFile, 0, len);
            return byteFile;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
//</editor-fold>

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="candidate">
    List<HrCandidiates> candidates = new ArrayList<>();

    DataModel<HrCandidiates> candidateListDataModel = new ListDataModel<>();

    public void recreateCandidateTraining() {
        hrEmpTrainDataModel = null;
        HrEmpTrainings hrEmpTra = null;
        hrEmpTrainDataModel = new ListDataModel(new ArrayList(hrCandidiates.getHrCandidateTrainingsList()));
        if (hrCandidiates.getHrCandidateTrainingsList().size() > 0) {
            for (HrCandidateTrainings candTraining : hrCandidiates.getHrCandidateTrainingsList()) {
                hrEmpTra = new HrEmpTrainings();
                hrEmpTra.setTrainingTitle(candTraining.getTrainingTitle());
                hrEmpTra.setInstitution(candTraining.getInstitution());
                hrEmpTra.setStartDate(candTraining.getStartDate());
                hrEmpTra.setEndDate(candTraining.getEndDate());
                hrEmpTra.setAddressId(candTraining.getAddressId());
                hrEmpTra.setSponsoredBy(candTraining.getSponsoredBy());
                hrEmployees.addToEmpTrain(hrEmpTra);
            }
        }
    }

    public void recreateCandidateEducation() {
        HrEmpEducations hrEmpEduc = null;
        hrEmpEducationsDataModel = null;
        hrEmpEducationsDataModel = new ListDataModel(new ArrayList(hrCandidiates.getHrCandidiateEducationsList()));
        if (hrCandidiates.getHrCandidiateEducationsList().size() > 0) {
            for (HrCandidiateEducations candEducation : hrCandidiates.getHrCandidiateEducationsList()) {
                hrEmpEduc = new HrEmpEducations();
                hrEmpEduc.setInstitution(candEducation.getInstitution());
                hrEmpEduc.setAward(candEducation.getAward());
                hrEmpEduc.setEducResult(String.valueOf(candEducation.getEducResult()));
                hrEmpEduc.setSponsoredBy(candEducation.getSponsoredBy());
                hrEmpEduc.setStartDate(candEducation.getStartDate());
                hrEmpEduc.setEducLevelId(candEducation.getEducLevelId());
                hrEmpEduc.setAddressId(candEducation.getAddressId());
                hrEmpEduc.setEducTypeId(candEducation.getEducTypeId());
                hrEmployees.addToEmpEducation(hrEmpEduc);
            }
        }
    }

    public void recreateCandidateEmpHistor() {
        HrEmpExperiences hrEmpExpr = null;
        hrEmpExpriancesDataModel = null;
        hrEmpExpriancesDataModel = new ListDataModel(new ArrayList(hrCandidiates.getHrCandidateEmpHistoriesList()));
        if (hrCandidiates.getHrCandidateEmpHistoriesList().size() > 0) {
            for (HrCandidateEmpHistories candemphis : hrCandidiates.getHrCandidateEmpHistoriesList()) {
                hrEmpExpr = new HrEmpExperiences();
                hrEmpExpr.setInstitution(candemphis.getInstitution());
                hrEmpExpr.setJobTitle(candemphis.getJobTitle());
                hrEmpExpr.setSalary(Float.valueOf(candemphis.getSalary()));
                hrEmpExpr.setStartDate(candemphis.getStartDate());
                hrEmpExpr.setEndDate(candemphis.getEndDate());
                hrEmpExpr.setKeyDutyResponsibility(candemphis.getKeyDutyResponsibility());
                hrEmpExpr.setReasonForTermination(candemphis.getReasonForTermination());
                hrEmployees.addToEmpExpriance(hrEmpExpr);
            }
        }
    }

    public void recreateCandidateLanguage() {
        HrEmpLanguages empLanguage = null;
        hrEmpLanguagesDataModel = null;
        hrEmpLanguagesDataModel = new ListDataModel(new ArrayList(hrCandidiates.getHrCandidateLanguagesList()));
        if (hrCandidiates.getHrCandidateLanguagesList().size() > 0) {
            for (HrCandidateLanguages candlenguage : hrCandidiates.getHrCandidateLanguagesList()) {
                empLanguage = new HrEmpLanguages();
                empLanguage.setListening(candlenguage.getListening());
                empLanguage.setReading(candlenguage.getReading());
                empLanguage.setWriting(candlenguage.getWriting());
                empLanguage.setSpeaking(candlenguage.getSpeaking());
                empLanguage.setLanguageId(candlenguage.getLanguageId());
                hrEmployees.addToEmpLang(empLanguage);
            }
        }
    }

    public void recreateCandidateReference() {
        HrEmpReferences empReference = null;
        hrEmpReferenceDataModel = null;
        hrEmpReferenceDataModel = new ListDataModel(new ArrayList(hrCandidiates.getHrCandidateReferencesList()));
        if (hrCandidiates.getHrCandidateReferencesList().size() > 0) {
            for (HrCandidateReferences candrefer : hrCandidiates.getHrCandidateReferencesList()) {
                empReference = new HrEmpReferences();
                empReference.setEmail(candrefer.getEmail());
                empReference.setJobTitle(candrefer.getJobTitle());
                empReference.setNameOfReferee(candrefer.getNameOfReferee());
                empReference.setPhoneNumber(candrefer.getPhoneNumber());
                empReference.setRelationshipType(candrefer.getRelationshipType());
                empReference.setRemark(candrefer.getRemark());
                hrEmployees.addToEmpReference(empReference);
            }
        }

    }
    boolean saveCandidate = false;

    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public HrCandidiates getHrCandidiates() {
        if (hrCandidiates == null) {
            hrCandidiates = new HrCandidiates();
        }
        return hrCandidiates;
    }

    public void setHrCandidiates(HrCandidiates hrCandidiates) {
        this.hrCandidiates = hrCandidiates;
    }

    public List<HrCandidiates> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<HrCandidiates> candidates) {
        this.candidates = candidates;
    }

    public DataModel<HrCandidiates> getCandidateListDataModel() {
        return candidateListDataModel;
    }

    public void setCandidateListDataModel(DataModel<HrCandidiates> candidateListDataModel) {
        this.candidateListDataModel = candidateListDataModel;
    }

//</editor-fold>
    public void loadCandidateProfile(SelectEvent event) {
        if (event.getObject() != null) {
            saveCandidate = true;
            hrCandidiates = (HrCandidiates) event.getObject();
            hrEmployees.setFirstName(hrCandidiates.getFirstName());
            hrEmployees.setMiddleName(hrCandidiates.getMiddleName());
            hrEmployees.setLastName(hrCandidiates.getLastName());
            hrEmployees.setSex(hrCandidiates.getSex());
            hrEmployees.setMaritalStatus(hrCandidiates.getMaritalStatus());
            hrEmployees.setDob(hrCandidiates.getDateOfBirth());
            hrEmployees.setNationalityId(hrCandidiates.getNationality());
            hrLuNationalities = hrEmployees.getNationalityId();
            tabToggle = "tab";
            tab = "";
            update = 0;
            SaveOrUpdateButton = "Save";
            renderPnlCreateGatePass = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
            setIcone("ui-icon-search");
            recreateCandidateTraining();
            recreateCandidateEducation();
            recreateCandidateEmpHistor();
            recreateCandidateLanguage();
            recreateCandidateReference();
        }
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Barchart">
    //<editor-fold defaultstate="collapsed" desc="chart">
    ChartSeries maleChart = new ChartSeries();
    ChartSeries femaleChart = new ChartSeries();

    public ChartSeries getMaleChart() {
        return maleChart;
    }

    public void setMaleChart(ChartSeries maleChart) {
        this.maleChart = maleChart;
    }

    public ChartSeries getFemaleChart() {
        return femaleChart;
    }

    public void setFemaleChart(ChartSeries femaleChart) {
        this.femaleChart = femaleChart;
    }

//</editor-fold>
    public class EmpStatusClass implements Serializable {

        String Emp_status;
        int males;
        int females;
        int total;

        //<editor-fold defaultstate="collapsed" desc="Getter and setter">
        public String getEmp_status() {
            return Emp_status;
        }

        public void setEmp_status(String Emp_status) {
            this.Emp_status = Emp_status;
        }

        public int getMales() {
            return males;
        }

        public void setMales(int males) {
            this.males = males;
        }

        public int getFemales() {
            return females;
        }

        public void setFemales(int females) {
            this.females = females;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

//</editor-fold>
        public EmpStatusClass SetData(String status, int maleCount, int femaleCount, int sub_total) {
            this.Emp_status = status;
            this.males = maleCount;
            this.females = femaleCount;
            this.total = sub_total;
            return this;

        }

    }
    EmpStatusClass empStatusClass = new EmpStatusClass();
    List<EmpStatusClass> currentEmpStatusList = new ArrayList<>();
    int totalEmployeeConut = 0;

    //<editor-fold defaultstate="collapsed" desc="getter and setter for dashbord items">
    public EmpStatusClass getEmpStatusClass() {
        return empStatusClass;
    }

    public void setEmpStatusClass(EmpStatusClass empStatusClass) {
        this.empStatusClass = empStatusClass;
    }

    public List<EmpStatusClass> getCurrentEmpStatusList() {
        return currentEmpStatusList;
    }

    public void setCurrentEmpStatusList(List<EmpStatusClass> currentEmpStatusList) {
        this.currentEmpStatusList = currentEmpStatusList;
    }
//</editor-fold>

    public void empStatusChartCreator() {
        List<HrEmployees> allemp = new ArrayList<>();
        int activeMaleCount = 0;
        int onLeaveMaleCount = 0;
        int terminatedMaleCount = 0;
        int onProbationMalesCount = 0;

        int activeFemaleCount = 0;
        int onLeaveFemaleCount = 0;
        int terminatedFemaleCount = 0;
        int onProbationFemalesCount = 0;
        totalEmployeeConut = 0;

        Integer maleSubTotal = 0;
        Integer femaleSubTotal = 0;

        allemp = hrEmployeeBean.findAll();
        totalEmployeeConut = allemp.size();
        for (int i = 0;
                i < allemp.size();
                i++) {
            if (allemp.get(i).getSex().equalsIgnoreCase("Male")) {
                if (allemp.get(i).getEmpStatus() == 1) {
                    activeMaleCount = activeMaleCount + 1;
                } else if (allemp.get(i).getEmpStatus() == 2) {
                    onLeaveMaleCount = onLeaveMaleCount + 1;
                } else if (allemp.get(i).getEmpStatus() == 6) {
                    onProbationMalesCount = onProbationMalesCount + 1;
                } else {
                    terminatedMaleCount = terminatedMaleCount + 1;
                }
            } else {
                if (allemp.get(i).getEmpStatus() == 1) {
                    activeFemaleCount = activeFemaleCount + 1;
                } else if (allemp.get(i).getEmpStatus() == 2) {
                    onLeaveFemaleCount = onLeaveFemaleCount + 1;
                } else if (allemp.get(i).getEmpStatus() == 6) {
                    onProbationFemalesCount = onProbationFemalesCount + 1;
                } else {
                    terminatedFemaleCount = terminatedFemaleCount + 1;
                }
            }

        }
        String EmpType = "";
        for (int j = 0;
                j < 4; j++) {
            if (j == 0) {
                currentEmpStatusList.add(empStatusClass.SetData("Active", activeMaleCount, activeFemaleCount, activeMaleCount + activeFemaleCount));
                EmpType = "Active";
                maleSubTotal = activeMaleCount;
                femaleSubTotal = activeFemaleCount;
            }
            if (j == 1) {
                currentEmpStatusList.add(empStatusClass.SetData("OnLeave", onLeaveMaleCount, onLeaveFemaleCount, onLeaveMaleCount + onLeaveFemaleCount));
                EmpType = "OnLeave";
                maleSubTotal = onLeaveMaleCount;
                femaleSubTotal = onLeaveFemaleCount;
            }
            if (j == 2) {
                currentEmpStatusList.add(empStatusClass.SetData("OnProbation", onProbationMalesCount, onProbationFemalesCount, onProbationMalesCount + onProbationFemalesCount));
                EmpType = "OnProbation";
                maleSubTotal = onProbationMalesCount;
                femaleSubTotal = onProbationFemalesCount;
            }
            if (j == 3) {
                currentEmpStatusList.add(empStatusClass.SetData("Terminated", terminatedMaleCount, terminatedFemaleCount, terminatedMaleCount + terminatedFemaleCount));
                EmpType = "Terminated";
                maleSubTotal = terminatedMaleCount;
                femaleSubTotal = terminatedFemaleCount;
            }
            maleChart.setLabel("Male");
            femaleChart.setLabel("Female");
            maleChart.set(EmpType, maleSubTotal);
            femaleChart.set(EmpType, femaleSubTotal);
        }

        createBarModel();

    }

    private void createBarModel() {
        EmpStatusBarModel = initBarModel1();
        EmpStatusBarModel.setTitle("Employees Grouped By Their Status ");
        EmpStatusBarModel.setLegendPosition("ne");
        EmpStatusBarModel.setAnimate(true);
        Axis xAxis = EmpStatusBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Employee Status");
        xAxis.setTickAngle(0);
        Axis yAxis = EmpStatusBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Total Employees");
        yAxis.setMin(0);
        yAxis.setMax(totalEmployeeConut);
    }

    private BarChartModel initBarModel1() {
        BarChartModel model = new BarChartModel();
        model.addSeries(maleChart);
        model.addSeries(femaleChart);
        return model;
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Dashboard elements">
    private void createDonutModels() {
        donutModel = initDonutModel();
        donutModel.setTitle("Requested Documents");
        donutModel.setLegendPosition("w");
        donutModel.setShowDataLabels(true);
    }
    //<editor-fold defaultstate="collapsed" desc="Pie Chart">
    private void createPieModel() {
        pieModel1 = new PieChartModel();
        int youngCount = 0;
        int adultCount = 0;
        int middle_ageCount = 0;
        int oldCount = 0;
        List<HrEmployees> EmployeeList = new ArrayList<>();
        EmployeeList = hrEmployeeBean.findActiveEmployees();
        for (int i = 0; i < EmployeeList.size(); i++) {
            if (EmployeeList.get(i).getDob() != null) {
                if (ageCalculator(EmployeeList.get(i).getDob()) > 13 && ageCalculator(EmployeeList.get(i).getDob()) < 20) {
                    youngCount = youngCount + 1;
                } else if (ageCalculator(EmployeeList.get(i).getDob()) > 19 && ageCalculator(EmployeeList.get(i).getDob()) < 40) {
                    adultCount = adultCount + 1;
                } else if (ageCalculator(EmployeeList.get(i).getDob()) > 39 && ageCalculator(EmployeeList.get(i).getDob()) < 60) {
                    middle_ageCount = middle_ageCount + 1;
                } else if (ageCalculator(EmployeeList.get(i).getDob()) > 59) {
                    oldCount = oldCount + 1;
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            //pieList.add(pie.DataSeter("Young",youngCount));
            if (i == 0) {
                pieModel1.set("Young(13-19)", youngCount);
            }
            if (i == 1) {
                pieModel1.set("Adult(20-39)", adultCount);
            }
            if (i == 2) {
                pieModel1.set("Middle-Age(40-59)", middle_ageCount);
            }
            if (i == 3) {
                pieModel1.set("Old(60-Above)", oldCount);
            }

        }
        pieModel1.setTitle("Current Staffs Grouped By Their Age");
        pieModel1.setLegendPosition("w");
        pieModel1.setShowDataLabels(true);
        pieModel1.setShadow(true);

    }

    public int ageCalculator(String birthdate) {
        String today[] = StringDateManipulation.toDayInEc().split("-");
        String birthday[] = birthdate.split("/");
        int cyear = Integer.valueOf(today[0]);
        int byear = Integer.valueOf(birthday[2]);
        int age = cyear - byear;
        return age;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Line Chart">
    public List<String> PastFiveyears() {
        List<String> fiveYears = new ArrayList<>();
        String today[] = StringDateManipulation.toDayInEc().split("-");
        int curentYear = Integer.valueOf(today[0]);
        for (int i = 0; i < 5; i++) {
            fiveYears.add(String.valueOf(curentYear));
            curentYear = curentYear - 1;
        }
        return fiveYears;
    }
    int max = 0;

    private void createLineModels() {
        lineModel = initLinearModel();
        lineModel.setTitle("Employee Turnover Rate(Within The Past Five Years)");
        lineModel.setLegendPosition("e");
        lineModel.setAnimate(true);
        Axis yAxis = lineModel.getAxis(AxisType.Y);
        lineModel.setShowPointLabels(true);
        lineModel.getAxes().put(AxisType.X, new CategoryAxis("Years"));
        yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("Turnovers");
        yAxis.setMin(0);
        yAxis.setMax(max);

    }

    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();
        ChartSeries turnovers = new ChartSeries();
        turnovers.setLabel("Employee Turnover");
        List<String> Years = PastFiveyears();
        //Integer currentYear = (int) (long) StringDateManipulation.currentYear;
        for (int i = Years.size(); i > 0; i--) {
            //Random rn = new Random();
            System.out.println("Years.get(i)===" + Years.get(i - 1));
            int answer = terminationRequestBeanLocal.terminationCountPerYear(Years.get(i - 1));
            max = max + answer;
            turnovers.set(Years.get(i - 1), answer);
        }

        model.addSeries(turnovers);
        return model;
    }
//</editor-fold>
    private DonutChartModel initDonutModel() {
        DonutChartModel model = new DonutChartModel();
        
        Map<String, Number> circle1 = new LinkedHashMap<String, Number>();
        circle1.put("Exprience", 150);
        circle1.put("Gurranty Letter", 400);
        circle1.put("Support Letter", 200);
        circle1.put("Employment Letter", 75);
        model.addCircle(circle1);
        return model;
    }
    
    private void createHorizontalBarModel() {
        horizontalBarModel = new HorizontalBarChartModel();
        
        ChartSeries boys = new ChartSeries();
        boys.setLabel("Boys");
        boys.set("2004", 50);
        boys.set("2005", 96);
        boys.set("2006", 44);
        boys.set("2007", 55);
        boys.set("2008", 25);
        
        ChartSeries girls = new ChartSeries();
        girls.setLabel("Girls");
        girls.set("2004", 52);
        girls.set("2005", 60);
        girls.set("2006", 82);
        girls.set("2007", 35);
        girls.set("2008", 120);
        
        horizontalBarModel.addSeries(boys);
        horizontalBarModel.addSeries(girls);
        
        horizontalBarModel.setTitle("Horizontal and Stacked");
        horizontalBarModel.setLegendPosition("e");
        horizontalBarModel.setStacked(true);
        horizontalBarModel.setAnimate(true);
        
        Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Births");
        xAxis.setMin(0);
        xAxis.setMax(200);
        
        Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Gender");
    }
    
    private MeterGaugeChartModel initMeterGaugeModel() {
        List<Number> intervals = new ArrayList<Number>() {
            {
                add(20);
                add(50);
                add(120);
                add(220);
            }
        };
        
        return new MeterGaugeChartModel(140, intervals);
    }
    
    private void createMeterGaugeModels() {
        meterGaugeModel = initMeterGaugeModel();
        meterGaugeModel.setTitle("Custom Options");
        meterGaugeModel.setSeriesColors("66cc66,93b75f,E7E658,cc6666");
        meterGaugeModel.setGaugeLabel("km/h");
        meterGaugeModel.setGaugeLabelPosition("bottom");
        meterGaugeModel.setShowTickLabels(false);
        meterGaugeModel.setLabelHeightAdjust(110);
        meterGaugeModel.setIntervalOuterRadius(100);
    }
//</editor-fold>
}
