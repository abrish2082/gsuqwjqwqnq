/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.integration;

import et.gov.eep.mms.entity.MmsFixedassetRegstDetail;
import et.gov.eep.mms.mapper.MmsFixedassetRegstDetailFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Me
 */
@Stateless
public class MmsFixedAssetRegstDetailIntegrationBean implements MmsFixedAssetRegstDetailIntegrationBeanLocal {

    @EJB
    MmsFixedassetRegstDetailFacade mmsFixedassetRegstDetailFacade;

    @Override
    public MmsFixedassetRegstDetail getOfficeAsset(MmsFixedassetRegstDetail mmsFixedassetRegstDetail) {
        return mmsFixedassetRegstDetailFacade.findByTag(mmsFixedassetRegstDetail);
    }
   
}
