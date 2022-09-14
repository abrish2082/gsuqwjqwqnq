/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.employee;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.employee.HrEmpContractsBeanLocal;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmpContracts;
import et.gov.eep.hrms.entity.employee.HrEmployees;
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

/**
 *
 * @author user
 */
@Named("hrEmployeeContratController")
@ViewScoped
public class HrEmpContractsController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity Injection">
    @Inject
            HrEmployees hrEmployees;
    @Inject
            SessionBean SessionBean;
    @Inject
            HrEmpContracts hrEmpContracts;
    @Inject
            ColumnNameResolver columnNameResolver;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="EJB declaration">
    @EJB
    private HrEmployeeBeanLocal hrEmployeeBeanLocal;
    @EJB
    private HrEmpContractsBeanLocal hrEmpContractsBeanLocal;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Datamodel initialization">
    DataModel<HrEmployees> employeesListDataModel;
    DataModel<HrEmpContracts> hrEmpContractsDataModel;
    List<ColumnNameResolver> ColumnNameResolverList = new ArrayList<>();
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="variable initialization">
    private String employeeId;
    private String toDate = "";
    private HrEmployees selectedEmp;
    private String saveOrUpdate = "Save";
    private boolean btnNewRender = false;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    public List<ColumnNameResolver> getColumnNameResolverList() {
        ColumnNameResolverList = hrEmployeeBeanLocal.findColumns();
        if (ColumnNameResolverList == null) {
            ColumnNameResolverList = new ArrayList<>();
        }
        return ColumnNameResolverList;
    }

    public void setColumnNameResolverList(List<ColumnNameResolver> ColumnNameResolverList) {
        this.ColumnNameResolverList = ColumnNameResolverList;
    }

    public String getSaveOrUpdate() {
        return saveOrUpdate;
    }

    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }

    public DataModel<HrEmployees> getEmployeesListDataModel() {
        return employeesListDataModel;
    }

    public void setEmployeesListDataModel(DataModel<HrEmployees> employeesListDataModel) {
        this.employeesListDataModel = employeesListDataModel;
    }

    public HrEmployees getHrEmployees() {
        if (hrEmployees == null) {
            hrEmployees = new HrEmployees();
        }
        return hrEmployees;
    }

    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
    }

    public HrEmpContracts getHrEmpContracts() {

        if (hrEmpContracts == null) {
            hrEmpContracts = new HrEmpContracts();

        }
        return hrEmpContracts;
    }

    public void setHrEmpContracts(HrEmpContracts hrEmpContracts) {
        this.hrEmpContracts = hrEmpContracts;
    }

    public DataModel<HrEmpContracts> getHrEmpContractsDataModel() {
        if (hrEmpContractsDataModel == null) {
            hrEmpContractsDataModel = new ListDataModel<>();
        }
        return hrEmpContractsDataModel;
    }

    public void setHrEmpContractsDataModel(DataModel<HrEmpContracts> hrEmpContractsDataModel) {
        this.hrEmpContractsDataModel = hrEmpContractsDataModel;
    }

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public HrEmployees getSelectedEmp() {
        return selectedEmp;
    }

    public void setSelectedEmp(HrEmployees selectedEmp) {
        this.selectedEmp = selectedEmp;
    }

    public void recreateEmpContrat() {
        hrEmpContractsDataModel = null;
        hrEmpContractsDataModel = new ListDataModel(new ArrayList(hrEmployees.getHrEmpContractsList()));
    }

    public void recreateEmpContractAfter() {
        hrEmpContractsDataModel = null;
        hrEmpContractsDataModel = new ListDataModel(new ArrayList(hrEmpContractsBeanLocal.searchbyempforarray(hrEmpContracts)));
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="search">
    private String createOrSearchBundle = "New";
    private String headerTitle = "Search Cotract";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private boolean renderPnlcont = false;

    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public boolean isRenderPnlCreateGatePass() {
        return renderPnlCreateGatePass;
    }

    public void setRenderPnlCreateGatePass(boolean renderPnlCreateGatePass) {
        this.renderPnlCreateGatePass = renderPnlCreateGatePass;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isRenderPnlcont() {
        return renderPnlcont;
    }

    public void setRenderPnlcont(boolean renderPnlcont) {
        this.renderPnlcont = renderPnlcont;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }
//</editor-fold>    

    public void createNewGatePassInfo() {
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateGatePass = true;
            renderPnlManPage = false;
            renderPnlcont = true;
            createOrSearchBundle = "Search";
            headerTitle = "Employee contrat";
            setIcone("ui-icon-search");
            btnNewRender = false;

        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGatePass = false;
            renderPnlManPage = true;
            renderPnlcont = false;
            createOrSearchBundle = "New";
            headerTitle = "Search Employee ";
            setIcone("ui-icon-plus");
            hrEmployees = null;
            hrEmpContractsDataModel = null;
            btnNewRender = false;

        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="main">  
    public void newButtonAction() {
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        renderPnlcont = true;
        createOrSearchBundle = "Search";
        headerTitle = "Employee contrat";
        setIcone("ui-icon-search");
        btnNewRender = false;
    }

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            columnNameResolver.setCol_Name_FromTable(event.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(event.getNewValue().toString().toLowerCase() + ":"));
            //hrJobTypes.setCol_Value(columnNameResolver.getParsed_Col_Name());
        }
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }

    public void saveContractRenewal() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveContractRenewal", dataset)) {
                try {
                    String Shday[] = hrEmpContracts.getFromDate().split("/");
                    int Ihday = Integer.parseInt(Shday[0]);
                    String Shmonth[] = hrEmpContracts.getFromDate().split("/");
                    int Ihmonth = Integer.parseInt(Shmonth[1]);
                    String Shyear[] = hrEmpContracts.getFromDate().split("/");
                    int Ihyear = Integer.parseInt(Shyear[2]);
                    String Scday[] = hrEmpContracts.getToDate().split("/");
                    int Icday = Integer.parseInt(Scday[0]);
                    String Scmonth[] = hrEmpContracts.getToDate().split("/");
                    int Icmonth = Integer.parseInt(Scmonth[1]);
                    String Scyear[] = hrEmpContracts.getToDate().split("/");
                    int Icyear = Integer.parseInt(Scyear[2]);
                    int expday = (Icday - Ihday) + ((Icmonth - Ihmonth) * 30) + ((Icyear - Ihyear) * 365);
                    if (expday > 1) {
                        if (saveOrUpdate.equalsIgnoreCase("Save")) {
                            hrEmpContracts.setEmpId(hrEmployees);
                            hrEmpContracts.setStatus("Active");
                            getHrEmployees().addToEmpContrat(hrEmpContracts);
                            hrEmpContractsBeanLocal.updatestate(hrEmployees);
                            hrEmpContractsBeanLocal.create(hrEmpContracts);
                            recreateEmpContractAfter();
                            JsfUtil.addSuccessMessage("Employee Contract Successfuly Saved.");
                            if (hrEmpContracts.getToDate() != null) {
                                toDate = hrEmpContracts.getToDate();
                            }
                            hrEmpContracts = null;
                            if (toDate != null) {
                                hrEmpContracts = new HrEmpContracts();
                                hrEmpContracts.setFromDate(toDate);
                            }
                        } else if (saveOrUpdate.equalsIgnoreCase("Update")) {
                            hrEmpContractsBeanLocal.edit(hrEmpContracts);
                            JsfUtil.addSuccessMessage("Employee Contract Successfuly updated.");
                            if (hrEmpContracts.getToDate() != null) {
                                toDate = hrEmpContracts.getToDate();
                            }
                            hrEmpContracts = null;
                            if (toDate != null) {
                                hrEmpContracts = new HrEmpContracts();
                                hrEmpContracts.setFromDate(toDate);
                            }
                            saveOrUpdate = "Save";
                        }
                    } else {
                        JsfUtil.addFatalMessage("Contract End Date Should be greater than Contract Start Date");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void editEmpContract() {
        saveOrUpdate = "Update";
        if (getHrEmpContractsDataModel().getRowData().getStatus().equalsIgnoreCase("Active")) {
            hrEmpContracts = getHrEmpContractsDataModel().getRowData();
        } else {
            JsfUtil.addFatalMessage("Contract can't be renewed.");
        }
    }

    public String clear() {
        hrEmpContracts = null;
//        saveOrUpdate = "Save";
        return null;

    }

    public ArrayList<HrEmployees> findListByFname_EmpID() {
        System.out.println("columnNameResolver.getCol_Name_FromTable()=="+columnNameResolver.getCol_Name_FromTable());
        System.out.println("hrEmployees.getCol_Value()=="+hrEmployees.getCol_Value());
        if (hrEmployees.getCol_Value()==null|| columnNameResolver.getCol_Name_FromTable()==null) {
            JsfUtil.addFatalMessage("Please Fill the search paramenters properly!");
        } else  {
            employeesListDataModel = new ListDataModel(new ArrayList(hrEmployeeBeanLocal.SearchEmpContract(hrEmployees,columnNameResolver.getCol_Name_FromTable())));
//        } else if (!hrEmployees.getFirstName().isEmpty() && hrEmployees.getEmpId().isEmpty()) {
//            employeesListDataModel = new ListDataModel(new ArrayList(hrEmployeeBeanLocal.SearchByFnameCont(hrEmployees)));
//        } else {
//            JsfUtil.addFatalMessage("Enter Only Employee Id or Employee Name!");
//        }
    }
        return null;
    }

    public void selectEmployee(SelectEvent event) {
//        HrEmpContracts hrEmpContractsn = new HrEmpContracts();
        if (event.getObject() != null) {
            hrEmployees = (HrEmployees) event.getObject();
            hrEmployees = hrEmployeeBeanLocal.getByEmpId(hrEmployees);
            employeeId = hrEmployees.getEmpId();
            if (hrEmployees.getHrEmpContractsList().isEmpty()) {
                hrEmpContracts.setFromDate(hrEmployees.getContractEndDate());

            } else {
                for (int i = 0; i < hrEmployees.getHrEmpContractsList().size(); i++) {
                    if (hrEmployees.getHrEmpContractsList().get(i) != null
                            && hrEmployees.getHrEmpContractsList().get(i).getStatus() != null) {
                        if (hrEmployees.getHrEmpContractsList().get(i).getStatus().equalsIgnoreCase("Active")) {
                            hrEmpContracts.setFromDate(hrEmployees.getHrEmpContractsList().get(i).getToDate());
                        }
                    }
                }
            }
            renderPnlCreateGatePass = true;
            renderPnlManPage = false;
            renderPnlcont = true;
            createOrSearchBundle = "Search";
            headerTitle = "Employee Registration";
            setIcone("ui-icon-search");
            setIcone("ui-icon-search");
            recreateEmpContrat();
            btnNewRender = true;

        }
    }

//</editor-fold>
}
