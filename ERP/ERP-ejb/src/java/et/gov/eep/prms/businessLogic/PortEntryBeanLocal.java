/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.entity.PrmsPortEntry;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Man
 */
@Local
public interface PortEntryBeanLocal {

    void update(PrmsPortEntry papmsPortEntry);

    void create(PrmsPortEntry papmsPortEntry);

    public ArrayList<PrmsPortEntry> searchPortByPortName(PrmsPortEntry papmsPortEntry);

    public PrmsPortEntry getPortName(PrmsPortEntry papmsPortEntry);

    public PrmsPortEntry getLastPortNo();

    List<PrmsPortEntry> searchPort(PrmsPortEntry prmsPortEntry);

    public ArrayList<PrmsPortEntry> getCapitalBudgetDisbursment(PrmsPortEntry capitalBudget);

    ArrayList<PrmsPortEntry> approveMaintReqList();

    public List<PrmsPortEntry> DryPortList();

    public PrmsPortEntry getSelectedRequest(BigDecimal id);

    public ArrayList<PrmsPortEntry> searchBidderRegistration();

    public String getNextPortNo();

    public List<PrmsPortEntry> getParamNameList();
}
