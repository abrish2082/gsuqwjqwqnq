/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

   // <editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.commonApplications.businessLogic.EthiopianCalendarBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.mapper.admin.FmsGeneralLedgerFacade;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import et.gov.eep.prms.mapper.PrmsServiceAndWorkRegFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
// </editor-fold>

/**
 *
 * @author sadik
 */
@Stateless
public class ServiceRegistrationBean implements ServiceRegistrationBeanLocal {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    PrmsServiceAndWorkRegFacade serviceAndWorkRegFacade;
    @EJB
    EthiopianCalendarBeanLocal ethiopianCalendarBeanLocal;
    @EJB
    FmsGeneralLedgerFacade fmsGeneralLedgerFacade;
    // </editor-fold>

    @Override
    public void create(PrmsServiceAndWorkReg serviceAndWorkReg) {
        serviceAndWorkRegFacade.create(serviceAndWorkReg);
    }

    @Override
    public ArrayList<PrmsServiceAndWorkReg> searchServiceInfoByServiceName(PrmsServiceAndWorkReg serviceAndWorkReg) {
        return serviceAndWorkRegFacade.searchServiceInfoByServiceName(serviceAndWorkReg);
    }

    @Override
    public void edit(PrmsServiceAndWorkReg serviceAndWorkReg) {
        serviceAndWorkRegFacade.edit(serviceAndWorkReg);
    }

    @Override
    public PrmsServiceAndWorkReg getServiceInfoByName(PrmsServiceAndWorkReg serviceAndWork) {
        return serviceAndWorkRegFacade.getServiceInfoByName(serviceAndWork);
    }

    @Override
    public PrmsServiceAndWorkReg getWorkInfoByName(PrmsServiceAndWorkReg serviceAndWork) {
        return serviceAndWorkRegFacade.getWorkInfoByName(serviceAndWork);
    }

    @Override
    public List<PrmsServiceAndWorkReg> searchWorkInfoByWorkName(PrmsServiceAndWorkReg serviceAndWorkReg) {
        return serviceAndWorkRegFacade.searchWorkInfoByWorkName(serviceAndWorkReg);
    }

    @Override
    public List<PrmsServiceAndWorkReg> findAll(PrmsServiceAndWorkReg serviceAndWorkReg) {
        return serviceAndWorkRegFacade.findAllof(serviceAndWorkReg);
    }

    @Override
    public List<FmsGeneralLedger> getGeneralLedgerCodes() {
        return fmsGeneralLedgerFacade.getGeneralLedgerCodeList();
    }

    @Override
    public List<PrmsServiceAndWorkReg> searchServiceByGeneralLedgerIDAndRegistratioType(PrmsServiceAndWorkReg serviceAndWorkEntity) {
        return serviceAndWorkRegFacade.searchServiceByGeneralLedgerIDAndRegistratioType(serviceAndWorkEntity);
    }

    @Override
    public String getServOrWorkSeqNo(String checkAsRegTypeIsServ) {
        String eYear = ethiopianCalendarBeanLocal.getEthiopianCurrentDate();
        String serv_Work_No = null;
        String prefix = null;
        int maxNo = 0;
        if (checkAsRegTypeIsServ.equals("service")) {
            prefix = "SN";
        } else if (checkAsRegTypeIsServ.equals("work")) {
            prefix = "WN";
        }
        List<PrmsServiceAndWorkReg> prmsServiceAndWorkList = serviceAndWorkRegFacade.getServOrWorkSeqNo(prefix, eYear);
        for (int i = 0; i < prmsServiceAndWorkList.size(); i++) {
            if (prefix.equals("SN")) {
                serv_Work_No = prmsServiceAndWorkList.get(i).getServiceNo();
            } else if (prefix.equals("WN")) {
                serv_Work_No = prmsServiceAndWorkList.get(i).getWorkNo();
            }
            String[] lastInspNos = serv_Work_No.split("-");
            String lastDatesPatern = lastInspNos[1];
            System.out.println("1 " + lastDatesPatern);
            String[] lastDatesPaterns = lastDatesPatern.split("/");
            int increment = Integer.parseInt(lastDatesPaterns[0]);
            System.out.println("increment " + increment);
            if (maxNo < increment) {
                maxNo = increment;
            }
        }
        maxNo = maxNo + 1;
        serv_Work_No = (prefix + "-" + maxNo + "/" + eYear);
        return serv_Work_No;
    }

    @Override
    public List<PrmsServiceAndWorkReg> getParamNameList() {
        return serviceAndWorkRegFacade.getParamNameList();
    }

}
