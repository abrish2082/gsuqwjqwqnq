/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;

//<editor-fold defaultstate="collapsed" desc="import classes">
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPgPlDeptBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPlPgBeanLocal;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.payroll.HrPayrollPlPg;
import et.gov.eep.hrms.entity.payroll.HrPayrollPlPgDept;
import et.gov.eep.hrms.integration.HrDepartmentsIntegrationBeanLocal;
import et.gov.eep.hrms.mapper.payroll.HrPayrollPlPgFacade;
import org.primefaces.context.RequestContext;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
//</editor-fold>

/**
 *
 * @author munir
 */
@Named("payLocationPayGroupController")
@ViewScoped
public class PayLocationPayGroupController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="dependency injection and variables">
    @Inject
    HrPayrollPlPgDept hrPayrollPlPgDept;
    @Inject
    HrPayrollPlPg hrPayrollPlPg;
    @Inject
    HrDepartments hrDepartmentMain;
    @Inject
    HrDepartments hrDepartmentSearch;
    @Inject
    FmsLuSystem fmsLuSystem;
    @Inject
    FmsCostCenter fmsCostCenter;
    @Inject
    FmsCostcSystemJunction fmsCostcSystemJunction;
    @Inject
    SessionBean sessionBeanLocal;
    @EJB
    HrPayrollPlPgBeanLocal hrPayrollPlPgBeanLocal;
    @EJB
    HrDepartmentsIntegrationBeanLocal hrDepartmentsIntegrationBeanLocal;
    @EJB
    HrDepartmentsBeanLocal hrDepartmentsBeanLocal;
    @EJB
    HrPayrollPgPlDeptBeanLocal hrPayrollPgPlDeptBeanLocal;
    @EJB
    FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal;

    private String selectedValue = HrPayrollPlPg.ALLDEPARTMENTS;
    private String allDepartments = HrPayrollPlPg.ALLDEPARTMENTS;
    private String included = HrPayrollPlPg.INCLUDED;
    private String nonIncluded = HrPayrollPlPg.NON_INCLUDED;
    private boolean isUpdated = false;
    private boolean disabled;
    private TreeNode root;
    private TreeNode selectedNode;
    private List<HrDepartments> allDepartment;
    private static List<HrDepartments> department;
    private List<PayLocationPayGroupTable> payLocationPayGroups = new ArrayList<>();
    private DataModel<PayLocationPayGroupTable> payLocationPayGroupDataModel = new ListDataModel<>();
    private List<HrPayrollPlPg> hrPayrollPlPgList = new ArrayList<>();

    /**
     * Creates a new instance of PayGroupPayLocation
     */
    public PayLocationPayGroupController() {
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="postConstract">
    @PostConstruct
    public void init() {
        hrPayrollPlPgList = hrPayrollPlPgBeanLocal.findAllPlPg();
        loadTree();
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getters & setters">

    public HrPayrollPlPgDept getHrPayrollPlPgDept() {
        if (hrPayrollPlPgDept == null) {
            hrPayrollPlPgDept = new HrPayrollPlPgDept();
        }
        return hrPayrollPlPgDept;
    }

    public void setHrPayrollPlPgDept(HrPayrollPlPgDept hrPayrollPlPgDept) {
        this.hrPayrollPlPgDept = hrPayrollPlPgDept;
    }

    public HrDepartments getHrDepartmentSearch() {
        if (hrDepartmentSearch == null) {
            hrDepartmentSearch = new HrDepartments();
        }
        return hrDepartmentSearch;
    }

    public void setHrDepartmentSearch(HrDepartments hrDepartmentSearch) {
        this.hrDepartmentSearch = hrDepartmentSearch;
    }

    public HrDepartments getHrDepartmentMain() {
        if (hrDepartmentMain == null) {
            hrDepartmentMain = new HrDepartments();
        }
        return hrDepartmentMain;
    }

    public void setHrDepartmentMain(HrDepartments hrDepartmentMain) {
        this.hrDepartmentMain = hrDepartmentMain;
    }

    public DataModel<PayLocationPayGroupTable> getPayLocationPayGroupDataModel() {
        return payLocationPayGroupDataModel;
    }

    public void setPayLocationPayGroupDataModel(DataModel<PayLocationPayGroupTable> payLocationPayGroupDataModel) {
        this.payLocationPayGroupDataModel = payLocationPayGroupDataModel;
    }

    public List<PayLocationPayGroupTable> getPayLocationPayGroups() {
        return payLocationPayGroups;
    }

    public void setPayLocationPayGroups(List<PayLocationPayGroupTable> payLocationPayGroups) {
        this.payLocationPayGroups = payLocationPayGroups;
    }

    public FmsLuSystem getFmsLuSystem() {
        return fmsLuSystem;
    }

    public void setFmsLuSystem(FmsLuSystem fmsLuSystem) {
        this.fmsLuSystem = fmsLuSystem;
    }

    public FmsCostCenter getFmsCostCenter() {
        return fmsCostCenter;
    }

    public void setFmsCostCenter(FmsCostCenter fmsCostCenter) {
        this.fmsCostCenter = fmsCostCenter;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public String getAllDepartments() {
        return allDepartments;
    }

    public void setAllDepartments(String allDepartments) {
        this.allDepartments = allDepartments;
    }

    public String getIncluded() {
        return included;
    }

    public void setIncluded(String included) {
        this.included = included;
    }

    public String getNonIncluded() {
        return nonIncluded;
    }

    public void setNonIncluded(String nonIncluded) {
        this.nonIncluded = nonIncluded;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public HrPayrollPlPg getHrPayrollPlPg() {
        if (hrPayrollPlPg == null) {
            hrPayrollPlPg = new HrPayrollPlPg();
        }
        return hrPayrollPlPg;
    }

    public void setHrPayrollPlPg(HrPayrollPlPg hrPayrollPlPg) {
        this.hrPayrollPlPg = hrPayrollPlPg;
    }

    public List<HrPayrollPlPg> getHrPayrollPlPgList() {
        return hrPayrollPlPgList;
    }

    public void setHrPayrollPlPgList(List<HrPayrollPlPg> hrPayrollPlPgList) {
        this.hrPayrollPlPgList = hrPayrollPlPgList;
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

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="inner class">
    public class PayLocationPayGroupTable implements Serializable {

        private Integer Id;
        private Integer deptId;
        private String deptName;
        private String systemCode;
        private String costCenterCode;
        private String payLocation;
        private String payGroup;
        private String remark;
        private Integer preparerId;
        private String preparerName;
        private String preparedOn;

        //<editor-fold defaultstate="collapsed" desc="getters & setters">
        public Integer getId() {
            return Id;
        }

        public void setId(Integer Id) {
            this.Id = Id;
        }

        public Integer getDeptId() {
            return deptId;
        }

        public void setDeptId(Integer deptId) {
            this.deptId = deptId;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public String getSystemCode() {
            return systemCode;
        }

        public void setSystemCode(String systemCode) {
            this.systemCode = systemCode;
        }

        public String getCostCenterCode() {
            return costCenterCode;
        }

        public void setCostCenterCode(String costCenterCode) {
            this.costCenterCode = costCenterCode;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public Integer getPreparerId() {
            return preparerId;
        }

        public void setPreparerId(Integer preparerId) {
            this.preparerId = preparerId;
        }

        public String getPreparerName() {
            return preparerName;
        }

        public void setPreparerName(String preparerName) {
            this.preparerName = preparerName;
        }

        public String getPreparedOn() {
            return preparedOn;
        }

        public void setPreparedOn(String preparedOn) {
            this.preparedOn = preparedOn;
        }
//</editor-fold>

        public PayLocationPayGroupTable(Integer Id, Integer deptId,
                String deptName, String systemCode, String costCenterCode,
                String payLocation, String payGroup, String remark) {
            this.Id = Id;
            this.deptId = deptId;
            this.deptName = deptName;
            this.systemCode = systemCode;
            this.costCenterCode = costCenterCode;
            this.payLocation = payLocation;
            this.payGroup = payGroup;
            this.deptName = deptName;
            this.remark = remark;
        }

    }

    private String saveOrUpdate = "Save";
    private String addOrUpdate = "Add";
    private String newOrSearch = "New";
    private String icone = "ui-icon-plus";
    private boolean searchPage = true;
    private boolean newPage = false;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public String getSaveOrUpdate() {
        return saveOrUpdate;
    }

    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }

    public String getAddOrUpdate() {
        return addOrUpdate;
    }

    public void setAddOrUpdate(String addOrUpdate) {
        this.addOrUpdate = addOrUpdate;
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

    public List<HrDepartments> getAllDepartment() {
        return allDepartment;
    }

    public void setAllDepartment(List<HrDepartments> allDepartment) {
        this.allDepartment = allDepartment;
    }

    public static List<HrDepartments> getDepartment() {
        return department;
    }

    public static void setDepartment(List<HrDepartments> department) {
        PayLocationPayGroupController.department = department;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="main methods">
    public void btnNewOrSearch() {
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

    public void vcl_plPg(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrPayrollPlPg = (HrPayrollPlPg) event.getNewValue();
            hrPayrollPlPgDept.setPayLocationPayGroupId(hrPayrollPlPg);
        }
    }

    public void loadTree() {
        allDepartment = hrDepartmentsBeanLocal.findAllDepartment();
        root = new DefaultTreeNode("Root", null);
        populateNode(allDepartment, 0, root);
    }

    public void populateNode(List<HrDepartments> depts, int id, TreeNode node) {
        department = new ArrayList<>();
        if (getAllDepartment() != null) {
            for (HrDepartments k : getAllDepartment()) {
                if (k.getParentId() != null && k.getParentId() == id) {
                    TreeNode childNode = new DefaultTreeNode(k.getDepId() + "--" + k.getDepName(), node);
                    department.add(k);
                    populateNode(department, k.getDepId(), childNode);
                }
            }
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int deptId = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);
        hrDepartmentMain.setDepId(deptId);
        hrDepartmentMain = hrDepartmentsBeanLocal.findByDepartmentId(hrDepartmentMain);
        fmsCostcSystemJunction = hrPayrollPgPlDeptBeanLocal.readSystemCostCenter(hrDepartmentMain);
        hrPayrollPlPgDept = hrPayrollPgPlDeptBeanLocal.readPayLocationPayGroupDetail(hrDepartmentMain);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlg2').hide();");
        if (hrPayrollPlPgDept != null) {
            hrPayrollPlPg = hrPayrollPlPgDept.getPayLocationPayGroupId();
            if (hrPayrollPlPgDept.getId() == 0) {
                isUpdated = false;
                saveOrUpdate = "Save";
            } else {
                isUpdated = true;
                saveOrUpdate = "Update";
            }
        }
    }

    public void onNodeSelectSearch(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int deptId = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);
        hrDepartmentSearch.setDepId(deptId);
        hrDepartmentSearch = hrDepartmentsBeanLocal.findByDepartmentId(hrDepartmentSearch);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlg1').hide();");
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

    private HrPayrollPlPgDept payLocPayGroupDetail() {
        HrPayrollPlPgDept payLocPayGroupDetail = new HrPayrollPlPgDept();
        payLocPayGroupDetail.setId(hrPayrollPlPgDept.getId());
        payLocPayGroupDetail.setDepId(hrDepartmentMain);
        payLocPayGroupDetail.setPayLocationPayGroupId(hrPayrollPlPgDept.getPayLocationPayGroupId());
        payLocPayGroupDetail.setRemark(hrPayrollPlPgDept.getRemark());
        payLocPayGroupDetail.setPreparedBy(String.valueOf(sessionBeanLocal.getUserId()));
        payLocPayGroupDetail.setPreparedOn(dateFormat(StringDateManipulation.toDayInEc()));
        return payLocPayGroupDetail;
    }

    public void savePayLocationPayGroup() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "savePayLocationPayGroup", dataset)) {
                if (hrDepartmentMain.getDepId() == null) {
                    JsfUtil.addFatalMessage("Department can't be empty! Please Select.");
                } else {
                    if (!isUpdated) {
                        try {
                            hrPayrollPgPlDeptBeanLocal.save(payLocPayGroupDetail());
                            JsfUtil.addSuccessMessage("Successfully Saved.");
                            fmsCostcSystemJunction = null;
                            hrPayrollPlPgDept = null;
                            hrPayrollPlPg = null;
                            hrDepartmentMain = null;
                        } catch (Exception ex) {
                            JsfUtil.addFatalMessage("Error Occurs While Saving Pay Location & Pay Group.");
                        }
                    } else {
                        try {
                            hrPayrollPgPlDeptBeanLocal.update(payLocPayGroupDetail());
                            JsfUtil.addSuccessMessage("Successfully Updated.");
                            fmsCostcSystemJunction = null;
                            hrPayrollPlPgDept = null;
                            hrPayrollPlPg = null;
                            hrDepartmentMain = null;
                        } catch (Exception ex) {
                            JsfUtil.addFatalMessage("Error Occurs While Updating Pay Location & Pay Group.");
                            ex.printStackTrace();
                            isUpdated = false;
                            saveOrUpdate = "Save";
                        }
                    }
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        hrPayrollPlPgDept = new HrPayrollPlPgDept();
        hrDepartmentMain = new HrDepartments();
        fmsCostcSystemJunction = new FmsCostcSystemJunction();
        isUpdated = false;
        saveOrUpdate = "Save";
        setDisabled(false);
    }

    public void recreateDataModel(List<PayLocationPayGroupTable> payLocationPayGroups) {
        payLocationPayGroupDataModel = null;
        payLocationPayGroupDataModel = new ListDataModel(new ArrayList<>(payLocationPayGroups));
    }

    private void readPayLocationPayGroups() {
        int allEmp = Integer.valueOf(getSelectedValue());
        payLocationPayGroups = new ArrayList<>();
        List<Object[]> plPg = hrPayrollPlPgBeanLocal.readPayLocPayGroup(allEmp);
        if (!plPg.isEmpty() && plPg.size() > 0) {
            for (Object[] obj : plPg) {
                PayLocationPayGroupTable addToTable = new PayLocationPayGroupTable(
                        Integer.valueOf(obj[4].toString()), //Id
                        Integer.valueOf(obj[0].toString()), //depId
                        obj[1] != null ? String.valueOf(obj[1].toString()) : null, //depName
                        obj[2] != null ? String.valueOf(obj[2].toString()) : null, //systemCode
                        obj[3] != null ? String.valueOf(obj[3].toString()) : null, //costCenterCode
                        obj[5] != null ? String.valueOf(obj[5].toString()) : null, //payLocation
                        obj[6] != null ? String.valueOf(obj[6].toString()) : null, //payGroup                       
                        obj[7] != null ? String.valueOf(obj[7].toString()) : null); //remark
                payLocationPayGroups.add(addToTable);
            }
        }
        recreateDataModel(payLocationPayGroups);
    }

    public String btnLoad_action() {
        readPayLocationPayGroups();
        return null;
    }
    //</editor-fold>

}
