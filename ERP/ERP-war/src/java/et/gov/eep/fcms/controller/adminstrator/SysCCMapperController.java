/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.adminstrator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.hrms.businessLogic.recruitment.HrRecruitmentRequestsBeanLocal;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.mapper.organization.HrDepartmentsFacade;

/**
 *
 * @author admin
 */
@Named(value = "sysCCMapperController")
@ViewScoped
public class SysCCMapperController implements Serializable {

//<editor-fold defaultstate="collapsed" desc="@EJB and @Inject declaration">
//@Inject
    @Inject
    SessionBean SessionBean;
    @Inject
    FmsCostcSystemJunction fmsCostcSystemJunction;
    @Inject
    FmsLuSystem fmsLuSystem;
    @Inject
    FmsCostCenter fmsCostCenter;
    @Inject
    HrDepartments hrDepartments;

    //@EJB
    @EJB
    FmsLuSystemBeanLocal fmsLuSystemBeanLocal;
    @EJB
    FmsCostCenterBeanLocal fmsCostCenterBeanLocal;
    @EJB
    FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal;
    @EJB
    private HrDepartmentsFacade hrDepartmentsFacade;
    @EJB
    private HrRecruitmentRequestsBeanLocal hrRecruitmentRequestsBeanLocal;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    FmsCostcSystemJunction selectedSCJ;

    FmsCostcSystemJunction fmsCC_SS_Jun;

    List<FmsCostcSystemJunction> filteredcostcSystemJunctionList;
    List<FmsCostCenter> costCenterList;
    List<FmsCostcSystemJunction> costcSystemJunctionList;
    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    DataModel<FmsCostcSystemJunction> fmsCostcSystemJunctionDataModel;
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    TreeNode DepartmentList;
    private static List<HrDepartments> araListe;
    boolean populateClicked = false;
    private String createOrSearchBundle = "New";
    private String headerTitle = "System - Cost Center Search";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
//</editor-fold>

    public SysCCMapperController() {
    }

    @PostConstruct
    public void init() {
        allDepartmentsList = hrDepartmentsFacade.findAll();
        root = new DefaultTreeNode("Root", null);
        recursive(allDepartmentsList, 0, root);
        root2 = getRoot();

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

    //<editor-fold defaultstate="collapsed" desc="getting and setter">
    public FmsLuSystem getFmsLuSystem() {
        if (fmsLuSystem == null) {
            fmsLuSystem = new FmsLuSystem();
        }
        return fmsLuSystem;
    }

    public void setFmsLuSystem(FmsLuSystem fmsLuSystem) {
        this.fmsLuSystem = fmsLuSystem;
    }

    public FmsCostcSystemJunction getSelectedSCJ() {
        if (selectedSCJ == null) {
            selectedSCJ = new FmsCostcSystemJunction();
        }
        return selectedSCJ;
    }

    public void setSelectedSCJ(FmsCostcSystemJunction selectedSCJ) {
        this.selectedSCJ = selectedSCJ;
    }

    public FmsCostcSystemJunction getFmsCC_SS_Jun() {
        return fmsCC_SS_Jun;
    }

    public void setFmsCC_SS_Jun(FmsCostcSystemJunction fmsCC_SS_Jun) {
        this.fmsCC_SS_Jun = fmsCC_SS_Jun;
    }

    public HrDepartments getHrDepartments() {
        return hrDepartments;
    }

    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
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

    public List<HrDepartments> getAllDepartmentsList() {
        return allDepartmentsList;
    }

    public void setAllDepartmentsList(List<HrDepartments> allDepartmentsList) {
        this.allDepartmentsList = allDepartmentsList;
    }

    public static List<HrDepartments> getAraListe() {
        return araListe;
    }

    public static void setAraListe(List<HrDepartments> araListe) {
        SysCCMapperController.araListe = araListe;
    }

    public List<FmsCostCenter> getCostCenterList() {
        if (costCenterList == null) {
            costCenterList = new ArrayList<>();
        }
        return costCenterList;
    }

    public void setCostCenterList(List<FmsCostCenter> costCenterList) {
        this.costCenterList = costCenterList;
    }

    public List<FmsCostcSystemJunction> getFilteredcostcSystemJunctionList() {
        return filteredcostcSystemJunctionList;
    }

    public void setFilteredcostcSystemJunctionList(List<FmsCostcSystemJunction> filteredcostcSystemJunctionList) {
        this.filteredcostcSystemJunctionList = filteredcostcSystemJunctionList;
    }

    public FmsCostCenter getFmsCostCenter() {
        if (fmsCostCenter == null) {
            fmsCostCenter = new FmsCostCenter();
        }
        return fmsCostCenter;
    }

    public void setFmsCostCenter(FmsCostCenter fmsCostCenter) {
        this.fmsCostCenter = fmsCostCenter;
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

    public List<FmsCostcSystemJunction> getCostcSystemJunctionList() {
        if (costcSystemJunctionList == null) {
            costcSystemJunctionList = new ArrayList<>();
        }
        return costcSystemJunctionList;
    }

    public void setCostcSystemJunctionList(List<FmsCostcSystemJunction> costcSystemJunctionList) {
        this.costcSystemJunctionList = costcSystemJunctionList;
    }

    public DataModel<FmsCostcSystemJunction> getFmsCostcSystemJunctionDataModel() {
        return fmsCostcSystemJunctionDataModel;
    }

    public void setFmsCostcSystemJunctionDataModel(DataModel<FmsCostcSystemJunction> fmsCostcSystemJunctionDataModel) {
        this.fmsCostcSystemJunctionDataModel = fmsCostcSystemJunctionDataModel;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isPopulateClicked() {
        return populateClicked;
    }

    public void setPopulateClicked(boolean populateClicked) {
        this.populateClicked = populateClicked;
    }
//</editor-fold>

    //recursive
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

    //select item
    public SelectItem[] getSystemList() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findAll(), true);
    }

    //value change event for system search
    public void SystemSearchChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuSystem.setSystemCode(event.getNewValue().toString());
            fmsLuSystem = fmsLuSystemBeanLocal.getSysDetail(fmsLuSystem);
            fmsCostcSystemJunctionDataModel = new ListDataModel(new ArrayList(fmsCostCSystemJunctionBeanLocal.fetchMappedSystem(fmsLuSystem)));
            costCenterList = fmsCostCenterBeanLocal.findUnmappedCostCenter(fmsLuSystem);
        }
    }

    //value change event for cost center search
    public void CostCenterSearchChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsCostCenter.setSystemName(event.getNewValue().toString());
            fmsCostCenter = fmsCostCenterBeanLocal.getCostDetail(fmsCostCenter);
        }
    }

