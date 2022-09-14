/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollAllEmpEdSetupsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollEarningDeductionsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollMonthlyTransactionLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPeriodsBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author user
 */
@Named(value = "leaveAdvancePayment")
@ViewScoped
public class LeaveAdvancePayment implements Serializable {

    /**
     * Creates a new instance of LeaveAdvancePayment
     */
    public LeaveAdvancePayment() {
    }
//<editor-fold defaultstate="collapsed" desc="Objects & Variables">

    @Inject
    HrPayrollPeriods hrPayrollPeriods;
    @Inject
    HrPayrollPeriods hrPayrollFrom;
    @Inject
    HrPayrollPeriods hrPayrollTo;
    @Inject
    HrPayrollPeriods activePayrollCode;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    HrPayrollEarningDeductions hrPayrollEarningDeductions;
    @Inject
    HrEmployees hrEmployees;
    @EJB
    HrPayrollEarningDeductionsBeanLocal hrPayrollEarningDeductionsLocal;
    @EJB
    HrPayrollPeriodsBeanLocal hrPayrollPeriodsLocal;
    @EJB
    HrEmployeeBeanLocal hrEmployeeBean;
    @EJB
    HrPayrollAllEmpEdSetupsBeanLocal hrPayrollAllEmpEdSetupsFacade;
    @EJB
    HrPayrollMonthlyTransactionLocal hrPayrollMonTransactionsFacadeLocal;

    SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
    private String toCode;
    private String fromCode;
    private String payFromDate;
    private String payToDate;
    private String lastClosedPayrollDate;
    private List<HrPayrollPeriods> payrollFrom;
    private List<HrPayrollPeriods> listOfPayrollPeriods;
    private List<HrPayrollPeriods> payrollTo;
    private List<HrPayrollPeriods> listOfTrans;
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="PostConstract">

