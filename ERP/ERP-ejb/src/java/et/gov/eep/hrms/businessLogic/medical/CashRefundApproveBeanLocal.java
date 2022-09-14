/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.medical;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.hrms.entity.medical.HrLocalMedSettlements;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author INSA
 */
@Local
public interface CashRefundApproveBeanLocal {

    public void saveOrUpdate(HrLocalMedSettlements hrLocalMedSettlements);

    public List<SelectItem> filterByStatus();

    public List<HrLocalMedSettlements> loadMedicaCashRefund(int status);

    public HrLocalMedSettlements getSelectedRequest(int request);

    public ArrayList<HrLocalMedSettlements> findAllRequest();

    public HrLocalMedSettlements fetchSettlement(String refNo);

    public List<HrLocalMedSettlements> loadMedSettlementList(Status status, int userId);

    public List<HrLocalMedSettlements> findPreparedList();
}
