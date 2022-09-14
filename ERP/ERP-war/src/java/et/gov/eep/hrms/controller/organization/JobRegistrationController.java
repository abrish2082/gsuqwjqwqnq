package et.gov.eep.hrms.controller.organization;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.localbean;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.lookup.HrLuEducLevelsBeanLocal;
import et.gov.eep.hrms.businessLogic.lookup.HrLuEducTypesBeanLocal;
import et.gov.eep.hrms.businessLogic.lookup.HrLuGradesBeanLocal;
import et.gov.eep.hrms.businessLogic.lookup.HrLuJobLevelsBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrDeptJobsBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrJobEducQualificationsBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrJobTypesBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrSalaryScaleRangesBeanLocal;
import et.gov.eep.hrms.businessLogic.recruitment.HrRecruitmentRequestsBeanLocal;
import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.lookup.HrLuEducTypes;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.lookup.HrLuJobLevels;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobEducQualifications;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import securityBean.SessionBean;
import securityBean.Utility;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author Benin
 */
@Named(value = "jobRegistrationController")
@ViewScoped
public class JobRegistrationController implements Serializable {

//<editor-fold defaultstate="collapsed" desc="object and variable">
    @Inject
    HrLuGrades hrLuGrades;
    @Inject
    localbean Languagelocalbean;
    @Inject
    HrJobEducQualifications hrJobEducQualifications;
    @Inject
    HrJobTypes hrJobTypes;
    @Inject
    HrLuEducLevels hrLuEducLevels;
    @Inject
    HrLuEducTypes hrLuEducTypes;
    @Inject
    HrDepartments hrDepartments;
    @Inject
    HrSalaryScaleRanges hrSalaryScaleRanges;
    @Inject
    HrLuJobLevels hrLuJobLevels;
    @Inject
    SessionBean SessionBean;
    @Inject
    ColumnNameResolver columnNameResolver;

    @EJB
    HrDeptJobsBeanLocal hrDeptJobsBeanLocal;
    @EJB
    HrJobTypesBeanLocal hrJobTypesBeanLocal;
    @EJB
    HrSalaryScaleRangesBeanLocal hrSalaryScaleRangesBeanLocal;
    @EJB
    HrLuEducLevelsBeanLocal hrLuEducLevelsBeanLocal;
    @EJB
    HrLuEducTypesBeanLocal hrLuEducTypesBeanLocal;
    @EJB
    HrDepartmentsBeanLocal hrDepartmentsBeanLocal;
    @EJB
    HrJobEducQualificationsBeanLocal hrJobEducQualificationsBeanLocal;
    @EJB
    HrLuJobLevelsBeanLocal hrLuJobLevelsBeanLocal;
    @EJB
    private HrRecruitmentRequestsBeanLocal hrRecruitmentRequestsBeanLocal;
    @EJB
    HrLuGradesBeanLocal hrLuGradesBeanLocal;

    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private boolean renderNewbtn = false;
    private String renderpnlContrat = "false";
    private String SaveOrUpdateButton = "Save";
    private String AddOrModify = "Add";
    private int selectedRowIndex = -1;
    private int updateStatus = 0;
    private String Col_Value;

    String saveOrUpdateQual = "Add";
    int selectedRowIndexQual = -1;
    String engBundle = "/et/gov/eep/hrms/en";
    String amhbundle = "/et/gov/eep/hrms/en_am_ET";
    //String engBundle = "/Local/am_en_US";
    //String amhbundle = "/Local/am";

    List<HrSalaryScaleRanges> hrSalaryScaleRangesList = new ArrayList<>();
    List<HrLuEducLevels> educLevelList = new ArrayList<>();
    List<HrLuEducTypes> educTypeList = new ArrayList<>();
    List<HrLuJobLevels> jobLevelsList = new ArrayList<>();
    List<HrJobTypes> hrJobTypesList = new ArrayList<>();
    DataModel<HrJobTypes> hrJobTypesListDataModel = new ListDataModel<>();
    DataModel<HrJobEducQualifications> jobEducQualModel = new ListDataModel<>();
    List<ColumnNameResolver> ColumnNameResolverList = new ArrayList<>();
//</editor-fold>

    public JobRegistrationController() {
    }

