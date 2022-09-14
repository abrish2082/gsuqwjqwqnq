/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.controller;

import Utility.JsfUtil;
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
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.ifrs.businessLogic.DataTypeBeanLocal;
import et.gov.eep.ifrs.businessLogic.DepreciationSetupBeanLocal;
import et.gov.eep.ifrs.entity.IfrsDepreciationSetup;
import et.gov.eep.ifrs.entity.IfrsOptionValues;
import et.gov.eep.mms.businessLogic.FixedAssetAttributeRegistrationBeanLocal;
import et.gov.eep.mms.entity.IfrsFixedAssetAttribute;

/**
 *
 * @author minab
 */
@Named(value = "fixedAssetAttributeController")
@ViewScoped
public class FixedAssetAttributeController implements Serializable {
//<editor-fold defaultstate="collapsed" desc="Entities">

    @Inject
    IfrsFixedAssetAttribute fixedassetattribute;
    @Inject
    IfrsDepreciationSetup ifrsDepreciationSetup;
    @Inject
    IfrsOptionValues optionvalueObject;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @Inject
    FmsSubsidiaryLedger fmsSubsidiaryLedger;
    @Inject
    SessionBean SessionBean;
    //</editor-fold> 

//<editor-fold defaultstate="collapsed" desc="EJb">
    @EJB
    FixedAssetAttributeRegistrationBeanLocal fixedAssetAttributeRegistrationBeanLocal;
    @EJB
    private subsidiaryBeanLocal subsidiaryBeanLocal;
    @EJB
    private FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    private FixedAssetAttributeRegistrationBeanLocal assetAttributeRegistrationBeanLocal;
    @EJB
    DepreciationSetupBeanLocal depreciationSetupBeanLocal;
    @EJB
    private DataTypeBeanLocal dataTypeBeanLocal;
    //</editor-fold> 

//<editor-fold defaultstate="collapsed" desc="Fields">
    private boolean renderdComType;
    private boolean require = false;
    private boolean valid = false;
    private boolean notRequired;
    private String saveorUpdateBundle = "Save";
    private String icone = "ui-icon-search";
    private String createOrSearchBundle = "Search";
    private boolean renderPnlCreateAttribute = true;
    private boolean renderPnlManPage = false;
    int updateStatus = 0;
    private boolean disableattribute = true;
    private IfrsFixedAssetAttribute aselect;
    private DataModel<IfrsFixedAssetAttribute> ifrsFixedAssetAttributSearchInfoDataModel;
    private DataModel<IfrsOptionValues> OptionvalueModel;
    private List<IfrsFixedAssetAttribute> attributeList = new ArrayList<>();
    private boolean renderImpaired = false;
    String glCode[];
    List<FmsGeneralLedger> getGlList;
    List<FmsSubsidiaryLedger> subsideryCodeList;
    List<FmsGeneralLedger> glCodeList;
    List<IfrsFixedAssetAttribute> fixedassetAttributList;
     //</editor-fold> 

//<editor-fold defaultstate="collapsed" desc="Postconstraction">
    @PostConstruct
    public void init() {

    }
    //</editor-fold> 

//<editor-fold defaultstate="collapsed" desc="gettre and setter">
    public List<IfrsFixedAssetAttribute> getAttributeList() {
        if (attributeList == null) {
            attributeList = new ArrayList<>();
        }
        return attributeList;
    }

