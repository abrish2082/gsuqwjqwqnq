/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.uniform;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.organization.HrJobTypesBeanLocal;
import et.gov.eep.hrms.businessLogic.uniform.uniformBeanLocal;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.uniform.HrUniform;
import et.gov.eep.hrms.entity.uniform.HrUniformDetails;
import et.gov.eep.mms.businessLogic.MmsCategoryBeanLocal;
import et.gov.eep.mms.businessLogic.MmsItemRegisrtationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsSubcatBeanLocal;
import et.gov.eep.mms.entity.MmsCategory;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsSubCat;
import et.gov.eep.mms.integration.MmsItemRegistrationIntegrationBeanLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author Behailu
 */
@Named(value = "uniformController")
@ViewScoped
public class uniformController implements Serializable {

    @Inject
    HrUniform hrUniform;
    @Inject
    HrUniformDetails hrUniformDetails;
    @Inject
    HrJobTypes hrJobTypes;
    @Inject
    MmsCategory mmsCategory;
    @Inject
    MmsSubCat mmsSubCat;
    @Inject
    MmsItemRegistration mmsItemRegistration;
//</editor-fold>

    @EJB
    uniformBeanLocal uniformBeanLocal;
    @EJB
    HrJobTypesBeanLocal hrJobTypesBeanLocal;
    @EJB
    MmsCategoryBeanLocal mmsCategoryBeanLocal;
    @EJB
    MmsSubcatBeanLocal mmsSubcatBeanLocal;
    @EJB
    MmsItemRegisrtationBeanLocal mmsItemRegisrtationBeanLocal;
    @EJB
    MmsItemRegistrationIntegrationBeanLocal mmsItemRegisrtationIntegrationBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="variable initialization">
    private boolean isUpdated = false;
    private String filtercriteria;
    private Set<String> checkJob = new HashSet<>();
    private Set<String> checkItem = new HashSet<>();

    /**
     * Creates a new instance of Uniform
     */
    public uniformController() {
    }
    //<editor-fold defaultstate="collapsed" desc="post Construct method">

    @PostConstruct
    public void init() {
        setListOfJobTypes(hrJobTypesBeanLocal.readAllJobTypes());
        setListOfCatagories(mmsCategoryBeanLocal.findAll());
        setListOfYears(readYears());
        hrUniform.setSex(1);
    }

    //<editor-fold defaultstate="collapsed" desc="getter & setter">
    public HrUniform getHrUniform() {
        return hrUniform;
    }

    public void setHrUniform(HrUniform hrUniform) {
        this.hrUniform = hrUniform;
    }

    public HrUniformDetails getHrUniformDetails() {
        return hrUniformDetails;
    }

    public void setHrUniformDetails(HrUniformDetails hrUniformDetails) {
        this.hrUniformDetails = hrUniformDetails;
    }

    public HrJobTypes getHrJobTypes() {
        return hrJobTypes;
    }

    public void setHrJobTypes(HrJobTypes hrJobTypes) {
        this.hrJobTypes = hrJobTypes;
    }

    public MmsCategory getMmsCategory() {
        return mmsCategory;
    }

    public void setMmsCategory(MmsCategory mmsCategory) {
        this.mmsCategory = mmsCategory;
    }

    public MmsSubCat getMmsSubCat() {
        return mmsSubCat;
    }

    public void setMmsSubCat(MmsSubCat mmsSubCat) {
        this.mmsSubCat = mmsSubCat;
    }

    public MmsItemRegistration getMmsItemRegistration() {
        return mmsItemRegistration;
    }

    public void setMmsItemRegistration(MmsItemRegistration mmsItemRegistration) {
        this.mmsItemRegistration = mmsItemRegistration;
    }

    public String getFiltercriteria() {
        return filtercriteria;
    }

