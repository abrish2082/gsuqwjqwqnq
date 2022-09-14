/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectOne;
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
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.ifrs.businessLogic.DepreciationSetupBeanLocal;
import et.gov.eep.mms.businessLogic.FixedAssetAttributeRegistrationBeanLocal;
import et.gov.eep.ifrs.businessLogic.FixedAssetOptionValuesLocal;
import et.gov.eep.mms.businessLogic.FixedAssetRegistrationBeanLocal;
import et.gov.eep.ifrs.entity.IfrsDepreciationSetup;
import et.gov.eep.ifrs.entity.IfrsFixedAssetAtValue;
import et.gov.eep.ifrs.entity.IfrsOptionValues;
import et.gov.eep.mms.entity.IfrsFixedAsset;
import et.gov.eep.mms.entity.IfrsFixedAssetAttribute;

/**
 *
 * @author Minab
 */
@Named(value = "fixedAssetRegistrationController")
@ViewScoped
public class FixedAssetRegistrationController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entities">
    @Inject
    IfrsFixedAsset fixedasset;
    @Inject
    IfrsDepreciationSetup ifrsDepreciationSetup;
    @Inject
    IfrsFixedAssetAtValue attributeValue;
    @Inject
    IfrsFixedAssetAttribute ifrsFixedAssetAttribute;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @Inject
    FmsSubsidiaryLedger fmsSubsidiaryLedger;
    @Inject
    SessionBean SessionBean;
    //</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="EJB">
    @EJB
    private subsidiaryBeanLocal subsidiaryBeanLocal;
    @EJB
    private FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    FixedAssetRegistrationBeanLocal registrationBeanLocal;
    @EJB
    FixedAssetAttributeRegistrationBeanLocal fixedAssetAttributeRegistrationBeanLocal;
    @EJB
    FmsCostCenterBeanLocal fmsCostCenterBeanLocal;
    @EJB
    FixedAssetOptionValuesLocal assetOptionValuesLocal;
    @EJB
    DepreciationSetupBeanLocal depreciationSetupBeanLocal;
    //</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="Fields">
    private List<IfrsFixedAssetAttribute> attributeList = new ArrayList<>();
    List<IfrsDepreciationSetup> ifrsDepreciationList = new ArrayList<>();
    private static List<IfrsDepreciationSetup> araListe;
    String faGName;
    private TreeNode selectedNode;
    private String activeIndex;
    private TreeNode root;
    boolean renderImpaired = false;
    List<IfrsOptionValues> optionValueList;
    private DataModel<IfrsFixedAsset> ifrsFixedAssetDataModel;
    List<FmsSubsidiaryLedger> subsideryCodeList;
    int update = 0;
    private String saveorUpdateBundle = "Save";
    private String icone = "ui-icon-plus";
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateFixedAsset = false;
    private boolean renderPnlManPage = true;
    int updateStatus = 0;
    private boolean disablefixedasset = true;
    private IfrsFixedAssetAttribute faselect;
    private boolean renderLease = false;
    List<FmsGeneralLedger> getGlList;
    List<IfrsDepreciationSetup> IfrsDepreciationSetupList = new ArrayList<>();
    Set<String> checkHashSetAttribute_id = new HashSet<>();
    List<IfrsFixedAsset> fixedAssetlist;
    boolean test = false;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="PostConstruct">
    @PostConstruct
    public void init() {
        System.out.println("===== in init==");
        root = new DefaultTreeNode("Root", null);
    }

    public void recursive(List<IfrsDepreciationSetup> liste, int id, TreeNode node) {
        araListe = new ArrayList<>();
        for (IfrsDepreciationSetup k : ifrsDepreciationList) {
            if (k.getGroupLevel() == id) {
                TreeNode childNode = new DefaultTreeNode(k.getId() + "--" + k.getDepreciationType(), node);
                araListe.add(k);
                recursive(araListe, k.getId().intValue(), childNode);
            }
        }

    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getter and setter">

    public boolean isRenderLease() {
        return renderLease;
    }

    public void setRenderLease(boolean renderLease) {
        this.renderLease = renderLease;
    }

    public IfrsFixedAsset getFixedasset() {
        if (fixedasset == null) {
            fixedasset = new IfrsFixedAsset();
        }
        return fixedasset;
    }

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }
 public List<IfrsDepreciationSetup> getListofGroups() {

        List<IfrsDepreciationSetup> list = null;
        list = depreciationSetupBeanLocal.depreciationList();
        System.err.println("list size=" + list.size());
        return list;
    }

    public List<IfrsDepreciationSetup> getDepreciationSetupsList() {
        System.out.println("=========in the list===");
        IfrsDepreciationSetupList = depreciationSetupBeanLocal.findAllFixedAssetGroup();
        return ifrsDepreciationList;

    }
    public DataModel<IfrsFixedAsset> getIfrsFixedAssetDataModel() {
        if (ifrsFixedAssetDataModel == null) {
            ifrsFixedAssetDataModel = new ArrayDataModel<>();
        }
        return ifrsFixedAssetDataModel;
    }

    public void setIfrsFixedAssetDataModel(DataModel<IfrsFixedAsset> ifrsFixedAssetDataModel) {
        this.ifrsFixedAssetDataModel = ifrsFixedAssetDataModel;
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

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public boolean isRenderImpaired() {
        return renderImpaired;
    }

    public void setRenderImpaired(boolean renderImpaired) {
        this.renderImpaired = renderImpaired;
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

    public boolean isRenderPnlCreateFixedAsset() {
        return renderPnlCreateFixedAsset;
    }

    public void setRenderPnlCreateFixedAsset(boolean renderPnlCreateFixedAsset) {
        this.renderPnlCreateFixedAsset = renderPnlCreateFixedAsset;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public boolean isDisablefixedasset() {
        return disablefixedasset;
    }

    public void setDisablefixedasset(boolean disablefixedasset) {
        this.disablefixedasset = disablefixedasset;
    }

    public IfrsFixedAssetAttribute getFaselect() {
        return faselect;
    }

    public void setFaselect(IfrsFixedAssetAttribute faselect) {
        this.faselect = faselect;
    }

    public List<IfrsDepreciationSetup> getIfrsDepreciationList() {
        if (ifrsDepreciationList == null) {
            ifrsDepreciationList = new ArrayList<>();
        }
        return ifrsDepreciationList;
    }

    public void setIfrsDepreciationList(List<IfrsDepreciationSetup> ifrsDepreciationList) {
        this.ifrsDepreciationList = ifrsDepreciationList;
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

    public String getFaGName() {
        return faGName;
    }

    public void setFaGName(String faGName) {
        this.faGName = faGName;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public IfrsFixedAssetAttribute getIfrsFixedAssetAttribute() {
        if (ifrsFixedAssetAttribute == null) {
            ifrsFixedAssetAttribute = new IfrsFixedAssetAttribute();
        }
        return ifrsFixedAssetAttribute;
    }

    public void setIfrsFixedAssetAttribute(IfrsFixedAssetAttribute ifrsFixedAssetAttribute) {
        this.ifrsFixedAssetAttribute = ifrsFixedAssetAttribute;
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

    public List<FmsGeneralLedger> getGlList() {
        List<FmsGeneralLedger> glList = null;
        glList = fmsGeneralLedgerBeanLocal.getGeneralLedgerCodeList();
        return glList;
    }
    String glCode[];

    public void setGetGlList(List<FmsGeneralLedger> getGlList) {
        this.getGlList = getGlList;
    }

    public void setFixedasset(IfrsFixedAsset fixedasset) {
        this.fixedasset = fixedasset;
    }

    public IfrsFixedAssetAtValue getAttributeValue() {
        if (attributeValue == null) {
            attributeValue = new IfrsFixedAssetAtValue();
        }
        return attributeValue;
    }

    public void setAttributeValue(IfrsFixedAssetAtValue attributeValue) {
        this.attributeValue = attributeValue;
    }

    public List<IfrsFixedAssetAttribute> getAttributeList() {
        if (attributeList == null) {
            attributeList = new ArrayList<>();
        }
        return attributeList;
    }

    public void setAttributeList(List<IfrsFixedAssetAttribute> attributeList) {
        this.attributeList = attributeList;
    }
    public SelectItem[] getListOfCostCenter() {
        return JsfUtil.getSelectItems(fmsCostCenterBeanLocal.findAll(), true);
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="handler,save,sreach and clear">
/*This method is used to  Handler when selecting the GLCode 
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

    public SelectItem[] handleSelectGlCode1(ValueChangeEvent event) {
        glCode = event.getNewValue().toString().split("--");
        int id = Integer.parseInt(glCode[0]);
        fmsGeneralLedger = null;
        fmsGeneralLedger = fmsGeneralLedgerBeanLocal.findByMasterId(id);
        if (glCode != null) {
            setSubsideryCodeList(subsidiaryBeanLocal.findSubsideryCodeByGlCode(fmsGeneralLedger));
            return Utility.JsfUtil.getSelectItems(getSubsideryCodeList(), true);
        } else {
            return Utility.JsfUtil.getSelectItems(null, true);
        }
    }

    /*This method is used to  clear the component
     */

    public void clearComponent() {
        fixedasset = null;
        ifrsFixedAssetAttribute = null;
        ifrsDepreciationSetup = null;
        fmsSubsidiaryLedger = null;
        fmsGeneralLedger = null;
        getGlList = null;
        subsideryCodeList = null;
    }

   
/*This method is used to  Check the impaired change
     */
    public void changeCheckImpaired(ValueChangeEvent e) {
        System.out.println("======inside===");
        if (null != e.getNewValue()) {
            System.out.println("=====inside if ====");
            if (e.getNewValue().toString().equalsIgnoreCase("1")) {
                System.out.println("inside if " + renderImpaired);
            } else {
                renderImpaired = false;
            }

        }
    }
/*This List is used to  check box for optionValue List
     */
    public List<IfrsOptionValues> checkboxForOptionValueList(List<IfrsFixedAssetAttribute> fixedassetattribute) {
        optionValueList = new ArrayList<>();
        if (fixedassetattribute != null) {
            if (fixedassetattribute.get(0).getIfrsOptionValuesList().size() > 0) {
                Integer optionValueId = fixedassetattribute.get(0).getIfrsOptionValuesList().get(0).getFaaId().getId();
                optionValueList = assetOptionValuesLocal.getListOfOptionValueList(optionValueId);
                System.out.println("========++++++========" + optionValueList);
            }
        }
        return optionValueList;
    }
/*This List is used to  optionalValueList 
     */
    public List<IfrsOptionValues> optionValueList(List<IfrsFixedAssetAttribute> fixedassetattribute) {
        System.out.println("test   select  " + fixedassetattribute.size());
        optionValueList = new ArrayList<>();
        if (fixedassetattribute != null) {
            if (fixedassetattribute.get(0).getIfrsOptionValuesList().size() > 0) {
                Integer optionValueId = fixedassetattribute.get(0).getIfrsOptionValuesList().get(0).getFaaId().getId();
                System.out.println("test  select Integer  " + optionValueId);
                optionValueList = assetOptionValuesLocal.getListOfOptionValueList(optionValueId);
            }
        }
        return optionValueList;
    }
/*This List is used to  radioForOptionValueList
     */
    public List<IfrsOptionValues> radioForOptionValueList(List<IfrsFixedAssetAttribute> fixedassetattribute) {
        System.out.println("test   redio  " + fixedassetattribute.size());
        optionValueList = new ArrayList<>();
        if (fixedassetattribute != null) {
            if (fixedassetattribute.get(0).getIfrsOptionValuesList().size() > 0) {
                Integer optionValueId = fixedassetattribute.get(0).getIfrsOptionValuesList().get(0).getFaaId().getId();
                System.out.println("test  redio Integer  " + optionValueId);
                optionValueList = assetOptionValuesLocal.getListOfOptionValueList(optionValueId);

            }
        }
        return optionValueList;
    }
/*This method is used to  Save
     */
    public void saveFixedAssetIfrs() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveFixedAssetIfrs", dataset)) {
                if (update == 0) {
                    fixedasset.setFagId(ifrsDepreciationSetup);
                    System.out.println("=======depreciation=== " + ifrsDepreciationSetup);
                    registrationBeanLocal.saveOrUpdateFixedAssetInformation(fixedasset);
                    System.out.println("=======depreciation=== " + ifrsDepreciationSetup);
                    Utility.JsfUtil.addSuccessMessage("Fixed Asset Information Successfully Saved !!");
                    clearComponent();

                } else {

                    System.out.println("===========insideupdate====");
                    fixedasset.setFagId(ifrsDepreciationSetup);
                    System.out.println("=======depreciation=== " + ifrsDepreciationSetup.getId());
                    registrationBeanLocal.saveOrUpdateFixedAssetInformation(fixedasset);
                    System.out.println("=======depreciation=== " + ifrsDepreciationSetup);
                    Utility.JsfUtil.addSuccessMessage("Fixed Asset Information Successfully Update !!");
                    clearComponent();
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
        } catch (Exception e) {
            e.printStackTrace();
            Utility.JsfUtil.addErrorMessage("Something is wrong!!");
        }
        clearComponent();
    }
/*This method is used to  get list of Attributes By category
     */
    public void getListOfAttributesByCategory(ValueChangeEvent e) {
        if (null != e.getNewValue() && !e.getNewValue().equals("")) {

            fmsSubsidiaryLedger = subsidiaryBeanLocal.getSubsidiaryCode(Integer.parseInt(e.getNewValue().toString()));
            attributeList = fixedAssetAttributeRegistrationBeanLocal.getListOfAttributesBySlCode(fmsSubsidiaryLedger);
            if (fmsSubsidiaryLedger.getFixedassetList() != null) {
                fixedasset = fmsSubsidiaryLedger.getFixedassetList();
                ifrsDepreciationSetup = fixedasset.getFagId();
                ArrayList<String> list = new ArrayList<>();
                for (Iterator<IfrsFixedAssetAttribute> iterator = attributeList.iterator(); iterator.hasNext();) {
                    IfrsFixedAssetAttribute next = iterator.next();
                    if (next.getFieldType().equalsIgnoreCase("CHECKMANY") == true) {
                        StringTokenizer st = new StringTokenizer(Arrays.toString(next.getIfrsFixedAssetAtValueList().getOptionvalueId().split(",")).replaceAll("[\\[\\]]", ""), ",");
                        int nto = st.countTokens();
                        for (int j = 0; j < nto; j++) {
                            String token = st.nextToken();
                            list.add(token.trim());
                        }
                    }
                    next.setOptionValueListForCheckbox(list);
                }
            } else {
                fixedasset.setSubsidiaryId(fmsSubsidiaryLedger);
            }

        }
        generateFixAssNo();

    }
/*This method is used to  Adding InputTextValueToList
     */
    public void addInputTextValueToList(ValueChangeEvent changeEvent) {
        System.out.println("============insideAdd input taxt value=======");
        if (null != changeEvent.getNewValue()) {
            Integer fixedAttributeId = (Integer) ((UIInput) changeEvent.getComponent()).getAttributes().get("myattribute");
            if (checkHashSetAttribute_id.contains(fixedAttributeId.toString()) == false) {
                checkHashSetAttribute_id.add(fixedAttributeId.toString());
                attributeValue = new IfrsFixedAssetAtValue();
                attributeValue.setValueName(changeEvent.getNewValue().toString());
                attributeValue.setAttributeId(fixedAssetAttributeRegistrationBeanLocal.getIfrsFixedAssetAttributeObject(fixedAttributeId));
                attributeValue.setFaId(fixedasset);
                System.out.println("=======attributevalue=====" + attributeValue);
                fixedasset.getIfrsFixedAssetAtValueList().add(attributeValue);
            } else {
                for (int i = 0; i < fixedasset.getIfrsFixedAssetAtValueList().size(); i++) {
                    IfrsFixedAssetAtValue get = fixedasset.getIfrsFixedAssetAtValueList().get(i);
                    if (Objects.equals(get.getAttributeId().getId(), fixedAttributeId) != false) {
                        fixedasset.getIfrsFixedAssetAtValueList().get(i).setValueName(changeEvent.getNewValue().toString());
                    }

                }
            }

        }
    }
/*This method is used to  add Select Checkbox Value To List
     */
    public void addSelectCheckboxValueToList(ValueChangeEvent changeEvent) {
        System.out.println("===========addSelectioncheckbox+++++++++++");
        if (null != changeEvent.getNewValue() && !changeEvent.getNewValue().equals("")) {
            Integer fixedAttributeId
                    = (Integer) ((UIInput) changeEvent.getComponent()).getAttributes().get("myCheckboxAttribute");
            String st = (changeEvent.getNewValue().toString().replaceAll("[\\[\\]]", ""));
            if (checkHashSetAttribute_id.contains(fixedAttributeId.toString()) == false) {
                checkHashSetAttribute_id.add(fixedAttributeId.toString());
                attributeValue = new IfrsFixedAssetAtValue();
                attributeValue.setValueName(null);
                attributeValue.setAttributeId(fixedAssetAttributeRegistrationBeanLocal.getIfrsFixedAssetAttributeObject(fixedAttributeId));
                attributeValue.setFaId(fixedasset);
                attributeValue.setOptionvalueId(st);
                System.out.println("--------------" + attributeValue);
                fixedasset.getIfrsFixedAssetAtValueList().add(attributeValue);

            } else {
                for (int i = 0; i < fixedasset.getIfrsFixedAssetAtValueList().size(); i++) {
                    IfrsFixedAssetAtValue get = fixedasset.getIfrsFixedAssetAtValueList().get(i);
                    if (Objects.equals(get.getAttributeId().getId(), fixedAttributeId) != false) {
                        fixedasset.getIfrsFixedAssetAtValueList().get(i).setOptionvalueId(changeEvent.getNewValue().toString());
                    }

                }
            }
        }
    }
/*This method is used to  Change optional Value
     */
    public void changeOptionValueObject(ValueChangeEvent changeEvent) {
        if (null != changeEvent.getNewValue() && !changeEvent.getNewValue().equals("")) {
            Integer fixedAttributeId
                    = (Integer) ((UISelectOne) changeEvent.getComponent())
                    .getAttributes().get("mySelectattribute");
            if (checkHashSetAttribute_id.contains(fixedAttributeId.toString()) == false) {
                checkHashSetAttribute_id.add(fixedAttributeId.toString());
                attributeValue = new IfrsFixedAssetAtValue();
                attributeValue.setValueName(null);
                attributeValue.setAttributeId(fixedAssetAttributeRegistrationBeanLocal.getIfrsFixedAssetAttributeObject(fixedAttributeId));
                attributeValue.setFaId(fixedasset);
                attributeValue.setOptionvalueId(changeEvent.getNewValue().toString());
                fixedasset.getIfrsFixedAssetAtValueList().add(attributeValue);
            } else {
                for (int i = 0; i < fixedasset.getIfrsFixedAssetAtValueList().size(); i++) {
                    IfrsFixedAssetAtValue get = fixedasset.getIfrsFixedAssetAtValueList().get(i);
                    if (Objects.equals(get.getAttributeId().getId(), fixedAttributeId) != false) {
                        fixedasset.getIfrsFixedAssetAtValueList().get(i).setOptionvalueId(changeEvent.getNewValue().toString());
                    }

                }
            }
        }
    }
/*This method is used to  add Select Radio Input Text Value ToList
     */
    public void addSelectRadioInputTextValueToList(ValueChangeEvent changeEvent) {
        System.out.println("=============addSelectRadioInputTextValueToList===========");
        if (null != changeEvent.getNewValue() && !changeEvent.getNewValue().equals("")) {
            Integer fixedAttributeId
                    = (Integer) ((UIInput) changeEvent.getComponent()).getAttributes().get("mySelectRadioAttribute");
            if (checkHashSetAttribute_id.contains(fixedAttributeId.toString()) == false) {
                checkHashSetAttribute_id.add(fixedAttributeId.toString());
                checkHashSetAttribute_id.add(fixedAttributeId.toString());
                attributeValue = new IfrsFixedAssetAtValue();
                attributeValue.setValueName(null);
                attributeValue.setAttributeId(fixedAssetAttributeRegistrationBeanLocal.getIfrsFixedAssetAttributeObject(fixedAttributeId));
                attributeValue.setFaId(fixedasset);
                attributeValue.setOptionvalueId(changeEvent.getNewValue().toString());
                fixedasset.getIfrsFixedAssetAtValueList().add(attributeValue);

            } else {
                for (int i = 0; i < fixedasset.getIfrsFixedAssetAtValueList().size(); i++) {
                    IfrsFixedAssetAtValue get = fixedasset.getIfrsFixedAssetAtValueList().get(i);
                    if (Objects.equals(get.getAttributeId().getId(), fixedAttributeId) != false) {
                        fixedasset.getIfrsFixedAssetAtValueList().get(i).setOptionvalueId(changeEvent.getNewValue().toString());
                    }

                }
            }

        }
    }
/*This method is used to  add Input Text Area Value To List
     */
    public void addInputTextAreaValueToList(ValueChangeEvent changeEvent) {
        System.out.println("=============addInputTextAreaValueToList========");
        if (null != changeEvent.getNewValue()) {
            Integer fixedAttributeId
                    = (Integer) ((UIInput) changeEvent.getComponent()).getAttributes().get("mytxtattribute");
            if (checkHashSetAttribute_id.contains(fixedAttributeId.toString()) == false) {
                checkHashSetAttribute_id.add(fixedAttributeId.toString());
                attributeValue = new IfrsFixedAssetAtValue();
                attributeValue.setValueName(changeEvent.getNewValue().toString());
                attributeValue.setAttributeId(fixedAssetAttributeRegistrationBeanLocal.getIfrsFixedAssetAttributeObject(fixedAttributeId));
                attributeValue.setFaId(fixedasset);
                attributeValue.setOptionvalueId(null);
                fixedasset.getIfrsFixedAssetAtValueList().add(attributeValue);

            } else {
                for (int i = 0; i < fixedasset.getIfrsFixedAssetAtValueList().size(); i++) {
                    IfrsFixedAssetAtValue get = fixedasset.getIfrsFixedAssetAtValueList().get(i);
                    if (Objects.equals(get.getAttributeId().getId(), fixedAttributeId) != false) {
                        fixedasset.getIfrsFixedAssetAtValueList().get(i).setValueName(changeEvent.getNewValue().toString());
                    }

                }
            }
        }
    }
/*This method is used to  Genetate FixedAsset NO 
     */
    public void generateFixAssNo() {

        IfrsFixedAsset ifRsFixedObj = new IfrsFixedAsset();
        ifRsFixedObj = registrationBeanLocal.getLastFixAId();
        DateFormat f = new SimpleDateFormat("yyyy");
        Date now = new Date();
        if (ifRsFixedObj != null) {

            int fixNo = 1, fixId = ifRsFixedObj.getId().intValue();
            String newFixNo = "FIXAssNo-" + (fixId + fixNo) + "/" + f.format(now);

            fixedasset.setFixedAssetNo(newFixNo);
            System.out.println("no" + newFixNo);
        } else {
            String newFixNo = "FIXAssNo" + "/" + f.format(now);
            fixedasset.setFixedAssetNo(newFixNo);
            System.out.println("no2" + newFixNo);
        }

    }
/*This method is used to  Handle Depreciation Type
     */
    public void handleSelectDepType(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            String asetType = event.getNewValue().toString();
            System.out.println("asetType" + asetType);
            ifrsDepreciationSetup.setDepreciationType(asetType);

        }
        generateFixAssNo();
    }
/*This method is used to  Create New FixedAsset Info
     */
    public void createNewFixaInfo() {
        clearComponent();
        saveorUpdateBundle = "Save";

        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateFixedAsset = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-search");

        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateFixedAsset = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-plus");
        }

    }
/*This method is used to  Searching 
     */
    public void searchFixedAsset() {
        System.out.println("-----------Inside Search----------");
        String str = fixedasset.getFixedAssetNo();
        fixedasset.setFixedAssetNo(str);
        if (fixedasset.getFixedAssetNo() != null) {

            System.out.println("-------- Inside If ------- ");
            fixedAssetlist = registrationBeanLocal.searchFixedAssetByNo(fixedasset);
            System.out.println("-------LostItemInfoList -----" + attributeList.size());
            recerateFixedAssetSearchModel();

        } else {
            System.out.println("--------- Inside else ---------");
        }

    }

    private void recerateFixedAssetSearchModel() {
        ifrsFixedAssetDataModel = null;
        ifrsFixedAssetDataModel = new ListDataModel(new ArrayList<>(fixedAssetlist));
    }
/*This method is used to  Row Select
     */
    public void rowSelect(SelectEvent event) {
        fixedasset = (IfrsFixedAsset) event.getObject();
        fixedasset.setId(fixedasset.getId());
        fixedasset = registrationBeanLocal.getSelectedRequest(fixedasset.getId().intValue());
        setDisablefixedasset(false);
        ifrsDepreciationSetup.setId(fixedasset.getSubsidiaryId().getSubsidiaryId());

        renderPnlManPage = false;
        renderPnlCreateFixedAsset = true;
        saveorUpdateBundle = "Update";
        setIcone("ui-icon-search");
        createOrSearchBundle = "Search";
        updateStatus = 1;

    }

}
//</editor-fold>
