/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.fixedasset;

import et.gov.eep.fcms.entity.fixedasset.FmsDprOfficeAsset;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Binyam
 */
@Local
public interface FmsDprOfficeAssetBeanLocal {
    public List<FmsDprOfficeAsset> findAll();
    
    public List<FmsDprOfficeAsset> findStatus1();

    public void edit(FmsDprOfficeAsset fmsDprOfficeAsset);

    public void create(FmsDprOfficeAsset fmsDprOfficeAsset);
    
    public List <FmsDprOfficeAsset> fetchOfficeAsset(FmsDprOfficeAsset fmsDprOfficeAsset);

    public FmsDprOfficeAsset findByTagandStatus(FmsDprOfficeAsset fmsDprOfficeAsset);
}
