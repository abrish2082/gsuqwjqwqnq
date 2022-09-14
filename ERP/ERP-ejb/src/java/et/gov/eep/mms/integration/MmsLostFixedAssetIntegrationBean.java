/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.integration;

import et.gov.eep.mms.entity.MmsLostFixedAssetDetail;
import et.gov.eep.mms.mapper.MmsLostFixedAssetDetailFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Me
 */
@Stateless
public class MmsLostFixedAssetIntegrationBean implements MmsLostFixedAssetIntegrationBeanLocal {

    @EJB
    MmsLostFixedAssetDetailFacade mmsLostFixedAssetDetailFacade;
    
    @Override
    public List<MmsLostFixedAssetDetail> fetchLostItems() {
        return mmsLostFixedAssetDetailFacade.fetchLostItems();
    }

    @Override
    public MmsLostFixedAssetDetail getLostItem(MmsLostFixedAssetDetail mmsLostFixedAssetDetail) {
        return mmsLostFixedAssetDetailFacade.getLostItem(mmsLostFixedAssetDetail);
    }
}
