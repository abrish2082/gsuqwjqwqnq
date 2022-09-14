/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsInspection;
import et.gov.eep.mms.entity.MmsInspectionDetail;
import et.gov.eep.mms.mapper.MmsInspectionDetailFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class MmsInspectionDetailBean implements MmsInspectionDetailBeanLocal {

   @EJB 
   MmsInspectionDetailFacade inspectiondetailFacade;

    /**
     *
     * @param selectedInspNo
     * @return
     */
    @Override
    public MmsInspectionDetail getAllDataWiththisInspNo(String selectedInspNo) {
        return inspectiondetailFacade.getByInspectionNumber(selectedInspNo);
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    /**
     *
     * @param inspection
     * @return
     */
    @Override
    public List<MmsInspectionDetail> getlistofInspectionDetails(MmsInspection inspection) {
      return inspectiondetailFacade.searchByinspectionId(inspection);
              }  
}
