/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsGrnDetail;
import et.gov.eep.mms.mapper.MmsGrnDetailFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class MmsGrnDetailBean implements MmsGrnDetailBeanLocal {

    @EJB
    MmsGrnDetailFacade grndetailfacade;

    /**
     *
     * @param id
     * @return
     */
    @Override
    public MmsGrnDetail getDetailbyId(MmsGrn id) {
        return grndetailfacade.getDetailbyId(id);
    }

    /**
     *
     * @param mmsGrnDetail
     * @return
     */
    @Override
    public MmsGrnDetail getGrnDetailInfosByMatcode(MmsGrnDetail mmsGrnDetail) {
        return grndetailfacade.getGrnDetailInfosByMatcode(mmsGrnDetail);
    }

    @Override
    public MmsGrnDetail getlastGrnDtlId() {
        return grndetailfacade.getLastGrnDtlId();
    }

    @Override
    public List<MmsGrnDetail> searchMmsGrnDetailByItemId(int id) {
        return grndetailfacade.searchMmsGrnDetailByItemId(id);
    }

    @Override
    public void edit(MmsGrnDetail grnDetail) {
        grndetailfacade.edit(grnDetail);
    }

    @Override
    public List<MmsGrnDetail> listOfGrnDetailsByItemId(int itemId, MmsGrn mmsGrn) {
        return grndetailfacade.listOfGrnDetailsByItemId(itemId, mmsGrn);
    }

}
