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
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Binyam
 */
@Named(value = "systemRegistrationController")
@ViewScoped
public class systemRegistrationController implements Serializable {

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        System.out.println("inside selected param");
        if (event.getNewValue() != null) {
            fmsLuSystem.setColumnName(String.valueOf(event.getNewValue()));
            fmsLuSystem.setColumnValue(null);
            
             System.out.println("fmsLuSystem.getColumnName(); " + fmsLuSystem.getColumnName());
            System.out.println("fmsLuSystem.getColumnValue(); " + fmsLuSystem.getColumnValue());
        }
    }
    List<FmsLuSystem> FmsLuSystemSearchingParameterList;

    public List<FmsLuSystem> getFmsLuSystemSearchingParameterList() {
        if (FmsLuSystemSearchingParameterList == null) {
            FmsLuSystemSearchingParameterList = new ArrayList<>();
            FmsLuSystemSearchingParameterList = fmsLuSystemBeanLocal.getFmsLuSystemSearchingParameterList();
            System.out.println("FmsVoucherSearchingParameterList==" + FmsLuSystemSearchingParameterList);
        }
        return FmsLuSystemSearchingParameterList;

    }

    public void setFmsLuSystemSearchingParameterList(List<FmsLuSystem> FmsLuSystemSearchingParameterList) {
        this.FmsLuSystemSearchingParameterList = FmsLuSystemSearchingParameterList;
    }

    DataModel<FmsLuSystem> fmsVoucherDataModel;

    public DataModel<FmsLuSystem> getFmsVoucherDataModel() {
        return fmsVoucherDataModel;
    }

    public void setFmsVoucherDataModel(DataModel<FmsLuSystem> fmsVoucherDataModel) {
        this.fmsVoucherDataModel = fmsVoucherDataModel;
    }

    public List<FmsLuSystem> getFmsLuSystemList1() {
        return fmsLuSystemList1;
    }

    public void setFmsLuSystemList1(List<FmsLuSystem> fmsLuSystemList1) {
        this.fmsLuSystemList1 = fmsLuSystemList1;
    }

    public void recreateJvDataModel() {
        fmsVoucherDataModel = null;
        fmsVoucherDataModel = new ListDataModel(new ArrayList(fmsLuSystemList1));
    }
    List<FmsLuSystem> fmsLuSystemList1;

// @Inject
//    SessionBean sessionBeanLocal;
    public void search_vouchers() {
//        fmsLuSystem.setPreparedBy(sessionBeanLocal.getUserId());
//        fmsLuSystem.setType("CHPV");
        fmsLuSystemList1 = fmsLuSystemBeanLocal.searchAllVochNo(fmsLuSystem);
        recreateJvDataModel();
        fmsLuSystem = new FmsLuSystem();
    }

//<editor-fold defaultstate="collapsed" desc="@EJB and @Inject declaration">
//@Inject
    @Inject
    SessionBean SessionBean;
    @Inject
    FmsLuSystem fmsLuSystem;

//@EJB
    @EJB
    FmsLuSystemBeanLocal fmsLuSystemBeanLocal;
    private FmsLuSystem selectedSystem;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Object and variable declaration">
    List<FmsLuSystem> systemList = new ArrayList<>();
    DataModel<FmsLuSystem> systemDataMOdel;
    private String systemSaveOrUpdateButton = "Save";
    private String createOrSearchBundle = "New";
    private String headerTitle = "System Search";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private boolean sysupdate = false;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private boolean renderpnlSearchPage;
