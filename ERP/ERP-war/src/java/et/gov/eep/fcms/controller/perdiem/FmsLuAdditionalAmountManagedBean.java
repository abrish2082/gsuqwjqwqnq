/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.perdiem;

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
import et.gov.eep.fcms.businessLogic.perDiem.PerdimeRateBeanLocal;
import et.gov.eep.fcms.businessLogic.perDiem.fmsLuAdditionalAmountBeanLocal;
import et.gov.eep.fcms.entity.perDiem.FmsLuAdditionalAmount;
import et.gov.eep.fcms.entity.perDiem.FmsLuPerdimeRate;
import et.gov.eep.fcms.mapper.perDiem.FmsLuPerdimeRateFacade;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.mms.entity.MmsDisposal;
import et.gov.eep.mms.entity.MmsDisposalItemsDtl;
import javax.annotation.PostConstruct;
import javax.faces.model.ArrayDataModel;
import org.primefaces.model.DefaultTreeNode;
import securityBean.Constants;

/**
 *
 * @author Mubarek jebel
 */
@Named(value = "fmsLuAdditionalAmountManagedBean")
@ViewScoped
public class FmsLuAdditionalAmountManagedBean implements Serializable {
//<editor-fold defaultstate="collapsed" desc="Inject Entities and @ EJB">
    //Inject entities
    @Inject
    SessionBean SessionBean;
    @Inject
    FmsLuAdditionalAmount additionalAmount;
    @Inject
    FmsLuPerdimeRate fmsLuPerdimeRate;
    @Inject
    HrSalaryScaleRanges hrSalaryScaleRanges;
    @EJB
    fmsLuAdditionalAmountBeanLocal additionalAmountBeanLocal;
    @EJB
    PerdimeRateBeanLocal perdimeRateBeanLocal;
    @EJB
    FmsLuPerdimeRateFacade fmsLuPerdimeRateFacade;
      @Inject
    ColumnNameResolver columnNameResolver;
//</editor-fold>
      
      //<editor-fold defaultstate="collapsed" desc="variables declaration">
    //Declaring instance variables
    DataModel<FmsLuAdditionalAmount> additionalAmountDModel;
    private boolean disableBtnCreate;
    private String saveorUpdateBundle = "Create";
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    boolean disable = false;
    int updateStatus = 0;
    String id;
    FmsLuAdditionalAmount selectadditional;
    List<FmsLuAdditionalAmount> addtinalList;
    List<FmsLuAdditionalAmount> fmsLevelList;
    private List<FmsLuAdditionalAmount> FmsLuAdditionalAmountColumnNameList;
    List<ColumnNameResolver> ColumnNameResolverList = new ArrayList<>();
//</editor-fold>
    /**
     * Creates a new instance of FmsLuAdditionalAmountManagedBean
     */
    public FmsLuAdditionalAmountManagedBean() {
    }

    //<editor-fold defaultstate="collapsed" desc="postConstruct">
    
    @PostConstruct
    public void init() {
      ColumnNameResolverList = additionalAmountBeanLocal.getFmsLuAdditionalAmountColumnNameList();   
      
        
    }
//</editor-fold>
//</editor-fold>
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    public FmsLuAdditionalAmount getSelectadditional() {
        return selectadditional;
    }

    public List<FmsLuAdditionalAmount> getFmsLuAdditionalAmountColumnNameList() {
        if (FmsLuAdditionalAmountColumnNameList==null) {
            FmsLuAdditionalAmountColumnNameList = new ArrayList<>();
            FmsLuAdditionalAmountColumnNameList = additionalAmountBeanLocal.getFmsLuAdditionalAmountListsByParameter(additionalAmount);
            
        }
        return FmsLuAdditionalAmountColumnNameList;
    }

    public void setFmsLuAdditionalAmountColumnNameList(List<FmsLuAdditionalAmount> FmsLuAdditionalAmountColumnNameList) {
        this.FmsLuAdditionalAmountColumnNameList = FmsLuAdditionalAmountColumnNameList;
    }

    public void setSelectadditional(FmsLuAdditionalAmount selectadditional) {
        this.selectadditional = selectadditional;
    }

    public List<ColumnNameResolver> getColumnNameResolverList() {
        return ColumnNameResolverList;
    }

    public void setColumnNameResolverList(List<ColumnNameResolver> ColumnNameResolverList) {
        this.ColumnNameResolverList = ColumnNameResolverList;
    }

    public FmsLuAdditionalAmount getAdditionalAmount() {
        if (additionalAmount == null) {
            additionalAmount = new FmsLuAdditionalAmount();
        }
        return additionalAmount;
    }

    public void setAdditionalAmount(FmsLuAdditionalAmount additionalAmount) {
        this.additionalAmount = additionalAmount;
    }

    public FmsLuPerdimeRate getFmsLuPerdimeRate() {
        return fmsLuPerdimeRate;
    }

