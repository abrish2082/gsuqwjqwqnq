/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.bank;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
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
import et.gov.eep.fcms.businessLogic.bank.fms_BankBeanLocal;
import et.gov.eep.fcms.entity.bank.FmsBank;
import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author mubejbl
 */
@Named(value = "bankManagedBean")
@ViewScoped
public class BankManagedBean implements Serializable {
//<editor-fold defaultstate="collapsed" desc="Inject Entities and @ EJB">
    //Inject @EJB

    @EJB
    private fms_BankBeanLocal bankBeanLocal;
    //Inject entities
    @Inject
    private FmsBank fmsBank;
    @Inject
    SessionBean SessionBean;
    @Inject
    ColumnNameResolver columnNameResolver;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variables declaration">
    //Declaring instance variables
    DataModel<FmsBank> bankDataModel;
    List<FmsBank> banks;
    private List<FmsBank> selectedBank;
    boolean isDupName;
    boolean isDupCode;
    String isSticky = "false";
    String saveorUpdateBundle = "Save";
    private String activeIndex;
    private String icone = "ui-icon-plus";
    private String createOrSearchBundle = "New";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateBank = false;
    private boolean renderPnlManPage = true;
    int updteStatus = 0;
    ColumnNames columnNames = new ColumnNames();
    List<ColumnNameResolver> ColumnNamesList = new ArrayList<>();
    private List<String> mmsBankColumnNameList;
//</editor-fold>

    public BankManagedBean() {
    }

    public class ColumnNames implements Serializable {

        String Table_Col_Name;
        String Parsed_Col_name;

        //<editor-fold defaultstate="collapsed" desc="getter and setter">
        public String getTable_Col_Name() {
            return Table_Col_Name;
        }

        public void setTable_Col_Name(String Table_Col_Name) {
            this.Table_Col_Name = Table_Col_Name;
        }

        public String getParsed_Col_name() {
            return Parsed_Col_name;
        }

        public void setParsed_Col_name(String Parsed_Col_name) {
            this.Parsed_Col_name = Parsed_Col_name;
        }

//</editor-fold>
    }
    @PostConstruct
    public void initBank() {
       getMmsBankColumnNameList();
    }
//<editor-fold defaultstate="collapsed" desc=" Getter and Setter ">

    public List<FmsBank> getSelectedBank() {
        return selectedBank;
    }