//node select event
    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int key = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);
        hrDepartments = hrRecruitmentRequestsBeanLocal.getSelectDepartement(key);
        fmsCostcSystemJunction.setDepId(hrDepartments);
    }

    //select event
    public void populate(SelectEvent event) {
        fmsCostcSystemJunction = (FmsCostcSystemJunction) event.getObject();
        hrDepartments = fmsCostcSystemJunction.getDepId();
        costCenterList.clear();
        costCenterList.add(fmsCostCenterBeanLocal.getCostDetail(fmsCostcSystemJunction.getFmsCostCenter()));
        populateClicked = true;
    }

    //select items
    public SelectItem[] getStatus() {
        return JsfUtil.getSelectItems(status(), true);
    }

    /**
     *
     * @return
     */
    //array list for status
    public ArrayList<String> status() {
        ArrayList<String> stat = new ArrayList<>();
        stat.add("Active");
        stat.add("Inactive");
        return stat;
    }

    //connect system and cost center
    public void connectSysCC() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "connectSysCC", dataset)) {
                if (populateClicked == true) {
                    FmsCostcSystemJunction ccssJunction = fmsCostCSystemJunctionBeanLocal.findbyDepId(hrDepartments);
                    if (ccssJunction.getDepId() == null) {
                        fmsCostCSystemJunctionBeanLocal.update(fmsCostcSystemJunction);
                        JsfUtil.addSuccessMessage("System - Cost Center Mapping Saved Succesfully");
                    } else {
                        JsfUtil.addFatalMessage("Department is already mapped.");
                    }
                    reset();
                } else {
                    fmsCostcSystemJunction.setFmsCostCenter(fmsCostCenter);
                    fmsCostcSystemJunction.setFmsLuSystem(fmsLuSystem);
                    fmsCostcSystemJunction.setDepId(hrDepartments);
                    FmsCostcSystemJunction ccssJunction = fmsCostCSystemJunctionBeanLocal.findbyDepId(hrDepartments);
                    if (ccssJunction.getDepId() == null) {
                        fmsCostCSystemJunctionBeanLocal.saveOrUpdate(fmsCostcSystemJunction);
                        JsfUtil.addSuccessMessage("System - Cost Center Mapping Saved Succesfully");
                    } else {
                        JsfUtil.addFatalMessage("Department is already mapped.");
                    }
                    reset();
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);

            }
        } catch (Exception ex) {
        }
    }

    //reser system and cost center
    public void resetSysCC() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "resetSysCC", dataset)) {
                if (fmsCostcSystemJunction.getStatus().matches("Inactive")) {
                    fmsCostcSystemJunction.setFmsCostCenter(fmsCostCenter);
                    fmsCostcSystemJunction.setFmsLuSystem(fmsLuSystem);
                    fmsCostcSystemJunction.setDepId(hrDepartments);
                    fmsCostcSystemJunction.setStatus("Active");
                } else {
                    fmsCostcSystemJunction.setFmsCostCenter(fmsCostCenter);
                    fmsCostcSystemJunction.setFmsLuSystem(fmsLuSystem);
                    fmsCostcSystemJunction.setDepId(hrDepartments);
                    fmsCostcSystemJunction.setStatus("Inactive");
                }
                fmsCostCSystemJunctionBeanLocal.update(fmsCostcSystemJunction);
                reset();
                JsfUtil.addSuccessMessage("System - Cost Center Mapping Updated Succesfully");
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);

            }
        } catch (Exception ex) {
        }
    }

    //reser method
    public void reset() {
        costCenterList = new ArrayList<>();
        fmsCostcSystemJunction = new FmsCostcSystemJunction();
        fmsLuSystem = new FmsLuSystem();
        fmsCostCenter = new FmsCostCenter();
        costcSystemJunctionList = new ArrayList<>();
        fmsCostcSystemJunctionDataModel = new ArrayDataModel<>();
        hrDepartments = new HrDepartments();
        populateClicked = false;
    }
    
    public String getHeaderTitle() {
        return headerTitle;
    }

//create and search button
    public void setHeaderTitle(String headerTitle) {    
        this.headerTitle = headerTitle;
    }

    public void createNewSystemView() {
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateGatePass = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            headerTitle = "System - Cost Center Mapper";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGatePass = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            headerTitle = "System - Cost Center Search";
            setIcone("ui-icon-plus");
        }
    }
    
}
