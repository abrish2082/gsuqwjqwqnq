/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.displines;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
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
import et.gov.eep.hrms.businessLogic.displine.HrDisciplineOffenceTypesBeanLocal;
import et.gov.eep.hrms.businessLogic.displine.HrDisciplinePenlitysBeansLocal;
import et.gov.eep.hrms.entity.displine.HrDisciplineOffenceTypes;
import et.gov.eep.hrms.entity.displine.HrDisciplinePenlitys;

/**
 *
 * @author user
 */
@Named("offenceTypesControllers")
@ViewScoped
public class OffenceTypesController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Entity, bussiness logic & variables">
    @EJB
    HrDisciplineOffenceTypesBeanLocal offenceTypesBeanLocal;
    @EJB
    HrDisciplinePenlitysBeansLocal disciplinePenlitysBeansLocal;

    @Inject
    HrDisciplineOffenceTypes offenceTypes;
    @Inject
    HrDisciplinePenlitys offencePenlitys;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    ColumnNameResolver columnNameResolver;

    private String saveorUpdateBundle = "Create";
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private String activeIndex;

    private boolean disableBtnCreate;
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    private boolean btnNewRender = false;

    DataModel<HrDisciplinePenlitys> offencePenlitysModels;
    List<HrDisciplineOffenceTypes> offenceList;
    private HrDisciplineOffenceTypes selectedRow;
    DataModel<HrDisciplineOffenceTypes> OffenceTypesDataModel;
    List<HrDisciplineOffenceTypes> nameList;
    ArrayList<HrDisciplineOffenceTypes> offenceNameList;
    List<HrDisciplineOffenceTypes> offenceTypeList;
    List<HrDisciplineOffenceTypes> offenceType;
    List<ColumnNameResolver> ColumnNameResolverList = new ArrayList<>();

    private String SaveOrUpdateButton = "Save";
    int updateStatus = 0;

//</editor-fold>
    @PostConstruct
    public void init() {
//        nameList = offenceTypesBeanLocal.findAlls();
        offenceTypes = (HrDisciplineOffenceTypes) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("offenceType");
        if (offenceTypes == null || offenceTypes.getId() == null) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        } else {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");

        }
    }
    // <editor-fold defaultstate="collapsed" desc="Getters setters"> 

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

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
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

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    public List<HrDisciplineOffenceTypes> getOffenceList() {
        return offenceList;
    }

    public void setOffenceList(List<HrDisciplineOffenceTypes> offenceList) {
        this.offenceList = offenceList;
    }

    public HrDisciplineOffenceTypes getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(HrDisciplineOffenceTypes selectedRow) {
        this.selectedRow = selectedRow;
    }

    public DataModel<HrDisciplinePenlitys> getOffencePenlitysModels() {
        if (offencePenlitysModels == null) {
            offencePenlitysModels = new ListDataModel<>();
        }
        return offencePenlitysModels;
    }

    public void setOffencePenlitysModels(DataModel<HrDisciplinePenlitys> offencePenlitysModels) {
        this.offencePenlitysModels = offencePenlitysModels;
    }

    public DataModel<HrDisciplineOffenceTypes> getOffenceTypesDataModel() {
        if (OffenceTypesDataModel == null) {
            OffenceTypesDataModel = new ListDataModel<>();
        }
        return OffenceTypesDataModel;
    }

    public void setOffenceTypesDataModel(DataModel<HrDisciplineOffenceTypes> OffenceTypesDataModel) {
        this.OffenceTypesDataModel = OffenceTypesDataModel;
    }

    public HrDisciplinePenlitys getOffencePenlitys() {
        if (offencePenlitys == null) {
            offencePenlitys = new HrDisciplinePenlitys();
        }
        return offencePenlitys;
    }

    public void setOffencePenlitys(HrDisciplinePenlitys offencePenlitys) {
        this.offencePenlitys = offencePenlitys;
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

    public ArrayList<HrDisciplineOffenceTypes> getOffenceNameList() {
        if (offenceNameList == null) {
            offenceNameList = new ArrayList<>();
        }
        return offenceNameList;
    }

    public void setOffenceNameList(ArrayList<HrDisciplineOffenceTypes> offenceNameList) {
        this.offenceNameList = offenceNameList;
    }

    public List<HrDisciplineOffenceTypes> getNameList() {
        if (nameList == null) {
            nameList = new ArrayList<>();
        }
        return nameList;
    }

    public void setNameList(List<HrDisciplineOffenceTypes> nameList) {
        this.nameList = nameList;
    }

    public List<HrDisciplineOffenceTypes> getOffenceTypeList() {
        if (offenceTypeList == null) {
            offenceTypeList = new ArrayList<>();
            offenceTypeList = offenceTypesBeanLocal.findColumnNames();
        }
        return offenceTypeList;
    }

    public void setOffenceTypeList(List<HrDisciplineOffenceTypes> offenceTypeList) {
        this.offenceTypeList = offenceTypeList;
    }

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    public List<ColumnNameResolver> getColumnNameResolverList() {
        ColumnNameResolverList = offenceTypesBeanLocal.findColumns();
        if (ColumnNameResolverList == null) {
            ColumnNameResolverList = new ArrayList<>();
        }
        return ColumnNameResolverList;
    }

    public void setColumnNameResolverList(List<ColumnNameResolver> ColumnNameResolverList) {
        this.ColumnNameResolverList = ColumnNameResolverList;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Search">
    public void populate(SelectEvent events) {
        offenceTypes = null;
        offenceTypes = (HrDisciplineOffenceTypes) events.getObject();
        renderPnlManPage = false;
        SaveOrUpdateButton = "Update";
        updateStatus = 1;
        renderPnlCreateAdditional = true;
        btnNewRender = true;
        createOrSearchBundle = "Search";
        icone = "ui-icon-search";
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
        clearOffence();
        renderPnlCreateAdditional = true;
        renderPnlManPage = false;
        btnNewRender = false;
    }

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            columnNameResolver.setCol_Name_FromTable(event.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(event.getNewValue().toString().toLowerCase() + ":"));
        }
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        return col;
    }

    public ArrayList<HrDisciplineOffenceTypes> searchByOffenceName(String hrOffenceTypeCodeOrName) {
        offenceTypes.setColumnValue(hrOffenceTypeCodeOrName);
        offenceNameList = offenceTypesBeanLocal.searchByCol_NameAndCol_Value(columnNameResolver, offenceTypes.getColumnValue());
        return offenceNameList;
    }

    public void getByOffenceName(SelectEvent event) {
        try {
            if (event.getObject() != null) {
                offenceTypes = (HrDisciplineOffenceTypes) event.getObject();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Main">
    public void saveOffence() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveOffence", dataset)) {
                try {
                    boolean isOffenceNameDuplicated = offenceTypesBeanLocal.checkDuplicationByName(offenceTypes);
                    if (isOffenceNameDuplicated == true && updateStatus == 0) {
                        JsfUtil.addFatalMessage("Duplicate Entry!!!");
                    } else {
                        offenceTypesBeanLocal.saveOrUpdate(offenceTypes);
                        if (updateStatus == 1) {
                            JsfUtil.addSuccessMessage("Updated Successfully");
                        } else {
                            JsfUtil.addSuccessMessage("Submitted Successfully");
                        }
                        clearOffence();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JsfUtil.addFatalMessage("Error occure while save update");
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveOffence");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void clearOffence() {
        offenceTypes = new HrDisciplineOffenceTypes();
        offencePenlitysModels = null;
        SaveOrUpdateButton = "Save";
        updateStatus = 0;
    }
    //</editor-fold>

}
