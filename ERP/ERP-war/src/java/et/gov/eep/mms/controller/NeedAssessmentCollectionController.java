/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.io.Serializable;
import org.primefaces.event.SelectEvent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.businessLogic.budgetYearLookUpBeanLocal;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.businessLogic.MmsNeedAssessmentBeanLocal;
import et.gov.eep.mms.businessLogic.MmsNeedAssessmentDtlBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsNeedAssessment;
import et.gov.eep.mms.entity.MmsNeedAssessmentDtl;
import et.gov.eep.mms.entity.MmsNeedAssessmentService;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.entity.MmsSubCat;


/**
 *
 * @author Minab
 */
@Named(value = "needAssessmentCollectionController")
@ViewScoped
public class NeedAssessmentCollectionController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="EJB">
    @EJB
    WfMmsProcessedBeanLocal workFlowInterface;
    @EJB
    private MmsNeedAssessmentBeanLocal needAssessmentinterfaces;
    @EJB
    private MmsNeedAssessmentDtlBeanLocal needAssessmentDtlInterfaces;
    @EJB
    private MmsStoreInformationBeanLocal storeInfoInterface;
    @EJB
    private budgetYearLookUpBeanLocal budgetYearLookUpBean;
    @EJB
    private FmsAccountingPeriodBeanLocal accountingPeriodBean;
    //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="Entities">
    @Inject
    private MmsNeedAssessment needAssessmentEntitys;
    @Inject
    private MmsNeedAssessment needAssessmentEntitysTemp;
    @Inject
    FmsLuBudgetYear fmsLubudgetYear;
    @Inject
    private MmsNeedAssessmentDtl needAssessmentDtlEntitys;
    @Inject
    private MmsNeedAssessmentService needAssessmentServiceDtlEntitys;
    @Inject

    private MmsItemRegistration itemRegstrationEntitys;
    @Inject
    private MmsSubCat subCategoryEntitys;
    @Inject
    private HrDepartments hrDepartmentsEntitys;
    @Inject
    private MmsStoreInformation storeInfoEntity;
    @Inject
    WfMmsProcessed workFlow;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="Fields">
    private DataModel<MmsNeedAssessmentDtl> mmsNeedAssessDtlCollectedDataModel;
    private DataModel<MmsNeedAssessmentService> mmsNeedAssessCollectedServiceDataModel;
    private DataModel<MmsNeedAssessmentDtl> requestingDepartmentsDataModel;
    private DataModel<MmsNeedAssessmentService> requestingDepartmentsDataModelForServiceAndWork;
    private String needAssessComRendered = "false";
    private String compSaveRend = "false";
    private final String regex = ",";
    private MmsNeedAssessmentDtl selectedNeed;
    private MmsNeedAssessmentService selectedService;
    private List<MmsNeedAssessmentDtl> departmentList;
    private List<MmsNeedAssessmentService> departmentListForWorkAndService;
    private boolean renderDecision = false;
    private String UserName;
    List<FmsLuBudgetYear> activePeriodList;
    private boolean renderStore = true;
    private boolean renderGoods = true;
    private boolean renderService = false;
    private boolean renderWork = false;
    private boolean renderServiceAndWorkTbl = false;
    String selectedValue = "";
    ArrayList<MmsNeedAssessment> needAssessmetListByYear;
    int requesterDepStatus = 0;
    private final String checkedStatus = "checked";
    private final String notCheckedStatus = "Not checked";
    int calcStatus = 0;
    int communitPrice = 0;
    ArrayList<MmsNeedAssessmentDtl> needAssessmetDetailCompileList;
    ArrayList<MmsNeedAssessmentService> needAssessmetServicesDetailCompileList;
    List<MmsStoreInformation> StoreList;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="PostConstruct">
    /**
     * Creates a new instance of NeedAssessmentCollectionController
     */
    public NeedAssessmentCollectionController() {
    }

    @PostConstruct
    public void initwfMmsProcessed() {

        FmsLuBudgetYear fmsLuBudgetYear = new FmsLuBudgetYear();
        FmsLuBudgetYear fmsLuBudgetYear2 = new FmsLuBudgetYear();
        FmsAccountingPeriod activePer = new FmsAccountingPeriod();
        activePer = accountingPeriodBean.getCurretActivePeriod();
        System.out.println("========acctivepre==" + activePer);
        fmsLuBudgetYear = activePer.getLuBudgetYearId();
        System.out.println("========acctivepre==" + fmsLuBudgetYear);
        activePeriodList = new ArrayList<>();
        activePeriodList.add(fmsLuBudgetYear);
        int year1 = fmsLuBudgetYear.getLuBudgetYearId() + 1;
        fmsLuBudgetYear2.setLuBudgetYearId(year1);
        fmsLuBudgetYear2 = budgetYearLookUpBean.findBgtYearbyId(fmsLuBudgetYear2);
        activePeriodList.add(fmsLuBudgetYear2);

        setUserName(SessionBean.getUserName());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());

    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public MmsNeedAssessmentService getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(MmsNeedAssessmentService selectedService) {
        this.selectedService = selectedService;
    }

    public boolean isRenderServiceAndWorkTbl() {
        return renderServiceAndWorkTbl;
    }

    public void setRenderServiceAndWorkTbl(boolean renderServiceAndWorkTbl) {
        this.renderServiceAndWorkTbl = renderServiceAndWorkTbl;
    }

    public boolean isRenderStore() {
        return renderStore;
    }

    public void setRenderStore(boolean renderStore) {
        this.renderStore = renderStore;
    }

    public boolean isRenderGoods() {
        return renderGoods;
    }

    public void setRenderGoods(boolean renderGoods) {
        this.renderGoods = renderGoods;
    }

    public boolean isRenderService() {
        return renderService;
    }

    public void setRenderService(boolean renderService) {
        this.renderService = renderService;
    }

    public boolean isRenderWork() {
        return renderWork;
    }

    public void setRenderWork(boolean renderWork) {
        this.renderWork = renderWork;
    }

    public String getNeedAssessComRendered() {
        return needAssessComRendered;
    }

    public void setNeedAssessComRendered(String needAssessComRendered) {
        this.needAssessComRendered = needAssessComRendered;
    }

    public MmsNeedAssessmentDtl getSelectedNeed() {
        return selectedNeed;
    }

    public void setSelectedNeed(MmsNeedAssessmentDtl selectedNeed) {
        this.selectedNeed = selectedNeed;
    }

    public String getCompSaveRend() {
        return compSaveRend;
    }

    public void setCompSaveRend(String compSaveRend) {
        this.compSaveRend = compSaveRend;
    }

    public MmsNeedAssessment getNeedAssessmentEntitys() {
        if (needAssessmentEntitys == null) {
            needAssessmentEntitys = new MmsNeedAssessment();
        }
        return needAssessmentEntitys;
    }

    public void setNeedAssessmentEntitys(MmsNeedAssessment needAssessmentEntitys) {
        this.needAssessmentEntitys = needAssessmentEntitys;
    }

    public MmsNeedAssessment getNeedAssessmentEntitysTemp() {
        if (needAssessmentEntitysTemp == null) {
            needAssessmentEntitysTemp = new MmsNeedAssessment();
        }
        return needAssessmentEntitysTemp;
    }

    public void setNeedAssessmentEntitysTemp(MmsNeedAssessment needAssessmentEntitysTemp) {
        this.needAssessmentEntitysTemp = needAssessmentEntitysTemp;
    }

    public MmsNeedAssessmentService getNeedAssessmentServiceDtlEntitys() {
        if (needAssessmentServiceDtlEntitys == null) {
            needAssessmentServiceDtlEntitys = new MmsNeedAssessmentService();
        }
        return needAssessmentServiceDtlEntitys;
    }

    public void setNeedAssessmentServiceDtlEntitys(MmsNeedAssessmentService needAssessmentServiceDtlEntitys) {
        this.needAssessmentServiceDtlEntitys = needAssessmentServiceDtlEntitys;
    }

    public MmsNeedAssessmentDtl getNeedAssessmentDtlEntitys() {
        if (needAssessmentDtlEntitys == null) {
            needAssessmentDtlEntitys = new MmsNeedAssessmentDtl();
        }
        return needAssessmentDtlEntitys;
    }

    public void setNeedAssessmentDtlEntitys(MmsNeedAssessmentDtl needAssessmentDtlEntitys) {
        this.needAssessmentDtlEntitys = needAssessmentDtlEntitys;
    }

    public MmsItemRegistration getItemRegstrationEntitys() {
        if (itemRegstrationEntitys == null) {
            itemRegstrationEntitys = new MmsItemRegistration();
        }
        return itemRegstrationEntitys;
    }

    public void setItemRegstrationEntitys(MmsItemRegistration itemRegstrationEntitys) {
        this.itemRegstrationEntitys = itemRegstrationEntitys;
    }

    public MmsSubCat getSubCategoryEntitys() {
        if (subCategoryEntitys == null) {
            subCategoryEntitys = new MmsSubCat();
        }
        return subCategoryEntitys;
    }

    public void setSubCategoryEntitys(MmsSubCat subCategoryEntitys) {
        this.subCategoryEntitys = subCategoryEntitys;
    }

    public HrDepartments getHrDepartmentsEntitys() {
        if (hrDepartmentsEntitys == null) {
            hrDepartmentsEntitys = new HrDepartments();
        }
        return hrDepartmentsEntitys;
    }

    public void setHrDepartmentsEntitys(HrDepartments hrDepartmentsEntitys) {
        this.hrDepartmentsEntitys = hrDepartmentsEntitys;
    }

    public MmsStoreInformation getStoreInfoEntity() {
        if (storeInfoEntity == null) {
            storeInfoEntity = new MmsStoreInformation();
        }
        return storeInfoEntity;
    }

    public void setStoreInfoEntity(MmsStoreInformation storeInfoEntity) {
        this.storeInfoEntity = storeInfoEntity;
    }

    public DataModel<MmsNeedAssessmentDtl> getMmsNeedAssessDtlCollectedDataModel() {
        if (mmsNeedAssessDtlCollectedDataModel == null) {
            mmsNeedAssessDtlCollectedDataModel = new ListDataModel<>();
        }
        return mmsNeedAssessDtlCollectedDataModel;
    }

    public void setMmsNeedAssessDtlCollectedDataModel(DataModel<MmsNeedAssessmentDtl> mmsNeedAssessDtlCollectedDataModel) {
        this.mmsNeedAssessDtlCollectedDataModel = mmsNeedAssessDtlCollectedDataModel;
    }

    public DataModel<MmsNeedAssessmentService> getMmsNeedAssessCollectedServiceDataModel() {
        if (mmsNeedAssessCollectedServiceDataModel == null) {
            mmsNeedAssessCollectedServiceDataModel = new ListDataModel<>();
        }
        return mmsNeedAssessCollectedServiceDataModel;
    }

    public void setMmsNeedAssessCollectedServiceDataModel(DataModel<MmsNeedAssessmentService> mmsNeedAssessCollectedServiceDataModel) {
        this.mmsNeedAssessCollectedServiceDataModel = mmsNeedAssessCollectedServiceDataModel;
    }

    public DataModel<MmsNeedAssessmentDtl> getRequestingDepartmentsDataModel() {
        if (requestingDepartmentsDataModel == null) {
            requestingDepartmentsDataModel = new ListDataModel<>();
        }
        return requestingDepartmentsDataModel;
    }

    public void setRequestingDepartmentsDataModel(DataModel<MmsNeedAssessmentDtl> requestingDepartmentsDataModel) {
        this.requestingDepartmentsDataModel = requestingDepartmentsDataModel;
    }

    public DataModel<MmsNeedAssessmentService> getRequestingDepartmentsDataModelForServiceAndWork() {
        if (requestingDepartmentsDataModelForServiceAndWork == null) {
            requestingDepartmentsDataModelForServiceAndWork = new ListDataModel<>();
        }
        return requestingDepartmentsDataModelForServiceAndWork;
    }

    public void setRequestingDepartmentsDataModelForServiceAndWork(DataModel<MmsNeedAssessmentService> requestingDepartmentsDataModelForServiceAndWork) {
        this.requestingDepartmentsDataModelForServiceAndWork = requestingDepartmentsDataModelForServiceAndWork;
    }

    public WfMmsProcessed getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(WfMmsProcessed workFlow) {
        this.workFlow = workFlow;
    }

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public WorkFlow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(WorkFlow workflow) {
        this.workflow = workflow;
    }

    public FmsLuBudgetYear getFmsLubudgetYear() {
        if (fmsLubudgetYear == null) {
            fmsLubudgetYear = new FmsLuBudgetYear();
        }
        return fmsLubudgetYear;
    }

    public void setFmsLubudgetYear(FmsLuBudgetYear fmsLubudgetYear) {
        this.fmsLubudgetYear = fmsLubudgetYear;
    }

    public List<FmsLuBudgetYear> getActivePeriodList() {
        return activePeriodList;
    }

    public void setActivePeriodList(List<FmsLuBudgetYear> activePeriodList) {
        this.activePeriodList = activePeriodList;
    }

    public ArrayList<MmsNeedAssessmentService> getNeedAssessmetServicesDetailCompileList() {
        return needAssessmetServicesDetailCompileList;
    }

    public void setNeedAssessmetServicesDetailCompileList(ArrayList<MmsNeedAssessmentService> needAssessmetServicesDetailCompileList) {
        this.needAssessmetServicesDetailCompileList = needAssessmetServicesDetailCompileList;
    }

    public ArrayList<MmsNeedAssessmentDtl> getNeedAssessmetDetailCompileList() {
        return needAssessmetDetailCompileList;
    }

    public void setNeedAssessmetDetailCompileList(ArrayList<MmsNeedAssessmentDtl> needAssessmetDetailCompileList) {
        this.needAssessmetDetailCompileList = needAssessmetDetailCompileList;
    }

    public List<String> getYearList() {
        int currentYear = Integer.parseInt(StringDateManipulation.todayInEC().split("-")[0]);
        List<String> aa = new ArrayList<>();
        aa.add("--- Select One ---");
        for (int i = currentYear + 1; i >= 2000; i--) {
            aa.add(String.valueOf(i));
        }
        return aa;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="handler,populate,recreate and clear">
    public void handleSelectBudgetYear(ValueChangeEvent event) {

        if (event.getNewValue() != null) {
            fmsLubudgetYear = (FmsLuBudgetYear) event.getNewValue();
            System.out.println("======================byears==" + fmsLubudgetYear.getBudgetYear());
            needAssessmentEntitysTemp.setBudgetYearId(fmsLubudgetYear);
            needAssessmentEntitysTemp.setCheckedStatus(notCheckedStatus);
            needAssessmentEntitysTemp.setStatus(Constants.APPROVE_VALUE);
            needAssessmetListByYear = new ArrayList<>();
            if (needAssessmentEntitysTemp.getPurchaseType().equals("Goods")) {
                needAssessmetListByYear = needAssessmentinterfaces.getNeeddedAssessentByStoreAndYearAndCheckedStatus(needAssessmentEntitysTemp);
                setValues();
                needAssessCompiled();
            } else {
                needAssessmetListByYear = needAssessmentinterfaces.getNeeddedAssessentByBudgetYearAndCheckedStatus(needAssessmentEntitysTemp);
                setValues();
                needAssessmentServiceCompiled();
                System.out.println("===sizelist=" + needAssessmetListByYear.size());
                System.out.println("===sizelist=" + needAssessmetListByYear);
            }

            System.out.println("===sizelist=" + needAssessmetListByYear.size());

        }

    }

    public void setValues() {
        if (needAssessmetListByYear.size() > 0) {
            requesterDepStatus = 1;
            calcStatus = 0;
            needAssessComRendered = "true";

        }
    }

    public void populateDepartments(SelectEvent event) {
        departmentList = new ArrayList<>();
        System.out.println("==========inside action========");
        needAssessmentDtlEntitys = (MmsNeedAssessmentDtl) event.getObject();
        //String deprtments[]=needAssessmentDtlEntitys.getListOfDepartment().split(regex);
        String[] deprtments = (needAssessmentDtlEntitys.getListOfDepartment().split(regex));
        System.out.println("==============TEST===========" + deprtments);
        System.out.println("===========output=====" + Arrays.toString(needAssessmentDtlEntitys.getListOfDepartment().split(regex)));
        int lengths = (needAssessmentDtlEntitys.getListOfDepartment().split(regex).length);

        for (int i = 0; i < lengths; i++) {
            MmsNeedAssessmentDtl obj = new MmsNeedAssessmentDtl();

            String reqdepartmentName = "";
            reqdepartmentName = deprtments[i];
            String[] splitQuantity = reqdepartmentName.split("[\\(\\)]");

            String Quntity = splitQuantity[1];
            String requestingDepName = splitQuantity[0];
            obj.setItemId(needAssessmentDtlEntitys.getItemId());
            obj.setDepNames(requestingDepName);
            obj.setRequestQuantity(Integer.parseInt(Quntity));

            departmentList.add(obj);
        }

        recreateReqDepartments();
    }

    public void populateDepartmentsForServiceAndWork(SelectEvent event) {
        departmentListForWorkAndService = new ArrayList<>();
        System.out.println("==========inside action========");
        needAssessmentServiceDtlEntitys = (MmsNeedAssessmentService) event.getObject();
        //String deprtments[]=needAssessmentDtlEntitys.getListOfDepartment().split(regex);
        String[] deprtments = (needAssessmentServiceDtlEntitys.getListOfDepartment().split(regex));
        System.out.println("==============TEST===========" + deprtments);
        System.out.println("===========output=====" + Arrays.toString(needAssessmentServiceDtlEntitys.getListOfDepartment().split(regex)));
        int lengths = (needAssessmentServiceDtlEntitys.getListOfDepartment().split(regex).length);

        for (int i = 0; i < lengths; i++) {
            MmsNeedAssessmentService obj = new MmsNeedAssessmentService();

            String reqdepartmentName = "";
            reqdepartmentName = deprtments[i];
            String[] splitQuantity = reqdepartmentName.split("[\\(\\)]");

            String Quntity = splitQuantity[1];
            String requestingDepName = splitQuantity[0];
            obj.setServiceId(needAssessmentServiceDtlEntitys.getServiceId());
            obj.setDepNames(requestingDepName);
            obj.setRequestQuantity(Integer.parseInt(Quntity));

            departmentListForWorkAndService.add(obj);
        }

        recreateReqDepartmentsForServiceAndWork();
    }

    public List<MmsStoreInformation> getStoreList() {
        StoreList = storeInfoInterface.findAllStoreInfo();
        return StoreList;
    }

    public void handleSelectStoreName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            storeInfoEntity = (MmsStoreInformation) event.getNewValue();

            needAssessmentEntitysTemp.setStoreId(storeInfoEntity);

        }

    }

    public void handleSelectAssessmentType(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            System.out.println("=====event=" + event.getNewValue().toString());
            switch (event.getNewValue().toString()) {
                case "Goods":
                    setRenderStore(true);
                    setRenderService(false);
                    setRenderWork(false);
                    setRenderGoods(true);
                    setRenderServiceAndWorkTbl(false);
                    needAssessmentEntitysTemp.setPurchaseType("Goods");
                    break;
                case "Service":
                    setRenderStore(false);
                    setRenderService(true);
                    setRenderWork(false);
                    setRenderGoods(false);
                    setRenderServiceAndWorkTbl(true);
                    needAssessmentEntitysTemp.setPurchaseType("Service");
                    break;
                case "Work":
                    setRenderStore(false);
                    setRenderService(false);
                    setRenderWork(true);
                    setRenderGoods(false);
                    setRenderServiceAndWorkTbl(true);
                    needAssessmentEntitysTemp.setPurchaseType("Work");
                    break;
            }

            //needAssessmentEntitys.setStoreId(storeInfoEntity);
        }

    }

    public void recreateModelCompiledDetail() {
        mmsNeedAssessDtlCollectedDataModel = null;
        mmsNeedAssessDtlCollectedDataModel = new ListDataModel<>(needAssessmetDetailCompileList);
    }

    public void recreateModelCompiledServiceDetail() {
        mmsNeedAssessCollectedServiceDataModel = null;
        mmsNeedAssessCollectedServiceDataModel = new ListDataModel<>(needAssessmetServicesDetailCompileList);
    }

    public void recreateReqDepartments() {
        requestingDepartmentsDataModel = null;
        requestingDepartmentsDataModel = new ListDataModel<>(departmentList);

    }

    public void recreateReqDepartmentsForServiceAndWork() {
        requestingDepartmentsDataModelForServiceAndWork = null;
        requestingDepartmentsDataModelForServiceAndWork = new ListDataModel<>(departmentListForWorkAndService);

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="need Assess Compiled Info"> 
    private String needAssessCompiled() {
        if (calcStatus == 0) {
            needAssessmetDetailCompileList = new ArrayList<>();
            int needAssessmetListSize = needAssessmetListByYear.size();
            for (int i = 0; i < needAssessmetListSize; i++) {
                int detialSize = 0;
                detialSize = needAssessmetListByYear.get(i).getMmsNeedAssessmentDtlList().size();
                needAssessmentEntitys = null;
                needAssessmentEntitys = needAssessmetListByYear.get(i);

                for (int j = 0; j < detialSize; j++) {
                    needAssessmentDtlEntitys = null;
                    needAssessmentDtlEntitys = needAssessmentEntitys.getMmsNeedAssessmentDtlList().get(j);
                    int needAssessmentDtlCompSize = 0;
                    needAssessmentDtlCompSize = needAssessmetDetailCompileList.size();
                    if (needAssessmentDtlCompSize == 0) {
                        needAssessmentDtlEntitys.setQuantitycompiled(needAssessmentDtlEntitys.getQuantity());
                        needAssessmentDtlEntitys.setListOfDepartment(needAssessmentDtlEntitys.getAssessmetId().getDepartmentId().getDepName() + "(" + needAssessmentDtlEntitys.getQuantity() + ")");
                        needAssessmetDetailCompileList.add(needAssessmentDtlEntitys);
                    } else {
                        int addStatus = 0;
                        for (int k = 0; k < needAssessmentDtlCompSize; k++) {
                            if (needAssessmetDetailCompileList.get(k).getItemId().getMatName().equals(needAssessmentDtlEntitys.getItemId().getMatName())) {
                                int quantity = 0;
                                String depName;
                                quantity = needAssessmetDetailCompileList.get(k).getQuantitycompiled();
                                quantity = quantity + needAssessmentDtlEntitys.getQuantity();
                                needAssessmetDetailCompileList.get(k).setQuantitycompiled(quantity);

                                depName = needAssessmetDetailCompileList.get(k).getListOfDepartment();
                                depName = depName + " , " + needAssessmentDtlEntitys.getAssessmetId().getDepartmentId().getDepName() + "(" + needAssessmentDtlEntitys.getQuantity() + ")";
                                needAssessmetDetailCompileList.get(k).setListOfDepartment(depName);

                                k = needAssessmentDtlCompSize;
                                addStatus = 1;
                            }
                            if (k == needAssessmentDtlCompSize - 1) {
                                if (addStatus == 0) {
                                    needAssessmentDtlEntitys.setQuantitycompiled(needAssessmentDtlEntitys.getQuantity());
                                    needAssessmentDtlEntitys.setListOfDepartment(needAssessmentDtlEntitys.getAssessmetId().getDepartmentId().getDepName() + "(" + needAssessmentDtlEntitys.getQuantity() + ")");
                                    needAssessmetDetailCompileList.add(needAssessmentDtlEntitys);

                                }
                            }
                        }
                    }
                }
            }
            for (int n = 0; n < needAssessmetDetailCompileList.size(); n++) {
                int compquantity = 0;
                double totalPrice = 0;
                compquantity = needAssessmetDetailCompileList.get(n).getQuantitycompiled();
                totalPrice = compquantity * needAssessmetDetailCompileList.get(n).getUnitPrice().doubleValue();
                needAssessmetDetailCompileList.get(n).setTotalCompiledPrice(totalPrice);
            }
            compSaveRend = "false";
            String selectYear = needAssessmentEntitys.getBudgetYear();
            compSaveRend = "true";
            recreateModelCompiledDetail();
            calcStatus = 1;
        }
        return null;
    }

    private String needAssessmentServiceCompiled() {
        if (calcStatus == 0) {
            needAssessmetServicesDetailCompileList = new ArrayList<>();
            int needAssessmetListSize = needAssessmetListByYear.size();
            for (int i = 0; i < needAssessmetListSize; i++) {
                int detialSize = 0;
                detialSize = needAssessmetListByYear.get(i).getMmsNeedAssessmentServiceList().size();
                needAssessmentEntitys = null;
                needAssessmentEntitys = needAssessmetListByYear.get(i);
                for (int j = 0; j < detialSize; j++) {
                    needAssessmentServiceDtlEntitys = null;
                    needAssessmentServiceDtlEntitys = needAssessmentEntitys.getMmsNeedAssessmentServiceList().get(j);
                    int needAssessmentDtlCompSize = 0;
                    needAssessmentDtlCompSize = needAssessmetServicesDetailCompileList.size();
                    if (needAssessmentDtlCompSize == 0) {
                        needAssessmentServiceDtlEntitys.setQuantitycompiled(needAssessmentServiceDtlEntitys.getQuantity());
                        needAssessmentServiceDtlEntitys.setListOfDepartment(needAssessmentServiceDtlEntitys.getAssessmentId().getDepartmentId().getDepName() + "(" + needAssessmentServiceDtlEntitys.getQuantity() + ")");
                        needAssessmetServicesDetailCompileList.add(needAssessmentServiceDtlEntitys);
                    } else {
                        int addStatus = 0;
                        for (int k = 0; k < needAssessmentDtlCompSize; k++) {
                            if (needAssessmetServicesDetailCompileList.get(k).getServiceId().getServiceName().equals(needAssessmentServiceDtlEntitys.getServiceId().getServiceName())) {
                                int quantity = 0;
                                String depName;
                                quantity = needAssessmetServicesDetailCompileList.get(k).getQuantitycompiled();
                                quantity = quantity + needAssessmentServiceDtlEntitys.getQuantity();
                                System.out.println("====================quantity=====" + quantity);
                                needAssessmetServicesDetailCompileList.get(k).setQuantitycompiled(quantity);

                                depName = needAssessmetServicesDetailCompileList.get(k).getListOfDepartment();
                                depName = depName + " , " + needAssessmentServiceDtlEntitys.getAssessmentId().getDepartmentId().getDepName() + "(" + needAssessmentServiceDtlEntitys.getQuantity() + ")";
                                System.out.println("====================dapName=====" + depName);
                                needAssessmetServicesDetailCompileList.get(k).setListOfDepartment(depName);

                                k = needAssessmentDtlCompSize;
                                addStatus = 1;
                            }
                            if (k == needAssessmentDtlCompSize - 1) {
                                if (addStatus == 0) {
                                    needAssessmentServiceDtlEntitys.setQuantitycompiled(needAssessmentServiceDtlEntitys.getQuantity());
                                    System.out.println("=============quantity==========" + needAssessmentServiceDtlEntitys.getQuantity());
                                    needAssessmentServiceDtlEntitys.setListOfDepartment(needAssessmentServiceDtlEntitys.getAssessmentId().getDepartmentId().getDepName() + "(" + needAssessmentDtlEntitys.getQuantity() + ")");
                                    needAssessmetServicesDetailCompileList.add(needAssessmentServiceDtlEntitys);

                                }
                            }
                        }
                    }
                }
            }
            for (int n = 0; n < needAssessmetServicesDetailCompileList.size(); n++) {
                int compquantity = 0;
                double totalPrice = 0;
                compquantity = needAssessmetServicesDetailCompileList.get(n).getQuantitycompiled();
                System.out.println("===================compquantity==" + compquantity);
                System.out.println("===================UnitPrice==" + needAssessmetServicesDetailCompileList.get(n).getUnitPrice().doubleValue());
                totalPrice = compquantity * needAssessmetServicesDetailCompileList.get(n).getUnitPrice().doubleValue();
                needAssessmetServicesDetailCompileList.get(n).setTotalCompiledPrice(totalPrice);

            }    
            compSaveRend = "false";
            String selectYear = needAssessmentEntitys.getBudgetYear();
            compSaveRend = "true";
            recreateModelCompiledServiceDetail();
            calcStatus = 1;
        }
        return null;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="save and clear">
/*This method is used to save Work flows
     */
    public void wfSave() {
        System.out.println("inside wf save");
        workFlowInterface.create(workFlow);
    }
    /*This method is used to Save Need Assessment Collected
     */

    public void saveNeedAssessmentCollected() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveNeedAssessmentCollected", dataset)) {
                if (needAssessmentEntitysTemp.getPurchaseType().equals("Goods")) {
                    System.out.println("==========goods===");
                    if (mmsNeedAssessDtlCollectedDataModel.getRowCount() > 0) {
                        for (int b = 0; b < needAssessmetDetailCompileList.size(); b++) {
                            needAssessmetDetailCompileList.get(b).getAssessmetId().setCheckedStatus("checked");
                            System.out.println("============1====");
                            needAssessmentEntitys.setCheckdedBy(SessionBean.getUserId());

                            needAssessmentDtlInterfaces.edit(needAssessmetDetailCompileList.get(b));
                            System.out.println("============2====");
                            needAssessmentinterfaces.edit(needAssessmetDetailCompileList.get(b).getAssessmetId());
                            System.out.println("============3====" + needAssessmetDetailCompileList.get(b).getAssessmetId());
                            needAssessmentinterfaces.edit(needAssessmentEntitys);
                        }

                        JsfUtil.addSuccessMessage("Need Assessment is upadted");
                        clear();
                    } else {
                        JsfUtil.addFatalMessage("Data Table Should Be filled");
                    }
                } else {
                    if (mmsNeedAssessCollectedServiceDataModel.getRowCount() > 0) {
                        needAssessmentinterfaces.edit(needAssessmentEntitys);
                        JsfUtil.addSuccessMessage("Need Assessment is upadted");
                        clear();
                    } else {
                        JsfUtil.addFatalMessage("Data Table Should Be filled");
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
        } catch (Exception ex) {

        }
    }
    /*This method is used to Clear Need Assessment Collected
     */

    public void clear() {

        storeInfoEntity = new MmsStoreInformation();
        needAssessmentEntitys = new MmsNeedAssessment();
        needAssessmentDtlEntitys = new MmsNeedAssessmentDtl();
        hrDepartmentsEntitys = new HrDepartments();
        mmsNeedAssessDtlCollectedDataModel = null;
        mmsNeedAssessCollectedServiceDataModel = null;
        needAssessmentEntitysTemp = null;
        fmsLubudgetYear = null;

    }
    //</editor-fold>
}
