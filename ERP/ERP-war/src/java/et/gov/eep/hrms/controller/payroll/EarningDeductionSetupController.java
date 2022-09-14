/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;

//<editor-fold defaultstate="collapsed" desc="Imports">
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollEarningDeductionsBeanLocal;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
//</editor-fold>

/**
 *
 * @author Administrator
 */
@Named(value = "earningDeductionSetupController")
@ViewScoped
public class EarningDeductionSetupController implements Serializable {

    public EarningDeductionSetupController() {
    }
//<editor-fold defaultstate="collapsed" desc="Objects & Variables">

    @Inject
    FmsSubsidiaryLedger fmsSubsid1aryLedger1;
    @Inject
    HrPayrollEarningDeductions hrPayrollEarningDeductions;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @Inject
    FmsGeneralLedger fmsCounterGeneralLedger;
    @Inject
    SessionBean sessionBeanLocal;
    @EJB
    HrPayrollEarningDeductionsBeanLocal hrPayrollEarningDeductionsLocal;
    @EJB
    subsidiaryBeanLocal subsidiaryBeanLocal;
    @EJB
    FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;

    private boolean searchPage = true;
    private boolean newPage = false;
    private boolean disableDedFirstfromSal = false;
    private boolean isTaxable;
    private boolean isForcedDeduction;
    private boolean percentileEarningDedUsingSal;
    private boolean isPercentage;
    private boolean isDeductionFirstMadeFromSal;
    private boolean value1;
    private boolean value2;
    private String selectedSubsidary;
    private String saveOrUpdate = "Save";
    private String earningDeductionType;
    private String glId;
    private String newOrSearch = "New";
    private String icone = "ui-icon-plus";
    private BigDecimal big;
    private List<FmsSubsidiaryLedger> listOfSubsidayLedger;
    private List<HrPayrollEarningDeductions> listOfEarnings;
    private List<HrPayrollEarningDeductions> listOfDeductions;
    private List<HrPayrollEarningDeductions> listOfHrpayrollEarningDeductions;
    private List<FmsGeneralLedger> listOfGeneralLedger;
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getters & Setters">

    /**
     *
     * @return
     */
    public String getGlId() {
        return glId;
    }

    public void setGlId(String glId) {
        this.glId = glId;
    }

    public String getEarningDeductionType() {
        return earningDeductionType;
    }

    public void setEarningDeductionType(String earningDeductionType) {
        this.earningDeductionType = earningDeductionType;
    }

    public boolean isValue1() {
        return value1;
    }

    public void setValue1(boolean value1) {
        this.value1 = value1;
    }

    public boolean isValue2() {
        return value2;
    }

    public void setValue2(boolean value2) {
        this.value2 = value2;
    }

    public HrPayrollEarningDeductions getHrPayrollEarningDeductions() {
        if (hrPayrollEarningDeductions == null) {
            hrPayrollEarningDeductions = new HrPayrollEarningDeductions();
        }
        return hrPayrollEarningDeductions;
    }

    /**
     *
     * @param hrPayrollEarningDeductions
     */
    public void setHrPayrollEarningDeductions(HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        this.hrPayrollEarningDeductions = hrPayrollEarningDeductions;
    }

    public List<FmsGeneralLedger> getListOfGeneralLedger() {
        listOfGeneralLedger = fmsGeneralLedgerBeanLocal.findAll();
        return listOfGeneralLedger;
    }

    public void setListOfGeneralLedger(List<FmsGeneralLedger> listOfGeneralLedger) {
        this.listOfGeneralLedger = listOfGeneralLedger;
    }

    public SelectItem[] getGLdata() {
        return JsfUtil.getSelectItems(fmsGeneralLedgerBeanLocal.findAll(), true);
    }

    /**
     *
     * @return
     */
    public List<HrPayrollEarningDeductions> getListOfHrpayrollEarningDeductions() {
        listOfHrpayrollEarningDeductions = hrPayrollEarningDeductionsLocal.findAll();
        return listOfHrpayrollEarningDeductions;
    }