    public void setSelectedBank(List<FmsBank> selectedBank) {
        this.selectedBank = selectedBank;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    public List<ColumnNameResolver> getColumnNamesList() {
        return ColumnNamesList;
    }

    public void setColumnNamesList(List<ColumnNameResolver> ColumnNamesList) {
        this.ColumnNamesList = ColumnNamesList;
    }

    public boolean isRenderPnlCreateBank() {
        return renderPnlCreateBank;
    }

    public void setRenderPnlCreateBank(boolean renderPnlCreateBank) {
        this.renderPnlCreateBank = renderPnlCreateBank;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getIsSticky() {
        return isSticky;
    }

    public void setIsSticky(String isSticky) {
        this.isSticky = isSticky;
    }

    public DataModel<FmsBank> getBankDataModel() {
        if (bankDataModel == null) {
            bankDataModel = new ListDataModel<>();
        }
        return bankDataModel;
    }

    public void setBankDataModel(DataModel<FmsBank> bankDataModel) {
        this.bankDataModel = bankDataModel;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public FmsBank getFmsBank() {
        if (fmsBank == null) {
            fmsBank = new FmsBank();
        }
        return fmsBank;
    }

    public void setFmsBank(FmsBank fmsBank) {
        this.fmsBank = fmsBank;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }
//</editor-fold>

    //recreate method for assigning banks list value to bankDataModel
    public void recreateDataModel() {
        bankDataModel = null;
        bankDataModel = new ListDataModel(new ArrayList(banks));
    }

    //search methos
    public void searchBankByName() {
        System.out.println("in search");
        banks = bankBeanLocal.getBankListsByParameter(columnNameResolver, fmsBank, fmsBank.getColumnValue());
        recreateDataModel();
//        try {

//            banks = new ArrayList<>();
//            if (fmsBank.getBankName().isEmpty()) {
//                banks = bankBeanLocal.getBankName();
//                recreateDataModel();
//            } else if (!fmsBank.getBankName().isEmpty()) {
//                fmsBank.setBankName(fmsBank.getBankName());
//                banks = bankBeanLocal.searchBankByName(fmsBank);
//                recreateDataModel();
//            } else {
//                JsfUtil.addFatalMessage("Unable to search! No Banks begins with " + fmsBank.getBankName() + ". Try again.");
//            }
//        } catch (Exception e) {
//            JsfUtil.addFatalMessage("Unable to search! No Banks begins with " + fmsBank.getBankName() + ". Try again.");
//        }
    }

    public void columnNameChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            System.out.println("event.getNewValue().toString()==" + event.getNewValue().toString());
            columnNameResolver.setCol_Name_FromTable(event.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            fmsBank.setColumnName(columnNameResolver.getCol_Name_FromTable());
            fmsBank.setColumnValue(null);
        }
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }

    public List<String> getMmsBankColumnNameList() {
        mmsBankColumnNameList = bankBeanLocal.getMmsBankColumnNameList();
        if (mmsBankColumnNameList == null) {
            mmsBankColumnNameList = new ArrayList<>();
        }
        System.out.println("=======mmsStoreColumnNameList==" + mmsBankColumnNameList);
        if (mmsBankColumnNameList.size() > 0) {
            ColumnNamesList = new ArrayList<>();
            for (int i = 0; i < mmsBankColumnNameList.size(); i++) {
                ColumnNameResolver obj = new ColumnNameResolver();
                obj.setCol_Name_FromTable((mmsBankColumnNameList.get(i).toLowerCase()));
                obj.setParsed_Col_Name(ColumnParser((mmsBankColumnNameList.get(i).toLowerCase())));
                ColumnNamesList.add(obj);
            }

        }
        return mmsBankColumnNameList;
    }

    public void setMmsBankColumnNameList(List<String> mmsBankColumnNameList) {
        this.mmsBankColumnNameList = mmsBankColumnNameList;
    }

    //select event for bank information
    public void getBankInfo(SelectEvent event) {
        banks = new ArrayList<>();
        fmsBank.setBankId(fmsBank.getBankId());
        fmsBank.setBankName(event.getObject().toString());
        fmsBank = bankBeanLocal.getBankInfo(fmsBank);
        updteStatus = 1;
        saveorUpdateBundle = "Update";
        recreateDataModel();
    }

    //edit render method
    public void onRowEditAdd() {
        renderPnlCreateBank = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updteStatus = 1;
        int rowIndex = bankDataModel.getRowIndex();
        fmsBank = banks.get(rowIndex);
    }

    //populate
    public void populate(SelectEvent event) {
        fmsBank = (FmsBank) event.getObject();
        renderPnlCreateBank = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updteStatus = 1;
    }

    //save bank
    public String saveFcmsBank() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(SessionBean.getUserName(), "saveFcmsBank", dataset)) {
            isDupCode = bankBeanLocal.findDupByBankCode(fmsBank);
            isDupName = bankBeanLocal.findDupByBankName(fmsBank);
            if (updteStatus == 0) {//on Save
                if (isDupName == false && isDupCode == false) {
                    try {
                        bankBeanLocal.create(fmsBank);
                        JsfUtil.addSuccessMessage("Registered Successfully!");
                        isSticky = "false";
                        saveUpdateClear();
                    } catch (Exception e) {
                        JsfUtil.addFatalMessage("Failed to Register. Try Again Reloading the Page.");
                    }
                } else if (isDupName == true) {
                    isSticky = "true";
                    JsfUtil.addFatalMessage("Duplication! Bank Name: '" + fmsBank.getBankName() + "' is Already Registered.");
                } else if (isDupCode == true) {
                    isSticky = "true";
                    JsfUtil.addFatalMessage("Unique Constraint! Bank Code: '" + fmsBank.getBankCode() + "' is Taken by an Other Bank.");
                }
            } else {//on Update
                if ((isDupName == false && isDupCode == false) || (isDupName == false && isDupCode == true) || (isDupName == true && isDupCode == false)) {
                    try {
                        bankBeanLocal.edit(fmsBank);
                        JsfUtil.addSuccessMessage("Updated Successfully!");
                        isSticky = "false";
                        saveorUpdateBundle = "Save";
                        saveUpdateClear();
                    } catch (Exception e) {
                        JsfUtil.addFatalMessage("Failed to Update! Try again.");
                    }
                } else if (isDupName == true || isDupCode == true) {
                    isSticky = "true";
                    JsfUtil.addFatalMessage("Unique Constraint! Either Bank Name: '" + fmsBank.getBankName() + "' OR Bank Code: '" + fmsBank.getBankCode() + "' is Taken by an Other Bank.");
                }
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
        return null;
    }

    //clear
    private void saveUpdateClear() {
        fmsBank = null;
        bankDataModel = null;
    }

//create and search render
    public void createNewBank() {
        saveUpdateClear();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateBank = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateBank = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

}