    public ColumnNameResolver getColumnNameResolver() {
        if(columnNameResolver==null){
            columnNameResolver= new ColumnNameResolver();
        }
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    public void setFmsLuPerdimeRate(FmsLuPerdimeRate fmsLuPerdimeRate) {
        this.fmsLuPerdimeRate = fmsLuPerdimeRate;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public DataModel<FmsLuAdditionalAmount> getAdditionalAmountDModel() {
        if (additionalAmountDModel == null) {
            additionalAmountDModel = new ListDataModel<>();
        }

        return additionalAmountDModel;
    }

    public void setAdditionalAmountDModel(DataModel<FmsLuAdditionalAmount> additionalAmountDModel) {
        this.additionalAmountDModel = additionalAmountDModel;
    }

    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }

    public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
//</editor-fold>

    //recreate to assign addtionalList value to additionalAmountDModel
    public void recreateDataModel() {
        additionalAmountDModel = null;
        additionalAmountDModel = new ListDataModel(new ArrayList(addtinalList));
    }

    //search additional list
    public List<FmsLuAdditionalAmount> getAddtinalList() {
        return addtinalList = additionalAmountBeanLocal.findAddtionalList(additionalAmount);
    }

    //search level
    public void searchLevelByParameter() {
//        if (additionalAmount.getLevelId() != null) {
         String str = additionalAmount.getLevelId();
        additionalAmount.setLevelId(str);
        additionalAmount.setLunchAdditional(SessionBean.getUserId());
            fmsLevelList = additionalAmountBeanLocal.getFmsLuAdditionalAmountListsByParameter(additionalAmount);
            System.out.println("====trno====" + fmsLevelList);
           
//            fmsLevelList = additionalAmountBeanLocal.getFmsLuAdditionalAmountListsByParameter(additionalAmount);
//        } else {
//            fmsLevelList = additionalAmountBeanLocal.searchAllLevel();
//        }
       additionalAmountDModel = new ListDataModel(fmsLevelList);
    }
    
    //value change event for adding additional amount
    public void lelaEvent(ValueChangeEvent e) {
        addtinalList = new ArrayList<>();
        String a = e.getNewValue().toString();
//        additionalAmount.setLevelId(a);
        additionalAmount = (FmsLuAdditionalAmount) additionalAmountBeanLocal.getFmsLuAdditionalAmountListsByParameter(additionalAmount);
////        additionalAmount.setLevelId(additionalAmount.getLevelId());
////        additionalAmount.setLodgingAdditional(additionalAmount.getLodgingAdditional());
//        additionalAmount.setLunchAdditional(additionalAmount.getLunchAdditional());
//        addtinalList.add(additionalAmount);
        recreateDataModel();

    }
    public void changeEventColumnName(ValueChangeEvent changeEvent) {
        if (changeEvent.getNewValue() != null) {
            columnNameResolver.setCol_Name_FromTable(changeEvent.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            additionalAmount.setColumnName(columnNameResolver.getCol_Name_FromTable());

        }

    }
   public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col.toLowerCase() + ":";
    }
    //select event for level id
    public void SystemChange(SelectEvent event) {
        id = additionalAmount.getLevelId();
    }

    //select event for additional row
    public void selectAdditionalRow(SelectEvent event) {
        disable = true;

        additionalAmount = (FmsLuAdditionalAmount) event.getObject();
        additionalAmount.setId(additionalAmount.getId());
        additionalAmount = additionalAmountBeanLocal.getById(additionalAmount);
        renderPnlCreateAdditional = true;
        renderPnlManPage = false;
        activeIndex = "0";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updateStatus = 1;
        saveorUpdateBundle = "Update";
    }

    //save ADditional AMount
    public void saveLuAdditionalAmount() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(SessionBean.getUserName(), "saveLuAdditionalAmount", dataset)) {
            if (updateStatus == 0) {
                if (additionalAmountBeanLocal.search1(additionalAmount) == null) {
                    additionalAmount.setLevelId(id);
                    additionalAmountBeanLocal.create(additionalAmount);
                    JsfUtil.addSuccessMessage("Saved Successfully");
                    clearPage();
                } else {
                    JsfUtil.addFatalMessage("Dupplication Error! The Level " + additionalAmount.getLevelId() + " is Already Registered");
                }
            } else {
                additionalAmountBeanLocal.edit(additionalAmount);
                JsfUtil.addSuccessMessage("Update Successfully!");
                clearPage();
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
    }

    //clear
    private void clearPage() {
        disable = false;
        additionalAmount = null;
        additionalAmountDModel = null;
        updateStatus = 0;
        saveorUpdateBundle = "Create";
    }

    //create and search render 
    public void createNewAdditionalAmount() {
        clearPage();
        saveorUpdateBundle = "Create";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
}
