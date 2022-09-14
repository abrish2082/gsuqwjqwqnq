/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface ServiceRegistrationBeanLocal {

    public void create(PrmsServiceAndWorkReg serviceAndWorkReg);

    public void edit(PrmsServiceAndWorkReg serviceAndWorkReg);

    public ArrayList<PrmsServiceAndWorkReg> searchServiceInfoByServiceName(PrmsServiceAndWorkReg serviceAndWorkReg);

    public PrmsServiceAndWorkReg getServiceInfoByName(PrmsServiceAndWorkReg serviceAndWork);

    public PrmsServiceAndWorkReg getWorkInfoByName(PrmsServiceAndWorkReg serviceAndWork);

    public List<PrmsServiceAndWorkReg> searchWorkInfoByWorkName(PrmsServiceAndWorkReg serviceAndWorkReg);

    public List<PrmsServiceAndWorkReg> findAll(PrmsServiceAndWorkReg serviceAndWorkReg);

    public List<FmsGeneralLedger> getGeneralLedgerCodes();

    public List<PrmsServiceAndWorkReg> searchServiceByGeneralLedgerIDAndRegistratioType(PrmsServiceAndWorkReg serviceAndWorkEntity);

    public String getServOrWorkSeqNo(String checkAsRegTypeIsServ);

    public List<PrmsServiceAndWorkReg> getParamNameList();
}
