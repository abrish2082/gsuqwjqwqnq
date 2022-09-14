/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.medical;

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
import et.gov.eep.hrms.businessLogic.medical.MedInstitutionsBeanLocal;
import et.gov.eep.hrms.entity.lookup.HrLuBankBranches;
import et.gov.eep.hrms.entity.lookup.HrLuBanks;
import et.gov.eep.hrms.entity.medical.HrLocalMedInstitutions;

/**
 *
 * @author INSA
 */
@Named(value = "medicalInstitutionController")
@ViewScoped
public class MedicalInstitutionController implements Serializable {

    @Inject
    HrLocalMedInstitutions medicalInstitutions;

    @Inject
    HrLocalMedInstitutions srcmedicalInstitutions;

    @Inject
    HrLuBanks hrLuBanks;

    @Inject
    HrLuBankBranches bankBranches;
    @Inject
    SessionBean sessionBeanLocal;

    @EJB
    MedInstitutionsBeanLocal medInstitutionsBeanLocal;

    int updateStatus = 0;
    private String saveOrUpdate = "Save";
    private String addOrUpdate = "Add";
    private String newOrSearch = "New";
    private String icone = "ui-icon-plus";
    private boolean searchPage = true;
    private boolean newPage = false;
    private boolean btnNewRender = false;

    DataModel<HrLocalMedInstitutions> medInstDataModel;
    List<HrLocalMedInstitutions> InstitutionName;
    private List<HrLocalMedInstitutions> medInstList;

    public MedicalInstitutionController() {
    }

    @PostConstruct
    public void init() {
        medicalInstitutions.setType("Hospital");
        medicalInstitutions.setPartner("Partner");
        medicalInstitutions.setStatus(0);
    }

    //<editor-fold defaultstate="collapsed" desc="getter setter">
    public HrLocalMedInstitutions getMedicalInstitutions() {
        if (medicalInstitutions == null) {
            medicalInstitutions = new HrLocalMedInstitutions();
        }
        return medicalInstitutions;
    }

    public void setMedicalInstitutions(HrLocalMedInstitutions medicalInstitutions) {
        this.medicalInstitutions = medicalInstitutions;
    }

    public HrLocalMedInstitutions getSrcmedicalInstitutions() {
        if (srcmedicalInstitutions == null) {
            srcmedicalInstitutions = new HrLocalMedInstitutions();
        }
        return srcmedicalInstitutions;
    }

    public void setSrcmedicalInstitutions(HrLocalMedInstitutions srcmedicalInstitutions) {
        this.srcmedicalInstitutions = srcmedicalInstitutions;
    }

    public HrLuBanks getHrLuBanks() {
        if (hrLuBanks == null) {
            hrLuBanks = new HrLuBanks();
        }
        return hrLuBanks;
    }

    public void setHrLuBanks(HrLuBanks hrLuBanks) {
        this.hrLuBanks = hrLuBanks;
    }

    public HrLuBankBranches getBankBranches() {
        if (bankBranches == null) {
            bankBranches = new HrLuBankBranches();
        }
        return bankBranches;
    }

    public void setBankBranches(HrLuBankBranches bankBranches) {
        this.bankBranches = bankBranches;
    }

    public DataModel<HrLocalMedInstitutions> getMedInstDataModel() {
        if (medInstDataModel == null) {
            medInstDataModel = new ArrayDataModel<>();
        }
        return medInstDataModel;
    }

    public void setMedInstDataModel(DataModel<HrLocalMedInstitutions> medInstDataModel) {
        this.medInstDataModel = medInstDataModel;
    }

    public List<HrLocalMedInstitutions> getInstitutionName() {
        InstitutionName = medInstitutionsBeanLocal.getAllInstitutionName();
        return InstitutionName;
    }

    public void setInstitutionName(List<HrLocalMedInstitutions> InstitutionName) {
        this.InstitutionName = InstitutionName;
    }

    public List<HrLocalMedInstitutions> getMedInstList() {
        return medInstList;
    }

    public void setMedInstList(List<HrLocalMedInstitutions> medInstList) {
        this.medInstList = medInstList;
    }

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

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    //</editor-fold>
    public void btnNewOrSearch() {
        switch (newOrSearch) {
            case "New":
                searchPage = false;
                newPage = true;
                btnNewRender = false;
                newOrSearch = "Search";
                setIcone("ui-icon-search");
                break;
            case "Search":
                searchPage = true;
                newPage = false;
                btnNewRender = false;
                newOrSearch = "New";
                setIcone("ui-icon-plus");
                break;
        }
    }

    public void newButtonAction() {
        resetMedicalInstitution();
        newPage = true;
        searchPage = false;
        btnNewRender = false;
    }

