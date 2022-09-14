/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsLcApplication;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.mapper.PrmsContractFacade;
import et.gov.eep.prms.mapper.PrmsLcApplicationFacade;
import et.gov.eep.prms.mapper.PrmsSupplyProfileFacade;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class LCApplicationBean implements LCApplicationBeanLocal {
    @EJB
    PrmsLcApplicationFacade prmsLcApplicationFacade;
    
    @EJB
    PrmsSupplyProfileFacade prmsSupplyProfileFacade;

    @EJB
    PrmsContractFacade prmsContractFacade;

    @Override
    public List<PrmsLcApplication> searchByLCAppNo(PrmsLcApplication prmsLcApplication) {
        return prmsLcApplicationFacade.getLCAppNo(prmsLcApplication);
    }

    @Override
    public void update(PrmsLcApplication prmsLcApplication) {
        prmsLcApplicationFacade.edit(prmsLcApplication);
    }

    @Override
    public void create(PrmsLcApplication prmsLcApplication) {
        prmsLcApplicationFacade.create(prmsLcApplication);
    }

    @Override
    public PrmsLcApplication getSelectedLCApp(BigDecimal id) {
        return prmsLcApplicationFacade.getSelectedId(id);
    }

    @Override
    public PrmsLcApplication getLastLCAppNo() {
        return prmsLcApplicationFacade.getLastLCAppNo();
    }

    @Override
    public List<PrmsSupplyProfile> listOfSuppName() {
        return prmsSupplyProfileFacade.findAll();
    }

    @Override
    public List<PrmsContract> listOfContractNO() {
        return prmsContractFacade.findAll();
  }

  }
