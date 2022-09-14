/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsGrnDetail;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface MmsGrnDetailBeanLocal {

    /**
     *
     * @param id
     * @return
     */
    public MmsGrnDetail getDetailbyId(MmsGrn id);

    /**
     *
     * @param mmsGrnDetail
     * @return
     */
    MmsGrnDetail getGrnDetailInfosByMatcode(MmsGrnDetail mmsGrnDetail);

    public MmsGrnDetail getlastGrnDtlId();

    public List<MmsGrnDetail> searchMmsGrnDetailByItemId(int id);

    void edit(MmsGrnDetail grnDetail);

    public List<MmsGrnDetail> listOfGrnDetailsByItemId(int itemId,MmsGrn mmsGrn);

}