    //<editor-fold defaultstate="collapsed" desc="bank search">
    public SelectItem[] getListOfBanks() {
        return JsfUtil.getSelectItems(medInstitutionsBeanLocal.findAllBanks(), true);
    }

    public SelectItem[] getBranchByBank() {
        if (hrLuBanks.getBankName() != null) {
            SelectItem[] steps = JsfUtil.getSelectItems(medInstitutionsBeanLocal.getBankBranchInfo(hrLuBanks), true);
            return steps;
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--- Select ---");
            return items;
        }
    }

    public void BankChanged(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            hrLuBanks.setBankName(event.getNewValue().toString());
            hrLuBanks = medInstitutionsBeanLocal.findBanks(hrLuBanks);
            medicalInstitutions.setBankId(hrLuBanks);
        }
    }

    public void BankBranchChanged(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            bankBranches.setBranchName(event.getNewValue().toString());
            bankBranches = medInstitutionsBeanLocal.findBankBranchs(bankBranches);
            medicalInstitutions.setBranchId(bankBranches);
        }
    }
//</editor-fold>

    public void handleSelectedName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            String name = String.valueOf(event.getNewValue().toString());
            srcmedicalInstitutions.setName(name);
        }
    }

    public void recreateDataModel(List<HrLocalMedInstitutions> recreateDataModel) {
        medInstDataModel = null;
        medInstDataModel = new ListDataModel(new ArrayList<>(recreateDataModel));
    }

    public void vcl_institutionName(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            String instName = String.valueOf(event.getNewValue());
            recreateDataModel(medInstitutionsBeanLocal.findByName(instName));
        }
    }

    public void medicalInstList() {
        try {
            if (srcmedicalInstitutions.getName() == null) {
                medInstList = medInstitutionsBeanLocal.getAllInstitutionName();
                medInstDataModel = new ListDataModel(medInstList);
            } else if (!srcmedicalInstitutions.getName().isEmpty()) {
                medInstList = medInstitutionsBeanLocal.findByInstitutionName(srcmedicalInstitutions);
                medInstDataModel = new ListDataModel(medInstList);
            } else {
                JsfUtil.addFatalMessage("Sorry!. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Sorry! Reload the page and try again.");
        }
    }

    public void medicalInstDisplayChanged(SelectEvent event) {
        medicalInstitutions = (HrLocalMedInstitutions) event.getObject();
        hrLuBanks = medicalInstitutions.getBankId();
        bankBranches = medicalInstitutions.getBranchId();
        newPage = true;
        searchPage = false;
        btnNewRender = true;
        newOrSearch = "Search";
        updateStatus = 1;
        saveOrUpdate = "Update";
        setIcone("ui-icon-search");
    }

    public void saveMedicalInstitution() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveMedicalInstitution", dataset)) {
                boolean checkDuplication = medInstitutionsBeanLocal.isExist(medicalInstitutions);
                if (updateStatus == 0 && checkDuplication == false) {
                    medInstitutionsBeanLocal.save(medicalInstitutions);
                    JsfUtil.addSuccessMessage("Successfully saved");
                    resetMedicalInstitution();
                } else if (updateStatus == 0 && checkDuplication == true) {
                    JsfUtil.addFatalMessage("Already registered. Try again!");
                } else {
                    medInstitutionsBeanLocal.edit(medicalInstitutions);
                    JsfUtil.addSuccessMessage("Successfully updated");
                    resetMedicalInstitution();
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveMedicalInstitution");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JsfUtil.addFatalMessage("Error occurs");
        }
    }

    public void resetMedicalInstitution() {
        medicalInstitutions = new HrLocalMedInstitutions();
        hrLuBanks = new HrLuBanks();
        bankBranches = new HrLuBankBranches();
        updateStatus = 0;
        saveOrUpdate = "Save";
    }

    public ArrayList<HrLocalMedInstitutions> findByInstitutionName(ValueChangeEvent event) {
        if (event.getNewValue().toString() == null) {
            JsfUtil.addFatalMessage("Institution name can't be empty");
        } else {
            String instName = event.getNewValue().toString();
            if (instName.equalsIgnoreCase("Load all")) {
                medInstList = medInstitutionsBeanLocal.getAllInstitutionName();
                medInstDataModel = new ListDataModel(medInstList);
            } else {
                recreateDataModel(medInstitutionsBeanLocal.findByName(instName));
            }
        }
        return null;
    }

    public SelectItem[] getinst_Type() {
        return JsfUtil.getSelectItems(inst_Type(), true);
    }

    public ArrayList<String> inst_Type() {
        ArrayList<String> typeList = new ArrayList<>();
        typeList.add("Hospital");
        typeList.add("Pharmacy");
        return typeList;
    }

}
