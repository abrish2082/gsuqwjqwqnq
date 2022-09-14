/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.bank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.convert.NumberConverter;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.businessLogic.bank.bankAccountBeanLocal;
import et.gov.eep.fcms.businessLogic.bank.bankBranchAccountsBeanLocal;
import et.gov.eep.fcms.businessLogic.bank.fms_BankBeanLocal;
import et.gov.eep.fcms.entity.bank.FmsBank;
import et.gov.eep.fcms.entity.bank.FmsBankAccount;
import et.gov.eep.fcms.entity.bank.FmsBankBranchAccounts;
import et.gov.eep.fcms.entity.bank.FmsBankCashDeposit;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.entity.admin.FmsSystemJobJunction;

/**
 *
 * @author mubejbl
 */
@Named(value = "bankAccountManagedBean")
@ViewScoped
public class BankAccountManagedBean implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Inject Entities and @ EJB">
    //Inject @EJB
    @EJB
    FmsCostCenterBeanLocal fmsCostCenterBeanLocal;
    @EJB
    subsidiaryBeanLocal subLedgerBeanLocal;
    @EJB
    private subsidiaryBeanLocal subsidiaryBeanLocal;
    @EJB
    private bankAccountBeanLocal bankAccBeanLocal;
    @EJB
    private fms_BankBeanLocal bankBeanLocal;
    @EJB
    private bankBranchAccountsBeanLocal branchAccountsBeanLocal;
    @EJB
    FmsLuSystemBeanLocal fmsLuSystemBeanLocal;
    @EJB
    FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal;
    @EJB
    private FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    private subsidiaryBeanLocal subsidiaryLedgerBeanLocal;
    //Inject entities
    @Inject
    FmsSubsidiaryLedger fmsSubsid1aryLedger1;
    @Inject
    private FmsBankBranchAccounts fmsBankBranchAccounts;
    @Inject
    private FmsBankAccount fmsBankAccount;
    @Inject
    private FmsBank fmsBank;
    @Inject
    private FmsBankCashDeposit fmsBankCashDeposit;
    @Inject
    SessionBean SessionBean;
    @Inject
    FmsLuSystem fmsLuSystem;
    @Inject
    FmsCostCenter fmsCostCenter;
    @Inject
    FmsSubsidiaryLedger fmsSubsidiaryLedger;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @Inject
    private FmsCostcSystemJunction fmsCostcSystemJunction;
    @Inject
    private FmsSystemJobJunction fmsSystemJobJunction;
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variables declaration">
    //Declaring instance variables
    DataModel<FmsBankBranchAccounts> fmsBankBranchAccountsModel;
    DataModel<FmsBankAccount> bankAccountDataModel;
    List<FmsBank> bankNameList;
    List<FmsSubsidiaryLedger> acctCodeList;
    List<FmsBankBranchAccounts> listsofaccount;
    List<FmsCostcSystemJunction> ssCcJunctionList = new ArrayList<>();
    List<FmsSystemJobJunction> sysJobNOList;
    List<FmsGeneralLedger> glList;
    List<FmsSubsidiaryLedger> slList;
    List<FmsLuSystem> systemList;
    List<FmsBankAccount> branchNames;
    List<FmsBankAccount> bankAccounts;
    boolean renderJobNo = false;
    final Integer projectType = 2;
    final Integer nonProjectType = 1;
    String saveorUpdateBundle = "Save";
    String forAddOrModify = "Add";
    private String concatination = "";
    private String display_conn;
    private String icone = "ui-icon-plus";
    private String Konk = "";
    private String activeIndex;
    private String createOrSearchBundle = "New";
    private String headerTitle = "Search Bank Account";
    private boolean renderPnlCreateAcc = false;
    private boolean renderPnlManPage = true;
    private boolean disableBtnCreate;
    int updateStatus = 0;
    int addUpdateStatus = 0;
    Set<String> checkSubsidiaryCode = new HashSet<>();
    Set<String> checkAccountNo = new HashSet<>();
    private FmsBankAccount selectedBankAcc;
    private FmsBankBranchAccounts selectedAccDetail;
    private NumberConverter numberConverter = new NumberConverter();
//</editor-fold>

    public BankAccountManagedBean() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(3);
    }
