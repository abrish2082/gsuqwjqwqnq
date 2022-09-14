/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.termination;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
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
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.termination.ClearanceBeanLocal;
import et.gov.eep.hrms.businessLogic.termination.ClearanceSettingBeanLocal;
import et.gov.eep.hrms.businessLogic.termination.RetirementRequestBeanLocal;
import et.gov.eep.hrms.businessLogic.termination.TerminationRequestBeanLocal;
import et.gov.eep.hrms.businessLogic.transfer.TransferReqBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.termination.HrClearance;
import et.gov.eep.hrms.entity.termination.HrClearanceSetting;
import et.gov.eep.hrms.entity.termination.HrRetirementRequest;
import et.gov.eep.hrms.entity.termination.HrTerminationRequests;
import et.gov.eep.hrms.entity.transfer.HrTransferRequests;

/**
 *
 * @author Ob
 */
@Named(value = "clearanceController")
@ViewScoped
public class ClearanceController implements Serializable {

    @Inject
    private HrClearance hrClearance;
    @Inject
    private HrClearanceSetting hrClearanceSetting;
    @Inject
    private HrTerminationRequests terminationRequest;
    @Inject
    private HrRetirementRequest hrRetirementRequest;
    @Inject
    private HrTransferRequests hrTransferRequests;
    @Inject
    private HrEmployees hrEmployees;
    @Inject
    private HrDepartments hrDepartment;
    @Inject
    SessionBean sessionBeanLocal;

    @EJB
    ClearanceBeanLocal clearanceBeanLocal;
    @EJB
    ClearanceSettingBeanLocal clearanceSettingBeanLocal;
    @EJB
    RetirementRequestBeanLocal retirementBeanLocal;
    @EJB
    TerminationRequestBeanLocal terminationBeanLocal;
    @EJB
    TransferReqBeanLocal transferBeanLocal;
    @EJB
    HrEmployeeBeanLocal employeeBeanLocal;

    private List<HrClearanceSetting> clearanceSettingList;
    private ArrayList<HrClearance> clearanceList;
    private ArrayList<HrClearance> savedTerminationList;
    private ArrayList<HrClearance> savedRetirementList;
    private ArrayList<HrClearance> savedTransferList;
    private DataModel<HrClearance> hrClearanceModel;
    private List<HrTerminationRequests> terminationRequestList;
    private List<HrRetirementRequest> retirementRequestList;
    private List<HrTransferRequests> transferRequestsList;
    List<HrClearanceSetting> tempList = new ArrayList<>();

    String dropDownType;
    String quireyType;
    String loadType;

    public ClearanceController() {
    }

    @PostConstruct
    public void init() {
    }

    //<editor-fold defaultstate="collapsed" desc="getter setter">
    public HrClearance getHrClearance() {
        if (hrClearance == null) {
            hrClearance = new HrClearance();
        }
        return hrClearance;
    }

    public void setHrClearance(HrClearance hrClearance) {
        this.hrClearance = hrClearance;
    }

    public HrClearanceSetting getHrClearanceSetting() {
        if (hrClearanceSetting == null) {
            hrClearanceSetting = new HrClearanceSetting();
        }
        return hrClearanceSetting;
    }

    public void setHrClearanceSetting(HrClearanceSetting hrClearanceSetting) {
        this.hrClearanceSetting = hrClearanceSetting;
    }

    public HrTerminationRequests getTerminationRequest() {
        if (terminationRequest == null) {
            terminationRequest = new HrTerminationRequests();
        }
        return terminationRequest;
    }

    public void setTerminationRequest(HrTerminationRequests terminationRequest) {
        this.terminationRequest = terminationRequest;
    }

    public HrRetirementRequest getHrRetirementRequest() {
        if (hrRetirementRequest == null) {
            hrRetirementRequest = new HrRetirementRequest();
        }
        return hrRetirementRequest;
    }

    public void setHrRetirementRequest(HrRetirementRequest hrRetirementRequest) {
        this.hrRetirementRequest = hrRetirementRequest;
    }

