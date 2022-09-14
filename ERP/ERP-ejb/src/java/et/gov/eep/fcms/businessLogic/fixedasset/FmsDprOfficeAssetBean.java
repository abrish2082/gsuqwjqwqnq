/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.fixedasset;

import et.gov.eep.fcms.entity.fixedasset.FmsDprOfficeAsset;
import et.gov.eep.fcms.mapper.fixedasset.FmsDprOfficeAssetFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Binyam
 */
@Stateless
public class FmsDprOfficeAssetBean implements FmsDprOfficeAssetBeanLocal {

    @EJB
    FmsDprOfficeAssetFacade fmsDprOfficeAssetFacade;
    
    @Override
    public List<FmsDprOfficeAsset> findAll() {
        return fmsDprOfficeAssetFacade.findAll();
    }

    @Override
    public List<FmsDprOfficeAsset> findStatus1() {
        return fmsDprOfficeAssetFacade.findStatus1();
    }

    @Override
    public void edit(FmsDprOfficeAsset fmsDprOfficeAsset) {
        fmsDprOfficeAssetFacade.edit(fmsDprOfficeAsset);
    }

    @Override
    public void create(FmsDprOfficeAsset fmsDprOfficeAsset) {
        fmsDprOfficeAssetFacade.create(fmsDprOfficeAsset);
    }

    @Override
    public List<FmsDprOfficeAsset> fetchOfficeAsset(FmsDprOfficeAsset fmsDprOfficeAsset) {
        return fmsDprOfficeAssetFacade.fetchOfficeAsset(fmsDprOfficeAsset);
    }

    @Override
    public FmsDprOfficeAsset findByTagandStatus(FmsDprOfficeAsset fmsDprOfficeAsset) {
        return fmsDprOfficeAssetFacade.findByTagandStatus(fmsDprOfficeAsset);
    }

    
    
}
