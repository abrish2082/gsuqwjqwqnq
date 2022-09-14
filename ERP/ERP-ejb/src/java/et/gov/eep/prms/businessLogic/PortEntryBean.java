/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

   // <editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.commonApplications.businessLogic.EthiopianCalendarBeanLocal;
import et.gov.eep.prms.entity.PrmsPortEntry;
import et.gov.eep.prms.mapper.PrmsPortEntryFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
// </editor-fold>

/**
 *
 * @author Man
 */
@Stateless
public class PortEntryBean implements PortEntryBeanLocal {

    //<editor-fold defaultstate="collapsed" desc="Injected EJBs">
    @EJB
    private PrmsPortEntryFacade prmsPortEntryFacade;

    @EJB
    EthiopianCalendarBeanLocal ethiopianCalendarBeanLocal;
//</editor-fold>

    @Override
    public void update(PrmsPortEntry papmsPortEntry) {
        prmsPortEntryFacade.edit(papmsPortEntry);
    }

    @Override
    public void create(PrmsPortEntry papmsPortEntry) {
        prmsPortEntryFacade.create(papmsPortEntry);
    }

    @Override
    public ArrayList<PrmsPortEntry> searchPortByPortName(PrmsPortEntry prmsPortEntry) {
        return prmsPortEntryFacade.searchPortByPortName(prmsPortEntry);
    }

    @Override
    public PrmsPortEntry getPortName(PrmsPortEntry papmsPortEntry) {
        return prmsPortEntryFacade.getPortName(papmsPortEntry);
    }

    @Override
    public PrmsPortEntry getLastPortNo() {
        return prmsPortEntryFacade.getLastPortNo();
    }

    @Override
    public List<PrmsPortEntry> searchPort(PrmsPortEntry prmsPortEntry) {
        return prmsPortEntryFacade.searchPort(prmsPortEntry);

    }

    @Override
    public ArrayList<PrmsPortEntry> getCapitalBudgetDisbursment(PrmsPortEntry capitalBudget) {
        return prmsPortEntryFacade.getCapitalBudgetDisbursment(capitalBudget);
    }

    @Override
    public ArrayList<PrmsPortEntry> approveMaintReqList() {
        return prmsPortEntryFacade.approveMaintReqList();
    }

    @Override
    public List<PrmsPortEntry> DryPortList() {
        return prmsPortEntryFacade.DryPortList();
    }

    @Override
    public PrmsPortEntry getSelectedRequest(BigDecimal id) {
        return prmsPortEntryFacade.getSelectedRequest(id);
    }

    @Override
    public ArrayList<PrmsPortEntry> searchBidderRegistration() {
        return prmsPortEntryFacade.searchBidderRegistration();
    }

    @Override
    public String getNextPortNo() {
        String eYear = ethiopianCalendarBeanLocal.getEthiopianCurrentDate();
        System.out.println("Current Ethiopian Year " + eYear);
        String portNo = null;
        String prefix = null;
        int maxNo = 0;
        prefix = "Port-No";

        List<PrmsPortEntry> prmsPortEntryList = prmsPortEntryFacade.getNextPortNo(prefix, eYear);
        for (int i = 0; i < prmsPortEntryList.size(); i++) {
            portNo = prmsPortEntryList.get(i).getPortNo();
            String[] lastInspNos = portNo.split("-");
            String lastDatesPatern = lastInspNos[2];
            String[] lastDatesPaterns = lastDatesPatern.split("/");
            int increment = Integer.parseInt(lastDatesPaterns[0]);
            if (maxNo < increment) {
                maxNo = increment;
            }
        }
        maxNo = maxNo + 1;
        portNo = (prefix + "-" + maxNo + "/" + eYear);
        return portNo;
    }

    @Override
    public List<PrmsPortEntry> getParamNameList() {
        return prmsPortEntryFacade.getParamNameList();
    }
}