    public HrTransferRequests getHrTransferRequests() {
        if (hrTransferRequests == null) {
            hrTransferRequests = new HrTransferRequests();
        }
        return hrTransferRequests;
    }

    public void setHrTransferRequests(HrTransferRequests hrTransferRequests) {
        this.hrTransferRequests = hrTransferRequests;
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

    public DataModel<HrClearance> getHrClearanceModel() {
        if (hrClearanceModel == null) {
            hrClearanceModel = new ListDataModel();
        }
        return hrClearanceModel;
    }

    public void setHrClearanceModel(DataModel<HrClearance> hrClearanceModel) {
        this.hrClearanceModel = hrClearanceModel;
    }

    public List<HrTerminationRequests> getTerminationRequestList() {
        terminationRequestList = clearanceBeanLocal.findApprovedTermination();
        return terminationRequestList;
    }

    public void setTerminationRequestList(List<HrTerminationRequests> terminationRequestList) {
        this.terminationRequestList = terminationRequestList;
    }

    public List<HrRetirementRequest> getRetirementRequestList() {
        retirementRequestList = clearanceBeanLocal.findApprovedRetirement();
        return retirementRequestList;
    }

    public void setRetirementRequestList(List<HrRetirementRequest> retirementRequestList) {
        this.retirementRequestList = retirementRequestList;
    }

    public List<HrTransferRequests> getTransferRequestsList() {
        transferRequestsList = clearanceBeanLocal.findApprovedTransfer();
        return transferRequestsList;
    }

    public void setTransferRequestsList(List<HrTransferRequests> transferRequestsList) {
        this.transferRequestsList = transferRequestsList;
    }

    public SelectItem[] getListOfApprovedTermination() {
        return JsfUtil.getSelectItems(clearanceBeanLocal.findApprovedTermination(), true);
    }

    public SelectItem[] getListOfApprovedRetirement() {
        return JsfUtil.getSelectItems(clearanceBeanLocal.findApprovedRetirement(), true);
    }

    public SelectItem[] getListOfApprovedTransfer() {
        return JsfUtil.getSelectItems(clearanceBeanLocal.findApprovedTransfer(), true);
    }
    //</editor-fold>

    public void loadExistingClearanceTable() {
        switch (dropDownType) {
            case "Termination":
                hrClearanceModel = null;
                hrClearanceModel = new ListDataModel(new ArrayList<>(savedTerminationList));
                tempList = clearanceBeanLocal.findClearanceSetting();
                for (int j = 0; j < tempList.size(); j++) {
                    HrClearance newClearance = new HrClearance();
                    HrDepartments hrDepObj = new HrDepartments();
                    hrDepObj = hrEmployees.getDeptId();
                    if (tempList.get(j).getDeptId().equals(hrDepObj)) {
                        newClearance.setClearanceSettingId(tempList.get(j));
                    }
                }
                hrClearanceModel = new ListDataModel(new ArrayList<>(savedTerminationList));
                break;
            case "Retirement":
                hrClearanceModel = null;
                hrClearanceModel = new ListDataModel(new ArrayList<>(savedRetirementList));
                tempList = clearanceBeanLocal.findClearanceSetting();
                for (int j = 0; j < tempList.size(); j++) {
                    HrClearance newClearance = new HrClearance();
                    HrDepartments deptObj = new HrDepartments();
                    deptObj = hrEmployees.getDeptId();
                    if (tempList.get(j).getDeptId().equals(deptObj)) {
                        newClearance.setClearanceSettingId(tempList.get(j));
                    }
                }
                hrClearanceModel = new ListDataModel(new ArrayList<>(savedRetirementList));
                break;
            case "Transfer":
                hrClearanceModel = null;
                hrClearanceModel = new ListDataModel(new ArrayList<>(savedTransferList));
                tempList = clearanceBeanLocal.findClearanceSetting();
                for (int j = 0; j < tempList.size(); j++) {
                    HrClearance newClearance = new HrClearance();
                    HrDepartments hrDepObj = new HrDepartments();
                    hrDepObj = hrEmployees.getDeptId();
                    if (tempList.get(j).getDeptId().equals(hrDepObj)) {
                        newClearance.setClearanceSettingId(tempList.get(j));
                    }
                }
                hrClearanceModel = new ListDataModel(new ArrayList<>(savedTransferList));
                break;
        }
    }

    public void loadNewClearanceTable() {
        clearanceList = new ArrayList<>();
        hrClearanceModel = null;
        clearanceSettingList = clearanceSettingBeanLocal.getSelectedDepartmentsList();
        tempList = clearanceBeanLocal.findClearanceSetting();
        int size = tempList.size();
        for (int j = 0; j < size; j++) {
            HrClearance newClearance = new HrClearance();
            HrDepartments hrDepObj = new HrDepartments();
            hrDepObj = hrEmployees.getDeptId();
            if (tempList.get(j).getDeptId().equals(hrDepObj)) {
                newClearance.setClearanceSettingId(tempList.get(j));
                clearanceList.add(newClearance);
            }
        }
        for (int i = 0; i < clearanceSettingList.size(); i++) {
            HrClearance newClearance = new HrClearance();
            newClearance.setClearanceSettingId(clearanceSettingList.get(i));
            clearanceList.add(newClearance);
            newClearance = null;
        }
        hrClearanceModel = new ListDataModel(new ArrayList<>(clearanceList));
    }

    public boolean isClearanceExist() {
        int x = 0;
        savedTerminationList = null;
        savedTerminationList = new ArrayList<>();
        savedRetirementList = null;
        savedRetirementList = new ArrayList<>();
        savedTransferList = null;
        savedTransferList = new ArrayList<>();
        switch (dropDownType) {
            case "Termination":
                savedTerminationList = clearanceBeanLocal.findTerminationeList(terminationRequest.getId(), terminationRequest.getEmpId().getId(), dropDownType);
                x = savedTerminationList.size();
                break;
            case "Retirement":
                savedRetirementList = clearanceBeanLocal.findRetirementList(hrRetirementRequest.getId(), hrRetirementRequest.getEmpId().getId(), dropDownType);
                x = savedRetirementList.size();
                break;
            case "Transfer":
                savedTransferList = clearanceBeanLocal.findTransferList(hrTransferRequests.getId(), hrTransferRequests.getRequesterId().getId(), dropDownType);
                x = savedTransferList.size();
                break;
        }
        if (x > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void handleTermination(ValueChangeEvent event) {
        try {
            hrClearance = null;
            dropDownType = null;
            dropDownType = "Termination";
            quireyType = "Termination";
            if (null != event.getNewValue()) {
                String selectedReqId[] = event.getNewValue().toString().split(" -- ");
                int id = Integer.parseInt(selectedReqId[0]);
                terminationRequest.setId(id);
                terminationRequest = terminationBeanLocal.getSelectedRequest(id);
                terminationRequest.setId(terminationRequest.getId());
                terminationRequest = terminationBeanLocal.getSelectedRequest(terminationRequest.getId());
                hrEmployees = new HrEmployees();
                hrEmployees = terminationRequest.getEmpId();
                if (isClearanceExist()) {
                    loadExistingClearanceTable();
                    loadType = "old";
                } else {
                    loadNewClearanceTable();
                    loadType = "new";
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleRetirment(ValueChangeEvent event) {
        try {
            hrClearance = null;
            dropDownType = null;
            dropDownType = "Retirement";
            quireyType = "Retirement";
            if (null != event.getNewValue().toString()) {
                String selectedReqId[] = event.getNewValue().toString().split(" -- ");
                int id = Integer.parseInt(selectedReqId[0]);
                hrRetirementRequest.setId(id);
                hrRetirementRequest = retirementBeanLocal.getSelectedRequest(id);
                hrRetirementRequest.setId(hrRetirementRequest.getId());
                hrRetirementRequest = retirementBeanLocal.getSelectedRequest(hrRetirementRequest.getId());
                hrEmployees = new HrEmployees();
                hrEmployees = hrRetirementRequest.getEmpId();
                if (isClearanceExist()) {
                    loadExistingClearanceTable();
                    loadType = "old";
                } else {
                    loadNewClearanceTable();
                    loadType = "new";
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleTransfer(ValueChangeEvent event) {
        try {
            hrClearance = null;
            dropDownType = null;
            dropDownType = "Transfer";
            quireyType = "Transfer";
            if (null != event.getNewValue().toString()) {
                String selectedReqId[] = event.getNewValue().toString().split(" -- ");
                int id = Integer.parseInt(selectedReqId[0]);
                hrTransferRequests.setId(id);
                hrTransferRequests = transferBeanLocal.getTransferInfo(id);
                hrTransferRequests.setId(hrTransferRequests.getId());
                hrTransferRequests = transferBeanLocal.getTransferInfo(hrTransferRequests.getId());
                hrEmployees = new HrEmployees();
                hrEmployees = hrTransferRequests.getRequesterId();
                if (isClearanceExist()) {
                    loadExistingClearanceTable();
                    loadType = "old";
                } else {
                    loadNewClearanceTable();
                    loadType = "new";
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    int y;
    HrClearance hrClearance2 = new HrClearance();

    public void editClearanceSetting() {
        tempList = clearanceBeanLocal.findClearanceSetting();
        int size = tempList.size();
        for (int j = 0; j < size; j++) {
            HrDepartments hrDepObj = new HrDepartments();
            hrDepObj = hrEmployees.getDeptId();
            if (tempList.get(j).getDeptId().equals(hrDepObj) && tempList.get(j).getDeptId().equals(hrClearanceSetting.getDeptId())) {
                hrClearanceSetting.setPriority(1);
                clearanceSettingBeanLocal.edit(hrClearanceSetting);
            }
        }
    }

    public void addDataTable() {
        switch (dropDownType) {
            case "Termination":
                if (savedTerminationList != null && savedTerminationList.size() > 0) {
                    savedTerminationList.add(hrClearance2);
                    loadExistingClearanceTable();
                }
                hrClearance = new HrClearance();
                break;
            case "Retirement":
                if (savedRetirementList != null && savedRetirementList.size() > 0) {
                    savedRetirementList.add(hrClearance2);
                    loadExistingClearanceTable();
                }
                hrClearance = new HrClearance();
                break;
            case "Transfer":
                if (savedTransferList != null && savedTransferList.size() > 0) {
                    savedTransferList.add(hrClearance2);
                    loadExistingClearanceTable();
                }
                hrClearance = new HrClearance();
                break;
        }
    }

    boolean disableOption = false;

    public boolean isDisableOption() {
        return disableOption;
    }

    public void setDisableOption(boolean disableOption) {
        this.disableOption = disableOption;
    }

    public void decisionOption() {
        HrClearance clear = hrClearanceModel.getRowData();
        if (clear.getClearanceSettingId().getPriority().equals(1)) {
            disableOption = false;
        } else {
            disableOption = true;
        }
    }

    String renderAdd = "true";

    public String getRenderAdd() {
        return renderAdd;
    }

    public void setRenderAdd(String renderAdd) {
        this.renderAdd = renderAdd;
    }

    public void disableDecision() {
        HrClearance clear = hrClearanceModel.getRowData();
        int size = savedRetirementList.size();
        System.out.println("......retirment size...." + size);
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                System.out.println(".......i...." + i);
                if (savedRetirementList.get(i).equals(clear)) {
                    String status = savedRetirementList.get(i - 1).getStatus();
                    System.out.println(".....status....." + status);
                    if (status.equalsIgnoreCase("Cleared")) {
                        renderAdd = "true";
                        System.out.println(".....true.........");
                    } else {
                        renderAdd = "false";
                        System.out.println(".....false.........");
                    }
                }
            } else {
                System.out.println(".......else true..........");
                renderAdd = "true";
            }
        }
        System.out.println("......end.....");
    }

    public void editClearance() {
        switch (dropDownType) {
            case "Termination":
                if (savedTerminationList.size() > 0) {
                    y = getHrClearanceModel().getRowIndex();
                    hrClearance = getHrClearanceModel().getRowData();
                    hrClearance2 = hrClearance;
                    savedTerminationList.remove(hrClearance);
                } else if (clearanceList.size() > 0) {
                    y = getHrClearanceModel().getRowIndex();
                    hrClearance = getHrClearanceModel().getRowData();
                    hrClearance2 = hrClearance;
                    savedTerminationList.remove(hrClearance);
                }
                break;
            case "Transfer":
                if (savedTransferList.size() > 0) {
                    y = getHrClearanceModel().getRowIndex();
                    hrClearance = getHrClearanceModel().getRowData();
                    hrClearance2 = hrClearance;
                    savedTransferList.remove(hrClearance);
                } else if (clearanceList.size() > 0) {
                    y = getHrClearanceModel().getRowIndex();
                    hrClearance = getHrClearanceModel().getRowData();
                    hrClearance2 = hrClearance;
                    savedTransferList.remove(hrClearance);
                }
                break;
            case "Retirement":
                if (savedRetirementList.size() > 0) {
                    y = getHrClearanceModel().getRowIndex();
                    hrClearance = getHrClearanceModel().getRowData();
                    hrClearance2 = hrClearance;
                    savedRetirementList.remove(hrClearance);
                } else if (clearanceList.size() > 0) {
                    y = getHrClearanceModel().getRowIndex();
                    hrClearance = getHrClearanceModel().getRowData();
                    hrClearance2 = hrClearance;
                    savedRetirementList.remove(hrClearance);
                }
                break;
        }
    }

    public boolean isClearanceFinished(List<HrClearance> clearCondition) {
        List<String> clrStatus = null;
        clrStatus = new ArrayList<String>();
        for (int i = 0; i < clearCondition.size(); i++) {
            hrClearance = clearCondition.get(i);
            clrStatus.add(hrClearance.getStatus());
            hrClearance = null;
        }
        if ((clrStatus.contains("UnCleared")) || (clrStatus.contains(null))) {
            return false;
        } else {
            return true;
        }
    }

    public void editEmployeeProfile() {
        hrEmployees = hrTransferRequests.getRequesterId();
        hrEmployees.setDeptId(hrTransferRequests.getTransferTo());
        hrEmployees.setJobId(hrTransferRequests.getRequesterId().getJobId());
        employeeBeanLocal.edit(hrEmployees);
    }

    //<editor-fold defaultstate="collapsed" desc="Main method">
    public void saveClearance() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveClearance", dataset)) {
                if (dropDownType != null) {
                    switch (dropDownType) {
                        case "Termination":
                            if ("old".equals(loadType)) {
                                for (int i = 0; i < savedTerminationList.size(); i++) {
                                    hrClearance = savedTerminationList.get(i);
                                    clearanceBeanLocal.edit(hrClearance);
                                    hrClearance = null;
                                    hrEmployees = new HrEmployees();
                                    hrClearanceModel = null;
                                }
                                if (isClearanceFinished(savedTerminationList)) {
                                    //for update
                                    hrEmployees.setEmpStatus(3);
                                    terminationRequest.setStatus(5);
                                    employeeBeanLocal.edit(hrEmployees);
                                    terminationBeanLocal.edit(terminationRequest);
                                }
                                JsfUtil.addSuccessMessage("Termination successfully updated");
                            } else if ("new".equals(loadType)) {
                                for (int i = 0; i < clearanceList.size(); i++) {
                                    hrClearance = clearanceList.get(i);
                                    hrClearance.setEmpId(terminationRequest.getEmpId());
                                    hrClearance.setClearanceType(dropDownType);
                                    hrClearance.setTerminationId(terminationRequest);
                                    clearanceBeanLocal.save(hrClearance);
                                    hrClearance = null;
                                    hrEmployees = new HrEmployees();
                                    hrClearanceModel = null;
                                }
                                if (isClearanceFinished(clearanceList)) {
                                    hrEmployees.setEmpStatus(3);
                                    terminationRequest.setStatus(5);
                                    employeeBeanLocal.edit(hrEmployees);
                                    terminationBeanLocal.edit(terminationRequest);
                                }
                                JsfUtil.addSuccessMessage("Termination successfully saved");
                            }
                            break;
                        case "Transfer":
                            if ("old".equals(loadType)) {
                                for (int i = 0; i < savedTransferList.size(); i++) {
                                    hrClearance = savedTransferList.get(i);
                                    clearanceBeanLocal.edit(hrClearance);
                                    hrClearance = null;
                                    hrEmployees = new HrEmployees();
                                    hrClearanceModel = null;
                                }
                                if (isClearanceFinished(savedTransferList)) {
                                    hrTransferRequests.setStatus(5);
                                    transferBeanLocal.edit(hrTransferRequests);
                                    editEmployeeProfile();
                                }
                                JsfUtil.addSuccessMessage("Transfer successfully updated");
                            } else if ("new".equals(loadType)) {
                                for (int i = 0; i < clearanceList.size(); i++) {
                                    hrClearance = clearanceList.get(i);
                                    hrClearance.setEmpId(hrTransferRequests.getRequesterId());
                                    hrClearance.setClearanceType(dropDownType);
                                    hrClearance.setTransferId(hrTransferRequests);
                                    clearanceBeanLocal.save(hrClearance);
                                    hrClearance = null;
                                    hrEmployees = new HrEmployees();
                                    hrClearanceModel = null;
                                }
                                if (isClearanceFinished(clearanceList)) {
                                    hrTransferRequests.setStatus(5);
                                    transferBeanLocal.edit(hrTransferRequests);
                                    editEmployeeProfile();
                                }
                                JsfUtil.addSuccessMessage("Transfer successfully saved");
                            }
                            break;
                        case "Retirement":
                            if ("old".equals(loadType)) {
                                for (int i = 0; i < savedRetirementList.size(); i++) {
                                    hrClearance = savedRetirementList.get(i);
                                    clearanceBeanLocal.edit(hrClearance);
                                    hrClearance = null;
                                    hrEmployees = new HrEmployees();
                                    hrClearanceModel = null;
                                }
                                if (isClearanceFinished(savedRetirementList)) {
                                    //do update
                                    hrEmployees.setEmpStatus(4);
                                    hrRetirementRequest.setStatus(5);
                                    employeeBeanLocal.edit(hrEmployees);
                                    retirementBeanLocal.edit(hrRetirementRequest);
                                }
                                JsfUtil.addSuccessMessage("Retirement successfully updated");
                            } else if ("new".equals(loadType)) {
                                for (int i = 0; i < clearanceList.size(); i++) {
                                    hrClearance = clearanceList.get(i);
                                    hrClearance.setEmpId(hrRetirementRequest.getEmpId());
                                    hrClearance.setClearanceType(dropDownType);
                                    hrClearance.setRetirementId(hrRetirementRequest);
                                    clearanceBeanLocal.save(hrClearance);
                                    hrClearance = null;
                                    hrEmployees = new HrEmployees();
                                    hrClearanceModel = null;
                                }
                                if (isClearanceFinished(clearanceList)) {
                                    //do update
                                    hrEmployees.setEmpStatus(4);
                                    hrRetirementRequest.setStatus(5);
                                    employeeBeanLocal.edit(hrEmployees);
                                    retirementBeanLocal.edit(hrRetirementRequest);
                                }
                                JsfUtil.addSuccessMessage("Retirement successfully saved");
                            }
                            break;
                        default:
                    }
                } else {
                    JsfUtil.addFatalMessage("Select list to clear");
                }

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            JsfUtil.addFatalMessage("Error occurs while clearing");
        }
    }
    //</editor-fold>
}
