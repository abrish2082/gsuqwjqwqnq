/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.integration;

import et.gov.eep.mms.entity.MmsDisposalDtl;
import et.gov.eep.mms.mapper.MmsDisposalDtlFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Me
 */
@Stateless
public class MmsDisposalDtlIntegrationBean implements MmsDisposalDtlIntegrationBeanLocal {

    @EJB
    MmsDisposalDtlFacade mmsDisposalDtlFacade;
    
    @Override
    public List<MmsDisposalDtl> fetchNewDisposedItems(){
        return mmsDisposalDtlFacade.fetchNewDisposedItems();
    }

    @Override
    public MmsDisposalDtl getDisposedItem(MmsDisposalDtl mmsDisposalDtl) {
        return mmsDisposalDtlFacade.getDisposedItem(mmsDisposalDtl);
    }
}
