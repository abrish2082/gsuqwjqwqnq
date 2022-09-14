/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.budgetYearLookUpBeanLocal;
import et.gov.eep.fcms.businessLogic.fmsTaxRegistrationBeanLocal;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.FmsTaxRegistration;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

/**
 *
 * @author user
 */
@Named(value = "fmsTaxRegistrationContoller")
@ViewScoped
public class fmsTaxRegistrationContoller implements Serializable {

    @EJB
    private fmsTaxRegistrationBeanLocal fmsTaxRegistrationBean;
    @EJB
    private budgetYearLookUpBeanLocal budgetYearLookUpBean;
    @EJB
    private HrEmployeeBeanLocal hrEmployeeBean;
    @Inject
    SessionBean SessionBean;
    @Inject
    FmsLuBudgetYear budgetYear;
    @Inject
    HrEmployees employee;
    @Inject
    FmsTaxRegistration taxRgistration;

    String saveUpdate = "Save";
    int updateStatus = 0;
    private boolean disableBtnCreate;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreate = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-plus";
    String rendered = "true";
    private boolean renderBtnFind = false;
    DataModel<FmsTaxRegistration> taxRegisterDataModel;
    private List<FmsTaxRegistration> TaxRegistrationList;

    public boolean isRenderBtnFind() {
        return renderBtnFind;
    }

    public void setRenderBtnFind(boolean renderBtnFind) {
        this.renderBtnFind = renderBtnFind;
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
        return renderPnlCreate;
    }