//</editor-fold>

    public systemRegistrationController() {
    }

    @PostConstruct
    public void init() {
        fmsLuSystem = new FmsLuSystem();
    }
    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">

    public List<FmsLuSystem> getSystemList() {
        if (systemList == null) {
            systemList = new ArrayList<>();
        }
        return systemList;
    }

    public void setSystemList(List<FmsLuSystem> systemList) {
        this.systemList = systemList;
    }

    public DataModel<FmsLuSystem> getSystemDataMOdel() {
        return systemDataMOdel;
    }

    public void setSystemDataMOdel(DataModel<FmsLuSystem> systemDataMOdel) {
        this.systemDataMOdel = systemDataMOdel;
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

    public boolean isRenderpnlSearchPage() {
        return renderpnlSearchPage;
    }

    public void setRenderpnlSearchPage(boolean renderpnlSearchPage) {
        this.renderpnlSearchPage = renderpnlSearchPage;
    }

    public FmsLuSystem getSelectedSystem() {
        return selectedSystem;
    }

    public void setSelectedSystem(FmsLuSystem selectedSystem) {
        this.selectedSystem = selectedSystem;
    }

    public boolean issysupdate() {
        return sysupdate;
    }

    public void setsysupdate(boolean sysupdate) {
        this.sysupdate = sysupdate;
    }

    public FmsLuSystem getFmsLuSystem() {
        if (fmsLuSystem == null) {
            fmsLuSystem = new FmsLuSystem();
        }
        return fmsLuSystem;
    }

    public void setFmsLuSystem(FmsLuSystem fmsLuSystem) {
        this.fmsLuSystem = fmsLuSystem;
    }

    public String getsystemSaveOrUpdateButton() {
        return systemSaveOrUpdateButton;
    }

    public void setsystemSaveOrUpdateButton(String systemSaveOrUpdateButton) {
        this.systemSaveOrUpdateButton = systemSaveOrUpdateButton;
    }

    public FmsLuSystemBeanLocal getFmsLuSystemBeanLocal() {
        return fmsLuSystemBeanLocal;
    }

    public void setFmsLuSystemBeanLocal(FmsLuSystemBeanLocal fmsLuSystemBeanLocal) {
        this.fmsLuSystemBeanLocal = fmsLuSystemBeanLocal;
    }
//</editor-fold>

    //save
    public void createSystem() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "createSystem", dataset)) {

                try {
                    if (fmsLuSystem.getSystemCode() != null && fmsLuSystem.getSystemName() != null && fmsLuSystem.getSystemCode().length() == 4) {
                        if (sysupdate == false) {
                            fmsLuSystemBeanLocal.create(fmsLuSystem);
                            JsfUtil.addSuccessMessage("System Created Successfully");

                        } else {
                            fmsLuSystemBeanLocal.edit(fmsLuSystem);
                            JsfUtil.addSuccessMessage("System Updated Successfully");
                            sysupdate = false;
                        }
                    } else {
                        JsfUtil.addFatalMessage("System form must contain correct values");
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                resetSys();

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

    //delete system
    public void deleteSystem() {
        fmsLuSystemBeanLocal.deleteSys(fmsLuSystem);
        JsfUtil.addSuccessMessage("System Deleted Successfully");
        fmsLuSystem = new FmsLuSystem();
    }

    //reset system
    public void resetSys() {
        try {
            fmsLuSystem = null;
            systemSaveOrUpdateButton = "Save";
            sysupdate = false;
            systemDataMOdel = null;
        } catch (Exception e) {
            throw e;
        }
    }

    //select event for system
    public void populateSystem(SelectEvent event) {
        fmsLuSystem.setSystemCode(event.getObject().toString());
        fmsLuSystem.getSystemCode();
        fmsLuSystem = fmsLuSystemBeanLocal.getSysDetail(fmsLuSystem);
        systemSaveOrUpdateButton = "Update";
        sysupdate = true;
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        renderpnlSearchPage = true;
        createOrSearchBundle = "New";
        headerTitle = "System Registration";
        setIcone("ui-icon-plus");
    }

    // search system list
    public void findSysList() {
        systemList = new ArrayList<>();
        if (fmsLuSystem.getSystemCode().isEmpty()) {
            systemList = fmsLuSystemBeanLocal.findAll();
            systemDataMOdel = new ListDataModel(new ArrayList(systemList));
        } else {
            systemList = fmsLuSystemBeanLocal.findBySytemCodeLike(fmsLuSystem);
            systemDataMOdel = new ListDataModel(new ArrayList(systemList));
        }
    }

    //select item
    public SelectItem[] getStatus() {
        return JsfUtil.getSelectItems(stat(), true);
    }

    //array list for status
    public ArrayList<String> stat() {
        ArrayList<String> position = new ArrayList<>();
        position.add("Active");
        position.add("Inactive");
        return position;
    }

    //search button action
    public void goBackToSearchPageBtnAction() {
        renderPnlManPage = true;
        renderPnlCreateGatePass = false;
        renderpnlSearchPage = false;
    }

    //display recieved data
    public void dispReceivedData(FmsLuSystem LuSystem) {
        setFmsLuSystem(LuSystem);
    }

    //create and search button
    public void createNewSystemView() {
        disableBtnCreate = false;
        renderpnlSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            resetSys();
            renderPnlCreateGatePass = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            headerTitle = "System Registration";
            systemSaveOrUpdateButton = "Save";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGatePass = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            headerTitle = "System Search";
            systemSaveOrUpdateButton = "Update";
            setIcone("ui-icon-plus");
            resetSys();
        }
    }

}
