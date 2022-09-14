package et.gov.eep.hrms.controller.succession;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.succession.CompetencyLocal;
import et.gov.eep.hrms.entity.succession.HrSmCompetency;

@Named("hrCompetencyController")
@ViewScoped
public class hrCompetencyController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Entity, bussiness logic & variables">
    @EJB
    CompetencyLocal competencyLocal;
    @Inject
    HrSmCompetency hrSmCompetency;
    @Inject
    SessionBean SessionBean;
    @Inject
    ColumnNameResolver columnNameResolver;

    DataModel<HrSmCompetency> competencyDataModel = new ListDataModel<>();
    ArrayList<HrSmCompetency> competencyList = new ArrayList<>();
    List<HrSmCompetency> competencys;
    HrSmCompetency selectedRow = null;
    List<ColumnNameResolver> ColumnNameResolverList = new ArrayList<>();

    private boolean renderPnlCreateGatePass = false;
    private boolean disableBtnCreate;
    private String saveorUpdateBundle = "Create";
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = false;
    private boolean position = false;
    private boolean renderPnlManPage = true;
    private boolean btnNewRender = false;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private String tab = "disabled";
    private boolean renderPnlApproved = false;
    private boolean checkBoxReqst = false;
    private boolean checkBoxApprove = false;
    private String saveOrUpdateButton = "Save";
    int update = 0;
// </editor-fold> 
    //<editor-fold defaultstate="collapsed" desc="geter and setter">

    public boolean isRenderPnlCreateGatePass() {
        return renderPnlCreateGatePass;
    }

    public void setRenderPnlCreateGatePass(boolean renderPnlCreateGatePass) {
        this.renderPnlCreateGatePass = renderPnlCreateGatePass;
    }

    public boolean isPosition() {
        return position;
    }

    public void setPosition(boolean position) {
        this.position = position;
    }

    public void displayOffenceChanged(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            HrSmCompetency offenceTypes = (HrSmCompetency) event.getNewValue();
        }
    }

    public DataModel<HrSmCompetency> getCompetencyDataModel() {
        return competencyDataModel;
    }

    public void setCompetencyDataModel(DataModel<HrSmCompetency> competencyDataModel) {
        this.competencyDataModel = competencyDataModel;
    }

    public HrSmCompetency getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(HrSmCompetency selectedRow) {
        this.selectedRow = selectedRow;
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

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
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

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
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

    public HrSmCompetency getHrSmCompetency() {
        if (hrSmCompetency == null) {
            hrSmCompetency = new HrSmCompetency();
        }
        return hrSmCompetency;
    }

    public void setHrSmCompetency(HrSmCompetency hrSmCompetency) {
        this.hrSmCompetency = hrSmCompetency;
    }

    public List<HrSmCompetency> getCompetencys() {
        return competencys;
    }

    public void setCompetencys(List<HrSmCompetency> competencys) {
        this.competencys = competencys;
    }

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    public List<ColumnNameResolver> getColumnNameResolverList() {
        return ColumnNameResolverList = competencyLocal.findColumns();
    }

    public void setColumnNameResolverList(List<ColumnNameResolver> ColumnNameResolverList) {
        this.ColumnNameResolverList = ColumnNameResolverList;
    }

    public ArrayList<HrSmCompetency> getCompetencyList() {
        return competencyList;
    }

    public void setCompetencyList(ArrayList<HrSmCompetency> competencyList) {
        this.competencyList = competencyList;
    }

//</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Search">
    public void createNewAdditionalAmount() {
        saveorUpdateBundle = "Create";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            btnNewRender = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            btnNewRender = false;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public void newButtonAction() {
        hrSmCompetency = new HrSmCompetency();
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = false;
        update = 0;
        saveOrUpdateButton = "Save";
    }

    public void populate(SelectEvent events) {
        hrSmCompetency = (HrSmCompetency) events.getObject();
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        btnNewRender = true;
        createOrSearchBundle = "Search";
        update = 1;
        saveOrUpdateButton = "Update";
        setIcone("ui-icon-search");
    }

    public void findByCompetencyName() {
        if (hrSmCompetency.getCompetencyName().isEmpty()) {
            competencys = competencyLocal.findAllCompetency(hrSmCompetency);
            competencyDataModel = new ListDataModel(new ArrayList(competencys));
        } else {
            competencys = competencyLocal.findbycompetencyname(hrSmCompetency);
            competencyDataModel = new ListDataModel(new ArrayList(competencys));
        }
    }

    public void findall() {
        competencyLocal.FindAll(hrSmCompetency);
    }

    public void valueChangeListnerCompetency(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            columnNameResolver.setCol_Name_FromTable(event.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(event.getNewValue().toString().toLowerCase() + ":"));
        }
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        return col;
    }

    public ArrayList<HrSmCompetency> searchByCompetencyName(String hrPenaltyCodeOrName) {
        hrSmCompetency.setColumnValue(hrPenaltyCodeOrName);
        competencyList = competencyLocal.searchByCol_NameAndCol_Value(columnNameResolver, hrSmCompetency.getColumnValue());
        return competencyList;
    }

    public void getByCompetencyName(SelectEvent event) {
        try {
            if (event.getObject() != null) {
                hrSmCompetency = (HrSmCompetency) event.getObject();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Main method">

    public void saveCompetency() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveCompetency", dataset)) {
                try {
                    boolean result = competencyLocal.searchdup(hrSmCompetency);
                    if (update == 0 && result == true) {
                        JsfUtil.addFatalMessage("data already exist.");
                    } else {
                        competencyLocal.SaveOrUpdate(hrSmCompetency);
                        if (update == 0) {
                            JsfUtil.addSuccessMessage("Saved Successfuly.");
                        } else {
                            JsfUtil.addSuccessMessage("Update Successfuly.");
                            saveOrUpdateButton = "Save";
                        }
                        hrSmCompetency = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JsfUtil.addErrorMessage("Error occured while save update");
                }

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveCompetency");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resetCompetency() {
        hrSmCompetency = null;
        hrSmCompetency = new HrSmCompetency();
    }

    //</editor-fold>
}