    public void setRenderPnlCreateAcc(boolean renderPnlCreate) {
        this.renderPnlCreate = renderPnlCreate;
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

    /**
     *
     * @return
     */
    public String getRendered() {
        return rendered;
    }

    /**
     *
     * @param rendered
     */
    public void setRendered(String rendered) {
        this.rendered = rendered;
    }

    /**
     *
     * @return
     */
    public DataModel<FmsTaxRegistration> getTaxRegisterDataModel() {
        if (taxRegisterDataModel == null) {
            taxRegisterDataModel = new ArrayDataModel<>();
        }

        return taxRegisterDataModel;
    }

    /**
     *
     * @param taxRegisterDataModel
     */
    public void setTaxRegisterDataModel(DataModel<FmsTaxRegistration> taxRegisterDataModel) {
        this.taxRegisterDataModel = taxRegisterDataModel;
    }

    /**
     *
     */
    public void recreateModelSrnDetailPop() {
        taxRegisterDataModel = null;
        taxRegisterDataModel = new ListDataModel(new ArrayList(TaxRegistrationList));
    }

    /**
     *
     * @return
     */
    public FmsTaxRegistration getTaxRgistration() {
        if (taxRgistration == null) {
            taxRgistration = new FmsTaxRegistration();
        }
        return taxRgistration;
    }

    /**
     *
     * @param taxRgistration
     */
    public void setTaxRgistration(FmsTaxRegistration taxRgistration) {
        this.taxRgistration = taxRgistration;
    }

    /**
     *
     * @return
     */
    public FmsLuBudgetYear getBudgetYear() {
        if (budgetYear == null) {
            budgetYear = new FmsLuBudgetYear();
        }
        return budgetYear;
    }

    /**
     *
     * @param budgetYear
     */
    public void setBudgetYear(FmsLuBudgetYear budgetYear) {
        this.budgetYear = budgetYear;
    }

    /**
     *
     * @return
     */
    public HrEmployees getEmployee() {
        if (employee == null) {
            employee = new HrEmployees();
        }
        return employee;
    }

    /**
     *
     * @param employee
     */
    public void setEmployee(HrEmployees employee) {
        this.employee = employee;
    }

    /**
     *
     * @return
     */
    public SelectItem[] getAllBudgetYearList() {
        return JsfUtil.getSelectItems(budgetYearLookUpBean.allBudgetYear(), true);
    }

    /**
     *
     * @return
     */
    public String getSaveUpdate() {
        return saveUpdate;
    }

    /**
     *
     * @param saveUpdate
     */
    public void setSaveUpdate(String saveUpdate) {
        this.saveUpdate = saveUpdate;
    }

    public List<FmsTaxRegistration> getSelectedTax() {
        return selectedTax;
    }

    public void setSelectedTax(List<FmsTaxRegistration> selectedTax) {
        this.selectedTax = selectedTax;
    }

    public void createNewTax() {
        taxRgistration = null;
        employee = null;
        taxRegisterDataModel = null;
        saveUpdate = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreate = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreate = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
    private List<FmsTaxRegistration> selectedTax;

    public void populate(SelectEvent event) {
        taxRgistration = (FmsTaxRegistration) event.getObject();
        System.out.println("inside populate 1" + taxRgistration);
        System.out.println("inside populate" + taxRgistration.getCasherId());
        employee.setFirstName(taxRgistration.getCasherId().getFirstName());
        renderPnlCreate = true;
        renderPnlManPage = false;
        saveUpdate = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updateStatus = 1;
    }

    /**
     *
     */
    public void registerTax() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "registerTax", dataset)) {

                try {
                    if (updateStatus == 0) {
                        taxRgistration.setCasherId(employee);
                        fmsTaxRegistrationBean.create(taxRgistration);
                        JsfUtil.addSuccessMessage("Registered Successfully!");
                    } else {
                        taxRgistration.setCasherId(employee);
                        fmsTaxRegistrationBean.edit(taxRgistration);
                        JsfUtil.addSuccessMessage("Updated Successfully!");
                    }
                    saveUpdate = "Save";
                    clear();
                } catch (Exception e) {
                    JsfUtil.addFatalMessage("Failed to Process. Try Again Reloading the Page");
                }

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);

            }
        } catch (Exception ex) {
        }
    }

    /**
     *
     */
    public void clear() {
        taxRgistration = null;
        employee = null;
        budgetYear = null;
        taxRegisterDataModel = null;
    }

    /**
     *
     * @param event
     */
    public void getByEmpId(SelectEvent event) {
    }

    /**
     *
     * @param fname
     * @return
     */
    public ArrayList<HrEmployees> SearchByFname(String fname) {
        ArrayList<HrEmployees> employees = null;
        employee.setFirstName(fname);
        employees = hrEmployeeBean.SearchByFname(employee);
        return employees;
    }

    /**
     *
     * @param event
     */
    public void getByFirstName(SelectEvent event) {
        employee.setFirstName(event.getObject().toString());
        employee = hrEmployeeBean.getByFirstName(employee);
    }

    /**
     *
     * @param EmpId
     * @return
     */
    public ArrayList<HrEmployees> SearchByEmpId(String EmpId) {
        ArrayList<HrEmployees> employees = null;
        employee.setEmpId(EmpId);
        employees = hrEmployeeBean.SearchByEmpId(employee);
        return employees;
    }

    /**
     *
     * @param event
     */
    public void yearValueChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            budgetYear.setBudgetYear(event.getNewValue().toString());
            budgetYear = fmsTaxRegistrationBean.getYear(budgetYear);
            taxRgistration.setYearId(budgetYear);
            renderBtnFind = true;
            TaxRegistrationList = null;
            taxRegisterDataModel = null;
        }
    }

    /**
     *
     * @return
     */
    public List<FmsTaxRegistration> getTaxRegistrationList() {
        if (TaxRegistrationList == null) {
            TaxRegistrationList = new ArrayList<>();
        }
        return TaxRegistrationList;
    }

    /**
     *
     * @param TaxRegistrationList
     */
    public void setTaxRegistrationList(List<FmsTaxRegistration> TaxRegistrationList) {
        this.TaxRegistrationList = TaxRegistrationList;
    }

    /**
     *
     * @param event
     */
    public void monthValueChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            taxRgistration.setMonth(event.getNewValue().toString());
            TaxRegistrationList = fmsTaxRegistrationBean.getMonth(taxRgistration, budgetYear);
            rendered = "true";
            recreateModelSrnDetailPop();
        }
    }

    public List<FmsTaxRegistration> findAllTaxes() {
        try {
            TaxRegistrationList = fmsTaxRegistrationBean.findAll();
            recreateModelSrnDetailPop();
            return null;
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Failed to Find, Please try again");
        }
        return null;
    }

    /**
     *
     * @return
     */
    public SelectItem[] getMonthList() {
        if (budgetYear.getLuBudgetYearId() != null) {
            return JsfUtil.getSelectItems(fmsTaxRegistrationBean.searcheMonth(budgetYear), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--- Select One ---");
            return items;
        }
    }

    /**
     *
     */
    public void updateTaxDetail() {
        taxRgistration = getTaxRegisterDataModel().getRowData();
        updateStatus = 1;
        saveUpdate = "Edit";

    }
}
