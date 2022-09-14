/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.adminstrator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
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
import et.gov.eep.fcms.businessLogic.admin.FmsAccountTypeBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsAccountType;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.pms.businessLogic.PmsCreateProjectBeanLocal;
import et.gov.eep.pms.entity.PmsCreateProjects;

/**
 *
 * @author Binyam
 */
@Named(value = "generalledgercontroller")
@ViewScoped
public class generalledgercontroller implements Serializable {
//<editor-fold defaultstate="collapsed" desc="@EJB and @Inject declaration">
//@Inject

    @Inject
    SessionBean SessionBean;
    @Inject
    private FmsAccountType fmsAccountType;

    //@EJB
    @EJB
    private FmsAccountTypeBeanLocal fmsAccountTypeBeanLocal;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @Inject
    PmsCreateProjects pmsCreateProjects;
    @EJB
    FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    PmsCreateProjectBeanLocal pmsCreateProjectBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Object and variable declaration">
    private String createOrSearchBundle = "New";
    private String headerTitle = "General Ledger Search";
    private String icone = "ui-icon-plus";
    private String GLSaveOrUpdateButton = "Save";
    private String projOption;
    private boolean isRenderCreate = true;
    private boolean disableBtnCreate;
    private boolean renderPnlToSearchPage;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private Boolean projCheck = false;
    private Boolean update = false;
    private Boolean projRender = false;
    private FmsGeneralLedger selectedGL;
    List<FmsGeneralLedger> genLedgerList;
    DataModel<FmsGeneralLedger> genLedgerDModel;
    private List<FmsAccountType> acctTypeList;
//</editor-fold>