    public void setFiltercriteria(String filtercriteria) {
        this.filtercriteria = filtercriteria;
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="bindings">
    private List<HrJobTypes> listOfJobTypes;
    private List<SelectItem> listOfYears;
    private List<MmsCategory> listOfCatagories;
    private List<MmsSubCat> listOfSubCatagories;
    private List<MmsItemRegistration> listOfUniformItems;
    private DataModel<HrUniformDetails> uniformDetailsDataModel;
    private List<HrUniform> ListOfUniform;
    //<editor-fold defaultstate="collapsed" desc="getters & setters">

    public List<HrJobTypes> getListOfJobTypes() {
        return listOfJobTypes;
    }

    public void setListOfJobTypes(List<HrJobTypes> listOfJobTypes) {
        this.listOfJobTypes = listOfJobTypes;
    }

    public List<SelectItem> getListOfYears() {
        return listOfYears;
    }

    public void setListOfYears(List<SelectItem> listOfYears) {
        this.listOfYears = listOfYears;
    }

    public List<MmsCategory> getListOfCatagories() {
        return listOfCatagories;
    }

    public void setListOfCatagories(List<MmsCategory> listOfCatagories) {
        this.listOfCatagories = listOfCatagories;
    }

    public List<MmsSubCat> getListOfSubCatagories() {
        if (listOfSubCatagories == null) {
            listOfSubCatagories = new ArrayList<>();
        }
        return listOfSubCatagories;
    }

    public void setListOfSubCatagories(List<MmsSubCat> listOfSubCatagories) {
        this.listOfSubCatagories = listOfSubCatagories;
    }

    public List<MmsItemRegistration> getListOfUniformItems() {
        return listOfUniformItems;
    }

    public void setListOfUniformItems(List<MmsItemRegistration> listOfUniformItems) {
        this.listOfUniformItems = listOfUniformItems;
    }

    public DataModel<HrUniformDetails> getUniformDetailsDataModel() {
        return uniformDetailsDataModel;
    }

    public void setUniformDetailsDataModel(DataModel<HrUniformDetails> uniformDetailsDataModel) {
        this.uniformDetailsDataModel = uniformDetailsDataModel;
    }

    public List<HrUniform> getListOfUniform() {
        return ListOfUniform;
    }

    public void setListOfUniform(List<HrUniform> ListOfUniform) {
        this.ListOfUniform = ListOfUniform;
    }

    //</editor-fold>
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="search">
    private String saveOrUpdate = "Save";
    private String addOrUpdate = "Add";
    private String newOrSearch = "New";
    private String icone = "ui-icon-plus";
    private boolean searchPage = true;
    private boolean newPage = false;

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
    //</editor-fold>

    private boolean btnNewRender = false;

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public void newButtonAction() {
        searchPage = false;
        newPage = true;
        newOrSearch = "Search";
        setIcone("ui-icon-search");
        btnNewRender = false;
        saveOrUpdate = "Save";
        btnNewRender=false;
        clear();
        clearUniforDetails();
    }

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
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="main">
    public List<SelectItem> readYears() {
        List<SelectItem> years = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            years.add(new SelectItem(String.valueOf(i), String.valueOf(i) + " years "));
        }
        return years;
    }