    /**
     *
     * @param listOfHrpayrollEarningDeductions
     */
    public void setListOfHrpayrollEarningDeductions(List<HrPayrollEarningDeductions> listOfHrpayrollEarningDeductions) {
        this.listOfHrpayrollEarningDeductions = listOfHrpayrollEarningDeductions;
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

    public FmsGeneralLedger getFmsCounterGeneralLedger() {
        if (fmsCounterGeneralLedger == null) {
            fmsCounterGeneralLedger = new FmsGeneralLedger();
        }
        return fmsCounterGeneralLedger;
    }

    public void setFmsCounterGeneralLedger(FmsGeneralLedger fmsCounterGeneralLedger) {
        this.fmsCounterGeneralLedger = fmsCounterGeneralLedger;
    }

    /**
     *
     * @return
     */
    public List<HrPayrollEarningDeductions> getListOfEarnings() {
        listOfEarnings = hrPayrollEarningDeductionsLocal.loadListOfEarnings();
        return listOfEarnings;
    }

    /**
     *
     * @param listOfEarnings
     */
    public void setListOfEarnings(List<HrPayrollEarningDeductions> listOfEarnings) {
        this.listOfEarnings = listOfEarnings;
    }

    /**
     *
     * @return
     */
    public List<HrPayrollEarningDeductions> getListOfDeductions() {
        listOfDeductions = hrPayrollEarningDeductionsLocal.loadListOfDeductions();
        return listOfDeductions;
    }

    /**
     *
     * @param listOfDeductions
     */
    public void setListOfDeductions(List<HrPayrollEarningDeductions> listOfDeductions) {
        this.listOfDeductions = listOfDeductions;
    }

    /**
     *
     * @return
     */
    public FmsSubsidiaryLedger getFmsSubsid1aryLedger1() {
        return fmsSubsid1aryLedger1;
    }

    /**
     *
     * @return
     */
    public String getSelectedSubsidary() {
        return selectedSubsidary;
    }

    /**
     *
     * @param selectedSubsidary
     */
    public void setSelectedSubsidary(String selectedSubsidary) {
        this.selectedSubsidary = selectedSubsidary;
    }

    /**
     *
     * @param fmsSubsid1aryLedger1
     */
    public void setFmsSubsid1aryLedger1(FmsSubsidiaryLedger fmsSubsid1aryLedger1) {
        this.fmsSubsid1aryLedger1 = fmsSubsid1aryLedger1;
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

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public List<FmsSubsidiaryLedger> getListOfSubsidayLedger() {
        listOfSubsidayLedger = subsidiaryBeanLocal.findAll();
        return listOfSubsidayLedger;
    }

    public String getSaveOrUpdate() {
        return saveOrUpdate;
    }

    /**
     *
     * @param saveOrUpdate
     */
    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }

    /**
     *
     * @param subsidayName
     * @return
     */
    public List<FmsSubsidiaryLedger> searchSubsidayLedger(String subsidayName) {
        try {
            return listOfSubsidayLedger = subsidiaryBeanLocal.searchSubsidiaryByName(subsidayName);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param listOfSubsidayLedger
     */
    public void setListOfSubsidayLedger(List<FmsSubsidiaryLedger> listOfSubsidayLedger) {
        this.listOfSubsidayLedger = listOfSubsidayLedger;
    }

    public boolean isIsDeductionFirstMadeFromSal() {
        return isDeductionFirstMadeFromSal;
    }

    public void setIsDeductionFirstMadeFromSal(boolean isDeductionFirstMadeFromSal) {
        this.isDeductionFirstMadeFromSal = isDeductionFirstMadeFromSal;
    }

    public boolean isDisableDedFirstfromSal() {
        return disableDedFirstfromSal;
    }

    public void setDisableDedFirstfromSal(boolean disableDedFirstfromSal) {
        this.disableDedFirstfromSal = disableDedFirstfromSal;
    }

    public boolean isIsPercentage() {
        return isPercentage;
    }

    public void setIsPercentage(boolean isPercentage) {
        this.isPercentage = isPercentage;
    }

    /**
     *
     * @return
     */
    public boolean isIsTaxable() {
        return isTaxable;
    }

    /**
     *
     * @param isTaxable
     */
    public void setIsTaxable(boolean isTaxable) {
        this.isTaxable = isTaxable;
    }

    /**
     *
     * @return
     */
    public boolean isIsForcedDeduction() {
        return isForcedDeduction;
    }

    /**
     *
     * @param isForcedDeduction
     */
    public void setIsForcedDeduction(boolean isForcedDeduction) {
        this.isForcedDeduction = isForcedDeduction;
    }

    public boolean isPercentileEarningDedUsingSal() {
        return percentileEarningDedUsingSal;
    }

    public void setPercentileEarningDedUsingSal(boolean percentileEarningDedUsingSal) {
        this.percentileEarningDedUsingSal = percentileEarningDedUsingSal;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Main Methods">

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

    /**
     *
     * @param vToBeSplited
     * @return
     */
    public String returnAccountTitle(String vToBeSplited) {
        try {
            String fSplitedValue;
            String conc[];
            conc = vToBeSplited.split("-");
            fSplitedValue = conc[1];
            return fSplitedValue;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param vToBeSplited
     * @return
     */
    public int firstSplietedValue(String vToBeSplited) {
        try {
            int fSplitedValue;
            String conc[];
            conc = vToBeSplited.split("-");
            fSplitedValue = Integer.valueOf(conc[0]);
            return fSplitedValue;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    /**
     *
     * @param event
     */
    public void handleSelect(SelectEvent event) {
        try {
            selectedSubsidary = "h";
            firstSplietedValue(event.getObject().toString());
            fmsSubsid1aryLedger1.setSubsidiaryId(firstSplietedValue(event.getObject().toString()));
            fmsSubsid1aryLedger1.setSubsidiaryCode(returnAccountTitle(event.getObject().toString()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    ///Payroll Earning Deductions
    /**
     *
     */
    public void saveEarningDeductions() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveEarningDeductions", dataset)) {
                if (isTaxable) {
                    hrPayrollEarningDeductions.setTaxable("YES");
                } else {
                    hrPayrollEarningDeductions.setTaxable("NO");
                }
                if (isForcedDeduction) {
                    hrPayrollEarningDeductions.setForcedDeduction("YES");
                } else {
                    hrPayrollEarningDeductions.setForcedDeduction("NO");
                }
                if (isPercentage) {
                    hrPayrollEarningDeductions.setPercentage("YES");
                } else {
                    hrPayrollEarningDeductions.setPercentage("NO");
                }
                if (isDeductionFirstMadeFromSal) {
                    hrPayrollEarningDeductions.setFirstDeductFromSal("YES");
                } else {
                    hrPayrollEarningDeductions.setFirstDeductFromSal("NO");
                }
                hrPayrollEarningDeductions.setGeneralLedgerId(fmsGeneralLedger);
                hrPayrollEarningDeductions.setCounterLederId(fmsGeneralLedger);
                if (saveOrUpdate.equalsIgnoreCase("Save")) {
                    //CHEAK THE ITEM CODE IS REPEATED OR NOT
                    if (hrPayrollEarningDeductionsLocal.cheakItemCode(hrPayrollEarningDeductions) == null) {
                        if (hrPayrollEarningDeductions.getCriterias().equalsIgnoreCase("Other")
                                || hrPayrollEarningDeductions.getCriterias().equalsIgnoreCase("All Employees Earning Deduction")
                                || hrPayrollEarningDeductions.getCriterias().equalsIgnoreCase("Allowance in JL")
                                || hrPayrollEarningDeductions.getCriterias().equalsIgnoreCase("Allowance in JT")
                                || hrPayrollEarningDeductions.getCriterias().equalsIgnoreCase("Allowance in Loc")) {
                            hrPayrollEarningDeductionsLocal.create(hrPayrollEarningDeductions);
                            JsfUtil.addSuccessMessage("Successfully Saved!");
                            hrPayrollEarningDeductions = null;
                            selectedSubsidary = null;
                            saveOrUpdate = "Save";
                        } else {
                            if (hrPayrollEarningDeductionsLocal.cheakErningDeductionCriterias(hrPayrollEarningDeductions) != null) {
                                JsfUtil.addFatalMessage("Repeatition is not allowed for this earning/Deduction!");
                            } else {
                                hrPayrollEarningDeductionsLocal.create(hrPayrollEarningDeductions);
                                JsfUtil.addSuccessMessage("Successfully Saved!");
                                hrPayrollEarningDeductions = null;
                                selectedSubsidary = null;
                                saveOrUpdate = "Save";
                            }
                        }
                    } else {
                        JsfUtil.addFatalMessage("The item code is already available,Change the item code first!");
                    }
                } else if (saveOrUpdate.equalsIgnoreCase("Update")) {
                    if (hrPayrollEarningDeductionsLocal.cheakItemCodeForUpdate(hrPayrollEarningDeductions) == null) {
                        hrPayrollEarningDeductionsLocal.edit(hrPayrollEarningDeductions);
                        JsfUtil.addSuccessMessage("Successfully Updated!");
                        hrPayrollEarningDeductions = null;
                        selectedSubsidary = null;
                        saveOrUpdate = "Save";
                    } else {
                        JsfUtil.addFatalMessage("The item code is already available,Change the item code first!");
                    }
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void populateEaring(SelectEvent events) {
        hrPayrollEarningDeductions = null;
        hrPayrollEarningDeductions = (HrPayrollEarningDeductions) events.getObject();
        try {
            saveOrUpdate = "Update";
            searchPage = false;
            newPage = true;
            isTaxable = false;
            isForcedDeduction = false;
            isDeductionFirstMadeFromSal = false;
            if (hrPayrollEarningDeductions.getTaxable().trim().equalsIgnoreCase("YES")) {
                isTaxable = true;
            } else {
                isTaxable = false;
            }
            if (hrPayrollEarningDeductions.getForcedDeduction().equalsIgnoreCase("YES")) {
                isForcedDeduction = true;
            } else {
                isForcedDeduction = false;
            }
            if (hrPayrollEarningDeductions.getFirstDeductFromSal().equalsIgnoreCase("YES")) {
                isDeductionFirstMadeFromSal = true;
            } else {
                isDeductionFirstMadeFromSal = false;
            }
            fmsGeneralLedger.setGeneralLedgerId(hrPayrollEarningDeductions.getGeneralLedgerId().getGeneralLedgerId());
            fmsCounterGeneralLedger.setGeneralLedgerId(hrPayrollEarningDeductions.getCounterLederId().getGeneralLedgerId());
        } catch (Exception e) {
        }
        btnNewOrSearch();
    }

    public void populateDeduction(SelectEvent event) {
        try {
            saveOrUpdate = "Update";
            searchPage = false;
            newPage = true;
            isTaxable = false;
            isForcedDeduction = false;
            isDeductionFirstMadeFromSal = false;
            if (hrPayrollEarningDeductions.getTaxable().trim().equalsIgnoreCase("YES")) {
                isTaxable = true;
            } else {
                isTaxable = false;
            }
            if (hrPayrollEarningDeductions.getForcedDeduction().equalsIgnoreCase("YES")) {
                isForcedDeduction = true;
            } else {
                isForcedDeduction = false;
            }
            if (hrPayrollEarningDeductions.getFirstDeductFromSal().equalsIgnoreCase("YES")) {
                isDeductionFirstMadeFromSal = true;
            } else {
                isDeductionFirstMadeFromSal = false;
            }
            fmsGeneralLedger.setGeneralLedgerId(hrPayrollEarningDeductions.getGeneralLedgerId().getGeneralLedgerId());
            fmsCounterGeneralLedger.setGeneralLedgerId(hrPayrollEarningDeductions.getCounterLederId().getGeneralLedgerId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        btnNewOrSearch();
    }

    public void editEarningDeductions() {
        try {
            hrPayrollEarningDeductionsLocal.edit(hrPayrollEarningDeductions);
            JsfUtil.addSuccessMessage("Successfully Saved!");
            hrPayrollEarningDeductions = null;
            selectedSubsidary = null;
        } catch (Exception e) {
        }
    }

    /**
     *
     */
    public void vcl_MapEarningDeduction(ValueChangeEvent event) {
        if (event.getNewValue() != null) {

        }
    }

    public void handleValueChangeGeneralLedger(ValueChangeEvent event) {
        try {
            fmsGeneralLedger.setGeneralLedgerId(Integer.valueOf(event.getNewValue().toString()));
            fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getByGlId(fmsGeneralLedger);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void handleValueChangeCounterGeneralLedger(ValueChangeEvent event) {
        try {
            fmsCounterGeneralLedger.setGeneralLedgerId(Integer.valueOf(event.getNewValue().toString()));
            fmsCounterGeneralLedger = fmsGeneralLedgerBeanLocal.getByGlId(fmsCounterGeneralLedger);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void handleChange() {
        try {
            hrPayrollEarningDeductions.setDescription(fmsGeneralLedger.getAccountTitle());
        } catch (Exception e) {
        }
    }

    public void handleChangeCounterJL() {
        try {
            hrPayrollEarningDeductions.setCounterLedDesc(fmsCounterGeneralLedger.getAccountTitle());
        } catch (Exception e) {
        }
    }

    public void handleValueChangeOnEdTypes(ValueChangeEvent event) {
        try {
            earningDeductionType = event.getNewValue().toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void handleConterGL() {
        try {
            if (hrPayrollEarningDeductions.getCounterDr().equalsIgnoreCase("Debit")) {
                hrPayrollEarningDeductions.setDrcr("Credit");

            } else if (hrPayrollEarningDeductions.getCounterDr().equalsIgnoreCase("Credit")) {
                hrPayrollEarningDeductions.setDrcr("Debit");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleChangeTo() {
        try {
            if (hrPayrollEarningDeductions.getDrcr().equalsIgnoreCase("Debit")) {
                hrPayrollEarningDeductions.setCounterDr("Credit");

            } else if (hrPayrollEarningDeductions.getDrcr().equalsIgnoreCase("Credit")) {
                hrPayrollEarningDeductions.setCounterDr("Debit");
            }

            if (earningDeductionType.equalsIgnoreCase("Earning")) {
                disableDedFirstfromSal = true;
                isDeductionFirstMadeFromSal = false;
            } else {
                disableDedFirstfromSal = false;
                isDeductionFirstMadeFromSal = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//</editor-fold>
}