    @PostConstruct
    public void _init() {
        try {
            payrollFrom = hrPayrollPeriodsLocal.loadListOfInactivePayrollDates();
            payrollTo = hrPayrollPeriodsLocal.findAll();//this is for back payment
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getters & Setters">

    public String getPayFromDate() {
        return payFromDate;
    }

    public void setPayFromDate(String payFromDate) {
        this.payFromDate = payFromDate;
    }

    public String getPayToDate() {
        return payToDate;
    }

    public void setPayToDate(String payToDate) {
        this.payToDate = payToDate;
    }

    public HrPayrollPeriods getActivePayrollCode() {
        if (activePayrollCode == null) {
            activePayrollCode = new HrPayrollPeriods();
        }
        return activePayrollCode;
    }

    public void setActivePayrollCode(HrPayrollPeriods activePayrollCode) {
        this.activePayrollCode = activePayrollCode;
    }

    public String getLastClosedPayrollDate() {
        return lastClosedPayrollDate;
    }

    public void setLastClosedPayrollDate(String lastClosedPayrollDate) {
        this.lastClosedPayrollDate = lastClosedPayrollDate;
    }

    public HrPayrollPeriods getHrPayrollFrom() {
        if (hrPayrollFrom == null) {
            hrPayrollFrom = new HrPayrollPeriods();
        }
        return hrPayrollFrom;
    }

    public void setHrPayrollFrom(HrPayrollPeriods hrPayrollFrom) {
        this.hrPayrollFrom = hrPayrollFrom;
    }

    public HrPayrollPeriods getHrPayrollTo() {

        if (hrPayrollTo == null) {
            hrPayrollTo = new HrPayrollPeriods();
        }
        return hrPayrollTo;
    }

    public void setHrPayrollTo(HrPayrollPeriods hrPayrollTo) {
        this.hrPayrollTo = hrPayrollTo;
    }

    public HrPayrollPeriods getHrPayrollPeriods() {
        if (hrPayrollPeriods == null) {
            hrPayrollPeriods = new HrPayrollPeriods();
        }
        return hrPayrollPeriods;
    }

    public void setHrPayrollPeriods(HrPayrollPeriods hrPayrollPeriods) {
        this.hrPayrollPeriods = hrPayrollPeriods;
    }

    public List<HrPayrollPeriods> getListOfPayrollPeriods() {
        listOfPayrollPeriods = hrPayrollPeriodsLocal.findAll();
        return listOfPayrollPeriods;
    }

    public List<HrPayrollPeriods> getPayrollFrom() {
        return payrollFrom;
    }

    public void setPayrollFrom(List<HrPayrollPeriods> payrollFrom) {
        this.payrollFrom = payrollFrom;
    }

    public List<HrPayrollPeriods> getPayrollTo() {
        return payrollTo;
    }

    public void setPayrollTo(List<HrPayrollPeriods> payrollTo) {
        this.payrollTo = payrollTo;
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

    public List<HrPayrollPeriods> getListOfTrans() {
        return listOfTrans;
    }

    public void setListOfTrans(List<HrPayrollPeriods> listOfTrans) {
        this.listOfTrans = listOfTrans;
    }

    public String getToCode() {
        return toCode;
    }

    public void setToCode(String toCode) {
        this.toCode = toCode;
    }

    public String getFromCode() {
        return fromCode;
    }

    public void setFromCode(String fromCode) {
        this.fromCode = fromCode;
    }
    private String toCodeDate;

    public String getToCodeDate() {
        return toCodeDate;
    }

    public void setToCodeDate(String toCodeDate) {
        this.toCodeDate = toCodeDate;
    }
    private String fromDate;

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Main Methods">

    public void getByFirstName(SelectEvent event) {
        try {
            hrEmployees.setFirstName(event.getObject().toString());
            hrEmployees = hrEmployeeBean.getByEmpId(hrEmployees);
            listOfTrans = null;
            listOfTrans = hrPayrollMonTransactionsFacadeLocal.returnLeaveAdvance(hrEmployees);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<HrEmployees> SearchByFname(String hrEmployee) {
        ArrayList<HrEmployees> employees = null;
        hrEmployees.setEmpId(hrEmployee);
        employees = hrEmployeeBean.SearchByEmpId(hrEmployees);
        return employees;
    }

    public String returnYearAndMonth(String dateToSplit) {
        try {
            String dates[] = dateToSplit.split("/");
            return dates[1] + "/" + dates[2];
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public String getReturnYM(String dateToSplit) {
        String dates[] = dateToSplit.split("/");
        return dates[1] + "/" + dates[2];
    }

    public String returnYear(String dateToSplit) {
        try {
            String dates[] = dateToSplit.split("/");
            return dates[2];
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String returnMonth(String dateToSplit) {
        try {
            String dates[] = dateToSplit.split("/");
            return dates[1];

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public void handleValueChangeFrom(ValueChangeEvent event) {
        try {
            fromCode = event.getNewValue().toString();
            BigDecimal big = new BigDecimal(fromCode);
            hrPayrollPeriods.setId(big);
            hrPayrollFrom = hrPayrollPeriodsLocal.findPayrollPeriod(hrPayrollPeriods);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void handleChangeFrom() {
        try {

            BigDecimal selectedCode = new BigDecimal(fromCode);
            hrPayrollEarningDeductions.setCode(selectedCode);
            hrPayrollPeriods.setId(selectedCode);
            hrPayrollPeriods = hrPayrollPeriodsLocal.findPayrollPeriod(hrPayrollPeriods);
            payrollTo = hrPayrollPeriodsLocal.payrollTo(returnYear(hrPayrollPeriods.getPaymentMadeForTheMonthOf()), returnMonth(hrPayrollPeriods.getPaymentMadeForTheMonthOf()));

        } catch (Exception e) {
        }
    }

    public void handleValueChangeTo(ValueChangeEvent event) {
        try {
            toCode = event.getNewValue().toString();
            BigDecimal big = new BigDecimal(toCode);
            hrPayrollPeriods.setId(big);
            hrPayrollTo = hrPayrollPeriodsLocal.findPayrollPeriod(hrPayrollPeriods);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void handleChangeTo() {
        try {
            BigDecimal selectedCode = new BigDecimal(toCode);
            hrPayrollEarningDeductions.setCode(selectedCode);
        } catch (Exception e) {
        }
    }

    public void addMessage(String msg) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void generateLeaveAdvance1() {
        try {
            addMessage("Successfully Generated!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void generateLeaveAdvance() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "generateLeaveAdvance", dataset)) {
                if (hrEmployees.getId() == null) {
                    addMessage("First search an employee!");
                } else {
                    if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Net Pay") == null) {
                        addMessage("First Define the Net Pay");
                    } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Tax") == null) {
                        addMessage("First Define the Tax");
                    } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Pension Addition") == null) {
                        addMessage("First Define the Pension Addition");
                    } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Pension Deduction") == null) {
                        addMessage("First Define the Pension Deduction");
                    } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Gross Salary") == null) {
                        addMessage("First Define the Gross Salary");
                    } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Earnings") == null) {
                        addMessage("First Define the Total Earnings");
                    } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Deduction") == null) {
                        addMessage("First Define the Total Deduction");
                    } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Taxable Earnings") == null) {
                        addMessage("First Define the Total Taxable Earnings");
                    } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Leave Advance") == null) {
                        addMessage("First Define the Leave Advance");
                    } else {
                        hrPayrollAllEmpEdSetupsFacade.saveLeaveAdvance(activePayrollCode, hrEmployees, hrPayrollFrom, hrPayrollTo,
                                StringDateManipulation.nextMonthInEC(hrPayrollTo.getPaymentMadeForTheMonthOf(), "/"),
                                hrPayrollEarningDeductionsLocal.findCriteriaInfo("Tax"),
                                hrPayrollEarningDeductionsLocal.findCriteriaInfo("Net Pay"),
                                hrPayrollEarningDeductionsLocal.findCriteriaInfo("Pension Addition"),
                                hrPayrollEarningDeductionsLocal.findCriteriaInfo("Pension Deduction"),
                                hrPayrollEarningDeductionsLocal.findCriteriaInfo("Gross Salary"),
                                hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Earnings"),
                                hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Deduction"),
                                hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Taxable Earnings"),
                                hrPayrollEarningDeductionsLocal.findCriteriaInfo("Leave Advance")
                        );
                        listOfTrans = hrPayrollMonTransactionsFacadeLocal.returnLeaveAdvance(hrEmployees);
                        if (listOfTrans.isEmpty()) {
                            JsfUtil.addFatalMessage("Not Generated Successfully Make sure to define the tax and pention rates and to active aswell!");
                        }
                        addMessage("Successfully Generated!");
                    }
                }
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String returnReport() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            ServletContext servletContext = (ServletContext) externalContext.getContext();
            String returnResources = servletContext.getRealPath("\\");
            returnResources = returnResources + "erp\\hrms\\payroll\\leaveAdvancePayment.jasper";
            return returnResources;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public BufferedImage returnImage() {
        try {
            File imgFile = new File(returnLogo());
            BufferedImage img = ImageIO.read(imgFile);
            return img;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public void test() {
        try {
            BufferedImage originalImage
                    = ImageIO.read(new File("c:\\image\\mypic.jpg"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
        }
    }

    public StreamedContent getProductImage() throws IOException, SQLException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            BufferedImage originalImage
                    = ImageIO.read(new File("c:\\image\\mypic.jpg"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            String id = context.getExternalContext().getRequestParameterMap()
                    .get("pid");
            byte[] image = imageInByte;
            return new DefaultStreamedContent(new ByteArrayInputStream(image));
        }
    }

    public void generateMonthlyReport(ActionEvent actionEvent) throws JRException, IOException {
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("LOGO", returnImage());
        File jasper = new File(returnReport());
        JRDataSource datasource = new JRBeanCollectionDataSource(hrPayrollAllEmpEdSetupsFacade.loadLeaveAdvancePyment(hrEmployees), false);
        JasperPrint print = JasperFillManager.fillReport(jasper.getPath(), hm, datasource);
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=Payroll.pdf");
        ServletOutputStream stream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(print, stream);
        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public String returnLogo() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            ServletContext servletContext = (ServletContext) externalContext.getContext();
            String returnResources = servletContext.getRealPath("\\");
            returnResources = returnResources + "resources\\image\\logo2.jpg";
            return returnResources;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
//</editor-fold>
}
