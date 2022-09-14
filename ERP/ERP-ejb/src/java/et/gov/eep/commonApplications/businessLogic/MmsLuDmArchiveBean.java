/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.businessLogic;

import et.gov.eep.commonApplications.entity.MmsLuDmArchive;
import et.gov.eep.commonApplications.mapper.MmsLuDmArchiveFacade;
import et.gov.eep.mms.businessLogic.MmsLandBeanLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author insa
 */
@Stateless
public class MmsLuDmArchiveBean implements MmsLuDmArchiveBeanLocal {

    @EJB
    MmsLuDmArchiveFacade mmsLuDmArchiveFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public List<MmsLuDmArchive> getFileList(MmsLuDmArchive mmsLuDmArchive) {
        return mmsLuDmArchiveFacade.getFileList(mmsLuDmArchive);
    }

    @Override
    public void saveFile(MmsLuDmArchive mmsLuDmArchive) {
        mmsLuDmArchiveFacade.create(mmsLuDmArchive);
    }
}
