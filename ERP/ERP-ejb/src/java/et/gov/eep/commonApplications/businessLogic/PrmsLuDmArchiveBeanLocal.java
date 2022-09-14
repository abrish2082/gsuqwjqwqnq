/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.businessLogic;

import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author mora1
 */
@Local
public interface PrmsLuDmArchiveBeanLocal {

    public void saveFileInfo(PrmsLuDmArchive prmsLuDmArchive);

    public void updateFileInfo(PrmsLuDmArchive prmsLuDmArchive);

    public List<PrmsLuDmArchive> getFileLists(PrmsLuDmArchive prmsLuDmArchive);

}