//<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">

    public FmsLuSystem getFmsLuSystem() {
        if (fmsLuSystem == null) {
            fmsLuSystem = new FmsLuSystem();
        }
        return fmsLuSystem;
    }

    public void setFmsLuSystem(FmsLuSystem fmsLuSystem) {
        this.fmsLuSystem = fmsLuSystem;
    }

    public FmsCostCenter getFmsCostCenter() {
        if (fmsCostCenter == null) {
            fmsCostCenter = new FmsCostCenter();
        }
        return fmsCostCenter;
    }

    public void setFmsCostCenter(FmsCostCenter fmsCostCenter) {
        this.fmsCostCenter = fmsCostCenter;
    }

    public FmsCostcSystemJunction getFmsCostcSystemJunction() {
        if (fmsCostcSystemJunction == null) {
            fmsCostcSystemJunction = new FmsCostcSystemJunction();
        }
        return fmsCostcSystemJunction;
    }

    public void setFmsCostcSystemJunction(FmsCostcSystemJunction fmsCostcSystemJunction) {
        this.fmsCostcSystemJunction = fmsCostcSystemJunction;
    }

    public FmsSystemJobJunction getFmsSystemJobJunction() {
        if (fmsSystemJobJunction == null) {
            fmsSystemJobJunction = new FmsSystemJobJunction();
        }
        return fmsSystemJobJunction;
    }

    public void setFmsSystemJobJunction(FmsSystemJobJunction fmsSystemJobJunction) {
        this.fmsSystemJobJunction = fmsSystemJobJunction;
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

    public List<FmsCostcSystemJunction> getSsCcJunctionList() {
        if (ssCcJunctionList == null) {
            ssCcJunctionList = new ArrayList<>();
        }
        return ssCcJunctionList;
    }

    public void setSsCcJunctionList(List<FmsCostcSystemJunction> ssCcJunctionList) {
        this.ssCcJunctionList = ssCcJunctionList;
    }

    public List<FmsSystemJobJunction> getSysJobNOList() {
        if (sysJobNOList == null) {
            sysJobNOList = new ArrayList<>();
        }
        return sysJobNOList;
    }

    public void setSysJobNOList(List<FmsSystemJobJunction> sysJobNOList) {
        this.sysJobNOList = sysJobNOList;
    }

    public List<FmsGeneralLedger> getGlList() {
        if (glList == null) {
            glList = new ArrayList<>();
        }
        return glList;
    }

    public void setGlList(List<FmsGeneralLedger> glList) {
        this.glList = glList;
    }

    public List<FmsSubsidiaryLedger> getSlList() {
        if (slList == null) {
            slList = new ArrayList<>();
        }
        return slList;
    }

    public void setSlList(List<FmsSubsidiaryLedger> slList) {
        this.slList = slList;
    }

    public List<FmsLuSystem> getSystemList() {
        if (systemList == null) {
            systemList = new ArrayList<>();
        }
        systemList = fmsLuSystemBeanLocal.findAll();
        return systemList;
    }

    public void setSystemList(List<FmsLuSystem> systemList) {
        this.systemList = systemList;
    }

    public String getDisplay_conn() {
        return display_conn;
    }

    public void setDisplay_conn(String display_conn) {
        this.display_conn = display_conn;
    }

    public boolean isRenderJobNo() {
        return renderJobNo;
    }

    public void setRenderJobNo(boolean renderJobNo) {
        this.renderJobNo = renderJobNo;
    }

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isRenderPnlCreateAcc() {
        return renderPnlCreateAcc;
    }

    public void setRenderPnlCreateAcc(boolean renderPnlCreateAcc) {
        this.renderPnlCreateAcc = renderPnlCreateAcc;
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

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public Set<String> getCheckSubsidiaryCode() {
        return checkSubsidiaryCode;
    }

    public void setCheckSubsidiaryCode(Set<String> checkSubsidiaryCode) {
        this.checkSubsidiaryCode = checkSubsidiaryCode;
    }

    public Set<String> getCheckAccountNo() {
        return checkAccountNo;
    }

    public void setCheckAccountNo(Set<String> checkAccountNo) {
        this.checkAccountNo = checkAccountNo;
    }

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public FmsBankAccount getFmsBankAccount() {
        if (fmsBankAccount == null) {
            fmsBankAccount = new FmsBankAccount();
        }
        return fmsBankAccount;
    }

    public void setFmsBankAccount(FmsBankAccount fmsBankAccount) {
        this.fmsBankAccount = fmsBankAccount;
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

    public FmsBankCashDeposit getFmsBankCashDeposit() {
        return fmsBankCashDeposit;
    }

    public void setFmsBankCashDeposit(FmsBankCashDeposit fmsBankCashDeposit) {
        this.fmsBankCashDeposit = fmsBankCashDeposit;
    }

    public String getKonk() {
        return Konk;
    }

    public void setKonk(String Konk) {
        this.Konk = Konk;
    }

    public DataModel<FmsBankAccount> getBankAccountDataModel() {
        if (bankAccountDataModel == null) {
            bankAccountDataModel = new ListDataModel();
        }
        return bankAccountDataModel;
    }

    public void setBankAccountDataModel(DataModel<FmsBankAccount> bankAccountDataModel) {
        this.bankAccountDataModel = bankAccountDataModel;
    }

    public List<FmsBankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<FmsBankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public DataModel<FmsBankBranchAccounts> getFmsBankBranchAccountsModel() {
        if (fmsBankBranchAccountsModel == null) {
            fmsBankBranchAccountsModel = new ListDataModel();
        }
        return fmsBankBranchAccountsModel;
    }

    public void setFmsBankBranchAccountsModel(DataModel<FmsBankBranchAccounts> fmsBankBranchAccountsModel) {
        this.fmsBankBranchAccountsModel = fmsBankBranchAccountsModel;
    }

    public FmsBankBranchAccounts getFmsBankBranchAccounts() {
        if (fmsBankBranchAccounts == null) {
            fmsBankBranchAccounts = new FmsBankBranchAccounts();
        }
        return fmsBankBranchAccounts;
    }

    public void setFmsBankBranchAccounts(FmsBankBranchAccounts fmsBankBranchAccounts) {
        this.fmsBankBranchAccounts = fmsBankBranchAccounts;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getForAddOrModify() {
        return forAddOrModify;
    }

    public void setForAddOrModify(String forAddOrModify) {
        this.forAddOrModify = forAddOrModify;
    }

    public String getConcatination() {
        return concatination;
    }

    public void setConcatination(String concatination) {
        this.concatination = concatination;
    }

    public List<FmsBank> getBankNameList() {
        if (bankNameList == null) {
            bankNameList = new ArrayList<>();
        }
        bankNameList = bankBeanLocal.getBankName();
        return bankNameList;
    }

    public void setBankNameList(List<FmsBank> bankNameList) {
        this.bankNameList = bankNameList;
    }

    public List<FmsBankAccount> getBranchNames() {
        return branchNames;
    }

    public void setBranchNames(List<FmsBankAccount> branchNames) {
        this.branchNames = branchNames;
    }

    public FmsBankAccount getSelectedBankAcc() {
        return selectedBankAcc;
    }

    public void setSelectedBankAcc(FmsBankAccount selectedBankAcc) {
        this.selectedBankAcc = selectedBankAcc;
    }

    public FmsBankBranchAccounts getSelectedAccDetail() {
        return selectedAccDetail;
    }

    public void setSelectedAccDetail(FmsBankBranchAccounts selectedAccDetail) {
        this.selectedAccDetail = selectedAccDetail;
    }

    public List<FmsSubsidiaryLedger> getAcctCodeList() {
        return acctCodeList;
    }

    public void setAcctCodeList(List<FmsSubsidiaryLedger> acctCodeList) {
        this.acctCodeList = acctCodeList;
    }

    public subsidiaryBeanLocal getSubsidiaryBeanLocal() {
        return subsidiaryBeanLocal;
    }

    public void setSubsidiaryBeanLocal(subsidiaryBeanLocal subsidiaryBeanLocal) {
        this.subsidiaryBeanLocal = subsidiaryBeanLocal;
    }

    public FmsSubsidiaryLedger getFmsSubsid1aryLedger1() {
        if (fmsSubsid1aryLedger1 == null) {
            fmsSubsid1aryLedger1 = new FmsSubsidiaryLedger();
        }
        return fmsSubsid1aryLedger1;
    }

    public void setFmsSubsid1aryLedger1(FmsSubsidiaryLedger fmsSubsid1aryLedger1) {
        this.fmsSubsid1aryLedger1 = fmsSubsid1aryLedger1;
    }

    public List<FmsBankBranchAccounts> getListsofaccount() {
        if (listsofaccount == null) {
            listsofaccount = new ArrayList<>();
        }
        listsofaccount = branchAccountsBeanLocal.getBankAccountNo();
        return listsofaccount;
    }

    public void setListsofaccount(List<FmsBankBranchAccounts> listsofaccount) {
        this.listsofaccount = listsofaccount;
    }

    public List<FmsBankAccount> getBranchName() {
        return bankAccBeanLocal.getBranchName();
    }

    public List<FmsBankAccount> getBankNameFromBankAccount() {

        return bankAccBeanLocal.getBankID();
    }
//</editor-fold>

    //method for concatination of fmsBankAccount.getFmsBankBranchAccountsList,getSubsidiaryId,getSubsidiaryCode
    public void concatenate2() {
        int size = fmsBankAccount.getFmsBankBranchAccountsList().size();
        for (int i = 0; i < size; i++) {
            String accountCode[] = fmsBankAccount.getFmsBankBranchAccountsList().get(i).getSubsidiaryId().getSubsidiaryCode().split("-");
            fmsLuSystem.setSystemId(Integer.parseInt(accountCode[0]));
            fmsLuSystem = fmsLuSystemBeanLocal.getSystembyId(fmsLuSystem);
            String SL_display = "SL";
            fmsGeneralLedger.setGeneralLedgerId(Integer.parseInt(accountCode[2]));
            fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getByGlId(fmsGeneralLedger);
            if (!"SL".equals(accountCode[3])) {
                fmsSubsid1aryLedger1.setSubsidiaryId(Integer.parseInt(accountCode[3]));
                fmsSubsid1aryLedger1 = subLedgerBeanLocal.getSubsidiaryInfo(fmsSubsid1aryLedger1);
                SL_display = fmsSubsid1aryLedger1.getSubsidiaryCode();
            }
            fmsBankAccount.getFmsBankBranchAccountsList().get(i).setDisplay_conn(display_conn);
        }
    }

    //method to concatinate fmsLuSystem.getSystemCode(), CC ,fmsSubsid1aryLedger1.getGeneralLedgerId().getGeneralLedgerCode()and SL
    public void Concatenate() {
        String CC = "CC";
        String SL = "SL";
        String CC_display = "CC";
        String SL_display = "SL";
        if (fmsSubsid1aryLedger1.getSubsidiaryCode() != null) {
            SL = fmsSubsid1aryLedger1.getSubsidiaryCode();
            SL_display = fmsSubsid1aryLedger1.getSubsidiaryId().toString();

        }
        display_conn = fmsLuSystem.getSystemCode() + "-" + CC + "-" + fmsSubsid1aryLedger1.getGeneralLedgerId().getGeneralLedgerCode() + "-" + SL;
        concatination = fmsLuSystem.getSystemId() + "-" + CC_display + "-" + fmsSubsid1aryLedger1.getGeneralLedgerId().getGeneralLedgerId() + "-" + SL_display;

    }

    //select event for populating raw data for the selected obeject
    public void populate(SelectEvent event) {
        fmsBankAccount = (FmsBankAccount) event.getObject();
        fmsBank = fmsBankAccount.getBankId();
        clearPopup();
        renderPnlCreateAcc = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updateStatus = 1;
        recreateAccountDataModel();
    }

    //select event for populating raw data for the selected obeject and change status to modify
    public void populateAccDetail(SelectEvent event) {
        try {
            addUpdateStatus = 1;
            forAddOrModify = "Modify";
            fmsBankBranchAccounts = (FmsBankBranchAccounts) event.getObject();
            splitAccountCode();
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Failed to populate! Try reloading the page.");
        }
    }

    /*method to split fmsBankBranchAccounts.getSubsidiaryId().getSubsidiaryCode() in to fmsLuSystem,
     fmsCostCenter,fmsGeneralLedger, fmsSubsidiaryLedger*/
    public void splitAccountCode() {
        String accountCode[] = fmsBankBranchAccounts.getSubsidiaryId().getSubsidiaryCode().split("-");
        String ss = accountCode[0];
        String cc = accountCode[1];
        String gl = accountCode[2];
        String sl = accountCode[3];
        fmsLuSystem.setSystemCode(ss);
        fmsCostCenter.setSystemName(cc);
        fmsGeneralLedger.setGeneralLedgerCode(gl);
        fmsSubsidiaryLedger.setSubsidiaryCode(sl);
        fmsLuSystem = fmsLuSystemBeanLocal.findBySytemCode2(fmsLuSystem);
        ssCcJunctionList = fmsLuSystem.getFmsCostcSystemJunctionList();
        if (fmsLuSystem.getType().toString().equals(projectType)) {
            searchProjectType();
            renderJobNo = true;
            sysJobNOList = fmsLuSystem.getFmsSystemJobJunctionList();
        } else {
            searchNonProjectType();
        }
        fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findBySystemCodeAndCostCenterCode(fmsLuSystem, fmsCostCenter);
        fmsCostCenter = fmsCostcSystemJunction.getFmsCostCenter();
        fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getGlCode(fmsGeneralLedger);
        fmsSubsidiaryLedger = fmsBankBranchAccounts.getSubsidiaryId();
        slList = new ArrayList<>();
        slList.add(fmsSubsidiaryLedger);
        fmsSubsidiaryLedger = subsidiaryLedgerBeanLocal.getSlCode(fmsSubsidiaryLedger);
    }

    //recreate method to assign FmsBankBranchAccountsList value to fmsBankBranchAccountsModel
    private void recreateAccountDataModel() {
        fmsBankBranchAccountsModel = null;
        fmsBankBranchAccountsModel = new ListDataModel(new ArrayList<>(fmsBankAccount.getFmsBankBranchAccountsList()));
    }

    //recreate method to assign bankAccounts list value to bankAccountDataModel
    private void recreateBankAccountDataModel() {
        bankAccountDataModel = null;
        bankAccountDataModel = new ListDataModel(new ArrayList(bankAccounts));
    }

    //update nethod to set dubsidery ledger id
    public void updateAccountDetail() {
        fmsBankBranchAccounts = getFmsBankBranchAccountsModel().getRowData();
        fmsBankBranchAccounts.setSubsidiaryId(getFmsBankBranchAccountsModel().getRowData().getSubsidiaryId());
    }

    //method to fetch raw data to fmsBankBranchAccounts
    public void deleteAccountDetail() {
        fmsBankBranchAccounts = getFmsBankBranchAccountsModel().getRowData();
    }

    //select items to find all from fmsGeneralLedgerBeanLocal
    public SelectItem[] getGLdata() {
        return JsfUtil.getSelectItems(fmsGeneralLedgerBeanLocal.findAll(), true);
    }

    //find all from subsidiaryBeanLocal
    public SelectItem[] findSubs() {
        return JsfUtil.getSelectItems(subsidiaryBeanLocal.findAll(), true);
    }

    //select items to find all from fmsLuSystemBeanLocal
    public SelectItem[] getListSys() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findAll(), true);
    }

    //select items to find all from subLedgerBeanLocal
    public SelectItem[] getSubList() {
        return JsfUtil.getSelectItems(subLedgerBeanLocal.findAll(), true);
    }

    //select items to find CostCenter from fmsCostCenterBeanLocal
    public SelectItem[] getCostCenterBySystemLU() {

        fmsLuSystem = fmsCostCenter.getSystemId();
        if (fmsLuSystem != null && fmsSubsid1aryLedger1 != null) {
            return JsfUtil.getSelectItems(fmsCostCenterBeanLocal.findCostCenter(fmsLuSystem), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--- Select One ---");
            return items;
        }
    }

    //select items to find list of subsidery ledger from subLedgerBeanLocal
    public SelectItem[] findListSubL() {
        ArrayList<FmsSubsidiaryLedger> subsidaryList = new ArrayList<>();
        if (fmsGeneralLedger != null) {
            SelectItem[] listSl = null;
            subsidaryList = subLedgerBeanLocal.findSubLedger(fmsGeneralLedger);
            if (subsidaryList.size() > 0) {
                listSl = new SelectItem[subsidaryList.size() + 1];
                listSl[0] = new SelectItem(null, "--- Select One ---");
                for (int i = 0; i < subsidaryList.size(); i++) {
                    listSl[i + 1] = new SelectItem(subsidaryList.get(i).getSubsidiaryId(), subsidaryList.get(i).getSubsidiaryCode());
                }
            }
            return listSl;
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--- Select One ---");
            return items;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="select and value change event">
    //select event for lusystem to get BankAccInfo from bankAccBeanLocal
    public void getBankAccInfo(SelectEvent event) {
        fmsBankAccount = bankAccBeanLocal.getBankAccInfo(fmsBankAccount);
        recreateAccountDataModel();
        updateStatus = 1;
        saveorUpdateBundle = "Update";
    }

    //value change event for lusystem to get SysDetail from fmsLuSystemBeanLocal
    public void SystemChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuSystem.setSystemCode(event.getNewValue().toString());
            fmsLuSystem = fmsLuSystemBeanLocal.getSysDetail(fmsLuSystem);
        }
    }

    //value change event for lusystem to get CostDetail from fmsCostCenterBeanLocal
    public void CostCenterChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            fmsCostCenter.setSystemName(event.getNewValue().toString());
            fmsCostCenter = fmsCostCenterBeanLocal.getCostDetail(fmsCostCenter);
        }
    }

    //value change event for lusystem to get GLDetail from fmsGeneralLedgerBeanLocal
    public void getGeneralLedgerChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsGeneralLedger.setGeneralLedgerCode(event.getNewValue().toString());
            fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getGLDetail(fmsGeneralLedger);
            fmsSubsid1aryLedger1.setGeneralLedgerId(fmsGeneralLedger);

        }
    }

    //value change event for lusystem to get SubsidiaryInfo from subLedgerBeanLocal
    public void getSubsidiaryLChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsSubsid1aryLedger1.setSubsidiaryId(Integer.parseInt(event.getNewValue().toString()));
            fmsSubsid1aryLedger1 = subLedgerBeanLocal.getSubsidiaryInfo(fmsSubsid1aryLedger1);
        }
    }

    //value change event for lusystem to get SubsidiaryInfo from subLedgerBeanLocal
    public void getBankAccDetailInfo(ValueChangeEvent event) {
        fmsBankAccount.setBankId(fmsBankAccount.getBankId());
        fmsBankAccount.setBranchName(fmsBankAccount.getBranchName());
        fmsBankAccount = bankAccBeanLocal.getinfoByBranchName(fmsBankAccount);
        fmsBankAccount.setLocation(fmsBankAccount.getLocation());
        fmsBankAccount.setAccCreatedDate(fmsBankAccount.getAccCreatedDate());
        clearPopup();
        recreateAccountDataModel();
        updateStatus = 1;
        saveorUpdateBundle = "Update";
    }

    //value change event for lusystem to get BranchNameById from bankAccBeanLocal
    public List<FmsBankAccount> getBankBranchFromBankAccount(ValueChangeEvent event) {

        fmsBankAccount.setBranchName(null);
        fmsBankAccount.setBankId(fmsBankAccount.getBankId());
        branchNames = bankAccBeanLocal.getBranchNameById(fmsBankAccount);
        return bankAccBeanLocal.getBranchNameById(fmsBankAccount);
    }

    //value change event to get the new value for changing System
    public void onChangeSystem(ValueChangeEvent event) {
        try {
            if (event.getNewValue() != null) {
                fmsLuSystem = (FmsLuSystem) event.getNewValue();
                renderJobNo = false;
                if (fmsLuSystem.getType().toString().equals(projectType)) {
                    searchProjectType();
                    renderJobNo = true;
                    sysJobNOList = fmsLuSystem.getFmsSystemJobJunctionList();
                } else {
                    searchNonProjectType();
                }
                ssCcJunctionList = fmsLuSystem.getFmsCostcSystemJunctionList();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    //value change event to get the new value for changing CostCenter
    public void onChangeCostCenter(ValueChangeEvent event) {
        try {
            if (event.getNewValue() != null) {
                fmsCostcSystemJunction = (FmsCostcSystemJunction) event.getNewValue();
                fmsCostCenter = fmsCostcSystemJunction.getFmsCostCenter();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    //value change event to get the new value for changing GeneralLedger
    public void onChangeGeneralLedger(ValueChangeEvent event) {
        try {
            if (!event.getNewValue().toString().isEmpty()) {
                fmsGeneralLedger.setGeneralLedgerCode(event.getNewValue().toString());
                fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getGLDetail(fmsGeneralLedger);
                fmsGeneralLedger = (FmsGeneralLedger) event.getNewValue();
                slList = subsidiaryLedgerBeanLocal.findBySsCcJuncAndGL(fmsCostcSystemJunction, fmsGeneralLedger);
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    //value change event to get the new value for changing SubsidiaryLedger
    public void onChangeSubsidiaryLedger(ValueChangeEvent event) {
        try {
            if (!event.getNewValue().toString().isEmpty()) {
                fmsSubsidiaryLedger = (FmsSubsidiaryLedger) event.getNewValue();
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    //value change event to get the new value for changing job number
    public void onChangeJobNo(ValueChangeEvent event) {
        try {
            if (event.getNewValue() != null) {
                fmsSystemJobJunction = (FmsSystemJobJunction) event.getNewValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    //value change event for account number
    public FmsBankAccount getAccountNumber(ValueChangeEvent event) {
        fmsBankAccount.setBranchName(fmsBankAccount.getBranchName());
        fmsBankAccount.setBankId(fmsBankAccount.getBankId());
        fmsBankAccount = bankAccBeanLocal.getAccountNumberByBranchName(fmsBankAccount);
        return fmsBankAccount;
    }
//</editor-fold>
    //method to add to fmsBankAccount,checkSubsidiaryCode,checkAccountNo,fmsBankBranchAccounts

    public String addAccountInfoDetail() {
        boolean isDupplicated = branchAccountsBeanLocal.findDup(fmsBankBranchAccounts);
        if (addUpdateStatus == 0 && isDupplicated) {
            JsfUtil.addFatalMessage("Account Number or Account Code is duplicated");
        } else {
            try {
                fmsBankBranchAccounts.setSubsidiaryId(fmsSubsidiaryLedger);
                if (checkSubsidiaryCode.contains(fmsBankBranchAccounts.getSubsidiaryId().getSubsidiaryCode()) || checkAccountNo.contains(fmsBankBranchAccounts.getAccountNumber())) {
                    JsfUtil.addFatalMessage("Account Number or Account Code is duplicated");
                } else {
                    fmsBankAccount.addAccountInfoDetail(fmsBankBranchAccounts);
                    checkSubsidiaryCode.add(fmsBankBranchAccounts.getSubsidiaryId().getSubsidiaryCode());
                    checkAccountNo.add(fmsBankBranchAccounts.getAccountNumber());
                    recreateAccountDataModel();
                    clearPopup();
                }

            } catch (Exception e) {
            }
        }
        return null;
    }

    //save bank account method
    public String saveBankAccount() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(SessionBean.getUserName(), "saveBankAccount", dataset)) {
            fmsBankAccount.setBankId(fmsBank);
            if (updateStatus == 0) {
                try {
                    if ((!(getFmsBankBranchAccountsModel().getRowCount() > 0))) {
                        JsfUtil.addFatalMessage("Account Detail Data Table Shoud be Filled");
                        return null;
                    }

                    bankAccBeanLocal.create(fmsBankAccount);
                    JsfUtil.addSuccessMessage("Saved Successfully!");
                    return clearPage();
                } catch (Exception e) {
                    JsfUtil.addFatalMessage("Duplicate account No.  Try with Unique Account No.");
                    return null;
                }
            } else {
                try {
                    if ((!(getFmsBankBranchAccountsModel().getRowCount() > 0))) {
                        JsfUtil.addFatalMessage("Data table shoud be filled");
                        return null;
                    }
                    bankAccBeanLocal.edit(fmsBankAccount);
                    JsfUtil.addSuccessMessage("Updated Successfully!");
                    return clearPage();
                } catch (Exception e) {
                    JsfUtil.addFatalMessage("Unable to Update. Try again.");
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

    //search method for bank acc using BankId,BranchName, fmsBankAccount and fmsBankBranchAccounts
    public void searchBankAcc() {
        try {
            bankAccounts = new ArrayList<>();
            if ((fmsBankAccount.getBankId() == null) && (fmsBankAccount.getBranchName().isEmpty()) && (fmsBankBranchAccounts == null)) {  //find all
                bankAccounts = bankAccBeanLocal.searchAllBankAcc();
                recreateBankAccountDataModel();
            } else if ((fmsBankAccount.getBankId() == null) && (fmsBankAccount.getBranchName().isEmpty()) && (fmsBankBranchAccounts != null)) {
                bankAccounts = bankAccBeanLocal.searchBankAcctByAccNumber(fmsBankBranchAccounts);//find by account no only
                recreateBankAccountDataModel();
            } else if (!(fmsBankAccount.getBankId() == null) && (fmsBankAccount.getBranchName().isEmpty())) {
                fmsBankAccount.setBankId(fmsBankAccount.getBankId());
                bankAccounts = bankAccBeanLocal.searchBankAccByBankName(fmsBankAccount);
                recreateBankAccountDataModel();
            } else if (!(fmsBankAccount.getBankId() == null) && !(fmsBankAccount.getBranchName().isEmpty())) {
                fmsBankAccount.setBankId(fmsBankAccount.getBankId());
                fmsBankAccount.setBranchName(fmsBankAccount.getBranchName());
                bankAccounts = bankAccBeanLocal.searchByBankAndBranchName(fmsBankAccount);
                recreateBankAccountDataModel();
            } else {
                JsfUtil.addFatalMessage("Unable to search! Try Again Reloading the Page.");
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to search! Try Again Reloading the Page.");
        }
    }

    //search for bank name
    public List<FmsBank> getBankNameList1() {
        return bankBeanLocal.getBankName();
    }

    //search for all
    public List<FmsSubsidiaryLedger> acctCodeListBySubsId() {
        acctCodeList = subsidiaryBeanLocal.findAll();
        return acctCodeList;
    }

    //search BankAcctByAccNumber from branchAccountsBeanLocal returning bank accountes
    public ArrayList<FmsBankBranchAccounts> searchBankAcctByAccNumber(String accountNumber) {
        ArrayList<FmsBankBranchAccounts> bankAccounts = null;
        fmsBankBranchAccounts.setAccountNumber(accountNumber);
        bankAccounts = branchAccountsBeanLocal.searchBankAcctByAccNumber(fmsBankBranchAccounts);
        return bankAccounts;
    }

    //select AccountType from fmsGeneralLedgerBeanLocal
    public void searchProjectType() {
        glList = fmsGeneralLedgerBeanLocal.getByAccountType(projectType);
    }

    //search for account type from fmsGeneralLedgerBeanLocal
    public void searchNonProjectType() {
        glList = fmsGeneralLedgerBeanLocal.getByAccountType(nonProjectType);
    }

    // render assign for raw edit
    public void onRowEditAdd() {
        renderPnlCreateAcc = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        headerTitle = "Bank Account Registration";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updateStatus = 1;
    }

    //clear method
    private String clearPopup() {
        fmsBankBranchAccounts = new FmsBankBranchAccounts();
        clearAccCodes();
        return null;
    }

    //clear acc code
    public void clearAccCodes() {
        fmsLuSystem = new FmsLuSystem();
        fmsGeneralLedger = new FmsGeneralLedger();
        fmsCostCenter = new FmsCostCenter();
        fmsSystemJobJunction = new FmsSystemJobJunction();
        fmsCostcSystemJunction = new FmsCostcSystemJunction();
        fmsSubsidiaryLedger = new FmsSubsidiaryLedger();
    }

    //clear page
    public String clearPage() {
        fmsBankAccount = new FmsBankAccount();
        fmsBankBranchAccounts = new FmsBankBranchAccounts();
        fmsBankBranchAccountsModel = null;
        bankNameList = null;
        fmsBank = new FmsBank();
        selectedBankAcc = null;
        bankAccountDataModel = null;
        clearAccCodes();
        updateStatus = 0;
        saveorUpdateBundle = "Save";
        return null;
    }

    //create and search render method
    public void createNewAccount() {
        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAcc = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            headerTitle = "Bank Account Registration";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAcc = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            headerTitle = "Search Bank Account";
            setIcone("ui-icon-plus");
        }
    }
}
