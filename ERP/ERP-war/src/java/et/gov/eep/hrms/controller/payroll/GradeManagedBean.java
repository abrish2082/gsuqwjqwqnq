/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;
//<editor-fold defaultstate="collapsed" desc="Imports">

import et.gov.eep.hrms.businessLogic.payroll.HrPayrollAllEmpEdSetupsBeanLocal;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.image.BufferedImage;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollEarningDeductionsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPeriodsBeanLocal;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
//</editor-fold>

/**
 *
 * @author user
 */
@Named(value = "gradeManagedBean")
@ViewScoped
public class GradeManagedBean implements Serializable {

    /**
     * Creates a new instance of GradeManagedBean
     */
    public GradeManagedBean() {
    }
//<editor-fold defaultstate="collapsed" desc="Objects & Variables">
    @Inject
    HrPayrollPeriods payrollPeriod;
    @Inject
    HrPayrollPeriods hrPayrollPeriodsEntity;
    @EJB
    HrPayrollPeriodsBeanLocal hrPayrollPeriodsLocal;
    @EJB
    HrPayrollEarningDeductionsBeanLocal hrPayrollEarningDeductionsLocal;
    @EJB
    HrPayrollAllEmpEdSetupsBeanLocal hrPayrollAllEmpEdSetupsFacade;

    private String fromDate;
    private String fromCode;
    List<HrPayrollPeriods> hrPayrollPeriods;
    private List<HrPayrollPeriods> payrollFrom;
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getters & Setters">

    public HrPayrollPeriods getPayrollPeriod() {
        if (payrollPeriod == null) {
            payrollPeriod = new HrPayrollPeriods();
        }
        return payrollPeriod;
    }

    public void setPayrollPeriod(HrPayrollPeriods payrollPeriod) {
        this.payrollPeriod = payrollPeriod;
    }

    public List<HrPayrollPeriods> getHrPayrollPeriods() {
        return hrPayrollPeriods;
    }

    public void setHrPayrollPeriods(List<HrPayrollPeriods> hrPayrollPeriods) {
        this.hrPayrollPeriods = hrPayrollPeriods;
    }

    public List<HrPayrollPeriods> getPayrollFrom() {
        payrollFrom = hrPayrollPeriodsLocal.findAll();

        return payrollFrom;
    }

    public void setPayrollFrom(List<HrPayrollPeriods> payrollFrom) {
        this.payrollFrom = payrollFrom;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getReturnYM(String dateToSplit) {
        String dates[] = dateToSplit.split("/");
        return dates[1] + "/" + dates[2];
    }

    public String getFromCode() {
        return fromCode;
    }

    public void setFromCode(String fromCode) {
        this.fromCode = fromCode;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Main Methods">

    public void handleValueChangeFrom(ValueChangeEvent event) {
        try {
            fromCode = event.getNewValue().toString();
            BigDecimal big = new BigDecimal(fromCode);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void save(ActionEvent actionEvent) throws JRException, IOException {

        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("title", "hello");
        String fileName = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Report3.jasper");
        File jasper = new File("D:\\report8.jasper");
        JRDataSource datasource = new JRBeanCollectionDataSource(hrPayrollAllEmpEdSetupsFacade.loadMonthlyPayroll(""), false);
        JasperPrint print = JasperFillManager.fillReport(jasper.getPath(), hm, datasource);
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        System.out.println("Test 4");
        response.addHeader("Content-disposition", "attachment; filename=Report2.pdf");
        System.out.println("Test 5");
        ServletOutputStream stream = response.getOutputStream();
        System.out.println("Test 6");
        JasperExportManager.exportReportToPdfStream(print, stream);
        System.out.println("Test 7");
        stream.flush();
        stream.close();
        System.out.println("Test 8");
        FacesContext.getCurrentInstance().responseComplete();
        System.out.println("Test 9");

    }

    public String returnReport() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            ServletContext servletContext = (ServletContext) externalContext.getContext();
            String returnResources = servletContext.getRealPath("\\");
            returnResources = returnResources + "erp\\hrms\\payroll\\netPay.jasper";
            return returnResources;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
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

    public String getReturnResources() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            ServletContext servletContext = (ServletContext) externalContext.getContext();
            String returnResources = servletContext.getRealPath("\\");
            returnResources = returnResources + "erp\\hrms\\payroll\\report8.jasper";
            File folder = new File(returnResources);
            File[] listOfFiles = folder.listFiles();
            File[] allFiles = folder.listFiles();
            int m = 0;
            int directory = 0;
            for (File file : listOfFiles) {
                if (file.isDirectory()) {
                    directory = directory + 1;
                }
            }
            String[] directorySize = new String[directory];
            int dd = 0;
            for (File s : listOfFiles) {
                if (s.isDirectory()) {
                    directorySize[dd] = s.getCanonicalPath();
                    dd = dd + 1;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public BufferedImage returnImage() {
        try {
//            File f = new File("D:\\logo2.jpg");
            File imgFile = new File(returnLogo());
            BufferedImage img = ImageIO.read(imgFile);
            return img;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public void generateMonthlyReport(ActionEvent actionEvent) throws JRException, IOException {
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("LOGO", returnImage());
        BigDecimal big = new BigDecimal(fromCode);
        payrollPeriod.setId(big);
        payrollPeriod = hrPayrollPeriodsLocal.findPayrollPeriod(payrollPeriod);
        hm.put("MONTH", hrPayrollPeriodsLocal.findPayrollPeriod(payrollPeriod).getPaymentMadeForTheMonthOf());
        File jasper = new File(returnReport());
        JRDataSource datasource = new JRBeanCollectionDataSource(hrPayrollAllEmpEdSetupsFacade.loadMonthlyPayroll(fromCode), false);
        JasperPrint print = JasperFillManager.fillReport(jasper.getPath(), hm, datasource);
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=Payroll.pdf");
        ServletOutputStream stream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(print, stream);
        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }

//</editor-fold>
}
