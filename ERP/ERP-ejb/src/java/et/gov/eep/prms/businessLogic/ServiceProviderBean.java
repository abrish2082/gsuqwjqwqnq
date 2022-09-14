/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

    // <editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.prms.entity.PrmsServiceProvider;
import et.gov.eep.prms.entity.PrmsServiceProviderDetail;
import et.gov.eep.prms.mapper.PrmsServiceProviderDetailFacade;
import et.gov.eep.prms.mapper.PrmsServiceProviderFacade;
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
public class ServiceProviderBean implements ServiceProviderBeanLocal {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    PrmsServiceProviderDetailFacade prmsServiceProviderDetailFacade;
    @EJB
    private PrmsServiceProviderFacade papmsServiceProviderFacade;

    // </editor-fold>

    @Override
    public List<PrmsServiceProvider> searchServiceProviderByName(PrmsServiceProvider papmsServiceProvider) {
        return papmsServiceProviderFacade.searchServiceProvideByName(papmsServiceProvider);
    }

    @Override
    public void create(PrmsServiceProvider papmsServiceProvider) {
        papmsServiceProviderFacade.create(papmsServiceProvider);
    }

    @Override
    public void update(PrmsServiceProvider papmsServiceProvider) {
        papmsServiceProviderFacade.edit(papmsServiceProvider);
    }

    @Override
    public PrmsServiceProvider getServiceProvider(PrmsServiceProvider papmsServiceProvider) {
        return papmsServiceProviderFacade.getServiceName(papmsServiceProvider);
    }

    @Override
    public PrmsServiceProvider getLastServiceNo() {
        return papmsServiceProviderFacade.getLastServiceNo();
    }

    @Override
    public List<PrmsServiceProvider> searchServiceProvider(PrmsServiceProvider prmsServiceProvider) {
        return papmsServiceProviderFacade.searchServiceProvider(prmsServiceProvider);
    }

    @Override
    public void deleteServiceProvideDetail(PrmsServiceProviderDetail prmsServiceProviderDetail) {
        prmsServiceProviderDetailFacade.remove(prmsServiceProviderDetail);
    }

    @Override
    public PrmsServiceProvider getSelectedRequest(BigDecimal id) {
        return papmsServiceProviderFacade.getSelectedRequest(id);
    }

    @Override
    public ArrayList<PrmsServiceProvider> getserviceproviders() {
        return papmsServiceProviderFacade.getserviceproviders();
    }

    @Override
    public List<PrmsServiceProvider> getParamNameList() {
         return papmsServiceProviderFacade.getParamNameList();
    }

}
