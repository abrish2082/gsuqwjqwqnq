/*
 *This class implements creating and updating budget codes
 *which are also integrated with the general ledger.
 */
package et.gov.eep.fcms.controller.budget;
//<editor-fold defaultstate="collapsed" desc="import">
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.budgetCodeBeanLocal;
import et.gov.eep.fcms.entity.budget.FmsBudgetCode;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    //</editor-fold>


/**
 *
 * @author Binyam F.
 */
@Named(value = "budgetCodeController")
@ViewScoped
public class BudgetCode implements Serializable {
//<editor-fold defaultstate="collapsed" desc="Inject">
      @Inject
    FmsBudgetCode budgetKode;
    @Inject
    SessionBean SessionBean;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="EJB">
    @EJB
    private budgetCodeBeanLocal budgetCodeBean;
    @EJB
    FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variable declarations">
     private FmsBudgetCode selectList;
    String update = "Save";
    int status = 0;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = true;
    private boolean renderPnlManPage = false;
    private String icone = "ui-icon-plus";
    private boolean buttonRenderd = false;
    private boolean renderpnlSearchPage;
    private String headerTitle = "Budget Code Search";
    private FmsBudgetCode budgetCodeSelect;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getter and setters">
     public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public FmsBudgetCode getBudgetCodeSelect() {
        return budgetCodeSelect;
    }

    public void setBudgetCodeSelect(FmsBudgetCode budgetCodeSelect) {
        this.budgetCodeSelect = budgetCodeSelect;
    }

    DataModel<FmsBudgetCode> budgetCodeDataModel;

    public DataModel<FmsBudgetCode> getBudgetCodeDataModel() {
        if (budgetCodeDataModel == null) {
            budgetCodeDataModel = new ListDataModel<>();
        }
        return budgetCodeDataModel;
    }

    public void setBudgetCodeDataModel(DataModel<FmsBudgetCode> budgetCodeDataModel) {
        this.budgetCodeDataModel = budgetCodeDataModel;
    }

    public boolean isButtonRenderd() {
        return buttonRenderd;
    }

    public void setButtonRenderd(boolean buttonRenderd) {
        this.buttonRenderd = buttonRenderd;
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

    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }

    public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public boolean isRenderpnlSearchPage() {
        return renderpnlSearchPage;
    }

