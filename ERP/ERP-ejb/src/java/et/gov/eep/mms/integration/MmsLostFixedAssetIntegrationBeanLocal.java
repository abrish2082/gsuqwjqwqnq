/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.integration;

import et.gov.eep.mms.entity.MmsLostFixedAssetDetail;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Me
 */
@Local
public interface MmsLostFixedAssetIntegrationBeanLocal {
    public List<MmsLostFixedAssetDetail> fetchLostItems();
    public MmsLostFixedAssetDetail getLostItem(MmsLostFixedAssetDetail mmsLostFixedAssetDetail);
}
