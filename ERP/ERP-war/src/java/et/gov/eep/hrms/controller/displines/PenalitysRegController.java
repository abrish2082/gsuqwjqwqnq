/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.displines;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
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
import et.gov.eep.hrms.businessLogic.displine.HrDisciplinePenaltyTypesBeanLocal;
import et.gov.eep.hrms.entity.displine.HrDisciplinePenaltyTypes;
import et.gov.eep.hrms.mapper.displine.HrDisciplinePenaltyTypesFacade;

/**
 *
 * @author user
 */
@Named("penalitysRegControllers")
@ViewScoped
public class PenalitysRegController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Entity, bussiness logic & variable">
    @EJB
    HrDisciplinePenaltyTypesFacade penaltyTypesFacade;
    @EJB
    HrDisciplinePenaltyTypesBeanLocal penaltyTypesBeanLocal;

    @Inject
    HrDisciplinePenaltyTypes disciplinePenaltyTypes;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    ColumnNameResolver columnNameResolver;

    List<HrDisciplinePenaltyTypes> penalityTypeList = new ArrayList<>();
    List<HrDisciplinePenaltyTypes> allenalityNameList;
    ArrayList<HrDisciplinePenaltyTypes> allPenalityList;
    List<HrDisciplinePenaltyTypes> disciplinePenaltyTypeses;
    List<ColumnNameResolver> ColumnNameResolverList = new ArrayList<>();

    private HrDisciplinePenaltyTypes selectedRow;
    DataModel<HrDisciplinePenaltyTypes> penalityTypeDataModel;

    private String saveorUpdateBundle = "Create";
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private String activeIndex;

    private boolean disableBtnCreate;
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    private boolean btnNewRender = false;

    private String SaveOrUpdateButton = "Save";
    int updateStatus = 0;
    Set<String> declaration = new HashSet<>();
