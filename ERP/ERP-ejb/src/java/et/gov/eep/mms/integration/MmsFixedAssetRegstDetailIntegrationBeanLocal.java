/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.integration;

import et.gov.eep.mms.entity.MmsFixedassetRegstDetail;
import javax.ejb.Local;

/**
 *
 * @author Me
 */
@Local
public interface MmsFixedAssetRegstDetailIntegrationBeanLocal {
    public MmsFixedassetRegstDetail getOfficeAsset(MmsFixedassetRegstDetail mmsFixedassetRegstDetail);
}
