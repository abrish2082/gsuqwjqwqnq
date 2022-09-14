/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.entity.PrmsLcRequest;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsServiceProvider;
import et.gov.eep.prms.mapper.LCRequestFacade;
import et.gov.eep.prms.mapper.PrmsContractFacade;
import et.gov.eep.prms.mapper.PrmsServiceProviderFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dege
 */
@Stateless
public class LCRequestBean implements LCRequestBeanLocal {

    @EJB
    LCRequestFacade lCRequestFacade;
    @EJB
    PrmsContractFacade prmsContractFacade;
    @EJB
    PrmsServiceProviderFacade prmsServiceProviderFacade;

    @Override
    public void create(PrmsLcRequest PrmsLcRequest) {
        lCRequestFacade.create(PrmsLcRequest);
    }

    @Override
    public void update(PrmsLcRequest PrmsLcRequest) {
        lCRequestFacade.edit(PrmsLcRequest);
    }
    

    @Override
    public List<PrmsLcRequest> findByRequestId(PrmsLcRequest PrmsLcRequest) {
       
        return lCRequestFacade.findByRequestId(PrmsLcRequest);
    }


    @Override
    public PrmsLcRequest getlastLCReqNo() {
        return lCRequestFacade.getlastLCReqNo();
    }

    @Override
    public List<PrmsContract> listOfContractNO() {
        return prmsContractFacade.findAll();
    }

    @Override
    public List<PrmsServiceProvider> listOfServiceNO() {
        return prmsServiceProviderFacade.findAll();
    }

    @Override
    public PrmsLcRequest getSelectedRequest(BigDecimal id) {
        return lCRequestFacade.getSelectedId(id);
    }

   
}