//</editor-fold>

    @PostConstruct
    private void __init() {
        penalityTypeList = penaltyTypesBeanLocal.findAll();
    }

    // <editor-fold defaultstate="collapsed" desc="Getters Setters"> 
    public List<HrDisciplinePenaltyTypes> getDisciplinePenaltyTypeses() {
        return disciplinePenaltyTypeses;
    }

    public void setDisciplinePenaltyTypeses(List<HrDisciplinePenaltyTypes> disciplinePenaltyTypeses) {
        this.disciplinePenaltyTypeses = disciplinePenaltyTypeses;
    }

    public List<HrDisciplinePenaltyTypes> getAllenalityNameList() {
        allenalityNameList = penaltyTypesBeanLocal.findAll();
        return allenalityNameList;
    }

    public void setAllenalityNameList(List<HrDisciplinePenaltyTypes> allenalityNameList) {
        this.allenalityNameList = allenalityNameList;
    }

    public ArrayList<HrDisciplinePenaltyTypes> getAllPenalityList() {
        return allPenalityList;
    }

    public void setAllPenalityList(ArrayList<HrDisciplinePenaltyTypes> allPenalityList) {
        this.allPenalityList = allPenalityList;
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

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    public HrDisciplinePenaltyTypes getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(HrDisciplinePenaltyTypes selectedRow) {
        this.selectedRow = selectedRow;
    }

    public List<HrDisciplinePenaltyTypes> getPenalityTypeList() {
        return penalityTypeList;
    }

    public void setPenalityTypeList(List<HrDisciplinePenaltyTypes> penalityTypeList) {
        this.penalityTypeList = penalityTypeList;
    }

    public DataModel<HrDisciplinePenaltyTypes> getPenalityTypeDataModel() {
        return penalityTypeDataModel;
    }

    public void setPenalityTypeDataModel(DataModel<HrDisciplinePenaltyTypes> penalityTypeDataModel) {
        this.penalityTypeDataModel = penalityTypeDataModel;
    }

    public HrDisciplinePenaltyTypes getDisciplinePenaltyTypes() {
        if (disciplinePenaltyTypes == null) {
            disciplinePenaltyTypes = new HrDisciplinePenaltyTypes();
        }
        return disciplinePenaltyTypes;
    }

    public void setDisciplinePenaltyTypes(HrDisciplinePenaltyTypes disciplinePenaltyTypes) {
        this.disciplinePenaltyTypes = disciplinePenaltyTypes;
    }

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    public List<ColumnNameResolver> getColumnNameResolverList() {
        return ColumnNameResolverList = penaltyTypesBeanLocal.findColumns();
    }

    public void setColumnNameResolverList(List<ColumnNameResolver> ColumnNameResolverList) {
        this.ColumnNameResolverList = ColumnNameResolverList;
    }

//</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Search">
    public void populate(SelectEvent events) {
        disciplinePenaltyTypes = null;
        disciplinePenaltyTypes = (HrDisciplinePenaltyTypes) events.getObject();
        SaveOrUpdateButton = "Update";
        updateStatus = 1;
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        btnNewRender = true;
        createOrSearchBundle = "Search";
        icone = "ui-icon-search";
    }

    public void penalityChanged(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            disciplinePenaltyTypes = penaltyTypesBeanLocal.findByPenalityCode(event.getNewValue().toString());
            SaveOrUpdateButton = "Update";
            updateStatus = 1;
        }
    }

    public void createNewAdditionalAmount() {
        saveorUpdateBundle = "Create";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
            btnNewRender = false;
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
            btnNewRender = false;
        }
    }

    public void newButtonAction() {
        clearPenalty();
        renderPnlCreateAdditional = true;
        renderPnlManPage = false;
        btnNewRender = false;
    }

    public void valueChangeListnerPenality(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            columnNameResolver.setCol_Name_FromTable(event.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(event.getNewValue().toString().toLowerCase() + ":"));
        }
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        return col;
    }

    public ArrayList<HrDisciplinePenaltyTypes> searchByPenaltyName(String hrPenaltyCodeOrName) {
        disciplinePenaltyTypes.setColumnValue(hrPenaltyCodeOrName);
        allPenalityList = penaltyTypesBeanLocal.searchByCol_NameAndCol_Value(columnNameResolver, disciplinePenaltyTypes.getColumnValue());
        return allPenalityList;
    }

    public void getByPenaltyName(SelectEvent event) {
        try {
            if (event.getObject() != null) {
                disciplinePenaltyTypes = (HrDisciplinePenaltyTypes) event.getObject();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Main">
    public void savePenalty() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "savePenalty", dataset)) {
                try {
                    boolean isPenaltyNameDuplicated = penaltyTypesBeanLocal.checkDuplicationByPenaltyName(disciplinePenaltyTypes);
                    if (isPenaltyNameDuplicated == true && updateStatus == 0) {
                        JsfUtil.addFatalMessage("Duplication Entry!!!");
                    } else {
                        penaltyTypesBeanLocal.saveOrUpdate(disciplinePenaltyTypes);
                        if (updateStatus == 0) {
                            JsfUtil.addSuccessMessage("Saved Successfully");
                        } else {
                            JsfUtil.addSuccessMessage("Updated Successfully");
                        }
                        clearPenalty();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JsfUtil.addFatalMessage("Error Occured while save update");
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "savePenalty");

                eventEntry.setProgram(program);

                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void clearPenalty() {
        disciplinePenaltyTypes = new HrDisciplinePenaltyTypes();
        SaveOrUpdateButton = "Save";
        updateStatus = 0;
    }

    private boolean checkDuplicate() {
        for (HrDisciplinePenaltyTypes language : penalityTypeList) {
            if (language.getPenaltyCode() != null) {
                if (language.getPenaltyCode().equalsIgnoreCase(disciplinePenaltyTypes.getPenaltyCode())) {
                    return false;
                }
            }
        }
        return true;
    }
    //</editor-fold>
}
