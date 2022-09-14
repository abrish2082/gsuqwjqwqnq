/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.businessLogic;

import et.gov.eep.commonApplications.entity.MmsLuDmArchive;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author insa
 */
@Local
public interface MmsLuDmArchiveBeanLocal {

    public List<MmsLuDmArchive> getFileList(MmsLuDmArchive mmsLuDmArchive);

    public void saveFile(MmsLuDmArchive mmsLuDmArchive);
    
}
