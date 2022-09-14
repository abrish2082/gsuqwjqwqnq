/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.entity.PrmsServiceProvider;
import et.gov.eep.prms.entity.PrmsServiceProviderDetail;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface ServiceProviderBeanLocal {

    public List<PrmsServiceProvider> searchServiceProviderByName(PrmsServiceProvider papmsServiceProvider);

    void create(PrmsServiceProvider papmsServiceProvider);

    void update(PrmsServiceProvider papmsServiceProvider);

    public PrmsServiceProvider getServiceProvider(PrmsServiceProvider papmsServiceProvider);

    public PrmsServiceProvider getLastServiceNo();

    List<PrmsServiceProvider> searchServiceProvider(PrmsServiceProvider prmsServiceProvider);

    public void deleteServiceProvideDetail(PrmsServiceProviderDetail prmsServiceProviderDetail);

    public PrmsServiceProvider getSelectedRequest(BigDecimal id);

    public ArrayList<PrmsServiceProvider> getserviceproviders();

    public List<PrmsServiceProvider> getParamNameList();
}