    public void setAttributeList(List<IfrsFixedAssetAttribute> attributeList) {
        this.attributeList = attributeList;
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

    public FmsSubsidiaryLedger getFmsSubsidiaryLedger() {
        if (fmsSubsidiaryLedger == null) {
            fmsSubsidiaryLedger = new FmsSubsidiaryLedger();
        }
        return fmsSubsidiaryLedger;
    }

    public void setFmsSubsidiaryLedger(FmsSubsidiaryLedger fmsSubsidiaryLedger) {
        this.fmsSubsidiaryLedger = fmsSubsidiaryLedger;
    }

    public IfrsOptionValues getOptionvalueObject() {
        if (optionvalueObject == null) {
            optionvalueObject = new IfrsOptionValues();
        }
        return optionvalueObject;
    }

    public void setOptionvalueObject(IfrsOptionValues optionvalueObject) {
        this.optionvalueObject = optionvalueObject;
    }

    public DataModel<IfrsOptionValues> getOptionvalueModel() {
        if (OptionvalueModel == null) {
            OptionvalueModel = new ArrayDataModel<>();
        }
        return OptionvalueModel;
    }

    public void setOptionvalueModel(DataModel<IfrsOptionValues> OptionvalueModel) {
        this.OptionvalueModel = OptionvalueModel;
    }

    public boolean isRenderdComType() {
        return renderdComType;
    }

    public void setRenderdComType(boolean renderdComType) {
        this.renderdComType = renderdComType;
    }

    public IfrsDepreciationSetup getIfrsDepreciationSetup() {
        if (ifrsDepreciationSetup == null) {
            ifrsDepreciationSetup = new IfrsDepreciationSetup();
        }
        return ifrsDepreciationSetup;
    }

    public void setIfrsDepreciationSetup(IfrsDepreciationSetup ifrsDepreciationSetup) {
        this.ifrsDepreciationSetup = ifrsDepreciationSetup;
    }

    public boolean isDisableattribute() {
        return disableattribute;
    }

    public void setDisableattribute(boolean disableattribute) {
        this.disableattribute = disableattribute;
    }

    public DataModel<IfrsFixedAssetAttribute> getIfrsFixedAssetAttributSearchInfoDataModel() {
        if (ifrsFixedAssetAttributSearchInfoDataModel == null) {
            ifrsFixedAssetAttributSearchInfoDataModel = new ArrayDataModel<>();
        }
        return ifrsFixedAssetAttributSearchInfoDataModel;
    }

    public void setIfrsFixedAssetAttributSearchInfoDataModel(DataModel<IfrsFixedAssetAttribute> ifrsFixedAssetAttributSearchInfoDataModel) {
        this.ifrsFixedAssetAttributSearchInfoDataModel = ifrsFixedAssetAttributSearchInfoDataModel;
    }

    public IfrsFixedAssetAttribute getAselect() {
        return aselect;
    }

    public void setAselect(IfrsFixedAssetAttribute aselect) {
        this.aselect = aselect;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isRenderPnlCreateAttribute() {
        return renderPnlCreateAttribute;
    }

    public void setRenderPnlCreateAttribute(boolean renderPnlCreateAttribute) {
        this.renderPnlCreateAttribute = renderPnlCreateAttribute;
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

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public boolean isRequire() {
        return require;
    }

    public void setRequire(boolean require) {
        this.require = require;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isNotRequired() {
        return notRequired;
    }

    public void setNotRequired(boolean notRequired) {
        this.notRequired = notRequired;
    }

    public SelectItem[] selectAllFixedAssetGroup() {
        return JsfUtil.getSelectItems(depreciationSetupBeanLocal.findAllFixedAssetGroup(), true);
    }

    public SelectItem[] selectAllDataType() {
        return JsfUtil.getSelectItems(dataTypeBeanLocal.selectAllDataType(), true);
    }

    public boolean isRenderImpaired() {
        return renderImpaired;
    }

    public void setRenderImpaired(boolean renderImpaired) {
        this.renderImpaired = renderImpaired;
    }
     public IfrsFixedAssetAttribute getFixedassetattribute() {
        if (fixedassetattribute == null) {
            fixedassetattribute = new IfrsFixedAssetAttribute();
        }
        return fixedassetattribute;
    }
 

    public void setFixedassetattribute(IfrsFixedAssetAttribute fixedassetattribute) {
        this.fixedassetattribute = fixedassetattribute;
    }

    public List<IfrsDepreciationSetup> getListofGroups() {
        List<IfrsDepreciationSetup> list = null;
        list = depreciationSetupBeanLocal.depreciationList();
        System.err.println("list size=" + list.size());
        return list;
    }

    public List<FmsGeneralLedger> getGlCodeList() {
        if (glCodeList == null) {
            glCodeList = new ArrayList<>();
        }
        return glCodeList;
    }

    public void setGlCodeList(List<FmsGeneralLedger> glCodeList) {
        this.glCodeList = glCodeList;
    }

    public List<FmsSubsidiaryLedger> getSubsideryCodeList() {
        if (subsideryCodeList == null) {
            subsideryCodeList = new ArrayList<>();
        }
        return subsideryCodeList;
    }

    public void setSubsideryCodeList(List<FmsSubsidiaryLedger> subsideryCodeList) {
        this.subsideryCodeList = subsideryCodeList;
    }

    public void setGetGlList(List<FmsGeneralLedger> getGlList) {
        this.getGlList = getGlList;
    }

    public List<FmsGeneralLedger> getGlList() {
        List<FmsGeneralLedger> glList = null;
        glList = fmsGeneralLedgerBeanLocal.getGeneralLedgerCodeList();
        return glList;
    }
    //</editor-fold> 

//<editor-fold defaultstate="collapsed" desc="save,search,handelar">
  /*This method is used to  Clear the page
     */
    public void clearComponent() {
        fixedassetattribute = null;
        fmsGeneralLedger = null;
        fmsSubsidiaryLedger = null;
        fmsGeneralLedger = null;
        optionvalueObject = null;
        OptionvalueModel = null;
        renderdComType = false;
        ifrsFixedAssetAttributSearchInfoDataModel = null;
        subsideryCodeList = null;
        getGlList = null;
    }
/*This method is used to  Handler  
     */
    public void handleListner(ValueChangeEvent event) {
        System.out.println("event value==" + event.getNewValue());
        String value[] = event.getNewValue().toString().split("-");
        int id = Integer.parseInt(value[0]);
        System.out.println("====== id1 " + id);
        fmsSubsidiaryLedger = null;
        attributeList.clear();
        fmsSubsidiaryLedger = subsidiaryBeanLocal.findById(id);
        System.out.println("=======id ======" + id);
        attributeList = fixedAssetAttributeRegistrationBeanLocal.getListOfAttributesBySlCode(fmsSubsidiaryLedger);
        System.out.println("--attributeList-" + attributeList);
        ifrsFixedAssetAttributSearchInfoDataModel = new ListDataModel<>(new ArrayList(attributeList));
        System.out.println("--attributeList-" + attributeList);
        fixedassetattribute.setSubsidiaryId(fmsSubsidiaryLedger);
    }
/*This method is used to get list of Attirbutes by category 
     */
    public void getListOfAttributesByCategory(ValueChangeEvent e) {
        if (null != e.getNewValue() && !e.getNewValue().equals("")) {
            System.out.println("=============inside getList=============");
            fmsSubsidiaryLedger = subsidiaryBeanLocal.getSubsidiaryCode(Integer.parseInt(e.getNewValue().toString()));
            attributeList = fixedAssetAttributeRegistrationBeanLocal.getListOfAttributesBySlCode(fmsSubsidiaryLedger);
            System.out.println("--attributeList-" + attributeList);
            ifrsFixedAssetAttributSearchInfoDataModel = new ListDataModel<>(new ArrayList(attributeList));
            System.out.println("--attributeList-" + attributeList);
            fixedassetattribute.setSubsidiaryId(fmsSubsidiaryLedger);
        }

    }

    public boolean testAttributeDup() {
        return assetAttributeRegistrationBeanLocal.getAttributeDup(fixedassetattribute);
    }
/*This method is used to checking impaired 
     */
    public void changeCheckimpairedType(ValueChangeEvent e) {
        System.out.println("=====inevent=====");
        if (null != e.getNewValue()) {
            System.out.println("======if==");
            if (e.getNewValue().toString().equalsIgnoreCase("Impiared")) {
                setRenderImpaired(true);
            } else {
                setRenderImpaired(false);
            }

        }
    }

/*This method is used to Save 
     */
    public void saveFixedAssetAttributeInfo() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveFixedAssetAttributeInfo", dataset)) {
                if (updateStatus == 0) {
                    try {
                        System.err.println("inside save " + testAttributeDup());
                        System.err.println("fmsSubsidiaryLedger " + fmsSubsidiaryLedger);

                        fixedassetattribute.setSubsidiaryId(fmsSubsidiaryLedger);
                        assetAttributeRegistrationBeanLocal.saveFixedAssetAttribute(fixedassetattribute);
                        JsfUtil.addSuccessMessage("Fixed Asset Attribute Successfully Saved !!");
                        clearComponent();
                    } catch (Exception e) {
                        e.printStackTrace();
                        JsfUtil.addErrorMessage("Something is wrong!!");
                    }
                } else {
                    try {
                        if (assetAttributeRegistrationBeanLocal.getAttributeDup(fixedassetattribute)) {
                            JsfUtil.addErrorMessage(fixedassetattribute.getFaaname() + ":allready registered.");
                        } else {
                            assetAttributeRegistrationBeanLocal.edit(fixedassetattribute);
                            clearComponent();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        JsfUtil.addErrorMessage("Something is wrong!!");
                    }
                }
                clearComponent();
            } else {
                et.gov.eep.commonApplications.utility.JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
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

    /*This method is used to Selecting GL Handler  
     */
    public SelectItem[] handleSelectGlCode(ValueChangeEvent event) {
        int id = Integer.parseInt(event.getNewValue().toString());
        System.out.println("====== id1 " + id);
        if (event.getNewValue() != null) {
            fmsGeneralLedger = null;
            fmsGeneralLedger = fmsGeneralLedgerBeanLocal.findByMasterId(id);

            System.out.println("====== id2 " + fmsGeneralLedger.getGeneralLedgerId());
            subsideryCodeList = subsidiaryBeanLocal.findSubsideryCodeByGlCode(fmsGeneralLedger);
            System.out.println("====== 1 " + getSubsideryCodeList());
            System.out.println("3===" + subsideryCodeList.size());
            return Utility.JsfUtil.getSelectItems(getSubsideryCodeList(), true);
        } else {
            return Utility.JsfUtil.getSelectItems(null, true);
        }
    }
/*This method is used to Selecting GL Handler  
     */
    public SelectItem[] handleSelectGlCode1(ValueChangeEvent event) {
        glCode = event.getNewValue().toString().split("--");
        int id = Integer.parseInt(glCode[0]);
        fmsGeneralLedger = null;
        fmsGeneralLedger = fmsGeneralLedgerBeanLocal.findByMasterId(id);
        System.out.println("====== id2 " + fmsGeneralLedger.getGeneralLedgerId());
        if (glCode != null) {
            setSubsideryCodeList(subsidiaryBeanLocal.findSubsideryCodeByGlCode(fmsGeneralLedger));
            return JsfUtil.getSelectItems(getSubsideryCodeList(), true);
        } else {
            return JsfUtil.getSelectItems(null, true);
        }
    }
/*This method is used to Creating New Data
     */
    public void createNewAttributt() {
        clearComponent();
        saveorUpdateBundle = "Save";
        if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAttribute = false;
            renderPnlManPage = true;
            createOrSearchBundle = "Search";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-plus");

        } else if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAttribute = true;
            renderPnlManPage = false;
            createOrSearchBundle = "New";
            setCreateOrSearchBundle(createOrSearchBundle);

            setIcone("ui-icon-search");
        }

    }

    public void searchAttribute() {
    }

    private void recerateAttributeSearchModel() {
        ifrsFixedAssetAttributSearchInfoDataModel = null;
        ifrsFixedAssetAttributSearchInfoDataModel = new ListDataModel(new ArrayList<>(attributeList));
    }
 /*This method is used to filtering   
     */
    public void filterTypeEvent(ValueChangeEvent e) {
        if (null != e.getNewValue() && !e.getNewValue().equals("")) {
            if (e.getNewValue().toString().equalsIgnoreCase("SELECTONE")
                    || e.getNewValue().toString().equalsIgnoreCase("RADIO")
                    || e.getNewValue().toString().equalsIgnoreCase("CHECKMANY")) {
                System.out.println("setFAAId   " + fixedassetattribute.getId());
                renderdComType = true;
            } else {
                renderdComType = false;
            }
            System.out.println("test   " + renderdComType);
        }
    }
/*This method is used to Row editing  
     */
    public void onRowFixedassetAttributeEdit() {
        fixedassetattribute = getIfrsFixedAssetAttributSearchInfoDataModel().getRowData();
        renderPnlCreateAttribute = true;
        renderPnlManPage = false;
        createOrSearchBundle = "Search";
        icone = "ui-icon-search";
        if (fixedassetattribute.getFieldType().equalsIgnoreCase("SELECTONE")
                || fixedassetattribute.getFieldType().equalsIgnoreCase("RADIO")
                || fixedassetattribute.getFieldType().equalsIgnoreCase("CHECKMANY")) {
            renderdComType = true;
            OptionvalueModel = null;
            OptionvalueModel = new ListDataModel(fixedassetattribute.getIfrsOptionValuesList());
        } else {
            renderdComType = false;
        }

    }
/*This method is used to Adding value  
     */
    public void addValueData() {
        fixedassetattribute.addToOptionvalue(optionvalueObject);
        OptionvalueModel = new ListDataModel(fixedassetattribute.getIfrsOptionValuesList());
        optionvalueObject = null;

    }
/*This method is used to onRowEditing
     */
    public void onRowOptionvalueEdit() {
        optionvalueObject = getOptionvalueModel().getRowData();
    }
/*This method is used to Filling Data
     */
    public void fillAttributeDataModel() {
        ifrsFixedAssetAttributSearchInfoDataModel = null;
        ifrsFixedAssetAttributSearchInfoDataModel = new ListDataModel(new ArrayList<>(assetAttributeRegistrationBeanLocal.getListOfAttributesByAssetGroupId(ifrsDepreciationSetup.getId())));

    }
//</editor-fold> 
}