    public void setRenderpnlSearchPage(boolean renderpnlSearchPage) {
        this.renderpnlSearchPage = renderpnlSearchPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    /**
     *
     * @return
     */
    public FmsBudgetCode getBudgetKode() {
        if (budgetKode == null) {
            budgetKode = new FmsBudgetCode();
        }
        return budgetKode;
    }

    /**
     *
     * @param budgetKode
     */
    public void setBudgetKode(FmsBudgetCode budgetKode) {
        this.budgetKode = budgetKode;
    }

    /**
     *
     * @return
     */
    public FmsBudgetCode getSelectList() {
        return selectList;
    }

    /**
     *
     * @param selectList
     */
    public void setSelectList(FmsBudgetCode selectList) {
        this.selectList = selectList;
    }

    /**
     *
     * @return
     */
    public String getUpdate() {
        return update;
    }

    /**
     *
     * @param update
     */
    public void setUpdate(String update) {
        this.update = update;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other metheds">
    public void saveBudgetCode() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveBudgetCode", dataset)) {

                if (status == 0) {
                    budgetCodeBean.create(budgetKode);
                    JsfUtil.addSuccessMessage("Data is saved successfully.");
                } else {
                    budgetCodeBean.edit(budgetKode);
                    JsfUtil.addSuccessMessage("Data is edited successfully.");
                }
     
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);

            }
        } catch (Exception ex) {
        }
    }

    /**
     *
     */
    public void deleteBgtCode() {
        budgetCodeBean.delete(budgetKode);
        JsfUtil.addSuccessMessage("Budget code deleted.");
        budgetKode = new FmsBudgetCode();
    }

    /**
     *
     */
    public void resetBudgetCode() {
        try {
            budgetKode = new FmsBudgetCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private List<FmsBudgetCode> budgetcodList;

    /**
     *
     * @return
     */
    public List<FmsBudgetCode> getBudgetcodList() {
        budgetcodList = budgetCodeBean.findAll();
        return budgetcodList;
    }

    /**
     *
     * @param budgetcodList
     */
    public void setBudgetcodList(List<FmsBudgetCode> budgetcodList) {
        this.budgetcodList = budgetcodList;
    }

    /**
     *
     * @param BudgetCode
     * @return
     */
    public ArrayList<FmsBudgetCode> searchBudgetCode(String BudgetCode) {
        ArrayList<FmsBudgetCode> budgetCodes = null;
        budgetKode.setBudgetCode(BudgetCode);
        budgetCodes = budgetCodeBean.searchBudgetCode(budgetKode);
        return budgetCodes;
    }

    /**
     *
     * @param event
     */
    public void getbudgetBudgetCode(SelectEvent event) {
        budgetKode.setBudgetCode(event.getObject().toString());
        budgetKode = budgetCodeBean.getBudgetCode(budgetKode);
        renderPnlManPage = true;
        buttonRenderd = true;
        status = 1;
        update = "Update";
    }

    /**
     *
     * @param event
     */
    public void createNewBudgetCodeView() {
       
        renderpnlSearchPage = false;

        clearPage();
        if (createOrSearchBundle.equalsIgnoreCase("New")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            createOrSearchBundle = "Search";
            buttonRenderd = true;
            update = "Save";
            budgetKode = null;
            headerTitle = "Budget Code Registration";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equalsIgnoreCase("Search")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            createOrSearchBundle = "New";
            buttonRenderd = false;
            update = "Edit";
            budgetKode = null;
            headerTitle = "Budget Code Search";
            setIcone("ui-icon-plus");
        }
        budgetCodeDataModel = null;
    }

    private void clearPage() {
        budgetKode = new FmsBudgetCode();
        selectList = new FmsBudgetCode();
        update = null;
    }
    private List<FmsBudgetCode> BudgeCodetList;

    public List<FmsBudgetCode> getBudgeCodetList() {
        if (BudgeCodetList == null) {
            BudgeCodetList = new ArrayList<>();
        }
        return BudgeCodetList;
    }

    public void setBudgeCodetList(List<FmsBudgetCode> BudgeCodetList) {
        this.BudgeCodetList = BudgeCodetList;
    }

    public void populate(SelectEvent event) {
        budgetKode.setBudgetCode(event.getObject().toString());
        budgetKode.getBudgetCode();
        budgetKode = budgetCodeBean.getBudgetCode(budgetKode);
        status = 1;
        update = "Edit";
    }

    public void searchBudgetCode() {
        BudgeCodetList = new ArrayList<>();
        if (budgetKode.getBudgetCode().isEmpty()) {
            BudgeCodetList = budgetCodeBean.findAll();
            if (BudgeCodetList.isEmpty()) {
                JsfUtil.addFatalMessage("No record found.");
            } else {
                budgetCodeDataModel = new ListDataModel(BudgeCodetList);
            }
        } else {
            BudgeCodetList = budgetCodeBean.searchBudgetCode(budgetKode);
            if (BudgeCodetList.isEmpty()) {
                JsfUtil.addFatalMessage("No record found.");
            } else {
                budgetCodeDataModel = new ListDataModel(BudgeCodetList);
            }
            status = 1;
            update = "Update";
        }
    }

    private String BgtCode;

    public String getBgtCode() {
        return BgtCode;
    }

    public void setBgtCode(String BgtCode) {
        this.BgtCode = BgtCode;
    }

    public void requestIdChanged(SelectEvent event) {
        budgetKode = (FmsBudgetCode) event.getObject();
        budgetKode.setBudgetId(budgetKode.getBudgetId());
        budgetKode = budgetCodeBean.getSelectedRequest(budgetKode.getBudgetId());
        renderPnlManPage = true;
        renderpnlSearchPage = true;
        renderPnlCreateAdditional = false;
        status = 1;
        update = "Update";
        createOrSearchBundle = "New";
        headerTitle = "Budget Code Registration";
        setIcone("ui-icon-plus");
    }

    public SelectItem[] getGLdata() {
        return JsfUtil.getSelectItems(fmsGeneralLedgerBeanLocal.findAll(), true);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Inject">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Inject">
    //</editor-fold>
    
    
  

   

   

    /**
     *
     */
    
}