    @PostConstruct
    public void init() {
       
        hrJobTypes.setType(BigInteger.valueOf(0));
        educLevelList = hrLuEducLevelsBeanLocal.findAll();
        educTypeList = hrLuEducTypesBeanLocal.findAll();
        allDepartmentsList = hrDepartmentsBeanLocal.findAll();
        root = new DefaultTreeNode("Root", null);
        recursive(allDepartmentsList, 0, root);
        root2 = getRoot();
        ColumnNameResolverList = hrJobTypesBeanLocal.findColumns();
        System.out.println("language=3==" + Languagelocalbean.getLangsession().getAttribute("lang").toString());
        Languagelocalbean.changeLanguage(Languagelocalbean.getLangsession().getAttribute("lang").toString());
        SaveOrUpdateButton = Languagelocalbean.getSave();
        System.out.println("EmpoyeeID======" + SessionBean.getEmpId());
        System.out.println("First Name==" + SessionBean.getFirstName());
        System.out.println("SessionBean.getFirstName()====" + SessionBean.getFirstName());
        System.out.println("SessionBean.getFirstName()====" + SessionBean.getLastName());
        System.out.println("SessionBean.getFirstName()====" + SessionBean.getMiddleName());

    }

    // <editor-fold defaultstate="collapsed" desc="getter & setter">
    public HrLuGrades getHrLuGrades() {
        if (hrLuGrades == null) {
            hrLuGrades = new HrLuGrades();
        }
        return hrLuGrades;
    }

    public void setHrLuGrades(HrLuGrades hrLuGrades) {
        this.hrLuGrades = hrLuGrades;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JobRegistrationController other = (JobRegistrationController) obj;
        if (!Objects.equals(this.Languagelocalbean, other.Languagelocalbean)) {
            return false;
        }
        return true;
    }

    public HrLuJobLevels getHrLuJobLevels() {
        if (hrLuJobLevels == null) {
            hrLuJobLevels = new HrLuJobLevels();
        }
        return hrLuJobLevels;
    }

    public void setHrLuJobLevels(HrLuJobLevels hrLuJobLevels) {
        this.hrLuJobLevels = hrLuJobLevels;
    }

    public HrJobEducQualifications getHrJobEducQualifications() {
        if (hrJobEducQualifications == null) {
            hrJobEducQualifications = new HrJobEducQualifications();
        }
        return hrJobEducQualifications;
    }