    public void vcl_jobTitle(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrJobTypes.setId(Integer.valueOf(event.getNewValue().toString()));
            hrJobTypes = hrJobTypesBeanLocal.findAllJobDetail(hrJobTypes);
        }
    }

    public void vcl_category(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            mmsCategory.setCatId(Integer.parseInt(event.getNewValue().toString()));
            mmsCategory = mmsCategoryBeanLocal.searchByCategoryId(mmsCategory);
            listOfSubCatagories = mmsCategory.getMmsSubCatList();
            mmsItemRegistration.setMatCategory(mmsCategory);
        }
    }

    public void vcl_subCategory(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            mmsSubCat.setSubCatId(Integer.parseInt(event.getNewValue().toString()));
            mmsItemRegistration.setMatSubcategory(mmsSubCat);
            listOfUniformItems = mmsItemRegisrtationIntegrationBeanLocal.searchItemWithMatCatAndSubCatOnly(mmsItemRegistration);
        }
    }

    public void recreateDataModel() {
        uniformDetailsDataModel = null;
        uniformDetailsDataModel = new ListDataModel(new ArrayList(hrUniform.getHrUniformDetailsList()));
    }

    public void clearUniforDetails() {
        hrUniformDetails = new HrUniformDetails();
        mmsItemRegistration = new MmsItemRegistration();
    }

    public void clear() {
        mmsCategory = new MmsCategory();
        mmsSubCat = new MmsSubCat();
        hrUniform = new HrUniform();
        hrJobTypes = new HrJobTypes();
        uniformDetailsDataModel = new ArrayDataModel<>();
        saveOrUpdate = "save";
        addOrUpdate = "add";
    }

    public void addUniform() {
//        if (checkJob.contains(hrJobTypes.getJobTitle())) {
//            // && checkItem.contains(hrUniformDetails.getUniformTypeId().getMatName())
//            JsfUtil.addFatalMessage("Uniform type already exists");
//        } else {
        hrUniform.setJobId(hrJobTypes);
        mmsItemRegistration.setMaterialId(mmsItemRegistration.getMaterialId());
        mmsItemRegistration = mmsItemRegisrtationIntegrationBeanLocal.findbyMaterialId(mmsItemRegistration.getMaterialId());
        System.out.println("uniformtype===" + mmsItemRegistration.getMaterialId());
        System.out.println("uniformtype name===" + mmsItemRegistration.getMatName());
        hrUniformDetails.setUniformTypeId(mmsItemRegistration);
        hrUniform.add(hrUniformDetails);
        checkJob.add(hrJobTypes.getJobTitle());
        // checkItem.add(mmsItemRegistration.getMatName());
        recreateDataModel();
        clearUniforDetails();
        JsfUtil.addSuccessMessage("Successfully Added");
//        }
    }

    public void EditUniformdata() {
        try {
            hrUniformDetails = uniformDetailsDataModel.getRowData();
            hrUniform = hrUniformDetails.getUniformId();
            hrUniform.setJobId(hrJobTypes);
            mmsItemRegistration = hrUniformDetails.getUniformTypeId();
            mmsItemRegistration.setMaterialId(hrUniformDetails.getUniformTypeId().getMaterialId());
            mmsItemRegistration = mmsItemRegisrtationIntegrationBeanLocal.findbyMaterialId(mmsItemRegistration.getMaterialId());
            hrUniformDetails.setUniformTypeId(mmsItemRegistration);
            mmsCategory = mmsItemRegistration.getMatCategory();
            mmsSubCat = mmsItemRegistration.getMatSubcategory();
            listOfSubCatagories = mmsCategory.getMmsSubCatList();
            listOfUniformItems = mmsItemRegisrtationIntegrationBeanLocal.searchItemWithMatCatAndSubCatOnly(mmsItemRegistration);
            System.out.println("item id===" + mmsItemRegistration.getMaterialId());
            System.out.println("item= name==" + mmsItemRegistration.getMatName());
            //mmsItemRegistration = mmsItemRegisrtationBeanLocal.searchByMaterialId(mmsItemRegistration.getMaterialId());
            // mmsCategory = (mmsItemRegistration.getMatCategory());
            System.out.println("cat id ===" + mmsCategory.getCatId());
            System.out.println("cat name===" + mmsCategory.getCatName());
            // mmsSubCat = mmsItemRegistration.getMatSubcategory();
            System.out.println("subcat ===" + mmsSubCat.getSubCatId());
            System.out.println("subcat name==" + mmsSubCat.getSubCatName());
            // mmsSubCat.setSubCatId(mmsSubCat.getSubCatId());
            hrUniformDetails.setUniformTypeId(hrUniformDetails.getUniformTypeId());
            System.out.println("year==" + hrUniformDetails.getYear());
            addOrUpdate = "modify";
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void saveUniform() {
        try {
//            AAA securityService = new AAA();
//            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
//            String systemBundle = "cfg/securityServer";
//            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
//            if (security.checkAccess(SessionBean.getUserName(), "saveUniform", dataset)) {

            if (isUpdated == false) {
                try {
                    uniformBeanLocal.saveorUpdate(hrUniform);
                    JsfUtil.addSuccessMessage("Successfully saved");
                    clear();;
                } catch (Exception ex) {
                    JsfUtil.addFatalMessage("Error occurs while saving");
                }
            } else {
                try {
                    uniformBeanLocal.saveorUpdate(hrUniform);
                    JsfUtil.addSuccessMessage("Successfully updated");
                    clear();
                } catch (Exception ex) {
                    JsfUtil.addFatalMessage("Error occurs while updating");
                }
            }
//            } else {
//                EventEntry eventEntry = new EventEntry();
//                eventEntry.setSessionId(SessionBean.getSessionID());
//                eventEntry.setUserId(SessionBean.getUserId());
//                QName qualifiedName = new QName("", "project");
//                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
//                eventEntry.setUserLogin(test);
////..... add more information by calling fields defined in the object 
//                security.addEventLog(eventEntry, dataset);
//            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void findByJobId(ValueChangeEvent event) {
        if (event.getNewValue().toString() != null) {
            filtercriteria = event.getNewValue().toString();
            System.out.println("crtname==" + filtercriteria);
            if (filtercriteria.equalsIgnoreCase("Load all")) {
                ListOfUniform = uniformBeanLocal.findAllUniforms();
                System.out.println("list==" + ListOfUniform);
            } else {
                hrJobTypes.setId(Integer.valueOf(filtercriteria));
                ListOfUniform = uniformBeanLocal.findByUniformId(hrJobTypes.getId());
                System.out.println("list==" + ListOfUniform);
            }
        }
    }

    public void uniformVlc(SelectEvent event) {
        uniformDetailsDataModel = null;
        hrUniform = (HrUniform) event.getObject();
        hrUniform.setId(hrUniform.getId());
        hrUniform = uniformBeanLocal.loadUniformDetail(hrUniform.getId());
        hrJobTypes = hrUniform.getJobId();
        recreateDataModel();
        newPage = true;
        searchPage = false;
        saveOrUpdate = "update";
        isUpdated = true;
        setIcone("ui-icon-search");
        btnNewRender = true;
    }

    public void reset() {
        hrUniform = new HrUniform();
        hrUniformDetails = new HrUniformDetails();
        hrJobTypes = new HrJobTypes();
        mmsCategory = new MmsCategory();
        mmsSubCat = new MmsSubCat();
        mmsItemRegistration = new MmsItemRegistration();
        isUpdated = false;
    }

//</editor-fold>
}