    public generalledgercontroller() {
    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    public FmsGeneralLedger getSelectedGL() {
        return selectedGL;
    }

    public void setSelectedGL(FmsGeneralLedger selectedGL) {
        this.selectedGL = selectedGL;
    }

    public Boolean getProjCheck() {
        return projCheck;
    }

    public void setProjCheck(Boolean projCheck) {
        this.projCheck = projCheck;
    }

    public FmsGeneralLedger getFmsGeneralLedger() {
        if (fmsGeneralLedger == null) {
            fmsGeneralLedger = new FmsGeneralLedger();
        }
        return fmsGeneralLedger;
    }

    public void setFmsGeneralLedger(FmsGeneralLedger fmsGeneralLedger) {
        this.fmsGeneralLedger = fmsGeneralLedger;
    }

    public String getProjOption() {
        return projOption;
    }

    public void setProjOption(String projOption) {
        this.projOption = projOption;
    }

    public Boolean getProjRender() {
        return projRender;
    }

    public void setProjRender(Boolean projRender) {
        this.projRender = projRender;
    }

    public String getGLSaveOrUpdateButton() {
        return GLSaveOrUpdateButton;
    }

    public void setGLSaveOrUpdateButton(String GLSaveOrUpdateButton) {
        this.GLSaveOrUpdateButton = GLSaveOrUpdateButton;
    }

    public boolean isRenderPnlToSearchPage() {
        return renderPnlToSearchPage;
    }

    public void setRenderPnlToSearchPage(boolean renderPnlToSearchPage) {
        this.renderPnlToSearchPage = renderPnlToSearchPage;
    }

    public PmsCreateProjects getPmsCreateProjects() {
        if (pmsCreateProjects == null) {
            pmsCreateProjects = new PmsCreateProjects();
        }
        return pmsCreateProjects;
    }

    public void setPmsCreateProjects(PmsCreateProjects pmsCreateProjects) {
        this.pmsCreateProjects = pmsCreateProjects;
    }

    public FmsGeneralLedgerBeanLocal getFmsGeneralLedgerBeanLocal() {
        return fmsGeneralLedgerBeanLocal;
    }

    public void setFmsGeneralLedgerBeanLocal(FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal) {
        this.fmsGeneralLedgerBeanLocal = fmsGeneralLedgerBeanLocal;
    }

    public PmsCreateProjectBeanLocal getPmsCreateProjectBeanLocal() {
        return pmsCreateProjectBeanLocal;
    }

    public void setPmsCreateProjectBeanLocal(PmsCreateProjectBeanLocal pmsCreateProjectBeanLocal) {
        this.pmsCreateProjectBeanLocal = pmsCreateProjectBeanLocal;
    }

    public Boolean getUpdate() {
        return update;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
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

    public List<FmsGeneralLedger> getGenLedgerList() {
        return genLedgerList;
    }

    public void setGenLedgerList(List<FmsGeneralLedger> genLedgerList) {
        this.genLedgerList = genLedgerList;
    }

    public DataModel<FmsGeneralLedger> getGenLedgerDModel() {
        return genLedgerDModel;
    }

    public void setGenLedgerDModel(DataModel<FmsGeneralLedger> genLedgerDModel) {
        this.genLedgerDModel = genLedgerDModel;
    }

    public boolean isIsRenderCreate() {
        return isRenderCreate;
    }

    public void setIsRenderCreate(boolean isRenderCreate) {
        this.isRenderCreate = isRenderCreate;
    }

    public FmsAccountType getFmsAccountType() {
        if (fmsAccountType == null) {
            fmsAccountType = new FmsAccountType();
        }
        return fmsAccountType;
    }

    public void setFmsAccountType(FmsAccountType fmsAccountType) {
        this.fmsAccountType = fmsAccountType;
    }

    public List<FmsAccountType> getAcctTypeList() {
        if (acctTypeList == null) {
            acctTypeList = new ArrayList<>();
        }
        acctTypeList = fmsAccountTypeBeanLocal.findAll();
        return acctTypeList;
    }

    public void setAcctTypeList(List<FmsAccountType> acctTypeList) {
        this.acctTypeList = acctTypeList;
    }
//</editor-fold>

//search button action
    public void goBackSearchButtonAction() {
        renderPnlToSearchPage = false;
        renderPnlCreateGatePass = false;
        renderPnlManPage = true;

    }

    //create
    public void createGLedger() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "createGLedger", dataset)) {
                checkAccountType();
                if (fmsGeneralLedger != null) {
                    System.out.println("----fmsAccountType 1-----" + fmsAccountType.getType());
                    fmsGeneralLedger.setAccountCategory(fmsAccountType);
                    if (fmsGeneralLedger.getGeneralLedgerCode() != null && update == false) {
                        fmsGeneralLedgerBeanLocal.create(fmsGeneralLedger);
                        JsfUtil.addSuccessMessage("General Ledger Created Succesfully");
                    } else {
                        System.out.println("----fmsAccountType-----" + fmsAccountType.getType());
                        fmsGeneralLedgerBeanLocal.edit(fmsGeneralLedger);
                        JsfUtil.addSuccessMessage("General Ledger Updated Succesfully");
                        update = false;
                    }
                } else {
                    JsfUtil.addSuccessMessage("General Ledger form must have values");
                }
                resetGL();

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

    //delete GL ledger
    public void deleteGLedger() {
        fmsGeneralLedgerBeanLocal.delete(fmsGeneralLedger);
        JsfUtil.addSuccessMessage("General Ledger Deleted");
        fmsGeneralLedger = new FmsGeneralLedger();
    }

    //reset GL ledger
    public void resetGL() {
        try {
            fmsGeneralLedger = new FmsGeneralLedger();
            fmsAccountType = new FmsAccountType();
            GLSaveOrUpdateButton = "Save";
            genLedgerDModel = null;
        } catch (Exception e) {
            throw e;
        }
    }

    //Value chage event for lefger type
    public void LedgerType(ValueChangeEvent e) {
        if (e.getNewValue().toString().equalsIgnoreCase("ProjectJob")) {
            projRender = true;
            projOption = "Project Job";
        } else if (e.getNewValue().toString().equalsIgnoreCase("NonProjectJob")) {
            projRender = false;
            projOption = "Non Project Job";
        }
    }

    //select event
    public void populate(SelectEvent event) {
        fmsGeneralLedger = (FmsGeneralLedger) event.getObject();
        fmsAccountType = (fmsGeneralLedger.getAccountCategory());
        if (fmsGeneralLedger.getAccountType() == 1) {
            projCheck = true;
        } else {
            projCheck = false;
        }
        update = true;
        GLSaveOrUpdateButton = "Update";
        renderPnlCreateGatePass = true;
        renderPnlToSearchPage = true;
        renderPnlManPage = false;
        createOrSearchBundle = "New";
        headerTitle = "General Ledger Registration";
        setIcone("ui-icon-plus");
    }

    //select item
    public SelectItem[] getStatus() {
        return JsfUtil.getSelectItems(stat(), true);
    }

    //select item for find all
    public SelectItem[] findProject() {
        return JsfUtil.getSelectItems(pmsCreateProjectBeanLocal.findAll(), true);
    }

    //array list
    public ArrayList<String> stat() {
        ArrayList<String> position = new ArrayList<>();
        position.add("Active");
        position.add("Inactive");
        return position;
    }

    //check account type
    public void checkAccountType() {
        if (projCheck) {
            fmsGeneralLedger.setAccountType(1);
        } else {
            fmsGeneralLedger.setAccountType(2);
        }
    }

    //List for find all
    public List<FmsGeneralLedger> findGenL() {
        return this.fmsGeneralLedgerBeanLocal.findAll();
    }

    //GL search
    public void LedgerSearch() {
        if (fmsGeneralLedger == null) {
            genLedgerList = fmsGeneralLedgerBeanLocal.findAll();
            genLedgerDModel = new ListDataModel(new ArrayList(genLedgerList));
        } else {
            genLedgerList = fmsGeneralLedgerBeanLocal.searchGenLedger(fmsGeneralLedger);
            genLedgerDModel = new ListDataModel(new ArrayList(genLedgerList));
        }
    }

    //create and search button
    public void createNewGLView() {
        disableBtnCreate = false;
        renderPnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            resetGL();
            renderPnlCreateGatePass = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            headerTitle = "General Ledger Registration";
            GLSaveOrUpdateButton = "Save";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGatePass = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            headerTitle = "General Ledger Search";
            GLSaveOrUpdateButton = "Update";
            setIcone("ui-icon-plus");
        }
    }

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            fmsGeneralLedger.setColumnName(event.getNewValue().toString());
            fmsGeneralLedger.setColumnValue(null);
            System.out.println("fmsVoucher.getColumnName(); " + fmsGeneralLedger.getColumnName());
            System.out.println("fmsVoucher.getColumnValue(); " + fmsGeneralLedger.getColumnValue());

        }
    }
    
       List<FmsGeneralLedger> FmsGeneralLedgerSearchingParameterList;
      

    public List<FmsGeneralLedger> getFmsGeneralLedgerSearchingParameterList() {
        if (FmsGeneralLedgerSearchingParameterList == null) {
            FmsGeneralLedgerSearchingParameterList = new ArrayList<>();
            FmsGeneralLedgerSearchingParameterList = fmsGeneralLedgerBeanLocal.getFmsGeneralLedgerSearchingParameterList();
            System.out.println("FmsGeneralLedgerSearchingParameterList==" + FmsGeneralLedgerSearchingParameterList);
        }
        return FmsGeneralLedgerSearchingParameterList;

    }

    public void setFmsGeneralLedgerSearchingParameterList(List<FmsGeneralLedger> FmsGeneralLedgerSearchingParameterList) {
        this.FmsGeneralLedgerSearchingParameterList = FmsGeneralLedgerSearchingParameterList;
    }
    List<FmsGeneralLedger> fmsVouchersList1;
    DataModel<FmsGeneralLedger> fmsGeneralLedgerDataModel;

    public DataModel<FmsGeneralLedger> getFmsGeneralLedgerDataModel() {
        return fmsGeneralLedgerDataModel;
    }

    public void setFmsGeneralLedgerDataModel(DataModel<FmsGeneralLedger> fmsGeneralLedgerDataModel) {
        this.fmsGeneralLedgerDataModel = fmsGeneralLedgerDataModel;
    }

   

   

    public void recreateJvDataModel() {
        fmsGeneralLedgerDataModel = null;
        fmsGeneralLedgerDataModel = new ListDataModel(new ArrayList(fmsVouchersList1));
    }
    
        public void search_vouchers() {
//        fmsVoucher.setPreparedBy(workFlow.getUserName());
//        fmsVoucher.setType("CHPV");
        fmsVouchersList1 = fmsGeneralLedgerBeanLocal.searchAllVochNo(fmsGeneralLedger);
        System.out.println("fmsVouchersList1==" + fmsVouchersList1);
        recreateJvDataModel();
        fmsGeneralLedger = new FmsGeneralLedger();
    }
}