    public void setHrJobEducQualifications(HrJobEducQualifications hrJobEducQualifications) {
        this.hrJobEducQualifications = hrJobEducQualifications;
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

    public String getAddOrModify() {
        return AddOrModify;
    }

    public void setAddOrModify(String AddOrModify) {
        this.AddOrModify = AddOrModify;
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

    public void setHrSalaryScaleRanges(HrSalaryScaleRanges hrSalaryScaleRanges) {
        this.hrSalaryScaleRanges = hrSalaryScaleRanges;
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

    public boolean isRenderNewbtn() {
        return renderNewbtn;
    }

    public void setRenderNewbtn(boolean renderNewbtn) {
        this.renderNewbtn = renderNewbtn;
    }

    public List<HrJobTypes> getHrJobTypesList() {
        return hrJobTypesList;
    }

    public void setHrJobTypesList(List<HrJobTypes> hrJobTypesList) {
        this.hrJobTypesList = hrJobTypesList;
    }

    public DataModel<HrJobTypes> getHrJobTypesListDataModel() {
        return hrJobTypesListDataModel;
    }

    public void setHrJobTypesListDataModel(DataModel<HrJobTypes> hrJobTypesListDataModel) {
        this.hrJobTypesListDataModel = hrJobTypesListDataModel;
    }

    public DataModel<HrJobEducQualifications> getJobEducQualModel() {
        return jobEducQualModel;
    }

    public void setJobEducQualModel(DataModel<HrJobEducQualifications> jobEducQualModel) {
        this.jobEducQualModel = jobEducQualModel;
    }

    public int getSelectedRowIndex() {
        return selectedRowIndex;
    }

    public void setSelectedRowIndex(int selectedRowIndex) {
        this.selectedRowIndex = selectedRowIndex;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public List<HrLuEducLevels> getEducLevelList() {
        return educLevelList;
    }

    public void setEducLevelList(List<HrLuEducLevels> educLevelList) {
        this.educLevelList = educLevelList;
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

    public TreeNode getDepartmentList() {
        return DepartmentList;
    }

    public void setDepartmentList(TreeNode DepartmentList) {
        this.DepartmentList = DepartmentList;
    }

    public static List<HrDepartments> getAraListe() {
        return araListe;
    }

    public static void setAraListe(List<HrDepartments> araListe) {
        JobRegistrationController.araListe = araListe;
    }

    public List<HrDepartments> getAllDepartmentsList() {
        return allDepartmentsList;
    }

    public void setAllDepartmentsList(List<HrDepartments> allDepartmentsList) {
        this.allDepartmentsList = allDepartmentsList;
    }

    public List<HrLuEducTypes> getEducTypeList() {
        return educTypeList;
    }

    public void setEducTypeList(List<HrLuEducTypes> educTypeList) {
        this.educTypeList = educTypeList;
    }

    public List<HrLuJobLevels> getJobLevelsList() {
        return jobLevelsList = hrLuJobLevelsBeanLocal.findAll();
    }

    public void setJobLevelsList(List<HrLuJobLevels> jobLevelsList) {
        this.jobLevelsList = jobLevelsList;
    }

    public List<HrSalaryScaleRanges> getHrSalaryScaleRangesList() {
        return hrSalaryScaleRangesList = hrSalaryScaleRangesBeanLocal.allJobGrades();
    }

    public void setHrSalaryScaleRangesList(List<HrSalaryScaleRanges> hrSalaryScaleRangesList) {
        this.hrSalaryScaleRangesList = hrSalaryScaleRangesList;
    }

    public String getSaveOrUpdateQual() {
        return saveOrUpdateQual;
    }

    public void setSaveOrUpdateQual(String saveOrUpdateQual) {
        this.saveOrUpdateQual = saveOrUpdateQual;
    }

    public int getSelectedRowIndexQual() {
        return selectedRowIndexQual;
    }

    public void setSelectedRowIndexQual(int selectedRowIndexQual) {
        this.selectedRowIndexQual = selectedRowIndexQual;

    }

    public String getCol_Value() {
        return Col_Value;
    }

    public void setCol_Value(String Col_Value) {
        this.Col_Value = Col_Value;
    }

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    public List<ColumnNameResolver> getColumnNameResolverList() {
        ColumnNameResolverList = hrJobTypesBeanLocal.findColumns();
        if (ColumnNameResolverList == null) {
            ColumnNameResolverList = new ArrayList<>();
        }
        return ColumnNameResolverList;
    }

    public void setColumnNameResolverList(List<ColumnNameResolver> ColumnNameResolverList) {
        this.ColumnNameResolverList = ColumnNameResolverList;
    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="department popup">
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    TreeNode DepartmentList;
    private static List<HrDepartments> araListe;
    List<HrDepartments> allDepartmentsList = new ArrayList<>();

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

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int key = Integer.valueOf((selectedNode.getData().toString()).split("--")[0]);
        System.out.println(selectedNode.getData().toString());
        hrDepartments = hrRecruitmentRequestsBeanLocal.getSelectDepartement(key);
        hrJobTypes.setDepId(hrDepartments);
        RequestContext context2 = RequestContext.getCurrentInstance();
        context2.execute("PF('dlgDpt').hide();");
    }
//</editor-fold>

    //     //<editor-fold defaultstate="collapsed" desc="search">
    public ArrayList<HrJobTypes> searchByJobCode(String hrJobType) {
        ArrayList<HrJobTypes> jobCode = null;
        hrJobTypes.setCol_Value(hrJobType);
        System.out.println("vCol_Value=== controller===" + hrJobTypes.getCol_Value());
        jobCode = hrJobTypesBeanLocal.searchByCol_NameAndCol_Value(columnNameResolver, hrJobTypes.getCol_Value());
        return jobCode;
    }

    public void getByJobCode(SelectEvent event) {
        try {
            if (event.getObject() != null) {
                hrJobTypes = (HrJobTypes) event.getObject();
                SaveOrUpdateButton = Languagelocalbean.getUpdate();
                AddOrModify = Languagelocalbean.getAdd();
                String totalNoEmpAllowedForJob = hrJobTypesBeanLocal.totalNoEmpAllowedForJob(hrJobTypes.getId());
                hrJobTypes.setNoEmpNeeded(new BigInteger(totalNoEmpAllowedForJob));
                hrDepartments = hrJobTypes.getDepId();
                hrSalaryScaleRanges = hrJobTypes.getJobGradeId();
                hrLuJobLevels = hrJobTypes.getJobLevelId();
                renderPnlCreateGatePass = true;
                renderPnlManPage = true;
                updateStatus = 1;
                createOrSearchBundle = "Search";
                setIcone("ui-icon-search");
                setIcone("ui-icon-search");
                recreateModelDetail();
                renderNewbtn = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Exception");
        }
    }

    public ArrayList<HrJobTypes> searchByJobTitle(String hrJobType) {
        ArrayList<HrJobTypes> jobTitle = null;
        hrJobTypes.setJobTitle(hrJobType);
        jobTitle = hrJobTypesBeanLocal.searchByJobTitle(hrJobTypes);
        return jobTitle;
    }

    public void getByJobTitle(SelectEvent event) {
        try {
            hrJobTypes.setJobTitle(event.getObject().toString());
            hrJobTypes = hrJobTypesBeanLocal.findByJobTitle(hrJobTypes);
            SaveOrUpdateButton = "Update";
            String totalNoEmpAllowedForJob = hrJobTypesBeanLocal.totalNoEmpAllowedForJob(hrJobTypes.getId());
            hrJobTypes.setNoEmpNeeded(new BigInteger(totalNoEmpAllowedForJob));
            hrDepartments = hrJobTypes.getDepId();
            hrSalaryScaleRanges = hrJobTypes.getJobGradeId();
            hrLuJobLevels = hrJobTypes.getJobLevelId();
            renderPnlCreateGatePass = true;
            renderPnlManPage = true;
            updateStatus = 1;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
            setIcone("ui-icon-search");
            recreateModelDetail();
            renderNewbtn = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Exception");
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="main">
    public void createNewGatePassInfo() {
        SaveOrUpdateButton = Languagelocalbean.getSave();
        AddOrModify = Languagelocalbean.getAdd();
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateGatePass = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            renderNewbtn = false;
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGatePass = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
            renderNewbtn = false;

        }
    }

    public void resetForm() {
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        clearAll();
        renderNewbtn = false;
        SaveOrUpdateButton = Languagelocalbean.getSave();
    }

    public void recreateModelDetail() {
        jobEducQualModel = null;
        jobEducQualModel = new ListDataModel(new ArrayList<>(hrJobTypes.getHrJobEducQualificationsList()));
    }

    public void clearAll() {
        clearDetail();
        hrJobTypes = new HrJobTypes();
        hrDepartments = new HrDepartments();
        hrLuEducTypes = new HrLuEducTypes();
        hrSalaryScaleRanges = new HrSalaryScaleRanges();
        hrJobTypesListDataModel = new ListDataModel<>();
        jobEducQualModel = new ListDataModel<>();
        hrLuJobLevels = new HrLuJobLevels();
        hrJobTypesList = new ArrayList<>();
        hrJobTypes.setType(BigInteger.valueOf(0));
        //SaveOrUpdateButton = "Save";
        SaveOrUpdateButton = Languagelocalbean.getSave();

    }

    public void clearDetail() {
        hrJobEducQualifications = new HrJobEducQualifications();
        hrLuEducLevels = new HrLuEducLevels();
        hrLuEducTypes = new HrLuEducTypes();
    }

    private boolean checkDuplicateEducation() {
        for (HrJobEducQualifications education : hrJobTypes.getHrJobEducQualificationsList()) {
            if (education.getEducLevelId().getEducLevel() != null && education.getEducQualId().getEducType() != null) {
                if (education.getEducLevelId().getEducLevel().equalsIgnoreCase(hrJobEducQualifications.getEducLevelId().getEducLevel())
                        && education.getEducQualId().getEducType().equalsIgnoreCase(hrJobEducQualifications.getEducQualId().getEducType())) {
                    return false;
                }
            }
        }

        return true;
    }

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            columnNameResolver.setCol_Name_FromTable(event.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(event.getNewValue().toString().toLowerCase() + ":"));
            //hrJobTypes.setCol_Value(columnNameResolver.getParsed_Col_Name());
        }
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }

    public void vlcEducLavel(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            int eduId = Integer.parseInt(event.getNewValue().toString());
            hrLuEducLevels = hrLuEducLevelsBeanLocal.searchEduclevelById(eduId);
            hrJobEducQualifications.setEducLevelId(hrLuEducLevels);
        } else {
            hrLuEducLevels = new HrLuEducLevels();
        }
    }

    public void vlcEducType(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            int eduTypeId = Integer.parseInt(event.getNewValue().toString());
            hrLuEducTypes = hrLuEducTypesBeanLocal.searchEducTypeById(eduTypeId);
            hrJobEducQualifications.setEducQualId(hrLuEducTypes);
        } else {
            hrLuEducTypes = new HrLuEducTypes();
        }
    }

    public void addQual() {
        if (checkDuplicateEducation()) {
            if (selectedRowIndexQual == -1) {
                hrJobTypes.addJobEducQual(hrJobEducQualifications);
                recreateModelDetail();
                clearDetail();
            } else {
                hrJobTypes.getHrJobEducQualificationsList().add(selectedRowIndexQual, hrJobEducQualifications);
                recreateModelDetail();
                clearDetail();
            }
            AddOrModify = Languagelocalbean.getAdd();
        } else {
            JsfUtil.addFatalMessage("Duplicate Entry!");
        }
    }

    public void selectJobQual(SelectEvent event) {
        updateStatus = 1;
        SaveOrUpdateButton = Languagelocalbean.getUpdate();
        AddOrModify = Languagelocalbean.getModify();
        hrJobEducQualifications = (HrJobEducQualifications) event.getObject();
        setSelectedRowIndexQual(hrJobTypes.getHrJobEducQualificationsList().indexOf(hrJobEducQualifications));
        System.out.println("-----------selectedRowIndexQual----=" + selectedRowIndexQual);
        hrJobTypes.getHrJobEducQualificationsList().remove(hrJobEducQualifications);
        educLevelList = hrLuEducLevelsBeanLocal.findAll();
        hrLuEducLevels = hrJobEducQualifications.getEducLevelId();
        educTypeList = hrLuEducTypesBeanLocal.findAll();
        hrLuEducTypes = hrJobEducQualifications.getEducQualId();
    }

    public void saveJobRegistration() {
        if (hrJobTypesBeanLocal.checkDuplicateJob(hrJobTypes) == true && updateStatus == 0) {
            JsfUtil.addFatalMessage("Duplicate Job Code or Job Title.");
        } else {
            try {
                AAA securityService = new AAA();
                IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
                String systemBundle = "et/gov/eep/commonApplications/securityServer";
                String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
                if (security.checkAccess(SessionBean.getUserName(), "saveJobRegistration", dataset)) {
                    try {
                        hrJobTypes.setJobGradeId(hrSalaryScaleRanges);
                        hrJobTypes.setJobLevelId(hrLuJobLevels);
                        if (updateStatus == 0) {
                            hrJobTypesBeanLocal.saveOrUpdate(hrJobTypes);
                            JsfUtil.addSuccessMessage("Successfuly Saved.");
                            clearAll();
                        } else {
                            hrJobTypesBeanLocal.saveOrUpdate(hrJobTypes);
                            JsfUtil.addSuccessMessage("Successfuly Updated.");
                            updateStatus = 0;
                            clearAll();
                        }
                    } catch (Exception e) {
                        JsfUtil.addFatalMessage("Error Occurs While Saving.");
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
    }

    public void vlcJobLevel(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            int joblevelId = Integer.parseInt(event.getNewValue().toString());
            hrLuJobLevels = hrLuJobLevelsBeanLocal.searchJobLevelById(joblevelId);
            hrJobEducQualifications.setEducLevelId(hrLuEducLevels);
        } else {
            hrLuJobLevels = new HrLuJobLevels();
        }
    }
//</editor-fold>

}
